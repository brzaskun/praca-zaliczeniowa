var zachowajwiersz = function (wierszid) {
    try {
        $(document.getElementById("wpisywaniefooter:wierszid")).val(wierszid);
    } catch (blad) {
        alert("Blad w dialgowprowadzanie.js zachowaj wiersz "+blad);
    }
};


