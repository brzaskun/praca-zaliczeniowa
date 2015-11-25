"use strict";

var lolo = function() {
    alert('lolo');
};

var calc = function() {
    var wpis = "formdialog_kalkulator:kalkulator_wpis";
    var ekran = "formdialog_kalkulator:kalkulator_ekran";
    var wynik = "formdialog_kalkulator:wynik";
    var znak,kwota;
    var oblicz = function() {
        var tresc = r(wpis).val();
        if (zawierapole(tresc)) {
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
        } else {
            MYAPP.kalkulator_biezaca = MYAPP.kalkulator_schowek;
            MYAPP.kalkulator_schowek = kwota;
            MYAPP.kalkulator_znak = MYAPP.kalkulator_znak_schowek;
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
            MYAPP.kalkulator_suma = 0.0;
        }
        return jestznak;
    }
    var pobierzkwote = function(tresc) {
        var wartosc = tresc.substring(0,tresc.length-1);
        return zrobFloat(wartosc);
    };
    var pobierzznak = function(tresc) {
        return tresc.substring(tresc.length-1, tresc.length);
    };
    var aktualizuj_output = function(kwota,znak) {
        r(ekran).append(kwota);
        r(ekran).append(znak);
    };
    var czyscinput = function() {
        r(wpis).val(null);
    };
    var aktualizuj_wynik = function(kwota) {
        r(wynik).text(zamien_na_waluta(kwota));
    }
    
    oblicz();
};

var kalkulator_reset = function() {
    r('formdialog_kalkulator:kalkulator_wpis').val('');
    r('formdialog_kalkulator:kalkulator_ekran').text('');
    MYAPP.kalkulator_suma = 0.0;
};


