/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var common_generuj_nazwykolumna = function (tablica) {
    var zwrot = new Array();
    for (var i = 0; i < tablica.length; i++) {
        var o1 = {"sTitle": tablica[i]};
        zwrot.push(o1);
    }
    return zwrot; 
};


var common_generuj_nazwykolumna = function (tablica, szerokosc) {
    var zwrot = new Array();
    for (var i = 0; i < tablica.length; i++) {
        var o1 = {"sTitle": tablica[i], "sWidth": szerokosc[i]};
        zwrot.push(o1);
    }
    return zwrot;
};

//pola do obrobki musza miec class czekedycja
var showhidecheckbuttons = function (checkbox, hs) {
    if (MYAPP.clickpole && MYAPP.clickpole !== checkbox) {//to sluzy do ukrywania poprzedniego
        resetline();
    }
    MYAPP.clickpole = checkbox;
    hideshow(checkbox,hs);
};

var resetline = function() {
    hideshow(MYAPP.clickpole,"hide");
    MYAPP.clickpole.checked = false;
    delete MYAPP.clickpole;
};

var hideshow = function (checkbox, hs) {
  var inneczekbox = $(checkbox).parent().siblings().find(".czekedycja");
    $(inneczekbox).each(function () {
        if (hs === "show") {
            $(this).show();
        } else {
            this.checked = false;
            $(this).hide();
        }
    });  
};

var pobierztrescpole = function (obj, nazwapola, t) {
    var siostry = $(obj).find('thead th');
    var pozycja = 0;
    if (siostry.length === 0) {
        alert("Blad w pobierztrescpole w common_generowanietabeli.js");
        return;
    }
    for (var i in siostry) {
        if ($(siostry[i]).text() === nazwapola) {
            pozycja = i;
            break;
        }
    }
    ;
    return $($(t).closest('tr').children()[pozycja]).text();
};
//nanosi kliki na wzystkie elementy wykorzystujac podfuncje
var naniesclickzbiorcze = function (nazwatablicy, nazwatablicyedycja, nazwapola) {
    var uTable = $(nazwatablicy).dataTable();
    uTable.on('search.dt', function () {
        nanieszdarzenieclick1(nazwatablicy, nazwapola);
        nanieszdarzenieclick2(nazwatablicyedycja);
    });
    uTable.on('draw.dt', function () {
        nanieszdarzenieclick1(nazwatablicy, nazwapola);
        nanieszdarzenieclick2(nazwatablicyedycja);
    });
    nanieszdarzenieclick1(nazwatablicy, nazwapola);
    nanieszdarzenieclick2(nazwatablicyedycja);
};

//służy do pokazywania batonow edycji
//nazwatablicy to nazwa tablicy z danymi
var nanieszdarzenieclick1 = function(nazwatablicy, nazwapola) {
    var pola = $(".czekbox");
    $.each(pola, function() {
        nanieszdarzenieclick1_sub(this, nazwatablicy,nazwapola);
    });
};
//nazwa pola sluzy tylko do wyswietlania info, nie ma znaczenia dla edytowania ktore to pole
var nanieszdarzenieclick1_sub = function (pole, nazwatablicy, nazwapola) {
    $(pole).on("click", function() {
        if (this.checked === true) {
            showhidecheckbuttons(pole, "show");
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono ' + pobierztrescpole(nazwatablicy, nazwapola, this)}]);
        } else {
            showhidecheckbuttons(this, "hide");
        }
    });
};
 
//służy do pobierania danych po kliknieciu
//nazwatablicy to nazwa tablicy docelowej
var nanieszdarzenieclick2 = function(nazwatablicy) {
    var pola = $(".czekbox");
    $.each(pola, function() {
        nanieszdarzenieclick2_sub(this, nazwatablicy);
    });
};

var nanieszdarzenieclick2_sub = function(pole, nazwatablicy) {
    $(pole).on("click", function(event) {
        if (this.checked === true) {
            var tresc = pobierzdane(event);
            var elements = pobierzelementy(event);
            wypelnijpolaedycji(nazwatablicy,tresc);
            MYAPP.pola = elements;
            $(this).css('background-color', 'white');
        }
    });
};

//pola do danych musza miec class doedycji
var pobierzdane = function (event) {
    var trwywolujacy = event.currentTarget;
    var currentRow = $(trwywolujacy).closest('tr').children().find(".doedycji");
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize ; i++){
        elements.push($(currentRow[i]).text());
    }
    trwywolujacy = event.currentTarget;
    return elements;
};

var pobierzelementy = function (event) {
    var trwywolujacy = event.currentTarget;
    return $(trwywolujacy).closest('tr').children().find(":not(:checkbox)");
};
 
var wypelnijpolaedycji = function(tabela,tresc) {
    var inputtabela = $(tabela).find(":input");
    var tablesize = inputtabela.length;
    for (var i = 0; i < tablesize ; i++){
        $(inputtabela[i]).val(tresc[i]);
    }
};

var lastcheckbox = function (checkbox) {
    var siostry = $(checkbox).parent().siblings();
    return $(siostry[siostry.size() - 1]).find("input");
};
var vorlastcheckbox = function (checkbox) {
    var siostry = $(checkbox).parent().siblings();
    return $(siostry[siostry.size() - 2]).find("input");
};

//size bez px, nazwa bez #
var tworzkontener = function(nazwatabeli, wraper, size) {
    if (nazwatabeli === undefined) {
        nazwatabeli = 'tabuser';
        wraper = 'tabuser_wrapper';
        size = 700;
    }
    $("#"+nazwatabeli).remove();
    $("#"+wraper).remove();
    var cont = "<table id='"+nazwatabeli+"' class=\"compact context-menu-one box menu-1\"  style=\"margin: 0px; width: "+size+"px;\"></table>";
    $('#tbl').append(cont);  
};

var tworztabele = function (nazwatabeli,tablicanazwykolumn) {
    if (nazwatabeli === undefined) {
        nazwatabeli = '#tabuser';
    } else {
        nazwatabeli = "#"+nazwatabeli;
    }
    return uTable = $(nazwatabeli).dataTable({
        "bDestroy": true,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "aoColumns": generujnazwykolumnogol(tablicanazwykolumn),
        "keys": true,
        "select": "single",
        "language": {
            "url": "resources/dataTableNew/Polish.json"
        },
        "dom": 'lfrBtip',
        "buttons": [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            {
                extend: 'pdfHtml5',
                orientation: 'landscape',
                pageSize: 'A4'
            }
        ]
    });
};

var tworztabelezdanymi = function (nazwatabeli,tablicanazwykolumn, tablicadane) {
    if (nazwatabeli === undefined) {
        nazwatabeli = '#tabuser';
    } else {
        nazwatabeli = "#"+nazwatabeli;
    }
    var uTable = $(nazwatabeli).dataTable({
        "bDestroy": true,
        "bJQueryUI": true,
        paging: false,
        "aoColumns": generujnazwykolumnogol(tablicanazwykolumn),
        "select": "single",
        "language": {
            "url": "resources/dataTableNew/Polish.json"
        },
    });
    uTable.fnAddData(tablicadane);
    return uTable;
};

var przetworztabele = function(tabela) {
    var wartosci = new Array();
    tabela.forEach(function (item) {
        wartosci.push(new Array(item));
    });
    return wartosci;
};

var dolacztabele = function(doczego, nazwatabeli, szerokosc) {
  var cont = "<div style=\"width: "+szerokosc+"px;\"><table id=\""+nazwatabeli+"\" class=\"compact \"  style=\"margin: 0px; width: "+szerokosc+"px;\"></table></div>";
  $('#'+doczego+'').append(cont);  
};

var generujnazwykolumnogol = function (tablicanazwykolumn) {
    var zwrot = new Array();
    for (var val of tablicanazwykolumn) {
        zwrot.push({"sTitle": ""+val+""});
    };
    return zwrot;
};