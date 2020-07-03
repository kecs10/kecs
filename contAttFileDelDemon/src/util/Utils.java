/*
------------------------Class 설명---------------------------
ID          	:   Utils
용도       	:   공통 유틸리티 모음 클래스이다.
주요기능 	:   1. String 포멧관련 메소드 정의
					2. 날짜관련 메소드 정의
					3. 쿼리정보 담고 있는 프로퍼티 관련

-------------------Modified Log-----------------------------
ver         date                	author      	description
1.0         2007.10.18         박철홍     	First Coding

============================================================*/

package util;
  
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.*;
//import javax.servlet.http.*;

import sun.misc.BASE64Encoder;
 
public class Utils{
  
    public final static int YYYYMMDD 					= 0;
	public final static int YYYYMMDDHHMMSS 		= 1;
	public final static int SYYYYMMDD 					= 2;
	public final static int SYYYYMMDDHHMMSS 	= 3;
	public final static int SYYYYMM 						= 4;
	public final static int YYYYMMDDHHMMSSMI 	= 5;
	public final static int SYYYYMMDDHHMMSSMI 	= 6;
    public static String sDateSeparator     				= "-";
    public static String sDateFormat         				= "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd";
    public static String sDateTimeFormat  				= "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd HH:mm:dd";
    private final static String CHARSET_8859_1      	= "8859_1";
    private final static String CHARSET_KSC5601   	= "KSC5601";    
    private static String sSeparatorChar    				= String.valueOf(File.separatorChar) ;
    
    private final static String ADMINSQL 					= 	"sql/admin.properties";    	//= 관리자 쿼리
    private final static String SESSIONSQL				= 	"sql/session.properties";     //= 로그인 ,사용자 쿼리
    private final static String CUSTSQL					= 	"sql/cust.properties";     		//= 고객지원 쿼리
    private final static String BILLSQL						= 	"sql/bill.properties";     		//= 전자세금계산서 작성 쿼리
    private final static String BILLISSUESQL			= 	"sql/bill_issue.properties";   //= 전자세금계산서 조회 쿼리
    private final static String COMMSQL					= 	"sql/comm.properties";     	//= 공통처리 쿼리
    private final static String AUTHSQL					=   "sql/auth.properties";			//= 권한관리 
    private final static String MYPAGESQL				=   "sql/mypage.properties";		//= 마이페이지
    private final static String CONTSQL					=   "sql/cont.properties";			//= 전자계약서 쿼리
    private final static String CONTISSUESQL			=   "sql/cont_issue.properties";	//= 전자계약서조회 쿼리
    private final static String EGISSQL					=	 "sql/egis.properties";			//= 전자보증 쿼리
    
    
    /**
    * 문자열의 앞뒤 공백과 null를 제거한다.
    * <p>
    * <pre>변환할 문자열이 null, 또는 "" 일경우 " " 를 반환한다.<pre>
    * @param    strString  변환될 문자열
    * @return     변환된 문자열
    */
    public static String getString(String strString) {
        String strTemp = strString;

        if ((strTemp == null) || strTemp.equals("")) {
            return "";
        }

        return strTemp.trim();
    } 

    /**
    * 문자열의 앞뒤 공백과 null를 제거한다.
    * <p>
    * <pre>변환할 문자열이 null, 또는 "" 일경우 " " 를 반환한다.<pre>
    * @param    strString  변환될 문자열
    * @return     변환된 문자열
    */
    public static String[] getString(String[] astrString) {
        for (int i = 0; i < astrString.length; i++) {
            astrString[i] = getString(astrString[i]);
        }

        return astrString;
    } 

    /**
    * 1byte 영문(character type '8859_1')을 2byte 한글(character type 'KSC5601')로 변환한다.
    * null 일 경우 ""반환
    * <p>
    * @param      	strEng  1byte 영문
    * @return     	String  2byte 한글
    * @exception  UnsupportedEncodingException
    */
    public static String getEngToKor(String strEng) throws UnsupportedEncodingException {
        if (strEng == null || strEng.trim().equals ("")) {
            return "";
        }

        return new String(strEng.getBytes(CHARSET_8859_1), CHARSET_KSC5601).trim();
    } 

    /**
    * 1byte 영문(character type '8859_1')배열을 2byte 한글(character type 'KSC5601')배열로 변환한다.
    * <p>
    * @param      	astrEng   변환될 문자배열
    * @return     	String[]  변환된 문자배열
    * @exception  UnsupportedEncodingException
    */
    public static String[] getEngToKor(String[] astrEng) throws UnsupportedEncodingException {
        for (int i = 0; i < astrEng.length; i++) {
            astrEng[i] = getEngToKor(astrEng[i]);
        }

        return astrEng;
    } 

    /**
    * 2byte 한글(character type 'KSC5601')을 1byte 영문(character type '8859_1')으로 변환한다.
    * null 일 경우 ""반환
    * <p>
    * @param      	strKor  2byte 한글
    * @return     	String  1byte 영문
    * @exception  UnsupportedEncodingException
    */
    public static String getKorToEng(String strKor) throws UnsupportedEncodingException {
        if (strKor == null || strKor.trim ().equals("")) {
            return "";
        }

        return new String(strKor.getBytes(CHARSET_KSC5601), CHARSET_8859_1).trim();
    } 

    /**
    * 2byte 한글(character type 'KSC5601')을 1byte 영문(character type '8859_1')배열로 변환한다.
    * <p>
    * @param      	astrKor   변환될 문자배열
    * @return     	String[]  변환된 문자배열
    * @exception  UnsupportedEncodingException
    */
    public static String[] getKorToEng(String[] astrKor) throws UnsupportedEncodingException {
        for (int i = 0; i < astrKor.length; i++) {
            astrKor[i] = getKorToEng(astrKor[i]);
        }

        return astrKor;
    } 

    /**
    * 금액 형식의 문자열에 포함되어 있는 특정문자를 제거한다.
    * <p>
    * @param    strString    string.
    * @return     특정문자를 제거한 string.
    */
    public static String getRmDel(char charDelim, String strString) {
       if (strString == null) {
           return "";
       }

       StringBuffer buf = new StringBuffer();
       StringTokenizer tokenizer = new StringTokenizer (strString, String.valueOf(charDelim));
       
       while (tokenizer.hasMoreTokens()) {
           buf.append (tokenizer.nextToken());
       }

       return buf.toString ();
    } 

    ///////////////////////////////////////////////////////////////////////////////
    // 날짜,시간관련 사용자정의 메소드
    /**
    * 현재일자를 구함    TYPE = yyyymmdd
    * <p>
    * @return     현재일자 문자열
    */
    public static String getSysDate()   {
       return getSysYear() + getSysMonth() + getSysDay();
    } 

    /**
    * 현재일자를 구함    TYPE = yyyy[/|:|-]mm[/|:|-]dd
    * <p>
    * @param    cTocken  구분자 Char [/|:|-]
    * @return   	현재일자 문자열
    */
    public static String getSysDate(char cTocken)   {
       return getSysYear() + cTocken + getSysMonth() + cTocken + getSysDay();
    } 

    /**
    * 현재일자를 구함    TYPE = yyyy[/|:|-]mm[/|:|-]dd
    * <p>
    * @param   	cTocken  구분자 Char [/|:|-]
    * @return    	현재일자 문자열
    */
    public static String getSysDate(String cTocken)   {
       return getSysYear() + cTocken + getSysMonth() + cTocken + getSysDay();
    }     
   
    /**
    * 현재시간을 구함    TYPE = ttmmss
    * <p>
    * @return     현재시간 문자열
    */
    public static String getSysTime()   {
       return getSysHour() + getSysMinute() + getSysSecond();
    } 
   
    /**
    * 현재시간을 구함    TYPE = tt[/|:|-]mm[/|:|-]ss
    * <p>
    * @param   	cTocken  구분자 Char [/|:|-]
    * @return    	현재시간 문자열
    */
    public static String getSysTime(char cTocken)   {
       return getSysHour() + cTocken + getSysMinute() + cTocken + getSysSecond();
    } 

    /**
    * 현재날짜와 시간을 구함 TYPE = yyyy-mm-dd tt:m:ss
    * <p>
    * @return     현재날짜와 시간 문자열
    */
    public static String getSysDateTime()  {
       return getSysDate('-') + " " + getSysTime(':');
    } 

    public static String getSysDateTime(String format)  {
        String fmt;
        
        if (format == null) {
            fmt = "yyyy/MM/dd HH:mm:ss";
        } else {
            fmt = format;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(fmt);

        return formatter.format(new Date());
    } 
       
    /**
    * 현재년도를 구함 TYPE = yyyy
    * <p>
    * @return     현재년도 문자열
    */
    public static String getSysYear()   {
       GregorianCalendar todaysDate = new GregorianCalendar();
 
       return Integer.toString(todaysDate.get(Calendar.YEAR));
    } 
   
    /**
    * 현재달을 구함 TYPE = mm
    * <p>
    * @return     현재달 문자열
    */
    public static String getSysMonth()  {
       int nMonth;
       DecimalFormat formatter = new DecimalFormat("00");
       GregorianCalendar todaysDate = new GregorianCalendar();

       nMonth = todaysDate.get(Calendar.MONTH)+1;
       return formatter.format(nMonth);
    } 
   
    /**
    * 현재일을 구함 TYPE = dd
    * <p>
    * @return     현재일 문자열
    */
    public static String getSysDay() {
       int nDay;
       DecimalFormat formatter = new DecimalFormat("00");
       GregorianCalendar todaysDate = new GregorianCalendar();

       nDay = todaysDate.get(Calendar.DATE);
       return formatter.format(nDay);
    } 

    /**
    * 현재시를 구함 TYPE = tt
    * <p>
    * @return     현재시 문자열
    */
    public static String getSysHour()   {
       int nHour, nAmpm;
      
       DecimalFormat formatter = new DecimalFormat("00");
       GregorianCalendar todaysDate = new GregorianCalendar();

       nAmpm = todaysDate.get(Calendar.AM_PM);
       nHour = todaysDate.get(Calendar.HOUR);
       if(nAmpm == 1) {
          nHour = nHour+12;
          return Integer.toString(nHour);
       } 
       return formatter.format(nHour);
    } 
   
    /**
    * 현재분을 구함 TYPE = mm
    * <p>
    * @return     현재분 문자열
    */
    public static String getSysMinute() {
       int nMinute;
       DecimalFormat formatter = new DecimalFormat("00");
       GregorianCalendar todaysDate = new GregorianCalendar();

       nMinute = todaysDate.get(Calendar.MINUTE);
       return formatter.format(nMinute);
    } 

    /**
    * 현재초를 구함 TYPE = ss
    * <p>
    * @return     현재초 문자열
    */
    public static String getSysSecond() {
       int nSecond;
       DecimalFormat formatter 			= new DecimalFormat("00");
       GregorianCalendar todaysDate 	= new GregorianCalendar();

       nSecond 								= todaysDate.get(Calendar.SECOND);
       return formatter.format(nSecond);
    } 

   /**
    * 날짜에서 년도를 더하고 뺌
    * <p>
    * @param    String   yyyymmdd    	날짜(TYPE = yyyymmdd)
    *           		String   strY        		증감값
    * @return   	String   변환날짜문자열
    */
    public static String getAddYear(String yyyymmdd, String strY) {
       return getAddYear(yyyymmdd, Integer.parseInt(strY));
    }

    /**
    * 날짜에서 년도를 더하고 뺌
    * <p>
    * @param    String   	yyyymmdd    날짜(TYPE = yyyymmdd)
    *           		int      	nY          		증감값
    * @return   	String   	변환날짜문자열
    */
    public static String getAddYear(String yyyymmdd, int distYear) {
        if (yyyymmdd.length () != 8) {
            return "";
        }
        
        String year 	= yyyymmdd.substring(0, 4);
        String month 	= yyyymmdd.substring(4, 6);
        String day 		= yyyymmdd.substring(6, 8);
        int sumYear 	= Integer.parseInt(year) + distYear;

        if(month.equals("02") && day.equals ("29"))  {
            // 2월29일이고 윤년이 아니면 28일을 되돌린다.
            if(!(sumYear % 4 == 0 && (sumYear % 100 != 0 || sumYear % 400 == 0))) {
                day = "28";
            }
        }
        
        return Integer.toString(sumYear) + month + day;
    }

    /**
    * 날짜에서 월을 더하고 뺌
    * <p>
    * @param    String   s           			날짜(TYPE = yyyymmdd)
    *           		String   addMonth    	addMonth 증감값
    * @return   	String   변환날짜문자열
    */
    public static String getAddMonth(String date, String addMonth) {
        int distMonth = Integer.parseInt(addMonth);

        return getAddMonth(date, distMonth);
    }

    /**
    * 날짜에서 월을 더하고 뺌
    * <p>
    * @param    String   	s           		날짜(TYPE = yyyymmdd)
    *           		int      	addMonth    	addMonth 증감값
    * @return   	String   	변환날짜문자열
    */
    public static String getAddMonth(String sDate, int addMonth) {
        if (sDate.length () != 8) {
            return "";
        }

        int year 	= Integer.parseInt (sDate.substring (0, 4));
        int month 	= Integer.parseInt (sDate.substring (4, 6));
        int day 		= Integer.parseInt (sDate.substring (6, 8));

        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add (Calendar.MONTH, addMonth);

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

        return formatter.format (calendar.getTime ());
    }

    private String getAddCalendar (String sDate, int add, int field) {
        if (sDate.length () != 8) {
            return "";
        }

        int year 	= Integer.parseInt (sDate.substring (0, 4));
        int month 	= Integer.parseInt (sDate.substring (4, 6));
        int day 		= Integer.parseInt (sDate.substring (6, 8));

        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add (field, add);

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

        return formatter.format (calendar.getTime ());
    }

    /**
    * 해당월의 마지막날을 구함
    * <p>
    * @param    int      	year        	년도(TYPE = yyyy)
    *           		int      	month       월(TYPE = mm)
    * @return   	String   	구해진날짜문자열
    */
    public static String lastDay(int year, int month)   {
       int day = 0;
       switch(month)  {
	       case 1: 		/* '\001' */
	       case 3: 		/* '\003' */
	       case 5: 		/* '\005' */
	       case 7: 		/* '\007' */
	       case 8: 		/* '\b' */
	       case 10: 	/* '\n' */
	       case 12: 	/* '\f' */
	          day = 31;
	          break;
	       case 2: 		/* '\002' */
	          if(year % 4 == 0) {
	             if(year % 100 == 0 && year % 400 != 0) {
	                 day = 28;
	             }
	             else {
	                 day = 29;
	             }
	          }
	          else  {
	             day = 28;
	          }
	          break;
	       case 4: 		/* '\004' */
	       case 6: 		/* '\006' */
	       case 9: 		/* '\t' */
	       case 11: 	/* '\013' */
	       default:
	          day = 30;
	          break;
       }
       return String.valueOf(day);
    }

    /**
    * 날짜형식으로 변환
    * <p>
    * @param    String   s        	년도(TYPE = yyyymmdd)
    *           		String   format   	날짜포맷
    * @return   	String   구해진날짜문자열
    */
    private static Date check(String year, String format) throws ParseException   {
       if(year == null) {
           throw new ParseException("date string to check is null", 0);
       }
       if(format == null) {
           throw new ParseException("format string to check date is null", 0);
       }

       SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
       Date date = null;

       try   {
          date = formatter.parse(year);
       }
       catch(ParseException e) {
          throw new ParseException(" wrong date:\"" + year + "\" with format \"" + format + "\"", 0);
       }

       if(!formatter.format(date).equals(year)) {
           throw new ParseException("Out of bound date:\"" + year + "\" with format \"" + format + "\"", 0);
       }
       else {
           return date;
       }
    }

    public static String numberFormat (long num, String pattern) {
        DecimalFormat dFormatter 	= new DecimalFormat (pattern);
        NumberFormat nFormatter 	= NumberFormat.getInstance ();

        String formatted;
        
        if (pattern.length () > 0) {
            formatted = dFormatter.format (num);
        } else {
            formatted = nFormatter.format (num);
        }
        
        return formatted;
    }
    
    public static String numberFormat (long num) {
        return numberFormat (num, "");
    }
 
    public static String numberFormat (double num) {
        return numberFormat (num, "");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // 문자열 연산
    /**
    * 숫자형을 '###,###,###,###,###' 형식으로 바꿈
    * <p>
    * @param    int      	st       값
    * @return   	String   	형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
    */
    public static String getComma(int num) {
        String formatted;
        
        if (num == 0) {
            formatted = "";
        } else {
            formatted = numberFormat (num, "###,###,###,###");
        }
        
        return formatted;
    }


    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    int      	st       값
    * @return   	String   	형식에 맞게 변환된결과
    */
    public static String getComma0(int num) {
        return numberFormat (num, "###,###,###,##0");
    }

    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    long     st       값
    * @return   	String   형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
    */
    public static String getComma(long num)    {
        String formatted;
        
        if (num == 0) {
            formatted = "";
        } else {
            formatted = numberFormat (num, "###,###,###,###,###");
        }
        
        return formatted;
    }

    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    long     st       값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getComma0(long num) {
        return numberFormat (num, "###,###,###,###,##0");
    }


    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    String   str      값
    * @return   	String   형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
    */
    public static String getComma(String strNum) {
        double dNum;
        String formatted;
        
        if (strNum == null) {
            dNum = 0;
        } else if (strNum.trim().length () == 0) {
            dNum = 0;
        } else if (strNum.indexOf(',') >= 0){
            return strNum;
        } else {
            dNum = Double.parseDouble (strNum);
        }
    
        if (dNum == 0) {
            formatted = "";
        } else {
            formatted = numberFormat (dNum);
        }
        
        return formatted;
    }

    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    String   str      값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getComma0(String strNum) {
        double dNum;
        
        if (strNum == null) {
            return "";
        } else if (strNum.trim().length () == 0) {
            //dNum = 0;
            return "";
        } else if (strNum.indexOf(',') >= 0){
            return strNum;
        } else {
            dNum = Double.parseDouble (strNum);
        }
    
        return numberFormat (dNum);
    }

    /**
     * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
     * <p>
     * @param    String   str      값
     * @return   	String   형식에 맞게 변환된결과
     */
     public static String getComma1(String strNum) {
         double dNum;
         
         if (strNum == null) {
             return "";
         } else if (strNum.trim().length () == 0) {
             return "";
         } else if (strNum.indexOf(',') >= 0){
             return strNum;
         } else {
             dNum = Double.parseDouble (strNum);
         }
     
         return numberFormat (dNum);
     }

    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    BigDecimal  st    값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getComma0(BigDecimal bdNum) {
        double num;
        
        if (bdNum == null) {
            num = 0;
        } else {
            num = bdNum.doubleValue ();
        }
        
        return numberFormat (num);
    }


    /**
    * 숫자형을 '###,###,###,###,###' 형식으로 바꿈
    * <p>
    * @param    BigDecimal  	str   값
    * @return   	String   			형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
    */
    public static String getComma(BigDecimal bdNum) {
        double dNum;
        String formatted;
        
        if (bdNum == null) {
            dNum = 0;
        } else {
            dNum = bdNum.doubleValue ();
        }

        if (dNum == 0) {
            formatted = "";
        } else {
            formatted = numberFormat (dNum);
        }
        
        return formatted;
    }


    /**
    * 숫자형을 '###,###,###,###,###' 형식으로 바꿈
    * <p>
    * @param    short    st    값
    * @return   	String   형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
    */
    public static String getComma(short shortNum) {
        String formatted;
        
        if (shortNum == 0) {
            formatted = "";
        } else {
            formatted = numberFormat (shortNum, "###,###,###,###");
        }
        
        return formatted;
    }


    /**
    * 숫자형을 '###,###,###,###,##0' 형식으로 바꿈
    * <p>
    * @param    short    st    값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getComma0(short shortNum)   {
        return numberFormat (shortNum, "###,###,###,##0");
    }


    /**
    * 시간값을 '##:##:##' 형식으로 바꿈
    * <p>
    * @param    String   str   값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getTimeColon(String str) {
        String time;

        if(str.equals("0000")) {
            time = "";
        } else if(str.trim().equals("")) {
            time = "";
        } else if(str.indexOf(":") >= 0) {
            time = str;
        } else {
            if(str.length() == 4) {
                time = str.substring(0, 2) + ":" + str.substring(2);
            } else {
                if(str.length() >= 6) {
                    time = str.substring(0, 2) + ":" + str.substring(2, 4) + ":" + str.substring(4);
                } else {
                    time = "";
                }
            }
        }

        return time;
    }

    /**
    * 년월일을 '####/##/##' 형식으로 바꿈
    * <p>
    * @param    String   str   값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getDaySlash(String str) {
       return getDaySlash(getString(str), "/");
    }

    /**
    * 년월일을 '####[사용자정의]##[사용자정의]##' 형식으로 바꿈
    * <p>
    * @param    String   str   	값
    *           		String   del   	구분자
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getDaySlash(String str, String del) {
       
    	if(str == null) str="";
    	
    	if(str.equals("00000000")) {
           return "";
    	} 

    	if(str.indexOf(del) != -1) {
           return str;
    	} 

       String date;

       if(str.length() == 8) {
           date = str.substring(0, 4) + del + str.substring(4, 6) + del + str.substring(6);
       }
       else if(str.length() == 6) {
           date = str.substring(0, 4) + del + str.substring(4, 6);
       }
       else {
           date = "";
       }

       return date;
    }

    /**
    * 년월을 '####/##' 형식으로 바꿈
    * <p>
    * @param    String   str   값
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getMonthSlash(String str)  {
       return getMonthSlash(str, "/");
    }

    /**
    * 년월을 '####[사용자정의]##' 형식으로 바꿈
    * <p>
    * @param    String   str   	값
    *           		String   del   	구분자
    * @return   	String   형식에 맞게 변환된결과
    */
    public static String getMonthSlash(String str, String del)  {
        if(str.equals("000000")) {
            return "";
        } 

        if(str.indexOf(del) != -1) {
            return str;
        } 

        String date;

        if(str.length() == 6) {
            date = str.substring(0, 4) + del + str.substring(4, 6);
        }
        else {
            date = "";
        }

        return date;
    }


	  /**
	  * <PRE>
	  * String을 int값으로 변환한다.<BR>
	  * EX) int cnt = BaseUtil.stoi("10");<BR>
	  * </PRE>
	  * @param  str int값으로 변환될 String문자열.
	  * @return 	변환된 int 값.
	  */
	  public static int stoi(String str) {
	      int num;
	      
	      if(str == null) {
	          num = 0;
	      } else {
	          if(str.equals("")) {
	              num = 0;
	          } else {
	              num = Integer.valueOf(str).intValue();
	          }
	      }
	
	      return num;
	  }

	  /**
	  * <PRE>
	  * int값을 String으로 변환한다.<BR>
	  * EX) Strint str = BaseUtil.itos(10);<BR>
	  * </PRE>
	  * @param  i String으로 변환될 int 값.
	  * @return 	변환된 String 값.
	  */
	  public static String itos(int iValue) {
	    return String.valueOf (iValue);
	  }

	/**
	* <PRE>
	* Float값을 String으로 변환한다.<BR>
	* EX) Strint str = BaseUtil.ftos(10);<BR>
	* </PRE>
	* @param  	f String으로 변환될 Float 값.
	* @return 	변환된 String 값.
	*/
	public static String ftos(float iValue) {
	  return iValue+"";
	}

	/**
	* <PRE>
	* String값을 float로 변환한다.<BR>
	* EX) float f = BaseUtil.stof("10");<BR>
	* </PRE>
	* @param  	i String으로 변환될 int 값.
	* @return 	변환된 String 값.
	*/
	public static float stof(String str) {
	  if(str == null ) {
	      return 0;
	  } 
	  if(str.equals("") ) {
	          return 0;
	      } 
	
	  return Float.parseFloat(str);
	}

  /**
  * <PRE>
  * String값을 double로 변환한다.<BR>
  * EX) double d = BaseUtil.stod("10");<BR>
  * </PRE>
  * @param  i String으로 변환될 int 값.
  * @return 	변환된 String 값.
  */
  public static double stod(String str) {
    if(str == null ) {
        return 0;
    } 
    if(str.equals("") ) {
            return 0;
        } 

    return Double.parseDouble(str);
  }

  /**
  * <PRE>
  * 데이타베이스로 문자열을 저장하기 전에 변환<BR>
  * (주의) 한글변환을 위하여 사용<BR>
  * </PRE>
  * @param  str    		DB로 저장할 한글이 들어있는 문자열
  * @return 	tmpStr 	8859_1 codeset으로 변환된 문자열
  */
  public static String toDB(String str) {
    String tmpStr = null;

    try {
        tmpStr = Ksc2Uni(str);
    } catch(UnsupportedEncodingException ueEx) {
        ueEx.printStackTrace(System.err);
    }

    return tmpStr;
  }

  /**
  * <PRE>
  * 데이타베이스로부터 얻은 문자열을 변환<BR>
  * (주의) 한글변환을 위하여 사용함<BR>
  * </PRE>
  * @param  str    		DB에서 가져온 한글이 들어있는 문자열
  * @return 	tmpStr 	KSC5601 codeset으로 변환된 문자열
  */
  public static String fromDB(String str){
    String tmpStr = null;

    try
    {
        tmpStr = Uni2Ksc(str);
    }
    catch(UnsupportedEncodingException ueEx)
    {
        ueEx.printStackTrace(System.err);
    }

    return tmpStr;
  }

  /**
  * <PRE>
  * Ksc5601 ---> 8859_1 문자열로 변환<BR>
  * 예) Strint str = BaseUtil.Ksc2Uni("제목:\n"); <BR>
  * </PRE>
  * @param  KscStr
  * @return 	String
  */
  public static String Ksc2Uni(String kscStr) throws java.io.UnsupportedEncodingException{
    if(kscStr == null)
    {
      return null;
    }
    else
    {
      return new String (kscStr.getBytes(CHARSET_KSC5601), CHARSET_8859_1);
    }
  }

  /**
  * <PRE>
  * 8859_1 ---> Ksc5601 문자열로 변환 <BR>
  * </PRE>
  * @param  UnicodeStr
  * @return 	String
  */
  public static String Uni2Ksc(String unicodeStr) throws UnsupportedEncodingException{
    if(unicodeStr == null)
    {
      return null;
    }
    else
    {
      return new String (unicodeStr.getBytes(CHARSET_8859_1), CHARSET_KSC5601);
    }
  }
  
  /**
  * <PRE>
  * 소숫점 자리수를 조작한다.
  * (예) 10.245 --> 10.24, 9 --> 9.00
  * </PRE>
  * @param    	strSrc 	변경할 숫자 문자열
  * @param    	len    	소숫점 자리수
  * @return   	변경된   숫자 문자열
  */
  public static String setDecimal(String strSrc, int len) {
    int length 				= strSrc.length();
    int iDec 				= strSrc.indexOf('.');
    StringBuffer convert	= new StringBuffer();
    
    if (iDec < 0) {
        convert.append(strSrc);
    } else {
        convert.append(strSrc.substring(0, iDec));
    }

    if(iDec < 0) {
        iDec = length;
    } 
    
    for(int i = 1; i <= len; i++) {
      if (i == 1) {
          convert.append(".");
      } 

      try {
        convert.append(((i + iDec) < length) ? strSrc.charAt(i + iDec) : '0');
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return convert.toString();
  }  
  

  /**
  * <PRE>
  * 소숫점 자리수를 조작한다. 예) 10.245 --> 10.24 , 9 --> 9.00
  * </PRE>
  * @param    	double 	변경할 숫자
  * @param    	len    	소숫점 자리수
  * @return   	변경된 숫자 문자열
  */
  public static String setDecimal(double dNumber, int len){
    return setDecimal(Double.toString(dNumber), len);
  }

  /**
  * <PRE>
  * 문자열 날짜를 mask format으로 분석하여 돌려줌.
  * </PRE>
  * @param  src  14자리 년월일 String
  * @param  mask yyyyMMdd
  * @return '/'로 분할된 10자리 년월일 String
  */
  public static String dateMask(String src, String mask) throws ParseException{
    if (src == null || src.length() == 0) {
        return "";
    } 
    src = getNumber(src);
    String pattern = "";

    switch (src.length())
    {
      case   8 :  pattern = "yyyyMMdd";
                  break;
      case  10 :  pattern = "yyyyMMddHH";
                  break;
      case  12 :  pattern = "yyyyMMddHHmm";
                  break;
      case  14 :  pattern = "yyyyMMddHHmmss";
                  break;
      case  15 :  pattern = "yyyyMMddHHmmss";
                  src = src.substring(0,14);
                  break;
    }

    // src를 날짜변수로 변환
    SimpleDateFormat fmt= new SimpleDateFormat(pattern);
    java.util.Date  date = fmt.parse(src);
    // 날짜변수를 주어진 mask패턴으로 변환
    fmt.applyPattern(mask);
    String str = fmt.format(date);

    return str;
  }

  /**
  * <PRE>
  * 입력받은 날짜가 한주의 몇번째 요일인지 int값을 돌려주는 함수
  * </PRE>
  * @param  	date  입력받은 날짜
  * @return       한주의 몇번째 요일
  */
  public static int getDayOfWeek(java.util.Date date){
    String weekdays[] 	= (new DateFormatSymbols()).getShortWeekdays();
    String weekday    	= (new SimpleDateFormat("E")).format(date);

    for (int i = 1; i <= weekdays.length; ++i)
    {
      if (weekday.equals(weekdays[i])) {
          return i;
      } 
    }

    return -1;
  }
 

  /**
  * <PRE>
  * 입력받은 String을 원하는 길이만큼 원하는 문자로 오른쪽을 채워주는 함수.
  * </PRE>
  * @param  	calendar 입력받은 String
  * @return       지정된 문자로 채워진 String
  */
  public static String rpad(String str, int len, char pad){
    String result 	= str;
    int templen 	= len - result.getBytes().length;

    for (int i = 0; i < templen; i++)
    {
      result = result + pad;
    }

    return result;
  }

  /**
  * <PRE>
  * 입력받은 String을 원하는 길이만큼 원하는 문자로 왼쪽을 채워주는 함수.
  * </PRE>
  * @param 		calendar  입력받은 String
  * @return      	지정된 문자로 채워진 String
  */
  public static String lpad(String str, int len, char pad){
    String result 	= str;
    int templen 	= len - result.getBytes().length;

    for (int i = 0; i < templen; i++) {
        result = pad + result;
    } 

    return result;
  }

  /**
  * <PRE>
  * 문자열중 특정문자를 치환한다.
  * </PRE>
  * @param    	str     	대상문자열
  * @param    	src     	치환당할 문자
  * @param    	tgt     	치환할 문자
  * @return   	완성된 문자열
  */
  public static String replace(String str, String src, String tgt){
    StringBuffer buf 	= new StringBuffer();
    String temp 		= null;

    if (str == null || str.length() == 0) {
        return "";
    } 

    int idx = 0;
    int len = src.length();

    while (idx < str.length() - len + 1) {
      temp = str.substring(idx, idx + len);

      if (temp.equals(src)) {
        buf.append(tgt);
        idx = idx + len;
      } else {
        buf.append(str.substring(idx, idx + 1));
        idx++;
      }
    }

    if (idx < str.length()) {
        buf.append(str.substring(idx, str.length()));
    } 

    return buf.toString();
  }
  
  /**
   * <PRE>
   * 문자열중 SPACE를 제거한다.
   * </PRE>
   * @param str
   * @return 	del space String
   */
  public static String delSpace(String str) throws Exception{
      StringBuffer sBuff = new StringBuffer();
      for (int i=0; i < str.length();  i++) 
      {  
          if (str.charAt(i) != 10)  
             sBuff.append(str.charAt(i)) ; 
      }       
      return sBuff.toString();
  }
 

  /**
  * <PRE>
  * 문자열중 특정문자를 제거한다  (예) 2001/01/01 ==> 20010101
  * </PRE>
  * @param    	str     	대상문자열
  * @param    	tok     	제가할 문자열
  * @return   	완성된 문자열
  */
  public static String remove(String str, String tok){
    return replace(str, tok, "");
  }

  /**
  * <PRE>
  * 입력받은 문자열 중 size(byte)를 넘지 않도록 계산해서 해당 문자열을 돌려줌.
  * </PRE>
  * @param 		str   	문자열
  * @param 		size  byte수
  * @return  		문자열 중 size(byte)를 넘지않는 문자열을 돌려줌
  */
  public static String getLeftHan(String str, int size){
    int cnt 	= 0;
    int pos 	= 0;

    while ((pos < str.length()) && (cnt < size-1))
    {
      String temp = str.substring(pos, pos+1);

      // 한글이 아닌 경우 뒤의 2byte가 0값으로 나온다.
      if (temp.getBytes().length == 2) {
          cnt = cnt + 2;
      } 
      else {
          cnt = cnt + 1;
      } 

      pos = pos + 1;
    }

    return str.substring(0, pos);
  }

  /**
  *  <PRE>
  *  년도 LISTBOX를 RETURN 시켜주는 함수
  *  </PRE>
  *  @param		objname  	리스트박스이름
  *  @param		cdefault 	default로 보여질 값
  *  @return		년도 select box
  */
  public String getListYearBox( String objname, String cdefault) {
    /*
    int i  = 0;
    int yy = Utils.stoi(Utils.getCurrentDate().substring(0,4));
    StringBuffer YearBox = new StringBuffer();
    YearBox.append("<select name=\""+ objname +"\">" + "\n");
    for(i = yy-5; i <= yy+3; i++){
      if(cdefault.equals(Utils.itos(i))) YearBox.append("<option value=\""+ i +"\" selected>"+ i +"</option>" + "\n");
      else YearBox.append("<option value=\""+ i +"\">"+ i +"</option>" + "\n");
    }
    YearBox.append("</select>" + "\n");
    return YearBox.toString();
    */
    return null;
  }

  /**
  * <PRE>
  * 월 LISTBOX를 RETURN 시켜주는 함수
  * </PRE>
  * @param		objname  	리스트박스이름
  * @param		cdefault 	default로 보여질 값
  * @return		월 select box
  */
  public String getListMonthBox( String objname, String cdefault){
    String newLine 	= "\n";
    String[] months 	= {"01","02","03","04","05","06","07","08","09","10","11","12"};
    StringBuffer monthBox = new StringBuffer();
    monthBox.append("<select name=\""+ objname +"\" > " + newLine);

    for (int i = 0; i < months.length; i++)
    {
      if(cdefault.equals(months[i])) {
          monthBox.append("<option value=\""+ months[i] +"\" selected>"+ months[i] +"</option>" + newLine);
      } else {
          monthBox.append("<option value=\""+ months[i] +"\">"+ months[i] +"</option>" + newLine);
      }
    }

    monthBox.append("</select>" + newLine);

    return monthBox.toString();
  }

  /**
  * <PRE>
  * 금액문자열을 금액표시타입으로 변환한다.
  * (예) 12345678 --> 12,345,678
  * </PRE>
  * @param    	strMoney    금액문자열
  * @param    	delimeter   금액표시 구분자
  * @return   	변경된 금액 문자열
  */
  public static String makeMoneyType(String strMoney, String delimeter) {
    DecimalFormat formatter 		= new DecimalFormat();
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setGroupingSeparator(delimeter.charAt(0));
    formatter.setGroupingSize(3);
    formatter.setDecimalFormatSymbols(dfs);

    return (formatter.format(Double.parseDouble(strMoney))).toString();
  }

  /**
  * <PRE>
  * 숫자를 금액표시타입으로 변환한다.
  * (예) 12345678 --> 12,345,678
  * </PRE>
  * @param    	intMoney  금액
  * @param    	delimeter 금액표시 구분자
  * @return   	변경된 금액 문자열
  */
  public static String makeMoneyType(int intMoney, String delimeter) {
    return makeMoneyType(Integer.toString(intMoney), delimeter);
  }

  /**
  * <PRE>
  * 금액문자열을 금액표시타입으로 변환한다.
  * (예) 12345678 --> 12,345,678
  * </PRE>
  * @param    	strMoney  	금액문자열
  * @param    	delimeter   금액표시 구분자
  * @param    	offset      	금액표시 구분 간격
  * @return   	변경된 금액 문자열
  */
  public static String makeMoneyType(String strMoney, String delimeter, int offset) {
    DecimalFormat formatter 		= new DecimalFormat();
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setGroupingSeparator(delimeter.charAt(0));
    formatter.setGroupingSize(offset);
    formatter.setDecimalFormatSymbols(dfs);

    return (formatter.format(Double.parseDouble(strMoney))).toString();
  }


  /**
  * <PRE>
  * 숫자를 금액표시타입으로 변환한다.
  * (예) 12345678 --> 12,345,678
  * </PRE>
  * @param    	intMoney  	금액
  * @param    	delimeter   금액표시 구분자
  * @param    	offset      	금액표시 구분 간격
  * @return   	변경된 금액 문자열
  */
  public static String makeMoneyType(int intMoney, String delimeter, int offset) {
    return makeMoneyType(Integer.toString(intMoney), delimeter, offset);
  }

  /**
  * <PRE>
  * 금액표시타입을 금액문자열로 변환한다.
  * (예) 12,345,678 --> 12345678
  * </PRE>
  * @param    	strMoney    	금액표시 문자열.
  * @param    	delimeter   	금액표시 구분자.
  * @return   	금액문자열.
  */
  public static String makeNoMoneyType(String strMoney, String delimeter) {
    StringTokenizer tokenizer 	= new StringTokenizer(strMoney, delimeter);
    StringBuffer convert 			= new StringBuffer();

    while(tokenizer.hasMoreTokens()){
      convert.append(tokenizer.nextToken());
    }

    return convert.toString();
  }
 

  /**
  * <PRE>
  * 주민번호 타입으로 변환한다.
  * (예) 7607171177510 ==> 760717-1177510
  * </PRE>
  * @param    	strPerno  주민번호문자열
  * @return   	변환된 주민번호 문자열
  */
  public static String makePernoType(String strPerno){
      String perno;
      
      if (strPerno == null || strPerno.length() == 0) {
          perno = "";
      } else {
          if (strPerno.length() != 13) {
              perno = strPerno;
          } else {
              perno = strPerno.substring(0,6) + "-" + strPerno.substring(6,13);
          }
      }

      return perno;
  }

  /**
  * <PRE>
  * 사업자번호 타입으로 변환한다.<br>
  * (예) 1231212345 ==> 123-12-12345<br>
  * </PRE>
  * @param    	strSano : 주민번호문자열
  * @return   	strSano1 + "-" + strSano2 + "-" + strSano3 : 변환된 주민번호 문자열
  */
  public static String makeSanoType(String strSano) {
      String sano;
      
      if (strSano == null || strSano.length() == 0) {
          sano = "";
      } else {
          if (strSano.length() != 10) {
              sano = strSano;
          } else {
              sano = strSano.substring(0,3) + "-" + strSano.substring(3,5) + "-" + strSano.substring(5);
          }
      }
      
      return sano;
  }

	/**
	* <PRE>
	* 법인번호 타입으로 변환한다.<br>
	* (예) 1234561234567 ==> 123456-1234567<br>
	* </PRE>
	* @param    strBuno : 법인번호문자열
	* @return   	strBuno1 + "-" + strSano2 : 변환된 법인번호 문자열
	*/
	public static String makeBunoType(String strBuno) {
	    String buno;
	      
	    if (strBuno == null || strBuno.length() == 0) {
	        buno = "";
	    } else {
	        if (strBuno.length() != 13) {
	           buno = strBuno;
	        } else {
	            buno = strBuno.substring(0,6) + "-" + strBuno.substring(6,13);
	        }
	    }
	      
	    return buno;
	}

  /**
  * <PRE>
  * 우편번호 타입으로 변환한다.
  * (예) 630521 --> 630-521
  * </PRE>
  * @param    	postno  우편번호
  * @return   	변환된 우편번호 문자열
  */
  public static String makePostType(String postno){
      String formatted;
      
      if (postno == null || postno.length() == 0) {
          formatted = "";
      } else {
          if (postno.length() != 6) {
              formatted = postno;
          } else {
              formatted = postno.substring(0,3) + "-" + postno.substring(3,6);
          }
      }
      
      return formatted;
  }



  /**
  * <PRE>
  * 문자열중 특정문자를 제거한다
  * (예) 2001/01/01 ==> 20010101
  * </PRE>
  * @param    	strDate  날짜문자열
  * @param    	flag     	제거할문자열
  * @return   	완성된 문자열
  */
  public static String spirit(String strDate, String flag){
    StringBuffer str = new StringBuffer ();
    
    if (strDate == null || strDate.length() == 0) {
        return "";
    } 
    
    for(int i = 0; i < strDate.length(); i++) {
      if(!strDate.substring (i, i + 1).equals(flag)) {
        str.append (strDate.substring (i, i + 1));
      }
    }
    
    return str.toString ();
  }

  /**
  * <pre>
  * get방식에서의 한글 스페이스 문제 (예 : 홍 길 동 --> 홍_길_동)
  * </pre>
  * @param args : 스페이스를 포함한 한글 문자열
  * @return '_'를 포함한 한글 문자열
  */
  public String encodeSpace(String str) {

    StringTokenizer tokenizer = new StringTokenizer(str);
    String spString = "";

    while (tokenizer.hasMoreTokens()) {
      spString = spString + tokenizer.nextToken() + "_";
    }

    return spString;
  }

  /**
  * <pre>
  * get방식에서의 한글 스페이스 문제(예 : 홍_길_동 --> 홍길동)
  * </pre>
  * @param args : '_'를 포함한 한글 문자열
  * @return '_'를 제외한 한글 문자열
  */
  public String decodeSpace(String str){
    StringTokenizer tokenizer = new StringTokenizer(str, "_");
    String returnStr = "";

    while(tokenizer.hasMoreTokens()){
      returnStr = returnStr + tokenizer.nextToken() + " ";
    }

    return returnStr;
  }

  /**
  * <pre>
  * 문자열을 바이트수만큼 자름
  * </pre>
  */
  public static String limitString(String src, int len) {
    boolean chkFlag = false;
    String  strName = src.trim();
    byte[] arName = strName.getBytes();

    if (arName.length > len) {
      for (int idx = 0; idx<len; idx++) {
        if (arName[idx] < 0) { // 0x80 이상 ( 1byte짜리. 키값들)
          chkFlag = !chkFlag; // true로
        } else {
          chkFlag = false; // false로
        }
      }

      if (chkFlag) {
        strName = new String(arName, 0, len+1); // 홀수이면
      } else {
        strName = new String(arName, 0, len); // 짝수일때
      }
    } else {
      strName = new String(arName,0,arName.length);
    }

    return strName;
  }


  /**
  * <pre>
  * 개발용 디버깅용 로그 프린트
  * </pre>
  */
  public static void print(String str) {
      boolean verbose = true;

      if(verbose == true) {
        System.out.println(str);
      }
  }
 
  public static String spdDate(String str,String mask) throws ParseException{
      
      return dateMask(getNumber(str),mask);
  }
 
 
  public static String spdData(java.lang.Object obj) {
      String rtn = "";

      if(obj != null && !(obj.toString().equals("NULL") || obj.toString().equals("null"))){
          rtn = Utils.replace(obj.toString(),"\"","\"\"");
      }

      return  rtn; 
  }

  public static String spdMemo(java.lang.Object obj){
      String rtn = "";

      if(obj != null && !obj.toString().equals("null")) {
          rtn = Utils.replace(Utils.spdData(obj.toString()),"\r\n", "\"&chr(13)&chr(10)&\"");
      }

      return  rtn; 
  }

 /**
  * <pre>
  * 페이징에 필요한 쿼리로 변환시킨다.
  * </pre>
  * @param 	args : String qry, int pageRCT, int firstSEQ
  * @return 	queryString
  */
  public static String makePagingQuery(String qry, int pageRCT, int pageNUM){
    int prePage   		= 0;
    int nextPage  	= 0;
    String rstQry 		= null;

    if((pageNUM - pageRCT) >= 0) {
        prePage  = pageNUM + 1;
    }else{
        prePage  = pageNUM;
    }

    nextPage = pageNUM + pageRCT;
    StringBuffer temp = new StringBuffer();

    temp.append("SELECT *                                                \n");
    temp.append("  FROM (                                                \n");
    temp.append("SELECT ROWNUM AS NUM, BSQY.*                            \n");
    temp.append("  FROM (                                                \n");
    temp.append("SELECT A2.*, SUM(RCNT) OVER (ORDER BY RCNT) RECORDCOUNT \n");
    temp.append("  FROM (                                                \n");
    temp.append("SELECT A1.*, 1 RCNT                                     \n");
    temp.append("  FROM                                                  \n");
    temp.append("( " + qry + " )                                         \n");
    temp.append("A1)                                                     \n");
    temp.append("A2)                                                     \n");
    temp.append("BSQY)                                                   \n");
    temp.append(" WHERE NUM BETWEEN " + prePage + " AND " + nextPage +"  \n");

    rstQry = temp.toString();
 

    return rstQry;
  }

 
  
  /**
  * <PRE>
  * 현재(한국기준) 시간정보를 얻는다. <BR>
  * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 셋팅하면 1998121011과 같이 Return. <BR>
  * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이 <BR>
  *      0~23시간 타입으로 Return. <BR>
  *      String CurrentDate = BaseUtil.getCurrentTime("yyyyMMddHH"); <BR>
  * </PRE>
  * @param    	format    	얻고자하는 현재시간의 Type
  * @return   	String    	현재 한국 시간.
  */
  public static String getCurrentTime(String format) {
      int millisPerHour = 60 * 60 * 1000; // 1hour(ms) = 60s * 60m * 1000ms
      SimpleDateFormat fmt = new SimpleDateFormat(format);
      SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour,"KST");
      fmt.setTimeZone(timeZone);
      String str = fmt.format(new java.util.Date(System.currentTimeMillis()));

      return str;
  }  
  
    /**
     * String 객체들이 담겨진 ArrayList 를 받아서 첫 번째
     * element 를 String 타입으로 리턴한다. 
     * @param 	list  String 객체들이 담겨진 ArrayList
     * @return  	List 에서 첫번째 element 를 리턴한다. 
     * 첫번째 element 가 비어 있으면 null string 을 리턴한다
     * 
     */  
    public static String getFirstElement(List list) {
        if ( list == null || list.size() <= 0 ) {
            return "";
        }
        else {
            return (String)list.get(0);
        } 
    }  

    public static String[] getStringToken(String str, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        String[] strArray = new String[tokenizer.countTokens()];
        
        for ( int i=0; tokenizer.hasMoreTokens(); i++ ){
            strArray[i] = tokenizer.nextToken();
        }
        
        return strArray;  
    }
    
    /**
    * <PRE>
    * 파일을 복사한다.
    * </PRE>
    * @param    scrPath    	원본 파일명
    * @param    DestPath   	대상 파일명
    * @return   	none
    */
    public static void copyFile (String scrPath, String destPath) throws IOException {
        int blockSize = 32767;

        File file1 = new File (scrPath);
        File file2 = new File (destPath);
        
        if (file2.exists ()) {
            file2.delete ();
        }
        file2.createNewFile ();
        BufferedInputStream bis = new BufferedInputStream (new FileInputStream (file1));
        BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream (file2));
        
        byte[] buf = new byte[blockSize];

        int readLength = bis.read (buf, 0, blockSize);
        
        while (readLength  > 0) {
            bos.write (buf, 0, readLength);
            
            readLength = bis.read (buf, 0, blockSize);
        }
        
        bis.close ();
        bos.close ();
    }
    
    /**
    * <PRE>
    * 파일을 복사한다.(추가할 내용이 있을경우 사용)
    * </PRE>
    * @param    scrPath    	원본 파일명
    * @param    DestPath   	대상 파일명
    * @return   	none
    */
    public static void copyFileAddStr (String scrPath, String destPath, String addStr) throws Exception {
        int blockSize 						= 32767;
        BufferedInputStream bis 		= null;
        BufferedOutputStream bos 	= null;        
        try{
            File file1 = new File (scrPath);
            File file2 = new File (destPath);
                
            if (file2.exists ()) {
                file2.delete ();
            }
            file2.createNewFile ();
            bis = new BufferedInputStream (new FileInputStream (file1));
            bos = new BufferedOutputStream (new FileOutputStream (file2));
            byte[] buf = new byte[blockSize];
            byte bHeader[]  = addStr.getBytes();    
            int readLength = bis.read (buf, 0, blockSize);
            bos.write(bHeader);                     
            while (readLength  > 0) {
                bos.write (buf, 0, readLength);
                readLength = bis.read (buf, 0, blockSize);
            }
        }catch(Exception e){
           LogWrapper.biz.error("◁◀ [FileControl.copyFileAddStr] Error : "+Utils.getLogStackTrace(e)+" ▶▷");     
           throw new Exception("[copyFileAddStr Error]"+e.getMessage());
        }finally{
            if( bis != null){ 
                bis.close ();                
            }
            if(bos != null){
                bos.close ();                
            }
        }
    }    
    
    /**
    * <PRE>
    * 파일을 내용을 가져온다.
    * </PRE>
    * @param    scrPath    원본 파일명
    * @return   String
    */
    public static String  getFileContent(String scrPath ) throws Exception {
        int blockSize 					= 32767;
        BufferedInputStream bis 	= null;
        StringBuffer sBuff 			= new StringBuffer();     
        try{
            File file1 	= new File (scrPath);
            bis 			= new BufferedInputStream (new FileInputStream (file1));
            byte[] buf	= new byte[blockSize];
            int readLength = bis.read (buf, 0, blockSize);
            while (readLength  > 0) {
                sBuff.append(new String(buf));
                readLength = bis.read (buf, 0, blockSize);
            }
        }catch(Exception e){
           LogWrapper.biz.error("◁◀ [FileControl.getFileContent] Error : "+Utils.getLogStackTrace(e)+" ▶▷");     
           throw new Exception("[copyFileAddStr Error]"+e.getMessage());
        }finally{
            if( bis != null){ 
                bis.close ();                
            }
        }
        return sBuff.toString().trim();
    }  
    
    /**
     * 파일에 내용넣기
     * @author jangkc 
     * @param fullPath_fName
     * @param content
     * @throws Exception
     */
    public static void putContentToFile(String fullPath_fName,String content) throws Exception {
        File outFile64 = new File(fullPath_fName);
        BufferedOutputStream outs64 = new BufferedOutputStream(new FileOutputStream(outFile64));
        try {
            outs64.write(content.getBytes());
        } finally {
            outs64.close();
        }          
    }
 
 
    /**
     * 저장 Directory 를 만든다.
     * @param 	sFilePath
     * @throws 	Exception
     */
    public static void createDirectory(String sFilePath) throws Exception {
    
        /*****************************************************************************
        *  OS window인 경우도 적용되도록 경로를 변경한다.
        *****************************************************************************/
        /*----------------------------------------------------------------------------------------------
        separatorChar
        public static final char separatorChar
        시스템에 의존하는 디폴트의 이름 단락 문자입니다.
        이 필드는, 시스템 프롭퍼티 file.separator 의 값의 최초의 문자를 격납 하도록(듯이) 초기화됩니다.
        UNIX 시스템의 경우, 이 필드의 값은 '/', Win32 시스템의 경우는 '\' 입니다.
    
        ※ '\\' 는 '\'가 특수 문자이기 때문에 '\'가 하나더 붙었다.
        ----------------------------------------------------------------------------------------------*/
        char otherSeparator = '/';
        if ( File.separatorChar == '/' ) {
            otherSeparator = '\\';
        }
    
        sFilePath = sFilePath.replace(otherSeparator, File.separatorChar);
        /*****************************************************************************
        *  경로가 없으면 경로를 생성한다.
        *****************************************************************************/
        File createDir = new File(sFilePath);
        if ( !createDir.exists() ) {
            createDir.mkdirs();
        }
    }    
    
    /**
     * 중복되지않게 Seq파일명을 생성한다.
     * @author jangkc 
     * @param sDir
     * @param sFileName
     * @return
     * @throws Exception
     */
    public static String renameFile(String sDir,String sFileName) throws Exception{
        StringBuffer sbReturnFileName = new StringBuffer(sFileName);
        String sIndexNum 	= "";
        int    nIndexNum 	=  0;
    
        while(true) {
            /** 확장자가 존재하면 확장자 바로 앞에 순번을 2자리로 붙여준다. */
            if(sFileName.lastIndexOf(".") != -1) {
                sIndexNum = sFileName.substring(sFileName.lastIndexOf(".") - 2, sFileName.lastIndexOf("."));
                try{
                    nIndexNum   = Integer.parseInt(sIndexNum);
                    if(nIndexNum > 9) {
                        sIndexNum = Integer.toString(nIndexNum + 1);
                    } else {
                        sIndexNum = "0" + Integer.toString(nIndexNum + 1);
                    }
                    sbReturnFileName = (StringBuffer) sbReturnFileName.replace(sFileName.lastIndexOf(".") - 2, sFileName.lastIndexOf("."), sIndexNum);
                }catch(Exception e){
                    sIndexNum   = "00";
                    sbReturnFileName = (StringBuffer) sbReturnFileName.insert(sFileName.lastIndexOf("."), sIndexNum);
                }
            }
            /**  확장자가 존재 하지 안으면 마지막에 순번을 2자리로 붙여준다. */
            else {
                sIndexNum = sFileName.substring(sFileName.length() - 2);
                try{
                    nIndexNum = Integer.parseInt(sIndexNum);
                    if(nIndexNum > 9) {
                        sIndexNum = Integer.toString(nIndexNum + 1);
                    } else {
                        sIndexNum = "0" + Integer.toString(nIndexNum + 1);
                    }
                    sbReturnFileName = (StringBuffer) sbReturnFileName.replace(sFileName.length() - 2, sFileName.length(), sIndexNum);
                }catch(Exception e){
                    sIndexNum = "00";
                    sbReturnFileName = (StringBuffer) sbReturnFileName.insert(sFileName.length(),sIndexNum);
                }
            }
            /**  기존에 파일이 존재하지 않으면 loop를 빠진다. */
            File fSaveFile = new File(sDir + sbReturnFileName.toString());
            sFileName = sbReturnFileName.toString();
            if ( !fSaveFile.exists() ) {
                break;
            }
        }
       return sFileName;
    }    
    
    /** 
     * Date 타입을 String 으로 변환한다
     * 
     * @param date
     * @return  yyyy/MM/dd hh:mm:ss 포맷의 날짜를 리턴한다.
     */
    public static String getDateString(Date date) {
        return getDateString(date, "yyyy/MM/dd hh:mm:ss");
    }
    
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return "";
        }
        
        SimpleDateFormat formatter =  new SimpleDateFormat(format);

        return formatter.format(date);
    }
    
    public static String encodeParameters(HashMap hash) {
        String encoded;
        StringBuffer sbTemp 	= new StringBuffer ();
        
        Iterator iter 					= hash.keySet().iterator();
        
        while (iter.hasNext()) {
            String key 		= (String) iter.next();
            String value 	= (String) hash.get(key);
            
            sbTemp.append ("|" + key + "=" + value);
        }
         
        try {
            encoded = URLEncoder.encode(sbTemp.toString (), "euc-kr");
        } catch (UnsupportedEncodingException uee) {
            encoded = "";
        }
        
        return encoded;
    }
    
    /**
	 * 한글로 넘어온 파라메터는 이걸로 한번 감싸줘야 한다.
	 * encoding의 예 iso-2022-kr, ks_c_5601-1987 (동일한 인코딩인 KSC5601도 가능함)등 
	 */
	public static String decode(String input, String encoding) {
		try {
			if (input==null)
	      return null;
			else
	      return new String(input.getBytes("8859_1"),encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
			return "";
		}
	}

    public static HashMap decodeParameters(String encoded) {
        String decoded;
        HashMap hash = new HashMap ();
        
        try {
            decoded = URLDecoder.decode (encoded, "euc-kr");
        } catch (UnsupportedEncodingException uee) {
            decoded = "";
        }
        
        StringTokenizer tokenizer = new StringTokenizer (decoded, "|=");
        
        while (tokenizer.hasMoreTokens()) {
            String key 		= (String) tokenizer.nextToken();
            String value 	= (String) tokenizer.nextToken();
            
            hash.put (key, value);
        }
        

        return hash;
    }
         
    //= 자료 조회
    public static Collection getSelect(String sQuery, Connection con)
    {
        boolean bFirst						=	true;
        ArrayList   arrList					= 	new ArrayList();
        int     iColcnt   						= 	0;     
        int paramIndex 						= 	0;
        HashMap	map  					= 	null;
        PreparedStatement     pstmt 	= 	null; 
        ResultSet             rsMain   		= 	null; 
        ResultSetMetaData     rsmd 		= 	null;
        

        try {
            pstmt 		= con.prepareStatement(sQuery.toString());
            rsMain 		= pstmt.executeQuery(); 

            while(rsMain.next()) 
            {
                if (bFirst)
                {
                    rsmd   	= rsMain.getMetaData();
                    iColcnt 	= rsmd.getColumnCount();
                    bFirst 	= false; 
                }
                
                map = new HashMap(iColcnt);
                
                for(int i=0; i<iColcnt; i++){
                	 map.put(rsmd.getColumnName(i + 1).toUpperCase(), rsMain.getString(i + 1));
                }
                
                arrList.add(map);
        
            }
            if (arrList.size() == 0)
                arrList = null;

        } 
        catch(Exception e)  
        {  
            e.printStackTrace();

            arrList = null;         
        }
        
        return arrList;
    }
    
    /**
    * <PRE>
    * DAO Properties File에서 쿼리를 가져온다.<BR>
    * </PRE>
    * @param      webApp 			- Property Name 
    * @param	  key 				- 쿼리를 가지는 Property Key
    * @return       String        		- 쿼리
    */    
    public static String getSqlByProp(String webApp, String key) {
        Properties properties 	= new Properties();
        String sProp 				= "";
        String sSQL  				= "";
        
        if (webApp.equals("admin")){
        // 관리자	
            sSQL = ADMINSQL;
        }else if (webApp.equals("session")){
        // 세션관리	
            sSQL = SESSIONSQL;
        }else if (webApp.equals("cust")){
        // 고객지원	
            sSQL = CUSTSQL;
        }else if (webApp.equals("bill")){
        // 전자세금계산서 작성	
            sSQL = BILLSQL;
        }else if (webApp.equals("billissue")){
        // 전자세금계산서 조회
            sSQL = BILLISSUESQL;
        }else if (webApp.equals("comm")){
        // 공통처리	
            sSQL = COMMSQL;
        }else if(webApp.equals("auth")){
        	sSQL = AUTHSQL;
        // 마이페이지 
	    }else if(webApp.equals("mypage")){
	    	sSQL = MYPAGESQL;
        // 전자계약서 
	    }else if(webApp.equals("cont")){
	    	sSQL = CONTSQL;
	     // 전자계약서조회 
	    }else if(webApp.equals("contissue")){
	    	sSQL = CONTISSUESQL;
	    }else if(webApp.equals("egis")){
	    	sSQL = EGISSQL;
	    }
        
        try{
            InputStream istream = Utils.class.getClassLoader().getResourceAsStream(sSQL);
            properties.load(istream);
            istream.close();
          
            //sProp = new String(properties.getProperty(key).getBytes(CHARSET_8859_1),CHARSET_KSC5601);
            sProp = properties.getProperty(key);
        } catch (Exception e) {
            LogWrapper.biz.debug(getLogStackTrace(e));
        }

        return sProp;
    }
    
        
    
	/**
     * 환경파일값을 읽어온다.
     * @param fileNM : property파일명 
     * @param key    	: 환경변수명 
     * @return
     * @throws Exception
     */                    
    public static String getConfByProp(String fileNM, String key) throws Exception{
        Properties properties 	= new Properties();
        String sProp 				= null;

        try{
            InputStream istream = Utils.class.getClassLoader().getResourceAsStream(fileNM);
            properties.load(istream);
            istream.close();
          
            sProp = new String(properties.getProperty(key).getBytes(CHARSET_8859_1),CHARSET_KSC5601);
        } catch (Exception fnfEx) {
            throw new Exception(fnfEx);
        }

        return sProp;
    }
    
    /**************************************************************************
    *  Null값을 Default값으로 변경
    **************************************************************************/
    public static String nullToStr(String sString, String sDefault)
    {
        if ( sString == null || sString.equals("null") ) {
            return sDefault;
        }
        return sString;
    }

    public static String nullToStr(String sString)
    {
        return nullToStr(sString, "");
    }

    /**************************************************************************
    *  공백을 null로
    **************************************************************************/
    public static String spaceToNull(String sString)
    {
        String sReturn 	= null;
        sString 				= sString.trim();

        if ( !(sString == null || sString.equals("")) ) {
            sReturn = sString;
        }
        return sReturn;
    }

    /**************************************************************************
    *  문자열에서 숫자값만 return
    **************************************************************************/
    public static String getNumber(String sString)
    {
        return getNumber(sString, "");
    }

    public static String getNumber(String sString, String sAddString)
    {
        String sReturn 	= "";
        String sNumber 	= "0123456789" + sAddString;

        if ( sString == null ) { return sReturn; }

        for (int i = 0; i < sString.length(); i++ ) {
            if ( sNumber.indexOf(sString.charAt(i)) > -1 ) {
                sReturn = sReturn + sString.charAt(i);
            }
        }
        return sReturn;
    }

    /**
     * 소수점 포함 숫자
     * @param sString
     * @param sAddString
     * @return
     */
    public static String getNumberExp(String sString, String sAddString)
    {
        String sReturn 	= "";
        String sNumber 	= "0123456789." + sAddString;

        if ( sString == null ) { return sReturn; }

        for (int i = 0; i < sString.length(); i++ ) {
            if ( sNumber.indexOf(sString.charAt(i)) > -1 ) {
                sReturn = sReturn + sString.charAt(i);
            }
        }
        return sReturn;
    }

    public static boolean getChkNumber(String sString)
    {
        boolean sReturn 	= false;
        String sNumber 		= "0123456789";

        if ( sString == null ) { return sReturn; }

        for (int i = 0; i < sString.length(); i++ ) {
            if ( sNumber.indexOf(sString.charAt(i)) == -1 ) {
                return sReturn;
            }else{
            	
            	sReturn = true;
            }
        }
        return sReturn;
    }


    /**************************************************************************
    *  숫자에 세자리마다 쉼표를 찍는다.
    **************************************************************************/
    public static String getNumFormat(double dNumber)
    {
        NumberFormat  numFormat = NumberFormat.getCurrencyInstance();
        DecimalFormat decFormat 	= (DecimalFormat) numFormat;

        String pattern 						= "";

        if ( ( dNumber % 1 ) == 0 ) {
            pattern = "###,###,###,##0";
        } else {
            pattern = "###,###,###,##0.##";
        }

        decFormat.applyPattern(pattern);
        return decFormat.format(dNumber);
    }

    public static String getNumFormat(String sString)
    {
        try
        {
            double dNumber = Double.parseDouble(sString);
            return getNumFormat(dNumber);
        }
        catch (NumberFormatException ex)
        {
            return "0";
        }
    }

    public static String getNumFormat(long nNumber)
    {
        double dNumber = new Long(nNumber).doubleValue();
        return getNumFormat(dNumber);
    }

    public static String getFloatFormat(String sString)
    {
        try
        {
            double dNumber 				= Double.parseDouble(sString);

            NumberFormat  numFormat = NumberFormat.getCurrencyInstance();
            DecimalFormat decFormat 	= (DecimalFormat) numFormat;

            String pattern 						= "";
            pattern 								= "###,###,###,##0.##";

            decFormat.applyPattern(pattern);
            return decFormat.format(dNumber);
        }
        catch (NumberFormatException ex)
        {
            return "0.00";
        }
    }


    /*****************************************************************************
    *  현재 날짜를 리턴한다.
    *****************************************************************************/
    public static String getCurrentDate(String sDateFormat) {

        java.util.Date   dateCurrent   			= new java.util.Date();
        SimpleDateFormat simDateFormat 	= new SimpleDateFormat(sDateFormat);

        return simDateFormat.format(dateCurrent);
    }

    public static String getCurrentDate() {
        return getCurrentDate(sDateFormat);
    }

    public static String getCurrentDateTime() {
        return getCurrentDate(sDateTimeFormat);
    }


    /*****************************************************************************
    *  현재날짜에서 iDay 맡큼 빼거나 더한 날짜 을 반환
    *****************************************************************************/
    public static String getAddDate(int nDay)
    {
        Calendar cToday = Calendar.getInstance();
        cToday.add(Calendar.DAY_OF_MONTH, nDay);

        String sYear    	=   Integer.toString(cToday.get(Calendar.YEAR));

        String sMon     	=   Integer.toString(cToday.get(Calendar.MONTH)+1);
        if (sMon.length() == 1) {
            sMon = "0" + sMon;
        }

        String sDay     	=   Integer.toString(cToday.get(Calendar.DAY_OF_MONTH));
        if (sDay.length() == 1) {
            sDay = "0" + sDay;
        }

        return getDateFormat(sYear+sMon+sDay);
    }
    
    //-----------------------------------------------------
    // 가감된 날짜 yyyyMMddHHmm 형식으로 얻기
    //  @param  field   'Y'(년),'M'(월),'D'(일),'h', 시간.
    //  @param  amount  가감량
    //-----------------------------------------------------
    public static String getAddedDate(char field, int amount)
    {
        SimpleDateFormat simpleDate 	= new SimpleDateFormat("yyyyMMddHHmm");
        Calendar cal 							= Calendar.getInstance();

        switch( field ) {
            case 'D':
                cal.add(Calendar.DATE, amount);
                break;
            case 'Y':
                cal.add(Calendar.YEAR, amount);
                break;
            case 'M':
                cal.add(Calendar.MONTH, amount);
                break;
            case 'h':
                cal.add(Calendar.HOUR, amount  );
                break;
            default : break;
        }
        return simpleDate.format(cal.getTime());
    }
    
    /*****************************************************************************
    *  날짜형식
    *****************************************************************************/
    public static String getDateFormat(String sDate) {
        String sReturn = "";

        if ( sDate == null) {
            return "";
        }

        sDate = getNumber(sDate);

        switch (sDate.length())
        {
            case 6 :
                sReturn = sDate.substring( 0,  4) + sDateSeparator + sDate.substring( 4    ) + sDateSeparator;
                break;
            case 8 :
                sReturn = sDate.substring( 0,  4) + sDateSeparator + sDate.substring( 4,  6) + sDateSeparator + sDate.substring( 6    );
                break;
            case 12 :
                sReturn = sDate.substring( 0,  4) + sDateSeparator +
                          sDate.substring( 4,  6) + sDateSeparator +
                          sDate.substring( 6,  8) + " " +
                          sDate.substring( 8, 10) + ":" +
                          sDate.substring(10    );
                break;
            case 14 :
                sReturn = sDate.substring( 0,  4) + sDateSeparator +
                          sDate.substring( 4,  6) + sDateSeparator +
                          sDate.substring( 6,  8) + " " +
                          sDate.substring( 8, 10) + ":" +
                          sDate.substring(10, 12) + ":" +
                          sDate.substring(12    );
                break;
            default :
                sReturn = sDate;
                break;
        }

        return sReturn;
    }


    /*****************************************************************************
    *  날짜형식
    *****************************************************************************/
    public static String getDateTimeFormat(String sDateTime) {
        return getDateFormat(sDateTime);
    }


    /**************************************************************************
    *  월의 마지막 날짜를 계산한다.
    **************************************************************************/
    public static String getLastDay(String sDate)
    {
        String[] lastday = {"31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};

        int nYear  		= 0;
        int nMonth 	= 0;

        nYear  			= Integer.parseInt(sDate.substring(0, 4));
        nMonth 			= Integer.parseInt(sDate.substring(4, 6));

        if((nYear%4 == 0) && (nYear%100 != 0) || (nYear%400 == 0)) {
            lastday[1] = "29";
        }

        return lastday[nMonth - 1];
    }


    /**************************************************************************
    *  ArrayList에 null값 설정
    **************************************************************************/
    public static ArrayList setArrayListNull(ArrayList alList, int Cnt) {
        for ( int i = 0; i < Cnt; i++ ) {
            alList.add(null);
        }

        return alList;
    }

    
    /**
     * 사업자등록번호  형식 ex)123-11-00221
     * <p>
     * @param    strDate   문자열
     * @return   변환된    문자열
     * @author   jkc
     */
    public static String getCompanyRegNum(String sTmpStr)
    {
       if (sTmpStr.length() < 10 ){
           return sTmpStr;
       }else {
           return sTmpStr.substring(0,3) + "-" + sTmpStr.substring(3,5) +"-"+sTmpStr.substring( 5 ) ;
       }
    }

    /**
     * 날짜타입으로 변환한다. (예) 20010101 ==> 2001-01-01
     * <p>
     * @param    strDate  날짜문자열
     * @return   변환된 날짜 문자열
     * @author   jkc
     */
     public static String makeDateType(String strDate){
         String sDate;

         if (strDate == null || strDate.length() == 0) {
             sDate = "";
         } else {
             if (strDate.length() == 8 ) {
                 String year  	= strDate.substring(0,4);
                 String month 	= strDate.substring(4,6);
                 String date  	= strDate.substring(6,8);

                 sDate = year + "-" + month + "-" + date;
             } else if(strDate.length() == 6 ) {
                 String year  	= strDate.substring(0,4);
                 String month 	= strDate.substring(4,6);

                 sDate = year + "-" + month;
             } else {
                 sDate = "";
             }
         }

         return sDate;
     }

    /**
     * src가 null거나 ""이면 tgt값으로 치환하여 return한다.
     * <p>
     * @param   String 입력받은 String
     * @return   	치환된 String
     * @author   jkc
     */
    public static String nvl(String src, String tgt)
    {
      String res;

      if (src == null) {
          res = tgt;
      }
      else if (src.equals("")) {
          res = tgt;
      }
      else {
          res = src.trim();
      }

      if (res == null) {
          res = "";
      }

      return res;
    }
    
    /**
     * 문자열을 델리미터 단위로 끊어서   배열로 만들어 줌
     * <p>
     * @param  	String source , String delimeter
     * @return   	String[]
     * @author   jkc
     */
    public static String[] split(String source, String delimeter){
        ArrayList list 	= new ArrayList(10);
        int i 				= 0;
        int j 				= source.indexOf(delimeter);

        while( j >= 0 ) {
            list.add(source.substring(i,j));
            i = j + delimeter.length();
            j = source.indexOf(delimeter,i);
        }
        if( !"".equals(source.substring(i)) ) {
            list.add( source.substring(i) );
        }
        return (String [])list.toArray(new String[0]);
      }

    
    /**
     * 문자열을 델리미터 단위로 끊어서   Vector로 만들어 줌
     * <p>
     * @param    	String source , String delimeter
     * @return   		Vector
     * @author   	jkc
     */
    public static Vector spdSplit(String str1, String str2){
      StringBuffer splitedStr 	= new StringBuffer();
      String newStr 				= (str1 == null)? "" : str1;
      Vector vtr 					= new Vector();

      while (newStr.length()>0) {
	        if (newStr.indexOf(str2)>=0) { //구분자가 있다면
		          int ord 	= newStr.indexOf(str2); //구분자의 위치
		
		          splitedStr.append(newStr.substring(0,ord)); //구분자 앞까지 잘라냄
		          vtr.addElement(splitedStr.toString());
		          newStr 	= newStr.substring(ord+1);
		          splitedStr.delete(0,splitedStr.length());
	        
	        } else {
	            vtr.addElement(newStr);
	          break;
	        }
      }

      return vtr;
    }
    
    /**
     * 숫자형을 '###,###,###,###,###' 형식으로 바꿈
     * <p>
     * @param  	String   str      값
     * @return   	String   형식에 맞게 변환된결과, 결과가 "0"일경우 "" 반환
     * @author   jkc
     */
    public static String numberFormat (double num, String pattern) {
        DecimalFormat dFormatter 	= new DecimalFormat (pattern);
        NumberFormat nFormatter 	= NumberFormat.getInstance ();

        String formatted;

        if (pattern.length () > 0) {
            formatted = dFormatter.format (num);
        } else {
            formatted = nFormatter.format (num);
        }

        return formatted;
    }
 


    /**
     * 콘솔에 로그 찍기
     * @param message		로그메세지
     * @return
     */
    public static void setPrintln(String message){
        System.out.println("[" + getCurrentDateTime() + "]" + message);
    }

    /**
     * 콘솔에 로그 찍기
     * @param message		로그메세지
     * @return
     */
    public static void setTimePrintln(String message){
        System.out.println("[" + getCurrentDateTime() + "]" + message);
    }
    
    /**
     * 금액 한글로 표기
     * @param sContractAmt
     * @return
     */
    public static String getKorPrice(String sContractAmt) {
        String[] units 		= {"십",  "백",    "천",    "만",    "십",    "백",    "천",    "억",    "십",    "백",    "천",    "조",    "십",    "백",    "천" };
        String[] numStr 	= { "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
        LogWrapper.biz.debug("◁◀ [Utils.getKorPrice] "+sContractAmt+" ▶▷");                
        
        if (sContractAmt == null){return "";}
        if(sContractAmt.trim().length()== 0 ){return "";}
        
        sContractAmt 	= getNumber(sContractAmt);
        int numSize 		= sContractAmt.length();
        
        if ( sContractAmt.length() == 0 ){return sContractAmt;}                
        String amtStr 		= "";
        
        for (int i = 0; i < numSize; i++) {
            int aa 			= sContractAmt.charAt(i) - '0';

            if(aa == 0){
               if((numSize -2 - i)%4 == 3){
                  if( !amtStr.substring(amtStr.trim().length()-1,amtStr.trim().length()).equals("억")){                         
                    amtStr 	+= units[numSize -2 - i];
                  }
               }
                 continue;
             }
            
            amtStr 			+= numStr[aa - 1];
            if( !amtStr.substring(amtStr.trim().length()-1,amtStr.trim().length() ).equals("억")){     
                if(i <= numSize-2){
                   amtStr 	+= units[numSize -2 - i];
                }
            }                    
 
            if (i-1 != numSize) {
                amtStr += " ";
            }
        }
        return amtStr;
        
    }
    
    /**
    * <PRE>
    * Message Properties File에서 코드값에 해당하는 메세지를 가져온다..<BR>
    * </PRE>
    * @param    key - 메세지를 가져오는  Property Key
    * @return   	String   메세지.
    */    
    /*public static String getCDMessage(String key) throws Exception{
        Properties properties 	= new Properties();
        String sMessage 		= "";
        String sMess  			= MESSAGE;

        try{
            InputStream istream	= Utils.class.getClassLoader().getResourceAsStream(sMess);
            properties.load(istream);
            istream.close();
            
            sMessage 				= new String(properties.getProperty(key).getBytes(CHARSET_8859_1),CHARSET_KSC5601);
        } catch (Exception fnfEx) {
        	
            throw new Exception("존재하는 메세지가 없습니다.");
        }
        return sMessage;
    }   */
    
    /**
    * <code>HttpInData</code> instance를 생성해 가져온다.
    * <p>
    * @param       	req    HttpServletRequest.
    * @return      	Clinet로 부터 입력된 parameter를 저장하고 관리하는 instance.
     * @throws 		Exception
    */
    /*public static HttpInData getHttpInData(HttpServletRequest req, MultipartRequest multi) throws Exception {
        HttpInData inData 			= 	new HttpInData();
        String key 						= 	"";
        String[] values 				= 	null;
		
		
            for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
	            key 				= (String) e.nextElement();
	            values 			= req.getParameterValues(key);
	         	inData.put(key, values);
	         	LogWrapper.biz.debug("☏☎ Request Parameter [" + key + "] : " + inData.get(key));
            }
        
	        if(multi != null){
	            for (Enumeration e = multi.getParameterNames(); e.hasMoreElements();) {
		            key 		= (String) e.nextElement();
		            values 	= multi.getParameterValues(key);
		            inData.put(key, values);
		         	LogWrapper.biz.debug("☏☎ MultiPartRequest Parameter [" + key + "] : " + inData.get(key));
		        }
	        }    
		
	        return inData;
    }     */
    
    /**
     * <code>HttpInData</code> instance를 생성해 가져온다.
     * <p>
     * 	@param       req    HttpServletRequest.
     * 	@return      	Clinet로 부터 입력된 parameter를 저장하고 관리하는 instance.
      * @throws 		Exception
     */
     /*public static HttpInData getHttpInData(HttpServletRequest req) throws Exception {
         HttpInData inData 			= 	new HttpInData();
         String key 					= 	"";
         String[] values 				= 	null;
 		
 		
         for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            key 				= (String) e.nextElement();
            values 			= req.getParameterValues(key);
         	inData.put(key, values);
         	LogWrapper.biz.debug("☏☎ Request Parameter [" + key + "] : " + inData.get(key));
         }
         
 	        
         return inData;
     }     */
     
    
    /**
     * 에러메세지 만들기.
     * @param exp
     * @return
     */
    public static String getLogStackTrace(Exception exp){
        StackTraceElement[] ste 	= exp.getStackTrace();
        StringBuffer errstr 				= new StringBuffer();         
        try{
            errstr.append("Exception : " + exp.getMessage() + "\n");
            
            for(int i=0 ; i < ste.length ; i++){
                StackTraceElement lastste = ste[i];
                String file       		= lastste.getFileName();
                String method     	= lastste.getMethodName();
                String linenumber 	= lastste.getLineNumber() + "";
                String objname    	= lastste.getClassName();
                errstr.append("	파일명 : "+file + " : " + objname + "." + method + "(" + linenumber + ")\n");
            } 
            
        }catch(Exception e1){}
        return errstr.toString();
    }     
    
    
    /**
     * <PRE>
     * 문자열에 SPACE를 추가한다.
     * </PRE>
     * @param str
     * @return  String
     */
    public static String addSpace(String str, int width) throws Exception{
        StringBuffer sBuff 	= 	new StringBuffer();
        String reStr      		=   "";
        reStr               		=   remove(str," ");
        
        for (int i=0; i < reStr.length();  i++) 
        {  
                sBuff.append(reStr.charAt(i)) ; 
            for(int k = 0 ; k < width; k++){
                sBuff.append("&nbsp;");
            }
        }       
        return sBuff.toString();
    }
    
    /**
     * <PRE>
     * 문자열에 SPACE를 추가한다.
     * </PRE>
     * @param str
     * @return  String
     */
    public static String addSpace(String str) throws Exception{
        StringBuffer sBuff 	= new StringBuffer();
        String reStr      		=   "";
        reStr               		=   remove(str," ");
        
        for (int i=0; i < reStr.length();  i++) 
        {  
            sBuff.append(reStr.charAt(i)) ; 
            sBuff.append(" ");
            
        }       
        return sBuff.toString();
    }
    /**
     * <PRE>
     * 필드명에 복수개의 필드값을 추가한다.
     * </PRE>
     * @param args
     * @author JKC
     */
    public static String addSql(String fieldnm,String joinboolean ,String[] aField) throws Exception{
        StringBuffer sBuffer = new StringBuffer();
        
        if(aField.length>0){
            sBuffer.append("(");
        }

        for(int i=0;i<aField.length;i++){
            sBuffer.append(fieldnm+" = "+aField[i]);            
            if(i < aField.length-1){
                sBuffer.append(" "+joinboolean+" ");
            }
        }
        
        if(aField.length>0){        
            sBuffer.append(")");  
        }                  
        
        return sBuffer.toString();
    }
    
    /**
    * 해당 문자열의 특수문자를  변환해준다.
    *
    * @param  str     	변환하려는 원본 문자열
    * @return result  	변환된 문자열
    * @author jkc
    */
    public  static String chkSpecificChar(String str) throws Exception
    {       
        String result 	= "";
        
        if(str != null) 
        {
            str = str.trim();
            str = replace(str,"&","&amp;");
            str = replace(str,"<","&lt;");
            str = replace(str,">","&gt;");
            str = replace(str,"'"  , "&apos;");
            str = replace(str, "\"", "");
            result = str;
        }
        return result;
    }
    
    /**
    * 해당 문자열의 특수문자를  변환해준다.
    *
    * @param  	str     	변환하려는 원본 문자열
    * @return 	result  	변환된 문자열
    * @author 	jkc
    */
    public  static String makeContentStr(String str) throws Exception
    {       
        String result 	= "";
        
        if(str != null) 
        {
            str = str.trim();
            str = replace(str,"\r\n","<br>");
            //str = replace(str,"\n","<br>");
            str = replace(str," ","&nbsp;");
            result = str;
        }
        return result;
    }
    
    /**
     * 내용보기에서 <BR>태그와 Space 처리.
     *
     * @param  	strOld   	변환하려는 원본 문자열
     * @return 	result  	변환된 문자열
     */
	public static String convStr(String strOld) {
		String strNew = "";
		strNew 			= replace(replace(strOld, "\n", "<BR>"), "  ", "&nbsp;");
		
		return strNew;
	}
    
	/**
     * 특정일(yyyyMMdd) 에서 주어진 일자만큼 더한 날짜를 계산한다..
     *
     * @param  	date   	기준날짜
     * @param	rday		더할려는 일자
     * @return 	result  	변환된 문자열
     */
	public static String getAddDate(String date, int rday) throws Exception {
         if (date == null) return null;
    
         if (date.length() < 8 ) return ""; 					// 최소 8 자리
    
         String time = "";
    
         try {
             TimeZone kst 		= TimeZone.getTimeZone("JST");
             TimeZone.setDefault(kst);
    
             Calendar cal 		= Calendar.getInstance(kst); // 현재
    
             int yyyy   			= Integer.parseInt(date.substring(0,4));
             int mm     			= Integer.parseInt(date.substring(4,6));
             int dd       			= Integer.parseInt(date.substring(6,8));
    
             cal.set(yyyy,mm-1,dd);   // 카렌더를 주어진 date 로 세팅하고
             cal.add (Calendar.DATE, rday); // 그 날자에서 주어진 rday 만큼 더한다.
    
             String strYear    	= Integer.toString(cal.get(Calendar.YEAR)); // 년도
             String strMonth  	= Integer.toString(cal.get(Calendar.MONTH) + 1);   //  월
             String strDay      	= Integer.toString(cal.get(Calendar.DAY_OF_MONTH));  //  일
    
             if(strMonth.length() < 2) 
                 strMonth   = "0" + strMonth;
             if(strDay.length() < 2) 
                 strDay      = "0" + strDay;
    
             time = strYear + strMonth + strDay;
    
         } catch (Exception ex) {
             throw new Exception("Utils.getAddDate(\""+date+"\","+rday+")\r\n"+ex.getMessage());
         }
        
        return time;
    
    }
    
    /**
     * Menu ID를 Return하는 함수
     * @param req
     * @return
     */
    /*
	public static String getMenuId(HttpServletRequest req)
    {
        String   sTemp      = "";
        String   sRtn_value = "";
        
        try
        {
            sTemp      =  req.getRequestURI();
            sTemp      =  sTemp.substring(sTemp.lastIndexOf("/")+1);

            StringTokenizer st = new StringTokenizer(sTemp,".");
            if (st.hasMoreTokens())
                sRtn_value = st.nextToken().trim().substring(0,8);
                
            	if(sRtn_value.indexOf("bill") > -1){
                    sRtn_value  =   "bill";
                }else if(sRtn_value.indexOf("admin") > -1){
                    sRtn_value  =   "admin";
                }else if(sRtn_value.indexOf("download") > -1){
                    sRtn_value  =   "download";
                }else if(sRtn_value.indexOf("login") > -1){
                    sRtn_value  =   "login";
                }else if(sRtn_value.indexOf("noti") > -1){
                    sRtn_value  =   "notice";
                }else if(sRtn_value.indexOf("auth") > -1){
                    sRtn_value  =   "auth";
                }
               
        }
        catch (Exception e)
        {
            LogWrapper.biz.debug("[Utils.getMenuId() method 오류] : " + e.toString());
            sRtn_value = "";
        }

        return sRtn_value;

    }
    */
    
//	/**
//     * Menu ID를 Return하는 함수
//     * @param req
//     * @return
//     */
//    public static String getMenuId(HttpServletRequest req)
//    {
//        //String   sTblType    	= 	"";   
//        String   sTemp      		= 	"";
//        String sRtn_value[]		=	null;
//        String sReturn				=	"";
//        try
//        {
//            /*
//            sTblType      			=  req.getParameter("tbltype");
//            sRtn_value				=	sTblType;
//            */
//        	sRtn_value      		=  split(req.getRequestURI(),"/");
//        	
//        	sReturn					=	sRtn_value[sRtn_value.length-2];
//            LogWrapper.biz.debug("[menuID] >>> " + sReturn) ;
//          
//        }
//        catch (Exception e)
//        {
//            LogWrapper.biz.debug("[Utils.getMenuId() method 오류] : " + e.toString());
//            sReturn = "";
//        }
//
//        return sReturn;
//
//    }
//    
//    /**
//     * 파일명 Return하는 함수
//     * @param req
//     * @return
//     */
//    public static String getMenuFileNm(HttpServletRequest req)
//    {
//        String   sTemp      		= "";
//        String   sRtn_value 		= "";
//        
//        try
//        {
//        	if(req != null){   
//	            sTemp      		=  req.getRequestURI();
//	            sTemp     	 		=  sTemp.substring(sTemp.lastIndexOf("/")+1);
//	            
//	            LogWrapper.biz.debug("[menuFileNm 파일명] >>> " + sTemp);	
//	            StringTokenizer st = new StringTokenizer(sTemp,".");
//	            
//	            if (st.hasMoreTokens())
//	                sRtn_value = st.nextToken().trim(); 
//        	}
//        }   
//        catch (Exception e)
//        {
//            LogWrapper.biz.debug("[Utils.getMenuFileNm() method 오류] : " + e.toString());
//            sRtn_value = "";
//        }
//
//        return sRtn_value;
//
//    }          
    
    /**   
     * Parameter의 값
     *
     * @param  	req     	request 넘어오는 파라미터
     * @param	multi		MultipartRequest 파라미터
     * @return 	result  	변환된 파라미터
     */
    /*public String getParm(HttpServletRequest req, MultipartRequest multi, String sParmName) 
    {
          String sTemp 	= "";
          String sRtn  		= "";
          try
          {
              sTemp = req.getParameter(sParmName);          
           	  LogWrapper.biz.debug("▷Request Parameter [" + sParmName + "] : " + sTemp);
              
           	  if (sTemp == null || sTemp.equals("")){
                 sTemp = multi.getParameter(sParmName);          
              	 LogWrapper.biz.debug("▶MultiPartRequest Parameter [" + sParmName + "] : " + sTemp);
              }	 	
              if (sTemp == null || sTemp.equals(""))
                  return "";
               else
                  return Utils.nvl(Utils.replace(sTemp,"'","''"), "");
          }
          catch (Exception e)
          {
              return "";
          }
    }*/
    

    

	// 문자열을 x-www-form-urlencoded 포맷으로 변환한다.
	public static String URLEncode(String s) throws Exception {
		if (s == null) return null;

		String result = null;

		try {
			result = URLEncoder.encode(s, "KSC5601");
		} catch (Exception ex) {
			throw new Exception("Utils.URLEncode(\""+s+"\")\r\n"+ex.getMessage());
		}
		return result;
	}


	/**
	* <PRE>
	* 파일을 삭제한다. <BR>
	* </PRE>
	* @param  	sPathName     String으로 파일경로명.
	* @return 	파일삭제성공 boolean.
	*/
    public static boolean fFileDel(String sPathName)
    {
        String[] fileList  = null;
        File fNewFile     = new File(sPathName);
    
        if (!fNewFile.exists()) 
        {
            LogWrapper.biz.debug("Not Exists " + sPathName);
            return false;
    
        }
    
        //= 파일명이 넘어 왔을 경우는 파일만 삭제하고 Return시킨다.  
        if (fNewFile.isFile()) 
        {
            if (fNewFile.delete())
            {
                return true;
            }
            else
            {
                LogWrapper.biz.debug("File 삭제 ERROR : " + sPathName);
                return false;
            }
    
        }
    
        //= 폴더명이 넘어 왔을 경우는 폴더 아래 파일을 삭제하고 폴더도 삭제한다.
        //= 단... 여기서는 서브폴더가 없는 전재하에서 작동한다.
        if (!fNewFile.isDirectory()) 
        {
                LogWrapper.biz.debug("File도 아니고 Folder도 아님 : " + sPathName);
                return false;
        }
    
            
        //= 폴더 인경우 폴더안에 있는 파일리스트를 구해 삭제한다.
        fileList = fNewFile.list();
    
        for (int i = 0 ; i < fileList.length ; i ++ )
        {
    
            File fFile = new File(sPathName + sSeparatorChar +  fileList[i]);
    
            if (!fFile.delete())
            {
                LogWrapper.biz.debug("Folder내 File 삭제 ERROR : " + sPathName + sSeparatorChar +  fileList[i]);
            }
        }
    
    
        //= 마지막으로 폴더를 삭제한다. 만약 윗부분에서 파일삭제시 에러가 난경우 폴더는 삭제가 되지 않는다.
        if (fNewFile.delete())
        {
            return true;
        }
        else
        {
            LogWrapper.biz.debug("Last Folder 삭제 ERROR : " + sPathName);
            return false;
        }
    }
    
    
    /*****************************************************************************
    고정소수점처리 함수
    num    	: 원값                                                     
    index  	: 반올림할 자리( 자리수 : -3 -2 -1 . 1 2 3 )
   *****************************************************************************/
   public static double formatRound(double num, double index){
	   	try {
	         double pos 		= Math.pow(10, index);
	         return Math.round(num*pos) / pos ;
	         
	   	} catch (Exception e) {
		    	return num;
	    }  
   }
    
    public static String getErrNum(String ErrMessage)
	{
		String ErrNum 	= null;
		String stkEx 		= null;
		try
		{
			if (!ErrMessage.equals("OK"))
			{
				ErrNum = "9999"; //SQL 에러가 났을경우
				
			}
			else
			{
				ErrNum = "0000"; //에러없이 성공했을경우 
			}
		}
		catch(Exception e)
		{
			LogWrapper.biz.debug("Utils.getErrNum Exception : " + e.toString());	
		}	
		return ErrNum;
	}
    
    public static String checkNull(String s) {
		if (s == null || s.equals("null") || s.equals("") || s.equals(" ")) {
			return "";
		} else {
			return s.trim();
		}
	}
    
    
    //  title을 일정 t길이만큼 잘라주고 t_val로 값을 대치함
	public static String mCutTitle(String title, int t, String t_val){ 
		try {
			String newTitle2 	= mtitleCut(title, t, t_val);
			int LenChar 		= mCharLen(newTitle2,1);
			int LenCharH 	= LenChar%2;
			
			if ( LenCharH == 1 ) {
				String cutTitle	= mtitleCut(title, t-1, t_val);
				newTitle2 		= cutTitle;
			}
			return newTitle2; 
		} catch(Exception e){return title;} 
	} 
	
//  title을 일정 t길이만큼 잘라주고 t_val로 값을 대치함
	public static String mCutTitleH(String title, int t, String t_val){ 
		try {
			String newTitle2 	= mtitleCut(title, t, t_val);
			int LenChar 		= mCharLen(newTitle2,0);
			int LenCharH 	= LenChar%2;
			
			if ( LenCharH == 1 ) {
				String cutTitle	= mtitleCut(title, t-1, t_val);
				newTitle2 		= cutTitle;
			}
			return newTitle2; 
		} catch(Exception e){return title;} 
	} 

	private static String mtitleCut(String title, int t, String t_val){ 
		//int LenChar = mCharLen(title,0);
		
		if( title.length() > t ) {   
			StringBuffer newTitle = new StringBuffer(title.substring(0,t)); 
			newTitle.append(t_val); 
			String cutTitle = newTitle.toString(); 
			return cutTitle; 
		} 
		return title; 
	} 
	
	public static String titleCut(String title, int t){
		if(title== null)	return "";

		//int LenChar = CharLen(title,0);

		if( title.length() >= t ) {
			StringBuffer newTitle 	= new StringBuffer(title.substring(0,t));
			newTitle.append("...");
			String cutTitle 			= newTitle.toString();
			
			return cutTitle;
		}
		return title;
	}
	public static String cutTitle(String title, int t){
		if(title== null)
		        return "";

		if( title.length() >= t ) {
			return title.substring(0, t)+"...";
		}
		return title;
	}

	private static int mCharLen(String str, int Han){  
		int strlen 			= 0; 
		int strlenH 			= 0; 
		//int strlenHsum = 0;
		int gubun 			= 0;
		
		gubun 				= Han;
		
		for(int j = 0; j < str.length(); j++){  
			char c 			= str.charAt(j);  
			
			if((c >= 0x20)&&(c <= 0x7f)) { 
				strlen++;  
			} else {    
				j++;
				strlen		+=2;  
				strlenH 	= strlenH +1 ; 				
			}  
		}

		if ( Han == 0 )
			return strlen;
		else
			return strlenH;	
	}        


	/**
	 * line의 문자중 oldString을 newString로 바꿔준다.
	 */ 
	public static String mReplace(String line,String oldString,String newString) 
	{
        if(line == null)
            return "";
        
		int index 			= 	0;
		while ((index = line.indexOf(oldString, index)) >= 0) {
			line 		= 	line.substring(0,index) + newString + line.substring(index + oldString.length());
			index 	+= newString.length();
		}
		
		return line;
	}

	/**
	 * 특수문자 처리 브라우저에 값을 Display시 사용함
	 */ 
 	public static String mHTML2SpecialChars(String source)
	{
		if (source == null)
			return "";
		else
			source = source.trim();
			
		char cdoublequot 		= '"';
		Character doublequot 	= new Character(cdoublequot);
		source = mReplace(source, "&", "&amp;");
		source = mReplace(source, doublequot.toString(), "&quot;");
		source = mReplace(source, "<", "&lt;");
		source = mReplace(source, ">", "&gt;");
		source = mReplace(source, "'", "&#39;");
		source = mReplace(source, "&nbsp;", "&amp;nbsp;");
		
		return source;
	}

	public static String mSpecialChars2HTML(String source)
	{
		if (source == null)
			return "";
		else
			source = source.trim();
			
		char cdoublequot 		= '"';
		Character doublequot 	= new Character(cdoublequot);
		source = mReplace(source, "&amp;", "&");
		source = mReplace(source, "&quot;", doublequot.toString());
		source = mReplace(source, "&lt;", "<");
		source = mReplace(source, "&gt;", ">");
		source = mReplace(source, "&#39;", "'");
		return source;
	}
		
	/**
	 *	엔터키를 쳤던것은 <br>태그로 치환
	 */
	public static String mReplaceBRTag(String inStr)
	{
		int length = inStr.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++){
			String tmp = inStr.substring(i, i+1);
			if("\n".compareTo(tmp) == 0) buffer.append("<br>&nbsp;");				
/*			if(" ".compareTo(tmp) == 0)
				buffer.append("&nbsp;");
			else	
*/
				buffer.append(tmp);					
		}
		return buffer.toString();
	}


	// 비밀번호 암호화 하기
	public static String md5Digest(String input) {
		try {
			MessageDigest md 			= MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			BASE64Encoder base64 	= new BASE64Encoder();
			
			return base64.encode(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getCurrDateString(int radix, String delim) {
		Calendar curr_time = Calendar.getInstance();
	
		String year = String.valueOf(curr_time.get(Calendar.YEAR));
	
		String month = String.valueOf(curr_time.get(Calendar.MONTH)+1);
		if(month.length() == 1)
			month = "0" + month;
		String day = String.valueOf(curr_time.get(Calendar.DATE));
		if(day.length() == 1)
			day = "0" + day;
		String hour = String.valueOf(curr_time.get(Calendar.HOUR));
		if(hour.length() == 1)
			hour = "0" + hour;
		String min = String.valueOf(curr_time.get(Calendar.MINUTE));
		if(min.length() == 1)
			min = "0" + min;
		String sec = String.valueOf(curr_time.get(Calendar.SECOND));
		if(sec.length() == 1)
			sec = "0" + sec;
		String msec = String.valueOf(curr_time.get(Calendar.SECOND));
		if(sec.length() < 3) {
			msec = "000" + msec;
			msec = msec.substring((msec.length() - 3));
		}

		if(radix == YYYYMMDD)
			return year + month + day;
		else if(radix == YYYYMMDDHHMMSS)
			return year + month + day + hour + min + sec;
		else if(radix == YYYYMMDDHHMMSSMI)
			return year + month + day + hour + min + sec + msec;
		else if(radix == SYYYYMMDD)
	          return year + delim + month + delim + day;
		else if(radix == SYYYYMM)
	          return year + delim + month;
		else if(radix == SYYYYMMDDHHMMSS)
			return year + delim +  month + delim + day + " " + hour + ":" +  min + ":" + sec;
		else if(radix == SYYYYMMDDHHMMSSMI)
			return year + delim +  month + delim + day + " " + hour + ":" +  min + ":" + sec + "." + msec;
		else
			return null;
	}
	
	public static void main(String[] args){
        String[] aa		=	{"123"};
        try{
            System.out.println( addSql("Name","AND",aa));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }        
    
}