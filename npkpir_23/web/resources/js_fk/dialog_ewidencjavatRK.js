"use strict";

var pokazewidencjevatRK = function() {
    try {
        var wiersz = document.activeElement.id;
        var numerwiersza = wiersz.split(":")[2];
        var opis = wiersz.split(":")[3]
        var rodzajwiersza = "formwpisdokument:dataList:"+numerwiersza+":idwiersza";
        var typwiersza = rj(rodzajwiersza).innerHTML;
        MYAPP.nrwierszaRK = numerwiersza;
        if (typwiersza === "0" && opis === "opis") {
            PF('dialogewidencjavatRK').show();
        }
    } catch (e) {}
};

var sprawdzczybrakklientaRK = function() {
    var zawartosc = $('#ewidencjavatRK\\:klientRK_input').val();
    if (zawartosc === "nowy klient") {
        PF('dlgwprowadzanieklienta').show();
    }
};


var ukryjdialogvatrk = function() {
    resetujdialog('dialogewidencjavatRK');
};

