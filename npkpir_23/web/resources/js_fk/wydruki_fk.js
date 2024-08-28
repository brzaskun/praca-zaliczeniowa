"use strict";

var wydrukidedra = function(co){
    var nazwapliku = '../resources/uploaded/deklaracjevat/'+co;
    $.get(nazwapliku)
    .done(function() { 
        // exists code 
        window.open(nazwapliku,'','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    }).fail(function() { 
        // not exists code
        alert("Nie ma takiego pliku z deklaracją");
    });
};

var wydrukpkpir = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("form:pkpirwysylka").style.display='inline';
};
var wydrukCechyzapisu = function(kto){
    window.open('../wydruki/'+kto+'dokumentcechyzapisu.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukinwestycja = function(kto){
    window.open('../wydruki/inwestycja'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    //document.getElementById("form:pkpirwysylka").style.display='inline';
};
var wydrukzapisynakoncie = function(kto){
    window.open('../wydruki/zapiskonto-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukzbiorcze = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("akordeon:formwysylka:zestawieniewysylka").style.display='inline';
};
var wydrukWNTWDT = function(kto){
    setTimeout(
    window.open('../wydruki/'+kto+'dokumentwntwdt.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50')
    , 4000);
};

var wydrukstr = function(kto){
    try {
        window.open('../wydruki/srodki'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
        document.getElementById("formSTR:ewwysylka").style.display='inline';
    } catch (e) {}
    
};

var wydrukvat7wysylkaN = function(kto){
    try {
        document.getElementById("formX:dokumentyLista:0:mailbutton").style.display='inline';
    } catch (e){
    }
};
//dlatego jest try bo wykorzystywana jest w dwoch miejscach vatwyslane i vat korekta gdzie nie ma button mail
var wydrukvat7 = function(kto, index){
    window.open('../wydruki/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    try {
        document.getElementById("formX:akordeon:dataList:"+index+":mailbutton").style.display='inline';
    } catch (ex) {}
};



var wydrukvatue = function(nazwa){
    window.open('../wydruki/'+nazwa+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var schowajmailbutton = function (index) {
    if (index === -1) {
        $(document.getElementById("formX:dokumentyLista:0:mailbutton")).attr('display','none');
        $(document.getElementById("formX:dokumentyLista:0:mailbuttonN")).attr('display','none');
    } else {
        $(document.getElementById("formX:akordeon:dataList:"+index+":mailbutton")).attr('display','none');
        $(document.getElementById("formX:akordeon:dataList:"+index+":mailbuttonN")).attr('display','none');
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

var wydrukmiejscakosztow = function(nip, nr){
    window.open('../wydruki/miejscakosztow'+nr+nip+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukmiejscaprzychodow = function(kto, nr){
    window.open('../wydruki/miejscaprzychodow'+nr+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukpojazdy = function(kto, nr){
    window.open('../wydruki/pojazdy'+nr+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydruksymulacjawyniku = function(kto, numer){
    if (numer===1) {
        window.open('../wydruki/symulacjawyniku'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    } else {
        window.open('../wydruki/symulacjawynikukonta'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    }
};

var wydruksymulacjawynikunar = function(kto){
    window.open('../wydruki/symulacjawynikunar-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};


var wydrukkonta = function (kto) {
    window.open('../wydruki/konta-' + kto + '.pdf', '', 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukRZiS = function(ktoco){
    window.open('../wydruki/'+ktoco+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukBilansuA = function(kto){
    window.open('../wydruki/'+kto+'BilansobliczenieA.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukBilansuP = function(kto){
    window.open('../wydruki/'+kto+'BilansobliczenieP.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};


var wydrukzaksiegowane = function(kto){
    window.open('../wydruki/'+kto+'dokument.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukZaksiegowaneLista = function(kto){
    window.open('../wydruki/'+kto+'dokumentzaksiegowane.pdf?faces-redirect=true','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
