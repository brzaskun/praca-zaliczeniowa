<?php
/**
 * Jetpack Compatibility File
 * See: http://jetpack.me/
 *
 * @package B3
 */

/**
 * Add theme support for Infinite Scroll.
 * See: http://jetpack.me/support/infinite-scroll/
 */
function b3theme_jetpack_setup() {
	add_theme_support('infinite-scroll', array(
		'container' => 'main',
		'footer'    => 'page',
	));
}
add_action('after_setup_theme', 'b3theme_jetpack_setup');
