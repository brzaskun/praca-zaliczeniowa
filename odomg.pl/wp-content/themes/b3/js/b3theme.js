function b3theme_slide_height() {
	jQuery('#b3theme-slider .carousel-inner, #b3theme-slider .carousel-inner .item').css('height', 
		Math.round(jQuery('#b3theme-slider .carousel-inner').width()/3.8) + 'px');
}

/*
	resizes too wide video
*/
function b3theme_video_size(elem) {
	var w, h, ow, pw, nw, nh;
	var e = jQuery(elem);
	if (e.prop('tagName') == 'EMBED' && e.parent().prop('tagName') == 'OBJECT') {
		pw = e.parent().parent().width();
	}
	else {
		pw = e.parent().width();
	}
	ow = e.outerWidth(true);
	if (ow <= pw) {
		return;
	}
	w = e.width();
	h = e.height();
	nw = pw - ow + w;
	nh = h * nw / w;
	e.width(nw + 'px');
	e.height(nh + 'px');
}

jQuery(document).ready(function($){
	$('.widget img').not('.wp-smiley').addClass('img-responsive');
	$('.entry-content table').addClass('table table-bordered');
	$('.widget select').parent().addClass('form-group');
	$('.widget select').addClass('form-control').addClass('spacer-top');
	$('.widget_calendar table').addClass('table table-bordered table-condensed').addClass('spacer-all');
	$('#submit').addClass('btn btn-default btn-lg spacer-top');
	$('.wp-caption').addClass('thumbnail');
	b3theme_slide_height();
	$('.entry-content iframe, .widget iframe, .entry-content object, .widget object, .entry-content embed, .widget embed, .entry-content video, .widget video')
		.each(function(index, elem){
			b3theme_video_size(elem);
	});
	$(window).resize(function(){
		b3theme_slide_height();
		$('.entry-content iframe, .widget iframe, .entry-content object, .widget object, .entry-content embed, .widget embed, .entry-content video, .widget video')
		.each(function(index, elem){
			b3theme_video_size(elem);
		});
	});
	$('.carousel').carousel({interval: 5000});
	$('.carousel').carousel('cycle');

});
