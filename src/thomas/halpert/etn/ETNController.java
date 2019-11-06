/**
 * File: ETNController.java
 * File Description: Contains the main controlling servlet for the application
 */

package thomas.halpert.etn;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
       
    public ETNController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get request parameters
		String name = request.getParameter("name");
		String numPeople = request.getParameter("numberOfPeople");
		String room = request.getParameter("escapeRoom");
		String date = request.getParameter("reservationDate");
		
//		String name = "Invalid 1";
//		String numPeople = "Three";
//		String room = "Invalid name 6";
//		String date = "OCT-31-2019";
//		
		// Used to validate request parameters
		ValidateReservation vr = new ValidateReservation();
		
		// Validate the parameters
		// If reservation form contain errors, display reservation form again
		if(!vr.validate(name, numPeople, room, date)) {
			ArrayList<String> errors = vr.getErrors();
			request.setAttribute("errors", errors); 
			this.getServletContext().getRequestDispatcher("/reservation-form.jsp").forward(request, response); 
		}
		// If reservation form contains no errors, proceed to checkout
		else {
			response.sendRedirect("/checkout.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// If a request to create a reservation is made, invoke doGet()
		if(request.getParameter("Reserve") != null) {
			doGet(request, response);
		}
		
		// If a request to go back during the payment process is made, display the reservation form
		else if(request.getParameter("Go Back") != null) {
			this.getServletContext().getRequestDispatcher("/reservation-form.jsp").forward(request, response); 
		}
		
		// If a request to confirm payment is made, validate the payment form and display the payment form or confirmation page
		else if(request.getParameter("Confirm") != null) {
			// Get request parameters from payment form
			String email = request.getParameter("email");
			String cardholder = request.getParameter("cardholder");
			String ccType = request.getParameter("creditCardType");
			String ccNumber = request.getParameter("creditCardNumber");
			String expMonth = request.getParameter("expMonth");
			String expYear = request.getParameter("expYear");
			ValidatePayment vp = new ValidatePayment(); // Create ValidatePayment object to validate request parameters
			
			// Validate the payment form parameters, and if there are errors, display checkout form again
			if(!vp.validate(email, cardholder, ccType, ccNumber, expMonth, expYear)) {
				ArrayList<String> errors = vp.getErrors();
				request.setAttribute("errors", errors);
				this.getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response); 
			}
			// If there are no errors, proceed to checkout
			else {
				this.getServletContext().getRequestDispatcher("/confirmation.jsp").forward(request, response);
			}
		}
	}

}
