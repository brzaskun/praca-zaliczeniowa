$(document).ready(function () {
    generujtabliceuzytkownikow();
    pobierzmaile();
    podswietlmenu(rj('menuuzytkownicy'));
});

$(document).ready(function () {
    $('#aktywnafirma').puidropdown({
        filter: true,
        change: function (e) {
            generujtabliceuzytkownikow();
            $("#Nfirmauser").val($("#aktywnafirma").val());
            if ($("#aktywnafirma").val() !== "wybierz bieżącą firmę") {
                $("#lidodaj").show();
            } else {
                $("#lidodaj").hide();
            }
              
        },
        data: function (callback) {
            $.ajax({
                type: "POST",
                url: "pobierzfirmyJson_112014_act.php",
                dataType: "json",
                context: this,
                success: function (response) {
                    callback.call(this, response);
                }
            });
        }
    });
    $('#zachowajbutton').puibutton();
    $('#eksportbutton').puibutton();
     $('#warunek1').puidropdown({
        change: function (e) {
          generujtabliceuzytkownikow();
        },
        style: {
            "width": "340px;"
        }
     });
    $('#warunek1div').hide();
});

//var dopelnijtabele = function () {
//    var tabela = $("#tabuser").dataTable();
//    tabela.$(".czekboks").on("click", function () {
//        var parent = this.parentNode.parentNode.children;
//        czyscinnewiersze(this.parentNode);
//        try {
//            if (parent[13].children[0].checked === true) {
//                parent[14].children[0].style.display = "inline";
//                parent[15].children[0].style.display = "inline";
//                parent[16].children[0].style.display = "inline";
//            } else {
//                parent[14].children[0].style.display = "none";
//                parent[15].children[0].style.display = "none";
//                parent[16].children[0].style.display = "none";
//            }
//        } catch (ex) {
//        }
//    });
//    var nNodes = tabela.fnGetNodes();
//    var dlugosc = nNodes.length;
//    for (var i = 0; i < dlugosc; i++) {
//        $(nNodes[i]).click(function (event) {
//            var tresc = pobierzdane(event);
//            var elementy = pobierzelementy(event);
//            var czykliknieto = $(elementy[13]).children().get(0).checked;
//            if (czykliknieto) {
//                $("#iduser").val(tresc[0]);
//                $("#email").val(tresc[1]);
//                $("#imienazwisko").val(tresc[2]);
//                $("#plecuser").val(tresc[3]);
//                $("#firmausernazwa").val(tresc[4]);
//                $('#szkolenieuser').val(tresc[5]);
//                $('#uprawnieniauser').val(tresc[6]);
//                $("#datazalogowania").val(tresc[9]);
//                MYAPP.pola = $(this).find('td');
//                $(this).css('background-color', 'white');
//            } else {
//                //$('#notify').puigrowl('show', [{severity: 'info', summary: 'Odznaczono użytkownika ' + tresc[2] }]);
//            }
//            //('#wiadomoscuserjs').html('Użytkownik '+$("#email").val()+' wybrany do edycji');
//        });
//    }
//};

var czyscinnewiersze = function (parent) {
    var innewiersze = $("#tabuser").find("tr");
    for (var numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent.parentNode) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[13].children[0].checked = false;
                wiersz[14].children[0].style.display = "none";
                wiersz[15].children[0].style.display = "none";
                wiersz[16].children[0].style.display = "none";
            } catch (ex) {

            }
        }
    }
};

//tablica z primeui jest niedopracowana moze pozniejsze werjse 31-07-2016
//var generujtabliceuzytkownikow = function () {
//    var nazwafirmy = $("#aktywnafirma").val();
//    var dane = {"firmanazwa": nazwafirmy};
//    $('#tabuser').remove();
//    $('#tabuser_wrapper').remove();
//    var cont = "<table id=\"tabuser\" onload=\"dopelnijtabele();\" class=\"hovertable context-menu-one box menu-1\"  style=\"margin: 0px; width: 1460px;\"></table>";
//    $('#tbl').append(cont);
//    $('#tabuser').puidatatable({
//        caption: 'tabela uczestników',
//        paginator: {
//            rows: 15
//        },
//        columns: generujnazwykolumn(),
//        datasource: function (callback) {
//            $.ajax({
//                type: "POST",
//                url: "pobierzuczestnicywszyscy_112014.php",
//                data: dane,
//                dataType: "json",
//                context: this,
//                error: function (xhr, status, error) {
//                    $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać danych użytkowników.'}]);
//                },
//                success: function (response) {
//                    //var tablice = $.parseJSON(decodeURIComponent(response));
//                    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano dane użytkowników'}]);
//                    callback.call(this, response);
//                },
//                complete: function () {
//                     dopelnijtabele();
//                }
//            });
//        }
//    });
//    $.ajax({
//        type: "POST",
//        url: "pobierzuczestnicywszyscy_112014.php",
//        data: dane,
//        cache: false,
//        error: function(xhr, status, error) { 
//            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać danych użytkowników.'}]);
//        },
//        success: function(response){
//            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano dane użytkowników'}]);
//            var tablice = $.parseJSON(decodeURIComponent(response));
//            $('#tabuser').remove();
//            $('#tabuser_wrapper').remove();
//            var cont = "<table id=\"tabuser\" onload=\"dopelnijtabele();\" class=\"hovertable context-menu-one box menu-1\"  style=\"margin: 0px; width: 1460px;\"></table>";
//            $('#tbl').append(cont);
//            if (tablice.length > 0) {
//                var uTable = $('#tabuser').dataTable({
//                    "bDestroy": true,
//                    "bJQueryUI": true,
//                    "sPaginationType": "full_numbers",
//                    "processing": true,
//                    "aoColumns": generujnazwykolumn()
//                });
//                uTable.fnAddData(tablice);
//                if ($("#aktywnafirma").val() === null || $("#aktywnafirma").val() === "wybierz bieżącą firmę") {
//                    uTable.fnSort([[0, 'desc']]);
//                } else {
//                    uTable.fnSort([[1, 'asc']]);
//                }
//                dopelnijtabele();
//            }
//        }
//      });
//    $("#zachowajbutton").show();
//};

var generujtabliceuzytkownikow = function () {
    var nazwafirmy = $("#aktywnafirma").val();
    var uczestnicyrodzaj = document.getElementById("warunek1").value;
    MYAPP.uczestnicyrodzaj = uczestnicyrodzaj;
    var teststring = {"firmanazwa": nazwafirmy, "uczestnicyrodzaj":uczestnicyrodzaj};
    $.ajax({
        type: "POST",
        url: "pobierzuczestnicywszyscy_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać danych użytkowników.'}]);
        },
        success: function (response) {
            if (response !== "brak") {
                $('#warunek1div').show();
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano dane użytkowników'}]);
                var tablice = $.parseJSON(decodeURIComponent(response));
                $('#tabuser').remove();
                $('#tabuser_wrapper').remove();
                var cont = "<table id=\"tabuser\" class=\"compact \"  style=\"margin: 0px; width: 1460px;\"></table>";
                $('#tbl').append(cont);
                if (tablice.length > 0) {
                    var uTable = $('#tabuser').dataTable({
                        "bDestroy": true,
                        "bJQueryUI": true,
                        "sPaginationType": "full_numbers",
                        "aoColumns": generujnazwykolumn(uczestnicyrodzaj),
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
                    uTable.fnAddData(tablice);
                    $(uTable).addClass("compact");
                    if ($("#aktywnafirma").val() === null || $("#aktywnafirma").val() === "wybierz bieżącą firmę") {
                        $(".dt-buttons").hide();
                        uTable.fnSort([[1, 'desc']]);
                    } else {
                        $(".dt-buttons").show();
                        uTable.fnSort([[2, 'asc']]);
                    }
                    uTable.on( 'search.dt', function () {
                        nanieszdarzenieclick1('#tabuser', 'email');
                        nanieszdarzenieclick2('#tabelaedituser'); 
                    });
                    uTable.on( 'draw.dt', function () {
                        nanieszdarzenieclick1('#tabuser', 'email');
                        nanieszdarzenieclick2('#tabelaedituser'); 
                    });
                    nanieszdarzenieclick1('#tabuser', 'email');
                    nanieszdarzenieclick2('#tabelaedituser');
                    $('#tabuser tbody').on('mouseover', 'td.details-control', function () {
                        var tr = $(this).closest('tr');
                        var row = uTable.api().row(tr);
                        var id_uz = $(row.data()[1]).text();
                        $.ajax({
                            type: "POST",
                            url: "pobierzuczestnikarchiwum.php",
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
                                    if (tab.length > 0) {
                                        var tavlica = format(tab);
                                        row.child(tavlica).show();
                                        tr.addClass('shown');
                                        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano archiwalne szkolenia użytkownika'}]);
                                    } else {
                                        $('#notify').puigrowl('show', [{severity: 'error', summary: 'Użytkownik nie ukończył jeszcze żadnego szkolenia'}]);
                                    }
                                }
                            }
                        }); 
                    });
                    //dopelnijtabele();
                }
            }
        }
    });
    $("#zachowajbutton").show();
};
var format = function (d) {
    var tabela = '<table class="tabelasub compact" cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
            '<tr>'+
            '<th>lp</th>'+
            '<th>nazwa szkolenia</th>'+
            '<th>rozpoczęcie</th>'+
            '<th>zakończenie</th>'+
            '<th>ilość logowań</th>'+
            '<th>wynik testu</th>'+
            '<th>ilość odpowiedzi</th>'+
            '<th>ilość poprawnych</th>'+
            '<th>ilość blednych</th>'+
            '<th>nr upoważnienia</th>'+
            '<th>indentyfikator</th>'+
            '<th>data nadania</th>'+
            '<th>data ustania</th>'+
            '<th>wyslane up</th>'+
            '<th>zarchiwizowano</th>'+
            '<th></th>'+
            '</tr>';
    for (var i = 0; i < d.length; i++){
        tabela = tabela+wiersz(d[i], i);
    };
    tabela = tabela+'</table>';
    
    // `d` is the original data object for the row
    return tabela;
};

var wiersz = function(w, i) {
  var wiersz = '<tr>' +
        '<td>' + w['id'] + '</td>' +
        '<td>' + w['nazwaszkolenia'] + '</td>' +
        '<td>' + w['sessionstart'] + '</td>' +
        '<td>' + w['sessionend'] + '</td>' +
        '<td style="text-align:center">' + w['ilosclogowan'] + '</td>' +
        '<td style="text-align:center">' + w['wyniktestu'] + '</td>' +
        '<td style="text-align:center">' + w['iloscodpowiedzi'] + '</td>' +
        '<td style="text-align:center">' + w['iloscpoprawnych'] + '</td>' +
        '<td style="text-align:center">' + w['iloscblednych'] + '</td>' +
        '<td>' + w['nrupowaznienia'] + '</td>' +
        '<td>' + w['indetyfikator'] + '</td>' +
        '<td>' + w['datanadania'] + '</td>' +
        '<td>' + w['dataustania'] + '</td>' +
        '<td style="text-align:center">' + w['wyslaneup'] + '</td>' +
        '<td>' + w['data'] + '</td>' +
        '<td><input type="button" value="usuń" onclick="usunwierszsub(this,'+w['id']+')"></td>' +
        '</tr>';
  return wiersz;
};

var usunwierszsub =function(td, id) {
    $(td).closest("tr").remove();
    $.ajax({
        type: "POST",
        url: "usunuser_archiwum.php",
        data: "id="+id,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się usunąć zapisu z archiwum'}]);
        },
        success: function (response) {
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto zapis z archiwum'}]);
        }
    });
};
//var generujnazwykolumn = function() {
//    var zwrot = [];
//    var o1 = {field : "id", headerText: "id", headerStyle: "width: 40px; ", bodyStyle: "height: 40px;", sortable: true};
//    zwrot.push(o1);
//    o1 = {field : "email", headerText: "email", headerStyle: "width: 140px;", filter: "true", filterMatchMode: "contains",
//        bodyStyle: "word-break: break-all; word-wrap: break-word !important; white-space: pre-wrap;"};
//    zwrot.push(o1);
//    o1 = {field : "imieinazwisko", headerText: "imię i nazwisko", filter: "true", filterMatchMode: "contains",
//        headerStyle: "width: 140px;", bodyStyle: "word-break: break-all; word-wrap: break-word !important; white-space: pre-wrap;"};
//    zwrot.push(o1);
//    o1 = {field: "plec", headerText: "płeć", headerStyle: "width: 40px;", sortable: true};
//    zwrot.push(o1);
//    o1 = {field: "firma", headerText: "firma", headerStyle: "width: 240px;", filter: "true", filterMatchMode: "contains",};
//    zwrot.push(o1);
//    o1 = {field: "szkolenie", headerText: "szkolenie", headerStyle: "width: 140px;", filter: "true", filterMatchMode: "contains",
//        bodyStyle: "word-break: break-all; word-wrap: break-word !important; white-space: pre-wrap;"};
//    zwrot.push(o1);
//    o1 = {field: "uprawnienia", headerText: "uprawnienia", filter: "true", filterMatchMode: "contains",};
//    zwrot.push(o1);
//    o1 = {field: "illog", headerText: "il. log.", headerStyle: "width: 40px;", sortable: true};
//    zwrot.push(o1);
//    o1 = {field: "link", headerText: "wysł. link", headerStyle: "width: 40px;", sortable: true};
//    zwrot.push(o1);
//    o1 = {field: "rozpoczecie", headerText: "rozpoczęcie", headerStyle: "width: 90px;"};
//    zwrot.push(o1);
//    o1 = {field: "zakonczenie", headerText: "zakończenie", headerStyle: "width: 90px;"};
//    zwrot.push(o1);
//    o1 = {field: "test", headerText: "wyn. test", headerStyle: "width: 40px;"};
//    zwrot.push(o1);
//    o1 = {field: "certyfikat", headerText: "wysł. cert.", headerStyle: "width: 40px;"};
//    zwrot.push(o1);
//    o1 = {field: "zaznacz", headerStyle: "width: 40px;", bodyStyle: "text-align: center;", content: function(rowData) {
//        return rowData['zaznacz'];
//    }};
//    zwrot.push(o1);
//    o1 = {field: "edytuj", headerStyle: "width: 60px;", bodyStyle: "text-align: center;",  content: function(rowData) {
//        return rowData['edytuj'];
//    }};
//    zwrot.push(o1);
//    o1 = {field: "reset", headerStyle: "width: 60px;", bodyStyle: "text-align: center;",  content: function(rowData) {
//        return rowData['reset'];
//    }};
//    zwrot.push(o1);
//    o1 = {field: "usun", headerStyle: "width: 60px;", bodyStyle: "text-align: center;",  content: function(rowData) {
//        return rowData['usun'];
//    }};
//    zwrot.push(o1);
//    return zwrot;
//};

var generujnazwykolumn = function (uczestnicyrodzaj) {
    var zwrot = new Array();
    //plusik do detali
    var o1 = {"sTitle": "",
        "className": 'details-control',
        "orderable": false,
        "data": null,
        "defaultContent": ''
    };
    zwrot.push(o1);
    var o1 = {"sTitle": "id"};
    zwrot.push(o1);
    o1 = {"sTitle": "email", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "imię i nazwisko", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "płeć", "sWidth": "10px", "sClass": "tabela_short"};
    zwrot.push(o1);
//    o1 = {"sTitle": "firma", "sClass": "tabela_email"};
//    zwrot.push(o1);
    o1 = {"sTitle": "szkolenie", "sWidth": "250px",};
    zwrot.push(o1);
    o1 = {"sTitle": "uprawnienia"};
    zwrot.push(o1);
    o1 = {"sTitle": "il. log."};
    zwrot.push(o1);
    o1 = {"sTitle": "wysł. link"};
    zwrot.push(o1);
    if (uczestnicyrodzaj === "archiwalni") {
        o1 = {"sTitle": "data nadania"};
        zwrot.push(o1);
        o1 = {"sTitle": "data ustania"};
        zwrot.push(o1);
    } else {
        o1 = {"sTitle": "rozpoczęcie"};
        zwrot.push(o1);
        o1 = {"sTitle": "zakończenie"};
        zwrot.push(o1);
    }
    o1 = {"sTitle": "wyn. test"};
    zwrot.push(o1);
    o1 = {"sTitle": "wysł. cert."};
    zwrot.push(o1);
    o1 = {"sTitle": ""};
    zwrot.push(o1);
    o1 = {"sTitle": "edytuj"};
    zwrot.push(o1);
    o1 = {"sTitle": "reset"};
    zwrot.push(o1);
    o1 = {"sTitle": "usuń"};
    zwrot.push(o1);
    return zwrot;
};

var pobierzmaile = function () {
    delete MYAPP.emaile;
    $.ajax({
        url: "pobierzmaile_112014.php",
        cache: false,
        success: function (result) {
            MYAPP.emaile = $.parseJSON(decodeURIComponent(result));
        }
    });
};

var nowyuser = function () {
    $("#newuser").puidialog({
        height: 320,
        width: 400,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#newuser").puidialog('show');
};

var canceluser = function () {
    $('#newuser').puidialog('hide');
};

var edituser = function (obj) {
    $("#edituser").puidialog({
        height: 370,
        width: 420,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#edituser").puidialog('show');
};

var canceledytujtabeleuser = function () {
    $('#edituser').puidialog('hide');
    resetline();
};

var edytujtabeleuser = function () {
    $(MYAPP.pola[1]).html($('#email').val());
    $(MYAPP.pola[2]).html($('#imienazwisko').val());
    $(MYAPP.pola[3]).html($('#plecuser').val());
    $(MYAPP.pola[4]).html($('#szkolenieuser').val());
    $(MYAPP.pola[5]).html($('#uprawnieniauser').val());
    $(MYAPP.pola[8]).html($('#datazalogowania').val());
    var wynikwalidacji = validateEmail($('#email').val());
    if (wynikwalidacji === false) {
        $('#notify').puigrowl('show', [{severity: 'error', summary: 'Zły mail ' + $("#email").val() + ''}]);
    } else {
        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane uzytkownika ' + $("#email").val() + ' zmienione'}]);
    }
    ;
    $("#edituser").puidialog('hide');
    var teststring;
    if ($('#datazalogowania').val() !== null && $('#datazalogowania').val() !== "") {
        teststring = "iduser=" + $('#iduser').val() + "&email=" + $('#email').val() + "&imienazwisko=" + $('#imienazwisko').val() + "&datazalogowania=" + $('#datazalogowania').val() + "&szkolenieuser=" + $('#szkolenieuser').val() + "&uprawnieniauser=" + $('#uprawnieniauser').val() + "&firmausernazwa=" + $('#aktywnafirma').val() + "&plecuser=" + $('#plecuser').val();
    } else {
        teststring = "iduser=" + $('#iduser').val() + "&email=" + $('#email').val() + "&imienazwisko=" + $('#imienazwisko').val() + "&szkolenieuser=" + $('#szkolenieuser').val() + "&uprawnieniauser=" + $('#uprawnieniauser').val() + "&firmausernazwa=" + $('#aktywnafirma').val() + "&plecuser=" + $('#plecuser').val();
    }
    $.ajax({
        type: "POST",
        url: "edituser_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownika'}]);
        },
        success: function (response) {
            if (response === "0") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Edytowano nowego użytkownika'}]);
            } else {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownika. Sprawdź czy nie duplikują się nazwy szkoleń.'}]);
            }
        }
    });
    resetline();
};

var weryfikujmaila = function () {
    var email = $('#Nemail').val();
    var wynikwalidacji = validateEmail(email);
    if (wynikwalidacji === false) {
        $('#notify').puigrowl('show', [{severity: 'error', summary: 'Niekompletny adres mail ' + email}]);
        $('#Nemail').css({
            color: "blue"
        });
        //$("#Ndodajuser").hide();
    } else {
        $('#Nemail').css({
            color: "black"
        });
        $("#Ndodajuser").show();
        var uzytkownicy = (MYAPP.emaile).includes_M(email.toLowerCase(), 'email');
        if (uzytkownicy.length > 0) {
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Mail uczestnika ' + email + ' istnieje już w bazie. Możesz dodać go jedynie z wraz innym szkoleniem.'}]);
            blokujusera(uzytkownicy);
            MYAPP.nazwaszkolenia = [];
            for (var i in uzytkownicy) {
                MYAPP.nazwaszkolenia.push(uzytkownicy[i]['nazwaszkolenia']);
                if (parseInt(i) === uzytkownicy.length - 1) {
                    break;
                }
            }
            //$("#Ndodajuser").hide();
        } else {
            odblokujusera();
            $("#Ndodajuser").show();
        }
    }
    ;
};

var odblokujusera = function () {
    $('#Nemail').css({
        color: "black"
    });
    $('#Nimienazwisko').val('');
    $('#Nimienazwisko').prop("disabled", false);
    $('#Nplecuser').val('m');
    $('#Nplecuser').prop("disabled", false);
    $('#Nuprawnieniauser').val('uzytkownik');
    $('#Nuprawnieniauser').prop("disabled", false);
    if (MYAPP.nazwaszkolenia) {
        for (var i in MYAPP.nazwaszkolenia) {
            $("#Nszkolenieuser").append('<option value="' + MYAPP.nazwaszkolenia[i] + '">' + MYAPP.nazwaszkolenia[i] + '</option>');
        }
        $("#Nszkolenieuser").each(function () {
            // Keep track of the selected option.
            var selectedValue = $(this).val();

            // Sort all the options by text. I could easily sort these by val.
            $(this).html($("option", $(this)).sort(function (a, b) {
                return a.text == b.text ? 0 : a.text < b.text ? -1 : 1
            }));

            // Select one option.
            $(this).val(selectedValue);
        });
        delete MYAPP.nazwaszkolenia;
    }
};

var blokujusera = function (uzytkownicy) {
    var uzytkownik = uzytkownicy[0];
    $('#Nemail').css({
        color: "red"
    });
    $('#Nimienazwisko').val(uzytkownik['imienazwisko']);
    $('#Nimienazwisko').prop("disabled", true);
    $('#Nplecuser').val(uzytkownik['plec']);
    $('#Nplecuser').prop("disabled", true);
    $('#Nuprawnieniauser').val(uzytkownik['uprawnienia']);
    $('#Nuprawnieniauser').prop("disabled", true);
    for (var i in uzytkownicy) {
        $("#Nszkolenieuser option[value='" + uzytkownicy[i]['nazwaszkolenia'] + "']").remove();
    }
};

var validateEmail = function (email) {
    // http://stackoverflow.com/a/46181/11236
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
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

var pobierzdanebutton = function (rzad) {
    var currentRow = rzad.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize; i++) {
        elements.push(currentRow[i].innerHTML);
    }
    trwywolujacy = rzad;
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

var pobierzelementybutton = function (rzad) {
    var currentRow = rzad.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize; i++) {
        elements.push(currentRow[i]);
    }
    trwywolujacy = rzad;
    return elements;
};

var usunwiersz = function (button) {
    MYAPP.button = button;
    $("#dialog_user_usun").puidialog({
        height: 100,
        width: 300,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#dialog_user_usun").puidialog('show');
};
var tak_usunwiersz = function () {
    var button = MYAPP.button;
    var uTable = $('#tabuser').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto użytkownika'}]);
    var teststring = "userid=" + aData[1] + "&email=" + aData[2];
    $.ajax({
        type: "POST",
        url: "usunuser_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Wystąpił błąd serera nieusunięto użytkownika'}]);
        },
        success: function (response) {
            if (response === "0") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto użytkownika'}]);
            } else {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się usunąć użytkownika.'}]);
            }
        }
    });
    //trzeba pobrac maile bo inaczej po usunieciu dalej bedzie informowal ze jest podatnik;
    MYAPP.emaile.remove(aData[1]);
    delete MYAPP.button;
    $('#Nemail').css({
        color: "black"
    });
    $("#dialog_user_usun").puidialog('hide');
};

var nie_usunwiersz = function () {
    $("#dialog_user_usun").puidialog('hide');
}; 

var dodajnowyuser = function () {
    var email = rj("Nemail").value;
    var teststring = "Nemail=" + rj("Nemail").value + "&Nimienazwisko=" + rj("Nimienazwisko").value + "&Nplecuser=" + rj("Nplecuser").value + "&Nfirmauser=" + rj("Nfirmauser").value + "&Nszkolenieuser=" + rj("Nszkolenieuser").value + "&Nuprawnieniauser=" + rj("Nuprawnieniauser").value + "&plik=admin112014_uzytkownicy.php";
    $("#ajax_sun").puidialog({
        height: 120,
        width: 150,
        resizable: false,
        closable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#ajax_sun").puidialog('show');
    $.ajax({
        type: "POST",
        url: "newuser_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $("#ajax_sun").puidialog('hide');
            $('#notify').puigrowl('show', [{severity: 'error', summary: xhr.responseText}]);
        },
        success: function (response) {
            if (response === "czas") {
                $("#ajax_sun").puidialog('hide');
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie wysłano maila z linkiem z powodu przekroczenia czasu serwera'}]);
                throw new Exception("Nie wysłano linka do użytkownika");
            } else if (response === "inny") {
                $("#ajax_sun").puidialog('hide');
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Wystąpił błąd przy dodawaniu użytownika. Wysłano informację na maila.'}]);
            } else if (response === "link") {
                $("#ajax_sun").puidialog('hide');
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Dodano użytkownika do bazy, ale nie wysłano linka do użytkownika'}]);
            } else if (response === "update") {
                $("#ajax_sun").puidialog('hide');
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Wysłano mail z instrukcją na adres $email, ale nie udało się zaznaczyć tego w tabeli'}]);
            } else {
                $("#ajax_sun").puidialog('hide');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowego użytkownika'}]);
                MYAPP.emaile.push(email);
                uzupelnijtabele(response);
            }
        }
    });
    $("#newuser").puidialog('hide');
};

var uzupelnijtabele = function (id) {
    var uTable = $('#tabuser').dataTable();
    uTable.fnAddData([
        "<span></span>",
        "<span class='doedycji'>"+id+"</span>",
        "<span class='doedycji'>"+rj("Nemail").value+"</span>",
        "<span class='doedycji'>"+rj("Nimienazwisko").value+"</span>",
        "<span class='doedycji'>"+rj("Nplecuser").value+"</span>",
        "<span class='doedycji'>"+rj("Nszkolenieuser").value+"</span>",
        "<span class='doedycji'>"+rj("Nuprawnieniauser").value+"</span>",
        "<span>0</span>",
        "<span>1</span>",
        "<span class='doedycji'></span>",
        "<span></span>",
        "<span>0</span>",
        "<span>0</span>",
        "<input type='checkbox' class='czekbox'/>",
        "<input title='edytuj' name='edytuj' value='edytuj' type='checkbox'  onclick='edituser(this);' class='czekedycja' style=\"display: none;\"/>",
        "<input title=\"reset\" name=\"reset\" value=\"reset\" type=\"checkbox\" onclick=\"resetujuser(this);\" class='czekedycja' style=\"display: none;\"/>",
        "<input title='usuń' name='usun' value='usuń' type='checkbox'  onclick='usunwiersz(this);' class='czekedycja' style=\"display: none;\"/>"
    ]);
    uTable.fnDraw();
    uTable.fnSort([[1, 'desc']]);
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[0];
    var lista_td = nLastNode.children;
    var cbx = $(lista_td[14]).find(":input")[0];
    nanieszdarzenieclick1_sub(cbx,"#tabuser","email");
    nanieszdarzenieclick2_sub(cbx,"#tabelaedituser");
    rj("Nemail").value = "";
    rj("Nimienazwisko").value = "";
};

var resetujuser = function (button) {
    var przesuniecie = $('#tabuser').DataTable().page.info().page;
    var uTable = $('#tabuser').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    uTable.fnUpdate("0", aPos[0], 7);
    uTable.fnUpdate("0", aPos[0], 8);
    uTable.fnUpdate("", aPos[0], 9);
    uTable.fnUpdate("", aPos[0], 10);
    uTable.fnUpdate("0", aPos[0], 11);
    uTable.fnUpdate("0", aPos[0], 12);
    uTable.fnPageChange(przesuniecie);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zresetowano użytkownika'}]);
    var trescwiersza = pobierzelementybutton(button.parentElement.parentElement);
    var id = trescwiersza[1].textContent;
    $.ajax({
        type: "POST",
        url: "resetpracownika_112014.php",
        data: "id=" + id,
        cache: false,
        success: function(result){
            button.checked = false;
            var butony = $(button).closest("td").siblings().find(".czekedycja");
            $(button).hide();
            $(butony[0]).hide();
            $(butony[1]).hide();
            var butonedit = $(button).closest("td").siblings().find(".czekbox")[0];
            butonedit.checked = false;
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zresetowano dane użytkownika'}]);
        },
        error: function (result) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Wystąpił błąd, nie zresetowano danych użytkownika'}]);
        }
    });
};


var generujtabelexls = function () {
    var nazwafirmy = $("#aktywnafirma").val();
    var teststring = "firmanazwa=" + nazwafirmy+"&uczestnicyrodzaj="+MYAPP.uczestnicyrodzaj;
    $("#ajax_sun").puidialog({
        height: 67,
        width: 150,
        resizable: false,
        closable: false,
        modal: true,
    });
    $("#ajax_sun").puidialog('show');
    $.ajax({
        type: 'POST',
        data: teststring,
        url: 'generujxls.php',
        success: function (data) {
            delete MYAPP.uczestnicyrodzaj;
            $("#ajax_sun").puidialog('hide');
            $("#iframe").attr('src', data);
        },
        error: function (xhr, ajaxOptions, thrownerror) {
            $("#ajax_sun").puidialog('hide');
        }
    });
};