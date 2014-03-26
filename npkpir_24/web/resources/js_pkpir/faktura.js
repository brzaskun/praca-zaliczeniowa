"use strict";

var aktywujpolewyboruklientanafakturze = function (){
                       $(document.getElementById("akordeon:formstworz:acForce_input")).focus();
                       $(document.getElementById("akordeon:formstworz:acForce_input")).select();
                       $(document.getElementById("akordeon:formstworz:acForce_input")).trigger("keyup");
                   };
 
var wybierzrzadfaktury = function (){
                       nazwa = ".komorka";
                       $(nazwa).last().focus();
                       $(nazwa).last().select();
                   };
                   
var przeskoczdoceny = function (){
    if ($('#akordeon\\:formstworz\\:rzad\\:0\\:nazwa').val()) {
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).focus();
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).select();
    }
};


