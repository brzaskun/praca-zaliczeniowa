<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['Nid'];
  $nazwazakladu = $_POST['Nnazwazakladu'];
  $ulica = $_POST['Nulica'];
  $miejscowosc = $_POST['Nmiejscowosc'];
  $progzdawalnosci = $_POST['Nprogzdawalnosci'];
  $kontakt = $_POST['Nkontakt'];
  $email = $_POST['Nemail'];
  $maxpracownikow = $_POST['Nmaxpracownikow'];
  $managerlimit = $_POST['Nmanagerlimit'];
  $sql = "INSERT INTO  `zakladpracy` (`id` ,`nazwazakladu` ,`ulica` ,`miejscowosc`,`progzdawalnosci`, `kontakt`, `maxpracownikow`, `managerlimit`, `email`) VALUES ('$id', '$nazwazakladu',  '$ulica', '$miejscowosc', '$progzdawalnosci', '$kontakt', '$maxpracownikow', '$managerlimit', '$email');";
  try {
    R::exec($sql);
    echo R::getCell("SELECT `id` FROM  `zakladpracy` WHERE  (`nazwazakladu` = '$nazwazakladu')");
  } catch (Exception $ex) {
  }
?>
