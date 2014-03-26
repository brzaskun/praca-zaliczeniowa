var przejdzwiersz = function(tabela, tabela1, zmienna) {
    var lolo = $(document.getElementById(tabela)).children("tr");
    if (!MYAPP.hasOwnProperty(zmienna)) {
        MYAPP[zmienna] = 1;
    } else {
        if (MYAPP[zmienna] < lolo.length - 1) {
            MYAPP[zmienna] += 1;
        }
    }
    var komorki = $(lolo[MYAPP[zmienna]]).children("td");
    var czynaekranie = isScrolledIntoViewZK(komorki[1]);
    if (!czynaekranie) {
        var wysokosc = 70;
        var elem = document.getElementById(tabela1);
        elem.scrollTop = elem.scrollTop + wysokosc;
    }
    $(komorki[1]).click();
};

var wrocwiersz = function(tabela, tabela1, zmienna) {
    var lolo = $(document.getElementById(tabela)).children("tr");
    if (!MYAPP.hasOwnProperty(zmienna)) {
        MYAPP[zmienna] = 1;
    } else {
        if (MYAPP[zmienna] > 0) {
            MYAPP[zmienna] -= 1;
        }
    }
    var komorki = $(lolo[MYAPP[zmienna]]).children("td");
    var czynaekranie = isScrolledIntoViewZK(komorki[1]);
    if (!czynaekranie) {
        var wysokosc = 70;
        var elem = document.getElementById(tabela1);
        elem.scrollTop = elem.scrollTop - wysokosc;
    }
    $(komorki[1]).click();
};

function isScrolledIntoViewZK(elem)
{
    try {
        var docViewTop = $(window).scrollTop() + 150;
        var docViewBottom = docViewTop + $(window).height() - 300;
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom)
                && (elemBottom <= docViewBottom) && (elemTop >= docViewTop));
    } catch (ex) {
        alert("Blad w chodzeniepokonahc.js isScrolledIntoViewZK " + ex.toString());
    }
}
