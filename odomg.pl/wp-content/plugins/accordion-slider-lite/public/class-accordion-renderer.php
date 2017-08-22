<?php
/**
 * Renders the accordion slider.
 * 
 * @since 1.0.0
 */
class BQW_ASL_Accordion_Renderer {

	/**
	 * Data of the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $data = null;

	/**
	 * ID of the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var int
	 */
	protected $id = null;

	/**
	 * Settings of the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $settings = null;

	/**
	 * Default accordion settings data.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $default_settings = null;

	/**
	 * HTML markup of the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var string
	 */
	protected $html_output = '';

	/**
	 * List of id's for the CSS files that need to be loaded for the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $css_dependencies = array();

	/**
	 * List of id's for the JS files that need to be loaded for the accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $js_dependencies = array();

	/**
	 * Initialize the accordion renderer by retrieving the id and settings from the passed data.
	 * 
	 * @since 1.0.0
	 *
	 * @param array $data The data of the accordion.
	 */
	public function __construct( $data ) {
		$this->data = $data;
		$this->id = $this->data['id'];
		$this->settings = $this->data['settings'];
		$this->default_settings = BQW_Accordion_Slider_Lite_Settings::getSettings();
	}

	/**
	 * Return the accordion's HTML markup.
	 *
	 * @since 1.0.0
	 * 
	 * @return string The HTML markup of the accordion.
	 */
	public function render() {
		$classes = 'accordion-slider as-no-js';

		$width = isset( $this->settings['width'] ) ? $this->settings['width'] : $this->default_settings['width']['default_value'];
		$height = isset( $this->settings['height'] ) ? $this->settings['height'] : $this->default_settings['height']['default_value'];

		$this->html_output .= "\r\n" . '<div id="accordion-slider-' . $this->id . '" class="' . $classes . '" style="width: ' . $width . 'px; height: ' . $height . 'px;">';

		if ( $this->has_panels() ) {
			$this->html_output .= "\r\n" . '	<div class="as-panels">';
			$this->html_output .= "\r\n" . '		' . $this->create_panels();
			$this->html_output .= "\r\n" . '	</div>';
		}

		$this->html_output .= "\r\n" . '</div>';

		return $this->html_output;
	}

	/**
	 * Check if the accordion has panels.
	 *
	 * @since  1.0.0
	 * 
	 * @return boolean Whether or not the accordion has panels.
	 */
	protected function has_panels() {
		if ( isset( $this->data['panels'] ) && ! empty( $this->data['panels'] ) ) {
			return true;
		}

		return false;
	}

	/**
	 * Create the accordion's panels and get their HTML markup.
	 *
	 * @since  1.0.0
	 * 
	 * @return string The HTML markup of the panels.
	 */
	protected function create_panels() {
		$panels_output = '';
		$panels = $this->data['panels'];
		$panel_counter = 0;

		foreach ( $panels as $panel ) {
			$panels_output .= $this->create_panel( $panel, $panel_counter );
			$panel_counter++;
		}

		return $panels_output;
	}

	/**
	 * Create a panel.
	 * 
	 * @since 1.0.0
	 *
	 * @param  array  $data          The data of the panel.
	 * @param  int    $panel_counter The index of the panel.
	 * @return string                The HTML markup of the panel.
	 */
	protected function create_panel( $data, $panel_counter ) {
		$panel = BQW_ASL_Panel_Renderer_Factory::create_panel( $data );

		$panel->set_data( $data, $this->id, $panel_counter);
		
		return $panel->render();
	}

	/**
	 * Return the inline JavaScript code of the accordion and identify all CSS and JS
	 * files that need to be loaded for the current accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @return string The inline JavaScript code of the accordion.
	 */
	public function render_js() {
		$js_output = '';
		$settings_js = '';

		foreach ( $this->default_settings as $name => $setting ) {
			if ( ! isset( $setting['js_name'] ) ) {
				continue;
			}

			$setting_default_value = $setting['default_value'];
			$setting_value = isset( $this->settings[ $name ] ) ? $this->settings[ $name ] : $setting_default_value;

			if ( $setting_value != $setting_default_value ) {
				if ( $settings_js !== '' ) {
					$settings_js .= ',';
				}

				if ( is_bool( $setting_value ) ) {
					$setting_value = $setting_value === true ? 'true' : 'false';
				} else if ( is_numeric( $setting_value ) === false ) {
					$setting_value = "'" . $setting_value . "'";
				}

				$settings_js .= "\r\n" . '			' . $setting['js_name'] . ': ' . $setting_value;
			}
		}

		$this->add_js_dependency( 'plugin' );

		$js_output .= "\r\n" . '		$( "#accordion-slider-' . $this->id . '" ).accordionSlider({' .
											$settings_js .
						"\r\n" . '		});' . "\r\n";


		if ( isset ( $this->settings['page_scroll_easing'] ) && $this->settings['page_scroll_easing'] !== 'swing' ) {
			$this->add_js_dependency( 'easing' );
		}

		return $js_output;
	}

	/**
	 * Add the id of a CSS file that needs to be loaded for the current accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @param string $id The id of the file.
	 */
	protected function add_css_dependency( $id ) {
		$this->css_dependencies[] = $id;
	}

	/**
	 * Add the id of a JS file that needs to be loaded for the current accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @param string $id The id of the file.
	 */
	protected function add_js_dependency( $id ) {
		$this->js_dependencies[] = $id;
	}

	/**
	 * Return the list of id's for CSS files that need to be loaded for the current accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @return array The list of id's for CSS files.
	 */
	public function get_css_dependencies() {
		return $this->css_dependencies;
	}

	/**
	 * Return the list of id's for JS files that need to be loaded for the current accordion.
	 *
	 * @since 1.0.0
	 * 
	 * @return array The list of id's for JS files.
	 */
	public function get_js_dependencies() {
		return $this->js_dependencies;
	}
}