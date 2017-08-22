<?php
/**
 * Template for dispalying single post (read full post page).
 */
get_header();
?> 
<div class="col-md-12 content-area" id="main-column">
    <main id="main" class="site-main" role="main">
		<?php
		while (have_posts()) {
			the_post();

			get_template_part('content', get_post_format());

			echo "\n\n";

			omahaPagination();

			echo "\n\n";

			// If comments are open or we have at least one comment, load up the comment template
			if (comments_open() || '0' != get_comments_number()) {
				comments_template();
			}

			echo "\n\n";
		} //endwhile;
		?> 
    </main>
</div>
<?php get_footer(); ?> 