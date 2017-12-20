/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import error.E;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import jpk.initupload.PrzygotujInitUploadXML;
import static jpk.view.jpk_podpis.podpisz;

/**
 *
 * @author Osito
 */
public class SzachMatJPK {
    
    public static void main(String[] args) {
        //wysylka();
        beanJPKwysylka.pobierzupo("72daad910032fb940000003f6cfd3114");
    }
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static void wysylka() {
        try {
            //JPK jpk = Wysylka.makedummyJPK();
            String mainfilename = "indyk.xml";
            String zipfilename = "james2.xml.zip";
            String partfilename = "james2.xml.zip.aes";
            Wysylka.zipfile(mainfilename,zipfilename);
            //Wysylka.encryptAES("james2.xml.zip", "james2.xml.zip.aes");
            SecretKey secretKey = Wysylka.encryptAESStart(zipfilename, partfilename);
            PublicKey publickey = Wysylka.getPublicKey("3af5843ae11db6d94edf0ea502b5cd1a.cer");
            String encryptionkeystring = Wysylka.wrapKey(publickey, secretKey);
            byte[] ivBytes = Wysylka.encryptKoniec(zipfilename, partfilename, secretKey);
            int mainfilesize = Wysylka.readFilesize(mainfilename);
            int partfilesize = Wysylka.readFilesize(partfilename);
            String mainfilehash = Wysylka.fileSha256ToBase64(mainfilename);
            String partfilehash = Wysylka.fileMD5ToBase64(partfilename);
            String plikxmlnazwa = "wysylka.xml";
            PrzygotujInitUploadXML.robDokument(encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), partfilename, partfilesize, partfilehash, plikxmlnazwa);
            String content = new String(Files.readAllBytes(Paths.get("wysylka.xml")));
            podpisz(content);
            beanJPKwysylka.wysylka(partfilename, "wysylkapodpis.xml");
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }

   
}
