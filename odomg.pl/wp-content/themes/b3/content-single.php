<?php
/**
 * @package B3
 */
?>

<div <?php b3theme_content_wrap_class(); ?>>
	<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
	<header class="entry-header">
		<h1 class="entry-title"><?php the_title(); ?></h1>

		<div class="entry-meta">
			<?php b3theme_posted_on(); ?>
		</div><!-- .entry-meta -->
	</header><!-- .entry-header -->

	<div class="entry-content clearfix">
		<?php the_content(); ?>
		<?php wp_link_pages(); ?>
	</div><!-- .entry-content -->

	<footer class="entry-meta">
		<?php
			/* translators: used between list items, there is a space after the comma */
			$category_list = get_the_category_list( __(', ', 'b3theme') );

			/* translators: used between list items, there is a space after the comma */
			$tag_list = get_the_tag_list('', ', ');

			$permalink = '<span class="permalink"><a href="%3$s" rel="bookmark"><span class="glyphicon glyphicon-link"></span> permalink</a></span>';

			if (!b3theme_categorized_blog()) {
				// This blog only has 1 category so we just need to worry about tags in the meta text
				if ('' != $tag_list ) {
					$meta_text = '<span class="cat-links"><span class="glyphicon glyphicon-tag"></span> %2$s</span> <span class="permalink">' .  $permalink .'</span>';
				} else {
					$meta_text =  $permalink;
				}

			} else {
				// But this blog has loads of categories so we should probably display them here
				if ('' != $tag_list ) {
					$meta_text = '<span class="cat-links"><span class="glyphicon glyphicon-folder-open"></span> %1$s</span> <span class="tag-links"><span class="glyphicon glyphicon-tag"></span> %2$s</span> '. $permalink;
				} else {
					$meta_text = '<span class="cat-links"><span class="glyphicon glyphicon-folder-open"></span> %1$s</span> '. $permalink;
				}

			} // end check for categories on this blog

			printf(
				$meta_text,
				$category_list,
				$tag_list,
				get_permalink()
			);
		?>

		<?php edit_post_link('<span class="glyphicon glyphicon-pencil"></span> '
			. __('Edit', 'b3theme'), '<span class="edit-link">', '</span>'); ?>
	</footer><!-- .entry-meta -->
</article><!-- #post-## -->
</div>
