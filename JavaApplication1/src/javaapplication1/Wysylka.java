/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Osito
 */
public class Wysylka {
     public static void zipfile(String filename) {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream("james.xml.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(filename);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(filename);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            //remember close it
            zos.close();
            System.out.println("Done");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String encryptAES(String filename) throws Exception {
        int iterations = 65536;
        int keySize = 256;
        removeCryptographyRestrictions();
        char[] plaintext = czytajplik(filename);
        byte[] saltBytes = getSalt().getBytes();
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
        SecretKey secretKey = skf.generateSecret(spec);
        saveprivatekey(secretKey);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));
        Files.write(Paths.get(filename + ".aes"), encryptedTextBytes);
        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }

    private static void saveprivatekey(SecretKey secretKey) throws Exception {
        PublicKey pk = readPublicKey("pubkey.pem");
        Cipher pkCipher = Cipher.getInstance("RSA");
        pkCipher.init(Cipher.ENCRYPT_MODE, pk);
        String file = "privatekey.key";
        CipherOutputStream os = new CipherOutputStream(new FileOutputStream(file), pkCipher);
        os.write(secretKey.getEncoded());
        os.close();
    }
    
    private static char[] czytajplik(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content.toCharArray();
    }

//    public static void hello() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
//        keyGenerator.init(128);
//        Key blowfishKey = keyGenerator.generateKey();
//
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(1024);
//        KeyPair keyPair = keyPairGenerator.genKeyPair();
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
//
//        byte[] blowfishKeyBytes = blowfishKey.getEncoded();
//        System.out.println(new String(blowfishKeyBytes));
//        byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
//        System.out.println(new String(cipherText));
//        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
//
//        byte[] decryptedKeyBytes = cipher.doFinal(cipherText);
//        System.out.println(new String(decryptedKeyBytes));
//        SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes, "Blowfish");
//    }

    public static byte[] readFileBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey readPublicKey(String filename) throws Exception {
        FileInputStream fin = new FileInputStream(filename);
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
        return certificate.getPublicKey();
//        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(publicSpec);
    }

    public static byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

    public static void main(String[] args) {
        try {
            removeCryptographyRestrictions();
            PublicKey publicKey = readPublicKey("D:\\Biuro\\JPK\\wysylka\\pubkey.pem");
            PrivateKey privateKey = readPrivateKey("private.der");
            byte[] message = "Hello World".getBytes("UTF8");
            byte[] secret = encrypt(publicKey, message);
            byte[] recovered_message = decrypt(privateKey, secret);
            System.out.println(new String(recovered_message, "UTF8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        try {
//            removeCryptographyRestrictions();
//            byte[] input = "Wiadomosc do zakodowania!".getBytes();
//            KeyGenerator kGen = KeyGenerator.getInstance("AES");
//            kGen.init(256);
//            SecretKey sKey = kGen.generateKey();
//            byte[] rawKey = sKey.getEncoded();
//            SecretKeySpec sKeySpec = new SecretKeySpec(rawKey, "AES");
//            // algorytm AES, tryb ECB, dope??nianie w PCKS#5
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
//            byte[] encrypted = cipher.doFinal(input);
//            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
//            byte[] decrypted = cipher.doFinal(encrypted);
//            print(input);
//            print(encrypted);
//            print(decrypted);
////  	System.out.println(MessageDigest.isEqual(input, decrypted));
//            System.out.println(Arrays.equals(input, decrypted));
//        } catch (Exception e) {
//            System.out.println("Error "+e.getMessage());
//        }
//    }
//    public static void print(byte[] b){
//        System.out.println(new String(b));
//        System.out.println("Length: " + b.length * 8);
//        System.out.println("---------------");
//    }
//    private static String salt;
//    private static int iterations = 65536  ;
//    private static int keySize = 256;
//    private static byte[] ivBytes;
//
//    private static SecretKey secretKey;
//
//    public static void main(String []args) throws Exception  {
//            salt = getSalt();
//            removeCryptographyRestrictions();
//            char[] message = "PasswordToEncrypt".toCharArray();
//            System.out.println("Message: " + String.valueOf(message));
//            System.out.println("Encrypted: " + encryptAES(message));
//            System.out.println("Decrypted: " + decrypt(encryptAES(message).toCharArray()));
//    }
//
//    public static String encryptAES(char[] plaintext) throws Exception {
//        byte[] saltBytes = salt.getBytes();
//
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
//        secretKey = skf.generateSecret(spec);
//        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
//        AlgorithmParameters params = cipher.getParameters();
//        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
//        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));
//
//        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
//    }
//
//    public static String decrypt(char[] encryptedText) throws Exception {
//
//        System.out.println(encryptedText);
//
//        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(new String(encryptedText));
//        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(ivBytes));
//
//        byte[] decryptedTextBytes = null;
//
//        try {
//            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
//        }   catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }   catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//
//        return new String(decryptedTextBytes);
//
//    }
//
    private static String getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return new String(salt);
    }

    private static void removeCryptographyRestrictions() {
        Logger logger = Logger.getLogger(JavaApplication1.class.getName());
        if (!isRestrictedCryptography()) {
            logger.fine("Cryptography restrictions removal not needed");
            return;
        }
        try {
            /*
         * Do the following, but with reflection to bypass access checks:
         *
         * JceSecurity.isRestricted = false;
         * JceSecurity.defaultPolicy.perms.clear();
         * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
             */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            logger.fine("Successfully removed cryptography restrictions");
        } catch (final Exception e) {
            logger.log(Level.WARNING, "Failed to remove cryptography restrictions", e);
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
}
