import java.io.IOException;
import java.sql.SQLException;

import data.ContAttFileDelTrans;

public class ContAttFileDelDemonMain {
	
	public void startDemon(){
			try{
				long start = System.currentTimeMillis();
				System.out.println("ContAttFileDelDemon Start!!!!!!!!!!!!!!!!");
				
				ContAttFileDelTrans cad = new ContAttFileDelTrans();
				cad.getData();
				System.out.println("ContAttFileDelDemon End!!!!!!!!!!!!!!!!");
				long elapsedTimeMillis = System.currentTimeMillis()-start;
				System.out.println("RunTime : " + elapsedTimeMillis/1000F + " Sec");
				
			}catch (Exception e) {
				// TODO: handle exception
			}finally{
			
			}
			
		
		
	}
	
	public static void main(String ar[]) throws SQLException,IOException{
		
		ContAttFileDelDemonMain downloadDemonMain =  new ContAttFileDelDemonMain();
		downloadDemonMain.startDemon();
	}
	
}
