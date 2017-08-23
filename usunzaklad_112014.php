<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $nazwazakladu = $_POST['nazwazakladu'];
  $sauzytkownicy = "";
  $sql = "SELECT * FROM uczestnicy WHERE firma = '$nazwazakladu'" ;
  $sauzytkownicy = R::getAll($sql);
  if ($sauzytkownicy[0] != "") {
      echo "tak";
  } else {
      $sql = "DELETE FROM `zakladpracy` WHERE `nazwazakladu`= '$nazwazakladu'";
      R::exec($sql);
      echo "nie";
  }
  
?>
