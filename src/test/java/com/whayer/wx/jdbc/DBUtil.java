package com.whayer.wx.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class DBUtil {
	
	private static final String URL = "jdbc:mysql://localhost:3306/SKYG";
	private static final String USER = "root";
	private static final String PASSWORD = "duyu2008";
	
	public static void main(String[] args) throws Exception {
		//加载mysql驱动
		Class.forName("com.mysql.jdbc.Driver");
		//加载数据库连接
		java.sql.Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		Statement statement = conn.createStatement();
		
		ResultSet set = statement.executeQuery("select username,password,mobile from sk_user limit 10");
		while (set.next()) {
			System.out.println(set.getString("username")+", "+set.getString("password")+", "+ set.getString("mobile") );
		}
	}
}
