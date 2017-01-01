import java.io.File;

class garbageDeleter extends Thread{
	private File myFile;


	public static void main(String[] args){

		File dFile = new File(args[0]);
		garbageDeleter myGD = new garbageDeleter(dFile);
		myGD.start();
	}



	// コンストラクタ
	garbageDeleter(File dFile){
		myFile = dFile;
	}

	public void run(){
		try {
			Thread.sleep(5000);

			for(int i=0;i<10000;i++){

				myFile.delete();

				if(!myFile.exists()){
					System.out.println("DELETED  :"+myFile);
					break;

				}else{
					System.out.println("DEL FAIL :"+myFile);
					Thread.sleep(1000);
				}
			}




		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}