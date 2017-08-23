/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var uTable = $('#tabzaklad').dataTable(
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
        }
    );
    $('#tabzaklad').addClass("compact");
    uTable.fnSort([[2, 'asc']]); 
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function(event) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[0]).children().get(0).checked;
            if (czykliknieto) {
                $("#idzaklad").val(tresc[1]);
                $("#nazwazakladu").val(tresc[2]);
                $("#ulica").val(tresc[3]);
                $("#miejscowosc").val(tresc[4]);
                $("#kontakt").val(tresc[5]);
                $("#email").val(tresc[6]);
                $("#progzdawalnosci").val(tresc[7]);
                if (tresc[8] === '1') {
                    $("#firmaaktywna").val('aktywna');
                } else {
                    $("#firmaaktywna").val('nieaktywna');
                }
                $("#maxpracownikow").val(tresc[9]);
                $("#managerlimit").val(tresc[11]);
                pola = $(this).find(' > td');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono firmę ' + tresc[1] }]);
                MYAPP.pola = $(this).find(' > td');
                $(this).css('background-color', 'white');
            }
        });
    }
    pobierzfirmy();
    podswietlmenu(rj('menufirmy'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
});

var pobierzfirmy = function () {
    MYAPP.nazwyzakladu = "";
     $.ajax({
        url: "pobierzfirmy_112014.php",
        cache: false,
        success: function(result){
            MYAPP.nazwyzakladu = result;
        },
       
    });
}

var weryfikujzaklad = function() {
    var zaklad = $('#Nnazwazakladu').val();
    if (MYAPP.nazwyzakladu.toLocaleLowerCase().indexOf(zaklad.toLocaleLowerCase())>0) {
        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Nazwa zakłądu '+zaklad+' istnieje już w bazie' }]);
        $('#Nnazwazakladu').css({
            color: "red"
        });
        $("#dodajzaklad").hide();
    } else {
        $('#Nnazwazakladu').css({
            color: "black"
        });
        $("#dodajzaklad").show();
    }
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

 var nowyzaklad = function() {
    $("#newzaklad").puidialog({
        height: 400,
        width: 450,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true 
    });
    $("#newzaklad").puidialog('show');
};

var dodajnowyzaklad = function() {
    var uTable = $('#tabzaklad').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox'/>",
    ostatninumer(),
    rj("Nnazwazakladu").value,
    rj("Nulica").value,
    rj("Nmiejscowosc").value,
    rj("Nkontakt").value,
    rj("Nemail").value,
    rj("Nprogzdawalnosci").value,
    "1",
    rj("Nmaxpracownikow").value, 
    "",
    rj("Nmanagerlimit").value,
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editzaklad(this);' class='buttonedytujuser' style=\"display: none;\"/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunzaklad(this);' style=\"display: none;\"/>"
    ]);
    $("#newzaklad").puidialog('hide');
    uTable.fnSort([[2, 'asc']]);
    uTable.fnDraw();
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy zakład'}]);
    var teststring = "Nnazwazakladu="+rj("Nnazwazakladu").value+"&Nulica="+rj("Nulica").value+"&Nmiejscowosc="+rj("Nmiejscowosc").value+"&Nkontakt="+rj("Nkontakt").value+"&Nemail="+rj("Nemail").value+"&Nprogzdawalnosci="+rj("Nprogzdawalnosci").value+"&Nmaxpracownikow="+rj("Nmaxpracownikow").value+"&Nmanagerlimit="+rj("Nmanagerlimit").value+"&plik=admin112014_firmy.php";
    $.ajax({ 
        type: "POST",
        url: "newzaklad_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            uzupelnijnumer(response);
        }
    });
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    $(parent[7]).css({
        'text-align': "center"
    });
    $(parent[8]).css({
        'text-align': "center"
    });
    $(parent[9]).css({
        'text-align': "center",
    });
    $(parent[11]).css({
        'text-align': "center"
    });
    $(nLastNode).addClass("czekboks");
    $(nLastNode).on("click", function() {
        var parent = nLastNode.children;
        czyscinnewiersze(nLastNode);
        if (parent[0].children[0].checked === true) {
            parent[12].children[0].style.display = "inline";
            parent[13].children[0].style.display = "inline";
        } else {
            parent[12].children[0].style.display = "none";
            parent[13].children[0].style.display = "none";
        }
    });
    $(nLastNode).on("click", function(event) {
        var tresc = pobierzdane(event);
        var elementy = pobierzelementy(event);
        var czykliknieto = $(elementy[0]).children().get(0).checked;
        if (czykliknieto) {
            $("#idzaklad").val(tresc[1]);
            $("#nazwazakladu").val(tresc[2]);
            $("#ulica").val(tresc[3]);
            $("#miejscowosc").val(tresc[4]);
            $("#kontakt").val(tresc[5]);
            $("#email").val(tresc[6]);
            $("#progzdawalnosci").val(tresc[7]);
            if (tresc[8] === '1') {
                $("#firmaaktywna").val('aktywna');
            } else {
                $("#firmaaktywna").val('nieaktywna');
            }
            $("#maxpracownikow").val(tresc[9]);
            $("#managerlimit").val(tresc[11]);
            pola = $(this).find(' > td');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono firmę ' + tresc[1] }]);
            MYAPP.pola = $(this).find(' > td');
            $(this).css('background-color', 'white');
        }
    });
};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabzaklad').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};

var ostatninumer = function () {
    var uTable = $('#tabzaklad').dataTable();
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
    var innewiersze = $("#tabzaklad").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[12].children[0].style.display = "none";
                wiersz[13].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
};

var resetwiersze = function() {
    var innewiersze = $("#tabzaklad").find("tr");
    for (numer in innewiersze) {
        if (numer > 0) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[12].children[0].style.display = "none";
                wiersz[13].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
};

var usunzaklad = function (button) {
    var uTable = $('#tabzaklad').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto zakład'}]);
    var teststring = "nazwazakladu="+aData[2];
    $.ajax({
        type: "POST",
        url: "usunzaklad_112014.php",
        data: teststring,
        cache: false,
        success: function(data, textStatus, jqXHR) {
            if (data === "tak") {
                $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie można usunąć firmy dopóki są w bazie jej użytkownicy'}]);
            } else {
                uTable.fnDeleteRow(aPos[0]);
            }
        }
    });
};
 var editzaklad = function(obj) {
    var tresc = pobierzdanebutton(obj.parentElement.parentElement);
    $("#idzaklad").val(tresc[0]);
    $("#nazwazakladu").val(tresc[1]);
    $("#ulica").val(tresc[2]);
    $("#miejscowosc").val(tresc[3]);
    $("#progzdawalnosci").val(tresc[6]);
    if (tresc[7] === '1') {
        $("#firmaaktywna").val('aktywna');
    } else {
        $("#firmaaktywna").val('nieaktywna');
    }
    $("#kontakt").val(tresc[5]);
    $("#maxpracownikow").val(tresc[8]);
    $("#managerlimit").val(tresc[10]);
    $("#editzaklad").puidialog({
        height: 500,
        width: 530, 
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#editzaklad").puidialog('show');
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

var edytujtabelezaklad = function (){
       $(MYAPP.pola[2]).html($('#nazwazakladu').val());
       $(MYAPP.pola[3]).html($('#ulica').val());
       $(MYAPP.pola[4]).html($('#miejscowosc').val());
       $(MYAPP.pola[5]).html($('#kontakt').val());
       $(MYAPP.pola[6]).html($('#email').val());
       $(MYAPP.pola[7]).html($('#progzdawalnosci').val());
       if ($('#firmaaktywna').val()==="aktywna") {
          $(MYAPP.pola[8]).html("1");
        } else {
           $(MYAPP.pola[8]).html("0");
        }
       $(MYAPP.pola[9]).html($('#maxpracownikow').val());
       $(MYAPP.pola[11]).html($('#managerlimit').val());
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane firmy '+$("#nazwazakladu").val()+' zmienione' }]);
       $("#editzaklad").puidialog('hide');
       var teststring = "nazwazakladu="+$('#nazwazakladu').val()+"&ulica="+$('#ulica').val()+"&miejscowosc="+$('#miejscowosc').val()+"&kontakt="+$('#kontakt').val()+"&email="+$('#email').val()+"&progzdawalnosci="+$('#progzdawalnosci').val()+"&firmaaktywna="+$('#firmaaktywna').val()+"&maxpracownikow="+$('#maxpracownikow').val()+"&managerlimit="+$('#managerlimit').val();
       $.ajax({
        type: "POST",
        url: "editzaklad_112014.php",
        data: teststring,
        cache: false
    });
};

var canceledytujtabelezaklad = function() {
    $('#editzaklad').puidialog('hide');
    resetwiersze();
};