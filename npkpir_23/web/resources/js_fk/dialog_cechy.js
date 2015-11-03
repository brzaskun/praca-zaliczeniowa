"use strict";

var focusNaStronieCechaWiersza = function() {
    var wnlubma = MYAPP.wnlubma;
    var wierszid = MYAPP.lpwiersza-1;
    if (wnlubma === "Wn") {
        r("formwpisdokument:dataList:"+wierszid+":kontown_input").select();
    } else {
        r("formwpisdokument:dataList:"+wierszid+":kontoma_input").select();
    }
};
