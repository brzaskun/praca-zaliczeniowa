var ustawdialog = function(nazwa,menu) {
    $(document.getElementById(nazwa)).width(1250).height(700);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center top",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 1 "+Exception);
    }

};

var ustawdialog = function(nazwa,menu, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center top",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};




var resetujdialog = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};
