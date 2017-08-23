<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class SprawdzWprowadzanyWiersz {
    
   public static function sprawdz($wierszbaza) {
        $zwrot = false;
        if (is_array($wierszbaza)) {
            $zwrot = true;
            if ($wierszbaza[0]=='id') {
                $zwrot = false;
            } else if ($wierszbaza[1]=='email') {
                $zwrot = false;
            } else if ($wierszbaza[2]=='imię i nazwisko') {
                $zwrot = false;
            } else if ($wierszbaza[3]=='nr upoważnienia') {
                $zwrot = false;
            }
        } else { 
            $zwrot = false;
        }
        return $zwrot;
    } 
     
    public static function sprawdz2($wierszbaza) {
        $zwrot = false;
        if (is_array($wierszbaza)) {
            $zwrot = true;
            if ($wierszbaza[0]=='adres email') {
                $zwrot = false;
            } else if ($wierszbaza[1]=='imię i nazwisko') {
                $zwrot = false;
            } else if ($wierszbaza[2]=='płeć') {
                $zwrot = false;
            }
        } else {
            $zwrot = false;
        }
        return $zwrot;
    } 
}
 
?>
