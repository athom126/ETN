package thomas.halpert.etn;

import java.util.*;
import java.util.regex.Pattern;

public class ValidatePayment {
	
	ArrayList<String> errors = new ArrayList<String>();
	
	public ValidatePayment() {}
	
	/* 	Calls methods to validate each parameter
	 *	Returns boolean indicate whether request parameters are valid
	 *	Errors should be retrieved and displayed to use if invalid by calling getErrors() 
	 */
	public boolean validate(String email, String name) {
		// Validate request parameters
		this.validateEmail(email);
		this.validateName(name);
		
		// If there are no errors, return true
		if(errors.isEmpty()) {
			return true;
		}
		return false; // Invalid request parameter(s)
	}
	
	// Validates email
	public boolean validateEmail(String email) {
		if(email == null || email.length() == 0) {
			errors.add("-Email address must be entered");
			return false;
		}
		// Check if email follows proper format of an email address
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + 
                      "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
		Pattern pattern = Pattern.compile(regex); // Create pattern specifying format of email address
		if(!pattern.matcher(email).matches()) {
			errors.add("-Invalid email address entered");
		    return false;
		}
		return true;
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
}
