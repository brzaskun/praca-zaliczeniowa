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

var snapshot = function (tabela){
    var lista = r(tabela).children();
    var lsize = lista.length;
    MYAPP.listaplankont = lista;
    MYAPP.listanrkont = new Array();
    for (var i = 0; i < lsize; i++) {
        var nrkontaP = lista[i].children[2].innerText;
        (MYAPP.listanrkont).push([nrkontaP,$(lista[i]).is(":visible")]);
    }
    
};

var recover = function(tabela) {
    var lista = r(tabela).children();
    var lsize = lista.length;
    var doklikania = new Array();
    for (var i = 0; i < lsize; i++) {
        var nrkontaP = lista[i].children[2].innerText;
        var znaleziono = $.grep(MYAPP.listanrkont, function (el) {
            return el[0] === nrkontaP;
        });
        if (znaleziono.length > 0) {
            if (znaleziono[0][1] === false) {
                $(lista[i]).hide();
            } else {
                $(lista[i]).show();
                doklikania.push(lista[i]);
            }
        } else {
            //tu bedzie ten nowododany
            $(lista[i]).show();
        }
    }
    dodajczujnik(doklikania, tabela);
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
    for (var lp in listawierszy) {
        $(listawierszy[lp]).dblclick(function(){
           var ltemp0 = new Array();
           ltemp0.push(this);
           var nrkontaP = this.children[2].innerText;
           var liczbaminusowP = nrkontaP.split('-').length;
           for (var lpCh in lista) {
               try {
                    var nrkontaCh = lista[lpCh].children[2].innerText;
                    var liczbaminusowCh = nrkontaCh.split('-').length;
                    if (nrkontaCh.indexOf(nrkontaP) > -1 && liczbaminusowP === liczbaminusowCh-1) {
                        var ltemp = new Array();
                        ltemp.push($(lista[lpCh]));
                        dodajczujnik(ltemp,tabela);
                        $(lista[lpCh]).show();
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

var ponumerujwiersze = function(lista) {
    if (typeof  lista !== 'undefined') {
        let wiersze = r(lista).children();
        $(wiersze).each(function(){
            let rowid = $(this).attr("data-rk");
            let wowid = "row_"+rowid;
            $(this).attr("id",wowid);
        });
        console.log("d");
    }
};