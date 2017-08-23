<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['Nid'];
  $Nszkolenie = $_POST['Nszkolenie'];
  $Ntest = $_POST['Ntest'];
  $Nuwagi = $_POST['Nuwagi'];
  $sql = "INSERT INTO  `szkolenietest` (`id` ,`szkolenie` ,`test` ,`uwagi`) VALUES ('$id', '$Nszkolenie',  '$Ntest', '$Nuwagi');";
  R::exec($sql);
  echo R::getCell("SELECT `id` FROM  `szkolenietest` WHERE  (`szkolenie`='$Nszkolenie' AND `test`='$Ntest')");
?>
