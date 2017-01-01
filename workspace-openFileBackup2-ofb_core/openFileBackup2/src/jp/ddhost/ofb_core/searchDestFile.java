package jp.ddhost.ofb_core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/// 2.�R�s�[��t�H���_��{�����A�R�s�[���ɖ����t�@�C�����폜(�܂��͑ޔ�)����N���X�B
class searchDestFile{

	// ���̃C���X�^���X����������content
	content aContent;

	searchDestFile(content myContent){
		aContent = myContent;

		searchDestFiles(new File(aContent.destination));
	}

	/// 2.�R�s�[��t�H���_��{�����A�R�s�[���ɖ����t�@�C�����폜(�܂��͑ޔ�)����B
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







	//�o�b�N�A�b�v�ς݃t�@�C�����o�b�N�A�b�v���t�@�C���Ɣ�r���A�o�b�N�A�b�v���ɂȂ���΍폜(�ޔ�)����B
	private void compare2(File destFile){

		try {
			//�R�s�[��t�@�C��
			String destFilePath = destFile.getCanonicalPath();


			//�R�s�[���t�@�C��
			String sourceFilePath = aContent.source + File.separator+destFilePath.substring(aContent.destination.length());
			if(aContent.encryption==true){
				sourceFilePath = sourceFilePath.substring(0,sourceFilePath.length()-6);
			}
			File sourceFile = new File(sourceFilePath);


			//�폜�ς݃t�@�C���̑ޔ��t�H���_
			String deletedFilePath="";
			File deletedFile=null;

			//�폜�ς݃t�@�C���͑ޔ����K�v�ȏꍇ�A�ޔ��t�@�C���������肵�Ă���
			if(aContent.deletedsafe == true){
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date date = new Date();
		        String dateTimeOffset = "_"+sdf.format( date ).toString();

				//deletedFilePath = aContent.deleted+File.separator+sourceFilePath.substring(aContent.source.length());

		        //�ޔ��ς݃t�@�C���̃p�X
		        deletedFilePath = aContent.deleted + File.separator+destFile.getName();
				deletedFilePath = deletedFilePath.substring(0,deletedFilePath.length()-4)+dateTimeOffset+deletedFilePath.substring(deletedFilePath.length()-4);

				// �Í����L���̂Ƃ��́A�g���qzip
				//if(aContent.encryption==true){
				//	deletedFilePath += "__.zip";
				//}

				deletedFile = new File(deletedFilePath);
			}




			//�����R�s�[���t�@�C���������Ȃ��Ă����ꍇ
			if(!sourceFile.exists()){
				if(aContent.deletedsafe){
					//�폜�ς݃t�@�C���Ƃ��đޔ�����B
					System.out.println("Escape   :"+destFilePath + " > "+ deletedFilePath);
					destFile.renameTo(deletedFile);

				}else{
					//�t�@�C���������B
					destFile.delete();
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}