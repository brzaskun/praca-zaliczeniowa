<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  require_once $_SERVER['DOCUMENT_ROOT'].'/resources/php/ConvertZaswiadczenie.php';
  $sql = "SELECT * FROM szkoleniewykaz"; 
  $szkoleniawykaz = R::getAll($sql);
  $output = "";
  foreach ($szkoleniawykaz as $val) {
      $id_zaswiadczenie = $val['id_zaswiadczenie'];
      $val['id_zaswiadczenie'] = ConvertZaswiadczenie::toName($id_zaswiadczenie);
      $output = $output.",".array_shift($val);
  }
  echo $output;
?>
