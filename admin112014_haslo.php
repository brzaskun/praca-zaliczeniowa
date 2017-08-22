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
        
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
         <link href="resources/dataTableNew/extensions/KeyTable/css/keyTable.dataTables.css?v180817c" rel="stylesheet" type="text/css"/>
        <link href="resources/dataTableNew/extensions/Select/css/select.dataTables.css?v180817c" rel="stylesheet" type="text/css"/>
        <link href="resources/dataTableNew/extensions/Buttons/css/buttons.dataTables.css?v180817c" rel="stylesheet" type="text/css"/>
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
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v180817c"></script>
        <script src="resources/dataTableNew/extensions/KeyTable/js/dataTables.keyTable.js?v180817c" type="text/javascript"></script>
        <script src="resources/dataTableNew/extensions/Select/js/dataTables.select.js?v180817c" type="text/javascript"></script>
        <script src="resources/dataTableNew/extensions/Buttons/js/dataTables.buttons.js?v180817c" type="text/javascript"></script>
        <script src="resources/jszip.min/jszip.min.js?v180817c" type="text/javascript"></script>
        <script src="resources/PDFMAKE_files/pdfmake.min.js?v180817c" type="text/javascript"></script>
        <script src="resources/PDFMAKE_files/vfs_fonts.js?v180817c" type="text/javascript"></script>
        <script src="resources/dataTableNew/extensions/Buttons/js/buttons.html5.min.js?v180817c" type="text/javascript"></script>
<!--        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/potwierdzenia.js?v180817c"></script>-->
        <script src="/resources/js/ciasteczka.js?v180817c"></script>
        <script src="/resources/ckeditor/ckeditor.js?v180817c"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v180817c"></script>
        <script src="/resources/js/admin_112014_haslo.js?v180817c"></script>
        <title>Testy Dane Wrażliwe</title>
        <script>
              $(document).ready(function() {
                $('#notify').puigrowl({
                    life: 6000
                });
            });
        </script>

    </head>
    <body>
        <?php
            include_once './menu_haslo.php';
        ?>
        <div id="notify"></div>
        <div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
            <p style="font-weight: 900;">Formularz zmiany hasła dostępu do aplikacji</p>
            <form >
                <label for="haslo" style="display: block; width: 170px;">wpisz nowe, 8 znakowe, hasło</label>
                <input id='haslo1' maxlength="8" min="5" max="8"/><br/>
                <label for="haslo" style="display: block; width: 170px;">powtórz nowe, 8 znakowe, hasło</label>
                <input id='haslo2' maxlength="8" min="5" max="8"/><br/>
                <input id='potwierdz' type='button' value="zmień" onclick="przetworzhaslo();" style="width: 120px;display: block; margin-top: 1%; margin-left: 3%;"/>
                <p id="potwierdzeniezmiany" style="display: none; color: blue; margin-left: 3%; font-weight: 900;">Sukces! Hasło zmienione.</p>
            </form>
        </div>
       
    </body>
</html>
