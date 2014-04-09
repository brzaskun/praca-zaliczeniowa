"use strict";

var zachowajnumerwiersza = function(numer, me) {
    MYAPP.nrbiezacegowiersza = numer;
};

var przejdzwiersz = function() {
    var ev = event.target;
    var lolo = $("#form\\:dokumentyLista_data").children("tr");
    if (!MYAPP.hasOwnProperty('nrbiezacegowiersza')) {
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza += 1;
        if (MYAPP.nrbiezacegowiersza > lolo.length) {
            MYAPP.nrbiezacegowiersza = lolo.length;
        }
    }
    var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById('form:dokumentyLista');
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};

var wrocwiersz = function() {
    var lolo = $("#form\\:dokumentyLista_data").children("tr");
    if (!MYAPP.hasOwnProperty('nrbiezacegowiersza')) {
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza -= 1;
        if (MYAPP.nrbiezacegowiersza < 0) {
            MYAPP.nrbiezacegowiersza = 0;
        }
    }
    var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById('form:dokumentyLista');
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};

function isScrolledIntoView(elem) {
    try {
        var parent = ((((elem.parentNode).parentNode).parentNode).parentNode).parentNode;
        var docViewTop = $(parent).scrollTop();
        var docViewBottom = $(parent).height();
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        var przesuniecie = 0;
        if (elemTop < docViewTop) {
            przesuniecie += elemTop - docViewTop;
        }
        if (elemBottom > docViewBottom) {
            przesuniecie += elemBottom - docViewBottom;
        }
        return przesuniecie;
    } catch (e) {
    }
    return 0;
}
;


