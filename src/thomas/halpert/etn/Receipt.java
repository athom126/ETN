package thomas.halpert.etn;

public class Receipt {
	
	String name; 			// Stores name reservation is under
	String email;  			// Stores email associated with user
	String numPeople; 		// Store number of participants
	String room; 			// Stores selected room
	String date; 			// Store date of reservation
	String confirmationNum; // Store confirmation number of reservation
	String totalCost;		// Store total cost as a formatted String
	
	
	// Default constructor
	public Receipt() {
		this.name = "";
		this.email = "";
		this.numPeople = "";
		this.room = "";
		this.date = "";
		this.confirmationNum = "";
		this.totalCost = "";
	}
	
	//Setters
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setNumPeople(String numPeople)
	{
		this.numPeople = numPeople;
	}
	
	public void setRoom(String room)
	{
		this.room = room;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setConfirmationNum(String confNum)
	{
		this.confirmationNum = confNum;
	}
	
	public void setTotalCost(Double cost) {
		this.totalCost = String.format("$%.2f", cost);
	}
	
	//Getters
	public String getName()
	{
		return this.name;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getNumPeople()
	{
		return this.numPeople;
	}

	public String getRoom()
	{
		return this.room;
	}
	
	public String getConfirmationNum()
	{
		return this.confirmationNum;
	}

	public String getDate()
	{
		String[] words = date.split("-");
		return words[0] + ", " + words[1] + " " + words[2] + ", " + words[3] + " at " + words[4] + " " + words[5];
	}
	
	public String getUnparsedDate()
	{
		return this.date;
	}
	
	public String getTotalCost() {
		return this.totalCost;
	}
}
