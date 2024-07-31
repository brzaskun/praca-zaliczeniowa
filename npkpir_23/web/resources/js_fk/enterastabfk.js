"use strict";
var TabKeyDown;

(function ($) {
    var focusable = ":input, a[href]";

    TabKeyDown = function (event) {
        var wpisywanie = isTabKey(event)||isBackspaceKey(event)||isArrowkey(event);
        var rozrachunki = isTabKey(event)||isSpaceKey(event)||isDeleteKey(event);
        if((r("dialogpierwszy").is(":visible") && wpisywanie)||(r("dialogdrugi").is(":visible") && rozrachunki)){
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
                var opis = taregetId.indexOf("opisdokwpis");
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
                    } else if (isArrowkey(event) && opis > -1) {
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
                        var walutaplatnosci = r("rozrachunki:walutarozliczajacego").text();
                        var kursplatnosci = zrobFloat((r("rozrachunki:kursrozliczajacego").text()));
                        var kursrachunku = pobierzkurs(taregetId.split(":")[2]);
                        var walutarachunku = pobierzwaluta(taregetId.split(":")[2]);
                        var limitplatnosci = zrobFloat($(document.getElementById('rozrachunki:pozostalodorozliczenia')).text());
                        if (limitplatnosci > 0) {
                            var i = "rozrachunki:dataList:" + index + ":pozostaloWn";
                            var i_obj = document.getElementById(i);
                            var limitrachunku = i_obj.innerText.replace(/\s+/g, '');
                            limitrachunku = limitrachunku.replace(",", ".");
                            limitrachunku = parseFloat(limitrachunku);
                            if (walutaplatnosci===walutarachunku) {
                                //nie zmieniaj limitu
                            } else if (walutarachunku !== "PLN" && walutaplatnosci==="PLN" ) {
                                limitrachunku = limitrachunku*kursrachunku;
                                limitrachunku = limitrachunku.round(2);
                            } else if (walutarachunku==="PLN" && walutaplatnosci!=="PLN") {
                                limitrachunku = limitrachunku/kursplatnosci;
                                limitrachunku = limitrachunku.round(2);
                            } else if (walutarachunku!=="PLN" && walutaplatnosci!=="PLN") {
                                if (walutarachunku===walutaplatnosci) {
                                    var limitplatnosciwpln =  limitplatnosci*kursplatnosci;
                                    limitplatnosci = limitplatnosciwpln/kursrachunku;
                                    limitplatnosci = limitplatnosci.round(2);
                                } else {
                                    var limitplatnosciwpln =  limitplatnosci*kursplatnosci;
                                    var limitrachunkuwpln =  limitrachunku*kursrachunku;
                                    if (limitrachunkuwpln<limitplatnosciwpln) {
                                        limitplatnosci = limitrachunkuwpln/kursplatnosci;
                                    } else {
                                        limitplatnosci = limitplatnosciwpln/kursplatnosci;
                                    }
                                    limitplatnosci = limitplatnosci.round(2);
                                }    
                            }
                            var kom1 = taregetId.split("_")[0]+"_input";
                            var kom2 = taregetId.split("_")[0]+"_hinput";
                            if (limitrachunku <= limitplatnosci) {
                                if (walutaplatnosci===walutarachunku) {
                                  //nie zmieniaj limitu
                                } else if (walutarachunku!=="PLN" && walutaplatnosci!=="PLN") {
                                    limitrachunku = limitrachunku*kursrachunku;
                                    limitrachunku = limitrachunku/kursplatnosci;
                                    limitrachunku = limitrachunku.round(2);
                                }
                                limitrachunku = limitrachunku.toString();
                                limitrachunku = limitrachunku.replace(".", ",");
                                r(kom1).val(limitrachunku);
                                r(kom2).val(limitrachunku);
                            } else {
                                limitplatnosci = limitplatnosci.toString();
                                limitplatnosci = limitplatnosci.replace(".", ",");
                                r(kom1).val(limitplatnosci);
                                r(kom2).val(limitplatnosci);
                            }
                        }
                    } else if (isDeleteKey(event)) {
                        var kom1 = taregetId.split("_")[0]+"_input";
                        var kom2 = taregetId.split("_")[0]+"_hinput";
                        r(kom1).val(0.0);
                        r(kom2).val(0.0);
                        $target.change();
                    }
                }
            }
        }
    };


    
    
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
    
    function isDeleteKey(event) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 46) {
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

