<?php
/**
 * The template part for displaying a message that posts cannot be found.
 *
 * Learn more: http://codex.wordpress.org/Template_Hierarchy
 *
 * @package B3
 */
?>

<div <?php b3theme_content_wrap_class(); ?>>
	<section class="no-results not-found <?php echo b3theme_option('panel_post') ? 'spacer-all' : ''; ?>">
	<header class="page-header">
		<h1 class="page-title"><?php _e('Nothing Found', 'b3theme'); ?></h1>
	</header><!-- .page-header -->

	<div class="page-content">
		<?php if ( is_home() && current_user_can('publish_posts') ) : ?>

			<p><?php printf( __('Ready to publish your first post? <a href="%1$s">Get started here</a>.', 'b3theme'), esc_url( admin_url('post-new.php') ) ); ?></p>

		<?php elseif ( is_search() ) : ?>

			<p><?php _e('Sorry, but nothing matched your search terms. Please try again with some different keywords.', 'b3theme'); ?></p>
			<?php get_search_form(); ?>

		<?php else : ?>

			<p><?php _e('It seems we can&rsquo;t find what you&rsquo;re looking for. Perhaps searching can help.', 'b3theme'); ?></p>
			<?php get_search_form(); ?>

		<?php endif; ?>
	</div><!-- .page-content -->
</section><!-- .no-results -->
</div>
