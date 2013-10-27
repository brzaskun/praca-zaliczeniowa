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
//sluszy do sumowania wprowadzonych kwot czy nie przekraczaja limitu i czy indywidualnie nie przekraczaja limitu w wierszu
var doklejsumowaniewprowadzonych = function() {
    $("#rozrachunki\\:dataList :input").keyup(function() {
        $(this).css("color", "black");
        $(this).css("font-weight", "normal");
        var numerwiersza = ($(this).attr('id').split(":"))[2];
        var wszystkiewiersze = $("#rozrachunki\\:dataList").find(":input");
        var iloscpozycji = wszystkiewiersze.length;
        var wprowadzonowpole = $(this).val();
        var wiersz = "rozrachunki:dataList:"+numerwiersza+":pozostalo";
        var wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
        $(document.getElementById(wiersz)).css("font-weight", "normal");
        $(document.getElementById(wiersz)).css("color", "black");
        var wartoscwprowadzona = zrobFloat(wprowadzonowpole);
        if (wartoscwprowadzona > wartoscpoprawej) {
            if (wartoscpoprawej===0) {
                $(document.getElementById(wiersz)).css("font-weight", "600");
                $(document.getElementById(wiersz)).css("color", "green");
            } else {
                $(document.getElementById(wiersz)).css("font-weight", "900");
                $(document.getElementById(wiersz)).css("color", "red");
            }
        }
        if (wprowadzonowpole === " zł") {
            $(this).val(wartoscpoprawej);
        }
        var wprowadzono = 0;
        var j = 0;
        for (var i = 0; i < iloscpozycji; i = i + 2) {
            var wiersz = "rozrachunki:dataList:"+j+":pozostalo";
            wprowadzono += zrobFloat($(wszystkiewiersze[i]).val());
            if (wprowadzono > MYAPP.limit) {
                $(wszystkiewiersze[i]).css("font-weight", "900");
                $(wszystkiewiersze[i]).css("color", "red");
            }
            j++;
        }
    });
};



