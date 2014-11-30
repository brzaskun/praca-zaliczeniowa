/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var sprawdzczybrakklientafaktura = function() {
    var zawartosc = $('#akordeon\\:formstworz\\:acForce_input').val();
    if (zawartosc === "nowy klient") {
        PF('dialognowyklientfaktura').show();
    }
};

var skopiujdanenowegoklientanowafaktura = function () {
    var szukana = document.getElementById('formnkfaktura:nazwaPole').value;
    PF('dialognowyklientfaktura').hide();
    try {
        $('#akordeon\\:formstworz\\:acForce_input').val(document.getElementById('formnkfaktura:nazwaPole').value);
        $('#akordeon\\:formstworz\\:acForce_hinput').val(document.getElementById('formnkfaktura:nazwaPole').value);
        $('#akordeon\\:formstworz\\:acForce_input').focus();
        $('#akordeon\\:formstworz\\:acForce_input').select();
        PF('tworzenieklientapolenazwy').search(szukana);
    } catch (e) {
    }
};

var kopiujnazwepelna = function () {
  var skadkopiowac = rj("formnkfaktura:nazwaPole").value;
  var dokadkopiowac = rj("formnkfaktura:symbolPole").value;
  if (dokadkopiowac === "") {
      rj("formnkfaktura:symbolPole").value = skadkopiowac;
  }
};

var kliknij = function () {
    
};