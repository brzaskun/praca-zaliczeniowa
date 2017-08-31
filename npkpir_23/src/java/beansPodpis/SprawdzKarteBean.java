/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPodpis;

import error.E;
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
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

/**
 *
 * @author Osito
 */
public class SprawdzKarteBean {
    
    static String HASLO = "marlena1";
    static String PLIK = "james.xml";
    static String DRIVER = "c:\\windows\\System32\\cryptoCertum3PKCS.dll";
    
    public static Boolean jestDriver() {
        Boolean zwrot = null;
        try {
            String pkcs11config = "name=SmartCardn"+"\r"
                    + "library="+DRIVER;
            byte[] pkcs11configBytes = pkcs11config.getBytes();
            ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
            Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
            Security.addProvider(pkcs11Provider);
            zwrot = true;
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static KeyStore jestKarta(String haslo) {
        KeyStore keyStore = null;
        try {
            boolean zwrot = false;
            char [] pin = haslo.toCharArray();
            keyStore = KeyStore.getInstance("PKCS11");
            keyStore.load(null, pin);
        }   catch (KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            E.e(ex);
        } catch (IOException ex) {
            E.e(ex);
        }
        return keyStore;
    }
    
    public static String aktualnyAlias(KeyStore keyStore) {
        String aliasfinal = null;
        try {
            Enumeration aliasesEnum = keyStore.aliases();
            while (aliasesEnum.hasMoreElements()) {
                String alias = (String)aliasesEnum.nextElement();
                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                // System.out.println("Certificate: " + cert);
                Date today = new Date();
                if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
                    aliasfinal = alias;
                    System.out.println("Data od: " + cert.getNotBefore());
                    System.out.println("Data do: " + cert.getNotAfter());
                }
            }
        } catch (KeyStoreException ex) {
            E.e(ex);
        }
        return aliasfinal;
    }
    
    public static Certificate certyfikat(String aliasfinal, KeyStore keyStore) {
        Certificate cert = null;
        try {
            cert = keyStore.getCertificate(aliasfinal);
        } catch (KeyStoreException ex) {
            E.e(ex);
        }
        return cert;
    }
    
    public static DSSDocument dokument(String nazwapliku) {
        DSSDocument toSignDocument = null;
        File f = new File(nazwapliku);
        if(f.exists() && !f.isDirectory()) { 
            toSignDocument = new FileDocument(nazwapliku);
        }
        return toSignDocument;
    }
    
    public static XAdESSignatureParameters ustawparametry(Certificate cert) {
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
        return parameters;
    }
    
    public static XAdESService podpisywacz() {
        CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
        XAdESService service = new XAdESService(commonCertificateVerifier);
        return service;
    }
    
    public static ToBeSigned danedoPodpisu(DSSDocument toSignDocument, XAdESSignatureParameters parameters) {
        // Create XAdES service for signature
        XAdESService service = podpisywacz();
        // Get the SignedInfo XML segment that need to be signed.
        ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);
        return dataToSign;
    }
    
    public static SignatureValue generujpodpis(ToBeSigned dataToSign,XAdESSignatureParameters parameters) {
        // This function obtains the signature value for signed information using the
        // private key and specified algorithm
        char [] password = "marlena1".toCharArray();
        Pkcs11SignatureToken signingToken = new Pkcs11SignatureToken(DRIVER, password);
        DSSPrivateKeyEntry pkey = signingToken.getKeys().get(1);
        SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), pkey);
        return signatureValue;
    }
    
    public static String podpisz(XAdESService service, DSSDocument toSignDocument, XAdESSignatureParameters parameters, SignatureValue signatureValue) {
        String nazwa = null;
        try {
            DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
            nazwa = signedDocument.getName();
            signedDocument.save(nazwa);
        } catch (IOException ex) {
            E.e(ex);
        }
        return nazwa;
    }
    
    public static void drukuj(Object o, String opis) {
        if (o != null) {
            System.out.println("jest "+opis);
        } else {
            System.out.println("nie ma "+opis);
        }
    }
    
    
    
    public static void main(String[] args) {
        Boolean driver = jestDriver();
        drukuj(driver,"driver");
        KeyStore keyStore = jestKarta(HASLO);
        drukuj(keyStore,"karta");
        String alias = aktualnyAlias(keyStore);
        System.out.println("symbol aktualnego certyfikatu "+alias);
        Certificate cert = certyfikat(alias, keyStore);
        drukuj(cert,"certyfikat");
        DSSDocument dok = dokument(PLIK);
        drukuj(dok,"dokument");
        XAdESSignatureParameters parameters = ustawparametry(cert);
        drukuj(parameters,"parametry");
        ToBeSigned toBeSigned = danedoPodpisu(dok, parameters);
        drukuj(toBeSigned,"dane do podpisu");
        SignatureValue signatureValue = generujpodpis(toBeSigned, parameters);
        drukuj(signatureValue,"podpis");
        String nazwapodpisana = podpisz(podpisywacz(), dok, parameters, signatureValue);
        drukuj(nazwapodpisana,"podpisano");
    }
}
