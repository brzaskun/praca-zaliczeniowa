var aktywujpolewyboruklientanafakturze = function (){
                       $(document.getElementById("akordeon:formstworz:acForce_input")).focus();
                       $(document.getElementById("akordeon:formstworz:acForce_input")).select();
                   };
 
var wybierzrzadfaktury = function (numer){
                       nazwa = "akordeon:formstworz:rzad:"+numer+":nazwa";
                       $(document.getElementById(nazwa)).focus();
                       $(document.getElementById(nazwa)).select();
                   };
                   
var przeskoczdoceny = function (){
    if ($('#akordeon\\:formstworz\\:rzad\\:0\\:nazwa').val()) {
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).focus();
        $(document.getElementById("akordeon:formstworz:rzad:0:cena_input")).select();
    }
};


