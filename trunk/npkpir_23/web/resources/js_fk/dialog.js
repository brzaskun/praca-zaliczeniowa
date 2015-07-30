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
        at: "left+20px top-40px",
        of: $(document.getElementById(rodzic)),
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


