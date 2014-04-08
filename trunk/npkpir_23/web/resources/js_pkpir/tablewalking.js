"use strict";

var zachowajnumerwiersza = function (numer) {
        MYAPP.nrbiezacegowiersza = numer;
};

var przejdzwiersz = function () {
  var ev = event.target;
  var lolo = $("#form\\:dokumentyLista_data").children("tr");
   if(!MYAPP.hasOwnProperty('nrbiezacegowiersza')){
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza += 1;
        if (MYAPP.nrbiezacegowiersza > lolo.length) {
            MYAPP.nrbiezacegowiersza = lolo.length;
        }
    }
  var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
  var czynaekranie = isScrolledIntoView(komorki[1]);
  if (!czynaekranie) {
    var wysokosc = 70;
    var elem = document.getElementById('form:dokumentyLista');
    elem.scrollTop = elem.scrollTop + wysokosc;
  }
  $(komorki[1]).click();
};

var wrocwiersz = function () {
  var lolo = $("#form\\:dokumentyLista_data").children("tr");
   if(!MYAPP.hasOwnProperty('nrbiezacegowiersza')){
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza -= 1;
        if (MYAPP.nrbiezacegowiersza < 0) {
            MYAPP.nrbiezacegowiersza = 0;
        }
    }
  var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
  var czynaekranie = isScrolledIntoView(komorki[1]);
  if (!czynaekranie) {
    var wysokosc = 70;
    var elem = document.getElementById('form:dokumentyLista');
    elem.scrollTop = elem.scrollTop - wysokosc;
  }
  $(komorki[1]).click();
};

function isScrolledIntoView(elem) {
    try {
       var docViewTop = $(window).scrollTop()+150;
       var docViewBottom = docViewTop + $(window).height()-300;
       var elemTop = $(elem).offset().top;
       var elemBottom = elemTop + $(elem).height();
       return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom)
            && (elemBottom <= docViewBottom) &&  (elemTop >= docViewTop) );
    } catch (e) {
    }
    return true;
};


