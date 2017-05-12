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
 /**
 * Oblicza numer wiersza w tabeli
 * @param {Object} obiekt
 * @param {String} nazwa obiektu
 * @return {Number} numer wiersza
 */
 var lp = function (objekt) {
    var zwrot = null;
    if (typeof objekt === "object") {
        var nazwapola = objekt.id;
        zwrot = wydlub(nazwapola);
    }
    if (typeof objekt === "string") {
        zwrot = wydlub(objekt);
    }
    if (isNaN(zwrot)) {
        zwrot = null;
    }
    return zwrot;
 };
 
 var wydlub = function (wyrazenie) {
    var zwrot = null;
    try {
        zwrot = wyrazenie.match(/\d+/)[0];
    } catch (e) {
    }
    return zwrot;
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

var kopiujtrescpola = function(zrodlo, cel) {
  var o1 = r(zrodlo).val();
  if (o1) {
      r(cel).val(o1);
      r(cel).select();
  }
};

var popupBlockerChecker = {
        popup_window: function() {
            return window.open("http://google.com","myWindow","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=100, height=100");
        },
        _is_popup_blocked: function(popup_window){
            if ((popup_window.innerHeight > 0)===false){_displayError(); }
        },
        check: function(popup_window){
            if (popup_window) {
                if(/chrome/.test(navigator.userAgent.toLowerCase())){
                    setTimeout(function () {
                        this._is_popup_blocked(popup_window);
                     },200);
                }else{
                    popup_window.onload = function () {
                        this._is_popup_blocked(popup_window);
                    };
                }
            }else{
                _displayError();
            }
        },
        _displayError: function(){
            alert("Popup Blocker is enabled! Please add this site to your exception list.");
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
 