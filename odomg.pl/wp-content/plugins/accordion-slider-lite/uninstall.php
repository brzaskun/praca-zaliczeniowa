<?php

if ( ! defined( 'WP_UNINSTALL_PLUGIN' ) ) {
	exit();
}

if ( function_exists( 'is_multisite' ) && is_multisite() ) {
	global $wpdb;			
	$blog_ids = $wpdb->get_col( "SELECT blog_id FROM $wpdb->blogs" );
	
	if ( $blog_ids !== false ) {
		foreach ( $blog_ids as $blog_id ) {
			switch_to_blog( $blog_id );
			bqw_accordion_slider_lite_delete_all_data();
		}

		restore_current_blog();
	}
} else {
	bqw_accordion_slider_lite_delete_all_data();
}

function bqw_accordion_slider_lite_delete_all_data() {
	if ( ! class_exists( 'BQW_Accordion_Slider' ) ) {
		global $wpdb;
		$prefix = $wpdb->prefix;

		$accordions_table = $prefix . 'accordionslider_accordions';
		$panels_table = $prefix . 'accordionslider_panels';
		$layers_table = $prefix . 'accordionslider_layers';

		$wpdb->query( "DROP TABLE $accordions_table, $panels_table, $layers_table" );

		delete_option( 'accordion_slider_load_stylesheets' );
		delete_option( 'accordion_slider_access' );
		delete_option( 'accordion_slider_version' );
		
		$wpdb->query( "DELETE FROM " . $prefix . "options WHERE option_name LIKE '%accordion_slider_cache%'" );
	}
}