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
		if(!vr.validate(name, numPeople, room, date)) {
			ArrayList<String> errors = vr.getErrors();
			request.setAttribute("errors", errors);
			this.getServletContext().getRequestDispatcher("/errors.jsp").forward(request, response);
		}
		this.getServletContext().getRequestDispatcher("/confirmation.jsp").forward(request, response);
	
//		response.getWriter().append("Served at: ").append(request.getContextPath()); // Auto-generated
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
