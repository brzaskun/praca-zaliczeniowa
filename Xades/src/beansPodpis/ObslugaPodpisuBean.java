/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPodpis;

import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Osito
 */
public class ObslugaPodpisuBean {
    
    static String HASLO = "marlena1";
    static String PLIK = "james.xml";
    static String DRIVER = "C:\\Users\\Osito\\Documents\\NetBeansProjects\\npkpir_23\\build\\web\\resources\\podpis\\cryptoCertum3PKCS.dll";
    
    public static Provider jestDriver() {
        Provider pkcs11Provider = null;
        try {
            String pkcs11config = "name=SmartCardn"+"\r"
                    + "library="+DRIVER;
            byte[] pkcs11configBytes = pkcs11config.getBytes("UTF-8");
            ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
            pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
            Security.addProvider(pkcs11Provider);
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
            E.e(ex);
        } catch (Exception ex) {
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
                    System.out.println("cert "+alias);
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
    
    public static void drukuj(Object o, String opis) {
        if (o != null) {
            System.out.println("jest "+opis);
            } else {
            System.out.println("nie ma "+opis);
        }
    }
    
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
     
    public static void main(String[] args) {
        try {
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
        FileInputStream fisTargetFile = new FileInputStream(new File("D:/vat7azapas.xml"));
        String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
        String xml = null;
        FileUtils.writeStringToFile(new File("D:/plik.xml"), xml);
        System.out.println("info "+xml);
        } catch (IOException ex) {
            Logger.getLogger(ObslugaPodpisuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
