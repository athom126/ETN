<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
				<li style="color: red; align: center; text-indent: -4px;"><%=reservation.getErrors().get(i)%></li>
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