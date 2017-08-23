<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_POST['idszkolenie'];
  $nazwaszkolenia = $_POST['nazwaszkolenia'];
  $naglowek = $_POST['naglowek'];
  $temat = $_POST['temat'];
  $tresc = $_POST['trescszkolenia'];
  //unescape danych ktore wczesniej escape javascript
  $str = preg_replace("/%u([0-9a-f]{3,4})/i","&#x\\1;",urldecode($tresc));
  $trescnowa = html_entity_decode($str,null,'UTF-8');
  $sql = "UPDATE  `szkolenie` SET  `tresc`='$trescnowa', `naglowek`='$naglowek' WHERE `id`='$id';";
  R::exec($sql);
 
?>

