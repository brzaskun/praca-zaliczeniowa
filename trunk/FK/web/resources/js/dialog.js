var ustaw = function(nazwa) {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center center",
        of: $("#dialogmenudokumenty"),
        collision: "none none"
    });
    $(document.getElementById(nazwa)).width(1100).height(500);

};
var resetuj = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};
