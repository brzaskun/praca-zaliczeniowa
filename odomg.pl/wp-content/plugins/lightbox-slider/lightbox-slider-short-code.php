<?php
/**
 * Lightbox Slider Shortcode
 */
add_shortcode( 'LBS', 'light_box_slider_short_code' );
function light_box_slider_short_code() {
	ob_start();

    /**
     * Load Lightbox Slider Settings
     */
    $LBS_Settings  = unserialize( get_option("WL_LBS_Settings") );
    if(count($LBS_Settings)) {
        $LBS_Hover_Animation     = $LBS_Settings['LBS_Hover_Animation'];
        $LBS_Gallery_Layout      = $LBS_Settings['LBS_Gallery_Layout'];
        $LBS_Hover_Color         = $LBS_Settings['LBS_Hover_Color'];
        $LBS_Hover_Color_Opacity = 1;
        $LBS_Font_Style          = $LBS_Settings['LBS_Font_Style'];
        $LBS_Image_View_Icon     = $LBS_Settings['LBS_Image_View_Icon'];
		$LBS_Gallery_Title       = $LBS_Settings['LBS_Gallery_Title'];
    } else {
		$LBS_Hover_Color_Opacity = 1;
		$LBS_Hover_Animation     = "flow";
        $LBS_Gallery_Layout      = "col-md-6";
        $LBS_Hover_Color         = "#74C9BE";
        $LBS_Font_Style          = "Arial";
        $LBS_Image_View_Icon     = "fa-picture-o";
		$LBS_Gallery_Title = "yes";
    }
	if(!function_exists('lbs_hex2rgb')) {
		function lbs_hex2rgb($hex) {
		   $hex = str_replace("#", "", $hex);

		   if(strlen($hex) == 3) {
			  $r = hexdec(substr($hex,0,1).substr($hex,0,1));
			  $g = hexdec(substr($hex,1,1).substr($hex,1,1));
			  $b = hexdec(substr($hex,2,1).substr($hex,2,1));
		   } else {
			  $r = hexdec(substr($hex,0,2));
			  $g = hexdec(substr($hex,2,2));
			  $b = hexdec(substr($hex,4,2));
		   }
		   $rgb = array($r, $g, $b);

		   return $rgb; // returns an array with the rgb values
		}
	}
    $RGB = lbs_hex2rgb($LBS_Hover_Color);
    $HoverColorRGB = implode(", ", $RGB);
    ?>

    <script>
        jQuery.browser = {};
        (function () {
            jQuery.browser.msie = false;
            jQuery.browser.version = 0;
            if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
                jQuery.browser.msie = true;
                jQuery.browser.version = RegExp.$1;
            }
        })();
    </script>

    <style>
    .b-link-fade .b-wrapper, .b-link-fade .b-top-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-flow .b-wrapper, .b-link-flow .b-top-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-stroke .b-top-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-stroke .b-bottom-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }

    .b-link-box .b-top-line{

        border: 16px solid rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-box .b-bottom-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-stripe .b-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-apart-horisontal .b-top-line, .b-link-apart-horisontal .b-top-line-up{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);

    }
    .b-link-apart-horisontal .b-bottom-line, .b-link-apart-horisontal .b-bottom-line-up{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-apart-vertical .b-top-line, .b-link-apart-vertical .b-top-line-up{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-apart-vertical .b-bottom-line, .b-link-apart-vertical .b-bottom-line-up{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }
    .b-link-diagonal .b-line{
        background: rgba(<?php echo $HoverColorRGB; ?>, <?php echo $LBS_Hover_Color_Opacity; ?>);
    }

    .b-wrapper{
        font-family:<?php echo str_ireplace("+", " ", $LBS_Font_Style); ?>; // real name pass here
    }
	.enigma_home_portfolio_caption h3{
	font-family:<?php echo str_ireplace("+", " ", $LBS_Font_Style); ?>; // real name pass here
    
	}
	img {
	max-width:1600px !important;
	}
	.row {
	margin-right: 0;
	margin-left: 0;
	}
    </style>

    <?php
    /**
     * Load All Image Gallery Custom Post Type
     */
    $IG_CPT_Name = "lightbox-slider";
    $IG_Taxonomy_Name = "category";
	$all_posts = wp_count_posts( 'lightbox-slider')->publish;
    $AllGalleries = array( 'post_type' => $IG_CPT_Name, 'orderby' => 'ASC','posts_per_page' =>$all_posts);
    $loop = new WP_Query( $AllGalleries );
    ?>
    <div id="gallery1" class="gal-container">
		<?php while ( $loop->have_posts() ) : $loop->the_post();?>
			<!--get the post id-->
			<?php $post_id = get_the_ID(); ?>
			<div id="<?php echo get_the_title($post_id); ?>" style="display: block; overflow:hidden; padding-bottom:20px;">
					<?php if($LBS_Gallery_Title==""){ $LBS_Gallery_Title == "yes"; } if($LBS_Gallery_Title == "yes") { ?>
				<!-- lbs gallery title-->
				<div style="font-weight: bolder;padding-bottom:20px;border-bottom:2px solid #f2f2f2;text-align:center ;margin-bottom: 20px;font-size:16px;">
					<?php echo ucwords(get_the_title($post_id)); ?>
				</div>
				<?php } ?>
				<!--lbs gallery photos-->
				<div>
					<div class="row">
					<?php

						/**
						 * Get All Photos from Gallery Post Meta
						 */
						$lbs_all_photos_details = unserialize(get_post_meta( get_the_ID(), 'lbs_all_photos_details', true));
						$TotalImages =  get_post_meta( get_the_ID(), 'lbs_total_images_count', true );
						$i = 1;

						foreach($lbs_all_photos_details as $lbs_single_photos_detail) {
							$name = $lbs_single_photos_detail['lbs_image_label'];
							$url = $lbs_single_photos_detail['lbs_image_url'];
						?>
							
							
							<div class="<?php echo $LBS_Gallery_Layout; ?>  wl-gallery" >
								<div style="box-shadow: 0 0 6px rgba(0,0,0,.7);">
									<div class="b-link-<?php echo $LBS_Hover_Animation; ?> b-animate-go">

										<img src="<?php echo $url; ?>" class="gall-img-responsive">

										<div class="b-wrapper">
											<p class="b-scale b-animate b-delay03">
												<a href="<?php echo $url; ?>" data-lightbox="image" title="<?php echo ucwords($name); ?>" class="hover_thumb">
													<i class="fa <?php echo $LBS_Image_View_Icon; ?> fa-4x"></i>
												</a>
											</p>
										</div>

									</div>
									<?php 
									if($name)
									{
									?>
										<div class="enigma_home_portfolio_caption">
											<h3><?php echo ucwords($name); ?></h3>
										</div>
									<?php 
									}
									?>		
								</div>
							</div>
							<?php if($LBS_Gallery_Layout=="col-md-4")
							{
								 if($i%3==0){
								?>
									</div>
									<div class="row">
									<?php
								}
							}
							else{
							 if($i%2==0){
								?>
									</div>
									<div class="row">
									<?php
								}
							}
							$i++;
						}
					?>
					</div>
				</div>
			</div>
		<?php endwhile; ?>
    </div>
    <?php wp_reset_query(); ?>
    <?php
	return ob_get_clean();
}
?>