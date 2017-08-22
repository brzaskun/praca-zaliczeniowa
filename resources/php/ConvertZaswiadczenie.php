<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class ConvertZaswiadczenie {

    public static function toId($nazwa) {
        return (int)R::getCell("SELECT id FROM zaswiadczenia WHERE nazwa = '$nazwa'");
    }
    
    public static function toName($id_zaswiadczenie) {
        return R::getCell("SELECT nazwa FROM zaswiadczenia WHERE id = '$id_zaswiadczenie'");
    }
}