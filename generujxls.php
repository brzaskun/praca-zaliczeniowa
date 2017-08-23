<?php
error_reporting(0);
if (session_status() != 2) {
    session_start();
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Excelwygeneruj.php');
$firma = $_POST['firmanazwa'];
$uczestnicyrodzaj = $_POST['uczestnicyrodzaj'];
echo Excelwygeneruj::exceladmin($firma, $uczestnicyrodzaj); 
?>