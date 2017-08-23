<?php
/**
 * Plugin Name: Lightbox Slider
 * Version: 1.4
 * Description: A Great Lightbox plugin to create and display various types of images galleries on WordPress blog.
 * Author: Weblizar
 * Author URI: http://www.weblizar.com
 * Plugin URI: http://weblizar.com/
 */

/**
 * Constant Variable
 */
define("WEBLIZAR_LBS_TEXT_DOMAIN","weblizar_lbs" );
define("WEBLIZAR_LBS_PLUGIN_URL", plugin_dir_url(__FILE__));

/**
 * Run 'Install' script on plugin activation
 */
register_activation_hook( __FILE__, 'LBS_DefaultSettings' );
function LBS_DefaultSettings(){
    $LBS_DefaultSettingsArray = serialize( array(
        'LBS_Hover_Animation' => "fade",
        'LBS_Gallery_Layout' => "col-md-6",
        'LBS_Hover_Color' => "#74C9BE",
        'LBS_Hover_Color_Opacity' => 1,
        'LBS_Font_Style' => "Arial",
        'LBS_Image_View_Icon' => "fa-picture-o",
		'LBS_Gallery_Title' => "yes" 
    ) );
    add_option("WL_LBS_Settings", $LBS_DefaultSettingsArray);
}



//Get Ready Plugin Translation
add_action('plugins_loaded', 'LBS_GetReadyTranslation');
function LBS_GetReadyTranslation() {
	load_plugin_textdomain(WEBLIZAR_LBS_TEXT_DOMAIN, FALSE, dirname( plugin_basename(__FILE__)).'/languages/' );
}

// Register Custom Post Type
function LBS_CPT_Function() {

    $labels = array(
        'name'                => _x( 'Lightbox Slider', 'Lightbox Slider', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'singular_name'       => _x( 'Lightbox Slider', 'Lightbox Slider', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'menu_name'           => __( 'Lightbox Slider', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'parent_item_colon'   => __( 'Parent Item:', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'all_items'           => __( 'All Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'view_item'           => __( 'View Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'add_new_item'        => __( 'Add New Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'add_new'             => __( 'Add New Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'edit_item'           => __( 'Edit Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'update_item'         => __( 'Update Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'search_items'        => __( 'Search Gallery', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'not_found'           => __( 'No Gallery Found', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'not_found_in_trash'  => __( 'No Gallery found in Trash', WEBLIZAR_LBS_TEXT_DOMAIN ),
    );
    $args = array(
        'label'               => __( 'Lightbox Slider', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'description'         => __( 'Lightbox Slider', WEBLIZAR_LBS_TEXT_DOMAIN ),
        'labels'              => $labels,
        'supports'            => array( 'title', '', '', '', '', '', '', '', '', '', '', ),
        //'taxonomies'          => array( 'category', 'post_tag' ),
        'hierarchical'        => false,
        'public'              => false,
        'show_ui'             => true,
        'show_in_menu'        => true,
        'show_in_nav_menus'   => false,
        'show_in_admin_bar'   => false,
        'menu_position'       => 5,
        'menu_icon'           => 'dashicons-format-gallery',
        'can_export'          => true,
        'has_archive'         => true,
        'exclude_from_search' => false,
        'publicly_queryable'  => false,
        'capability_type'     => 'page',
    );
    register_post_type( 'lightbox-slider', $args );

}

// Hook into the 'init' action
add_action( 'init', 'LBS_CPT_Function', 0 );


/**
 * Add Meta Box & load required CSS and JS for interface
 */
add_action('admin_init','Lightbox_Slider_init');
function Lightbox_Slider_init() {
    add_meta_box('Lightbox_Slider_meta', __('Add New Images', WEBLIZAR_LBS_TEXT_DOMAIN), 'lightbox_slider_function', 'lightbox-slider', 'normal', 'high');
    
	add_action('save_post','light_box_meta_save');
	add_meta_box(__('Plugin Shortcode', WEBLIZAR_LBS_TEXT_DOMAIN) , __('Plugin Shortcode', WEBLIZAR_LBS_TEXT_DOMAIN), 'lbs_plugin_shortcode', 'lightbox-slider', 'side', 'low');
	add_meta_box(__('Rate us on WordPress', WEBLIZAR_LBS_TEXT_DOMAIN) , __('Rate us on WordPress', WEBLIZAR_LBS_TEXT_DOMAIN), 'lbs_rate_us_function', 'lightbox-slider', 'side', 'low');
    
	add_meta_box(__('Upgrade To Pro Version', WEBLIZAR_LBS_TEXT_DOMAIN) , __('Upgrade To Pro Version', WEBLIZAR_LBS_TEXT_DOMAIN), 'lbs_upgrade_to_pro_function', 'lightbox-slider', 'side', 'low');
    add_meta_box(__('Pro Features', WEBLIZAR_LBS_TEXT_DOMAIN) , __('Pro Features', WEBLIZAR_LBS_TEXT_DOMAIN), 'lbs_pro_features', 'lightbox-slider', 'side', 'low');
	wp_enqueue_script('theme-preview');
    wp_enqueue_script('lbs-media-uploads',WEBLIZAR_LBS_PLUGIN_URL.'js/lbs-media-upload-script.js',array('media-upload','thickbox','jquery'));
    wp_enqueue_style('dashboard');
    wp_enqueue_style('lbs-meta-css', WEBLIZAR_LBS_PLUGIN_URL.'css/rpg-meta.css');
    wp_enqueue_style('thickbox');
}

/**
plugin shortcode
**/
function lbs_plugin_shortcode(){
?>
<p>Use below shortcode in any Page/Post to publish your Lightbox Slider</p>
		<input readonly="readonly" type="text" value="<?php echo "[LBS]"; ?>"> 
<?php
} 

/**
Rate us 
**/

function lbs_rate_us_function(){
?>
<div style="text-align:center">
<h3>If you like our plugin then please show us some love </h3>

<style>
.wrg-rate-us span.dashicons{
width: 30px;
height: 30px;
}
.wrg-rate-us span.dashicons-star-filled:before {
content: "\f155";
font-size: 30px;
}
</style>

<a class="wrg-rate-us" style="text-align:center; text-decoration: none;font:normal 30px/l;" href="https://wordpress.org/plugins/lightbox-slider/" target="_blank">
			<span class="dashicons dashicons-star-filled"></span>
			<span class="dashicons dashicons-star-filled"></span>
			<span class="dashicons dashicons-star-filled"></span>
			<span class="dashicons dashicons-star-filled"></span>
			<span class="dashicons dashicons-star-filled"></span>
		</a>
		<div class="upgrade-to-pro-demo" style="text-align:center;margin-bottom:10px;margin-top:10px;">
	<a href="https://wordpress.org/plugins/lightbox-slider/" target="_new" class="button button-primary button-hero">Click Here</a>
</div>
		</div>
<?php
}


/**
 * Meta box interface design
 */
function lightbox_slider_function() {
    $lbs_all_photos_details = unserialize(get_post_meta( get_the_ID(), 'lbs_all_photos_details', true));
    $TotalImages =  get_post_meta( get_the_ID(), 'lbs_total_images_count', true );
    $i = 1;
    ?>
		<style>
			#titlediv #title {
			margin-bottom:15px;
			}
		</style>
    <input type="hidden" id="count_total" name="count_total" value="<?php if($TotalImages==0){ echo 0; } else { echo $TotalImages; } ?>"/>
    <div style="clear:left;"></div>

    <?php
    /* load saved photos into gallery */
    if($TotalImages) {
        foreach($lbs_all_photos_details as $lbs_single_photos_detail) {
            $name = $lbs_single_photos_detail['lbs_image_label'];
            $url = $lbs_single_photos_detail['lbs_image_url'];
            ?>
                <div class="rpg-image-entry" id="rpg_img<?php echo $i; ?>">
					<a class="gallery_remove" href="#gallery_remove" id="rpg_remove_bt<?php echo $i; ?>"onclick="remove_meta_img(<?php echo $i; ?>)"><img src="<?php echo  WEBLIZAR_LBS_PLUGIN_URL.'images/Close-icon.png'; ?>" /></a>
					<img src="<?php echo  $url; ?>" class="rpg-meta-image" alt=""  style="">
					<input type="button" id="upload-background-<?php echo $i; ?>" name="upload-background-<?php echo $i; ?>" value="Upload Image" class="button-primary" onClick="weblizar_image('<?php echo $i; ?>')" />
					<input type="text" id="img_url<?php echo $i; ?>" name="img_url<?php echo $i; ?>" class="rpg_label_text"  value="<?php echo  $url; ?>"  readonly="readonly" style="display:none;" />
					<input type="text" id="image_label<?php echo $i; ?>" name="image_label<?php echo $i; ?>" placeholder="Enter Image Label" class="rpg_label_text" value="<?php echo $name; ?>">
                </div>
            <?php
            $i++;
        } // end of foreach
    } else {
        $TotalImages = 0;
    }
    ?>


    <div id="append_rpg_img">
    </div>
    <div class="rpg-image-entry add_rpg_new_image" onclick="add_rpg_meta_img()">
            <div class="dashicons dashicons-plus"></div>
            <p><?php _e('Add New Image', WEBLIZAR_LBS_TEXT_DOMAIN); ?></p>
    </div>
    <div style="clear:left;"></div>

    <script>
    var rpg_i = parseInt(jQuery("#count_total").val());
    function add_rpg_meta_img() {
        rpg_i = rpg_i + 1;

        var rpg_output = '<div class="rpg-image-entry" id="rpg_img'+ rpg_i +'">'+
                            '<a class="gallery_remove" href="#gallery_remove" id="rpg_remove_bt' + rpg_i + '"onclick="remove_meta_img(' + rpg_i + ')"><img src="<?php echo  WEBLIZAR_LBS_PLUGIN_URL.'images/Close-icon.png'; ?>" /></a>'+
                            '<img src="<?php echo  WEBLIZAR_LBS_PLUGIN_URL.'images/rpg-default.jpg'; ?>" class="rpg-meta-image" alt=""  style="">'+
                            '<input type="button" id="upload-background-' + rpg_i + '" name="upload-background-' + rpg_i + '" value="Upload Image" class="button-primary" onClick="weblizar_image(' + rpg_i + ')" />'+
                            '<input type="text" id="img_url'+ rpg_i +'" name="img_url'+ rpg_i +'" class="rpg_label_text"  value="<?php echo  WEBLIZAR_LBS_PLUGIN_URL.'images/rpg-default.jpg'; ?>"  readonly="readonly" style="display:none;" />'+
                            '<input type="text" id="image_label'+ rpg_i +'" name="image_label'+ rpg_i +'" placeholder="Enter Image Label" class="rpg_label_text"   >'+
                        '</div>';
        jQuery(rpg_output).hide().appendTo("#append_rpg_img").slideDown(500);
        jQuery("#count_total").val(rpg_i);
    }

    function remove_meta_img(id){
        jQuery("#rpg_img"+id).slideUp(600, function(){
            jQuery(this).remove();
        });

        count_total = jQuery("#count_total").val();
        count_total = count_total - 1;
        var id_i= id + 1;

        for(var i=id_i;i<=rpg_i;i++){
            var j = i-1;
            jQuery("#rpg_remove_bt"+i).attr('onclick','remove_meta_img('+j+')');
            jQuery("#rpg_remove_bt"+i).attr('id','rpg_remove_bt'+j);
            jQuery("#img_url"+i).attr('name','img_url'+j);
            jQuery("#image_label"+i).attr('name','image_label'+j);
            jQuery("#img_url"+i).attr('id','img_url'+j);
            jQuery("#image_label"+i).attr('id','image_label'+j);

            jQuery("#rpg_img"+i).attr('id','rpg_img'+j);
        }
        jQuery("#count_total").val(count_total);
        rpg_i = rpg_i - 1;
    }
    </script>
    <?php
}

function lbs_upgrade_to_pro_function(){
?>
<div class="upgrade-to-pro-demo" style="text-align:center;margin-bottom:10px;margin-top:10px;">
	<a href="http://demo.weblizar.com/lightbox-slider-pro-demo/" target="_new" class="button button-primary button-hero">View Live Demo</a>
</div>
<div class="upgrade-to-pro-admin-demo" style="text-align:center;margin-bottom:10px;">
	<a href="http://demo.weblizar.com/lightbox-slider-pro-admin-demo/" target="_new" class="button button-primary button-hero">View Admin Demo</a>
</div>
<div class="upgrade-to-pro" style="text-align:center;margin-bottom:10px;">
	<a href="http://weblizar.com/lightbox-slider-pro/" target="_new" class="button button-primary button-hero">Upgarde To Pro</a>
</div>
<?php
}

function lbs_pro_features(){
	?>

	<ul style="">
				<li class="plan-feature">Responsive Design</li>
				<li class="plan-feature">Gallery Layout</li>
				<li class="plan-feature">Unlimited Hover Color</li>
				<li class="plan-feature">10 Types of Hover Color Opacity</li>
				<li class="plan-feature">All Gallery Shortcode</li>
				<li class="plan-feature">Each Gallery has Unique Shortcode</li>
				<li class="plan-feature">8 Types of Hover Animation</li>
				<li class="plan-feature">5 Types of Gallery Design Layout</li>
				<li class="plan-feature">500+ of Font Style</li>
				<li class="plan-feature">8 types Of Lightbox Integrated</li>
				<li class="plan-feature">Drag and Drop image Position</li>
				<li class="plan-feature">Multiple Image uploader</li>
				<li class="plan-feature">Shortcode Button on post or page</li>
				<li class="plan-feature">Unique settings for each gallery</li>
				<li class="plan-feature">Hide/Show gallery Title and label</li>
				<li class="plan-feature">Font icon Customization</li>
				<li class="plan-feature">Google Fonts</li>
				<li class="plan-feature">Isotope/Masonry Effects</li>
			</ul>
	<?php 
} 

/**
 * Save All Photo Gallery Images
 */
function light_box_meta_save() {
    if(isset($_POST['post_ID'])) {
        $post_ID = $_POST['post_ID'];
        $post_type = get_post_type($post_ID);
        if($post_type == 'lightbox-slider') {
            $TotalImages = $_POST['count_total'];
            $ImagesArray = array();
            if($TotalImages) {
                for($i=1; $i <= $TotalImages; $i++) {
                    $name = stripslashes($_POST['image_label'.$i]);
                    $url = $_POST['img_url'.$i];
                    $ImagesArray[] = array(
                        'lbs_image_label' => $name,
                        'lbs_image_url' => $url
                    );
                }
                update_post_meta($post_ID, 'lbs_all_photos_details', serialize($ImagesArray));
                update_post_meta($post_ID, 'lbs_total_images_count', $TotalImages);
            } else {
                $TotalImages = 0;
                update_post_meta($post_ID, 'lbs_total_images_count', $TotalImages);
                $ImagesArray = array();
                update_post_meta($post_ID, 'lbs_all_photos_details', serialize($ImagesArray));
            }
        }
    }
}


/**
 * Weblizar Lightbox Slider Short Code Detect Function
 * Plugin never load plugin CSS & JS files with all theme pages.
 * CSS & JS files only load on required pages where you use or embed [LBS] shortcode.
 */
function WeblizarLightboxSliderShortCodeDetect() {
    global $wp_query;
    $Posts = $wp_query->posts;
    $Pattern = get_shortcode_regex();

    foreach ($Posts as $Post) {
        if( preg_match_all( '/'. $Pattern .'/s', $Post->post_content, $Matches ) && array_key_exists( 2, $Matches ) && in_array( 'LBS', $Matches[2] ) ) {
            /**
             * js scripts
             */
				wp_enqueue_script('jquery');
				wp_enqueue_script('wl-lbs-hover-pack-js',WEBLIZAR_LBS_PLUGIN_URL.'js/hover-pack.js', array('jquery'));
				wp_enqueue_script('wl-lbs-bootstrap-js',WEBLIZAR_LBS_PLUGIN_URL.'js/bootstrap.min.js', array('jquery'));
				wp_enqueue_script('wl-lbs-lightbox',WEBLIZAR_LBS_PLUGIN_URL.'js/lightbox-2.6.min.js', array('jquery'));

            /**
             * css scripts
             */
				wp_enqueue_style('wl-lbs-hover-pack-css', WEBLIZAR_LBS_PLUGIN_URL.'css/hover-pack.css');
				wp_enqueue_style('wl-lbs-reset-css', WEBLIZAR_LBS_PLUGIN_URL.'css/reset.css');
				wp_enqueue_style('wl-lbs-boot-strap-css', WEBLIZAR_LBS_PLUGIN_URL.'css/bootstrap.css');
				wp_enqueue_style('wl-lbs-img-gallery-css', WEBLIZAR_LBS_PLUGIN_URL.'css/img-gallery.css');

				wp_enqueue_style('wl-lbs-font-awesome-4', WEBLIZAR_LBS_PLUGIN_URL.'css/font-awesome-4.0.3/css/font-awesome.min.css');
				wp_enqueue_style('wl-lbs-lightbox', WEBLIZAR_LBS_PLUGIN_URL.'css/lightbox.css');

            break;
        } //end of if
    } //end of foreach
}
add_action( 'wp', 'WeblizarLightboxSliderShortCodeDetect' );

/**
 * Settings Page for Lightbox Slider
 */
add_action('admin_menu' , 'LBS_SettingsPage');

function LBS_SettingsPage() {
    add_submenu_page('edit.php?post_type=lightbox-slider', __('Settings', WEBLIZAR_LBS_TEXT_DOMAIN), __('Settings', WEBLIZAR_LBS_TEXT_DOMAIN), 'administrator', 'light-box-settings', 'lightbox_slider_settings_page_function');
    add_submenu_page('edit.php?post_type=lightbox-slider', 'Pro Screenshots', 'Pro Screenshots', 'administrator', 'get-image-gallery-pro-plugin', 'get_lightbox_slider_pro_page_function');
}

/**
 * Photo Gallery Settings Page
 */
function lightbox_slider_settings_page_function() {
    //css
	wp_enqueue_script('dashboard');
	wp_enqueue_script('jquery');
	wp_enqueue_style('dashboard');
    wp_enqueue_style('wl-lbs-font-awesome-4', WEBLIZAR_LBS_PLUGIN_URL.'css/font-awesome-4.0.3/css/font-awesome.min.css');
    require_once("lightbox-slider-settings.php");
}

/**
 * Get Lightbox Slider Pro Plugin Page
 */
function get_lightbox_slider_pro_page_function() {
    //css
    wp_enqueue_style('wl-lbs-font-awesome-4', WEBLIZAR_LBS_PLUGIN_URL.'css/font-awesome-4.0.3/css/font-awesome.min.css');
    wp_enqueue_style('wl-lbs-pricing-table-css', WEBLIZAR_LBS_PLUGIN_URL.'css/pricing-table.css');
    wp_enqueue_style('wl-lbs-pricing-table-responsive-css', WEBLIZAR_LBS_PLUGIN_URL.'css/pricing-table-responsive.css');
    wp_enqueue_style('wl-lbs-boot-strap-responsive-min-2-3-css', WEBLIZAR_LBS_PLUGIN_URL.'css/bootstrap.css');
	require_once("get-lightbox-slider-pro.php");
	}


/**
 * Lightbox Slider Short Code [LBS]
 */
require_once("lightbox-slider-short-code.php");
?>