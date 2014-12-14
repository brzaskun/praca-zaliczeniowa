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
 
 var usunspacje = function(polezespacja) {
    if (polezespacja.value === " ") {
        polezespacja.value = "";  
    }
};

 
var t;
var startTimer = function (){
   t = setTimeout("PF('dialogAjaxCzekaj').show()", 1100);
};
var stopTimer = function (){
   clearTimeout(t);
   PF('dialogAjaxCzekaj').hide();
};
 