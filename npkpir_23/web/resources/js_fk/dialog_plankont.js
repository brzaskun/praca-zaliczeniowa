"use strict";
var ustalmaxlevel = function (lista) {
    var lista = r(lista).children();
    var maxlevel = 0;
    var lsize1 = lista.length;
    for (var i = 0; i < lsize1; i++) {
        var level = parseInt(lista[i].children[0].innerText);
        if (level > maxlevel) {
            maxlevel = level;
        }
    }
    MYAPP.maxlevel = maxlevel;
    MYAPP.currentlevel = maxlevel;
};

var zwinwszystkie = function (lista) {
    var lista = r(lista).children();
    var lsize = lista.length;
    var doschowania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > 0) {
            doschowania.push(lista[i]);
            
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).hide();
    }
    MYAPP.currentlevel = 0;
};

var zwinjeden = function (lista) {
    var lista = r(lista).children();
    var currentlevel = MYAPP.currentlevel;
    if (currentlevel === 0) {
        MYAPP.currentlevel = 1;
        return;
    } else {
        currentlevel--;
        MYAPP.currentlevel = currentlevel;
    }
    if (currentlevel === 0) {
        MYAPP.currentlevel = 1;
    }
    if (currentlevel < 0) {
        return;
    }
    var lsize = lista.length;
    var doschowania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > currentlevel) {
            doschowania.push(lista[i]);
            
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).hide();
    }
};

var rozwinwszystkie = function (lista) {
    var lista = r(lista).children();
    var lsize = lista.length;
    var doschowania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > 0) {
            doschowania.push(lista[i]);
            
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).show();
    }
    MYAPP.currentlevel = MYAPP.maxlevel;
};

var rozwinjeden = function (lista) {
    var lista = r(lista).children();
    var currentlevel = MYAPP.currentlevel;
    if (currentlevel > MYAPP.maxlevel) {
        MYAPP.currentlevel = MYAPP.maxlevel;
        return;
    } else {
        currentlevel++;
        MYAPP.currentlevel = currentlevel;
    }
    var lsize = lista.length;
    var doschowania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval < currentlevel) {
            doschowania.push(lista[i]);
            
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).show();
    }
    if (currentlevel > MYAPP.maxlevel) {
        MYAPP.currentlevel = currentlevel-1;
        currentlevel--;
    }
};

var kopiujnazwepelnakonta = function(zrodlo,cel) {
    var nazwapelna = r(zrodlo).val();
    r(cel).val(nazwapelna);
};