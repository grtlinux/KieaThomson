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
		log = LoggerFactory.getLogger(new Object() {}.getClass().getEnclosingClass());
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		if (!flag) test01(args);   // 13
		if (!flag) test02(args);   // 23
		if (flag) test03(args);   // sync between 13 and 23
		if (!flag) test04(args);   // select and insert
	}
	
	private static void test01(String[] args) throws Exception {
		if (!flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.13:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			String sql = ""
					+ " select"
					+ "    count(*) REC_COUNT"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					+ "";

			pstmt = conn.prepareStatement(sql);

			resultSet = pstmt.executeQuery();
			metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				if (flag) System.out.printf(">>>>> 13: %d, %s = %s%n"
						, metaData.getColumnCount()
						, metaData.getColumnName(1)
						, String.valueOf(resultSet.getObject(1))
						);
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
				pstmtTgt = connTgt.prepareStatement("select max(ID) MAX_ID from THOMSON");
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
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.23:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			String sql = ""
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

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, 150000);

			resultSet = pstmt.executeQuery();
			metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				if (flag) System.out.printf(">>>>> [13] %s=%s, %s=%s%n"
						, metaData.getColumnName(1)
						, resultSet.getString(1)
						, metaData.getColumnName(4)
						, new JSONObject(resultSet.getString(4)).toString(2)
						);
				
				if (!flag) break;
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
}
