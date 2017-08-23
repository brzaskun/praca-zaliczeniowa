<?php
/**
 * Renderer class for custom panels and base class for dynamic panel renderers.
 *
 * @since  1.0.0
 */
class BQW_ASL_Panel_Renderer {

	/**
	 * Data of the panel.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $data = null;

	/**
	 * ID of the accordion to which the panel belongs.
	 *
	 * @since 1.0.0
	 * 
	 * @var int
	 */
	protected $accordion_id = null;

	/**
	 * index of the panel.
	 *
	 * @since 1.0.0
	 * 
	 * @var int
	 */
	protected $panel_index = null;

	/**
	 * Indicates whether or not the panel's images will be lazy loaded.
	 *
	 * @since 1.0.0
	 * 
	 * @var bool
	 */
	protected $lazy_loading = null;

	/**
	 * Indicates whether or not the panel's image or link can be opened in a lightbox.
	 *
	 * @since 1.0.0
	 * 
	 * @var bool
	 */
	protected $lightbox = null;

	/**
	 * HTML markup of the panel.
	 *
	 * @since 1.0.0
	 * 
	 * @var string
	 */
	protected $html_output = '';

	/**
	 * No implementation yet
	 * .
	 * @since 1.0.0
	 */
	public function __construct() {
		
	}

	/**
	 * Set the data of the panel.
	 *
	 * @since 1.0.0
	 * 
	 * @param array $data         The data of the panel.
	 * @param int   $accordion_id The id of the accordion.
	 * @param int   $panel_index  The index of the panel.
	 */
	public function set_data( $data, $accordion_id, $panel_index ) {
		$this->data = $data;
		$this->accordion_id = $accordion_id;
		$this->panel_index = $panel_index;
	}

	/**
	 * Create the background image(s), link, inline HTML and layers, and return the HTML markup of the panel.
	 *
	 * @since  1.0.0
	 * 
	 * @return string the HTML markup of the panel.
	 */
	public function render() {
		$classes = 'as-panel';

		$this->html_output = "\r\n" . '		<div class="' . $classes . '">';

		if ( $this->has_background_image() ) {
			$this->html_output .= "\r\n" . '			' . ( $this->has_background_link() ? $this->add_link_to_background_image( $this->create_background_image() ) : $this->create_background_image() );
		}

		$this->html_output .= "\r\n" . '		</div>';

		return $this->html_output;
	}

	/**
	 * Check if the panel has a background image.
	 *
	 * @since  1.0.0
	 * 
	 * @return boolean
	 */
	protected function has_background_image() {
		if ( isset( $this->data['background_source'] ) && $this->data['background_source'] !== '' ) {
			return true;
		}

		return false;
	}

	/**
	 * Create the HTML markup for the background image.
	 *
	 * @since  1.0.0
	 * 
	 * @return string HTML markup
	 */
	protected function create_background_image() {
		$background_source = $this->lazy_loading === true ? ' src="' . plugins_url( 'accordion-slider-lite/public/assets/css/images/blank.gif' ) . '" data-src="' . esc_attr( $this->data['background_source'] ) . '"' : ' src="' . esc_attr( $this->data['background_source'] ) . '"';
		$background_alt = isset( $this->data['background_alt'] ) && $this->data['background_alt'] !== '' ? ' alt="' . esc_attr( $this->data['background_alt'] ) . '"' : '';
		$background_title = isset( $this->data['background_title'] ) && $this->data['background_title'] !== '' ? ' title="' . esc_attr( $this->data['background_title'] ) . '"' : '';
		$background_width = isset( $this->data['background_width'] ) && $this->data['background_width'] != 0 ? ' width="' . esc_attr( $this->data['background_width'] ) . '"' : '';
		$background_height = isset( $this->data['background_height'] ) && $this->data['background_height'] != 0 ? ' height="' . esc_attr( $this->data['background_height'] ) . '"' : '';
		
		$classes = "as-background";

		$background_image = '<img class="' . $classes . '"' . $background_source . $background_alt . $background_title . $background_width . $background_height . ' />';

		return $background_image;
	}

	/**
	 * Check if the panel has a link for the background image(s).
	 *
	 * @since  1.0.0
	 * 
	 * @return boolean
	 */
	protected function has_background_link() {
		if ( ( isset( $this->data['background_link'] ) && $this->data['background_link'] !== '' ) || $this->lightbox === true ) {
			return true;
		} 

		return false;
	}

	/**
	 * Create a link for the background image(s).
	 *
	 * If the lightbox is enabled and a link was not specified,
	 * add the background image URL as a link.
	 *
	 * @since 1.0.0
	 * 
	 * @param  string  $image The image markup.
	 * @return string         The link markup.
	 */
	protected function add_link_to_background_image( $image ) {
		$background_link_href = '';

		if ( isset( $this->data['background_link'] ) && $this->data['background_link'] !== '' ) {
			$background_link_href = $this->data['background_link'];
		} else if ( $this->lightbox === true ) {
			if ( $this->has_background_image() ) {
				$background_link_href = $this->data['background_source'];
			}
		}

		$background_link = 
			'<a href="' . $background_link_href . '">' . 
				"\r\n" . '				' . $image . 
			"\r\n" . '			' . '</a>';
		
		return $background_link;
	}
}