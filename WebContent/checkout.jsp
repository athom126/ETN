<%@ page import="java.util.*, java.io.*, thomas.halpert.etn.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	session = request.getSession(false);
	ValidatePayment payment = (ValidatePayment)session.getAttribute("payment");
	if(payment == null) {
		payment = new ValidatePayment();
	}
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
		<% if(payment != null && payment.getErrors().size() > 0) { %>
		<ul>
			<% for(int i = 0; i < payment.getErrors().size(); i++) { %>	
			<li style="color: #820b1e; list-style-type:none"><span style="left: -10px;"><%=payment.getErrors().get(i)%></span></li>
		<%	} %>	
		</ul>
		<%}%>
				<form name="form" autocomplete="off" method="post" action="http://localhost:8080/ThomasHalpertETN/ETNController">
				<h3 style="margin-bottom: 20px">Payment Details</h3>
						<u>Your card will be charged <%=reservation.getTotalCostFormatted()%>.</u><br><br>
						<label for="email" style="display:inline-block;float:left;align:right;margin-right:10px">Email: </label>
						<input style="display:inline-block;float:left;margin-left: 10px;background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;width:250px;" 
						type="email" name="email" id="email" title="Email address to where a confirmation email can be sent" value="<%=payment.getEmail()%>">
						<br>
						<label for="cardholder"  style="display:inline-block;float:left;align:right;margin-right:10px">Name of Card Holder: </label>
						<input style="display:inline-block;float:left;margin-left: 10px;background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;width:250px;"
						 type="text" name="cardholder" id="cardholder" maxLength="100" title="Name on credit card" value="<%=payment.getCCHolder()%>">
						<br>
						<label for="creditCardType" style="display:inline-block;float:left;align:center;margin-right:10px">Credit Card Type: 
						<input type="radio" value="Discover" name="creditCardType" id="creditCardType" title="Credit card company" <%= payment.getType().equals("Discover")?"checked":""%>>Discover&nbsp;
						<input type="radio" value="MasterCard" name="creditCardType" id="creditCardType" title="Credit card company" <%= payment.getType().equals("MasterCard")?"checked":""%>>MasterCard&nbsp;
						<input type="radio" value="Visa" name="creditCardType" id="creditCardType"  title="Credit card company" <%= payment.getType().equals("Visa")?"checked":""%>>VISA&nbsp;
						</label>
						<br>
						<label for="creditcardnumber" style="display:inline-block;float:left;align:right;margin-right:10px">Credit Card Number: </label>
						<input style="display:inline-block;float:left;margin-left: 10px;background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;width:250px;"
						 type="text" name="creditCardNumber" id="creditCardNumber" maxLength="16" title="Credit card number" value="<%=payment.getNumber()%>">
						<br>
						<label for="expiration" style="display:inline-block;float:left;align:right;margin-right:10px">Expiration Date: </label>
						<select name="expMonth" id="expMonth" title="Month of expiration" 
						style="display:inline-block;float:left;margin-left: 10px;background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;">
							<option value="January" <%= payment.getMonth().toLowerCase().equals("january")?"selected":""%>>January</option>
							<option value="February" <%= payment.getMonth().toLowerCase().equals("february")?"selected":""%>>February</option>
							<option value="March" <%= payment.getMonth().toLowerCase().equals("march")?"selected":""%>>March</option>
							<option value="April" <%= payment.getMonth().toLowerCase().equals("april")?"selected":""%>>April</option>
							<option value="May" <%= payment.getMonth().toLowerCase().equals("may")?"selected":""%>>May</option>
							<option value="June" <%= payment.getMonth().toLowerCase().equals("june")?"selected":""%>>June</option>
							<option value="July" <%= payment.getMonth().toLowerCase().equals("july")?"selected":""%>>July</option>
							<option value="August" <%= payment.getMonth().toLowerCase().equals("august")?"selected":""%>>August</option>
							<option value="September" <%= payment.getMonth().toLowerCase().equals("september")?"selected":""%>>September</option>
							<option value="October" <%= payment.getMonth().toLowerCase().equals("october")?"selected":""%>>October</option>
							<option value="November" <%= payment.getMonth().toLowerCase().equals("november")?"selected":""%>>November</option>
							<option value="December" <%= payment.getMonth().toLowerCase().equals("december")?"selected":""%>>December</option>
						</select>&nbsp;
						<select name="expYear" id="expYear" title="Year of expiration"
						style="display:inline-block;float:left;margin-left: 10px;background: #000000;font-family: 'Indie Flower', cursive;font-size:24px;color:#FFFFFF;background:#171616;border:0;outline:0;">
							<option value="2019" <%= payment.getYear().equals("2019")?"selected":""%>>2019</option>
							<option value="2020" <%= payment.getYear().equals("2020")?"selected":""%>>2020</option>
							<option value="2021" <%= payment.getYear().equals("2021")?"selected":""%>>2021</option>
							<option value="2022" <%= payment.getYear().equals("2022")?"selected":""%>>2022</option>
							<option value="2023" <%= payment.getYear().equals("2023")?"selected":""%>>2023</option>
							<option value="2024" <%= payment.getYear().equals("2024")?"selected":""%>>2024</option>
							<option value="2025" <%= payment.getYear().equals("2025")?"selected":""%>>2025</option>
							<option value="2026" <%= payment.getYear().equals("2026")?"selected":""%>>2026</option>
							<option value="2027" <%= payment.getYear().equals("2027")?"selected":""%>>2027</option>
							<option value="2028" <%= payment.getYear().equals("2028")?"selected":""%>>2028</option>
							<option value="2029" <%= payment.getYear().equals("2029")?"selected":""%>>2029</option>
							<option value="2030" <%= payment.getYear().equals("2030")?"selected":""%>>2030</option>
						</select>
						<br><br>
					<input type="submit" name="Go Back" id="Go Back" value="Go Back" class="button">
					<input type="submit" name="Confirm" id="Confirm" value="Confirm" class="button">
			</form>
		</div>
	</body>
</html>