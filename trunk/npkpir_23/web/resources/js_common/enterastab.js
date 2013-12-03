(function ($) {
    var focusable = ":input, a[href]";
 
    TabKeyDown = function (event) {
        if(!MYAPP.hasOwnProperty('liczydloautocomplete')){
           MYAPP.liczydloautocomplete = 0;
        } else {
            if (MYAPP.liczydloautocomplete > 1) {
                    MYAPP.liczydloautocomplete = 0;
            }
        }
        //Get the element that registered the event
        var $target = $(event.target);
        var kontoinput = $(event.target).is(".ui-autocomplete-input");
        if($(event.target).is("button")===false){
        if (isTabKey(event)&&kontoinput === false) {
            var isTabSuccessful = tab(true, event.shiftKey, $target);
             if (isTabSuccessful) {
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
                return false;
            }
        } else if (isTabKey(event) && kontoinput === true && MYAPP.liczydloautocomplete === 0) {
            MYAPP.liczydloautocomplete = 1;
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
                return false;
        } else if (isTabKey(event) && kontoinput === true && MYAPP.liczydloautocomplete > 0) {
            var isTabSuccessful = tab(true, event.shiftKey, $target);
             MYAPP.liczydloautocomplete = 2;
             if (isTabSuccessful) {
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
                return false;
            }
        }
    }
    };
 
    function LoadKeyDown() {
        //on adds a handler to the object.  In this case it is the document itself
        $(document).on("keydown", TabKeyDown);
    }
 
    function isTabKey(event) {
 
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode == 13) {
            return true;
        }
 
        return false;
    }
 
    function tab(isTab, isReverse, $target) {
        if (isReverse) {
            return performTab($target, -1);
        } else {
            return performTab($target, +1);
        }
    }
 
    function performTab($from, offset) {
        var $next = findNext($from,offset);
        $next.focus();
        $next.select();
 
        return true;
    }
 
    function findNext($from, offset) {
        var $focusable = $(focusable).not(":disabled").not(":hidden").not("a[href]:empty");
 
        var currentIndex = $focusable.index($from);
 
        var nextIndex = (currentIndex + offset) % $focusable.length;
 
        var $next = $focusable.eq(nextIndex);
 
        return $next;
    }
 
    $(LoadKeyDown)
})(jQuery);

