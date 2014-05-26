"use strict";

var zachowajobiekt = function(obiekt) {
    MYAPP.obiekt = obiekt;
};

var zachowajnumerwiersza = function(numer, zmienna) {
    MYAPP[zmienna] = numer;
};

var wylicznumerwiersza = function(wiersze) {
    var iloscrzedow = wiersze.size();
    try {
        var numerwiersza = 0;
        for(var i = 0; i < iloscrzedow; i++) {
            if (wiersze[i] === MYAPP.obiekt) {
                numerwiersza = i;
            }
        }
        zachowajnumerwiersza(numerwiersza,'zmiennazapisy');
    } catch (e) {
        alert('error');
    }
};


var przejdzwiersz = function(tabela, tabela1, zmienna) {
    var wiersze = $(document.getElementById(tabela)).children("tr");
    wylicznumerwiersza(wiersze);
    if (!MYAPP.hasOwnProperty(zmienna)) {
        MYAPP[zmienna] = 1;
    } else {
        MYAPP[zmienna] += 1;
        if (MYAPP[zmienna] > wiersze.length) {
            MYAPP[zmienna] = wiersze.length;
        }
    }
    var komorki = $(wiersze[MYAPP[zmienna]]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};

var wrocwiersz = function(tabela, tabela1, zmienna) {
    var wiersze = $(document.getElementById(tabela)).children("tr");
    wylicznumerwiersza(wiersze);
    if (!MYAPP.hasOwnProperty(zmienna)) {
        MYAPP[zmienna] = 1;
    } else {
        if (MYAPP[zmienna] > 0) {
            MYAPP[zmienna] -= 1;
        }
    }
    var komorki = $(wiersze[MYAPP[zmienna]]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};


var isScrolledIntoView = function(elem) {
    try {
        //tak daleko zeby dotrzec do kontenera
        var docViewTop = 200;
        var docViewBottom = 700;
        var viewableheight = elem.scrollHeight;
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        var przesuniecie = 0;
        if (elemTop < (docViewTop + viewableheight)) {
            przesuniecie += -viewableheight;
        }
        if (elemBottom > docViewBottom) {
            przesuniecie += elemBottom - docViewBottom;
        }
        return przesuniecie;
    } catch (ex) {
         alert("Blad w chodzeniepokonahc.js isScrolledIntoView " + ex.toString());
    }
    return 0;
};

var znajdzwierszzkontonumer = function(wiersze, wartosc) {
    var iloscrzedow = wiersze.size();
    try {
        for(var i = 0; i < iloscrzedow; i++) {
            var trescwiersza = wiersze[i].innerHTML;
            if (trescwiersza.indexOf(wartosc)>-1) {
                return wiersze[i];
            }
        }
    } catch (e) {
        alert('error');
    }
}

var zaznacznoda = function(tabela, tabela1, inputpole) {
    try {
        var wartosc = document.getElementById(inputpole).value;
        wartosc = wartosc.split(" ");
        var wiersze = $(document.getElementById(tabela)).children("tr");
        var node = znajdzwierszzkontonumer(wiersze, wartosc[0]);
        ($(node).children("td"))[0].click();
        zachowajobiekt(node);
        przejdzwierszNode(tabela, tabela1, node);
        document.getElementById(inputpole).value = "";
        document.getElementById(inputpole).value = "";
    } catch (ex) {
        alert("Problem z zaznacznoda/chodzeniepokontach.js");
    }
};

var przejdzwierszNode = function(tabela, tabela1, node) {
    var wiersze = $(document.getElementById(tabela)).children("tr");
    var iloscrzedow = wiersze.size();
    try {
        var numerwiersza = 0;
        for(var i = 0; i < iloscrzedow; i++) {
            if (wiersze[i] === node) {
                numerwiersza = i;
            }
        }
    } catch (e) {
        alert('error');
    }
    var komorki = $(wiersze[numerwiersza]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};