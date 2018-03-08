function change(clicked_id)
{
	try {
		var el = document.getElementById(clicked_id);
		el.value = "Agregado"; 
		alert(clicked_id);
	}
	catch(err) {
	    document.getElementById("demo").innerHTML = err.message;
	}
	
    
}
