package thomas.halpert.etn;

import java.sql.*;

public class UserDB {
	
	public static boolean emailExists(String email)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select EMAIL from USERS where EMAIL = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
				rs = ps.executeQuery();
				return rs.next();
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			} finally {
				DBUtil.closePreparedStatement(ps);
				DBUtil.closeResultSet(rs);
			}
		}
		else
		{
			return false;
		}
	}
	
	
	
	public static boolean addUser(String email, String name)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "insert into USERS (EMAIL, NAME) values (?,?)";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
				ps.setString(2, name);
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
	

	public static boolean removeUser(String email)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "delete from Users where EMAIL = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
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
