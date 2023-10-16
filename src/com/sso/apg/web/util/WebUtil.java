package com.sso.apg.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class WebUtil {

	private static final Logger logger = Logger.getLogger(WebUtil.class);
	public static String[] split(String inputString, String separator){
		if(inputString != null)
			return inputString.split(separator);
		else
			return null;
	}

	public static boolean makeZip(String [] file,String zipFile) throws IOException {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		for(int i = 0; i<file.length ; i++){
			ZipEntry ze= new ZipEntry(file[i].substring(file[i].lastIndexOf("\\")+1, file[i].length()));
			zos.putNextEntry(ze);
			FileInputStream in = new FileInputStream(file[i]);
			int len;
			while ((len = in.read(buffer)) > 0) {
				//logger.debug(new String(buffer));
				zos.write(buffer, 0, len);
			}
			zos.flush();
			in.close();
			zos.closeEntry();
		}

		zos.close();
		return true;
	}

	public static boolean deleteFile(String path){
		File localFile = new File(path);
		return localFile.delete();
	}

	public static boolean uploadFile(String uploadPath, FormFile importFile) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		//create the upload folder if not exists
		File folder = new File(uploadPath);
		if(!folder.exists()){
			folder.mkdir();
		}

		String fileName = importFile.getFileName();

		if(!("").equals(fileName)){
			logger.debug("Server path : " + uploadPath);
			File newFile = new File(uploadPath, fileName);
			if(newFile.exists())
				newFile.delete();
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(importFile.getFileData());
			fos.flush();
			fos.close();
		}
		return true;
	}

	public static boolean unzip(String uploadPath, String fileName) throws IOException{	
		byte[] buffer = new byte[1024];
		ZipInputStream localZis = new ZipInputStream(new FileInputStream(new File(uploadPath + File.separator + fileName)));
		ZipEntry localZe = localZis.getNextEntry();

		while(localZe != null){
			String file = localZe.getName();
			File newFile = new File(uploadPath + File.separator + file);
			logger.debug("file entry : "+ newFile.getAbsoluteFile()); 
			new File(newFile.getParent()).mkdirs();
			FileOutputStream fos = new FileOutputStream(newFile);             
			int len;
			while ((len = localZis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();   
			localZe = localZis.getNextEntry();
		}		
		localZis.close();
		return true;

	}

	public static boolean makeDir(String folder) {
		// TODO Auto-generated method stub
		File localFolder = new File(folder);
		if(!localFolder.exists()){
			return localFolder.mkdirs();
		}else
			return true;
	}

}
