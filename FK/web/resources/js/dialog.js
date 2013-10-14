var ustaw = function(nazwa) {
        $(document.getElementById(nazwa)).position({
        my: "top",
        at: "center",
        of: "#ramki",
        collision: "none"
    });
    $(document.getElementById(nazwa)).width(600).height(500);

};
var resetuj = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};
