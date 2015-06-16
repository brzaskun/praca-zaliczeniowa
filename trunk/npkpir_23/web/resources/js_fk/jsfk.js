/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//podpowiadanie kont w dokumencie    
function wywolajdok(opis, numer, loopid) {
    var licz = 1;
    var id = loopid - 1;
    var zloz = "#formwpisdokument\\:dataList\\:" + id + "\\:opis";
    var wynikwn = "formwpisdokument:dataList:" + id + ":kontown_hinput";
    var wynikwn_ = "formwpisdokument:dataList:" + id + ":kontown_input";
    var wynikma = "formwpisdokument:dataList:" + id + ":kontoma_hinput";
    var wynikma_ = "formwpisdokument:dataList:" + id + ":kontoma_input";
    var szukana = $(zloz).val();
    if (szukana.length == 0) {
        licz = 1;
    }
    var siatka = szukana.split(',');
    var mapa = {};
    var tablica = opis.toLowerCase().split(',');
    var tablica2 = numer.split(',');
    for (var w = 0; w < tablica.length; w++) {
        var sobo = tablica[w].toLowerCase();
        mapa[sobo] = tablica2[w];
    }
    var dlug = siatka.length;
    for (licz; licz < dlug; licz++) {
        var dawniej = licz - 1;
        var szczegol = siatka[dawniej].toLowerCase();
        var b = szczegol.toLowerCase();
        if ($.isSubstring(tablica, b)) {
            var rodzajkonta = mapa[szczegol];
            var wynikszukania = rodzajkonta[0];
            if(wynikszukania==="4"){
                document.getElementById(wynikwn).value = mapa[szczegol];
                document.getElementById(wynikwn_).value = mapa[szczegol];
            } else {
                document.getElementById(wynikma).value = mapa[szczegol];
                document.getElementById(wynikma_).value = mapa[szczegol];
            }
            licz = 1;
            break;
        }
    }
};


var aktywujwiersz = function(nrWiersza){
    var i = "#formwpisdokument\\:dataList\\:"+nrWiersza+"\\:opis";
    if($(i)!== typeof 'undefined'){
    $(i).focus();
    $(i).css('backgroundColor','#ffe');
    }
    chowanienapoczatek();
};




    jQuery.isSubstring = function(haystack, needle) {
            return $.inArray(needle,haystack) !== -1;
        };





//to makro sprawdza i blokuje puste czesci wiersza
var zablokujwnma = function(wiersz,co){
    var w = wiersz-1;
    var ico = co === 'wn' ? 'ma' : 'wn';
    var blokowany = "#formwpisdokument\\:dataList\\:"+w+"\\:"+co+"_input";
    var sprawdzany = "#formwpisdokument\\:dataList\\:"+w+"\\:"+ico+"_input";
    var cozawiera = $(sprawdzany).val().length;
    if(cozawiera>0){
        $(blokowany).hide();
        $(sprawdzany).show();
        var pozycja = {pozycja: w, blokowany: co};
        zachowajwtablicy(pozycja);
        var kontopole = '[id="formwpisdokument:dataList:'+w+':konto_input"]';
        $(kontopole).focus();
    } else {
        $(blokowany).show();
        MYAPP.chowane.splice(w,1);
        var kontopole = "#formwpisdokument\\:dataList\\:"+w+"\\:"+co+"_input";
        $(kontopole).focus();
    }
};

var focusNaOstatnimWierszu = function(){
    //usunztablicydok();
    //chowanienapoczatekdok();
        var dlugosclisty = rj("formwpisdokument:dataList_data").children.length;
        var nrwiersza = dlugosclisty-1;
        var i = 'formwpisdokument:dataList:'+nrwiersza+':opis';
        $(document.getElementById(i)).focus();
};




var zmienkolor = function(color, i,wnma) {
    var dopasowanywiersz = "formwpisdokument:dataList:" + i + ":" + wnma;
    $(document.getElementById(dopasowanywiersz)).css("color", color);
};

//menu do zakrycia poszczegolnych pol w przypadku podgladu dokumentu
var zakryjpolaedycjadokumentu = function(iloscwierszy){
    MYAPP.chowanedok = null;
    MYAPP.chowanedok = [];
    for(var i = 0 ; i < iloscwierszy; i++){
        sprawdzpoprzedniwierszdialog(i);
    }
};

//var bigscreen = function () {
//  try {
//        docElement = document.documentElement;
//        request = docElement.requestFullScreen || docElement.webkitRequestFullScreen || docElement.mozRequestFullScreen || docElement.msRequestFullScreen;
//        if(typeof request!=="undefined" && request){
//        request.call(docElement);
//        }
//        } catch (el) {
//            alert("Blad pelny ekran "+el);
//        }  
//};

var zamykanieDialogow = function() {
    PF('wpisywanie').hide();
    PF('wiersze').hide();
    PF('tablicadokumenty').unselectAllRows();
};

var powrocNaPoczatekDokfk = function() {
    //chowanienapoczatekdok();
    $('#formwpisdokument\\:data2DialogWpisywanie').select();
};

//rodzial dotyczacy umiejscawiania pozycji z  faktury
var petlawywolujaca = function(lw, gr, co) {
    //alert('petlawywolujaca');
    var dlugosc = lw.length - 1;
    for (var j = 0; j < dlugosc; j++) {
        //alert(lw[j]+","+gr[j]+","+co[j]);
        var lewy = parseInt(lw[j]);
        var cos = co[j];
        var gora = parseInt(gr[j]);
        $(document.getElementById(cos)).css({position: "absolute",
            marginLeft: 0, marginTop: 0,
            top: gora, left: lewy});

    }
};

var sprawdzczynazwaskroconafakturaniejestshown = function() {
    var czywidzialne = rj("nazwaskroconafaktura").getAttribute("aria-hidden");
    if (czywidzialne === "true"){
        $(document.getElementById("formkontowybor:wybormenu")).focus();
    }
};