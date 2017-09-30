/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xad;

import java.lang.ref.Reference;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.xml.bind.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
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
import org.w3c.dom.Document;

/**
 *
 * @author Osito
 */
public class ffff {
    public void generateSignatureforResumen(String originalXmlFilePath,
        String destnSignedXmlFilePath, IAIKPkcs11 pkcs11Provider_, KeyStore tokenKeyStore, String pin) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, GeneralSecurityException, TokenException  {
    //Get the XML Document object
    Document doc = getXmlDocument(originalXmlFilePath);
    //Create XML Signature Factory
    PrivateKey signatureKey_ = null;
    PublicKey pubKey_ = null;
    X509Certificate signingCertificate_ = null;
    Boolean prik = false;
    Boolean pubk = false;
    Enumeration aliases = tokenKeyStore.aliases();
    while (aliases.hasMoreElements()) {
      String keyAlias = aliases.nextElement().toString();
      java.security.Key key = tokenKeyStore.getKey(keyAlias, pin.toCharArray());
      if (key instanceof java.security.interfaces.RSAPrivateKey) {
        Certificate[] certificateChain = tokenKeyStore.getCertificateChain(keyAlias);
        X509Certificate signerCertificate = (X509Certificate) certificateChain[0];
        boolean[] keyUsage = signerCertificate.getKeyUsage();
        // check for digital signature or non-repudiation,
        // but also accept if none is set
        if ((keyUsage == null) || keyUsage[0] || keyUsage[1]) {
          signatureKey_ = (PrivateKey) key;
          signingCertificate_ = signerCertificate;
          prik = true;
          pubKey_ = signerCertificate.getPublicKey();
          break;
        }
      } 
    }


    if (signatureKey_ == null) {
      throw new GeneralSecurityException(
          "Found no signature key. Ensure that a valid card is inserted.");
    }
//****************************************************
     XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
        Reference ref = null;
        SignedInfo signedInfo = null;
        try {
            ref = (Reference) xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
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
        X509Data x509data = kif.newX509Data(Collections.nCopies(1, signingCertificate_));
        KeyValue kval = kif.newKeyValue(pubKey_);
        List keyInfoItems = new ArrayList();
        keyInfoItems.add(kval);
        keyInfoItems.add(x509data);
        //Object list[];
        KeyInfo keyInfo = kif.newKeyInfo(keyInfoItems);
//Create a new XML Signature
    XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);



    DOMSignContext domSignCtx = new DOMSignContext((Key) signatureKey_, doc.getDocumentElement());


    try {
        //Sign the document
        xmlSignature.sign(domSignCtx);
    } catch (MarshalException ex) {
        ex.printStackTrace();
    } catch (XMLSignatureException ex) {
        ex.printStackTrace();
    }
    //Store the digitally signed document inta a location
    storeSignedDoc(doc, destnSignedXmlFilePath);
}
