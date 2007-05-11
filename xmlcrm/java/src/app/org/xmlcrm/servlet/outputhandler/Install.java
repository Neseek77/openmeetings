package org.xmlcrm.servlet.outputhandler;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context; 
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlcrm.app.documents.InstallationDocumentHandler;


public class Install extends VelocityViewServlet {

	private static final Log log = LogFactory.getLog(DownloadHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {

		try {
			
			String command = httpServletRequest.getParameter("command");
			
			String lang = httpServletRequest.getParameter("lang");
			if (lang == null) lang = "EN";
			
			String working_dir = getServletContext().getRealPath("/")+InstallationDocumentHandler.installFolderName;
			
			if (command == null){
				log.error("command equals null");
				
				File installerFile = new File(working_dir+InstallationDocumentHandler.installFileName);
				
				if (!installerFile.exists()){
					
					File installerdir = new File(working_dir);
					
					log.error("bb "+installerFile);
					log.error("bb "+working_dir+InstallationDocumentHandler.installFileName);
					
					boolean b = installerdir.canWrite();
					
					if (!b) {
						//File could not be created so throw an error
						ctx.put("error", "Could not Create File, Permission set? ");
						ctx.put("path", working_dir);
						return getVelocityEngine().getTemplate("install_error_"+lang+".vm");
					} else {
						InstallationDocumentHandler.getInstance().createDocument(working_dir+InstallationDocumentHandler.installFileName,0);
						//File has been created so follow first step of Installation					
						return getVelocityEngine().getTemplate("install_welcome_"+lang+".vm");
					}
					
				} else {
					int i = InstallationDocumentHandler.getInstance().getCurrentStepNumber(working_dir);
					if (i == 0){
						return getVelocityEngine().getTemplate("install_welcome_"+lang+".vm");
					}
//					ctx.put("error", "Already exist File, Permission set? ");
//					ctx.put("path", working_dir);
//				
//					return getVelocityEngine().getTemplate("install_error_"+lang+".vm");
				}
				
			} else if (command.equals("step1")) {
				
				int i = InstallationDocumentHandler.getInstance().getCurrentStepNumber(working_dir);
				if (i == 0){
					
					log.error("do init installation");
					
					//update to next step
					InstallationDocumentHandler.getInstance().createDocument(working_dir+InstallationDocumentHandler.installFileName,1);
					
					//return getVelocityEngine().getTemplate("install_complete_"+lang+".vm");
					return getVelocityEngine().getTemplate("install_step1_"+lang+".vm");
				} else {
					ctx.put("error", "This Step of the installation has already been done. continue with step 2 <A HREF='?command=step2'>continue with step 2</A>");
					return getVelocityEngine().getTemplate("install_exception_"+lang+".vm");
				}
				
			} else if (command.equals("step")){
				
				int i = InstallationDocumentHandler.getInstance().getCurrentStepNumber(working_dir);
				if (i == 0){
					
				}
				
			}
			
			
		} catch (IOException err) {
			log.error("Install: ",err);			
		} catch (Exception err2) {
			log.error("Install: ",err2);		
		}
		
		return null;
	}

}
