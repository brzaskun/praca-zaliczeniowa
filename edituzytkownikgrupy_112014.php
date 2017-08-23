<?php error_reporting(0); 
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $wiersze = json_decode($_POST["dane"]);
  date_default_timezone_set('Europe/Warsaw');
  $czasbiezacy = date("Y-m-d H:i:s");
  foreach ($wiersze as $wiersz) {
      $aktywne = array();
      $nieaktywne = array();
      $datnad = str_replace('/', '.', $wiersz->datanadania);
      $datnad = str_replace('-', '.', $datnad);
      $datust = str_replace('/', '.', $wiersz->dataustania);
      $datust = str_replace('-', '.', $datust);
      $sql = "UPDATE  `uczestnicy` SET wyslaneup = '$wiersz->wyslaneup', nrupowaznienia = '$wiersz->nrupowaznienia', indetyfikator = '$wiersz->indetyfikator', datanadania = '$datnad', dataustania = '$datust', zmodyfikowany = '$czasbiezacy'  WHERE  `uczestnicy`.`id` = '$wiersz->id'";
      R::exec($sql);
      foreach ($wiersz as $key=>$value) {
        if ($key != "id" && $key != "email" && $key != "imienazwisko" && $key != "nrupowaznienia" && $key != "indetyfikator" && $key != "datanadania" && $key != "dataustania") {
            if ($value == 1) {
                array_push($aktywne, $key);
            } else {
                array_push($nieaktywne, $key);
            }
        }
      }
      foreach ($aktywne as $val) {
          try {
            $sql = "INSERT INTO uczestnikgrupy (email,nazwiskoiimie,grupa,id_uczestnik) VALUES ('$wiersz->email','$wiersz->imienazwisko','$val','$wiersz->id');";
            R::exec($sql);
          } catch (Exception $e) {
              
          }
      }
      foreach ($nieaktywne as $val) {
          try {
            $sql = "DELETE FROM uczestnikgrupy WHERE id_uczestnik='$wiersz->id' AND grupa='$val'";
            R::exec($sql);
          } catch (Exception $e) {
              
          }
      }
  }
  $sql = "SELECT * FROM uczestnikgrupy"; 
  $uzytkownikgrupa = R::getAll($sql);
?>
