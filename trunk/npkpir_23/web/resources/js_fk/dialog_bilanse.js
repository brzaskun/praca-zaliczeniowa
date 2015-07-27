/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var selectpustepolebo = function(nrlisty,nrwiersza)  {
    try {
        nrwiersza = parseInt(nrwiersza)-1;
        var nazwa = "formbilanswprowadzanie:tabviewbilans:tab"+nrlisty+":"+nrwiersza+":konto_input";
        if (rj(nazwa).value === " " || rj(nazwa).value === "") {
            rj(nazwa).focus();
            rj(nazwa).select();
        }
    } catch (e) {
        
    }
};


