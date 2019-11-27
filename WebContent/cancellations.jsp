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
			Enter the confirmation number of the <br>
			reservation you would like to cancel.
			<input style="background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;width:250px;" 
			type="text" name="confirmationNo" id="confirmationNo" title="The confirmation number of the reservation to be cancelled."/>
			<input type="submit" name="Cancel" value="Cancel" class="cancelButton"><br>
			<% 
				// Check if an attempt to cancel exists
				if(request.getAttribute("cancellationResult") != null) {
					boolean cancelResult = (boolean)request.getAttribute("cancellationResult");
					// Success
					if(cancelResult) { 
			%>
						<span style="color: #820b1e; list-style-type:none; font-family: 'Poppins', sans-serif;font-size: 15px">Your reservation has been canceled and your<br>
						refund will be processed within 2-3 business days.</span>
					<% } 
					// Failure
				   	else {
					   	String errorMessage = request.getParameter("cancellationError");
					%>
						<ul><li><span class="cancel"><%= errorMessage %></span></li></ul>
				 <% } 
				 } %>
		</form>
	</div>
</body>
</html>