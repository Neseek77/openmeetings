package org.xmlcrm.app.documents;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.commons.transaction.util.FileHelper;

public class GeneratePDF {
	
	private static GeneratePDF instance;

	private GeneratePDF() {}

	public static synchronized GeneratePDF getInstance() {
		if (instance == null) {
			instance = new GeneratePDF();
		}
		return instance;
	}
	
	public HashMap<String,HashMap> convertPDF(String current_dir, String fileNameExt,
			String roomName, String fileNameShort, boolean fullProcessing,
			String completeName, String newFileSystemExtName)
			throws Exception {
		
		HashMap<String,HashMap> returnError = new HashMap<String,HashMap>();
		
		HashMap<String,Object> processPDF = new HashMap<String,Object>();
		processPDF.put("process","processPDF");
		
		String working_imgdir = current_dir + "upload" + File.separatorChar + roomName
				+ File.separatorChar;
		String working_pptdir = current_dir + "uploadtemp" + File.separatorChar
				+ roomName + File.separatorChar;

		String fileFullPath = working_pptdir + fileNameExt;
		String newFolderName = fileNameExt.substring(0,fileNameExt.length() - 4);
		String destinationFolder = working_imgdir + newFolderName;

		File f = new File(destinationFolder + File.separatorChar);
		if (f.exists()) {
			int recursiveNumber = 0;
			String tempd = destinationFolder + "_" + recursiveNumber;
			while (f.exists()) {
				recursiveNumber++;
				tempd = destinationFolder + "_" + recursiveNumber;
				f = new File(tempd);

			}
			destinationFolder = tempd;
		}

		boolean b = f.mkdir();
		if (!b) {
			processPDF.put("error", "convertPDF + ERROR: Folder could not create " + f.getAbsolutePath());
			processPDF.put("exitValue",-1);
		} else {
			processPDF.put("exitValue",0);
		}
		returnError.put("processPDF", processPDF);
		
		String outputfolder = destinationFolder + File.separatorChar;
		destinationFolder = destinationFolder + File.separatorChar;

		if (fullProcessing) {
			HashMap<String,Object> processOpenOffice = this.doConvertExec(current_dir, fileFullPath, destinationFolder,fileNameShort);
			returnError.put("processOpenOffice", processOpenOffice);
			HashMap<String,Object> processThumb = GenerateThumbs.getInstance().generateBatchThumb(current_dir, destinationFolder + fileNameShort + ".pdf", destinationFolder, 80);
			returnError.put("processThumb", processThumb);
		} else {
			HashMap<String,Object> processThumb = GenerateThumbs.getInstance().generateBatchThumb(current_dir, fileFullPath, destinationFolder, 80);
			returnError.put("processThumb", processThumb);
		}
				
		//now it should be completed so copy that file to the expected location
		File fileToBeMoved = new File(completeName + newFileSystemExtName);
		File fileWhereToMove = new File(outputfolder+ fileNameShort + newFileSystemExtName);
		fileWhereToMove.createNewFile();
		FileHelper.moveRec(fileToBeMoved, fileWhereToMove);

		return returnError;
	}	
	
	/**
	 * Generate PDF and thumbs (and swf)
	 * @param current_dir
	 * @param fileFullPath
	 * @param destinationFolder
	 * @param outputfile
	 * @return
	 */
	public HashMap<String,Object> doConvertExec(String current_dir , String fileFullPath, 
			String destinationFolder, String outputfile) {
		HashMap<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("process", "doConvertExec");				
		try {
			
			String runtimeFile = "jodconverter.bat";
			String command = "cmd.exe /c start "+current_dir + "jod" + File.separatorChar
				+ runtimeFile + " java " + fileFullPath + " "
				+ destinationFolder + outputfile + ".pdf " + current_dir
				+ "jod" + File.separatorChar;
			
			if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
				runtimeFile = "jodconverter.sh";
				command = current_dir + "jod" + File.separatorChar
					+ runtimeFile + " java " + fileFullPath + " "
					+ destinationFolder + outputfile + ".pdf " + current_dir
					+ "jod" + File.separatorChar;				
			}
			Runtime rt = Runtime.getRuntime();
			returnMap.put("command",command);
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null)
				error += line;
			returnMap.put("error", error);
			int exitVal = proc.waitFor();
			returnMap.put("exitValue", exitVal);
			return returnMap;
		} catch (Throwable t) {
			t.printStackTrace();
			returnMap.put("error", t.getMessage());
			returnMap.put("exitValue", -1);
			return returnMap;
		}
	}	
	
}
