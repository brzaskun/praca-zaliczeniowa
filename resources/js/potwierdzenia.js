/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var potwierdzenieuser = function (nazwadialogu) {
    if (nazwadialogu === '#dialog-test') {
     $( nazwadialogu ).puidialog({
       height: 350,
       width: 400,
       modal: true,
       buttons: {
       Ok: function() {
           $( this ).dialog( "close" );
       }
       } 
     });   
    } else {
     $( nazwadialogu ).puidialog({
       height: 270,
       width: 320,
       modal: true,
       buttons: {
       Ok: function() {
           $( this ).dialog( "close" );
       }
       } 
     });
    }
};
