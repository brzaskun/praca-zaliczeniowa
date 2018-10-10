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
            return window.open("http://localhost:8080/npkpir_23/","myWindow","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, minimizable=yes, alwaysLowered=yes, resizable=no, dialog=yes, copyhistory=no, width=1, height=1");
        },
        _is_popup_blocked: function(popup_window){
            if ((popup_window.innerHeight > 0)===false){
                popupBlockerChecker._displayError();
            } else {
                popup_window.close();
            }
        },
        check: function(){
            var win = popupBlockerChecker.popup_window();
            if (win) {
                if(/chrome/.test(navigator.userAgent.toLowerCase())){
                    setTimeout(function () {
                        popupBlockerChecker._is_popup_blocked(win);
                     },200);
                }else{
                    popup_window.onload = function () {
                        popupBlockerChecker._is_popup_blocked(win);
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

var znajdzdivshown = function() {
    MYAPP.otwartedialogi = new Array();
    $(".ui-dialog").each(function() {
        if ($(this).attr("aria-hidden") === "false") {
            let did = $(this).attr("id")
            let wvar = getWidgetVarById(did);
            MYAPP.otwartedialogi.push(wvar);
            PF(wvar).hide();
            };
    });
    console.log('');
};

var getWidgetVarById = function (id) {
   for (var propertyName in PrimeFaces.widgets) {
     if (PrimeFaces.widgets[propertyName].id === id) {
       return PrimeFaces.widgets[propertyName].widgetVar;
     }
   }
};

var odtworzdivshown = function() {
    if (typeof MYAPP.otwartedialogi !== 'undefined') {
        MYAPP.otwartedialogi.forEach(function(item, index) {
            try {
                PF(item).show();
            } catch(e){}
        });
    }
};

var con = function() {
    $("body").css("cursor", "wait");
};

var coff = function() {
    $("body").css("cursor", "default");
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

var wydrukJPK = function(nazwa){
    window.open('../resources/xml/'+nazwa,'','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukXML = function(nazwa){
    window.open('../resources/xml/'+nazwa,'','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var pokazwydruk = function(ktoco){
    window.open('../wydruki/'+ktoco+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var pokazwydrukpdf = function(ktoco){
    window.open('../wydruki/'+ktoco,'','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var startajaxm = function() {
  PF('ajaxm').renderMessage({summary:'Trwa szukanie', detail: 'proszę czekać', severity: 'info'});  
};

var stopajaxm = function() {
  PF('ajaxm').renderMessage({summary:'Zakończono szukanie', detail: 'można przeglądać', severity: 'info'});  
};

$(document).on("ajaxStart pfAjaxSend", function() {
    $("html").addClass("progress");
}).on("ajaxStop pfAjaxComplete", function() {
    $("html").removeClass("progress");
});

