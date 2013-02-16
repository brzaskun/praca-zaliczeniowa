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

function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
} 

document.onkeypress = stopRKey; 

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


$(window).bind('beforeunload', function(){
    document.getElementById("ft:wt").click();
    alert("Good Bye");
});

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
    }

function aktywujsrodek(){
        document.getElementById("dodWiad:form:acForce1").focus();
}

function aktywujopis(){
    document.getElementById("dodWiad:numerwlasny").focus();
}

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
}

function focusdatavalidate(){
        document.getElementById("dodWiad:dataPole").focus();
        };

function updatesum() {
    //alert(document.getElementById("dodWiad:rodzajTrans").value);
    if((document.getElementById("dodWiad:rodzajTrans").value == "SZ")||(document.getElementById("dodWiad:rodzajTrans").value == "SZK")||(document.getElementById("dodWiad:rodzajTrans").value == "RACH")){
    document.getElementById("dodWiad:brutto1_input").value = +(document.getElementById("dodWiad:netto1_hinput").value -0)+(document.getElementById("dodWiad:vat1_hinput").value -0);
    document.getElementById("dodWiad:brutto2_input").value = +(document.getElementById("dodWiad:netto2_hinput").value -0)+(document.getElementById("dodWiad:vat2_hinput").value -0);
    document.getElementById("dodWiad:brutto3_input").value = +(document.getElementById("dodWiad:netto3_hinput").value -0)+(document.getElementById("dodWiad:vat3_hinput").value -0);
    document.getElementById("dodWiad:brutto4_input").value = +(document.getElementById("dodWiad:netto4_hinput").value -0)+(document.getElementById("dodWiad:vat4_hinput").value -0);
    document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0)
            +(document.getElementById("dodWiad:netto2_hinput").value -0)
            +(document.getElementById("dodWiad:vat2_hinput").value -0)
            +(document.getElementById("dodWiad:netto3_hinput").value -0)
            +(document.getElementById("dodWiad:vat3_hinput").value -0)
            +(document.getElementById("dodWiad:netto4_hinput").value -0)
            +(document.getElementById("dodWiad:vat4_hinput").value -0);
      } else {
      document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0);
     document.getElementById("dodWiad:brutto1_input").value = +(document.getElementById("dodWiad:netto1_hinput").value -0)+(document.getElementById("dodWiad:vat1_hinput").value -0);
     }
}
 
 function updatesuma1(){
      document.getElementById("dodWiad:brutto1_input").value = +(document.getElementById("dodWiad:netto1_hinput").value -0)+(document.getElementById("dodWiad:vat1_hinput").value -0);
    if((document.getElementById("dodWiad:rodzajTrans").value == "SZ")||(document.getElementById("dodWiad:rodzajTrans").value == "SZK")||(document.getElementById("dodWiad:rodzajTrans").value == "RACH")){
            document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0)
            +(document.getElementById("dodWiad:netto2_hinput").value -0)
            +(document.getElementById("dodWiad:vat2_hinput").value -0)
            +(document.getElementById("dodWiad:netto3_hinput").value -0)
            +(document.getElementById("dodWiad:vat3_hinput").value -0)
            +(document.getElementById("dodWiad:netto4_hinput").value -0)
            +(document.getElementById("dodWiad:vat4_hinput").value -0);
      } else {
              document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0);
     }
 }

function updatesuma2(){
      document.getElementById("dodWiad:brutto2_input").value = +(document.getElementById("dodWiad:netto2_hinput").value -0)+(document.getElementById("dodWiad:vat2_hinput").value -0);
       if((document.getElementById("dodWiad:rodzajTrans").value == "SZ")||(document.getElementById("dodWiad:rodzajTrans").value == "SZK")||(document.getElementById("dodWiad:rodzajTrans").value == "RACH")){
            document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0)
            +(document.getElementById("dodWiad:netto2_hinput").value -0)
            +(document.getElementById("dodWiad:vat2_hinput").value -0)
            +(document.getElementById("dodWiad:netto3_hinput").value -0)
            +(document.getElementById("dodWiad:vat3_hinput").value -0)
            +(document.getElementById("dodWiad:netto4_hinput").value -0)
            +(document.getElementById("dodWiad:vat4_hinput").value -0);
      } else {
              document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0);
     }
 }

function updatesuma3(){
      document.getElementById("dodWiad:brutto3_input").value = +(document.getElementById("dodWiad:netto3_hinput").value -0)+(document.getElementById("dodWiad:vat3_hinput").value -0);
       if((document.getElementById("dodWiad:rodzajTrans").value == "SZ")||(document.getElementById("dodWiad:rodzajTrans").value == "SZK")||(document.getElementById("dodWiad:rodzajTrans").value == "RACH")){
            document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0)
            +(document.getElementById("dodWiad:netto2_hinput").value -0)
            +(document.getElementById("dodWiad:vat2_hinput").value -0)
            +(document.getElementById("dodWiad:netto3_hinput").value -0)
            +(document.getElementById("dodWiad:vat3_hinput").value -0)
            +(document.getElementById("dodWiad:netto4_hinput").value -0)
            +(document.getElementById("dodWiad:vat4_hinput").value -0);
      } else {
              document.getElementById("dodWiad:sumbrutto_input").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0);
     }
 }
 
 function updatesuma4(){
      document.getElementById("dodWiad:brutto4_input").value = +(document.getElementById("dodWiad:netto4_hinput").value -0)+(document.getElementById("dodWiad:vat4_hinput").value -0);
       if((document.getElementById("dodWiad:rodzajTrans").value == "SZ")||(document.getElementById("dodWiad:rodzajTrans").value == "SZK")||(document.getElementById("dodWiad:rodzajTrans").value == "RACH")){
            document.getElementById("dodWiad:sumbrutto_hinput").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0)
            +(document.getElementById("dodWiad:netto2_hinput").value -0)
            +(document.getElementById("dodWiad:vat2_hinput").value -0)
            +(document.getElementById("dodWiad:netto3_hinput").value -0)
            +(document.getElementById("dodWiad:vat3_hinput").value -0)
            +(document.getElementById("dodWiad:netto4_hinput").value -0)
            +(document.getElementById("dodWiad:vat4_hinput").value -0);
      } else {
              document.getElementById("dodWiad:sumbrutto_hinput").value = 
            +(document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0);
     }
 }