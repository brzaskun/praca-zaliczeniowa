<?php

/**
 * Omaha theme
 * 
 * @package omaha
 */
/**
 * Required WordPress variable.
 */

require_once('wp_bootstrap_navwalker.php');

if (!isset($content_width)) {
	$content_width = 1040;
}

/**
 * Setup theme and register support wp features.
 */
function omahaSetup() {
	/**
	 * Make theme available for translation
	 * Translations can be filed in the /languages/ directory
	 * 
	 * copy from underscores theme
	 */
	load_theme_textdomain('omaha', get_template_directory() . '/languages');

	// add theme support post and comment automatic feed links
	add_theme_support('automatic-feed-links');

	// enable support for post thumbnail or feature image on posts and pages
	add_theme_support('post-thumbnails');

	add_theme_support('custom-header');
	add_theme_support('custom-background');
	add_editor_style('custom-editor-style.css');

	// add support menu
	register_nav_menus(array(
		'primary' => __('Primary Menu', 'omaha'),
	));
}

// omahaSetup
add_action('after_setup_theme', 'omahaSetup');

/**
 * Register widget areas
 */
function omahaWidgetsInit() {
	register_sidebar(array(
		'name' => __('Header right', 'omaha'),
		'id' => 'header-right',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));

	register_sidebar(array(
		'name' => __('Footer left', 'bootstrap-basic'),
		'id' => 'footer-left',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));

	register_sidebar(array(
		'name' => __('Footer right', 'bootstrap-basic'),
		'id' => 'footer-right',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));
	
	
	register_sidebar(array(
		'name' => __('Footer center', 'bootstrap-basic'),
		'id' => 'footer-center',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));
	
	register_sidebar(array(
		'name' => __('Footer right-2', 'bootstrap-basic'),
		'id' => 'footer-right-2',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));
	
		register_sidebar(array(
		'name' => __('Footer bottom', 'bootstrap-basic'),
		'id' => 'footer-bottom',
		'before_widget' => '<div id="%1$s" class="widget %2$s">',
		'after_widget' => '</div>',
		'before_title' => '<h1 class="widget-title">',
		'after_title' => '</h1>',
	));
	
}

// omahaWidgetsInit
add_action('widgets_init', 'omahaWidgetsInit');

/**
 * Enqueue scripts & styles
 */
function omahaEnqueueScripts() {
	wp_enqueue_style('google-font', 'http://fonts.googleapis.com/css?family=Lato:400,700|Titillium+Web:600,400,700&subset=latin-ext,latin');
	wp_enqueue_style('bootstrap-style', get_template_directory_uri() . '/css/bootstrap.min.css');
	wp_enqueue_style('fontawesome-style', get_template_directory_uri() . '/css/font-awesome.min.css');
	wp_enqueue_style('omaha-style', get_stylesheet_uri());

	wp_enqueue_script('modernizr-script', get_template_directory_uri() . '/js/vendor/modernizr.min.js');
	wp_enqueue_script('respond-script', get_template_directory_uri() . '/js/vendor/respond.min.js');
	wp_enqueue_script('html5-shiv-script', get_template_directory_uri() . '/js/vendor/html5shiv.js');
	wp_enqueue_script('bootstrap-script', get_template_directory_uri() . '/js/vendor/bootstrap.min.js', array('jquery'));

	if (is_singular())
		wp_enqueue_script('comment-reply');
}

// omahaEnqueueScripts
add_action('wp_enqueue_scripts', 'omahaEnqueueScripts');


require get_template_directory() . '/inc/template-tags.php';

add_filter('img_caption_shortcode', 'omaha_captions', 10, 3);

function omaha_captions($x = null, $attr, $content) {

	extract(shortcode_atts(array(
		'id' => '',
		'align' => 'alignnone',
		'width' => '',
		'caption' => ''
					), $attr));

	if (1 > (int) $width || empty($caption)) {
		return $content;
	}

	if ($id)
		$id = 'id="' . $id . '" ';

	return '<div ' . $id . 'class="wp-caption ' . $align . '" style="width: ' . ((int) $width) . 'px">'
			. $content . '<p class="wp-caption-text">' . $caption . '</p></div>';
}

add_filter('wp_title', 'omaha_wp_title');

function omaha_wp_title($title) {
	if (empty($title) && ( is_home() || is_front_page() )) {
		return bloginfo('name') . ' | ' . get_bloginfo('description');
	}
	return $title;
}
