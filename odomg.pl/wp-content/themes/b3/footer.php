<?php
/**
 * The template for displaying the footer.
 *
 * Contains the closing of the #content div and all content after
 *
 * @package B3
 */
?>

	</div><!-- #content -->
	<?php
	if ( 'Y' == b3theme_option('sidebar_bottom') && (!is_404() || is_404() && 'Y' != b3theme_option('reduce_404_page')) ) {
		get_sidebar('bottom');
	}
	?>
	<footer id="colophon" class="site-footer text-center spacer-all" role="contentinfo">
		<div class="site-info center">
			<?php
			if ($text = b3theme_option('copyright')) {
				echo '<p>&copy; ' .$text . '</p>';
			}
			if ('Y' == b3theme_option('credits')) {
				do_action('b3theme_credits'); ?>
				<?php printf( __('Proudly powered by %s', 'b3theme'), '<a href="http://wordpress.org/">WordPress</a>'); ?>.
				<?php printf( __('Theme %1$s by %2$s.', 'b3theme'), 'B3', '<a href="http://andrey.ws/">Andrey K</a>');
			}
			?>
		</div><!-- .site-info -->
	</footer><!-- #colophon -->
</div><!-- #page -->

<?php wp_footer(); ?>

</body>
</html>