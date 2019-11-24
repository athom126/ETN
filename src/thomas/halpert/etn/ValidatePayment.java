/**
 * File: ValidatePayment.java
 * File Description: Contains a class that can be used to  
 * validate request parameters from the payment form
 */

package thomas.halpert.etn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.regex.Pattern;

// TO DO: create methods to validate address, state, zip code, and expiration date

public class ValidatePayment {
	
	String email;  // Stores email associated with user
	String ccHolder; // Stores name of card holder
	String ccType; // Stores credit card (CC) company
	String ccNum; // Stores CC number
	String month; // Stores CC expiration month
	String year; // Stores CC expiration year
	
	ArrayList<String> paymentErrors = new ArrayList<String>(); // Contains any errors that occur during payment
	
	public ValidatePayment() {
		this.email = "";
		this.ccHolder = "";
		this.ccNum = "";
		this.ccType = "";
		this.month = "";
		this.year = "";
	}
	
	/* Accessors */
	public String getEmail() {
		return this.email;
	}
	
	public String getCCHolder() {
		return this.ccHolder;
	}
	
	public String getType() {
		return this.ccType;
	}
	
	public String getNumber() {
		return this.ccNum;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public String getYear() {
		return this.year;
	}
	
	/* Setters */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setCCHolder(String ccHolder) {
		this.ccHolder = ccHolder;
	}
	
	public void setType(String ccType) {
		this.ccType = ccType;
	}
	
	public void setNumber(String ccNum) {
		this.ccNum = ccNum;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	/* 	Calls methods to validate each parameter from the payment form
	 *	Returns a boolean indicating whether the request parameters are valid
	 *	Errors should be retrieved and displayed to use if invalid by calling getErrors() 
	 */
	public boolean validate(String email, String name, String ccType, String ccNum, String ccExpMonth, String ccExpYear) {
		
		// If errors exist already, clear them before validating again to remove previous errors
		if(!this.paymentErrors.isEmpty()) {
			this.paymentErrors.clear();
		}
		
		// Invoke methods to validate payment request parameters
		this.validEmail(email);
		this.validName(name);
		this.validCCType(ccType);
		this.validCCNumber(ccNum);
		this.validMonth(ccExpMonth);
		this.validYear(ccExpYear);
		this.validDate(ccExpMonth, ccExpYear);
		
		// If no payment errors, return true
		if(this.paymentErrors.isEmpty()) {
			return true;
		}
		return false;
	}
	
	// Validates email address
	public boolean validEmail(String email) {
		this.email = email;
		if(email == null || email.length() == 0) {
			paymentErrors.add("Email address must be entered");
			return false;
		}
		// Check if email follows proper format of an email address
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + 
                      "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
		Pattern pattern = Pattern.compile(regex); // Create pattern specifying format of email address
		if(!pattern.matcher(email).matches()) {
			paymentErrors.add("Invalid email address entered");
		    return false;
		}
		return true;
	}
	
	// Validates card holder's name
	public boolean validName(String name) {
		this.ccHolder = name;
		if(name == null || name.length() == 0)  {
			paymentErrors.add("Name must be entered");
			return false;
		}
		// Check that name is completely alphabetical
		else {
			char[] nameArray = name.toCharArray();
			for(Character c : nameArray) {
				// Ensure that name contains only letters and spaces
				if(!Character.isLetter(c) && c != ' ') {
					this.paymentErrors.add("Invalid name entered");
					return false;
				}
			}
		}
		return true;
	}
	
	// Validates credit card company
	public boolean validCCType(String ccType) {
		this.ccType = ccType;
		if(ccType == null || ccType == "") {
			this.ccType = "";
			this.paymentErrors.add("A credit card type must be selected");
			return false;
		}
		ArrayList<String> types = new ArrayList<String>();
		types.add("discover");
		types.add("mastercard");
		types.add("visa");
		if(!types.contains(ccType.toLowerCase())) {
			this.paymentErrors.add("Invalid card type selected");
			return false;
		}
		return true;
	}
	
	// Validates credit card number
	public boolean validCCNumber(String ccNum) {
		this.ccNum = ccNum;
		if(ccNum == null || ccNum == "") {
			this.paymentErrors.add("A credit card number must be entered");
			return false;
		}
		if(ccNum.length() != 16) {
			this.paymentErrors.add("Please enter a valid 16 digit credit card number");
			return false;
		}
		for(int i = 0; i < ccNum.length(); i++) {
			Character c = ccNum.charAt(i);
			if(!Character.isDigit(c)) {
				this.paymentErrors.add("Invalid credit card number entered");
				return false;
			}
		}
		return true;
	}
	
	// Checks credit card expiration status (i.e. expired or not?)
	public boolean validDate(String month, String year) {
		// If the month and year are valid, check if date is in the past
		if(validMonth(month) && validYear(year)) {
			int mon = Month.valueOf(month.toUpperCase()).getValue(); // Get integer representation of month
			String strDate = Integer.toString(mon)+"/"+year; 
			try {
				// If the date is after the current date -> Card has not expired (valid date)
				if(new SimpleDateFormat("MM/yyyy").parse(strDate).after(new Date())) {
					return true;
				}
			} catch (ParseException e) {
				System.out.println("ParsingException occurred");
				e.printStackTrace();
			}
		}
		this.paymentErrors.add("The card entered is expired");
		return false;
	}
	
	// Validates the expiration month
	public boolean validMonth(String month) {
		this.month = month;
		if(month == null || month == "") {
			this.month = "";
			paymentErrors.add("Expiration month must be selected");
			return false;
		}
		ArrayList<String> months = new ArrayList<String>();
		months.add("JANUARY");
		months.add("FEBRUARY");
		months.add("MARCH");
		months.add("APRIL");
		months.add("MAY");
		months.add("JUNE");
		months.add("JULY");
		months.add("AUGUST");
		months.add("SEPTEMBER");
		months.add("OCTOBER");
		months.add("NOVEMBER");
		months.add("DECEMBER");
		if(!months.contains(month.toUpperCase())) {
			this.paymentErrors.add("Invalid expiration month entered");
			return false;
		}
		return true;
	}
	
	// Validates the expiration year
	public boolean validYear(String year) {
		this.year = year;
		if(year == null || year == "") {
			this.year = "";
			this.paymentErrors.add("Expiration year must be selected");
			return false;
		}
		try {
			int yr = Integer.parseInt(year);
			if(yr < 2019 && yr > 2030) {
				this.paymentErrors.add("Invalid expiration year entered");
				return false;
			}
		}
		catch(NumberFormatException nfe) {
			this.paymentErrors.add("Invalid expiration year entered");
			return false;
		}
		return true;
	}
	
	public ArrayList<String> getErrors() {
		return this.paymentErrors;
	}
	
}
