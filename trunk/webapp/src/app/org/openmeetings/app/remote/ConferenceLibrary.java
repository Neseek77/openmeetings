package org.openmeetings.app.remote;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;

import org.apache.commons.lang.StringUtils;
import org.openmeetings.app.data.basic.files.*;
import org.openmeetings.app.data.basic.AuthLevelmanagement;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.user.Usermanagement;
import org.openmeetings.app.documents.LibraryDocumentConverter;
import org.openmeetings.app.documents.LibraryWmlLoader;
import org.openmeetings.app.documents.LoadLibraryPresentation;
import org.openmeetings.app.documents.LoadLibraryPresentationToObject;
import org.openmeetings.app.documents.CreateLibraryPresentation;
import org.openmeetings.app.remote.red5.Application;

/**
 * 
 * @author swagner
 *
 */
public class ConferenceLibrary {

	private static final Logger log = LoggerFactory.getLogger(ConferenceLibrary.class);
	
	private static ConferenceLibrary instance;

	public static synchronized ConferenceLibrary getInstance() {
		if (instance == null) {
			instance = new ConferenceLibrary();
		}
		return instance;
	}		
	
	protected HashMap<String,String> fileExtensions = new HashMap<String,String>();
	
	public ConferenceLibrary(){
		fileExtensions.put("ext1", ".ppt");
		fileExtensions.put("ext2", ".odp");
		fileExtensions.put("ext3", ".odt");
		fileExtensions.put("ext4", ".sxw");
		fileExtensions.put("ext5", ".wpd");
		fileExtensions.put("ext6", ".doc");
		fileExtensions.put("ext7", ".rtf");
		fileExtensions.put("ext8", ".txt");
		fileExtensions.put("ext9", ".ods");
		fileExtensions.put("ext10", ".sxc");
		fileExtensions.put("ext11", ".xls");
		fileExtensions.put("ext12", ".sxi");
		fileExtensions.put("ext13", ".pdf");
		fileExtensions.put("ext14", ".swf");
	}
	
	/**
	 * @deprecated use getListOfFilesObjectByAbsolutePath instead
	 * @param SID
	 * @param moduleName
	 * @param parentFolder
	 * @param room
	 * @param domain
	 * @param current_dir
	 * @return
	 */
	public LinkedHashMap<String,Object> getListOfFiles(String SID, String moduleName,
			String parentFolder, Long room_id ) {
		
		LinkedHashMap<String,Object> returnMap = new LinkedHashMap<String,Object>();
		
		try {
			
			LinkedList<LinkedList<String>> filesMap = new LinkedList<LinkedList<String>>();
			LinkedList<LinkedList<String>> foldersMap = new LinkedList<LinkedList<String>>();
			LinkedHashMap<String,LinkedHashMap> presentationObject = null;
						
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        
	        log.debug("#############users_id : "+users_id);
	        log.debug("#############user_level : "+user_level);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	
				String roomName = room_id.toString();
				
				String current_dir = Application.webAppPath+"/upload";
				String working_dir = current_dir + File.separatorChar + roomName + parentFolder;
				log.debug("#############working_dir : "+working_dir);

				File dir = new File(working_dir);
				
				//First get all Directories of this Folder
				FilenameFilter ff = new FilenameFilter() {
				     public boolean accept(File b, String name) {
				    	  String absPath = b.getAbsolutePath()+File.separatorChar+name;
				    	  File f = new File (absPath);
				          return f.isDirectory();
				     }
				};			
				
				String[] allfolders = dir.list(ff);
				if(allfolders!=null){
					for(int i=0; i<allfolders.length; i++){
						File file = new File(working_dir+File.separatorChar+allfolders[i]);
						Date lastModifiedDate = new Date(file.lastModified());
						String lastModified = formatDate(lastModifiedDate);
						String fileName = allfolders[i];
						log.debug("Found Folders, foldername: "+fileName);
						LinkedList<String> fileInfo = new LinkedList<String>();
						fileInfo.add("");
						fileInfo.add("");
						fileInfo.add(fileName);
						fileInfo.add(lastModified);
						foldersMap.add(fileInfo);
					}
				}
				
				
				//Secoond get all Files of this Folder
				FilenameFilter ff2 = new FilenameFilter() {
				     public boolean accept(File b, String name) {
				    	  String absPath = b.getAbsolutePath()+File.separatorChar+name;
				    	  File f = new File (absPath);
				          return f.isFile();
				     }
				};	
				
				String[] allfiles = dir.list(ff2);			
				if(allfiles!=null){
					for(int i=0; i<allfiles.length; i++){
						File file = new File(working_dir+File.separatorChar+allfiles[i]);
						
						System.out.println("working_dir+File.separatorChar+allfiles[i]: "+working_dir+File.separatorChar+allfiles[i]);
						if (allfiles[i].startsWith("_thumb_")){
							//log.error("Found Thumbs: "+allfiles[i]);
						} else {
							String lastModified = formatDate(new Date(file.lastModified()));
							String fileName = allfiles[i];
							String fileBytes = new Long(file.length()).toString();
							
							LinkedList<String> fileInfo = new LinkedList<String>();
							
							String fileNamePure = fileName.substring(0, fileName.length()-4);
							String fileNameExt = fileName.substring(fileName.length()-4,fileName.length());
							String isimage = "y";
							if(checkForPresention(fileNameExt.toLowerCase())) isimage = "n";
							log.debug("Found File, fileName: "+fileName);
							fileInfo.add(fileName);
							fileInfo.add(fileNamePure);
							fileInfo.add(fileNameExt);
							fileInfo.add(lastModified);
							fileInfo.add(fileBytes);
							fileInfo.add(isimage);
							filesMap.add(fileInfo);
							
							if (fileName.equals(CreateLibraryPresentation.libraryFileName)){
								presentationObject = LoadLibraryPresentation.getInstance().parseLibraryFileToObject(file.getAbsolutePath());
							}
								
						}
					}
				}				
				
				returnMap.put("presentationObject",presentationObject);
				returnMap.put("folders",foldersMap);
				returnMap.put("files",filesMap);
				returnMap.put("error", "");
				
				return returnMap;
	        } else {
	        	log.error("not Authentificated");
	        	returnMap.put("error", "not authenificated");
	        	return returnMap; 
	        }
	         
		} catch (Exception e) {
			log.error("[getListOfFilesByAbsolutePath]",e);
			e.printStackTrace();
			returnMap.put("error", e.getMessage());
			return returnMap; 
		}        

	}
	
	/**
	 * Method provide a List of Files-Object's
	 * @param SID
	 * @param moduleName
	 * @param parentFolder
	 * @param room
	 * @param domain
	 * @return
	 */
	public LiberaryObject getListOfFilesObjectByAbsolutePath(String SID, String moduleName,
			String parentFolder, Long room_id) {
		LiberaryObject returnMap = new LiberaryObject();
		try {
			String current_dir = Application.webAppPath+"/upload";
			
			LinkedHashMap<String,LinkedHashMap> presentationObject = null;
						
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        
	        log.debug("#############users_id : "+users_id);
	        log.debug("#############user_level : "+user_level);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){
	        	
				String roomName = room_id.toString();
				String working_dir = current_dir + File.separatorChar + roomName + parentFolder;
				log.debug("#############working_dir : "+working_dir);

				File dir = new File(working_dir);
				//First get all Directories of this Folder
				FilenameFilter ff = new FilenameFilter() {
				     public boolean accept(File b, String name) {
				    	  String absPath = b.getAbsolutePath()+File.separatorChar+name;
				    	  File f = new File (absPath);
				          return f.isDirectory();
				     }
				};			
				

				String[] allfolders = dir.list(ff);
				if(allfolders!=null && allfolders.length!=0){
					log.debug("INIT setFoldersList");
					returnMap.setFoldersList(new LinkedList<FoldersObject>());
					for(int i=0; i<allfolders.length; i++){
						File file = new File(working_dir+File.separatorChar+allfolders[i]);
						Date lastModifiedDate = new Date(file.lastModified());
						String lastModified = formatDate(lastModifiedDate);
						String folderName = allfolders[i];
						log.debug("Found Folders, foldername: "+folderName);
						FoldersObject folderInfo = new FoldersObject();
						folderInfo.setFolderName(folderName);
						folderInfo.setLastModified(lastModified);
						returnMap.getFoldersList().add(folderInfo);
					}
				}
				
				
				//Secoond get all Files of this Folder
				FilenameFilter ff2 = new FilenameFilter() {
				     public boolean accept(File b, String name) {
				    	  String absPath = b.getAbsolutePath()+File.separatorChar+name;
				    	  File f = new File (absPath);
				          return f.isFile();
				     }
				};	
				
				
				
				String[] allfiles = dir.list(ff2);			
				if(allfiles!=null && allfiles.length!=0){
					
					boolean isPresentation = false;
					//We need to split this up into 2 different process
					//because Axis cannot serialize the empty
					//LinkedList and also it makes no sense to iterate through
					//all files if it is a Presentation-File which is handled different
					for(int i=0; i<allfiles.length; i++){
						File file = new File(working_dir+File.separatorChar+allfiles[i]);
						
						System.out.println("working_dir+File.separatorChar+allfiles[i]: "+working_dir+File.separatorChar+allfiles[i]);
						if (allfiles[i].startsWith("_thumb_")){
							//log.error("Found Thumbs: "+allfiles[i]);
						} else {
							//String lastModified = formatDate(new Date(file.lastModified()));
							String fileName = allfiles[i];
							
							if (fileName.equals(CreateLibraryPresentation.libraryFileName)){
								isPresentation = true;
								//returnMap.setPresentationObject(new PresentationObject());
								returnMap.setPresentationObject(LoadLibraryPresentationToObject.getInstance().parseLibraryFileToObject(file.getAbsolutePath()));
							}
								
						}
					}
					
					//Only do this if it ain't a Presentation-File
					if (!isPresentation){
						log.debug("INIT setFilesList");
						returnMap.setFilesList(new LinkedList<FilesObject>());
						for(int i=0; i<allfiles.length; i++){
							File file = new File(working_dir+File.separatorChar+allfiles[i]);
							
							System.out.println("working_dir+File.separatorChar+allfiles[i]: "+working_dir+File.separatorChar+allfiles[i]);
							if (allfiles[i].startsWith("_thumb_")){
								//log.error("Found Thumbs: "+allfiles[i]);
							} else {
								String lastModified = formatDate(new Date(file.lastModified()));
								String fileName = allfiles[i];
								String fileBytes = new Long(file.length()).toString();
								FilesObject fileInfo = new FilesObject();
								String fileNamePure = fileName.substring(0, fileName.length()-4);
								String fileNameExt = fileName.substring(fileName.length()-4,fileName.length());
								String isimage = "y";
								if(checkForPresention(fileNameExt.toLowerCase())) isimage = "n";
								log.debug("Found File, fileName: "+fileName);
								fileInfo.setFileName(fileName);
								fileInfo.setFileNamePure(fileNamePure);
								fileInfo.setFileNameExt(fileNameExt);
								fileInfo.setLastModified(lastModified);
								fileInfo.setFileBytes(fileBytes);
								fileInfo.setIsimage(isimage);
								returnMap.getFilesList().add(fileInfo);									
							}
						}
					}
				}				
				
				returnMap.setError("");
				
				return returnMap;
	        } else {
	        	log.error("not Authentificated");
	        	returnMap.setError("not authenificated");
	        	return returnMap; 
	        }
	         
		} catch (Exception e) {
			log.error("[getListOfFilesByAbsolutePath]",e);
			e.printStackTrace();
			returnMap.setError(e.getMessage());
			return returnMap; 
		}        

	}
	
	private String formatDate(Date date) {
		SimpleDateFormat formatter;
		String pattern = "dd/MM/yy H:mm:ss";
		Locale locale= new Locale("en","US");
		formatter = new SimpleDateFormat(pattern, locale);
		return formatter.format(date);
	}

	public Boolean deleteFile(String SID, String fileName, String moduleName, String parentFolder, Long room_id){
		try {		
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){		
						
				String current_dir = Application.webAppPath+"/upload";
				String roomName = room_id.toString();
				String working_dir = current_dir+File.separatorChar+roomName+parentFolder;
				log.debug("working_dir+fileName: "+working_dir+File.separatorChar+fileName);
				File dir = new File(working_dir+File.separatorChar+fileName);
				
				File thumb = new File(working_dir+File.separatorChar+"_thumb_"+fileName);
				if (thumb.exists()) thumb.delete();
				
				boolean returnVal = dir.delete();
				log.debug("delete file: "+working_dir+File.separatorChar+fileName);
				
				//Iterate through the Files if it is a directory
				if (!returnVal && dir.isDirectory()){
					String[] listOfFiles = dir.list();
					for (int i=0;i<listOfFiles.length;i++){
						log.debug("Deleting recursive: "+working_dir+File.separatorChar+fileName+File.separatorChar+listOfFiles[i]);
						File d2 = new File(working_dir+File.separatorChar+fileName+File.separatorChar+listOfFiles[i]);
						d2.delete();
						File thumb2 = new File(working_dir+File.separatorChar+fileName+File.separatorChar+"_thumb_"+listOfFiles[i]);
						if (thumb2.exists()) thumb2.delete();
					}
					dir.delete();
				}
				return returnVal;
	        }
		} catch (Exception e) {
			log.error("[deleteFile]: ",e);
		}
		return false;		
	}
	
	private boolean checkForPresention (String fileExtension) throws Exception{
		//log.debug("sys fileExtensions.size(): "+fileExtensions.size());
		Iterator<String> extensionIt = fileExtensions.keySet().iterator();
		while (extensionIt.hasNext()) {
			String fileExt = fileExtensions.get(extensionIt.next());
			//log.debug("sys fileExt: "+fileExt);
			if(fileExtension.equals(fileExt)){
				return true;
			}
		}
		return false;
	}	
	
	public String saveAsImage(Object t){
		try {
			log.error("saveAsImage",t);
		} catch (Exception err){
			log.error("[saveAsImage] "+err);
		}
		return null;
	}	
	
	public Long saveAsObject(String SID, Long room_id, String fileName, Object t){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){		
				LinkedHashMap tObject = (LinkedHashMap)t;
				log.debug("saveAsObject",tObject.size());
				
				String roomName = room_id.toString();				
				String current_dir = Application.webAppPath+"/upload"+File.separatorChar+roomName+File.separatorChar;

				log.error("### this is my working directory: "+current_dir);
				
				return LibraryDocumentConverter.getInstance().writeToLocalFolder(current_dir, fileName, tObject);
	        }
		} catch (Exception err){
			log.error("[saveAsImage] "+err);
		}	        
		return null;
	}
	
	public LinkedHashMap loadWmlObject(String SID, Long room_id, String fileName){
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);  
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)){		
				String roomName = room_id.toString();
				String current_dir = Application.webAppPath+"/upload"+File.separatorChar+roomName+File.separatorChar;
				log.debug("### this is my working directory: "+current_dir);
				
				return LibraryWmlLoader.getInstance().loadWmlFile(current_dir, fileName);
	        }
		} catch (Exception err){
			log.error("[saveAsImage] "+err);
		}
		return null;
	}
	
}
