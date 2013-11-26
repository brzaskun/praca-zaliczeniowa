var zachowajwiersz = function (wierszid, wnlubma) {
    MYAPP.wierszrozrachukowy = wierszid;
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
        załadujmodelzachowywaniawybranegopola();
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

//dodaje do globalnych numer pole na ktore kliknieto
var załadujmodelzachowywaniawybranegopola = function () {
        $("#formwpisdokument\\:dataList :text").focus(function () {
//         var wartosc = $(document.getElementById(this.id)).val();
//         try {
//         var activeObj = wartosc.split(" ");
//         var a = $.isSubstring(activeObj,"200-1");
//            if (a) {
//                MYAPP.idinputfocus = activeObj;
                MYAPP.zaznaczonepole = this;
//            } else {
//                delete MYAPP.idinputfocus;
//                MYAPP.zaznaczonepole = this;
//            }
//         //$(this).css("background-color","dodgerblue");
//         } catch (problem) {
//             //alert("jest problem załadujmodelzachowywaniawybranegopola "+problem);
//         }
//       });
//        $(":text").focusout(function () {
//            $(this).css("background-color","#FFFFFF");
        });
};




