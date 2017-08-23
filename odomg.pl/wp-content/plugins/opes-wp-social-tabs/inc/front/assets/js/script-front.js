jQuery( function() {

	jQuery( 'body' ).prepend( social_tabs_html );

	jQuery( 'body' ).find( '.opes-wp-social-tab' ).hover(
		function() {
			var this_tab = this;

			var tab_id = jQuery( this_tab ).attr( 'id' );
			jQuery( '.opes-wp-social-tab' ).each( function( i , el ) {
				if ( jQuery( el ).attr( 'id' ) == tab_id ) {
					jQuery( el ).css( { zIndex: '999999' } );
				} else {
					jQuery( el ).css( { zIndex: '99999' } );
				}
			});

			jQuery( this_tab ).stop().animate( { right: '0px' } , 800 );
		},
		function() {
			var this_tab = this;

			jQuery( this_tab ).stop().animate( { right: '-360px' } , 400 );
		}
	);
	
});