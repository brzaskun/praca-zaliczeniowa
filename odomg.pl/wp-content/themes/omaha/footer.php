<?php
/**
 * The theme footer
 * 
 * @package omaha
 */
?>

</div><!--.site-content-->
</div><!--.container page-container-->

<div id="footer-wrapper">
    <div class="container page-container">
        <footer id="site-footer" role="contentinfo">
        <img src="kwick/dzialy_.gif" />
            <div id="footer-row" class="row site-footer">
               
               <div>&nbsp;</div>
               <center><a href="kwick/regulamin sude.pdf"><span style="color: #f26d2a; font-family: Verdana;font-weight:300; font-size: 12px;">Regulamin ŚUDE&nbsp; </span></a><a href="kwick/COOKIES.pdf"><span style="color: #f26d2a; font-family: Verdana; font-weight:300;font-size: 12px;">Regulamin Cookies</span></a></center>
               
				 <div class="col-md-3 footer-left">
					<?php
					dynamic_sidebar('footer-left');
					?> 
                </div>
                
                
                <div class="col-md-3 footer-right">
					<?php dynamic_sidebar('footer-right'); ?> 
                </div>
                
                <div class="col-md-3 footer-center" >
					<?php dynamic_sidebar('footer-center'); ?> 
                </div>
                
                
                <div class="col-md-3 footer-right-2" >
					<?php dynamic_sidebar('footer-right-2'); ?> 
                </div>
                
                <div class="col-md-12 footer-bottom" >
					<?php dynamic_sidebar('footer-bottom'); ?> 
                </div>
                
                
            </div>
            </div>
        </footer>
    </div><!--.container page-container-->
</div>









<!--wordpress footer-->
<?php wp_footer(); ?> 


</body>
</html>