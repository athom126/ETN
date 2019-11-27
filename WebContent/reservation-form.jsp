<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>    
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.*" %>
<% 
	session = request.getSession(false);
	ValidateReservation reservation = (ValidateReservation)session.getAttribute("reservation");
	if(reservation == null) {
		reservation = new ValidateReservation();
	}
%>
<!DOCTYPE html>
<HTML>
	<head>
		<title>Nightmare On Your Street</title>
		<link href="etn-styles.css" rel="stylesheet" type ="text/css"/>
		<link href="https://fonts.googleapis.com/css?family=Poppins|Indie+Flower|Special+Elite|Nosifer|Homemade+Apple&display=swap" rel="stylesheet">
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
		<select name="escapeRoom" id="escapeRoom" title="Escape room choice" required onchange="onRoomSelectionChange()">
			<option value="Badham Preschool" <%= reservation.getRoom().equalsIgnoreCase("badham preschool")?"selected":""%>>Badham Preschool</option>
			<option value="The Strode Residence" <%= reservation.getRoom().equalsIgnoreCase("the strode residence")?"selected":""%>>The Strode Residence</option>
			<option value="Jigsaw's Warehouse" <%= reservation.getRoom().equalsIgnoreCase("jigsaw's warehouse")?"selected":""%>>Jigsaw's Warehouse</option>
		</select>,<br>
		on <br>
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
		String selectedRoom = "Badham Preschool"; //default to first option in list
		if(!reservation.getRoom().equalsIgnoreCase(""))
		{
			//If reservation exists with a room, it will be selected.
			selectedRoom = reservation.getRoom(); 
		}
		try{
			conn = DriverManager.getConnection(connectionUrl+dbName, userId, password);
			String query = "SELECT * FROM RESERVATION WHERE Room = ? AND Status = 'open' AND Date > ?";
			//ToDo: if current session has a room on hold, 
			ps = conn.prepareStatement(query);
			
		%>
		<select name="reservationDateBP" id="reservationDateBP" title="Desired reservation date" required <%= selectedRoom.equalsIgnoreCase("Badham Preschool")?"":"style=\"display:none\""%>>
		<%
		    LocalDateTime today = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			ps.setString(1, "Badham Preschool");
			ps.setString(2, today.format(formatter));
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				java.sql.Timestamp ts = rs.getTimestamp("date");
				java.util.Date date = ts;
				//date.setHours(date.getHours()-1); //To account for time offset with DB.
				DateFormat day = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
				DateFormat df = new SimpleDateFormat("EEEEE, MMMM dd, yyyy 'at' h:mm aa");
				
				if(selectedRoom.equalsIgnoreCase("Badham Preschool")) 
				{
					%>
					<option value=<%=day.format(date)%> <%= reservation.getDate().equals(day.format(date))?"selected":""%>><%=df.format(date)%></option>
					<%
				}
				else
				{
					//If this room wasn't previously selected, don't pre-select anything
					%>
					<option value=<%=day.format(date)%>><%=df.format(date)%></option>
					<%
				}
			}
			if(rs != null) {
				rs.close();
			}
		%>
		</select><select name="reservationDateSR" id="reservationDateSR" title="Desired reservation date" required <%= selectedRoom.equalsIgnoreCase("The Strode Residence")?"":"style=\"display:none\""%>>
		<%
			
			ps.setString(1, "The Strode Residence");
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				java.sql.Timestamp ts = rs.getTimestamp("date");
				java.util.Date date = ts;
				//date.setHours(date.getHours()-1); //To account for time offset with DB.
				DateFormat day = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
				DateFormat df = new SimpleDateFormat("EEEEE, MMMM dd, yyyy 'at' h:mm aa");
				
				if(selectedRoom.equalsIgnoreCase("The Strode Residence")) 
				{
					%>
					<option value=<%=day.format(date)%> <%= reservation.getDate().equals(day.format(date))?"selected":""%>><%=df.format(date)%></option>
					<%
				}
				else
				{
					//If this room wasn't previously selected, don't pre-select anything
					%>
					<option value=<%=day.format(date)%>><%=df.format(date)%></option>
					<%
				}
			}
			if(rs != null) {
				rs.close();
			}
		%>
		</select><select name="reservationDateJW" id="reservationDateJW" title="Desired reservation date" required <%= selectedRoom.equalsIgnoreCase("Jigsaw's Warehouse")?"":"style=\"display:none\""%>>
		<%
			
			ps.setString(1, "Jigsaws Warehouse");
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				java.sql.Timestamp ts = rs.getTimestamp("date");
				java.util.Date date = ts;
				//date.setHours(date.getHours()-1); //To account for time offset with DB.
				DateFormat day = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
				DateFormat df = new SimpleDateFormat("EEEEE, MMMM dd, yyyy 'at' h:mm aa");
				
				if(selectedRoom.equalsIgnoreCase("Jigsaw's Warehouse")) 
				{
					%>
					<option value=<%=day.format(date)%> <%= reservation.getDate().equals(day.format(date))?"selected":""%>><%=df.format(date)%></option>
					<%
				}
				else
				{
					//If this room wasn't previously selected, don't pre-select anything
					%>
					<option value=<%=day.format(date)%>><%=df.format(date)%></option>
					<%
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
		%>
		</select>.
		<h3>Disclaimer</h3>
		<p class="disclaimerText">By submitting this form, I acknowledge that...<br>
		<input type="checkbox" value="agreement1" required/> <label>I understand that on the day of my reservation<br>
		consent forms will need to be filled out by every hostage.</label><br>
		<input type="checkbox" value="agreement2" required/> <label>I understand that if I cancel the reservation within 24 hours<br>
		of the scheduled reservation I will not be refunded.</label><p>
		<input type="submit" id="Reserve" name="Reserve" value="Reserve" class="button"/>
	</form>
</div>
</body>
</html>