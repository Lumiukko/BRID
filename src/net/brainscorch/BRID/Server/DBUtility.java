package net.brainscorch.BRID.Server;

import java.sql.*;

public class DBUtility {
	private final static String DATABASE_NAME = "images2.db";
	private final static String TABLE_NAME = "images";
	
	private static Connection con = null;
	
	
	/*
	public static void main( String args[] )
	{
		connect();
		if (checkIfTableExists()) System.println("Table found.");
		disconnect();
	}
	*/
	
	private static boolean checkIfTableExists() {
		try {
			Statement st = con.createStatement();
			String sql = String.format(
				"SELECT name"
				+ " FROM sqlite_master"
				+ " WHERE type='table'"
				+ " AND name='%s';", TABLE_NAME);
			
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {
				String tableName = res.getString("name");
				if (tableName.equals(TABLE_NAME))
					return true;
			}
		}
		catch (SQLException e) {
			System.err.printf("Error: %s\n", e.getMessage());
			
		}
		return false;
	}
		
	private static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			System.out.println("BRID Server: Opened database.");
			checkIfTableExists();
		}
		catch (ClassNotFoundException e) {
			System.err.printf("Error: Class not found: %s\n", e.getMessage());
		}
		catch (SQLException e) {
			System.err.printf("Error: %s\n", e.getMessage());
		}
	}
	
	private static void disconnect() {
		try {
			con.close();
		}
		catch (SQLException e) {
			System.err.printf("Error: %s\n", e.getMessage());
		}
	}
}
