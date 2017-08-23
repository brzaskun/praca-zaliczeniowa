<?php

/**
 * B3 functions and definitions
 *
 * @package B3
 */

define('B3THEME_URI', get_template_directory_uri());
define('B3THEME_PATH', get_template_directory());

function add_b3theme_menu() {
	add_theme_page(__('B3 Theme settings', 'b3theme'), __('B3 Theme settings', 'b3theme'), 'edit_theme_options', 'b3theme_settings', 'b3theme_settings_page');
}

add_action('admin_menu', 'add_b3theme_menu');

/**
 * Sets up theme defaults and registers support for various WordPress features.
 *
 * Note that this function is hooked into the after_setup_theme hook, which runs
 * before the init hook. The init hook is too late for some features, such as indicating
 * support post thumbnails.
 */

function b3theme_empty_array($arr) {
	return array();
}

function b3theme_setup() {
global $b3theme_options;
	load_theme_textdomain('b3theme', get_template_directory() . '/languages');
	add_theme_support('automatic-feed-links');
	register_nav_menus(array('primary' => __('Primary Menu', 'b3theme'),));
	add_theme_support('post-formats', array('aside', 'image', 'video', 'quote', 'link'));
	add_theme_support('custom-background', array('default-color' => 'ffffff', 'default-image' => '') );
	add_theme_support('post-thumbnails');
	add_filter('wp_nav_menu_container_allowedtags', 'b3theme_empty_array');
	add_filter('wp_nav_menu_args', 'b3theme_wp_nav_menu_args');
	require B3THEME_PATH . '/inc/options.php';
	$b3theme_options = get_option('b3theme_options');
	if (!$b3theme_options) {
		$b3theme_options = b3theme_sanitize_options(array());
	}
	add_action('admin_init', 'b3theme_admin_init');
}

add_action('after_setup_theme', 'b3theme_setup');

if ( !isset( $content_width ) ) {
	$content_width = 970;
}

/*
	$content_width not really used currently in this theme but required by Wordpress theme autochecker.
	Content width is calculated and being set by Bootstrap.
*/

function b3theme_option($key) {
	global $b3theme_options;
	if (isset($b3theme_options[$key])) {
		return $b3theme_options[$key];
	}
	return '';
}

function b3theme_wp_nav_menu_args($args) {
	$args['container'] = false;
	return $args;
}

function b3theme_wp_page_menu($args) {
	$pages = get_pages();
	foreach($pages as $key=>$page) {
		$pages[$key]->db_id = $page->ID;
		$pages[$key]->menu_item_parent = $page->post_parent;
		$pages[$key]->url = get_page_link($page->ID);
	}
	$pages =  b3theme_wp_nav_menu_objects($pages);
	$out = '<ul class="nav navbar-nav">';

	if ('Y'==b3theme_option('show_home')) {
		$class = '';
		if ( is_front_page() && !is_paged() ) {
			$class = 'class="current_page_item"';
		}
		$out .= '<li ' . $class . '><a href="' . home_url('/') . '">'
			. $args['link_before'] . __('Home', 'b3theme') . $args['link_after'] . '</a></li>';
	}

	$walker = new Tb3theme_Walker_Nav_Menu();
	$walker->before = '';
	$walker->after = '';
	$walker->link_before = '';
	$walker->link_after = '';
	$tree = $walker->walk($pages, 0);
	$out .= $tree;
	$out .= '</ul>';
	return $out;
}

add_filter('wp_nav_menu_objects', 'b3theme_wp_nav_menu_objects');

function b3theme_wp_nav_menu_objects ($menu_items) {
	foreach($menu_items as $key => $item) {
		$sort_num_of[$item->db_id] = $key;
	}
	foreach($menu_items as $key => $item) {
		if ($item->menu_item_parent) {
			$menu_items[ $sort_num_of[$item->menu_item_parent] ]->has_children = 1;
		}
	}
	return $menu_items;
}

class Tb3theme_Walker_Nav_Menu extends Walker_Nav_Menu {

	function start_el( &$output, $item, $depth = 0, $args = array(), $id = 0 ) {
		$has_children = empty($item->has_children) ? false : true;
		$indent = ( $depth ) ? str_repeat( "\t", $depth ) : '';
		$class_names = $value = '';

		$classes = empty( $item->classes ) ? array() : (array) $item->classes;
		$classes[] = 'menu-item-' . $item->ID;
		if($has_children) {
			if ($depth ) {
				$classes[] = 'dropdown-submenu';
			}
			else {
				$classes[] = 'dropdown';
			}
		}
		$classes[] = 'depth-'.$depth;

		if (!is_object($args)) {
			$args = (object)array_merge(array('link_before' => '', 'link_after' => '', 'before' => '', 'after' => ''), $args);
		}
		$class_names = join(' ', apply_filters('nav_menu_css_class', array_filter( $classes ), $item, $args ) );
		$class_names = $class_names ? ' class="' . esc_attr( $class_names ) . '"' : '';

		$id = apply_filters('nav_menu_item_id', 'menu-item-'. $item->ID, $item, $args);
		$id = $id ? ' id="' . esc_attr( $id ) . '"' : '';

		$output .= $indent . '<li' . $id . $value . $class_names .'>';

		$attributes  = ! empty( $item->attr_title ) ? ' title="'  . esc_attr( $item->attr_title ) .'"' : '';
		$attributes .= ! empty( $item->target )     ? ' target="' . esc_attr( $item->target     ) .'"' : '';
		$attributes .= ! empty( $item->xfn )        ? ' rel="'    . esc_attr( $item->xfn        ) .'"' : '';
		$attributes .= ! empty( $item->url )        ? ' href="'   . esc_attr( $item->url        ) .'"' : '';

		$item_output = $args->before;
		$item_output .= '<a'. $attributes
			. ( !empty($item->has_children) ? ' class="dropdown-toggle" data-toggle="dropdown"' :'')
			. '>';
		$item_output .= $args->link_before
			. apply_filters('the_title', !empty($item->post_title) ? $item->post_title : $item->title, $item->ID)
			. $args->link_after;
		
		$item_output .= (!empty($item->has_children) && !$depth) ? ' <b class="caret"></b>' :'';
		$item_output .= '</a>';
		$item_output .= $args->after;
		$output .= apply_filters('walker_nav_menu_start_el', $item_output, $item, $depth, $args );
	}
}

function b3theme_widgets_init() {
	if ('Y' == b3theme_option('panel_widget')) {
		$args= array(
			'before_widget' => '<aside id="%1$s" class="widget %2$s panel panel-default">',
			'after_widget' => '</div><!-- /.panel-body --></aside>',
			'before_title' => '',
			'after_title' => '', // see b3theme_panel_widget_title()
		);
	}
	else {
		$args= array(
			'before_widget' => '<aside id="%1$s" class="widget %2$s">',
			'after_widget' => '</aside>',
			'before_title' => '<h3 class="widget-title">',
			'after_title' => '</h3>',
		);
	}
	$args['name'] = __('Sidebar 1', 'b3theme');
	$args['id']   = 'sidebar-1';
	register_sidebar($args);

	register_sidebar(array(
		'name'          => __('Top Sidebar', 'b3theme'),
		'id'            => 'sidebar-top',
		'before_widget' => '<aside id="%1$s" class="widget %2$s">',
		'after_widget'  => '</aside>',
		'before_title'  => '<h3 class="widget-title">',
		'after_title'   => '</h3>',
	));
	register_sidebar( array(
		'name'          => __('Bottom Sidebar', 'b3theme'),
		'id'            => 'sidebar-bottom',
		'before_widget' => '<aside id="%1$s" class="widget %2$s col-md-3 col-sm-4 col-xs-12">',
		'after_widget'  => '</aside>',
		'before_title'  => '<h3 class="widget-title">',
		'after_title'   => '</h3>',
	));

}
add_action('widgets_init', 'b3theme_widgets_init');

function b3theme_panel_widget_title($title) {
	if ('Y' == b3theme_option('panel_widget')) {
		if (!$title) {
			$title = '&nbsp;';
		}
		$out = '<div class="panel-heading"><h3 class="widget-title panel-title">'
		. $title . '</h3></div><div class="panel-body">';
	}
	else {
		$out = $title;
	}
	return $out;
}


function b3theme_enqueue_scripts() {
	global $wp_scripts;
	wp_enqueue_script('bootstrap', apply_filters('b3theme_bootstrap_js', B3THEME_URI . '/bootstrap/js/bootstrap.min.js'),
			array('jquery'), '3.1.1', true);
// apply_filters - a way to implement bootstrap CDN by plugins.
	wp_enqueue_script('b3theme',  B3THEME_URI . '/js/b3theme.js', array('jquery'), null, true);
	if ( is_singular() && comments_open() && get_option('thread_comments') ) {
		wp_enqueue_script('comment-reply');
	}
	if (is_singular() && wp_attachment_is_image()) {
		wp_enqueue_script('b3theme-keyboard-image-navigation',  B3THEME_URI . '/js/keyboard-image-navigation.js', array('jquery'), '20120202');
	}
}

add_action('wp_enqueue_scripts', 'b3theme_enqueue_scripts');

function b3theme_enqueue_styles() {
	global $wp_styles;
	wp_enqueue_style('bootstrap', apply_filters('b3theme_bootstrap_css', B3THEME_URI . '/bootstrap/css/bootstrap.min.css'));
	wp_enqueue_style('b3theme',  B3THEME_URI . '/style.css');
	wp_add_inline_style('b3theme', b3theme_options_css());
	wp_enqueue_style('ie8',  B3THEME_URI . '/css/ie8.css');
	$wp_styles->add_data('ie8', 'conditional', 'lt IE 9');
}

add_action('wp_enqueue_scripts', 'b3theme_enqueue_styles');

function b3theme_admin_enqueue_scripts($hook_suffix) {
	if ( 'appearance_page_b3theme_settings' === $hook_suffix ) {
		wp_enqueue_script('b3theme-admin', B3THEME_URI . '/js/b3theme-admin.js', array('wp-color-picker'), null, true);
		wp_localize_script('b3theme-admin', 'b3theme_admin', b3theme_admin_localize());
		wp_enqueue_script('quicktags', '', array('jquery'));
		wp_enqueue_media();
		wp_enqueue_style('b3theme-admin', B3THEME_URI . '/css/b3theme-admin.css');
		wp_enqueue_style('wp-color-picker');
		wp_enqueue_style('quicktags', includes_url('css/editor.min.css'));
	}
}

add_action('admin_enqueue_scripts', 'b3theme_admin_enqueue_scripts');

function b3theme_options_css() {
	$out = '';
	if ('left' == b3theme_option('sidebar_main')) {
		$out .= '#secondary {float: left;} #primary {float: right;} ';
	}
	if (($css = b3theme_option('text_color')) && $css != '#333333') {
		$out .= 'body {color:'. $css . ';} ';
		$out .= '.entry-meta {color:'. b3theme_option('text_color2') . ';} ';
	}
	if ($css = b3theme_option('headers_color')) {
		$out .= 'h1,h2,h3,h4,h5,h6 {color:'. $css . ';} ';
	}
	if ($css = b3theme_option('link_color')) {
		$out .= 'a, a:visited {color:'. $css . ';} ';
	}
	if ($css = b3theme_option('link_hover_color')) {
		$out .= 'a:hover {color:'. $css . ';} ';
		$out .= 'ul.pagination li.active span, ul.pagination li.active span:hover {background-color:'
			. $css . '; border: solid 1px '. $css . ';} ';
	}
	if (($css = b3theme_option('navbar_color')) && '#F8F8F8' != $css) {
		$out .= '.navbar-b3theme {background-color: ' . $css . '; border: ' . b3theme_option('navbar_border') .' solid 1px;}';
		if ('Y' == b3theme_option('navbar_gradient')) {
			$color2 = b3theme_option('navbar_color2');
			$g = 'linear-gradient(top, ' . $css . ', '. $color2 .')';
			$gg = 'linear-gradient(to bottom, ' . $css . ', '. $color2 .')';
			$out .= '.navbar-b3theme {background-image: -webkit-' . $g
				. '; background-image: -ms-' . $g . '; background-image: ' . $gg . ';}';
		}
	}
	if (($css = b3theme_option('navbar_link_color')) && '#777777' != $css) {
		$out .= '.navbar-nav > li > a, .navbar-nav > li > a:visited, a.navbar-brand {color:'. $css . ' !important;} ';
		$out .= '.navbar-nav > li > a:hover, a.navbar-brand:hover {color:'. b3theme_option('navbar_link_color2') . ' !important;} ';
	}
	$navbar_default = false;

	switch (b3theme_option('navbar_location')) {
		case 'default':
			$navbar_default = true;
			break;
		case 'top':
			$out .= '.navbar-b3theme {border-top: none; margin-bottom: 10px;}';
			break;
		case 'fixed-top':
			$out .= 'body {padding-top: 61px;} .navbar-b3theme {border-top: none;}';
			break;
		case 'fixed-bottom':
			$out .= 'body {padding-bottom: 50px;} .navbar-b3theme {border-bottom: none;}';
			break;
		case 'full-width':
			$out .= '.navbar-b3theme {position: absolute; left: 0; width: 100%;} .site-header {margin-bottom: 67px;}';
			break;			
	}
	if (!$navbar_default) {
		$out .= '.navbar-b3theme {border-radius: 0; border-right: none; border-left: none;}';
	}

	if ( ($bgcolor = b3theme_option('page_bgcolor')) ) {
		$out .= '#page {background: '.  $bgcolor .'}';
	}
	return $out;
}

function b3theme_comment_form_before() {
	echo '<div class="form-group">';
}

function b3theme_comment_form_after() {
	echo '</div>';
}

function b3theme_post_class($classes) {
	if ( 'Y'==b3theme_option('panel_post') || in_array('sticky', $classes) ) {
		$classes[]= 'panel-body';
	}
	$classes[]= 'clearfix';
	return $classes;
}

function b3theme_content_wrap_class() {
	$classes = array();
	if ( 'Y'==b3theme_option('panel_post') || !is_404() && is_sticky() && !is_paged() ) {
		$classes[]= 'panel';
	}
	if ($classes) {
		echo 'class="' . implode(' ', $classes) . '"';
	}
}

add_filter('post_class', 'b3theme_post_class');
add_action('comment_form_before', 'b3theme_comment_form_before');
add_action('comment_form_after', 'b3theme_comment_form_after');

if (!function_exists('b3theme_carousel')):
	function b3theme_carousel() {
		$case = b3theme_option('carousel');
		if ('N' == $case) {
			return;
		}
		else {
				if ('Y' == $case) {
					$slides = b3theme_option('slides');
				}
				else {
					include B3THEME_PATH . '/inc/demo-slides.php';
				}
				if ( $total = count($slides) ) {
?>
		<div id="b3theme-slider" class="carousel slide spacer-bottom" data-ride="carousel" >
<?php if ( $total > 1 ) { ?>
		<ol class="carousel-indicators">
<?php
					for($i=0; $i<$total; $i++) {
						echo '<li data-target="#b3theme-slider" data-slide-to="' . $i . '" ' . ($i ? '' : 'class="active"') . '></li>';
					} ?>
		</ol>
<?php } ?>
		<div class="carousel-inner clearfix" >
<?php

			foreach ($slides as $c => $slide ) {
?>
					<div class="item <?php echo ($c>1) ? '' : 'active'; ?>" >
						<a href="<?php echo $slide['link'] ? $slide['link'] : '#'; ?>"><img style="width:100%;" src="<?php echo $slide['src']; ?>" alt="<?php echo $slide['alt'] ?>" /></a>
						<div class="container">
								<div class="carousel-caption">
									<h1><a href="<?php echo $slide['link']; ?>"><?php echo apply_filters('the_title', $slide['title']); ?></a></h1>
								<?php
									if ('N' != b3theme_option('disable_slide_the_content')) {
										$slide['content'] = apply_filters('the_content', $slide['content']);
									}
									if (is_multisite()) {
										$slide['content'] = wp_kses_post($slide['content']);
									}
									echo '<div>' . $slide['content'] . '</div>';
								?>
								</div>
						 </div>
					</div>
<?php
	
			}
?>
					</div><!-- /.carousel-inner -->
				<?php
				if ($total>1) { ?>
					<a class="left carousel-control" href="#b3theme-slider" data-slide="prev"><span class="glyphicon glyphicon-chevron-left icon-large"></span></a>
					<a class="right carousel-control" href="#b3theme-slider" data-slide="next"><span class="glyphicon glyphicon-chevron-right glyphicon-large"></span></a>
				 <?php
				} ?>
		</div>
<?php
			} else {
				echo '<div class="alert alert-warning text-center">';
				printf( __('You have no slides yet. You can <a href="%1$s">add your slides</a> or activate demo slides. The optimum image width/height ratio is %2$s, the best image size: %3$s.', 'b3theme'),
					admin_url('themes.php?page=b3theme_settings'),
					'17:5', '1140 &times; 300 pixel');
			echo '</div>';
			}
		}
	}

endif;

function b3theme_footer_script() {
	if ('Y' == b3theme_option('image_rounded')) {
?>
<script type="text/javascript">
	jQuery('.entry-content img, img.avatar').addClass('img-rounded');
</script>
<?php
	}
}

add_action('wp_footer', 'b3theme_footer_script');

function b3theme_admin_localize() {
	$out = array(
		'reset'	=> __('All theme settings will be replaced by default dettings. Continue?', 'b3theme'),
		'upload' => __('Theme settings will be replaced by settings from the uploaded file. Continue?', 'b3theme'),
		'slide_remove' => __('This slide will be removed. Continue?', 'b3theme'),
		'slide' => __('Slide', 'b3theme'),
		'link' => __('Link', 'b3theme'),
		'image_url' => __('Image URL', 'b3theme'),
		'description' => __('Description', 'b3theme'),
		'title' => __('Title', 'b3theme'),
		'alt_text' => __('Alt text', 'b3theme'),
		'choose' => __('Choose', 'b3theme'),
		'choose_image' => __('Choose Image', 'b3theme'),
		'specify_slide_url' => __('Please specify image URL for each slide or remove unnecessary slides.', 'b3theme'),
	);
	return $out;
}

function b3theme_ie_conditional() { ?>
<!--[if lt IE 9]>
	<script src="<?php echo B3THEME_URI; ?>/js/respond.min.js"></script>
	<script src="<?php echo B3THEME_URI; ?>/js/html5shiv.js"></script>
<![endif]-->
<?php
}

add_action('wp_head', 'b3theme_ie_conditional');

function b3theme_wp_link_pages_link($link) {
	$active = '';
	if ( !strpos($link, '</a>')) {
		$active = ' class="active"';
		$link = '<span>' . $link . '<span class="sr-only">(current)</span></span>';
	}
	$link = '<li'. $active. '>' . $link . '</li>';
	return $link;
}

function b3theme_wp_link_pages_args($args) {
	$args['before'] = '<div class="text-center"><ul class="pagination"><li class="disabled"><span>' . __('Pages:', 'b3theme') . '</span></li>';
	$args['after']  = '</ul></div>';
	$args['separator'] = '';
	return $args;
}

add_filter('wp_link_pages_link', 'b3theme_wp_link_pages_link');
add_filter('wp_link_pages_args', 'b3theme_wp_link_pages_args');

function b3theme_navbar() { ?>
	<nav class="navbar navbar-default <?php
	if (b3theme_option('navbar_location') !== 'default') {
		echo ' navbar-' . b3theme_option('navbar_location');
	}
	echo (b3theme_option('navbar_color') && '#F8F8F8' != b3theme_option('navbar_color')) ? ' navbar-b3theme' : ''; ?>" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button><?php
				if ($brand = b3theme_option('navbar_brand')) {
					echo '<a class="navbar-brand" href="'. home_url() .'">'. $brand .'</a>';
				} ?>
				</div>
				<div class="collapse navbar-collapse">
						<?php
	$params = array(
		'theme_location' => 'primary',
		'container'      => false,
		'fallback_cb' => 'b3theme_wp_page_menu',
		'menu_class' => 'nav navbar-nav',
		'echo' => false,
		'walker' => new Tb3theme_Walker_Nav_Menu,
	);
	$menu = wp_nav_menu($params);
	$menu = str_replace('class="sub-menu"', 'class="dropdown-menu"', $menu);
	echo $menu;
	?>
				</div>
			</div>
	</nav>
<?php
}

require B3THEME_PATH . '/inc/custom-header.php';
require B3THEME_PATH . '/inc/template-tags.php';
require B3THEME_PATH . '/inc/extras.php';
require B3THEME_PATH . '/inc/customizer.php';
require B3THEME_PATH . '/inc/jetpack.php';
