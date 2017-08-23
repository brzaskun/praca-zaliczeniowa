<?php 
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = filter_input(INPUT_POST,'id', FILTER_SANITIZE_STRING);
  $sql = "SELECT grupyupowaznien.nazwagrupy FROM grupyupowaznien WHERE `id`='$id'";
  $nazwagrupy = R::getRow($sql);
  $sql = "SELECT grupyupowaznien.firma FROM grupyupowaznien WHERE `id`='$id'";
  $firmanazwa = R::getRow($sql);
  $uczestnicy = array();
  $sql = "SELECT uczestnicy.email FROM uczestnicy WHERE `firma`='$firmanazwa[firma]'";
  $uczestnicy = R::getCol($sql);
  foreach ($uczestnicy as $value) {
      $sql = "SELECT uczestnikgrupy.id FROM uczestnikgrupy WHERE `grupa`='$nazwagrupy[nazwagrupy]' AND email = '$value'";
      $iduczestnikgrupa = R::getRow($sql);
      $sql = "DELETE FROM `uczestnikgrupy` WHERE `id`='$iduczestnikgrupa[id]';";
      R::exec($sql);
  }
  $sql = "DELETE FROM `grupyupowaznien` WHERE id=$id";
  R::exec($sql);
  
?>
