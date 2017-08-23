<?php
/**
 * The template for displaying 404 pages (Not Found).
 *
 * @package B3
 */

get_header();
$reduce = 'Y' == b3theme_option('reduce_404_page') ? true : false;
?>

	<div id="primary" class="content-area <?php echo $reduce ? '' : 'col-md-9 col-sm-8'; ?> col-xs-12">
		<main id="main" class="site-main" role="main">
		<div <?php b3theme_content_wrap_class(); ?>>
			<section class="error-404 not-found  <?php echo b3theme_option('panel_post') ? 'spacer-all' : ''; ?>">
				<header class="page-header">
					<h1 class="page-title"><?php _e('Oops! That page can&rsquo;t be found.', 'b3theme'); ?></h1>
				</header><!-- .page-header -->

				<div class="page-content">
					<p><?php _e('It looks like nothing was found at this location. Maybe try one of available links or a search?', 'b3theme'); ?></p>

					<?php get_search_form();

				if ( !$reduce ) {
					the_widget('WP_Widget_Recent_Posts');
					if ( b3theme_categorized_blog() ) { // Only show the widget if site has multiple categories.
					?>
					<div class="widget widget_categories">
						<h2 class="widgettitle"><?php _e('Most Used Categories', 'b3theme'); ?></h2>
						<ul>
						<?php
							wp_list_categories( array(
								'orderby'    => 'count',
								'order'      => 'DESC',
								'show_count' => 1,
								'title_li'   => '',
								'number'     => 10,
							) );
						?>
						</ul>
					</div><!-- .widget -->
					<?php
					}

					/* translators: %1$s: smiley */
					$archive_content = '<p>' . sprintf( __('Try looking in the monthly archives. %1$s', 'b3theme'), convert_smilies(':)') ) . '</p>';
					the_widget('WP_Widget_Archives', 'dropdown=1', "after_title=</h2>$archive_content" );
					the_widget('WP_Widget_Tag_Cloud');
				} ?>

				</div><!-- .page-content -->
			</section><!-- .error-404 -->
		</div>
		</main><!-- #main -->
	</div><!-- #primary -->

<?php
if ( !$reduce ) {
	get_sidebar();
}
get_footer();