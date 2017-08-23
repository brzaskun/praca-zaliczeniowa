<?php
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = filter_input(INPUT_POST,'userid', FILTER_SANITIZE_STRING);
  $email = filter_input(INPUT_POST,'email',FILTER_SANITIZE_STRING);
  $zwrot = 0;
  try {
    if ($email != "mchmielewska@interia.pl") {
      $sql = "DELETE FROM `uczestnicy` WHERE id='$id'";
      R::exec($sql);
      $sql = "DELETE FROM uczestnikgrupy WHERE id_uczestnik='$id'";
      R::exec($sql);
    }
  } catch (Exception $e) {
      $zwrot = 1;
  }
  echo $zwrot;
?>
