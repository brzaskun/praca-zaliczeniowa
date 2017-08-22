<?php error_reporting(2); 
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $firma = $_POST['firmanazwa'];
  if ($firma != "" && $firma != 'null' && $firma != "wybierz bieżącą firmę" && $firma != "wszystkiefirmy") {
      $sql = "SELECT * FROM uczestnicy WHERE `uczestnicy`.`firma` = '$firma'";
  } else if ($firma == "wszystkiefirmy"){
      $sql = "SELECT * FROM zakladpracy INNER JOIN uczestnicy ON `uczestnicy`.`firma` = `zakladpracy`.`nazwazakladu` WHERE `zakladpracy`.`firmaaktywna` = 1 ORDER BY `uczestnicy`.`id` DESC";
  } else {
      echo "brak";
  } 
  $uczestnicy = R::getAll($sql);
  $czlonkowie = array();
   foreach ($uczestnicy as $val) {
       try {
        $od = $val['sessionstart'] != "" ? substr($val['sessionstart'], 0, 10) : "";
        $do = $val['sessionend'] != "" ? substr($val['sessionend'], 0, 10) : "";
        $tab = array();
        array_push($tab, "<span class='doedycji'>" . $val['id'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['email'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['imienazwisko'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['plec'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['firma'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['nazwaszkolenia'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['uprawnienia'] . "</span>");
        array_push($tab, "<span>" . $val['ilosclogowan'] . "</span>");
        array_push($tab, "<span>" . $val['wyslanymailupr'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $od . "</span>");
        array_push($tab, "<span>" . $do . "</span>");
        array_push($tab, "<span>" . $val['wyniktestu'] . "</span>");
        array_push($tab, "<span>" . $val['wyslanycert'] . "</span>");
        array_push($tab, "<input type='checkbox' class=\"czekbox\"/>");
        array_push($tab, "<input title=\"edytuj\" name=\"edytuj\" type='checkbox' class='czekedycja' onclick=\"edituser(this);\" class=\"buttonedytujuser\" style=\"display: none;\"/>");
        array_push($tab, "<input title=\"reset\" name=\"reset\" type='checkbox' class='czekedycja' onclick=\"resetujuser(this);\" style=\"display: none;\"/>");
        if ($val['email'] == "mchmielewska@interia.pl" || $value['email'] == "brzaskun@o2.pl") {
            array_push($tab, " ");
        } else {
            array_push($tab, "<input title=\"usuń\" name=\"usun\" type='checkbox' class='czekedycja' onclick=\"usunwiersz(this);\"  style=\"display: none;\"/>");
        }
        array_push($czlonkowie, $tab);
    } catch (Exception $e) {
        
    }
} 
  $output = json_encode($czlonkowie); 
  echo $output;
  
 
  
  //tablice primeui
//   foreach ($uczestnicy as $val) {
//      $od = $val['sessionstart'] != "" ?  $val['sessionstart'] : "";
//      $do = $val['sessionend'] != "" ? $val['sessionend'] : "";
//      $tab = array('id' => $val['id'],'email' => $val['email'],'imieinazwisko' => $val['imienazwisko'], 'plec' => $val['plec'], 'firma' => $val['firma'], 'szkolenie' => $val['nazwaszkolenia'], 'uprawnienia' => $val['uprawnienia'], 'illog' => $val['ilosclogowan'], 'link' => $val['wyslanymailupr'], 'rozpoczecie' => $od, 'zakonczenie' => $do,'test' => $val['wyniktestu'], 'certyfikat' => $val['wyslanycert']);
//      $t1 = '<input type=\'checkbox\' class=\'czekboks\'/>';
//      $tab['zaznacz'] =$t1;
//      $t2 = "<input title=\"edytuj\" name=\"edytuj\" value=\"edytuj\" type=\"button\"  onclick=\"edituser(this);\" class=\"buttonedytujuser\" style=\"display: none;\"/>";
//      $tab['edytuj'] =$t2;
//      $t3 = "<input title=\"reset\" name=\"reset\" value=\"reset\" type=\"button\"  onclick=\"resetujuser(this);\" style=\"display: none;\"/>";
//      $tab['reset'] =$t3;
//      if ($val['email'] == "mchmielewska@interia.pl" || $value['email'] == "brzaskun@o2.pl") {
//        $t4 = "";
//        $tab['usun'] =$t4;
//      } else {
//        $t4 = "<input title=\"usuń\" name=\"usun\" value=\"usuń\" type=\"button\" onclick=\"usunwiersz(this);\"  style=\"display: none;\"/>";
//        $tab['usun'] =$t4;
//      }
//      array_push($czlonkowie, $tab);
//  }   
?> 