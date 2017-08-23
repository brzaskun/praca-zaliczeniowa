<?php
/**
 * Custom template tags for this theme.
 *
 * Eventually, some of the functionality here could be replaced by core features
 *
 * @package B3
 */

if ( !function_exists('b3theme_content_nav') ) :
/**
 * Display navigation to next/previous pages when applicable
 */
function b3theme_content_nav( $nav_id ) {
	global $wp_query, $post;

	if ( is_single() ) {
		if ('disable' == b3theme_option('prev_next_link'))
			return;

		$previous = ( is_attachment() ) ? get_post( $post->post_parent ) : get_adjacent_post( false, '', true );
		$next = get_adjacent_post( false, '', false );

		if ( ! $next && ! $previous )
			return;
	}

	if ( $wp_query->max_num_pages < 2 && ( is_home() || is_archive() || is_search() ) )
		return;

	$nav_class = ( is_single() ) ? 'post-navigation' : 'paging-navigation';

	if ( is_single() ) { // navigation links for single posts ?>
		<nav role="navigation" id="<?php echo esc_attr( $nav_id ); ?>" class="<?php echo $nav_class; ?>"><ul class="pager">
		<?php
		if ($previous) {
			$previous_text = '<span class="meta-nav">' . _x('&larr;', 'Previous post link', 'b3theme') . '</span> ';
			$previous_text .= 'prev_next' == b3theme_option('prev_next_link') ?
				__('Previous post', 'b3theme') : esc_html($previous->post_title);
			previous_post_link('<li class="previous">%link</li>', $previous_text);
		} ?>

		<?php
		if ($next) {
			$next_text = 'prev_next' == b3theme_option('prev_next_link') ?
				__('Next post', 'b3theme') : esc_html($next->post_title);
			$next_text .= ' <span class="meta-nav">' . _x('&rarr;', 'Next post link', 'b3theme') . '</span>';
			next_post_link('<li class="next">%link</li>', $next_text);
		}
		?>
		</ul></nav> <?php

	} elseif ( $wp_query->max_num_pages > 1 && ( is_home() || is_archive() || is_search() ) ) {
		// navigation links for home, archive, and search pages ?>
			<nav role="navigation" id="<?php echo esc_attr( $nav_id ); ?>" class="<?php echo $nav_class; ?>">
				<?php
					if ( 'number' == b3theme_option('paginate_links') ) {
						b3theme_blog_pager();
					}	else { ?>
						<ul class="pager">
						<?php
						if ( get_next_posts_link() ) { ?>
							<li class="previous"><?php next_posts_link( __('<span class="meta-nav">&larr;</span> Older posts', 'b3theme') ); ?></li>	<?php
						} 
						if ( get_previous_posts_link() ) { ?>
								<li class="next"><?php previous_posts_link( __('Newer posts <span class="meta-nav">&rarr;</span>', 'b3theme') ); ?></li> <?php
						} ?>
						</ul> <?php					
					} ?>
				</nav> <?php	
				}
}
endif; // b3theme_content_nav

function b3theme_blog_pager() {
	global $wp_query;
	$pp= get_option('posts_per_page');
	$total= ceil( $wp_query->found_posts / $pp );
	if ($total<=1) return '';

	preg_match('/([^ ]+)([^a-z])page(\/|d=)([0-9]+)([^ ]{0,})$/', get_next_posts_page_link(), $res);
	$current= ($res[4]==1 || $res[4]=='' || $res[4]==0)?1:($res[4]-1);

	$links = paginate_links( array(
		'show_all' => false,
		'total'=> $total ,
		'current' => $current,
		'base'=> $res[1].'%_%',
		'format' => $res[2] . 'page' . $res[3] . '%#%' . $res[5],
		'prev_next' => true,
		'end_size' => 2,
		'mid_size' => 2,
		'prev_text' => '<span class="glyphicon glyphicon-chevron-left"></span>',
		'next_text' => '<span class="glyphicon glyphicon-chevron-right"></span>',
		'type' => 'array',
	) );
	
	foreach ($links as $i => $link) {
		$active = strpos($link, 'span') == 1 && !strpos($link, '"page-numbers dots"') ? ' class="active"' : '';
		$links[$i] = '<li' . $active . '>' . $link . '</li>';
	}
	echo '<div class="text-center"><ul class="pagination">' . implode('', $links) . '</ul></div>';
}

if ( ! function_exists('b3theme_comment') ) :
/**
 * Template for comments and pingbacks.
 *
 * Used as a callback by wp_list_comments() for displaying the comments.
 */
function b3theme_comment($comment, $args, $depth) {
	$GLOBALS['comment'] = $comment;

	if ('pingback' == $comment->comment_type || 'trackback' == $comment->comment_type ) : ?>

	<li id="comment-<?php comment_ID(); ?>" <?php comment_class(); ?>>
		<div class="comment-body">
			<?php _e('Pingback:', 'b3theme'); ?> <?php comment_author_link(); ?> <?php edit_comment_link( __('Edit', 'b3theme'), '<span class="edit-link">', '</span>'); ?>
		</div>

	<?php else : ?>

	<li id="comment-<?php comment_ID(); ?>" <?php comment_class( empty( $args['has_children'] ) ? '' : 'parent'); ?>>
		<article id="div-comment-<?php comment_ID(); ?>" class="comment-body">
			<footer class="comment-meta">
				<div class="comment-author vcard">
					<?php if ( 0 != $args['avatar_size'] ) echo get_avatar( $comment, $args['avatar_size'] ); ?>
					<?php printf( __('%s <span class="says">says:</span>', 'b3theme'), sprintf('<cite class="fn">%s</cite>', get_comment_author_link() ) ); ?>
				</div><!-- .comment-author -->

				<div class="comment-metadata">
					<a href="<?php echo esc_url( get_comment_link( $comment->comment_ID ) ); ?>">
						<time datetime="<?php comment_time('c'); ?>">
							<?php printf( _x('%1$s at %2$s', '1: date, 2: time', 'b3theme'), get_comment_date(), get_comment_time() ); ?>
						</time>
					</a>
					<?php edit_comment_link( __('Edit', 'b3theme'), '<span class="edit-link">', '</span>'); ?>
				</div><!-- .comment-metadata -->

				<?php if ('0' == $comment->comment_approved ) : ?>
				<div class="comment-awaiting-moderation alert alert-warning"><?php _e('Your comment is awaiting moderation.', 'b3theme'); ?></div>
				<?php endif; ?>
			</footer><!-- .comment-meta -->

			<div class="comment-content">
				<?php comment_text(); ?>
			</div><!-- .comment-content -->

			<?php
				comment_reply_link( array_merge( $args, array(
					'add_below' => 'div-comment',
					'depth'     => $depth,
					'max_depth' => $args['max_depth'],
					'before'    => '<div class="reply">',
					'after'     => '</div>',
				) ) );
			?>
		</article><!-- .comment-body -->

	<?php
	endif;
}
endif; // ends check for b3theme_comment()

if ( ! function_exists('b3theme_the_attached_image') ) :
/**
 * Prints the attached image with a link to the next attached image.
 */
function b3theme_the_attached_image() {
	$post                = get_post();
	$attachment_size     = apply_filters('b3theme_attachment_size', array( 1200, 1200 ) );
	$next_attachment_url = wp_get_attachment_url();

	/**
	 * Grab the IDs of all the image attachments in a gallery so we can get the
	 * URL of the next adjacent image in a gallery, or the first image (if
	 * we're looking at the last image in a gallery), or, in a gallery of one,
	 * just the link to that image file.
	 */
	$attachment_ids = get_posts( array(
		'post_parent'    => $post->post_parent,
		'fields'         => 'ids',
		'numberposts'    => -1,
		'post_status'    => 'inherit',
		'post_type'      => 'attachment',
		'post_mime_type' => 'image',
		'order'          => 'ASC',
		'orderby'        => 'menu_order ID'
	) );

	// If there is more than 1 attachment in a gallery...
	if ( count( $attachment_ids ) > 1 ) {
		foreach ( $attachment_ids as $attachment_id ) {
			if ( $attachment_id == $post->ID ) {
				$next_id = current( $attachment_ids );
				break;
			}
		}

		// get the URL of the next image attachment...
		if ( $next_id )
			$next_attachment_url = get_attachment_link( $next_id );

		// or get the URL of the first image attachment.
		else
			$next_attachment_url = get_attachment_link( array_shift( $attachment_ids ) );
	}

	printf('<a href="%1$s" rel="attachment">%2$s</a>',
		esc_url( $next_attachment_url ),
		wp_get_attachment_image( $post->ID, $attachment_size )
	);
}
endif;

if ( ! function_exists('b3theme_posted_on') ) :
/**
 * Prints HTML with meta information for the current post-date/time and author.
 */
function b3theme_posted_on() {
	if ('Y'==b3theme_option('post_date')) {
		$time_string = '<time class="entry-date published" datetime="%1$s">%2$s</time>';
		$time_updated = '';
		if (get_the_time('Ymd') !== get_the_modified_time('Ymd')) {
			$time_updated = ' &nbsp;<span class="glyphicon glyphicon-refresh"></span> <time class="updated" datetime="%1$s">%2$s</time>';
			$time_updated = sprintf( $time_updated,
				esc_attr( get_the_modified_date('c') ),
				esc_html( get_the_modified_date() )
			);
		}

		$time_string = sprintf($time_string, esc_attr(get_the_date('c') ),esc_html(get_the_date())  );

		printf('<span class="posted-on"><span class="glyphicon glyphicon-calendar"></span> %1$s</span>',
			sprintf('<a href="%1$s" rel="bookmark">%2$s</a>',
				esc_url( get_day_link(get_the_date('Y'), get_the_date('m'), get_the_date('d')) ), $time_string ));

		echo $time_updated;
	}
	if ('Y'==b3theme_option('post_author')) {
		printf(' &nbsp;<span class="byline glyphicon glyphicon-user"></span> <span class=">author vcard"><a class="url fn n" href="%1$s">%2$s</a></span>',
			esc_url( get_author_posts_url( get_the_author_meta('ID') ) ),
				esc_html( get_the_author() ) );
	}
}
endif;

/**
 * Returns true if a blog has more than 1 category
 */
function b3theme_categorized_blog() {
	if ( false === ( $all_the_cool_cats = get_transient('all_the_cool_cats') ) ) {
		// Create an array of all the categories that are attached to posts
		$all_the_cool_cats = get_categories( array(
			'hide_empty' => 1,
		) );

		// Count the number of categories that are attached to the posts
		$all_the_cool_cats = count( $all_the_cool_cats );

		set_transient('all_the_cool_cats', $all_the_cool_cats);
	}

	if ('1' != $all_the_cool_cats ) {
		// This blog has more than 1 category so b3theme_categorized_blog should return true
		return true;
	} else {
		// This blog has only 1 category so b3theme_categorized_blog should return false
		return false;
	}
}

/**
 * Flush out the transients used in b3theme_categorized_blog
 */
function b3theme_category_transient_flusher() {
	// Like, beat it. Dig?
	delete_transient('all_the_cool_cats');
}
add_action('edit_category', 'b3theme_category_transient_flusher');
add_action('save_post', 'b3theme_category_transient_flusher');
