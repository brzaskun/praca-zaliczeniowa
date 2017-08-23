
var fileSelected = function () {
    var file = document.getElementById('file').files[0];
    if (file) {
        var fileSize = 0;
        if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
        else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
        document.getElementById('fileName').innerHTML = 'Nazwa pliku: ' + file.name;
        document.getElementById('fileSize').innerHTML = 'Rozmiar: ' + fileSize;
        document.getElementById('fileType').innerHTML = 'Typ: ' + file.type;
        var gdziekropka = file.name.lastIndexOf('.');
        var rozszerzenie = file.name.slice(gdziekropka + 1, file.name.length);
        document.getElementById('fileType').innerHTML = 'Typ: ' + rozszerzenie;
        if (rozszerzenie === "xls" || rozszerzenie === "xlsx") {
            $('#wyslij').show();
            $('#niewlasciwyplik').hide();
        } else {
            $('#wyslij').hide();
            $('#niewlasciwyplik').show();
        }
        $('#plikwzorcowy').remove();
        $('#przyciskladowanie').remove();
    }
};

var uploadFile = function () {
    var fd = new FormData();
    fd.append("file", document.getElementById('file').files[0]);
    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", "upload.php");
    xhr.send(fd);
};

var uploadProgress = function (evt) {
    if (evt.lengthComputable) {
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
    } else {
        document.getElementById('progressNumber').innerHTML = 'nie jestem w stanie wyliczyc wielkosci pliku';
    }
};

var uploadComplete = function (evt) {
    /* This event is raised when the server send back a response */
    //alert(evt.target.responseText);
    $('#wyslij').hide();
    $('#progressEfect').show();
};

var uploadFailed = function (evt) {
    alert("Podczas przesyłania pliku wystąpił błąd.");
};

var uploadCanceled = function (evt) {
    alert("Ładowanie pliku przerwane przez użytkownika bądź z powodu błędu przeglądarki.");
};

