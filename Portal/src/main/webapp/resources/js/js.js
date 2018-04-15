$(document).ready(function() {
	PF('statusDialog').show();
	$(".memenu").memenu();

	if(!$(".lati").val() || $(".long").val())
		getLocation();
	$(".memenu").show();
});

$(window).load(function(){
	showDivs(slideIndex);
	$(".w3-content").show();
	slideIndex = 0;
	PF('statusDialog').hide();
})

var slideIndex = 0;

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  if(x && x.length > 0) {
	  if (n > x.length) {slideIndex = 1}    
	  if (n < 1) {slideIndex = x.length}
	  for (i = 0; i < x.length; i++) {
	     x[i].style.display = "none";  
	  }
	  x[slideIndex-1].style.display = "block";
  }
}

var basic;
function initCropper() {
	$(document).ready(function() {
		
		if(basic)
			basic.destroy();
		
		var imgSrc = $('.imgSrc').val();
		
		basic = $('#demo-basic').croppie({
		    viewport: {
		        width: 300,
		        height: 300
		    }
		});
		basic.croppie('bind', {
		    url: imgSrc,
		    points: [0,0,300,300]
		});
		
	});
}

function crop() {
	
	basic.croppie('result', 'base64').then(function(html) {
	    $(".base64").attr('value', html);
	});
	$(".base64").ready(function() {
		setTimeout(function() {
			$(".base64").ready(function() {
				$(".cropperBtn").click();
			});
		}, 2500);
	})
}

function getLocation() {
    if (navigator.geolocation) {
    	navigator.geolocation.getCurrentPosition(showPosition,showError);
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