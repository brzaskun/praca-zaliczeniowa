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
            var date_array = document.getElementById(param).value.split('-');
            var day = date_array[2];
            // Attention! Javascript consider months in the range 0 - 11
            var month = date_array[1]-1;
            var year = date_array[0];
            // This instruction will create a date object
            source_date = new Date(year,month,day);
            if(year != source_date.getFullYear())
            {
               alert('Nieprawidłowa data - sprawdź! ');
               document.getElementById(param).focus();
               return false;
            }

            if(month != source_date.getMonth())
            {
               alert('Nieprawidłowa data - sprawdź!');
               document.getElementById(param).focus();
               return false;
            }
      return true;
}

 function validate(){
        txt = parseInt(document.getElementById("dodWiad:dataPole").value.length,10);
        if (txt>0&&txt<10) {
            alert("Niepe\u0142na data. Wymagany format RRRR-MM-DD");
            document.getElementById("dodWiad:dataPole").focus();
            return false;
        } else if (txt==10){
            check_form("dodWiad:dataPole");
        } else {
        return true;
        }
    };
 
 function validateK(){
        document.getElementById("dodWiad:acForce_hinput").focus();
        txt = parseInt(document.getElementById("dodWiad:acForce_hinput").value.length,10);
        if (txt<3) {
            alert("Nie wybra\u0142eś klienta!");
            document.getElementById("dodWiad:acForce_input").focus();
            return false
        }else{
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

function aktywujopis(){
    document.getElementById("dodWiad:numerwlasny").focus();
}