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
$czasbiezacy = date("Y-m-d H:i:s");
$pierwszywiersz = array_shift($tablicapobranychpracownikow);
$dl = count((array)$pierwszywiersz);
$start = $dl-6;
$listadodanychgrup = array();
$listagrupniedodanych = array();
$bledydodawanieuzytkownikow = array();
$niewyslanymail = array();
$zleoznaczeni = array();
$dobrzedodani = array();
if ($start > 0) {
    for ($i = 6; $i < $dl; $i++) {
        try {
           $nazwygrup[$pierwszywiersz[$i]] = $i;
            $sql = "INSERT INTO `grupyupowaznien` (`firma` ,`nazwagrupy`) VALUES ('$firmabaza',  '$pierwszywiersz[$i]');";
            R::exec($sql);
            array_push($listadodanychgrup,$pierwszywiersz[$i]);
        } catch (Exception $e){
            array_push($listagrupniedodanych,$pierwszywiersz[$i]);
        }
    }
}
$liczbadobrzedodanych = 0;
foreach ($tablicapobranychpracownikow  as $wierszbaza) {
        try {
            $sql = "INSERT INTO  `uczestnicy` (`email` ,`imienazwisko` ,`plec` ,`firma` , `nazwaszkolenia`, `uprawnienia` ,`wyslanymailupr` ,`sessionstart` ,
            `sessionend` ,`wyniktestu` ,`wyslanycert`,`indetyfikator`, `nrupowaznienia`, `utworzony`)
            VALUES ('$wierszbaza[0]',  '$wierszbaza[1]', '$wierszbaza[2]', '$firmabaza', '$wierszbaza[3]', 'uzytkownik' , 0, NULL , NULL , 0 , 0, '$wierszbaza[4]', '$wierszbaza[5]', '$czasbiezacy');";
            R::exec($sql);
            $id_uzytkownik = R::getInsertID();
            if ($id_uzytkownik > 0) {
                $wynik = Mail::mailautomat($wierszbaza[1], $wierszbaza[2], $wierszbaza[0], $wierszbaza[3], $id_uzytkownik);
                if (strpos($wynik, "@") !== false) {
                    array_push($niewyslanymail, $wynik);
                }
                foreach ($nazwygrup as $key => $value) {
                    $wartoscpola = $wierszbaza[$value];
                    if ($wartoscpola == 1) {
                        try {
                            $sql = "INSERT INTO uczestnikgrupy (email,nazwiskoiimie,grupa,id_uczestnik) VALUES ('$wierszbaza[0]','$wierszbaza[1]','$key', '$id_uzytkownik');";
                            R::exec($sql);
                        } catch (Exception $e) {
                            array_push($zleoznaczeni, $wierszbaza[0]);
                        }
                    } else {
                        try {
                            $sql = "DELETE FROM uczestnikgrupy WHERE grupa='$key' AND id_uczestnik='$id_uzytkownik'";
                            R::exec($sql);
                        } catch (Exception $e) {
                            array_push($zleoznaczeni, $wierszbaza[0]);
                        }
                    }
            }
            $liczbadobrzedodanych++;
        }
    } catch (Exception $e){
            array_push($bledydodawanieuzytkownikow, $wierszbaza[1]);
            array_push($niewyslanymail, $wierszbaza[0]);
        }
}
array_push($dobrzedodani, $liczbadobrzedodanych);
$output = json_encode(array($listadodanychgrup,$listagrupniedodanych,$bledydodawanieuzytkownikow,$niewyslanymail, $zleoznaczeni, $dobrzedodani));
echo $output;
?>