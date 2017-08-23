<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class ConvertNames {

    public static function cn($text) {
        $text = preg_replace("/[ą]/u","a",$text);
        $text = preg_replace("/[Ą]/u","A",$text);
        $text = preg_replace("/[ę]/u","e",$text);
        $text = preg_replace("/[Ę]/u","E",$text);
        $text = preg_replace("/[ó]/u","o",$text);
        $text = preg_replace("/[Ó]/u","O",$text);
        $text = preg_replace("/[ś]/u","s",$text);
        $text = preg_replace("/[Ś]/u","S",$text);
        $text = preg_replace("/[ł]/u","l",$text);
        $text = preg_replace("/[Ł]/u","L",$text);
        $text = preg_replace("/[ń]/u","n",$text);
        $text = preg_replace("/[Ń]/u","N",$text);
        $text = preg_replace("/[ż]/u","z",$text);
        $text = preg_replace("/[Ż]/u","Z",$text);
        $text = preg_replace("/[ź]/u","z",$text);
        $text = preg_replace("/[Ź]/u","Z",$text);
        $text = preg_replace("/[ć]/u","c",$text);
        $text = preg_replace("/[Ć]/u","C",$text);
        $text = preg_replace('/\s+/', '', $text);
        return $text;
    }
}