function al(){
    alert("document.getElementById(dodWiad:rodzajTrans).focus();");
}


function orientujsie(){
    document.getElementById("dodWiad:rodzajTrans").focus();
}

function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
} 

document.onkeypress = stopRKey; 

$(document).ready(function() {
    $(':input').focus(
    function(){
        $(this).css({'background-color' : '#CEE4F0'});
    });

    $(':input').blur(
    function(){
        $(this).css({'background-color' : '#DFD8D1'});
    });
});