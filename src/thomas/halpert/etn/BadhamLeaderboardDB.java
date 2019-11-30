package thomas.halpert.etn;

import java.sql.*;

public class BadhamLeaderboardDB {
	
	public static boolean editEntry(String id, String user, String timeToEscape)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "UPDATE BADHAM SET TIME = ?, NAME = ? WHERE ID = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, timeToEscape);
				ps.setString(2, user);
				ps.setString(3, id);
				int updatedRows = ps.executeUpdate();
				if(updatedRows == 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			} finally {
				DBUtil.closePreparedStatement(ps);
			}
		}
		else
		{
			return false;
		}
	}
	
	public static boolean addEntry(String user, String timeToEscape)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "INSERT INTO BADHAM (TIME, NAME) values (?,?)";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, timeToEscape);
				ps.setString(2, user);
				int updatedRows = ps.executeUpdate();
				if(updatedRows == 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			} finally {
				DBUtil.closePreparedStatement(ps);
			}
		}
		else
		{
			return false;
		}
	}
	

	public static boolean removeEntry(String id)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "DELETE FROM BADHAM WHERE ID = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, id);
				if(ps.executeUpdate() == 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			} finally {
				DBUtil.closePreparedStatement(ps);
			}
		}
		else
		{
			return false;
		}
	}

	private static Connection getConnection()
	{
		Connection conn = null;
		String driverName = "com.mysql.cj.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://sql9.freemysqlhosting.net:3306";
		String dbName = "/sql9310910";
		String userId = "sql9310910";
		String password = "8M4rA4YdVk";
		try {
			Class.forName(driverName);
			String dbURL = connectionUrl + dbName;
			conn = DriverManager.getConnection(dbURL, userId, password);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
