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
        if (typwiersza === 1) {
            $(document.getElementById("wpisywaniefooter:dodajwierszWn")).click();
        }
    } else {
        $(document.getElementById("wpisywaniefooter:dodajwierszMa")).click();
    }
};

//aktywuje nowy wiersz
var aktywujPierwszePoleNowegoWiersza = function(){
    var nrWiersza = MYAPP.idwiersza;
    var i = "#formwpisdokument\\:dataList\\:"+nrWiersza+"\\:opis";
    $(i).focus();
    $(i).select();
    $(i).css('backgroundColor','#ffb');
    //sprawdzpoprzedniwiersz(nrWiersza);
};





