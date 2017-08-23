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
class NextslideTest {

    static $ilosc;
    static $test;
    static $uczestnik;
    static $wybranepytania = [];
    static $opis;
    static $odpowiedzi;

    private function __construct() {
       
    }

    public static function init() {
        if (isset($_SESSION['test'])) {
            self::$test = $_SESSION['test'];
            self::$ilosc = $_SESSION['ilosc'];
            self::$opis = $_SESSION['opis']; 
        } else {
            require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
            $nazwaszkolenia = $_SESSION['uczestnik']['nazwaszkolenia'];
            //pobieramy nazwy przyporzadkowanych testow
            $sqltesty = "SELECT * FROM szkolenietest WHERE szkolenie = '$nazwaszkolenia'";
            $przyporzadkowanetesty = R::getAll($sqltesty);
            self::$test = array();
            foreach ($przyporzadkowanetesty as $kolejny) {
                $nazwatestu = $kolejny['test'];
                $sqlstring = "SELECT * FROM test WHERE nazwatest = '$nazwatestu'";
                $pobranepytania = R::getAll($sqlstring);
                self::$test = array_merge(self::$test, $pobranepytania);
            }
            $_SESSION['test'] = self::$test;
            self::$opis = R::getCell("SELECT opis FROM testwykaz WHERE nazwa = '$nazwatestu'");
            $_SESSION['opis'] = self::$opis;
            if ($nazwaszkolenia == "szkoleniedemo") {
                array_push(self::$wybranepytania, ($_SESSION['test'][0]));
                array_push(self::$wybranepytania, ($_SESSION['test'][1]));
                $_SESSION['wybranepytania'] = self::$wybranepytania;
                $_SESSION['pytaniasize'] = sizeof(self::$wybranepytania); 
            } else {
                $_SESSION['testsize'] = sizeof(self::$test);
                $numery = [];
                $i = 0;
                $maxliczbapytan = $_SESSION['uczestnik']['iloscpytan'];
                while ($i < $maxliczbapytan){
                    $numerlosowy = rand(0, $_SESSION['testsize']-1);
                    if (in_array($numerlosowy, $numery)==false){
                        array_push($numery, $numerlosowy);
                        $i++;
                    }
                }
                foreach ($numery as $numerkolejny){
                    array_push(self::$wybranepytania, ($_SESSION['test'][$numerkolejny]));
                }
                $_SESSION['wybranepytania'] = self::$wybranepytania;
                $_SESSION['pytaniasize'] = sizeof(self::$wybranepytania);
            }
            $_SESSION['ilosc'] = 0;
            self::$ilosc = 0;
            self::$uczestnik = $_SESSION['uczestnik'];
        }
    }

    public static function next() {
        self::init();
        self::$odpowiedzi = self::pobierzodpowiedzi();
        if (isset($_SESSION['ilosc'])) {
            $top = $_SESSION['pytaniasize'] - 1 > 0 ? $_SESSION['pytaniasize'] - 1 : 0;
            if ($_SESSION['ilosc'] < $top) {
                $_SESSION['ilosc'] = $_SESSION['ilosc'] + 1;
                self::$ilosc = $_SESSION['ilosc'];
                self::$test = $_SESSION['test'];
                self::$wybranepytania = $_SESSION['wybranepytania'];
            } else {
                self::$ilosc = $top;
                self::$test = $_SESSION['test'];
                self::$wybranepytania = $_SESSION['wybranepytania'];
            }
        } else {
            $_SESSION['ilosc'] = 0;
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
                self::$test = $_SESSION['test'];
                self::$wybranepytania = $_SESSION['wybranepytania'];
            } else {
                self::$ilosc = 0;
                self::$test = $_SESSION['test'];
                self::$wybranepytania = $_SESSION['wybranepytania'];
            }
        } else {
            $_SESSION['ilosc'] = 0;
            self::$ilosc = 0;
        }
        self::$uczestnik = $_SESSION['uczestnik'];
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
    
    public static function przywrocenieodpowiedzi() {
        $odpowiedz = "błąd";
        if (isset($_SESSION['testodpowiedzi'][$_SESSION['ilosc']])) {
            $odpowiedz = implode(",",$_SESSION['testodpowiedzi'][$_SESSION['ilosc']]);
        }
        return $odpowiedz;
    }
}

?>
