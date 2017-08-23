/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
//$(document).ready(function() {
//    var uTable = $('#tabszkolenieust').dataTable(
//            {
//                "bJQueryUI": true,
//                "sPaginationType": "full_numbers"
//            }
//    );
//    uTable.fnSort([[2, 'asc'],[3, 'asc']]);
//    $(uTable).addClass("compact");
//    // Get the nodes from the table
//    var nNodes = uTable.fnGetNodes();
//    var dlugosc = nNodes.length;
//    for (var i = 0; i < dlugosc; i++) {
//        $(nNodes[i]).click(function(event) {
//            var tresc = pobierzdane(event);
//            var elementy = pobierzelementy(event);
//            var czykliknieto = $(elementy[0]).children().get(0).checked;
//            if (czykliknieto) {
//                $("#idszkolenieust").val(tresc[1]);
//                $("#firmaszkoleniaust").val(tresc[2]);
//                $("#nazwaszkoleniaust").val(tresc[3]);
//                $("#iloscpytanust").val(tresc[4]);
//                pola = $(this).find('> td');
//                $(this).css('background-color', 'white');
//                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Element ustawień szkolenia: ' + $("#firmaszkoleniaust").val()+ ' ' + $("#nazwaszkoleniaust").val() + ' wybrany do edycji' }]);
//                MYAPP.pola = $(this).find(' > td');
//            }
//        });
//    };
//    podswietlmenu(rj('menufirmy'));
//    $(":input:not(:checkbox):not(:button)").puiinputtext();
//});
//
//var pobierzdane = function (event) {
//    var currentRow = event.currentTarget.children;
//    var elements = [];
//    var i = 0;
//    var tablesize = currentRow.length;
//    for (var i = 0; i < tablesize ; i++){
//        elements.push(currentRow[i].innerHTML);
//    }
//    trwywolujacy = event.currentTarget;
//    return elements;
//};
//
//var pobierzelementy = function (event) {
//    var currentRow = event.currentTarget.children;
//    var elements = [];
//    var i = 0;
//    var tablesize = currentRow.length;
//    for (var i = 0; i < tablesize ; i++){
//        elements.push(currentRow[i]);
//    }
//    trwywolujacy = event.currentTarget;
//    return elements;
//};
$(document).ready(function() {
    generujtabliceuzytkownikow();
});

var generujtabliceuzytkownikow = function () {
    $.ajax({
        type: "POST",
        url: "pobierzszkolenieust.php",
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać danych szkolenieust.'}]);
        },
        success: function (response) {
            if (response !== "brak") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano dane ustawienia szkoleń'}]);
                var tablice = $.parseJSON(decodeURIComponent(response));
                $('#tabuser').remove();
                $('#tabuser_wrapper').remove();
                var cont = "<table id=\"tabuser\" class=\"compact context-menu-one box menu-1\"  style=\"margin: 0px; width: 850px;\"></table>";
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
                    uTable.fnSort([[2, 'asc']]);
                    $(uTable).addClass("compact");
                    naniesclickzbiorcze('#tabuser','#tabelaeditszkolenieust', 'firma');
                }
            }
        }
    });
    $("#zachowajbutton").show();
};

var generujnazwykolumn = function () {
    var zwrot = new Array();
    var o1 = {"sTitle": ""};
    zwrot.push(o1);
    o1 = {"sTitle": "id"};
    zwrot.push(o1);
    o1 = {"sTitle": "firma", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "nazwa szkolenia", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "ilosć pytań", "sClass": "tabela_short"};
    zwrot.push(o1);
    o1 = {"sTitle": "email", "sClass": "tabela_email"};
    zwrot.push(o1);
    o1 = {"sTitle": "próg zdawalności"};
    zwrot.push(o1);
    o1 = {"sTitle": "edytuj"};
    zwrot.push(o1);
    o1 = {"sTitle": "usuń"};
    zwrot.push(o1);
    return zwrot;
};

 var noweszkolenieust = function() {
    $("#newszkolenieust").puidialog({
        height: 200,
        width: 410, 
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#newszkolenieust").puidialog('show');
};

var dodajnoweszkolenieust = function() {
    var teststring = "Nfirmaszkoleniaust="+rj("Nfirmaszkoleniaust").value+"&Nnazwaszkoleniaust="+rj("Nnazwaszkoleniaust").value+"&Niloscpytanust="+rj("Niloscpytanust").value+"&plik=admin112014_firma_ust.php";
    $.ajax({
        type: "POST",
        url: "newszkolenieust_1112014.php",
        data: teststring, 
        cache: false,
        success: function (response) {
            dodajwierszdotabeli('#tabuser', response);
            naniesclickzbiorcze('#tabuser','#tabelaeditszkolenieust','firma');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowe ustawienia szkolenia'}]);
        }
    });
};

var dodajwierszdotabeli = function (tablename, ostatninumer) {
    var uTable = $(tablename).dataTable();
    uTable.fnAddData( [
    "<input type='checkbox' class=\"czekbox\"/>",
    "<span class='doedycji'>"+ostatninumer+"</span>",
    "<span class='doedycji' style='text-align: center;'>"+rj("Nfirmaszkoleniaust").value+"</span>",
    "<span class='doedycji' style='text-align: center;'>"+rj("Nnazwaszkoleniaust").value+"</span>",
    "<span class='doedycji' style='text-align: center;'>"+rj("Niloscpytanust").value+"</span>",
    "<span></span>",
    "<span></span>",
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editszkolenieust(this);'  class='czekedycja' style=\"display: none;\"/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunszkolenieust(this);' class='czekedycja' style=\"display: none;\"/>"
    ]);
    $("#newszkolenieust").puidialog('hide');
    uTable.fnDraw();
    uTable.fnSort([[2, 'asc'],[3, 'asc']]);
};

var ostatninumer = function () {
        var uTable = $('#tabszkolenieust').dataTable();
        var nNodes = uTable.fnGetNodes();
        var max = 0;
        for (var il in nNodes) {
            try {
                var id = parseInt(($(nNodes[il]).children()[1]).innerHTML);
                if (id > max) {
                    max = id;
                }
            } catch (e) {
                break;
            }
        }
    return id+1;
};

var czyscinnewiersze = function(parent) {
    var innewiersze = $("#tabszkolenieust").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[5].children[0].style.display = "none";
                wiersz[6].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
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
    var teststring = "firmaszkoleniaust="+aData[2]+"&nazwaszkoleniaust="+aData[3];
    $.ajax({
        type: "POST",
        url: "usunszkolenieust_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Wystąpił błąd serera nieusuniętoustawień'}]);
        },
        success: function (response) {
            if (response === "0") {
                uTable.fnDeleteRow(aPos[0]);
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto ustawienie szkolenia'}]);
            } else {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się usunąć ustawień.'}]);
            }
        }
    });
    $("#dialog_user_usun").puidialog('hide');
    resetline();
};

var nie_usunwiersz = function () {
    $("#dialog_user_usun").puidialog('hide');
}; 
//var usunszkolenieust = function (button) {
//    var uTable = $('#tabszkolenieust').dataTable();
//    var aPos = uTable.fnGetPosition(button.parentElement);
//    var aData = uTable.fnGetData(aPos[0]);
//    uTable.fnDeleteRow(aPos[0]);
//    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto ustawienie szkolenia'}]);
//    var teststring = "firmaszkoleniaust="+aData[2]+"&nazwaszkoleniaust="+aData[3];
//    $.ajax({
//        type: "POST",
//        url: "usunszkolenieust_112014.php",
//        data: teststring,
//        cache: false
//    });
//};

var editszkolenieust = function(obj) {
    $("#editszkolenieust").puidialog({
        height: 300,
        width: 410, 
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#editszkolenieust").puidialog('show');
};

var canceleditszkolenieust = function () {
    $('#editszkolenieust').puidialog('hide');
    resetline();
};

var pobierzdanebutton = function (rzad){
    var currentRow = rzad.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize ; i++){
        elements.push(currentRow[i].innerHTML);
    }
    trwywolujacy = rzad;
    return elements;
};

var edytujtabeleszkolenieust = function (){
       $(MYAPP.pola[3]).html($('#iloscpytanust').val());
       $(MYAPP.pola[4]).html($('#emailust').val());
       $(MYAPP.pola[5]).html($('#progzdawalnosciust').val());
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane szkolenia ' + $("#firmaszkoleniaust").val()+ ' ' + $("#nazwaszkoleniaust").val() + ' zmienione' }]);
       $("#editszkolenieust").puidialog('hide');
       var teststring = "emailust="+$('#emailust').val()+"&progzdawalnosciust="+$('#progzdawalnosciust').val()+"&iloscpytanust="+$('#iloscpytanust').val()+"&firmaszkoleniaust="+$('#firmaszkoleniaust').val()+"&nazwaszkoleniaust="+$('#nazwaszkoleniaust').val();
       $.ajax({
        type: "POST",
        url: "editszkolenieust_112014.php",
        data: teststring,
        cache: false
    });
    resetline();
};