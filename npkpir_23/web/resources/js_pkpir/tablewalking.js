"use strict";

var zachowajnumerwiersza = function(numer, me) {
    MYAPP.nrbiezacegowiersza = numer;
};

var przejdzwiersz = function() {
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
    var biezacy = $(lolo[MYAPP.nrbiezacegowiersza]);
    var poprzedni = $(lolo[MYAPP.nrbiezacegowiersza-1]);
    dokTable.unselectRow(poprzedni, "");
    dokTable.selectRow(biezacy, "");
    
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
    var biezacy = $(lolo[MYAPP.nrbiezacegowiersza]);
    var poprzedni = $(lolo[MYAPP.nrbiezacegowiersza+1]);
    dokTable.unselectRow(poprzedni, "");
    dokTable.selectRow(biezacy, "");
};

var isScrolledIntoView = function(elem) {
    try {
        //tak daleko zeby dotrzec do kontenera
        var parent = elem.parentNode
        do {
            parent = parent.parentNode;
        } while (parent.className !== "ui-layout-unit-content ui-widget-content");
        var docViewTop = elem.parentNode.offsetParent.offsetTop;
        var docViewBottom = $(parent).height();
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
    } catch (e) {
    }
    return 0;
};

var okreslwysokosctabeli = function() {
    return "400px";
};


