/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 *
 * @author Osito
 */
public class Chyba {

    public static void main(String[] args) {
        try {
            KeyStore keyStore = KeyStore.getInstance("Windows-MY");
            keyStore.load(null, null);  // Load keystore
            Certificate[] chain = keyStore.getCertificateChain("mykey");
            Certificate cert = keyStore.getCertificate("mykey");
            String typ = keyStore.getType();
            PublicKey pubkey = cert.getPublicKey();
            String type = cert.getType();
            System.out.println("sss "+type);
            // Choose the document to be signed.
            DSSDocument toSignDocument = new FileDocument("/xml_example.xml");
            // Generate the private key and certificate chain
//            CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA256withRSA");
//            certAndKeyGen.generate(1024);
//
//            Key key = certAndKeyGen.getPrivateKey();
//            X509Certificate cert = certAndKeyGen.getSelfCertificate(new X500Name("CN=JavaSecurity"), 365 * 24 * 60 * 60);
//            X509Certificate[] chain = new X509Certificate[]{cert};
//
//            keyStore.setKeyEntry("mykey", key, null, chain);
//
//            keyStore.store(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
