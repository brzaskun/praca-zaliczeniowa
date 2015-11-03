"use strict";

var selectpustepolebo = function(nrlisty,nrwiersza)  {
    try {
        nrwiersza = parseInt(nrwiersza)-1;
        var nazwa = "formbilanswprowadzanie:tabviewbilans:tab"+nrlisty+":"+nrwiersza+":konto_input";
        if (rj(nazwa).value === " " || rj(nazwa).value === "") {
            rj(nazwa).focus();
            rj(nazwa).select();
        }
    } catch (e) {
        
    }
};

//wywalilem to bo spowalnialo przy duzej ilosci kont
var podswietltesamekonta = function(nrlisty,wiersz) {
     try {
        var nrkonta = wiersz.value.split(" ")[0];
        var tabela = "formbilanswprowadzanie:tabviewbilans:tab"+nrlisty+"_data";
        var data = r(tabela).find("tr");
        for (var inx in data) {
            var konto = data[inx].children[1].children[0].children[0].value.split(" ")[0];
            if (konto === nrkonta) {
                var ch = $(data[inx]).find(".kontobo");
                var chch = $(ch).find(":input");
                for (var i = 0; i < chch.length; i++) {
                    $(chch[i]).css( "color", "green");
                    $(chch[i]).css( "font-weight", "600");
                }
            } else {
                var ch = $(data[inx]).find(".kontobo");
                var chch = $(ch).find(":input");
                for (var i = 0; i < chch.length; i++) {
                    $(chch[i]).css( "color", "initial");
                    $(chch[i]).css( "font-weight", "normal");
                }
            }
        }
        if (rj(nazwa).value === " " || rj(nazwa).value === "") {
            rj(nazwa).focus();
            rj(nazwa).select();
        }
    } catch (e) {
        
    }
};

//r(ch).css( "font-weight", "900");
//r(ch).css( "font-style", "italic");
