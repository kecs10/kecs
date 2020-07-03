package util;


public class DBInit implements Environment {
	
	public static String getMsSql() {
		return real ? mUrlR : mUrlT;
	}
	
	public static String getID(){
		return real ? mIDR : mIdT;
	}
	
	public static String getPW(){
		return real ? mPWR : mPWT;
	}
}
