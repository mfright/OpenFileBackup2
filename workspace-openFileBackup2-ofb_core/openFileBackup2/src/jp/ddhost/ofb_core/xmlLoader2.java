package jp.ddhost.ofb_core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class xmlLoader2{

	/// Load contents.xml
	static ArrayList<content> loadXml(){

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
}