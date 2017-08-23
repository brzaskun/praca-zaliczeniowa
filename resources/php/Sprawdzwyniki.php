<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Sprawdzwyniki
 *
 * @author Osito
 */
class Sprawdzwyniki {
    static $zadanepytania;
    static $udzieloneodpowiedzi = [];
    static $iloscpoprawnych = 0;
    static $iloscblednych = 0;
    static $przyznanepunkty = 0;
    static $iloscodpowiedzi = 0;
    static $wynik = 0;
    static $progzdawalnosci = 0;
    static $zdane = 0;
    static $roznicapunktow = 0;
     
    public static function jakoscodpowiedzi(){
        $sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
        require_once($sciezkaroot . '/resources/php/Rb.php');
        R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        self::$zadanepytania = $_SESSION['wybranepytania'];
        self::pobierzodpowiedzi();
        self::pobierzzachowaneodpowiedzi();
        $j = 0;
        foreach (self::$zadanepytania as $kolejnepytanie){
            $odpowiedziprawidlowe = [$kolejnepytanie['odp1walidacja'],$kolejnepytanie['odp2walidacja'],$kolejnepytanie['odp3walidacja'],$kolejnepytanie['odp4walidacja']];
            foreach ($odpowiedziprawidlowe as $odpdododania){
                if ($odpdododania === 'true'){
                    self::$iloscodpowiedzi++;
                }
            }
            for ($i = 0 ; $i < 4 ; $i++){
                $odp1 = self::$udzieloneodpowiedzi[$j][$i];
                $odp2 = $odpowiedziprawidlowe[$i];
                //za prawidlowa +
                if ($odp1 === 'true' && $odp2 === 'true'){
                        self::$iloscpoprawnych++;
                }
                //za zaznaczona niepawidlowa -
                if ($odp1 === 'true' && $odp2 === 'false') {
                        self::$iloscblednych--;
                }
            }
            $j++;
        }
        self::$roznicapunktow = self::$iloscpoprawnych + self::$iloscblednych < 0 ? 0 : self::$iloscpoprawnych + self::$iloscblednych;
        self::$wynik = intval(intval(self::$roznicapunktow) / intval(self::$iloscodpowiedzi) * 100);
        self::$progzdawalnosci = Sprawdzwyniki::wybierzprogzdawalnosci();
        if (self::$wynik>=self::$progzdawalnosci){
            self::$zdane = 1;
        }
        if (self::$zdane === 1){
            self::nanieszdanieegzaminu();
        }
    }
    
    private static function wybierzprogzdawalnosci() {
        $zwrot = $_SESSION['progzdawalnosciuczestnik'];
        $szkolenie = $_SESSION['uczestnik']['nazwaszkolenia'];
        $firma = $_SESSION['uczestnik']['firma'];
        $sql = "SELECT progzdawalnosci FROM szkolenieust WHERE szkolenieust.firma = '$firma' AND szkolenieust.nazwaszkolenia = '$szkolenie'";
        $progzdawalnosci = R::getCell($sql);
        if (isset($progzdawalnosci)) {
            $zwrot = $progzdawalnosci;
        }
        return $zwrot;
    }
    
    private static function pobierzzachowaneodpowiedzi() {
        $licznik = 0;
        while (isset($_SESSION['testodpowiedzi'][$licznik])) {
            array_push(self::$udzieloneodpowiedzi, $_SESSION['testodpowiedzi'][$licznik]);
            $licznik = $licznik + 1;
        }
    }
    
    public static function pobierzodpowiedzi() {
        $odp1 = isset($_POST['odp1']) ? "true" : "false";
        $odp2 = isset($_POST['odp2']) ? "true" : "false";
        $odp3 = isset($_POST['odp3']) ? "true" : "false";
        $odp4 = isset($_POST['odp4']) ? "true" : "false";
        $odptabela = array($odp1, $odp2, $odp3, $odp4);
        $_SESSION['testodpowiedzi'][$_SESSION['ilosc']] = $odptabela;
        return $odptabela;
    }
    
    private static function nanieszdanieegzaminu(){
        date_default_timezone_set('Europe/Warsaw');
        $uczestnik = $_SESSION['uczestnik'];
        $id = $_SESSION['uczestnik']['id'];
        $czasbiezacy = date("Y-m-d H:i:s");
        $_SESSION['uczestnik']['sessionend'] = $czasbiezacy;
        $wynik = self::$wynik;
        $iloscpoprawnych = self::$iloscpoprawnych;
        $iloscblednych = self::$iloscblednych;
        $iloscodpowiedzi = self::$iloscpoprawnych+self::$iloscblednych;
        //rejestrowanie zdania wyniku
        R::exec("UPDATE  `uczestnicy` SET  `sessionend`='$czasbiezacy', `wyniktestu`='$wynik', `iloscpoprawnych`='$iloscpoprawnych', `iloscblednych` = '$iloscblednych', `iloscodpowiedzi` = '$iloscodpowiedzi'  WHERE  `uczestnicy`.`id` = '$id';");
    }
    
   
}

?>
