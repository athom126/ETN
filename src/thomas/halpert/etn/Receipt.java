package thomas.halpert.etn;

public class Receipt {
	
	String name; 		// Stores name reservation is under
	String email;  		// Stores email associated with user
	String numPeople; 	// Store number of participants
	String room; 		// Stores selected room
	String date; 		// Store date of reservation
	
	// Default constructor
	public Receipt() {
		this.name = "";
		this.email = "";
		this.numPeople = "";
		this.room = "";
		this.date = "";
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

	public String getDate()
	{
		return this.date;
	}

}
