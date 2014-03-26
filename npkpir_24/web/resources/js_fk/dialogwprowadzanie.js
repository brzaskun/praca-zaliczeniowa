var zachowajwiersz = function (wierszid, wnlubma) {
    MYAPP.wierszrozrachukowy = wierszid;
    MYAPP.zaznaczonepole = event.target;
    //document.getElementById("zaznaczonakomorka").innerHTML = event.target ;
    try {
        $(document.getElementById("wpisywaniefooter:wierszid")).val(wierszid);
        $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
    } catch (blad) {
        alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var pierwszyonShow = function () {
    try {
        ustawdialog('dialogpierwszy','menudokumenty');
        //drugi.hide();
        przygotujdokumentdoedycji();
        $('#formwpisdokument\\:datka').select();
        //Blokuje te z rozrachunkamio
        //zablokujwierszereadonly();
    } catch (Exception) {
        alert ("blad w fukncji pierwszyonShow jsfk wiersz 73 "+Exception);
    }
};

var pierwszyOnHide = function () {
    resetujdialog('dialogpierwszy');
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
    $(document.getElementById('wpisywaniefooter:przywrocwpisbutton')).click();
};







