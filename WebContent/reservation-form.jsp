<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>    
<%@ page import="java.text.*" %>
<% 
	session = request.getSession(false);
	ValidateReservation reservation = (ValidateReservation)request.getAttribute("reservation");
	if(reservation == null) {
		reservation = new ValidateReservation();
	}
%>
<!DOCTYPE html>
<HTML>
	<head>
		<title>Nightmare On Your Street</title>
		<link href="etn-styles.css" rel="stylesheet" type ="text/css"/>
		<link href="https://fonts.googleapis.com/css?family=Poppins|Indie+Flower|Special+Elite|Nosifer&display=swap" rel="stylesheet">
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
<div>
	<% if(reservation != null && reservation.getErrors().size() > 0) { %>
		<ul>
		<% for(int i = 0; i < reservation.getErrors().size(); i++) { %>	
			<li style="color: #820b1e; list-style-type:none"><span style="left: -10px;"><%=reservation.getErrors().get(i)%></span></li>
		<%	} %>	
		</ul>
	<%}%>
	<form autocomplete="off" method="post" action="ETNController">
		<label class="firstLetter">I&nbsp;</label>,&nbsp;
		<input type="text" name="name" title="Your name" required value="<%=reservation.getName()%>"/> , <br>
		am bringing 
		<select name="numberOfPeople" id="numberOfPeople" title="Total number of people in group including yourself" required>
			<option value="1" <%= reservation.getNumPeople().equals("1")?"selected":""%>>1</option>
			<option value="2" <%= reservation.getNumPeople().equals("2")?"selected":""%>>2</option>
			<option value="3" <%= reservation.getNumPeople().equals("3")?"selected":""%>>3</option>
			<option value="4" <%= reservation.getNumPeople().equals("4")?"selected":""%>>4</option>
			<option value="5" <%= reservation.getNumPeople().equals("5")?"selected":""%>>5</option>
			<option value="6" <%= reservation.getNumPeople().equals("6")?"selected":""%>>6</option>
			<option value="7" <%= reservation.getNumPeople().equals("7")?"selected":""%>>7</option>
			<option value="8" <%= reservation.getNumPeople().equals("8")?"selected":""%>>8</option>
		</select>
	hostage(s)<br>
	to escape 
		<select name="escapeRoom" id="escapeRoom" title="Escape room choice" required>
			<option value="Badham Preschool" <%= reservation.getRoom().equals("badham preschool")?"selected":""%>>Badham Preschool</option>
			<option value="The Strode Residence" <%= reservation.getRoom().equals("the strode residence")?"selected":""%>>The Strode Residence</option>
			<option value="Jigsaw's Warehouse"<%= reservation.getRoom().equals("jigsaw's warehouse")?"selected":""%>>Jigsaw's Warehouse</option>
		</select>,<br>
		on 
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
		Statement statement = null;
		ResultSet rs = null;
		
		%>
		
		<h2 align="center"><b>Retrieving data from DB</b></h2>
		<table align="center" cellpadding="5" cellspacing="5" border="1">
		<tr> </tr>
		<tr>
			<td><b>Room</b></td>
			<td><b>Date</b></td>
			<td><b>Email</b></td>
			<td><b>Status</b></td>
		</tr>
		
		<%
		try{
			conn = DriverManager.getConnection(connectionUrl+dbName, userId, password);
			statement = conn.createStatement();
			String sql = "Select * from RESERVATION";
			
			rs = statement.executeQuery(sql);
			while(rs.next())
			{
				%>
				<tr>
				<td><%=rs.getString("room") %></td>
				<% 
				java.sql.Timestamp ts = rs.getTimestamp("date");
				java.util.Date date = ts;
				date.setHours(date.getHours()-1); //To account for time offset with DB.
				DateFormat day = new SimpleDateFormat("MM/dd/YYYY");
				DateFormat df = new SimpleDateFormat("EEEEE, MMMM dd, YYYY hh:mm aa");
				%>
				<td><%=day.format(date)%></td>
				<td><%=df.format(date)%></td>
				<td><%=rs.getString("email") %></td>
				<td><%=rs.getString("status") %></td>
				</tr>
				<%
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		%>
		</table>
		
		<select name="reservationDate" id="reservationDate" title="Desired reservation date" required>
			<option value="10-11-2019" <%= reservation.getDate().equals("10/11/2019")?"selected":""%>>Friday, October 11th, 2019 at 5:00pm</option>
			<option value="10-12-2019"<%= reservation.getDate().equals("10/12/2019")?"selected":""%>>Saturday, October 12th, 2019 at 5:00pm</option>
			<option value="10-13-2019"<%= reservation.getDate().equals("10/13/2019")?"selected":""%>>Sunday, October 13th, 2019 at 5:00pm</option>
		</select>.
		<h3>Disclaimer</h3>
		<span class="disclaimerText">By submitting this form, I acknowledge that...<br>
		<input type="checkbox" value="agreement1" required/> <label>I understand that on the day of my reservation<br>
		consent forms will need to be filled out by every hostage.</label><br>
		<input type="checkbox" value="agreement2" required/> <label>I understand that if I cancel the reservation within 24 hours<br>
		of the scheduled reservation I will not be refunded.</label></span><p>
		<input type="submit" id="Reserve" name="Reserve" value="Reserve" class="button"/>
	</form>
</div>
</body>
</html>