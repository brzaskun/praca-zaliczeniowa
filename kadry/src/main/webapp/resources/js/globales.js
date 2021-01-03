/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var con = function() {
    $("body").css("cursor", "wait");
};

var coff = function() {
    $("body").css("cursor", "default");
};

var pokazwydruk = function(ktoco){
    setTimeout(window.open('resources/wydruki/'+ktoco,'','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50'),10000);
};
