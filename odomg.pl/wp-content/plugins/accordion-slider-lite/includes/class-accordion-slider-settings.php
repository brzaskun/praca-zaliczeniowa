<?php
/**
 * Contains the default settings for the accordion, panels, layers etc.
 * 
 * @since 1.0.0
 */
class BQW_Accordion_Slider_Lite_Settings {

	/**
	 * The accordion's settings.
	 * 
	 * The array contains the name, label, type, default value, 
	 * JavaScript name and description of the setting.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $settings = array();

	/**
	 * The groups of settings.
	 *
	 * The settings are grouped for the purpose of generating
	 * the accordion's admin sidebar.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $setting_groups = array();

	/**
	 * Layer settings.
	 *
	 * The array contains the name, label, type, default value
	 * and description of the setting.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $layer_settings = array();

	/**
	 * Panel settings.
	 *
	 * The array contains the name, label, type, default value
	 * and description of the setting.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $panel_settings = array();

	/**
	 * Hold the state (opened or closed) of the sidebar panels.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $panels_state = array(
		'appearance' => '',
		'animations' => 'closed',
		'autoplay' => 'closed',
		'mouse_wheel' => 'closed',
		'keyboard' => 'closed',
	);

	/**
	 * Holds the plugin settings.
	 *
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected static $plugin_settings = array();

	/**
	 * Return the accordion settings.
	 *
	 * @since 1.0.0
	 * 
	 * @param  string      $name The name of the setting. Optional.
	 * @return array|mixed       The array of settings or the value of the setting.
	 */
	public static function getSettings( $name = null ) {
		if ( empty( self::$settings ) ) {
			self::$settings = array(
				'width' => array(
					'js_name' => 'width',
					'label' => __( 'Width', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 800,
					'description' => __( 'Sets the width of the accordion. Can be set to a fixed value, like 900 (indicating 900 pixels), or to a percentage value, like 100%. In order to make the accordion responsive, it\'s not necessary to use percentage values. More about this in the description of the Responsive option.', 'accordion-slider-lite' )
				),
				'height' => array(
					'js_name' => 'height',
					'label' => __( 'Height', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 400,
					'description' => __( 'Sets the height of the accordion. Can be set to a fixed value, like 400 (indicating 400 pixels). It\'s not recommended to set this to a percentage value, and it\'s not usually needed, as the accordion will be responsive even with a fixed value set for the height.', 'accordion-slider-lite' )
				),
				'responsive' => array(
					'js_name' => 'responsive',
					'label' => __( 'Responsive', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Makes the accordion responsive. The accordion can be responsive even if the Width and/or Height options are set to fixed values. In this situation, the Width and Height will act as the maximum width and height of the accordion.', 'accordion-slider-lite' )
				),
				'responsive_mode' => array(
					'js_name' => 'responsiveMode',
					'label' => __( 'Responsive Mode', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'auto',
					'available_values' => array(
						'auto' => __( 'Auto', 'accordion-slider-lite' ),
						'custom' => __( 'Custom', 'accordion-slider-lite' )
					),
					'description' => __( '\'Auto\' resizes the accordion and all of its elements (e.g., layers, videos) automatically, while \'Custom\' resizes only the accordion container and panels, and you are given flexibility over the way inner elements (e.g., layers, videos) will respond to smaller sizes. For example, you could use CSS media queries to define different text sizes or to hide certain elements when the accordion becomes smaller, ensuring that all content remains readable without having to zoom in. It\'s important to note that, if \'Auto\' responsiveness is used, the \'Width\' and \'Height\' need to be set to fixed values, so that the accordion can calculate correctly how much it needs to scale.', 'accordion-slider-lite' )
				),
				'aspect_ratio' => array(
					'js_name' => 'aspectRatio',
					'label' => __( 'Aspect Ratio', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => -1,
					'description' => __( 'Sets the aspect ratio of the accordion. The accordion will set its height depending on what value its width has, so that this ratio is maintained. For this reason, the set \'Height\' might be overridden. This property can be used only when \'Responsive Mode\' is set to \'Custom\'. When it\'s set to \'Auto\', the \'Aspect Ratio\' needs to remain -1.', 'accordion-slider-lite' )
				),
				'orientation' => array(
					'js_name' => 'orientation',
					'label' => __( 'Orientation', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'horizontal',
					'available_values' => array(
						'horizontal' => __( 'Horizontal', 'accordion-slider-lite' ),
						'vertical' => __( 'Vertical', 'accordion-slider-lite' )
					),
					'description' => __( 'Sets the orientation of the panels.', 'accordion-slider-lite' )
				),
				'shadow' => array(
					'js_name' => 'shadow',
					'label' => __( 'Shadow', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Indicates if the panels will have a drop shadow effect.', 'accordion-slider-lite' )
				),
				'panel_distance' => array(
					'js_name' => 'panelDistance',
					'label' => __( 'Panel Distance', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 0,
					'description' => __( 'Sets the distance between consecutive panels. Can be set to a percentage or fixed value.', 'accordion-slider-lite' )
				),
				'panel_overlap' => array(
					'js_name' => 'panelOverlap',
					'label' => __( 'Panel Overlap', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Indicates if the panels will be overlapped. If set to false, the panels will have their width or height set so that they are next to each other, but not overlapped.', 'accordion-slider-lite' )
				),
				'start_panel' => array(
					'js_name' => 'startPanel',
					'label' => __( 'Start Panel', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => -1,
					'description' => __( 'Indicates which panel will be opened when the accordion loads (0 for the first panel, 1 for the second panel, etc.). If set to -1, no panel will be opened.', 'accordion-slider-lite' )
				),

				'opened_panel_size' => array(
					'js_name' => 'openedPanelSize',
					'label' => __( 'Opened Panel Size', 'accordion-slider-lite' ),
					'type' => 'mixed',
					'default_value' => 'max',
					'description' => __( 'Sets the size (width if the accordion\'s Orientation is Horizontal; height if the accordion\'s Orientation is Vertical) of the opened panel. Possible values are: \'max\', which will open the panel to its maximum size, so that all the inner content is visible, a percentage value, like \'50%\', which indicates the percentage of the total size (width or height, depending on the Rrientation) of the accordion, or a fixed value.', 'accordion-slider-lite' )
				),
				'max_opened_panel_size' => array(
					'js_name' => 'maxOpenedPanelSize',
					'label' => __( 'Max Opened Panel Size', 'accordion-slider-lite' ),
					'type' => 'mixed',
					'default_value' => '80%',
					'description' => __( 'Sets the maximum allowed size of the opened panel. This should be used when the \'Opened Panel Size\' is set to \'max\', because sometimes the maximum size of the panel might be too big and we want to set a limit. The property can be set to a percentage (of the total size of the accordion) or to a fixed value.', 'accordion-slider-lite' )
				),
				'open_panel_on' => array(
					'js_name' => 'openPanelOn',
					'label' => __( 'Open Panel On', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'hover',
					'available_values' => array(
						'hover' => __( 'Hover', 'accordion-slider-lite' ),
						'click' => __( 'Click', 'accordion-slider-lite' ),
						'never' => __( 'Never', 'accordion-slider-lite' )
					),
					'description' => __( 'If set to \'Hover\', the panels will be opened by moving the mouse pointer over them; if set to \'Click\', the panels will open when clicked. Can also be set to \'never\' to disable the opening of the panels.', 'accordion-slider-lite' )
				),
				'close_panels_on_mouse_out' => array(
					'js_name' => 'closePanelsOnMouseOut',
					'label' => __( 'Close Panels On Mouse Out', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Determines whether the opened panel closes or remains open when the mouse pointer is moved away.', 'accordion-slider-lite' )
				),
				'mouse_delay' => array(
					'js_name' => 'mouseDelay',
					'label' => __( 'Mouse Delay', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 200,
					'description' => __( 'Sets the delay in milliseconds between the movement of the mouse pointer and the opening of the panel. Setting a delay ensures that panels are not opened if the mouse pointer only moves over them without an intent to open the panel.', 'accordion-slider-lite' )
				),
				'open_panel_duration' => array(
					'js_name' => 'openPanelDuration',
					'label' => __( 'Open Panel Duration', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 700,
					'description' => __( 'Determines the duration in milliseconds for the opening animation of the panel.', 'accordion-slider-lite' )
				),
				'close_panel_duration' => array(
					'js_name' => 'closePanelDuration',
					'label' => __( 'Close Panel Duration', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 700,
					'description' => __( 'Determines the duration in milliseconds for the closing animation of the panel.', 'accordion-slider-lite' )
				),

				'autoplay' => array(
					'js_name' => 'autoplay',
					'label' => __( 'Autoplay', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Indicates if the autoplay will be enabled.', 'accordion-slider-lite' )
				),
				'autoplay_delay' => array(
					'js_name' => 'autoplayDelay',
					'label' => __( 'Autoplay Delay', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 5000,
					'description' => __( 'Sets the delay, in milliseconds, of the autoplay cycle.', 'accordion-slider-lite' )
				),
				'autoplay_direction' => array(
					'js_name' => 'autoplayDirection',
					'label' => __( 'Autoplay Direction', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'normal',
					'available_values' => array(
						'normal' =>  __( 'Normal', 'accordion-slider-lite' ),
						'backwards' =>  __( 'Backwards', 'accordion-slider-lite' )
					),
					'description' => __( 'Sets the direction in which the panels will be opened. Can be set to \'Normal\' (ascending order) or \'Backwards\' (descending order).', 'accordion-slider-lite' )
				),
				'autoplay_on_hover' => array(
					'js_name' => 'autoplayOnHover',
					'label' => __( 'Autoplay On Hover', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'pause',
					'available_values' => array(
						'pause' => __( 'Pause', 'accordion-slider-lite' ),
						'stop' => __( 'Stop', 'accordion-slider-lite' ),
						'none' => __( 'None', 'accordion-slider-lite' )
					),
					'description' => __( 'Indicates if the autoplay will be paused when the accordion is hovered.', 'accordion-slider-lite' )
				),

				'mouse_wheel' => array(
					'js_name' => 'mouseWheel',
					'label' => __( 'Mouse Wheel', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Indicates if the accordion will respond to mouse wheel input.', 'accordion-slider-lite' )
				),
				'mouse_wheel_sensitivity' => array(
					'js_name' => 'mouseWheelSensitivity',
					'label' => __( 'Mouse Wheel Sensitivity', 'accordion-slider-lite' ),
					'type' => 'number',
					'default_value' => 50,
					'description' => __( 'Sets how sensitive the accordion will be to mouse wheel input. Lower values indicate stronger sensitivity.', 'accordion-slider-lite' )
				),
				'mouse_wheel_target' => array(
					'js_name' => 'mouseWheelTarget',
					'label' => __( 'Mouse Wheel Target', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'panel',
					'available_values' => array(
						'panel' => __( 'Panel', 'accordion-slider-lite' ),
						'page' => __( 'Page', 'accordion-slider-lite' )
					),
					'description' => __( 'Sets what elements will be targeted by the mouse wheel input. Can be set to \'Panel\' or \'Page\'. Setting it to \'Panel\' will indicate that the panels will be scrolled, while setting it to \'Page\' indicate that the pages will be scrolled.', 'accordion-slider-lite' )
				),

				'keyboard' => array(
					'js_name' => 'keyboard',
					'label' => __( 'Keyboard', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => true,
					'description' => __( 'Indicates if the accordion will respond to keyboard input.', 'accordion-slider-lite' )
				),

				'keyboard_only_on_focus' => array(
					'js_name' => 'keyboardOnlyOnFocus',
					'label' => __( 'Keyboard Only On Focus', 'accordion-slider-lite' ),
					'type' => 'boolean',
					'default_value' => false,
					'description' => __( 'Indicates if the accordion will respond to keyboard input only if the accordion has focus.', 'accordion-slider-lite' )
				),

				'keyboard_target' => array(
					'js_name' => 'keyboardTarget',
					'label' => __( 'Keyboard Target', 'accordion-slider-lite' ),
					'type' => 'select',
					'default_value' => 'panel',
					'available_values' => array(
						'panel' => __( 'Panel', 'accordion-slider-lite' ),
						'page' => __( 'Page', 'accordion-slider-lite' )
					),
					'description' => __( 'Sets what elements will be targeted by the keyboard input. Can be set to \'Panel\' or \'Page\'. Setting it to \'Panel\' will indicate that the panels will be scrolled, while setting it to \'Page\' indicate that the pages will be scrolled.', 'accordion-slider-lite' )
				)
			);

			self::$settings = apply_filters( 'accordion_slider_default_settings', self::$settings );
		}

		if ( ! is_null( $name ) ) {
			return self::$settings[ $name ];
		}

		return self::$settings;
	}

	/**
	 * Return the setting groups.
	 *
	 * @since 1.0.0
	 * 
	 * @return array The array of setting groups.
	 */
	public static function getSettingGroups() {
		if ( empty( self::$setting_groups ) ) {
			self::$setting_groups = array(
				'appearance' => array(
					'label' => __( 'Appearance', 'accordion-slider-lite' ),
					'list' => array(
						'width',
						'height',
						'responsive',
						'responsive_mode',
						'aspect_ratio',
						'orientation',
						'shadow',
						'panel_distance',
						'panel_overlap',
						'start_panel'
					)
				),

				'animations' => array(
					'label' => __( 'Animations', 'accordion-slider-lite' ),
					'list' => array(
						'opened_panel_size',
						'max_opened_panel_size',
						'open_panel_on',
						'close_panels_on_mouse_out',
						'mouse_delay',
						'open_panel_duration',
						'close_panel_duration'
					)
				),

				'autoplay' => array(
					'label' => __( 'Autoplay', 'accordion-slider-lite' ),
					'list' => array(
						'autoplay',
						'autoplay_delay',
						'autoplay_direction',
						'autoplay_on_hover'
					)
				),

				'mouse_wheel' => array(
					'label' => __( 'Mouse Wheel', 'accordion-slider-lite' ),
					'list' => array(
						'mouse_wheel',
						'mouse_wheel_sensitivity',
						'mouse_wheel_target'
					)
				),

				'keyboard' => array(
					'label' => __( 'Keyboard', 'accordion-slider-lite' ),
					'list' => array(
						'keyboard',
						'keyboard_only_on_focus',
						'keyboard_target'
					)
				)
			);
		}

		return self::$setting_groups;
	}

	/**
	 * Return the default panels state.
	 *
	 * @since 1.0.0
	 * 
	 * @return array The array of panels state.
	 */
	public static function getPanelsState() {
		return self::$panels_state;
	}

	/**
	 * Return the plugin settings.
	 *
	 * @since 1.0.0
	 * 
	 * @return array The array of plugin settings.
	 */
	public static function getPluginSettings() {
		if ( empty( self::$plugin_settings ) ) {
			self::$plugin_settings = array(
				'load_stylesheets' => array(
					'label' => __( 'Load stylesheets', 'accordion-slider-lite' ),
					'default_value' => 'automatically',
					'available_values' => array(
						'automatically' => __( 'Automatically', 'accordion-slider-lite' ),
						'homepage' => __( 'On homepage', 'accordion-slider-lite' ),
						'all' => __( 'On all pages', 'accordion-slider-lite' )
					),
					'description' => __( 'The plugin can detect the presence of the accordion in a post, page or widget, and will automatically load the necessary stylesheets. However, when the accordion is loaded in PHP code, like in the theme\'s header or another template file, you need to manually specify where the stylesheets should load. If you load the accordion only on the homepage, select <i>On homepage</i>, or if you load it in the header or another section that is visible on multiple pages, select <i>On all pages</i>.' , 'accordion-slider-lite' )
				),
				'cache_expiry_interval' => array(
					'label' => __( 'Cache expiry interval', 'accordion-slider-lite' ),
					'default_value' => 24,
					'description' => __( 'Indicates the time interval after which an accordion\'s cache will expire. If the cache of an accordion has expired, the accordion will be rendered again and cached the next time it is viewed.', 'accordion-slider-lite' )
				),
				'hide_inline_info' => array(
					'label' => __( 'Hide inline info', 'accordion-slider-lite' ),
					'default_value' => false,
					'description' => __( 'Indicates whether the inline information will be displayed in admin panels and wherever it\'s available.', 'accordion-slider-lite' )
				),
				'hide_getting_started_info' => array(
					'label' => __( 'Hide <i>Getting Started</i> info', 'accordion-slider-lite' ),
					'default_value' => false,
					'description' => __( 'Indicates whether the <i>Getting Started</i> information will be displayed in the <i>All Accordions</i> page, above the list of accordions. This setting will be disabled if the <i>Close</i> button is clicked in the information box.', 'accordion-slider-lite' )
				),
				'access' => array(
					'label' => __( 'Access', 'accordion-slider-lite' ),
					'default_value' => 'manage_options',
					'available_values' => array(
						'manage_options' => __( 'Administrator', 'accordion-slider-lite' ),
						'publish_pages' => __( 'Editor', 'accordion-slider '),
						'publish_posts' => __( 'Author', 'accordion-slider-lite' ),
						'edit_posts' => __( 'Contributor', 'accordion-slider-lite' )
					),
					'description' => __( 'Sets what category of users will have access to the plugin\'s admin area.', 'accordion-slider-lite' )
				)
			);
		}

		return self::$plugin_settings;
	}
}