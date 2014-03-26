var pokazwierszedok = function() {
        dokfkwiersze.show();
};

var ustawdialogwiersze = function(nazwa,menu) {
    try {
        $(document.getElementById(nazwa)).position({
        my: "center top",
        at: "center center",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    $(document.getElementById(nazwa)).width(1000).height(400);
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};
