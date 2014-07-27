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
     } else {
         var dataplatnosc = document.getElementById("dodWiad:tabelapkpir2:0:dataTPole");
         var datasprzedazy = document.getElementById("dodWiad:dataSPole");
         var rozliczony = document.getElementById("dodWiad:tabelapkpir2:0:rozliczony");
         dataplatnosc.value = dataWyst.value;
         datasprzedazy.value = dataWyst.value;
         $(rozliczony).attr('checked', true);
     }
   };
   
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

function ustawDateFK(rok,mc){
    var dataWyst = document.getElementById("formwpisdokument:dataDialogWpisywanie");
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
   };
