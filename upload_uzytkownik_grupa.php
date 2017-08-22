<?php error_reporting(0);
if (session_status()!=2) {
    session_start();
    }; 
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Mail.php');
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/SprawdzWprowadzanyWiersz.php'); 
//tablica zachowana w admin_uzytkownik_grupa_plik.php
$tablicapobranychpracownikow = $_SESSION['tablicapobranychpracownikow']; 
$firmabaza = urldecode($_COOKIE['firmadoimportu']);
$rodzajdanych = $_POST['rodzajdanych'];
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$nazwygrup = array(); 
foreach ($tablicapobranychpracownikow  as $wierszbaza) {
    if (SprawdzWprowadzanyWiersz::sprawdz($wierszbaza)) {
        try {
          if ($rodzajdanych == "wd") {
            $sql = "UPDATE  `uczestnicy` SET  `nrupowaznienia` =  '$wierszbaza[3]', `indetyfikator` = '$wierszbaza[4]', `datanadania` = '$wierszbaza[5]', `dataustania` = '$wierszbaza[6]' WHERE  `uczestnicy`.`id` =  '$wierszbaza[0]'";
             R::exec($sql);
          } else if ($rodzajdanych == "du") {
             $sql = "UPDATE  `uczestnicy` SET  `dataustania` = '$wierszbaza[6]' WHERE  `uczestnicy`.`id` =  '$wierszbaza[0]'";
             R::exec($sql);
          } else if ($rodzajdanych == "ndu") {
             $sql = "UPDATE  `uczestnicy` SET  `dataustania` = '$wierszbaza[6]' WHERE  `uczestnicy`.`id` =  '$wierszbaza[0]' AND (dataustania IS NULL OR CHAR_LENGTH(dataustania) < 1)";
             R::exec($sql);
          }
        } catch (Exception $e){
        }
    }
}
 return "Zapisano zmiany w tabeli użytkowników"
?>
