"use strict";
var walutypokaz = function () {
    $(document.getElementById('wpiswalut:dataTablewpisywaniewalut')).show();
    $(document.getElementById('wpiswalut:dataTablewpisywaniewalut:0:symbol')).select();
    $(document.getElementById('wpiswalut:walutydodajbutton')).show();
    $(document.getElementById('wpiswalut:walutynowabutton')).hide();
};

var walutyschowaj = function () {
    $(document.getElementById('wpiswalut:dataTablewpisywaniewalut')).hide();
    $(document.getElementById('wpiswalut:walutydodajbutton')).hide();
    $(document.getElementById('wpiswalut:walutynowabutton')).show();
};

