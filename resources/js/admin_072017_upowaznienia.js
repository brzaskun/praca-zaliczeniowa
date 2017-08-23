$(document).ready(function () {
    $("#ajax_sun").puidialog({
        height: 120,
        width: 200,
        resizable: false,
        closable: false,
        location: 'center',
        modal: true
    });
    podswietlmenu(rj('menuupowaznienia'));
    $("#potwierdz").puibutton();
    var uTable = $("#tabelazplikami").dataTable(
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
    uTable.fnSort([[2, 'desc']]);
});
