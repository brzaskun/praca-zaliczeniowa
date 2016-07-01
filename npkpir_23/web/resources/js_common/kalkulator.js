"use strict";

var lolo = function() {
    alert('lolo');
};

var calc = function() {
    var wpis = "formdialog_kalkulator:kalkulator_wpis";
    var ekran = "formdialog_kalkulator:kalkulator_ekran";
    var wynik = "formdialog_kalkulator:wynik";
    var znak,kwota;
    var korekta;
    var oblicz = function() {
        var tresc = r(wpis).val();
        if (zawieraminus(tresc)) {
            korekta(tresc);
            aktualizuj_wynik(MYAPP.kalkulator_suma);
        } else if (zawierapole(tresc)) {
            operacja(tresc);
            aktualizuj_wynik(MYAPP.kalkulator_suma);
        }
    };
    var operacja = function(tresc) {
        if (isNaN(MYAPP.kalkulator_suma)) {
            MYAPP.kalkulator_suma = 0.0;
        }
        var kwota = pobierzkwote(tresc);
        var znak = pobierzznak(tresc);
        if (isNaN(MYAPP.kalkulator_schowek)) {
            MYAPP.kalkulator_biezaca = kwota;
            MYAPP.kalkulator_schowek = kwota;
            MYAPP.kalkulator_znak = znak;
            MYAPP.kalkulator_znak_schowek = znak;
            MYAPP.kalkulator_suma = kwota;
        } else {
            if (korekta === true) {
                MYAPP.kalkulator_biezaca = kwota;
                MYAPP.kalkulator_schowek = kwota;
                MYAPP.kalkulator_znak = znak;
                MYAPP.kalkulator_znak_schowek = znak;
                korekta = false;
            } else {
                MYAPP.kalkulator_biezaca = MYAPP.kalkulator_schowek;
                MYAPP.kalkulator_schowek = kwota;
                MYAPP.kalkulator_znak = MYAPP.kalkulator_znak_schowek;
                MYAPP.kalkulator_znak_korekta = MYAPP.kalkulator_znak_schowek;
                MYAPP.kalkulator_znak_schowek = znak;
            }
            if (MYAPP.kalkulator_znak === "+") {
                MYAPP.kalkulator_suma += MYAPP.kalkulator_schowek;
            } else if (MYAPP.kalkulator_znak === "-") {
                MYAPP.kalkulator_suma -= MYAPP.kalkulator_schowek;
            } else if (MYAPP.kalkulator_znak === "*") {
                MYAPP.kalkulator_suma *= MYAPP.kalkulator_schowek;
            } else if (MYAPP.kalkulator_znak === "/") {
                MYAPP.kalkulator_suma /= MYAPP.kalkulator_schowek;
            }
        }
    };
    
    var korekta = function(tresc) {
        MYAPP.kalkulator_biezaca = MYAPP.kalkulator_schowek;
        MYAPP.kalkulator_znak = pobierzznakprzeciwny();
        if (MYAPP.kalkulator_znak === "+") {
            MYAPP.kalkulator_suma += MYAPP.kalkulator_schowek;
        } else if (MYAPP.kalkulator_znak === "-") {
            MYAPP.kalkulator_suma -= MYAPP.kalkulator_schowek;
        } else if (MYAPP.kalkulator_znak === "*") {
            MYAPP.kalkulator_suma *= MYAPP.kalkulator_schowek;
        } else if (MYAPP.kalkulator_znak === "/") {
            MYAPP.kalkulator_suma /= MYAPP.kalkulator_schowek;
        }
        korekta = true;
    };
    
    var zawieraminus = function(tresc) {
        var jestznak = false;
        if (tresc.indexOf("--") > -1) {
            jestznak = true;
            aktualizuj_output_korekta(MYAPP.kalkulator_schowek,pobierzznakprzeciwny(tresc));
            czyscinput();
        }
        return jestznak;
    };
    
    var zawierapole = function(tresc) {
        var robicdalej = false;
        if (tresc.length > 1 && sprawdzznak(tresc)) {
            kwota = pobierzkwote(tresc);
            if (isNaN(kwota) === false) {
                znak = pobierzznak(tresc);
                aktualizuj_output(kwota,znak);
                robicdalej = true;
            }
            czyscinput();
        }
        return robicdalej;
    };
    var sprawdzznak = function(tresc) {
        var jestznak = false;
        if (_.include(tresc,"+")) {
            jestznak = true;
        }
        if (_.include(tresc,"-")) {
            jestznak = true;
        }
        if (_.include(tresc,"*")) {
            jestznak = true;
        }
        if (_.include(tresc,"/")) {
            jestznak = true;
        }
        if (_.include(tresc,"=")) {
            jestznak = true;
            czyscinput();
        }
        return jestznak;
    };
    var pobierzkwote = function(tresc) {
        var wartosc = tresc.substring(0,tresc.length-1);
        return zrobFloat(wartosc);
    };
    var pobierzznak = function(tresc) {
        return tresc.substring(tresc.length-1, tresc.length);
    };
    var pobierzznakprzeciwny = function() {
        var znak = MYAPP.kalkulator_znak_korekta;
        if (znak === "+") {
            znak = "-";
        } else if (znak === "-") {
            znak = "+";
        } else if (znak === "*") {
            znak = "/";
        } else if (znak === "/") {
            znak = "*";
        }
        return znak;
    };
    var aktualizuj_output = function(kwota,znak) {
        r(ekran).append(kwota);
        r(ekran).append(znak);
    };
    var aktualizuj_output_korekta = function(kwota,znak) {
        var e = r(ekran).text();
        var ne = e.substring(0, e.length-1);
        ne = ne +znak +kwota+MYAPP.kalkulator_znak_schowek;
        r(ekran).text(ne);
    };
    var czyscinput = function() {
        r(wpis).val(null);
    };
    var aktualizuj_wynik = function(kwota) {
        r(wynik).text(zamien_na_waluta(kwota));
    };
    
    oblicz();
};

var kalkulator_reset = function() {
    r('formdialog_kalkulator:kalkulator_wpis').val('');
    r('formdialog_kalkulator:kalkulator_ekran').text('');
    r('formdialog_kalkulator:wynik').text('');
    MYAPP.kalkulator_suma = 0.0;
    delete MYAPP.kalkulator_schowek;
    var wpis = "formdialog_kalkulator:kalkulator_wpis";
    r(wpis).focus();
};

var kalkulator_close = function() {
    PF('dialog_kalkulator').hide();
    $(MYAPP.kalkulator_cel).val(parseFloat(MYAPP.kalkulator_suma).toFixed(2));
    $(MYAPP.kalkulator_cel).next().val(parseFloat(MYAPP.kalkulator_suma).toFixed(2));
    $(MYAPP.kalkulator_cel).click();
    $(MYAPP.kalkulator_cel).select();
    kalkulator_reset();
};

var kalkulator_pobierzwiersz = function(pole) {
  MYAPP.kalkulator_cel = pole;  
};

var uruchomkalkulator = function(wywolujacy) {
    MYAPP.wywolujacy = wywolujacy;
    PF('dialog_kalkulator').show();
};