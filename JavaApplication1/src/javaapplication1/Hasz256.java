/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Osito
 */
public class Hasz256 {
//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        System.out.println(hasz("Jan"));
//        System.out.println(hasz("Jana"));
//        System.out.println(hasz("Jan"));
//    }
    
    public static String hasz(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        byte[] encodedBytes = Base64.encodeBase64(text.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes);
    }
    
    public static void main(String[] args) {
        FileInputStream fisTargetFile = null;
        try {
            fisTargetFile = new FileInputStream(new File("d:/vpa3.xml"));
            String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
            String lo = hasz(targetFileStr);
            System.out.println("d: "+lo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hasz256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hasz256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hasz256.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fisTargetFile.close();
            } catch (IOException ex) {
                Logger.getLogger(Hasz256.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
