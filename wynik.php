<!DOCTYPE html>
<html lang="pl">
    <?php
    error_reporting(0);
    if (session_status() != 2) {
        session_start();
    };
    $_SESSION['szkolenietrwa'] = "nie";
    $_SESSION['testrozpoczety'] = "nie";
    ?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v220817a" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/main.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/details.css?v220817a"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v220817a"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v220817a"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v220817a"></script>
        <script src="/resources/js/jquery.form.js?v220817a"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v220817a"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v220817a"></script>
        <script src="/resources/js/main.js?v220817a"></script>
        <script src="/resources/js/ciasteczka.js?v220817a"></script>
        <title>Testy Dane Wrażliwe</title>
    </head>
    <body> 
        <?php
        error_reporting(0);
        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Sprawdzwyniki.php');
        Sprawdzwyniki::jakoscodpowiedzi();
        ?>
        <div class="box">
            <div class="slajd">
                <div id="testnaglowek">
                    <h2>Wynik testu</h2>
                </div>
                <form id="form" action="">
                    <div style="width: 90%; height: 320px; margin-left: auto; margin-right: auto; margin-top: 0px; font-size: 120%;"> 
                        <!--                <div style="height: 100px; text-align: left; font-size: 120%;">
                                            <p id="odpowiedz">Zadane w teście pytania:</p>
                                            <php foreach (Sprawdzwyniki::$zadanepytania as $pytanie) {?>
                                            <span><php echo $pytanie['pytanie']?></span><br/>
                                            <php }?>
                                        </div>-->
                        <div class="trescszkolenia"> 
                            <p>punkty przyznane za zaznaczenie odp. poprawnych: <?php error_reporting(0);
        echo Sprawdzwyniki::$iloscpoprawnych ?></p>
                            <p>punkty odjęte za zaznaczenie odp. niepoprawnych: <?php error_reporting(0);
        echo Sprawdzwyniki::$iloscblednych ?></p>
                            <p>wynik końcowy - ilość uzyskanych punktów: <?php error_reporting(0);
        echo Sprawdzwyniki::$roznicapunktow ?></p>
                            <p>maksymalna ilość punktów do uzyskania: <?php error_reporting(0);
        echo Sprawdzwyniki::$iloscodpowiedzi ?></p>
                            <p>zaliczyłeś test w: <?php error_reporting(0);
        echo Sprawdzwyniki::$wynik ?>%</p><div id="zaliczeniebar"><div></div></div>
                            <p>wyznaczony próg zdawalności: <?php error_reporting(0);
        echo Sprawdzwyniki::$progzdawalnosci ?>%</p><div id="zdawalnoscbar"><div></div></div>
                        </div>
                        <div class="dolneprzyciski" >
<!--                            <button id="zaswiadczenie" name="zaswiadczenie" class="buttonszkolenie" type="button" onclick="generujtesty()" style="float: right;" title="Pobranie zaświadczenia o ukończeniu szkolenia">
                                <span class="spanszkolenie">zaświadczenie</span>
                            </button>-->
                            <button id="zaswiadczenie" name="zaswiadczenie" class="buttonszkolenie" formaction="drukzaswiadczenie.php" formmethod="post"
                                    type="submit"  style="float: right;" title="Pobranie zaświadczenia o ukończeniu szkolenia" onclick="generujtesty()">
                                <span class="spanszkolenie">zaświadczenie</span>
                            </button>
                            <button id="powtorztest" name="powtorztest" type="submit" class="buttonszkolenie" formaction="nowylogin.php" formmethod="post"
                                    style="float: right;"><span class="spanszkolenie" title="Możliwość powtórzenia szkolenia i testu">powtórz</span></button>
                        </div>
                    </div>
                </form>

                <script>
                    (function () {
                        var zdane = <?php error_reporting(0);
        echo Sprawdzwyniki::$zdane ?>;
                        if (zdane === 1) {
                            $('#zaswiadczenie').show();
                            $('#powtorztest').hide();
                        } else {
                            $('#zaswiadczenie').hide();
                            $('#powtorztest').show();
                        }
                        $("#zaliczeniebar").progressbar({
                            value: <?php error_reporting(0);
        echo Sprawdzwyniki::$wynik ?>
                        });
                        $("#zaliczeniebar").css({
                            'background': 'white'
                        });
                        $("#zaliczeniebar > div").css({
                            'background': 'rgb(74,26,15)'
                        });
                        $("#zdawalnoscbar").progressbar({
                            value: <?php error_reporting(0);
        echo Sprawdzwyniki::$progzdawalnosci ?>
                        });
                        $("#zdawalnoscbar").css({
                            'background': 'white'
                        });
                        $("#zdawalnoscbar > div").css({
                            'background': 'rgb(243,112,33)'
                        });

                    }());
                </script>
        <?php error_reporting(0);
        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/KoniecTestu.php');
        if (Sprawdzwyniki::$zdane ==1) {
            KoniecTestu::odnotuj();
            KoniecTestu::archiwizuj();
        }
        ?>;
            </div>
        </div>
        <div id="ajax_sun" title="generowanie " style="display: none; text-align: center; z-index: -1; position: absolute; width: 100px; height: 120px;">
            <img src="/images/ajax_loaderc.gif" alt="ajax" height="90" width="95">;
        </div>
    </body>
</html>