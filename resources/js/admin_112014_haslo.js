$(document).ready(function() {
    podswietlmenu(rj('menuhaslo'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
});

var getCookie  = function (cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}

var przetworzhaslo = function() {
    var mail = decodeURIComponent(getCookie("mail"));
    var haslo1 = $("#haslo1").val();
    var haslo2 = $("#haslo2").val();
    if (haslo1===haslo2) {
        $.ajax({
        type: "POST",
        url: "haslo_112014.php",
        data: "haslo="+haslo1+"&mail="+mail,
        cache: false,
        timeout: 10000,        // sets timeout for the request (10 seconds)
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            if (response === "0") {
                $("#haslo1").val(null);
                $("#haslo2").val(null);
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zmieniono hasło' }]);
                $("#potwierdzeniezmiany").show();
            }
        }
    });
    }
};