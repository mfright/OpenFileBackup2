package jp.ddhost.ofb_core;

import java.io.IOException;


import net.lingala.zip4j.exception.ZipException;

//import org.apache.tools.zip.ZipEntry;
//import org.apache.tools.zip.ZipOutputStream;

public class fileArchiever{



	public static Boolean archieve(String sourceFilePath,String destFilePath,String password){
		try {
			new zipTool().zip(sourceFilePath, destFilePath,password);
		} catch (ZipException | IOException | CloneNotSupportedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}


}