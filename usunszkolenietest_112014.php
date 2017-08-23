<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['Nid'];
  $Nszkolenie = $_POST['Nszkolenie'];
  $Ntest = $_POST['Ntest'];
  $Nuwagi = $_POST['Nuwagi'];
  //unescape danych ktore wczesniej escape javascript
  $sql = "DELETE FROM `szkolenietest` WHERE `id`='$id'";
  R::exec($sql);
?>

