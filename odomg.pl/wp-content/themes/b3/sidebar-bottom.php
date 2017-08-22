<?php
/**
 * The Sidebar containing the main widget areas.
 *
 * @package B3
 */
?>
	<div id="sidebar-bottom" class="widget-area row" role="complementary">
<?php
	do_action('before_sidebar', 'sidebar-bottom');
		remove_filter('widget_title', 'b3theme_panel_widget_title');
		if ( ! dynamic_sidebar('sidebar-bottom') ) {
			$args = array(
				'before_widget' => '<aside class="widget col-md-3 col-sm-4 col-xs-12">',
				'after_widget' => '</aside>',
				'before_title' => ' <h3 class="widget-title">',
				'after_title' => '</h3>',
			);
			the_widget('WP_Widget_Recent_Posts', array('number' => 5), $args);
			the_widget('WP_Widget_Recent_Comments', array(), $args);
			the_widget('WP_Widget_Tag_Cloud', array(), $args);
			the_widget('WP_Widget_Meta', array(), $args);
		} ?>
	</div>

