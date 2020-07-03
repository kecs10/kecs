package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import sql.ContAttFileDelQuery;
import util.Compress;
import util.DBConnection;
import util.LogWrapper;
import util.Utils;

public class ContAttFileDelTrans {

	Connection conn = null;

	String sMessage = "";

	public boolean getData() {
		int sResult = 0;
		try {

			conn = DBConnection.getToConnection();
			ArrayList contAttFileList = new ArrayList();
			HashMap fileMap = null;

			String att_Str0_Nm = null; // 파일명
			String att_File_Path = null; // 파일경로

			contAttFileList = DBConnection.getSelect(conn, ContAttFileDelQuery.contAttFileListQuery(), contAttFileList);

			for (int i = 0; i < contAttFileList.size(); i++) {
				fileMap = (HashMap) contAttFileList.get(i);
				att_File_Path = Utils.checkNull((String) fileMap.get("ATT_FILE_PATH"));
				att_Str0_Nm = Utils.checkNull((String) fileMap.get("ATT_STR0_NM"));
				LogWrapper.biz.debug("▣ 경로:" + att_File_Path + "/" + att_Str0_Nm);
				LogWrapper.biz.debug("▣ 파일명" + "[" + i + "]" + ":" + att_Str0_Nm);

				removeFiles(att_File_Path + "/" + att_Str0_Nm);
			}

		} catch (Exception e) {
			// TODO: handle exception
			LogWrapper.biz.error("error : " + Utils.getLogStackTrace(e));
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					LogWrapper.biz.error("error : " + Utils.getLogStackTrace(e));
				}
			}
		}
		return sResult > 0;
	}

	/**
	 * 파일 또는 폴더를 삭제한다.
	 * 
	 * @param sFolder
	 *            저장경로
	 * @return boolean
	 */
	public static boolean removeFiles(String sFolder) {
		return Utils.fFileDel(sFolder);
	}
}
