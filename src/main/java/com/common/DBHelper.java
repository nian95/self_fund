package com.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	public static final String driver = "oracle.jdbc.driver.OracleDriver";
	public static final String url = "jdbc:oracle:thin:@localhost:1521:LOCAL";
	public static final String user = "nian";
	public static final String password = "nian";
	
	public Connection conn = null;
	public PreparedStatement preparedStatement = null;
	public Statement statement = null;
	
	public DBHelper(String sql ) {
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			preparedStatement = conn.prepareStatement(sql);
			statement = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {

		try {
			this.preparedStatement.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
