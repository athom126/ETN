package thomas.halpert.etn;

import java.sql.*;

public class ReservationDBUtil {
	
	public static void testDbConn()
	{
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		String query = "select * from RESERVATION";
		if(conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next())
				{
					System.out.println(rs.getTimestamp("date") + ", " + rs.getString("room"));
				}
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				DBUtil.closePreparedStatement(stmt);
				DBUtil.closeResultSet(rs);
			}
		}
		else
		{
			//error
		}
	}
	
	//This dateString string must be of the format "yyyy-MM-dd HH:00:00"
	public static String checkRoomStatus(String dateString, String room)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "select * from RESERVATION where Date = ? and Room = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, dateString);
				ps.setString(2, room);
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					//found reservation by date and room
					return rs.getString("status");
				}
				else
				{
					return "res not found";
				}
			} catch (SQLException e) {
				System.out.println(e);
				return "error";
			} finally {
				DBUtil.closePreparedStatement(ps);
				DBUtil.closeResultSet(rs);
			}
		}
		else
		{
			//error
			return "error";
		}
	}
	
	public static boolean holdRoom(String dateString, String room)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "UPDATE RESERVATION SET Status = 'on hold' WHERE Date = ? and Room = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, dateString);
				ps.setString(2, room);
				int numRowsUpdated = ps.executeUpdate();
				if(numRowsUpdated == 1) {
					return true;
				} else {
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
			//error
			return false;
		}
	}
	
	public static boolean removeHold(String dateString, String room)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "UPDATE RESERVATION SET Status = 'open' WHERE Date = ? and Room = ?";
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, dateString);
				ps.setString(2, room);
				int numRowsUpdated = ps.executeUpdate();
				if(numRowsUpdated == 1) {
					return true;
				} else {
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
			//error
			return false;
		}
	}
	
	//This dateString string must be of the format "yyyy-MM-dd HH:00:00"
	public static boolean setEmail(String email, String dateString, String room)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "UPDATE RESERVATION SET Email = ? WHERE Date = ? and Room = ?";
		
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
				ps.setString(2, dateString);
				ps.setString(3, room);
				int numRowsUpdated = ps.executeUpdate();
				if(numRowsUpdated == 1) {
					return true;
				} else {
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
			//error
			return false;
		}
	}
	
	//This dateString string must be of the format "yyyy-MM-dd HH:00:00"
	public static boolean confirmReservation(String dateString, String room, String confirmationNum, String email)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null, ps2 = null;
		ResultSet rs = null;

		String query = "UPDATE RESERVATION SET Confirmation_Num = ?, Status = 'reserved', Email = ? WHERE Date = ? AND Room = ?";
		String checkQuery = "SELECT * FROM RESERVATION WHERE Confirmation_Num = ?";

		if(conn != null) {
			try {
				ps2 = conn.prepareStatement(checkQuery);
				ps2.setString(1, confirmationNum);
				rs = ps2.executeQuery();

				if(rs.next()) 
				{
					System.out.println("confirmation num alrdy exists");
					//Confirmation number already exists
					return false;
				}
				else
				{
					ps = conn.prepareStatement(query);
					ps.setString(1, confirmationNum);
					ps.setString(2, email);
					ps.setString(3, dateString);
					ps.setString(4, room);
					int numRowsUpdated = ps.executeUpdate();
					System.out.println("Updated " + numRowsUpdated + " rows for confirmationNum with room " + room + " and date = " + dateString);
					if(numRowsUpdated == 1) {
						return true;
					} else {
						return false;
					}
				}
			} catch (SQLException e) {
				System.out.println(e);
				return false;
			} finally {
				DBUtil.closePreparedStatement(ps);
				DBUtil.closePreparedStatement(ps2);
				DBUtil.closeResultSet(rs);
			}
		}
		else
		{
			//error
			return false;
		}
	}
	
	public static boolean cancelReservation(String confirmationNum)
	{
		Connection conn = getConnection();
		PreparedStatement ps = null;
		
		String query = "UPDATE RESERVATION SET Email = NULL, Status = 'open', Confirmation_Num = NULL WHERE Confirmation_Num = ?";
		
		if(conn != null) {
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, confirmationNum);
				int numRowsUpdated = ps.executeUpdate();
				if(numRowsUpdated == 1) {
					return true;
				} else {
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
			//error
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
