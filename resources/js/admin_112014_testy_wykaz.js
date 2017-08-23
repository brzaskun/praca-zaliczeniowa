$(document).ready(function () {
    generujtablicetesty();
    podswietlmenu(rj('menutesty'));
    $(":input:not(:checkbox):not(:button)").puiinputtext();
});

var generujtablicetesty = function () {
    $.ajax({
        type: "POST",
        url: "pobierztesty_012017.php",
        cache: false,
        error: function (xhr, status, error) {
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać testów.'}]);
        },
        success: function (response) {
            if (response !== "brak") {
                $('#notify').puigrowl('show', [{severity: 'info', summary: 'Pobrano opisy wszystkich testy'}]);
                var tablicezdanymi = $.parseJSON(decodeURIComponent(response));
                if (tablicezdanymi.length > 0) {
                    tworzkontener();
                    var uTable = tworztabele('tabuser', ["", "id", "nazwa", "skrót", "opis", "edytuj", "usuń"]);
                    uTable.fnAddData(tablicezdanymi);
                    naniesclickzbiorcze('#tabuser', '#tabelaedituser', 'nazwa');
                }
            }
        }
    });
    $("#zachowajbutton").show();
};


var nowytestwykaz = function () {
    $("#newtestwykaz").puidialog({
        height: 190,
        width: 450,
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#newtestwykaz").puidialog('show');
};

var dodajnowytestwykaz = function () {
    var uTable = $('#tabuser').dataTable();
    var teststring = "Ntestu=" + rj("Ntestu").value + "&Nopis=" + rj("Nopis").value + "&Nskrot=" + rj("Nskrot").value;
    $.ajax({
        type: "POST",
        url: "newtestwykaz_1112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function (response) {
            uTable.fnAddData([
                "<input type='checkbox' class=\"czekbox\"/>",
                "<span class='doedycji'>"+response+"</span>",
                "<span class='doedycji'>"+rj("Ntestu").value+"</span>",
                "<span class='doedycji'>"+rj("Nskrot").value+"</span>",
                "<span class='doedycji'>"+rj("Nopis").value+"</span>",
                "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='editwiersz(this);' class='czekedycja' style=\"display: none;\"/>",
                "<input title='usuń' name='usun' value='usuń' type='button'  onclick='usunwiersz(this);' class='czekedycja' style=\"display: none;\"/>"
            ]);
            $("#newtestwykaz").puidialog('hide');
            uTable.fnSort([[1, 'desc']]);
            uTable.fnDraw();
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dodano nowy rodzaj testu'}]);
            naniesclickzbiorcze('#tabuser', '#tabelaedituser', 'nazwa');
        }
    });
};

var usunwiersz = function (button) {
    var uTable = $('#tabuser').dataTable();
    var aPos = uTable.fnGetPosition(button.parentElement);
    var aData = uTable.fnGetData(aPos[0]);
    uTable.fnDeleteRow(aPos[0]);
    $('#notify').puigrowl('show', [{severity: 'info', summary: 'Usunięto szkolenie'}]);
    var teststring = "Nid=" + aData[1];
    $.ajax({
        type: "POST",
        url: "usuntestwykaz_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function (response) {
        }
    });
};

var editwiersz = function (obj) {
    $("#edittestwykaz").puidialog({
        height: 190,
        width: 450,
        scrollbars: false,
        resizable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center",
        modal: true
    });
    $("#edittestwykaz").puidialog('show');
};


var edytujtabeletestwykaz = function () {
    var teststring = "Nid=" + $('#idszkolenie').val() + "&Ntestu=" + $('#szkolenia').val() + "&Nopis=" + $('#opis').val() + "&Nskrot=" + rj("skrot").value;
    $.ajax({
        type: "POST",
        url: "edittestwykaz_112014.php",
        data: teststring,
        cache: false,
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function (response) {
            $(MYAPP.pola[2]).html($('#szkolenia').val());
            $(MYAPP.pola[3]).html($('#skrot').val());
            $(MYAPP.pola[4]).html($('#opis').val());
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Dane testu ' + $("#szkolenia").val() + ' ' + $("#opis").val() + ' zmienione'}]);
            $("#edittestwykaz").puidialog('hide');
        }
    });
};