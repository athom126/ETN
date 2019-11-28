<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>    
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<HTML>
	<head>
		<title>Nightmare On Your Street</title>
		<link href="etn-styles.css" rel="stylesheet" type ="text/css"/>
		<link href="https://fonts.googleapis.com/css?family=Poppins|Indie+Flower|Special+Elite|Playfair+Display+SC:400,700|Schoolbell|Averia+Gruesa+Libre|Raleway:300|Nosifer&display=swap" rel="stylesheet">
		<script src="etn.js"></script>
</head>

<body oncontextmenu="return false;" class="">
	<ul id="nav">
		<li><a href="index.html">Home</a></li>
		<li><a href="reservation-form.jsp">Reservations</a></li>
		<li>
			<a href="">Escape Rooms</a>
			<ul>
				<li><a href="badham.html">Badham Preschool</a></li>
				<li><a href="strode.html">The Strode Residence</a></li>
				<li><a href="jigsaw.html">Jigsaw's Warehouse</a></li>
			</ul>
		</li>
		<li><a href="leaderboards.jsp">Leaderboards</a></li>
		<li><a href="contact.html">Contact</a></li>
	</ul>
	<img src="resources/site-banner.jpg" class="banner" align="middle" width="100%" height="100%" alt="Nightmare On Your Street"/>
	<div class="leaderboardPage">
		<h3>Administrator</h3>
		<hr/>
		<button class="toggleStrode" onclick="showStrode()" title="Click to display the leaderboard">The Strode Residence</button>
		<div class="leaderboard" id="strodeTbl">
			<br>
			<%
				String driverName = "com.mysql.cj.jdbc.Driver";
				String connectionUrl = "jdbc:mysql://sql9.freemysqlhosting.net:3306";
				String dbName = "/sql9310910";
				String userId = "sql9310910";
				String password = "8M4rA4YdVk";
				
				try {
					Class.forName(driverName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					conn = DriverManager.getConnection(connectionUrl+dbName, userId, password);
			%>
			<form name="add-form" id="add-form" method="post" action="ETNController">
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;margin-right:10px">Escape Time (min): </label>
				<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
				style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Time to escape in minutes"><br>
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px; margin-right:10px;">Name: </label>
				<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
				style="width:200px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Name of the user"><br>
				<input type="hidden" id="tableName" name="tableName" value="SAMPLE">
				<input type="submit" name="addEntry" id="addEntry" value="Add" class="addButton">
			</form><br>
			<table style="padding:none;table-layout:fixed;width:100%">
				<tr><th>Escape Time<br>(minutes)</th><th>Name</th><th>Operations</th></tr>
			<%
					String query = "SELECT TIME, NAME, ID FROM SAMPLE ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int entryId = rs.getInt("ID");
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
						<tr>
							<form name="edit-form" id="edit-form" method="post" action="ETNController">
								<td class="column1">
									<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
									style="padding:0px;margin:0px;width:33%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=time%>" title="Time to escape in minutes">
								</td>
								<td class="column2">
									<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
									style="padding:0px;margin:0px;width:80%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=name%>" title="Name of the user">
								</td>
								<td class="td">
									<input type="hidden" name="entryToEdit" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="SAMPLE">
									<input type="submit" name="editEntry" id="editEntry" value="Edit" class="editButton">
							</form>
								<form name="delete-form" id="delete-form" method="post" action="ETNController">
									<input type="hidden" name="entryToDelete" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="SAMPLE">
									<input type="submit" name="deleteEntry" id="deleteEntry" value="Delete" class="deleteButton">
								</form>
							</td>
						</tr>
			<%		
					}
				}
				finally {
					conn.close();
					ps.close();
					rs.close();
				}
			%>
			</table>
		</div>
		<hr/>
			
		<button class="toggleBadham" onclick="showBadham()" title="Click to display the leaderboard">Badham Preschool</button>
		<div class="leaderboard" id="badhamTbl" style="display: none"><br>
<%				
				try {
					Class.forName(driverName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				conn = null;
				ps = null;
				rs = null;
				try {
					conn = DriverManager.getConnection(connectionUrl+dbName, userId, password);
			%>
			
			<form name="add-form" id="add-form" method="post" action="ETNController">
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;margin-right:10px">Escape Time (min): </label>
				<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
				style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Time to escape in minutes"><br>
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px; margin-right:10px;">Name: </label>
				<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
				style="width:200px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Name of the user"><br>
				<input type="hidden" id="tableName" name="tableName" value="BADHAM">
				<input type="submit" name="addEntry" id="addEntry" value="Add" class="addButton">
			</form><br>
			<table style="padding:none;table-layout:fixed;width:100%">
				<tr><th>Escape Time<br>(minutes)</th><th>Name</th><th>Operations</th></tr>
			<%
					String query = "SELECT TIME, NAME, ID FROM BADHAM ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int entryId = rs.getInt("ID");
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
						<tr>
							<form name="edit-form" id="edit-form" method="post" action="ETNController">
								<td class="column1">
									<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
									style="padding:0px;margin:0px;width:33%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=time%>" title="Time to escape in minutes">
								</td>
								<td class="column2">
									<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
									style="padding:0px;margin:0px;width:80%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=name%>" title="Name of the user">
								</td>
								<td class="td">
									<input type="hidden" name="entryToEdit" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="BADHAM">
									<input type="submit" name="editEntry" id="editEntry" value="Edit" class="editButton">
							</form>
								<form name="delete-form" id="delete-form" method="post" action="ETNController">
									<input type="hidden" name="entryToDelete" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="BADHAM">
									<input type="submit" name="deleteEntry" id="deleteEntry" value="Delete" class="deleteButton">
								</form>
							</td>
						</tr>
			<%		
					}
				}
				finally {
					conn.close();
					ps.close();
					rs.close();
				}
			%>
			</table>
		</div>
		<hr/>
			
		<button class="toggleJigsaw" onclick="showJigsaw()" title="Click to display the leaderboard">Jigsaw's Warehouse</button>
		<div class="leaderboard" id="jigsawTbl" style="display: none"><br>
			<%
				try {
					Class.forName(driverName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				conn = null;
				ps = null;
				rs = null;
				try {
					conn = DriverManager.getConnection(connectionUrl+dbName, userId, password);
			%>
			
			<form name="add-form" id="add-form" method="post" action="ETNController">
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;margin-right:10px">Escape Time (min): </label>
				<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
				style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Time to escape in minutes"><br>
				<label style="width:50px;font-family: 'Poppins', sans-serif;font-size: 15px; margin-right:10px;">Name: </label>
				<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
				style="width:200px;font-family: 'Poppins', sans-serif;font-size: 15px;" title="Name of the user"><br>
				<input type="hidden" id="tableName" name="tableName" value="JIGSAW">
				<input type="submit" name="addEntry" id="addEntry" value="Add" class="addButton">
			</form><br>
			<table style="padding:none;table-layout:fixed;width:100%">
				<tr><th>Escape Time<br>(minutes)</th><th>Name</th><th>Operations</th></tr>
			<%
					String query = "SELECT TIME, NAME, ID FROM JIGSAW ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int entryId = rs.getInt("ID");
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
						<tr>
							<form name="edit-form" id="edit-form" method="post" action="ETNController">
								<td class="column1">
									<input type="text" name="timeToEscape" id="timeToEscape" maxLength="3" required 
									style="padding:0px;margin:0px;width:33%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=time%>" title="Time to escape in minutes">
								</td>
								<td class="column2">
									<input type="text" name="nameOfUser" id="nameOfUser" maxLength="30" required 
									style="padding:0px;margin:0px;width:80%;font-family: 'Poppins', sans-serif; font-size: 15px;" 
									value="<%=name%>" title="Name of the user">
								</td>
								<td class="td">
									<input type="hidden" name="entryToEdit" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="JIGSAW">
									<input type="submit" name="editEntry" id="editEntry" value="Edit" class="editButton">
							</form>
								<form name="delete-form" id="delete-form" method="post" action="ETNController">
									<input type="hidden" name="entryToDelete" value="<%=entryId%>">
									<input type="hidden" name="tableName" id="tableName" value="JIGSAW">
									<input type="submit" name="deleteEntry" id="deleteEntry" value="Delete" class="deleteButton">
								</form>
							</td>
						</tr>
			<%		
					}
				}
				finally {
					conn.close();
					ps.close();
					rs.close();
				}
			%>
			</table>
		</div>	
		<br><br>

	</div>
</body>
</html>