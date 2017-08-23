<?php

error_reporting(2);
if (session_status() != 2) {
    session_start();
};
$sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
require_once($sciezkaroot . '/resources/php/Rb.php');
require_once($sciezkaroot . '/resources/php/Nextslide.php');
require_once($sciezkaroot . '/resources/php/PobierzIP.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$sqlfirma = $_SESSION['uczestnik']['firma'];
$sql = "SELECT `firmaaktywna` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$firmaaktywna = R::getCell($sql);
$sql = "SELECT `progzdawalnosci` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$_SESSION['progzdawalnosciuczestnik'] = R::getCell($sql);
$sql = "SELECT `maxpracownikow` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$_SESSION['maxpracownikow'] = R::getCell($sql);
$sql = "SELECT `managerlimit` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$_SESSION['managerlimit'] = R::getCell($sql);
$sql = "SELECT `kontakt` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$_SESSION['uczestnik']['kontakt'] = R::getCell($sql);
$nazwaszkolenia = $_SESSION['uczestnik']['nazwaszkolenia'];
$sql = "SELECT `email` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
$_SESSION['uczestnik']['BCC'] = R::getCell($sql);
$sql = "SELECT `iloscpytan` FROM `szkolenieust` WHERE `szkolenieust`.`firma`='$sqlfirma' AND `szkolenieust`.`nazwaszkolenia`='$nazwaszkolenia';";
$_SESSION['uczestnik']['iloscpytan'] = R::getCell($sql);

$ilosclogowan = $_SESSION['uczestnik']['ilosclogowan'];
//czas sesji jest potrzebny i dla managera i dla usera 
date_default_timezone_set('Europe/Warsaw');
$czasbiezacy = date("Y-m-d H:i:s");
$id = $_SESSION['uczestnik']['id'];

$ip = PobierzIp::getClientIP(true);
R::exec("UPDATE  `uczestnicy` SET  `iplogowania`='$ip' WHERE  `uczestnicy`.`id` = '$id';");
if (!isset($_SESSION['uczestnik']['sessionstart'])) {
    //rejestrowanie pierwszego zalogowania
    R::exec("UPDATE  `uczestnicy` SET  `sessionstart`='$czasbiezacy' WHERE  `uczestnicy`.`id` = '$id';");
    $_SESSION['uczestnik']['sessionstart'] = $czasbiezacy;
}
//managera tez przerzucamy od razu do jego zakladki :)
if ($_SESSION['uczestnik']['uprawnienia'] == "manager") {
    $url = 'manager.php';
    header("Location: $url");
    exit();
}

//jezeli uczestnik jest z firmy nieaktywnej to przekieruj na specjalny slide
if ($firmaaktywna==0) {
    $url = 'exit_.php';
    header("Location: $url");
    $_SESSION['wyjdz'] = 'tak';
    exit();
}

//jezeli uczestnik zdal test to nie ma sensu robic innych rzeczy tylko przekierowac go na strone wynik testu. moze chce sobie przypomniec chwile chwaly
//lub pobrac certyfikat
if (isset($_SESSION['uczestnik']['sessionend'])) {
    $url = 'exit_zdanytest.php';
    header("Location: $url");
    $_SESSION['wyjdz'] = 'tak';
    exit();
}
if (isset($_SESSION['uczestnik']['sessionstart'])) {
    $czasrozpoczecia = $_SESSION['uczestnik']['sessionstart'];
    $datetime1 = new DateTime($czasrozpoczecia);
    $datetime2 = new DateTime($czasbiezacy);
    $intervald = $datetime1->diff($datetime2)->d;
    if ($intervald > 0) {
        $url = 'exit_mineladoba.php';
        header("Location: $url");
        exit();
    }
}
//robi update ilosci logowan

if ($_SESSION['uczestnik']['uprawnienia'] != "admin") {
    if ($ilosclogowan > 3) {
        $ilosclogowan++;
        R::exec("UPDATE  `uczestnicy` SET `ilosclogowan`='$ilosclogowan', `dataostatniegologowania`='$czasbiezacy' WHERE  `uczestnicy`.`id` = '$id';");
        $_SESSION['test'] = null;
        $url = 'exit_zaduzoszkolen.php';
        header("Location: $url");
        exit();
    } else {
        $ilosclogowan++;
        R::exec("UPDATE  `uczestnicy` SET `ilosclogowan`='$ilosclogowan', `dataostatniegologowania`='$czasbiezacy' WHERE  `uczestnicy`.`id` = '$id';");
        $_SESSION['test'] = null;
        $_SESSION['szkolenietrwa'] = "tak";
        $url = 'szkolenie.php';
        header("Location: $url");
        exit();
    }
}
?>

