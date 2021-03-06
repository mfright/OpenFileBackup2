package jp.ddhost.ofb_core;


import java.util.ArrayList;

public class ofb_core2{

	//content myContent;
	//ArrayList<content> myContents;
	//content aContent;

	public static void main(String[] args){
		ofb_core2 myOFB = new ofb_core2();
		myOFB.backup();
	}




	/// メイン処理.
	private void backup(){
		// contents.xmlを読み込む。
		ArrayList<content> myContents = xmlLoader2.loadXml();


		for(int i=0;i<myContents.size();i++){
			printf("------------------------------------------------");
			printf("Backup"+ (i+1) +" start.");

			content aContent = myContents.get(i);

			// このcontentがdisabledなら処理を中止
			if(aContent.disabled){
				printf("This job is disabled.");
				continue;
			}

			printf("Copy target     :"+aContent.source);
			printf("Copy destination:"+aContent.destination);
			printf("Deleted files in:"+aContent.deleted+"\r\n");

			if(aContent.isFTP()){
				new searchSourceFileFTP(aContent);

			}else{
				//バックアップ元ファイルを捜査し、必要ならバックアップ先へコピーする。
				new searchSourceFile(aContent);

				//バックアップ先ファイルを捜査し、バックアップ元ファイルから無くなったファイルを削除(または退避)する。
				new searchDestFile(aContent);
			}

			printf("Backup"+ (i+1) +" finished.");
		}
	}





	/// 標準出力する
	private void printf(String message){
		System.out.println(message);
	}
}