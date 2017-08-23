jQuery(document).ready(function($) {
	try { $('.b3theme-color-option').wpColorPicker(); }
	finally {}
	$('ul.b3theme-settings-tabs a').bind('click', function(index){
		$('ul.b3theme-settings-tabs li').removeClass('tab-active');
		$(this).parent().addClass('tab-active');
		if ( $(this).attr('href') == '#b3theme-display-all') {
			$('.b3theme-settings-section').show();
		}
		else {
			$('.b3theme-settings-section').hide();
			$('.' + $( this ).attr('href').substring(1) ).toggle();
		}
		$('.b3theme-slides textarea').each(function(index, elem) {
			b3theme_add_quicktags(elem.getAttribute('id'));
		});
		return false;
	});
});

window.qtjs = location.href;
qtjs = qtjs.substring(0, qtjs.indexOf('/wp-admin')) + '/wp-includes/js/quicktags.js';

/*
	Adds quicktags to the textarea identified by id
*/

function b3theme_add_quicktags(id) {
	if (!jQuery('#qt_'+ id + '_toolbar').length) {
		jQuery('<script src="' + qtjs + '"></script>').insertBefore('#'+id);
		quicktags({id: id, buttons: 'strong,em,link,block,del,ins,ul,ol,li,code,close'});
	}
}

function b3theme_reset() {
	if (confirm(b3theme_admin.reset)) {
		jQuery('#b3theme_action').val('reset');
		jQuery('#b3theme_options_form').submit();
	}
}

function b3theme_upload() {
	if (confirm(b3theme_admin.upload)) {
		jQuery('#b3theme_action').val('upload');
		jQuery('#b3theme_options_form').submit();
	}
}

function b3theme_remove_slide(i) {
	if (confirm(b3theme_admin.slide_remove)) {
		jQuery('#slide-' + i).remove();
	}
	return false;
}

function b3theme_add_new_slide() {
	var i = 0;
	var html, id;
	var after = '.b3theme-slides h3.edit-slides';
	var slides = jQuery('.b3theme-slides > div');
	slides.each(function(index, elem) {
		id = jQuery(elem).attr('id');
		i = Math.max(i, id.substring(6));
		after = '#'+ id;
	});

	i++;

	html = '<div id="slide-' + i + '" class=""><h4><span class="space"> </span>'
		+ b3theme_admin.slide + ' #' + i + ' &nbsp; <a name="slide-' + i
		+ '" class="slide-remove" href="#" onclick="b3theme_remove_slide(' + i
		+ ');">&times;</a></span></h4>';
	
	html += '<p><label for="b3theme_options-slides-' + i + '-title">'
		+ b3theme_admin.title + '</label>' + '<input id="b3theme_options-slides-'
		+ i + '-title" type="text" name="b3theme_options[slides][' + i + '][title]" value="" /></p>';

	html += '<p><label for="b3theme_options-slides-'+ i + '-src">' + b3theme_admin.image_url + '</label>'
		+ '<input id="b3theme_options-slides-' + i + '-src" type="text" name="b3theme_options[slides]['
		+ i + '][src]" onchange="b3theme_slide_preview_change(' + i + ');" /></p>'
		+ '<p><span class="space"> </span><a href="#" id="slide-url-choose-' + i
		+ '" class="button button-secondary slide-url-choose" onclick="b3theme_slide_choose(' + i
		+ ');"><span class="media-icon"></span> ' + b3theme_admin.choose
		+ '</a> <span class="slide-preview" id="slide-preview-'	+ i + '"></span></p>';

	html += '<div><label class="slide-textarea-label" for="b3theme_options-slides-' + i
		+ '-content">' + b3theme_admin.description + '</label></div>';

	html += '<div id="wp-b3theme_options-slides-' + i +'-content-wrap" class="slide-textarea-wrap">'

	html += '<textarea class="wp-editor-area" rows="5" id="b3theme_options-slides-' + i + '-content" '
		+ 'name="b3theme_options[slides][' + i + '][content]"></textarea>';
	html += '</div>';

	html += '<p><label for="b3theme_options-slides-' + i + '-link">' + b3theme_admin.link
		+ '</label>' + '<input id="b3theme_options-slides-' + i
		+ '-link" type="text" name="b3theme_options[slides][' + i + '][link]" /></p>';

	html += '<p><label for="b3theme_options-slides-' + i + '-alt">'
		+ b3theme_admin.alt_text + '</label>' + '<input id="b3theme_options-slides-' + i
		+ '-alt" type="text" name="b3theme_options[slides][' + i + '][alt]" /></p></div>';

	jQuery(html).insertAfter(after);
	b3theme_add_quicktags('b3theme_options-slides-' + i + '-content');
	window.location.hash = "#add-new-slide";
}

function b3theme_slide_preview_change(n) {
	var src = jQuery('#b3theme_options-slides-'+ n + '-src').val();
	jQuery('#slide-preview-'+ n).html( (src ? '<img src="' + src + '" alt="" />' : '') );
}

function b3theme_slide_choose(n) {
	var b3theme_uploader;

	if (b3theme_uploader) {
		b3theme_uploader.open();
		return;
	}

	b3theme_uploader = wp.media.frames.file_frame = wp.media({
		title: b3theme_admin.choose_image,
		button: {text: b3theme_admin.choose_image},
		multiple: false
	});

	b3theme_uploader.on('select', function() {
		attachment = b3theme_uploader.state().get('selection').first().toJSON();
		jQuery('#b3theme_options-slides-'+ n + '-src').val(attachment.url);
		jQuery('#slide-preview-'+ n).html( '<img src="' + attachment.url + '" alt="" />' );
		jQuery('#b3theme_options-slides-' + n + '-alt').val(attachment.alt);
		jQuery('#b3theme_options-slides-' + n + '-title').val(attachment.title);
		jQuery('#b3theme_options-slides-' + n + '-content').val(attachment.description);
	});

	b3theme_uploader.open();
	return false;
}
