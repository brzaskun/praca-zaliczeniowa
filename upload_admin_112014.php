<?php error_reporting(0); 
if (session_status()!=2) {
    session_start();
    }; 
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Mail.php');
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/SprawdzWprowadzanyWiersz.php'); 
$tablicapobranychpracownikow = $_SESSION['tablicapobranychpracownikow'];
$firmabaza = urldecode($_COOKIE['firmadoimportu']);
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$nazwygrup = array();
date_default_timezone_set('Europe/Warsaw');
$czasbiezacy = date("Y-m-d H:i:s");
foreach ($tablicapobranychpracownikow  as $wierszbaza) {
    if (SprawdzWprowadzanyWiersz::sprawdz2($wierszbaza)) {
        try {
          $sql = "INSERT INTO  `uczestnicy` (`id`, `email` ,`imienazwisko` ,`plec` ,`firma` , `nazwaszkolenia`, `uprawnienia` ,`wyslanymailupr` ,`sessionstart` ,
            `sessionend` ,`wyniktestu` ,`wyslanycert`,`indetyfikator`, `nrupowaznienia`, `utworzony`)
            VALUES (0, '$wierszbaza[0]',  '$wierszbaza[1]', '$wierszbaza[2]', '$firmabaza', '$wierszbaza[3]', 'uzytkownik' , 0, NULL , NULL , 0 , 0, '$wierszbaza[4]', '$wierszbaza[5]', '$czasbiezacy');";
            R::exec($sql);
            $id_uzytkownik = R::getInsertID();
            Mail::mailautomat($wierszbaza[1], $wierszbaza[2], $wierszbaza[0], $wierszbaza[3], $id_uzytkownik);
          foreach ($nazwygrup as $key=>$value) {
               $wartoscpola = $wierszbaza[$value];
                if ($wartoscpola == 1) {
                    try {
                      $sql = "INSERT INTO uczestnikgrupy (email,nazwiskoiimie,grupa,id_uczestnik) VALUES ('$wierszbaza[0]','$wierszbaza[1]','$key', '$id_uzytkownik');";
                      R::exec($sql);
                    } catch (Exception $e) {

                    }
                } else {
                     try {
                      $sql = "DELETE FROM uczestnikgrupy WHERE grupa='$key' AND id_uczestnik='$id_uzytkownik'";
                      R::exec($sql);
                    } catch (Exception $e) {

                    }
                }
          } 
        } catch (Exception $e){
              header("Location: admin112014_uzytkownicy.php?error=Wystąpił błąd podczas ładowania danych z pliku!");
              exit();
        }
    } else {
        $dl = count((array)$wierszbaza);
        $start = $dl-6;
        if ($start > 0) {
            for ($i = 6; $i < $dl; $i++) {
                try {
                   $nazwygrup[$wierszbaza[$i]] = $i;
                    $sql = "INSERT INTO `grupyupowaznien` (`firma` ,`nazwagrupy`) VALUES ('$firmabaza',  '$wierszbaza[$i]');";
                    R::exec($sql);
                } catch (Exception $e){
                    
                }
            }
        }
    } 
}
 $url = "Location: admin112014_uzytkownicy.php";
 header($url);
 exit();
?>