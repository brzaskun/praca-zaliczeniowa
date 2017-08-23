<?php
/**
 * Factory for panel renderers.
 *
 * Implements the appropriate functionality for each panel, depending on the panel's type.
 *
 * @since  1.0.0
 */
class BQW_ASL_Panel_Renderer_Factory {

	/**
	 * Return an instance of the renderer class based on the type of the panel.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array  $data The data of the panel.
	 * @return object       An instance of the appropriate renderer class.
	 */
	public static function create_panel( $data ) {
		return new BQW_ASL_Panel_Renderer();
	}
}