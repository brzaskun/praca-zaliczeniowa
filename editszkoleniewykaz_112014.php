<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  require_once $_SERVER['DOCUMENT_ROOT'].'/resources/php/ConvertZaswiadczenie.php';
  $id = $_POST['Nid'];
  $Nszkolenia = $_POST['Nszkolenia'];
  $Nskrot = $_POST['Nskrot'];
  $Nopis = $_POST['Nopis'];
  $Nzaswiadczenie = $_POST['Nzaswiadczenie'];
  $Ninstrukcja = $_POST['Ninstrukcja'];
  $id_zas = ConvertZaswiadczenie::toId($Nzaswiadczenie);
  $sql = "UPDATE  `szkoleniewykaz` SET `skrot` = '$Nskrot', `opis` = '$Nopis', `id_zaswiadczenie` = '$id_zas', `instrukcja` = '$Ninstrukcja' WHERE `id`=$id"; 
  R::exec($sql);
?>

