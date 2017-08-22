var pola;
var trwywolujacy;
var sprawdzoneslide;


var validateEmail = function(email) {
    // http://stackoverflow.com/a/46181/11236
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
};

var validate = function() {
    $("#result").text("");
    var email = $("#email").val();
    if (validateEmail(email)) {
        $("#result").text("wpisany email jest kompletny :)");
        $("#result").css("color", "green");
        $("#result").css("font-size", "80%");
        $("#logowanie").css("display", "block");
        if (email==="mchmielewska@interia.pl" || email==="brzaskun@o2.pl") {
            $("#polepin").show();
            $("#buttonlogowanie").attr("disabled", true);
            $("#buttonlogowanie").hide();
        }
    } else {
        if (email.length > 4) {
            $("#result").text("wpisany email jest jeszcze niekompletny :(");
            $("#result").css("color", "red");
            $("#result").css("font-size", "80%");
        }
    }
    return false;
}; 


var sprawdzpin = function() {
    $.ajax({
        type: "POST",
        url: "haslower_112014.php",
        data: "mail="+rj("email").value+"&haslo="+rj("pin").value,
        cache: false,
        timeout: 20000,        // sets timeout for the request (10 seconds)
        error: function(xhr, status, error) {
            alert('Error: '+ xhr.status+ ' - '+ error); },
        success: function(response){
            if (response === "1") {
                $("#pin").val('');
                $("#result").text("poprawny pin :)");
                var plik = "sprawdzlogin_adm.php?mail="+rj("email").value+"&wynik="+response;
                window.location.href = plik;
            } 
        }
     });
};

var zaloguj = function() {
    $.ajax({
        type: "POST",
        url: "kopiujmail.php",
        data: "mail=" + rj("email").value,
        cache: false,
        timeout: 1000, // sets timeout for the request (10 seconds)
        error: function(xhr, status, error) {
            alert('Error: ' + xhr.status + ' - ' + error);
        },
        success: function(response) {
            window.location.href = "sprawdzlogin.php";
        }
    });
};
 
var przechowajodpowiedz = function(nrpytania) {
    var odp1 = $('#odp1').is(':checked');
    var odp2 = $('#odp2').is(':checked');
    var odp3 = $('#odp3').is(':checked');
    var odp4 = $('#odp4').is(':checked');
    var bl = 0;
    var nazwaciasteczka = "pytanie"+nrpytania;
    var ciasteczko = new Cookie(nazwaciasteczka);
    ciasteczko.value = [];
    ciasteczko.value.push(odp1 === true ? 'true' : 'false');
    ciasteczko.value.push(odp2 === true ? 'true' : 'false');
    ciasteczko.value.push(odp3 === true ? 'true' : 'false');
    ciasteczko.value.push(odp4 === true ? 'true' : 'false');
    ciasteczko.save();
};

var pobierzdane = function (event) {
    var currentRow = event.currentTarget.children;
    var elements = [];
    var i = 0;
    var tablesize = currentRow.length;
    for (var i = 0; i < tablesize ; i++){
        elements.push(currentRow[i].innerHTML);
    }
    trwywolujacy = event.currentTarget;
    return elements;
};
 //var edytujtabeleuser = function (){
//       $(pola[2]).html($('#email').val());
//       $(pola[3]).html($('#imienazwisko').val());
//       $(pola[4]).html($('#plecuser').val());
//       $(pola[5]).html($('#firmausernazwa').val());
//       $(pola[6]).html($('#szkolenieuser').val());
//       $(pola[7]).html($('#uprawnieniauser').val());
//       $(pola[10]).html($('#datazalogowania').val());
//       var wynikwalidacji = validateEmail($('#email').val());
//       if (wynikwalidacji===false) {
//        $('#uzytkownikwiadomosc').text("Wpisano zły mail!!");   
//        $('#wiadomoscuserjs').html('Zły mail '+$("#email").val()+'');
//       } else {
//        $('#uzytkownikwiadomosc').text($('#imienazwisko').val());
//        $('#wiadomoscuserjs').html('Dane uzytkownika '+$("#email").val()+' zmienione');
//       }
//       potwierdzenieuser('#dialog-user');
//};






var edytujtabelezaklad = function (){
       $(pola[1]).html($('#nazwazakladu').val());
       $(pola[2]).html($('#ulica').val());
       $(pola[3]).html($('#miejscowosc').val());
       $(pola[4]).html($('#kontakt').val());
       $(pola[5]).html($('#email').val());
       $(pola[6]).html($('#progzdawalnosci').val());
       $(pola[7]).html($('#firmaaktywna').val());
       $(pola[8]).html($('#maxpracownikow').val());
       $(pola[10]).html($('#managerlimit').val());
       $('#zakladwiadomosc').text($('#nazwazakladu').val());
       potwierdzenieuser('#dialog-zaklad');
       $('#wiadomoscuserjs').html('Dane firmy '+$("#nazwazakladu").val()+' zmienione');
};

var edytujtabeleszkolenie = function (){
       $(pola[1]).html($('#nazwaszkolenia').val());
       $(pola[2]).html($('#naglowek').val());
       $(pola[3]).html($('#temat').val());
       var edit = CKEDITOR.instances.trescszkolenia.getData();
       $(pola[4]).html(edit);
       var ciasteczko = new Cookie("editslajd");
       ciasteczko.value = edit;
       ciasteczko.save();
       $('#szkoleniewiadomosc').text($('#naglowek').val());
       potwierdzenieuser('#dialog-szkolenie');
       $('#wiadomoscuserjs').html('Element szkolenia '+$("#temat").val()+' zmieniony');
};

//to sa stare rzeczy jak byl tylko php!!
var edytujtabeletest = function (){
       $(pola[1]).html($('#nazwatest').val());
       $(pola[2]).html($('#ttresc').val());
       $(pola[3]).html($('#trodzaj').val());
       $(pola[4]).html($('#tpytanie').val());
       $(pola[5]).html($('#todp1').val());
       $(pola[6]).html($('#todp2').val());
       $(pola[7]).html($('#todp3').val());
       $(pola[8]).html($('#todp4').val());
       $(pola[9]).html($('#todp1w').is(':checked') === true ? 'true' : 'false');
       $(pola[10]).html($('#todp2w').is(':checked') === true ? 'true' : 'false');
       $(pola[11]).html($('#todp3w').is(':checked') === true ? 'true' : 'false');
       $(pola[12]).html($('#todp4w').is(':checked') === true ? 'true' : 'false');
//       $("#todp1w").attr('checked', elements[9] === 'true' ? true : false);
//       $('#odp1').attr('checked', wybrane[0] === 'true' ? true : false);
       $('#testwiadomosc').text($('#tpytanie').val());
       potwierdzenieuser('#dialog-test');
       $('#wiadomoscuserjs').html('Element tetsu '+$("#tpytanie").val()+' zmieniony');
};

var usuntabeleuser = function (){
       $(trwywolujacy).remove();
       $('#uzytkownikwiadomoscusun').text($('#imienazwisko').val());
       potwierdzenieuser('#dialog-user-usun');
       $('#wiadomoscuserjs').html('Użytkownik '+$("#email").val()+' usunięty');
       $('#dialog-user-usun').show();
};

var usuntabeleusermanager = function (){
       var czyjuzzaczal = $("#datazalogowania").val();
       if (czyjuzzaczal.length>0) {
       $('#uzytkownikwiadomoscnousun').text($('#imienazwisko').val());
       potwierdzenieuser('#dialog-user-nousun');
       $('#wiadomoscuserjs').html('Użytkownik '+$("#email").val()+' nie usunięty');
       } else {
       $(trwywolujacy).remove();
       $('#uzytkownikwiadomoscusun').text($('#imienazwisko').val());
       potwierdzenieuser('#dialog-user-usun');
       $('#wiadomoscuserjs').html('Użytkownik '+$("#email").val()+' usunięty');
       $('#odswiez').click();
       }
};


var usuntabelezaklad = function (){
       $(trwywolujacy).remove();
       $('#wiadomoscuserjs').html('Firma '+$("#nazwazakladu").val()+' usunięta');
};

var usuntabeleszkolenie = function (){
       $(trwywolujacy).remove();
       $('#wiadomoscuserjs').html('Element szkolenia usunięty');
};

var usuntabeleszkolenie = function (){
       $(trwywolujacy).remove();
       $('#wiadomoscuserjs').html('Element szkolenia usunięty');
};

var usuntabeletest = function (){
       $(trwywolujacy).remove();
       $('#wiadomoscuserjs').html('Element testu usunięty');
}; 

var usunwieleuserow = function () {
  try {
  var elements = [];  
  elements = $("#tabuser tr").find('.id');
  var checkbox = [];
  checkbox = $("#tabuser tr").find(':checkbox');
  var numery =[];
  for(var pozycja in checkbox) {
      if (checkbox[pozycja].checked===true) {
          numery.push(elements[pozycja].innerHTML);
      }
  }
   var ciasteczko = new Cookie("listadousuniecia");
   ciasteczko.value = numery;
   ciasteczko.save();
  } catch (el) {
      console.log("Blad "+el);
  }
};

var generujtesty = function() { 
     $("#ajax_sun").puidialog({
        height: 100,
        width: 150,
        resizable: false,
        closable: false,
        modal: true,
    });
    $.ajax({
           type: 'POST',
           url: 'wcisnietyklawisz.php',
           async: false
    });
    $("#ajax_sun").puidialog('show');
    $.ajax({
        type: 'POST',
        url: 'generujdoc.php',
        success: function() {
            $("#ajax_sun").puidialog('hide');
            window.location.href = 'pobierzcertyfikat2.php'; 
        },
        error: function(xhr, ajaxOptions, thrownerror) { 
            $("#ajax_sun").puidialog('hide'); 
            window.location.href = 'exit_niewygenerowanozaswiadczenia.php'; 
        } 
    });
};
 

