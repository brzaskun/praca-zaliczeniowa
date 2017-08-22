<?php
/**
 * The Header for our theme.
 *
 * Displays all of the <head> section and everything up till <div id="content">
 *
 * @package B3
 */
?><!DOCTYPE html>
<html <?php language_attributes(); ?>>
<head>
<meta charset="<?php bloginfo('charset'); ?>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><?php wp_title('|', true, 'right'); ?></title>
<link rel="profile" href="http://gmpg.org/xfn/11">
<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<?php wp_head(); ?>
</head>
<body <?php body_class(); ?>>
<?php 
	if ('top' == b3theme_option('navbar_location')) {
		b3theme_navbar();
	}
?>
<div id="page" class="hfeed site container">
	<?php do_action('before_header'); ?>
	<header id="masthead" class="site-header" role="banner">
<div class="row">
	<div class="site-branding <?php echo 'Y' == b3theme_option('sidebar_top') ? 'col-md-6': 'col-md-11'; ?> clearfix">
<?php
		if ('Y' == b3theme_option('logo_enabled')) {
			echo '<a title="' . get_option('blogname') . '" href="' . esc_url(home_url('/'))
				. '"><img class="site-logo img-responsive" src="';
			header_image();
			echo '" alt="'. get_option('blogname') .' logo" width="' . get_custom_header()->width . '" height="' . get_custom_header()->height . '" /></a>';
		}

		if ('Y' == b3theme_option('site_title_enabled')) { ?>
			<h1 class="site-title"><a href="<?php echo esc_url(home_url('/')); ?>" rel="home"><?php bloginfo('name'); ?></a></h1>
			<?php
		}
		if ('Y' == b3theme_option('site_description_enabled')) { ?>
			<h2 class="site-description"><?php bloginfo('description'); ?></h2>
			<?php
		}
?>  
	</div>

	<?php if ('Y' == b3theme_option('sidebar_top')) { get_sidebar('top'); } ?>

</div>
<?php
	if ( !in_array(b3theme_option('navbar_location'), array('disable', 'top')) ) {
		b3theme_navbar();
	}
?>
	</header><!-- #masthead -->

<?php
	if ( is_front_page() && !get_query_var('paged')) {
		b3theme_carousel();
	}
?>

	<div id="content" class="site-content row">
