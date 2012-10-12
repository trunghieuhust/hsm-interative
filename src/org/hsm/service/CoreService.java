package org.hsm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import org.hsm.control.Control;

public class CoreService {
	private static CoreService instance;

	public static CoreService getInstance() {
		if (instance == null) {
			instance = new CoreService();
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				Control.getInstance()
						.getLogger()
						.log(Level.SEVERE,
								"Init postgresql driver failed.\nMessage: "
										+ e.getMessage());
				return null;
			}
		}
		return instance;
	}

	private static String getUrl(Properties loginInfo) {
		String url = "jdbc:postgresql://"
				+ loginInfo.getProperty("host", "localhost") + ":"
				+ loginInfo.getProperty("port", "5432") + "/"
				+ loginInfo.getProperty("dbname", "hedspi") + "?user="
				+ loginInfo.getProperty("username", "Admin") + "&password="
				+ loginInfo.getProperty("password", "hedspi");
		// + "&ssl=" + loginInfo.getProperty("ssl", "false");
		// Control.getInstance().getLogger().log(Level.INFO, url);
		return url;
	}

	public static boolean isGoodLogin(Properties loginInfo) {
		String url = getUrl(loginInfo);
		try {
			Connection con = DriverManager.getConnection(url);
			con.close();
		} catch (SQLException e) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, "Login failed: {0}", e.getMessage());
			return false;
		}
		return true;
	}

	private Properties loginInfo;

	private void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (Throwable e) {
				Control.getInstance().getLogger()
						.log(Level.WARNING, e.getMessage());
			}
	}

	private void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				Control.getInstance().getLogger()
						.log(Level.WARNING, e.getMessage());
			}
	}

	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				Control.getInstance().getLogger()
						.log(Level.WARNING, e.getMessage());
			}
		}
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(getUrl());

		} catch (Throwable e) {
			Control.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
			return null;
		}
		if (conn == null) {
			Control.getInstance().getLogger()
					.log(Level.SEVERE, "Failed to connect to database");
			return null;
		}

		try {
			for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn
					.getNextWarning()) {
				Control.getInstance().getLogger()
						.log(Level.WARNING, warn.getMessage());
			}
		} catch (Exception e) {
			Control.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
		}
		return conn;
	}

	private Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			Control.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
			return null;
		}
		if (stmt == null) {
			Control.getInstance().getLogger()
					.log(Level.SEVERE, "Cannot create query");
			return null;
		}
		return stmt;
	}

	private String getUrl() {
		return getUrl(loginInfo);
	}

	/**
	 * Thực hiện truy vấn trả về kết quả.
	 * @param queryStr	câu truy vấn có trả về kết quả.
	 * Nếu một truy vấn không có kết quả được gọi, cảnh báo sẽ xuất hiện
	 * trong file log.
	 * @return	danh sách các bản ghi trả về, mỗi bản ghi là một map.
	 * Trường hợp truy vấn lỗi sẽ trả về một danh sách rỗng.
	 */
	public ArrayList<HashMap<String, Object>> query(String queryStr) {
		Control.getInstance().getLogger()
				.log(Level.INFO, "Execute query {0}", queryStr);
		ArrayList<HashMap<String, Object>> result = new ArrayList<>();

		Connection conn = getConnection();
		if (conn == null)
			return result;

		Statement stmt = getStatement(conn);
		if (stmt == null) {
			close(conn);
			return result;
		}

		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(queryStr);
		} catch (SQLException e) {
			Control.getInstance().getLogger().log(Level.WARNING, e.getMessage());
			close(stmt);
			close(conn);
			return result;
		}
		if (rs == null) {
			close(stmt);
			close(conn);
			return result;
		}

		try {
			int n = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<>();
				for (int i = 1; i <= n; i++) {
					String label = rs.getMetaData().getColumnLabel(i);
					Object val = rs.getObject(i);
					map.put(label, val);
				}
				result.add(map);
			}
		} catch (SQLException e) {
			Control.getInstance().getLogger().log(Level.SEVERE, e.getMessage());
		} finally {
			close(rs);
			close(stmt);
			close(conn);
		}
		return result;
	}

	/**
	 * Thực thi truy vấn cập nhật CSDL, sử dụng giống như {@link #query query} tuy nhiên
	 * lệnh này sử dụng cho những câu truy vấn không trả về kết quả.
	 * @param updateStr	câu truy vấn có thể không trả về kết quả
	 * @return	trả về <code>null</code> nếu thành công, ngược lại sẽ trả về String chứa
	 * thông báo lỗi.
	 */
	public String update(String updateStr){
		Control.getInstance().getLogger()
		.log(Level.INFO, "Execute udpate " + updateStr);

		Connection conn = getConnection();
		if (conn == null)
			return "Get connection failed";

		Statement stmt = getStatement(conn);
		if (stmt == null) {
			close(conn);
			return "Cannot get statement";
		}

		try {
			stmt.executeUpdate(updateStr);
		} catch (SQLException e) {
			Control.getInstance().getLogger()
			.log(Level.WARNING, e.getMessage());
			return e.getMessage();
		}

		close(stmt);
		close(conn);
		return null;
	}

	public void setLoginInfo(Properties loginInfo) {
		this.loginInfo = loginInfo;
	}
}
