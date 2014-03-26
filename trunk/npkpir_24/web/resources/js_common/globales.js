"use strict";

var MYAPP = {};

var zrobFloat = function (kwota){
      var strX = kwota.replace(",",".");
      strX = strX.replace(/\s/g, "");
      return parseFloat(strX);
 };
 
 var r = function (pole) {
     return $(document.getElementById(pole));
 };
 
 var rj = function (pole) {
     return document.getElementById(pole);
 };
 
