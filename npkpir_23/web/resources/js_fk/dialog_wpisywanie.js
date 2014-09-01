var zachowajwiersz = function (lpwiersza, wnlubma, typwiersza) {
    try {
    MYAPP.lpwiersza = lpwiersza;
    MYAPP.wnlubma = wnlubma;
    MYAPP.zaznaczonepole = event.target;
    MYAPP.typwiersza = typwiersza;
    //document.getElementById("zaznaczonakomorka").innerHTML = event.target ;
    $(document.getElementById("wpisywaniefooter:wierszid")).val(lpwiersza);
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
    $(document.getElementById("wpisywaniefooter:typwiersza")).val(typwiersza);
    } catch (blad) {
        //alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var wpisywanieOnShow = function () {
    try {
        ustawdialog('dialogpierwszy','menudokumenty',1100,700);
        //drugi.hide();
        //przygotujdokumentdoedycji();
        pozazieleniajNoweTransakcje();
        $('#formwpisdokument\\:dataDialogWpisywanie').select();
        //Blokuje te z rozrachunkamio
        //zablokujwierszereadonly();
    } catch (Exception) {
        alert ("blad w fukncji pierwszyonShow jsfk wiersz 73 "+Exception);
    }
};

var wpisywanieOnHide = function () {
    resetujdialog('dialogpierwszy');
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
    $(document.getElementById('wpisywaniefooter:przywrocwpisbutton')).click();
};



//aktywuje nowy wiersz
var aktywujPierwszePoleNowegoWiersza = function(){
    var nrWiersza;
    if (MYAPP.hasOwnProperty('lpwiersza')) {
        nrWiersza = MYAPP.lpwiersza;
    } else {
        nrWiersza = 0;
    }
    var i = "formwpisdokument:dataList:"+nrWiersza+":opis";
    var i_obj = document.getElementById(i);
    try {
        while (i_obj.value !== "") {
            nrWiersza++;
            i = "formwpisdokument:dataList:"+nrWiersza+":opis";
            i_obj = document.getElementById(i);
        }
    } catch (e) {}
        $(i_obj).css('backgroundColor','#ffb');
        $(i_obj).focus();
        $(i_obj).select();
    //sprawdzpoprzedniwiersz(nrWiersza);
};

var aktywujPierwszePoleNowegoWierszaEnter = function(){
    var nrWiersza;
    if (MYAPP.hasOwnProperty('lpwiersza')) {
        nrWiersza = MYAPP.lpwiersza;
    } else {
        nrWiersza = 0;
    }
    var i = "formwpisdokument:dataList:"+nrWiersza+":opis";
    var i_obj = document.getElementById(i);
    $(i_obj).css('backgroundColor','#ffb');
    $(i_obj).focus();
    $(i_obj).select();
    //sprawdzpoprzedniwiersz(nrWiersza);
};

var aktywujNastepnePolePoprzedniegoWiersza = function(){
    var nrnastepnego = parseInt(MYAPP.lpwiersza) + 1;;
    var runfunckja = true;
    var i = "formwpisdokument:dataList:"+nrnastepnego+":opis";
    var i_obj = document.getElementById(i);
    if (i_obj) {
        runfunckja = false;
    }
    if (runfunckja) {
        var nrWiersza = parseInt(MYAPP.lpwiersza) -1;
        var i = "formwpisdokument:dataList:"+nrWiersza+":ma_input";
        var i_obj = document.getElementById(i);
        if (i_obj) {
            $(i_obj).css('backgroundColor','#ffb');
            $(i_obj).focus();
            $(i_obj).select();
        } else {
            aktywujPierwszePoleNowegoWiersza();
        }
    }
    //sprawdzpoprzedniwiersz(nrWiersza);
};

var selectOnfocus = function(wierszindex) {
    var i = "formwpisdokument:dataList:"+wierszindex+":ma_input";
    var i_obj = document.getElementById(i);
    $(i_obj).select();
};

var sprawdzczybrakklientawpisywanie = function() {
    var zawartosc = $('#formwpisdokument\\:acForce_input').val();
    if (zawartosc === "nowy klient") {
        PF('dlgwprowadzanieklienta').show();
    }
};

var sprawdzczykopiowacklienta = function() {
//    var zawartosc = $('#formwpisdokument\\:acForce_input').val();
//    if (zawartosc === "+") {
//        var text = $('#zobWiad\\:nazwa').html();
//        $('#formwpisdokument\\:acForce_input').val($('#zobWiad\\:nazwa').html());
//        $('#formwpisdokument\\:acForce_hinput').val($('#zobWiad\\:nazwa').html());
//        $('#formwpisdokument\\:acForce_input').focus();
//        $('#formwpisdokument\\:acForce_input').select();
//        PF('dialogklient').search(text);
//        event.cancelBubble = true;
//        event.stopPropagation();
//        event.stopImmediatePropagation();
//    }
};





