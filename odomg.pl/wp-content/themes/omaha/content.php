<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
    <header class="entry-header">
        <div class="row">
            <div class="col-md-6">
                <h2 class="entry-title"><a href="<?php the_permalink(); ?>" rel="bookmark"><?php the_title(); ?></a></h2>
            </div>
            <div class="col-md-6">
				<?php if ('post' == get_post_type()) { ?> 
					<div class="entry-meta">
						<?php omahaPostOn(); ?>
						<div class="clearfix"></div>
						<div class="entry-meta-category-tag">
							Category:
							<?php
							/* translators: used between list items, there is a space after the comma */
							$categories_list = get_the_category_list(__(', ', 'omaha'));
							if (!empty($categories_list)) {
								?> 
								<span class="cat-links">
									<?php echo $categories_list; ?>
								</span>

							</div><!--.entry-meta-category-tag-->
						<?php } // End if 'post' == get_post_type()  ?>

						<div class="entry-meta-comment-tools">
							<?php if (!post_password_required() && (comments_open() || '0' != get_comments_number())) { ?> 
								, <?php comments_popup_link(); ?>
							<?php } //endif;  ?>
						</div>
					</div><!-- .entry-meta -->
				<?php } //endif;  ?> 
            </div>
        </div>
		<div class="clearfix"></div>
		<?php
		if (!post_password_required() && !is_attachment()) :
			the_post_thumbnail();
		endif;
		?>
    </header><!-- .entry-header -->

    <div class="clearfix"></div>

	<?php if (is_search()) { // Only display Excerpts for Search ?> 
		<div class="entry-summary">
			<?php the_excerpt(); ?> 
			<div class="clearfix"></div>
		</div><!-- .entry-summary -->
	<?php } else { ?> 
		<div class="entry-content">
			<?php the_content(omahaMoreLinkText()); ?> 
			<div class="clearfix"></div>
			<?php
			wp_link_pages(array(
				'before' => '<div class="page-links">' . __('Pages:', 'omaha') . ' <ul class="pagination">',
				'after' => '</ul></div>',
				'separator' => ''
			));
			?> 
		</div><!-- .entry-content -->
	<?php } //endif;  ?> 


    <footer class="entry-meta">
        <div class="entry-meta-category-tag">
			<?php the_tags(); ?>
        </div>

		<?php edit_post_link(); ?>
    </footer><!-- .entry-meta -->
    <div class="clearfix"></div>
</article><!-- #post-## -->