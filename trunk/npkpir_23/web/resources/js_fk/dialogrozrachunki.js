var pilnujwprowadzanychrozrachunkow = function() {
    var limit = zrobFloat($(document.getElementById('rozrachunki:formE:dorozliczenia')).text());
    MYAPP.limit = limit;
    doklejsumowaniewprowadzonych();
};

var drugionShow = function() {
    ustawdialog('dialogdrugi', 'menudokumenty');
    pilnujwprowadzanychrozrachunkow();
};

//wykonuje czynnosci podczas zamykania dialogu z rozrachunkami
var rozrachunkionHide = function() {
    resetujdialog('dialogdrugi');
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
    $(MYAPP.zaznaczonepole).focus();
    $(MYAPP.zaznaczonepole).select();
};

var doklejsumowaniewprowadzonych = function() {
    $("#rozrachunki\\:dataList :input").keyup(function() {
        $(this).css("background-color", "#AFEEEE");
        var wszystkiewiersze = $("#rozrachunki\\:dataList").find(":input");
        var iloscpozycji = wszystkiewiersze.length;
        var wprowadzonowpole = $(this).val();
        var wprowadzono = 0;
        var j = 0;
        for (var i = 0; i < iloscpozycji; i = i + 2) {
            var wiersz = "rozrachunki:dataList:"+j+":pozostalo";
            var wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
            var wartoscwprowadzona = zrobFloat($(wszystkiewiersze[i]).val());
            if (wartoscwprowadzona > wartoscpoprawej) {
                $(document.getElementById(wiersz)).css("font-weight", "900");
            }
            if (wprowadzonowpole === " zł") {
                $(this).val(wartoscpoprawej);
            }
            wprowadzono += zrobFloat($(wszystkiewiersze[i]).val());
            if (wprowadzono > MYAPP.limit) {
                $(wszystkiewiersze[i]).css("background-color", "red");
            }
            j++;
        }
    });
};



