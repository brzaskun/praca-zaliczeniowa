<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Excelwygeneruj
 *
 * @author Osito
 */
class KoniecTestu {

    public static function odnotuj() {
        try {
            require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        } catch (exception $e) {
            
        };
        $id = $_SESSION['uczestnik']['id'];
        date_default_timezone_set('Europe/Warsaw');
        try {
            if ($id > 0) {
                $data = date("Y-m-d H:i:s");
                $sql = "UPDATE  `uczestnicy` SET  `wcisnietyklawisz` = '$data' WHERE  `uczestnicy`.`id` = $id;";
                R::exec($sql);
            }
        } catch (exception $e) {
            
        };
        try {
            if ($id > 0) {
                $datanadania = R::getCell("SELECT  `datanadania` FROM `uczestnicy` WHERE  `uczestnicy`.`id` = '$id';");
                if ($datanadania == null || $datanadania == "") {
                    $datanadania = date("d.m.Y");
                    $sql = "UPDATE  `uczestnicy` SET  `datanadania` = '$datanadania' WHERE  `uczestnicy`.`id` = $id;";
                    R::exec($sql);
                }
            }
        } catch (exception $e) {
            
        };
    }

    public static function archiwizuj() {
        date_default_timezone_set('Europe/Warsaw');
        try {
            require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        } catch (exception $e) {};
        $id = $_SESSION['uczestnik']['id'];
        $val = R::getRow("SELECT * FROM uczestnicy WHERE id='$id'");
        $wyslanycert = $val['wyslanycert'];
        $wyslanymailupr = $val['wyslanymailupr'];
        $sessionstart = $val['sessionstart'];
        $sessionend = $val['sessionend'];
        $wyniktestu = $val['wyniktestu'];
        $ilosclogowan = $val['ilosclogowan'];
        $iloscpoprawnych = $val['iloscpoprawnych'];
        $iloscblednych = $val['iloscblednych'];
        $iloscodpowiedzi = $val['iloscodpowiedzi'];
        $nrupowaznienia = $val['nrupowaznienia'];
        $indetyfikator = $val['indetyfikator'];
        $datanadania = $val['datanadania'];
        $dataustania = $val['dataustania'];
        $wyslaneup = $val['wyslaneup'];
        date_default_timezone_set('Europe/Warsaw');
        $data = date("Y-m-d H:i:s");
        $nazwaszkolenia = $val['nazwaszkolenia'];
        $sql = "INSERT INTO `tb152026_testdane`.`uczestnicyarchiwum`
        (`id_uzytkownik`,`wyslanymailupr`,`sessionstart`,`sessionend`,`wyniktestu`,`wyslanycert`,
        `ilosclogowan`,`iloscpoprawnych`,`iloscblednych`,`iloscodpowiedzi`,`nrupowaznienia`,
        `indetyfikator`,`datanadania`,`dataustania`,`wyslaneup`,`data`,`nazwaszkolenia`)
        VALUES
        ('$id','$wyslanymailupr','$sessionstart','$sessionend','$wyniktestu','$wyslanycert','$ilosclogowan','$iloscpoprawnych','$iloscblednych','$iloscodpowiedzi'
            ,'$nrupowaznienia','$indetyfikator','$datanadania','$dataustania','$wyslaneup','$data','$nazwaszkolenia')";
        R::exec($sql);
    }

}

?>
