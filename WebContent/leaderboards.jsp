<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
				<tr><td class="column1">r1c1</td><td class="column2">r1c2</td></tr>
				<tr><td class="column1">r2c1</td><td class="column2">r2c2</td></tr>
				<tr><td class="column1">r3c1</td><td class="column2">r3c2</td></tr>
				<tr><td class="column1">r4c1</td><td class="column2">r4c2</td></tr>
			</table>
		</div>
		<hr/>
			
		<button class="toggleBadham" onclick="showBadham()" title="Click to display the leaderboard">Badham Preschool</button>
		<div class="leaderboard" id="badhamTbl" style="display: none">
			<br>
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
				<tr><td class="column1">r1c1</td><td class="column2">r1c2</td></tr>
				<tr><td class="column1">r2c1</td><td class="column2">r2c2</td></tr>
				<tr><td class="column1">r3c1</td><td class="column2">r3c2</td></tr>
				<tr><td class="column1">r4c1</td><td class="column2">r4c2</td></tr>
			</table>
		</div>
		<hr/>
			
		<button class="toggleJigsaw" onclick="showJigsaw()" title="Click to display the leaderboard">Jigsaw's Warehouse</button>
		<div class="leaderboard" id="jigsawTbl" style="display: none">
			<br>
			<table>
				<tr><th>Escape Time (minutes)</th><th>Name</th></tr>
				<tr><td class="column1">r1c1</td><td class="column2">r1c2</td></tr>
				<tr><td class="column1">r2c1</td><td class="column2">r2c2</td></tr>
				<tr><td class="column1">r3c1</td><td class="column2">r3c2</td></tr>
				<tr><td class="column1">r4c1</td><td class="column2">r4c2</td></tr>
			</table>
		</div>	
		<br><br>

	</div>
</body>
</html>