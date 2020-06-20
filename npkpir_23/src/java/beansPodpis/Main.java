package beansPodpis;

import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.KeyStoreKeyingDataProvider;
import xades4j.providers.impl.PKCS11KeyStoreKeyingDataProvider;
import xades4j.utils.DOMHelper;

/**
 *
 * @author Joan Josep Escandell
 */
public class Main {

    private static final String SIGNED      = "D:/deklsigned.xml";
    private static final String DOCUMENT    = "D:/dekl.xml";
    
    private static String DRIVER = "resources\\podpis\\cryptoCertum3PKCS.dll";
    private static String PROVIDERNAME = "SmartCardn";
    private static String HASLO = "marlena1";
    private static X509Certificate CERT;

    public static void main(String[] args) throws Exception {
        error.E.s("______________________");
        error.E.s("\tSign");
        signBes();
        
//        error.E.s("______________________");
//        error.E.s("\tVerify");
//        error.E.s("______________________");
//        verifyBes();

    }

    private static void signBes() throws Exception {
        Provider provider = jestDriver();
        KeyStore keyStore = jestKarta(HASLO);
        CERT = aktualnyCert(keyStore);
        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new File(DOCUMENT));
        Element elem = doc.getDocumentElement();
        DOMHelper.useIdAsXmlId(elem);
        List<X509Certificate> list = Collections.synchronizedList(new ArrayList<>());
        KeyingDataProvider
        kdp;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+DRIVER;
        kdp = new PKCS11KeyStoreKeyingDataProvider(
                realPath,
                PROVIDERNAME, null,
                new KeyStoreKeyingDataProvider.KeyStorePasswordProvider() {
                    @Override
                    public char[] getPassword() {
                        return HASLO.toCharArray();
                    }
                }, new KeyStoreKeyingDataProvider.KeyEntryPasswordProvider() {
            @Override
            public char[] getPassword(String string, X509Certificate xc) {
                return HASLO.toCharArray();
            }
        }, false);
        DataObjectDesc obj = new DataObjectReference("#" + elem.getAttribute("Id"))
                .withTransform(new EnvelopedSignatureTransform());
        SignedDataObjects dataObjs = new SignedDataObjects().withSignedDataObject(obj);

        XadesSigner signer = new XadesBesSigningProfile(kdp).newSigner();
        signer.sign(dataObjs, elem);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(doc);        
        StreamResult result = new StreamResult(new File(SIGNED));
        transformer.transform(source, result);
    }

    
    private static Provider jestDriver() {
        Provider pkcs11Provider = null;
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+DRIVER;
            String pkcs11config = "name=SmartCardn"+"\r"
                    + "library="+realPath;
            byte[] pkcs11configBytes = pkcs11config.getBytes("UTF-8");
            ByteArrayInputStream configStream = new ByteArrayInputStream(pkcs11configBytes);
            pkcs11Provider = new sun.security.pkcs11.SunPKCS11(configStream);
            Security.addProvider(pkcs11Provider);
        } catch (Exception e) {
            E.e(e);
        }
        return pkcs11Provider;
    }
    
    private static KeyStore jestKarta(String haslo) {
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
    
    private static X509Certificate aktualnyCert(KeyStore keyStore) {
        X509Certificate cert = null;
        try {
            Enumeration aliasesEnum = keyStore.aliases();
            Date today = new Date();
            while (aliasesEnum.hasMoreElements()) {
                String alias = (String)aliasesEnum.nextElement();
                cert = (X509Certificate) keyStore.getCertificate(alias);
                // error.E.s("Certificate: " + cert);
                if (today.after(cert.getNotBefore()) && today.before(cert.getNotAfter())) {
                    break;
                }
            }
        } catch (KeyStoreException ex) {
            E.e(ex);
        }
        return cert;
    }
}
