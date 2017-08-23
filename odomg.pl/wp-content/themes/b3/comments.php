<?php
/**
 * The template for displaying Comments.
 *
 * The area of the page that contains both current comments
 * and the comment form. The actual display of comments is
 * handled by a callback to b3theme_comment() which is
 * located in the inc/template-tags.php file.
 *
 * @package B3
 */

/*
 * If the current post is protected by a password and
 * the visitor has not yet entered the password we will
 * return early without loading the comments.
 */
if ( post_password_required() )
	return;
?>

<div id="comments" class="comments-area">

	<?php // You can start editing here -- including this comment! ?>

	<?php if ( have_comments() ) : ?>
		<h2 class="comments-title">
			<?php
				printf(_nx('One comment on &laquo;%2$s&raquo;', '%1$s comments on &laquo;%2$s&raquo;', get_comments_number(), 'comments title', 'b3theme'),
					number_format_i18n( get_comments_number() ), '<span>' . get_the_title() . '</span>');
			?>
		</h2>

		<?php if ( get_comment_pages_count() > 1 && get_option('page_comments') ) : // are there comments to navigate through ?>
		<nav id="comment-nav-above" class="comment-navigation" role="navigation">
			<h1 class="screen-reader-text"><?php _e('Comment navigation', 'b3theme'); ?></h1>
			<div class="nav-previous"><?php previous_comments_link( __('&larr; Older Comments', 'b3theme') ); ?></div>
			<div class="nav-next"><?php next_comments_link( __('Newer Comments &rarr;', 'b3theme') ); ?></div>
		</nav><!-- #comment-nav-above -->
		<?php endif; // check for comment navigation ?>

		<ol class="comment-list">
			<?php
				/* Loop through and list the comments. Tell wp_list_comments()
				 * to use b3theme_comment() to format the comments.
				 * If you want to override this in a child theme, then you can
				 * define b3theme_comment() and that will be used instead.
				 * See b3theme_comment() in inc/template-tags.php for more.
				 */
				wp_list_comments( array('callback' => 'b3theme_comment') );
			?>
		</ol><!-- .comment-list -->

		<?php if ( get_comment_pages_count() > 1 && get_option('page_comments') ) : // are there comments to navigate through ?>
		<nav id="comment-nav-below" class="comment-navigation" role="navigation">
			<h1 class="screen-reader-text"><?php _e('Comment navigation', 'b3theme'); ?></h1>
			<div class="nav-previous"><?php previous_comments_link( __('&larr; Older Comments', 'b3theme') ); ?></div>
			<div class="nav-next"><?php next_comments_link( __('Newer Comments &rarr;', 'b3theme') ); ?></div>
		</nav><!-- #comment-nav-below -->
		<?php endif; // check for comment navigation ?>

	<?php endif; // have_comments() ?>

	<?php
		// If comments are closed and there are comments, let's leave a little note, shall we?
		if (!comments_open() && '0' != get_comments_number() && post_type_supports( get_post_type(), 'comments') ) :
	?>
		<p class="no-comments"><?php _e('Comments are closed.', 'b3theme'); ?></p>
	<?php else: ?>
	<div class="row spacer-bottom"><div class="col-sm-10 col xs-12">
<?php
	$commenter = wp_get_current_commenter();
	$req = get_option( 'require_name_email' );
	$aria_req = ( $req ? " aria-required='true'" : '' );
	$required_text = ' <span class="required">*</span>';
	$fields =  array(
		'author' => '<div class="comment-form-author row spacer-top"><div class="col-xs-6">
				' . '<label for="author">'
			. __('Name', 'b3theme') . ( $req ? $required_text : '') . '</label> '
			. '<input class="form-control" id="author" name="author" type="text" value="'
			. esc_attr( $commenter['comment_author'] ) . '" size="30"' . $aria_req . ' />
			</div></div>',

		'email'  => '<div class="comment-form-email row spacer-top">
				<div class="col-xs-6">
					<label for="email">' . __('Email', 'b3theme')
			. ( $req ? $required_text : '') . '</label>'
			. '<input class="form-control" id="email" name="email" type="text" value="' . esc_attr( $commenter['comment_author_email'] )
			. '" size="30"' . $aria_req . ' />
			</div></div>',

		'url'  => '<div class="comment-form-url row spacer-top"><div class="col-xs-6">
				<label for="url">' . __('Website', 'b3theme') . '</label>'
			. '<input class="form-control" id="url" name="url" type="text" value="' . esc_attr( $commenter['comment_author_url'] )
			. '" size="30" />
			</div></div>',
	);

$args = array('fields' => $fields,
	'comment_field' => '<div class="comment-form-comment form-group spacer-top"><label for="comment">'
		. _x('Comment', 'noun', 'b3theme') . '</label><textarea class="form-control" rows="8" id="comment" name="comment" aria-required="true" ></textarea></div>',

		'must_log_in' => '<div class="must-log-in">'
			. sprintf( __('You must be <a href="%s">logged in</a> to post a comment.'),
				wp_login_url( apply_filters('the_permalink', get_permalink( $post->ID ) ) ) ) . '</div>',

		'logged_in_as'  => '<div class="logged-in-as">'
			 . sprintf( __('Logged in as <a href="%1$s">%2$s</a>. <a href="%3$s" title="Log out of this account">Log out?</a>'),
			 get_edit_user_link(), $user_identity, wp_logout_url( apply_filters('the_permalink', get_permalink( $post->ID ) ) ) )
			 . '</div>',
		'comment_notes_before' => '<div class="comment-notes">' . __('Your email address will not be published.', 'b3theme')
			. ( $req ? $required_text : '') . '</div>',
		'comment_notes_after'  => '<div class="form-allowed-tags">' . sprintf( __('You may use these <abbr title="HyperText Markup Language">HTML</abbr> tags and attributes: %s', 'b3theme'), ' <span class="text-info">' . allowed_tags() . '</span>') . '</div>', 

);

	comment_form($args); ?>
	</div></div>
<?php  endif; ?>

</div><!-- #comments -->
