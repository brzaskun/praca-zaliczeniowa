<?php

error_reporting(0);
if (session_status() != 2) {
    session_start();
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
try {
    R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
    $szkolenia = R::getAll('SELECT * FROM testwykaz ORDER BY nazwa');
    $czlonkowie = array();
    foreach ($szkolenia as $val) {
        $tab = array();
        array_push($tab, "<input type='checkbox' class=\"czekbox\"/>");
        array_push($tab, "<span class='doedycji'>" . $val['id'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['nazwa'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['skrot'] . "</span>");
        array_push($tab, "<span class='doedycji'>" . $val['opis'] . "</span>");
        array_push($tab, "<input title=\"edytuj\" name=\"edytuj\" type='checkbox' class='czekedycja' onclick=\"editwiersz(this);\" class=\"buttonedytujuser\" style=\"display: none;\"/>");
        array_push($tab, "<input title=\"usuń\" name=\"usun\" type='checkbox' class='czekedycja' onclick=\"usunwiersz(this);\"  style=\"display: none;\"/>");
        array_push($czlonkowie, $tab);
    }
    $output = json_encode($czlonkowie);
    echo $output;
} catch (Exception $e) {
    echo "brak";
}
?>