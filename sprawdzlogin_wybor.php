<?php
error_reporting(2);
$sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
require_once($sciezkaroot . '/resources/php/Rb.php');
require_once($sciezkaroot . '/resources/php/Zerowanieciastek.php');
//inicjujemy clase do lazczenia sie z baza danych
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
if (session_status() != 2) {
    session_start();
}
$mail = $_SESSION['mail'];
$parametr = "email = '$mail'";
$uczestnicy = R::findAll('uczestnicy', $parametr);
$szkolenianowe = array();
$szkoleniazdane = array();
foreach (array_values($uczestnicy) as $val) {
    if ($val->wyslanycert == 1) {
        $szkoleniazdane[$val->nazwaszkolenia] = $val->id;
    } else {
        $szkolenianowe[$val->nazwaszkolenia] = $val->id;
    }
}
//if (isset($_GET['mail'])) {
//    $mail = filter_input(INPUT_GET, 'mail', FILTER_VALIDATE_EMAIL);
//    $_SESSION['mail'] = $mail;
//    $url = 'sprawdzlogin.php';
//    header("Location: $url");
//    exit();
//}
?>
<!DOCTYPE html>
<html lang="pl">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="cache-control" content="max-age=0" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
        <meta http-equiv="pragma" content="no-cache" />
        <link rel="stylesheet" href="/resources/dataTable/start/jquery-ui-1.10.3.custom.css?v180817c" />
        <link rel="stylesheet" href="/resources/dataTableNew/media/css/jquery.dataTables.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/tablecss.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/main.css?v180817c"/>
        <link rel="stylesheet" href="/resources/css/details.css?v180817c"/>
        <link rel="stylesheet" href="/resources/contextmenu/jquery.contextMenu.css?v180817c"/>
        <link rel="stylesheet" href="/resources/primeui-4.1.12/primeui.min.css?v180817c"/>
        <link rel="icon" type="image/png" sizes="32x32" href="/resources/css/images/ODOLogoVector.png"/>
        <script src="/resources/js/jquery-1.12.3.js?v180817c"></script>
        <script src="/resources/dataTable/jquery-ui-1.10.3.custom.js?v180817c"></script>
        <script src="/resources/js/jquery.form.js?v180817c"></script>
        <script src="/resources/dataTableNew/media/js/jquery.dataTables.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.contextMenu.js?v180817c"></script>
        <script src="/resources/contextmenu/jquery.ui.position.js?v180817c"></script>
        <script src="/resources/js/main.js?v180817c"></script>
        <script src="/resources/js/globales.js?v180817c"></script>
        <script src="/resources/js/ciasteczka.js?v180817c"></script>
        <script src="/resources/primeui-4.1.12/primeui.min.js?v180817c"></script>
        <!--[if lt IE 9]>
                 <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?v180817c"></script>
         <![endif]-->
        <title>Wybór szkolenia</title>
        <script>
            var odhaczinne = function (obj, iduczestnika) {
                var par = $(obj).closest("tr");
                var czekboxy = $(par).siblings();
                $.each(czekboxy, function () {
                    if (this !== par) {
                        $(this).find(".czekbox").attr('checked', false);
                    }
                });
                var ciastko = new Cookie("iduczestnika");
                ciastko.value = iduczestnika;
                ciastko.save();
                $("#buttonlogowanie").show();
                $("#wyborszkoleniainfo").hide();
            }
        </script>
    </head>
    <body>
        <div class="box">
            <div class="slajd">
                <div id="loginnaglowek">
                    <h2>Twoje szkolenia</h2>
                </div>
                <div style="text-align: center;">
                    <h2>Przygotowano dla Ciebie więcej niż jedno szkolenie.</h2>
                    <h2>Wybierz, które chcesz rozpocząć.</h2>
                </div>
                <div style="padding: 10px; margin-left: 25%;">
                    <form id="loginform">
                        <table>
                            <?php
                            foreach ($szkolenianowe as $key => $value) {
                                echo "<tr>";
                                echo "<td style='font-size: larger'><input type='checkbox' class='czekbox' onclick='odhaczinne(this,\"$value\")'></input><span>$key - nowe, rozpocznij szkolenie</span></td>";
                                echo "</tr>";
                            }
                            foreach ($szkoleniazdane as $key => $value) {
                                echo "<tr>";
                                echo "<td style='font-size: larger'><input type='checkbox' class='czekbox' onclick='odhaczinne(this,\"$value\")'></input><span>$key - zdane, pobierz certyfikat</span></td>";
                                echo "</tr>";
                            }
                            ?>
                        </table>
                        <button id="buttonlogowanie" type="submit"  title="Kliknij celem rozpoczęcia szkolenia" 
                                formaction="sprawdzlogin_1.php" formmethod="POST" style="display: none;">
                            <span class="spanszkolenie" >wybierz</span>
                        </button>
                    </form>
                </div>
                <div class="margin2" style="height: 10px; text-align: left;">
                    <span id="wiadomoscajax"></span>
                </div>
                <div class="margin1" style="margin-top: 5%; text-align: left;">
                    <span id="wyborszkoleniainfo">Szkolenie rozpoczyna się pod warunkiem wybrania jednego szkolenia z wielu</span>
                </div>
            </div>
        </div>
        <div id="notify"></div>
    </body>
</html>
