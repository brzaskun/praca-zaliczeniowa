<?php
    error_reporting(2);
    echo "Zaczynam<br/>";
    require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
    R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
    $sql = "SELECT * FROM uczestnicy";
    $uczestnicy = R::getAll($sql);
    $sql = "SELECT * FROM uczestnikgrupy";
    $uczestnik_grupy = R::getAll($sql);
    foreach ($uczestnik_grupy as $uczgrp) {
        $em = $uczgrp['email'];
        $id = $uczgrp['id'];
        $sql = "SELECT id FROM `uczestnicy` WHERE  `uczestnicy`.`email` = '$em'";
        $id_uzytkownik = R::getCell($sql);
        $sql = "UPDATE `uczestnikgrupy` SET `id_uczestnik` = '$id_uzytkownik' WHERE `id` = $id";
        R::exec($sql);
    }
    echo "Koniec"; 
?>

