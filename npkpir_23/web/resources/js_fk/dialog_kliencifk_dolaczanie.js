"use strict";

var sprawdzczybrakklienta = function () {
    var zawartosc = $('#kliencifk\\:wyborkontrahenta_input').val();
    if(zawartosc==="nowy klient"){
        PF('dlgwprowadzanieklienta').show();
    }
};

var skopiujdanenowegoklientafk = function () {
    var szukana = document.getElementById('formXNowyKlient:nazwaPole').value;
    PF('dlgwprowadzanieklienta').hide();
    try {
        var czywidzialne = rj("dialog_kliencifk_dolaczanie").getAttribute("aria-hidden");
        if (czywidzialne === "false"){
            var czy_toKlienci_fk = $('#kliencifk\\:wyborkontrahenta_input').val();
            //$('#kliencifk\\:wyborkontrahenta_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            //$('#kliencifk\\:wyborkontrahenta_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#kliencifk\\:wyborkontrahenta_input').focus();
            $('#kliencifk\\:wyborkontrahenta_input').select();
            //PF('dialogklient').search(szukana);
        }
    } catch (e) {
    }
    //nie potrzebne bo mamy kopiowanie w funkcji
    try {
        var czywidzialne = rj("dialogpierwszy").getAttribute("aria-hidden");
        if (czywidzialne === "false"){
            var czy_wpisywaniedok_fk = $('#formwpisdokument\\:acForce_input').val();
            //$('#formwpisdokument\\:acForce_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            //$('#formwpisdokument\\:acForce_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            //PF('poleklientawpisywaniefk').search(szukana);
            $('#formwpisdokument\\:acForce_input').focus();
            $('#formwpisdokument\\:acForce_input').select();
//            r('formwpisdokument:opisdokumentu').focus();
//            r('formwpisdokument:opisdokumentu').select();
        }
    } catch (e) {
    }
    try {
        var czywidzialne = rj("dialogewidencjavatRK").getAttribute("aria-hidden");
        if (czywidzialne === "false"){
            var czy_wpisywaniedok_fk = $('#ewidencjavatRK\\:klientRK_input').val();
            //$('#ewidencjavatRK\\:klientRK_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            //$('#ewidencjavatRK\\:klientRK_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#ewidencjavatRK\\:klientRK_input').focus();
            $('#ewidencjavatRK\\:klientRK_input').select();
            //PF('poleklientawpisywaniefkRK').search(szukana);
        }
    } catch (e) {
    }
    
//     try {
//        var czywidzialne = rj("dialog_wpisywanie_znajdzkontrahenta").getAttribute("aria-hidden");
//        if (czywidzialne === "false"){
//            var nowyklient = document.getElementById('formXNowyKlient:nazwaPole').value;
//            r('form_dialog_wpisywanie_znajdzkontrahenta:numernip_input').val(nowyklient);
//            r('form_dialog_wpisywanie_znajdzkontrahenta:numernip_hinput').val(nowyklient);
//            PF('dialog_wpisywanie_znajdzkontrahenta').serach(nowyklient);
//            r('form_dialog_wpisywanie_znajdzkontrahenta:numernip_input').focus();
//            r('form_dialog_wpisywanie_znajdzkontrahenta:numernip_input').select();
//            //PF('poleklientawpisywaniefkRK').search(szukana);
//        }
//    } catch (e) {
//    }
};

