package jp.ddhost.ofb_core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

//バックアップ元を捜査し、必要ならバックアップ先へコピーする処理をするクラス
class searchSourceFile{


	content aContent;	//このインスタンスが処理するcontent

	//コンストラクタ
	public searchSourceFile(content myContent){
		aContent = myContent;

		// バックアップ処理する
		searchSourceFiles(new File(aContent.source));
	}




	/// 1.バックアップ元フォルダを捜査する。
	private void searchSourceFiles(File sourceDir){

		// 全ファイル・フォルダ一覧
		File[] includedFiles = sourceDir.listFiles();

		//ファイル・フォルダが無いときreturn
		if( includedFiles == null ){
			return;
		}


		for( File file : includedFiles ) {
			if( !file.exists() ){
				continue;
			}else if( file.isDirectory() ){
				//フォルダのとき

				//必要ならフォルダ作成
				makeDestFolder(file);

				//フォルダ内について再帰処理
		        searchSourceFiles( file );

			}else if( file.isFile() ){
				//ファイルのとき

				//必要ならコピーする。
		        compare1( file );
		    }

		}
	}




	/// 1.コピー元ファイルとコピー先ファイルを比較し、必要に応じてコピーする。
	private void compare1(File sourceFile){
		try {
			//コピー元ファイル
			String sourceFilePath = sourceFile.getCanonicalPath();


			//コピー先ファイル
			String destFilePath = aContent.destination+File.separator+sourceFilePath.substring(aContent.source.length());
			if(aContent.encryption==true){
				destFilePath += "__.zip";
			}
			File destFile = new File(destFilePath);


			//削除済みファイルの退避先フォルダ
			String deletedFilePath="";
			File deletedFile=null;

			//削除済みファイルは退避が必要な場合、退避先ファイル名を決定しておく
			if(aContent.deletedsafe == true){
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date date = new Date();
		        String dateTimeOffset = "_"+sdf.format( date ).toString();

				//deletedFilePath = aContent.deleted+File.separator+sourceFilePath.substring(aContent.source.length());

		        // 退避先フォルダパス + ファイル名
		        deletedFilePath = aContent.deleted+File.separator + sourceFile.getName();

		        // ファイル名のラストに日時追記
		        deletedFilePath = deletedFilePath.substring(0,deletedFilePath.length()-4)+dateTimeOffset+deletedFilePath.substring(deletedFilePath.length()-4);

		        // 暗号化の場合、ファイル名の後ろはzip
				if(aContent.encryption==true){
					deletedFilePath += "__.zip";
				}
				deletedFile = new File(deletedFilePath);
			}


			//バックアップ対象から除外されているとき
			if(aContent.isExcluded(sourceFilePath)){
				//バックアップしない
				printf("Excluded :"+sourceFilePath);
				return;
			}


			// バックアップ先にすでにファイルがある場合
			if(destFile.exists()){
				printf("EXISTS   :"+destFile.getCanonicalPath());

				// 双方の最終更新日時を取得
				Long sourceLastModified = sourceFile.lastModified();
				Long destLastModified = destFile.lastModified();




				// ファイルの最終更新日時が一緒の場合
				//if(sourceLastModified.equals(destLastModified)){
				if(destLastModified - 120000 < sourceLastModified && sourceLastModified < destLastModified + 120000){
					printf("NO_CHANGE:"+sourceFilePath);
					//処理を抜ける。
					return;

				}else if(aContent.deletedsafe == true){
					// ファイルの更新日時は違うのでコピーしたいが、バックアップ先にすでにファイルがある場合、すでにあるバックアップを退避先へコピーする。

					printf("ESCAPE   :"+destFilePath + " > "+deletedFilePath);
					Files.copy(destFile.toPath(), deletedFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				}

			}


			//ファイルをコピーする。
			printf("COPY     :"+sourceFilePath + " > "+destFilePath);
			if(aContent.encryption== true){
				//zip暗号化が有効なとき
				//zip圧縮する。
				fileArchiever.archieve(sourceFilePath, destFilePath, aContent.encryptPassword);

			}else{
				//zip暗号化が無効なとき
				Files.copy(sourceFile.toPath(),destFile.toPath(),StandardCopyOption.REPLACE_EXISTING);

			}


			//最終更新日時を一致させる。
			//(次回バックアップ時に、同一ファイルだと分かるようにするため)
			Long lastModified = sourceFile.lastModified();
			destFile.setLastModified(lastModified);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	// コピー先にフォルダが無ければ作成するメソッド
	private void makeDestFolder(File sourceFolder){
		try {
			String sourceFolderPath = sourceFolder.getCanonicalPath();
			String destFolderPath = aContent.destination+File.separator+sourceFolderPath.substring(aContent.source.length());
			File destFolder = new File(destFolderPath);

			// コピー先のフォルダがなく、コピー対象外でもない場合
			if(!destFolder.exists() && !aContent.isExcluded(sourceFolderPath)){
				printf("mkdir    :"+destFolder.getCanonicalPath());
				destFolder.mkdirs();
			}


		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}




	/// 標準出力するメソッド
		private void printf(String message){
			System.out.println(message);
		}
}