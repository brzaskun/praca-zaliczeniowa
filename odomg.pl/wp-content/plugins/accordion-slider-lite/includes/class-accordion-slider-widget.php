<?php
/**
 * Accordion Slider widget
 * 
 * @since 1.0.0
 */
class BQW_Accordion_Slider_Lite_Widget extends WP_Widget {
	
	/**
	 * Initialize the widget
	 *
	 * @since 1.0.0
	 */
	public function __construct() {
		
		$widget_opts = array(
			'classname' => 'bqw-accordion-slider-lite-widget',
			'description' => 'Display an Accordion Slider instance in the widgets area.'
		);
		
		$this->WP_Widget( 'bqw-accordion-slider-lite-widget', 'Accordion Slider', $widget_opts );
	}
	
	/**
	 * Create the admin interface of the widget.
	 *
	 * Receives the title of the widget and the id of the
	 * selected accordion. Then it gets loads all accordion
	 * id's and names from the database and displays them in
	 * the list of accordions to chose from.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array $instance The accordion id and widget title
	 */
	public function form( $instance ) {
		$instance = wp_parse_args( ( array )$instance, array( 'accordion_id' => '' ) );
		
		$accordion_id = strip_tags( $instance['accordion_id'] );
		$title = isset( $instance['title'] ) ? strip_tags( $instance['title'] ) : '';
		
		global $wpdb;
		$table_name = $wpdb->prefix . 'accordionslider_accordions';
		$accordions = $wpdb->get_results( "SELECT id, name FROM $table_name", ARRAY_A );
		
		echo '<p>';
		echo '<label for="' . $this->get_field_name( 'title' ) . '">Title: </label>';
		echo '<input type="text" value="' . $title . '" name="' . $this->get_field_name( 'title' ) . '" id="' . $this->get_field_name( 'title' ) . '" class="widefat">';
		echo '</p>';
		
		echo '<p>';
		echo '<label for="' . $this->get_field_name( 'accordion_id' ) . '">Select the accordion: </label>';
		echo '<select name="' . $this->get_field_name( 'accordion_id' ) . '" id="' . $this->get_field_name( 'accordion_id' ) . '" class="widefat">';
			foreach ( $accordions as $accordion ) {
				$selected = $accordion_id == $accordion['id'] ? 'selected="selected"' : "";
				echo "<option value=". $accordion['id'] ." $selected>" . stripslashes( $accordion['name'] ) . ' (' . $accordion['id'] . ')' . "</option>";
			}
		echo '</select>';
		echo '</p>';
	}
	
	/**
	 * Updates the selected accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array $new_instance The new accordion instance.
	 * @param  array $old_instance The old accordion instance.
	 * @return array               The new accordion instance.
	 */
	public function update( $new_instance, $old_instance ) {
		$instance = $old_instance;		
		$instance['accordion_id'] = strip_tags( $new_instance['accordion_id'] );
		$instance['title'] = strip_tags( $new_instance['title'] );
		
		return $instance;
	}
	
	/**
	 * Create the public view.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array $args     Widget data.
	 * @param  array $instance Accordion instance id and widget title
	 */
	function widget( $args, $instance ) {
		extract( $args, EXTR_SKIP );
		$title = apply_filters( 'widget_title', $instance['title'], $instance, $this->id_base );
		
		echo $before_widget;
		
		if ( $title ) {
			echo $before_title . $title . $after_title;
		}

		echo do_shortcode( '[accordion_slider id="' . $instance['accordion_id'] . '"]' );
		echo $after_widget;
	}
}

/**
 * Register the widget
 *
 * @since 1.0.0
 */
function bqw_asl_register_widget() {
	register_widget( 'BQW_Accordion_Slider_Lite_Widget' );
}