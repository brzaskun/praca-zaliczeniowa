"use strict";

var aktywujpolewyboruklientanafakturze = function (){
    $(document.getElementById("akordeon:formstworz:datawystawienia")).focus();
    $(document.getElementById("akordeon:formstworz:datawystawienia")).select();
};
 
var wybierzrzadfaktury = function (){
    var nazwa = ".komorka";
    $(nazwa).last().focus();
    $(nazwa).last().select();
};

var wybierzrzadfakturykorekta = function (){
    var nazwa = ".komorkak";
    $(nazwa).last().focus();
    $(nazwa).last().select();
};
                   
var przeskoczdoceny = function (){
    if ($('#akordeon\\:formstworz\\:rzad\\:0\\:nazwa').val()) {
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).focus();
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).select();
    }
};


