var managerinit = function() {
    var uTable = $('#tabuser').dataTable(
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
        $(nNodes[i]).click(function(event) {
            var elements = pobierzdane(event);
            $("#iduser").val(elements[13]);
            $("#email").val(elements[1]);
            $("#imienazwisko").val(elements[2]);
            $("#plecuser").val(elements[3]);
            $("#firmausernazwa").val(elements[4]);
            $('#szkolenieuser').val(elements[5]);
            $('#uprawnieniauser').val(elements[6]);
            $("#datazalogowania").val(elements[9]);
            pola = $(this).find('td');
            $(this).css('background-color', 'white');
            $('#wiadomoscuserjs').html('Użytkownik ' + $("#email").val() + ' wybrany do edycji');
        });
    }
    ;

    $("#tabs").tabs({
        "show": function(event, ui) {
            var table = $.fn.dataTable.fnTables(true);
            if (table.length > 0) {
                $(table).dataTable().fnAdjustColumnSizing();
            }
        }
    });
    var optionsuser = {
        url: 'edituser_manager.php'
    };

    // bind 'myForm' and provide a simple callback function 
    $('#formedituser').ajaxForm(optionsuser);
    setInterval(function() {
        $('#wiadomoscuserjs').html(' ');
    }, 6000);
    setInterval(function() {
        $('#wiadomoscusererror').html(' ');
    }, 6000);
    setInterval(function() {
        $('#wiadomoscuserinfo').html(' ');
    }, 6000);
};


var nowyuser = function() {
    $("#newuser").dialog({
        height: 300,
        width: 400,
        scrollbars: false,
        modal: true
    });
    $("#newuser").show();
};







