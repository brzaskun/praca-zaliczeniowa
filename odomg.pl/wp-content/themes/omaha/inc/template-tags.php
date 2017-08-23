<?php

/**
 * Custom template tags for this theme
 * 
 * @package omaha
 */

/**
 * Display categories list with bootstrap icon
 * 
 * @param string $categories_list list of categories.
 * @return string
 */
function omahaCategoriesList($categories_list = '') {
	return sprintf('<span class="categories-icon glyphicon glyphicon-th-list" title="' . __('Posted in', 'omaha') . '"></span> %1$s', $categories_list);
}

// omahaCategoriesList

/**
 * Displaying a comment
 * 
 * @param object $comment
 * @param array $args
 * @param integer $depth
 * @return string the content already echo.
 */
function omahaComment($comment, $args, $depth) {

	$GLOBALS['comment'] = $comment;

	echo '<li class="comment comment-author-' . get_comment_author() . ' bypostauthor depth-' . $depth . '" id="comment-' . get_comment_ID() . '">';
	echo '<div id="div-comment-' . get_comment_ID() . '" class="comment-body">';
	echo '<div class="comment-meta">';
	echo '<div class="comment-author vcard">';
	echo '<cite class="fn">' . get_comment_author() . '</cite><span class="says">,</span>
                            </div>';

	echo '<div class="commentmetadata"><a href="' . get_permalink() . '#comment-' . get_comment_ID() . '">
			' . get_comment_time() . ' ' . get_comment_date() . '</a>' . edit_comment_link() . '</div>';
	comment_reply_link(array_merge($args, array('depth' => $depth, 'max_depth' => $args['max_depth'])));
	echo '</div>';

	comment_text();

	echo '</div>
    </li>';
}

// omahaComment

/**
 * Custom comment popup link
 * 
 * @return string
 */
function omahaCommentsPopupLink() {
	$comment_icon = '<span class="comment-icon glyphicon glyphicon-comment"><small class="comment-total">%d</small></span>';
	$comments_icon = '<span class="comment-icon glyphicon glyphicon-comment"><small class="comment-total">%s</small></span>';
	return comments_popup_link(sprintf($comment_icon, ''), sprintf($comment_icon, '1'), sprintf($comments_icon, '%'), 'btn btn-default btn-xs');
}

// omahaCommentsPopupLink

/**
 * Display edit post link
 * 
 * @return string
 */
function omahaEditPostLink() {
	return edit_post_link('<b class="edit-post-icon glyphicon glyphicon-pencil" title="' . __('Edit', 'omaha') . '"></b>', '<span class="edit-post-link btn btn-default btn-xs" title="' . __('Edit', 'omaha') . '">', '</span>');
}

// omahaEditPostLink

/**
 * Display full page search form
 * 
 * @return string the search form element
 */
function omahaFullPageSearchForm() {
	$output = '<form class="form-horizontal" method="get" action="' . esc_url(home_url('/')) . '" role="form">';
	$output .= '<div class="form-group">';
	$output .= '<div class="col-xs-10">';
	$output .= '<input type="text" name="s" value="' . esc_attr(get_search_query()) . '" placeholder="' . esc_attr_x('Search &hellip;', 'placeholder', 'omaha') . '" title="' . esc_attr_x('Search &hellip;', 'label', 'omaha') . '" class="form-control" />';
	$output .= '</div>';
	$output .= '<div class="col-xs-2">';
	$output .= '<button type="submit" class="btn btn-default">' . __('Search', 'omaha') . '</button>';
	$output .= '</div>';
	$output .= '</div>';
	$output .= '</form>';

	return $output;
}

// omahaFullPageSearchForm

/**
 * get the link in content
 * 
 * @return string
 */
function omahaGetLinkInContent() {
	$content = get_the_content();
	$has_url = get_url_in_content($content);

	if ($has_url) {
		return $has_url;
	} else {
		return apply_filters('the_permalink', get_permalink());
	}
}

// omahaGetLinkInContent

/**
 * Custom more link (continue reading) text
 * @return string
 */
function omahaMoreLinkText() {
	return __('Continue reading <span class="meta-nav">&rarr;</span>', 'omaha');
}

// omahaMoreLinkText

/**
 * display pagination (1 2 3 ...) instead of previous, next of wordpress style.
 * 
 * @param string $pagination_align_class
 * @return string the content already echo
 */
function omahaPagination($pagination_align_class = 'pagination-center pagination-row') {
	global $wp_query;
	$big = 999999999;
	$pagination_array = paginate_links(array(
		'base' => str_replace($big, '%#%', get_pagenum_link($big)),
		'format' => '/page/%#%',
		'current' => max(1, get_query_var('paged')),
		'total' => $wp_query->max_num_pages,
		'prev_text' => '&laquo;',
		'next_text' => '&raquo;',
		'type' => 'array'
	));

	unset($big);

	if (is_array($pagination_array) && !empty($pagination_array)) {
		echo '<nav class="' . $pagination_align_class . '">';
		echo '<ul class="pagination">';
		foreach ($pagination_array as $page) {
			echo '<li';
			if (strpos($page, '<a') === false && strpos($page, '&hellip;') === false) {
				echo ' class="active"';
			}
			echo '>';
			if (strpos($page, '<a') === false && strpos($page, '&hellip;') === false) {
				echo '<span>' . $page . '</span>';
			} else {
				echo $page;
			}
			echo '</li>';
		}
		echo '</ul>';
		echo '</nav>';
	}

	unset($page, $pagination_array);
}

// omahaPagination

/**
 * display post date/time and author
 * 
 * @return string
 */
function omahaPostOn() {
	$time_string = '<time class="entry-date published" datetime="%1$s">%2$s</time>';
	if (get_the_time('U') !== get_the_modified_time('U')) {
		$time_string .= '<time class="updated" datetime="%3$s">%4$s</time>';
	}

	$time_string = sprintf($time_string, esc_attr(get_the_date('c')), esc_html(get_the_date()), esc_attr(get_the_modified_date('c')), esc_html(get_the_modified_date())
	);

	printf(__('<span class="posted-on">Posted on %1$s</span><span class="byline"> by %2$s</span>', 'omaha'), sprintf('<a href="%1$s" title="%2$s" rel="bookmark">%3$s</a>', esc_url(get_permalink()), esc_attr(get_the_time()), get_the_date()
			), sprintf('<span class="author vcard"><a class="url fn n" href="%1$s" title="%2$s">%3$s</a></span>', esc_url(get_author_posts_url(get_the_author_meta('ID'))), esc_attr(sprintf(__('View all posts by %s', 'omaha'), get_the_author())), esc_html(get_the_author())
			)
	);
}

// omahaPostOn

/**
 * display tags list
 * 
 * @param string $tags_list
 * @return string
 */
function omahaTagsList($tags_list = '') {
	return sprintf('<span class="tags-icon glyphicon glyphicon-tags" title="' . __('Tagged', 'omaha') . '"></span>&nbsp; %1$s', $tags_list);
}

// omahaTagsList

/**
 * Display attach image with link.
 * 
 * @return string image element with link.
 */
function omahaTheAttachedImage() {
	$post = get_post();
	$attachment_size = apply_filters('bootstrap_basic_attachment_size', array(1140, 1140));
	$next_attachment_url = wp_get_attachment_url();

	/**
	 * Grab the IDs of all the image attachments in a gallery so we can get the
	 * URL of the next adjacent image in a gallery, or the first image (if
	 * we're looking at the last image in a gallery), or, in a gallery of one,
	 * just the link to that image file.
	 */
	$attachment_ids = get_posts(array(
		'post_parent' => $post->post_parent,
		'fields' => 'ids',
		'numberposts' => -1,
		'post_status' => 'inherit',
		'post_type' => 'attachment',
		'post_mime_type' => 'image',
		'order' => 'ASC',
		'orderby' => 'menu_order ID'
	));

	// If there is more than 1 attachment in a gallery...
	if (count($attachment_ids) > 1) {
		foreach ($attachment_ids as $attachment_id) {
			if ($attachment_id == $post->ID) {
				$next_id = current($attachment_ids);
				break;
			}
		}


		if ($next_id) {
			// get the URL of the next image attachment...
			$next_attachment_url = get_attachment_link($next_id);
		} else {
			// or get the URL of the first image attachment.
			$next_attachment_url = get_attachment_link(array_shift($attachment_ids));
		}
	}

	printf('<a href="%1$s" title="%2$s" rel="attachment">%3$s</a>', esc_url($next_attachment_url), the_title_attribute(array('echo' => false)), wp_get_attachment_image($post->ID, $attachment_size, false, array('class' => 'img-responsive aligncenter'))
	);
}

// omahaTheAttachedImage