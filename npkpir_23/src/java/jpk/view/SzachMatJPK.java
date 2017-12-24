/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import entity.UPO;
import error.E;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import jpk.initupload.PrzygotujInitUploadXML;
import static jpk.view.UnzipUtility.unzip;

/**
 *
 * @author Osito
 */
public class SzachMatJPK {
    
    private static String publiccertyffile = "3af5843ae11db6d94edf0ea502b5cd1a.cer";
    
    public static void main(String[] args) {
        //wysylka();
        //
        //beanJPKwysylka.pobierzupo("85cc022a0135cd960000003e0c5dfb48");
        //james.zip.aes teraz sie nazywa
        //beanJPKwysylka.pobierzupo("8620b1f00131b6870000003f59bb9a27");
        //wywalilem base64 zzachowania pliku aes
        beanJPKwysylka.pobierzupo("8636dfe903fa6b820000003f7b28f7e8");
    }
    
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static void wysylka(String mainfilename, String zipfilename, String aesfilename) {
        try {
            Wysylka.zipfile(mainfilename,zipfilename);
            SecretKey secretKey = Wysylka.encryptAESStart(zipfilename, aesfilename);
            PublicKey publickey = Wysylka.getPublicKey("3af5843ae11db6d94edf0ea502b5cd1a.cer");
            String encryptionkeystring = Wysylka.wrapKey(publickey, secretKey);
            byte[] ivBytes = Wysylka.encryptKoniec(zipfilename, aesfilename, secretKey);
            int mainfilesize = Wysylka.readFilesize(mainfilename);
            int partfilesize = Wysylka.readFilesize(aesfilename);
            String mainfilehash = Wysylka.fileSha256ToBase64(mainfilename);
            String partfilehash = Wysylka.fileMD5ToBase64(aesfilename);
            String plikxmlnazwa = "wysylka.xml";
            PrzygotujInitUploadXML.robDokument(encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), aesfilename, partfilesize, partfilehash, plikxmlnazwa);
            String content = new String(Files.readAllBytes(Paths.get("wysylka.xml")));
            beansPodpis.Xad.podpiszjpk(content);
            beanJPKwysylka.wysylka(aesfilename, "wysylkapodpis.xml");
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
    public static UPO pobierzupo(String nrref) {
        UPO upo = null;
        try {
            upo = beanJPKwysylka.pobierzupo("nrref");
        } catch (Exception e) {
            E.e(e);
        }
        return upo;
    }
    
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static void wysylkaTest() {
        try {
            //JPK jpk = Wysylka.makedummyJPK();
            String mainfilename = "james2.xml";
            String zipfilename = "james2.zip";
            String partfilename = "james2.zip.aes";
            Wysylka.zipfile(mainfilename,zipfilename);
            unzip(zipfilename, "unzipfolder");
            //Wysylka.encryptAES("james2.xml.zip", "james2.xml.zip.aes");
            SecretKey secretKey = Wysylka.encryptAESStart(zipfilename, partfilename);
            PublicKey publickey = Wysylka.getPublicKey("3af5843ae11db6d94edf0ea502b5cd1a.cer");
            String encryptionkeystring = Wysylka.wrapKey(publickey, secretKey);
            byte[] ivBytes = Wysylka.encryptKoniec(zipfilename, partfilename, secretKey);
            decrypt(secretKey, partfilename, ivBytes);
            unzip("odkodowana.zip", "unzipfolder2");
            int mainfilesize = Wysylka.readFilesize(mainfilename);
            int partfilesize = Wysylka.readFilesize(partfilename);
            String mainfilehash = Wysylka.fileSha256ToBase64(mainfilename);
            String partfilehash = Wysylka.fileMD5ToBase64(partfilename);
            String plikxmlnazwa = "wysylka.xml";
            PrzygotujInitUploadXML.robDokument(encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), partfilename, partfilesize, partfilehash, plikxmlnazwa);
            String content = new String(Files.readAllBytes(Paths.get("wysylka.xml")));
            beansPodpis.Xad.podpiszjpk(content);
            beanJPKwysylka.wysylka(partfilename, "wysylkapodpis.xml");
        } catch (Exception ex) {
            E.e(ex);
        }
    }

    public static void decrypt(SecretKey key, String partfilename, byte[] ivBytes){
        try {
            Path path = Paths.get(partfilename);
            byte[] ciphertext = Files.readAllBytes(path);
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivBytes));
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            c.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = c.doFinal(Base64.getDecoder().decode(ciphertext));
            Files.write(Paths.get("odkodowana.zip"), original);
            System.out.println("odkodowalem");
            //System.out.println("decrypt: "+new String(original));
        } catch (Exception ex) {
            System.out.println("decryot "+ex);
        }
    }
   
}
