package com.whayer.wx.jdbc;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBUtilV2 {
	private String driver;// 定义驱动
	private String url;// 定义URL
	private String user;// 定义用户名
	private String password;// 定义密码
	private Connection conn;// 定义连接
	private Statement stmt;// 定义STMT
	private ResultSet rs;// 定义结果集
	// 构造函数，默认加裁配置文件为jdbc.driver

	public DBUtilV2() {
		this("jdbc.properties");//x.properties
	}

	// 有参构造函数，传入路径Conf并用方法loadProperties进行加载，用setConn进行设置
	public DBUtilV2(String conf) {
		loadProperties(conf);
		setConn();
	}

	// 返回Conn
	public Connection getConn() {
		return this.conn;
	}

	// 传入参数路径Conf加载配置文件取得配置文件中的参数并设置为类变量的参数
	private void loadProperties(String conf) {
		/*Properties props = new Properties();
		try {
			props.load(new FileInputStream(conf));// 根据配置文件路径Conf加载配置文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		Properties props = new Properties();
	    File defaultProperties = null;
	    try {
	      java.net.URL u = DBUtilV2.class.getClassLoader().getResource("");
	      java.net.URL u2 = DBUtilV2.class.getClassLoader().getResource("/");
	      defaultProperties = new File(u == null ? u2.getPath() : u.getPath(), conf);
	      props.load(new FileInputStream(defaultProperties));
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		this.driver = props.getProperty("driver");// 从配置文件中取得相应的参数并设置类变量
		this.url = props.getProperty("url");
		this.user = props.getProperty("username");
		this.password = props.getProperty("password");
	}

	// 设置CONN
	private void setConn() {
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException classnotfoundexception) {
			classnotfoundexception.printStackTrace();
			System.err.println("db: " + classnotfoundexception.getMessage());
		} catch (SQLException sqlexception) {
			System.err.println("db.getconn(): " + sqlexception.getMessage());
		}
	}

	// 执行插入
	public boolean doInsert(String sql) {
		try {
		    stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			return i > 0;
		} catch (SQLException sqlexception) {
			System.err.println("db.executeInset:" + sqlexception.getMessage());
			return false;
		} finally {
		}
	}

	// 执行删除
	public boolean doDelete(String sql) {
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			return i > 0;
		} catch (SQLException sqlexception) {
			System.err.println("db.executeDelete:" + sqlexception.getMessage());
			return false;
		}
	}

	// 执行更新
	public boolean doUpdate(String sql) {
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			return i > 0;
		} catch (SQLException sqlexception) {
			System.err.println("db.executeUpdate:" + sqlexception.getMessage());
			return false;
		}
	}

	// 查询结果集
	public ResultSet doSelect(String sql) {
		try {
			stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException sqlexception) {
			System.err.println("db.executeQuery: " + sqlexception.getMessage());
		}
		return rs;
	}

	/**
	 * 关闭数据库结果集，数据库操作对象，数据库链接
	 * 
	 * @Function: Close all the statement and conn int this instance and close
	 *            the parameter ResultSet
	 * @Param: ResultSet
	 * @Exception: SQLException,Exception
	 **/
	public void close(ResultSet rs) throws SQLException, Exception {

		if (rs != null) {
			rs.close();
			rs = null;
		}

		if (stmt != null) {
			stmt.close();
			stmt = null;
		}

		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	/**
	 * 关闭数据库操作对象，数据库连接对象 Close all the statement and conn int this instance
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public void close() throws SQLException, Exception {
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}

		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	// 测试类
	public static void main(String[] args) {
		try {
			// 根据传入配置路径构造类
			DBUtilV2 db = new DBUtilV2();
			System.out.print(db.driver);
			// 取得连接
			Connection conn = db.getConn();
			if (conn != null && !conn.isClosed()) {
				System.out.println("連結成功");
				ResultSet rs = db.doSelect("select * from sk_user");
				while (rs.next()) {
					System.out.println(rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getString(3));
				}
				rs.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
