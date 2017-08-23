///* 
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
$(document).ready(function() {
    var uTable = $('#tabszkoleniewykaz').dataTable(
        {
            "bJQueryUI": true, 
            "sPaginationType": "full_numbers",
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
        uTable.fnSort([[1, 'desc']]);
        naniesclickzbiorcze('#tabszkoleniewykaz','#tabelaeditszkoleniewykaz','nazwa')
        podswietlmenu(rj('menuszkolenia'));
        $(":input:not(:checkbox):not(:button)").puiinputtext();
    });
   



 var noweszkoleniewykaz = function() {
    $("#newszkoleniewykaz").puidialog({
        height: 260,
        width: 480,
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#newszkoleniewykaz").puidialog('show');
};


var dodajnoweszkoleniewykaz = function() {
    var uTable = $('#tabszkoleniewykaz').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox'/>",
    ostatninumer(),
    rj("Nszkolenia").value,
    rj("Nskrot").value,
    rj("Nopis").value,
    rj("Nzaswiadczenie").value,
    rj("Ninstrukcja").value,
    "<input title='edytuj' value='edytuj' type='checkbox'  onclick='editszkoleniewykaz(this);' class='buttonedytujuser' style='display: none;'/>",
    "<input title='usuń'  value='usuń' type='checkbox'  onclick='usunszkoleniewykaz(this);' style='display: none;'/>"
    ]);
    $("#newszkoleniewykaz").puidialog('hide'); 
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy rodzaj szkolenia'}]);
    uTable.fnSort([[1, 'desc']]);
    uTable.fnDraw();
    var teststring = "Nszkolenia="+rj("Nszkolenia").value+"&Ninstrukcja="+rj("Ninstrukcja").value+"&Nopis="+rj("Nopis").value+"&Nskrot="+rj("Nskrot").value+"&Nzaswiadczenie="+rj("Nzaswiadczenie").value+"&plik=admin112014_firma_ust.php";
    $.ajax({
        type: "POST",
        url: "newszkoleniewykaz_1112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            uzupelnijnumer(response);
            uTable.fnSort([[1, 'desc']]);
            uTable.fnDraw();
            nanieszdarzenieclick1('#tabszkoleniewykaz','nazwa');
            nanieszdarzenieclick2('#tabelaeditszkoleniewykaz');
        }
    });
};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabszkoleniewykaz').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};

var ostatninumer = function () {
        var uTable = $('#tabszkoleniewykaz').dataTable();
        var nNodes = uTable.fnGetNodes();
        var max = 0;
        for (var il in nNodes) {
            try {
                var id = parseInt($($($(nNodes)[il]).find("td")[1]).text());
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
    var innewiersze = $("#tabszkoleniewykaz").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[6].children[0].style.display = "none";
                wiersz[7].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
};

var usunszkoleniewykaz = function (obj) {
    var uTable = $('#tabszkoleniewykaz').dataTable();
    var aPos = uTable.fnGetPosition(obj.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto szkolenie'}]);
    var teststring = "Nid="+$(aData[1]).text();
    $.ajax({
        type: "POST",
        url: "usunszkoleniewykaz_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
        }
    });
};

var editszkoleniewykaz = function(event) {
    var s = event.target;
    if( $(s).is(':checked') ) {
        $("#editszkoleniewykaz").puidialog({
            height: 260,
            width: 480,
            scrollbars: false,
            resizable: false,
            showEffect: 'fade',
            hideEffect: 'fade',
            location: "center", 
            modal: true
        });
        MYAPP.editszkolenie_checkbox = s;
        $("#editszkoleniewykaz").puidialog('show');
    }
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

var edytujtabeleszkoleniewykaz = function () {
    $(MYAPP.pola[1]).html($('#szkolenia').val());
    $(MYAPP.pola[2]).html($('#skrot').val());
    $(MYAPP.pola[3]).html($('#opis').val());
    $(MYAPP.pola[4]).html($('#zaswiadczenie').val());
    $(MYAPP.pola[5]).html($('#instrukcja').val());
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane szkolenia ' + $("#szkolenia").val() + ' ' + $("#opis").val() + ' zmienione'}]);
    $("#editszkoleniewykaz").puidialog('hide');
    var teststring = "Nid=" + $('#idszkolenie').val() + "&Nszkolenia=" + $('#szkolenia').val() + "&Nopis=" + $('#opis').val() + "&Nskrot=" + rj("skrot").value + "&Nzaswiadczenie="+rj("zaswiadczenie").value+ "&Ninstrukcja="+rj("instrukcja").value;
    $.ajax({
        type: "POST",
        url: "editszkoleniewykaz_112014.php",
        data: teststring,
        cache: false
    });
    resetline();
};

var canceledytujszkolenie = function () {
    $('#editszkoleniewykaz').puidialog('hide');
    resetline();
}; 

var cancelnoweszkolenie = function () {
    $('#newszkoleniewykaz').puidialog('hide');
};