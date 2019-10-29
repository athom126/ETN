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
		<label class="firstLetter">I&nbsp;</label>,&nbsp;
		<input type="text" name="name" title="Your name" required/> , <br>
		am bringing 
		<select name="numberOfPeople" id="numberOfPeople" title="Total number of people in group including yourself" required>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
		</select>
	hostage(s)<br>
	to escape 
		<select name="escapeRoom" id="escapeRoom" title="Escape room choice" required>
			<option value="Badham">Badham Preschool</option>
			<option value="Strode">The Strode Residence</option>
			<option value="Jigsaw">Jigsaw's Warehouse</option>
		</select>,<br>
		on 
		<select name="reservationDate" id="reservationDate" title="Desired reservation date" required>
			<option value="10-11-2019">Friday, October 11th, 2019 at 5:00pm</option>
			<option value="10-12-2019">Saturday, October 12th, 2019 at 5:00pm</option>
			<option value="10-13-2019">Sunday, October 13th, 2019 at 5:00pm</option>
		</select>.
		<h3>Disclaimer</h3>
		<p class="disclaimerText">By submitting this form, I acknowledge that...<br>
		<input type="checkbox" value="agreement1" required/> <label>I understand that on the day of my reservation<br>
		consent forms will need to be filled out by every hostage.</label><br>
		<input type="checkbox" value="agreement2" required/> <label>I understand that if I cancel the reservation within 24 hours<br>
		of the scheduled reservation I will not be refunded.</label></p>
		<input type="submit" value="Reserve" class="button"/>
	</form>
</div>
</body>
</html>