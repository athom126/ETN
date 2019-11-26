/** JavaScript file top support EN.605.682 project
 * 
 */

function onRoomSelectionChange()
{
	var sel = document.getElementById("escapeRoom");
	//alert(sel.options[sel.selectedIndex].value);
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
	else if(sel.options[sel.selectedIndex].value == "Jigsaw's Warehouse")
	{
		document.getElementById("reservationDateBP").style.display="none";
		document.getElementById("reservationDateSR").style.display="none";
		document.getElementById("reservationDateJW").style.display="";
	}
}

function showStrode() {
	  var x = document.getElementById("strodeTbl");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } 
	  else {
	    x.style.display = "none";
	  }
}

function showBadham() {
	  var x = document.getElementById("badhamTbl");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } 
	  else {
	    x.style.display = "none";
	  }
}

function showJigsaw() {
	  var x = document.getElementById("jigsawTbl");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } 
	  else {
	    x.style.display = "none";
	  }
}