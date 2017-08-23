<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of nextslide
 *
 * @author Osito
 */
class NextslideSzkolenie {

    static $ilosc;
    static $szkolenie;
    static $uczestnik;
    static $opisszkolenia;

    private function __construct() {
        
    }

    public static function init() {
        if (isset($_SESSION['szkolenie'])) {
            self::$szkolenie = $_SESSION['szkolenie'];
            self::$ilosc = $_SESSION['ilosc'];
            self::$opisszkolenia = $_SESSION['opisszkolenia'];
        } else {
            $nazwaszkolenia = $_SESSION['uczestnik']['nazwaszkolenia'];
            require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
            self::$opisszkolenia = R::getCell("SELECT opis FROM szkoleniewykaz WHERE nazwa = '$nazwaszkolenia'");
            $_SESSION['opisszkolenia'] = self::$opisszkolenia;
            $_SESSION['szkolenie'] = R::getAll("SELECT * FROM szkolenie where `nazwaszkolenia`='$nazwaszkolenia' ORDER BY `id` ASC");
            self::$szkolenie = $_SESSION['szkolenie'];
            $_SESSION['szkoleniesize'] = sizeof(self::$szkolenie);
            $_SESSION['ilosc'] = 0;
            self::$ilosc = 0;
            self::$uczestnik = $_SESSION['uczestnik'];
            $_SESSION['pokaznext'] = 1;
            $_SESSION['pokazprevious'] = 0;
        }
    }

    public static function next() {
        self::init();
        if (isset($_SESSION['ilosc'])) {
            $top = $_SESSION['szkoleniesize'] - 1 > 0 ? $_SESSION['szkoleniesize'] - 1 : 0;
            if ($_SESSION['ilosc'] < $top) {
                $_SESSION['ilosc'] = $_SESSION['ilosc'] + 1;
                self::$ilosc = $_SESSION['ilosc'];
                self::$szkolenie = $_SESSION['szkolenie'];
                $_SESSION['pokaznext'] = 1;
                $_SESSION['pokazprevious'] = 1;
            } else {
                self::$ilosc = $top;
                self::$szkolenie = $_SESSION['szkolenie'];
                $_SESSION['pokaznext'] = 0;
                $_SESSION['pokazprevious'] = 1;
            }
        } else {
            $_SESSION['ilosc'] = 0;
            $_SESSION['pokaznext'] = 1;
            $_SESSION['pokazprevious'] = 0;
            self::$ilosc = 0;
        }
        self::$uczestnik = $_SESSION['uczestnik'];
    }

    public static function back() {
        self::init();
        if (isset($_SESSION["ilosc"])) {
            if ($_SESSION['ilosc'] != 0) {
                $_SESSION['ilosc'] = $_SESSION['ilosc'] - 1;
                self::$ilosc = $_SESSION['ilosc'];
                self::$szkolenie = $_SESSION['szkolenie'];
                $_SESSION['pokazprevious'] = 1;
            } else {
                self::$ilosc = 0;
                self::$szkolenie = $_SESSION['szkolenie'];
                $_SESSION['pokazprevious'] = 0;
                $_SESSION['pokaznext'] = 1;
            }
        } else {
            $_SESSION['ilosc'] = 0;
            $_SESSION['pokaznext'] = 1;
            $_SESSION['pokazprevious'] = 0;
            self::$ilosc = 0;
        }
        self::$uczestnik = $_SESSION['uczestnik'];
    }

}

?>
