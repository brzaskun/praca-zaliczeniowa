/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import static jpkview.WysylkaSub.removeCryptographyRestrictions;


/**
 *
 * @author Osito
 */
public class AESCryptoOld {
    private static String mainfilename = "indyk.xml";
    private static String zipfilename = "james2.zip";
    private static String partfilename = "james2.zip.aes";
    
    public static void main(String[] args) {
        try {
            //String text = new String(Files.readAllBytes(Paths.get(mainfilename)));
            String text = "RandomInitżęśćóć";
            SecretKey secretKey = encryptAESStart(text);
            Object[] zwrot = encryptKoniec(text, secretKey);
            decrypt(secretKey, (byte[])zwrot[0], (byte[])zwrot[1]);
        } catch (Exception ex) {
            Logger.getLogger(AESCryptoOld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static SecretKey encryptAESStart(String text) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        int iterations = 65536;
//        int keySize = 256;
//        removeCryptographyRestrictions();
//        char[] plaintext = text.toCharArray();
//        byte[] saltBytes = getSalt().getBytes();
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
//        SecretKey secretKey = skf.generateSecret(spec);
        removeCryptographyRestrictions();
        KeyGenerator key = KeyGenerator.getInstance("AES");
        key.init(256);
//        SecretKey s = key.generateKey();
//        byte[] raw = s.getEncoded();
//        SecretKeySpec skeySpec= new SecretKeySpec(raw, "AES");
        return key.generateKey();
    }
    
    
    public static Object[] encryptKoniec(String text, SecretKey skey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skey);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        System.out.println("iv size "+ivBytes.length);
        byte[] encryptedTextBytes = Base64.getEncoder().encode(cipher.doFinal(text.getBytes("UTF-8")));
        byte[] ivencoded = Base64.getEncoder().encode(ivBytes);
        Object[] zwrot = new Object[2];
        zwrot[0] = encryptedTextBytes;
        Files.write(Paths.get("zakodowana.aes"), encryptedTextBytes);
        zwrot[1] = ivencoded;
        return zwrot;
    }
    
    public static void decrypt(SecretKey key, byte[] cf, byte[] ivBytes){
        try {
            Path path = Paths.get("zakodowana.aes");
            byte[] ciphertext = Files.readAllBytes(path);
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivBytes));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(Base64.getDecoder().decode(ciphertext));
        } catch (Exception ex) {
        }
    }
    
     private static String getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return new String(salt);
    }
}
