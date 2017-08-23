<?php
/**
 * Template for displaying search form in omaha theme
 * 
 * @package omaha
 */
?>
<form role="search" method="get" class="search-form form" action="<?php echo esc_url(home_url('/')); ?>">
    <label for="form-search-input" class="sr-only"><?php _ex('Search for', 'label', 'omaha'); ?></label>
    <div class="input-group">
        <input type="search" id="form-search-input" class="form-control" placeholder="<?php echo esc_attr_x('Search &hellip;', 'placeholder', 'omaha'); ?>" value="<?php echo esc_attr(get_search_query()); ?>" name="s" title="<?php echo esc_attr_x('Search for:', 'label', 'omaha'); ?>">
    </div>
</form>