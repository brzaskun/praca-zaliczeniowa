var ustawdialog = function(nazwa,menu) {
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center top",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    $(document.getElementById(nazwa)).width(1250).height(700);
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};

var ustawdialog = function(nazwa,menu, szerokosc, wysokosc) {
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center top",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};

var resetujdialog = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};
