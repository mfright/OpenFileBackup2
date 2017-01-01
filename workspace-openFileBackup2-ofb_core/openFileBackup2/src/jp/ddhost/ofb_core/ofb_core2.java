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




	/// ���C������.
	private void backup(){
		// contents.xml��ǂݍ��ށB
		ArrayList<content> myContents = xmlLoader2.loadXml();


		for(int i=0;i<myContents.size();i++){
			printf("------------------------------------------------");
			printf("Backup"+ (i+1) +" start.");

			content aContent = myContents.get(i);

			// ����content��disabled�Ȃ珈���𒆎~
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
				//�o�b�N�A�b�v���t�@�C����{�����A�K�v�Ȃ�o�b�N�A�b�v��փR�s�[����B
				new searchSourceFile(aContent);

				//�o�b�N�A�b�v��t�@�C����{�����A�o�b�N�A�b�v���t�@�C�����疳���Ȃ����t�@�C�����폜(�܂��͑ޔ�)����B
				new searchDestFile(aContent);
			}

			printf("Backup"+ (i+1) +" finished.");
		}
	}





	/// �W���o�͂���
	private void printf(String message){
		System.out.println(message);
	}
}