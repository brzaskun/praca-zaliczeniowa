<?php
error_reporting(0);
if (session_status() != 2) {
    session_start();
};
try {
    require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
    R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
} catch (exception $e) {};
    $id = $_SESSION['uczestnik']['id'];
try {
    if ($id > 0) {
        date_default_timezone_set('Europe/Warsaw');
        $data = date("Y-m-d H:i:s");
        $sql = "UPDATE  `uczestnicy` SET  `wcisnietyklawisz` = '$data' WHERE  `uczestnicy`.`id` = $id;";
        R::exec($sql);
    }
 } catch (exception $e) {};
?>