<?php
/**
 * The template part for displaying message that posts cannot be found.
 * 
 * @package omaha
 */
?>
<section class="no-results not-found">
	<header class="page-header">
		<h1 class="page-title"><?php _e('Nothing Found', 'omaha'); ?></h1>
	</header><!-- .page-header -->

	<div class="page-content row-with-vspace">
		<?php if (is_home() && current_user_can('publish_posts')) { ?> 
			<p><?php printf(__('Ready to publish your first post? <a href="%1$s">Get started here</a>.', 'omaha'), esc_url(admin_url('post-new.php'))); ?></p>
		<?php } elseif (is_search()) { ?> 
			<p><?php _e('Sorry, but nothing matched your search terms. Please try again with some different keywords.', 'omaha'); ?></p>
			<?php echo omahaFullPageSearchForm(); ?> 
		<?php } else { ?> 
			<p><?php _e('It seems we can&rsquo;t find what you&rsquo;re looking for. Perhaps searching can help.', 'omaha'); ?></p>
			<?php echo omahaFullPageSearchForm(); ?> 
		<?php } //endif; ?> 
	</div><!-- .page-content -->
</section><!-- .no-results -->