package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compress {
 
	static final int COMPRESSION_LEVEL = 8;
	static final int BUFFER_SIZE = 2024 * 2024;

	public static void getZip(String dir ,String fileName)throws IOException{
		
		String targetDir = dir;

		long beginTime = System.currentTimeMillis();
		int cnt;
		byte[] buffer = new byte[BUFFER_SIZE];
		FileInputStream  finput = null;
		FileOutputStream foutput;
		
		


		targetDir.replace('\\',File.separatorChar);
		targetDir.replace('/', File.separatorChar);
		String dirNm = targetDir.substring(targetDir.lastIndexOf(File.separatorChar)+1 , targetDir.length());



		File file = new File(targetDir);
		String filePath = file.getAbsolutePath();
		LogWrapper.biz.info("FILE path = : " + file.getAbsolutePath());

		if (file.isDirectory()) {
			LogWrapper.biz.info("Directory......");
		} else {
			file = new File(file.getParent());
		}


		File[] fileArray = file.listFiles();
		for (int i=0; i < fileArray.length; i++) {
			LogWrapper.biz.debug("fileArray[" + i + "] : " + fileArray[i].toString());
		}


		String zfileNm =fileName+ ".zip";
		int num = 1;
		while (new File(zfileNm).exists()) {
			zfileNm = fileName + "_" + num++ + ".zip";
		}
		LogWrapper.biz.info("Zip File Path and Name : " + zfileNm);



		// Zip 파일을 만든다.
		File zfile = new File(dir+zfileNm);
		// Zip 파일 객체를 출력 스트림에 넣는다.
		foutput = new FileOutputStream(zfile);

		// 집출력 스트림에 집파일을 넣는다.
		ZipOutputStream zoutput = new ZipOutputStream((OutputStream)foutput);

		ZipEntry zentry = null;

		try {
			//for (int i=0; i < fileArray.length; i++) {
			for(String tmpfile : getFiles(targetDir)) {
				LogWrapper.biz.debug("tmpfile : " + targetDir + tmpfile);
				if(tmpfile.equals(zfileNm)) continue;
				// 압축할 파일 배열 중 하나를 꺼내서 입력 스트림에 넣는다.
				finput = new FileInputStream(targetDir + tmpfile);

				// ze = new net.sf.jazzlib.ZipEntry ( inFile[i].getName());
//				zentry = new  ZipEntry(tmpfile.getName());
				zentry = new  ZipEntry(tmpfile);

				LogWrapper.biz.info("Target File Name for Compression : " 

						+ tmpfile

						+ ", File Size : " 

						+ finput.available());

				zoutput.putNextEntry(zentry);

				/*
				 ****************************************************************
				 * 압축 레벨을 정하는것인데 9는 가장 높은 압축률을 나타냅니다.
				 * 그 대신 속도는 젤 느립니다. 디폴트는 8입니다.
				 *****************************************************************/
				zoutput.setLevel(COMPRESSION_LEVEL);

				cnt = 0;
				int ct = 0;
				while ((cnt = finput.read(buffer)) != -1) {
					ct++;
					zoutput.write(buffer, 0, cnt);
					
					if(ct == 15){
						break;
					}
				}

				finput.close();
				zoutput.closeEntry();
			}
			zoutput.close();
			foutput.close();
		
		} catch (Exception e) {
			LogWrapper.biz.info("Compression Error : " + e.toString());
			/*
			 **********************************************
			 * 압축이 실패했을 경우 압축 파일을 삭제한다.
			 ***********************************************/
			LogWrapper.biz.info(zfile.toString() + " : 압축이 실패하여 파일을 삭제합니다...");
			if (!zfile.delete()) {
//				LogWrapper.biz.info(zfile.toString() + " : 파일 삭제가 실패하여 다시 삭제합니다...");
//				while(!zfile.delete()) {
//					LogWrapper.biz.info(zfile.toString() + " : 삭제가 실패하여 다시 삭제합니다....");
//				}
			}
			e.printStackTrace();
		//	throw new Exception(e);
		} finally {
			if (finput != null) {
				finput.close();
			}
			if (zoutput != null) {
				zoutput.close();
			}
			if (foutput != null) {
				foutput.close();
			}
			file.delete();
		}

		long msec = System.currentTimeMillis() - beginTime;

		LogWrapper.biz.info("Check :: >> " + msec/1000 + "." + (msec % 1000) + " sec. elapsed...");
	}
	
	public static String[] getFiles(String dir) throws Exception {
		  File parent = new File(dir);
		  if(!parent.isDirectory()) return null;
		  File[] children = parent.listFiles();
		  List<String> names = new ArrayList<String>();
		  for(File child : children){
		   String name = child.getName();
		   if(child.isFile()) names.add(name);
		   else getFileNames(name, names, child);
		  }//for
		  return names.toArray(new String[names.size()]);
		 }//getFiles
	
	private static void getFileNames(String name, List<String> names, File parent) throws Exception {
		  if(!parent.isDirectory()) return;
		  File[] children = parent.listFiles();
		  for(File child : children){
		   String tmp = name+"/"+child.getName();
		   if(child.isFile()) names.add(tmp);
		   else getFileNames(tmp, names, child);
		  }//for
		 }//getFileNames
	
	public static boolean zip(String path, String[] files, File zipfile) throws Exception {
		  File pathfile = new File(path);
		  if(!pathfile.isDirectory()) return false;
		  if(zipfile.isDirectory()) return false;
		  else{
		   File parent = zipfile.getParentFile();
		   if(!parent.exists()) parent.mkdir();
		   if(!zipfile.exists()) zipfile.createNewFile();
		  }//if-else
		  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipfile));
		  ZipOutputStream zos = new ZipOutputStream(bos);
		  for(String tmp : files){
		   File tmpfile = new File(path+tmp);
		   if(!tmpfile.exists()){
		    zos.close();
		    bos.close();
		    return false;
		   }//if
		   ZipEntry zentry = new ZipEntry(tmp);
		   zos.putNextEntry(zentry);
		   BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tmpfile));
		   byte[] buffer = new byte[1024];
		   int len = 0;
		   while((len=bis.read(buffer, 0, 1024)) > -1){
		    zos.write(buffer, 0, len);
		    zos.flush();
		   }//while
		   zos.closeEntry();
		  }//for
		  zos.close();
		  bos.close();
		  return true;
		 }//zip
}

 
