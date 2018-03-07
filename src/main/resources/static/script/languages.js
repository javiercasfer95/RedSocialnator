$(document).ready(function() {
	$("#languageDropdownMenuButton a").click(function(e) {
<<<<<<< HEAD
		e.preventDefault(); // cancel the link behaviour var
		languageSelectedText = $(this).text();
		var languageSelectedValue = $(this).attr("value");

=======
		e.preventDefault(); // cancel the link behaviour
		var languageSelectedText = $(this).text();
		var languageSelectedValue = $(this).attr("value");
>>>>>>> ea9498000265f27225b3b1023bf1ef85847943bb
		$("#btnLanguage").text(languageSelectedText);
		window.location.replace('?lang=' + languageSelectedValue);
		return false;
	});
<<<<<<< HEAD
});
=======
});
>>>>>>> ea9498000265f27225b3b1023bf1ef85847943bb
