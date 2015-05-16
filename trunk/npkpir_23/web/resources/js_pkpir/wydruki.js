"use strict";

var wydrukpkpir = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("form:pkpirwysylka").style.display='inline';
};

var wydrukinwestycja = function(kto){
    window.open('../wydruki/inwestycja'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    //document.getElementById("form:pkpirwysylka").style.display='inline';
};

var wydrukzbiorcze = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("akordeon:form:pkpirwysylka").style.display='inline';
};

var wydrukstr = function(kto){
    window.open('../wydruki/srodki'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formSTR:ewwysylka").style.display='inline';
    
};
//dlatego jest try bo wykorzystywana jest w dwoch miejscach vatwyslane i vat korekta gdzie nie ma button mail
var wydrukvat7 = function(kto, index){
    window.open('../wydruki/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    try {
        document.getElementById("formX:akordeon:dataList:"+index+":mailbutton").style.display='inline';
    } catch (ex) {}
};

var wydrukvat7wysylka = function(kto){
    window.open('../wydruki/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:dokumentyLista:0:mailbutton").style.display='inline';
};

var wydrukvatue = function(kto){
    window.open('../wydruki/VATUE'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var schowajmailbutton = function (index) {
    if (index === -1) {
        $(document.getElementById("formX:dokumentyLista:0:mailbutton")).attr('display','none');
    } else {
        $(document.getElementById("formX:akordeon:dataList:"+index+":mailbutton")).attr('display','none');
    }
 };


var wydrukobroty = function(kto){
    window.open('../wydruki/obroty'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:obrotywysylka").style.display='inline';
};

var wydruksumavat = function(kto){
    window.open('../wydruki/vatsuma'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukpk = function(kto){
    window.open('../wydruki/pk'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukfaktura = function(kto){
    window.open('../wydruki/fakturaNr'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukpit5 = function(kto){
    window.open('../wydruki/pit5'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukewidencje = function(kto,nazwa, target){
    if (typeof target !== "undefined") {
        var sib = r(target.source).siblings("button");
        $(sib[1]).show();
    }
    if(!nazwa.indexOf("sprzedaż", 0)){
    var nazwanowa = nazwa.substr(0, nazwa.length-1);
    } else {
        nazwanowa = nazwa;
    }
    window.open('../wydruki/vat-'+nazwanowa+'-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukewidencjewszystkie = function(kto){
    window.open('../wydruki/vat-wszystko-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukewidencjewszystkiewartosc = function(kto){
    window.open('../wydruki/vat-wszystko-wartosc-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};


var wydrukvatListaVATKorekta = function(kto){
    window.open('../wydruki/dokumentyVATKorektaReczna'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukfakturysporzadzone = function(kto){
    window.open('../wydruki/fakturysporzadzone-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukmiejscakosztow = function(kto){
    window.open('../wydruki/miejscakosztow-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukpojazdy = function(kto){
    window.open('../wydruki/pojazdy-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydruksymulacjawyniku = function(kto){
    window.open('../wydruki/symulacjawyniku-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydruksymulacjawynikunar = function(kto){
    window.open('../wydruki/symulacjawynikunar-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};


var wydrukkonta = function (kto) {
    window.open('../wydruki/konta-' + kto + '.pdf', '', 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukRZiS = function(kto){
    window.open('../wydruki/'+kto+'RZiSobliczenie.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukBilansuA = function(kto){
    window.open('../wydruki/'+kto+'BilansobliczenieA.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukBilansuP = function(kto){
    window.open('../wydruki/'+kto+'BilansobliczenieP.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};