/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TKodKraju;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.JPK;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.JPK.Podmiot1;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TAdresJPK;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TKodFormularza;
import pl.gov.mf.jpk.wzor._2016._10._26._10261.TNaglowek;

/**
 *
 * @author Osito
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void s() {
        try {
            JPK jpk = new JPK();
            jpk.setNaglowek(naglowek("2016-09-01", "2016-09-30"));
            jpk.setPodmiot1(podmiot1());
            dodajWierszeSprzedazy(jpk);
            jpk.setSprzedazCtrl(obliczsprzedazCtrl(jpk));
            dodajWierszeZakupy(jpk);
            jpk.setZakupCtrl(obliczzakupCtrl(jpk));
            JAXBContext context = JAXBContext.newInstance(JPK.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(jpk, System.out);
            marshaller.marshal(jpk, new FileWriter("james.xml"));
            zipfile("james.xml");
            encryptAES("james.xml.zip");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james.xml"));
            //System.out.println(person2);
//            System.out.println(person2.getNazwisko());
//            System.out.println(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (Exception ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static TNaglowek naglowek(String dataod, String datado) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 2;
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.JPK_VAT);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setDataWytworzeniaJPK(databiezaca());
            n.setDataOd(dataoddo(dataod));
            n.setDataDo(dataoddo(datado));
            n.setKodUrzedu("0202");
            n.setDomyslnyKodWaluty(CurrCodeType.PLN);
        } catch (Exception ex) {

        }
        return n;
    }

    private static XMLGregorianCalendar databiezaca() throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal).normalize();
    }

    private static XMLGregorianCalendar dataoddo(String data) throws DatatypeConfigurationException {
        String f = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(f);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
    }

    private static Podmiot1 podmiot1() {
        Podmiot1 p = new Podmiot1();
        p.setIdentyfikatorPodmiotu(indetyfikator());
        p.setAdresPodmiotu(adrespodmiotu());
        return p;
    }

    private static TIdentyfikatorOsobyNiefizycznej indetyfikator() {
        TIdentyfikatorOsobyNiefizycznej i = new TIdentyfikatorOsobyNiefizycznej();
        i.setNIP("1111111111");
        i.setREGON("123456789");
        i.setPelnaNazwa("Pełna Nazwa");
        return i;
    }

    private static TAdresJPK adrespodmiotu() {
        TAdresJPK t = new TAdresJPK();
        t.setKodKraju(TKodKraju.PL);
        t.setWojewodztwo("Zachodniopomorskie");
        t.setPowiat("powiat");
        t.setGmina("gmina");
        t.setUlica("ulica");
        t.setNrDomu("nrdomu");
        t.setNrLokalu("nrlokalu");
        t.setMiejscowosc("miejscowosc");
        t.setKodPocztowy("70-100");
        t.setPoczta("poczta");
        return t;
    }

    private static void dodajWierszeSprzedazy(JPK jpk) {
        jpk.getSprzedazWiersz().add(dodajwierszsprzedazy());
    }

    private static JPK.SprzedazWiersz dodajwierszsprzedazy() {
        JPK.SprzedazWiersz w = new JPK.SprzedazWiersz();
        try {
            w.setLpSprzedazy(BigInteger.ONE);
            w.setDataSprzedazy(dataoddo("2016-01-01"));
            w.setDataWystawienia(dataoddo("2016-01-02"));
            w.setNrKontrahenta("nrkonrahenta");
            w.setNazwaKontrahenta("nazwakontrahenta");
            w.setAdresKontrahenta("adreskontrahenta");
            w.setDowodSprzedazy("dowodsprzedazy");
            w.setTyp("G");
            w.setK19(BigDecimal.valueOf(100));
            w.setK20(BigDecimal.valueOf(23));
        } catch (Exception ex) {

        }
        return w;
    }

    private static JPK.SprzedazCtrl obliczsprzedazCtrl(JPK jpk) {
        List<JPK.SprzedazWiersz> l = jpk.getSprzedazWiersz();
        JPK.SprzedazCtrl s = new JPK.SprzedazCtrl();
        for (JPK.SprzedazWiersz r : l) {
            sumujsprzedaz(r, s);
        }
        return s;
    }

    private static void sumujsprzedaz(JPK.SprzedazWiersz r, JPK.SprzedazCtrl s) {
        BigInteger b = s.getLiczbaWierszySprzedazy();
        if (b == null) {
            s.setLiczbaWierszySprzedazy(BigInteger.ONE);
        } else {
            s.setLiczbaWierszySprzedazy(s.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
        }
        BigDecimal b1 = s.getPodatekNalezny();
        if (b == null) {
            s.setPodatekNalezny(r.getK20());
        } else {
            s.setPodatekNalezny(s.getPodatekNalezny().add(r.getK20()));
        }
    }

    private static void dodajWierszeZakupy(JPK jpk) {
        jpk.getZakupWiersz().add(dodajwierszzakupu());
    }

    private static JPK.ZakupWiersz dodajwierszzakupu() {
        JPK.ZakupWiersz w = new JPK.ZakupWiersz();
        try {
            w.setLpZakupu(BigInteger.ONE);
            w.setDataZakupu(dataoddo("2016-01-01"));
            w.setDataWplywu(dataoddo("2016-01-02"));
            w.setNrDostawcy("nrdostawcy");
            w.setNazwaDostawcy("nazwadostawcy");
            w.setAdresDostawcy("adresdostawcy");
            w.setDowodZakupu("dowodzakupu");
            w.setTyp("G");
            w.setK45(BigDecimal.valueOf(1000));
            w.setK46(BigDecimal.valueOf(230));
        } catch (Exception ex) {

        }
        return w;
    }

    private static JPK.ZakupCtrl obliczzakupCtrl(JPK jpk) {
        List<JPK.ZakupWiersz> l = jpk.getZakupWiersz();
        JPK.ZakupCtrl s = new JPK.ZakupCtrl();
        for (JPK.ZakupWiersz r : l) {
            sumujzakup(r, s);
        }
        return s;
    }

    private static void sumujzakup(JPK.ZakupWiersz r, JPK.ZakupCtrl s) {
        BigInteger b = s.getLiczbaWierszyZakupow();
        if (b == null) {
            s.setLiczbaWierszyZakupow(BigInteger.ONE);
        } else {
            s.setLiczbaWierszyZakupow(s.getLiczbaWierszyZakupow().add(BigInteger.ONE));
        }
        BigDecimal b1 = s.getPodatekNaliczony();
        if (b == null) {
            s.setPodatekNaliczony(r.getK46());
        } else {
            s.setPodatekNaliczony(s.getPodatekNaliczony().add(r.getK46()));
        }
    }

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
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));
        Files.write(Paths.get(filename + ".aes"), encryptedTextBytes);
        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }

    private static char[] czytajplik(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content.toCharArray();
    }

    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
        keyGenerator.init(128);
        Key blowfishKey = keyGenerator.generateKey();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        byte[] blowfishKeyBytes = blowfishKey.getEncoded();
        System.out.println(new String(blowfishKeyBytes));
        byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
        System.out.println(new String(cipherText));
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        byte[] decryptedKeyBytes = cipher.doFinal(cipherText);
        System.out.println(new String(decryptedKeyBytes));
        SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes, "Blowfish");
    }

    public byte[] readFileBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

    public void Hello() {
        try {
            PublicKey publicKey = readPublicKey("public.der");
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
