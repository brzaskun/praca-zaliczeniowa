<?php error_reporting(0);
if(session_status()!=2){     session_start(); };

if (isset($_POST['zakonczszkolenie'])) {
    $_SESSION['testrozpoczety'] = "tak";
    $url = 'test.php';
    header("Location: $url");
    exit();
}
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/NextslideSzkolenie.php');
if (isset($_POST['nextszkolenie'])) {
    NextslideSzkolenie::next();
} else if (isset($_POST['backszkolenie'])) {
    NextslideSzkolenie::back();
} else {
    NextslideSzkolenie::init();
}
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
        <!--[if lt IE 9]>
		<script src="https://html5shim.googlecode.com/svn/trunk/html5.js?v180817c"></script>
	<![endif]-->
        <title>Testy Dane Wrażliwe</title>

    </head>
    <body>



<div class="box">
        <div class="slajd">
    <?php 
    if(session_status()!=2){     session_start(); };
//    
    if (!isset($_SESSION['szkolenietrwa'])) {
        die("<div id='gornawklejka'><span>prosimy nie używać przycisku powrotu w przeglądarce!</span>
            </div><div id='szkolenienaglowek'><h2>Rozpoczęto już test, nie można wrócić do szkolenia</h2></div>
            <div style='width: 700px; height: 40px; padding: 10px; margin-left: auto; margin-right: auto; font-size: larger;'>
                    <form id='form' action='' method='post' >
                    <button id='zalogujponownie' name='zalogujponownie' class='buttonszkolenie' type='submit' formaction='index.php' style='float: left;' title='Powrót do strony logowania'><span class='spanszkolenie'>login</span></button>
                    </form>
            </div></div></body></html>
            ");
    }
    if ($_SESSION['szkolenietrwa'] != "tak") {
        $_SESSION['testrozpoczety'] = "nie";
        die("<div id='gornawklejka'><span>prosimy nie używać przycisku powrotu w przeglądarce!</span>
            </div><div id='szkolenienaglowek'><h2>Rozpoczęto już test, nie można wrócić do szkolenia</h2></div>
            <div style='width: 700px; height: 40px; padding: 10px; margin-left: auto; margin-right: auto; font-size: larger;'>
                    <form id='form' action='' method='post' >
                    <button id='zalogujponownie' name='zalogujponownie' class='buttonszkolenie' type='submit' formaction='index.php' style='float: left;' title='Powrót do strony logowania'><span class='spanszkolenie'>login</span></button>
                    </form>
            </div></div></body></html>
            ");
    }
    ?>
            <form id="form" action="" method="post">
            <div id="gornawklejka">
                <span style="font-size: larger;"><?php  echo NextslideSzkolenie::$opisszkolenia; ?></span> 
            </div>
            <div id="szkolenienaglowek">
                <h2><?php  echo NextslideSzkolenie::$szkolenie[NextslideSzkolenie::$ilosc]['naglowek']; ?></h2>
            </div>
            
                <div class="trescszkolenia" style="margin-top: 10px;">
                    <span><?php  echo NextslideSzkolenie::$szkolenie[NextslideSzkolenie::$ilosc]['tresc']; ?></span>
                </div>
                <div class="zakonczylesszkolenie">
                    <span id="koniecszkolenia" style="display: none; ">Zakończyłeś szkolenie. Możesz rozpocząć test. W tym celu kliknij poniżej.</span>
                </div>
                <div class="dolneprzyciski" >
                    <button id="backszkolenie" name="backszkolenie" class="buttonszkolenie"  type="submit" title="Powrót do poprzedniej strony szkolenia"><span class="spanszkolenie">wróć</span></button>
                    <button id="nextszkolenie" name="nextszkolenie" class="buttonszkolenie" type="submit" style="float: right;" title="Przejście do kolejnej strony szkolenia"><span class="spanszkolenie">dalej</span></button>
                    <button id="zakonczszkolenie" name="zakonczszkolenie" class ="buttonszkolenie" type="submit" style="float: right;" title="Definitywne zakończenie szkolenia i rozpoczęcie testu"><span class="spanszkolenie">test</span></button>
                </div>
                <script>
                    (function() {
                        var nrkolejny = <?php  echo $_SESSION['ilosc'] + 1 ?>;
                        var max = <?php  echo $_SESSION['szkoleniesize'] ?>;
                        if (nrkolejny < max) {
                            $('#nextszkolenie').show();
                        }
                        if (nrkolejny > 1) {
                            $('#backszkolenie').show();
                        } else {
                            $('#backszkolenie').hide();
                        }
                        if (nrkolejny === max) {
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
</div>
    </body>
</html>
