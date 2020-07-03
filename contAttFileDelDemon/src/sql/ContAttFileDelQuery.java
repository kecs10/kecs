package sql;

public class ContAttFileDelQuery {

	
	/**
	 * 
	 * @return
	 */
	public static String contAttFileListQuery(){
		StringBuffer sb = new StringBuffer();  
		
		sb.append("		SELECT	\n");
		sb.append("		CCI.CONT_NUMB, CCI.CONT_CHNG_NUMB, CCI.BUSI_N, AFM.ATT_FILE_NM, AFM.ATT_FILE_PATH, ATT_STR0_NM	\n");
		sb.append("		FROM	\n");
		sb.append("		CONT_CUST_IF CCI WITH(NOLOCK), ATT_FILE_MNG AFM WITH(NOLOCK)	\n");
		sb.append("		WHERE CCI.CONT_NUMB = SUBSTRING(AFM.ATT_TY_N,0,15)	\n");
		sb.append("		AND CCI.CONT_CHNG_NUMB = SUBSTRING(AFM.ATT_TY_N, 16, 17)		\n");
		sb.append("		AND CCI.BUSI_N = '1234567890' 	\n");	

		return sb.toString();
	}	

}
