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
import jpk201701.JPK;

/**
 *
 * @author Osito
 */
public class SzachMatJPK {
    
    public static void main(String[] args) {
        //wysylka();
        beanJPKwysylka.pobierzupo("6f675a5903ff25d00000003f462ffbe1");
        beanJPKwysylka.pobierzupo("6f6da1f101770fed0000003f4366e012");
    }
    //UWAGA USTAWIENIA PRODUKCYJNE
    public static void wysylka() {
        try {
            //JPK jpk = Wysylka.makedummyJPK();
            Wysylka.zipfile("indyk.xml","james2.xml.zip");
            Wysylka.encryptAES("james2.xml.zip", "james2.xml.zip.aes");
            SecretKey secretKey = Wysylka.encryptAESStart("james2.xml.zip", "james2.xml.zip.aes");
            PublicKey publickey = Wysylka.getPublicKey("3af5843ae11db6d94edf0ea502b5cd1a.cer");
            String encryptionkeystring = Wysylka.wrapKey(publickey, secretKey);
            String mainfilename = "james2.xml";
            String partfilename = "james2.xml.zip.aes";
            byte[] ivBytes = Wysylka.encryptKoniec("james2.xml.zip", mainfilename, secretKey);
            int mainfilesize = Wysylka.readFilesize(mainfilename);
            int partfilesize = Wysylka.readFilesize(partfilename);
            String mainfilehash = Wysylka.fileSha256ToBase64(mainfilename);
            String partfilehash = Wysylka.fileMD5ToBase64(partfilename);
            String plikxmlnazwa = "wysylka.xml";
            PrzygotujInitUploadXML.robDokument(encryptionkeystring, mainfilename, mainfilesize, mainfilehash, new String(ivBytes), partfilename, partfilesize, partfilehash, plikxmlnazwa);
            String content = new String(Files.readAllBytes(Paths.get("wysylka.xml")));
            podpisz(content);
            String referenceNumber = beanJPKwysylka.wysylka(partfilename, "wysylkapodpis.xml");
            System.out.println("Koniec szachmat");
            beanJPKwysylka.pobierzupo(referenceNumber);
        } catch (Exception ex) {
            E.e(ex);
        }
        
    }

   
}
