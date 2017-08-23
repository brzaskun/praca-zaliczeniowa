/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    var uTable = $('#tabszkolenietestust').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers",
                "language": {
                        "url": "resources/dataTableNew/Polish.json"
                    }
            }
    );
    uTable.fnSort([[1, 'desc']]);
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function(event) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[0]).children().get(0).checked;
            if (czykliknieto) {
                $("#id").val(tresc[1]);
                $("#szkolenie").val(tresc[2]);
                $("#test").val(tresc[3]);
                $("#uwagi").val(tresc[4]);
                pola = $(this).find('> td');
                $(this).css('background-color', 'white');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Element ustawień szkolenia: ' + $("#szkolenie").val()+ ' ' + $("#test").val() + ' wybrany do edycji' }]);
                MYAPP.pola = $(this).find(' > td');
            }
        });
    };
    podswietlmenu(rj('menuszkolenia'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
});

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

 var noweszkolenietestust = function() {
    $("#newszkolenietestust").puidialog({
        height: 200,
        width: 410, 
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#newszkolenietestust").puidialog('show');
};

var dodajnoweszkolenietestust = function() {
    var uTable = $('#tabszkolenietestust').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox'/>",
    ostatninumer(),
    rj("Nszkolenie").value,
    rj("Ntest").value,
    rj("Nuwagi").value,
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editszkolenietestust(this);' class='buttonedytujuser' style=\"display: none;\"/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunszkolenietestust(this);' style=\"display: none;\"/>"
    ]);
    $("#newszkolenietestust").puidialog('hide');
    uTable.fnSort([[1, 'desc']]);
    uTable.fnDraw();
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowe ustawienia szkolenia'}]);
    var teststring = "Nszkolenie="+rj("Nszkolenie").value+"&Ntest="+rj("Ntest").value+"&Nuwagi="+rj("Nuwagi").value+"&plik=admin112014_szkolenia_testy.php";
    $.ajax({
        type: "POST",
        url: "newszkolenietest_1112014.php",
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
    $(parent[4]).css({
        'text-align': "center"
    });
    $(nLastNode).addClass("czekboks");
    $(nLastNode).on("click", function() {
        var parent = nLastNode.children;
        czyscinnewiersze(nLastNode);
        if (parent[0].children[0].checked === true) {
            parent[5].children[0].style.display = "inline";
            parent[6].children[0].style.display = "inline";
        } else {
            parent[5].children[0].style.display = "none";
            parent[6].children[0].style.display = "none";
        }
    });
    $(nLastNode).on("click", function(event) {
        var tresc = pobierzdane(event);
        var elements = pobierzelementy(event);
        var czykliknieto = $(elements[0]).children().get(0).checked;
        if (czykliknieto) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[0]).children().get(0).checked;
            if (czykliknieto) {
                $("#id").val(tresc[1]);
                $("#szkolenie").val(tresc[2]);
                $("#test").val(tresc[3]);
                $("#uwagi").val(tresc[4]);
                pola = $(this).find(' > td');
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono szkolenie-test ' + tresc[1] }]);
                MYAPP.pola = $(this).find(' > td');
                $(this).css('background-color', 'white');
            }
        }
    });

};

var uzupelnijnumer = function(response) {
    var uTable = $('#tabszkolenietestust').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};

var ostatninumer = function () {
        var uTable = $('#tabszkolenietestust').dataTable();
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
    var innewiersze = $("#tabszkolenietestust").find("tr");
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

var usunszkolenietestust = function (button) {
    var uTable = $('#tabszkolenietestust').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto ustawienie szkolenia-testu'}]);
    var teststring = "Nid="+aData[1];
    $.ajax({
        type: "POST",
        url: "usunszkolenietest_112014.php",
        data: teststring,
        cache: false
    });
};

var editszkolenietestust = function(obj) {
    $("#editszkolenietestust").puidialog({
        height: 200,
        width: 410, 
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true
    });
    $("#editszkolenietestust").puidialog('show');
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

var edytujtabeleszkolenietestust = function (){
       $(MYAPP.pola[4]).html($('#uwagi').val());
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane szkolenia ' + $("#szkolenie").val()+ ' ' + $("#test").val() + ' zmienione' }]);
       $("#editszkolenietestust").puidialog('hide');
       var teststring = "Nid="+$('#id').val()+"&Nuwagi="+$('#uwagi').val();
       $.ajax({
        type: "POST",
        url: "editszkolenietest_112014.php",
        data: teststring,
        cache: false
    });
};