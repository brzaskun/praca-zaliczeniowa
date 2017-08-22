<!DOCTYPE html>
<!--[if lt IE 7]>  <html class="no-js lt-ie9 lt-ie8 lt-ie7" <?php language_attributes(); ?>> <![endif]-->
<!--[if IE 7]>     <html class="no-js lt-ie9 lt-ie8" <?php language_attributes(); ?>> <![endif]-->
<!--[if IE 8]>     <html class="no-js lt-ie9" <?php language_attributes(); ?>> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" <?php language_attributes(); ?>> <!--<![endif]-->
    <head>
        <meta charset="<?php bloginfo('charset'); ?>">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><?php wp_title('|', true, 'right'); ?></title>
        <meta name="viewport" content="width=device-width">

        <link rel="profile" href="http://gmpg.org/xfn/11">
        <link rel="pingback" href="<?php bloginfo('pingback_url'); ?>">

		<?php
		$header_style = '';
		$header_image = get_header_image();
		if ($header_image != '') {
			$header_style = "background-image: url('" . $header_image . "');";
		}
		?>

		<?php wp_head(); ?>
    </head>
    <body <?php body_class(); ?>>
        <!--[if lt IE 8]>
                <p class="chromeframe">
		<?php _e('You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.', 'omaha'); ?>
		</p>
        <![endif]-->
		<div id="header" style="<?php echo $header_style; ?>">
			<div class="container page-container">
				<header role="banner">
					<div class="row row-with-vspace site-branding">
                    
                    
                    
                    <div class="col-md-6 site-title">
							<h1 class="site-title-heading">
								<h5 class="brand"><a href="http://odomg.pl" ><img src="wp-content/uploads/2015/01/Logo_ we_know_how.jpg" alt="" title="powrót do strony głównej" border="0" /></a></h5>
							</h1>
							<!--                        <div class="site-description">
							<small>
							<?php bloginfo('description'); ?> 
							 </small>
							 </div>-->
						</div>
						<div class="col-md-6 page-header-top-right">
							<div class="sr-only">
								<a href="#content" title="<?php esc_attr_e('Skip to content', 'omaha'); ?>"><?php _e('Skip to content', 'omaha'); ?></a>
							</div>
							<?php if (is_active_sidebar('header-right')) { ?> 
								<div class="pull-right">
									<?php dynamic_sidebar('header-right'); ?> 
								</div>
								<div class="clearfix"></div>
							<?php } // endif; ?> 
						</div>
					</div><!--.site-branding-->
				</header>
			</div>
		</div>
		<div class="bg-shadow">
			<div class="container page-container">
				<header role="banner">
					<div class="row main-navigation">
						<div class="col-md-12">
							<nav class="navbar navbar-omaha" role="navigation">
								<div class="navbar-header">
									<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-primary-collapse">
										<span class="sr-only"><?php _e('Toggle navigation', 'omaha'); ?></span>
										<span class="icon-bar"></span>
										<span class="icon-bar"></span>
										<span class="icon-bar"></span>
									</button>
								</div>
								<?php
								wp_nav_menu(array(
									'theme_location' => 'primary',
									'depth' => 2,
									'container' => 'div',
									'container_class' => 'collapse navbar-collapse navbar-primary-collapse',
									'menu_class' => 'nav navbar-nav',
									'fallback_cb' => 'wp_bootstrap_navwalker::fallback',
									'walker' => new wp_bootstrap_navwalker()
								));
								?> 
                                
                                
							</nav>
                            
						</div>
					</div><!--.main-navigation-->
				
		


 
 <link rel="stylesheet" href="index.php_pliki/normalize.css">
 
  <!-- kwick -->
<link rel="stylesheet" type="text/css" media="all" href="index.php_pliki/kwick.css">
 <!-- menu --> 
<link rel="stylesheet" href="index.php_pliki/menu.css">
  <!-- glowne -->
 <link rel="stylesheet" href="index.php_pliki/main.css">


 


 


<!-- <script src="js/modernizr.js"></script> -->
  
</head>

<body>
        <!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

<div id="main_body">
	<header class="fjustify">
		
	</header>

	
			


		<div style="width:100%;background:url(kwick/kwik_boki.jpg) no-repeat top center;height:366px;">
			<section id="slider-wrapper" style="margin-top:-1px;">
			
			<div class="container">
			<span style="background: url(kwick/kwick-item-shadow.png) no-repeat scroll left top rgba(0, 0, 0, 0);
    height: 366px;position: absolute;left: -25px;top: 4;width: 25px;z-index: 9999;"></span>
    
			  <ul style="width: 999px; height: 374px;" class="kwicks horizontal">
              
                <li class="" style="left: 0px; margin: 0px; position: absolute; width: 193.527px;">
					<span class="shadow"></span>
					<div class="thumb">
						<div class="bg" style="background-image: url(kwick/audyt.jpg)"></div>
					</div>			
					<span class="colorImage" style="background-image: url(&quot;kwick/audyt-color.jpg&quot;); display: none;"></span>
					<div class="desc color-1">
						<a href="audyt">
							<div class="left-bg">
								<h2>Audyt</h2>					
							</div>s
						</a>
					</div>
				</li>	
				<li class="active" style="left: 188.527px; margin: 0px; position: absolute; width: 193.527px; overflow: hidden;">
					<span class="shadow"></span>
					<div class="thumb">
						<div class="bg" style="background-image:url(kwick/dokumentacja.jpg)"></div>
					</div>			
					<span class="colorImage" style="background-image: url(&quot;kwick/dokumentacja-color.jpg&quot;); display: none;"></span>
					<div class="desc color-2">
						<a href="dokumentacja">
							<div class="left-bg">
								<h2>Dokumentacja</h2>					
							</div>
						</a>
					</div>
				</li>	
				<li class="" style="left: 434.419px; margin: 0px; position: absolute; width: 193.527px;">
					<span class="shadow"></span>
					<div class="thumb">
						<div class="bg" style="background-image:url(kwick/outsourcing.jpg)"></div>
					</div>			
					<span class="colorImage" style="background-image: url(&quot;kwick/outsourcing-color.jpg&quot;); display: none;"></span>
					<div class="desc color-3">
						<a href="outsourcing-abi">
							<div class="left-bg">
								<h2>Outsourcing ABI</h2>					
							</div>
						</a>
					</div>
					
				</li>	

				<li class="" style="left: 622.946px; margin: 0px; position: absolute; width: 193.527px;">
					<span class="shadow"></span>
					<div class="thumb">
						<div class="bg" style="background-image:url(kwick/szkolenie.jpg)"></div>
					</div>			
					<span class="colorImage" style="background-image: url(&quot;kwick/szkolenie-color.jpg&quot;); display: none;"></span>
					<div class="desc color-4">
						<a href="szkolenie">
							<div class="left-bg">
								<h2>Szkolenia</h2>					
							</div>
						</a>
					</div>
				</li>	
				<li class="" style="right: 0px; margin: 0px; position: absolute; width: 193.527px;">
					<span class="shadow"></span>
					<div class="thumb">
						<div class="bg" style="background-image:url(kwick/expert.jpg)"></div>
					</div>			
					<span class="colorImage" style="background-image: url(&quot;kwick/expert-color.jpg&quot;); display: none;"></span>
					<div class="desc color-5">
						<a href="zapytaj-eksperta">
							<div class="left-bg">
								<h2>Zapytaj Eksperta</h2>					
							</div>
						</a>
					</div>
				</li>	
			  </ul>
			</div>
		  </section><!--#slider-->
		</div>

			

			
				
				
				

	
	
</div><!-- main_body -->	
 
 
 
 <script src="index.php_pliki/jquery-1.js"></script><!-- -->
  <!-- menu --> 
<script src="index.php_pliki/menu.js"></script><!--menu  -->
 <!-- kwick -->
<script type="text/javascript" src="index.php_pliki/superfish.js"></script>
<script type="text/javascript" src="index.php_pliki/jquery.js"></script>
<script type="text/javascript" src="index.php_pliki/easyTooltip.js"></script>
<script type="text/javascript" src="index.php_pliki/cufon-replace.js"></script>
<script type="text/javascript" src="index.php_pliki/kwicks-1.js"></script>
 
<script type="text/javascript">
  	// initialise plugins
		jQuery(function(){
			// main navigation init
			jQuery('ul.sf-menu').superfish({
				delay:       1000, 		// one second delay on mouseout 
				animation:   {opacity:'show',height:'show'}, // fade-in and slide-down animation 
				speed:       'normal',  // faster animation speed 
				onHide		: function(){Cufon.refresh()}
			});
			
			// prettyphoto init
			jQuery("a[rel^='prettyPhoto']").prettyPhoto({
				animation_speed:'normal',
				theme:'facebook',
				slideshow:5000,
				autoplay_slideshow: false
			});
			
			// easyTooltip init
			jQuery("a.tooltip, .social-networks li a").easyTooltip();
			
			//kwicks image hover
			jQuery(".kwicks.horizontal li").hover(function(){
				jQuery(this).find(".colorImage").fadeIn();
			},function(){
				jQuery(this).find(".colorImage").fadeOut();
			});
			
			//kwicks excerpt hover
			jQuery(".kwicks.horizontal li").hover(function(){
				jQuery(this).find(".excerpt").stop().animate({right:"75px"},"slow");
			},function(){
				jQuery(this).find(".excerpt").stop().animate({right:"-280px"},"medium");
			});
			
			//kwicks button hover
			jQuery(".kwicks.horizontal li").hover(function(){
				jQuery(this).find(".kwick-button").stop().animate({bottom:"5px"},"slow");
			},function(){
				jQuery(this).find(".kwick-button").stop().animate({bottom:"-24px"},"medium");
			});
			
			jQuery(".patient-sidebar .widget.widget_links li:nth-child(5n)").addClass("border");
			
			jQuery("body.archive.author").find("#sidebar").removeClass("maxheight");
			
		});
		
		// Init for audiojs
		audiojs.events.ready(function() {
			var as = audiojs.createAll();
		});
  </script>
  
  <script type="text/javascript">
		jQuery(document).ready(function() {
			//kwicks begin
			jQuery('.kwicks').kwicks({
				spacing : 1,
				sticky : false,
				event : 'mouseover',
				max : 540
			});
		});
	</script>

 <!-- jssor -->
<script type="text/javascript" src="index.php_pliki/jssor.js"></script>

	<script>
        jQuery(document).ready(function ($) {
            var options = {
                $AutoPlay: true,
				$Loop: 1,
                $AutoPlaySteps: 1,
                $AutoPlayInterval: 0,
                $PauseOnHover: 12,
                $ArrowKeyNavigation: true,
                $SlideEasing: $JssorEasing$.$EaseLinear,
                $SlideDuration: 5000,
                $MinDragOffsetToSlide: 20,
                $SlideWidth: 270,
                //$SlideHeight: 100, 
                $SlideSpacing: 20,
                $DisplayPieces: 7,
                $ParkingPosition: 0,
                $UISearchMode: 1,
                $PlayOrientation: 1,
                $DragOrientation: 1
            };

            var jssor_slider1 = new $JssorSlider$("slider1_container", options);
            function ScaleSlider() {
                var bodyWidth = document.body.clientWidth;
                if (bodyWidth)
                    jssor_slider1.$ScaleWidth(Math.min(bodyWidth, 800));
                else
                    window.setTimeout(ScaleSlider, 30);
            }
            ScaleSlider();
            if (!navigator.userAgent.match(/(iPhone|iPod|iPad|BlackBerry|IEMobile)/)) {
                $(window).bind('resize', ScaleSlider);
            }
        });
    </script>	
            
		</div>
        
        
		<div class="container page-container">

			<div id="content" class="row row-with-vspace site-content">
            
            