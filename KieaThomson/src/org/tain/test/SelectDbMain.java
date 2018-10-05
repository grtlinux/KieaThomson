package org.tain.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tain.utils.ClassUtils;

public class SelectDbMain {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (!flag) select01(args);  // simple select
		if (flag) selectCountLoop(args);   // select count(*)
	}

	private static void select01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		List<Map<String, Object>> list = findAll(50000);

		for (Map<String, Object> map : list) {
			System.out.println(">>>>> json message : " + String.valueOf(map.get("message")));
		}
	}

	private static void selectCountLoop(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		List<Map<String, Object>> list = null;
		long cnt;
		for (int i=0; i < 10000; i++) {
			list = countAll();
			cnt = (long) list.get(0).get("cnt");
			System.out.printf(">>>>> Result: cnt = %d (%s)%n", cnt, new Date());

			try { Thread.sleep(10000); } catch (InterruptedException e) {}
		}
	}

	private static List<Map<String, Object>> findAll(final int size) {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		List<Map<String, Object>> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.2.13:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			list = new ArrayList<Map<String, Object>>();

			String sql = ""
					+ "select"
					+ "    ID"
					+ "    , GUID"
					+ "    , SIZE"
					+ "    , MESSAGE"
					+ " from"
					+ "    THOMSON"
					+ " where 1=1"
					+ "    and SIZE > ?"
					+ "";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, size);

			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", resultSet.getLong("ID"));
				map.put("guid", resultSet.getString("GUID"));
				map.put("size", resultSet.getInt("SIZE"));
				map.put("message", resultSet.getString("MESSAGE"));
				list.add(map);
			}

			if (flag) System.out.printf(">>>>> list.size() = %d%n", list.size());

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

		return list;
	}

	private static List<Map<String, Object>> countAll() {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		List<Map<String, Object>> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.2.13:3306/tain", "root", "toor");
			conn.setAutoCommit(false);

			list = new ArrayList<Map<String, Object>>();

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
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cnt", resultSet.getLong("CNT"));
				list.add(map);
			}

			if (!flag) System.out.printf(">>>>> list.size() = %d%n", list.size());

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

		return list;
	}
}
