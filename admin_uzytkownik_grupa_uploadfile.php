<!DOCTYPE html>
<html lang="pl">
    <?php error_reporting(0);
    if(session_status()!=2){     session_start(); };
    if ($_SESSION['uczestnik']['uprawnienia'] != "admin") {
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
        <link rel="stylesheet" href="/resources/primeui-4.1.12/primeui.min.css?v220817a"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/themes/bootstrap/theme.css?v220817a"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v220817a"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v220817a"></script>
        <script src="/resources/js/jquery.form.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v220817a"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v220817a"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v220817a"></script>
        <script src="/resources/js/globales.js?v220817a"></script>
        <script src="/resources/js/fileupload.js?v220817a"></script>
        <script src="/resources/js/admin_uzytkownik_grupa_uploadfile.js?v220817a"></script>
<!--        <script src="/resources/js/main.js?v220817a"></script>
        <script src="/resources/js/potwierdzenia.js?v220817a"></script-->
        <script src="/resources/js/ciasteczka.js?v220817a"></script>
        <script src="/resources/ckeditor/ckeditor.js?v220817a"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v220817a"></script>
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
            include_once './menu_upload_file.php';
        ?>
        <div id="notify"></div>
        <?php
            include_once './admin_uzytkownik_grupa_plik.php'; 
        ?>
    </body>
</html>
