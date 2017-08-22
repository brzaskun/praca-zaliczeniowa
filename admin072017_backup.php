<!DOCTYPE html>
<html lang="pl">
    <meta charset="utf-8">
    <?php error_reporting(0);
    if(session_status()!=2){     session_start(); };
    if ($_SESSION['uczestnik']['uprawnienia'] != "admin") {
        die("Nie jesteś upoważniony do przeglądania zasobu");
    }
    ?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/primeui.min.css?v180817c"/>
        <link href="resources/font-awesome/css/font-awesome.min.css?v180817c" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/themes/bootstrap/theme.css?v180817c"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v180817c"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v180817c"></script>
        <script src="/resources/js/jquery.form.js?v180817c"></script>
        <script src="/resources/js/globales.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v180817c"></script>
        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/potwierdzenia.js?v180817c"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v180817c"></script>
        <script src="/resources/js/admin_072017_backup.js?v180817c"></script>
        <title>Testy Dane Wrażliwe</title>
        <style>
            .center {
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%); /* Yep! */
                width: 48%;
                height: 59%;
              }
        </style>
      </head>
    <body>
        <?php
            include_once './menu_backup.php';
        ?>
        <div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
            <form >
                <button id='potwierdz' type='button' value="archiwizuj" onclick="archiwizuj();" style="width: 120px; margin-top: 1%; margin-left: 3%;">archiwizuj</button>
            </form>
            <p id="pole1"  style="display: none; color: blue; margin-left: 3%; font-weight: 900;">Rozpoczynam archiwizację</p>
            <p id="pole2"  style="display: none; color: blue; margin-left: 3%; font-weight: 900;">Zakończyłem archiwizację</p>
            <div id="ajax_sun" title="archiwizowanie" style="display: none; text-align: center;" class="center">;
            <img src="/images/ajax_loaderc.gif" alt="ajax" height="70" width="70">;
        </div>
        
        </div>
    </body>
</html>
