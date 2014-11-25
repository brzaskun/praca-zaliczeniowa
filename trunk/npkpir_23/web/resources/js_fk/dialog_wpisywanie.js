var zachowajwiersz = function (lpwiersza, wnlubma, typwiersza) {
    try {
    var source = event.target || event.srcElement;
    MYAPP.lpwiersza = lpwiersza;
    MYAPP.wnlubma = wnlubma;
    MYAPP.zaznaczonepole = event.target;
    MYAPP.typwiersza = typwiersza;
    //document.getElementById("zaznaczonakomorka").innerHTML = event.target ;
    $(document.getElementById("wpisywaniefooter:wierszid")).val(lpwiersza);
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
    $(document.getElementById("wpisywaniefooter:typwiersza")).val(typwiersza);
    setTimeout(focusNaNowoDodanym(source),5000);
    } catch (blad) {
        //alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var zachowajwierszVATRK = function (source) {
    try {
        var lp = parseInt(source)-1;
        MYAPP.lpwiersza = "formwpisdokument:dataList:"+lp+":opis";
    } catch (blad) {
        //alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var odtworzwierszVATRK = function(data) {
    if (MYAPP.lpwiersza) {
        $(data).removeClass('ui-state-focus');
        $(data).addClass('ui-state-default');
        r(MYAPP.lpwiersza).focus();
        r(MYAPP.lpwiersza).select();
        r(MYAPP.lpwiersza).keyup();
        document.activeElement = rj(MYAPP.lpwiersza);
        delete MYAPP.lpwiersza;
    }
};

var wpisywanieOnShow = function () {
    try {
        ustawdialog('dialogpierwszy','menudokumenty',1100,700);
        //drugi.hide();
        //przygotujdokumentdoedycji();
        pozazieleniajNoweTransakcje();
        $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).focus();
        $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).select();
    } catch (Exception) {
        alert ("blad w fukncji pierwszyonShow jsfk wiersz 73 "+Exception);
    }
};

var wybierzdate = function () {
    try {
        var zawartosc = document.getElementById('formwpisdokument:data1DialogWpisywanie').value;
        if (zawartosc === "") {
            $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).focus();
            $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).select();
        }
    } catch (e) {
        $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).focus();
        $(document.getElementById('formwpisdokument:data1DialogWpisywanie')).select();
    }
}

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

var aktywujbiezacepole = function(index, strona) {
    if (strona === "Wn") {
        var i = "formwpisdokument:dataList:"+nrWiersza+":kontown_input";
        var i_obj = document.getElementById(i);
        if (i_obj) {
            $(i_obj).css('backgroundColor','#ffb');
            $(i_obj).focus();
            $(i_obj).select();
        }
    } else {
        var i = "formwpisdokument:dataList:"+nrWiersza+":kontoma_input";
        var i_obj = document.getElementById(i);
        if (i_obj) {
            $(i_obj).css('backgroundColor','#ffb');
            $(i_obj).focus();
            $(i_obj).select();
        }
    }
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

var znalezionoduplikat = function() {
    document.getElementById('formwpisdokument:numerwlasny').focus();
    document.getElementById('formwpisdokument:numerwlasny').select();
    r('formwpisdokument:wpiszdokbutton').hide();
    
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

var czydodackontoShow = function (){
    $(document.getElementById('czydodackontoShow')).width(300).height(80);
    try {
        $(document.getElementById('czydodackonto')).position({
        my: "center center",
        at: "center center",
        of: $(document.getElementById('dialogpierwszy')),
        collision: "none none"
    });
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog_wpisywanie.js wiersz 160 "+Exception);
    }
    $(document.getElementById("formczydodackonto:czydodackontookbutton")).focus();
};

var focusNaNowoDodanym = function(source) {
    try {
        var rzedy = r("formwpisdokument:dataList_data").children();
        for (var rzad in rzedy) {
            var wybrany = $(rzedy[rzad]).children().get(1);
            console.log(wybrany);
            if ($(wybrany.firstChild).val() == "") {
                $(wybrany.firstChild).focus();
                break;
            }
        }
    } catch (e) {
        
    };
};

var kopiujnazwepelna = function () {
  var skadkopiowac = rj("formX:nazwaPole").value;
  var dokadkopiowac = rj("formX:symbolPole").value;
  if (dokadkopiowac === "") {
      rj("formX:symbolPole").value = skadkopiowac;
  }
};



