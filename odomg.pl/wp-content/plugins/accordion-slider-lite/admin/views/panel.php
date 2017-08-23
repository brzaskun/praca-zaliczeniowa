<div class="panel">
	<span class="spinner panel-spinner"></span>
	
	<div class="panel-preview">
		<?php 
			if ( $panel_image !== '' ) {
				echo '<img src="' . esc_url( $panel_image ) . '" />';
			} else {
				echo '<p class="no-image">' . __( 'Click to add image', 'accordion-slider-lite' ) . '</p>';
			}
		?>
	</div>

	<div class="panel-controls">
		<a class="delete-panel" href="#" title="Delete Panel">Delete</a>
		<a class="duplicate-panel" href="#" title="Duplicate Panel">Duplicate</a>
	</div>
</div>
