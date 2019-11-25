<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*, thomas.halpert.etn.Receipt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	session = request.getSession(false);
	Receipt receipt = (Receipt)session.getAttribute("receipt");
%>
<!DOCTYPE html>
<HTML>
	<head>
		<title>Nightmare On Your Street</title>
		<link href="etn-styles.css" rel="stylesheet" type ="text/css"/>
		<link href="https://fonts.googleapis.com/css?family=Poppins|Indie+Flower|Special+Elite|Homemade+Apple|Nosifer&display=swap" rel="stylesheet">
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
		<p><%=receipt.getName()%>, <br>
		You have been confirmed to escape <br>
		<%=receipt.getRoom() %>,<br>
		on <br>
		<%=receipt.getUnparsedDate() %>,<br>
		with <%=receipt.getNumPeople() %> hostages.<br><br></p>
		A confirmation receipt has been sent to your email.<br>
		If you choose to cancel your reservation, it must be done at least<br>
		24 hours before your reserved time in order to be issued a refund.<br>
</div>
</body>
</html>