<?php

//define( '__OWPST_jdvu__THIS_PLUGIN__PROJECT_IS_VALID_' , 'The project was set up as being valid' );
//define( '__OWPST_jdvu__THIS_PLUGIN__PROJECT_IS_NOT_VALID_' , 'The project was outdated' );
//define( '__OWPST_jdvu__THIS_PLUGIN__PROJECT_VALIDATION_ERROR_' , 'An error occurred during the validation' );
//define( '__OWPST_jdvu__THIS_PLUGIN__PROJECT_VALIDATION_TITLE_' , 'Project validation message' );

class _OWPST_jdvu__Conf {

	

	public static $defaultScriptsAndStyles = array(
		'common' => array(
			'js' => array(
				
			),
			'css' => array(

			)
		),
		'admin' => array(
			'js' => array(
/*
				array( 
					'handle' => 'owpst_jdvu_admin-single-project',
					'src' => 'script-admin-single-project.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'post-new.php',
						'post.php'
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-single-project-update',
					'src' => 'script-admin-single-project-update.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'post-new.php',
						'post.php'
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-datetime-picker',
					'src' => 'jquery.datetimepicker.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'post.php',
						'post-new.php'
					)
				),

				array(
					'handle' => 'owpst_jdvu_admin-pnotify-custom',
					'src' => 'pnotify.custom.min.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-projects-list',
					'src' => 'script-admin-projects-list.js',
					'deps' => array(
						'jquery',
						'owpst_jdvu_admin-pnotify-custom'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
					),
					'localize' => array(
						'jdvu_project_is_valid' => __OWPST_jdvu__THIS_PLUGIN__PROJECT_IS_VALID_,
						'jdvu_project_is_not_valid' => __OWPST_jdvu__THIS_PLUGIN__PROJECT_IS_NOT_VALID_,
						'jdvu_error_project_validation_error' => __OWPST_jdvu__THIS_PLUGIN__PROJECT_VALIDATION_ERROR_,
						'jdvu_error_project_validation_title' => __OWPST_jdvu__THIS_PLUGIN__PROJECT_VALIDATION_TITLE_
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-dashboard',
					'src' => 'script-admin-dashboard.js',
					'deps' => array(
						'jquery',
						'jquery-ui-dialog'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'index.php',
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-bootstrap',
					'src' => 'bootstrap.min.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'index.php',
					)
				)
*/
			),
			'css' => array(
/*
				array( 
					'handle' => 'owpst_jdvu_admin-single-project',
					'src' => 'style-admin-single-project.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'post-new.php',
						'post.php'
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-datetime-picker',
					'src' => 'jquery.datetimepicker.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'post.php',
						'post-new.php'
					)
				),

				array(
					'handle' => 'owpst_jdvu_admin-pnotify-custom',
					'src' => 'pnotify.custom.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
					)
				),

				array(
					'handle' => 'owpst_jdvu_admin-jquery-ui',
					'src' => 'jquery-ui.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
						'index.php'
					)
				),

				array(
					'handle' => 'owpst_jdvu_admin-jquery-ui-structure',
					'src' => 'jquery-ui.structure.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
						'index.php'
					)
				),

				array(
					'handle' => 'owpst_jdvu_admin-jquery-ui-theme',
					'src' => 'jquery-ui.theme.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
						'index.php'
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-projects-list',
					'src' => 'style-admin-projects-list.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'edit.php',
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-dashboard',
					'src' => 'style-admin-dashboard.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'index.php'
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-bootstrap',
					'src' => 'bootstrap.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'index.php',
					)
				),

				array( 
					'handle' => 'owpst_jdvu_admin-bootstrap-theme',
					'src' => 'bootstrap-theme.min.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'hook_deps' => array(
						'index.php',
					)
				)
*/
			)
		),
		'front' => array(
			'js' => array(
/*
				array( 
					'handle' => 'owpst_jdvu_front',
					'src' => 'script-front.js',
					'deps' => array(
						'jquery'
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_,
					'localize' => array(
						'jdvu_ajaxurl' => __OWPST_jdvu__THIS_PLUGIN__ADMINAJAX_
					)
				)
*/
			),
			'css' => array(
/*
				array( 
					'handle' => 'owpst_jdvu_front-style-css',
					'src' => 'style-front-css.css',
					'deps' => array(
					),
					'ver' => __OWPST_jdvu__THIS_PLUGIN__VERSION_
				)
*/
			)
		)
	);

	public static $added_PHP_Common_Files = array(
	);

	public static $added_PHP_Admin_Files = array(
	);

	public static $added_PHP_Front_Files = array(
	);

	public static $added_SCRIPT_Common_Files = array(
	);

	public static $added_STYLE_Common_Files = array(
	);

	public static $added_SCRIPT_Admin_Files = array(
	);

	public static $added_STYLE_Admin_Files = array(
	);

	public static $added_SCRIPT_Front_Files = array(
	);

	public static $added_STYLE_Front_Files = array(
	); 

}
