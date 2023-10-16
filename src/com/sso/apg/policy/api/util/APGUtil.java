package com.sso.apg.policy.api.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.sso.apg.policy.bean.ImportObjectBO;

public class APGUtil {

	private static final Logger logger = Logger.getLogger(APGUtil.class);
	public static boolean writeFile(BufferedReader reader, String path) throws IOException{
		return writeFile(reader, path, false);

	}

	public static boolean writeFile(BufferedReader reader, String path, boolean isOverWrite) throws IOException{

		String line = new String();
		logger.debug("file " + path + " deleted ? " + deleteFile(path));
		logger.debug("Write to file : " + path);
		File localFile = new File(path);
		localFile.createNewFile();
		PrintWriter localPWriter = new PrintWriter(localFile);
		BufferedWriter localWriter = new BufferedWriter(localPWriter);
		while((line = reader.readLine()) != null){
			localWriter.write(line);
			localWriter.newLine();
		}
		localPWriter.flush();
		localWriter.flush();
		localPWriter.close();
		localWriter.close();
		return true; 

	}

	public static boolean deleteFile(String path){
		File localFile = new File(path);
		return localFile.delete();
	}

	public static void readFile(String path) throws IOException{
		File localFile = new File(path);
		FileReader localFReader = new FileReader(localFile);
		BufferedReader localReader = new BufferedReader(localFReader);
		String line = new String();
		while((line = localReader.readLine()) != null){
			logger.debug(">>>>><<<<<<<<<<<"  + line);
		}
		localReader.close();
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

	public static List<String> unzip(String folderPath, String fileName) throws IOException{	
		byte[] buffer = new byte[1024];
		ZipInputStream localZis = new ZipInputStream(new FileInputStream(new File(folderPath + File.separator + fileName)));
		ZipEntry localZe = localZis.getNextEntry();
		//String importFile = "";
		List<String> unzippedFiles = new ArrayList<String>();
		while(localZe != null){
			String file = localZe.getName();
			File newFile = new File(folderPath + File.separator + file);
			logger.debug("file entry : "+ newFile.getAbsoluteFile()); 
/*			if(file.endsWith(".smdif"))
				importFile = newFile.getAbsoluteFile().getAbsolutePath();*/
			unzippedFiles.add(newFile.getAbsoluteFile().getAbsolutePath());
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
		return unzippedFiles;

	}
	
	public static boolean updateNameOidMap(String nameOidMapFile, List<ImportObjectBO> objectList) throws IOException{
		BufferedReader localReader = new BufferedReader(new FileReader(nameOidMapFile));
		String line = "";
		String fileContent = "";
		while((line = localReader.readLine()) != null){
			fileContent += line + "\r\n";
		}
		localReader.close();
			for(int i = 0; i < objectList.size() ; i++){
				fileContent = fileContent.replaceAll(objectList.get(i).getOldObjName(), objectList.get(i).getUpdatedObjName());
			}
		BufferedReader localReader1 = new BufferedReader(new StringReader(fileContent));
		return writeFile(localReader1,nameOidMapFile);
	}
}
