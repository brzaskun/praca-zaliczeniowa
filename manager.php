
<!DOCTYPE html>
<html lang="pl">
    <?php error_reporting(0);
    if (session_status() != 2) {
        session_start();
    };
    if ($_SESSION['uczestnik']['uprawnienia'] != "manager") {
        die("Nie jesteś upoważniony do przeglądania zasobu");
    }
    if (isset($_SESSION['uczestnik']['sessionstart'])) {
        date_default_timezone_set('Europe/Warsaw');
        $czasbiezacy = date("Y-m-d H:i:s");
        $czasrozpoczecia = $_SESSION['uczestnik']['sessionstart'];
        $datetime1 = new DateTime($czasrozpoczecia);
        $datetime2 = new DateTime($czasbiezacy);
        $intervald = $datetime1->diff($datetime2)->d;
        if ($intervald > $_SESSION['managerlimit']) {
            $url = 'exit_mineladoba_manager.php';
            header("Location: $url");
            exit();
        }
    }
    ?>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
        <script src="/resources/js/jquery-1.12.3.js?v180817c"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v180817c"></script>
        <script src="/resources/jquery-cookie/jquery.cookie.js?v180817c"></script>
        <script src="/resources/js/jquery.form.js?v180817c"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v180817c"></script>
        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/ciasteczka.js?v180817c"></script>
        <script src="/resources/js/potwierdzenia.js?v180817c"></script>
        <script src="/resources/ckeditor/ckeditor.js?v180817c"></script>

        <script src="/resources/js/managerscript.js?v180817c"></script>

        <title>Szkolenie Dane Osobowe - Panel Menadżera</title>
        <script>
            $(document).ready(function() {
                    managerinit();
                    var kliknij = '<?php error_reporting(0); if (isset($_GET['zaladowany'])) { echo $_GET['zaladowany'];} ?>';
                    if (kliknij==='tak') {
                        $('#importbutton').click();
                    }
            });
        </script>
    </head>
    <body>
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Tabela użytkowników</a></li>
                <li><a id="importbutton" href="#tabs-2">Import z pliku</a></li>
            </ul>
            <div>
                <div style="height: 25px; box-shadow: 10px 10px 5px #888; vertical-align: central;  font-family: Verdana, Arial, Helvetica, sans-serif; background-color: gainsboro; vertical-align: middle;">
                    <h2 id="wiadomoscusererror" style="color: red; text-align: center; vertical-align: middle;"><?php error_reporting(0);
                        if (isset($_GET['error'])) {
                            echo $_GET['error'];
                        }
                        ?></h2>
                    <h2 id="wiadomoscuserinfo" style="color: #0078ae; text-align: center; vertical-align: middle;"><?php error_reporting(0);
                        if (isset($_GET['info'])) {
                            echo $_GET['info'];
                        }
                        ?></h2>
                    <h2 id="wiadomoscuserjs" style="color: #0078ae; text-align: center; vertical-align: middle;"> </h2>
                </div>
            </div>
            <div id="tabs-1">
                <p>
                    <?php error_reporting(0);
                    include_once "manager_uzytkownicy.php";
                    ?>
                </p>
            </div>
            <div id="tabs-2">
                <p>
                    <?php error_reporting(0);
                    include_once "manager_plik.php";
                    ?>
                </p>
            </div>
        </div>
    </body>
</html>
