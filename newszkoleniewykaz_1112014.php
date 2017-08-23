<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  require_once $_SERVER['DOCUMENT_ROOT'].'/resources/php/ConvertZaswiadczenie.php';
  $Nszkolenia = $_POST['Nszkolenia'];
  $Nskrot = $_POST['Nskrot'];
  $Nopis = $_POST['Nopis'];
  $Nzaswiadczenie = $_POST['Nzaswiadczenie'];
  $Ninstrukcja = $_POST['Ninstrukcja'];
  $id_zas = ConvertZaswiadczenie::toId($Nzaswiadczenie);
  $sql = "INSERT INTO  `szkoleniewykaz` (`nazwa` , `skrot`, `opis`, `id_zaswiadczenie`, `instrukcja`) VALUES ('$Nszkolenia', '$Nskrot',  '$Nopis', '$id_zas', '$Ninstrukcja');";
  R::exec($sql);
  echo R::getInsertID();
?>
