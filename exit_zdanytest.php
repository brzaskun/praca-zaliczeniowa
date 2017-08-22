<!DOCTYPE html>
<html lang="pl">
<?php error_reporting(0); 
if(session_status()!=2){     session_start(); };
$_SESSION['szkolenietrwa'] = "nie";
$_SESSION['testrozpoczety']= "nie";
?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v220817a" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/main.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/details.css?v220817a"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v220817a"/>
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
         <?php error_reporting(0);
             require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Wynikiarchiwum.php');
             Wynikiarchiwum::pobierzwynikiarchiwalne();
            ?>
      
        <div class="box">
             
            <div class="slajd">
            <div id="testnaglowek">
                    <h2>Zakończono test z wynikiem pozytywnym</h2>
                </div>
            <form id="form" action="">
                <div class="trescszkolenia"> 
                    <p>moment rozpoczęcia szkolenia: <?php error_reporting(0); echo Wynikiarchiwum::$datarozpoczecia?></p>
                    <p>moment zakończenia testu: <?php error_reporting(0); echo Wynikiarchiwum::$datazakonczenia?></p>
                    <p>punkty przyznane za zaznaczenie odpowiedzi poprawnych: <?php error_reporting(0); echo Wynikiarchiwum::$iloscpoprawnych?></p>
                    <p>punkty karne, odjęte za zaznaczenie odpowiedzi niepoprawnych: <?php error_reporting(0); echo Wynikiarchiwum::$iloscblednych?></p>
                    <p>wynik końcowy - ilość uzyskanych punktów: <?php error_reporting(0); echo Wynikiarchiwum::$roznicapunktow?></p>
                    <p>maksymalna ilość punktów do uzyskania: <?php error_reporting(0); echo Wynikiarchiwum::$iloscodpowiedzi?></p>
                    <p>zaliczono test w: <?php error_reporting(0); echo Wynikiarchiwum::$wynik?>%</p><div id="zaliczeniebar"><div></div></div>
                    <p>wyznaczony próg zdawalności: <?php error_reporting(0); echo Wynikiarchiwum::$progzdawalnosci?>%</p><div id="zdawalnoscbar"><div></div></div>
                </div>
                <div class="dolneprzyciski"
                     >
                    <button id="zaswiadczenie" name="zaswiadczenie" class="buttonszkolenie" formaction="drukzaswiadczenie.php" formmethod="post"
                                    type="submit"  style="float: right;" title="Pobranie zaświadczenia o ukończeniu szkolenia" onclick="generujtesty()">
                                <span class="spanszkolenie">zaświadczenie</span>
                            </button>
                </div>
                <script>
                    (function(){
                        $( "#zaliczeniebar" ).progressbar({
                            value: <?php error_reporting(0); echo Wynikiarchiwum::$wynik?>
                        });
                        $( "#zdawalnoscbar" ).progressbar({
                            value: <?php error_reporting(0); echo Wynikiarchiwum::$progzdawalnosci?>
                        });
                          $( "#zaliczeniebar" ).css({
                            'background': 'white'
                        });
                        $( "#zaliczeniebar > div" ).css({
                            'background': '#e05424'
                        });
                        $( "#zdawalnoscbar" ).css({
                            'background': 'white'
                        });
                        $( "#zdawalnoscbar > div" ).css({
                            'background': '#422421'
                        });

                    }());
                     </script>
            </form>
        </div>
        </div>
          <div id="ajax_sun" title="generowanie" style="display: none; text-align: center; z-index: -1; position: absolute; width: 100px; height: 120px;">
            <img src="/images/ajax_loaderc.gif" alt="ajax" height="90" width="95">;
        </div>
    </body>
</html>