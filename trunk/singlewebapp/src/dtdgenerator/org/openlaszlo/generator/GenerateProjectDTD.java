package org.openlaszlo.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;

import javax.xml.parsers.SAXParserFactory;

import org.openlaszlo.generator.elements.ClassElementList;
import org.openlaszlo.generator.elements.ElementList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class GenerateProjectDTD implements ContentHandler {
	
	private ClassElementList elementList = new ClassElementList();
	
	FilenameFilter folderFilter = new FilenameFilter(){
		 public boolean accept(File b, String name) {
			  //We do not scan folder that start with a "."
			  if (name.startsWith(".")) {
	    		  return false;
	    	  }
			  
	    	  String absPath = b.getAbsolutePath()+File.separatorChar+name;
	    	  File f = new File (absPath);
	    	  
	    	  if (f.isDirectory()) {
	    		  
	    		  File checkForIgnore = new File(absPath+File.separatorChar+".ignore_dtd");
	    		  if (checkForIgnore.exists()) {
	    			  return false;
	    		  }
	    		  
	    		  return true;
	    	  }
	    	  
	          return false;
	     }
	};
	
	FilenameFilter lzxFilter = new FilenameFilter(){
		 public boolean accept(File b, String name) {
	          return name.endsWith(".lzx");
	     }
	};
	
	private String currentClassName = "";

	private String currentFile;
	
	public static void main(String... args) {
		new GenerateProjectDTD("WebContent/src/");
	}
	
	public GenerateProjectDTD(String basePath) {
		this.scanFolder(basePath);
		
		// elementList.filePrint();
		elementList.filePrint(true, "project.dtd", "test/lzx.dtd");
	}
	
	public void scanFolder(String filePath) {
		try {
			
			File baseFolder = new File(filePath);
			
			if (!baseFolder.exists()) {
				throw new Exception("Base path does not exist "+filePath);
			}
			
			if (baseFolder.isFile()) {
				scanFile(filePath);
			} else if (baseFolder.isDirectory()) {
				for (String folder : baseFolder.list(folderFilter)) {
					scanFolder(filePath + File.separatorChar + folder);
				}
				for (String file : baseFolder.list(lzxFilter)) {
					scanFile(filePath + File.separatorChar +file);
				}
			}
			
			
		} catch (Exception err) {
			err.printStackTrace();
			System.err.println(err.getMessage());
		} 
	}
	
	public void scanFile(String filePath) {
		try {
			
			this.currentFile = filePath;
			
			InputSource is = new InputSource(filePath);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			XMLReader parser = factory.newSAXParser().getXMLReader();
			
			parser.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(
					new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
				}
			});

			
			parser.setContentHandler(this);
            parser.parse(is);
            
		} catch (Exception err) {
			err.printStackTrace();
			System.err.println(err.getMessage());
		}
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		currentClassName = "";
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		try {
		//System.out.println("startElement "+qName);
			if (qName.equals("class") || qName.equals("interface") ) {
				
				String className = atts.getValue("name");
				
				if (className == null) {
					return;
				}
				
				String extendsName = atts.getValue("extends");
				
				if (extendsName == null) {
					if (className.equals("node")) {
						extendsName = "";
					} else {
						extendsName = "node";
					}
				}
				
				currentClassName = className;
				elementList.addClassElement(className, extendsName);
				
			} else if (qName.equals("attribute")) {
				
				if (currentClassName.length() == 0) {
					return;
				}
				
				String attrName = atts.getValue("name");
				if (attrName != null) {
					elementList.addClassAttribute(attrName, false, currentClassName);
				}
				
			}
		} catch (Exception err) {
			System.err.println("Error in File "+currentFile);
			err.printStackTrace();
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		
		//Removes last occurrence of the element
		//openNodes.removeLastOccurrence(qName);
		
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
