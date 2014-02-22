package com.bill99.yn.webmgmt.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestPrepareStatement {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void testPreparementStatementExecuteQuery() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/webmgmt?"
					+ "user=root&password=liudonghua");

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("select content from ss_log where log_action = ?");
			preparedStatement.setString(1, "STORE_DATA");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// It is possible to get the columns via name
				// also possible to get the columns via the column number
				// which starts at 1
				// e.g. resultSet.getSTring(2);
				System.out.println("Content: " + resultSet.getString("content"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
	
	public static void main(String[] args) {
		TestPrepareStatement test = new TestPrepareStatement();
		try {
			test.testPreparementStatementExecuteQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
