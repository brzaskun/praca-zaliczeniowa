/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xad;

import beansPodpis.ObslugaPodpisuBean;
import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author Osito
 */
public class Xad {

    private static final String FILE = "d:/vat7a.xml";
    private static String HASLO = "marlena1";
    private static String DRIVER = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/podpis/cryptoCertum3PKCS.dll";
    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        try {
            Provider provider = ObslugaPodpisuBean.jestDriver();
            KeyStore keyStore = ObslugaPodpisuBean.jestKarta(HASLO);
            String alias = ObslugaPodpisuBean.aktualnyAlias(keyStore);
            X509Certificate signingCertificate = (X509Certificate) ObslugaPodpisuBean.certyfikat(alias, keyStore);
            PrivateKey privkey = (PrivateKey) keyStore.getKey(alias, HASLO.toCharArray());
            PublicKey pubKey = signingCertificate.getPublicKey();
            XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
            Reference ref = null;
            SignedInfo signedInfo = null;
            try {
                ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
                        Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
                        (TransformParameterSpec) null)), null, null);
                signedInfo = xmlSigFactory.newSignedInfo(
                        xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                        (C14NMethodParameterSpec) null),
                        xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                        Collections.singletonList(ref));


            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
            KeyInfoFactory kif = xmlSigFactory.getKeyInfoFactory();
        X509Data x509data = kif.newX509Data(Collections.nCopies(1, signingCertificate));
        KeyValue kval = kif.newKeyValue(pubKey);
        List keyInfoItems = new ArrayList();
        keyInfoItems.add(kval);
        keyInfoItems.add(x509data);
        //Object list[];
        KeyInfo keyInfo = kif.newKeyInfo(keyInfoItems);
        //Create a new XML Signature
        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
        Document doc = loadXML(FILE);
        DOMSignContext domSignCtx = new DOMSignContext((Key) privkey, doc.getDocumentElement());
        try {
            //Sign the document
            xmlSignature.sign(domSignCtx);
        } catch (XMLSignatureException ex) {
            ex.printStackTrace();
        }
//            Document document = loadXML(FILE);
//            Element lastElement = document.createElement("Certyfikat");
//            lastElement.setTextContent(x);
//            document.getLastChild().appendChild(lastElement);
        saveXML(doc);
//            System.out.println(""+document.toString());
//            JAXBContext context = JAXBContext.newInstance(JPK.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(jpk, System.out);
//            marshaller.marshal(jpk, new FileWriter("james.xml"));
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            JPK person2 = (JPK) unmarshaller.unmarshal(new File("james.xml"));
            //System.out.println(person2);
//            System.out.println(person2.getNazwisko());
//            System.out.println(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (Exception ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public static Document loadXML(String filename) {
         try {
             DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
             return builder.parse(new File(filename));
         } catch (Exception ex) {
             Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
     
    public static void saveXML(Document document) {
        try {
            // creating and writing to xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource domSource = new DOMSource(document);
            String filename = "d:/vp.xml";
            File outputFile = new File(filename);
            StreamResult streamResult = new StreamResult(outputFile);
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Xad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          
}
