package org.openlaszlo.generator.elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.xml.sax.Attributes;

public class ClassElementList {

	// Using natural order ?!
	private Map<String, ClassElement> elementList = new HashMap<String, ClassElement>();
	
	public void addClassElement(String name, String parent) {
		ClassElement element = elementList.get(name);
		
		if (element == null) {
			element = new ClassElement();
		}
		
		element.setParentAsString(parent);
		elementList.put(name, element);
		
	}
	
	public void addClassAttribute(String name, boolean required, String className) throws Exception {
		ClassElement element = elementList.get(className);
		
		if (element == null) {
			throw new Exception("Class not available "+className+ " "+name);
		}
		
		element.getAttributes().add(new ClassAttribute(name, required));
	}
	
	

	public void fixParents() {
		
		for (Entry<String, ClassElement> entry : elementList
				.entrySet()) {
			
			ClassElement element = entry.getValue();
			String className = entry.getKey();
			
			String parentAsString = element.getParentAsString();
			if (parentAsString.length() > 0) {
				ClassElement parent = elementList.get(parentAsString);
				
				if (parent == null) {
					System.err.println("Could not find parent "+parentAsString+ " Classname " +className);
				}
				
				element.setParent(parent);
				
			} else {
				
			}
			
		}

	}
	
	private void generateBaseClassTag() {

		ClassElement element = new ClassElement();
		element.setParentAsString("");
		
		element.getAttributes().add(new ClassAttribute("extends", false));
		
		for (Entry<String, ClassElement> entry : elementList
				.entrySet()) {
			
			ClassElement elementTemp = entry.getValue();
			element.getAttributes().addAll(elementTemp.getAllClassAttributes());
		}
		
		elementList.put("class", element);
		
	}
	
	public final String[] TEXT_OPTION_ENABLED = { "handler", "method", "text" };
	
	private boolean checkAllowSingleTextNode(String key) {
		for (String textOption : TEXT_OPTION_ENABLED) {
			if (textOption.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public void filePrint(boolean debug, String fileName, String baseDtd) {
		try {
			File f = new File(fileName);
			if (f.exists()){
				f.delete();
			}
			
			this.fixParents();
			
			this.generateBaseClassTag();
			
			f.createNewFile();
			
			OutputStream ou = new FileOutputStream(f);
			
			//ou.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes());
			
			if (baseDtd.length() > 0) {
				
				// Get file and handle download
				RandomAccessFile rf = new RandomAccessFile(baseDtd, "r");
					
				byte[] buffer = new byte[1024];
				int readed = -1;

				while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
					ou.write(buffer, 0, readed);
				}

				rf.close();

			}
			
			
			for (Entry<String, ClassElement> entry : elementList
					.entrySet()) {
				
				String className = entry.getKey();
				ClassElement element = entry.getValue();
				
				StringBuilder sBuilder = new StringBuilder();
				
				if (checkAllowSingleTextNode(className)) {
					sBuilder.append("<!ELEMENT " + className + " ( #PCDATA ) > \n");
				} else {
					sBuilder.append("<!ELEMENT " + className + " ANY > \n");
				}
				
				
				if (element.getAllClassAttributes().size() > 0) {
					sBuilder.append("<!ATTLIST " + className + " \n");
		
					for (ClassAttribute attr : element.getAllClassAttributes()) {
						sBuilder.append("    " + attr.getName() + " CDATA  #IMPLIED \n");
					}
					sBuilder.append(">\n");
				}
				
				if (debug) {
					System.out.print(sBuilder);
				}
				
				ou.write(sBuilder.toString().getBytes());
				
			}
			
			ou.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	

}
