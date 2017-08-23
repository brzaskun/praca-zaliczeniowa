<?php 
error_reporting(1);
if (session_status() != 2) {
    session_start();
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$id = $_POST['iduser'];
$email = $_POST['email'];
$imienazwisko = $_POST['imienazwisko'];
$sessionstart = NULL;
if ($_POST['datazalogowania'] == "") {
    $sessionstart = NULL;
} else {
    $sessionstart = $_POST['datazalogowania'];
}
$nazwaszkolenia = $_POST['szkolenieuser'];
$uprawnienia = $_POST['uprawnieniauser'];
$firma = $_POST['firmausernazwa'];
$plec = $_POST['plecuser'];
$nic = NULL;
$zwrot = 0;
date_default_timezone_set('Europe/Warsaw');
$czasbiezacy = date("Y-m-d H:i:s");
try {
    //musza byc dwa bo wpisywal 0 a nie null jak bylo nic w stringu a musi byc null bo tylko wtedy generuje rozpoczecie sesji
    $sql = "UPDATE  `uczestnicy` SET  `email` =  '$email', `imienazwisko` =  '$imienazwisko', `plec` = '$plec', `firma` = '$firma', `nazwaszkolenia` = '$nazwaszkolenia', `sessionstart` = '$sessionstart' , `uprawnienia`='$uprawnienia', zmodyfikowany = '$czasbiezacy' WHERE  `uczestnicy`.`id` = '$id';";
    $sqlnull = "UPDATE  `uczestnicy` SET  `email` =  '$email', `imienazwisko` =  '$imienazwisko', `plec` = '$plec', `firma` = '$firma', `nazwaszkolenia` = '$nazwaszkolenia', `sessionstart` = NULL, `uprawnienia`='$uprawnienia', zmodyfikowany = '$czasbiezacy' WHERE  `uczestnicy`.`id` =  '$id'";
    R::exec($sessionstart == null ? $sqlnull : $sql);
} catch (Exception $e) {
    $zwrot = 1;
}
echo $zwrot;
?>