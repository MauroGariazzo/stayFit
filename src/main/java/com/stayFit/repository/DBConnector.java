package com.stayFit.repository;

import java.sql.*;

public class DBConnector {
		
	private final String url = "jdbc:mysql://localhost:3306/stayfit";
	private final String username = "root";
	private final String password = "Bitcamp_0";
	
	/*public DBConnector(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}*/

	public Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(url, username, password);
		} 
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
	}

	public Statement getStatementObj() throws SQLException {
		try {
			return getConnection().createStatement();
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
	}
	
	public PreparedStatement getPreparedStatementObj(String query) throws SQLException {
		try {
			return getConnection().prepareStatement(query);
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
	}
	
	
	public ResultSet getResultSetObj(String query) throws SQLException{
		try {
			return getPreparedStatementObj(query).executeQuery();
		}
		catch(Exception ex) {
			throw new SQLException(ex.getMessage());
		}
	}
	
	public void closeConnection() {
		
	}
}
