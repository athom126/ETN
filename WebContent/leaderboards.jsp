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
		<h3>Leaderboards</h3>
		<span style="font-size:20px">The leaderboards below display the survivors who were the quickest to escape.<br>
		They are updated every month. Do you have what it takes to make it on here?<br></span>
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
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
			<%
					String query = "SELECT TIME, NAME FROM STRODE ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
				<tr><td class="column1"><%=time%></td><td class="column2"><%=name%></td></tr>
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
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
			<%
					String query = "SELECT TIME, NAME FROM BADHAM ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
				<tr><td class="column1"><%=time%></td><td class="column2"><%=name%></td></tr>
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
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
			<%
					String query = "SELECT TIME, NAME FROM JIGSAW ORDER BY TIME ASC;";
					ps = conn.prepareStatement(query);
					rs = ps.executeQuery();
					while(rs.next()) {
						int time = rs.getInt("TIME");
						String name = rs.getString("NAME");
			%>
				<tr><td class="column1"><%=time%></td><td class="column2"><%=name%></td></tr>
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
	<hr><div style="background-color:#121212;align:center"><a href="index.html">Home</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="login.jsp">Administrator</a></div>
</body>
</html>