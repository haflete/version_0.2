$(document).ready(function() {
	$(".memenu").memenu();
	
	if(!$(".lati").val() || $(".long").val())
		getLocation();
});

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
    	alert("please register using your mobile");
    }
}

function showPosition(position) {
  var x=document.getElementById("demo");
  var lat=position.coords.latitude;
  var lon=position.coords.longitude;
  $(".lati").val(lat); 
  $(".long").val(lon);
  $(".long").ready(function() {
	 $(".mapBtn").click();
  });
}

function showError(error) {
  var x=document.getElementById("demo");
  switch(error.code) {
    case error.PERMISSION_DENIED:
      x.innerHTML="User denied the request for Geolocation."
      break;
    case error.POSITION_UNAVAILABLE:
      x.innerHTML="Location information is unavailable."
      break;
    case error.TIMEOUT:
      x.innerHTML="The request to get user location timed out."
      break;
    case error.UNKNOWN_ERROR:
      x.innerHTML="An unknown error occurred."
      break;
    }
}