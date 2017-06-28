/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edeklaracje;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Osito
 */
public class edekl {
    private static final String DOCUMENT    = "C:/Test/sign-verify/document.xml";
    private static final String CERT_FOLDER = "C:/Certs/";
    private static final String CERT        = "mycert.pfx";
    private static final String SIGNED      = "C:/Test/sign-verify/signed-bes.xml";
    
    private static void signBes() throws Exception {
        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new File(DOCUMENT));
        Element elem = doc.getDocumentElement();
        
        //DOMHelper.useIdAsXmlId(elem);
        

//        KeyingDataProvider kdp = new FileSystemKeyStoreKeyingDataProvider(
//                "pkcs12",
//                CERT_FOLDER + CERT,
//                new FirstCertificateSelector(),
//                new DirectPasswordProvider(PASS),
//                new DirectPasswordProvider(PASS),
//                true);
//        DataObjectDesc obj = new DataObjectReference("#" + elem.getAttribute("Id"))
//                .withTransform(new EnvelopedSignatureTransform());
//        SignedDataObjects dataObjs = new SignedDataObjects().withSignedDataObject(obj);
//
//        XadesSigner signer = new XadesBesSigningProfile(kdp).newSigner();
//        signer.sign(dataObjs, elem);
//
//        TransformerFactory tFactory = TransformerFactory.newInstance();
//        Transformer transformer = tFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);        
//        StreamResult result = new StreamResult(new File(SIGNED));
//        transformer.transform(source, result);
    }
}
