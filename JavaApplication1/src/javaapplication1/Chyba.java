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
import eu.europa.esig.dss.ToBeSigned;
import eu.europa.esig.dss.token.Pkcs11SignatureToken;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.x509.CertificateToken;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import sun.security.x509.X509CertImpl;


/**
 *
 * @author Osito
 */
public class Chyba {

    public static void main(String[] args) {
        try {
            KeyStore keyStore = KeyStore.getInstance("Windows-MY","SunMSCAPI");
            keyStore.load(null, null);
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = (String) aliases.nextElement();
                X509CertImpl certificate = (X509CertImpl) keyStore.getCertificate(alias);
                System.out.println("Alias: " + alias);
                System.out.println("  Subject: " + certificate.getSubjectDN());
                System.out.println("  Issued By: " + certificate.getIssuerDN());
                if (keyStore.isKeyEntry(alias)) {
                    System.out.println("  Has private key");
                }
            }
            Certificate[] chain = keyStore.getCertificateChain("Grzegorz Grzelczyk (1)");
            Certificate cert = keyStore.getCertificate("Grzegorz Grzelczyk (1)");
            String typ = keyStore.getType();
            PublicKey pubkey = cert.getPublicKey();
            String type = cert.getType();
            // Choose the document to be signed.
            DSSDocument toSignDocument = new FileDocument("/xml_example.xml");
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("Grzegorz Grzelczyk (1)", null);
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
            Pkcs11SignatureToken signingToken = new Pkcs11SignatureToken(type, password);
            //SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), privateKey);

            // We invoke the service to sign the document with the signature value obtained in
            // the previous step.
            //DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//    
//    public static void main(String[] args) {
//        try {
//            KeyStore ks = KeyStore.getInstance("Windows-MY");
//            ks.load(null, null);
//            Enumeration en = ks.aliases();
//            while (en.hasMoreElements()) {
//                String aliasKey = (String) en.nextElement();
//                Certificate c = ks.getCertificate(aliasKey);
//                System.out.println("---> alias : " + aliasKey);
//                if (ks.isKeyEntry(aliasKey)) {
//                    Certificate[] chain = ks.getCertificateChain(aliasKey);
//                    System.out.println("---> chain length: " + chain.length);
//                    for (Certificate cert : chain) {
//                        System.out.println(cert);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Chyba.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
