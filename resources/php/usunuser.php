<?php error_reporting(2);
  if(session_status()!=2){     session_start(); };
  require_once('/home/tb152026/public_html/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $id = $_GET['userid'];
  $email = $_GET['email'];
  if ($email != 'mchmielewska@interia.pl') {
    $sql = "DELETE FROM `uczestnicy` WHERE id=$id AND email=`$email`";
    R::exec($sql);
  } else {
    header("Location: admin.php?info=Nie można usunąc admina z listy");   
  }
?>
