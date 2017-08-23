<?php
/**
 * The template used for displaying page content in page.php
 *
 * @package B3
 */
?>

<div <?php b3theme_content_wrap_class(); ?>>
	<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
	<header class="entry-header">
		<h1 class="entry-title"><?php the_title(); ?></h1>
	</header><!-- .entry-header -->

	<div class="entry-content clearfix">
		<?php the_content(); ?>
		<?php wp_link_pages(); ?>
	</div><!-- .entry-content -->
	<?php edit_post_link( __('Edit', 'b3theme'), '<footer class="entry-meta"><span class="edit-link">', '</span></footer>'); ?>
</article><!-- #post-## -->
</div>
