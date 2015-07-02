/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var focusNaStronieCechaWiersza = function() {
    var wnlubma = MYAPP.wnlubma;
    var wierszid = MYAPP.lpwiersza-1;
    if (wnlubma === "Wn") {
        r("formwpisdokument:dataList:"+wierszid+":kontown_input").select();
    } else {
        r("formwpisdokument:dataList:"+wierszid+":kontoma_input").select();
    }
};
