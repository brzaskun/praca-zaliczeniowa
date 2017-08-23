/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
});

var firmadoimportu = function() {
    var ciasteczko = new Cookie("firmadoimportu");
    ciasteczko.value = encodeURIComponent($('#IMPfirmauser').val());
    ciasteczko.save();
    $('#zaladuj').show();
} 


