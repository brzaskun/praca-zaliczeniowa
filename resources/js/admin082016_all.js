window.onload = function () {
    $("#ajax_sun").show();
};
$(document).ready(function () {
    generujtabliceuzytkownikow();
    podswietlmenu(rj('menuduzatablica'));
    $('#notify').puigrowl({
        life: 6000
    });
    $('#mb1').puimenubar({
        autoDisplay: true
    });
});

var generujtabliceuzytkownikow = function () {
    var teststring = {"firmanazwa": "wszystkiefirmy"};
    $.ajax({
        type: "POST",
        url: "pobierzuczestnicywszyscy_112014_bigtable.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            let wiadomosc = 'Nie udało się pobrać danych użytkowników. '+error;
            $('#notify').puigrowl('show', [{severity: 'error', summary: wiadomosc}]);
        },
        success: function (response) {
            if (response !== "brak") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano dane użytkowników'}]);
                var tablice = $.parseJSON(decodeURIComponent(response));
                $('#tabuser').remove();
                $('#tabuser_wrapper').remove();
                var cont = "<table id=\"tabuser\" onload=\"dopelnijtabele();\" class=\"compact context-menu-one box menu-1\"  style=\"margin: 0px; width: 1460px;\"></table>";
                $('#tbl').append(cont);
                if (tablice.length > 0) {
                    var uTable = $('#tabuser').dataTable({
                        "bDestroy": true,
                        "bJQueryUI": true,
                        "sPaginationType": "full_numbers",
                        "aoColumns": generujnazwykolumn(),
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
                    if ($("#aktywnafirma").val() === null || $("#aktywnafirma").val() === "wybierz bieżącą firmę") {
                        $(".dt-buttons").hide();
                        uTable.fnSort([[0, 'desc']]);
                    } else {
                        $(".dt-buttons").show();
                        uTable.fnSort([[1, 'asc']]);
                    }
                }
                naniesclickzbiorcze("#tabuser","#tabelaedituser","email");
            }
            $("#ajax_sun").hide();
        }
    });
};

var generujnazwykolumn = function () {
    var zwrot = new Array();
    var o1 = {"sTitle": "id"};
    zwrot.push(o1);
    o1 = {"sTitle": "email", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "imię i nazwisko", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "płeć", "sWidth": "10px", "sClass": "tabela_short"};
    zwrot.push(o1);
    o1 = {"sTitle": "firma", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "szkolenie"};
    zwrot.push(o1);
    o1 = {"sTitle": "uprawnienia"};
    zwrot.push(o1);
    o1 = {"sTitle": "il. log."};
    zwrot.push(o1);
    o1 = {"sTitle": "wysł. link"};
    zwrot.push(o1);
    o1 = {"sTitle": "rozpoczęcie"};
    zwrot.push(o1);
    o1 = {"sTitle": "zakończenie"};
    zwrot.push(o1);
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
    $(MYAPP.pola[4]).html($('#firmausernazwa').val());
    $(MYAPP.pola[5]).html($('#szkolenieuser').val());
    $(MYAPP.pola[6]).html($('#uprawnieniauser').val());
    $(MYAPP.pola[9]).html($('#datazalogowania').val());
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
        teststring = "iduser=" + $('#iduser').val() + "&email=" + $('#email').val() + "&imienazwisko=" + $('#imienazwisko').val() + "&datazalogowania=" + $('#datazalogowania').val() + "&szkolenieuser=" + $('#szkolenieuser').val() + "&uprawnieniauser=" + $('#uprawnieniauser').val() + "&firmausernazwa=" + $('#firmausernazwa').val() + "&plecuser=" + $('#plecuser').val();
    } else {
        teststring = "iduser=" + $('#iduser').val() + "&email=" + $('#email').val() + "&imienazwisko=" + $('#imienazwisko').val() + "&szkolenieuser=" + $('#szkolenieuser').val() + "&uprawnieniauser=" + $('#uprawnieniauser').val() + "&firmausernazwa=" + $('#firmausernazwa').val() + "&plecuser=" + $('#plecuser').val();
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

var validateEmail = function(email) {
    // http://stackoverflow.com/a/46181/11236
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};