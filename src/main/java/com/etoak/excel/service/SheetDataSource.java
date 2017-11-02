package com.etoak.excel.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SheetDataSource {
	public static   Connection  getConn() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		 return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
	}
	public static ResultSet selectAllDataFromDB() throws Exception{
		Connection conn = getConn();
		Statement sm = conn.createStatement();
		return sm.executeQuery("select * from user");
	}
}
