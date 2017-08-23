<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['idszkolenie'];
  $nazwaszkolenia = $_POST['nazwaszkolenia'];
  $naglowek = $_POST['naglowek'];
  $temat = $_POST['temat'];
  $tresc = $_POST['trescszkolenia'];
  $sql = "TRUNCATE TABLE  `szkoleniepodglad`";
  R::exec($sql);
  $sql = "INSERT INTO `szkoleniepodglad` (`id` ,`nazwaszkolenia` ,`naglowek` ,`temat`,`tresc`) VALUES ('1', '$nazwaszkolenia',  '$naglowek', NULL, '$tresc');";
  R::exec($sql); 
  echo R::getCell("SELECT opis FROM szkoleniewykaz WHERE nazwa = '$nazwaszkolenia'");
?>

