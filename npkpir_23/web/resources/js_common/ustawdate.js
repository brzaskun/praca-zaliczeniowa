"use strict";

function ustawDate(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataPole");
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2018-02-29") {
        dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2019-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2021-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2022-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2023-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2025-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else {
         var dataplatnosc = document.getElementById("dodWiad:dataTPole");
         var datasprzedazy = document.getElementById("dodWiad:dataSPole");
         var terminplatnosci = document.getElementById("dodWiad:dataTrPole");
         var rozliczony = document.getElementById("dodWiad:rozliczony");
         dataplatnosc.value = dataWyst.value;
         datasprzedazy.value = dataWyst.value;
         if (terminplatnosci === null) {
            $(rozliczony).attr('checked', true);
        }
     }
   };
   
   function ustawDateEdycja(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataPole");
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2018-02-29") {
        dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2019-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2021-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2022-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2023-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else if (testw==="2025-02-29") {
         dataWyst.value = "b\u0142ędna data";
     } else {
         var terminplatnosci = document.getElementById("dodWiad:dataTrPole");
         var rozliczony = document.getElementById("dodWiad:rozliczony");
         if (terminplatnosci === null) {
            $(rozliczony).attr('checked', true);
        }
     }
   };
   
  function ustawDaterozbicie(rok, mc, pole) {
    var wart = $(pole).val();
    if (mc !== 10 && mc !== 11 && mc !== 12) {
        mc = "0" + mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
    if (wart.match(re3)) {
        $(pole).val(wart);
    } else if (wart.match(re2)) {
        $(pole).val(rok + "-" + wart);
    } else if (wart.match(re1)) {
        $(pole).val(rok + "-" + mc + "-" + wart);
    }
    var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
    var testw = $(pole).val();
    if (!testw.match(re)) {
        $(pole).val("b\u0142ędna data");
    } 
}
;
   
function ustawDateSprzedazy(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataSPole");
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
   };
   
   function ustawDateGen(rok,mc,pole){
    var dataWyst = document.getElementById(pole);
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
   };
   
   function ustawDateGen2(rok,mc,pole){
    var dataWyst = r('akordeon:form_dokument_wpis_platnosci1:poledataplatnosci').find("input")[0];
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
   };

function ustawDateFK(rok,mc, koncowkaadresu, zapisz0edytuj1){
    var coswpisanowpoledaty;
    var adres = "formwpisdokument:"+koncowkaadresu;
    var dataWyst = document.getElementById(adres);
    if (dataWyst.value !== "") {
        coswpisanowpoledaty = true;
    }
    if (coswpisanowpoledaty) {
        try {
            var adres = "formwpisdokument:"+koncowkaadresu;
            var dataWyst = document.getElementById(adres);
            var wart = dataWyst.value;
            if(mc!==10&&mc!==11&&mc!==12){
                mc = "0"+mc;
            }
            //dzieki temu akceptuje wpis 0214
            if (wart.length === 4) {
                var nowa = wart.substring(0, 2)+"-"+wart.substring(2, 4);
                wart = nowa;
            }
            if (wart.length === 8) {
                var nowa = wart.substring(0, 4)+"-"+wart.substring(4, 6)+"-"+wart.substring(6, 8);
                wart = nowa;
            }
            var re1 = /[0-3][0-9]/;
            var re2 = /[0-1][0-9]\S[0-3][0-9]/;
            var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
             if (wart.match(re3)) {
                    dataWyst.value = wart ;
                } else if (wart.match(re2)){
                    dataWyst.value = rok + "-"+wart;
                } else if (wart.match(re1)){
                    dataWyst.value = rok + "-" + mc + "-" + wart ;
                }
             var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
             var testw = dataWyst.value;
              if (!testw.match(re)){
                    dataWyst.value = "b\u0142ędna data";
                } else if (testw==="2018-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                } else if (testw==="2019-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                } else if (testw==="2021-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                } else if (testw==="2022-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                } else if (testw==="2023-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                }  else if (testw==="2025-02-29") {
                    dataWyst.value = "b\u0142ędna data";
                }  else {
        //         var dataplatnosc = document.getElementById("formwpisdokument:dataTPole");
        //         var datasprzedazy = document.getElementById("formwpisdokument:dataSPole");
        //         var rozliczony = document.getElementById("formwpisdokument:rozliczony");
        //         dataplatnosc.value = dataWyst.value;
        //         datasprzedazy.value = dataWyst.value;
        //         $(rozliczony).attr('checked', true);
             }
             if (zapisz0edytuj1 === false && coswpisanowpoledaty) {
                if (koncowkaadresu === "data1DialogWpisywanie") {
                   var adres = "formwpisdokument:data2DialogWpisywanie";
                   var dataWyst1 = document.getElementById(adres);
                   dataWyst1.value = dataWyst.value;
                   adres = "formwpisdokument:data3DialogWpisywanie";
                   dataWyst1 = document.getElementById(adres);
                   dataWyst1.value = dataWyst.value;
                   adres = "formwpisdokument:data4DialogWpisywanie";
                   dataWyst1 = document.getElementById(adres);
                   dataWyst1.value = dataWyst.value;
                }
                 if (koncowkaadresu === "data2DialogWpisywanie") {
                   var adres = "formwpisdokument:data3DialogWpisywanie";
                   var dataWyst1 = document.getElementById(adres);
                   dataWyst1.value = dataWyst.value;
                }
            }
           //zerujwiadomosc();
         } catch (e) {
             alert("Blad ustawdate.js ustawDateFK(rok,mc) "+e.toString());
         }
     }
   };
   
   var weryfikujdateauto = function (el) {
       var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
       var testw = document.getElementById(el).value;
        if (!testw.match(re)){
            document.getElementById(el).value = "b\u0142ędna data";
        }
   };
   
   
   var weryfikujdatekursreczny = function (rokwpisu) {
       var dataWyst = document.getElementById("formkursrecznie:dataKursReczny:0:datakurs");
       var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
       var testw = dataWyst.value;
        if (!testw.match(re)){
            dataWyst.value = "b\u0142ędna data";
            r("formkursrecznie:dataKursReczny:0:datakurs").select();
            r("formkursrecznie:dataKursReczny:0:zapiswalutarecznie").hide();
        } else if (testw.split("-")[0] != rokwpisu) {
            //nie moze byc !== bo nie rzutuje wtedy a to jest string!=integer
            dataWyst.value = "b\u0142ędny rok";
            r("formkursrecznie:dataKursReczny:0:datakurs").select();
            r("formkursrecznie:dataKursReczny:0:zapiswalutarecznie").hide();
        } else {
            r("formkursrecznie:dataKursReczny:0:zapiswalutarecznie").show();
        }
   };
   
   var weryfikujsporzadzfakture = function (poleDaty) {
       var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
       var testw = poleDaty.value;
         if (!testw.match(re)){
             poleDaty.value = null;
         }
   };
   
   var weryfikujnumertabeli = function () {
       try {
        var nrTabeli = document.getElementById("formkursrecznie:dataKursReczny:0:numertabeli");
        var re = /\d{3}[\/]\w{1}[\/]\w{3}[\/]\d{4}/;
        var testw = nrTabeli.value;
          if (!testw.match(re)){
              nrTabeli.value = "b\u0142ędny numer tabeli";
          }
          if (testw.indexOf("NBP")>-1) {
              nrTabeli.value = "b\u0142ędny numer tabeli";
          }
      } catch (e) {
          
      }
   };
   
   function ustawDateFKRK(rok,mc, koncowkaadresu){
    try {
        var adres = "ewidencjavatRK:"+koncowkaadresu;
        var dataWyst = document.getElementById(adres);
        var wart = dataWyst.value;
        if(mc!==10&&mc!==11&&mc!==12){
            mc = "0"+mc;
        }
        var re1 = /[0-3][0-9]/;
        var re2 = /[0-1][0-9]\S[0-3][0-9]/;
        var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
         if (wart.match(re3)) {
                dataWyst.value = wart ;
            } else if (wart.match(re2)){
                dataWyst.value = rok + "-"+wart;
            } else if (wart.match(re1)){
                dataWyst.value = rok + "-" + mc + "-" + wart ;
            }
         var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
         var testw = dataWyst.value;
         if (!testw.match(re)){
             dataWyst.value = "b\u0142ędna data";
         } else {
    //         var dataplatnosc = document.getElementById("formwpisdokument:dataTPole");
    //         var datasprzedazy = document.getElementById("formwpisdokument:dataSPole");
    //         var rozliczony = document.getElementById("formwpisdokument:rozliczony");
    //         dataplatnosc.value = dataWyst.value;
    //         datasprzedazy.value = dataWyst.value;
    //         $(rozliczony).attr('checked', true);
         }
         if (koncowkaadresu === "data1DialogVAT") {
            var adres = "ewidencjavatRK:data2DialogVAT";
            var dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
         }
     } catch (e) {
         alert("Blad ustawdate.js ustawDateFKRK(rok,mc) "+e.toString());
     }
   };
