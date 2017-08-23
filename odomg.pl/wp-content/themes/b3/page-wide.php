<?php
/**

Template Name: Wide (No sidebar)
 *
 * @package B3
 */

get_header(); ?>

	<div id="primary" class="content-area col-xs-12">
		<main id="main" class="site-main" role="main">

			<?php while ( have_posts() ) : the_post(); ?>

				<?php get_template_part('content', 'page'); ?>

				<?php
					// If comments are open or we have at least one comment, load up the comment template
					if ('Y' != b3theme_option('disable_comment_page') && comments_open() || '0' != get_comments_number() )
						comments_template();
				?>

			<?php endwhile; // end of the loop. ?>

		</main><!-- #main -->
	</div><!-- #primary -->

<?php get_footer();
