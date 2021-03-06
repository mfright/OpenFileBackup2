package jp.ddhost.ofb_ui;

import java.util.ArrayList;

public class content{
	public String source = "";	//Backup source folder.

	public String destination = "";	//Backup destination folder.

	public Boolean deletedsafe = false;	// Save deleted files or not.
	public String deleted = "";	//Folder deleted files.

	public Boolean encryption = false;	//Backup files must be zip-encrypted or not.
	public String encryptPassword = "";	//zip password.

	public Boolean disabled = false; //This backup-job will not be started if this is true.






	public ArrayList<String> excludeFilePaths = new ArrayList<String>();	//These files will not be copied.

	// Check the file is excluded to backup or not.
	public Boolean isExcluded(String filePath){
		if(excludeFilePaths.size() <1){
			return false;
		}

		for(int i=0;i<excludeFilePaths.size();i++){
			String anExcludePath = excludeFilePaths.get(i);
			if(filePath.indexOf( anExcludePath )== 0 ){
				return true;
			}
		}

		return false;
	}




	public String ftpid = "";	// FTP user-id
	public String ftppw = "";	// FTP password.

	// バックアップ先がFTPかどうか判定
	public Boolean isFTP(){
		if(destination.indexOf("ftp://")==0){
			return true;
		}else{
			return false;
		}
	}

	// FTP接続先URLを返す
	public String getFTPURL(){
		if(isFTP()){
			int slashPoint = destination.indexOf("/",6);
			return destination.substring(6,slashPoint);
		}
		return null;
	}

	public String getFtpRemoteDir(){
		if(isFTP()){
			int slashPoint = destination.indexOf("/",6);
			return destination.substring(slashPoint);
		}
		return null;
	}







	public String fullBuffer="";	// Full XML code in <content> element.

	public void finalizeFullBuffer(){

		// fullBufferから各プロパティへ情報を反映

		if(fullBuffer.indexOf("<source>")>-1){
			source = fullBuffer.substring(fullBuffer.indexOf("<source>")+8, fullBuffer.indexOf("</source>"));
		}

		if(fullBuffer.indexOf("<destination>")>-1){
			destination = fullBuffer.substring(fullBuffer.indexOf("<destination>")+13, fullBuffer.indexOf("</destination>"));
		}

		if(fullBuffer.indexOf("<deleted>")>-1){
			deleted = fullBuffer.substring(fullBuffer.indexOf("<deleted>")+9, fullBuffer.indexOf("</deleted>"));
		}

		if(fullBuffer.indexOf("<deletedsafe>true</deletedsafe>")>-1){
			deletedsafe = true;
		}

		if(fullBuffer.indexOf("<encrypt>true</encrypt>")>-1){
			encryption = true;
		}

		if(fullBuffer.indexOf("<encryptpassword>")>-1){
			encryptPassword = fullBuffer.substring(fullBuffer.indexOf("<encryptpassword>")+17, fullBuffer.indexOf("</encryptpassword>"));
		}

		if(fullBuffer.indexOf("<disabled>true</disabled>")>-1){
			disabled = true;
		}


		// <exclude>要素をすべて読み込む。
		int cursor=0;
		int foundPoint;
		int stringStart=0,stringEnd=0;
		String excluded = "";
		while(cursor<fullBuffer.length()){

			foundPoint = fullBuffer.indexOf("<exclude>",cursor);

			if(foundPoint > -1){
				stringStart = foundPoint+9;
				stringEnd = fullBuffer.indexOf("</exclude>",stringStart);

				excluded = fullBuffer.substring(stringStart,stringEnd);
				excludeFilePaths.add(excluded);

				cursor = stringEnd + 9;
			}else{
				break;
			}

		}


		if(fullBuffer.indexOf("<ftpid>")>-1){
			ftpid = fullBuffer.substring(fullBuffer.indexOf("<ftpid>")+7,fullBuffer.indexOf("</ftpid>"));
		}

		if(fullBuffer.indexOf("<ftppw>")>-1){
			ftppw = fullBuffer.substring(fullBuffer.indexOf("<ftppw>")+7,fullBuffer.indexOf("</ftppw>"));
		}



	}



}