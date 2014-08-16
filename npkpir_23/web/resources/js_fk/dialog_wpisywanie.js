var zachowajwiersz = function (wierszid, wnlubma, typwiersza) {
    try {
    MYAPP.idwiersza = wierszid;
    MYAPP.wnlubma = wnlubma;
    MYAPP.zaznaczonepole = event.target;
    MYAPP.typwiersza = typwiersza;
    //document.getElementById("zaznaczonakomorka").innerHTML = event.target ;
    $(document.getElementById("wpisywaniefooter:wierszid")).val(wierszid);
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
    $(document.getElementById("wpisywaniefooter:typwiersza")).val(typwiersza);
    } catch (blad) {
        //alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var wpisywanieOnShow = function () {
    try {
        ustawdialog('dialogpierwszy','menudokumenty',1250,700);
        //drugi.hide();
        przygotujdokumentdoedycji();
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


var dodajnowywiersz = function () {
    var wnlubma = $(document.getElementById("wpisywaniefooter:wnlubma")).val();
    var typwiersza = $(document.getElementById("wpisywaniefooter:typwiersza")).val();
    if (wnlubma === "Wn") {
        if (typwiersza === "1") {
            $(document.getElementById("wpisywaniefooter:dodajwierszWn")).click();
        } else {
            $(document.getElementById("wpisywaniefooter:dodajwierszWn5")).click();
        }
    } else {
        $(document.getElementById("wpisywaniefooter:dodajwierszMa")).click();
    }
};

//aktywuje nowy wiersz
var aktywujPierwszePoleNowegoWiersza = function(){
    var nrWiersza;
    if (MYAPP.hasOwnProperty('idwiersza')) {
        nrWiersza = MYAPP.idwiersza;
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
    var nrWiersza = parseInt(MYAPP.idwiersza) -1;
    var i = "formwpisdokument:dataList:"+nrWiersza+":ma_input";
    var i_obj = document.getElementById(i);
    if (i_obj) {
        $(i_obj).css('backgroundColor','#ffb');
        $(i_obj).focus();
        $(i_obj).select();
    } else {
        aktywujPierwszePoleNowegoWiersza();
    }
    //sprawdzpoprzedniwiersz(nrWiersza);
};

var selectOnfocus = function(wierszindex) {
    var i = "formwpisdokument:dataList:"+wierszindex+":ma_input";
    var i_obj = document.getElementById(i);
    $(i_obj).select();
};




