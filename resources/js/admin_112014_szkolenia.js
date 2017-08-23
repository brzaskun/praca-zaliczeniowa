/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var uTable = $('#tabszkolenie').dataTable( 
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
    uTable.fnSort([[1, 'desc']]);
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[0]).children().get(0).checked;
            if (czykliknieto) {
                $("#nazwaszkolenia").val(tresc[2]);
                pola = $(this).find('td');
                $(this).css('background-color', 'white');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Element szkolenia: ' + $("#nazwaszkolenia").val() + ' wybrany do edycji'}]);
                MYAPP.pola = $(this).find(' > td');
                $(this).css('background-color', 'white');
            }
        });
    }
    ;
    podswietlmenu(rj('menuszkolenia'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
});

var sprawdzslajd = function (dane) {
    
}

var weryfikujslajd = function() {
    var szkolenie = $('#Nnazwaszkolenia').val();
    var naglowek = $('#Nnaglowek').val();
    var dane = "szk="+szkolenie+"&nag="+naglowek;
     $.ajax({
        type: "POST",
        url: "pobierzslajd_112014.php",
        data: dane,
        cache: false,
        success: function(result){
            if (result === "tak") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Slajd do tego szkolenia o takim naglowku istnieje już w bazie' }]);
                $('#Nnazwaszkolenia').css({
                    color: "red"
                });
                 $('#Nnaglowek').css({
                    color: "red"
                });
                $("#dodajszkolenie").hide();
            } else {
                $('#Nnazwaszkolenia').css({
                    color: "black"
                });
                $('#Nnaglowek').css({
                    color: "black"
                });
                $("#dodajszkolenie").show();
            }
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

 var noweszkolenie = function() {
    $("#newszkolenie").puidialog({
        height: 540,
        width: 960,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true 
    });
    $('#Ntrescszkolenia').ckeditor();
    $("#newszkolenie").puidialog('show');
};


var rezygnujnoweszkolenie = function() {
    $("#newszkolenie").puidialog('hide');
};

var dodajnoweszkolenie = function() {
    var uTable = $('#tabszkolenie').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox'/>",
    ostatninumer(),
    rj("Nnazwaszkolenia").value,
    rj("Nnaglowek").value,
    CKEDITOR.instances.Ntrescszkolenia.getData(),
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editszkolenie(this);' class='buttonedytujuser' style=\"display: none;\"/>",
    "<input title='wstaw' name='wstaw' value='wstaw' type='button'  onclick='wstawszkolenie(this);' class='buttonedytujuser' style='display: none;'/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunszkolenie(this);' style=\"display: none;\"/>"
    ]);
    $("#newszkolenie").puidialog('hide');
    uTable.fnSort([[1, 'desc']]); 
    uTable.fnDraw();
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy slajd szkolenia'}]);
    var teststring = "Nnazwaszkolenia="+rj("Nnazwaszkolenia").value+"&Nnaglowek="+rj("Nnaglowek").value+"&Ntemat=''"+"&Ntrescszkolenia="+CKEDITOR.instances.Ntrescszkolenia.getData()+"&plik=admin112014_szkolenie.php";
    $.ajax({
        type: "POST",
        url: "newszkolenie_112014.php",
        data: teststring,
        cache: false,
        timeout: 20000,        // sets timeout for the request (10 seconds)
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            uzupelnijnumer(response);
        }
    });
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    $(nLastNode).addClass("czekboks");
    $(nLastNode).on("click", function() {
        var parent = nLastNode.children;
        czyscinnewiersze(nLastNode);
        if (parent[0].children[0].checked === true) {
            parent[5].children[0].style.display = "inline";
            parent[6].children[0].style.display = "inline";
            parent[7].children[0].style.display = "inline";
        } else {
            parent[5].children[0].style.display = "none";
            parent[6].children[0].style.display = "none";
            parent[7].children[0].style.display = "none";
        }
    });
    $(nLastNode).on("click", function(event) {
        var tresc = pobierzdane(event);
        var elementy = pobierzelementy(event);
        var czykliknieto = $(elementy[0]).children().get(0).checked;
        if (czykliknieto) {
            $("#idszkolenie").val(tresc[1]);
            $("#nazwaszkolenia").val(tresc[2]);
            $("#naglowek").val(tresc[3]);
            pola = $(this).find(' > td');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono firmę ' + tresc[1] }]);
            MYAPP.pola = $(this).find(' > td');
            $(this).css('background-color', 'white');
        }
    });
};

var wstawszkolenie = function (button) {
    var uTable = $('#tabszkolenie').dataTable();
    var elementy = pobierzelementybutton(button.parentElement.parentElement);
    var tresc = pobierzdanebutton(button.parentElement.parentElement);
    var noweid = parseInt(tresc[1]) + 1;
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy pusty slajd szkolenia'}]);
    var teststring = "slidenr=" + tresc[1] + "&Nnazwaszkolenia=" + tresc[2];
    $.ajax({
        type: "POST",
        url: "wstawwiersz_112014.php",
        data: teststring,
        cache: false,
        timeout: 20000, // sets timeout for the request (10 seconds)
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function (response) {
            if (response === "") {
                return;
            } else {
                uTable.fnAddData([
                    "<input type='checkbox'/>",
                    noweid,
                    tresc[2],
                    "proszę wypełnić",
                    "proszę wypełnić",
                    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editszkolenie(this);' class='buttonedytujuser' style=\"display: none;\"/>",
                    "<input title='wstaw' name='wstaw' value='wstaw' type='button'  onclick='wstawszkolenie(this);' class='buttonedytujuser' style='display: none;'/>",
                    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunszkolenie(this);' style=\"display: none;\"/>"
                ]);
                var nNodes = uTable.fnGetNodes();
                var nThisNode = nNodes[nNodes.length - 1];
                uTable.fnSort([[1, 'desc']]);
                uTable.fnDraw();
                var nNodes = uTable.fnGetNodes();
                var nLastNode = nNodes[nNodes.length - 1];
                var parent = nLastNode.children;
                $(nLastNode).addClass("czekboks");
                $(nLastNode).on("click", function () {
                    var parent = nLastNode.children;
                    czyscinnewiersze(nLastNode);
                    if (parent[0].children[0].checked === true) {
                        parent[5].children[0].style.display = "inline";
                        parent[6].children[0].style.display = "inline";
                        parent[7].children[0].style.display = "inline";
                    } else {
                        parent[5].children[0].style.display = "none";
                        parent[6].children[0].style.display = "none";
                        parent[7].children[0].style.display = "none";
                    }
                });
                $(nLastNode).on("click", function (event) {
                    var tresc = pobierzdane(event);
                    var elementy = pobierzelementy(event);
                    var czykliknieto = $(elementy[0]).children().get(0).checked;
                    if (czykliknieto) {
                        $("#idszkolenie").val(tresc[1]);
                        $("#nazwaszkolenia").val(tresc[2]);
                        $("#naglowek").val(tresc[3]);
                        pola = $(this).find(' > td');
                        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono firmę ' + tresc[1]}]);
                        MYAPP.pola = $(this).find(' > td');
                        $(this).css('background-color', 'white');
                    }
                });
                uzupelnijnumerWstaw(response, nThisNode);
                przenumerujpozostale(response);
            }
        }
    });

};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabszkolenie').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};

var uzupelnijnumerWstaw = function(response, nThisNode) { 
    var parent = nThisNode.children;
    parent[1].innerHTML = response;
};
 
var przenumerujpozostale = function (response) {
    var uTable = $('#tabszkolenie').dataTable();
    var nNodes = uTable.fnGetNodes();
    var limit = parseInt(response);
    for (var il in nNodes) {
        try {
            var aNode = nNodes[il];
            var parent = aNode.children;
            if (parseInt(parent[1].innerHTML) >= limit && parent[3].innerHTML !== 'proszę wypełnić') {
                parent[1].innerHTML = parseInt(parent[1].innerHTML) + 1;
            }
        } catch (e) {
        }
    }
    uTable.fnSort([[1, 'desc']]);
    uTable.fnDraw();
};

var ostatninumer = function () {
    var uTable = $('#tabszkolenie').dataTable();
    var nNodes = uTable.fnGetNodes();
    var max = 0;
    var i = 0;
    for (var il in nNodes) {
        try {
            var id = parseInt(($(nNodes[il]).children()[1]).innerHTML);
            if (i===0) {
                max = id;
                i++;
            } else if (id > max) {
                max = id;
            } else {
                break;
            }
        } catch (e) {
            break;
        }
    }
    return max+1;
};

var czyscinnewiersze = function(parent) {
    var innewiersze = $("#tabszkolenie").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[5].children[0].style.display = "none";
                wiersz[6].children[0].style.display = "none";
                wiersz[7].children[0].style.display = "none"; 
            } catch (ex) {
                
            }
        }
    }
};

var usunszkolenie= function (button) {
    var uTable = $('#tabszkolenie').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto szkolenie'}]);
    var teststring = "idszkolenie="+aData[1];
    $.ajax({
        type: "POST",
        url: "usunszkolenie_112014.php",
        data: teststring,
        cache: false
    });
};
 var editszkolenie = function(obj) {
    var tresc = pobierzdanebutton(obj.parentElement.parentElement);
    if (typeof(CKEDITOR.instances.trescszkolenia)!=="object") {
        CKEDITOR.replace( 'trescszkolenia');
    }
    var edit = CKEDITOR.instances.trescszkolenia;
    $("#idszkolenie").val(tresc[1]);
    $("#nazwaszkolenia").val(tresc[2]);
    $("#naglowek").val(tresc[3]);
    edit.setData(tresc[4]);
    $("#editszkolenie").puidialog({
        height: 540,
        width: 960,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#editszkolenie").puidialog('show');
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

var edytujtabeleszkolenie = function (){
       var edit = CKEDITOR.instances.trescszkolenia.getData();
       $(MYAPP.pola[3]).html(rj("naglowek").value);
       $(MYAPP.pola[4]).html(edit);
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane szkolenia '+$("#nazwaszkolenia").val()+' zmienione' }]);
       $("#editszkolenie").puidialog('hide');
       var teststring = "idszkolenie="+rj("idszkolenie").value+"&nazwaszkolenia="+rj("nazwaszkolenia").value+"&naglowek="+rj("naglowek").value+"&temat=''"+"&trescszkolenia="+CKEDITOR.instances.trescszkolenia.getData()+"&plik=admin112014_szkolenie.php";
       $.ajax({
        type: "POST",
        url: "editszkolenie_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
        }
    });
};

var edytujtabeleszkoleniepodglad = function (){
       var edit = CKEDITOR.instances.trescszkolenia.getData();
       $(MYAPP.pola[3]).html(rj("naglowek").value);
       $(MYAPP.pola[4]).html(edit);
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane szkolenia '+$("#nazwaszkolenia").val()+' zmienione' }]);
       var teststring = "idszkolenie="+rj("idszkolenie").value+"&nazwaszkolenia="+rj("nazwaszkolenia").value+"&naglowek="+rj("naglowek").value+"&temat=''"+"&trescszkolenia="+edit+"&plik=admin112014_szkolenie.php";
       $.ajax({
        type: "POST",
        url: "editszkoleniepodglad_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            var opis = "szkolenie_podglad.php?opis="+response;
            window.open(opis, '_blank');
        }
    });
};