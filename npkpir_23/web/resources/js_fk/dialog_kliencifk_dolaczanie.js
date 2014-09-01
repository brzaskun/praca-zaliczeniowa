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
    dlgwprowadzanieklienta.hide();
    var szukana = document.getElementById('formX:nazwaPole').value;
    try {
        var czy_toKlienci_fk = $('#kliencifk\\:wyborkontrahenta_input').val();
        $('#kliencifk\\:wyborkontrahenta_input').val(document.getElementById('formX:nazwaPole').value);
        $('#kliencifk\\:wyborkontrahenta_hinput').val(document.getElementById('formX:nazwaPole').value);
        $('#kliencifk\\:wyborkontrahenta_input').focus();
        $('#kliencifk\\:wyborkontrahenta_input').select();
        PF('dialogklient').search(szukana);
    } catch (e) {
        $('#formwpisdokument\\:acForce_input').val(document.getElementById('formX:nazwaPole').value);
        $('#formwpisdokument\\:acForce_hinput').val(document.getElementById('formX:nazwaPole').value);
        $('#formwpisdokument\\:acForce_input').focus();
        $('#formwpisdokument\\:acForce_input').select();
        PF('poleklientawpisywaniefk').search(szukana);
    }

};

