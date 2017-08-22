<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Wynikiarchiwum
 *
 * @author Osito
 */
class Wynikiarchiwum {
    
    static $datarozpoczecia;
    static $datazakonczenia;
    static $wynik;
    static $pobranocertyfikat;
    static $progzdawalnosci;
    static $iloscpoprawnych;
    static $iloscblednych;
    static $iloscodpowiedzi;
    static $roznicapunktow;


    public static function pobierzwynikiarchiwalne(){
        self::$datarozpoczecia = $_SESSION['uczestnik']['sessionstart'];
        self::$datazakonczenia = $_SESSION['uczestnik']['sessionend'];
        self::$wynik = $_SESSION['uczestnik']['wyniktestu'];
        self::$pobranocertyfikat = $_SESSION['uczestnik']['wyslanycert'];
        self::$progzdawalnosci = $_SESSION['progzdawalnosciuczestnik'];
        self::$iloscpoprawnych = $_SESSION['uczestnik']['iloscpoprawnych'];
        self::$iloscblednych = $_SESSION['uczestnik']['iloscblednych'];
        self::$iloscodpowiedzi = $_SESSION['uczestnik']['iloscodpowiedzi'];
        self::$roznicapunktow = self::$iloscpoprawnych+self::$iloscblednych;
    }
}

?>
