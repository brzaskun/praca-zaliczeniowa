var przejdzwiersz = function () {
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
  $(komorki[1]).click();
};


