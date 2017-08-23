<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $sql = "SELECT zakladpracy.nazwazakladu FROM zakladpracy";
  $nazwyzakladu = R::getAll($sql);
  $output = "";
  foreach ($nazwyzakladu as $val) {
      $output = $output.",".array_shift($val);
  }
  echo $output;
?>
