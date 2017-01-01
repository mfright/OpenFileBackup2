package jp.ddhost.ofb_ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class xmlSaver{

	public static void saveXml(UserInterface ui){
		try{
			  File myFile = new File("contents.xml");

			  if (writableCheck(myFile)){
				  BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));




				  //------------XML�w�b�_------------
				  writer.write("<?xml version=1.0>");
				  writer.newLine();
				  writer.write("<contents>");
				  writer.newLine();




				  // ------------contents1------------
				  writer.write("   <content>");
				  writer.newLine();
				  writer.write("      <title>JOB1</title>");
				  writer.newLine();
				  writer.write("      <source>"+ui.txtSourceFolder1.getText()+"</source>");
				  writer.newLine();
				  writer.write("      <destination>"+ui.txtDestFolder1.getText()+"</destination>");
				  writer.newLine();
				  writer.write("      <ftpid>"+ui.lblFtpid1.getText()+"</ftpid>");
				  writer.newLine();
				  writer.write("      <ftppw>"+ui.lblFtppw1.getText()+"</ftppw>");
				  writer.newLine();

				  if(ui.chkSafeFileEnabled1.isSelected()){
					  writer.write("      <deletedsafe>true</deletedsafe>");
				  }else{
					  writer.write("      <deletedsafe>false</deletedsafe>");
				  }
				  writer.newLine();

				  writer.write("      <deleted>"+ui.txtDeletedFolder1.getText()+"</deleted>");
				  writer.newLine();

				  if(ui.chkEncrypt1.isSelected()){
					  writer.write("      <encrypt>true</encrypt>");
				  }else{
					  writer.write("      <encrypt>false</encrypt>");
				  }
				  writer.newLine();

				  writer.write("      <encryptpassword>"+ui.txtPassword1.getText()+"</encryptpassword>");
				  writer.newLine();

				  for(int i=0; i<ui.model1.size() ;i++){
					  String anExcludedPath = ui.model1.getElementAt(i);
					  writer.write("      <exclude>"+anExcludedPath+"</exclude>");
					  writer.newLine();
				  }

				  if(ui.chkDisabled1.isSelected()){
					  writer.write("      <disabled>true</disabled>");
				  }else{
					  writer.write("      <disabled>false</disabled>");
				  }
				  writer.newLine();

				  writer.write("   </content>");
				  writer.newLine();




				  // ------------contents2------------
				  writer.write("   <content>");
				  writer.newLine();
				  writer.write("      <title>JOB2</title>");
				  writer.newLine();
				  writer.write("      <source>"+ui.txtSourceFolder2.getText()+"</source>");
				  writer.newLine();
				  writer.write("      <destination>"+ui.txtDestFolder2.getText()+"</destination>");
				  writer.newLine();
				  writer.write("      <ftpid>"+ui.lblFtpid2.getText()+"</ftpid>");
				  writer.newLine();
				  writer.write("      <ftppw>"+ui.lblFtppw2.getText()+"</ftppw>");
				  writer.newLine();

				  if(ui.chkSafeFileEnabled2.isSelected()){
					  writer.write("      <deletedsafe>true</deletedsafe>");
				  }else{
					  writer.write("      <deletedsafe>false</deletedsafe>");
				  }
				  writer.newLine();

				  writer.write("      <deleted>"+ui.txtDeletedFolder2.getText()+"</deleted>");
				  writer.newLine();

				  if(ui.chkEncrypt2.isSelected()){
					  writer.write("      <encrypt>true</encrypt>");
				  }else{
					  writer.write("      <encrypt>false</encrypt>");
				  }
				  writer.newLine();

				  writer.write("      <encryptpassword>"+ui.txtPassword2.getText()+"</encryptpassword>");
				  writer.newLine();

				  for(int i=0; i<ui.model2.size() ;i++){
					  String anExcludedPath = ui.model2.getElementAt(i);
					  writer.write("      <exclude>"+anExcludedPath+"</exclude>");
					  writer.newLine();
				  }

				  if(ui.chkDisabled2.isSelected()){
					  writer.write("      <disabled>true</disabled>");
				  }else{
					  writer.write("      <disabled>false</disabled>");
				  }
				  writer.newLine();

				  writer.write("   </content>");
				  writer.newLine();




				// ------------contents3------------
				  writer.write("   <content>");
				  writer.newLine();
				  writer.write("      <title>JOB3</title>");
				  writer.newLine();
				  writer.write("      <source>"+ui.txtSourceFolder3.getText()+"</source>");
				  writer.newLine();
				  writer.write("      <destination>"+ui.txtDestFolder3.getText()+"</destination>");
				  writer.newLine();
				  writer.write("      <ftpid>"+ui.lblFtpid3.getText()+"</ftpid>");
				  writer.newLine();
				  writer.write("      <ftppw>"+ui.lblFtppw3.getText()+"</ftppw>");
				  writer.newLine();

				  if(ui.chkSafeFileEnabled3.isSelected()){
					  writer.write("      <deletedsafe>true</deletedsafe>");
				  }else{
					  writer.write("      <deletedsafe>false</deletedsafe>");
				  }
				  writer.newLine();

				  writer.write("      <deleted>"+ui.txtDeletedFolder3.getText()+"</deleted>");
				  writer.newLine();

				  if(ui.chkEncrypt3.isSelected()){
					  writer.write("      <encrypt>true</encrypt>");
				  }else{
					  writer.write("      <encrypt>false</encrypt>");
				  }
				  writer.newLine();

				  writer.write("      <encryptpassword>"+ui.txtPassword3.getText()+"</encryptpassword>");
				  writer.newLine();

				  for(int i=0; i<ui.model3.size() ;i++){
					  String anExcludedPath = ui.model3.getElementAt(i);
					  writer.write("      <exclude>"+anExcludedPath+"</exclude>");
					  writer.newLine();
				  }

				  if(ui.chkDisabled3.isSelected()){
					  writer.write("      <disabled>true</disabled>");
				  }else{
					  writer.write("      <disabled>false</disabled>");
				  }
				  writer.newLine();

				  writer.write("   </content>");
				  writer.newLine();




				  //XML�t�b�^
				  writer.write("</contents>");
				  writer.newLine();



				  writer.close();
			  }else{
			    System.out.println("ERROR: contents.xml is not writable!");
			  }
		    }catch(IOException e){
		      System.out.println(e);
		    }
	}




	//�t�@�C�����ߋ��N�\�ł��邩����
	private static boolean writableCheck(File file){
	    if (file.exists()){
	      if (file.isFile() && file.canWrite()){
	        return true;
	      }
	    }

	    return false;
	  }
}