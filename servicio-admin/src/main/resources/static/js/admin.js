$(document).ready(function(){
	var $link_logout = $('#logout');
	
	
	$link_logout.click(function(){
		$.ajax({
			beforeSend: csrf_control, 
			url: '/logout',
			method: 'POST',
			dataType: 'json',
			data: {}
		})
		.always(function(){
			window.location = "/";
		});
	
	});
	
	var csrf_control = function(xhr,settings){
		var xsrf_token_cookie_name = 'XSRF-TOKEN';
		var xsrf_token_cookie_value;
		if(document.cookie && document.cookie != ''){
			var cookies = document.cookie.split(';');
			for(var i = 0; i < cookies.length; i++){
				var cookie = jQuery.trim(cookies[i]);
				if(cookie.substring(0, xsrf_token_cookie_name.length + 1) == (xsrf_token_cookie_name + '=')){
					xsrf_token_cookie_value = decodeURIComponent(cookie.substring(xsrf_token_cookie_name.length + 1));
					break;
				}
			}
			xhr.setRequestHeader("X-XSRF-TOKEN",xsrf_token_cookie_value);
		}
	}
});