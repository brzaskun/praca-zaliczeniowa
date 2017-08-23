<!DOCTYPE html>
<html lang="pl">
<?php error_reporting(2);
if(session_status()!=2){     
    session_start(); 
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/UpowaznienieGenerowanie.php');
UpowaznienieGenerowanie::generuj();
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
        <script src="/resources/js/main.js?v220817a"></script>
        <script src="/resources/js/ciasteczka.js?v220817a"></script>
        <title>Testy Dane Wrażliwe</title> 
       </head>
    <body>
        <div class="box">
            <div class="slajd">
            <div id="testnaglowek">
                    <h2>Wygenerowano upoważnienie</h2>
                </div>
                <div class="pytanietest">
                    <p style="font-size: 150%; margin-left: 3%"> Gratulujemy ukończenia szkolenia przygotowanego przez</p>
                    <p style="font-size: 150%; margin-left: 3%"> ODO Management Group.</p>
                    <div style="padding: 3%; padding-top: 1%">
                        <p>Na adres email, którym logowaleś się do systemu e-szkoleń zostało właśnie wysłane właściwe upoważnienie.</p>
                    </div>
                    <div style="padding: 3%;">
                        <p>Zespół ODO Management Group</p> 
                    </div>
                    
                </div>
            
        </div>
        </div>
<?php error_reporting(2);
if(session_status()!=2){     
    session_start(); 
};
$_SESSION['szkolenietrwa'] = "nie";
$_SESSION['testrozpoczety']= "nie";
// Na koniec zniszcz sesję
$_SESSION = array();
session_destroy();
?>
    </body>
</html>