<?php
error_reporting(2);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$email = filter_input(INPUT_POST, "mail", FILTER_SANITIZE_EMAIL);
$haslo = filter_input(INPUT_POST, "haslo", FILTER_SANITIZE_STRING);
$nowehaslo = crypt($haslo,'pUf$ku$*12_gogo');
try {
    $sql = "DELETE FROM  `haslo` WHERE `email`='$email';";
    R::exec($sql);
} catch (Exception $e) {
    
}
$sql = "INSERT INTO `haslo` (`email` ,`haslo`) VALUES ('$email', '$nowehaslo');";
R::exec($sql);
echo 0;

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

