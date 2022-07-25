"use strict";
var TabKeyDownRK;

(function ($) {
    var focusable = ":input, a[href]";

    TabKeyDownRK = function (event) {
        var wpisywanie = isTabKey(event)||isBackspaceKey(event)||isArrowkey(event);
        if (r("dialogewidencjavatRK").is(":visible") && wpisywanie){
            //Get the element that registered the event
            var $target = $(event.target);
            var taregetId = event.target.id;
            if (taregetId === "") {
                taregetId = event.target.name;
            }
            var czypoleedycji = $(event.target).is("input") || $(event.target).is("textarea");
            if (isBackspaceKey(event) && czypoleedycji === false) {
                //alert('backspace');
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
            } else {
                var war1 = isTabKey(event);
                if (war1===true) {
                    if ($(event.target).is("button") === false) {
                        if (isTabKey(event)) {
                            var isTabSuccessful = tab(true, event.shiftKey, $target);
                            if (isTabSuccessful === true) {
                                event.preventDefault();
                                event.stopPropagation();
                                event.stopImmediatePropagation();
                                return false;
                            }
                        }
                    }
                }
            }
        }
    };


    
    
      
    function isTabKey(event) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 13) {
            return true;
        }
        return false;
    }
    
    function isArrowkey(event) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && (event.keyCode === 38 || event.keyCode === 40)) {
            return true;
        }
        return false;
    }

    function isSpaceKey(event) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 32) {
            return true;
        }
        return false;
    }
    
  
    function isBackspaceKey(event) {

        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 8) {
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
        var $next = findNext($from, offset);
        if ($next[0].id==="formwpisdokument:opisdokumentu_focus") {
            $next = findNext($next, offset);
        }
        if ($next[0].id==="formwpisdokument:opisdokumentu_input") {
            $next = findNext($next, offset);
        }
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

})(jQuery);

