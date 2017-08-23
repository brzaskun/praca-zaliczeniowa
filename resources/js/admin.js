$(document).ready(function () {
    var uTable = $('#tabuser').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers"
            }

    );
    uTable.fnSort([[1, 'desc']]);
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var elements = pobierzdane(event);
            $('#notify').puigrowl('show', [{severity: 'info', summary: 'Użytkownik ' + elements[2] + '<br/> wybrany do edycji', detail: 'Edycja w fomularzu na dole strony'}]);
            $("#iduser").val(elements[1]);
            $("#email").val(elements[2]);
            $("#imienazwisko").val(elements[3]);
            $("#plecuser").val(elements[4]);
            $("#firmausernazwa").val(elements[5]);
            $('#szkolenieuser').val(elements[6]);
            $('#uprawnieniauser').val(elements[7]);
            $("#datazalogowania").val(elements[10]);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            //('#wiadomoscuserjs').html('Użytkownik '+$("#email").val()+' wybrany do edycji');
        });
    }
    ;
});
$(function () {
    $("#tabs").tabs({
        "show": function (event, ui) {
            var table = $.fn.dataTable.fnTables(true);
            if (table.length > 0) {
                $(table).dataTable().fnAdjustColumnSizing();
            }
        }
    });
});
$(document).ready(function () {
    var uTable = $('#tabzaklad').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers"
            }
    );
    uTable.fnSort([[0, 'desc']]);
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var elements = pobierzdane(event);
            $("#idzaklad").val(elements[0]);
            $("#nazwazakladu").val(elements[1]);
            $("#ulica").val(elements[2]);
            $("#miejscowosc").val(elements[3]);
            $("#progzdawalnosci").val(elements[6]);
            if (elements[6] === '1') {
                $("#firmaaktywna").val('aktywna');
            } else {
                $("#firmaaktywna").val('nieaktywna');
            }
            $("#kontakt").val(elements[4]);
            $("#maxpracownikow").val(elements[7]);
            $("#managerlimit").val(elements[8]);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#wiadomoscuserjs').html('Firma ' + $("#nazwazakladu").val() + ' wybrana do edycji');
        });
    }
    ;
});
$(document).ready(function () {
    var uTable = $('#tabszkolenie').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers"
            }
    );
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    var edit = CKEDITOR.instances.trescszkolenia;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var elements = pobierzdane(event);
            $("#idszkolenie").val(elements[0]);
            $("#nazwaszkolenia").val(elements[1]);
            $("#naglowek").val(elements[2]);
            $("#temat").val(elements[3]);
            edit.setData(elements[4]);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#wiadomoscuserjs').html('Element szkolenia: ' + $("#nazwaszkolenia").val() + ' wybrany do edycji');
        });
    }
    ;
});
$(document).ready(function () {
    var uTable = $('#tabszkolenieust').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers"
            }
    );
    uTable.fnSort([[0, 'desc']]);
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var elements = pobierzdane(event);
            $("#idszkolenieust").val(elements[0]);
            $("#firmaszkoleniaust").val(elements[1]);
            $("#nazwaszkoleniaust").val(elements[2]);
            $("#iloscpytanust").val(elements[3]);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#wiadomoscuserjs').html('Element ustawień szkolenia: ' + $("#nazwaszkolenia").val() + ' wybrany do edycji');
        });
    }
    ;
});
$(document).ready(function () {
    var uTable = $('#tabtest').dataTable(
            {
                "bJQueryUI": true,
                "sPaginationType": "full_numbers"
            }
    );
    // Get the nodes from the table
    var nNodes = uTable.fnGetNodes();
    var dlugosc = nNodes.length;
    for (var i = 0; i < dlugosc; i++) {
        $(nNodes[i]).click(function (event) {
            var elements = pobierzdane(event);
            $("#idtest").val(elements[0]);
            $("#nazwatest").val(elements[1]);
            $("#ttresc").val(elements[2]);
            $("#trodzaj").val(elements[3]);
            $("#tpytanie").val(elements[4]);
            $("#todp1").val(elements[5]);
            $("#todp2").val(elements[6]);
            $("#todp3").val(elements[7]);
            $("#todp4").val(elements[8]);
            $("#todp1w").attr('checked', elements[9] === 'true' ? true : false);
            $("#todp2w").attr('checked', elements[10] === 'true' ? true : false);
            $("#todp3w").attr('checked', elements[11] === 'true' ? true : false);
            $("#todp4w").attr('checked', elements[12] === 'true' ? true : false);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#wiadomoscuserjs').html('Element testu: ' + $("#tpytanie").val() + ' wybrany do edycji');
        });
    }
    ;
});
var nowyuser = function () {
//                $("#newuser").dialog({
//                    height: 400,
//                    width: 400,
//                    scrollbars: false,
//                    modal: true
//                });
    $("#newuser").puidialog({
        height: 400,
        width: 400,
        showEffect: 'fade',
        hideEffect: 'fade',
        modal: true
    });
    $("#newuser").puidialog('show');
};
var nowyzaklad = function () {
    $("#newzaklad").dialog({
        height: 460,
        width: 450,
        scrollbars: false,
        modal: true
    });
    $("#newzaklad").show();
};
var noweszkolenieust = function () {
    $("#newszkolenieust").dialog({
        height: 400,
        width: 600,
        scrollbars: false,
        modal: true
    });
    $("#newszkolenie").show();
};
var nowetest = function () {
    $("#newtest").dialog({
        height: 700,
        width: 1000,
        scrollbars: false,
        modal: true
    });
    $("#newtest").show();
};
//            $(function() {
//                $.contextMenu({
//                    selector: '.context-menu-one',
//                    callback: function(key, options) {
//                        var m = "clicked: " + key;
//                        window.console && console.log(m) || alert(m);
//                    },
//                    items: {
//                        "edit": {name: "Edit", icon: "edit"},
//                        "cut": {name: "Cut", icon: "cut"},
//                        "copy": {name: "Copy", icon: "copy"},
//                        "paste": {name: "Paste", icon: "paste"},
//                        "delete": {name: "Delete", icon: "delete"},
//                        "sep1": "---------",
//                        "quit": {name: "Quit", icon: "quit"}
//                    }
//                });
//
//                $('.context-menu-one').on('click', function(e) {
//                    console.log('clicked', this);
//                })
//            });



