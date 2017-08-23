<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $szk = filter_input(INPUT_POST, "szk", FILTER_SANITIZE_STRING);
  $nag = filter_input(INPUT_POST, "nag", FILTER_SANITIZE_STRING);
  $sql = "SELECT * FROM szkolenie WHERE nazwaszkolenia = '$szk' AND naglowek = '$nag'";
  $slajdy = R::getAll($sql);
  $output = "";
  foreach ($slajdy as $val) {
      $output = $output.",".array_shift($val);
  }
  if ($output === "") {
      echo "nie";
  } else {
      echo "tak";
  }
?>
