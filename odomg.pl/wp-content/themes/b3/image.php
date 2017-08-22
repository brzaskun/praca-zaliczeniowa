<?php
/**
 * The template for displaying image attachments.
 *
 * @package B3
 */

get_header(); ?>

	<div id="primary" class="content-area image-attachment col-sm-9 col-xs-12">
		<main id="main" class="site-main" role="main">

		<?php while ( have_posts() ) : the_post(); ?>
		 <div <?php b3theme_content_wrap_class(); ?>>
			<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
				<header class="entry-header">
					<?php the_title('<h1 class="entry-title">', '</h1>'); ?>

					<div class="entry-meta">
						<?php
							$metadata = wp_get_attachment_metadata();
							$time = ('Y'== b3theme_option('post_date')) ?
									sprintf('<span class="entry-date"><span class="glyphicon glyphicon-calendar"></span> <time class="entry-date" datetime="%1$s">%2$s</time></span>',
										esc_attr(get_the_date('c')), esc_html(get_the_date())) : '';

							printf('%1$s<span class="glyphicon glyphicon-pushpin"></span> <a href="%5$s" rel="gallery">%6$s</a>, '
								. __('size <a href="%2$s"> %3$s &times; %4$s</a>', 'b3theme'), $time,
								esc_url( wp_get_attachment_url() ),
								$metadata['width'],
								$metadata['height'],
								esc_url( get_permalink( $post->post_parent ) ),
								get_the_title( $post->post_parent )
							);
							?>
					</div><!-- .entry-meta -->
				 <nav role="navigation" class="image-navigation">
					 <ul class="pager">
						<li class="previous"><?php previous_image_link( false, __('<span class="meta-nav">&larr;</span> Previous', 'b3theme') ); ?></li>
						<li class="next"><?php next_image_link( false, __('Next <span class="meta-nav">&rarr;</span>', 'b3theme') ); ?></li>
					</ul>
				 </nav><!-- #image-navigation --></li>
				</header><!-- .entry-header -->

				<div class="entry-content">
					<div class="entry-attachment">
						<div class="attachment">
							<?php b3theme_the_attached_image(); ?>
						</div><!-- .attachment -->

						<?php if ( has_excerpt() ) : ?>
						<div class="entry-caption">
							<?php the_excerpt(); ?>
						</div><!-- .entry-caption -->
						<?php endif; ?>
					</div><!-- .entry-attachment -->

					<?php the_content(); ?>
					<?php wp_link_pages(); ?>
				</div><!-- .entry-content -->

				<?php edit_post_link( '<span class="glyphicon glyphicon-pencil"></span> ' . __('Edit', 'b3theme'), '<footer class="entry-meta"><span class="edit-link">', '</span></footer>'); ?>
			</article><!-- #post-## -->
		 </div>

			<?php
				// If comments are open or we have at least one comment, load up the comment template
				if ( comments_open() || '0' != get_comments_number() )
					comments_template();
			?>

		<?php endwhile; // end of the loop. ?>

		</main><!-- #main -->
	</div><!-- #primary -->

<?php
get_sidebar();
get_footer();
