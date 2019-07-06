"use strict";

var pokazwierszedok = function() {
        PF('wiersze').show();
};

var ustawdialogwiersze = function(nazwa,menu) {
    $(document.getElementById(nazwa)).width(1000).height(400);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center top",
        at: "center center",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};

var schowajeditbutton = function(rzad) {
    MYAPP.schowajeditbuttonnr = rzad;
    var wiersz = 'zestawieniedokumentow:dataList:'+rzad+':edytujbutton';
    r(wiersz).hide();
};

var pokazeditbutton = function() {
    try {
        rzad = MYAPP.schowajeditbuttonnr;
        var wiersz = 'zestawieniedokumentow:dataList:'+rzad+':edytujbutton';
        r(wiersz).show();
    } catch (e) {
        
    }
};

var zapisywierszywybordok = function() {
    PF('wpisywanie').show();
    var lp = document.getElementById('zestawieniezapisownakontachpola:wierszDoPodswietlenia').value;
    var nazwa = 'formwpisdokument:dataList:'+lp+':opisdokwpis';
    $(document.getElementById(nazwa)).select();
};

var pokazwierszoznaczony = function() {
    try {
        if (document.getElementById('zestawieniezapisownakontachpola:wierszDoPodswietlenia').value !== "-1") {
//            r("formwpisdokument:data2DialogWpisywanie").focus();
//            r("formwpisdokument:data2DialogWpisywanie").select();
//        } else {
            var lp = document.getElementById('zestawieniezapisownakontachpola:wierszDoPodswietlenia').value;
            //var jest = rj("formwpiskonta:wyborkonta_input").value;
            if (lp !== "-1") {
                var nazwa = "formwpisdokument:dataList:"+lp+":opisdokwpis";
                //r(nazwa).closest("td")[0].click();
                var bliskietd = r(nazwa).closest("td")[0];
                var tablicaid = $(bliskietd).closest(".walkingtable2")[0].id;
                obliczwysokosc(tablicaid);
                var przesun = isScrolledIntoView(bliskietd);
                r(nazwa).select();
                document.getElementById(tablicaid).scrollTop = document.getElementById(tablicaid).scrollTop + przesun;
                document.getElementById('zestawieniezapisownakontachpola:wierszDoPodswietlenia').value = "-1";
            }
        }
    } catch (e){
        //alert('blad '+e);
    }
};

