"use strict";

var sprawdzczybrakklientafaktura = function() {
    var zawartosc = $('#akordeon\\:formstworz\\:acForce_input').val();
    if (zawartosc === "nowy klient") {
        PF('dialognowyklientfaktura').show();
    }
};

var skopiujdanenowegoklientanowafaktura = function () {
    var szukana = document.getElementById('formnkfaktura:nazwaPole').value;
    PF('dialognowyklientfaktura').hide();
    try {
        $('#akordeon\\:formstworz\\:acForce_input').val(document.getElementById('formnkfaktura:nazwaPole').value);
        $('#akordeon\\:formstworz\\:acForce_hinput').val(document.getElementById('formnkfaktura:nazwaPole').value);
        $('#akordeon\\:formstworz\\:acForce_input').focus();
        $('#akordeon\\:formstworz\\:acForce_input').select();
        PF('tworzenieklientapolenazwy').search(szukana);
    } catch (e) {
    }
};

var kopiujnazwepelna = function () {
  var skadkopiowac = rj("formnkfaktura:nazwaPole").value;
  var dokadkopiowac = rj("formnkfaktura:symbolPole").value;
  if (dokadkopiowac === "") {
      rj("formnkfaktura:symbolPole").value = skadkopiowac;
  }
};

var kopiujnazwepelnakontrahenci = function () {
  var skadkopiowac = rj("formX:nazwaPole").value;
  var dokadkopiowac = rj("formX:symbolPole").value;
  if (dokadkopiowac === "") {
      rj("formX:symbolPole").value = skadkopiowac;
  }
};

var wykresllokal_wykazklientow_dialog = function() {
  var pole = rj("formX:lokalPole").value;
  if (pole === "") {
      rj("formX:lokalPole").value = "-";
  }
};

var kliknijpolekontrahenta = function (nip) {
    PF('tworzenieklientapolenazwy').search(nip);
    PF('tworzenieklientapolenazwy').activate();
    PF('tworzenieklientapolenazwy').close();
};

var walidacjadatyzaplaty = function () {
    var cowpisano = rj("formdatazaplaty:datazaplaty").value;
    var czyjest = cowpisano.indexOf("_");
    if (czyjest === -1) {
        r("formdatazaplaty:datazaplatybutton").show();
        r("formdatazaplaty:datazaplatycancelbutton").hide();
    } else {
        r("formdatazaplaty:datazaplatybutton").hide();
        r("formdatazaplaty:datazaplatycancelbutton").show();
    }
};

 var weryfikujdatazaplaty = function (dataWyst) {
       var d1 = new Date(dataWyst);
       var dataZaplaty = document.getElementById("formdatazaplaty:datazaplaty");
       var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
       var testw = dataZaplaty.value;
       var d2 = new Date(testw);
       console.log(d1 > d2);
         if (!testw.match(re) || (d1 > d2)){
             dataZaplaty.value = "b\u0142ędna data";
             r('formdatazaplaty:datazaplaty').focus();
             r("formdatazaplaty:datazaplatybutton").hide();
             r("formdatazaplaty:datazaplatycancelbutton").show();
         }
   };
   
   var fakturaduplikatklienta = function () {
       rj("formnkfaktura:nazwaPole").value = "";
       rj("formnkfaktura:symbolPole").value = "";
       r("formnkfaktura:nazwaPole").focus();
       r("formnkfaktura:nazwaPole").select();
   };
   
   var fakturaduplikatklientakontrahent = function () {
       rj("formX:symbolPole").value = "";
       rj("formX:nazwaPole").value = "taki kontrahent już istnieje";
       document.activeElement = rj("formX:nazwaPole");
       r("formX:nazwaPole").focus();
    };