package util;


public interface Environment {
    final static boolean real = true;			// 실서버 여부(true), 개발기(false)
    
    //final static String mUrlT = "jdbc:sqlserver://192.168.123.142:1433;SelectMethod=cursor;DatabaseName=csbill";  //dev
    final static String mUrlT = "jdbc:sqlserver://kecs.codns.com:1433;SelectMethod=cursor;DatabaseName=csbill";  //dev
    final static String mIdT  = "kecs1";
    final static String mPWT  = "kecs1234";
    
    final static String mUrlR = "jdbc:sqlserver://114.108.138.104:1433;SelectMethod=cursor;DatabaseName=kecs";	//real
    final static String mIDR  = "kecs";
    final static String mPWR  = "kecs1234";
}
 