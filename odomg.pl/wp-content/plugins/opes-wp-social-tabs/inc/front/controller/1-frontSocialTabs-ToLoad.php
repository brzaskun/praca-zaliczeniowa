<?php

# Here you can include the 'model' and 'view' files - not other controllers! All controllers are loaded automatically.

class _OWPST_jdvu__FrontSocialTabs {

	private $optionsPanelSlug;

	private $options = array(
		'main_settings' 	=> '',
		'social_links' 		=> ''
	);

	private function __construct( $params ) {
		global $_OWPST_jdvu__InitData;

		$this->optionsPanelSlug 			= $_OWPST_jdvu__InitData->plugin_short_slug . '_options';
		$this->options[ 'main_settings' ] 	= get_option( $this->optionsPanelSlug . '_main_settings' );
		$this->options[ 'social_links' ] 	= get_option( $this->optionsPanelSlug . '_social_links' );

		if ( isset( $this->options[ 'main_settings' ][ 'activity_status' ] ) && $this->options[ 'main_settings' ][ 'activity_status' ] == 'on' ) {

			if ( isset( $this->options[ 'social_links' ][ 'fb' ] ) || isset( $this->options[ 'social_links' ][ 'gp' ] ) || isset( $this->options[ 'social_links' ][ 'yt' ] ) ) {
				add_action( 'wp_enqueue_scripts', array( $this , 'enqueueSocialTabsScripts' ) );
				add_action( 'wp_enqueue_scripts', array( $this , 'enqueueSocialTabsStyles' ) );
			}
		}
	}

	public static function init( $params ) {
		return new _OWPST_jdvu__FrontSocialTabs( $params );
	}

	public function enqueueSocialTabsScripts() {
		wp_enqueue_script( 'opes-wp-social-tabs' , __OWPST_jdvu__THIS_PLUGIN__FRONT_URL_ . 'assets' . __OWPST_jdvu__PS_ . 'js'.__OWPST_jdvu__PS_.'script-front.js' , array( 'jquery' ) , __OWPST_jdvu__THIS_PLUGIN__VERSION_ , false );

		$social_tabs_html_new = '';

		$fromTop = 100;
		$tabID = 1;

		if ( isset( $this->options[ 'social_links' ][ 'fb' ] ) 
			//&& 1==2
			) {
			$iframe = '';

			$social_tabs_html_new .= '<div class="opes-wp-social-tab tab-'.$tabID.'" id="opes-wp-social-tab-fb" style="position: fixed; right: -360px; top:'.$fromTop.'px; width: 360px;">';

				$social_tabs_html_new .= '<div class="hover-tab" id="hover-tab-fb" style="position: absolute; top: 0px; left: -35px; height: 109px; float: left; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"><img src="'.__OWPST_jdvu__THIS_PLUGIN__FRONT_URL_.'assets/images/fb-tab.png" style="width: 35px; height: 109px; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"></div>';

				$social_tabs_html_new .= '<div class="content-tab" id="content-tab-fb" style="float: left; postion: relative; width: 360px; /*height: 100%;*/ overflow: visible; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;">';

					$iframe = '<iframe src="https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2F'.$this->options[ 'social_links' ][ 'fb' ].'&amp;width=360&amp;connections=36&amp;stream=false&amp;header=true&amp;height=630" scrolling="no" frameborder="0" style="border: none; background: #fff; overflow: hidden; width: 360px; height: 630px;" allowtransparency="true"></iframe>';

					$social_tabs_html_new .= '<div class="iframe-content" id="iframe-content-fb" style="position: absolute;">'.$iframe.'</div>';

				$social_tabs_html_new .= '</div>';

			$social_tabs_html_new .= '</div>';

			$tabID++;
			$fromTop = $fromTop + 109;
		}

		if ( isset( $this->options[ 'social_links' ][ 'gp' ] ) ) {
			$iframe = '';

			$social_tabs_html_new .= '<div class="opes-wp-social-tab tab-'.$tabID.'" id="opes-wp-social-tab-gp" style="position: fixed; right: -360px; top:'.$fromTop.'px; width: 360px;">';

				$social_tabs_html_new .= '<div class="hover-tab" id="hover-tab-gp" style="position: absolute; top: 0px; left: -35px; height: 109px; float: left; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"><img src="'.__OWPST_jdvu__THIS_PLUGIN__FRONT_URL_.'assets/images/gp-tab.png" style="width: 35px; height: 109px; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"></div>';

				$social_tabs_html_new .= '<div class="content-tab" id="content-tab-gp" style="float: left; postion: relative; width: 360px; /*height: 100%;*/ overflow: visible; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;">';

					$iframe = '<div class="g-'.$this->options[ 'social_links' ][ 'gp_g_type' ].'" data-width="360" data-href="https://plus.google.com/'.$this->options[ 'social_links' ][ 'gp' ].'" data-layout="portrait" data-theme="light" data-rel="publisher" data-showtagline="true" data-showcoverphoto="true"></div>';

					$iframe .= '<script type="text/javascript">'."window.___gcfg = {lang: 'pl'};(function() {var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;po.src = 'https://apis.google.com/js/platform.js';var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);})();</script>";

					$social_tabs_html_new .= '<div class="iframe-content" id="iframe-content-gp" style="position: absolute;">'.$iframe.'</div>';

				$social_tabs_html_new .= '</div>';

			$social_tabs_html_new .= '</div>';

			$tabID++;
			$fromTop = $fromTop + 109;
		}

		if ( isset( $this->options[ 'social_links' ][ 'yt' ] ) ) {
			$iframe = '';

			$social_tabs_html_new .= '<div class="opes-wp-social-tab tab-'.$tabID.'" id="opes-wp-social-tab-yt" style="position: fixed; right: -360px; top:'.$fromTop.'px; width: 360px;">';

				$social_tabs_html_new .= '<div class="hover-tab" id="hover-tab-yt" style="position: absolute; top: 0px; left: -35px; height: 109px; float: left; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"><img src="'.__OWPST_jdvu__THIS_PLUGIN__FRONT_URL_.'assets/images/yt-tab.png" style="width: 35px; height: 109px; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;"></div>';

				$social_tabs_html_new .= '<div class="content-tab" id="content-tab-yt" style="float: left; postion: relative; width: 360px; /*height: 100%;*/ overflow: visible; margin-top: 0px; margin-bottom: 0px; margin-right: 0px; margin-left: 0px; padding-top: 0px; padding-bottom: 0px; padding-right: 0px; padding-left: 0px;">';

					$iframe = '<iframe frameborder="0" id="fr" scrolling="no" src="http://www.youtube.com/subscribe_widget?p='.$this->options[ 'social_links' ][ 'yt' ].'" style="overflow: hidden; height: 72px; width: 360px;border: 0"></iframe>';

					$social_tabs_html_new .= '<div class="iframe-content" id="iframe-content-yt" style="position: absolute;">'.$iframe.'</div>';

				$social_tabs_html_new .= '</div>';

			$social_tabs_html_new .= '</div>';

			$tabID++;
			$fromTop = $fromTop + 150;			
		}

		wp_localize_script( 'opes-wp-social-tabs' , 'social_tabs_html' , $social_tabs_html_new );
	}

	public function enqueueSocialTabsStyles() {
		wp_enqueue_style( 'opes-wp-social-tabs' , __OWPST_jdvu__THIS_PLUGIN__FRONT_URL_ . 'assets/css/style-front.css' , array() , __OWPST_jdvu__THIS_PLUGIN__VERSION_ , 'all' );
	}
}

_OWPST_jdvu__FrontSocialTabs::init( $params );