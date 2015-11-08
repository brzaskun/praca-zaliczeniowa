"use strict";
var TabKeyDown;

(function ($) {
    var focusable = ":input, a[href]";

    TabKeyDown = function (event) {
        //Get the element that registered the event
        var $target = $(event.target);
        var taregetId = event.target.id;
        if (taregetId === "") {
            taregetId = event.target.name;
        }
        var zawartoscpola = r(taregetId).val();
        var wiersze = $(document.getElementById("formwpisdokument:dataList_data")).children("tr");
        var dlugoscwierszy = wiersze.length;
        try {
            var czyZawieraWn = taregetId.indexOf("kontown");
            var czyZawieraMa = taregetId.indexOf("kontoma");
            var enterdefault = taregetId.indexOf("enterdefault");
            var rozrachunki = taregetId.indexOf("rozrachunki");
            var typwiersza = MYAPP.typwiersza;
            var wierszlp = parseInt($target.attr("name").split(":")[2])+1;
        } catch (e1) {
            
        }
        if (rozrachunki === -1) {
            var toJestPoleKonta = false;
            if (czyZawieraWn > 0 || czyZawieraMa > 0 || enterdefault > 0) {
                toJestPoleKonta = true;
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
                    var war2 = dlugoscwierszy === wierszlp;
                    var war3 = czyZawieraWn > 0 && zawartoscpola !== "" && typwiersza === 1;
                    var war4 = czyZawieraMa > 0 && zawartoscpola !== "";
                    if ($(event.target).is("button") === false) {
                        if (war1 && war2 && war3 || war1 && war2 && war4) {
                            $(document.getElementById("wpisywaniefooter:dodajPustyWierszNaKoncu")).click();
                                event.preventDefault();
                                event.stopPropagation();
                                event.stopImmediatePropagation();
                                return false;
                        } else if (isTabKey(event)) {
                            var isTabSuccessful = tab(true, event.shiftKey, $target);
                            if (isTabSuccessful === true) {
                                event.preventDefault();
                                event.stopPropagation();
                                event.stopImmediatePropagation();
                                return false;
                            }
                        }
                    }
                } else if (isArrowkey(event)) {
                    if (event.keyCode === 40) {
                        goDown(wiersze,wierszlp);
                    } else if (event.keyCode === 38) {
                        goUp(wiersze,wierszlp);
                    }
                }
            }
        } else if (rozrachunki > -1) {
            //dodaje kwoty z wiersza obok przy rozliczaniu rozachunkow
            if ($(event.target).is("button") === false) {
                if (isTabKey(event)) {
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
                } else if (isSpaceKey(event)) {
                    var index = $target[0].id.match(/\d+/)[0];
                    var limit = zrobFloat($(document.getElementById('rozrachunki:pozostalodorozliczenia')).text());
                    if (limit > 0) {
                        var i = "rozrachunki:dataList:" + index + ":pozostaloWn";
                        var i_obj = document.getElementById(i);
                        var wartosc = i_obj.innerText.replace(/\s+/g, '');
                        wartosc = wartosc.replace(",", ".");
                        wartosc = parseFloat(wartosc);
                        if (wartosc <= limit) {
                            $target.val(wartosc);
                        } else {
                            $target.val(limit);
                            var wprowadzonowpole = limit;
                        }
                    }
                }
            }
        }
    };


    function LoadKeyDown() {
        //on adds a handler to the object.  In this case it is the document itself
        $(document).on("keydown", TabKeyDown);
    }
    
    function goDown(wiersze,wierszlp) {
        var nowywiersz = wiersze[wierszlp];
        var tdspan = $(nowywiersz).children()[1];
        var pole = $(tdspan).find(':input');
        $(pole).focus();
    }
    
    function goUp(wiersze,wierszlp) {
        var nowaliczba = +wierszlp !== 1 ? +wierszlp-2 : 0;
        var nowywiersz = wiersze[nowaliczba];
        var tdspan = $(nowywiersz).children()[1];
        var pole = $(tdspan).find(':input');
        $(pole).focus();
    }
    
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

    $(LoadKeyDown);
})(jQuery);

