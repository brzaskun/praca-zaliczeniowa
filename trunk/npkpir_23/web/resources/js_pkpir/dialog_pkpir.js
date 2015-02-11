/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var ustawdialog4 = function(nazwa,menu, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center center",
        at: "center center",
        of: window,
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialog = function(nazwa,menu, szerokosc, wysokosc, poledoaktywacji) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(poledoaktywacji)).focus();
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+40px top",
        of: $(document.getElementById(menu)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var resetujdialog = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};

var sprawdzczynazwaskroconafakturaniejestshown = function() {
    var czydialogjestshown = $("#nazwaskroconafaktura").hasClass("ui-overlay-visible");
    if (czydialogjestshown) {
        $(document.getElementById("formkontowybor:wybormenu")).focus();
    }
};

