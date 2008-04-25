package org.openmeetings.servlet.outputhandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmeetings.app.data.basic.Sessionmanagement;
import org.openmeetings.app.data.user.Usermanagement;

public class DownloadHandler extends HttpServlet {

	private static final Log log = LogFactory.getLog(DownloadHandler.class);
	
	private static final String defaultImageName = "deleted.jpg";
	private static final String defaultProfileImageName = "profile_pic.jpg";
	private static final String defaultProfileImageNameBig = "_big_profile_pic.jpg";
	private static final String defaultChatImageName = "_chat_profile_pic.jpg";
	private static final String defaultSWFName = "deleted.swf";
	private static final String defaultPDFName = "deleted.pdf";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			log.debug("sid: " + sid);

			Long users_id = Sessionmanagement.getInstance().checkSession(sid);
			Long user_level = Usermanagement.getInstance().getUserLevelByID(users_id);

			if (user_level!=null && user_level > 0) {
				String room_id = httpServletRequest.getParameter("room_id");
				if(room_id == null){
					room_id = "default";
				}	
				
				String moduleName = httpServletRequest.getParameter("moduleName");
				if (moduleName == null) {
					moduleName = "nomodule";
				}				
				
				String parentPath = httpServletRequest.getParameter("parentPath");
				if (parentPath == null) {
					parentPath = "nomodule";
				}
				
				String requestedFile = httpServletRequest.getParameter("fileName");
				if (requestedFile == null) {
					requestedFile = "";
				}
				
				//make a complete name out of domain(organisation) + roomname
				String roomName = room_id;
				//trim whitespaces cause it is a directory name
				roomName = StringUtils.deleteWhitespace(roomName);

				//Get the current User-Directory
				
				String current_dir = getServletContext().getRealPath("/");

				String working_dir = "";

				working_dir = current_dir+"upload"+File.separatorChar;
				
				// Add the Folder for the Room

				if (moduleName.equals("videoconf1")) {
					if (parentPath.length() != 0) {
						
						working_dir = working_dir + roomName
								+ File.separatorChar + parentPath
								+ File.separatorChar;
						
					} else {
						working_dir = current_dir + roomName
								+ File.separatorChar;
					}
				} else if (moduleName.equals("userprofile")){
					working_dir += "profiles" + File.separatorChar;
					File f = new File(working_dir);
					if (!f.exists()) {
						boolean c = f.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					working_dir += "profile_"+users_id + File.separatorChar;
					File f2 = new File(working_dir);
					if (!f2.exists()) {
						boolean c = f2.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
				} else if (moduleName.equals("remoteuserprofile")){
					working_dir += "profiles" + File.separatorChar;
					File f = new File(working_dir);
					if (!f.exists()) {
						boolean c = f.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					String remoteUser_id = httpServletRequest.getParameter("remoteUserid");
					if(remoteUser_id == null){
						remoteUser_id = "0";
					}
					
					working_dir += "profile_"+remoteUser_id + File.separatorChar;
					File f2 = new File(working_dir);
					if (!f2.exists()) {
						boolean c = f2.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
				} else if (moduleName.equals("remoteuserprofilebig")){
					working_dir += "profiles" + File.separatorChar;
					File f = new File(working_dir);
					if (!f.exists()) {
						boolean c = f.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					String remoteUser_id = httpServletRequest.getParameter("remoteUserid");
					if(remoteUser_id == null){
						remoteUser_id = "0";
					}
					
					working_dir += "profile_"+remoteUser_id + File.separatorChar;
					File f2 = new File(working_dir);
					if (!f2.exists()) {
						boolean c = f2.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					requestedFile = this.getBigProfileUserName(working_dir);
					
				} else if (moduleName.equals("chat")){
					
					working_dir += "profiles" + File.separatorChar;
					File f = new File(working_dir);
					if (!f.exists()) {
						boolean c = f.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					String remoteUser_id = httpServletRequest.getParameter("remoteUserid");
					if(remoteUser_id == null){
						remoteUser_id = "0";
					}	
					
					working_dir += "profile_"+remoteUser_id + File.separatorChar;
					File f2 = new File(working_dir);
					if (!f2.exists()) {
						boolean c = f2.mkdir();
						if (!c) {
							log.error("cannot write to directory");
						}
					}
					
					requestedFile = this.getChatUserName(working_dir);
					
				} else {
					working_dir = working_dir + roomName + File.separatorChar;
				}
				
				if (!moduleName.equals("nomodule")) {

					
					log.debug("requestedFile: " + requestedFile + " current_dir: "+working_dir);

					String full_path = working_dir + requestedFile;
					
					File f = new File(full_path);
					if (!f.exists() || !f.canRead()) {
						if (!f.canRead()){
							log.debug("LOG DownloadHandler: The request file is not readable");
						} else {
							log.debug("LOG DownloadHandler: The request file does not exist / has already been deleted");
						}
						log.debug("LOG ERROR requestedFile: "+requestedFile);
						//replace the path with the default picture/document
						
						if (requestedFile.endsWith(".jpg")){
							log.debug("LOG endsWith d.jpg");
							
							log.debug("LOG moduleName: "+moduleName);

							requestedFile = DownloadHandler.defaultImageName;
							if (moduleName.equals("remoteuserprofile")) {
								requestedFile = DownloadHandler.defaultProfileImageName;
							} else if (moduleName.equals("remoteuserprofilebig")) {
								requestedFile = DownloadHandler.defaultProfileImageNameBig;
							} else if (moduleName.equals("userprofile")) {
								requestedFile = DownloadHandler.defaultProfileImageName;
							} else if (moduleName.equals("chat")) {
								requestedFile = DownloadHandler.defaultChatImageName;
							}
							//request for an image
							full_path = current_dir + File.separatorChar + "default" + 
									File.separatorChar + requestedFile;
						} else if (requestedFile.endsWith(".swf")){
							requestedFile = DownloadHandler.defaultSWFName;
							//request for a SWFPresentation
							full_path = current_dir + File.separatorChar + "default" + 
									File.separatorChar + DownloadHandler.defaultSWFName;
						} else {
							//Any document, must be a download request
							requestedFile = DownloadHandler.defaultPDFName;
							full_path = current_dir + File.separatorChar + "default" + 
								File.separatorChar + DownloadHandler.defaultPDFName;
						}
					}
					
					log.debug("full_path: "+full_path);
					
					File f2 = new File(full_path);
					if (!f2.exists() || !f2.canRead()) {
						if (!f2.canRead()){
							log.debug("DownloadHandler: The request DEFAULT-file does not exist / has already been deleted");
						} else {
							log.debug("DownloadHandler: The request DEFAULT-file does not exist / has already been deleted");
						}
						//no file to handle abort processing
						return;
					}
					
					//Get file and handle download
					RandomAccessFile rf = new RandomAccessFile(full_path, "r");

					httpServletResponse.reset();
					httpServletResponse.resetBuffer();
					OutputStream out = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
					httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
					httpServletResponse.setHeader("Content-Length", ""+ rf.length());

					byte[] buffer = new byte[1024];
					int readed = -1;

					while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
						out.write(buffer, 0, readed);
					}

					rf.close();

					out.flush();
					out.close();

				}
			} else {
				System.out.println("ERROR DownloadHandler: not authorized FileDownload "+(new Date()));
			}

		} catch (Exception er) {
			System.out.println("Error downloading: " + er);
			er.printStackTrace();
		}
	}
	
	private String getChatUserName(String userprofile_folder) throws Exception{
		
		File f = new File(userprofile_folder);
		if (f.exists() && f.isDirectory()) {
			String filesString[] = f.list();
			for (int i=0;i<filesString.length;i++) {
				String fileName = filesString[i];
				if (fileName.startsWith("_chat_")) return fileName;
			}
		}
		return "_no.jpg";
	}
	
	private String getBigProfileUserName(String userprofile_folder) throws Exception{
		
		File f = new File(userprofile_folder);
		if (f.exists() && f.isDirectory()) {
			String filesString[] = f.list();
			for (int i=0;i<filesString.length;i++) {
				String fileName = filesString[i];
				if (fileName.startsWith("_big_")) return fileName;
			}
		}
		return "_no.jpg";
	}	

}
