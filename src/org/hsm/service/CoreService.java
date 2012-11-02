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
import org.hsm.model.hedspiObject.HedspiObject;

/**
 * Tập hợp driver tương tác trực tiếp với server.
 * Một số đã có doc sẵn, một số chưa. Nếu cần cái nào thì email
 * bảo rồi tôi sẽ viết
 * @author haidang-ubuntu
 *
 */
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
		// String url = "jdbc:postgresql://"
		// + loginInfo.getProperty("host", "localhost") + ":"
		// + loginInfo.getProperty("port", "5432") + "/"
		// + loginInfo.getProperty("dbname", "hedspi") + "?user="
		// + loginInfo.getProperty("username", "Admin") + "&password="
		// + loginInfo.getProperty("password", "hedspi");
		// + "&ssl=" + loginInfo.getProperty("ssl", "false");
		// Control.getInstance().getLogger().log(Level.INFO, url);
		String url = "jdbc:postgresql://" + loginInfo.getProperty("host") + ":"
				+ loginInfo.getProperty("port") + "/"
				+ loginInfo.getProperty("dbname") + "?user="
				+ loginInfo.getProperty("username") + "&password="
				+ loginInfo.getProperty("password");
		return url;
	}

	public static boolean isGoodLogin(Properties loginInfo) {
		String url = getUrl(loginInfo);
		try {
			Connection con = DriverManager.getConnection(url);
			con.close();
		} catch (SQLException e) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, "Login failed!");// : {0}",
															// e.getMessage());
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
	 * 
	 * @param queryStr
	 *            câu truy vấn có trả về kết quả. Nếu một truy vấn không có kết
	 *            quả được gọi, cảnh báo sẽ xuất hiện trong file log.
	 * @return danh sách các bản ghi trả về, mỗi bản ghi là một map. Trường hợp
	 *         truy vấn lỗi sẽ trả về một danh sách rỗng.
	 *         Ngoài ra, thông báo đi kèm sẽ được ghi vào {@link org.hsm.control.Control#getQueryMessage() query message
	 *         là thông báo lỗi hoặc <code>null</code> nếu truy vẫn thành công.
	 *         Lưu ý: danh sách rỗng khác với kết quả là rỗng.
	 * @see #queryFull(String)
	 */
	public ArrayList<HashMap<String, Object>> query(String queryStr) {
		Pair<ArrayList<HashMap<String, Object>>, String> queryFull = queryFull(queryStr);
		Control.getInstance().setQueryMessage(queryFull.getT2());
		return queryFull.getT1();
	}

	/**
	 * Thực hiện truy vấn loại có trả về kết quả.
	 * Lưu ý: trường hợp câu truy vấn không trả về kết quả, sẽ có thông báo lỗi,
	 * trường hợp này hãy sử dụng hàm {@link #update(String) update} để thay thế
	 * @param queryStr câu truy vấn có trả về kết quả
	 * @return một cặp gồm kết quả truy vấn và thông báo nếu có. Thông báo là <code>null</code> tức là thành công. 
	 * @see #update(String)
	 */
	private Pair<ArrayList<HashMap<String, Object>>, String> queryFull(
			String queryStr) {
		Control.getInstance().getLogger()
				.log(Level.INFO, "Execute query {0}", queryStr);
		ArrayList<HashMap<String, Object>> result = new ArrayList<>();

		Connection conn = getConnection();
		if (conn == null)
			return new Pair<>(result, "Cannot create connection");

		Statement stmt = getStatement(conn);
		if (stmt == null) {
			close(conn);
			return new Pair<>(result, "Cannot create query (statement)");
		}

		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(queryStr);
		} catch (SQLException e) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, e.getMessage());
			close(stmt);
			close(conn);
			return new Pair<>(result, e.getMessage());
		}
		if (rs == null) {
			close(stmt);
			close(conn);
			return new Pair<>(result, "Query failed");
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
		return new Pair<>(result, null);
	}

	/**
	 * Thực thi truy vấn cập nhật CSDL, sử dụng giống như {@link #query query}
	 * tuy nhiên lệnh này sử dụng cho những câu truy vấn không trả về kết quả.
	 * 
	 * @param updateStr
	 *            câu truy vấn có thể không trả về kết quả
	 * @return trả về <code>null</code> nếu thành công, ngược lại sẽ trả về
	 *         String chứa thông báo lỗi.
	 * @see #query(String)
	 */
	public String update(String updateStr) {
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

	/**
	 * Truyền tham số vào cho hàm trên server từ các biến của java.
	 * Có sửa đổi một số kiểu biến nhất định cho phù hợp với server.
	 * Chủ yếu sửa đổi:
	 * <li>biến boolean</li>
	 * <li>biến dãy</li>
	 * @param func tên hàm
	 * @param args danh sách biến
	 * @return dòng gọi hàm đầy đủ biến
	 */
	private String getFullFunctionCaller(String func, Object[] args) {
		String query = func + "(";
		for (int i = 0; i < args.length; i++) {
			String arg = args[i] instanceof String ? ("'"
					+ args[i].toString().replace("'", "''") + "'::text")
					: args[i].toString();
			if (args[i] instanceof Boolean)
				arg = (Boolean) args[i] ? "true" : "false";
			if (args[i] instanceof Double)
				arg = args[i].toString() + "::double precision";
			if (args[i] instanceof HedspiObject[]) {
				HedspiObject[] arr = (HedspiObject[]) args[i];
				arg = "";
				for (int j = 0; j < arr.length; j++)
					arg += arr[j].getId() + ", ";
				arg += "-1";
			}
			if (args[i] instanceof double[]) {
				arg = "";
				double[] arr = (double[]) args[i];
				for (int j = 0; j < arr.length; j++)
					arg += arr[j] + "::double precision, ";
				arg += "-1::double precision";
			}
			query += arg == "" ? "" : ((i > 0 ? ", " : "") + arg);
		}
		query += ")";
		return query;
	}

	/**
	 * Thực hiện một hàm có trả về kết quả từ trên server.
	 * @param func tên hàm đặt trên server
	 * @param args danh sách các tham số. Một số tham số có kiểu đặc biệt sẽ được
	 * điều hướng lại để cho phù hợp với server. Xem {@link #getFullFunctionCaller(String, Object[])}
	 * để biết cụ thể
	 * @return
	 * @see #getFullFunctionCaller(String, Object[])
	 */
	public ArrayList<HashMap<String, Object>> doQueryFunction(String func,
			Object... args) {
		String queryStr = "SELECT * FROM " + getFullFunctionCaller(func, args);
		return query(queryStr);
	}

	/**
	 * Sử dụng để gọi các hàm trên server. Mặc định hàm đó phải trả về text
	 * Trong đó cần trả về xâu rỗng nếu thành công, ngược lại trả về thông báo
	 * lỗi.
	 * 
	 * @param func
	 *            tên hàm
	 * @param args
	 *            danh sách các tham số, nếu là <code>String</code>, sẽ tự động
	 *            được thay thế kí tự <code>'</code> bởi <code>''</code>
	 * @return xem {@link #update update}
	 */
	@Deprecated
	public String doUpdateFunctionDeplicated(String func, Object... args) {
		final String RESULT_LABEL = "result";
		String updateStr = "SELECT " + getFullFunctionCaller(func, args)
				+ " AS " + RESULT_LABEL;
		Pair<ArrayList<HashMap<String, Object>>, String> rs = queryFull(updateStr);
		if (rs.getT2() != null)
			return rs.getT2();
		if (!rs.getT1().isEmpty()) {
			if (rs.getT1().get(0).get(RESULT_LABEL) == null)
				return "Server function {" + func
						+ "} did not return expected result";
			if (rs.getT1().get(0).get(RESULT_LABEL).equals(""))
				return null;
			else
				return (String) rs.getT1().get(0).get(RESULT_LABEL);
		}
		return "Query {" + func + "} failed";
	}

	/**
	 * Thực hiện hàm cập nhật trên server.
	 * @param func tên hàm
	 * @param args danh sách biến
	 * @return xâu rỗng nếu thành công, ngược lại trả về thông báo lỗi
	 * @see #queryFull(String)
	 * @see #doQueryFunction(String, Object...)
	 * @see #getFullFunctionCaller(String, Object[])
	 * @see #update(String)
	 */
	public String doUpdateFunction(String func, Object... args) {
		String updateStr = "SELECT " + getFullFunctionCaller(func, args);
		return queryFull(updateStr).getT2();
	}

	/**
	 * Hàm chuyển đổi kết quả của các phép truy vấn về dãy các đối tượng đơn giản.
	 * Yêu cầu các truy vấn trả về dãy các bảng có ít nhất 2 trường
	 * <li><code>id</code> kiểu <code>integer</code></li>
	 * <li><code>name</code> kiểu <code>text</code></li>
	 * @param rs kết quả của các truy vấn cần được chuyển
	 * @return danh sách đối tượng sau khi chuyển
	 */
	public HedspiObject[] rsToSimpleArray(ArrayList<HashMap<String, Object>> rs) {
		ArrayList<HedspiObject> ret = new ArrayList<>();
		for (HashMap<String, Object> it : rs) {
			if (it.get("id") == null)
				continue;
			int id = (int) it.get("id");
			String name = (String) it.get("name");
			if (name == null)
				name = "";
			ret.add(new HedspiObject(id, name));
		}
		return ret.toArray(new HedspiObject[ret.size()]);
	}

	/**
	 * Trả về đối tượng đầu tiên của phép gọi.
	 * Xem {@link #rsToSimpleArray(ArrayList)} để biết các quy ước về kết quả trả về của các câu truy vấn
	 * @param rs kết quả các câu truy vấn
	 * @return kết quả sau khi chuyển đổi, là đối tượng đầu tiên của kết quả truy vấn
	 * hoặc <code>null</code> nếu câu truy vẫn không có kết quả 
	 */
	public HedspiObject firstSimpleResult(ArrayList<HashMap<String, Object>> rs) {
		HedspiObject[] arr = rsToSimpleArray(rs);
		if (arr.length == 0)
			return null;
		return arr[0];
	}

	/**
	 * Thực hiện truy vấn hoặc cập nhật.
	 * @param queryStr câu truy vấn hoặc câu cập nhật.
	 * @return dãy các <code>String</code> với một quy ước về cấu trúc dữ liệu khác thường.
	 */
	public String[] executeQuery(String queryStr) {
		Control.getInstance().getLogger()
				.log(Level.INFO, "Execute udpate or query" + queryStr);

		Connection conn = getConnection();
		if (conn == null)
			return new String[] { "Get connection failed", "0" };

		Statement stmt = getStatement(conn);
		if (stmt == null) {
			close(conn);
			return new String[] { "Cannot get statement", "0" };
		}

		ArrayList<String> ret = new ArrayList<>();
		try {
			// TODO: query, get result and message(hard) from here
			if (stmt.execute(queryStr)) {
			}
		} catch (SQLException e) {
			Control.getInstance().getLogger()
					.log(Level.WARNING, e.getMessage());
			return new String[] { e.getMessage(), "0" };
		} finally {
			close(stmt);
			close(conn);
		}

		return ret.toArray(new String[ret.size()]);
	}
}