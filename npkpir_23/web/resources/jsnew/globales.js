"use strict";
//$(document).keypress(function(e) {
//    if (e.which === 32) {
//        console.log("wdwdw");
//   }
//});
//
//var zlap = function (){
//    console.log("wdwdw");
//};

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
 

var con = function() {
    $("body").css("cursor", "wait");
};

var coff = function() {
    $("body").css("cursor", "default");
};


$(document).on("ajaxStart pfAjaxSend", function() {
    $("html").addClass("progress");
}).on("ajaxStop pfAjaxComplete", function() {
    $("html").removeClass("progress");
});

