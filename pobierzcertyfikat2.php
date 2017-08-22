<!DOCTYPE html>
<html lang="pl">
<?php error_reporting(0);
if(session_status()!=2){     session_start(); };
$_SESSION['szkolenietrwa'] = "nie";
$_SESSION['testrozpoczety']= "nie";
// Na koniec zniszcz sesję
$_SESSION = array();
session_destroy();
?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/details.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
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
                    <h2>Zaświadczenie wysłane</h2>
                </div>
                <div class="pytanietest">
                    <p style="font-size: 150%; margin-left: 3%"> Gratulujemy ukończenia szkolenia przygotowanego przez</p>
                    <p style="font-size: 150%; margin-left: 3%"> ODO Management Group.</p>
                    <div style="padding: 3%; padding-top: 1%">
                        <p>Na adres email, którym logowaleś się do systemu e-szkoleń zostało właśnie wysłane zaświadczenie potwierdzające zaliczenie testu.</p>
                    </div>
                    <div style="padding: 3%;">
                        <p>Dziękujemy za skorzystanie z naszego systemu e-szkoleń.</p>
                        <p>Zespół ODO Management Group</p> 
                    </div>
                </div>
            
        </div>
        </div>
    </body>
</html>