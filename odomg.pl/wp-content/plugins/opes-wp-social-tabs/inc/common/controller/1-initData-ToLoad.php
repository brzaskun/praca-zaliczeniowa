<?php

# Here you can include the 'model' and 'view' files - not other controllers! All controllers are loaded automatically.

class _OWPST_jdvu__InitData {

	public $plugin_full_name_label;

	public $plugin_slug = '_OWPST_jdvu_';

	public $plugin_short_slug = 'owpst_jdvu';

	private function __construct( $params ) {
		$this->plugin_full_name_label = __( 'Opes WP Social Tabs' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ );
	}
	
	public static function init( $params ) {
		return new _OWPST_jdvu__InitData( $params );
	}

}

$GLOBALS['_OWPST_jdvu__InitData'] = _OWPST_jdvu__InitData::init( array() );
