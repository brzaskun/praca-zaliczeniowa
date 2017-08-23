<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['idzaklad'];
  $nazwazakladu = $_POST['nazwazakladu'];
  $ulica = $_POST['ulica'];
  $miejscowosc = $_POST['miejscowosc'];
  $progzdawalnosci = $_POST['progzdawalnosci'];
  $firmaaktywna = '0';
  $kontakt = $_POST['kontakt'];
  $email = $_POST['email'];
  $maxpracownikow = $_POST['maxpracownikow'];
  $managerlimit = $_POST['managerlimit'];
  if ($_POST['firmaaktywna'] == 'aktywna') {
    $firmaaktywna = '1';
  }
  $sql = "UPDATE  `zakladpracy` SET  `ulica` = '$ulica', `miejscowosc` = '$miejscowosc', `progzdawalnosci`='$progzdawalnosci' ,"
          . " `firmaaktywna` = '$firmaaktywna', `kontakt` = '$kontakt', `email` = '$email', `maxpracownikow` = $maxpracownikow, `managerlimit` = $managerlimit"
          . " WHERE  `zakladpracy`.`nazwazakladu` = '$nazwazakladu'"; 
  R::exec($sql);
?>
