<?php

# Here you can include the 'model' and 'view' files - not other controllers! All controllers are loaded automatically.

class _OWPST_jdvu__adminOptionsPanel {

	private $hookname;

	private $optionsPanelSlug;

	private $initialOptions = array(
		'main_settings_group' => array(
			'main_settings' => array(
				//'activity_status'
			)
		),
		'social_links_group' => array(
			'social_links' => array(
				//'activity_status'
			)
		)
	);

	private function __construct( $params ) {
		global $_OWPST_jdvu__InitData;

		register_activation_hook( __OWPST_jdvu__THIS_PLUGIN__MAIN_FILE_ , array( $this , 'activatePluginActions' ) );

		$this->optionsPanelSlug = $_OWPST_jdvu__InitData->plugin_short_slug . '_options';
		add_action( 'admin_menu', array( $this , 'registerAdminOptionsPanel' ) );

		add_action( 'admin_init', array( $this , 'registerAdminOptionsPanel_MainSettings' ) );
	}
	
	public static function init( $params ) {
		return new _OWPST_jdvu__adminOptionsPanel( $params );
	}

	public function activatePluginActions() {
		global $_OWPST_jdvu__InitData;

		$main_settings = get_option( $this->optionsPanelSlug . '_main_settings' , false );
		$social_links = get_option( $this->optionsPanelSlug . '_social_links' , false );

		if ( !is_array( $main_settings ) ) {
			update_option( $this->optionsPanelSlug . '_main_settings' , $this->initialOptions[ 'main_settings_group' ][ 'main_settings' ] );
		};

		if ( !is_array( $social_links ) ) {
			update_option( $this->optionsPanelSlug . '_social_links' , $this->initialOptions[ 'social_links_group' ][ 'social_links' ] );
		};
	}

	public function registerAdminOptionsPanel() {
		

		$this->hookname = add_menu_page( 
			__( 'Opes WP Social Tabs' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ),
			__( 'OWP Social Tabs' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ), 
			'manage_options', 
			$this->optionsPanelSlug,
			array( $this , 'displayAdminOptionsPanel' ), 
			__OWPST_jdvu__THIS_PLUGIN__ADMIN_URL_ . 'assets' . __OWPST_jdvu__PS_ . 'images' . __OWPST_jdvu__PS_ . 'icon-20-green.png',
			81
		);
	}

	public function displayAdminOptionsPanel() {
		global $_OWPST_jdvu__InitData;

		$main_settings = get_option( $this->optionsPanelSlug . '_main_settings' );
		$social_links = get_option( $this->optionsPanelSlug . '_social_links' );
?>
	<div class="wrap">
		<h2><?php echo $_OWPST_jdvu__InitData->plugin_full_name_label; ?></h2>

		<h3><?php _e( 'Main settings' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ) ?></h3>
		<form method="post" action="options.php">
	<?php 
			settings_fields( $this->optionsPanelSlug . '_main_settings_group' ); 
	?>
	<?php 
			do_settings_sections( $this->optionsPanelSlug . '_main_settings_group' );
	?>
			<table class="form-table">
				<tr valign="top">
					<th scope="row"><?php _e( 'Activity status' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></th>
					<td>
						<input type="checkbox" name="<?php echo $this->optionsPanelSlug; ?>_main_settings[activity_status]" <?php 
							if ( isset( $main_settings[ 'activity_status' ] ) && $main_settings[ 'activity_status' ] == 'on' ) {
								echo 'checked="checked"' ; 
							}
						?> />
					</td>
				</tr>
			</table>

			<p>
	<?php 
				submit_button( __( 'Save main' ) , 'primary' , $this->optionsPanelSlug . '_main_settings[submit]' , false , array( 
						'tabindex' => '1'
					) 
				);
				echo ' ';
				submit_button( __( 'Reset main' ) , 'secondary' , $this->optionsPanelSlug . '_main_settings[reset]' , false , array( 
						'tabindex' => '2'
					) 
				);
	?>
			</p>
		</form>

		<h3><?php _e( 'Social IDs' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ) ?></h3>
		<form method="post" action="options.php">
	<?php 
			settings_fields( $this->optionsPanelSlug . '_social_links_group' ); 
	?>
	<?php 
			do_settings_sections( $this->optionsPanelSlug . '_social_links_group' );
	?>
			<table class="form-table">
				<tr valign="top">
					<th scope="row"><?php _e( 'Facebook ID' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></th>
					<td>
						<input type="text" name="<?php echo $this->optionsPanelSlug; ?>_social_links[fb]" value="<?php 
							if ( isset( $social_links[ 'fb' ] ) && is_string( trim( $social_links[ 'fb' ] ) ) ) {
								echo trim( $social_links[ 'fb' ] ) ; 
							}
						?>" />
					</td>
				</tr>
				
				<tr valign="top">
					<th scope="row"><?php _e( 'Google Plus ID' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></th>
					<td>
						<input type="text" name="<?php echo $this->optionsPanelSlug; ?>_social_links[gp]" value="<?php 
							if ( isset( $social_links[ 'gp' ] ) && is_string( trim( $social_links[ 'gp' ] ) ) ) {
								echo trim( $social_links[ 'gp' ] ) ; 
							}
						?>" /><br/>
						<?php 
							if ( isset( $social_links[ 'gp_g_type' ] ) && trim( $social_links[ 'gp_g_type' ] ) != '' ) {
								$selected = $social_links[ 'gp_g_type' ];
							} else {
								$selected = '';
							}
						?>
						<select name="<?php echo $this->optionsPanelSlug; ?>_social_links[gp_g_type]">
							<option value="person" <?php if ( $selected == 'person' ) echo 'selected="selected"'; ?> ><?php _e( 'Person' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></option>
							<option value="page" <?php if ( $selected == 'page' ) echo 'selected="selected"'; ?>><?php _e( 'Page' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></option>
						</select> <?php _e( 'G-type' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?>
					</td>
				</tr>
				
				<tr valign="top">
					<th scope="row"><?php _e( 'Youtube ID' , __OWPST_jdvu__THIS_PLUGIN__TEXT_DOMAIN_ ); ?></th>
					<td>
						<input type="text" name="<?php echo $this->optionsPanelSlug; ?>_social_links[yt]" value="<?php 
							if ( isset( $social_links[ 'yt' ] ) && is_string( trim( $social_links[ 'yt' ] ) ) ) {
								echo trim( $social_links[ 'yt' ] ) ; 
							}
						?>" />
					</td>
				</tr>

			</table>
			
			<p>
	<?php 
				submit_button( __( 'Save links' ) , 'primary' , $this->optionsPanelSlug . '_social_links[submit]' , false , array( 
						'tabindex' => '1'
					) 
				);
				echo ' ';
				submit_button( __( 'Reset links' ) , 'secondary' , $this->optionsPanelSlug . '_social_links[reset]' , false , array( 
						'tabindex' => '2'
					) 
				);
	?>
			</p>
		</form>
	</div>
<?php
	}

	public function registerAdminOptionsPanel_MainSettings() {
		//global $_OWPST_jdvu__InitData;

		register_setting( $this->optionsPanelSlug . '_main_settings_group' , $this->optionsPanelSlug . '_main_settings' , array( $this , 'validateMainSettingsGroup' ) );

		register_setting( $this->optionsPanelSlug . '_social_links_group' , $this->optionsPanelSlug . '_social_links' , array( $this , 'validateSocialLinksGroup' ) );
	}

	public function validateSocialLinksGroup( $inputs ) {
		$validateInputs = array();

		//echo "<pre>".print_r($inputs,true)."</pre>";
		//wp_die();

		if ( isset( $inputs[ 'submit' ] ) ) {
			if ( isset( $inputs[ 'fb' ] ) &&  is_string( trim( $inputs[ 'fb' ] ) ) && trim( $inputs[ 'fb' ] ) != '' ) {
				$validateInputs[ 'fb' ] = trim( $inputs[ 'fb' ] );
			};
			if ( isset( $inputs[ 'gp' ] ) &&  is_string( trim( $inputs[ 'gp' ] ) ) && trim( $inputs[ 'gp' ] ) != '' ) {
				$validateInputs[ 'gp' ] = trim( $inputs[ 'gp' ] );

				if ( isset( $inputs[ 'gp_g_type' ] ) &&  is_string( trim( $inputs[ 'gp_g_type' ] ) ) && trim( $inputs[ 'gp_g_type' ] ) != '' ) {
					$validateInputs[ 'gp_g_type' ] = trim( $inputs[ 'gp_g_type' ] );
				};
			};
			if ( isset( $inputs[ 'yt' ] ) &&  is_string( trim( $inputs[ 'yt' ] ) ) && trim( $inputs[ 'yt' ] ) != '' ) {
				$validateInputs[ 'yt' ] = trim( $inputs[ 'yt' ] );
			};
		} else if ( isset( $inputs[ 'reset' ] ) ) {
			$validateInputs = $this->initialOptions[ 'social_links_group' ][ 'social_links' ];
		};

		return $validateInputs;
	}

	public function validateMainSettingsGroup( $inputs ) {
		$validateInputs = array();

		//echo "<pre>".print_r($inputs,true)."</pre>";
		//wp_die();

		if ( isset( $inputs[ 'submit' ] ) ) {
			if ( isset( $inputs[ 'activity_status' ] ) && $inputs[ 'activity_status' ] === 'on' ) {
				$validateInputs[ 'activity_status' ] = $inputs[ 'activity_status' ];
			};
		} else if ( isset( $inputs[ 'reset' ] ) ) {
			$validateInputs = $this->initialOptions[ 'main_settings_group' ][ 'main_settings' ];
		};

		return $validateInputs;
	}
}

_OWPST_jdvu__adminOptionsPanel::init( array() );