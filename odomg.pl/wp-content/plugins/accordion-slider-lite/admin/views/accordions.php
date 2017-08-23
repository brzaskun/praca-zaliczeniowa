<div class="wrap accordion-slider-admin">
	<h2><?php _e( 'All Accordions' ); ?></h2>

	<table class="widefat accordions-list">
		<thead>
			<tr>
				<th><?php _e( 'ID', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Name', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Shortcode', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Created', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Modified', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Actions', 'accordion-slider-lite' ); ?></th>
			</tr>
		</thead>
		
		<tbody>
			<?php
				global $wpdb;
				$prefix = $wpdb->prefix;

				$accordions = $wpdb->get_results( "SELECT * FROM " . $prefix . "accordionslider_accordions ORDER BY id" );
				
				if ( count( $accordions ) === 0 ) {
					echo '<tr class="no-accordion-row">' .
							 '<td colspan="100%">' . __( 'You don\'t have saved accordions.', 'accordion-slider-lite' ) . '</td>' .
						 '</tr>';
				} else {
					foreach ( $accordions as $accordion ) {
						$accordion_id = $accordion->id;
						$accordion_name = stripslashes( $accordion->name );
						$accordion_created = $accordion->created;
						$accordion_modified = $accordion->modified;

						include( 'accordions-row.php' );
					}
				}
			?>
		</tbody>
		
		<tfoot>
			<tr>
				<th><?php _e( 'ID', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Name', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Shortcode', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Created', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Modified', 'accordion-slider-lite' ); ?></th>
				<th><?php _e( 'Actions', 'accordion-slider-lite' ); ?></th>
			</tr>
		</tfoot>
	</table>
    
    <div class="new-accordion-buttons">
		<a class="button-secondary" href="<?php echo admin_url( 'admin.php?page=accordion-slider-lite-new' ); ?>"><?php _e( 'Create New Accordion', 'accordion-slider-lite' ); ?></a>
    </div>
</div>