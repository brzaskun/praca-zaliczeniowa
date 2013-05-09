function al(){
    alert("document.getElementById(dodWiad:rodzajTrans).focus();");
}


function orientujsie(){
    document.getElementById("dodWiad:rodzajTrans").focus();
}

function openwindow(){
    alert("test");
     my_window = window.open("", "mywindow1", "status=1,width=350,height=150");
    my_window.document.write('<h1>Popup Test!</h1>');
}

//function stopRKey(evt) { 
//  var evt = (evt) ? evt : ((event) ? event : null); 
//  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
//  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
//} 
//document.onkeypress = stopRKey; 



$(document).ready(function() {
    $(':input').focus(
    function(){
        $(this).css({'background-color' : '#CEE4F0'});
    });

    $(':input').blur(
    function(){
        $(this).css({'background-color' : '#DFD8D1'});
    });
});


//nie wiem po co to
//$(window).bind('beforeunload', function(){
//    document.getElementById("ft:wt").click();
//    alert("Good Bye");
//});

 function check_form(param){
     if(document.getElementById(param).value){
            var date_array = document.getElementById(param).value.split('-');
            var day = date_array[2];
            // Attention! Javascript consider months in the range 0 - 11
            var month = date_array[1]-1;
            var year = date_array[0];
            // This instruction will create a date object
            source_date = new Date(year,month,day);
            if(year != source_date.getFullYear()){
               alert('Nieprawid\u0142owa data - sprawdź! ');
               document.getElementById(param).focus();
               return false;
            }
            if(month != source_date.getMonth()){
               alert('Nieprawid\u0142owa data - sprawdź!');
               document.getElementById(param).focus();
               return false;
            }
      return true;
}};

 function validate(){
        txt = parseInt(document.getElementById("dodWiad:dataPole").value.length,10);
        if (txt>1&&txt<10) {
            alert("Niepe\u0142na data. Wymagany format RRRR-MM-DD");
            document.getElementById("dodWiad:dataPole").focus();
            return false;
        } else {
            check_form("dodWiad:dataPole");
        }};
 
 function validateK(){
        document.getElementById("dodWiad:acForce_hinput").focus();
        txt = parseInt(document.getElementById("dodWiad:acForce_hinput").value.length,10);
        tekst = document.getElementById("dodWiad:acForce_input").value;
        if (txt<3) {
            document.getElementById("dodWiad:acForce_input").focus();
            return false
        }else{
            if(tekst == ""){
                window.location.href = "klienci.xhtml";
//                window.open('klienci.xhtml?redirect=true', 'popup', 'location=yes,links=no,scrollbars=no,toolbar=no,status=no,width=1200,height=300,top=150,left=300'); 
            }
            return true;
        }};
 
 function validateOpis(){
        document.getElementById("dodWiad:opis_hinput").focus();
        txt = parseInt(document.getElementById("dodWiad:opis_hinput").value.length,10);
        if (txt<3) {
            alert("Brak opisu!");
            document.getElementById("dodWiad:opis_input").focus();
            return false
        }else{
            return true;
        }};
 
 function validateTermin(){
            txt = parseInt(document.getElementById("dodWiad:dataTPole").value.length,10);
        if (txt>=0&&txt<10) {
            alert("Niepe\u0142na data. Wymagany format RRRR-MM-DD");
            document.getElementById("dodWiad:dataTPole").focus();
            return false
        }else{
            check_form("dodWiad:dataTPole");
        }};
    
    function wyloguj(){
        document.getElementById("templateform:wyloguj").click();
        if (document.getElementById("form:westIndex:panelwyboru")){
            window.location.href = "login.xhtml";
        } else {
            window.location.href = "../login.xhtml";
        }
    };

function aktywujsrodek(){
        document.getElementById("dodWiad:form:acForce1").focus();
};

function aktywujopis(){
    var dokument = $('#dodWiad\\:rodzajTrans').val();
    if(dokument=='IN'){
        $('#dodWiad\\:inwestycja').show();
        $('#dodWiad\\:inwestycjas').show();
//        $("#dodWiad\\:inwestycja").bind('mouseover', function() {
//        alert($('#dodWiad\\:inwestycja').val());
//            });
    $("#dodWiad\\:inwestycja").bind('blur', function() {

    if( $('#dodWiad\\:inwestycja').val()=="wybierz"){
        $('#dodWiad\\:inwestycja').focus();
    }
    });
    } else {
        $('#dodWiad\\:inwestycja').hide();
        $('#dodWiad\\:inwestycjas').hide();
    }
    $('#dodWiad\\:opis').on('keydown',function(e){
        if(e.which=='120'){
            $('#dodWiad\\:dodajopis').click();
            $('#dodWiad\\:opis').focus();
        }
    }); 
        $('#dodWiad\\:numerwlasny').focus();
        
};

$(function(){
    $("#dodWiad\\:inwestycja").bind('blur', function() {

    if( $('#dodWiad\\:inwestycja').val()=="wybierz"){
        $('#dodWiad\\:inwestycja').focus();
    }
    });
    
    $('#dodWiad\\:opis').on('keydown',function(e){
        $('#log').html(e.type + ': ' +  e.which );
        if(e.which=='120'){
            $('#dodWiad\\:dodajopis').click();
        }
    });
     $('#dodWiad\\:dodkol').on('keyup',function(e){
        if(e.which=='119'){
            $('#dodWiad\\:dodkol').click();
        }
    }); 
});

function pokazdodawanie(){
    document.getElementById("formX:dkp").style.display = 'inline';
}

function pokazdodawanieanal(){
    document.getElementById("formY:dkp").style.display = 'inline';
}

function schowajdodawanie(){
    document.getElementById("formX:dkp").style.display = 'none';
    document.getElementById("formY:dkp").style.display = 'none';
}

function aktywujwiersz(wiersz){
    var i = "#form\\:dataList\\:"+wiersz+"\\:opis";
    if($(i)!=null){
    $(i).focus();
    $(i).css('backgroundColor','#ffe');
    }
};

function zablokujma(wiersz){
    var w = wiersz-1;
    var blokowany = "#form\\:dataList\\:"+w+"\\:ma_input";
    var sprawdzany = "#form\\:dataList\\:"+w+"\\:wn_input";
    var cozawiera = $(sprawdzany).val().length;
    if(cozawiera>0){
        $(blokowany).fadeOut();
        var kontopole = '[id="form:dataList:'+w+':konto_input"]';
        $(kontopole).focus();
    } else {
        $(blokowany).show();
        var kontopole = "\\form\\:dataList\\:"+w+"\\:ma_input";
        $(kontopole).focus();
    }
}

function zablokujwn(wiersz){
    var w = wiersz-1;
    var blokowany = "form:dataList:"+w+":wn_input";
    var sprawdzany = "form:dataList:"+w+":ma_input";
    var cozawiera = document.getElementById(sprawdzany).value.length;
    if(cozawiera>0){
        document.getElementById(blokowany).setAttribute('disabled','true');
        document.getElementById("form:dataList:"+w+":ma").setAttribute('display','none');
        var kontopole = "form:dataList:"+w+":konto_input";
        document.getElementById(kontopole).focus();
    } else {
        document.getElementById(blokowany).removeAttribute('disabled');
        var kontopole = "form:dataList:"+w+":wn_input";
        document.getElementById(kontopole).focus();
    }
}

function aktywujnetto(){
    document.getElementById("dodWiad:opis").focus();
};

 PrimeFaces.locales['pl'] = {
            closeText: 'Zamknij',
            prevText: 'Poprzedni',
            nextText: 'Nast\u0119pny',
            currentText: 'Bie\u017cący',
            monthNames: ['Stycze\u0144','Luty','Marzec','Kwiecie\u0144','Maj','Czerwiec','Lipiec','Sierpie\u0144','Wrzesie\u0144','Pa\u017adziernik','Listopad','Grudzie\u0144'],
            monthNamesShort: ['Sty','Lut','Mar','Kwi','Maj','Cze', 'Lip','Sie','Wrz','Pa\u017a','Lis','Gru'],
            dayNames: ['Niedziela','Poniedzia\u0142ek','Wtorek','\u015aroda','Czwartek','Pi\u0105tek','Sobota'],
            dayNamesShort: ['Nie','Pon','Wt','\u015ar','Czw','Pt','So'],
            dayNamesMin: ['N','P','W','\u015a','Cz','P','S'],
            weekHeader: 'Tydzie\u0144',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: 'r',
            timeOnlyTitle: 'Tylko czas',
            timeText: 'Czas',
            hourText: 'Godzina',
            minuteText: 'Minuta',
            secondText: 'Sekunda',
            currentText: 'Teraz',
            ampm: false,
            month: 'Miesi\u0105c',
            week: 'Tydzie\u0144',
            day: 'Dzie\u0144',
            allDayText: 'Ca\u0142y dzie\u0144'
 };
  
function oknoklientanowego(){
    window.open("kliencipopup.xhtml?redirect=true","",'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

function wydrukpkpir(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("form:pkpirwysylka").style.display='inline';
}

function wydrukstr(kto){
    window.open('../wydruki/srodki'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formSTR:ewwysylka").style.display='inline';
    
}
// progress on transfers from the server to the client (downloads)
function updateProgress (oEvent) {
  if (oEvent.lengthComputable) {
    var percentComplete = oEvent.loaded / oEvent.total;
    // ...
  } else {
    // Unable to compute progress information since the total size is unknown
  }
}
 
function transferComplete(evt) {
  alert("The transfer is complete.");
}
 
function transferFailed(evt) {
  alert("An error occurred while transferring the file.");
}
 
function transferCanceled(evt) {
  alert("The transfer has been canceled by the user.");
}
function wydrukvat7(kto){
//    var oReq = new XMLHttpRequest();
//    window.addEventListener("progress", updateProgress, false);
//    window.addEventListener("load", transferComplete, false);
//    window.addEventListener("error", transferFailed, false);
//    window.addEventListener("abort", transferCanceled, false);
    
    window.open('../vat/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:dataList:1:mailbutton").style.display='inline';
//    var myrequest = window.XMLHttpRequest;
//    myrequest.status=200;
}


function wydrukobroty(kto){
    window.open('../wydruki/obroty'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:obrotywysylka").style.display='inline';
}

function wydruksumavat(kto){
    window.open('../wydruki/vatsuma'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
}

function wydrukpk(kto){
    window.open('../wydruki/pk'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
}

function wydrukpit5(kto){
    window.open('../wydruki/pit5'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
}

function wydrukewidencje(kto,nazwa){
    if(!nazwa.indexOf("sprzedaż", 0)){
    nazwa = nazwa.substr(0, nazwa.length-1);
    }
    window.open('../wydruki/vat-'+nazwa+'-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
}

function focusdatavalidate(){
        document.getElementById("dodWiad:dataPole").focus();
        };

function number_format (number, decimals, dec_point, thousands_sep) {
// Formats a number with grouped thousands
//
// version: 906.1806
// discuss at: http://phpjs.org/functions/number_format
// +   original by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
// +   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
// +     bugfix by: Michael White (http://getsprink.com)
// +     bugfix by: Benjamin Lupton
// +     bugfix by: Allan Jensen (http://www.winternet.no)
// +    revised by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
// +     bugfix by: Howard Yeend
// +    revised by: Luke Smith (http://lucassmith.name)
// +     bugfix by: Diogo Resende
// +     bugfix by: Rival
// +     input by: Kheang Hok Chin (http://www.distantia.ca/)
// +     improved by: davook
// +     improved by: Brett Zamir (http://brett-zamir.me)
// +     input by: Jay Klehr
// +     improved by: Brett Zamir (http://brett-zamir.me)
// +     input by: Amir Habibi (http://www.residence-mixte.com/)
// +     bugfix by: Brett Zamir (http://brett-zamir.me)
// *     example 1: number_format(1234.56);
// *     returns 1: '1,235'
// *     example 2: number_format(1234.56, 2, ',', ' ');
// *     returns 2: '1 234,56'
// *     example 3: number_format(1234.5678, 2, '.', '');
// *     returns 3: '1234.57'
// *     example 4: number_format(67, 2, ',', '.');
// *     returns 4: '67,00'
// *     example 5: number_format(1000);
// *     returns 5: '1,000'
// *     example 6: number_format(67.311, 2);
// *     returns 6: '67.31'
// *     example 7: number_format(1000.55, 1);
// *     returns 7: '1,000.6'
// *     example 8: number_format(67000, 5, ',', '.');
// *     returns 8: '67.000,00000'
// *     example 9: number_format(0.9, 0);
// *     returns 9: '1'
// *     example 10: number_format('1.20', 2);
// *     returns 10: '1.20'
// *     example 11: number_format('1.20', 4);
// *     returns 11: '1.2000'
// *     example 12: number_format('1.2000', 3);
// *     returns 12: '1.200'
var n = number, prec = decimals;

var toFixedFix = function (n,prec) {
    var k = Math.pow(10,prec);
    return (Math.round(n*k)/k).toString();
};

n = !isFinite(+n) ? 0 : +n;
prec = !isFinite(+prec) ? 0 : Math.abs(prec);
var sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep;
var dec = (typeof dec_point === 'undefined') ? '.' : dec_point;

var s = (prec > 0) ? toFixedFix(n, prec) : toFixedFix(Math.round(n), prec); //fix for IE parseFloat(0.55).toFixed(0) = 0;

var abs = toFixedFix(Math.abs(n), prec);
var _, i;

if (abs >= 1000) {
    _ = abs.split(/\D/);
    i = _[0].length % 3 || 3;

    _[0] = s.slice(0,i + (n < 0)) +
          _[0].slice(i).replace(/(\d{3})/g, sep+'$1');
    s = _.join(dec);
} else {
    s = s.replace('.', dec);
}

var decPos = s.indexOf(dec);
if (prec >= 1 && decPos !== -1 && (s.length-decPos-1) < prec) {
    s += new Array(prec-(s.length-decPos-1)).join(0)+'0';
}
else if (prec >= 1 && decPos === -1) {
    s += dec+new Array(prec).join(0)+'0';
}
return s+" zł"; };

function przekazTrans(trans){
    alert(trans);
}

function updatesum() {
    document.getElementById("dodWiad:vat1").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)*0.23, 2, '.', ' ');
    if(document.getElementById("dodWiad:dokumentprosty").checked == true){
        document.getElementById("dodWiad:sumbrutto").value = number_format((document.getElementById("dodWiad:kwotaPkpir_hinput").value -0), 2, '.', ' ');
    } else if (typeof(document.getElementById("dodWiad:netto2_hinput")) != 'undefined' && (document.getElementById("dodWiad:netto2_hinput") != null)){
        
    document.getElementById("dodWiad:vat2").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)*0, 2, '.', ' ');
    document.getElementById("dodWiad:vat3").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)*0, 2, '.', ' ');
    document.getElementById("dodWiad:vat4").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)*0, 2, '.', ' ');
    document.getElementById("dodWiad:vat5").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)*0, 2, '.', ' ');
    document.getElementById("dodWiad:brutto1").value = number_format(parseFloat(document.getElementById("dodWiad:netto1_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat1").value), 2, '.', ' ');
      document.getElementById("dodWiad:brutto2").value = number_format(parseFloat(document.getElementById("dodWiad:netto2_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat2").value), 2, '.', ' ');
       document.getElementById("dodWiad:brutto3").value = number_format(parseFloat(document.getElementById("dodWiad:netto3_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat3").value), 2, '.', ' ');
        document.getElementById("dodWiad:brutto4").value = number_format(parseFloat(document.getElementById("dodWiad:netto4_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat4").value), 2, '.', ' ');
         document.getElementById("dodWiad:brutto5").value = number_format(parseFloat(document.getElementById("dodWiad:netto5_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat5").value), 2, '.', ' ');
    document.getElementById("dodWiad:sumbrutto").value = number_format(
            parseFloat(document.getElementById("dodWiad:netto1_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat1").value)
            +parseFloat(document.getElementById("dodWiad:netto2_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat2").value)
            +parseFloat(document.getElementById("dodWiad:netto3_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat3").value)
            +parseFloat(document.getElementById("dodWiad:netto4_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat4").value)
            +parseFloat(document.getElementById("dodWiad:netto5_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat5").value), 2, '.', ' ');
      } else {
        
      document.getElementById("dodWiad:sumbrutto").value = number_format((parseFloat(document.getElementById("dodWiad:netto1_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat1").value)),2,'.',' ');
      document.getElementById("dodWiad:brutto1").value =  number_format((parseFloat(document.getElementById("dodWiad:netto1_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat1").value)),2,'.',' ');
     }
};

 
 function updatesuma(wiersz){
      document.getElementById("dodWiad:vat1").value = number_format(parseFloat(document.getElementById("dodWiad:netto1_hinput").value)*0.23, 2, '.', ' ');
      document.getElementById("dodWiad:brutto1").value =  number_format((parseFloat(document.getElementById("dodWiad:netto1_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat1").value)),2,'.',' ');
    if (typeof(document.getElementById("dodWiad:netto2_hinput")) != 'undefined' && (document.getElementById("dodWiad:netto2_hinput") != null)){
     document.getElementById("dodWiad:vat2").value = number_format(zrobFloat(document.getElementById("dodWiad:netto2_hinput").value)*0.08, 2, '.', ' ');
    document.getElementById("dodWiad:vat3").value = number_format(zrobFloat(document.getElementById("dodWiad:netto3_hinput").value)*0.05, 2, '.', ' ');
    document.getElementById("dodWiad:vat4").value = number_format(zrobFloat(document.getElementById("dodWiad:netto4_hinput").value)*0, 2, '.', ' ');
    document.getElementById("dodWiad:vat5").value = number_format(zrobFloat(document.getElementById("dodWiad:netto5_hinput").value)*0, 2, '.', ' ');
    document.getElementById("dodWiad:brutto"+wiersz).value =  number_format((parseFloat(document.getElementById("dodWiad:netto"+wiersz+"_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat"+wiersz).value)),2,'.',' ');
           document.getElementById("dodWiad:sumbrutto").value = number_format(
            parseFloat(document.getElementById("dodWiad:netto1_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat1").value)
            +parseFloat(document.getElementById("dodWiad:netto2_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat2").value)
            +parseFloat(document.getElementById("dodWiad:netto3_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat3").value)
            +parseFloat(document.getElementById("dodWiad:netto4_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat4").value)
            +parseFloat(document.getElementById("dodWiad:netto5_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat5").value), 2, '.', ' ');
      } else {
              document.getElementById("dodWiad:sumbrutto").value = number_format((parseFloat(document.getElementById("dodWiad:netto1_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat1").value)),2,'.',' ');
     }
 };
 
 function zrobFloat(kwota){
      var strX = kwota.replace(",",".");
      strX = strX.replace(/\s/g, "");
      return parseFloat(strX);
 }
 
 function updatevat(wiersz){
      document.getElementById("dodWiad:vat"+wiersz).value = number_format(zrobFloat(document.getElementById("dodWiad:vat"+wiersz).value), 2, '.', ' ');
      document.getElementById("dodWiad:brutto"+wiersz).value =  number_format((parseFloat(document.getElementById("dodWiad:netto"+wiersz+"_hinput").value)+zrobFloat(document.getElementById("dodWiad:vat"+wiersz).value)),2,'.',' ');
    if (typeof(document.getElementById("dodWiad:netto2_hinput")) != 'undefined' && (document.getElementById("dodWiad:netto2_hinput") != null)){
          document.getElementById("dodWiad:sumbrutto").value = number_format(
            parseFloat(document.getElementById("dodWiad:netto1_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat1").value)
            +parseFloat(document.getElementById("dodWiad:netto2_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat2").value)
            +parseFloat(document.getElementById("dodWiad:netto3_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat3").value)
            +parseFloat(document.getElementById("dodWiad:netto4_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat4").value)
            +parseFloat(document.getElementById("dodWiad:netto5_hinput").value)
            +zrobFloat(document.getElementById("dodWiad:vat5").value), 2, '.', ' ');
      } else {
              document.getElementById("dodWiad:sumbrutto").value = number_format((parseFloat(document.getElementById("dodWiad:netto"+wiersz+"_hinput").value)+parseFloat(document.getElementById("dodWiad:vat"+wiersz).value)),2,'.',' ');
     }
 };


 
 function przekazdate(){
     document.getElementById("dodWiad:dataTPole").value = document.getElementById("dodWiad:dataPole").value;
 };
 
 function dodajPkpirX(){
     document.getElementById("dodWiad:netto1").value = number_format((document.getElementById("dodWiad:kwotaPkpir_hinput").value -0)+(document.getElementById("dodWiad:kwotaPkpirX_hinput").value -0), 2, '.', ' ');
      document.getElementById("dodWiad:sumbrutto").value = number_format(
            (document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0), 2, '.', ' ');
     document.getElementById("dodWiad:brutto1").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)+(document.getElementById("dodWiad:vat1_hinput").value -0), 2, '.', ' ');
 };
 
 function dataprzyjecia(){
     if(document.getElementById("dodWiad:acForce1_hinput").value===null){
     } else {
         document.getElementById("dodWiad:dataprz").value = document.getElementById("dodWiad:dataPole").value;
         document.getElementById("dodWiad:nazwasrodka").focus();
     }
 };
 
function ustawDate(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataPole");
    var wart = dataWyst.value;
    if(mc!=10||mc!=11||mc!=12){
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
         var dataplatnosc = document.getElementById("dodWiad:dataTPole");
         var datasprzedazy = document.getElementById("dodWiad:dataSPole");
         var rozliczony = document.getElementById("dodWiad:rozliczony");
         dataplatnosc.value = dataWyst.value;
         datasprzedazy.value = dataWyst.value;
         rozliczony.click();
     }
};
     
     
     function ustawDateSrodekTrw(){
    var dataWyst = document.getElementById("dodWiad:dataprz");
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
};

    jQuery.isSubstring = function(haystack, needle) {
//            return haystack.indexOf(needle) !== -1;
            return $.inArray(needle,haystack) !== -1;
        };
        
        var licz = 1;
        function wywolaj(opis,numer,loopid){
           var id = loopid-1; 
           var zloz = "#form\\:dataList\\:"+id+"\\:opis";
           var zlozwynik = "form:dataList:"+id+":konto_hinput";
           var zlozwynik2 = "form:dataList:"+id+":konto_input";
           var szukana = $(zloz).val();
           if(szukana.length==0){
               licz = 1;
           }
           var siatka = szukana.split(',');
           var mapa = {};
           var tablica = opis.split(',');
           var tablica2 = numer.split(',');
           for(var w=0;w<10;w++){
               var sobo = tablica[w];
               mapa[sobo] = tablica2[w];
           }
           var dlug = siatka.length;
           for (licz; licz<dlug; licz++) {
            var dawniej = licz-1;
            var szczegol = siatka[dawniej];
            if($.isSubstring(tablica,szczegol)){
               document.getElementById(zlozwynik).value = mapa[szczegol];
               document.getElementById(zlozwynik2).value = mapa[szczegol];
               licz = 1;
               break;
            }
        }
        };
        
//        $(function(){
//            $('.ui-menuitem').hover(function(){
//               $(this).siblings().fadeTo(1,.6);
//               $(this).fadeTo(1,1);
//            });
//        });

function generujoknowyboru(){
    $('#form\\:confirmDialog').bind('mouseover',function(){$('body').fadeIn(20);
});
}




(function ($) {
    var focusable = ":input, a[href]";
 
    TabKeyDown = function (event) {
        //Get the element that registered the event
        var $target = $(event.target);
        if($(event.target).is('#dodWiad\\:wprowadzenieNowego')==false){
        if (isTabKey(event)) {
            var isTabSuccessful = tab(true, event.shiftKey, $target);
 
            if (isTabSuccessful) {
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
 
                return false;
            }
        }}
    };
 
    function LoadKeyDown() {
        //on adds a handler to the object.  In this case it is the document itself
        $(document).on("keydown", TabKeyDown)
    }
 
    function isTabKey(event) {
 
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode == 13) {
            return true;
        }
 
        return false;
    }
 
    function tab(isTab, isReverse, $target) {
        if (isReverse) {
            return performTab($target, -1)
        } else {
            return performTab($target, +1)
        }
    }
 
    function performTab($from, offset) {
        var $next = findNext($from, offset);
        $next.focus();
        $next.select();
 
        return true;
    }
 
    function findNext($from, offset) {
        var $focusable = $(focusable).not(":disabled").not(":hidden").not("a[href]:empty");
 
        var currentIndex = $focusable.index($from);
 
        var nextIndex = (currentIndex + offset) % $focusable.length;
 
        var $next = $focusable.eq(nextIndex);
 
        return $next;
    }
 
    $(LoadKeyDown)
})(jQuery);