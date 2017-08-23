<?php
/**
 * B3 functions and definitions
 *
 * @package B3
 */

add_filter('sanitize_option_b3theme_options', 'b3theme_sanitize_options');

function b3theme_sanitize_options($arr) {
	$defaults = array(
		'logo_enabled' => 'Y',
		'site_title_enabled' => 'Y',
		'site_description_enabled' => 'Y',
		'navbar_brand' => 'Project Name',
		'navbar_location' => 'default',
		'navbar_gradient' => 'Y',
		'copyright' => date('Y ') . get_option('blogname'),
		'show_home' => 'N',
		'reduce_404_page' => 'N',
		'disable_comment_page' => 'N',
		'sidebar_main' => 'right',
		'sidebar_top' => 'Y',
		'sidebar_bottom' => 'Y',
		'panel_widget' => 'N', 
		'panel_post' => 'N',
		'carousel' => 'demo',
		'disable_slide_the_content' => 'N',
		'image_rounded' => 'N',
		'prev_next_link' => 'titles',
		'paginate_links' => 'next',
		'credits' => 'Y',
		'post_thumbnail' => 'Y',
		'post_date' => 'Y',
		'post_author' => 'Y',
		'excerpt' => 'N',
		'text_color' => '#333333',
		'headers_color' => '',
		'link_color' => '',
		'link_hover_color' => '',
		'navbar_color' => '',
		'navbar_color2' => '',
		'navbar_border' => '',
		'navbar_link_color' => '',
		'navbar_link_color2' => '',
		'page_bgcolor' => '',
		'slides' => array(),
	);

	$out = array();
	foreach ($defaults as $key => $value) {
		$out[$key] = isset($arr[$key]) ? $arr[$key] : $value;
		if (strpos($key, 'color') && !preg_match('/^#[0-9a-f]{6,6}$/i', $out[$key])) {
			$out[$key] = '';
		}

		if ( 'slides' == $key ) {
			$slides = array(0);
			foreach($out[$key] as $slide_raw) {
				if (is_array($slide_raw)) {
					$slide['title'] = sanitize_text_field($slide_raw['title']);
					$slide['src'] = sanitize_text_field($slide_raw['src']);
					$slide['alt'] = sanitize_text_field($slide_raw['alt']);
					$slide['link'] = sanitize_text_field($slide_raw['link']);
					$slide['content'] = trim($slide_raw['content']);
					if ($slide['src']) {
						$slides[] = $slide;
					}
				}
			}
			unset($slides[0]);
			$out[$key] = $slides;
		}
	}

	$out = stripslashes_deep($out);

	if ($out['text_color']) {
		$out['text_color2'] = b3theme_smudge_color($out['text_color'], 40);
	}
	else {
		$out['text_color'] = $out['text_color2'] = '';
	}

	if ($out['link_color']) {
		$out['link_hover_color'] = b3theme_darken($out['link_color'], 15);
	}
	else {
		$out['link_color'] = $out['link_hover_color'] = '';
	}
	if ($out['navbar_color']) {
		$out['navbar_color2'] = b3theme_lighten($out['navbar_color'], 75);
		$out['navbar_border'] = b3theme_lighten($out['navbar_color'], 25);
	}
	else {
		$out['navbar_color'] = $out['navbar_color2'] = $out['navbar_border'] = '';
	}
	if ($out['navbar_link_color']) {
		$out['navbar_link_color2'] = b3theme_darken($out['navbar_link_color'], 40);
	}
	else {
		$out['navbar_link_color'] = $out['navbar_link_color2'] = '';
	}
	$out['copyright'] = htmlspecialchars($out['copyright']);
	$out['navbar_brand'] = htmlspecialchars($out['navbar_brand']);
	return $out;
}

function b3theme_darken($color, $percent) {
	$p = min(abs($percent), 100);
	$ratio = 1 - $p*0.01;
	$dec = hexdec(substr($color, 1));
	$b = dechex(floor((0xFF & $dec) * $ratio));
	if (strlen($b) == 1) $b = '0'.$b;
	$g = dechex(floor(((0xFF00 & $dec) >> 8) * $ratio));
	if (strlen($g) == 1) $g = '0'.$g;
	$r = dechex(floor(((0xFF0000 & $dec) >> 16) * $ratio));
	if (strlen($r) == 1) $r = '0'.$r;
	return "#$r$g$b";
}

function b3theme_lighten($color, $percent) {
	$p = min(abs($percent), 100);
	$ratio =  1 + $p*0.01;
	$dec = hexdec(substr($color, 1));
	$b = dechex(min( floor((0xFF & $dec) * $ratio), 255 ));
	if (strlen($b) == 1) $b = '0'.$b;
	$g = dechex(min( floor(((0xFF00 & $dec) >> 8) * $ratio), 255 ));
	if (strlen($g) == 1) $g = '0'.$g;
	$r = dechex(min( floor(((0xFF0000 & $dec) >> 16) * $ratio), 255 ));
	if (strlen($r) == 1) $r = '0'.$r;
	return "#$r$g$b";
}

function b3theme_smudge($c, $r) {
	$x = dechex(floor($c*(1-$r) + (255-$c)*$r));				
	if (strlen($x) == 1) $b = '0'.$x;
	return $x;
}

function b3theme_smudge_color($color, $percent) {
	$p = min(abs($percent), 100);
	$ratio = $p*0.01;
	$dec = hexdec(substr($color, 1));
	$b = b3theme_smudge(0xFF & $dec, $ratio);
	$g = b3theme_smudge((0xFF00 & $dec) >> 8, $ratio);
	$r = b3theme_smudge((0xFF0000 & $dec) >> 16, $ratio);
	return "#$r$g$b";
}

function b3theme_admin_init() {
	global $b3theme_options;
	register_setting('b3theme-settings-group', 'b3theme_options');

	if ( !empty($_GET['noheader']) && 'b3theme_save_file' == $_GET['action']) {
		$str = serialize($b3theme_options);
		if ( empty($str) ) die;
		header("Content-Type: application/octet-stream;");
		header('Content-Disposition: attachment; filename='. 'b3theme-settings-'. date('YmdHis') .'.txt');
		header('Accept-Ranges: bytes');
		header('Content-Length: '.strlen($str));
		die($str);
	}
	add_action('admin_notices', 'b3theme_update_options_notice');
	if ( !empty($_POST['b3theme_action']) ) {
		$b3theme_action = preg_replace('/[^a-z0-9_]/', '', $_POST['b3theme_action']);
		if ( !wp_verify_nonce( $_POST['_wpnonce'], 'b3theme-settings-group-options' ) ) {
			wp_die( 'Invalid or expired nonce marker.' ); 
		}
		switch ($b3theme_action) {
			case 'upload':
				if ( ($file = $_FILES['b3theme_upload_settings']) && !$file['error'] && $file['size']) {
					require_once ABSPATH . 'wp-admin/includes/class-wp-filesystem-base.php';
					require_once ABSPATH . 'wp-admin/includes/class-wp-filesystem-direct.php';
					$f = new WP_Filesystem_Direct('');
					$content = $f->get_contents($file['tmp_name']);
// I can obtain it by one call file get contents but Theme-Check grumbles
					$import = maybe_unserialize($content);
					$f->delete($file['tmp_name']);
					if (is_array($import)) {
						update_option('b3theme_options', $import);
					}
					else {
						$b3theme_action = 'import_failed';
					}
				}
				else {
					$b3theme_action = 'import_failed';
				}
				break;
			case 'reset':
				update_option('b3theme_options', array());
				break;
			case 'update':
				$b3theme_new_options = array_merge($b3theme_options, $_POST['b3theme_options']);
				update_option('b3theme_options', $b3theme_new_options);
				break;
		}
		wp_redirect( admin_url('themes.php?page=b3theme_settings&notice=' . $b3theme_action) );
		die;
	}
}

function b3theme_option_input($option_key, $name = '', $type = 'text', $default = '', $options = array()) {
	switch ($type) {
		case 'radio':
			echo '<label>' . $name . '</label>';
			$arr = $options ? $options : array('Y' => __('Yes', 'b3theme'), 'N' => __('No', 'b3theme'));
			foreach ($arr as $key => $value ) {
				echo ' <input name="b3theme_options[' . $option_key . ']" type="radio" value="'. $key . '" '
					. (b3theme_option($option_key) == $key ? 'checked' : ''). '/>'. $value . '&nbsp;';
			}
			break;

		case 'checkbox':
			echo '<input name="b3theme_options[' . $option_key . ']" type="hidden" value="N" />';
			echo '<label>' . $name . '</label>';
			echo ' <input name="b3theme_options[' . $option_key . ']" type="checkbox" value="Y" '
				. ('Y' == b3theme_option($option_key) ? ' checked' : ''). '/>';  
			break;

		case 'color':
				echo '<label>' . $name . '</label>';
				echo '<input name="b3theme_options[' . $option_key . ']" class="b3theme-color-option" data-default-color="'
					. $default . '" type="text" value="' . b3theme_option($option_key) . '" />';
		break;

		case 'text':
			echo '<label>' . $name . '</label>';
			echo '<input name="b3theme_options[' . $option_key . ']" type="text" value="' . b3theme_option($option_key) . '" />';
		break;

		case 'hidden':
			echo '<input name="b3theme_options[' . $option_key . ']" type="hidden" value="' . b3theme_option($option_key) . '" />';
		break;

		default:
			echo '<label>' . $name . '</label>';
			echo '<input name="b3theme_options[' . $option_key . ']" type="text" value="' . b3theme_option($option_key) . '" />';  
	}
}

function b3theme_update_options_notice() {
	if ( empty($_GET['notice']) ) return;
	$type = 'updated';
	switch($_GET['notice']) {
		case 'update':
			$message = __('Theme settings have been updated.', 'b3theme');
			break;
		case 'reset':
			$message = __('Theme settings have been reset.', 'b3theme');
			break;
		case 'upload':
			$message = __('Theme settings have been imported from the uploded file.', 'b3theme');
			break;
		case 'import_failed':
			$type = 'error';
			$message = __('Error: theme settings import failed.', 'b3theme');
			break;
	}
	if ( !empty($message) ) {
		echo '<div id="message" class="'. $type . ' "><p>' . $message . '</p></div>';
	}
}

function b3theme_settings_page() {
	global $b3theme_options;
?>
<div class="wrap"><div id="icon-themes" class="icon32"><br></div>
<h2><?php _e('B3 Custom Settings', 'b3theme'); ?></h2>
<form method="post" action="themes.php?page=b3theme_settings" id="b3theme_options_form" enctype="multipart/form-data">
	<?php settings_fields('b3theme-settings-group'); ?>

<div class="b3theme-settings-wrap">
	<ul class="b3theme-settings-tabs">
		<li class="tab-active"><a href="#b3theme-site-branding"><?php _e('Site Branding', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-custom-colors"><?php _e('Custom Colors', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-page-structure"><?php _e('Page Structure', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-posts-look"><?php _e('Posts Look', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-slides"><?php _e('Slides', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-backup-restore"><?php _e('Backup/Restore', 'b3theme'); ?></a></li>
		<li><a href="#b3theme-display-all"><?php _e('Display All', 'b3theme'); ?></a></li>
		<?php do_action('b3theme_settings_list_items'); ?>
	</ul>

		<div class="b3theme-settings-section b3theme-site-branding">
			<h3><?php _e('Site Branding', 'b3theme'); ?></h3>
			<div> <?php b3theme_option_input('logo_enabled', __('Display logo', 'b3theme'), 'checkbox'); ?> </div>
			<div> <?php b3theme_option_input('site_title_enabled', __('Display site title?', 'b3theme'), 'checkbox'); ?> </div>
			<div> <?php b3theme_option_input('site_description_enabled', __('Display site description?', 'b3theme'), 'checkbox'); ?></div>
			<div> <?php b3theme_option_input('navbar_brand', __('Menu Bar title', 'b3theme'), 'text'); ?></div>
			<div> <?php
				$nav_loc = array('default' => __('default', 'b3theme'), 'disable' => __('disable', 'b3theme'),
					'top' => __('top', 'b3theme'), 'fixed-top' => __('fixed top', 'b3theme'),
					'fixed-bottom' => __('fixed bottom', 'b3theme'), 'full-width' => __('full width', 'b3theme'));
				b3theme_option_input('navbar_location', __('Menu Location', 'b3theme'), 'radio', 'default', $nav_loc) ?></div>
			<div> <?php b3theme_option_input('copyright', __('Copyright text', 'b3theme'), 'text'); ?></div>
			<div> <?php b3theme_option_input('credits', __('Display credits', 'b3theme'), 'checkbox') ?></div>

		</div>
		<div class="b3theme-settings-section b3theme-custom-colors">
			<h3><?php _e('Custom Colors', 'b3theme'); ?></h3>
			<div> <?php b3theme_option_input('text_color', __('Text color', 'b3theme'), 'color', '#333333'); ?></div>
			<div> <?php b3theme_option_input('headers_color', __('Headers H1...H6 Color', 'b3theme'), 'color') ?></div>
			<div> <?php b3theme_option_input('link_color', __('Link color', 'b3theme'), 'color') ?></div>
			<div> <?php b3theme_option_input('navbar_color', __('Menu bar color', 'b3theme'), 'color', '#F8F8F8'); ?></div>
			<div> <?php b3theme_option_input('navbar_gradient', __('Menu bar gradient', 'b3theme'), 'checkbox'); ?></div>
			
			<div> <?php b3theme_option_input('navbar_link_color', __('Navigation link color', 'b3theme'), 'color', '#777777'); ?></div>

			<div> <?php b3theme_option_input('page_bgcolor', __('Page background color', 'b3theme'), 'color', ''); ?></div>

		</div>


		<div class="b3theme-settings-section b3theme-page-structure">
			<h3><?php _e('Page Structure', 'b3theme'); ?></h3>
			<div> <?php b3theme_option_input('sidebar_main', __('Main sidebar', 'b3theme'), 'radio', 'left',
				array('left'=> __('Left', 'b3theme'), 'right'=> __('Right', 'b3theme'),) ); ?></div>
			<div> <?php b3theme_option_input('sidebar_top', __('Top sidebar', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('sidebar_bottom', __('Bottom sidebar', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('disable_comment_page', __('Disable comments on pages', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('panel_widget', __('Widget as panel', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('panel_post', __('Post as panel', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('show_home', __('Home link (in pages menu)', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('reduce_404_page', __('Reduce &quot;Not Found&quot; page', 'b3theme'), 'checkbox') ?></div>

		</div>

		<div class="b3theme-settings-section b3theme-posts-look">
			<h3><?php _e('Posts Look', 'b3theme'); ?></h3>
			<div> <?php b3theme_option_input('excerpt', __('Excerpts in archives', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('post_thumbnail', __('Featured images in excerpts', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('post_date', __('Display post date', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('post_author', __('Display post author', 'b3theme'), 'checkbox') ?></div>

			<div> <?php b3theme_option_input('prev_next_link', __('Adjacent post links', 'b3theme'), 'radio', 'titles',
				array('titles' => __('Titles', 'b3theme'), 'prev_next' => __('Next/Previous', 'b3theme'), 'disable' => __('Disable', 'b3theme'))
			); ?></div>

			<div> <?php b3theme_option_input('image_rounded', __('Rounded images', 'b3theme'), 'checkbox') ?></div>
			<div> <?php b3theme_option_input('paginate_links', __('Blog pagination', 'b3theme'), 'radio', 'next',
				array('next' => __('Older/Newer', 'b3theme'), 'number' => __('Numbers', 'b3theme'),) ) ?></div>
		</div>

		<div class="b3theme-settings-section b3theme-slides">
			<h3><?php _e('Slides', 'b3theme');?></h3>


			<p> <?php b3theme_option_input('carousel', __('Homepage carousel', 'b3theme'), 'radio', 'demo',
				array('Y' => __('Yes', 'b3theme'), 'N' => __('No', 'b3theme'), 'demo' => __('Demo', 'b3theme'),)); ?></p>

			<p> <?php
				if (current_user_can('unfiltered_html')) {
					b3theme_option_input('disable_slide_the_content', __('Disable &#39;the_content&#39; filter', 'b3theme'), 'checkbox');
			}	?></p>

			<h3 class="edit-slides"><?php _e('Edit Slides', 'b3theme');?></h3>


			<input type="hidden" name="b3theme_options[slides][0]" value="dummy" />
			<?php $slides = b3theme_option('slides');
				if ( !$slides ) {
					$slides[1] = array(
						'title'=> '', 'src'=> '', 'content'=> '', 'link'=> '', 'alt'=> '',
					);
				}
				foreach ($slides as $i => $slide) {
					echo '<div id="slide-' . $i . '" ><h4><span class="space"> </span>'
						. __('Slide', 'b3theme') . ' #' . $i
						. ' &nbsp; <a class="slide-remove" href="#" onclick="b3theme_remove_slide('. $i . ');">&times;</a></span></h4>';

					echo '<p><label for="b3theme_options-slides-'. $i . '-title">'. __('Title', 'b3theme')
						. '</label><input id="b3theme_options-slides-'. $i . '-title" type="text" name="b3theme_options[slides]['
						. $i . '][title]" value="' . $slides[$i]['title'] . '"></p>';
					echo '<p><label for="b3theme_options-slides-'. $i . '-src">'
						. __('Image URL', 'b3theme') . '</label><input class="b3theme-slide-src" id="b3theme_options-slides-' . $i
						. '-src" type="text" name="b3theme_options[slides]['	. $i . '][src]" value="'
						. $slides[$i]['src'] . '" onchange="b3theme_slide_preview_change(' . $i . ');"> </p>'
						. '<p><span class="space"> </span><a href="#" id="slide-url-choose-'. $i . '" class="button button-secondary slide-url-choose" onclick="b3theme_slide_choose('
						. $i .');"><span class="media-icon"></span> ' . __('Choose', 'b3theme') . '</a> <span class="slide-preview" id="slide-preview-'
						. $i .'">' . ($slides[$i]['src'] ? '<img src="' . $slides[$i]['src'] . '" alt="" /> ' : ''). '</span></p>';

					echo '<div><label class="slide-textarea-label" for="b3theme_options-slides-' . $i . '-content">'. __('Description', 'b3theme')
						. '</label></div>';

					echo '<div id="wp-b3theme_options-slides-' . $i . '-content-wrap" class="slide-textarea-wrap"><textarea class="wp-editor-area" name="b3theme_options[slides]['. $i .'][content]" rows="5" id="b3theme_options-slides-' . $i . '-content">'
					. $slides[$i]['content'] . '</textarea></div>';
					echo '<p><label for="b3theme_options-slides-'. $i . '-link">'. __('Link', 'b3theme')
						. '</label><input id="b3theme_options-slides-'. $i . '-link" type="text" name="b3theme_options[slides]['.$i.'][link]" value="'
						. $slides[$i]['link'] . '"></p>';
					echo '<p><label for="b3theme_options-slides-'. $i . '-alt">'. __('Alt text', 'b3theme')
						. '</label><input id="b3theme_options-slides-'. $i . '-alt" type="text" name="b3theme_options[slides]['.$i.'][alt]" value="'
						. $slides[$i]['alt'] . '"></p>';
					echo '</div>';
				}
			?>
			<p><a name="add-new-slide" class="button button-secondary" href="#add-new-slide" onclick="b3theme_add_new_slide();"><?php _e('Add new slide', 'b3theme'); ?></a></p>
		</div>

		<div class="b3theme-settings-section b3theme-backup-restore">
			<h3><?php _e('Backup/Restore Settings', 'b3theme'); ?></h3>
			<div><label><?php _e('Export Settings', 'b3theme'); ?></label><a class="button button-secondary" href="themes.php?page=b3theme_settings&amp;noheader=1&amp;action=b3theme_save_file"><?php _e('Save as File', 'b3theme'); ?></a></div>
			<p>&nbsp;</p>
			<div><?php _e('Import', 'b3theme'); ?>:<br /> <input type="file" name="b3theme_upload_settings" />
				<input class="button button-primary" type="button" value="<?php _e('Upload and Apply', 'b3theme'); ?>" onclick="b3theme_upload();" />
			</div>
			<p>&nbsp;</p>			
			<div>
				<label>&nbsp;</label><input type="button" class="button button-secondary" onclick="b3theme_reset();" value="<?php _e('Reset Options', 'b3theme'); ?>" />
			</div>

		</div>
<?php
	do_settings_sections('b3theme_settings');
// (so an external plugin can add some output here)
?>
<p>&nbsp;</p>
	<p>
		<input name="b3theme_save_options" type="submit" value="<?php _e('Save Settings', 'b3theme'); ?>" class="button button-primary" id="b3theme_save_options"> 
		<input type="hidden" id="b3theme_action" name="b3theme_action" value="update" />		
	</p>
</div>
</form>
<p>&nbsp;</p>
	<p><small><?php
	$readme = '<a href="' . B3THEME_URI .'/readme.txt">readme.txt</a>';
	printf( __('Any questions? Look %1$s.', 'b3theme'), $readme); ?></small></p>
</div>
<?php
}
