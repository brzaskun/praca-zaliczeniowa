$(document).ready(function () {
    $("#ajax_sun").puidialog({
        height: 120,
        width: 200,
        resizable: false,
        closable: false,
        location: 'center',
        modal: true
    });
    podswietlmenu(rj('menubackup'));
    $("#potwierdz").puibutton();
});
var archiwizuj = function () {
    $("#pole1").show();
    $("#pole2").hide();
    $("#ajax_sun").show();
    $.ajax({
        type: "POST",
        url: "databasebackup.php",
        cache: false,
        timeout: 300000, // sets timeout for the request
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function (response) {
            $("#pole2").show();
            $("#ajax_sun").hide();
        }
    });
};