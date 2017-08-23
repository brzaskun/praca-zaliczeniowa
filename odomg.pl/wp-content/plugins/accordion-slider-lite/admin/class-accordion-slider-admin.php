<?php
/**
 * Accordion Slider admin class.
 * 
 * @since 1.0.0
 */
class BQW_Accordion_Slider_Lite_Admin {

	/**
	 * Current class instance.
	 * 
	 * @since 1.0.0
	 * 
	 * @var object
	 */
	protected static $instance = null;

	/**
	 * Stores the hook suffixes for the plugin's admin pages.
	 * 
	 * @since 1.0.0
	 * 
	 * @var array
	 */
	protected $plugin_screen_hook_suffixes = null;

	/**
	 * Current class instance of the public Accordion Slider class.
	 * 
	 * @since 1.0.0
	 * 
	 * @var object
	 */
	protected $plugin = null;

	/**
	 * Plugin class.
	 * 
	 * @since 1.0.0
	 * 
	 * @var object
	 */
	protected $plugin_slug = null;

	/**
	 * Initialize the admin by registering the required actions.
	 *
	 * @since 1.0.0
	 */
	private function __construct() {
		$this->plugin = BQW_Accordion_Slider_Lite::get_instance();
		$this->plugin_slug = $this->plugin->get_plugin_slug();

		// load the admin CSS and JavaScript
		add_action( 'admin_enqueue_scripts', array( $this, 'enqueue_admin_styles' ) );
		add_action( 'admin_enqueue_scripts', array( $this, 'enqueue_admin_scripts' ) );

		add_action( 'admin_menu', array( $this, 'add_admin_menu' ) );

		add_action( 'wp_ajax_accordion_slider_lite_get_accordion_data', array( $this, 'ajax_get_accordion_data' ) );
		add_action( 'wp_ajax_accordion_slider_lite_save_accordion', array( $this, 'ajax_save_accordion' ) );
		add_action( 'wp_ajax_accordion_slider_lite_preview_accordion', array( $this, 'ajax_preview_accordion' ) );
		add_action( 'wp_ajax_accordion_slider_lite_delete_accordion', array( $this, 'ajax_delete_accordion' ) );
		add_action( 'wp_ajax_accordion_slider_lite_duplicate_accordion', array( $this, 'ajax_duplicate_accordion' ) );
		add_action( 'wp_ajax_accordion_slider_lite_add_panels', array( $this, 'ajax_add_panels' ) );
		add_action( 'wp_ajax_accordion_slider_lite_clear_all_cache', array( $this, 'ajax_clear_all_cache' ) );
	}

	/**
	 * Return the current class instance.
	 *
	 * @since 1.0.0
	 * 
	 * @return object The instance of the current class.
	 */
	public static function get_instance() {
		if ( self::$instance == null ) {
			self::$instance = new self;
		}

		return self::$instance;
	}

	/**
	 * Loads the admin CSS files.
	 *
	 * It loads the public and admin CSS, and also the public custom CSS.
	 *
	 * @since 1.0.0
	 */
	public function enqueue_admin_styles() {
		if ( ! isset( $this->plugin_screen_hook_suffixes ) ) {
			return;
		}

		$screen = get_current_screen();

		if ( in_array( $screen->id, $this->plugin_screen_hook_suffixes ) ) {
			wp_enqueue_style( $this->plugin_slug . '-admin-style', plugins_url( 'accordion-slider-lite/admin/assets/css/accordion-slider-admin.min.css' ), array(), BQW_Accordion_Slider_Lite::VERSION );
			wp_enqueue_style( $this->plugin_slug . '-plugin-style', plugins_url( 'accordion-slider-lite/public/assets/css/accordion-slider.min.css' ), array(), BQW_Accordion_Slider_Lite::VERSION );
		}
	}

	/**
	 * Loads the admin JS files.
	 *
	 * It loads the public and admin JS, and also the public custom JS.
	 * Also, it passes the PHP variables to the admin JS file.
	 *
	 * @since 1.0.0
	 */
	public function enqueue_admin_scripts() {
		if ( ! isset( $this->plugin_screen_hook_suffixes ) ) {
			return;
		}

		$screen = get_current_screen();

		if ( in_array( $screen->id, $this->plugin_screen_hook_suffixes ) ) {
			if ( function_exists( 'wp_enqueue_media' ) ) {
		    	wp_enqueue_media();
			}
			
			wp_enqueue_script( $this->plugin_slug . '-admin-script', plugins_url( 'accordion-slider-lite/admin/assets/js/accordion-slider-admin.min.js' ), array( 'jquery' ), BQW_Accordion_Slider_Lite::VERSION );
			wp_enqueue_script( $this->plugin_slug . '-plugin-script', plugins_url( 'accordion-slider-lite/public/assets/js/jquery.accordionSlider.min.js' ), array( 'jquery' ), BQW_Accordion_Slider_Lite::VERSION );

			$id = isset( $_GET['id'] ) ? $_GET['id'] : -1;

			wp_localize_script( $this->plugin_slug . '-admin-script', 'as_js_vars', array(
				'admin' => admin_url( 'admin.php' ),
				'ajaxurl' => admin_url( 'admin-ajax.php' ),
				'plugin' => plugins_url( 'accordion-slider-lite' ),
				'page' => isset( $_GET['page'] ) && ( $_GET['page'] === 'accordion-slider-lite-new' || ( isset( $_GET['id'] ) && isset( $_GET['action'] ) && $_GET['action'] === 'edit' ) ) ? 'single' : 'all',
				'id' => $id,
				'lad_nonce' => wp_create_nonce( 'load-accordion-data' . $id ),
				'sa_nonce' => wp_create_nonce( 'save-accordion' . $id ),
				'no_image' => __( 'Click to add image', 'accordion-slider-lite' ),
				'accordion_delete' => __( 'Are you sure you want to delete this accordion?', 'accordion-slider-lite' ),
				'panel_delete' => __( 'Are you sure you want to delete this panel?', 'accordion-slider-lite' ),
				'yes' => __( 'Yes', 'accordion-slider-lite' ),
				'cancel' => __( 'Cancel', 'accordion-slider-lite' ),
				'accordion_update' => __( 'Accordion slider updated.', 'accordion-slider-lite' ),
				'accordion_create' => __( 'Accordion slider created.', 'accordion-slider-lite' )
			) );
		}
	}

	/**
	 * Create the plugin menu.
	 *
	 * @since 1.0.0
	 */
	public function add_admin_menu() {
		$plugin_settings = BQW_Accordion_Slider_Lite_Settings::getPluginSettings();
		$access = get_option( 'accordion_slider_access', $plugin_settings['access']['default_value'] );

		add_menu_page(
			'Accordion Slider',
			'Accordion Slider',
			$access,
			$this->plugin_slug,
			array( $this, 'render_accordion_page' ),
			plugins_url( '/accordion-slider-lite/admin/assets/css/images/as-icon.png' )
		);

		$this->plugin_screen_hook_suffixes[] = add_submenu_page(
			$this->plugin_slug,
			__( 'Accordion Slider', $this->plugin_slug ),
			__( 'All Accordions', $this->plugin_slug ),
			$access,
			$this->plugin_slug,
			array( $this, 'render_accordion_page' )
		);

		$this->plugin_screen_hook_suffixes[] = add_submenu_page(
			$this->plugin_slug,
			__( 'Add New Accordion', $this->plugin_slug ),
			__( 'Add New', $this->plugin_slug ),
			$access,
			$this->plugin_slug . '-new',
			array( $this, 'render_new_accordion_page' )
		);

		$this->plugin_screen_hook_suffixes[] = add_submenu_page(
			$this->plugin_slug,
			__( 'Plugin Settings', $this->plugin_slug ),
			__( 'Plugin Settings', $this->plugin_slug ),
			$access,
			$this->plugin_slug . '-settings',
			array( $this, 'render_plugin_settings_page' )
		);

		$this->plugin_screen_hook_suffixes[] = add_submenu_page(
			$this->plugin_slug,
			__( 'Upgrade', $this->plugin_slug ),
			__( 'Upgrade', $this->plugin_slug ),
			$access,
			$this->plugin_slug . '-ugrade',
			array( $this, 'render_upgrade_page' )
		);
	}

	/**
	 * Renders the accordion page.
	 *
	 * Based on the 'action' parameter, it will render
	 * either an individual accordion page or the list
	 * of all the accordions.
	 *
	 * If an individual accordion page is rendered, delete
	 * the transients that store the post names and posts data,
	 * in order to trigger a new fetching of them.
	 * 
	 * @since 1.0.0
	 */
	public function render_accordion_page() {
		if ( isset( $_GET['id'] ) && isset( $_GET['action'] ) && $_GET['action'] === 'edit' ) {
			$accordion = $this->plugin->get_accordion( $_GET['id'] );

			if ( $accordion !== false ) {
				$accordion_id = $accordion['id'];
				$accordion_name = $accordion['name'];
				$accordion_settings = $accordion['settings'];
				$accordion_panels_state = $accordion['panels_state'];

				$panels = isset( $accordion['panels'] ) ? $accordion['panels'] : false;

				delete_transient( 'accordion_slider_post_names' );
				delete_transient( 'accordion_slider_posts_data' );

				include_once( 'views/accordion.php' );
			} else {
				include_once( 'views/accordions.php' );
			}
		} else {
			include_once( 'views/accordions.php' );
		}
	}

	/**
	 * Renders the page for a new accordion.
	 *
	 * Also, delete the transients that store
	 * the post names and posts data,
	 * in order to trigger a new fetching of them.
	 * 
	 * @since 1.0.0
	 */
	public function render_new_accordion_page() {
		$accordion_name = 'My Accordion';

		delete_transient( 'accordion_slider_post_names' );
		delete_transient( 'accordion_slider_posts_data' );

		include_once( 'views/accordion.php' );
	}

	/**
	 * Renders the plugin settings page.
	 *
	 * It also checks if new data was posted, and saves
	 * it in the options table.
	 *
	 * It verifies the purchase code supplied and displays
	 * if it's valid.
	 * 
	 * @since 1.0.0
	 */
	public function render_plugin_settings_page() {
		$plugin_settings = BQW_Accordion_Slider_Lite_Settings::getPluginSettings();
		$load_stylesheets = get_option( 'accordion_slider_load_stylesheets', $plugin_settings['load_stylesheets']['default_value'] );
		$cache_expiry_interval = get_option( 'accordion_slider_cache_expiry_interval', $plugin_settings['cache_expiry_interval']['default_value'] );
		$access = get_option( 'accordion_slider_access', $plugin_settings['access']['default_value'] );

		if ( isset( $_POST['plugin_settings_update'] ) ) {
			check_admin_referer( 'plugin-settings-update', 'plugin-settings-nonce' );

			if ( isset( $_POST['load_stylesheets'] ) ) {
				$load_stylesheets = $_POST['load_stylesheets'];
				update_option( 'accordion_slider_load_stylesheets', $load_stylesheets );
			}

			if ( isset( $_POST['cache_expiry_interval'] ) ) {
				$cache_expiry_interval = $_POST['cache_expiry_interval'];
				update_option( 'accordion_slider_cache_expiry_interval', $cache_expiry_interval );
			}

			if ( isset( $_POST['access'] ) ) {
				$access = $_POST['access'];
				update_option( 'accordion_slider_access', $access );
			}
		}
		
		include_once( 'views/plugin-settings.php' );
	}

	/**
	 * Renders the 'Upgrade' page.
	 * 
	 * @since 1.0.2
	 */
	public function render_upgrade_page() {
		include_once( 'views/upgrade.php' );
	}

	/**
	 * AJAX call for getting the accordion's data.
	 *
	 * @since 1.0.0
	 * 
	 * @return string The accordion data, as JSON-encoded array.
	 */
	public function ajax_get_accordion_data() {
		$nonce = $_GET['nonce'];
		$id = $_GET['id'];

		if ( ! wp_verify_nonce( $nonce, 'load-accordion-data' . $id ) ) {
			die( 'This action was stopped for security purposes.' );
		}

		$accordion = $this->get_accordion_data( $_GET['id'] );

		echo json_encode( $accordion );

		die();
	}

	/**
	 * Return the accordion's data.
	 *
	 * @since 1.0.0
	 * 
	 * @param  int   $id The id of the accordion.
	 * @return array     The accordion data.
	 */
	public function get_accordion_data( $id ) {
		return $this->plugin->get_accordion( $id );
	}

	/**
	 * AJAX call for saving the accordion.
	 *
	 * It can be called when the accordion is created, updated
	 * or when an accordion is imported. If the accordion is 
	 * imported, it returns a row in the list of accordions.
	 *
	 * @since 1.0.0
	 */
	public function ajax_save_accordion() {
		$accordion_data = json_decode( stripslashes( $_POST['data'] ), true );
		$nonce = $accordion_data['nonce'];
		$id = intval( $accordion_data['id'] );
		$action = $accordion_data['action'];

		if ( ! wp_verify_nonce( $nonce, 'save-accordion' . $id ) ) {
			die( 'This action was stopped for security purposes.' );
		}

		$accordion_id = $this->save_accordion( $accordion_data );

		if ( $action === 'save' ) {
			echo $accordion_id;
		}

		die();
	}

	/**
	 * Save the accordion.
	 *
	 * It either creates a new accordion or updates and existing one.
	 *
	 * For existing accordions, the panels and layers are deleted and 
	 * re-inserted in the database.
	 *
	 * The cached accordion is deleted every time the accordion is saved.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array $accordion_data The data of the accordion that's saved.
	 * @return int                   The id of the saved accordion.
	 */
	public function save_accordion( $accordion_data ) {
		global $wpdb;

		$id = intval( $accordion_data['id'] );
		$panels_data = $accordion_data['panels'];

		if ( $id === -1 ) {
			$wpdb->insert($wpdb->prefix . 'accordionslider_accordions', array( 'name' => $accordion_data['name'],
																				'settings' => json_encode( $accordion_data['settings'] ),
																				'created' => date( 'm-d-Y' ),
																				'modified' => date( 'm-d-Y' ),
																				'panels_state' => json_encode( $accordion_data['panels_state'] ) ), 
																		array( '%s', '%s', '%s', '%s', '%s' ) );
			
			$id = $wpdb->insert_id;
		} else {
			$wpdb->update( $wpdb->prefix . 'accordionslider_accordions', array( 'name' => $accordion_data['name'], 
																			 	'settings' => json_encode( $accordion_data['settings'] ),
																			 	'modified' => date( 'm-d-Y' ),
																				'panels_state' => json_encode( $accordion_data['panels_state'] ) ), 
																	   	array( 'id' => $id ), 
																	   	array( '%s', '%s', '%s', '%s' ), 
																	   	array( '%d' ) );
				
			$wpdb->query( $wpdb->prepare( "DELETE FROM " . $wpdb->prefix . "accordionslider_panels WHERE accordion_id = %d", $id ) );

			$wpdb->query( $wpdb->prepare( "DELETE FROM " . $wpdb->prefix . "accordionslider_layers WHERE accordion_id = %d", $id ) );
		}

		foreach ( $panels_data as $panel_data ) {
			$panel = array('accordion_id' => $id,
							'label' => isset( $panel_data['label'] ) ? $panel_data['label'] : '',
							'position' => isset( $panel_data['position'] ) ? $panel_data['position'] : '',
							'visibility' => isset( $panel_data['visibility'] ) ? $panel_data['visibility'] : '',
							'background_source' => isset( $panel_data['background_source'] ) ? $panel_data['background_source'] : '',
							'background_alt' => isset( $panel_data['background_alt'] ) ? $panel_data['background_alt'] : '',
							'background_title' => isset( $panel_data['background_title'] ) ? $panel_data['background_title'] : '',
							'background_width' => isset( $panel_data['background_width'] ) ? $panel_data['background_width'] : '',
							'background_height' => isset( $panel_data['background_height'] ) ? $panel_data['background_height'] : '',
							'background_link' => isset( $panel_data['background_link'] ) ? $panel_data['background_link'] : '',
							'background_link_title' => isset( $panel_data['background_link_title'] ) ? $panel_data['background_link_title'] : '',
							'settings' => isset( $panel_data['settings'] ) ? json_encode( $panel_data['settings'] ) : '');

			$wpdb->insert( $wpdb->prefix . 'accordionslider_panels', $panel, array( '%d', '%s', '%d', '%s', '%s', '%s', '%s', '%d', '%d', '%s', '%s', '%s' ) );
		}
		
		delete_transient( 'accordion_slider_cache_' . $id );

		return $id;
	}

	/**
	 * AJAX call for previewing the accordion.
	 *
	 * Receives the current data from the database (in the accordions page)
	 * or from the current settings (in the accordion page) and prints the
	 * HTML markup and the inline JavaScript for the accordion.
	 *
	 * @since 1.0.0
	 */
	public function ajax_preview_accordion() {
		$accordion = json_decode( stripslashes( $_POST['data'] ), true );
		$accordion_name = $accordion['name'];
		$accordion_output = $this->plugin->output_accordion( $accordion, false ) . $this->plugin->get_inline_scripts();

		include( 'views/preview-window.php' );

		die();	
	}

	/**
	 * AJAX call for duplicating an accordion.
	 *
	 * Loads an accordion from the database and re-saves it with an id of -1, 
	 * which will determine the save function to add a new accordion in the 
	 * database.
	 *
	 * It returns a new accordion row in the list of all accordions.
	 *
	 * @since 1.0.0
	 */
	public function ajax_duplicate_accordion() {
		$nonce = $_POST['nonce'];
		$original_accordion_id = $_POST['id'];

		if ( ! wp_verify_nonce( $nonce, 'duplicate-accordion' . $original_accordion_id ) ) {
			die( 'This action was stopped for security purposes.' );
		}

		if ( ( $original_accordion = $this->plugin->get_accordion( $original_accordion_id ) ) !== false ) {
			$original_accordion['id'] = -1;
			$accordion_id = $this->save_accordion( $original_accordion );
			$accordion_name = $original_accordion['name'];
			$accordion_created = date( 'm-d-Y' );
			$accordion_modified = date( 'm-d-Y' );

			include( 'views/accordions-row.php' );
		}

		die();
	}

	/**
	 * AJAX call for deleting an accordion.
	 *
	 * It's called from the list of accordions, when the
	 * 'Delete' link is clicked.
	 *
	 * It calls the 'delete_accordion()' method and passes
	 * it the id of the accordion to be deleted.
	 *
	 * @since 1.0.0
	 */
	public function ajax_delete_accordion() {
		$nonce = $_POST['nonce'];
		$id = intval( $_POST['id'] );

		if ( ! wp_verify_nonce( $nonce, 'delete-accordion' . $id ) ) {
			die( 'This action was stopped for security purposes.' );
		}

		echo $this->delete_accordion( $id ); 

		die();
	}

	/**
	 * Delete the accordion indicated by the id.
	 *
	 * @since 1.0.0
	 * 
	 * @param  int $id The id of the accordion to be deleted.
	 * @return int     The id of the accordion that was deleted.
	 */
	public function delete_accordion( $id ) {
		global $wpdb;

		$wpdb->query( $wpdb->prepare( "DELETE FROM " . $wpdb->prefix . "accordionslider_panels WHERE accordion_id = %d", $id ) );

		$wpdb->query( $wpdb->prepare( "DELETE FROM " . $wpdb->prefix . "accordionslider_accordions WHERE id = %d", $id ) );

		return $id;
	}
	
	/**
	 * Create a panel from the passed data.
	 *
	 * Receives some data, like the background image, or
	 * the panel's content type. A new panel is created by 
	 * passing 'false' instead of any data.
	 *
	 * @since 1.0.0
	 * 
	 * @param  array|bool $data The data of the panel or false, if the panel is new.
	 */
	public function create_panel( $data ) {
		$panel_image = '';

		if ( $data !== false ) {
			$panel_image = isset( $data['background_source'] ) ? $data['background_source'] : $panel_image;
		}

		include( 'views/panel.php' );
	}
	
	/**
	 * AJAX call for adding multiple or a single panel.
	 *
	 * If it receives any data, it tries to create multiple
	 * panels by padding the data that was received, and if
	 * it doesn't receive any data it tries to create a
	 * single panel.
	 *
	 * @since 1.0.0
	 */
	public function ajax_add_panels() {
		if ( isset( $_POST['data'] ) ) {
			$panels_data = json_decode( stripslashes( $_POST['data'] ), true );

			foreach ( $panels_data as $panel_data ) {
				$this->create_panel( $panel_data );
			}
		} else {
			$this->create_panel( false );
		}

		die();
	}

	/**
	 * AJAX call for deleting the cached accordions
	 * stored using transients.
	 *
	 * It's called from the Plugin Settings page.
	 *
	 * @since 1.0.0
	 */
	public function ajax_clear_all_cache() {
		$nonce = $_POST['nonce'];

		if ( ! wp_verify_nonce( $nonce, 'clear-all-cache' ) ) {
			die( 'This action was stopped for security purposes.' );
		}

		global $wpdb;

		$wpdb->query( "DELETE FROM " . $wpdb->prefix . "options WHERE option_name LIKE '%accordion_slider_cache%'" );

		echo true;

		die();
	}
}