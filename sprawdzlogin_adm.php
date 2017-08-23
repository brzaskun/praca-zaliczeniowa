<?php error_reporting(0);
if(session_status()!=2){     session_start(); };
$sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
require_once($sciezkaroot.'/resources/php/Rb.php');
require_once($sciezkaroot.'/resources/php/Nextslide.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane','tb152026_madrylo','Testdane7005*');
$mail = $_GET['mail']; 
$_COOKIE['mail'] = $mail;
setcookie("mail", $mail, time()+3600);
$parametr = "email = '$mail'";
$uczestnik = R::findOne('uczestnicy', $parametr);
if (!isset($uczestnik)) {
    $url = 'index.php';
    header("Location: $url"); 
    $_SESSION['wyjdz'] = 'tak';
    exit();
} else {
    $_SESSION['uczestnik'] = $uczestnik->getProperties();
} 
//admina trzeba od razu przerzucic do jego zakladki
$wyniksprawdzaniahasla = $_GET['wynik'];
if ($_SESSION['uczestnik']['uprawnienia']=="admin"){
    if ($wyniksprawdzaniahasla == 1) {
        $url = 'admin112014_uzytkownicy.php';
        header("Location: $url");
        exit();
    } else {
        $url = 'index.php';
        header("Location: $url");
        $_SESSION['wyjdz'] = 'tak';
        exit();
    }
}
?>

