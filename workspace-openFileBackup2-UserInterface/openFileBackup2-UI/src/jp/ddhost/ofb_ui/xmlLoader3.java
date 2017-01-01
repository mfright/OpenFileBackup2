package jp.ddhost.ofb_ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class xmlLoader3{

	/// Load contents.xml
	public static ArrayList<content> loadXml(){

		// �ǂݍ��񂾓��e�͂����֕ۊ�
		ArrayList<content> contents = new ArrayList<content>();

		try{

			// contents.xml���I�[�v��
			File file = new File("contents.xml");
			BufferedReader br = new BufferedReader(new FileReader(file));



			  // �S�s��ǂݍ���,�econtent��fullBuffer�ɕۊ�
			  content contentBuffer = new content();
			  String tmpBuffer = br.readLine();
			  while(tmpBuffer != null){

				  contentBuffer.fullBuffer += tmpBuffer;

				  if(tmpBuffer.indexOf("</content>") > -1){
					  contentBuffer.finalizeFullBuffer();
					  contents.add(contentBuffer);
					  contentBuffer = new content();
				  }

				  tmpBuffer = br.readLine();
			  }

			  // contents.xml���N���[�Y
			  br.close();

		}catch(FileNotFoundException e){
		  System.out.println(e);
		}catch(IOException e){
		  System.out.println(e);
		}

		return contents;
	}




	public static void displayContents(UserInterface ui,ArrayList<content> contents){
		try{
			// �ݒ�P�̕\��
			content myContent = contents.get(0);

			ui.txtSourceFolder1.setText(myContent.source);
			ui.txtDestFolder1.setText(myContent.destination);
			ui.chkSafeFileEnabled1.setSelected(myContent.deletedsafe);
			ui.txtDeletedFolder1.setText(myContent.deleted);
			for(int i=0;i<myContent.excludeFilePaths.size();i++){
				ui.model1.addElement(myContent.excludeFilePaths.get(i));
			}
			ui.chkEncrypt1.setSelected(myContent.encryption);
			ui.txtPassword1.setText(myContent.encryptPassword);
			ui.chkDisabled1.setSelected(myContent.disabled);
			ui.lblFtpid1.setText(myContent.ftpid);
			ui.lblFtppw1.setText(myContent.ftppw);

			// �ݒ�Q�̕\��
			myContent = contents.get(1);

			ui.txtSourceFolder2.setText(myContent.source);
			ui.txtDestFolder2.setText(myContent.destination);
			ui.chkSafeFileEnabled2.setSelected(myContent.deletedsafe);
			ui.txtDeletedFolder2.setText(myContent.deleted);
			for(int i=0;i<myContent.excludeFilePaths.size();i++){
				ui.model2.addElement(myContent.excludeFilePaths.get(i));
			}
			ui.chkEncrypt2.setSelected(myContent.encryption);
			ui.txtPassword2.setText(myContent.encryptPassword);
			ui.chkDisabled2.setSelected(myContent.disabled);
			ui.lblFtpid2.setText(myContent.ftpid);
			ui.lblFtppw2.setText(myContent.ftppw);

			// �ݒ�R�̕\��
			myContent = contents.get(2);

			ui.txtSourceFolder3.setText(myContent.source);
			ui.txtDestFolder3.setText(myContent.destination);
			ui.chkSafeFileEnabled3.setSelected(myContent.deletedsafe);
			ui.txtDeletedFolder3.setText(myContent.deleted);
			for(int i=0;i<myContent.excludeFilePaths.size();i++){
				ui.model3.addElement(myContent.excludeFilePaths.get(i));
			}
			ui.chkEncrypt3.setSelected(myContent.encryption);
			ui.txtPassword3.setText(myContent.encryptPassword);
			ui.chkDisabled3.setSelected(myContent.disabled);
			ui.lblFtpid3.setText(myContent.ftpid);
			ui.lblFtppw3.setText(myContent.ftppw);





		}catch(Exception ex){
			ex.printStackTrace();
		}
		return;
	}
}