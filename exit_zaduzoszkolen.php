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
            <div id="gornawklejka">
                <span>szkolenie z zakresu ochrony danych osobowych</span>
            </div>
            <div id="szkolenienaglowek">
                <h2></h2>
            </div>
            <form id="form" action="" method="post" >
                <div class="odpowiedzitest">
                    <p>Przekroczono liczbę dopuszczalnych logowań do serwisu szkoleń.</p>
                    <p>Zgodnie z regulaminem szkoleń do serwisu można logować się maksymalnie 4 razy.</p>
                    <p>Pierwsze zalogowanie z użyciem adresu <?php error_reporting(0); if(session_status()!=2){     session_start(); }; echo $_SESSION['uczestnik']['email'];?> miało miejsce
                        <?php error_reporting(0);  echo $_SESSION['uczestnik']['sessionstart'];?>.</p><br/>
                        
                    <span>Jeżeli dodatkowe logowania nastąpiły omyłkowo prosimy, w celu reaktywacji konta, skontaktować się z osobą odpowiedzialną za szkolenia.</span><br/>
                    <p> W twojej firmie jest to: <?php error_reporting(0); echo $_SESSION['uczestnik']['kontakt'];?>.</p>
                    
                </div>
            </form>
         </div>
</div>
    </body>
</html>
