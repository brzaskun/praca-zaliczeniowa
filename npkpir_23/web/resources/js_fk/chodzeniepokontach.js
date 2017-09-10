"use strict";
    
//var zachowajnumerwiersza = function(zmienna ,numer, tabela, event) {
//        MYAPP.zmienna = zmienna;
//        MYAPP[MYAPP.zmienna] = numer;
//        var parent = event.target || event.srcElement;
//        if (tabela !== "") {
//            MYAPP.sourceid = tabela;
//        } else {
//            do {
//                parent = parent.parentNode;
//            } while (parent.className !== "ui-datatable-data ui-widget-content");
//            MYAPP.sourceid = parent.id;//tu mamy informacje, wiersz ktorej tabeli jest klikniety
//        }
//        
//        if (MYAPP.sourceid === "tabelanowerozrachunki:tabela_data") {
//            MYAPP.tabeladata = "tabelanowerozrachunki:tabela_data";
//            MYAPP.tabela = "tabelanowerozrachunki:tabela";
//            MYAPP.zmienna = "zmiennadata";
//            MYAPP.wyliczaj = false;
//        } else if (MYAPP.sourceid === "tabelazzapisami:tabela_data") {
//            MYAPP.tabeladata = "tabelazzapisami:tabela_data";
//            MYAPP.tabela = "tabelazzapisami:tabela";
//            MYAPP.zmienna = "zmiennazapisy";
//            MYAPP.wyliczaj = false;
//        } else if (MYAPP.sourceid === "zestawieniedokumentow:dataList_data") {
//            MYAPP.tabeladata = "zestawieniedokumentow:dataList_data";
//            MYAPP.tabela = "zestawieniedokumentow:dataList";
//            MYAPP.zmienna = "zmiennazaksiegowane";
//            MYAPP.wyliczaj = false;
//        } else if (MYAPP.sourceid === "formwpisdokument:tablicavat_data") {
//            MYAPP.tabeladata = "formwpisdokument:tablicavat_data";
//            MYAPP.tabela = "formwpisdokument:tablicavat";
//            MYAPP.zmienna = "zmiennavat";
//            MYAPP.wyliczaj = false;
//        } else if (MYAPP.sourceid === "formtablicaanalityczne:tablicasaldaanalityczne_data") {
//            MYAPP.tabeladata = "formtablicaanalityczne:tablicasaldaanalityczne_data";
//            MYAPP.tabela = "formtablicaanalityczne:tablicasaldaanalityczne";
//            MYAPP.zmienna = "zmiennatablicaanalityczne";
//            MYAPP.wyliczaj = false;
//        } else if (MYAPP.sourceid === "form1:dataList_data") {
//            MYAPP.tabeladata = "form1:dataList_data";
//            MYAPP.tabela = "form1:dataList";
//            MYAPP.zmienna = "zmiennarozrachunki";
//            MYAPP.wyliczaj = false;
//        }
//};

var zachowajobiekt = function(obiekt, event) {
    try {
        if (event !== null) {
            var source = event.target || event.srcElement;
        } else {
            var source = obiekt;
        }
        if (source != null) {
            var sourceid = $(source).closest(".walkingtable")[0].id;
            MYAPP.sourceid = sourceid;
            var tabeladata = sourceid+"_data";
            var listawierszy = r(tabeladata).children("tr");
            var lppierwszywiersz = $(listawierszy[0]).find(".lpwiersza").text();
            var lpwiersznast = $.trim($(obiekt).closest("tr").find(".lpwiersza").text()); // trim to remove end space, closest gets closest parent of selected type
            var numerwiersza = lpwiersznast - lppierwszywiersz;
            MYAPP.tabeladata = tabeladata;
            MYAPP.tabela = sourceid;
            MYAPP.zmienna = "zmiennazapisy";
            MYAPP[MYAPP.zmienna] = numerwiersza;
            obliczwysokosc(sourceid);
            MYAPP.obiekt = obiekt;
            stop();
        }
    } catch (ex) {
        alert("Blad w zachowajobiekt/chodzeniepokonach.js zachowajobiekt" + ex.toString());
    }
};


var idz = function(DolGora) {
        var elem = document.getElementById(MYAPP.tabela);
        if (elem) {
            var wiersze = $(document.getElementById(MYAPP.tabeladata)).children("tr");
            var dlugoscwierszy = wiersze.length;
            var staretd = pobierzelementklinalny(wiersze);
            if (DolGora === "D") {
                zmiennadol(dlugoscwierszy);
            } else {
                zmiennagora(dlugoscwierszy);
            }
            var nowetd = pobierzelementklinalny(wiersze);
            if (nowetd.length > 0) {
                var przesun = isScrolledIntoView(nowetd);
                elem.scrollTop = elem.scrollTop + przesun;
                try {
                    if (DolGora === "D") {
                        znajdzklikmiejsce(nowetd).click();
                        stop();
                    } else {
                        znajdzklikmiejsce(staretd).click();
                        var przenies = znajdzklikmiejsce(nowetd);
                        zachowajobiekt(przenies, null);
                        stop();
                    }
                } catch (e){}
            } else {
                if (DolGora === "D") {
                    MYAPP[MYAPP.zmienna] = MYAPP["magazyn"];
                } else {
                    MYAPP[MYAPP.zmienna] = MYAPP["magazyn"];
                }
            }
            //document.getElementById("poledanych1").innerHTML= ' klikam na '+komorki[3].innerText+' ';
            MYAPP.przetwarzajdalej = false;
        }
};

var pobierzelementklinalny = function(wiersze) {
    if ($(wiersze[MYAPP[MYAPP.zmienna]]).find(".checkwiersza").length > 0) {
        return  $(wiersze[MYAPP[MYAPP.zmienna]]).find(".checkwiersza");
    } else {
        return  $(wiersze[MYAPP[MYAPP.zmienna]]).find(".niecheckwiersza");
    }
};

var znajdzklikmiejsce = function(td) {
    if ($(td).find("span").length > 0) {
        return $(td).find("span")[0];
    } else {
        $(td).closest("td").click();
        return $(td);
    }
};

var zmiennagora = function (dlugoscwierszy) {
    MYAPP["magazyn"] = MYAPP[MYAPP.zmienna];
    if (MYAPP[MYAPP.zmienna] > dlugoscwierszy) {
        MYAPP[MYAPP.zmienna] = dlugoscwierszy;
    } else if (MYAPP[MYAPP.zmienna] === 0) {
        MYAPP[MYAPP.zmienna] = 0;
    } else {
        MYAPP[MYAPP.zmienna] -= 1;
    }
};

var zmiennadol = function(dlugoscwierszy) {
    MYAPP["magazyn"] = MYAPP[MYAPP.zmienna];
    if (MYAPP[MYAPP.zmienna] >= dlugoscwierszy - 1) {
        MYAPP[MYAPP.zmienna] = dlugoscwierszy - 1;
    } else if (MYAPP[MYAPP.zmienna] === 0) {
        MYAPP[MYAPP.zmienna] = 1;
    } else {
        MYAPP[MYAPP.zmienna] += 1;
    }
};

var obliczwysokosc = function(sourceid) {
        MYAPP.top = r(sourceid).offset().top;
        MYAPP.height = r(sourceid).height();
        MYAPP.bottom = MYAPP.top+MYAPP.height;  
};

var stop = function () {
    event.preventDefault();
    event.stopPropagation();
    event.stopImmediatePropagation();
};

var isScrolledIntoView = function(elem) {
    try {
        //tak daleko zeby dotrzec do kontenera
        var docViewTop = MYAPP.top+30;
        var docViewBottom = MYAPP.bottom-100;
        var viewableheight = elem.scrollHeight;
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        var przesuniecie = 0;
        var zadanepolozenie = docViewTop + viewableheight;
        if (elemTop < (zadanepolozenie)) {
            var obliczonaroznica = elemTop - zadanepolozenie;
            przesuniecie += obliczonaroznica;
        }
        if (elemBottom > docViewBottom) {
            przesuniecie += elemBottom - docViewBottom;
        }
        return przesuniecie;
    } catch (ex) {
    }
    return 0;
};

    
var znajdzwierszzkontonumer = function(wiersze, wartosc) {
    var iloscrzedow = wiersze.size();
    try {
        for(var i = 0; i < iloscrzedow; i++) {
            var trescwiersza = $(wiersze[i]).children()[2].innerText;
//            if (trescwiersza.length < 3) {
//                trescwiersza = $(wiersze[i]).children()[2].innerText;
//            }
            if (trescwiersza === wartosc) {
                return wiersze[i];
            }
        }
    } catch (e) {
        alert('error');
    }
};

var zaznacznoda = function(tabela, tabela1, inputpole) {
    try {
        var wartosc = document.getElementById(inputpole).value;
        obliczwysokosc(tabela1);
        if (wartosc !== " ") {
            wartosc = wartosc.trim().split(" ");
            var wiersze = $(document.getElementById(tabela)).children("tr");
            var node = znajdzwierszzkontonumer(wiersze, wartosc[0]);
            var komorki = $(node).children("td");
            var przesun = isScrolledIntoView(komorki[1]);
            var elem = document.getElementById(tabela1);
            elem.scrollTop = elem.scrollTop + przesun +800;
            $(node).children("td")[1].click();
            MYAPP.obiekt = node;
            przejdzwierszNode(tabela, tabela1, node);
            document.getElementById(inputpole).value = "";
            document.getElementById(inputpole).value = "";
        }
    } catch (ex) {
        alert("Problem z zaznacznoda/chodzeniepokontach.js zaznacznoda "+ex);
    }
};

var przejdzwierszNode = function(tabela, tabela1, node) {
    var wiersze = $(document.getElementById(tabela)).children("tr");
    var iloscrzedow = wiersze.size();
    try {
        var numerwiersza = 0;
        for(var i = 0; i < iloscrzedow; i++) {
            if (wiersze[i] === node) {
                numerwiersza = i;
            }
        }
    } catch (e) {
        alert("Problem z przejdzwierszNode/chodzeniepokontach.js przejdzwierszNode");
    }
    var komorki = $(wiersze[numerwiersza]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};