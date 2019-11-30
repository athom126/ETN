/**
 * File: ETNController.java
 * File Description: Contains the main controlling servlet for the application
 */

package thomas.halpert.etn;

import java.io.*;
import java.util.*;
import java.text.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thomas.halpert.etn.ReservationDBUtil;

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
					
			SimpleDateFormat sdf = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
			SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			Date date1 = null;
			try {
				date1 = sdf.parse(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String dateString = dbSdf.format(date1);
			// Put a hold on currently selected room while payment is processed
			ReservationDBUtil.holdRoom(dateString, room);
			
//			this.getServletContext().getRequestDispatcher("/checkout.jsp").forward(request, response); // Display checkout page
			response.sendRedirect("https://localhost:8443/ThomasHalpertETN/checkout.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// If a request to create a reservation is made, invoke doGet()
		if(request.getParameter("Reserve") != null) {
			doGet(request, response);
		}
		
		// If a request to go back to the reservation form during the payment process is made, display the reservation form
		else if(request.getParameter("Go Back") != null) {
			HttpSession session = request.getSession(false);
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
			SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			ValidateReservation res = (ValidateReservation) session.getAttribute("reservation");
			Date date1 = null;
			try {
				date1 = sdf.parse(res.getDate());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String dateString = dbSdf.format(date1);
			
			// Release hold on currently selected room
			ReservationDBUtil.removeHold(dateString, res.getRoom());
			
			// Get request parameters from payment form
			String email = request.getParameter("email");
			String cardholder = request.getParameter("cardholder");
			String ccType = request.getParameter("creditCardType");
			String ccNumber = request.getParameter("creditCardNumber");
			String expMonth = request.getParameter("expMonth");
			String expYear = request.getParameter("expYear");
			
			ValidatePayment vp = (ValidatePayment)session.getAttribute("payment");
			if(vp == null) {
				vp = new ValidatePayment(); // Create ValidatePayment (VP) object to validate request parameters
			}
			vp.setEmail(email); // Set properties of VP object
			vp.setCCHolder(cardholder);
			if(ccType == null) {
				//This parameter can return null in this path
				vp.setType(""); 
			} else {
				vp.setType(ccType);
			}
			vp.setNumber(ccNumber);
			vp.setMonth(expMonth);
			vp.setYear(expYear);
			session.setAttribute("payment", vp); // Set VP object as session parameter
						
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
			vp.setCCHolder(cardholder);
			vp.setType(ccType);
			vp.setNumber(ccNumber);
			vp.setMonth(expMonth);
			vp.setYear(expYear);
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
					receipt.setTotalCost(vr.getTotalCost());
				}
				
				// Add the user to the database
				if(!UserDB.emailExists(receipt.getEmail()))
				{
					UserDB.addUser(receipt.getEmail(), receipt.getName());
				}
				
				// Set the email column of the Reservation table to the email that the reservation is under
				SimpleDateFormat sdf = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
				SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
				Date date = null;
				try {
					date = sdf.parse(receipt.getUnparsedDate());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int confirmationNum = 10000000; //minimum confirmation number
				Random random = new Random();
				
				if(date != null) {	
					// "EEEEE-MMMM-dd-YYYY-h-aa" -> "yyyy-MM-dd HH:00:00"
					String dateString = dbSdf.format(date);
					
					// Generate a unique confirmation number
					boolean uniqueNumFound = false;
					int numTries = 1;
					while(!uniqueNumFound) {
						confirmationNum = random.nextInt(89999999) + 10000000;
						// addConfirmationNum returns false is number already exists
						if(ReservationDBUtil.confirmReservation(dateString, receipt.getRoom(), Integer.toString(confirmationNum), receipt.getEmail()));
						{
							uniqueNumFound = true;
						}
						if(numTries++ > 5) {
							uniqueNumFound = true; //break to stop endless loop (not expected to occur)
						}
					}
				} 
				else {
					request.getSession().setAttribute("errorMsg", "ERROR: unable to add email to Reservation table");
				}
				receipt.setConfirmationNum(Integer.toString(confirmationNum));
				session.setAttribute("receipt", receipt);
				
				// Send confirmation email to user
				String fromAddr = "noys.noreply@gmail.com";
				String toAddr = receipt.getEmail();
				String subj = "Nightmare on Your Street Reservation Confirmation";
				String body = "Dear " + receipt.getName() + ",\n\n" 
						+ "You are confirmed to be bringing " + receipt.getNumPeople() + " hostage(s) to " + receipt.getRoom() 
						+ " on " + receipt.getDate() + ". \nYour confirmation number is " + confirmationNum;
				try {
					MailHelper.sendMail(toAddr, fromAddr, subj, body);
				} catch (MessagingException e) {
					request.getSession().setAttribute("errorMsg", "ERROR: Unable to send mail. " + e.getMessage());
				}
				
				// Finally, display confirmation page after sending email to user and adding user to database
				this.getServletContext().getRequestDispatcher("/confirmation.jsp").forward(request, response); 
				
				session.invalidate(); // Invalidate session after confirmation page is displayed
			}
		}
		// A request to modify the leader board is made
		else if(request.getParameter("operation") != null) {
			String operation = request.getParameter("operation");
			if(operation.equals("Edit")) {
				if(request.getParameter("entryToEdit") != null && request.getParameter("timeToEscape") != null
					&& request.getParameter("nameOfUser") != null && request.getParameter("tableName") != null) {
					String id = request.getParameter("entryToEdit"); // ID of entry being modified
					String tableName = request.getParameter("tableName"); // The name of table being modified
					String user = request.getParameter("nameOfUser"); // The name of the user for this entry
					String timeToEscape = request.getParameter("timeToEscape"); // The escape time in minutes
					try {
						Integer.parseInt(timeToEscape); // Check if the time and ID are parsable as an integer
						Integer.parseInt(id);
						// Check if the time is within a valid range
						if(Integer.parseInt(timeToEscape) >= 1 && Integer.parseInt(timeToEscape) <= 999) {
							// Check the table name is valid
							if(tableName.equals("JIGSAW") || tableName.equals("STRODE") || tableName.equals("BADHAM")) {
								boolean validName = true;
								// Check if the name of the user is valid
								char[] nameArray = user.toCharArray();
								for(Character c : nameArray) {
									// Ensure that name contains only letters and spaces
									if(!Character.isLetter(c) && c != ' ') {
										validName = false;
										break;
									}
								}
								if(validName && user.trim().length() != 0) {
									if(tableName.equals("JIGSAW"))
									{
										if(!JigsawLeaderboardDB.editEntry(id, user, timeToEscape))
										{
											request.setAttribute("error", "Unable to edit selected entry.");
										}
									} 
									else if(tableName.equals("STRODE"))
									{
										if(!StrodeLeaderboardDB.editEntry(id, user, timeToEscape))
										{
											request.setAttribute("error", "Unable to edit selected entry.");
										}
									} 
									else // "BADHAM"
									{
										if(!BadhamLeaderboardDB.editEntry(id, user, timeToEscape))
										{
											request.setAttribute("error", "Unable to edit selected entry.");
										}
									}
								}
								else {
									request.setAttribute("error", "Please enter a valid name in the name field.");
								}
							}
							// Table doesn't exist currently
							else {
								request.setAttribute("error", "Please enter a valid table name.");
							}
						}
						// Out of range
						else {
							request.setAttribute("error", "The value of escape time must be between 1 and 999 minutes.");
						}
					}
					catch(NumberFormatException nfe) {
						request.setAttribute("error", "Please enter a 1 to 3 digit number for the escape time.");
					}
				}
				else {
					request.setAttribute("error", "Please enter an escape time and a name.");
				}
			}
			else if(operation.equals("Add")) {
				if(request.getParameter("timeToEscape") != null && request.getParameter("nameOfUser") != null && request.getParameter("tableName") != null) {
					String tableName = request.getParameter("tableName"); // The name of table being modified
					String user = request.getParameter("nameOfUser"); // The name of the user for this entry
					String timeToEscape = request.getParameter("timeToEscape"); // The escape time in minutes
					try {
						Integer.parseInt(timeToEscape); // Check if the time is parsable as an integer
						// Check if the time is within a valid range
						if(Integer.parseInt(timeToEscape) >= 1 && Integer.parseInt(timeToEscape) <= 999) {
							// Check the table name is valid
							if(tableName.equals("JIGSAW") || tableName.equals("STRODE") || tableName.equals("BADHAM")) {
								boolean validName = true;
								// Check if the name of the user is valid
								char[] nameArray = user.toCharArray();
								for(Character c : nameArray) {
									// Ensure that name contains only letters and spaces
									if(!Character.isLetter(c) && c != ' ') {
										validName = false;
										break;
									}
								}
								if(validName && user.trim().length() != 0) {
									if(tableName.equals("JIGSAW"))
									{
										if(!JigsawLeaderboardDB.addEntry(user, timeToEscape))
										{
											request.setAttribute("error", "Unable to add entry.");
										}
									} 
									else if(tableName.equals("STRODE"))
									{
										if(!StrodeLeaderboardDB.addEntry(user, timeToEscape))
										{
											request.setAttribute("error", "Unable to add entry.");
										}
									} 
									else // "BADHAM"
									{
										if(!BadhamLeaderboardDB.addEntry(user, timeToEscape))
										{
											request.setAttribute("error", "Unable to add entry.");
										}
									}
								}
								else {
									request.setAttribute("error", "Please enter a valid name in the name field.");
								}
							}
							else {
								request.setAttribute("error", "Please enter a valid table name.");
							}
						}
						// Out of range
						else {
							request.setAttribute("error", "The value of escape time must be between 1 and 999 minutes.");
						}
					}
					catch(NumberFormatException nfe) {
						request.setAttribute("error", "Please enter a 1 to 3 digit number for the escape time.");
					}
				}
				else {
					request.setAttribute("error", "Please enter an escape time and a name.");
				}
			}
			// Delete request is made
			else {
				if(request.getParameter("entryToDelete") != null && request.getParameter("tableName") != null) {
					String tableName = request.getParameter("tableName"); // The name of table being modified
					String id = request.getParameter("entryToDelete"); // ID of entry being deleted
					try {
						Integer.parseInt(id); // Check if the id is parsable as an integer
						// Check the table name is valid
						if(tableName.equals("JIGSAW") || tableName.equals("STRODE") || tableName.equals("BADHAM")) {
							if(tableName.equals("JIGSAW"))
							{
								if(!JigsawLeaderboardDB.removeEntry(id))
								{
									request.setAttribute("error", "Unable to remove entry.");
								}
							} 
							else if(tableName.equals("STRODE"))
							{
								if(!StrodeLeaderboardDB.removeEntry(id))
								{
									request.setAttribute("error", "Unable to remove entry.");
								}
							} 
							else // "BADHAM"
							{
								if(!BadhamLeaderboardDB.removeEntry(id))
								{
									request.setAttribute("error", "Unable to remove entry.");
								}
							}
						}
						// Table doesn't exist currently
						else {
							request.setAttribute("error", "Please enter a valid table name.");
						}
					}					
					catch(NumberFormatException nfe) {
						request.setAttribute("error", "Please enter a valid entry ID.");
					}
				}				
				else {
					request.setAttribute("error", "Please select an entry to delete and a table from which to delete the entry.");
				}
			}
			this.getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response); // Forward the request back to the admin page
		}
		else if(request.getParameter("Cancel") != null) 
		{
			String confirmationNum = request.getParameter("confirmationNo");
			
			// Check if empty or null
			if(confirmationNum == "" || confirmationNum == null) {
				request.setAttribute("cancellationResult", false);
				request.setAttribute("error", "Please enter a confirmation number.");
			}
			// Check length is 8
			else if(confirmationNum.length() != 8) {
				request.setAttribute("cancellationResult", false);
				request.setAttribute("error", "Invalid confirmation number entered.");
			}
			// Check if numeric
			else if(confirmationNum.length() == 8) {
				try {
					Integer.parseInt(confirmationNum); // Try parsing
					
					// Grab email so we can see if this was their only reservation
					String cancelledEmail = ReservationDBUtil.getEmailByConfNum(confirmationNum);
					
					// Try canceling in the database
					if(ReservationDBUtil.cancelReservation(confirmationNum))
					{
						//Cancellation was successful
						request.setAttribute("cancellationResult", true);
						
						// Check if that was the only reservation for user email
						if((cancelledEmail != "") && (ReservationDBUtil.numUserReservations(cancelledEmail) == 0))
						{
							// Remove user email from UserDB
							UserDB.removeUser(cancelledEmail);
						}
					}
					else
					{
						//Cancellation failed
						request.setAttribute("cancellationResult", false);
						request.setAttribute("error", "A reservation with the entered confirmation number does not exist.");
					}
				}
				catch(NumberFormatException nfe) {
					request.setAttribute("cancellationResult", false);
					request.setAttribute("error", "Invalid confirmation number entered.");
				}
			}
			this.getServletContext().getRequestDispatcher("/cancellations.jsp").forward(request, response);
		}
	}
}
