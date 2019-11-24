/**
 * File: ETNController.java
 * File Description: Contains the main controlling servlet for the application
 */

package thomas.halpert.etn;

import java.io.*;
import java.util.*;
import java.lang.*;

//import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

@WebServlet("/ETNController")
public class ETNController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/************************* DATABASE INFORMATION *************************
	 * The following information is needed to connect to the remote database:
	 * 
	 *		Server: sql9.freemysqlhosting.net
	 *		Username: sql9310910
	 * 		Password: 8M4rA4YdVk
	 *		Port number: 3306
	 *
	 * The same information is needed to sign into phpMyAdmin:
	 * 					http://www.phpmyadmin.co/
	**************************************************************************/
	
	/**************************** JAVA MAIL INFO ****************************
			
			Username: noys.noreply@gmail.com
			Password: n!ghtmar3
			
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtps.host", "smtp.gmail.com");
			props.put("mail.smtps.port", 465);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.quitwait", "false");
			Session session = Session.getDefaultInstance(props);
			
	**************************************************************************/
       
    public ETNController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get request parameters
		String name = request.getParameter("name");
		String numPeople = request.getParameter("numberOfPeople");
		String room = request.getParameter("escapeRoom");
		String date = "";
		// Retrieve date depending on which drop down list was displayed
		if(room.equalsIgnoreCase("Badham Preschool")) {
			date = request.getParameter("reservationDateBP");		
		}
		else if(room.equalsIgnoreCase("The Strode Residence")) {
			date = request.getParameter("reservationDateSR");		
		}
		else if(room.equalsIgnoreCase("Jigsaw's Warehouse")) {
			date = request.getParameter("reservationDateJW");
		}
		
		// Use for testing
//		String name = "Laur!e Str0de";
//		String numPeople = "three";
//		String room = "badham school";
//		String date = "OCT-32-2019";
	
		// Used to validate request parameters
		HttpSession session = request.getSession(false);
		ValidateReservation vr = (ValidateReservation)session.getAttribute("reservation");
		if(vr == null) {
			vr = new ValidateReservation();
		}
		vr.setName(name);
		vr.setNumPeople(numPeople);
		vr.setRoom(room);
		vr.setDate(date);
		session.setAttribute("reservation", vr);
		
		// Validate the parameters
		// If reservation form contain errors, display reservation form again
		if(!vr.validate(name, numPeople, room, date)) {
			this.getServletContext().getRequestDispatcher("/reservation-form.jsp").forward(request, response); 
		}
		// If reservation form contains no errors, proceed to checkout
		else {
			// TO DO: Create Receipt class and object and set the collected parameters
			this.getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response); // Display checkout page
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// If a request to create a reservation is made, invoke doGet()
		if(request.getParameter("Reserve") != null) {
			doGet(request, response);
		}
		
		// If a request to go back to the reservation form during the payment process is made, display the reservation form
		else if(request.getParameter("Go Back") != null) {
			this.getServletContext().getRequestDispatcher("/reservation-form.jsp").forward(request, response); 
		}
		
		// If a request to confirm the payment is made, validate the form, then display the payment form or confirmation page
		else if(request.getParameter("Confirm") != null) {
			// Get request parameters from payment form
			String email = request.getParameter("email");
			String cardholder = request.getParameter("cardholder");
			String ccType = request.getParameter("creditCardType");
			String ccNumber = request.getParameter("creditCardNumber");
			String expMonth = request.getParameter("expMonth");
			String expYear = request.getParameter("expYear");
			HttpSession session = request.getSession(false);
			ValidatePayment vp = (ValidatePayment)session.getAttribute("payment");
			if(vp == null) {
				vp = new ValidatePayment(); // Create ValidatePayment (VP) object to validate request parameters
			}
			vp.setEmail(email); // Set properties of VP object
			vp.setCCHolder(email);
			vp.setType(email);
			vp.setNumber(email);
			vp.setMonth(email);
			vp.setYear(email);
			session.setAttribute("payment", vp); // Set VP object as session parameter
			
			// Validate the payment form parameters, and if there are errors, display checkout form again
			if(!vp.validate(email, cardholder, ccType, ccNumber, expMonth, expYear)) {
				this.getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response);
			}
			// If there are no errors, proceed to confirmation page
			else {
				
				// Get or create a receipt
				Receipt receipt = (Receipt)session.getAttribute("receipt");
				if(receipt == null)
				{
					receipt = new Receipt();
				}
				
				// Get reservation
				ValidateReservation vr = (ValidateReservation)session.getAttribute("reservation"); 
				if(vr == null)
				{
					//shouldn't be null here, report error?
					session.setAttribute("errorMsg", "Unable to find reservation data in session");
				}
				// If reservation exists, set receipt properties
				else
				{
					receipt.setName(vr.getName());
					receipt.setEmail(vp.getEmail());
					receipt.setNumPeople(vr.getNumPeople());
					receipt.setRoom(vr.getRoom());
					receipt.setDate(vr.getDate());
					session.setAttribute("receipt", receipt);
				}
				
				// TO DO: Add the user to the database and send confirmation email
				this.getServletContext().getRequestDispatcher("/confirmation.jsp").forward(request, response); 
				
				session.invalidate(); // Invalidate session after confirmation has been sent
			}
		}
	}

}
