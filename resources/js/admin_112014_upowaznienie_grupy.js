$(document).ready(function() {
    var uTable = $('#tabuser').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers",
                "processing": true,
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
            } 

    );
    uTable.fnSort([[1, 'asc']]);
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function(event) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[3]).children().get(0).checked;
            if (czykliknieto) {
                $("#idgrupa").val(tresc[0]);
                $("#firmanazwa").val(tresc[1]);
                $("#nazwagrupy").val(tresc[2]);
                MYAPP.pola = $(this).find('td');
                $(this).css('background-color', 'white');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Grupa '+tresc[2]+' wybrana do edycji'}]);
            } else {
                //$('#notify').puigrowl('show', [{severity: 'info', summary: 'Odznaczono użytkownika ' + tresc[2] }]);
            }
        });
    }
    podswietlmenu(rj('menuupowaznieniegrupy'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
 
});


 var nowanazwagrupy = function() {
    $("#nowanazwagrupy").puidialog({
        height: 170,
        width: 400,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#nowanazwagrupy").puidialog('show');
};

 var editnazwagrupy = function(obj) {
    $("#editnazwagrupy").puidialog({
        height: 170,
        width: 420,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#editnazwagrupy").puidialog('show');
};

var edytujgrupaupowaznien = function (){
       $(MYAPP.pola[0]).html($('#idgrupa').val());
       $(MYAPP.pola[1]).html($('#firmanazwa').val());
       $(MYAPP.pola[2]).html($('#nazwagrupy').val());
       $("#editnazwagrupy").puidialog('hide');
       var teststring = "idgrupa="+rj("idgrupa").value+"&firmanazwa="+rj("firmanazwa").value+"&nazwagrupy="+rj("nazwagrupy").value;
       $.ajax({
        type: "POST",
        url: "editnazwagrupy_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { 
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się zmienić danych użytkownika.'}]);
        },
        success: function(response){
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Edytowano nowego użytkownika'}]);
        }
       
    });
};


var pobierzdane = function (event) {
    var currentRow = event.currentTarget.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize ; i++){
        elements.push(currentRow[i].innerHTML);
    }
    trwywolujacy = event.currentTarget;
    return elements;
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

var pobierzelementy = function (event) {
    var currentRow = event.currentTarget.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize ; i++){
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
    for (var i = 0; i < tablesize ; i++){
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
    button = MYAPP.button;
    var uTable = $('#tabuser').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto nazwę grupy'}]);
    var teststring = "id="+aData[0];
    $.ajax({
        type: "POST",
        url: "usunnazwagrupa_112014.php",
        data: teststring,
        cache: false,
    });
    delete MYAPP.button;
    $("#dialog_user_usun").puidialog('hide');
};

var nie_usunwiersz = function() {
    $("#dialog_user_usun").puidialog('hide');
};

var dodajnazwagrupy = function() {
    var teststring = "&Nfirmauser="+rj("Nfirmauser").value+"&Nnazwagrupy="+rj("Nnazwagrupy").value;
     $("#ajax_sun").puidialog({
        height: 67,
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
        url: "newnazwagrupy_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { 
            $("#ajax_sun").puidialog('hide');
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie dodano nowej grupy. Wystąpił błąd.'}]);
        },
        success: function(response){
            $("#ajax_sun").puidialog('hide');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nazwę nowej grupy'}]);
            uzupelnijtabele(response);
        }
    });
    $("#nowanazwagrupy").puidialog('hide');
}; 

var uzupelnijtabele = function (response) {
    var uTable = $('#tabuser').dataTable();
    uTable.fnAddData( [
    response,
    rj("Nfirmauser").value,
    rj("Nnazwagrupy").value,
    "<input type='checkbox'/>",
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editnazwagrupy(this);' class='buttonedytujuser' style=\"display: none;\"/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunwiersz(this);' style=\"display: none;\"/>"
    ]);
    uTable.fnSort([[1, 'desc']]);
    uTable.fnDraw();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    $(parent[1]).css({
        'text-align': "left"
    });
    $(parent[2]).css({
        'text-align': "left"
    });
    $(parent[3]).css({
        'text-align': "center"
    });
    $(parent[4]).css({
        'text-align': "center",
        'width': '5%'
    });
    $(parent[5]).css({
        'text-align': "center",
        'width': '5%'
    });
    $(nLastNode).addClass("czekboks");
    $(nLastNode).on("click", function() {
        var parent = nLastNode.children;
        czyscinnewiersze(nLastNode);
        if (parent[3].children[0].checked === true) {
            parent[4].children[0].style.display = "inline";
            parent[5].children[0].style.display = "inline";
        } else {
            parent[4].children[0].style.display = "none";
            parent[5].children[0].style.display = "none";
        }
    });
    $(nLastNode).on("click", function(event) {
        var tresc = pobierzdane(event);
        var elementy = pobierzelementy(event);
        var czykliknieto = $(elementy[3]).children().get(0).checked;
        if (czykliknieto) {
            $("#idgrupa").val(tresc[0]);
            $("#firmanazwa").val(tresc[1]);
            $("#nazwagrupy").val(tresc[2]);
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono grupe ' + tresc[2] }]);
            MYAPP.pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Grupa '+tresc[2]+' wybrana do edycji'}]);
        } else {
            //$('#notify').puigrowl('show', [{severity: 'info', summary: 'Odznaczono użytkownika ' + tresc[2] }]);
        }
    });
    rj("Nfirmauser").value = "";
    rj("Nnazwagrupy").value = "";
};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabuser').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[0].innerHTML = response;
};

 var czyscinnewiersze = function(parent) {
    var innewiersze = $("#tabuser").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[3].children[0].checked = false;
                wiersz[4].children[0].style.display = "none";
                wiersz[5].children[0].style.display = "none";
                wiersz[6].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
};

var ostatninumer = function () {
    var uTable = $('#tabuser').dataTable();
    var nNodes = uTable.fnGetNodes();
    var node = nNodes[nNodes.length-1];
    return parseInt(($(node).children()[0]).innerHTML)+1;
};

var resetujuser = function(button) {
    var poz = $("#tabuser_paginate").find("a.fg-button.ui-button.ui-state-default.ui-state-disabled");
    var przesuniecie = 0;
    if ($(poz).size()>1) {
        for (il in poz) {
            if (parseInt(poz[il].text)>0) { 
                przesuniecie = parseInt(poz[il].text)-1;
                break;
            }
        }
    } else {
        przesuniecie = parseInt(poz.text())-1;
    }
    var uTable = $('#tabuser').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    uTable.fnUpdate("0",aPos[0],7);
    uTable.fnUpdate("",aPos[0],9);
    uTable.fnUpdate("",aPos[0],10);
    uTable.fnUpdate("0",aPos[0],11); 
    uTable.fnUpdate("0",aPos[0],12);
    uTable.fnPageChange(przesuniecie);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zresetowano użytkownika'}]);
    var id = pobierzelementybutton(button.parentElement.parentElement)[0].textContent;
    $.ajax({
        type: "POST",
        url: "resetpracownika_112014.php",
        data: "userid="+id,
        cache: false,
//        success: function(result){
//            alert("sukces");
//        },
        error: function(result){
            alert("blad resetuj user");
        }
    });
};

