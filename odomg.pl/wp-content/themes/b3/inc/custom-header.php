<?php
/**
 *
 * @package B3
 *
 * Sample implementation of the Custom Header feature
 * http://codex.wordpress.org/Custom_Headers
 *
 * @uses b3theme_admin_header_style() to style wp-admin form.
 * @uses b3theme_admin_header_image() to add custom markup to wp-admin form.
 *
 */
function b3theme_custom_header_setup() {
	add_theme_support('custom-header', array(
		'default-image' => B3THEME_URI . '/images/b3theme-logo.png',
		'width'                  => 120,
		'height'                 => 108,
		'flex-height'            => false,
		'flex-width'             => false,
		'header-text'            => false,
		'uploads'                => true,
		'admin-head-callback'    => 'b3theme_admin_header_style',
		'admin-preview-callback' => 'b3theme_admin_header_image',
	));
}

add_action('after_setup_theme', 'b3theme_custom_header_setup');

/**
 * Styles the header image displayed on the Appearance > Header admin panel.
 *
 * @see b3theme_custom_header_setup().
 */
function b3theme_admin_header_style() {
?>
<style type="text/css">
	.appearance_page_custom-header #headimg {
		border: none;
	}
	#headimg {
		position: relative;
		background-repeat: no-repeat;
		height : 160px;
	}
	#name-desctiption {
		position: absolute;
		top: 0;
		left: 200px;
	}
	</style>
<?php
}

if ( ! function_exists('b3theme_admin_header_image') ) :
/**
 * Custom header image markup displayed on the Appearance > Header admin panel.
 *
 * @see b3theme_custom_header_setup().
 */
function b3theme_admin_header_image() {
?>
	<div id="headimg">
		<?php if ( get_header_image() ) : ?>
		<img src="<?php header_image(); ?>" alt="">
		<?php endif; ?>
		<div id="name-desctiption">
			<h1 class="displaying-header-text"><a id="name" onclick="return false;" href="<?php echo esc_url( home_url('/') ); ?>"><?php bloginfo('name'); ?></a></h1>
			<div class="displaying-header-text" id="desc"><?php bloginfo('description'); ?></div>
		</div>
	</div>
<?php
}
endif; // b3theme_admin_header_image
