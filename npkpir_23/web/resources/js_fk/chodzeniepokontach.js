var przejdzwiersz = function(tabela, tabela1, zmienna) {
    var lolo = $(document.getElementById(tabela)).children("tr");
    if (!MYAPP.hasOwnProperty(zmienna)) {
        MYAPP[zmienna] = 1;
    } else {
        MYAPP[zmienna] += 1;
        if (MYAPP[zmienna] > lolo.length) {
            MYAPP[zmienna] = lolo.length;
        }
    }
    var komorki = $(lolo[MYAPP[zmienna]]).children("td");
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
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
    var przesun = isScrolledIntoView(komorki[1]);
    var elem = document.getElementById(tabela1);
    elem.scrollTop = elem.scrollTop + przesun;
    $(komorki[1]).click();
};


var isScrolledIntoView = function(elem) {
    try {
        //tak daleko zeby dotrzec do kontenera
        var parent = elem.parentNode
        do {
            parent = parent.parentNode;
        } while (parent.className !== "ui-layout-unit-content ui-widget-content");
        var docViewTop = elem.parentNode.offsetParent.offsetTop;
        var docViewBottom = $(parent).height();
        var viewableheight = elem.scrollHeight;
        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        var przesuniecie = 0;
        if (elemTop < (docViewTop + viewableheight)) {
            przesuniecie += -viewableheight;
        }
        if (elemBottom > docViewBottom) {
            przesuniecie += elemBottom - docViewBottom;
        }
        return przesuniecie;
    } catch (ex) {
         alert("Blad w chodzeniepokonahc.js isScrolledIntoViewZK " + ex.toString());
    }
    return 0;
};