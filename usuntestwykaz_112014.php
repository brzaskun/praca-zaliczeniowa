<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = filter_input(INPUT_POST, 'Nid',FILTER_SANITIZE_NUMBER_INT);
  $sql = "DELETE FROM `testwykaz` WHERE `id`='$id'";
  R::exec($sql);
  echo "lolo"; 
?>
