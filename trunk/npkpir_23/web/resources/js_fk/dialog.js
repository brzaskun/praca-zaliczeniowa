var ustaw = function(nazwa) {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "center top",
        of: $("#dialogmenudokumenty"),
        collision: "none none"
    });
    $(document.getElementById(nazwa)).width(1170).height(700);

};
var resetuj = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};
