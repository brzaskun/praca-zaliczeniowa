<?php

error_reporting(0);
if (session_status() != 2) {
    session_start();
};
$sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
require_once($sciezkaroot . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$iduczestnika = $_COOKIE['iduczestnika'];
setcookie("iduczestnika", "", time() - 3600);
$mail = "";
if (isset($_SESSION['automail'])) {
    $mail = $_SESSION['automail'];
    $_SESSION['automail'] = null;
} else {
    $mail = $_POST['mail'];
}
$_COOKIE['mail'] = $mail;
setcookie("mail", $mail, time() + 3600);
$parametr = "email = '$mail'";
$uczestnik = R::findAll('uczestnicy', $parametr);
if (isset($iduczestnika)) {
    $parameter = "id='$iduczestnika'";
    $znaleziony_ucz = R::findOne('uczestnicy', $parameter);
    $_SESSION['uczestnik'] = $znaleziony_ucz->getProperties();
    $url = 'sprawdzlogin_2.php';
    header("Location: $url");
    exit();
} else if (sizeof($uczestnik) == 0) {
    $url = 'index.php';
    header("Location: $url");
    $_SESSION['wyjdz'] = 'tak';
    exit();
} else if (sizeof($uczestnik) == 1) {
    $_SESSION['uczestnik'] = array_values($uczestnik)[0]->getProperties();
    $url = 'sprawdzlogin_2.php';
    header("Location: $url");
    exit();
} else {
    $_SESSION['mail'] = $mail;
    $url = 'sprawdzlogin_wybor.php';
    header("Location: $url");
    exit();
}
?>
