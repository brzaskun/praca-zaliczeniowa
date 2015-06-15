var zachowajwiersz = function (lpwiersza, wnlubma, typwiersza) {
    try {
        var source = event.target || event.srcElement;
        MYAPP.lpwiersza = lpwiersza;
        MYAPP.wnlubma = wnlubma;
        MYAPP.zaznaczonepole = event.target;
        MYAPP.typwiersza = typwiersza;
        //document.getElementById("zaznaczonakomorka").innerHTML = event.target ;
//        $(document.getElementById("wpisywaniefooter:wierszid")).val(lpwiersza);
//        $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
        $(document.getElementById("wpisywaniefooter:typwiersza")).val(typwiersza);
        if (source.value === null) {
            MYAPP.liczydloWcisnietychEnter = 1;
        }
        //setTimeout(focusNaNowoDodanym(source), 100);
        console.log("lpwiersza " + lpwiersza + " wnma " + wnlubma + " typwiersza "+ typwiersza);
    } catch (blad) {
        alert("Blad w dialgowprowadzanie.js zachowaj wiersz " + blad);
    }
};

var zachowajwierszVATRK = function (lp) {
    try {
        console.log("zachowajwierszVATRK "+lp);
        MYAPP.lpwiersza = "formwpisdokument:dataList:"+lp+":opis";
        $(document.getElementById("wpisywaniefooter:wierszid")).val(lp);
        $(document.getElementById("wpisywaniefooter:lpwierszaRK")).val(lp);
    } catch (blad) {
        //alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};

var odtworzwierszVATRK = function(lp) {
    var id = parseInt(lp)-1;
    MYAPP.lpwiersza = "formwpisdokument:dataList:"+id+":opis";
    if (MYAPP.lpwiersza) {
//        $(data).removeClass('ui-state-focus');
//        $(data).addClass('ui-state-default');
        r(MYAPP.lpwiersza).focus();
        r(MYAPP.lpwiersza).select();
        r(MYAPP.lpwiersza).keyup();
        document.activeElement = rj(MYAPP.lpwiersza);
        delete MYAPP.lpwiersza;
    }
};

var wpisywanieOnShow = function (szer, wys) {
    try {
        ustawdialog('dialogpierwszy','menudokumenty',szer, wys);
        $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).focus();
        $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).select();
    } catch (Exception) {
        alert ("blad w fukncji wpisywanieOnShow jsfk wiersz 73 "+Exception);
    }
};

var wybierzdate = function () {
    try {
        var zawartosc = document.getElementById('formwpisdokument:data2DialogWpisywanie').value;
        if (zawartosc === "") {
            $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).focus();
            $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).select();
        }
    } catch (e) {
        $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).focus();
        $(document.getElementById('formwpisdokument:data2DialogWpisywanie')).select();
    }
}

var wpisywanieOnHide = function () {
    resetujdialog('dialogpierwszy');
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
    $(document.getElementById('wpisywaniefooter:przywrocwpisbutton')).click();
};

var focusNowyVAT = function (wiersz) {
    setTimeout(aktywujPierwszePoleNowegoWierszaVAT(wiersz), 500);
};

//aktywuje nowy wiersz
var aktywujPierwszePoleNowegoWierszaVAT = function(wiersz){
    var lp = wiersz.name.split(":")[2];
    var nextlp = parseInt(lp)+1;
    var nextwiersz = "formwpisdokument:tablicavat:"+nextlp+":netto_input";
    try {
        var tresc = document.getElementById(nextwiersz).value;
        if (tresc === "0.00 zł") {
            $(document.getElementById(nextwiersz)).focus();
            $(document.getElementById(nextwiersz)).select();
            event.preventDefault();
            event.stopPropagation();
            event.stopImmediatePropagation();
        }
    } catch (e) {
        console.log("nie ma pola");
    }
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


var znalezionoduplikat = function() {
    if (rj('formwpisdokument:stanprzyciskuzapis').innerText === "false") {
        document.getElementById('formwpisdokument:numerwlasny').focus();
        document.getElementById('formwpisdokument:numerwlasny').select();
    }
};


var czydodackontoShow = function (){
    $(document.getElementById('czydodackonto')).width(300).height(80);
    try {
        $(document.getElementById('czydodackonto')).position({
        my: "center center",
        at: "center center",
        of: $(document.getElementById('dialogpierwszy')),
        collision: "none none"
        
    });
    r("formczydodackonto:poleinput").focus();
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog_wpisywanie.js wiersz 160 "+Exception);
    }
    
};

var dodajnowegoklienta = function () {
    var zawartosc = $('#formwpisdokument\\:acForce_input').val();
    if (zawartosc === "nowy klient") {
        PF('dlgwprowadzanieklienta').show();
    }
};

var sprawdzczymakonto = function (niemakonta0makonto1) {
        if (niemakonta0makonto1) {
            PF('czydodackonto').show();
            //$(document.getElementById("formczydodackonto:czydodackontookbutton")).focus();
        }
    };

var focusNaNowoDodanym = function(source) {
    try {
        var rzedy = r("formwpisdokument:dataList_data").children();
        var dlugosc = rzedy.length;
            var wybrany = $(rzedy[dlugosc-1]).find('td').get(1);
            //console.log(wybrany);
            if (typeof wybrany !== 'undefined') {
                if ($(wybrany.firstChild).val() == "") {
                    $(wybrany.firstChild).focus();
                    document.return
//                    var poprzednie = $(rzedy[parseInt(nrkolejny-1)]).find('td');
//                    try {
//                        for (var i = 0; i < poprzednie.length; i++) {
//                            if (typeof $(poprzednie[i]).find("input").get(0) !== 'undefined') {
//                                if ($($(poprzednie[i]).find("input").get(0)).hasClass("ui-state-focus")){
//                                    $(poprzednie[i]).find("input").get(0).removeClass("ui-state-focus");
//                                }   
//                            }
//                            //alert("robie "+poprzednie[i]);
//                            //console.log("robie "+wybrany[pole]);
//                        }
//                    } catch (ex) {
//                        
//                    }
                    return;
                }
            } 
    } catch (e) {

    }
    ;
};

//usuwa podswietlenie z wierszy tabeli wpisywania
var usunpodswietlenie = function(source) {
    try {
        var rzedy = r("formwpisdokument:dataList_data").children();
        var poprzednie = $(rzedy[parseInt(rzedy.length - 2)]).find('td');
        try {
            for (var i = 0; i < poprzednie.length; i++) {
                if (typeof $(poprzednie[i]).find("input").get(0) !== 'undefined') {
                    if ($($(poprzednie[i]).find("input").get(0)).hasClass("ui-state-focus")) {
                        $($(poprzednie[i]).find("input").get(0)).removeClass("ui-state-focus");
                    }
                }
            }
        } catch (ex) {

        }
    } catch (e) {

    };
};

var focusNowyWiersz = function () {
    setTimeout(robfocus(), 500);
};

var robfocus = function() {
   var wiersze = $(document.getElementById("formwpisdokument:dataList_data")).children("tr");
    var dlugoscwierszy = wiersze.length;
    var poprzedniid = dlugoscwierszy - 2;
    var obecnywierszid = dlugoscwierszy - 1;
    var wiersz = "formwpisdokument:dataList:" + obecnywierszid + ":opis";
    try {
        var tresc = document.getElementById(wiersz).value;
        if (tresc === "") {
            document.activeElement.blur();
            var wierszpoprzedniMa = "formwpisdokument:dataList:"+poprzedniid+":kontoma_input";
            var wierszpoprzedniWn = "formwpisdokument:dataList:"+poprzedniid+":kontown_input";
            r(wierszpoprzedniWn).removeClass("ui-state-focus");
            r(wierszpoprzedniMa).removeClass("ui-state-focus");
            r(wiersz).focus();
            r(wiersz).select();
            event.preventDefault();
            event.stopPropagation();
            event.stopImmediatePropagation();
        }
    } catch (e) {

    } 
};

var focusNaNowoDodanymEnter = function() {
    try {
        var rzedy = r("formwpisdokument:dataList_data").children();
        for (var rzad in rzedy) {
            var wybrany = $(rzedy[rzad]).find('td').get(1);
            if (typeof wybrany !== 'undefined') {
                if ($(wybrany.firstChild).val() == "") {
                    $(wybrany.firstChild).focus();
                    return;
                }
            } else {
                break;
            }
        }
        for (var i = 0; i < rzedy.length; i++) {
            for (var n in $(rzedy[i]).find('td')) {
                var kolejnetd = $(rzedy[i]).find('td').get(n);
                if (kolejnetd.localName === "td") {
                    try {
                        if ($(kolejnetd.firstChild.firstChild).hasClass("ui-autocomplete-input ui-inputfield")) {
                            if ($(kolejnetd.firstChild.firstChild).val() === "") {
                                $(kolejnetd.firstChild.firstChild).focus();
                                return;
                            }
                        }
                    } catch (e) {
                    }
                } else {
                    break;
                }
            }
        };
    } catch (e) {
        console.log("Blad dialog_wpisywanie.js focusNaNowoDodanymEnter "+e)
    };
};

var kopiujnazwepelna = function () {
  var skadkopiowac = rj("formXNowyKlient:nazwaPole").value;
  var dokadkopiowac = rj("formXNowyKlient:symbolPole").value;
  if (dokadkopiowac === "") {
      rj("formXNowyKlient:symbolPole").value = skadkopiowac;
  }
};

var obsluzewidencjavatsprzedaz = function () {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode === 107) {
        event.target.value = 0.0;
        r("formwpisdokument:dataList:0:opis").focus();
        event.preventDefault();
        event.stopPropagation();
        event.stopImmediatePropagation();
    }
    
};

var pokazwybortransakcji = function() {
    var typkonta = rj("parametry:aktualnywierszrozrachunkow").value;
    if (typkonta === "0") {
        var czywidzialne = rj("dialogdrugi").getAttribute("aria-hidden");
        if (czywidzialne === "true"){
            setTimeout(PF('transakcjawybor').show(), 500);
        }
    } else if (typkonta !== "-1") {
        var czywidzialne = rj("niemarachunkow").getAttribute("aria-hidden");
        if (czywidzialne === "true"){
            setTimeout(PF('rozrachunki').show(), 500);
        }
    } else if (typkonta === "-2") {
        setTimeout(PF('niemarachunkow').show(), 500);
    }
};

var pokazwybortransakcjidialog = function() {
    var typkonta = rj("parametry:aktualnywierszrozrachunkow").value;
    if (typkonta === "2") {
        var czywidzialne = rj("dialogdrugi").getAttribute("aria-hidden");
        if (czywidzialne === "true"){
            PF('transakcjawybor').hide();
            setTimeout(PF('rozrachunki').show(), 1000);
        }
    } else if (typkonta === "1") {
        PF('transakcjawybor').hide();
        powrotDoStronyPoWyborzeRachunekPlatnosc();
    } else if (typkonta === "-2") {
        setTimeout(PF('niemarachunkow').show(), 1000);
    }
};

var pobierznumergrupywierszy = function(lpmacierzystego, lpwiersza) {
    try {
        var dotychczasowagrupa;
        var nrgr = (lpmacierzystego === 0 ? lpwiersza : lpmacierzystego);
        if (MYAPP.hasOwnProperty("nrgrupywierszy")) {
            dotychczasowagrupa = MYAPP.nrgrupywierszy;
        }
        if (dotychczasowagrupa === nrgr) {
            //console.log("tasamagrupa "+nrgr);
        } else {
            //console.info("nowagrupa "+nrgr);
            MYAPP.nrgrupywierszy = nrgr;
            if (typeof dotychczasowagrupa !== "undefined") {
                $(document.getElementById("wpisywaniefooter:nrgrupyaktualny")).val(nrgr);
                $(document.getElementById("wpisywaniefooter:nrgrupywierszy")).val(dotychczasowagrupa);
                $(document.getElementById("wpisywaniefooter:sprawdzwartoscigrupy")).click();
            } else {
                $(document.getElementById("wpisywaniefooter:nrgrupyaktualny")).val(nrgr);
                $(document.getElementById("wpisywaniefooter:nrgrupywierszy")).val(nrgr);
            }
        }
        console.log("lpmacierzystego "+lpmacierzystego+" lpwiersza "+lpwiersza);
    }  catch (blad) {
        alert("Blad w dialgowprowadzanie.js pobierznumergrupywierszy " + blad);
    }
};

var sprawdzgrupeprzykliknieciuwzapisz = function() {
    $(document.getElementById("wpisywaniefooter:sprawdzwartoscigrupy")).click();
};


var podswietlznalezionywierszzbrakiem = function(nrwiersza) {
    var nr1 = "formwpisdokument:dataList:"+nrwiersza+":opis";
    var nr2 = "formwpisdokument:dataList:"+nrwiersza+":wn_input";
    var nr3 = "formwpisdokument:dataList:"+nrwiersza+":kontown_input";
    var nr4 = "formwpisdokument:dataList:"+nrwiersza+":ma_input";
    var nr5 = "formwpisdokument:dataList:"+nrwiersza+":kontoma_input";
    r(nr1).css( "background-color", "#E0E0E0" );
    r(nr1).css( "color", "red" );
    r(nr2).css( "color", "red" );
    r(nr3).css( "color", "red" );
    r(nr4).css( "color", "red" );
    r(nr5).css( "color", "red" );
};

var obsluzDateWiersza = function(idwiersza) {
  var biezacadata = r("formwpisdokument:dataList:"+idwiersza+":dataWiersza");
  var biezacadataVal = biezacadata.val();
  if (biezacadataVal === "") {
      if (idwiersza > 0) {
          var idpoprzedni = idwiersza-1;
          var poprzedniadata = r("formwpisdokument:dataList:"+idpoprzedni+":dataWiersza").val();
          biezacadata.val(poprzedniadata);
      }
  } else if (biezacadataVal.length === 1) {
      biezacadata.val("0"+biezacadataVal);
  } else if (biezacadataVal.length === 2) {
      var dataint = parseInt(biezacadataVal);
      if (dataint > 31) {
          biezacadata.val(null);
      }
  }
};

var skopiujKwoteZWierszaWyzej = function (idwiersza) {
  var biezacastronawiersza = r("formwpisdokument:dataList:"+idwiersza+":wn_input");
  var biezacastronawierszaH = r("formwpisdokument:dataList:"+idwiersza+":wn_hinput");
  var biezacakwota = biezacastronawiersza.val();
  if (biezacakwota === "0.00") {
      if (idwiersza > 0) {
          var idpoprzedni = idwiersza-1;
          var kwotawierszwyzej = r("formwpisdokument:dataList:"+idpoprzedni+":wn_input").val();
          biezacastronawiersza.val(kwotawierszwyzej);
          biezacastronawierszaH.val(zrobFloat(kwotawierszwyzej));
      }
  }
};

var skopiujKwoteZeStronaWn = function (idwiersza) {
  var biezacastronawiersza = r("formwpisdokument:dataList:"+idwiersza+":ma_input");
  var biezacastronawierszaH = r("formwpisdokument:dataList:"+idwiersza+":ma_hinput");
  var biezacakwota = biezacastronawiersza.val();
  if (biezacakwota === "0.00") {
          var kwotawierszWn = r("formwpisdokument:dataList:"+idwiersza+":wn_input").val();
          biezacastronawiersza.val(kwotawierszWn);
          biezacastronawierszaH.val(zrobFloat(kwotawierszWn));
  }
};

var powrotpozmianietabeli = function() {
    r(MYAPP.lpwiersza).focus();
    r(MYAPP.lpwiersza).select();
};

var kopiujdatedialogwpis = function() {
    var wierszbiezacy = event.target.id;
    if (document.getElementById(wierszbiezacy).value === ""){
        var skladnia = wierszbiezacy.split(":");
        var lpwiersza = skladnia[2];
        if (lpwiersza !== "0") {
            var polepoprzednie = parseInt(lpwiersza)-1;
            var wierszpoprzedni = skladnia[0]+":"+skladnia[1]+":"+polepoprzednie+":"+skladnia[3];
            document.getElementById(wierszbiezacy).value = document.getElementById(wierszpoprzedni).value;
            r(wierszbiezacy).trigger("change");
        }
    }
};