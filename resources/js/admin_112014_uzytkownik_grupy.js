$(document).ready(function () {
    var notf = $('#notify').puinotify({
        easing: 'easeInOutCirc',
        position: 'bottom'
    })
    $('#aktywnafirma').puidropdown({
        filter: true,
        change: function (e) {
            wybierzaktywnafirme();
            $(notf).hide();
        },
        data: function (callback) {
            $.ajax({
                type: "POST",
                url: "pobierzfirmyJson_112014.php",
                dataType: "json",
                context: this,
                success: function (response) {
                    callback.call(this, response);
                }
            });
        }
    });
    $("#ajax_sun").puidialog({
        height: 120,
        width: 200,
        resizable: false,
        closable: false,
        modal: true
    });
    $('#zachowajbutton').puibutton();
    $('#eksportbutton').puibutton();
    $('#eksportbutton').hide();
    $('#zachowajbutton').hide();
   
    $('#warunek').puicheckbox({
        change: function (e) {
         wybierzaktywnafirme();
         $(notf).hide();
        }
     });
     $('#warunek1').puidropdown({
        change: function (e) {
         wybierzaktywnafirme();
         $(notf).hide();
        },
        style: {
            "width": "540px;"
        }
     });
    $('#warunekdiv').hide();
    $('#warunek1div').hide();
    
    $('#polegorne').mouseover(function() {
        let nazwafirmy1 = $("#aktywnafirma").val();
        if (nazwafirmy1 === "wybierz bieżącą firmę") {
            $(notf).show();
        };
    });
    podswietlmenu(rj('menuupowaznieniagrupa'));
});


var tableinit2 = function (uTable) {
    $(":input:not(:checkbox):not(:button)").puiinputtext();
};


var wybierzaktywnafirme = function () {
    var nazwafirmy = $("#aktywnafirma").val();
    MYAPP.nazwafirmy = nazwafirmy;
    if (nazwafirmy !== "wybierz bieżącą firmę") {
        var bezgrup = document.getElementById("warunek").checked;
        var uczestnicyrodzaj = document.getElementById("warunek1").value;
        var teststring = "firmanazwa=" + nazwafirmy + "&bezgrup=" +bezgrup + "&uczestnicyrodzaj=" +uczestnicyrodzaj;
        $("#ajax_sun").puidialog('show');
        $.ajax({
            type: "POST",
            url: "pobierzuczestnicy_112014.php",
            data: teststring,
            cache: false,
            error: function (xhr, status, error) {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownika.'}]);
                $("#ajax_sun").puidialog('hide');
            },
            success: function (response) {
                try {
                    var tablice = $.parseJSON(decodeURIComponent(response));
                    MYAPP.tablice = tablice;
                    console.log(tablice[0]);
                    $('#tabuser').remove();
                    $('#tabuser_wrapper').remove();
                    var contener = "<table id=\"tabuser\" class=\"context-menu-one box menu-1\"  style=\"margin: 0px; \"></table>";
                    $('#tbl').find("#komunikatobraku").remove();
                    $('#tbl').append(contener);
                    if (tablice[0].length > 0) {
                        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Uczestnicy z firmy  ' + nazwafirmy}]);
                        var uTable = $('#tabuser').dataTable({
                            "bDestroy": true,
                            "bJQueryUI": true,
                            "sPaginationType": "full_numbers",
                            "aoColumns": nazwykolumnagrupa(tablice[0]),
                            "keys": true,
                            "select": "single",
                            "language": {
                                "url": "resources/dataTableNew/Polish.json"
                            },
                            "drawCallback": function( settings ) {
                                tableinit2(uTable);
                            }
                        });
                        uTable.fnAddData(tablice[1]);
                        uTable.fnSort([[1, 'asc']]);
                        podswietlmenu(rj('menuupowaznieniagrupa'));
                        //tableinit2(uTable);
                        // Add event listener for opening and closing details
                        $(uTable).addClass("compact");
                        $(uTable).on( 'search.dt', function () {
                            wycentruj('#tabuser');
                        } );
                        $('#tabuser tbody').on('click', 'td.details-control', function () {
                            var tr = $(this).closest('tr');
                            var row = uTable.api().row(tr);
                            var id_uz = row.data()[1];
                            $.ajax({
                                type: "POST",
                                url: "pobierzuczestnik.php",
                                data: "id=" + id_uz,
                                context: this,
                                success: function (response) {
                                    var tab = $.parseJSON(response);
                                    if (row.child.isShown()) {
                                        // This row is already open - close it
                                        row.child.hide();
                                        tr.removeClass('shown');
                                    } else {
                                        // Open this row
                                        row.child(format(tab)).show();
                                        tr.addClass('shown');
                                    }
                                }
                            });
                        });
                        wycentruj('#tabuser');
                    }
                    $("#ajax_sun").puidialog('hide');
                    $("#zachowajbutton").show();
                    $("#eksportbutton").show();
                    $('#warunekdiv').show();
                    $('#warunek1div').show();
                } catch (e) {
                    $("#ajax_sun").puidialog('hide');
                    $('#tabuser').remove();
                    $('#tabuser_wrapper').remove();
                    $('#tbl').append("<p id='komunikatobraku'><span> Brak nieprzyporzadkowanych uczestników</span></p>");
                    $('#eksportbutton').hide();
                    $('#zachowajbutton').hide();
                }
            }
        });
    } else {
        $('#tabuser').remove();
        $('#tabuser_wrapper').remove();
        $('#tbl').find("#komunikatobraku").remove();
        $('#zachowajbutton').puibutton();
        $('#eksportbutton').puibutton();
        $('#eksportbutton').hide();
        $('#zachowajbutton').hide();
        $('#warunekdiv').hide();
        $('#warunek1div').hide();
    }
};

var wycentruj = function(tabela) {
    var czekboksy = $(tabela).find(".czekgrupy");
    $.each(czekboksy, function() {
        var par = $(this).parent();
        par.css("text-align", "center");
    });
};

var format = function (d) {
    var nazwaszkolenia = d['nazwaszkolenia'];
    var ilosclogowan = d['ilosclogowan'];
    var ip = d['iplogowania'];
    var sessionstart = d['sessionstart'] !== null ? d['sessionstart'] : "nie rozpoczęto";
    var sessionend = d['sessionend'] !== null ? d['sessionend'] : "nie zakończono";
    var utworzony = d['utworzony'] !== null ? d['utworzony'] : "brak danych";
    var zmodyfikowany = d['zmodyfikowany'] !== null ? d['zmodyfikowany'] : "brak danych";
    var wyniktestu = d['wyniktestu'] !== null ? d['wyniktestu'] : "brak danych";
    var poprawne = d['iloscpoprawnych'] !== null ? d['iloscpoprawnych'] : "";
    var bledne = d['iloscblednych'] !== null ? d['iloscblednych'] : "";
    var ogolem = d['iloscodpowiedzi'] !== null ? d['iloscodpowiedzi'] : "";
    var dataostatniegologowania = d['dataostatniegologowania'] !== null ? d['dataostatniegologowania'] : "";
    var ostatnireset = d['ostatnireset'] !== null ? d['ostatnireset'] : "nieresetowany";
    var wcisnietyklawisz = d['wcisnietyklawisz'] !== null ? d['wcisnietyklawisz'] : "nie";
    
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
            '<tr>' +
            '<td>szkolenie:</td>' +
            '<td>' + nazwaszkolenia + '</td>' +
            '<td>ip logowania</td>' +
            '<td>' + ip + '</td>' +
            '<td>ilość logowań</td>' +
            '<td>' + ilosclogowan + '</td>' +
            '<td>ostatnie logowanie</td>' +
            '<td>' + dataostatniegologowania +'</td>' +
            '</tr>' + 
            '<tr>' +
            '<td>rozpoczeto</td>' +
            '<td>' + sessionstart + '</td>' +
            '<td>zakończono</td>' +
            '<td>' + sessionend + '</td>' +
            '<td>utworzono w bazie</td>' +
            '<td>' + utworzony + '</td>' +
            '<td>zmodyfikowano dane</td>' +
            '<td>' + zmodyfikowany + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>wynik testu</td>' +
            '<td>' + wyniktestu + '</td>' +
            '<td>odpowiedzi: ogół/dobre/złe</td>' +
            '<td>' + ogolem + '/'+ poprawne + '/' + bledne +'</td>' +
            '<td>ostatni reset</td>' +
            '<td>' + ostatnireset + '</td>' +
            '<td>wciśnięto generowanie</td>' +
            '<td>' + wcisnietyklawisz + '</td>' +
            '</tr>' +
            '</table>';
};

var isDataTable = function () {
    var ex = document.getElementById('tabuser');
    if ($.fn.DataTable.fnIsDataTable(ex)) {
        return true;
    }
    return false;
};

var nazwykolumnagrupa = function (tablica) {
    var zwrot = new Array();
    var o0 = {"sTitle": "",
        "className": 'details-control',
        "orderable": false,
        "data": null,
        "defaultContent": ''
    };
    zwrot.push(o0);
    var o1 = {"sTitle": "id"};
    zwrot.push(o1);
    o1 = {"sTitle": "email"};
    zwrot.push(o1);
    o1 = {"sTitle": "imię i nazwisko", "sWidth": "200px"};
    zwrot.push(o1);
    o1 = {"sTitle": "nr upoważnienia"};
    zwrot.push(o1);
    o1 = {"sTitle": "identyfikator"};
    zwrot.push(o1);
    o1 = {"sTitle": "data nadania"};
    zwrot.push(o1);
    o1 = {"sTitle": "data ustania"};
    zwrot.push(o1);
    o1 = {"sTitle": "wysłano up."};
    zwrot.push(o1);
    for (var i = 0; i < tablica.length; i++) {
        o1 = {"sTitle": tablica[i].nazwagrupy};
        zwrot.push(o1);
    }
//    o1 = {"sTitle" : "wybierz"};
//    zwrot.push(o1);
//    o1 = {"sTitle" : "edytuj"};
//    zwrot.push(o1);
    return zwrot;
};

var edytujgrupaupowaznien = function () {
    $(MYAPP.pola[0]).html($('#idgrupa').val());
    $(MYAPP.pola[1]).html($('#firmanazwa').val());
    $(MYAPP.pola[2]).html($('#nazwagrupy').val());
    $("#editnazwagrupy").puidialog('hide');
    var teststring = "idgrupa=" + rj("idgrupa").value + "&firmanazwa=" + rj("firmanazwa").value + "&nazwagrupy=" + rj("nazwagrupy").value;
    $.ajax({
        type: "POST",
        url: "editnazwagrupy_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownika.'}]);
        },
        success: function (response) {
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Edytowano nowego użytkownika'}]);
        }

    });
};


var pobierzdane = function (event) {
    var currentRow = event.currentTarget.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize; i++) {
        elements.push(currentRow[i].innerHTML);
    }
    trwywolujacy = event.currentTarget;
    return elements;
};


var pobierzelementy = function (event) {
    var currentRow = event.currentTarget.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize; i++) {
        elements.push(currentRow[i]);
    }
    trwywolujacy = event.currentTarget;
    return elements;
};

var uzupelnijnumer = function (response) {
    var uTable = $('#tabuser').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length - 1];
    var parent = nLastNode.children;
    parent[0].innerHTML = response;
};

var czyscinnewiersze = function (parent, dl) {
    var innewiersze = $("#tabuser").find("tr");
    for (var numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[dl - 1].children[0].checked = false;
                wiersz[dl].children[0].style.display = "none";
            } catch (ex) {

            }
        }
    }
};

var ostatninumer = function () {
    var uTable = $('#tabuser').dataTable();
    var nNodes = uTable.fnGetNodes();
    var node = nNodes[nNodes.length - 1];
    return parseInt(($(node).children()[0]).innerHTML) + 1;
};

var pobierzwierszetabeli = function () {
    console.log("Pobieram wiersze");
    var wiersze = $("#tabuser").find("tr");
    var nazwygrup = pobierznazwygrup(wiersze[0]);
    var tabelauzgrupa = [];
    for (var i = 1; i < wiersze.length; i++) {
        tabelauzgrupa.push(tworztabeleuzytkownikgrupa(wiersze[i], nazwygrup));
    }
    var dane = JSON.stringify(tabelauzgrupa);
    $.ajax({
        type: "POST",
        url: "edituzytkownikgrupy_112014.php",
        data: "dane=" + dane,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownikagrupa.'}]);
        },
        success: function (response) {
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Edytowano nowego użytkownikagrupa'}]);
        }

    });
};


var pobierznazwygrup = function (wiersz) {
    var children = wiersz.children; 
    var tabela = [];
    for (var i = 9; i < children.length; i++) {
        tabela[i - 9] = children[i].textContent;
    }
    return tabela;
};

var tworztabeleuzytkownikgrupa = function (wiersz, nazwygrup) {
    var td = wiersz.children;
    var item = {};
    item["id"] = td[1].innerText;
    item["email"] = td[2].innerText;
    item["imienazwisko"] = td[3].innerText;
    item["nrupowaznienia"] = td[4].children[0].value;
    item["indetyfikator"] = td[5].children[0].value;
    item["datanadania"] = td[6].children[0].value;
    item["dataustania"] = td[7].children[0].value;
    item["wyslaneup"] = td[8].children[0].value;
    for (var i = 0; i < nazwygrup.length; i++) {
        item[nazwygrup[i]] = td[i + 9].children[0].checked;
    }
    return item;
};

var generujtabeleugxls = function () {
    $("#ajax_sun").puidialog('show');
    $.ajax({
        type: 'POST',
        data: {"naglowek": MYAPP.tablice[0], "tablica": JSON.stringify(MYAPP.tablice[2]), "nazwafirmy":MYAPP.nazwafirmy},
        url: 'generujxls_uzytkownik_grupa.php',
        success: function (data) {
            $("#ajax_sun").puidialog('hide');
            $("#iframe").attr('src', data);
        },
        error: function (xhr, ajaxOptions, thrownerror) {
            $("#ajax_sun").puidialog('hide');
        }
    });
};

//var generujtabeleugxlspuste = function () {
//    $("#ajax_sun").puidialog('show');
//    $.ajax({
//        type: 'POST',
//        data: {"naglowek": MYAPP.tablice[0], "tablica": JSON.stringify(usunpuste(MYAPP.tablice[2]))},
//        url: 'generujxls_uzytkownik_grupa_puste.php',
//        success: function (data) {
//            $("#ajax_sun").puidialog('hide');
//            $("#iframe").attr('src', data);
//        },
//        error: function (xhr, ajaxOptions, thrownerror) {
//            $("#ajax_sun").puidialog('hide');
//        }
//    });
//};

//var usunpuste = function(wiersze) {
//   var zwrot = new Array();
//   wiersze.forEach(function (item){
//       if (typeof item[6] === "string" && item[6].length == 10) {
//           //zeby we wszystkich innyc przypadkach przekazywal czy to null czy pusty string czy niepelna data
//       } else {
//           zwrot.push(item);
//       }
//    });
//    return zwrot;
//};