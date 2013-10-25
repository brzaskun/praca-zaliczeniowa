var zachowajwiersz = function (wierszid, wnlubma) {
    try {
        $(document.getElementById("wpisywaniefooter:wierszid")).val(wierszid);
        $(document.getElementById("wpisywaniefooter:wnlubma")).val(wnlubma);
    } catch (blad) {
        alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};


