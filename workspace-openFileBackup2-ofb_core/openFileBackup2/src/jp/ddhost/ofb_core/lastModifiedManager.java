package jp.ddhost.ofb_core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/// �t�@�C���̍ŏI�X�V�����Ǘ�����N���X
class lastModifiedManager{

	//�t�@�C���Q�̍ŏI�X�V�����
	ArrayList<modifyInfo> modifiedInfoList = new ArrayList<modifyInfo>();


	// �R���X�g���N�^
	lastModifiedManager(){
		loadFile();
	}




	// �t�@�C�������[�h����B
	private void loadFile(){
		try{

			// �I�[�v��
			File file = new File("lastmodified.txt");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));


			//�s���ƂɍŏI�X�V������ǂݍ���
			String tmpBuffer = br.readLine();
			String filePath = "";
			String lastModified="";
			int separatorPoint = 0;
			modifyInfo myModifyInfo;
			while(tmpBuffer != null){
				separatorPoint = tmpBuffer.indexOf(' ');
				if(separatorPoint == -1){
					return;
				}

				filePath = tmpBuffer.substring(0,separatorPoint);
				lastModified = tmpBuffer.substring(separatorPoint+1);

				myModifyInfo = new modifyInfo();
				myModifyInfo.filePath = filePath;
				myModifyInfo.lastModified = Long.valueOf(lastModified);
				modifiedInfoList.add(myModifyInfo);



				tmpBuffer = br.readLine();
			}


			// �N���[�Y
			  br.close();

		}catch(FileNotFoundException e){
		  System.out.println(e);
		}catch(IOException e){
		  System.out.println(e);
		}
	}




	// �ŏI�X�V������o�^����B
	public void saveLastModified(File sourceFile){

		try {
			modifyInfo myModifyInfo = new modifyInfo();
			myModifyInfo.lastModified = sourceFile.lastModified();


			myModifyInfo.filePath = sourceFile.getCanonicalPath();

			modifiedInfoList.add(myModifyInfo);








		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

	}



	// �o�^����Ă����t�@�C���̍ŏI�X�V����Ԃ��B
	public long getLastModified(File sourceFile){
		try {
			String myFilePath = sourceFile.getCanonicalPath();

			modifyInfo anInfo;
			String anFilePath;
			for(int i=0;i<modifiedInfoList.size();i++){
				anInfo = modifiedInfoList.get(i);
				anFilePath = anInfo.filePath;

				if(myFilePath.equals(anFilePath)){
					return anInfo.lastModified;
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}




	// �t�@�C���ɕۑ�����B
	public void close(){
		try{




		    File file = new File("lastmodified.txt");

		    BufferedWriter bw = new BufferedWriter(new FileWriter(file));


		    String line = "";
		    modifyInfo myMI;
		    for(int i=0;i<modifiedInfoList.size();i++){
		    	myMI = modifiedInfoList.get(i);
		    	line = myMI.filePath+" " + myMI.lastModified;

		    	bw.write(line);
		    	bw.newLine();
		    }

		    bw.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}


}