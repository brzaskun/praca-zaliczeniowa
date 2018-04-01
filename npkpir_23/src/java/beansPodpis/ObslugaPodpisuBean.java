/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPodpis;

import error.E;
import java.io.ByteArrayInputStream;
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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Osito
 */
public class ObslugaPodpisuBean {
    
    static String HASLO = "marlena1";
//    static String PLIK = "james.xml";
    static String DRIVER = "resources\\podpis\\cryptoCertum3PKCS.dll";
//  
    public static boolean moznapodpisacjpk() {
        boolean zwrot = false;
        Provider provider = ObslugaPodpisuBean.jestDriver();
        if (provider!=null) {
            KeyStore keyStore = ObslugaPodpisuBean.jestKarta(HASLO);
            if (provider != null && keyStore != null) {
                zwrot = true;
            }
        }
        return zwrot;
    }
    
    public static Provider jestDriver() {
        Provider pkcs11Provider = null;
        int proba = 0;
        try {
            do {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+DRIVER;
                String pkcs11config = "name=SmartCardn"+"\r"
                        + "library="+realPath;
                byte[] pkcs11configBytes = pkcs11config.getBytes("UTF-8");
                ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
                pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
                Security.addProvider(pkcs11Provider);
                proba++;
            } while (proba < 3 && pkcs11Provider==null);
        } catch (Exception e) {
            E.e(e);
        }
        return pkcs11Provider;
    }
    
    public static KeyStore jestKarta(String haslo) {
        KeyStore keyStore = null;
        try {
            char [] pin = haslo.toCharArray();
            keyStore = KeyStore.getInstance("PKCS11");
            keyStore.load(null, pin);
        }   catch (KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            keyStore = null;
            E.e(ex);
        } catch (Exception ex) {
            keyStore = null;
            E.e(ex);
        }
        return keyStore;
    }
    
    public static String aktualnyAlias(KeyStore keyStore) {
        String aliasfinal = null;
        try {
            Enumeration aliasesEnum = keyStore.aliases();
            Date today = new Date();
            while (aliasesEnum.hasMoreElements()) {
                String alias = (String)aliasesEnum.nextElement();
                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                //FileUtils.writeStringToFile(new File("D:\\cert"+alias+".xml"), cert.toString());
                // System.out.println("Certificate: " + cert);
                if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
                    aliasfinal = alias;
                    //break;
                }
            }
        } catch (KeyStoreException ex) {
            E.e(ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aliasfinal;
    }
    
    public static Certificate certyfikat(String aliasfinal, KeyStore keyStore) {
        Certificate cert = null;
        try {
            cert = keyStore.getCertificate(aliasfinal);
            keyStore.getCertificateChain(aliasfinal);
        } catch (KeyStoreException ex) {
            E.e(ex);
        }
        return cert;
    }
    
//    private static String aktualnyAlias(KeyStore keyStore) {
//        String aliasfinal = null;
//        try {
//            Enumeration aliasesEnum = keyStore.aliases();
//            Date today = new Date();
//            while (aliasesEnum.hasMoreElements()) {
//                String alias = (String)aliasesEnum.nextElement();
//                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
//                //FileUtils.writeStringToFile(new File("D:\\cert"+alias+".xml"), cert.toString());
//                // System.out.println("Certificate: " + cert);
//                if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
//                    aliasfinal = alias;
//                    System.out.println(cert.toString());
//                    System.out.println("Data od: " + cert.getNotBefore());
//                    System.out.println("Data do: " + cert.getNotAfter());
//                    //break;
//                }
//            }
//        } catch (KeyStoreException ex) {
//            E.e(ex);
////        } catch (IOException ex) {
////            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return aliasfinal;
//    }
//    
//    private static Certificate certyfikat(String aliasfinal, KeyStore keyStore) {
//        Certificate cert = null;
//        try {
//            cert = keyStore.getCertificate(aliasfinal);
//            keyStore.getCertificateChain(aliasfinal);
//        } catch (KeyStoreException ex) {
//            E.e(ex);
//        }
//        return cert;
//    }
//    
//       
//    private static DSSDocument dokument(String nazwapliku) {
//        DSSDocument toSignDocument = null;
//        File f = new File(nazwapliku);
//        if(f.exists() && !f.isDirectory()) { 
//            toSignDocument = new FileDocument(nazwapliku);
//        }
//        return toSignDocument;
//    }
//    
//    private static DSSDocument dokumentzXML(String xml) {
//        MimeType m = MimeType.XML;
//        DSSDocument toSignDocument = null;
//        try {
//            toSignDocument = new InMemoryDocument(xml.getBytes("UTF-8"), "UTF-8", m);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return toSignDocument;
//    }
//    
//    private static XAdESSignatureParameters ustawparametry(Certificate cert) {
//        XAdESSignatureParameters parameters = new XAdESSignatureParameters();
//        // We choose the level of the signature (-B, -T, -LT, -LTA).
//        parameters.setSignatureLevel(SignatureLevel.XAdES_BASELINE_B);
//        BLevelParameters bLevelParameters = new BLevelParameters();
//        // We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
//        parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);
//        // We set the digest algorithm to use with the signature algorithm. You must use the
//        // same parameter when you invoke the method sign on the token. The default value is SHA256
//        parameters.setDigestAlgorithm(DigestAlgorithm.SHA1);
//           // We set the signing certificate
//        parameters.setSigningCertificate(new CertificateToken((X509Certificate) cert));
//        parameters.setSigningCertificateDigestMethod(DigestAlgorithm.SHA1);
//        parameters.setEncryptionAlgorithm(EncryptionAlgorithm.RSA);
////        parameters.setSignedInfoCanonicalizationMethod("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
//        parameters.setSignedInfoCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
//        parameters.setSignedPropertiesCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
////        parameters.getReferences().get(0).getTransforms().get(0).setAlgorithm("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
//        return parameters;
//    }
//    
//    private static XAdESService podpisywacz() {
//        CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
//        XAdESService service = new XAdESService(commonCertificateVerifier);
//        return service;
//    }
//    
//    private static ToBeSigned danedoPodpisu(DSSDocument toSignDocument, XAdESSignatureParameters parameters) {
//        // Create XAdES service for signature
//        XAdESService service = podpisywacz();
//       // Get the SignedInfo XML segment that need to be signed.
//        ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);
//        
//        return dataToSign;
//    }
//    
//    private static SignatureValue generujpodpis(ToBeSigned dataToSign,XAdESSignatureParameters parameters) {
//        // This function obtains the signature value for signed information using the
//        // private key and specified algorithm
//        char [] password = HASLO.toCharArray();
//        Pkcs11SignatureToken signingToken = new Pkcs11SignatureToken(DRIVER, password);
//        DSSPrivateKeyEntry pkey = signingToken.getKeys().get(1);
//        SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), pkey);
//        return signatureValue;
//    }
//    
//    private static String podpisz(XAdESService service, DSSDocument toSignDocument, XAdESSignatureParameters parameters, SignatureValue signatureValue) {
//        String nazwa = null;
//        try {
//            DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
//            nazwa = signedDocument.getName();
//            signedDocument.save(nazwa);
//        } catch (IOException ex) {
//            E.e(ex);
//        }
//        return nazwa;
//    }
//    
//    private static byte[] podpiszIS(XAdESService service, DSSDocument toSignDocument, XAdESSignatureParameters parameters, SignatureValue signatureValue) {
//        String xml = null;
//        byte[] bytes = null;
//        try {
//            String s = parameters.getReferences().get(0).getTransforms().get(0).getAlgorithm();
//            DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
//            bytes = IOUtils.toByteArray(signedDocument.openStream());
//        } catch (IOException ex) {
//            E.e(ex);
//        }
//        return bytes;
//    }
//    
//    private static String podpiszISString(XAdESService service, DSSDocument toSignDocument, XAdESSignatureParameters parameters, SignatureValue signatureValue) {
//        String xml = null;
//        try {
//            parameters.getReferences().get(0).getTransforms().get(0).setAlgorithm("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
//            parameters.getReferences().get(0).setDigestMethodAlgorithm(null);
//            DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
//            OutputStream out = new ByteArrayOutputStream();  
//            signedDocument.writeTo(out);
//            xml = out.toString();
//        } catch (IOException ex) {
//            E.e(ex);
//        }
//        return xml;
//    }
//    
//    private static void drukuj(Object o, String opis) {
//        if (o != null) {
//            System.out.println("jest "+opis);
//            } else {
//            System.out.println("nie ma "+opis);
//        }
//    }
//    
    public static boolean moznaPodpisac() {
        boolean zwrot = false;
        Provider provider = jestDriver();
        KeyStore keyStore = jestKarta(HASLO);
        if (provider != null && keyStore != null) {
            zwrot = true;
        }
        Security.removeProvider("SmartCardn");
        return zwrot;
    }
    
//        
//    public static String podpiszDeklaracje(String xml) {
//        //xml = StringEscapeUtils.escapeJava(xml);
//        DSSTransform d = new DSSTransform();
//        Provider provider = jestDriver();
//        KeyStore keyStore = jestKarta(HASLO);
//        String alias = aktualnyAlias(keyStore);
//        System.out.println("symbol aktualnego certyfikatu "+alias);
//        Certificate cert = certyfikat(alias, keyStore);
//        DSSDocument dok = dokumentzXML(xml);
//        XAdESSignatureParameters parameters = ustawparametry(cert);
//        ToBeSigned toBeSigned = danedoPodpisu(dok, parameters);
//        SignatureValue signatureValue = generujpodpis(toBeSigned, parameters);
//        String xmlpod = podpiszISString(podpisywacz(), dok, parameters, signatureValue);
//        //xmlpod = StringEscapeUtils.unescapeJava(xmlpod);
//        return xmlpod;
//    }
//    
//    public static byte[] podpiszDeklaracjeByte(String xml) {
//        //xml = StringEscapeUtils.escapeJava(xml);
//        Provider provider = jestDriver();
//        KeyStore keyStore = jestKarta(HASLO);
//        String alias = aktualnyAlias(keyStore);
//        System.out.println("symbol aktualnego certyfikatu "+alias);
//        Certificate cert = certyfikat(alias, keyStore);
//        DSSDocument dok = dokumentzXML(xml);
//        XAdESSignatureParameters parameters = ustawparametry(cert);
//        ToBeSigned toBeSigned = danedoPodpisu(dok, parameters);
//        SignatureValue signatureValue = generujpodpis(toBeSigned, parameters);
//        String xmlpod = podpiszISString(podpisywacz(), dok, parameters, signatureValue);
//        return podpiszIS(podpisywacz(), dok, parameters, signatureValue);
//        //xmlpod = StringEscapeUtils.unescapeJava(xmlpod);
//    }
//    
 //   public static void main(String[] args) {
//        try {
            //        Boolean driver = jestDriver();
//        drukuj(driver,"driver");
//        KeyStore keyStore = jestKarta(HASLO);
//        drukuj(keyStore,"karta");
//        boolean mozna = moznaPodpisac();
//        if (mozna) {
//            KeyStore keyStore = jestKarta(HASLO);
//            String alias = aktualnyAlias(keyStore);
//            System.out.println("symbol aktualnego certyfikatu "+alias);
//            Certificate cert = certyfikat(alias, keyStore);
//            drukuj(cert,"certyfikat");
//            DSSDocument dok = dokument(PLIK);
//            drukuj(dok,"dokument");
//            XAdESSignatureParameters parameters = ustawparametry(cert);
//            drukuj(parameters,"parametry");
//            ToBeSigned toBeSigned = danedoPodpisu(dok, parameters);
//            drukuj(toBeSigned,"dane do podpisu");
//            SignatureValue signatureValue = generujpodpis(toBeSigned, parameters);
//            drukuj(signatureValue,"podpis");
//            String nazwapodpisana = podpisz(podpisywacz(), dok, parameters, signatureValue);
//            drukuj(nazwapodpisana,"podpisano");
//        } else {
//            System.out.println("brak karty");
//        }
//            DSSDocument dok = dokumentzXML("lolo");
//            drukuj(dok,"dokument");
//        String d = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Deklaracja xmlns=\"http://crd.gov.pl/wzor/2016/08/05/3412/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/\"><Naglowek><KodFormularza kodSystemowy=\"VAT-7 (17)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</KodFormularza><WariantFormularza>17</WariantFormularza><CelZlozenia poz=\"P_7\">1</CelZlozenia><Rok>2017</Rok><Miesiac>07</Miesiac><KodUrzedu>3215</KodUrzedu></Naglowek><Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>8521011364</etd:NIP><etd:ImiePierwsze>WOJCIECH</etd:ImiePierwsze><etd:Nazwisko>KADRZY\\u0143S\\u0104\\u0118\\u00D3\\u0141KI</etd:Nazwisko><etd:DataUrodzenia>1965-06-04</etd:DataUrodzenia></etd:OsobaFizyczna></Podmiot1><PozycjeSzczegolowe><P_10>8745</P_10><P_17>22117</P_17><P_18>1769</P_18><P_19>489517</P_19><P_20>112589</P_20><P_23>38661</P_23><P_24>8892</P_24><P_40>559040</P_40><P_41>123250</P_41><P_42>0</P_42><P_43>0</P_43><P_44>0</P_44><P_45>167494</P_45><P_46>38398</P_46><P_51>38398</P_51><P_52>0</P_52><P_54>84852</P_54><P_55>0</P_55><P_56>0</P_56><P_57>0</P_57><P_58>0</P_58><P_59>0</P_59><P_60>0</P_60><P_61>0</P_61><P_74>2017-09-04</P_74></PozycjeSzczegolowe><Pouczenia>1</Pouczenia></Deklaracja>";
//        FileInputStream fisTargetFile = new FileInputStream(new File("d:/vat7a.xml"));
//        String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
//        String xml = podpiszDeklaracje(targetFileStr);
//        FileUtils.writeStringToFile(new File("D:/plik.xml"), xml);
//        System.out.println("info "+xml);
//        } catch (IOException ex) {
//            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}    
}
