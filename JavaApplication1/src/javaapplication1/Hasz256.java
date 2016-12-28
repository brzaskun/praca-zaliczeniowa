/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Osito
 */
public class Hasz256 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(hasz("Jan"));
        System.out.println(hasz("Jana"));
        System.out.println(hasz("Jan"));
    }
    
    public static String hasz(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(hash);
    }
}
