<?php
/**
 * The template for displaying Archive pages.
 *
 * Learn more: http://codex.wordpress.org/Template_Hierarchy
 *
 * @package B3
 */

get_header(); ?>

	<section id="primary" class="content-area col-md-9 col-sm-8 col-xs-12">
		<main id="main" class="site-main" role="main">

		<?php if ( have_posts() ) : ?>

			<header class="page-header">
				<h1 class="page-title">
					<?php
						if ( is_category() ) {
							single_cat_title();
						}
						elseif ( is_tag() ) {
							single_tag_title();
						}
						elseif ( is_author() ) {

							/* Queue the first post, that way we know
							 * what author we're dealing with (if that is the case).
							*/
							the_post();
							printf( __('Author: %s', 'b3theme'), '<span class="vcard">' . get_the_author() . '</span>');
/* Since we called the_post() above, we need to rewind the loop back. */
							rewind_posts();
						}
						elseif ( is_day() ) {
							printf( __('Day: %s', 'b3theme'), '<span>' . get_the_date() . '</span>');
						}
						elseif ( is_month() ) {
							printf( __('Month: %s', 'b3theme'), '<span>' . get_the_date('F Y') . '</span>');
						}
						elseif ( is_year() ) {
							printf( __('Year: %s', 'b3theme'), '<span>' . get_the_date('Y') . '</span>');
						}
						elseif ( is_tax('post_format', 'post-format-aside') ) {
							_e('Asides', 'b3theme');
						}
						elseif ( is_tax('post_format', 'post-format-image') ) {
							_e('Images', 'b3theme');
						}
						elseif ( is_tax('post_format', 'post-format-video') ) {
							_e('Videos', 'b3theme');
						}
						elseif ( is_tax('post_format', 'post-format-quote') ) {
							_e('Quotes', 'b3theme');
						}
						elseif ( is_tax('post_format', 'post-format-link') ) {
							_e('Links', 'b3theme');
						}
						elseif ( is_tax() ) {
							single_term_title();
						}
						elseif ( is_post_type_archive() ) {
							post_type_archive_title();
						}
						else {
							_e('Archives', 'b3theme');
						}
					?>
				</h1>
				<?php
					// Show an optional term description.
					$term_description = term_description();
					if ( !empty( $term_description )) :
						printf('<div class="taxonomy-description">%s</div>', $term_description );
					endif;
				?>
			</header><!-- .page-header -->

			<?php /* Start the Loop */ ?>
			<?php while ( have_posts() ) : the_post(); ?>

				<?php
					/* Include the Post-Format-specific template for the content.
					 * If you want to override this in a child theme, then include a file
					 * called content-___.php (where ___ is the Post Format name) and that will be used instead.
					 */
					get_template_part('content', get_post_format() );
						endwhile;
						b3theme_content_nav('nav-below');
				else :
						get_template_part('content', 'none');
				endif; ?>

		</main><!-- #main -->
	</section><!-- #primary -->

<?php
get_sidebar();
get_footer();
