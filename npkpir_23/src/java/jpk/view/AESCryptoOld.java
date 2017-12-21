/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Osito
 */
public class AESCryptoOld {
    private static String mainfilename = "indyk.xml";
    private static String zipfilename = "james2.xml.zip";
    private static String partfilename = "james2.xml.zip.aes";
    
    public static void main(String[] args) {
        try {
            //String text = new String(Files.readAllBytes(Paths.get(mainfilename)));
            String text = "kotek plotek";
            System.out.println("text "+text);
            SecretKeySpec secretKeySpec = encryptAESStart(text);
            Object[] zwrot = encryptKoniec(text, secretKeySpec);
            decrypt(secretKeySpec, (byte[])zwrot[0], (byte[])zwrot[1]);
        } catch (Exception ex) {
            Logger.getLogger(AESCryptoOld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static SecretKeySpec encryptAESStart(String text) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        int iterations = 65536;
//        int keySize = 256;
//        removeCryptographyRestrictions();
//        char[] plaintext = text.toCharArray();
//        byte[] saltBytes = getSalt().getBytes();
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
//        SecretKey secretKey = skf.generateSecret(spec);
        SecretKeySpec skeySpec = new SecretKeySpec("RandomInitKeySCS".getBytes("UTF-8"), "AES");
        return skeySpec;
    }
    
    
    public static Object[] encryptKoniec(String text, SecretKeySpec skeySpec) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = Base64.getEncoder().encode(cipher.doFinal(text.getBytes("UTF-8")));
        byte[] ivencoded = Base64.getEncoder().encode(ivBytes);
        Object[] zwrot = new Object[2];
        zwrot[0] = encryptedTextBytes;
        System.out.println("encrypt "+new String(encryptedTextBytes));
        zwrot[1] = ivencoded;
        return zwrot;
    }
    
    public static void decrypt(SecretKey key, byte[] ciphertext, byte[] ivBytes){
        try {
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivBytes));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(Base64.getDecoder().decode(ciphertext));
            System.out.println("decrypt: "+new String(original));
        } catch (Exception ex) {
            System.out.println("decryot "+ex);
        }
    }
    
     private static String getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return new String(salt);
    }
}
