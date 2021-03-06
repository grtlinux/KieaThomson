package org.tain.kang.thomson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tain.kang.utils.ClassUtils;
import org.tain.kang.utils.ResourcesUtils;

@SuppressWarnings("unused")
public class InsertDb {

	private static final boolean flag;

	private static final Boolean _mysqlEnable;
	private static final String _jdbcDriver;
	private static final String _dbUrl;
	private static final String _user;
	private static final String _pass;
	private static final String _charSet;

	private static Connection _conn = null;
	private static PreparedStatement _pstmt = null;
	private static String _sql = null;

	static {
		flag = true;
		_mysqlEnable = Boolean.valueOf(ResourcesUtils.getString("org.tain.kiea.thomson.mysql.enable"));
		_jdbcDriver = ResourcesUtils.getString("org.tain.kiea.thomson.mysql.jdbcDriver");
		_dbUrl = ResourcesUtils.getString("org.tain.kiea.thomson.mysql.dbUrl");
		_user = ResourcesUtils.getString("org.tain.kiea.thomson.mysql.user");
		_pass = ResourcesUtils.getString("org.tain.kiea.thomson.mysql.pass");
		_charSet = ResourcesUtils.getString("org.tain.kiea.thomson.mysql.charSet");

		if (flag) {
			System.out.printf(">>>>> InsertDb._jdbcDriver = %s%n", _jdbcDriver);
			System.out.printf(">>>>> InsertDb._dbUrl      = %s%n", _dbUrl);
			System.out.printf(">>>>> InsertDb._user       = %s%n", _user);
			System.out.printf(">>>>> InsertDb._pass       = %s%n", _pass);
			System.out.printf(">>>>> InsertDb._charSet    = %s%n", _charSet);
		}

		if (flag) {
			if (_mysqlEnable) {
				try {
					_sql = "insert into THOMSON"
							+ " ("
							+ "    GUID"
							+ ",   SIZE"
							+ ",   MESSAGE"
							+ " ) values ("
							+ "    ?"    // GUID varchar(100)
							+ ",   ?"    // SIZE int(11)
							+ ",   ?"    // MESSAGE mediumtext
							+ ")";
					Class.forName(_jdbcDriver);
					_conn = DriverManager.getConnection(_dbUrl, _user, _pass);
					_pstmt = _conn.prepareStatement(_sql);
					_conn.setAutoCommit(false);
					System.out.println(">>>>> MySQL Connection is OK!!!!!");
				} catch (Exception e) {
					e.printStackTrace();
					_conn = null;
					_pstmt = null;
				}
			} else {
				_conn = null;
				_pstmt = null;
			}
		}
	}

	public InsertDb() {}

	public static int insertMessage(String guid, String message) {
		if (!flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (_conn == null) {
			System.out.println(">>>>> MySQL Connection is null.......");
			return -1;
		}

		int length = 0;
		int retValue = -1;

		try {
			length = message.getBytes(_charSet).length;

			_pstmt.setString(1, guid);
			_pstmt.setInt   (2, length);
			_pstmt.setString(3, message);

			retValue = _pstmt.executeUpdate();

			_conn.commit();
		} catch (SQLException e) {
			if (_conn != null) try { _conn.rollback(); } catch (Exception e2) {}
			_conn = null;
			e.printStackTrace();
		} catch (Exception e) {
			if (_conn != null) try { _conn.rollback(); } catch (Exception e2) {}
			_conn = null;
			e.printStackTrace();
		}

		if (flag) System.out.printf(">>>>> MySQL.insertMessage() --> SIZE=%7d, GUID=\"%s\" %n", length, guid);

		return retValue;
	}

	///////////////////////////////////////////////////////////////////////////

	/*
	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) select01(args);  // select
		if (!flag) insertTest01(args);   // insert a sample data
		if (!flag) delete01(args);   // delete the articles in table Thomson
	}
	*/

	private static void select01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		List<Map<String, Object>> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName(_jdbcDriver);
			conn = DriverManager.getConnection(_dbUrl, _user, _pass);
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
			pstmt.setInt(1, 50000);

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
	}

	private static void insertTest01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(_jdbcDriver);
			conn = DriverManager.getConnection(_dbUrl, _user, _pass);
			conn.setAutoCommit(false);

		    String sql = "insert into THOMSON"
					+ " ("
					+ "  GUID"
					+ ", SIZE"
					+ ", MESSAGE"
					+ " ) values ("
					+ "  ?"    // GUID varchar(100)
					+ ", ?"    // SIZE int(11)
					+ ", ?"    // MESSAGE mediumtext
					+ ")";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "GUID-key");
			pstmt.setInt(2, 12345);
			pstmt.setString(3, "sender@gmail.com..sender@gmail.com..sender@gmail.com..sender@gmail.com");

			int rowUpdate = pstmt.executeUpdate();
			if (flag) System.out.printf(">>>>> rowUpdate = %d%n", rowUpdate);

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

	private static void delete01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(_jdbcDriver);
			conn = DriverManager.getConnection(_dbUrl, _user, _pass);
			conn.setAutoCommit(false);

		    String sql = "delete from THOMSON "
		    		+ " where 1=1"
		    		+ "     and id < ?"
		    		+ "";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 100);

			int rowUpdate = pstmt.executeUpdate();
			if (flag) System.out.printf(">>>>> rowUpdate = %d%n", rowUpdate);

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
