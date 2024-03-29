/**
 * File: ValidateReservation.java
 * File Description: Contains a class that can be used to  
 * validate request parameters from the main reservation form
 * 
 */

package thomas.halpert.etn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ValidateReservation {
	
	String name; // Stores name reservation is under
	String numPeople; // Store number of participants
	String room; // Stores selected room
	String date; // Store date of reservation
	Double totalCost; // Store total cost 
	
	private ArrayList<String> errors = new ArrayList<String>(); // Errors get added into here during validation process

	// Default constructor
	public ValidateReservation() {
		this.name = "";
		this.numPeople = "";
		this.room = "";
		this.date = "";
	}
	
	// Accessors 
	public String getName() {
		return this.name;
	}
	
	public String getNumPeople() {
		return this.numPeople;
	}
	
	public String getRoom() {
		return this.room;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getTotalCostFormatted() {
		return String.format("$%.2f", this.totalCost);
	}
	
	public Double getTotalCost() {
		return this.totalCost;
	}
	
	// Setters
	public void setName(String name) {
		this.name= name;
	}
	
	public void setNumPeople(String numPeople) {
		this.numPeople = numPeople;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public void setDate(String date) {
		this.date = date;
	}	
	
	public void setTotalCost(String numPeople) {
		this.totalCost = 40.0 * Double.parseDouble(this.getNumPeople());
	}
	
	/* Calls methods to validate each parameter
	 * Returns a boolean indicating whether the request parameters are valid
	 * Errors should be retrieved and displayed if invalid by calling getErrors() 
	 */
	public boolean validate(String name, String numPeople, String room, String date) {
		
		// If errors exist already, clear them before validating again to remove previous errors
		if(!this.errors.isEmpty()) {
			this.errors.clear();
		}
				
		// Validate request parameters
		this.validateName(name);
		this.validateNumPeople(numPeople); 
		this.validateRoom(room);
		this.validateDate(date);
		
		// If there are no errors, set the total cost, and return true
		if(errors.isEmpty()) {
			this.setTotalCost(numPeople);
			return true;
		}
		return false; // Invalid request parameter(s)
	}

	// Validates name
	public boolean validateName(String name) {
		if(name == null || name.length() == 0)  {
			errors.add("Name must be entered");
			return false;
		}
		// Check that name is completely alphabetical
		else {
			char[] nameArray = name.toCharArray();
			for(Character c : nameArray) {
				// Ensure that name contains only letters and spaces
				if(!Character.isLetter(c) && c != ' ') {
					this.errors.add("Invalid name entered");
					return false;
				}
			}
		}
		return true;
	}
	
	// Validates number of people
	public boolean validateNumPeople(String numPeople) {
		int numberOfPeople;
		if(numPeople == null) {
			errors.add("The number of guests must be entered");
			return false;
		}
		// Verify that number of people is an integer
		try {
			numberOfPeople = Integer.parseInt(numPeople);
		}
		catch(NumberFormatException nfe) {
			errors.add("Invalid number entered for number of guests");
			return false;
		}
		// Check if number of people is within valid range
		if(numberOfPeople < 1 || numberOfPeople > 8) {
			errors.add("The number of guests must be between 1 and 8");
			return false;
		}
		return true;
	}
	
	// Validates room
	public boolean validateRoom(String room) {
		if(room == null) {
			errors.add("An escape room must be selected");
			return false;
		}
		ArrayList<String> escapeRooms = new ArrayList<String>(); // List of valid escape rooms
		escapeRooms.add("badham preschool"); // Populate list
		escapeRooms.add("the strode residence");
		escapeRooms.add("jigsaw's warehouse");
		if(!escapeRooms.contains(room.toLowerCase())) {
			errors.add("Invalid escape room");
			return false; // Returns false if room not in list (invalid room)
		}
		return true;
	}
	
	// Checks if the date is a valid (real) date
	// Check is the date is an open date and in the database
	public boolean validateDate(String date) {
		if(date == null) {
			errors.add("A reservation date and time must be selected");
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("EEEEE-MMMM-dd-yyyy-h-aa");
			SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			Date date1 = sdf.parse(date); //If input format is incorrect, this will throw an exception
			String dateString = dbSdf.format(date1);

			//Make sure room slot is open
			if(!ReservationDBUtil.checkRoomStatus(dateString, this.room).equalsIgnoreCase("open"))
			{
				this.errors.add("Room not open at that time slot.");
			}
		} catch (ParseException e1) {
			this.errors.add("Invalid date format");
			return false;
		} catch (Exception e) {
			this.errors.add("Invalid date entered");
			return false;
		}  
		return true;
	}
	
	// Returns all errors
	public ArrayList<String> getErrors() {
		return this.errors;
	}
	
}
