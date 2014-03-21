"use strict";
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var buttonOn = function(languagebutton) {
    $(languagebutton).prop('disabled', true).fadeTo('slow', 0.2);
    var buttonslist = $('.languagebutton');
    var dlugosc = buttonslist.length;
    for (var i = 0; i < dlugosc; i++) {
        if (buttonslist[i] !== languagebutton) {
            $(buttonslist[i]).prop('disabled', false).fadeTo('slow', 1);
        }
    }
};




