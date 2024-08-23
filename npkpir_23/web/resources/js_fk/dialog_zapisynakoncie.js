
"use strict";

var zaznaczpole = function() {
    r("formwpiskonta:wyborkonta_input").select();
};

//dzieki temu toggleselect nie zaznacza juz wszystkich ignorujac filtr
 var toggleFilteredSelect = function(checkbox, tableWidgetVar) {
       var tableWidget = PF(tableWidgetVar);
       var rows = tableWidget.tbody.find('tr:visible');
       var checked = checkbox.checked;

       rows.each(function() {
           var row = $(this);
           var checkbox = row.find('td .ui-chkbox-box');

           if (checked) {
               checkbox.addClass('ui-state-active');
               row.addClass('ui-state-highlight');
               row.find(':checkbox').prop('checked', true);
           } else {
               checkbox.removeClass('ui-state-active');
               row.removeClass('ui-state-highlight');
               row.find(':checkbox').prop('checked', false);
           }
       });

       tableWidget.updateData();
   };
