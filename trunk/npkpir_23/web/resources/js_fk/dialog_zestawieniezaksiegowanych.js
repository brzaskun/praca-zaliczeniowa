    var pokazwierszedok = function() {
        PF('wiersze').show();
};

var ustawdialogwiersze = function(nazwa,menu) {
    $(document.getElementById(nazwa)).width(1000).height(400);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center top",
        at: "center center",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};
