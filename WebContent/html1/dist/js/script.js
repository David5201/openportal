$(document).ready(function(){
	// Set options
	var speed = 500;			// Fade speed
	var autoSwitch = true;		// Auto slider options
	var autoSwitchSpeed = 4000;	// Auto slider speed
	var hoverPause = true;	// Pause auto slider on hover
	var keyPressSwitch = true;	// Key press next/prev
	
	// Add initial active class
	$('.slide').first().addClass('active');
	
	// Hide all slides
	$('.slide').hide();
	
	// Show first slide
	$('.active').show();
		
	// Switch to next slide
	function nextSlide(){
		$('.active').removeClass('active').addClass('oldActive');
		if($('.oldActive').is(':last-child')){
			$('.slide').first().addClass('active');
		} else {
			$('.oldActive').next().addClass('active');
		}
		$('.oldActive').removeClass('oldActive');
		$('.slide').fadeOut(speed);
		$('.active').fadeIn(speed);
	}
	
	// Switch to prev slide
	function prevSlide(){
		$('.active').removeClass('active').addClass('oldActive');
		if($('.oldActive').is(':first-child')){
			$('.slide').last().addClass('active');
		} else {
			$('.oldActive').prev().addClass('active');
		}
		$('.oldActive').removeClass('oldActive');
		$('.slide').fadeOut(speed);
		$('.active').fadeIn(speed);
	}

	// Key press event handler
	if(keyPressSwitch === true){
		$("body").keydown(function(e){
			if(e.keyCode === 37){
		    	nextSlide();
		  	} else if(e.keyCode === 39){
		    	prevSlide();
		  	}
		});
	}

	// Next handler
	$('#next').on('click', nextSlide);
	
	// Prev handler
	$('#prev').on('click', prevSlide);
	
	// Auto slider handler
	if(autoSwitch === true){
		var interval = null;
		interval = window.setInterval(function(){nextSlide();},autoSwitchSpeed);
	}

	// Stop and start on hover
	if(autoSwitch === true && hoverPause === true){
		$('#slider,#prev,#next').hover(function() {
		    window.clearInterval(interval);    
		}, function() {
		    interval = window.setInterval(function(){nextSlide();},autoSwitchSpeed);
		});
	}

	// Slider hover class handler
	$('#sliderContainer').hover(function() {
	    $('#sliderContainer').addClass('sliderHovered');
	}, function() {
	    $('#sliderContainer').removeClass('sliderHovered');
	});

});







