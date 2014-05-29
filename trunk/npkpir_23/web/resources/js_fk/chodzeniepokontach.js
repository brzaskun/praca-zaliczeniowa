"use strict";

var zachowajobiekt = function(obiekt, event) {
    MYAPP.obiekt = obiekt;
    var source = event.target || event.srcElement;
    var sourceid = source.parentNode.parentNode.id;
    MYAPP.sourceid = sourceid;
    if (MYAPP.sourceid === "form:dataList_data") {
        MYAPP.tabeladata = "form:dataList_data";
        MYAPP.tabela = "form:dataList";
        MYAPP.zmienna = "zmiennazapisy";
    } else {
        MYAPP.tabeladata = "formobroty:dataListObroty_data";
        MYAPP.tabela = "formobroty:dataListObroty";
        MYAPP.zmienna = "zmiennaobroty";
    }
    console.log(sourceid);
};


var zachowajnumerwiersza = function(numer, zmienna) {
    MYAPP[zmienna] = numer;
};



var przejdzwiersz = function() {
    event.preventDefault();
    event.stopPropagation();
    event.stopImmediatePropagation();
    event.cancelBubble = true;
    var wiersze = $(document.getElementById(MYAPP.tabeladata)).children("tr");
    wylicznumerwiersza(wiersze, MYAPP[MYAPP.zmienna]);
    if (MYAPP[MYAPP.zmienna] > wiersze.length) {
        MMYAPP[MYAPP.zmienna] = wiersze.length;
    } else {
        MYAPP[MYAPP.zmienna] += 1;
    }
    var komorki = $(wiersze[MYAPP[MYAPP.zmienna]]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(MYAPP.tabela);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};

var wrocwiersz = function() {
    event.preventDefault();
    event.stopPropagation();
    event.stopImmediatePropagation();
    event.cancelBubble = true;
    var wiersze = $(document.getElementById(MYAPP.tabeladata)).children("tr");
    wylicznumerwiersza(wiersze, MYAPP[MYAPP.zmienna]);
    if (MYAPP[MYAPP.zmienna] > 0) {
        MYAPP[MYAPP.zmienna] -= 1;
    } else {
        MYAPP[MYAPP.zmienna] = 1;
    }
    var komorki = $(wiersze[MYAPP[MYAPP.zmienna]]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(MYAPP.tabela);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};

var stop = function () {
    event.preventDefault();
    event.stopPropagation();
    event.stopImmediatePropagation();
};

var isScrolledIntoView = function(elem) {
    try {
        //tak daleko zeby dotrzec do kontenera
        var docViewTop = 220;
        var docViewBottom = 750;
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
var wylicznumerwiersza = function(wiersze, zmienna) {
    var wartosc = MYAPP.obiekt.innerText;
    wartosc = wartosc.split("\t");
    var iloscrzedow = wiersze.size();
    try {
        var numerwiersza = 0;
        for(var i = 0; i < iloscrzedow; i++) {
            var trescwiersza = $(wiersze[i]).text();
            if (trescwiersza.indexOf(wartosc[0])>-1) {
                console.log("Znaleziony wiersz");
                MYAPP[MYAPP.zmienna] = i;
                console.log(MYAPP[MYAPP.zmienna]);
                return;
            }
        }
        zachowajnumerwiersza(numerwiersza,zmienna);
    } catch (e) {
        console.log('error wylicznumerwiersza'+e);
    }
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
        if (wartosc !== " ") {
            wartosc = wartosc.split(" ");
            var wiersze = $(document.getElementById(tabela)).children("tr");
            var node = znajdzwierszzkontonumer(wiersze, wartosc[0]);
            ($(node).children("td"))[0].click();
            zachowajobiekt(node);
            przejdzwierszNode(tabela, tabela1, node);
            document.getElementById(inputpole).value = "";
            document.getElementById(inputpole).value = "";
        }
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