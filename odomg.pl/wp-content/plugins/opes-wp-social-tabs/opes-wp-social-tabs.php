<?php
/**
 * @package __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_
 */
/*
Plugin Name: Opes WP Social Tabs
Plugin URI: https://wordpress.org/plugins/opes-wp-social-tabs/
Description: Opes WP Social Tabs allows you to add and manage social sliders on your WordPress website.
Version: 1.2.1
Author: Paweł Twardziak
Author URI: http://it-opes.com/
License: GPLv2 or later
Text Domain: __OWPST_jdvu__
*/

/*
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

define( '__OWPST_jdvu__DS_' , DIRECTORY_SEPARATOR );
define( '__OWPST_jdvu__PS_' , '/' );
define( '__OWPST_jdvu__THIS_PLUGIN__VERSION_' , '1.2.1' );
define( '__OWPST_jdvu__THIS_PLUGIN__MAIN_FILE_' , __FILE__ );
define( '__OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_' , '__OWPST_jdvu__' );

define( '__OWPST_jdvu__THIS_PLUGIN__ADMINAJAX_' , admin_url( 'admin-ajax.php' ) );
define( '__OWPST_jdvu__THIS_PLUGIN__DB_VERSION_' , '1.0.0' );

define( '__OWPST_jdvu__THIS_PLUGIN__DIR_' , plugin_dir_path( __FILE__ ) );
define( '__OWPST_jdvu__THIS_PLUGIN__INC_DIR_' , __OWPST_jdvu__THIS_PLUGIN__DIR_ . 'inc' . __OWPST_jdvu__DS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__COMMON_DIR_' , __OWPST_jdvu__THIS_PLUGIN__INC_DIR_ . 'common' . __OWPST_jdvu__DS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__ADMIN_DIR_' , __OWPST_jdvu__THIS_PLUGIN__INC_DIR_ . 'admin' . __OWPST_jdvu__DS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__FRONT_DIR_' , __OWPST_jdvu__THIS_PLUGIN__INC_DIR_ . 'front' . __OWPST_jdvu__DS_ );

define( '__OWPST_jdvu__THIS_PLUGIN__URL_' , plugin_dir_url( __FILE__ ) );
define( '__OWPST_jdvu__THIS_PLUGIN__INC_URL_' , __OWPST_jdvu__THIS_PLUGIN__URL_ . 'inc' . __OWPST_jdvu__PS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__COMMON_URL_' , __OWPST_jdvu__THIS_PLUGIN__INC_URL_ . 'common' . __OWPST_jdvu__PS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__ADMIN_URL_' , __OWPST_jdvu__THIS_PLUGIN__INC_URL_ . 'admin' . __OWPST_jdvu__PS_ );
define( '__OWPST_jdvu__THIS_PLUGIN__FRONT_URL_' , __OWPST_jdvu__THIS_PLUGIN__INC_URL_ . 'front' . __OWPST_jdvu__PS_ );

require_once __OWPST_jdvu__THIS_PLUGIN__DIR_ . "conf.php";
require_once __OWPST_jdvu__THIS_PLUGIN__INC_DIR_ . "main.class.php";

_OWPST_jdvu__Main::init( array() );


