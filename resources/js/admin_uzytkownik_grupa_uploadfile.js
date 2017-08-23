$(document).ready(function () {
    $('#IMPfirmauser').puidropdown({
        filter: true,
        change: function (e) {
            firmadoimportu();
        },
        data: function (callback) {
            $.ajax({
                type: "POST",
                url: "pobierzfirmyJson_112014.php",
                dataType: "json",
                context: this,
                success: function (response) {
                    callback.call(this, response);
                }
            });
        }
    });
    $('#rodzajdanych').puidropdown();
    podswietlmenu(rj('menuupowaznieniagrupa'));
});


var firmadoimportu = function () {
    var ciasteczko = new Cookie("firmadoimportu");
    ciasteczko.value = encodeURIComponent($('#IMPfirmauser').val());
    ciasteczko.save();
    $('#zaladuj').show();
};

var uploadfile_uzyt_grupa = function () {
    $("#ajax_sun").puidialog({
        height: 67,
        width: 150,
        resizable: false,
        closable: false,
        modal: true
    });
    $("#ajax_sun").puidialog('show');
    var rodzajdanych = document.getElementById("rodzajdanych").value;
    var rodzajdanych = "rodzajdanych=" + rodzajdanych;
     $.ajax({
        type: "POST",
        url: "upload_uzytkownik_grupa.php",
        data: rodzajdanych,
        cache: false,
        success: function(result){
            window.location.href = "admin112014_uzytkownik_grupy.php";
        },
        error: function(xhr, status, error) { 
            $('#notify').puigrowl('show', [{severity: 'error', summary: 'Nie udało się pobrać listy zaświadczeń.'}]);
        },
    });
}


