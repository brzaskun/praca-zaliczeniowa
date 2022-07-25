"use strict";

var dodajinfopole62 = function (pole) {
    var pole2 = parseInt(document.getElementById("form:pole62").value);
    if (pole2>0) {
        var newElement = document.createElement('label');
        newElement.innerHTML = " - wymagany załącznik VAT-ZT";
        newElement.style.color = "green";
        document.getElementById("form:pole62").parentNode.insertBefore(newElement, document.getElementById("form:pole62").nextSibling);  
    }
};


