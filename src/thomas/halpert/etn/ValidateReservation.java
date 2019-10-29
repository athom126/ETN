package thomas.halpert.etn;

import java.text.*;
import java.util.*;

public class ValidateReservation {

	private ArrayList<String> errors = new ArrayList<String>(); // Errors get added into here during validation process
	
	// Default constructor
	public ValidateReservation() {}
	
	// Calls methods to validate each parameter
	// Returns boolean indicate whether request parameters are valid
	// Errors should be retrieved and displayed to use if invalid by calling getErrors()
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
		else {
			return false; // Invalid request parameter(s)
		}
	}

	public boolean validateName(String name) {
		if(name == null || name.length() == 0)  {
			errors.add("-Name must be entered");
			return false;
		}
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
	
	public boolean validateNumPeople(String numPeople) {
		int numberOfPeople;
		
		if(numPeople == null) {
			errors.add("-The number of guests must be entered");
			return false;
		}
		try {
			numberOfPeople = Integer.parseInt(numPeople);
		}
		catch(NumberFormatException nfe) {
			errors.add("-Invalid number entered for number of guests");
			return false;
		}
		if(numberOfPeople < 1 || numberOfPeople > 8) {
			errors.add("-The number of guests must be between 1 and 8");
			return false;
		}
		return true;
	}
	
	public boolean validateRoom(String room) {
		if(room == null) {
			errors.add("-An escape room must be selected");
			return false;
		}
		if(!room.equals("Badham") && !room.equals("Strode") && !room.contentEquals("Jigsaw")) {
			errors.add("-Invalid escape room");
			return false;
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
