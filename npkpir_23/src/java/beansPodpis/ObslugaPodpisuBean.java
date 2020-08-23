/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPodpis;

import error.E;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Osito
 */
public class ObslugaPodpisuBean {
    
//    static String PLIK = "james.xml";
    static String DRIVER = "resources\\\\podpis\\\\cryptoCertum3PKCS.dll";
    static Map<Integer, String> odpowiedz;
//  
    public static boolean moznapodpisacError(String innehaslo, String innypesel) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        resetujodpowiedz();
        String haslo = inneHaslo(innehaslo);
        String pesel = innyPesel(innypesel);
        boolean zwrot = false;
        Provider provider = ObslugaPodpisuBean.jestCzytnikDriver();
        if (provider!=null) {
            KeyStore keyStore = ObslugaPodpisuBean.jestKartaPodpisy(haslo, pesel, provider);
            if (provider != null && keyStore != null) {
                zwrot = true;
            }
        } else {
            
        }
        return zwrot;
    }
    
    private static void resetujodpowiedz() {
        Security.removeProvider("SmartCardn");
        odpowiedz = new HashMap<>();
        odpowiedz.put(0, null);
        odpowiedz.put(1, null);
        odpowiedz.put(2, null);
        odpowiedz.put(3, null);
        odpowiedz.put(4, null);
        odpowiedz.put(5, null);
        odpowiedz.put(6, null);
        odpowiedz.put(7, null);
    }
    
    public static Provider jestCzytnikDriver() {
        Provider pkcs11Provider = null;
        int proba = 0;
        try {
            do {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/").replace("\\", "\\\\")+DRIVER;
                //String realPath = "C:\\Windows\\System32\\cryptoCertum3PKCS.dll";
                String pkcs11config = "name=SmartCardn"+"\r"
                        + "library="+realPath+"\r"+ "slotListIndex=0";
                byte[] pkcs11configBytes = pkcs11config.getBytes("UTF-8");
                ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
                pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
                Security.removeProvider(pkcs11Provider.getName());
                Security.addProvider(pkcs11Provider);
                proba++;
            } while (proba < 3 && pkcs11Provider==null);
            if (pkcs11Provider!=null) {
                odpowiedz.put(0, "tak");
            }
        } catch (Exception e) {
            error.E.s("Brak wpiętego czytnika karty/pendriva");
        } 
        return pkcs11Provider;
    }
    
    public static KeyStore jestKarta(String haslo, String pesel, Provider provider) {
        KeyStore keyStore = null;
        try {
            char [] pin = haslo.toCharArray();
            KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pin);
            keyStore = KeyStore.getInstance("PKCS11", provider);
            odpowiedz.put(1, "tak");
            if (keyStore!=null) {
                int proba = 0;
                do {
                    try {
                        keyStore.load(null, pp.getPassword());
                    } catch (IOException ee)
                    {}
                    proba++;
                } while (proba < 2); 
                if (keyStore == null) {
                    throw new IOException();
                }
            }
            odpowiedz.put(3, "tak");
            sprawdzcertyfikat(keyStore, pesel);
        } catch (KeyStoreException ex) {
            Security.removeProvider(provider.getName());
            keyStore = null;
            error.E.s("Brak karty w czytniku");
        } catch (IOException ex) {
            Security.removeProvider(provider.getName());
            keyStore = null;
            error.E.s("Błędne hasło!");
        } catch (Exception ex) {
            Security.removeProvider(provider.getName());
            keyStore = null;
            E.e(ex);
        }
        return keyStore;
    }
    
    public static KeyStore jestKartaPodpisy(String haslo, String pesel, Provider provider) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = null;
        char [] pin = haslo.toCharArray();
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pin);
        keyStore = KeyStore.getInstance("PKCS11", provider);
        odpowiedz.put(1, "tak");
        if (keyStore!=null) {
            int proba = 0;
            do {
                    try {
                        keyStore.load(null, pp.getPassword());
                    } catch (IOException ee)
                    {}
                    proba++;
                } while (proba < 2); 
                if (keyStore == null) {
                    throw new IOException();
                }
        }
        odpowiedz.put(3, "tak");
        sprawdzcertyfikat(keyStore, pesel);
        return keyStore;
    }
    
    
    private static String sprawdzcertyfikat(KeyStore keyStore, String pesel) throws KeyStoreException {
        String zwrot = "tak";
        Enumeration<String> enumeration = keyStore.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = enumeration.nextElement();
            Date certExpiryDate = ((X509Certificate) keyStore.getCertificate(alias)).getNotAfter();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            //Tue Oct 17 06:02:22 AEST 2006
            Date today = new Date();
            long dateDiff = certExpiryDate.getTime() - today.getTime();
            long expiresIn = dateDiff / (24 * 60 * 60 * 1000);
            String name = ((X509Certificate) keyStore.getCertificate(alias)).getSubjectDN().getName();
            odpowiedz.put(5,name);
            if (name.contains(pesel) && expiresIn>0) {
                zwrot = "tak";
                odpowiedz.put(2, "tak");
                odpowiedz.put(4, String.valueOf(expiresIn));
                break;
            } else if (name.contains(pesel) && expiresIn<=0) {
                zwrot = "Zainstalowany certyfikat wygasł";
                odpowiedz.put(2, zwrot);
                error.E.s("Zainstalowany certyfikat wygasł");
            } else if (!name.contains("70052809810")) {
                zwrot = "Na serwerze nie zainstalowano certyfikatu dla podpisującego";
                odpowiedz.put(2, zwrot);
                error.E.s("Na serwerze nie zainstalowano certyfikatu dla podpisującego");
            }
            // error.E.s("Certifiate: " + alias + "\tExpires On: " + certExpiryDate + "\tFormated Date: " + ft.format(certExpiryDate) + "\tToday's Date: " + ft.format(today) + "\tExpires In: "+ expiresIn);
        }
        if (zwrot!="tak") {
            keyStore = null;
        }
        return zwrot;
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
                // error.E.s("Certificate: " + cert);
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
    
//<editor-fold defaultstate="collapsed" desc="comment">
//    private static String aktualnyAlias(KeyStore keyStore) {
//        String aliasfinal = null;
//        try {
//            Enumeration aliasesEnum = keyStore.aliases();
//            Date today = new Date();
//            while (aliasesEnum.hasMoreElements()) {
//                String alias = (String)aliasesEnum.nextElement();
//                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
//                //FileUtils.writeStringToFile(new File("D:\\cert"+alias+".xml"), cert.toString());
//                // error.E.s("Certificate: " + cert);
//                if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
//                    aliasfinal = alias;
//                    error.E.s(cert.toString());
//                    error.E.s("Data od: " + cert.getNotBefore());
//                    error.E.s("Data do: " + cert.getNotAfter());
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
//        char [] password = haslo.toCharArray();
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
//</editor-fold>
  
    private static void drukuj(Object o, String opis) {
        if (o != null) {
            error.E.s("jest "+opis);
            } else {
            error.E.s("nie ma "+opis);
        }
    }
    
    public static boolean moznaPodpisacMute(String innehaslo, String innypesel) {
        resetujodpowiedz();
        String haslo = inneHaslo(innehaslo);
        String pesel = innyPesel(innypesel);
        boolean zwrot = false;
        Provider provider = jestCzytnikDriver();
        KeyStore keyStore = jestKarta(haslo, pesel, provider);
        if (provider != null && keyStore != null) {
            zwrot = true;
        }
        Security.removeProvider("SmartCardn");
        return zwrot;
    }
    
    public static String inneHaslo(String innehaslo) {
        String zwrot = "marlena1";
        if (innehaslo!=null && !innehaslo.equals("")) {
            zwrot = innehaslo;
        }
        odpowiedz.put(6,zwrot);
        return zwrot;
    }
    
    public static String innyPesel(String innypesel) {
        String zwrot = "70052809810";
        if (innypesel!=null && !innypesel.equals("")) {
            zwrot = innypesel;
        }
        odpowiedz.put(7,zwrot);
        return zwrot;
    }

    public static Map<Integer, String> getOdpowiedz() {
        return odpowiedz;
    }

    public static void setOdpowiedz(Map<Integer, String> odpowiedz) {
        ObslugaPodpisuBean.odpowiedz = odpowiedz;
    }


//<editor-fold defaultstate="collapsed" desc="comment">
    
//
//    public static String podpiszDeklaracje(String xml) {
//        //xml = StringEscapeUtils.escapeJava(xml);
//        DSSTransform d = new DSSTransform();
//        Provider provider = jestCzytnikDriver();
//        KeyStore keyStore = jestKarta(haslo);
//        String alias = aktualnyAlias(keyStore);
//        error.E.s("symbol aktualnego certyfikatu "+alias);
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
//        Provider provider = jestCzytnikDriver();
//        KeyStore keyStore = jestKarta(haslo);
//        String alias = aktualnyAlias(keyStore);
//        error.E.s("symbol aktualnego certyfikatu "+alias);
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
    //        Boolean driver = jestCzytnikDriver();
//        drukuj(driver,"driver");
//        KeyStore keyStore = jestKarta(haslo);
//        drukuj(keyStore,"karta");
//        boolean mozna = moznaPodpisac();
//        if (mozna) {
//            KeyStore keyStore = jestKarta(haslo);
//            String alias = aktualnyAlias(keyStore);
//            error.E.s("symbol aktualnego certyfikatu "+alias);
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
//            error.E.s("brak karty");
//        }
//            DSSDocument dok = dokumentzXML("lolo");
//            drukuj(dok,"dokument");
//        String d = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Deklaracja xmlns=\"http://crd.gov.pl/wzor/2016/08/05/3412/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/\"><Naglowek><KodFormularza kodSystemowy=\"VAT-7 (17)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</KodFormularza><WariantFormularza>17</WariantFormularza><CelZlozenia poz=\"P_7\">1</CelZlozenia><Rok>2017</Rok><Miesiac>07</Miesiac><KodUrzedu>3215</KodUrzedu></Naglowek><Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>8521011364</etd:NIP><etd:ImiePierwsze>WOJCIECH</etd:ImiePierwsze><etd:Nazwisko>KADRZY\\u0143S\\u0104\\u0118\\u00D3\\u0141KI</etd:Nazwisko><etd:DataUrodzenia>1965-06-04</etd:DataUrodzenia></etd:OsobaFizyczna></Podmiot1><PozycjeSzczegolowe><P_10>8745</P_10><P_17>22117</P_17><P_18>1769</P_18><P_19>489517</P_19><P_20>112589</P_20><P_23>38661</P_23><P_24>8892</P_24><P_40>559040</P_40><P_41>123250</P_41><P_42>0</P_42><P_43>0</P_43><P_44>0</P_44><P_45>167494</P_45><P_46>38398</P_46><P_51>38398</P_51><P_52>0</P_52><P_54>84852</P_54><P_55>0</P_55><P_56>0</P_56><P_57>0</P_57><P_58>0</P_58><P_59>0</P_59><P_60>0</P_60><P_61>0</P_61><P_74>2017-09-04</P_74></PozycjeSzczegolowe><Pouczenia>1</Pouczenia></Deklaracja>";
//        FileInputStream fisTargetFile = new FileInputStream(new File("d:/vat7a.xml"));
//        String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
//        String xml = podpiszDeklaracje(targetFileStr);
//        FileUtils.writeStringToFile(new File("D:/plik.xml"), xml);
//        error.E.s("info "+xml);
//        } catch (IOException ex) {
//            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}
//</editor-fold>
 
    public static void main(String[] args) {
        Provider pkcs11Provider = null;
        try {
            String realPath = "d:\\cryptoCertum3PKCS.dll";
            String pkcs11config = "name=SmartCardn"+"\r"
                    + "library="+realPath+"\r"+ "slotListIndex=0";
            byte[] pkcs11configBytes = pkcs11config.getBytes("UTF-8");
            ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
            pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
            //Security.removeProvider(pkcs11Provider.getName());
            KeyStore keyStore = KeyStore.getInstance("PKCS11", pkcs11Provider);
            if (keyStore==null) {
                Security.addProvider(pkcs11Provider);
            }
            //keyStore.load(null, "5030".toCharArray());  // Load keystore
            KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection("marlena1".toCharArray());
            keyStore.load(null, pp.getPassword());
            //keyStore.load(null, "marlena1".toCharArray());  // Load keystore
            Enumeration<String> enumeration = keyStore.aliases();
            while(enumeration.hasMoreElements()) {
                String alias = enumeration.nextElement();
                Date certExpiryDate = ((X509Certificate) keyStore.getCertificate(alias)).getNotAfter();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                //Tue Oct 17 06:02:22 AEST 2006
                Date today = new Date();
                long dateDiff = certExpiryDate.getTime() - today.getTime();
                long expiresIn = dateDiff / (24 * 60 * 60 * 1000);
                String name = ((X509Certificate) keyStore.getCertificate(alias)).getSubjectDN().getName();
                if (name.contains("70052809810") && expiresIn>0) {
                    error.E.s("Odnaleziono właściwy aktywny certyfikat użytkownika");
                    error.E.s("Hasło poprawne, karta załadowana");
                    break;
                } else if (name.contains("70052809810") && expiresIn<=0) {
                    error.E.s("Zainstalowany certyfikat wygasł");
                } else if (!name.contains("70052809810")) {
                    error.E.s("Na serwerze nie zainstalowano certyfikatu dla podpisującego");
                }
                // error.E.s("Certifiate: " + alias + "\tExpires On: " + certExpiryDate + "\tFormated Date: " + ft.format(certExpiryDate) + "\tToday's Date: " + ft.format(today) + "\tExpires In: "+ expiresIn);
            }
            
        } catch (IOException ex) {
            Security.removeProvider(pkcs11Provider.getName());
            error.E.s("Błędne hasło!");
        } catch (KeyStoreException ex) {
            Security.removeProvider(pkcs11Provider.getName());
            error.E.s("Brak karty w czytniku");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Security.removeProvider(pkcs11Provider.getName());
            error.E.s("Nie zainstalowano certyfikatu na serwerze.");
        } catch (Exception e) {
            error.E.s("Brak wpiętego czytnika karty/pendriva");
        } finally {
            
        }
    }

    

    
}
