<?php error_reporting(2);
if(session_status()!=2){     session_start(); };
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$slide = R::getAll("SELECT * FROM `testpodglad`");
$slidedopokazania = $slide[0];
?>
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
        <title>Testy Dane Wrażliwe</title>
    </head>
    <body>
        <div class="box">
        <div class="slajd">
            <div id="testnaglowek">
                <h2>Test z ochrony danych osobowych</h2>
            </div>
         
            <form id="form" action="" method="post">
                <div class="pytanietest">
                    <span>Pytanie nr 1 : <?php error_reporting(0);  echo $slidedopokazania['pytanie']; ?></span>
                </div>
                <div class="odpowiedzitest" >
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp1" name ="odp1" type="checkbox"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo $slidedopokazania['odp1']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp2" name ="odp2" type="checkbox"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo $slidedopokazania['odp2']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp3" name ="odp3" type="checkbox"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo $slidedopokazania['odp3']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="height: 25%;">
                        <table>
                            <tr>
                                <td style="vertical-align: baseline;">
                                    <input id="odp4" name ="odp4" type="checkbox"><span></span>
                                </td><td style="vertical-align: baseline;">
                                    <span style="text-justify: inter-word;"><?php error_reporting(0);  echo $slidedopokazania['odp4']; ?></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div style="margin: 20px; height: 20px; text-align: right">
                    <span id="odpowiedz"></span>
                </div>
                <div class="zakonczylesszkolenie">
                    <span id="koniectest" style="display: none;">Jeśli chcesz możesz teraz zakończyć test i sprawdzić wynik. W tym celu kliknij poniżej.</span>
                </div>
                <div class="dolneprzyciski">
                    <button id="backtest" name="backtest" class="buttonszkolenie" type="submit"  style="float: left;" title="Powrót do poprzedniej strony testu"><span class="spanszkolenie">wróć</span></button>
                    <button id="nexttest" name="nexttest" class="buttonszkolenie" type="submit" style="float: right;" title="Przejście do kolejnej strony testu"><span class="spanszkolenie">dalej</span></button>
                    <button id="check" name="check" class ="buttonszkolenie" type="submit"  style="float: right;" title="Definitywne zakończenie testu i publikacja wyniku"><span class="spanszkolenie">sprawdź</span></button>
                </div>
<!--                <input id="koniec" name="koniec" value="Koniec" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%;">-->
            </form>
        </div>
        </div>
    </body>
</html>
