package util;

import org.apache.log4j.Logger;


/*********************************************************************************************************
 * *******************************************************************************************************
 * ID       :   LogWrapper
 * 용도      :   log4j 사용
 * 주요기능 	:   1. 오류로그  
 * *******************************************************************************************************
 ********************************************************************************************************/

public class LogWrapper
{
	
	 public final static Logger biz = Logger.getLogger("BIZLOGGER");
	    public static void println(String mess){
	       biz.debug(mess);
	    }
	    public static void print(String mess){
	       biz.debug(mess);
	    }    
}//End-of-class
