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
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/primeui.min.css?v180817c"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/themes/bootstrap/theme.css?v180817c"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v180817c"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v180817c"></script>
        <script src="/resources/js/jquery.form.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v180817c"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v180817c"></script>
        <script src="/resources/js/globales.js?v180817c"></script>
        <script src="/resources/js/fileupload.js?v180817c"></script>
        <script src="/resources/js/admin_uzytkownik_grupa_uploadfile.js?v180817c"></script>
<!--        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/potwierdzenia.js?v180817c"></script-->
        <script src="/resources/js/ciasteczka.js?v180817c"></script>
        <script src="/resources/ckeditor/ckeditor.js?v180817c"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v180817c"></script>
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
