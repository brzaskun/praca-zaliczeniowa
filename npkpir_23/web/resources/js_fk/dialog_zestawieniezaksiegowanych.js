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
    var nazwa = 'formwpisdokument:dataList:'+lp+':opis';
    $(document.getElementById(nazwa)).select();
};

