$(document).on("ready", function(){
	$(".hourglass").hide();
});

$(document).on({
	ajaxStart: function() { 
		$(".hourglass").show();
	},
	ajaxStop: function() { 
		$(".hourglass").hide();
	},
	ajaxError: function() { 
		$(".hourglass").hide();
	}    
});
