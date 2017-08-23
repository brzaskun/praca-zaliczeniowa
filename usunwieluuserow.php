<?php error_reporting(0);
  if(session_status()!=2){session_start();};
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $numery = null;
  if (isset($_COOKIE["listadousuniecia"])) {
      $numery = $_COOKIE["listadousuniecia"];
      $tablica = explode(",", $numery);
        foreach ($tablica as $id) {
            $sql = "DELETE FROM `uczestnicy` WHERE `id`=$id AND NOT email='mchmielewska@interia.pl'";
            R::exec($sql);
        }
  }
  setcookie("listadousuniecia", "", time()-3600);
  header("Location: admin.php?info=Pracownicy usunięci z listy");    
?>

