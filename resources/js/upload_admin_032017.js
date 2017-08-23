/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var uploadusers = function() {
    $("#panelladowaniapliku").remove();
    $("#zaladuj").remove();
    $("#rezultat_text").html("Ładuje dane");
    $('<div>').attr('id', 'progressbar').appendTo('#rezultat');
    $("#progressbar").width(300);
    pbar(37,2000);
    $('<p>').appendTo('#rezultat').html("Wysyłam dane do bazy danych");
    $("#panelprzyciskladowanie").css({
        "margin-top": "10px"
    });
//    var dane = JSON.stringify(tabelauzgrupa);
//    var tablice = $.parseJSON(decodeURIComponent(response));
    $.ajax({
        type: "POST",
        url: "upload_admin_032017.php",
        //data: "dane="+dane,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się dodać użytkowników'}]);
        },
        success: function (response) {
            var tablice = $.parseJSON(response);
            var dodanegrupy = tablice[0];
            var niedodanegrupy = tablice[1];
            var niedodani = tablice[2];
            var niewyslani = tablice[3];
            var zleoznaczeni = tablice[4];
            pbar(67,3000);
            pbar(100,4000);
            sleep(20000);
            pokaznowegrupy(dodanegrupy);
            pokazniedodanegrupy(niedodanegrupy);
            pokazniedodani(niedodani);
            pokazniewyslani(niewyslani);
            pokazzleoznaczeni(zleoznaczeni);
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano użytkowników'}]);
            $("#rezultat_text").html("Załadowano dane. Dodano osób: "+tablice[5][0]);
            
        }
    });
};

var pbar = function(proc,time) {
   setTimeout(function(){
        $("#progressbar").progressbar({
            value: proc
        });
    },time);  
};

var pokaznowegrupy = function(dodanegrupy) {
    if (Boolean(Array.isArray(dodanegrupy) && dodanegrupy.length)) {
        $('<p>').attr('id', 'nowegrupy').appendTo('#rezultat');
        var wartosci = przetworztabele(nowegrupy);
        dolacztabele("nowegrupy","tabpokaznowegrupy","400");
        tworztabelezdanymi("tabpokaznowegrupy",["Dodano następujące nowe grupy upoważnień"], wartosci);
    }
};

var pokazniedodanegrupy = function (niedodanegrupy) {
    if (Boolean(Array.isArray(niedodanegrupy) && niedodanegrupy.length)) {
        $('<p style="font-weight: 900;">').attr('id', 'nienowegrupy').appendTo('#rezultat');
        var wartosci = przetworztabele(niedodanegrupy);
        dolacztabele("nienowegrupy","tabpokazniedodanegrupy","400");
        tworztabelezdanymi("tabpokazniedodanegrupy",["te grupy upoważnień już były"], wartosci);
    }
};

var pokazniedodani = function (niedodanegrupy) {
    if (Boolean(Array.isArray(niedodanegrupy) && niedodanegrupy.length)) {
        $('<p>').attr('id', 'niedodani').appendTo('#rezultat');
        var wartosci = przetworztabele(niedodanegrupy);
        dolacztabele("niedodani","tabpokazniedodani","400");
        tworztabelezdanymi("tabpokazniedodani",["Nie dodano następujących użytkowników"], wartosci);
    }
};

var pokazniewyslani = function (niewyslani) {
    if (Boolean(Array.isArray(niewyslani) && niewyslani.length)) {
        $('<p>').attr('id', 'niewyslani').appendTo('#rezultat');
        var wartosci = przetworztabele(niewyslani);
        dolacztabele("niewyslani","tabpokazniewyslani","400");
        tworztabelezdanymi("tabpokazniewyslani",["Nie wysłano maili do następujących użytkowników"], wartosci);
    }
};

var pokazzleoznaczeni = function (zleoznaczeni) {
    if (Boolean(Array.isArray(zleoznaczeni) && zleoznaczeni.length)) {
        $('<p>').attr('id', 'zleoznaczeni').appendTo('#rezultat');
        var wartosci = przetworztabele(zleoznaczeni);
        dolacztabele("zleoznaczeni","tabpokazzleoznaczeni","400");
        tworztabelezdanymi("tabpokazzleoznaczeni",["Nie przyporządkowano prawidłowo do grup upoważnień następujących użytkowników:"], wartosci);
    }
};

var sleep = function (milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
};