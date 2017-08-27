/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.DigestAlgorithm;
import eu.europa.esig.dss.FileDocument;
import eu.europa.esig.dss.SignatureLevel;
import eu.europa.esig.dss.SignaturePackaging;
import eu.europa.esig.dss.SignatureValue;
import eu.europa.esig.dss.ToBeSigned;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs11SignatureToken;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.x509.CertificateToken;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class smartcard {

    public static void main(String[] args) throws IOException, CertificateException {
        try {
            String pkcs11config
                    = "name=SmartCardn"+"\r"
                    + "library=c:\\windows\\System32\\cryptoCertum3PKCS.dll";
            byte[] pkcs11configBytes = pkcs11config.getBytes();
            ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
            Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
            Security.addProvider(pkcs11Provider);
            char [] pin = "marlena1".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS11");
            try {
                keyStore.load(null, pin);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(smartcard.class.getName()).log(Level.SEVERE, null, ex);
            }
            Enumeration aliasesEnum = keyStore.aliases();
            String aliasfinal = null;
            PrivateKey privateKeyfinal = null;
            while (aliasesEnum.hasMoreElements()) {
               String alias = (String)aliasesEnum.nextElement();
               System.out.println("Alias: " + alias);
               X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
              // System.out.println("Certificate: " + cert);
               PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);
               System.out.println("Private key: " + privateKey);
               System.out.println("Data od: " + cert.getNotBefore());
               System.out.println("Data do: " + cert.getNotAfter());
               Date today = new Date();
               if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
                   aliasfinal = alias;
                   privateKeyfinal = (PrivateKey) keyStore.getKey(alias, null);
               }
               System.out.println("  Subject: " + cert.getSubjectDN());
               System.out.println("  Issued By: " + cert.getIssuerDN());
               if (keyStore.isKeyEntry(alias)) {
                    System.out.println("  Has private key");
               }
            }
            Certificate[] chain = keyStore.getCertificateChain(aliasfinal);
            Certificate cert = keyStore.getCertificate(aliasfinal);
            String typ = keyStore.getType();
            PublicKey pubkey = cert.getPublicKey();
            String type = cert.getType();
            // Choose the document to be signed.
            DSSDocument toSignDocument = new FileDocument("james.xml");
          // Preparing parameters for the XAdES signature
            XAdESSignatureParameters parameters = new XAdESSignatureParameters();
            // We choose the level of the signature (-B, -T, -LT, -LTA).
            parameters.setSignatureLevel(SignatureLevel.XAdES_BASELINE_B);
            // We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
            parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);
            // We set the digest algorithm to use with the signature algorithm. You must use the
            // same parameter when you invoke the method sign on the token. The default value is SHA256
            parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);

            // We set the signing certificate
            parameters.setSigningCertificate(new CertificateToken((X509Certificate) cert));
            // We set the certificate chain
            //parameters.setCertificateChain(chain);

            // Create common certificate verifier
            CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

            // Create XAdES service for signature
            XAdESService service = new XAdESService(commonCertificateVerifier);

            // Get the SignedInfo XML segment that need to be signed.
            ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

            // This function obtains the signature value for signed information using the
            // private key and specified algorithm
            char [] password = "marlena1".toCharArray();
            Pkcs11SignatureToken signingToken = new Pkcs11SignatureToken("c:\\windows\\System32\\cryptoCertum3PKCS.dll", password);
            DSSPrivateKeyEntry pkey = signingToken.getKeys().get(1);
            SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), pkey);

            // We invoke the service to sign the document with the signature value obtained in
            // the previous step.
            DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
            signedDocument.save(signedDocument.getName());
            System.out.println("Koniec ok");
        } catch (Exception ex) {
            Logger.getLogger(smartcard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//        KeyStore keyStore = KeyStore.getInstance(getKeyStoreType(), "SunMSCAPI");
//keyStore.load(null, null);
//
//try {
//    Field field = keyStore.getClass().getDeclaredField("keyStoreSpi");
//    field.setAccessible(true);
//
//    KeyStoreSpi keyStoreVeritable = (KeyStoreSpi)field.get(keyStore);
//    field = keyStoreVeritable.getClass().getEnclosingClass().getDeclaredField("entries");
//    field.setAccessible(true);
//} catch (Exception e) {
//    LOGGER.log(Level.SEVERE, "Set accessible keyStoreSpi problem", e);
//}
//
//Enumeration enumeration = keyStore.aliases();
//    }
}
