"use strict";

var ustawdialogWindow = function(nazwa, szerokosc, wysokosc) {
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
    
var pokazmes = function() {
  PF('grmes').renderMessage({summary:'Wybrano wiersz', detail: 'można edytować', severity: 'info'});  
};


var ustawdialog = function(nazwa,rodzic) {
    $(document.getElementById(nazwa)).width(1250).height(700);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+40px top",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 1 "+Exception);
    }

};

var ustawdialog = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+20px top-10px",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogAuto = function(nazwa, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+60px top+30px",
        of: $(document.getElementById(znajdzmenu(nazwa))),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogCenter = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center center",
        at: "center center",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogrk = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left center",
        at: "left+150px center",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};



var resetujdialog = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};

var znajdzmenu = function(nazwa) {
    return $(document.getElementById(nazwa)).parent().find(".menutabView")[0].id;
};
