package util;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.LogWrapper;
import util.Utils;



/**
 * database 연결
 * @author Han
 *
 */
public class DBConnection {
	
	public static Connection getToConnection() throws SQLException, IOException {
		
		String url  		= null;
		String id			= null;
		String pwd			= null;
		
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            url 	= DBInit.getMsSql();
    	    id 		= DBInit.getID();
    	    pwd 	= DBInit.getPW();
    	    
    	    System.out.println("url:"+url);
    	    System.out.println("id:"+id);
    	    System.out.println("pwd:"+pwd);
    	    LogWrapper.biz.debug("url:"+url);
    	    LogWrapper.biz.debug("id:"+id);
    	    LogWrapper.biz.debug("pwd:"+pwd);
        }
      	catch (ClassNotFoundException e) {
      		LogWrapper.print("[getToConnection Exception]"+e);
		}catch (Exception e){
			LogWrapper.print("[getToConnection Exception]"+e);
		}
		    
	    return DriverManager.getConnection(url,id,pwd);
	}
  	
	
	
	public static int executeUpdate(Connection con, String query) throws SQLException,Exception {
	   
		Statement stmt = con.createStatement();
	    
		try
		{
			System.out.println(query);
			LogWrapper.biz.debug(query);
			return stmt.executeUpdate(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			stmt.close();
		}
	}
	
	public static int executeUpdateParam(Connection con, String query, List argu) throws SQLException,Exception {
		
		PreparedStatement pstmt = null;
		int paramIndex 		= 	0;
		
		try
		{
			pstmt = con.prepareStatement(query);
			
			LogWrapper.biz.debug(query);
			if (argu != null) {
				String tt = null;
				int size = 0;
				
				if (argu != null) size = argu.size();
				
				for (int i = 0; i < size ; i++) {
					tt = (String)argu.get(i);
					pstmt.setString((i+1), tt);
					paramIndex = i + 1;
	                LogWrapper.biz.info("☞ DBSession iParam(" + paramIndex + ") = "+ tt);
				}
			}
			
//			System.out.println(query);
			return pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ignored) {
				}
			}
		}
	}
	
	
	
	public static List executeQuery(Connection conn, String query, List argu) throws SQLException {

		ResultSet rs 			= null;
		PreparedStatement pstmt = null;
		List results 			= new ArrayList();

		try {
			System.out.println(query);
			LogWrapper.biz.debug(query);
			pstmt = conn.prepareStatement(query);
			
			
			if (argu != null) {
				String tt = null;
				int size = 0;
				
				if (argu != null) size = argu.size();
				
				for (int i = 0; i < size ; i++) {
					tt = (String)argu.get(i);
					pstmt.setString((i+1), tt);
				}
			}

			rs = pstmt.executeQuery(); 

			if (rs != null) {
				String tempStr 				= null;
				ResultSetMetaData rsMeta 	= rs.getMetaData();
				int numberOfColumns 		= rsMeta.getColumnCount();


				while (rs.next()) {
					Map row = new HashMap();

					for (int j = 1; j <= numberOfColumns; j++) {

						tempStr = rsMeta.getColumnName(j);
						row.put(tempStr.toLowerCase(), rs.getString(tempStr));
					}

					results.add(row);
				}
			}
			
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			LogWrapper.biz.debug("[executeQuery Exception] : " + e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ignored) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ignored) {
				}
			}
		}
		return results;

	}
	
	
	public static String queryString(Connection conn,String query) throws SQLException {
        
    	String result	= null;
        Statement stmt	= null;
   
        LogWrapper.biz.debug("[Exec Query] = "+query);
        
        try {
        	stmt=conn.createStatement();
            LogWrapper.biz.info("▶queryString stmt:"+ stmt);
            
            ResultSet rs=stmt.executeQuery(query);
            LogWrapper.biz.info("▶queryInteger rs:"+rs);
            try {
                if (rs.next()) {
                    result=rs.getString(1);
                    //LogWrapper.biz.debug("a row selected result="+result);
                } else {
                    LogWrapper.biz.debug("no rows selected");
                }
            } finally {
                //rs.close();
            	if (rs != null) {
    				try {
    					rs.close();
    					LogWrapper.biz.info("▶queryInteger rs closed");
    				} catch (Exception ignored) {
    				}
    			}
            }
        }catch(SQLException se){
    		LogWrapper.biz.debug("▶ SQLException:" + se);
    		throw se;
        } finally {
            //stmt.close();
        	if (stmt != null) {
				try {
					stmt.close();
					LogWrapper.biz.info("▶queryInteger stmt closed");
				} catch (Exception ignored) {
				}
			}
        }
        return result;
    }
	
	
	public static String queryStringParam(Connection conn,String query, List argu) throws SQLException {
		
		String result	= null;
		PreparedStatement pstmt = null;
		int paramIndex 		= 	0;
		
		LogWrapper.biz.debug("[Exec Query] = "+query);
		
		try {
			pstmt = conn.prepareStatement(query);
			LogWrapper.biz.info("▶queryString stmt:"+ pstmt);
			
			if (argu != null) {
				String tt = null;
				int size = 0;
				
				if (argu != null) size = argu.size();
				
				for (int i = 0; i < size ; i++) {
					tt = (String)argu.get(i);
					pstmt.setString((i+1), tt);
					paramIndex = i + 1;
	                LogWrapper.biz.info("☞ DBSession iParam(" + paramIndex + ") = "+ tt);
				}
			}
			
			ResultSet rs=pstmt.executeQuery(); 
			LogWrapper.biz.info("▶queryInteger rs:"+rs);
			try {
				if (rs.next()) {
					result=rs.getString(1);
					//LogWrapper.biz.debug("a row selected result="+result);
				} else {
					LogWrapper.biz.debug("no rows selected");
				}
			} finally {
				//rs.close();
				if (rs != null) {
					try {
						rs.close();
						LogWrapper.biz.info("▶queryInteger rs closed");
					} catch (Exception ignored) {
					}
				}
			}
		}catch(SQLException se){
			LogWrapper.biz.debug("▶ SQLException:" + se);
			throw se;
		} finally {
			//stmt.close();
			if (pstmt != null) {
				try {
					pstmt.close();
					LogWrapper.biz.info("▶queryInteger stmt closed");
				} catch (Exception ignored) {
				}
			}
		}
		return result;
	}
	
	
	public static int queryInteger(Connection conn,String query) throws SQLException {
        
    	int result	= 0;
        Statement stmt	= null;
   
        System.out.println(query);
        LogWrapper.biz.debug("[Exec Query] = "+query);
        
        try {
        	stmt=conn.createStatement();
            LogWrapper.biz.info("▶queryString stmt:"+ stmt);
            
            ResultSet rs=stmt.executeQuery(query);
            LogWrapper.biz.info("▶queryInteger rs:"+rs);
            try {
                if (rs.next()) {
                    result=rs.getInt(1);
                    //LogWrapper.biz.debug("a row selected result="+result);
                } else {
                    LogWrapper.biz.debug("no rows selected");
                }
            } finally {
                //rs.close();
            	if (rs != null) {
    				try {
    					rs.close();
    					LogWrapper.biz.info("▶queryInteger rs closed");
    				} catch (Exception ignored) {
    				}
    			}
            }
        }catch(SQLException se){
    		LogWrapper.biz.debug("▶ SQLException:" + se);
    		throw se;
        } finally {
            //stmt.close();
        	if (stmt != null) {
				try {
					stmt.close();
					LogWrapper.biz.info("▶queryInteger stmt closed");
				} catch (Exception ignored) {
				}
			}
        }
        return result;
    }
	
	//= 자료 조회
    public static ArrayList getSelect(Connection conn, String sQuery, ArrayList iParam)
    {
        boolean bFirst		=	true;
        ArrayList   arrList	= 	new ArrayList();
        int     iColcnt   		= 	0;     
        int paramIndex 		= 	0;
        HashMap	map  	= 	null;
        PreparedStatement     pstmt 	= null; 
        ResultSet             rsMain    	= null; 
        ResultSetMetaData     rsmd 	= null;

        LogWrapper.biz.info("▒ DBSession getSelect() Start ▒");
        LogWrapper.biz.info("DBSession[sQuery]= \n"+sQuery);
        try {
            pstmt 		= conn.prepareStatement(sQuery.toString());
            if(iParam != null) {
	            for(int i=0; i<iParam.size(); i++){
	            	paramIndex = i + 1;
	                LogWrapper.biz.info("☞ DBSession iParam(" + paramIndex + ") = "+ (String) iParam.get(i));
	    	        	
	            	pstmt.setString(paramIndex, (String) iParam.get(i));
	            	
	            }
            }
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
        catch(SQLException e)   
        {   
            LogWrapper.biz.info("	DBSession[SQLException]="+Utils.getLogStackTrace(e));
            arrList = null;         
        } 
        catch(Exception e)  
        {  
            LogWrapper.biz.info("	DBSession[Exception]="+Utils.getLogStackTrace(e));

            arrList = null;         
        } finally
        {
            LogWrapper.biz.info("DBSession[finally]");
            LogWrapper.biz.info("▒ DBSession getSelect() End   ▒");
        }
        
        return arrList;
    }
	
}//END CLASS
