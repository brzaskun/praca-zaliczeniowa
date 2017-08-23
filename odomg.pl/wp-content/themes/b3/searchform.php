<?php
/**
 * The template for displaying search forms in B3
 *
 * @package B3
 */
?>
<form role="search" method="get" class="search-form form-inline spacer-top spacer-bottom" action="<?php echo esc_url( home_url('/') ); ?>">
	<div class=" input-group">
		<input type="search" class="search-field form-control input-sm" placeholder="<?php echo esc_attr_x('Search &hellip;', 'placeholder', 'b3theme'); ?>" value="<?php echo esc_attr( get_search_query() ); ?>" name="s">
		<span class=" input-group-btn"><button class="search-submit btn btn-default btn-sm"><span class="glyphicon glyphicon-search"> </span></button></span>
	</div>
</form>
