$(document).ready(function() {
    $('#notify').puigrowl({
        life: 6000
    });
    $.ajax({
        type: "POST",
        url: "pobierzzaswiadczenia_072016.php",  
        cache: false,
        error: function(xhr, status, error) { 
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać listy zaświadczeń.'}]);
        },
        success: function(response){
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano listę zaświadczeń'}]);
            var tablice = $.parseJSON(response);
            MYAPP.tablice = tablice;
            var cont = "<table id=\"tabzaswiadczenie\" class=\"context-menu-one box menu-1\"  style=\"margin: 0px; width: 1300px;\"></table>";
            $('#tbl').append(cont);
            var szerokosc = ["1%","2%","12%","7%","7%","9%","9%","9%","7%","8%","28%"];
            if (tablice[0].length > 0) {
                var uTable = $('#tabzaswiadczenie').dataTable({
                    "bDestroy": true, 
                    "bJQueryUI": true,
                    "bAutoWidth": false,
                    "sPaginationType": "full_numbers",
                    "processing": true,
                    "keys": true,
                    "select": "single",
                    "aoColumns": common_generuj_nazwykolumna(tablice[0],szerokosc),
                    "language": {
                        "url": "resources/dataTableNew/Polish.json"
                    },
                    "order": [[1, 'desc']],
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
                uTable.fnAddData(tablice[1]);
                nanieszdarzenieclick1('#tabzaswiadczenie','nazwa');
                nanieszdarzenieclick2('#tabelaeditzaswiadczeniewykaz');
            }
        }
      });
    podswietlmenu(rj('menuzaswiadczenia'));
});

var nowezaswiadczeniewykaz = function() {
    $("#nowezaswiadczeniewykaz").puidialog({
        height: 640,
        width: 550,
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#nowezaswiadczeniewykaz").puidialog('show');
};


var dodajnowezaswiadczeniewykaz = function() {
    var uTable = $('#tabzaswiadczenie').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox' class='czekbox'/>",
    ostatninumer(),
    "<span>"+rj("Nnazwazaswiadczenia").value+"</span>",
    "<span>"+rj("Nskrot").value+"</span>",
    "<span>"+rj("Npoziom").value+"</span>",
    "<span>"+rj("Nlinia1").value+"</span>",
    "<span>"+rj("Nlinia2").value+"</span>",
    "<span>"+rj("Nlinia3").value+"</span>",
    "<span>"+rj("Npdf").value+"</span>", 
    "<span>"+rj("NtrescM").value+"</span>",
    "<span>"+rj("NtrescK").value+"</span>",
    "<input type='button' value='podgląd' onclick='podgladzaswiadczenia(this);'/>",
    "<input type='checkbox' onclick='editzaswiadczenie(event);' style='display: none;'/>",
    "<input type='checkbox' onclick='usunzaswiadczenie(this);' style='display: none;'/>"
    ]);
    $("#nowezaswiadczeniewykaz").puidialog('hide');
    var teststring = "Nnazwazaswiadczenia="+rj("Nnazwazaswiadczenia").value+"&Nskrot="+rj("Nskrot").value+"&Npoziom="+rj("Npoziom").value+
            "&Nlinia1="+rj("Nlinia1").value+"&Nlinia2="+rj("Nlinia2").value+"&Nlinia3="+rj("Nlinia3").value+"&Npdf="+rj("Npdf").value+"&NtrescM="+rj("NtrescM").value+"&NtrescK="+rj("NtrescK").value;
    $.ajax({
        type: "POST",
        url: "newzaswiadczenie_072016.php", 
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { 
            alert('Error: '+ xhr.status+ ' - '+ error); 
        },
        success: function(response){
            if (response !== "błąd") {
                uzupelnijnumer(response);
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy rodzaj zaświadczenia'}]);
            } else {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie dodano. Wystąpił błąd. Sprawdź, czy dane zaświadczenie już nie istnieje w tabeli.'}]);
            }
        }
    });
    nanieszdarzenieclick1('#tabzaswiadczenie','nazwa'); 
    nanieszdarzenieclick2('#tabelaeditzaswiadczeniewykaz');
};



var wypelnijpolawierszapoedycji = function(tabela) {
    var inputtabela = $(tabela).find(":input");
    var tablesize = inputtabela.length;
    MYAPP.trescdoedycji = new Array();
    for (var i = 0; i < tablesize ; i++){
        var label = ""+i;
        MYAPP.trescdoedycji.push($(inputtabela[i]).val());
        $(MYAPP.pola[i]).text($(inputtabela[i]).val());
    }
};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabzaswiadczenie').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};
 
var ostatninumer = function () {
        var uTable = $('#tabzaswiadczenie').dataTable();
        var nNodes = uTable.fnGetNodes();
        var max = 0;
        if (nNodes.length > 0) {
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
        } else {
            return 1;
        };
};

var czyscinnewiersze = function(parent) {
    var innewiersze = $("#tabszkoleniewykaz").find("tr");
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

var usunzaswiadczenie = function (button) {
    MYAPP.button = button;
    $("#dialog_usun").puidialog({
        height: 100,
        width: 300,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true   
    });
    $("#dialog_usun").puidialog('show');
};

var tak_usunwiersz = function () {
    var button = MYAPP.button;
    var uTable = $('#tabzaswiadczenie').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    var data = "Nnazwa="+$(aData[2]).text();
    $.ajax({
        type: "POST",
        url: "usunzaswiadczeniewykaz_072016.php",
        data: data,
        cache: false,
        error: function(xhr, status, error) { 
            alert('Error: '+ xhr.status+ ' - '+ error); 
        },
        success: function(response){
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto zaświadczenie '+aData[2]}]);
            $("#dialog_usun").puidialog('hide');
        }
    });
};

var nie_usunwiersz = function() {
    $("#dialog_usun").puidialog('hide');
};

var editzaswiadczenie = function(event) {
    var s = event.target;
    if( $(s).is(':checked') ) {
        $("#editzaswiadczeniewykaz").puidialog({
            height: 640,
            width: 550,
            scrollbars: false, 
            resizable: false,
            showEffect: 'fade',
            hideEffect: 'fade',
            location: "center", 
            modal: true
        });
        MYAPP.editzaswiadczenie_checkbox = s;
        $("#editzaswiadczeniewykaz").puidialog('show');
    }
};

var canceledytujzaswiadczenie = function() {
    $('#editzaswiadczeniewykaz').puidialog('hide');
    resetline();
};


var edytujzaswiadczeniephp = function () {
    wypelnijpolawierszapoedycji('#tabelaeditzaswiadczeniewykaz');
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane zaświadczenia ' + $(MYAPP.pola[1]).text() + ' ' + $(MYAPP.pola[2]).text() + ' zmienione'}]);
    $(lastcheckbox(MYAPP.clickpole)).hide();
    $(vorlastcheckbox(MYAPP.clickpole)).hide();
    MYAPP.clickpole.checked = false;
    $("#editzaswiadczeniewykaz").puidialog('hide'); 
    $.ajax({
        type: "POST",
        url: "editzaswiadczeniewykaz_072016.php",
        dataType: "json",
        data: "tab="+JSON.stringify(MYAPP.trescdoedycji),
        cache: false
    });
    resetline();
}; 

var podgladzaswiadczenia = function(button) {
    var nazwazaswiadczenia = $(button).closest("tr").find("td:nth-child(3)").text();
    window.open('zaswiadczenie_podglad.php?nazwazaswiadczenia='+nazwazaswiadczenia+'', '_blank'); 
};