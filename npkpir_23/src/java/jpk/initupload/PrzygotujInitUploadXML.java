/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.initupload;

import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Osito
 */
public class PrzygotujInitUploadXML {
    
    public static InitUploadType robDokument(String enkrypszynkey, String mainfilename, long mainfilelength, String mainfilehash, String ivvalue, String partfilename, int partfilelength, String partfilehash, String plikxmlnazwa) {
        InitUploadType doc = new InitUploadType();
        try {
            doc.setDocumentType("JPK");
            doc.setVersion("01.02.01.20160617");
            InitUploadType.EncryptionKey ek = new InitUploadType.EncryptionKey();
            ek.setAlgorithm("RSA");
            ek.setEncoding("Base64");
            ek.setMode("ECB");
            ek.setPadding("PKCS#1");
            ek.setValue(enkrypszynkey);
            doc.setEncryptionKey(ek);
            doc.setDocumentList(new ArrayOfDocumentType());
            ArrayOfDocumentType.Document adok = new ArrayOfDocumentType.Document();
            DocumentType.FormCode formkode = new DocumentType.FormCode();
            formkode.setSystemCode("JPK_VAT (2)");
            formkode.setSchemaVersion("1-0");
            formkode.setValue("JPK_VAT");
            adok.setFormCode(formkode);
            adok.setFileName(mainfilename);
            adok.setContentLength(mainfilelength);
            DocumentType.HashValue hash = new DocumentType.HashValue();
            hash.setAlgorithm(new HashValueSHAType().getAlgorithm());
            hash.setEncoding(new HashValueSHAType().getEncoding());
            hash.setValue(mainfilehash);
            adok.setHashValue(hash);
            DocumentType.FileSignatureList fileSignatureList = new DocumentType.FileSignatureList();
            fileSignatureList.filesNumber = 1;
            ArrayOfFileSignatureType.Packaging pack = new ArrayOfFileSignatureType.Packaging();
            ArrayOfFileSignatureType.Packaging.SplitZip  sz = new ArrayOfFileSignatureType.Packaging.SplitZip();
            sz.setMode(sz.getMode());
            sz.setType(sz.getType());
            pack.setSplitZip(sz);
            ArrayOfFileSignatureType.Encryption encry = new ArrayOfFileSignatureType.Encryption();
            ArrayOfFileSignatureType.Encryption.AES aese = new ArrayOfFileSignatureType.Encryption.AES();
            ArrayOfFileSignatureType.Encryption.AES.IV aesiv = new ArrayOfFileSignatureType.Encryption.AES.IV();
            aesiv.setBytes(new EncryptionAESIVType().getBytes());
            aesiv.setEncoding(new EncryptionAESIVType().getEncoding());
            aesiv.setValue(ivvalue);
            aese.setIV(aesiv);
            aese.setBlock(16);
            aese.setMode(aese.getMode());
            aese.setPadding(aese.getPadding());
            aese.setSize(256);
            encry.setAES(aese);
            fileSignatureList.setPackaging(pack);
            fileSignatureList.setEncryption(encry);
            FileSignatureType f = new FileSignatureType();
            FileSignatureType.HashValue hashfile = new FileSignatureType.HashValue();
            hashfile.setAlgorithm(new HashValueMD5Type().getAlgorithm());
            hashfile.setEncoding(new HashValueMD5Type().getEncoding());
            hashfile.setValue(partfilehash);
            f.setOrdinalNumber(1);
            f.setFileName(partfilename);
            f.setContentLength(partfilelength);
            f.setHashValue(hashfile);
            fileSignatureList.getFileSignature().add(f);
            adok.setFileSignatureList(fileSignatureList);
            doc.getDocumentList().setDocument(adok);
            JAXBContext context = JAXBContext.newInstance(InitUploadType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(doc, System.out);
            FileOutputStream fileStream = new FileOutputStream(new File(plikxmlnazwa));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(doc, writer);
            System.out.println("Wygenerowalem doc");
        } catch (Exception e) {
            System.out.println("Blad, nie wygenerowano doc");
            E.e(e);
        }
        return doc;
    }
    
    public static void main(String[] args) {
        robDokument("enkrypszyn", "FILE1", 1024, "MAINHASH", "ivvalue", "partfilename", 800, "partfilehash", "plikxml.xml");
    }
}
