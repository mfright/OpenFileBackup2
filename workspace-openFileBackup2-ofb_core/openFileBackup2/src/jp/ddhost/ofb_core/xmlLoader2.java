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

		// 読み込んだ内容はここへ保管
		ArrayList<content> contents = new ArrayList<content>();

		try{

			// contents.xmlをオープン
			File file = new File("contents.xml");
			BufferedReader br = new BufferedReader(new FileReader(file));



			  // 全行を読み込み,各contentのfullBufferに保管
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


			// contents.xmlをクローズ
			  br.close();

		}catch(FileNotFoundException e){
		  System.out.println(e);
		}catch(IOException e){
		  System.out.println(e);
		}

		return contents;
	}
}