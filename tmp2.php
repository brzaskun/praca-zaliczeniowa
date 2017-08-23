<!DOCTYPE html>
<html lang="pl">
     <?php error_reporting(0);
if(session_status()!=2){     session_start(); };
if(!isset($_SESSION['szkolenietrwa'])){
    die("Nie jesteś upoważniony do przeglądania zasobu");
}
if ($_SESSION['szkolenietrwa']!= "tak") {
    $_SESSION['testrozpoczety'] = "nie";
    die("Nie jesteś upoważniony do przeglądania zasobu");
}
?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v220817a" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v220817a"/>
        <link rel="stylesheet" href="/resources/css/main.css?v220817a"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v220817a"/>
        <script src="/resources/js/jquery-1.12.3.js?v220817a"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v220817a"></script>
        <script src="/resources/js/jquery.form.js?v220817a"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v220817a"></script>
        <script src="/resources/js/main.js?v220817a"></script>
        <script src="/resources/js/ciasteczka.js?v220817a"></script>
        <title>Testy Dane Wrażliwe</title>
<!--        <script>
            $(function() {
                $("#dialog-modal").dialog({
                    height: 550,
                    width: 790,
                    scrollbars: false,
                    modal: true
                });
            });
        </script>-->

       </head>
    <body>
             <?php error_reporting(0);
             if(isset($_POST['zakonczszkolenie'])){
                 $_SESSION['testrozpoczety'] = "tak";
                 $url = 'test.php';
                 header("Location: $url");
                 exit();  
             }
             require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/NextslideSzkolenie.php'); 
             if(isset($_POST['nextszkolenie'])){
                 NextslideSzkolenie::next();
             } else if (isset($_POST['backszkolenie'])){
                 NextslideSzkolenie::back();
             } else {
                 NextslideSzkolenie::init();
             }
            // var_dump(NextslideSzkolenie::$test1[NextslideSzkolenie::$ilosc]);
            ?>
             
              
            <div style="background-image: 'resources/pictures/e-kursscreen.jpg'; margin-top: 20%; margin-left: 15%; width: 60%; padding: 50px; border-radius: 15px; box-shadow: 10px 10px 5px #888;">
                <div style="height: 35px; width: 715px; display: table-cell; border-radius: 15px; vertical-align: middle; text-align: center; alignment-adjust: central; ">
                    <h2>"Szkolenie z ochrony danych osobowych. Slide  <?php error_reporting(0); echo $_SESSION['ilosc']+1?> z <?php error_reporting(0); echo $_SESSION['szkoleniesize']?>"</h2>
                </div>
                <img src="/resources/pictures/e-kursscreen.jpg" alt="Klematis" width="110" height="90">
            <form id="form" action="" method="post">
                 <div style="width: 700px; height: 50px;">
                    <span><?php error_reporting(0); echo NextslideSzkolenie::$szkolenie[NextslideSzkolenie::$ilosc]['naglowek'];?></span>
                </div>
                 <div style="width: 700px; height: 50px;">
                    <span><?php error_reporting(0); echo NextslideSzkolenie::$szkolenie[NextslideSzkolenie::$ilosc]['temat'];?></span>
                </div>
                <div style="width: 700px; height: 270px;">
                    <span><?php error_reporting(0); echo NextslideSzkolenie::$szkolenie[NextslideSzkolenie::$ilosc]['tresc'];?></span>
                </div>
                <div style="width: 700px; height: 30px; text-align: right; font-weight: 900; font-size: larger;">
                    <span id="koniecszkolenia" style="display: none; ">Zakończyłeś szkolenie. Możesz rozpocząć test. W tym celu kliknij poniżej.</span>
                </div>
                <input id="backszkolenie" name="backszkolenie" value="poprzedni" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
                <input id="nextszkolenie" name="nextszkolenie" value="następny"  type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 4%; float: right;">
                <input id="zakonczszkolenie" name="zakonczszkolenie" value="test"  type="submit" style="padding: 5px; display: none; padding: 10px; width: 120px; margin-left: 4%; float: right;">
                  <script>
                    (function(){
                        var nrkolejny = <?php error_reporting(0); echo $_SESSION['ilosc']+1?>;
                        var max = <?php error_reporting(0); echo $_SESSION['szkoleniesize']?>;
                        if ( nrkolejny < max ){
                            $('#nextszkolenie').show();
                        }
                        if ( nrkolejny > 1 ){
                            $('#backszkolenie').show();
                        } else {
                            $('#backszkolenie').hide();
                        }
                         if ( nrkolejny === max ){
                            $('#zakonczszkolenie').show();
                            $('#nextszkolenie').hide();
                            $('#koniecszkolenia').show();
                        } else {
                            $('#nextszkolenie').show();
                            $('#zakonczszkolenie').hide();
                            $('#koniecszkolenia').hide();
                        }
                    }());
                     </script>
            </form>
        </div>
    </body>
</html>
