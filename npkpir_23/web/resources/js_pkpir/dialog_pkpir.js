"use strict";

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
    var czywidzialne = rj("nazwaskroconafaktura").getAttribute("aria-hidden");
    if (czywidzialne === "true"){
        $(document.getElementById("formkontowybor:wybormenu")).focus();
    }
};

