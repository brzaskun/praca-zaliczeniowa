function al(){
    alert("document.getElementById(dodWiad:rodzajTrans).focus();");
}


function orientujsie(){
    document.getElementById("dodWiad:rodzajTrans").focus();
}

function openwindow(){
    alert("test");
     my_window = window.open("", "mywindow1", "status=1,width=350,height=150");
    my_window.document.write('<h1>Popup Test!</h1>');
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


$(window).bind('beforeunload', function(){
    document.getElementById("ft:wt").click();
    alert("Good Bye");
});


//$(document).ready(function() {
//    $('form').submit(function() {
//        $(this).find('input[type="submit"]').attr('disabled', true);
//        $(this).find('a[data-submit], a.submit').click(function() {
//            return false;
//        });
//    })
//});