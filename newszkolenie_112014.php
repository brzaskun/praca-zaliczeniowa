<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $nazwaszkolenia = filter_input(INPUT_POST, 'Nnazwaszkolenia', FILTER_SANITIZE_STRING);
  $naglowek = filter_input(INPUT_POST, 'Nnaglowek', FILTER_SANITIZE_SPECIAL_CHARS);
  $tresc = filter_input(INPUT_POST, 'Ntrescszkolenia');
  $sql = "INSERT INTO `szkolenie` (`nazwaszkolenia` ,`naglowek` ,`temat`,`tresc`) VALUES ('$nazwaszkolenia',  '$naglowek', NULL, '$tresc');";
  R::exec($sql); 
  echo R::getInsertID();
?>
