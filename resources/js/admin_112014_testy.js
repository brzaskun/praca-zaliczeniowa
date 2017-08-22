/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    var uTable = $('#tabtest').dataTable(
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
    uTable.fnSort([[1, 'desc'],[2, 'asc']]); 
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function(event) {
            var tresc = pobierzdane(event);
            var elementy = pobierzelementy(event);
            var czykliknieto = $(elementy[0]).children().get(0).checked;
            if (czykliknieto) {
                $("#idtest").val(tresc[1]);
                $("#nazwatest").val(tresc[2]);
                $("#ttresc").val(tresc[3]);
                $("#trodzaj").val(tresc[4]);
                $("#tpytanie").val(tresc[5]);
                $("#todp1").val(tresc[6]);
                $("#todp2").val(tresc[7]);
                $("#todp3").val(tresc[8]);
                $("#todp4").val(tresc[9]);
                $("#todp1w").prop('checked', tresc[10] === 'pda'? true : false);
                $("#todp2w").prop('checked', tresc[11] === 'pda'? true : false);
                $("#todp3w").prop('checked', tresc[12] === 'pda'? true : false);
                $("#todp4w").prop('checked', tresc[13] === 'pda'? true : false);
                pola = $(this).find('td');
                $(this).css('background-color', 'white');
                MYAPP.pola = $(this).find(' > td');
                $('#wiadomoscuserjs').html('Element testu: ' + $("#tpytanie").val() + ' wybrany do edycji');
            }
        });
    };
    podswietlmenu(rj('menutesty'));
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

 var nowetest = function() {
    $("#newtest").puidialog({
        height: 550,
        width: 860,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true 
    });
    $("#newtest").puidialog('show');
};

var dodajnowytest = function() {
    var uTable = $('#tabtest').dataTable();
    uTable.fnAddData( [
    "<input type='checkbox'/>",
    ostatninumer(),
    rj("Nnazwatest").value,
    rj("Nttresc").value,
    rj("Ntrodzaj").value,
    rj("Ntpytanie").value,
    rj("Ntodp1").value,
    rj("Ntodp2").value,
    rj("Ntodp3").value,
    rj("Ntodp4").value,
    rj("Ntodp1w").checked === true ? 'pda' : 'npda',
    rj("Ntodp2w").checked === true ? 'pda' : 'npda',
    rj("Ntodp3w").checked === true ? 'pda' : 'npda',
    rj("Ntodp4w").checked === true ? 'pda' : 'npda',
    "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='edittest(this);' class='buttonedytujuser' style=\"display: none;\"/>",
    "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usuntest(this);' style=\"display: none;\"/>"
    ]);
    $("#newtest").puidialog('hide');
    uTable.fnSort([[1, 'desc'],[2, 'asc']]); 
    uTable.fnDraw();
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowe pytanie testowe'}]);
    var teststring = "Nnazwatest="+rj("Nnazwatest").value+"&Nttresc="+rj("Nttresc").value+"&Ntrodzaj="+rj("Ntrodzaj").value+"&Ntpytanie="+rj("Ntpytanie").value+"&Ntodp1="+rj("Ntodp1").value+"&Ntodp2="+rj("Ntodp2").value+"&Ntodp3="+rj("Ntodp3").value+"&Ntodp4="+rj("Ntodp4").value+"&Ntodp1w="+rj("Ntodp1w").checked+"&Ntodp2w="+rj("Ntodp2w").checked+"&Ntodp3w="+rj("Ntodp3w").checked+"&Ntodp4w="+rj("Ntodp4w").checked+"&plik=admin112014_testy.php";
    $.ajax({
        type: "POST",
        url: "newtest_112014.php",
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
    $(nLastNode).addClass("czekboks");
    $(nLastNode).on("click", function() {
        var parent = nLastNode.children;
        czyscinnewiersze(nLastNode);
        if (parent[0].children[0].checked === true) {
            parent[14].children[0].style.display = "inline";
            parent[15].children[0].style.display = "inline";
        } else {
            parent[14].children[0].style.display = "none";
            parent[15].children[0].style.display = "none";
        }
    });
    $(nLastNode).on("click", function(event) {
        var tresc = pobierzdane(event);
        var elementy = pobierzelementy(event);
        var czykliknieto = $(elementy[0]).children().get(0).checked;
        if (czykliknieto) {
            $("#idtest").val(tresc[1]);
            $("#nazwatest").val(tresc[2]);
            $("#ttresc").val(tresc[3]);
            $("#trodzaj").val(tresc[4]);
            $("#tpytanie").val(tresc[5]);
            $("#todp1").val(tresc[6]);
            $("#todp2").val(tresc[7]);
            $("#todp3").val(tresc[8]);
            $("#todp4").val(tresc[9]);
            $("#todp1w").prop('checked', tresc[10] === 'pda'? true : false);
            $("#todp2w").prop('checked', tresc[11] === 'pda'? true : false);
            $("#todp3w").prop('checked', tresc[12] === 'pda'? true : false);
            $("#todp4w").prop('checked', tresc[13] === 'pda'? true : false);
            pola = $(this).find(' > td');
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Zaznaczono firmę ' + tresc[1] }]);
            MYAPP.pola = $(this).find(' > td');
            $(this).css('background-color', 'white');
        }
    });
};
var uzupelnijnumer = function(response) {
    var uTable = $('#tabtest').dataTable();
    var nNodes = uTable.fnGetNodes();
    var nLastNode = nNodes[nNodes.length-1];
    var parent = nLastNode.children;
    parent[1].innerHTML = response;
};

var ostatninumer = function () {
    var uTable = $('#tabtest').dataTable();
    var nNodes = uTable.fnGetNodes();
    var max = 0;
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
    var innewiersze = $("#tabtest").find("tr");
    for (numer in innewiersze) {
        if (numer > 0 && innewiersze[numer] !== parent) {
            try {
                var wiersz = innewiersze[numer].children;
                wiersz[0].children[0].checked = false;
                wiersz[14].children[0].style.display = "none";
                wiersz[15].children[0].style.display = "none";
            } catch (ex) {
                
            }
        }
    }
};

var usuntest = function (button) {
    var uTable = $('#tabtest').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto pytanie testowe'}]);
    var teststring = "idtest="+aData[1];
    $.ajax({
        type: "POST",
        url: "usuntest_112014.php",
        data: teststring,
        cache: false
    });
};
 var edittest = function(obj) {
    $("#edittest").puidialog({
        height: 550,
        width: 860,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true 
    }); 
    $("#edittest").puidialog('show');
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

var edytujtabeletest = function (){
       $(MYAPP.pola[1]).html($('#idtest').val()); 
       $(MYAPP.pola[2]).html($('#nazwatest').val());
       $(MYAPP.pola[3]).html($('#ttresc').val());
       $(MYAPP.pola[4]).html($('#trodzaj').val());
       $(MYAPP.pola[5]).html($('#tpytanie').val());
       $(MYAPP.pola[6]).html($('#todp1').val());
       $(MYAPP.pola[7]).html($('#todp2').val());
       $(MYAPP.pola[8]).html($('#todp3').val());
       $(MYAPP.pola[9]).html($('#todp4').val());
       $(MYAPP.pola[10]).html(rj('todp1w').checked === true ? 'pda' : 'npda');
       $(MYAPP.pola[11]).html(rj('todp2w').checked === true ? 'pda' : 'npda');
       $(MYAPP.pola[12]).html(rj('todp3w').checked === true ? 'pda' : 'npda');
       $(MYAPP.pola[13]).html(rj('todp4w').checked === true ? 'pda' : 'npda');
       $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane pytania testowego '+$("#ttresc").val()+' zmienione' }]);
       $("#edittest").puidialog('hide');
       var teststring = "idtest="+rj("idtest").value+"&nazwatest="+rj("nazwatest").value+"&ttresc="+rj("ttresc").value+"&trodzaj="+rj("trodzaj").value+"&tpytanie="+rj("tpytanie").value+"&todp1="+rj("todp1").value+"&todp2="+rj("todp2").value+"&todp3="+rj("todp3").value+"&todp4="+rj("todp4").value+"&todp1w="+rj("todp1w").checked+"&todp2w="+rj("todp2w").checked+"&todp3w="+rj("todp3w").checked+"&todp4w="+rj("todp4w").checked+"&plik=admin112014_testy.php";
       $.ajax({
        type: "POST",
        url: "edittest_112014.php",
        data: teststring,
        cache: false
    });
};

var edytujtabeletestpodglad = function (){
        $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane pytania testowego '+$("#ttresc").val()+' zmienione' }]);
       var teststring = "idtest="+rj("idtest").value+"&nazwatest="+rj("nazwatest").value+"&ttresc="+rj("ttresc").value+"&trodzaj="+rj("trodzaj").value+"&tpytanie="+rj("tpytanie").value+"&todp1="+rj("todp1").value+"&todp2="+rj("todp2").value+"&todp3="+rj("todp3").value+"&todp4="+rj("todp4").value+"&todp1w="+rj("todp1w").checked+"&todp2w="+rj("todp2w").checked+"&todp3w="+rj("todp3w").checked+"&todp4w="+rj("todp4w").checked+"&plik=admin112014_testy.php";
       $.ajax({
        type: "POST",
        url: "edittestpodglad_112014.php",
        data: teststring,
        cache: false,
        error: function(xhr, status, error) { alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            window.open('testpodglad.php', '_blank');
        }
    });
};

 