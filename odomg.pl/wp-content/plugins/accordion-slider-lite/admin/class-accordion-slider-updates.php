<?php
/**
 * Handles options and database changes in updates
 * 
 * @since 1.0.0
 */
class BQW_Accordion_Slider_Lite_Updates {

	/**
	 * Current class instance.
	 * 
	 * @since 1.0.0
	 * 
	 * @var object
	 */
	protected static $instance = null;

	/**
	 * Current version number in the database
	 * 
	 * @since 1.0.0
	 * 
	 * @var string
	 */
	protected $db_version; 

	/**
	 * @since 1.0.0
	 *
	 * Retrieves the version number from the database and
	 * if it's less than the current version number, it
	 * starts the updating process for all the sites in the network
	 */
	public function __construct() {
		$this->db_version = get_option( 'accordion_slider_version', '1.0.0' );

		if ( version_compare( $this->db_version, BQW_Accordion_Slider_Lite::VERSION, '>=' ) ) {
			return;
		}

		if ( function_exists( 'is_multisite' ) && is_multisite() ) {
			$blog_ids = $this->get_blog_ids();

			foreach ( $blog_ids as $blog_id ) {
				switch_to_blog( $blog_id );
				$this->single_update();
			}

			restore_current_blog();
		} else {
			$this->single_update();
		}
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
	 * Return a list of all blogs' id's.
	 *
	 * @since 1.0.0
	 * 
	 * @return object The id's.
	 */
	private function get_blog_ids() {
		global $wpdb;

		$sql = "SELECT blog_id FROM $wpdb->blogs WHERE archived = '0' AND spam = '0' AND deleted = '0'";

		return $wpdb->get_col($sql);
	}

	/**
	 * Do database modifications if necessary.
	 *
	 * @since 1.0.0
	 */
	private function single_update() {
		update_option( 'accordion_slider_version', BQW_Accordion_Slider_Lite::VERSION );
	}
}