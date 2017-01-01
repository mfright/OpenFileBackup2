package jp.ddhost.ofb_core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/// 2.コピー先フォルダを捜査し、コピー元に無いファイルを削除(または退避)するクラス。
class searchDestFile{

	// このインスタンスが処理するcontent
	content aContent;

	searchDestFile(content myContent){
		aContent = myContent;

		searchDestFiles(new File(aContent.destination));
	}

	/// 2.コピー先フォルダを捜査し、コピー元に無いファイルを削除(または退避)する。
	private void searchDestFiles(File destDir){
		File[] includedFiles = destDir.listFiles();
		if( includedFiles == null ){
			return;
		}

		for( File file : includedFiles ) {
			if( !file.exists() )
				continue;
			else if( file.isDirectory() ){
				searchDestFiles( file );

			}else if( file.isFile() ){
		        compare2( file );
		    }

		}
	}







	//バックアップ済みファイルをバックアップ元ファイルと比較し、バックアップ元になければ削除(退避)する。
	private void compare2(File destFile){

		try {
			//コピー先ファイル
			String destFilePath = destFile.getCanonicalPath();


			//コピー元ファイル
			String sourceFilePath = aContent.source + File.separator+destFilePath.substring(aContent.destination.length());
			if(aContent.encryption==true){
				sourceFilePath = sourceFilePath.substring(0,sourceFilePath.length()-6);
			}
			File sourceFile = new File(sourceFilePath);


			//削除済みファイルの退避先フォルダ
			String deletedFilePath="";
			File deletedFile=null;

			//削除済みファイルは退避が必要な場合、退避先ファイル名を決定しておく
			if(aContent.deletedsafe == true){
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date date = new Date();
		        String dateTimeOffset = "_"+sdf.format( date ).toString();

				//deletedFilePath = aContent.deleted+File.separator+sourceFilePath.substring(aContent.source.length());

		        //退避済みファイルのパス
		        deletedFilePath = aContent.deleted + File.separator+destFile.getName();
				deletedFilePath = deletedFilePath.substring(0,deletedFilePath.length()-4)+dateTimeOffset+deletedFilePath.substring(deletedFilePath.length()-4);

				// 暗号化有効のときは、拡張子zip
				//if(aContent.encryption==true){
				//	deletedFilePath += "__.zip";
				//}

				deletedFile = new File(deletedFilePath);
			}




			//もしコピー元ファイルが無くなっていた場合
			if(!sourceFile.exists()){
				if(aContent.deletedsafe){
					//削除済みファイルとして退避する。
					System.out.println("Escape   :"+destFilePath + " > "+ deletedFilePath);
					destFile.renameTo(deletedFile);

				}else{
					//ファイルを消す。
					destFile.delete();
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}