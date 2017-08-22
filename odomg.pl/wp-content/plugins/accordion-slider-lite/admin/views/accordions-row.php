<?php
	$lad_nonce = wp_create_nonce( 'load-accordion-data' . $accordion_id );
	$delete_nonce = wp_create_nonce( 'delete-accordion' . $accordion_id );
	$duplicate_nonce = wp_create_nonce( 'duplicate-accordion' . $accordion_id );

	$edit_url = admin_url( 'admin.php?page=accordion-slider-lite&id=' . $accordion_id . '&action=edit' );
	$preview_url = admin_url( 'admin.php?page=accordion-slider-lite&id=' . $accordion_id . '&action=preview' ) . '&lad_nonce=' . $lad_nonce;
	$delete_url = admin_url( 'admin.php?page=accordion-slider-lite&id=' . $accordion_id . '&action=delete' ) . '&da_nonce=' . $delete_nonce;
	$duplicate_url = admin_url( 'admin.php?page=accordion-slider-lite&id=' . $accordion_id . '&action=duplicate' ) . '&dua_nonce=' . $duplicate_nonce;
?>
<tr class="accordion-row">
	<td><?php echo $accordion_id; ?></td>
	<td><?php echo esc_html( $accordion_name ); ?></td>
	<td><?php echo '[accordion_slider id="' . $accordion_id . '"]'; ?></td>
	<td><?php echo $accordion_created; ?></td>
	<td><?php echo $accordion_modified; ?></td>
	<td>
		<a href="<?php echo $edit_url; ?>"><?php _e( 'Edit', 'accordion-slider-lite' ); ?></a> |
		<a class="preview-accordion" href="<?php echo $preview_url; ?>"><?php _e( 'Preview', 'accordion-slider-lite' ); ?></a> |
		<a class="delete-accordion" href="<?php echo $delete_url; ?>"><?php _e( 'Delete', 'accordion-slider-lite' ); ?></a> |
		<a class="duplicate-accordion" href="<?php echo $duplicate_url; ?>"><?php _e( 'Duplicate', 'accordion-slider-lite' ); ?></a>
	</td>
</tr>