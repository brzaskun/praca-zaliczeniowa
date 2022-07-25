/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksef;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Osito
 */

public class TestEncryptWithPublicKey {
    private Logger logger = LoggerFactory.getLogger(TestEncryptWithPublicKey.class);
    private final File publicKeyFile;
    private final String authorisationToken;
    private final Date authorisationChallengeResponseTimestamp;

    public TestEncryptWithPublicKey(String timestamp) throws URISyntaxException, ParseException {
        //ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String realPath = ctx.getRealPath("/");
        //String path = realPath+"/resources/ksef/DemoPublicKey.der";
        this.publicKeyFile = new File("d://DemoPublicKey.der");
        this.authorisationToken = "06cc94651fd44853cf33e00a4c4d566d0af7e4c5fd395b3ceea05deace20acd8";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.authorisationChallengeResponseTimestamp = format.parse(timestamp);
    }
    
    public byte[] encrypt_with_public_key() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] messageEncrypted = null;
        byte[] publicKeyFileBytes = Files.readAllBytes(publicKeyFile.toPath());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyFileBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        byte[] message = (authorisationToken+"|"+authorisationChallengeResponseTimestamp.getTime()).getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        messageEncrypted = cipher.doFinal(message);
        return messageEncrypted;
    }
    
}
 