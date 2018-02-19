/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Osito
 */
public class RSACrypto {
    public static void main(String[] args) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "SunJSSE");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            System.out.println ( "Key decrypted, length is " + keyPair.getPublic().getEncoded().length );
            SecretKey sessionKey = new SecretKeySpec(new byte[16], "AES");
            
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] result1 = c.doFinal(sessionKey.getEncoded());
            
            c.init(Cipher.WRAP_MODE, keyPair.getPublic());
            byte[] result2 = c.wrap(sessionKey);
            System.out.println("blocksize "+c.getBlockSize());
            
            c.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
            SecretKey sessionKey1 = (SecretKey) c.unwrap(result1, "AES",
                    Cipher.SECRET_KEY);
            
            c.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            SecretKey sessionKey2 = new SecretKeySpec(c.doFinal(result2), "AES");
            
            System.out.println(Arrays.equals(sessionKey1.getEncoded(),
                    sessionKey2.getEncoded()));
            // encode data on your side using BASE64
            byte[]   bytesEncoded = Base64.getEncoder().encode("kijhygtrfdcvbsge".getBytes());
            System.out.println("ecncoded value is " + new String(bytesEncoded ));

            // Decode data on other side, by processing encoded data
            byte[] valueDecoded= Base64.getDecoder().decode("a2lqaHlndHJmZGN2YnNnZQ==".getBytes());
        } catch (Exception ex) {
            Logger.getLogger(RSACrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
