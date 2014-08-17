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
    var nowynumer = parseInt(MYAPP.lpwiersza)-1;
    var wiersz = "formwpisdokument:dataList:"+nowynumer+":kontown_input";
    var czyczworka = document.getElementById(wiersz).value[0];
    if (wnlubma === "Wn") {
        if (typwiersza === "1" && czyczworka !== "4") {
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

var aktywujNastepnePolePoprzedniegoWiersza = function(){
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
    //sprawdzpoprzedniwiersz(nrWiersza);
};

var selectOnfocus = function(wierszindex) {
    var i = "formwpisdokument:dataList:"+wierszindex+":ma_input";
    var i_obj = document.getElementById(i);
    $(i_obj).select();
};




