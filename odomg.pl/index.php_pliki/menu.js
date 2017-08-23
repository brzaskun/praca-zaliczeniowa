;(function ($) {
	"use strict";

/* ================================
   RWD Navigation
   ================================ */

$.fn.rwdNav = function(options) {

	var settings = $.extend({
		subMenu: ".sub-menu",
		hasSubMenu: ".menu-item-has-children",
		awningButton: ".masternav__awning__button",
		animationSpeed: 300,
		hideSubMenuDelay: 300, //0 for closing immediately
		wordpressSupport: false,
		forceTouchScreen: false, // force touch screen
	}, options);

	// fix Modernizr problem with touch screens
	if ( window.navigator.userAgent.indexOf("Windows Phone") > 0 )
		$("html").addClass("touch");

	if ( settings.forceTouchScreen )
		$("html").addClass("touch");

	var $nav_menu = this,
		$nav_sub_menus = $(settings.subMenu, $nav_menu),
		$nav_has_sub_menus = $(settings.hasSubMenu, $nav_menu);

	if ( settings.wordpressSupport )
		$nav_sub_menus.before("<button class=\"show-sub-menu icon-angle-down\"></button>");

	function closeNav() {
		$(this).removeClass("open").slideUp({duration:settings.animationSpeed});;
	}

	function openNav() {
		$(this).addClass("open").slideDown({duration:settings.animationSpeed});
	}

	// Open/close on click (touch screens) and focus on "tab"
	$(".show-sub-menu", $nav_menu).each(function(i, element){
		var $submenu = $(element).siblings(settings.subMenu);
		$(element).on({
			click : function (e) {
				if ( $("html").hasClass("touch") )
					if( $submenu.hasClass("open") )
						closeNav.call($submenu);
					else {
						closeNav.call($nav_has_sub_menus.not($submenu.parents(settings.hasSubMenu)).find(settings.subMenu));
						openNav.call($submenu);
					}
			},
			keyup : function (e) {
				openNav.call($submenu);
			}
		});
	});

	// Close submenus when clicking outside menu
	$(window).on("click", function (e) {
		if ($(e.target).parents($nav_menu.selector).length == 0)
			closeNav.call($nav_sub_menus);
	});

	// Open/close on mouseenter/mouseleave (standard screens)
	if (!$("html").hasClass("touch")){
		$nav_has_sub_menus.each(function(index, element){
			var hover_timer;
			var $submenu = $("> " + settings.subMenu, element);
			$(element).on({mouseenter : function () {
				clearTimeout(hover_timer);
				if(!$submenu.hasClass("open"))
					openNav.call($submenu);
			}, mouseleave : function () {
				hover_timer = setTimeout(function () {
					closeNav.call($submenu);
				}, settings.hideSubMenuDelay);
			}});
		});
	}

	// Close after last "tab"
	$nav_sub_menus.each(function(index, element){
		$(element).find("> li:last-child > a").on("blur", function () {
			closeNav.call(element);
		});
	});

	// Open/close menu for smartphones
	$(settings.awningButton).on("click", function () {
		$nav_menu.slideToggle(settings.animationSpeed);
	});

	return this;
}

})(jQuery);