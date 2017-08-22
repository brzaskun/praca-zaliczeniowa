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
 
 
 var podswietlmenu = function(menu) { 
     $(menu).css('background','#91D5FF'); 
     $(menu).siblings().css('background','white');
 };
 
 
 

 
 Array.prototype.remove = function(elem, all) {
  for (var i=this.length-1; i>=0; i--) {
    if (this[i] === elem) {
        this.splice(i, 1);
        if(!all)
          break;
    }
  }
  return this;
};


Array.prototype.includes = function(searchElement /*, fromIndex*/ ) {
    var O = Object(this);
    var len = parseInt(O.length) || 0;
    if (len === 0) {
      return false;
    }
    var n = parseInt(arguments[1]) || 0;
    var k;
    if (n >= 0) {
      k = n;
    } else {
      k = len + n;
      if (k < 0) {k = 0;}
    }
    var currentElement;
    while (k < len) {
      currentElement = O[k];
      if (searchElement === currentElement ||
         (searchElement !== searchElement && currentElement !== currentElement)) {
        return true;
      }
      k++;
    }
    return false;
  };
  
  Array.prototype.includes_M = function(searchElement, col) {
    var O = Object(this);
    var len = parseInt(O.length) || 0;
    if (len === 0) {
      return false;
    }
    var k = 0;
    var currentElement;
    var uzytkownicy = [];
    while (k < len) {
      currentElement = (O[k])[col];
      if (searchElement === currentElement ||
         (searchElement !== searchElement && currentElement !== currentElement)) {
        uzytkownicy.push(O[k]);
      }
      k++;
    }
    return uzytkownicy;
  };
  
