package jp.ddhost.ofb_core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

//�o�b�N�A�b�v����{�����A�K�v�Ȃ�o�b�N�A�b�v��փR�s�[���鏈��������N���X
class searchSourceFile{


	content aContent;	//���̃C���X�^���X����������content

	//�R���X�g���N�^
	public searchSourceFile(content myContent){
		aContent = myContent;

		// �o�b�N�A�b�v��������
		searchSourceFiles(new File(aContent.source));
	}




	/// 1.�o�b�N�A�b�v���t�H���_��{������B
	private void searchSourceFiles(File sourceDir){

		// �S�t�@�C���E�t�H���_�ꗗ
		File[] includedFiles = sourceDir.listFiles();

		//�t�@�C���E�t�H���_�������Ƃ�return
		if( includedFiles == null ){
			return;
		}


		for( File file : includedFiles ) {
			if( !file.exists() ){
				continue;
			}else if( file.isDirectory() ){
				//�t�H���_�̂Ƃ�

				//�K�v�Ȃ�t�H���_�쐬
				makeDestFolder(file);

				//�t�H���_���ɂ��čċA����
		        searchSourceFiles( file );

			}else if( file.isFile() ){
				//�t�@�C���̂Ƃ�

				//�K�v�Ȃ�R�s�[����B
		        compare1( file );
		    }

		}
	}




	/// 1.�R�s�[���t�@�C���ƃR�s�[��t�@�C�����r���A�K�v�ɉ����ăR�s�[����B
	private void compare1(File sourceFile){
		try {
			//�R�s�[���t�@�C��
			String sourceFilePath = sourceFile.getCanonicalPath();


			//�R�s�[��t�@�C��
			String destFilePath = aContent.destination+File.separator+sourceFilePath.substring(aContent.source.length());
			if(aContent.encryption==true){
				destFilePath += "__.zip";
			}
			File destFile = new File(destFilePath);


			//�폜�ς݃t�@�C���̑ޔ��t�H���_
			String deletedFilePath="";
			File deletedFile=null;

			//�폜�ς݃t�@�C���͑ޔ����K�v�ȏꍇ�A�ޔ��t�@�C���������肵�Ă���
			if(aContent.deletedsafe == true){
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		        Date date = new Date();
		        String dateTimeOffset = "_"+sdf.format( date ).toString();

				//deletedFilePath = aContent.deleted+File.separator+sourceFilePath.substring(aContent.source.length());

		        // �ޔ��t�H���_�p�X + �t�@�C����
		        deletedFilePath = aContent.deleted+File.separator + sourceFile.getName();

		        // �t�@�C�����̃��X�g�ɓ����ǋL
		        deletedFilePath = deletedFilePath.substring(0,deletedFilePath.length()-4)+dateTimeOffset+deletedFilePath.substring(deletedFilePath.length()-4);

		        // �Í����̏ꍇ�A�t�@�C�����̌���zip
				if(aContent.encryption==true){
					deletedFilePath += "__.zip";
				}
				deletedFile = new File(deletedFilePath);
			}


			//�o�b�N�A�b�v�Ώۂ��珜�O����Ă���Ƃ�
			if(aContent.isExcluded(sourceFilePath)){
				//�o�b�N�A�b�v���Ȃ�
				printf("Excluded :"+sourceFilePath);
				return;
			}


			// �o�b�N�A�b�v��ɂ��łɃt�@�C��������ꍇ
			if(destFile.exists()){
				printf("EXISTS   :"+destFile.getCanonicalPath());

				// �o���̍ŏI�X�V�������擾
				Long sourceLastModified = sourceFile.lastModified();
				Long destLastModified = destFile.lastModified();


				// �t�@�C���̍ŏI�X�V�������ꏏ�̏ꍇ
				if(sourceLastModified.equals(destLastModified)){
					printf("No_change:"+sourceFilePath);
					//�����𔲂���B
					return;

				// �t�@�C���̍X�V�����͈Ⴄ�̂ŃR�s�[���������A�o�b�N�A�b�v��ɂ��łɃt�@�C��������ꍇ�A���łɂ���o�b�N�A�b�v��ޔ��փR�s�[����B
				}else if(aContent.deletedsafe == true){
					printf("ESCAPE   :"+destFilePath + " > "+deletedFilePath);
					Files.copy(destFile.toPath(), deletedFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				}

			}


			//�t�@�C�����R�s�[����B
			printf("COPY     :"+sourceFilePath + " > "+destFilePath);
			if(aContent.encryption== true){
				//zip�Í������L���ȂƂ�
				//zip���k����B
				fileArchiever.archieve(sourceFilePath, destFilePath, aContent.encryptPassword);

			}else{
				//zip�Í����������ȂƂ�
				Files.copy(sourceFile.toPath(),destFile.toPath(),StandardCopyOption.REPLACE_EXISTING);

			}


			//�ŏI�X�V��������v������B
			//(����o�b�N�A�b�v���ɁA����t�@�C�����ƕ�����悤�ɂ��邽��)
			Long lastModified = sourceFile.lastModified();
			destFile.setLastModified(lastModified);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	// �R�s�[��Ƀt�H���_��������΍쐬���郁�\�b�h
	private void makeDestFolder(File sourceFolder){
		try {
			String sourceFolderPath = sourceFolder.getCanonicalPath();
			String destFolderPath = aContent.destination+File.separator+sourceFolderPath.substring(aContent.source.length());
			File destFolder = new File(destFolderPath);

			// �R�s�[��̃t�H���_���Ȃ��A�R�s�[�ΏۊO�ł��Ȃ��ꍇ
			if(!destFolder.exists() && !aContent.isExcluded(sourceFolderPath)){
				printf("mkdir    :"+destFolder.getCanonicalPath());
				destFolder.mkdirs();
			}


		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}




	/// �W���o�͂��郁�\�b�h
		private void printf(String message){
			System.out.println(message);
		}
}