<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $sql = "SELECT * FROM uczestnicy"; 
  $wynik = R::getAll($sql);
//  $output = array();
//  foreach ($maile as $val) {
//      array_push($output, array_shift($val));
//  }
  echo json_encode($wynik);
?>
 