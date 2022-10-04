/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var con = function () {
    $("body").css("cursor", "wait");
};

var coff = function () {
    $("body").css("cursor", "default");
};

var concoff = function(ile) {
    $("body").css("cursor", "wait");
    setTimeout(function(){ coff(); }, ile);
};

