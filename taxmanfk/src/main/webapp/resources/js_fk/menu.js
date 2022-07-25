//var ustawmenudokumenty = function(nazwa) {
//    $(document.getElementById(nazwa)).position({
//        my: "left top",
//        at: "left top",
//        of: "#ramki",
//        collision: "none"
//    });
//    $(document.getElementById(nazwa)).width(600).height(500);
//    $(document.getElementById(nazwa)).css({"margin-left": "2%"});
//
//};
//var resetujdialog = function(nazwa) {
//    $(document.getElementById(nazwa)).removeAttr('style');
//};




//var podswietlaniewierszy = function () {
///**
// * Gets the tr at the specified row or column
// */
//var tbody = document.getElementsByTagName('zestawieniedokumentow:dataList');
//function getRow(row) {
//    return tbody.getElementsByTagName('tr')[row];
//}
//
//// store these so we won't have to keep recalculating
//var numRows = tbody.getElementsByTagName('tr').length;
//
//// index of the currently highlighted row
//var curRow = 0;
//
//// highlight the initially highlighted cell
//getRow(curRow).className = 'highlighted';
//
//
//
//
//// listen for keydown event
//if (addEventListener) {
//  window.addEventListener('keydown',keydownHandler, false);
//} else if (window.attachEvent) {
//  window.attachEvent('onkeydown', keydownHandler);
//}
//
//
//
//// handle keydown event
//function keydownHandler (evt) {
//    // return the old cell to normal
//    getRow(curRow).className = 'normal';
//
//    // increment/decrement the position of the current cell
//    // depending on the key pressed
//    if (evt.keyCode === 38 && curRow > 0) // up
//        curRow--;
//    else if (evt.keyCode === 40 && curRow < numRows-1) // down
//        curRow++;
//
//    // update the new cell
//    getRow(curRow).className = 'highlighted';  
//}
//
//
//};

