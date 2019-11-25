<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<form autocomplete="off" method="post" action="ETNController">
			<h3>Cancel Reservations</h3>
			To cancel enter your email address.
			<input style="background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;width:250px;" 
			type="text" name="username" id="username" title="Enter the email to look up your reservations"/><br><br>
			<table>
				<tr><th>Escape Room</th><th>Date of Reservation</th></tr>
					<tr><td>room</td><td>date</td>
					<td class="td">
						<form method="post" action="ETNController">
							<input type="submit" name="cancel" value="Cancel" class="cancelButton">
							<input type="hidden" name="courseToRemove" value="date">
						</form>
					</td></tr>
			</table>
		</form>
	</div>
</body>
</html>