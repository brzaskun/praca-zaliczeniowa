<?php
/**
*	Plugin Name: Responsive Lightbox
*	Description: This plugin offers a nice and elegant way to add Lightbox functionality for images, html content and media on your webpages.  
*	Author: subhansanjaya
*	Version: 1.3.2
*	Plugin URI: http://wordpress.org/plugins/responsive-lightbox-lite/
*	Author URI: http://weaveapps.com
*	Donate link: https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=BXBCGCKDD74UE
*	License: GPLv2 or later
*	License URI: http://www.gnu.org/licenses/gpl-2.0.html
*
* 	@package Responsive Lightbox Lite
* 	@author subhansanjaya
*/
class Responsive_Lightbox_Lite {

	//default settings
	private $defaults = array(
	'settings' => array(
		'script' => 'nivo_lightbox',
		'selector' => 'lightbox',
		'galleries' => true,
		'videos' => true,
		'image_links' => true,
		'images_as_gallery' => false,
		'deactivation_delete' => false,
		'loading_place' => 'header',
		'enable_custom_events' => false,
		'custom_events' => 'ajaxComplete'
	),
	'version' => '1.0.0'
);
	private $options = array();
	private $tabs = array();
	private $gallery_no = 0;

	public function __construct() {

		register_activation_hook(__FILE__, array(&$this, 'wa_rll_activation'));
		register_deactivation_hook(__FILE__, array(&$this, 'wa_rll_deactivation'));

		//Add admin option
		add_action('admin_menu', array(&$this, 'admin_menu_options'));
		add_action('admin_init', array(&$this, 'register_settings'));

		//add text domain for localization
		add_action('plugins_loaded', array(&$this, 'load_textdomain'));

		//load defaults
		add_action('plugins_loaded', array(&$this, 'load_defaults'));

		//update plugin version
		update_option('responsive_lightbox_lite_version', $this->defaults['version'], '', 'no');
		$this->options['settings'] = array_merge($this->defaults['settings'], (($array = get_option('responsive_lightbox_lite_settings')) === FALSE ? array() : $array));
		
		//insert js and css files
		add_action('wp_enqueue_scripts', array(&$this, 'include_scripts'));
		add_action('admin_enqueue_scripts', array(&$this, 'admin_include_scripts'));

		if($this->options['settings']['galleries'] === TRUE)
		add_filter('wp_get_attachment_link', array(&$this, 'add_lightbox_selector_gallery'), 1000, 6);

		if($this->options['settings']['videos'] === TRUE)
		add_filter('the_content', array(&$this, 'add_lightbox_selector_videos')); 

		if($this->options['settings']['image_links'] === TRUE || $this->options['settings']['images_as_gallery'] === TRUE)
		add_filter('the_content', array(&$this, 'add_lightbox_selector_images_links'));
	}

	//activation hook
	public function wa_rll_activation() {
		add_option('responsive_lightbox_lite_settings', $this->defaults['settings'], '', 'no');
		add_option('responsive_lightbox_lite_version', $this->defaults['version'], '', 'no');
	}

	//deactivation hook
	public function wa_rll_deactivation($multi = FALSE)
	{
		
		$check = $this->options['settings']['deactivation_delete'];

		if($check === TRUE)
		{
			delete_option('responsive_lightbox_lite_settings');
			delete_option('responsive_lightbox_lite_version');
		}
	}

	/* Add lightbox selector for gallery */
	public function add_lightbox_selector_gallery($link, $id, $size, $permalink, $icon, $text)
	{
		$link = (preg_match('/<a.*? rel=("|\').*?("|\')>/', $link) === 1 ? preg_replace('/(<a.*? rel=(?:"|\').*?)((?:"|\').*?>)/', '$1 '.$this->options['settings']['selector'].'[gallery-'.$this->gallery_no.']'.'$2', $link) : preg_replace('/(<a.*?)>/', '$1 rel="'.$this->options['settings']['selector'].'[gallery-'.$this->gallery_no.']'.'">', $link));

		return (preg_match('/<a.*? href=("|\').*?("|\')>/', $link) === 1 ? preg_replace('/(<a.*? href=(?:"|\')).*?((?:"|\').*?>)/', '$1'.wp_get_attachment_url($id).'$2', $link) : preg_replace('/(<a.*?)>/', '$1 href="'.wp_get_attachment_url($id).'">', $link));
	}

	/* add lightbox selectors for videos */
	public function add_lightbox_selector_videos($content)
	{
		preg_match_all('/<a(.*?)href=(?:\'|")((?:(?:http|https):\/\/)?(?:www\.)?((youtube\.com\/watch\?v=[a-z0-9_\-]+)|(vimeo\.com\/[0-9]{8,})))(?:\'|")(.*?)>/i', $content, $links);

		if(isset($links[0]))
		{
			foreach($links[0] as $id => $link)
			{
				if(preg_match('/<a.*?rel=(?:\'|")(.*?)(?:\'|").*?>/', $link, $result) === 1)
				{
					if(isset($result[1]))
					{
						$new_rels = array();
						$rels = explode(' ', $result[1]);

						if(in_array($this->options['settings']['selector'], $rels, TRUE))
						{
							foreach($rels as $no => $rel)
							{
								if($rel !== $this->options['settings']['selector'])
									$new_rels[] = $rel;
							}

							$content = str_replace($link, preg_replace('/rel=(?:\'|")(.*?)(?:\'|")/', 'rel="'.(!empty($new_rel) ? simplode(' ', $new_rels).' ' : '').$this->options['settings']['selector'].'-video-'.$id.'"', $link), $content);
						}
						else
							$content = str_replace($link, preg_replace('/rel=(?:\'|")(.*?)(?:\'|")/', 'rel="'.($result[1] !== '' ? $result[1].' ' : '').$this->options['settings']['selector'].'-video-'.$id.'"', $link), $content);
					}
				}
				else if(preg_match('/<a(.*?)href=(?:\'|")((?:(?:http|https):\/\/)?(?:www\.)?((youtube\.com\/watch\?v=[a-z0-9_\-]+)))(?:\'|")(.*?)>/', $link, $result) === 1){
					$content = str_replace($link, '<a'.$links[1][$id].'href="'.$links[2][$id].'"'.$links[6][$id].' rel="'.$this->options['settings']['selector'].'-video-'.$id.'" data-type="youtube">', $content);
			
					}else if(preg_match('/<a(.*?)href=(?:\'|")((?:(?:http|https):\/\/)?(?:www\.)?((vimeo\.com\/[0-9]{8,})))(?:\'|")(.*?)>/', $link, $result) === 1){

					$content = str_replace($link, '<a'.$links[1][$id].'href="'.$links[2][$id].'"'.$links[6][$id].' rel="'.$this->options['settings']['selector'].'-video-'.$id.'" data-type="vimeo">', $content);
			
					}

			}
		}

		return $content;
	}

	/* add lightbox selectors for images links */
	public function add_lightbox_selector_images_links($content)
	{
		preg_match_all('/<a(.*?)href=(?:\'|")([^<]*?).(bmp|gif|jpeg|jpg|png)(?:\'|")(.*?)>/i', $content, $links);
		if(isset($links[0]))
		{
			if($this->options['settings']['images_as_gallery'] === TRUE)
				$rel_hash = '[gallery-'.wp_generate_password(4, FALSE, FALSE).']';
			foreach($links[0] as $id => $link)
			{
				if(preg_match('/<a.*?rel=(?:\'|")(.*?)(?:\'|").*?>/', $link, $result) === 1)
				{
					if($this->options['settings']['images_as_gallery'] === TRUE)
					{
						$content = str_replace($link, preg_replace('/rel=(?:\'|")(.*?)(?:\'|")/', 'rel="'.$this->options['settings']['selector'].$rel_hash.'"'.($this->options['settings']['script'] === 'imagelightbox' ? ' data-imagelightbox="'.$id.'"' : ''), $link), $content);
					}
					else
					{
						if(isset($result[1]))
						{
							$new_rels = array();
							$rels = explode(' ', $result[1]);

							if(in_array($this->options['settings']['selector'], $rels, TRUE))
							{
								foreach($rels as $no => $rel)
								{
									if($rel !== $this->options['settings']['selector'])
										$new_rels[] = $rel;
								}

								$content = str_replace($link, preg_replace('/rel=(?:\'|")(.*?)(?:\'|")/', 'rel="'.(!empty($new_rels) ? implode(' ', $new_rels).' ' : '').$this->options['settings']['selector'].'-'.$id.'"'.($this->options['settings']['script'] === 'imagelightbox' ? ' data-imagelightbox="'.$id.'"' : ''), $link), $content);
							}
							else
								$content = str_replace($link, preg_replace('/rel=(?:\'|")(.*?)(?:\'|")/', 'rel="'.($result[1] !== '' ? $result[1].' ' : '').$this->options['settings']['selector'].'-'.$id.'"'.($this->options['settings']['script'] === 'imagelightbox' ? ' data-imagelightbox="'.$id.'"' : ''), $link), $content);
						}
					}
				}
				else
					$content = str_replace($link, '<a'.$links[1][$id].'href="'.$links[2][$id].'.'.$links[3][$id].'"'.$links[4][$id].' rel="'.$this->options['settings']['selector'].($this->options['settings']['images_as_gallery'] === TRUE ? $rel_hash : '-'.$id).'"'.($this->options['settings']['script'] === 'imagelightbox' ? ' data-imagelightbox="'.$id.'"' : '').'>', $content);
			}
		}
		return $content;
	}

	/* insert css files js files */
	public function include_scripts() {	

		$args = apply_filters('rll_lightbox_args', array(
			'script' => $this->options['settings']['script'],
			'selector' => $this->options['settings']['selector'],
			'custom_events' => ($this->options['settings']['enable_custom_events'] === TRUE ? ' '.$this->options['settings']['custom_events'] : '')
		));

		if($args['script'] === 'nivo_lightbox'){

			wp_register_script('responsive-lightbox-nivo_lightbox',plugins_url('assets/nivo-lightbox/nivo-lightbox.min.js', __FILE__),array('jquery'),'',($this->options['settings']['loading_place'] === 'header' ? false : true));
			wp_enqueue_script('responsive-lightbox-nivo_lightbox');

			wp_register_style('responsive-lightbox-nivo_lightbox-css',plugins_url('assets/nivo-lightbox/nivo-lightbox.css', __FILE__));
			wp_enqueue_style('responsive-lightbox-nivo_lightbox-css');

					wp_register_style('responsive-lightbox-nivo_lightbox-css-d',plugins_url('assets/nivo-lightbox/themes/default/default.css', __FILE__));
			wp_enqueue_style('responsive-lightbox-nivo_lightbox-css-d');

		}
			wp_register_script('responsive-lightbox-lite-script',plugins_url('assets/inc/script.js', __FILE__),array('jquery'),'',($this->options['settings']['loading_place'] === 'header' ? false : true));
			wp_enqueue_script('responsive-lightbox-lite-script');

			wp_localize_script('responsive-lightbox-lite-script','rllArgs',$args);
	}

	/* insert css files for admin area */
	public function admin_include_scripts() {
			wp_register_style('responsive-lightbox-lite-admin',plugins_url('assets/css/admin.css', __FILE__));
			wp_enqueue_style('responsive-lightbox-lite-admin');
	}

	public function admin_menu_options()
	{
		add_options_page(
			__('Responsive Lightbox Lite', 'responsive-lightbox-lite'),
			__('Responsive Lightbox Lite', 'responsive-lightbox-lite'),
			'manage_options',
			'responsive-lightbox-lite',
			array(&$this, 'options_page')
		);
	}

	/* register setting for plugins page */
	public function register_settings()
	{
		register_setting('responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings', array(&$this, 'validate_options'));
		//general settings
		add_settings_section('responsive_lightbox_lite_settings', __('', 'responsive-lightbox-lite'), '', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_galleries', __('Galleries', 'responsive-lightbox-lite'), array(&$this, 'rll_galleries'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_videos', __('Video links', 'responsive-lightbox-lite'), array(&$this, 'rll_videos'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_image_links', __('Image links', 'responsive-lightbox-lite'), array(&$this, 'rll_image_links'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_enable_custom_events', __('Custom events', 'responsive-lightbox-lite'), array(&$this, 'rll_enable_custom_events'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_loading_place', __('Loading place', 'responsive-lightbox-lite'), array(&$this, 'rll_loading_place'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
		add_settings_field('rll_deactivation_delete', __('Deactivation', 'responsive-lightbox-lite'), array(&$this, 'rll_deactivation_delete'), 'responsive_lightbox_lite_settings', 'responsive_lightbox_lite_settings');
	}




	public function rll_enable_custom_events()
	{
		echo '
		<div id="rll_enable_custom_events" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			$val = esc_attr($val);

			echo '
			<input id="rll-enable-custom-events-'.$val.'" type="radio" name="responsive_lightbox_settings[enable_custom_events]" value="'.$val.'" '.checked(($val === 'yes' ? true : false), $this->options['settings']['enable_custom_events'], false).' />
			<label for="rll-enable-custom-events-'.$val.'">'.esc_html($trans).'</label>';
		}

		echo '
			<p class="description">'.__('Enable triggering lightbox on custom jquery events.', 'responsive-lightbox').'</p>
			<div id="rll_custom_events"'.($this->options['settings']['enable_custom_events'] === false ? ' style="display: none;"' : '').'>
				<input type="text" name="responsive_lightbox_settings[custom_events]" value="'.esc_attr($this->options['settings']['custom_events']).'" />
				<p class="description">'.__('Enter a space separated list of events.', 'responsive-lightbox').'</p>
			</div>
		</div>';
	}

	public function rll_loading_place()
	{
		echo '
		<div id="rll_loading_place" class="wplikebtns">';

		foreach($this->loading_places as $val => $trans)
		{
			$val = esc_attr($val);

			echo '
			<input id="rll-loading-place-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[loading_place]" value="'.$val.'" '.checked($val, $this->options['settings']['loading_place'], false).' />
			<label for="rll-loading-place-'.$val.'">'.esc_html($trans).'</label>';
		}

		echo '
			<p class="description">'.__('Select where all the lightbox scripts should be placed.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function rll_galleries()
	{
		echo '
		<div id="rll_galleries" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			echo '
			<input id="rll-galleries-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[galleries]" value="'.esc_attr($val).'" '.checked(($val === 'yes' ? TRUE : FALSE), $this->options['settings']['galleries'], FALSE).' />
			<label for="rll-galleries-'.$val.'">'.$trans.'</label>';
		}

		echo '
			<p class="description">'.__('Add lightbox to WordPress image galleries by default.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function rll_videos()
	{
		echo '
		<div id="rll_videos" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			echo '
			<input id="rll-videos-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[videos]" value="'.esc_attr($val).'" '.checked(($val === 'yes' ? TRUE : FALSE), $this->options['settings']['videos'], FALSE).' />
			<label for="rll-videos-'.$val.'">'.$trans.'</label>';
		}

		echo '
			<p class="description">'.__('Add lightbox to YouTube and Vimeo video links by default.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function rll_image_links()
	{
		echo '
		<div id="rll_image_links" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			echo '
			<input id="rll-image-links-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[image_links]" value="'.esc_attr($val).'" '.checked(($val === 'yes' ? TRUE : FALSE), $this->options['settings']['image_links'], FALSE).' />
			<label for="rll-image-links-'.$val.'">'.$trans.'</label>';
		}

		echo '
			<p class="description">'.__('Add lightbox to WordPress image links by default.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function rll_images_as_gallery()
	{
		echo '
		<div id="rll_images_as_gallery" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			echo '
			<input id="rll-images-as-gallery-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[images_as_gallery]" value="'.esc_attr($val).'" '.checked(($val === 'yes' ? TRUE : FALSE), $this->options['settings']['images_as_gallery'], FALSE).' />
			<label for="rll-images-as-gallery-'.$val.'">'.$trans.'</label>';
		}

		echo '
			<p class="description">'.__('Display single post images as a gallery.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function rll_deactivation_delete()
	{
		echo '
		<div id="rll_deactivation_delete" class="wplikebtns">';

		foreach($this->choices as $val => $trans)
		{
			echo '
			<input id="rll-deactivation-delete-'.$val.'" type="radio" name="responsive_lightbox_lite_settings[deactivation_delete]" value="'.esc_attr($val).'" '.checked(($val === 'yes' ? TRUE : FALSE), $this->options['settings']['deactivation_delete'], FALSE).' />
			<label for="rll-deactivation-delete-'.$val.'">'.$trans.'</label>';
		}

		echo '
			<p class="description">'.__('Delete settings on plugin deactivation.', 'responsive-lightbox').'</p>
		</div>';
	}

	public function options_page()
	{
		$tab_key = (isset($_GET['tab']) ? $_GET['tab'] : 'general-settings');

		echo '<div class="wrap">'.screen_icon().'
			<h2>'.__('Responsive Lightbox Lite', 'responsive-lightbox-lite').'</h2>
			<h2 class="nav-tab-wrapper">';

		foreach($this->tabs as $key => $name) {
			echo '
			<a class="nav-tab '.($tab_key == $key ? 'nav-tab-active' : '').'" href="'.esc_url(admin_url('options-general.php?page=responsive-lightbox-lite&tab='.$key)).'">'.$name['name'].'</a>';
		}

		echo '
			</h2>
			<div class="responsive-lightbox-settings">
				<div class="wa-credits">
					<h3 class="hndle">'.__('Responsive Lightbox Lite', 'responsive-lightbox').'</h3>
					<div class="inside">
						<p class="inner">'.__('Plugin URI: ', 'responsive-lightbox').' <a href="http://weaveapps.com/shop/wordpress-plugins/responsive-lightbox-lite/" target="_blank" title="'.__('Plugin URL', 'responsive-lightbox-lite').'">'.__('Weave Apps', 'responsive-lightbox-lite').'</a></p>
					</p> 
					<hr />
					<h4 class="inner">'.__('Do you like this plugin?', 'responsive-lightbox').'</h4>
					<p class="inner">'.__('Please, ', 'wa-wps-txt').'<a href="http://wordpress.org/support/view/plugin-reviews/responsive-lightbox-lite" target="_blank" title="'.__('rate it', 'wa-wps-txt').'">'.__('rate it', 'wa-wps-txt').'</a> '.__('on WordPress.org', 'wa-wps-txt').'<br />                     
						<hr />
					<div style="width:auto; margin:auto; text-align:center;"><a href="http://weaveapps.com/shop/wordpress-plugins/responsive-lightbox-wordpress-plugin/" target="_blank"><img width="270" height="70" src="'.plugins_url('assets/images/pro.png',__FILE__).'"/></a></div>
					
					</div>
				</div><form action="options.php" method="post">
				<input type="hidden" name="script_r" value="'.esc_attr($this->options['settings']['script']).'" />';

		wp_nonce_field('update-options');
		settings_fields($this->tabs[$tab_key]['key']);
		do_settings_sections($this->tabs[$tab_key]['key']);

		echo '<p class="submit">';
		submit_button('', 'primary', $this->tabs[$tab_key]['submit'], FALSE);
		echo ' ';
		echo submit_button(__('Reset to defaults', 'responsive-lightbox'), 'secondary', $this->tabs[$tab_key]['reset'], FALSE);
		echo '</p></form></div><div class="clear"></div></div>';
	}

	public function load_defaults()
	{
		
		$this->choices = array(
			'yes' => __('Enable', 'responsive-lightbox-lite'),
			'no' => __('Disable', 'responsive-lightbox-lite')
		);

		$this->loading_places = array(
			'header' => __('Header', 'responsive-lightbox-lite'),
			'footer' => __('Footer', 'responsive-lightbox-lite')
		);

		$this->tabs = array(
			'general-settings' => array(
				'name' => __('General settings', 'responsive-lightbox-lite'),
				'key' => 'responsive_lightbox_lite_settings',
				'submit' => 'save_rll_settings',
				'reset' => 'reset_rll_settings',
			)
		);
	}

	/* load text domain for localization */
	public function load_textdomain()
	{
		load_plugin_textdomain('responsive-lightbox-lite', FALSE, dirname(plugin_basename(__FILE__)).'/languages/');
	}

	/* validate options and register settings */
	public function validate_options($input)
	{
		if(isset($_POST['save_rll_settings']))
		{

			// selector
			$input['selector'] = sanitize_text_field(isset($input['selector']) && $input['selector'] !== '' ? $input['selector'] : $this->defaults['settings']['selector']);

			// loading place
			$input['loading_place'] = (isset($input['loading_place'], $this->loading_places[$input['loading_place']]) ? $input['loading_place'] : $this->defaults['settings']['loading_place']);

			// enable custom events
			$input['enable_custom_events'] = (isset($input['enable_custom_events'], $this->choices[$input['enable_custom_events']]) ? ($input['enable_custom_events'] === 'yes' ? true : false) : $this->defaults['settings']['enable_custom_events']);

			// custom events
			if($input['enable_custom_events'] === true)
			{
				$input['custom_events'] = sanitize_text_field(isset($input['custom_events']) && $input['custom_events'] !== '' ? $input['custom_events'] : $this->defaults['settings']['custom_events']);
			}

			// checkboxes
			$input['galleries'] = (isset($input['galleries'], $this->choices[$input['galleries']]) ? ($input['galleries'] === 'yes' ? true : false) : $this->defaults['settings']['galleries']);
			$input['videos'] = (isset($input['videos'], $this->choices[$input['videos']]) ? ($input['videos'] === 'yes' ? true : false) : $this->defaults['settings']['videos']);
			$input['image_links'] = (isset($input['image_links'], $this->choices[$input['image_links']]) ? ($input['image_links'] === 'yes' ? true : false) : $this->defaults['settings']['image_links']);
			$input['images_as_gallery'] = (isset($input['images_as_gallery'], $this->choices[$input['images_as_gallery']]) ? ($input['images_as_gallery'] === 'yes' ? true : false) : $this->defaults['settings']['images_as_gallery']);
			$input['deactivation_delete'] = (isset($input['deactivation_delete'], $this->choices[$input['deactivation_delete']]) ? ($input['deactivation_delete'] === 'yes' ? true : false) : $this->defaults['settings']['deactivation_delete']);
		

		}elseif(isset($_POST['reset_rll_settings']))
		{
			$input = $this->defaults['settings'];

			add_settings_error('reset_general_settings', 'general_reset', __('Settings restored to defaults.', 'responsive-lightbox-lite'), 'updated');
		}

		return $input;
	}
}
$responsive_lightbox = new Responsive_Lightbox_Lite();