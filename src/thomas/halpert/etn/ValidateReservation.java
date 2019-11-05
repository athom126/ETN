/**
 * File: ValidateReservation.java
 * File Description: Contains a class that can be used to  
 * validate request parameters from the main reservation form
 * 
 */

package thomas.halpert.etn;

import java.text.*;
import java.util.*;
import java.util.regex.Pattern;

public class ValidateReservation {

	private ArrayList<String> errors = new ArrayList<String>(); // Errors get added into here during validation process

	// Default constructor
	public ValidateReservation() {}
	
	/* Calls methods to validate each parameter
	 * Returns a boolean indicating whether the request parameters are valid
	 * Errors should be retrieved and displayed if invalid by calling getErrors() 
	 */
	public boolean validate(String name, String numPeople, String room, String date) {
		// Validate request parameters
		this.validateName(name);
		this.validateNumPeople(numPeople); 
		this.validateRoom(room);
		this.validateDate(date);
		
		// If there are no errors, return true
		if(errors.isEmpty()) {
			return true;
		}
		return false; // Invalid request parameter(s)
	}

	// Validates name
	public boolean validateName(String name) {
		if(name == null || name.length() == 0)  {
			errors.add("-Name must be entered");
			return false;
		}
		// Check that name is completely alphabetical
		else {
			char[] nameArray = name.toCharArray();
			for(Character c : nameArray) {
				// Ensure that name contains only letters and spaces
				if(!Character.isLetter(c) && c != ' ') {
					this.errors.add("-Invalid name entered");
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
			errors.add("-The number of guests must be entered");
			return false;
		}
		// Verify that number of people is an integer
		try {
			numberOfPeople = Integer.parseInt(numPeople);
		}
		catch(NumberFormatException nfe) {
			errors.add("-Invalid number entered for number of guests");
			return false;
		}
		// Check if number of people is within valid range
		if(numberOfPeople < 1 || numberOfPeople > 8) {
			errors.add("-The number of guests must be between 1 and 8");
			return false;
		}
		return true;
	}
	
	// Validates room
	public boolean validateRoom(String room) {
		if(room == null) {
			errors.add("-An escape room must be selected");
			return false;
		}
		ArrayList<String> escapeRooms = new ArrayList<String>(); // List of valid escape rooms
		escapeRooms.add("badham preschool"); // Populate list
		escapeRooms.add("the strode residence");
		escapeRooms.add("jigsaw's warehouse");
		if(!escapeRooms.contains(room.toLowerCase())) {
			errors.add("-Invalid escape room");
			return false; // Returns false if room not in list (invalid room)
		}
		return true;
	}
	
	// Checks if the date is a valid (real) date
	// TO DO: Check is the date is an open date and in the database
	public boolean validateDate(String date) {
		if(date == null) {
			errors.add("-A date must be selected");
			return false;
		}
		try {
			// TO DO
		} catch (Exception e) {
			this.errors.add("-Invalid date entered");
			return false;
		}  
		return true;
	}
	
	// Returns all errors
	public ArrayList<String> getErrors() {
		return this.errors;
	}
	
}
