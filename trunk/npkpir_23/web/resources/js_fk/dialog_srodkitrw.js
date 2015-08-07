/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var dataprzyjecia = function() {
};

var ustawDateSrodekTrw = function() {
    var dataWyst = rj("formdialogsrodki:datazak");
    var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
    var testw = dataWyst.value;
    if (testw !== "") {
        if (!testw.match(re)) {
            dataWyst.value = "b\u0142ędna data";
        }
    }
    dataWyst = rj("formdialogsrodki:dataprz");
    re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
    testw = dataWyst.value;
    if (testw !== "") {
        if (!testw.match(re)) {
            dataWyst.value = "b\u0142ędna data";
        }
    }
};

