"use strict";

var MYAPP = {};

Number.prototype.round = function(places) {
  return +(Math.round(this + "e+" + places)  + "e-" + places);
};

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


var zweryfikujczyniebrakujesrodektrw = function(tablica) {
  var nazwa = r(tablica+":nazwasrodka").val();
  var datazak = r(tablica+":datazak").val();
  var dataprz = r(tablica+":dataprz").val();
  var numerfaktzakupu = r(tablica+":numerfaktzakupu").val();
  var cenazakupu_input = parseInt(r(tablica+":cenazakupu_hinput").val());
  if (nazwa !== "" && datazak !== "" && dataprz !== "" && numerfaktzakupu !== "" && cenazakupu_input !== "") {
      r(tablica+":dodajsrodekbutton").show();
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
 