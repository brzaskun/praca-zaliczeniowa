<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $sql = "SELECT * FROM zaswiadczenia";
  $baza = R::getAll($sql);
  $wiersze = array(); 
  foreach ($baza as $val) {
      $tab = array();
      array_push($tab, "<input type='checkbox' class='czekbox'/>");
      array_push($tab, "<span class='doedycji'>".$val['id']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['nazwa']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['skrot']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['poziom']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['linia1']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['linia2']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['linia3']."</span>");
      array_push($tab, "<span class='doedycji'>".$val['pdf']."</span>");
      array_push($tab, "<textarea rows='4' cols='50' disabled class='doedycji'>".$val['trescM']."</textarea>");
      array_push($tab, "<textarea rows='4' cols='50' disabled class='doedycji'>".$val['trescK']."</textarea>"); 
      array_push($tab, "<input type='button' value='podgląd' onclick='podgladzaswiadczenia(this);'/>"); 
      array_push($tab, "<input type='checkbox' class='czekedycja' onclick='editzaswiadczenie(event);' style='display: none;'/>");
      array_push($tab, "<input type='checkbox' class='czekedycja' onclick='usunzaswiadczenie(this);' style='display: none;'/>");
      array_push($wiersze, $tab);
  }
  $naglowki = array("","id","nazwa","skrót","poziom","linia1","linia2","linia3","pdf","treść M","treść K","podgląd","edytuj","usuń");
  $output = array($naglowki,$wiersze);
  echo json_encode($output); 
?>
 