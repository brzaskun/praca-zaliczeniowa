<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['Nid'];
  $id_int = (int)preg_replace('/(\<span\>|\<\/span\>)/', '', $id);
  $sql = "DELETE FROM `szkoleniewykaz` WHERE `id`=$id_int";
  R::exec($sql);
  echo "lolo"; 
?>
