<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $sql = "SELECT nazwazakladu FROM zakladpracy WHERE firmaaktywna = 1 ORDER BY nazwazakladu";
  $maile = R::getAll($sql);
  $output = array();
  array_push($output, "wybierz bieżącą firmę");
  foreach ($maile as $val) {
      array_push($output, array_shift($val));
  }
  echo json_encode($output);
?>
 