<?php
/**
 * The Sidebar containing the main widget areas.
 *
 * @package B3
 */
?>
	<div id="secondary" class="widget-area col-md-3 col-sm-4 col-xs-12" role="complementary">
		<?php
		do_action('before_sidebar', 'sidebar-1');
		add_filter('widget_title', 'b3theme_panel_widget_title', 10);
		if (!dynamic_sidebar('sidebar-1') ) {
			if ('Y' == b3theme_option('panel_widget')) {
				remove_filter('widget_title', 'b3theme_panel_widget_title', 10);
				$args= array(
					'before_widget' => '<aside class="widget panel panel-default">',
					'after_widget' => '</div></aside>',
					'before_title' => '<div class="panel-heading"><h3 class="widget-title panel-title">',
					'after_title' => '</h3></div><div class="panel-body">',
				);
			}
			else {
				$args= array(
					'before_widget' => '<aside class="widget">',
					'after_widget' => '</aside>',
					'before_title' => '<h3 class="widget-title">',
					'after_title' => '</h3>',
				);
			}
			the_widget('WP_Widget_Categories', array(), $args);
			the_widget('WP_Widget_Archives', array(), $args);
		} ?>
	</div>

