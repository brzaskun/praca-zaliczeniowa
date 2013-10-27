var pilnujwprowadzanychrozrachunkow = function () {
    var limit = zrobFloat($(document.getElementById('rozrachunki:formE:dorozliczenia')).text());
    MYAPP.limit = limit;
    doklejsumowaniewprowadzonych();
};

var drugionShow = function () {
    ustawdialog('dialogdrugi','menudokumenty');
    pilnujwprowadzanychrozrachunkow();
};

//wykonuje czynnosci podczas zamykania dialogu z rozrachunkami
var rozrachunkionHide = function () {
  resetujdialog('dialogdrugi');
  $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
  $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
  $(MYAPP.zaznaczonepole).focus();
  $(MYAPP.zaznaczonepole).select();
};

var doklejsumowaniewprowadzonych = function () {
     $("#rozrachunki\\:dataList :input").keyup(function () {
         $(this).css("background-color","#AFEEEE");
         var wszystkiewiersze = $("#rozrachunki\\:dataList").find(":input");
         var iloscpozycji = wszystkiewiersze.length;
         var wprowadzono = 0;
         for (var i = 0 ; i < iloscpozycji; i = i + 2) {
         wprowadzono += zrobFloat($(wszystkiewiersze[i]).val());
         if (wprowadzono > MYAPP.limit) {
             $(wszystkiewiersze[i]).css("background-color","red");
         }
         }
     });
};



