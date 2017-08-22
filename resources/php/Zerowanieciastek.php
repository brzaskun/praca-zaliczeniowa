<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Zerowanieciastek
 *
 * @author Osito
 */
class Zerowanieciastek {

    static private $ciastka = array('bledne', 'iloscpytan', 'zadanepytania', 'editslajd', 'liczbapracownikow', 'firmadoimportu');

    public final static function usunciastka() {
        if (session_status() != 2) {
            session_start();
        };
        session_unset();
        static::dodajnazwyciastkapytania();
        foreach (self::$ciastka as $nazwaciastka) {
            if (isset($_COOKIE[$nazwaciastka])) {
                unset($_COOKIE[$nazwaciastka]);
                setcookie($nazwaciastka, "", time() - 3600);
            }
        }
    }

    private static function dodajnazwyciastkapytania() {
        $licznikciastek = 1;
        $nazwaciastka = 'pytanie' . $licznikciastek;
        while (isset($_COOKIE[$nazwaciastka])) {
            array_push(self::$ciastka, $nazwaciastka);
            $licznikciastek = $licznikciastek + 1;
            $nazwaciastka = 'pytanie' . $licznikciastek;
        }
    }

}

?>
