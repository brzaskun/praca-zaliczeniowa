var ustawmenudokumenty = function() {
    $("#dialogmenudokumenty").position({
        my: "left top",
        at: "left top",
        of: "#ramki",
        collision: "none"
    });
    $("#dialogmenudokumenty").width(600).height(500);
    $("#dialogmenudokumenty").css({"margin-left": "2%"});

};
var resetujdialog = function() {
    $('#dialogmenudokumenty').removeAttr('style')
};



