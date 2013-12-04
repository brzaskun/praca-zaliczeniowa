/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//podpowiadanie kont w raporcie kasowym        

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


var aktywujwiersz = function(wiersz){
    var i = "#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis";
    if($(i)!== typeof 'undefined'){
    $(i).focus();
    $(i).css('backgroundColor','#ffe');
    }
    chowanienapoczatek();
};
//aktywuje nowy wiersz
var obsluzwiersz = function(wiersz){
    if(!MYAPP.hasOwnProperty('iloscwierszy')){
        MYAPP.iloscwierszy = 1;
    } else {
        MYAPP.iloscwierszy += 1;
    }
    var i = "#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis";
    if($(i)!== typeof 'undefined'){
    $(i).focus();
    $(i).css('backgroundColor','#ffb');
    }
    sprawdzpoprzedniwiersz(wiersz);
};


//robi ukrywanie kolumn dla dialogu edycji
var przygotujdokumentdoedycji = function (){
    try {
    var wiersz = 0;
    while ($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").val()){
        wiersz++;
    };
    //moze byc suma wierszy bo potem jest odpowiedni loop
    if (wiersz>1){
        zakryjpolaedycjadokumentu(wiersz);
    };
    } catch (Exception) {
        alert ("blad w fukncji przygotujdokumentdoedycji jsfk wiersz 82 "+Exception);
    }
};

var drugishow = function (){
        drugi.show();
        //$(MYAPP.zaznaczonepole).focus();
        //$(MYAPP.zaznaczonepole).select(); 
};





//sprawdza czy w poprzenim wierszu sumy sie zgadaja, jak nie to ukrywa odpowiednie pola kazdorazoow przy pwisywaniu
var sprawdzpoprzedniwiersz = function(wiersz){
    try {
    if(wiersz===0){
        return;
    } else {
        var wierszwyzej = wiersz-1;
        var kwotaWn = "#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:wn_input";
        var kwotaMa = "#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:ma_input";
        var wartoscWn = zrobFloat($(kwotaWn).val());
        var wartoscMa = zrobFloat($(kwotaMa).val());
        var roznica = wartoscWn-wartoscMa;
        if(roznica>0){
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").val("kontoma: "+$("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontown_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val(roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").val(roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val(roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").val(roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontown").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontown_hinput").val($("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontown_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontown_input").val($("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontown_input").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").css('backgroundColor','#ffb');
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").select();
            var pozycja = {pozycja: wiersz, blokowany: 'wn'};
            zachowajwtablicydok(pozycja);
        } else if (roznica<0){
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").val("kontown: "+$("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontoma_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val(-roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val(-roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").val(-roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").val(-roznica);
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontoma").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontoma_hinput").val($("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontoma_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontoma_input").val($("#formwpisdokument\\:dataList\\:"+wierszwyzej+"\\:kontoma_input").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").css('backgroundColor','#ffb');
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").select();
            var pozycja = {pozycja: wiersz, blokowany: 'ma'};
            zachowajwtablicydok(pozycja);
            
        }
        chowanienapoczatekdok();
        }
        } catch (Exception){
            alert ("blad w fukncji sprawdzpoprzedniwiersz jsfk wiersz 101 "+Exception);
        }
};

//sprawdza czy w poprzenim wierszu sumy sie zgadaja, jak nie to ukrywa odpowiednie pola robi to dla dialogu seryjnie
var sprawdzpoprzedniwierszdialog = function(wiersz){
    if(wiersz===0){
        return;
    } else {
        var wierszwyzej = wiersz-1;
        var opisbiezacego1 = _.str.include($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").val(),'kontoma');
        var opisbiezacego2 = _.str.include($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").val(),'kontown');
        var opiszawiera = opisbiezacego1 || opisbiezacego2;
        if(opisbiezacego1){
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontown").hide();
            var pozycja = {pozycja: wiersz, blokowany: 'wn'};
            zachowajwtablicydok(pozycja);
        } else if (opisbiezacego2){
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma").hide();
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val());
            $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:kontoma").hide();
            var pozycja = {pozycja: wiersz, blokowany: 'ma'};
            zachowajwtablicydok(pozycja);
        }
        chowanienapoczatekdok();
    }
    
};

    jQuery.isSubstring = function(haystack, needle) {
//            return haystack.indexOf(needle) !== -1;
            return $.inArray(needle,haystack) !== -1;
        };


//kopiuje opis jak nic nie ma
var skopiujopis = function(wiersz){
    if(wiersz===1){
        return;
    } else {
        var starywiersz = wiersz-2;
        wiersz -= 1;
        var biezacyopis = "#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis";
        var biezacyopisval = $(biezacyopis).val();
        var poprzedniopis = $("#formwpisdokument\\:dataList\\:"+starywiersz+"\\:opis").val();
        var tablica = poprzedniopis.split(':');
        for(var w = 0 ; w < MYAPP.iloscwierszy; w++){
            var a = $.isSubstring(tablica,'kontoma');
            var b = $.isSubstring(tablica,'kontown');
        if(a||b){
            starywiersz = starywiersz - 1;
            biezacyopis = "#formwpisdokument\\:dataList\\:"+wiersz+"\\:opis";
            biezacyopisval = $(biezacyopis).val();
            poprzedniopis = $("#formwpisdokument\\:dataList\\:"+starywiersz+"\\:opis").val();
            tablica = poprzedniopis.split(':');
        }
        }
        if(biezacyopisval === ""){
        $(biezacyopis).val(poprzedniopis);
        }
        $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn").focus();
    }
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

var chowanienapoczatek = function(){
     if(!MYAPP.hasOwnProperty('chowane')){
        MYAPP.chowane = [];
    } else {
        for(i = 0; i < MYAPP.chowane.length; i++){
            var blokowany = "#formwpisdokument\\:dataList\\:"+MYAPP.chowane[i].pozycja+"\\:"+MYAPP.chowane[i].blokowany+"_input";
            $(blokowany).hide();
        }
    }
};

var aktualizujmape = function(){
    usunztablicydok();
    chowanienapoczatekdok();
};

var aktualizujmapedialog = function(wiersze){
    usunztablicydok();
    zakryjpolaedycjadokumentu(wiersze);
};

//sub do aktualizuj mape
var chowanienapoczatekdok = function(){
     if(!MYAPP.hasOwnProperty('chowanedok')){
        MYAPP.chowanedok = [];
    } else {
        var dl = MYAPP.chowanedok.length;
        for(i = 0; i < dl; i++){
            var blokowany = "#formwpisdokument\\:dataList\\:"+MYAPP.chowanedok[i].pozycja+"\\:opis";
            var pozycja = MYAPP.chowanedok[i].pozycja;
            $(blokowany).hide();
            blokowany = "#formwpisdokument\\:dataList\\:" + pozycja + "\\:"+MYAPP.chowanedok[i].blokowany;
            $(blokowany).hide();
            blokowany = "#formwpisdokument\\:dataList\\:" + pozycja + "\\:konto"+MYAPP.chowanedok[i].blokowany;
            $(blokowany).hide();
        };
    };
};

var zachowajwtablicy = function(pozycjaszukana){
    //sprawdza czy wystepuje w poli
    var wynik = 0;
    var miejsce;
    var dl = MYAPP.chowanedok.length;
    for(i = 0; i < dl; i++){
        var znaleziono = MYAPP.chowane[i].pozycja;
        if(znaleziono===pozycjaszukana.pozycja){
            wynik = 1;
            miejsce = i;
        }
    }
    if(wynik===0){
        MYAPP.chowane.push(pozycjaszukana);
    } else {
        MYAPP.chowane.splice(miejsce,1,pozycjaszukana);
    }
};

var zachowajwtablicydok = function(pozycjaszukana){
     if(!MYAPP.hasOwnProperty('chowanedok')){
        MYAPP.chowanedok = [];
    }
    //sprawdza czy wystepuje w poli
    var wynik = 0;
    var miejsce;
    var dl = MYAPP.chowanedok.length;
    for(i = 0; i < dl; i++){
        var znaleziono = MYAPP.chowanedok[i].pozycja;
        if(znaleziono===pozycjaszukana.pozycja){
            wynik = 1;
            miejsce = i;
        }
    }
    if(wynik===0){
        MYAPP.chowanedok.push(pozycjaszukana);
    } else {
        MYAPP.chowanedok.splice(miejsce,1,pozycjaszukana);
    }
};

var usunztablicydok = function(){
    var pozycjaszukana = 0;
    if(MYAPP.hasOwnProperty('iloscwierszy')){
        pozycjaszukana = MYAPP.iloscwierszy;
    }
     if(!MYAPP.hasOwnProperty('chowanedok')){
        MYAPP.chowanedok = [];
    }
    //sprawdza czy wystepuje w poli
    var wynik = 0;
    var miejsce;
    var dl = MYAPP.chowanedok.length;
    for(i = 0; i < dl; i++){
        var znaleziono = MYAPP.chowanedok[i].pozycja;
        if(znaleziono===pozycjaszukana){
            wynik = 1;
            miejsce = i;
        }
    }
    if(wynik===0){
    } else {
        MYAPP.chowanedok.splice(miejsce,1);
    }
    if(!MYAPP.hasOwnProperty('iloscwierszy')){
        MYAPP.iloscwierszy = 0;
    } else {
        MYAPP.iloscwierszy -= 1;
    }
};


// to byly rzeczy dotyczace pelnej ksiegowosci



//uzupelnia pole Ma wartoscia pola Wn jezeli jego wartosc jest == 0
var sprawdzwartosc = function(wiersz){
    wiersz -= 1;
    var zloz = "#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input";
    var wartosc = $(zloz).val();
    if(wartosc === "0.00"){
        $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_hinput").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_hinput").val());
        $("#formwpisdokument\\:dataList\\:"+wiersz+"\\:ma_input").val($("#formwpisdokument\\:dataList\\:"+wiersz+"\\:wn_input").val());
    }
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


