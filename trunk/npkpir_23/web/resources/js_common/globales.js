"use strict";

var MYAPP = {};

var zrobFloat = function (kwota){
    try {
        var strX = kwota.replace(",",".");
        strX = strX.replace(/\s/g, "");
        return parseFloat(strX);
    } catch (e) {
        return 0.0;
    }
 };
 
 var r = function (pole) {
     return $(document.getElementById(pole));
 };
 
 var rj = function (pole) {
     return document.getElementById(pole);
 };
 
 var usunspacje = function (polezespacja) {
    try {
        if (polezespacja.value === " ") {
            polezespacja.value = "";
        }
    } catch (e) {
        alert("Blad w globales.js usunspacje");
    }
};

// 
//var t;
//var startTimer = function (){
//   t = setTimeout("PF('dialogAjaxCzekaj').show()", 1100);
//};
//var stopTimer = function (){
//   clearTimeout(t);
//   PF('dialogAjaxCzekaj').hide();
//};
 