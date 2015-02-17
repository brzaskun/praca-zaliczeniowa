/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        if ($("#dialog_kliencifk_dolaczanie").hasClass('ui-overlay-visible')){
            var czy_toKlienci_fk = $('#kliencifk\\:wyborkontrahenta_input').val();
            $('#kliencifk\\:wyborkontrahenta_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#kliencifk\\:wyborkontrahenta_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#kliencifk\\:wyborkontrahenta_input').focus();
            $('#kliencifk\\:wyborkontrahenta_input').select();
            PF('dialogklient').search(szukana);
        }
    } catch (e) {
    }
    //nie potrzebne bo mamy kopiowanie w funkcji
    try {
        if ($("#dialogpierwszy").hasClass('ui-overlay-visible')){
//            var czy_wpisywaniedok_fk = $('#formwpisdokument\\:acForce_input').val();
//            $('#formwpisdokument\\:acForce_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
//            $('#formwpisdokument\\:acForce_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
//            $('#formwpisdokument\\:acForce_input').focus();
//            $('#formwpisdokument\\:acForce_input').select();
//            PF('poleklientawpisywaniefk').search(szukana);
            r('formwpisdokument:opisdokumentu').focus();
            r('formwpisdokument:opisdokumentu').select();
        }
    } catch (e) {
    }
    try {
        if ($("#dialogewidencjavatRK").hasClass('ui-overlay-visible')){
            var czy_wpisywaniedok_fk = $('#ewidencjavatRK\\:klientRK_input').val();
            $('#ewidencjavatRK\\:klientRK_input').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#ewidencjavatRK\\:klientRK_hinput').val(document.getElementById('formXNowyKlient:nazwaPole').value);
            $('#ewidencjavatRK\\:klientRK_input').focus();
            $('#ewidencjavatRK\\:klientRK_input').select();
            PF('poleklientawpisywaniefkRK').search(szukana);
        }
    } catch (e) {
    }
    
};

