package org.tain.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		if (flag) test01(args);
		if (flag) test02(args);
	}
	
	private static void test01(String[] args) throws Exception {
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.13:3306/tain", "root", "toor");
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
				if (flag) System.out.println(">>>>> 13 cnt = " + resultSet.getLong("CNT"));
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
		if (flag) log.info("INFO: {}", ClassUtils.getFileLine());
		
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
}
