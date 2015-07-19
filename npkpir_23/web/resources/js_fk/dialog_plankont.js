"use strict";
var ustalmaxlevel = function (tabela) {
    var lista = r(tabela).children();
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

var zwinwszystkie = function (tabela) {
    var lista = r(tabela).children();
    var lsize = lista.length;
    var doschowania = new Array();
    var doklikania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > 0) {
            doschowania.push(lista[i]);
        } else if (tdval === 0) {
            doklikania.push(lista[i]);
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).hide();
    }
    MYAPP.currentlevel = 0;
    removeczujnik(doschowania);
    dodajczujnik(doklikania, tabela);
};

var zwinjeden = function (tabela) {
    var lista = r(tabela).children();
    var currentlevel = MYAPP.currentlevel;
    if (currentlevel === 0) {
        MYAPP.currentlevel = 1;
        return;
    } else {
        currentlevel--;
        MYAPP.currentlevel = currentlevel;
    }
    if (currentlevel < 0) {
        return;
    }
    var lsize = lista.length;
    var doschowania = new Array();
    var doklikania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > currentlevel) {
            doschowania.push(lista[i]);
        }
        if (tdval === currentlevel) {
            doklikania.push(lista[i]);
        }
    }
    for (var key in doschowania) {
        $(doschowania[key]).hide();
    }
    removeczujnik(doschowania);
    dodajczujnik(doklikania, tabela);
};

var rozwinwszystkie = function (tabela) {
    var lista = r(tabela).children();
    var lsize = lista.length;
    var dopokazania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval > 0) {
            dopokazania.push(lista[i]);
        }
    }
    for (var key in dopokazania) {
        $(dopokazania[key]).show();
    }
    MYAPP.currentlevel = MYAPP.maxlevel;
};

var rozwinjeden = function (tabela) {
    var lista = r(tabela).children();
    var currentlevel = MYAPP.currentlevel;
    if (currentlevel > MYAPP.maxlevel) {
        MYAPP.currentlevel = MYAPP.maxlevel;
        return;
    } else {
        currentlevel++;
        MYAPP.currentlevel = currentlevel;
    }
    var lsize = lista.length;
    var dopokazania = new Array();
    var doklikania = new Array();
    for (var i = 0; i < lsize; i++) {
        var td = lista[i].children[0];
        var tdval = parseInt(td.innerText);
        if (tdval < currentlevel) {
            dopokazania.push(lista[i]);
        } else if (tdval === currentlevel) {
            dopokazania.push(lista[i]);
            doklikania.push(lista[i]);
        }
    }
    for (var key in dopokazania) {
        $(dopokazania[key]).show();
    }
    if (currentlevel > MYAPP.maxlevel) {
        MYAPP.currentlevel = currentlevel-1;
        currentlevel--;
        removeczujnik(dopokazania);
    } else {
        removeczujnik(dopokazania);
        dodajczujnik(doklikania, tabela);
    }
};

var kopiujnazwepelnakonta = function(zrodlo,cel) {
    var nazwapelna = r(zrodlo).val();
    r(cel).val(nazwapelna);
};

var dodajczujnik = function(listawierszy,tabela) {
    var lista = r(tabela).children();
    for (var wiersz in listawierszy) {
        $(listawierszy[wiersz]).dblclick(function(){
           var ltemp0 = new Array();
           ltemp0.push(this);
           var nr1 = this.children[2].innerText;
           var liczbaminusow = nr1.split('-').length;
           for (var wiersz2 in lista) {
               try {
                    var nr2 = lista[wiersz2].children[2].innerText;
                    var liczbaminusow2 = nr2.split('-').length;
                    if (nr2.indexOf(nr1) > -1 && liczbaminusow === liczbaminusow2-1) {
                        var ltemp = new Array();
                        ltemp.push($(lista[wiersz2]));
                        dodajczujnik(ltemp,tabela);
                        $(lista[wiersz2]).show();
                        removeczujnik(ltemp0);
                    }
                } catch (e) {
                    break;
                }
           }
        });
    }
};

var removeczujnik = function(listawierszy) {
    for (var wiersz in listawierszy) {
        $(listawierszy[wiersz]).unbind('dblclick');
    }
};