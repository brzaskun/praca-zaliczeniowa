<?php
error_reporting(2);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$email = filter_input(INPUT_POST, "mail", FILTER_SANITIZE_EMAIL);
$haslo = filter_input(INPUT_POST, "haslo", FILTER_SANITIZE_STRING);
$sql = "SELECT * FROM  `haslo` WHERE `email`='$email';";
$odnalezione = R::getAll($sql);
$doporownania = $odnalezione[0]['haslo'];
if ($haslo !== "") {
    $nowe = crypt($haslo, $doporownania);
    if ( $nowe == $doporownania) {
        echo 1;
    } else {
        echo 0;
    }
} else {
    echo "puste";
}

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

