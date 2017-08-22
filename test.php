
<!DOCTYPE html>
<html lang="pl">
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/details.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v180817c"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v180817c"></script>
        <script src="/resources/js/jquery.form.js?v180817c"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v180817c"></script>
        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/ciasteczka.js?v180817c"></script>
        <!--[if lt IE 9]>
		<script src="https://html5shim.googlecode.com/svn/trunk/html5.js?v180817c"></script>
	<![endif]-->
        <title>Testy Dane Wrażliwe</title>
    </head>
    <body>

        <?php error_reporting(0); 
        require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Nextslide.php');
//             if(isset($_POST['koniec'])){
//                 session_unset();
//             }
        if(session_status()!=2){     session_start(); };
        if (isset($_POST['nexttest'])) {
            NextslideTest::next();
        } else if (isset($_POST['backtest'])) {
            NextslideTest::back();
        } else {
            NextslideTest::init();
        }
        // var_dump(Nextslide::$test1[Nextslide::$ilosc]);
        ?>
        <div class="box">
        <div class="slajd">
                <?php error_reporting(0); 
    if(session_status()!=2){     session_start(); };
    $_SESSION['szkolenietrwa'] = "nie";
    if (!isset($_SESSION['testrozpoczety'])) {
         die("<div id='gornawklejka'><span>prosimy nie używać przycisku powrotu w przeglądarce!</span>
            </div><div id='szkolenienaglowek'><h2>Zakończono już test, nie można do niego wrócić</h2></div>
            <div style='width: 700px; height: 40px; padding: 10px; margin-left: auto; margin-right: auto; font-size: larger;'>
                    <form id='form' action='' method='post' >
                    <button id='zalogujponownie' name='zalogujponownie' class='buttonszkolenie' type='submit' formaction='index.php' style='float: left;' title='Powrót do strony logowania'><span class='spanszkolenie'>login</span></button>
                    </form>
            </div></div></body></html>
            ");
    }
    if ($_SESSION['testrozpoczety'] != "tak") {
        die("<div id='gornawklejka'><span>prosimy nie używać przycisku powrotu w przeglądarce!</span>
            </div><div id='szkolenienaglowek'><h2>Zakończono już test, nie można do niego wrócić</h2></div>
            <div style='width: 700px; height: 40px; padding: 10px; margin-left: auto; margin-right: auto; font-size: larger;'>
                    <form id='form' action='' method='post' >
                    <button id='zalogujponownie' name='zalogujponownie' class='buttonszkolenie' type='submit' formaction='index.php' style='float: left;' title='Powrót do strony logowania'><span class='spanszkolenie'>login</span></button>
                    </form>
            </div></div></body></html>
            ");
    }
    ?>
            <div id="testnaglowek">
                <h2><?php  echo NextslideTest::$opis; ?></h2>
            </div>
         
            <form id="form" action="" method="post">
                <div class="pytanietest">
                    <span>Pytanie nr <?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?> : <?php error_reporting(0);  echo NextslideTest::$wybranepytania[NextslideTest::$ilosc]['pytanie']; ?></span>
                </div>
                <div class="odpowiedzitest" >
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp1" name ="odp1" type="checkbox" class="czekboxtext"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo NextslideTest::$wybranepytania[NextslideTest::$ilosc]['odp1']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;"> 
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp2" name ="odp2" type="checkbox" class="czekboxtext"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo NextslideTest::$wybranepytania[NextslideTest::$ilosc]['odp2']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp3" name ="odp3" type="checkbox" class="czekboxtext"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo NextslideTest::$wybranepytania[NextslideTest::$ilosc]['odp3']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp4" name ="odp4" type="checkbox" class="czekboxtext"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo NextslideTest::$wybranepytania[NextslideTest::$ilosc]['odp4']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <script>
//                    (function() {
//                        /*odtwarzanie zaznaczonych juz odpowiedzi w przypadku cofniecia sie uzytkownika*/
//                        var nazwaciasteczka = "pytanie" +<?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?>;
//                        ciasteczko = new Cookie(nazwaciasteczka);
//                        ciasteczko.refresh();
//                        if (typeof ciasteczko.value !== 'undefined') {
//                            var wybrane = ciasteczko.value.split(",");
//                            $('#odp1').attr('checked', wybrane[0] === 'true' ? true : false);
//                            $('#odp2').attr('checked', wybrane[1] === 'true' ? true : false);
//                            $('#odp3').attr('checked', wybrane[2] === 'true' ? true : false);
//                            $('#odp4').attr('checked', wybrane[3] === 'true' ? true : false);
//                        }
//                    }()); 
                     (function() {
                        /*odtwarzanie zaznaczonych juz odpowiedzi w przypadku cofniecia sie uzytkownika*/
                        var odpowiedzi = '<?php error_reporting(0);  echo NextslideTest::przywrocenieodpowiedzi() ?>';
                        if (odpowiedzi !== 'błąd') {
                            var wybrane = odpowiedzi.split(",");
                            $('#odp1').attr('checked', wybrane[0] === 'true' ? true : false);
                            $('#odp2').attr('checked', wybrane[1] === 'true' ? true : false);
                            $('#odp3').attr('checked', wybrane[2] === 'true' ? true : false);
                            $('#odp4').attr('checked', wybrane[3] === 'true' ? true : false);
                        }
                    }());
                </script>
                <div style="margin: 20px; height: 20px; text-align: right">
                    <span id="odpowiedz"></span>
                </div>
                <div class="zakonczylesszkolenie">
                    <span id="koniectest" style="display: none;">Jeśli chcesz możesz teraz zakończyć test i sprawdzić wynik. W tym celu kliknij poniżej.</span>
                </div>
                <div class="dolneprzyciski">
<!--                 po id tych buttonow wiadomo jest czy to nastepny czy poprzeni. jest to transportowane przez $_Post nie ma action wiec laduje sie ta sama strona-->
                    <button id="backtest" name="backtest" class="buttonszkolenie" type="submit" onclick="przechowajodpowiedz('<?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?>');" style="float: left;" title="Powrót do poprzedniej strony testu"><span class="spanszkolenie">wróć</span></button>
                    <button id="nexttest" name="nexttest" class="buttonszkolenie" type="submit" onclick="przechowajodpowiedz('<?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?>');" style="float: right;" title="Przejście do kolejnej strony testu"><span class="spanszkolenie">dalej</span></button>
                    <button id="check" name="check" class ="buttonszkolenie" type="submit" formaction="wynik.php" formmethod="post" onclick="przechowajodpowiedz('<?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?>');" style="float: right;" title="Definitywne zakończenie testu i publikacja wyniku"><span class="spanszkolenie">sprawdź</span></button>
                </div>
                <script>
                    (function() {
                        /*'zabawa' z przyciskami. odpowiednio pokazywania lub chowanie w zaleznosci od ego na ktorym slidzie jestesmy*/
                        var nrkolejny = <?php error_reporting(0);  echo $_SESSION['ilosc'] + 1 ?>;
                        var max = <?php error_reporting(0);  echo $_SESSION['pytaniasize'] ?>;
                        if (nrkolejny < max) {
                            $('#nexttest').show();
                        }
                        if (nrkolejny > 1) {
                            $('#backtest').show();
                        } else {
                            $('#backtest').hide();
                        }
                        if (nrkolejny === max) {
                            $('#koniectest').show();
                            $('#nexttest').hide();
                            $('#check').show();
                        } else {
                            $('#nexttest').show();
                            $('#koniectest').hide();
                            $('#check').hide();
                        }
                    }());
                </script>
<!--                <input id="koniec" name="koniec" value="Koniec" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%;">-->
            </form>
        </div>
        </div>
    </body>
</html>
