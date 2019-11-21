/** JavaScript file top support EN.605.682 project
 * 
 */

function onRoomSelectionChange()
{
	var sel = document.getElementById("escapeRoom");
	
	if(sel.options[sel.selectedIndex].value == "Badham Preschool")
	{
		document.getElementById("reservationDateBP").style.display="";
		document.getElementById("reservationDateSR").style.display="none";
		document.getElementById("reservationDateJW").style.display="none";
	}
	else if(sel.options[sel.selectedIndex].value == "The Strode Residence")
	{
		document.getElementById("reservationDateBP").style.display="none";
		document.getElementById("reservationDateSR").style.display="";
		document.getElementById("reservationDateJW").style.display="none";
	}
	else if(sel.options[sel.selectedIndex].value == "Jigsaws Warehouse")
	{
		document.getElementById("reservationDateBP").style.display="none";
		document.getElementById("reservationDateSR").style.display="none";
		document.getElementById("reservationDateJW").style.display="";
	}
}