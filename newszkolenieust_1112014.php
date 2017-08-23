<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $firmaszkolenia = filter_input(INPUT_POST, 'Nfirmaszkoleniaust', FILTER_SANITIZE_STRING);
  $nazwaszkolenia = filter_input(INPUT_POST, 'Nnazwaszkoleniaust', FILTER_SANITIZE_STRING);
  $iloscpytan = filter_input(INPUT_POST, 'Niloscpytanust', FILTER_SANITIZE_NUMBER_INT);
  $sql = "INSERT INTO `szkolenieust` (`firma` ,`nazwaszkolenia` ,`iloscpytan`) VALUES ('$firmaszkolenia','$nazwaszkolenia','$iloscpytan');";
  R::exec($sql);
  echo R::getInsertID();
?>
