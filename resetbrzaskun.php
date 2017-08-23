<?php
  error_reporting(2);
  if(session_status()!=2){     session_start(); };
  date_default_timezone_set('Europe/Warsaw');
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  //$id = 12952;
  $id = 12971;
  $data = date("Y-m-d H:i:s");
  $sqlnull = "UPDATE  `uczestnicy` SET `sessionstart` = NULL, `sessionend` = NULL, `wcisnietyklawisz` = NULL, `wyniktestu` = '0', `ilosclogowan` = '0', `iloscpoprawnych` = '0', `iloscblednych` = '0', `iloscodpowiedzi` = '0',`zaswiadczeniedata` = NULL, `upowaznieniedata` = NULL, `wyslaneup` = '0', `wyslanycert` = '0', `datanadania` = NULL, `ostatnireset`='$data'  WHERE  `uczestnicy`.`id` = '$id';";
  //$sqlnull = "UPDATE  `uczestnicy` SET `sessionstart` = null, `sessionend` = null, `ilosclogowan` = 0,  `wyniktestu` = '0' WHERE  `uczestnicy`.`id` = $id;";
  R::exec($sqlnull);
echo "Zresetowalem brzaskun"
?>
