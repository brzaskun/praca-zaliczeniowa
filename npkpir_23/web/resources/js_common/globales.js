var MYAPP = {};

 function zrobFloat(kwota){
      var strX = kwota.replace(",",".");
      strX = strX.replace(/\s/g, "");
      return parseFloat(strX);
 }

