"use strict";

//nadaje odpowiednie kolory podświetlanym polom formularza
//var kolorujpola = function() {
//    $("*[name*='dodWiad'").focus(
//    function(){
//        $(this).css({'color' : 'red'});
//    });
//
//    $("*[name*='dodWiad'").blur(
//    function(){
//        $(this).css({'background-color' : '#DFD8D1'});
//    });
//};

var focusnanowymwierszu = function() {
    $("#formdialog_add_wiad_kolumna1rozbicie\\:rozbicietabeladane tr:last input:first").focus();
};


var wyloguj = function() {
    document.getElementById("templateform:wyloguj").click();
    if (document.getElementById("form:westIndex:panelwyboru")) {
        window.location.href = "login.xhtml";
    } else {
        window.location.href = "../login.xhtml";
    }
};

var aktywujsrodek = function() {
    document.getElementById("dodWiad:form:acForce1").focus();
};

var aktywujnetto = function() {
    //dolaczwyliczenie();
    document.getElementById("dodWiad:opis").focus();
};


var dolaczwyliczenie = function() {
//     try {
//        r("dodWiad:tabelapkpir:1:kwotaPkpir_input").on('keyup', function(event) {
//            var roznica;
//            if (event.which === 107) {
//                var odjema = zrobFloat($(this).val());
//                var odjemna = zrobFloat(r("dodWiad:tabelapkpir:0:kwotaPkpir_input").val());
//                roznica = odjemna - odjema;
//                $(this).val(Math.abs(roznica));
//                r("dodWiad:tabelapkpir:1:kwotaPkpir_hinput").val(Math.abs(roznica));
//                event.preventDefault();
//            }
//         });
//     } catch (ec) {
//         alert("bladzik");
//     }
};

var dolaczwyliczenieKopiowanie = function(event) {
    if (r("dodWiad:tabelapkpir:1:kwotaPkpir_input")) {
        try {
            r("dodWiad:tabelapkpir:1:kwotaPkpir_input").on('keyup', function(event) {
                if (event.which === 107) {
                    var zakuptowarow;
                    var calosc = zrobFloat($(this).val());
                    var kosztyuboczne = zrobFloat(r("dodWiad:tabelapkpir:0:kwotaPkpir_input").val());
                    zakuptowarow = calosc - kosztyuboczne;
                    r("dodWiad:tabelapkpir:1:kwotaPkpir_input").val(Math.abs(zakuptowarow));
                    r("dodWiad:tabelapkpir:1:kwotaPkpir_hinput").val(Math.abs(zakuptowarow));
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    //r("dodWiad:tabelapkpir:1:kwotaPkpir_input").blur();
                }
            });
        } catch (ec) {
            alert("bladzik");
        }
    }
    ;
};


var aktywujopis = function(czyjestvat) {
    var dokument = $('#dodWiad\\:rodzajTrans').val();
    if (dokument === 'IN') {
        r('dodWiad:inwestycja').show();
//        $("#dodWiad:inwestycja").bind('mouseover', function() {
//        alert($('#dodWiad:inwestycja').val());
//            });
        r("dodWiad:inwestycja").bind('blur', function() {

            if (r('dodWiad:inwestycja').val() === "wybierz") {
                r('dodWiad:inwestycja').focus();
            }
        });
    } else {
        r('dodWiad:inwestycja').hide();
    }
    //zaznacza pola checkboxow w dla dokumentow prostych
    switch (dokument) {
        case 'LP':
        case 'PK':
        case 'ZUS':
            if (r('dodWiad:dokumentprosty').is(':checked') === false) {
                r('dodWiad:dokumentprosty').trigger("click");
                r('dodWiad:panelewidencjivat').hide();
            }
            break;
        case 'IU':
            if (r('dodWiad:dokumentprosty').is(':checked') === true) {
                r('dodWiad:dokumentprosty').trigger("click");
                r('dodWiad:panelewidencjivat').show();
            }
            break;
        default:
            if (r('dodWiad:dokumentprosty').is(':checked') === true && czyjestvat === false) {
                r('dodWiad:dokumentprosty').trigger("click");
                r('dodWiad:panelewidencjivat').show();
            }
    }

    //dodaje nowa kolumne podczas wpisywania faktury. robi to po stwierdzeniu wcisniecia klawisza +. usuwa tez symbol + z ciagu opisu
    //zachowuje takze opis dokumentgu po wcisnieciu klawisza F8
   lisnerdodanienowegowiersza();
    //$('#dodWiad\\:numerwlasny').focus();
};

var lisnerdodanienowegowiersza = function () {
    r('dodWiad:opis_input').on('keyup', function (e) {
        var kodklawisza = e.which;
        if (kodklawisza === 107) {
            r('dodWiad:dodkol').click();
            var wartoscpola = r('dodWiad:opis_input').val();
            r('dodWiad:opis_input').val(wartoscpola.slice(0, -1));
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
        }
        if (kodklawisza === 109) {
            r('dodWiad:usunkol').click();
            var wartoscpola = r('dodWiad:opis_input').val();
            r('dodWiad:opis_input').val(wartoscpola.slice(0, -1));
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
        }
        //zapisuje opis w bazie
        if (kodklawisza === 120) {
            r('dodWiad:dodajopis').click();
            r('dodWiad:opis_input').css({
                color: 'green',
                "font-weight": 900
            });
            r('dodWiad:opis').focus();
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
        }
    });
}

//to jest konieczne do wyswietlania prawidlowych nazw w kalendarzu
PrimeFaces.locales['pl'] = {
    closeText: 'Zamknij',
    prevText: 'Poprzedni',
    nextText: 'Nast\u0119pny',
    currentText: 'Bie\u017cący',
    monthNames: ['Stycze\u0144', 'Luty', 'Marzec', 'Kwiecie\u0144', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpie\u0144', 'Wrzesie\u0144', 'Pa\u017adziernik', 'Listopad', 'Grudzie\u0144'],
    monthNamesShort: ['Sty', 'Lut', 'Mar', 'Kwi', 'Maj', 'Cze', 'Lip', 'Sie', 'Wrz', 'Pa\u017a', 'Lis', 'Gru'],
    dayNames: ['Niedziela', 'Poniedzia\u0142ek', 'Wtorek', '\u015aroda', 'Czwartek', 'Pi\u0105tek', 'Sobota'],
    dayNamesShort: ['Nie', 'Pon', 'Wt', '\u015ar', 'Czw', 'Pt', 'So'],
    dayNamesMin: ['N', 'P', 'W', '\u015a', 'Cz', 'P', 'S'],
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
    ampm: false,
    month: 'Miesi\u0105c',
    week: 'Tydzie\u0144',
    day: 'Dzie\u0144',
    allDayText: 'Ca\u0142y dzie\u0144'
};

var oknoklientanowego = function() {
    window.open("kliencipopup.xhtml?redirect=true", "", 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};


var focusdatavalidate = function() {
    document.getElementById("dodWiad:dataPole").focus();
};


var przekazdate = function() {
    document.getElementById("dodWiad:dataTPole").value = document.getElementById("dodWiad:dataPole").value;
};


var ustawDateSrodekTrw = function() {
    try {
        var dataWyst = rj("dodWiad:tabelasrodkitrwaleOT:0:dataprz");
        var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
        var testw = dataWyst.value;
        if (!testw.match(re)) {
            dataWyst.value = "b\u0142ędna data";
        }
    } catch(e) {
        
    }
};





var generujoknowyboru = function() {
    $('#form\\:confirmDialog').bind('mouseover', function() {
        $('body').fadeIn(20);
    });
};


//rodzial dotyczacy umiejscawiania pozycji z  faktury
var petlawywolujaca = function(lw, gr, co) {
    //alert('petlawywolujaca');
    var dlugosc = lw.length - 1;
    for (var j = 0; j < dlugosc; j++) {
        //alert(lw[j]+","+gr[j]+","+co[j]);
        var lewy = parseInt(lw[j]);
        var cos = co[j];
        var gora = parseInt(gr[j]);
        $(document.getElementById(cos)).css({position: "absolute",
            marginLeft: 0, marginTop: 0,
            top: gora, left: lewy});

    }
};

var sprawdzczybrakklienta = function() {
    var zawartosc = $('#dodWiad\\:acForce_input').val();
    if (zawartosc === "nowy klient") {
        PF('dlg123').show();
    }
};

var sprawdzczykopiowacklienta = function() {
    var zawartosc = $('#dodWiad\\:acForce_input').val();
    if (zawartosc === "+") {
        var text = $('#zobWiad\\:nazwa').html();
        $('#dodWiad\\:acForce_input').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_hinput').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_input').focus();
        $('#dodWiad\\:acForce_input').select();
        PF('dialogklient').search(text);
        event.cancelBubble = true;
        event.stopPropagation();
        event.stopImmediatePropagation();
    }
};

var sprawdzczykopiowacklientaarch = function() {
    var zawartosc = $('#dodWiad\\:acForce_input').val();
    if (zawartosc === "+") {
        var text = $('#zobWiad\\:nazwa').html();
        $('#dodWiad\\:acForce_input').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_hinput').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_input').focus();
        $('#dodWiad\\:acForce_input').select();
        PF('dialogklient').search(text);
        event.cancelBubble = true;
        event.stopPropagation();
        event.stopImmediatePropagation();
    }
};

var skopiujdanenowegoklienta = function() {
    PF('dlg123').hide();
    $('#dodWiad\\:acForce_input').focus();
    var szukana = document.getElementById('formX:nazwaPole').value;
    PF('dialogklient').search(szukana);

};



var varzmienkolorpola47deklvat = function() {
    $("#form\\:pole47").css({
        backgroundColor: '#ADD8E6'
    });
    $("#form\\:pole47").focus();
    $("#form\\:pole47").select();
};



 