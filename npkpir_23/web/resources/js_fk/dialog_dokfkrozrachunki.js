/* global r, PF, MYAPP */

"use strict";

var rozrachunkiOnShow = function (szer,wys) {
    ustawdialog('dialogdrugi', 'menudokumenty',szer,wys);
    var limit = zrobFloat($(document.getElementById('rozrachunki:pozostalodorozliczenia')).text());
    MYAPP.limit = limit;
    MYAPP.podswietlone = new Array();
    doklejsumowaniewprowadzonych();
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
    PF('rozrachunkiKsiega').clearFilters();
};

var niemarachunkowShow = function () {
    $(document.getElementById('niemarachunkow')).width(450).height(80);
    try {
        $(document.getElementById('niemarachunkow')).position({
            my: "center center",
            at: "center center",
            of: $(document.getElementById('dialogpierwszy')),
            collision: "none none"
        });
        $(document.getElementById("niemarachunkowform:niemarachunkowbutton")).focus();
    } catch (Exception) {
        alert("blad w fukncji ustawdialog w pliku dialog_dokfkrozrachunki.js wiersz 14 " + Exception);
    }
};

//var transakcjawyborShow = function () {
//    $(document.getElementById('transakcjawybor')).width(300).height(80);
//    try {
//        $(document.getElementById('transakcjawybor')).position({
//            my: "center center",
//            at: "center center",
//            of: $(document.getElementById('dialogpierwszy')),
//            collision: "none none"
//        });
//    } catch (Exception) {
//        alert("blad w fukncji ustawdialog w pliku dialog_dokfkrozrachunki.js wiersz 14 " + Exception);
//    }
//    $(document.getElementById("formtransakcjawybor:transakcjawybormenu")).focus();
//};

var kontownmawyborShow = function () {
    $(document.getElementById('kontownmawybor')).width(300).height(80);
    try {
        $(document.getElementById('kontownmawybor')).position({
            my: "center center",
            at: "center center",
            of: $(document.getElementById('dialogbilansuklad')),
            collision: "none none"
        });
    } catch (Exception) {
        alert("blad w fukncji ustawdialog w pliku dialog_dokfkrozrachunki.js wiersz 44 " + Exception);
    }
    $(document.getElementById("formkontowybor:wybormenu")).focus();
};

var kontownmawyborRZiSShow = function () {
    $(document.getElementById('kontownmawyborRZiS')).width(300).height(80);
    try {
        $(document.getElementById('kontownmawyborRZiS')).position({
            my: "center center",
            at: "center center",
            of: $(document.getElementById('dialogrzisuklad')),
            collision: "none none"
        });
    } catch (Exception) {
        alert("blad w fukncji ustawdialog w pliku dialog_dokfkrozrachunki.js wiersz 44 " + Exception);
    }
    $(document.getElementById("formkontowyborRZiS:wybormenuRZiS")).focus();
};

var powrotDoStronyPoWyborzeRachunekPlatnosc = function () {
    try {
        var powrot = $(MYAPP.zaznaczonepole).attr('id');
        $(document.getElementById(powrot)).focus();
        $(document.getElementById(powrot)).select();
    } catch (e) {
    }
}

//uzywane w DokfkView
//var znadzpasujacepolerozrachunku = function(kwota) {
//    setTimeout(znadzpasujacepolerozrachunku2(kwota), 1000);
//};

var znadzpasujacepolerozrachunku2 = function () {
    var kwota = zrobFloat(r("rozrachunki:pozostalodorozliczenia")[0].innerText);
    var wiersze = $(document.getElementById("rozrachunki:dataList_data")).children("tr");
    var opisy = new Array();
    var sumarozliczonych = 0.0;
    var dlwiersze = wiersze.size();
    if (dlwiersze > 0) {
        try {//moze sie zdarzyc ze nie bedzie nic
            for (var i = 0; i < dlwiersze; i++) {
                var trescwiersza = $(wiersze[i]).children("td");
                opisy[i] = trescwiersza[3].innerText;
                var linijka = "rozrachunki:dataList:" + i + ":kwotarozliczenia_input";
                sumarozliczonych += zrobFloat(r(linijka).val());
            }
            //uzupelniamy tylko wtedy jak inne pola sa puste. inaczej przy edycji bedzie gupota
            if (sumarozliczonych === 0) {
                var opisaktualnyrorachunek = (document.getElementById("rozrachunki:opiswierszaaktualnyrozrachunek").textContent).toLocaleLowerCase();
                var dl = opisy.length;
                var gdzieszukac = -1;
                for (var i = 0; i < dl; i++) {
                    var opisbiezacy = opisy[i];
                    var opisbiezacyLC = (opisbiezacy.toLocaleString()).toLowerCase();
                    var znaleziono = opisaktualnyrorachunek.indexOf(opisbiezacyLC);
                    if (znaleziono > -1) {
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
                    $(document.getElementById(dopasowanywiersz)).keyup();
                    //change musi byc bo inaczej n ie przelicza rownic kursowych
                    $(document.getElementById(dopasowanywiersz)).change();
                    $(document.getElementById(dopasowanywiersz)).select();
                } else {
                    dopasowanywiersz = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_input";
                    dopasowanywierszH = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_hinput";
                    var zastanakwota = $(document.getElementById(dopasowanywiersz)).val();
                    if (zastanakwota === "0.00" && dlwiersze === 1) {
                        var dorozliczenia = zrobFloat(document.getElementById("rozrachunki:dataList:0:pozostaloWn").innerText);
                        if (kwota < dorozliczenia) {
                            $(document.getElementById(dopasowanywiersz)).val(kwota);
                            $(document.getElementById(dopasowanywierszH)).val(kwota);
                        } else {
                            $(document.getElementById(dopasowanywiersz)).val(dorozliczenia);
                            $(document.getElementById(dopasowanywierszH)).val(dorozliczenia);
                        }
                    }
                    $(document.getElementById(dopasowanywiersz)).keyup();
                    $(document.getElementById(dopasowanywiersz)).change();
                    $(document.getElementById(dopasowanywiersz)).select();
                }
            } else {
                dopasowanywiersz = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_input";
                $(document.getElementById(dopasowanywiersz)).focus();
                $(document.getElementById(dopasowanywiersz)).change();
                $(document.getElementById(dopasowanywiersz)).select();
            }
        } catch (el) {
            dopasowanywiersz = "rozrachunki:dataList:" + 0 + ":kwotarozliczenia_input";
            $(document.getElementById(dopasowanywiersz)).keyup();
            $(document.getElementById(dopasowanywiersz)).change();
            $(document.getElementById(dopasowanywiersz)).select();
        }
    }

};

//wykonuje czynnosci podczas zamykania dialogu z rozrachunkami
var rozrachunkiOnHide = function () {
    resetujdialog('dialogdrugi');
    delete MYAPP.podswietlone;
    try {
        powrotdopolaPoNaniesieniuRozrachunkow();
    } catch (e) {
    }
};
//sluzy do zaznaczania pol nierozrachunowych
var powrotdopolaPoNaniesieniuRozrachunkow = function () {
    var powrot = $(MYAPP.zaznaczonepole).attr('id');
    $(document.getElementById(powrot)).focus();
    $(document.getElementById(powrot)).select();
};

var zaznacznafocus = function(ee) {
    //dziwnie skacze - wylaczylem
    var wprowadzonowpole = ee.value;
    if (wprowadzonowpole === "0.00") {
        $(ee).select();
    }
};

var doklejfocuswprowadzonych = function (e) {
     $(e).data('oldvalue', $(e).val());
};

//sluszy do sumowania wprowadzonych kwot czy nie przekraczaja limitu i czy indywidualnie nie przekraczaja limitu w wierszu
var doklejsumowaniewprowadzonych = function () {
    r("rozrachunki:dataList").find("input").change(function () {
        var wprowadzonowpole = $(this).val();
        var starawartosc = zrobFloat($(this).data('oldvalue'));
        var rozliczeniebiezace = zrobFloat(r('rozrachunki:rozliczeniebiezace').text());
        r("rozrachunki:zapiszrozrachunekButton").show();
        setTonormal(this);
        var numerwiersza = lp(this);
        if (wprowadzonowpole === "") {
            uzupelnijpustepole(numerwiersza);
            wprowadzonowpole = 0.0;
        }
        var walutarachunki = pobierzwaluta(numerwiersza);
        var kurs = pobierzkurs(numerwiersza);
        var wierszrachunekpozostalo = "rozrachunki:dataList:" + numerwiersza + ":pozostaloWn";
        var kwotarachunekpozostalo = zrobFloat(r(wierszrachunekpozostalo).text());
        if (isNaN(kwotarachunekpozostalo) === true) {
            wierszrachunekpozostalo = "rozrachunki:dataList:" + numerwiersza + ":pozostaloMa";
            kwotarachunekpozostalo = zrobFloat(r(wierszrachunekpozostalo).text());
        }
        //dopuszczalny margines dla nadplat bo platnosc w walucie
        var kwotarachunekpozostalowPLN = kwotarachunekpozostalo;
        if (walutarachunki !== "PLN") {
            kwotarachunekpozostalowPLN = kwotarachunekpozostalo * kurs + kwotarachunekpozostalo * .2;
        }
        setTonormal(wierszrachunekpozostalo);
        var wierszTransakcjaRozliczajaca = "rozrachunki:pozostalodorozliczenia";
        setTonormal(wierszTransakcjaRozliczajaca);
        var kwotaplatnosci = zrobFloat(wprowadzonowpole);
        var walutaplatnosci = r("rozrachunki:walutarozliczajacego").text();
        var kwotaplatnosciwPLN = kwotaplatnosci;
        if (walutaplatnosci !== "PLN" && walutarachunki === "PLN") {
            var kursplatnosci = parseFloat(r("rozrachunki:kursrozliczajacego").text());
            kwotaplatnosciwPLN = kwotaplatnosci * kursplatnosci - kwotarachunekpozostalo * 0.2;
        }
        var _jednak_nie_odslaniaj;
        if (kwotaplatnosciwPLN > kwotarachunekpozostalowPLN) {
            if (kwotarachunekpozostalo === 0) {
                setToOther(wierszrachunekpozostalo,"normal","green");
                _jednak_nie_odslaniaj = false;
            } else {
                setToOther(wierszrachunekpozostalo,"900","red");
                r("rozrachunki:zapiszrozrachunekButton").hide();
                _jednak_nie_odslaniaj = true;
            }
        }
        if (wprowadzonowpole === " zł") {
            if (kwotarachunekpozostalo >= MYAPP.limit) {
                $(this).val(MYAPP.limit);
            } else {
                $(this).val(kwotarachunekpozostalo);
            }
        }
        var dorozliczenia = zrobFloat(r('rozrachunki:dorozliczenia').text());
        var juzrozliczono = zrobFloat(r('rozrachunki:juzrozliczono').text());
        rozliczeniebiezace = zrobFloat(r('rozrachunki:rozliczeniebiezace').text());
        var kwotarozliczeniabiezacego = rozliczeniebiezace+kwotaplatnosci-starawartosc;
        r("rozrachunki:rozliczeniebiezace").text(zamien_na_waluta(kwotarozliczeniabiezacego));
        var kwotarozliczenia = juzrozliczono+rozliczeniebiezace+kwotaplatnosci-starawartosc;
        r("rozrachunki:pozostalodorozliczenia").text(zamien_na_waluta(dorozliczenia - kwotarozliczenia));
        //
        MYAPP.limit = (dorozliczenia - kwotarozliczenia).round(2);
        if (MYAPP.limit < 0) {
            setToOther(wierszTransakcjaRozliczajaca,"900","red");
            r("rozrachunki:zapiszrozrachunekButton").hide();
        } else {
            setTonormal(wierszTransakcjaRozliczajaca);
            //inaczej odslania button zapisu nawet jak kwota wprowadzona jest wieksza od tej po prawej
            if (_jednak_nie_odslaniaj === false) {
                r("rozrachunki:zapiszrozrachunekButton").show();
            }
        }
        $(this).css("color", "green");
        if (MYAPP.limit === 0.0) {
            r("rozrachunki:zapiszrozrachunekButton").focus();
            r("rozrachunki:zapiszrozrachunekButton").select();
        }
    });
};

var setTonormal = function(wiersz) {
    r(wiersz).css("font-weight", "normal");
    r(wiersz).css("color", "black");
}

var setToOther = function(wiersz, font, color) {
    r(wiersz).css("font-weight", font);
    r(wiersz).css("color", color);
}

var uzupelnijpustepole = function(numerwiersza) {
    var adres = "rozrachunki:dataList:" + numerwiersza;
    r(adres + ":kwotarozliczenia_input").val("0.00");
    r(adres + ":kwotarozliczenia_hinput").val(0.0);
    r(adres + ":kwotarozliczenia").change();
};

var oblicziloscpozycji = function() {
    var wszystkiewiersze = r("rozrachunki:dataList").find("input");
    return wszystkiewiersze.length;
};

var pobierzwaluta = function(numerwiersza) {
    var waluta = "";
    try {
        waluta = r("rozrachunki:dataList:" + numerwiersza + ":walutaWn").text();
        if (waluta === "") {
            waluta = r("rozrachunki:dataList:" + numerwiersza + ":walutaMa").text();
        }
        if (waluta === "") {
            waluta = r("rozrachunki:dataList:" + numerwiersza + ":walutaMaBO").text();
        }
    } catch (e) {

    }
    if (waluta === "") {
        waluta = "PLN";
    }
    return waluta;
};

var pobierzkurs = function(numerwiersza) {
    var kurs = "";
    try {
        kurs = r("rozrachunki:dataList:" + numerwiersza + ":kursWn").text();
        if (kurs === "") {
            kurs = r("rozrachunki:dataList:" + numerwiersza + ":kursWn1").text();
        }
        if (kurs === "") {
            kurs = r("rozrachunki:dataList:" + numerwiersza + ":kursMa").text();
        }
    } catch (e) {

    }
    kurs = zrobFloat(kurs);
    if (isNaN(kurs)) {
        kurs = 0.0;
    } 
    return kurs;
};



//var rozliczwprowadzone2 = function(wprowadzonowpole, wierszR) {
//  var limit = zrobFloat($(document.getElementById('rozrachunki:pozostalodorozliczenia')).text());
//            MYAPP.limit = limit;
//            r("rozrachunki:zapiszrozrachunekButton").show();
//            r(wierszR).css("color", "black");
//            r(wierszR).css("font-weight", "normal");
//            var numerwiersza = wierszR.split(":")[2];
//            var wszystkiewiersze = $("#rozrachunki\\:dataList").find(".kwotarozrachunku");
//            var iloscpozycji = wszystkiewiersze.length;
//            if (wprowadzonowpole === "") {
//                $(document.getElementById("rozrachunki:dataList:" + numerwiersza + ":kwotarozliczenia_input")).val(0.0);
//                $(document.getElementById("rozrachunki:dataList:" + numerwiersza + ":kwotarozliczenia_hinput")).val(0.0);
//                $(document.getElementById("rozrachunki:dataList:" + numerwiersza + ":kwotarozliczenia_input")).select();
//            }
//            var wiersz = "rozrachunki:dataList:" + numerwiersza + ":pozostaloWn";
//            var wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
//            if (isNaN(wartoscpoprawej)===true) {
//                wiersz = "rozrachunki:dataList:" + numerwiersza + ":pozostaloMa";
//                wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
//            }
//            $(document.getElementById(wiersz)).css("font-weight", "normal");
//            $(document.getElementById(wiersz)).css("color", "black");
//            var wierszTransakcjaRozliczajaca = "rozrachunki:pozostalodorozliczenia";
//            $(document.getElementById(wierszTransakcjaRozliczajaca)).css("font-weight", "normal");
//            $(document.getElementById(wierszTransakcjaRozliczajaca)).css("color", "black");
//            var wartoscwprowadzona = wprowadzonowpole;
//            var _jednak_nie_odslaniaj;
//            if (wartoscwprowadzona > wartoscpoprawej) {
//                if (wartoscpoprawej === 0) {
//                    $(document.getElementById(wiersz)).css("font-weight", "600");
//                    $(document.getElementById(wiersz)).css("color", "green");
//                    _jednak_nie_odslaniaj = false;
//                } else {
//                    $(document.getElementById(wiersz)).css("font-weight", "900");
//                    $(document.getElementById(wiersz)).css("color", "red");
//                    r("rozrachunki:zapiszrozrachunekButton").hide();
//                    _jednak_nie_odslaniaj = true;
//                }
//            }
//            if (wprowadzonowpole === " zł") {
//                if (wartoscpoprawej >= MYAPP.limit) {
//                    r(wiersz).val(MYAPP.limit);
//                } else {
//                    r(wiersz).val(wartoscpoprawej);
//                }
//            }
//            //oznaczamy odpowienio kolorem kwote pozostalo w wierszu rozliczajacym u gory dialogrozrachunki
//            var sumawprowadzonych = 0;
//            for (var i = 0; i < iloscpozycji; i = i + 1) {
//                var wiersz2 = "rozrachunki:dataList:" + i + ":kwotarozliczenia_hinput";
//                sumawprowadzonych += zrobFloat(r(wiersz2).val());
//            }
//            var kwotapierwotna = zrobFloat($(document.getElementById('rozrachunki:dorozliczenia')).text());
//            $(document.getElementById("rozrachunki:juzrozliczono")).text(zamien_na_waluta(sumawprowadzonych));
//            $(document.getElementById("rozrachunki:pozostalodorozliczenia")).text(zamien_na_waluta(kwotapierwotna-sumawprowadzonych));
//            MYAPP.limit = (kwotapierwotna-sumawprowadzonych).round(2);
//            for (var i = 0; i < iloscpozycji; i = i + 2) {
//                if (MYAPP.limit < 0) {
//                    $(wszystkiewiersze[i]).css("font-weight", "900");
//                    $(wszystkiewiersze[i]).css("color", "red");
//                    $(document.getElementById(wierszTransakcjaRozliczajaca)).css("font-weight", "900");
//                    $(document.getElementById(wierszTransakcjaRozliczajaca)).css("color", "red");
//                    r("rozrachunki:zapiszrozrachunekButton").hide();
//                } else {
//                    $(wszystkiewiersze[i]).css("font-weight", "600");
//                    $(wszystkiewiersze[i]).css("color", "black");
//                    $(document.getElementById(wierszTransakcjaRozliczajaca)).css("font-weight", "600");
//                    $(document.getElementById(wierszTransakcjaRozliczajaca)).css("color", "black");
//                    //inaczej odslania button zapisu nawet jak kwota wprowadzona jest wieksza od tej po prawej
//                    if (_jednak_nie_odslaniaj===false)  {
//                        r("rozrachunki:zapiszrozrachunekButton").show();
//                    }
//                }
//            }
//           
//};
//chodzenie po wierszach tabeli przy uzyciu klawiszy strzalek z przewijaniem
var przejdzwiersz = function () {
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

var wrocwiersz = function () {
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
var podswietlrozrachunki = function () {
    try {
        var listawierszy = document.getElementById("zapisydopodswietlenia").innerHTML;
        if (listawierszy.length === 0) {
            alert("Lista kont pusta, nie ma co podswietlac");
        }
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
                MYAPP.znalezionorozrachunki = true;
                for (var kom = 0; kom < 15; kom++) {
                    $(komorki[kom]).css("font-weight", "600");
                    $(komorki[kom]).css("color", "green");
                }
                break;
            }
        }
    } catch (e) {}
};

var odswietlrozrachunki = function () {
    if (MYAPP.znalezionorozrachunki) {
        var wierszewtabeli = $("#tabelazzapisami\\:tabela_data").children("tr");
        var dlugosc = wierszewtabeli.length;
        var znaleziono = -1;
        for (var i = 0; i < dlugosc; i++) {
            var wierszdoobrobki = wierszewtabeli[i];
            var komorki = $(wierszdoobrobki).children("td");
            for (var kom = 0; kom < 15; kom++) {
                $(komorki[kom]).css("font-weight", "initial");
                $(komorki[kom]).css("color", "initial");
            }
        }
        delete MYAPP.znalezionorozrachunki;
    }
};

var zablokujcheckbox = function (zablokuj, pole) {
    try {
        if (zablokuj === 'true') {
            if (pole === 'rachunek') {
                $(document.getElementById("formcheckbox:znaczniktransakcji")).hide();
                r("formcheckbox:labelcheckboxrozrachunki").text("Rachunek został rozliczony przez płatności. Nie można odznaczyć go jako transakcji do rozliczenia.");
            } else {
                $(document.getElementById("formcheckbox:znaczniktransakcji")).hide();
                r("formcheckbox:labelcheckboxrozrachunki").text("Płatność rozliczyla rachunki. Nie można oznaczyć jej jako nowej transakcji.");
            }
        } else {
            $(document.getElementById("formcheckbox:znaczniktransakcji")).show();
            r("formcheckbox:labelcheckboxrozrachunki").text("Oznacz jako transakcję do rozliczenia");
        }
    } catch (e) {
        console.log('error dialog_dokfkrozrachunki.js zablokujcheckbox ' + e);
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
                var cozablokowacWn = "formwpisdokument:dataList:" + i + ":wn_input";
                var cozablokowacWn2 = "formwpisdokument:dataList:" + i + ":wn_hinput";
                if (czyzablokowac === "true") {
                    $(document.getElementById(cozablokowacWn)).attr('readonly', 'readonly');
                    $(document.getElementById(cozablokowacWn2)).attr('readonly', 'readonly');
                    $(document.getElementById(blockwaluty)).attr('readonly', 'readonly');
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
                var cozablokowacWn = "formwpisdokument:dataList:" + i + ":ma_input";
                var cozablokowacWn2 = "formwpisdokument:dataList:" + i + ":ma_hinput";
                if (czyzablokowac === "true") {
                    $(document.getElementById(cozablokowacWn)).attr('readonly', 'readonly');
                    $(document.getElementById(cozablokowacWn2)).attr('readonly', 'readonly');
                    $(document.getElementById(blockwaluty)).attr('readonly', 'readonly');
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



