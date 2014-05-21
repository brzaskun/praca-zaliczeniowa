var pilnujwprowadzanychrozrachunkow = function() {
    var limit = zrobFloat($(document.getElementById('rozrachunki:dorozliczenia')).text());
    MYAPP.limit = limit;
    doklejsumowaniewprowadzonych();
};

var drugionShow = function() {
    ustawdialog('dialogdrugi', 'menudokumenty');
    pilnujwprowadzanychrozrachunkow();
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
};

var znadzpasujacepolerozrachunku = function(kwota) {
    var wiersze = $(document.getElementById("rozrachunki:dataList_data")).children("tr");
    var opisy = new Array();
    var dlwiersze = wiersze.size();
    if (dlwiersze > 0) {
        try {//moze sie zdarzyc ze nie bedzie nic
            for (var i = 0; i < dlwiersze; i++) {
                var trescwiersza = $(wiersze[i]).children("td");
                opisy[i] = trescwiersza[8].innerText;
            }
            var opisaktualnyrorachunek = document.getElementById("rozrachunki:opiswierszaaktualnyrozrachunek").textContent;
            var dl = opisy.length;
            var gdzieszukac = -1;
            for (var i = 0; i < dl; i++) {
                var opisbiezacy = opisy[i];
                var znaleziono = opisaktualnyrorachunek.indexOf(opisbiezacy);
                if (znaleziono > 0) {
                    gdzieszukac = i;
                    break;
                }
            }
            if (gdzieszukac > -1) {
                var dopasowanywiersz = "rozrachunki:dataList:" + gdzieszukac + ":nrwlasnydok";
                $(document.getElementById(dopasowanywiersz)).css("color", "green");
                $(document.getElementById(dopasowanywiersz)).css("background-color", "#FFFFB4");
                $(document.getElementById(dopasowanywiersz)).css("font-weight", "bold");
                dopasowanywiersz = "rozrachunki:dataList:" + gdzieszukac + ":kwotarozliczenia_input";
                var dopasowanywierszH = "rozrachunki:dataList:" + gdzieszukac + ":kwotarozliczenia_hinput";
                $(document.getElementById(dopasowanywiersz)).css("color", "green");
                $(document.getElementById(dopasowanywiersz)).css("background-color", "#FFFFB4");
                $(document.getElementById(dopasowanywiersz)).css("font-weight", "bold");
                var zastanakwota = $(document.getElementById(dopasowanywiersz)).val();
                if (zastanakwota === "0.00") {
                    $(document.getElementById(dopasowanywiersz)).val(kwota);
                    $(document.getElementById(dopasowanywierszH)).val(kwota);
                }
                $(document.getElementById(dopasowanywiersz)).select();
            } else {
                dopasowanywiersz = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_input";
                dopasowanywierszH = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_hinput";
                var zastanakwota = $(document.getElementById(dopasowanywiersz)).val();
                if (zastanakwota === "0.00" && dlwiersze === 1) {
                    $(document.getElementById(dopasowanywiersz)).val(kwota);
                    $(document.getElementById(dopasowanywierszH)).val(kwota);
                }
                $(document.getElementById(dopasowanywiersz)).focus();
                $(document.getElementById(dopasowanywiersz)).select();
            }
        } catch (el) {
             dopasowanywiersz = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_input";
             $(document.getElementById(dopasowanywiersz)).focus();
             $(document.getElementById(dopasowanywiersz)).select();
        }
    }

};

var updateroznice = function () {
    
};

//wykonuje czynnosci podczas zamykania dialogu z rozrachunkami
var rozrachunkionHide = function() {
    resetujdialog('dialogdrugi');
    $(document.getElementById("wpisywaniefooter:wnlubma")).val("");
    $(document.getElementById("wpisywaniefooter:wierszid")).val("");
    try {
        var wiersznr = MYAPP.wierszrozrachukowy - 1;
        sprawdzpoprzedniwiersz(wiersznr);
    } catch (e) {
        alert("Blad w dialogrozrachunki.js rozrachunkionHide");
    }
    try {
        var powrot = $(MYAPP.zaznaczonepole).attr('id');
        $(document.getElementById(powrot)).focus();
        $(document.getElementById(powrot)).select();
    } catch (e) {
    }
};
//sluzy do zaznaczania pol nierozrachunowych
var powrotdopola = function() {
    var powrot = $(MYAPP.zaznaczonepole).attr('id');
    $(document.getElementById(powrot)).focus();
    $(document.getElementById(powrot)).select();
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
        var wiersz = "rozrachunki:dataList:" + numerwiersza + ":pozostalo";
        var wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
        $(document.getElementById(wiersz)).css("font-weight", "normal");
        $(document.getElementById(wiersz)).css("color", "black");
        var wierszaktualny = "rozrachunki:dorozliczenia";
        $(document.getElementById(wierszaktualny)).css("font-weight", "normal");
        $(document.getElementById(wierszaktualny)).css("color", "black");
        var wartoscwprowadzona = zrobFloat(wprowadzonowpole);
        if (wartoscwprowadzona > wartoscpoprawej) {
            if (wartoscpoprawej === 0) {
                $(document.getElementById(wiersz)).css("font-weight", "600");
                $(document.getElementById(wiersz)).css("color", "green");
            } else {
                $(document.getElementById(wiersz)).css("font-weight", "900");
                $(document.getElementById(wiersz)).css("color", "red");
            }
        }
        if (wprowadzonowpole === " zł") {
            if (wartoscpoprawej >= MYAPP.limit) {
                $(this).val(MYAPP.limit);
            } else {
                $(this).val(wartoscpoprawej);
            }
        }
        var wprowadzono = 0;
        var j = 0;
        for (var i = 0; i < iloscpozycji; i = i + 2) {
            var wiersz = "rozrachunki:dataList:" + j + ":pozostalo";
            wprowadzono += zrobFloat($(wszystkiewiersze[i]).val());
            if (wprowadzono > MYAPP.limit) {
                $(wszystkiewiersze[i]).css("font-weight", "900");
                $(wszystkiewiersze[i]).css("color", "red");
                $(document.getElementById(wierszaktualny)).css("font-weight", "900");
                $(document.getElementById(wierszaktualny)).css("color", "red");
            }
            j++;
        }
    });
};
//chodzenie po wierszach tabeli przy uzyciu klawiszy strzalek z przewijaniem
var przejdzwiersz = function() {
    var wierszewbiezacejtabeli = $("#zestawieniedokumentow\\:dataList_data").children("tr");
    if (!MYAPP.hasOwnProperty('nrbiezacegowiersza')) {
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza += 1;
        if (MYAPP.nrbiezacegowiersza > wierszewbiezacejtabeli.length) {
            MYAPP.nrbiezacegowiersza = wierszewbiezacejtabeli.length;
        }
    }
    var komorki = $(wierszewbiezacejtabeli[MYAPP.nrbiezacegowiersza]).children("td");
    var czynaekranie = isScrolledIntoView(komorki[1]);
    if (!czynaekranie) {
        var wysokosc = 70;
        var elem = document.getElementById('zestawieniedokumentow:dataList');
        elem.scrollTop = elem.scrollTop + wysokosc;
    }
    $(komorki[1]).click();
};

var wrocwiersz = function() {
    var wierszewbiezacejtabeli = $("#zestawieniedokumentow\\:dataList_data").children("tr");
    if (!MYAPP.hasOwnProperty('nrbiezacegowiersza')) {
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza -= 1;
        if (MYAPP.nrbiezacegowiersza < 0) {
            MYAPP.nrbiezacegowiersza = 0;
        }
    }
    var komorki = $(wierszewbiezacejtabeli[MYAPP.nrbiezacegowiersza]).children("td");
    var czynaekranie = isScrolledIntoView(komorki[1]);
    if (!czynaekranie) {
        var wysokosc = 70;
        var elem = document.getElementById('zestawieniedokumentow:dataList');
        elem.scrollTop = elem.scrollTop - wysokosc;
    }
    $(komorki[1]).click();
};

function isScrolledIntoView(elem)
{
    var docViewTop = $(window).scrollTop() + 150;
    var docViewBottom = docViewTop + $(window).height() - 300;

    var elemTop = $(elem).offset().top;
    var elemBottom = elemTop + $(elem).height();

    return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom)
            && (elemBottom <= docViewBottom) && (elemTop >= docViewTop));
}

//podswietla powiazane rozrachunki w zapisach konta
var podswietlrozrachunki = function() {
    var listawierszy = document.getElementById("zapisydopodswietlenia").innerHTML;
    listawierszy = listawierszy.replace(/[^0-9\\.]+/g, ' ').trim();
    listawierszy = listawierszy.split(' ');
    var wierszewtabeli = $("#tabelazzapisami\\:tabela_data").children("tr");
    var dlugosc = wierszewtabeli.length;
    var znaleziono = -1;
    for (var i = 0; i < dlugosc; i++) {
        var wierszdoobrobki = wierszewtabeli[i];
        var komorki = $(wierszdoobrobki).children("td");
        var nrpolazapisu = komorki[1].innerHTML;
        znaleziono = $.inArray(nrpolazapisu, listawierszy);
        if (znaleziono > -1) {
            var wiersz = "tabelazzapisami:tabela:" + i + ":kontonr";
            $(document.getElementById(wiersz)).css("font-weight", "900");
            $(document.getElementById(wiersz)).css("color", "green");
            var kwotawnwiersz = "tabelazzapisami:tabela:" + i + ":kwotawnwiersz";
            var kwotamawiersz = "tabelazzapisami:tabela:" + i + ":kwotamawiersz";
            var wartoscwn = zrobFloat(document.getElementById(kwotawnwiersz).innerHTML.replace('&nbsp;', ''));
            var wartoscma = zrobFloat(document.getElementById(kwotamawiersz).innerHTML.replace('&nbsp;', ''));
            if (wartoscwn !== 0) {
                $(document.getElementById(kwotawnwiersz)).css("font-weight", "900");
                $(document.getElementById(kwotawnwiersz)).css("color", "green");
            } else {
                $(document.getElementById(kwotamawiersz)).css("font-weight", "900");
                $(document.getElementById(kwotamawiersz)).css("color", "green");
            }
        }
    }

};

var zablokujcheckbox = function(nowatransakcja) {
    var wartosc = zrobFloat(nowatransakcja);
    if (wartosc > 0) {
        $(document.getElementById("formcheckbox:znaczniktransakcji")).hide();
    } else {
        $(document.getElementById("formcheckbox:znaczniktransakcji")).show();
    }

};

var zablokujwierszereadonly = function () {
    var wiersze = $(document.getElementById("formwpisdokument:dataList_data")).children("tr");
    var dl = wiersze.size();
    if (dl > 0) {
         var blockwaluty = "formwpisdokument:wybranawaluta";
         try {//moze sie zdarzyc ze nie bedzie nic
            for (var i = 0; i < dl; i++) {
                var trescwiersza = $(wiersze[i]).children("td");
                var czyzablokowac;
                if (trescwiersza[11].parentElement) {
                    czyzablokowac = trescwiersza[10].innerText;
                } else {
                    czyzablokowac = trescwiersza[9].innerText;
                }
                var cozablokowacWn = "formwpisdokument:dataList:"+i+":wn_input";
                var cozablokowacWn2 = "formwpisdokument:dataList:"+i+":wn_hinput";
                if (czyzablokowac === "true") {
                    $(document.getElementById(cozablokowacWn)).attr('readonly','readonly');
                    $(document.getElementById(cozablokowacWn2)).attr('readonly','readonly');
                    $(document.getElementById(blockwaluty)).attr('readonly','readonly');
                } else {
                    $(document.getElementById(cozablokowacWn)).removeAttr('readonly');
                    $(document.getElementById(cozablokowacWn2)).removeAttr('readonly');
                    $(document.getElementById(blockwaluty)).removeAttr('readonly');
                }
            }
            for (var i = 0; i < dl; i++) {
                var trescwiersza = $(wiersze[i]).children("td");
                var czyzablokowac;
                if (trescwiersza[11].parentElement) {
                    czyzablokowac = trescwiersza[11].innerText;
                } else {
                    czyzablokowac = trescwiersza[10].innerText;
                }
                var cozablokowacWn = "formwpisdokument:dataList:"+i+":ma_input";
                var cozablokowacWn2 = "formwpisdokument:dataList:"+i+":ma_hinput";
                if (czyzablokowac === "true") {
                    $(document.getElementById(cozablokowacWn)).attr('readonly','readonly');
                    $(document.getElementById(cozablokowacWn2)).attr('readonly','readonly');
                    $(document.getElementById(blockwaluty)).attr('readonly','readonly');
                } else {
                    $(document.getElementById(cozablokowacWn)).removeAttr('readonly');
                    $(document.getElementById(cozablokowacWn2)).removeAttr('readonly');
                    $(document.getElementById(blockwaluty)).removeAttr('readonly');
                }
            }
        } catch (el) {
        }
    }
    
};
//to swuzy nam do blokowania rekordow oraz do zakrywani pol niepelnych wierszy gdy robimy nowe rozrachunki
var porzadkujwierszeporozrachunkach = function (iloscwierszy) {
    zablokujwierszereadonly();
    zakryjpolaedycjadokumentu(iloscwierszy);//jest w pliku jsfk.js
};

