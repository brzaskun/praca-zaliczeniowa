<?php
/**
 * The Sidebar containing the main widget areas.
 *
 * @package B3
 */

/*
* This widget area may be used for something like searchform,
* shopping cart and also for banner so it's 1/2 page width
* I 
*/
?>
	<div id="sidebar-top" class="widget-area col-md-6" role="complementary">
		<?php do_action('before_sidebar', 'sidebar-top'); ?>
		<?php if ( ! dynamic_sidebar('sidebar-top') ) : ?>
			<div class="row"><div class="col-xs-5 col-sm-6">&nbsp;</div>
				<aside id="search-default" class="col-xs-7 col-sm-6 widget widget_search">
				<?php get_search_form(); ?>
			</aside>
			</div>
		<?php endif; ?>
	</div>
