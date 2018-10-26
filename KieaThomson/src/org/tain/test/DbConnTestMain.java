package org.tain.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tain.utils.ClassUtils;

public class DbConnTestMain {

	private static final boolean flag;
	private static final Logger log;
	
	static {
		flag = true;
		//log = LoggerFactory.getLogger(new Object() {}.getClass().getEnclosingClass());
		log = LoggerFactory.getLogger(DbConnTestMain.class);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		if (flag) test01(args);   // 13 23 rec_count
		if (flag) try { Thread.sleep(2000); } catch (InterruptedException e) {}
		if (!flag) test02(args);   // 23
		if (flag) test03(args);   // sync between 13 and 23
		if (flag) try { Thread.sleep(2000); } catch (InterruptedException e) {}
		if (flag) test04(args);   // select and insert into other tables
	}
	
	private static void test01(String[] args) throws Exception {
		if (!flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection conn13 = null;
		Connection conn23 = null;
		PreparedStatement pstmt13 = null;
		PreparedStatement pstmt23 = null;
		ResultSet resultSet13 = null;
		ResultSet resultSet23 = null;
		ResultSetMetaData metaData13 = null;
		ResultSetMetaData metaData23 = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn13 = DriverManager.getConnection("jdbc:mysql://192.168.1.13:3306/tain", "root", "toor");
			conn23 = DriverManager.getConnection("jdbc:mysql://192.168.1.23:3306/tain", "root", "toor");
			conn13.setAutoCommit(false);
			conn23.setAutoCommit(false);

			String sql13 = ""
					+ " select"
					+ "    count(*) REC_COUNT13"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					+ "";

			String sql23 = ""
					+ " select"
					+ "    count(*) REC_COUNT23"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					+ "";

			pstmt13 = conn13.prepareStatement(sql13);
			pstmt23 = conn23.prepareStatement(sql23);

			resultSet13 = pstmt13.executeQuery();
			metaData13 = resultSet13.getMetaData();
			while (resultSet13.next()) {
				if (flag) System.out.printf(">>>>> 13: %d, %s = %s%n"
						, metaData13.getColumnCount()
						, metaData13.getColumnName(1)
						, String.valueOf(resultSet13.getObject(1))
						);
			}

			resultSet23 = pstmt23.executeQuery();
			metaData23 = resultSet23.getMetaData();
			while (resultSet23.next()) {
				if (flag) System.out.printf(">>>>> 13: %d, %s = %s%n"
						, metaData23.getColumnCount()
						, metaData23.getColumnName(1)
						, String.valueOf(resultSet23.getObject(1))
						);
			}

			if (flag)
				conn13.commit();
			else
				conn13.rollback();

			if (flag)
				conn23.commit();
			else
				conn23.rollback();
		} catch (SQLException e) {
			if (conn13 != null) try { conn13.rollback(); } catch (Exception e2) {}
			if (conn23 != null) try { conn23.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} catch (Exception e) {
			if (conn13 != null) try { conn13.rollback(); } catch (Exception e2) {}
			if (conn23 != null) try { conn23.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt13 != null) try { pstmt13.close(); } catch (Exception e) {}
			if (pstmt23 != null) try { pstmt23.close(); } catch (Exception e) {}
			if (conn13 != null) try { conn13.close(); } catch (Exception e) {}
			if (conn23 != null) try { conn23.close(); } catch (Exception e) {}
		}
	}

	private static void test02(String[] args) throws Exception {
		if (!flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.23:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			String sql = ""
					+ " select"
					+ "    count(*) CNT"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					+ "";

			pstmt = conn.prepareStatement(sql);

			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				if (flag) System.out.println(">>>>> 23 cnt = " + resultSet.getLong("CNT"));
			}

			if (flag)
				conn.commit();
			else
				conn.rollback();
		} catch (SQLException e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void test03(String[] args) throws Exception {
		if (!flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection connSrc = null;
		Connection connTgt = null;
		PreparedStatement pstmtSrc = null;
		PreparedStatement pstmtTgt = null;
		ResultSet resultSetSrc = null;
		ResultSet resultSetTgt = null;
		ResultSetMetaData metaDataSrc = null;
		//ResultSetMetaData metaDataTgt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connSrc = DriverManager.getConnection("jdbc:mysql://192.168.1.13:3306/tain", "root", "toor");
			connTgt = DriverManager.getConnection("jdbc:mysql://192.168.1.23:3306/tain", "root", "toor");
			connSrc.setAutoCommit(false);
			connTgt.setAutoCommit(false);

			long maxId = 0;
			if (flag) {
				// get maxId from tgt
				pstmtTgt = connTgt.prepareStatement("select ifnull(max(ID), 0) MAX_ID from THOMSON");
				resultSetTgt = pstmtTgt.executeQuery();
				while (resultSetTgt.next()) {
					maxId = resultSetTgt.getLong("MAX_ID");
				}
				if (flag) System.out.printf(">>>>> [23] MAX_ID = %d%n", maxId);
			}
			
			if (flag) {
				// [13] select
				String sqlSrc = ""
						+ " select"
						+ "    ID,"
						+ "    GUID,"
						+ "    SIZE,"
						+ "    MESSAGE"
						+ " from"
						+ "    THOMSON"
						+ " where 1=1"
						+ "    and ID > ?"
						+ "";
				
				// [23] insert
				String sqlTgt = ""
						+ " insert into THOMSON"
						+ " ("
						+ "    ID,"
						+ "    GUID,"
						+ "    SIZE,"
						+ "    MESSAGE"
						+ " ) values ("
						+ "    ?,"
						+ "    ?,"
						+ "    ?,"
						+ "    ?"
						+ " )";
				
				pstmtSrc = connSrc.prepareStatement(sqlSrc);
				pstmtSrc.setLong(1, maxId);
				
				resultSetSrc = pstmtSrc.executeQuery();
				metaDataSrc = resultSetSrc.getMetaData();
				while (resultSetSrc.next()) {
					if (flag) System.out.printf(">>>>> [13]: %s = %s%n"
							, metaDataSrc.getColumnName(1)
							, String.valueOf(resultSetSrc.getObject(1))
							);
					// insert
					pstmtTgt = connTgt.prepareStatement(sqlTgt);
					pstmtTgt.setInt(1, resultSetSrc.getInt(1));
					pstmtTgt.setString(2, resultSetSrc.getString(2));
					pstmtTgt.setInt(3, resultSetSrc.getInt(3));
					pstmtTgt.setString(4, resultSetSrc.getString(4));
					pstmtTgt.executeUpdate();
					
					if (!flag) break;
				}
			}

			if (flag) {
				connSrc.commit();
				connTgt.commit();
			} else {
				connSrc.rollback();
				connTgt.rollback();
			}
		} catch (SQLException e) {
			if (connSrc != null) try { connSrc.rollback(); } catch (Exception e2) {}
			if (connTgt != null) try { connTgt.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} catch (Exception e) {
			if (connSrc != null) try { connSrc.rollback(); } catch (Exception e2) {}
			if (connTgt != null) try { connTgt.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmtSrc != null) try { pstmtSrc.close(); } catch (Exception e) {}
			if (pstmtTgt != null) try { pstmtTgt.close(); } catch (Exception e) {}
			if (connSrc != null) try { connSrc.close(); } catch (Exception e) {}
			if (connTgt != null) try { connTgt.close(); } catch (Exception e) {}
		}
	}

	private static void test04(String[] args) throws Exception {
		if (!flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtInsert = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.23:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			String sqlSelect = ""   // not used
					+ " select"
					+ "    ID,"
					+ "    GUID,"
					+ "    SIZE,"
					+ "    MESSAGE"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					//+ "    and ID < ?"
					+ "    and ID >= ?"
					+ "";
			
			sqlSelect = ""
					+ " select"
					+ "    ID,"
					+ "    GUID,"
					+ "    SIZE,"
					+ "    MESSAGE"
					+ " from"
					+ "    TAIN.THOMSON" 
					+ " where 1=1"
					+ "    and ID > ("
					+ "        select ifnull(max(ID), 0) from TAIN.REUTERS"
					+ "    )"
					+ " order by"
					+ "    ID "
					+ " limit 100000"
					+ "";
			
			String sqlInsert = ""
					+ " insert into REUTERS"
					+ " ("
					+ "    ID,"
					+ "    LANGUAGE,"
					+ "    ACTIVDATE,"
					+ "    TIMACTMS,"
					+ "    ALTID,"
					+ "    GUID,"
					+ "    MIMETYPE,"
					+ "    HEADLINE,"
					+ "    BODY,"
					+ "    ZIPSIZE,"
					+ "    UDPSIZE,"
					+ "    FIRSTCREATED,"
					+ "    VERSIONcREATED"
					+ " ) values ("
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?,"
					+ "    ?"
					+ " )";

			pstmt = conn.prepareStatement(sqlSelect);
			//pstmt.setLong(1, 1);
			//pstmt.setLong(1, 100000);

			resultSet = pstmt.executeQuery();
			metaData = resultSet.getMetaData();
			JSONObject jsonObject = null;
			while (resultSet.next()) {
				jsonObject = new JSONObject(resultSet.getString(4));
				
				if (!flag) System.out.printf(">>>>> [13] %s=%s, %s=%s%n"
						, metaData.getColumnName(1)
						, resultSet.getString(1)
						, metaData.getColumnName(4)
						, jsonObject.toString(2)
						);
				
				if (flag) {
					System.out.printf(">>>>> [ID:%7d] [GUID:%s]%n", resultSet.getInt("ID"), jsonObject.getString("GUID"));
					
					//System.out.println(">>>>> GUID        : " + jsonObject.getString("GUID"));
					//System.out.println(">>>>> language    : " + jsonObject.getString("language"));
					//System.out.println(">>>>> mimeType    : " + jsonObject.getString("mimeType"));
					//System.out.println(">>>>> firstCreated: " + jsonObject.getString("firstCreated"));
					//System.out.println(">>>>> headline    : " + jsonObject.getString("headline"));
					//System.out.println(">>>>> body        : " + jsonObject.getString("body"));
				}
				
				pstmtInsert = conn.prepareStatement(sqlInsert);
				pstmtInsert.setInt   (1, resultSet.getInt("ID"));
				pstmtInsert.setString(2,  jsonObject.getString("language")      );
				pstmtInsert.setString(3,  jsonObject.getString("ACTIV_DATE")    );
				pstmtInsert.setString(4,  jsonObject.getString("TIMACT_MS")     );
				pstmtInsert.setString(5,  jsonObject.getString("altId")         );
				pstmtInsert.setString(6,  jsonObject.getString("GUID")          );
				pstmtInsert.setString(7,  jsonObject.getString("mimeType")      );
				pstmtInsert.setString(8,  jsonObject.getString("headline")      );
				pstmtInsert.setString(9,  jsonObject.getString("body")          );
				pstmtInsert.setInt   (10, Integer.parseInt(jsonObject.getString("TOT_SIZE")));
				pstmtInsert.setInt   (11, Integer.parseInt(resultSet.getString("SIZE")));
				pstmtInsert.setString(12, jsonObject.getString("firstCreated")  );
				pstmtInsert.setString(13, jsonObject.getString("versionCreated"));
				pstmtInsert.executeUpdate();
				
				if (!flag) break;
			}

			if (!flag) System.out.println(">>>>> jsonObject = " + jsonObject.toString(2));
			
			if (flag)
				conn.commit();
			else
				conn.rollback();
		} catch (SQLException e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}
}
