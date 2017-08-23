<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $firmanazwa = $_POST['firmanazwa'];
  $bezgrup = $_POST['bezgrup'];
  $uczestnicyrodzaj = $_POST['uczestnicyrodzaj'];
  if ($uczestnicyrodzaj == "wszyscy") {
        $sql = "SELECT * FROM uczestnicy where firma = '$firmanazwa' ORDER BY `uczestnicy`.`email` ASC";
        $uczestnicy = R::getAll($sql);
  } else if ($uczestnicyrodzaj == "aktywni"){
       $sql = "SELECT * FROM uczestnicy where firma = '$firmanazwa' AND (dataustania IS NULL OR CHAR_LENGTH(dataustania) < 1) ORDER BY `uczestnicy`.`email` ASC";
       $uczestnicy = R::getAll($sql);
  } else {
       $sql = "SELECT * FROM uczestnicy where firma = '$firmanazwa' AND (dataustania IS NOT NULL AND CHAR_LENGTH(dataustania) = 10) ORDER BY `uczestnicy`.`email` ASC";
       $uczestnicy = R::getAll($sql);
  }
  $sql = "SELECT grupyupowaznien.nazwagrupy FROM grupyupowaznien where firma = '$firmanazwa'";
  $grupy = R::getAll($sql);
  $czlonkowie = array(); 
  $czlonkowie_wiersze = array(); 
  //$czek = "<input type='checkbox'/>";
  //$edit = "<input title='edytuj' name='edytuj' value='edytuj' type='button'  onclick='edituser(this);' class='buttonedytujuser' style=\"display: none;\"/>";
  foreach ($uczestnicy as $val) {
      $tab = array("",$val['id'],$val['email'],$val['imienazwisko']);
//      tab wiersze sa do eksportu danych w formacie XLS 
      $tab_wiersze = array($val['id'],$val['email'],$val['imienazwisko']);
      array_push($tab,"<input type='text' value='".$val['nrupowaznienia']."' style='width: 150px;'/>");
      array_push($tab_wiersze, $val['nrupowaznienia']);
      array_push($tab,"<input type='text' value='".$val['indetyfikator']."' style='width: 100px;'/>");
      array_push($tab_wiersze, $val['indetyfikator']);
      array_push($tab,"<input type='text' value='".$val['datanadania']."' style='width: 60px;' maxlength='10' placeholder='dd.mm.rrrr'/>");
      array_push($tab_wiersze, $val['datanadania']);
      array_push($tab,"<input type='text' value='".$val['dataustania']."' style='width: 60px;'maxlength='10' placeholder='dd.mm.rrrr'/>");
      array_push($tab_wiersze, $val['dataustania']);
      array_push($tab,"<input type='text' value='".$val['wyslaneup']."' style='width: 10px;'maxlength='1'/>");
//      array_push($tab,"<span style='width: 10px;'>".$val['wyslaneup']."</span>");
      array_push($tab_wiersze, $val['wyslaneup']);
      $id = $val['id'];
      $sql = "SELECT uczestnikgrupy.grupa FROM uczestnikgrupy WHERE id_uczestnik = '$id'";
      $zapisanegrupy = R::getCol($sql);
      if ($bezgrup == "true" && sizeof($zapisanegrupy)== 0) {
         foreach ($grupy as $value) {
             array_push($tab,"<input type='checkbox' class='czekgrupy'/>");
                array_push($tab_wiersze,'');
           }
          array_push($czlonkowie, $tab);
          array_push($czlonkowie_wiersze, $tab_wiersze); 
      } else if ($bezgrup == "false"){
        $zapisanegrupysmall = array();
        foreach ($zapisanegrupy as $value) { 
            array_push($zapisanegrupysmall, strtolower($value)); 
        }
        foreach ($grupy as $value) {
           if (in_array(strtolower($value['nazwagrupy']), $zapisanegrupysmall, true)) {
              array_push($tab,"<input type='checkbox' checked class='czekgrupy'/>");
              array_push($tab_wiersze,1);
           } else {
              array_push($tab,"<input type='checkbox' class='czekgrupy'/>");
              array_push($tab_wiersze,'');
           }
        } 
          array_push($czlonkowie, $tab);
          array_push($czlonkowie_wiersze, $tab_wiersze);
      }
  }
  if (sizeof($czlonkowie_wiersze) > 0) {
    $outarray = array($grupy,$czlonkowie,$czlonkowie_wiersze);
    $output = json_encode($outarray);
    echo $output;
  } else {
      echo NULL;
  }
?>

