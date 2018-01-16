$(function () {
    'use strict';

    var $sidebar = $('#sidebar'),
        $cart = $('#cart'),
        $cart_button = $('#cart-button'),
        $hamburger_menu = $('#hamburger-menu'),
        $overlay = $('#overlay');

    var _isOpenClass = 'is-open',
        _isVisibleClass = 'is-visible';

    $hamburger_menu.on('click', function(){
        $cart.removeClass(_isOpenClass);
        toggle_panel_visibility($sidebar, $overlay);
    });

    $cart_button.on('click', function(){
        $sidebar.removeClass(_isOpenClass);
        toggle_panel_visibility($cart, $overlay);
    });

    $overlay.on('click', function(){
        $overlay.removeClass(_isVisibleClass);
        $sidebar.removeClass(_isOpenClass);
        $cart.removeClass(_isOpenClass);
    });

    function toggle_panel_visibility ($panel) {
        if( $panel.hasClass(_isOpenClass) ) {
            $panel.removeClass(_isOpenClass);
            $overlay.removeClass(_isVisibleClass);
        } else {
            $panel.addClass(_isOpenClass);
            $overlay.addClass(_isVisibleClass);
        }
    }
});