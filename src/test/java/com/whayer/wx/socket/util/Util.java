package com.whayer.wx.socket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

//import snaq.db.ConnectionPool;

public class Util {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/imooc";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	//public static ConnectionPool DBPool ;

    //create pools
    /*public static void createPool() {
        try {
            Class c = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver) c.newInstance();
            DriverManager.registerDriver(driver);
            DBPool = new ConnectionPool("DBPool", 3, 30, 50, 90,
                    "jdbc:mysql://localhost:3306/filesaver?useUnicode=true&characterEncoding=utf-8", "FileHelper", "file");
        } catch (Exception e) {
            e.getStackTrace();
        }

    }*/
	
	
	
	/**
	 * <dependency>
		    <groupId>mysql</groupId>
		    <artifactId>mysql-connector-java</artifactId>
		    <version>5.1.6</version>
		</dependency>
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class c = Class.forName(DRIVER);
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 关闭资源的操作
	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
