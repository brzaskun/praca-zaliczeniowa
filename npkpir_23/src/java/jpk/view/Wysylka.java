/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import vies.VIESCheckBean;

/**
 *
 * @author Osito
 */
public class Wysylka {
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
        saveprivatekey(secretKey);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));
        Files.write(Paths.get(filename + ".aes"), encryptedTextBytes);
        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }

    private static void saveprivatekey(SecretKey secretKey) throws Exception {
        PublicKey pk = readPublicKey("pubkey.pem");
        Cipher pkCipher = Cipher.getInstance("RSA");
        pkCipher.init(Cipher.ENCRYPT_MODE, pk);
        String file = "privatekey.key";
        CipherOutputStream os = new CipherOutputStream(new FileOutputStream(file), pkCipher);
        os.write(secretKey.getEncoded());
        os.close();
    }
    
    private static char[] czytajplik(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content.toCharArray();
    }

//    public static void hello() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
//        keyGenerator.init(128);
//        Key blowfishKey = keyGenerator.generateKey();
//
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(1024);
//        KeyPair keyPair = keyPairGenerator.genKeyPair();
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
//
//        byte[] blowfishKeyBytes = blowfishKey.getEncoded();
//        System.out.println(new String(blowfishKeyBytes));
//        byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
//        System.out.println(new String(cipherText));
//        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
//
//        byte[] decryptedKeyBytes = cipher.doFinal(cipherText);
//        System.out.println(new String(decryptedKeyBytes));
//        SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes, "Blowfish");
//    }

    public static byte[] readFileBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey readPublicKey(String filename) throws Exception {
        FileInputStream fin = new FileInputStream(filename);
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
        return certificate.getPublicKey();
//        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(publicSpec);
    }

    public static byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

//    public static void main(String[] args) {
//        try {
//            removeCryptographyRestrictions();
//            PublicKey publicKey = readPublicKey("D:\\Biuro\\JPK\\wysylka\\pubkey.pem");
//            PrivateKey privateKey = readPrivateKey("private.der");
//            byte[] message = "Hello World".getBytes("UTF8");
//            byte[] secret = encrypt(publicKey, message);
//            byte[] recovered_message = decrypt(privateKey, secret);
//            System.out.println(new String(recovered_message, "UTF8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
        public static void main(String[] args) {
        try {
            String params = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"id-f80fcb47a1716069ca994e2132c0e12d\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><ds:Reference Id=\"r-id-1\" Type=\"http://www.w3.org/2000/09/xmldsig#Object\" URI=\"#o-id-1\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#base64\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>cy5VQ4V2A5zIbQjZqfsOk9fVNzy1dc/8Hgow4KKeRZo=</ds:DigestValue></ds:Reference><ds:Reference Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#xades-id-f80fcb47a1716069ca994e2132c0e12d\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>4MWYCdbM/3yRSEdPo66zPMduADhhjoBy9uDpdMQz59c=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue Id=\"value-id-f80fcb47a1716069ca994e2132c0e12d\">XT6K61mPou7Punk1H43eT8JGR5+qWiDnTbcPcaBQOODc7qX+1LS33UGXZO2Z2RB8fVfH4DMwL5bEg2N1zJIbPVEOXmHAnnAyEWmAUgouUlXv0THq+769XnaAec3b+jpmEs+Q/5XePX+qF1qW3Q4w1g6igtvS6VO9Si1XYUIEtDahyZU4nQaYO0bOVx293mYcqPy54oq9HhWp/LhoZZTzxyxWNOMOV7yhjoE6/fHacptpXo8nGO8Yhs/Gub3XSFlkQ1J0BPrYE6RDhZfPj4fhoS7hh2zFH3oH7qSZh/XnHefCRc45xhSuQNjkAL3pBs3LeCcznt7uA9cdz+P3XrNnnQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIDnjCCAoagAwIBAgIEVyxkyDANBgkqhkiG9w0BAQUFADCBkDELMAkGA1UEBhMCUEwxFDASBgNVBAgTC21hem93aWVja2llMREwDwYDVQQHEwhXYXJzemF3YTEeMBwGA1UEChMVTWluaXN0ZXJzdHdvIEZpbmFuc293MSAwHgYDVQQLExdEZXBhcnRhbWVudCBJbmZvcm1hdHlraTEWMBQGA1UEAxMNanBrLm1mLmdvdi5wbDAeFw0xNjA1MDYwOTMyNTZaFw0yMDA1MDUwOTMyNTZaMIGQMQswCQYDVQQGEwJQTDEUMBIGA1UECBMLbWF6b3dpZWNraWUxETAPBgNVBAcTCFdhcnN6YXdhMR4wHAYDVQQKExVNaW5pc3RlcnN0d28gRmluYW5zb3cxIDAeBgNVBAsTF0RlcGFydGFtZW50IEluZm9ybWF0eWtpMRYwFAYDVQQDEw1qcGsubWYuZ292LnBsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApDhZPQCL8GGe6/rTGP3GT7kSV2tqWVh1QE0XQjoG57QLCowNiROtjzwNsx4bo3TjBjavhmpCmnOCmIIwoPBcSoZslxRyBlbFyE4u5OAGxaiB6gSoapYyfqNxqybiVpSFk8kXUhHswbGY6755Dd9/EuK+R1o8xkkriyHJL6mba1ojppEBEqb0TqxZGUkOAc5DgFmIqgBNqXlQZi2LcdIaRl5xO/vupOWF+Dc5lzV9KcPgWpDyYCJU8PLEIzei4J3HoNYsM9fy3tRAxEeds4+6S+CcOE5rq91HJw+CA2xjZ90olXpuXcxYRwf7PlMP6s4dkVaaAGcrTiPhaDVshChOXwIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQAELmvPmWik0fY6QE++OpqImT/QA4ojqXOsAkmG9QMFoKKcO7dZQm47INz8Ut0y8qKlNtvylGAVa6sQ62krtnnXJNVEnFrU9uS4eNXs4ZC2TqqC/ni8keiXJFzhm59AAgaj/a+isfZ9xXCT6hCWxuRpJJVsyWGrmDgUC5qFlJ3dlHsHdAi7ZLvvQ48EoFU/6HH/RbAsSiWzL5UV6VZi5fBq0kWr0edsgUp9yDVPcGaGPctSsvH9/3znY1WEPbnqfb8sjyMzrd1WXDVAqy2Ng7xrB4YqVFXCgce0tVFVFEOChUF1pUKQ8eFzS4WtevxBFV9kUZvMV3ul/Wj1AKp4YH32</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Target=\"#id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedProperties Id=\"xades-id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedSignatureProperties><xades:SigningTime>2017-01-30T12:32:53Z</xades:SigningTime><xades:SigningCertificate><xades:Cert><xades:CertDigest><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><ds:DigestValue>kPpw/0EDtMXdBvRnqCrP/fD502I=</ds:DigestValue></xades:CertDigest><xades:IssuerSerial><ds:X509IssuerName>CN=jpk.mf.gov.pl,OU=Departament Informatyki,O=Ministerstwo Finansow,L=Warszawa,ST=mazowieckie,C=PL</ds:X509IssuerName><ds:X509SerialNumber>1462527176</ds:X509SerialNumber></xades:IssuerSerial></xades:Cert></xades:SigningCertificate></xades:SignedSignatureProperties><xades:SignedDataObjectProperties><xades:DataObjectFormat ObjectReference=\"#r-id-1\"><xades:MimeType>text/xml</xades:MimeType></xades:DataObjectFormat></xades:SignedDataObjectProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object><ds:Object Id=\"o-id-1\">PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8SW5pdFVwbG9hZCB4bWxucz0iaHR0cDovL2UtZG9rdW1lbnR5Lm1mLmdvdi5wbCI+CiAgICA8RG9jdW1lbnRUeXBlPkpQSzwvRG9jdW1lbnRUeXBlPgogICAgPFZlcnNpb24+MDEuMDIuMDEuMjAxNjA2MTc8L1ZlcnNpb24+CiAgICA8RW5jcnlwdGlvbktleSBhbGdvcml0aG09IlJTQSIgbW9kZT0iRUNCIiBwYWRkaW5nPSJQS0NTIzEiIGVuY29kaW5nPSJCYXNlNjQiPlJwQkxrZy9ZNE8raVFWMDFBa3N6aEZmTVN5SmxVUWgxSllRcTYyM0FPUGdOYWhHZW1hMVNxQjFDUlcrVDBNZzA2bjdtTWtialRsZFIxbTdYNXdJS04xcWNwbUxJTjJ2ckNROXpPc1AzcG1tcDlxczc1ZVRNVUxzcjZmTEtveFA1a3Z5UlNhSFZVN2tkdzd3WGgxK0pjYzk3V3pXVXd6KzRBWGlIODdTNW5PUklYRXJ3SFFuZ3d1ellOcVRnNXlzQlJCUzF3QjZhRjJ1UjFoQ25KcXpqcEtSaFFjS2FqWlVHdUdLRXJuTVljUWZoUFA1d1VnelFyRzIvcjlHTmhRUGFRbkxxbHdHei8yUXUzR0E2ekRkQWpzQTZPVU9xQnhpQXIrQXc2RDRUQW9ITklPV0pNWXVTNXQ4QitWcGx6Yk5zQ2orQU9Dd1p3Z1ZVbWEzWDYxNFQ3QT09PC9FbmNyeXB0aW9uS2V5PgogICAgPERvY3VtZW50TGlzdD4KICAgICAgICA8RG9jdW1lbnQ+CiAgICAgICAgICAgIDxGb3JtQ29kZSBzeXN0ZW1Db2RlPSJKUEtfVkFUICgyKSIgc2NoZW1hVmVyc2lvbj0iMS0wIj5KUEtfVkFUPC9Gb3JtQ29kZT4KICAgICAgICAgICAgPEZpbGVOYW1lPkpQSy1WQVQtVEVTVC0wMDAxLnhtbDwvRmlsZU5hbWU+CiAgICAgICAgICAgIDxDb250ZW50TGVuZ3RoPjEyMDQ8L0NvbnRlbnRMZW5ndGg+CiAgICAgICAgICAgIDxIYXNoVmFsdWUgYWxnb3JpdGhtPSJTSEEtMjU2IiBlbmNvZGluZz0iQmFzZTY0Ij5hZnRCZ0tXdElUSFJTOXZwcmQ5Qkg3bnNLMm5WU3gydE8zR0V2WXBhWkFVPTwvSGFzaFZhbHVlPgogICAgICAgICAgICA8RmlsZVNpZ25hdHVyZUxpc3QgZmlsZXNOdW1iZXI9IjEiPgogICAgICAgICAgICAgICAgPFBhY2thZ2luZz4KICAgICAgICAgICAgICAgICAgICA8U3BsaXRaaXAgdHlwZT0ic3BsaXQiIG1vZGU9InppcCIvPgogICAgICAgICAgICAgICAgPC9QYWNrYWdpbmc+CiAgICAgICAgICAgICAgICA8RW5jcnlwdGlvbj4KICAgICAgICAgICAgICAgICAgICA8QUVTIHNpemU9IjI1NiIgYmxvY2s9IjE2IiBtb2RlPSJDQkMiIHBhZGRpbmc9IlBLQ1MjNyI+CiAgICAgICAgICAgICAgICAgICAgICAgIDxJViBieXRlcz0iMTYiIGVuY29kaW5nPSJCYXNlNjQiPk1USXpORFUyTnpnNU1ERXlNelExTmc9PTwvSVY+CiAgICAgICAgICAgICAgICAgICAgPC9BRVM+CiAgICAgICAgICAgICAgICA8L0VuY3J5cHRpb24+CiAgICAgICAgICAgICAgICA8RmlsZVNpZ25hdHVyZT4KICAgICAgICAgICAgICAgICAgICA8T3JkaW5hbE51bWJlcj4xPC9PcmRpbmFsTnVtYmVyPgogICAgICAgICAgICAgICAgICAgIDxGaWxlTmFtZT5KUEstVkFULVRFU1QtMDAwMS54bWwuemlwLmFlczwvRmlsZU5hbWU+CiAgICAgICAgICAgICAgICAgICAgPENvbnRlbnRMZW5ndGg+ODAwPC9Db250ZW50TGVuZ3RoPgogICAgICAgICAgICAgICAgICAgIDxIYXNoVmFsdWUgYWxnb3JpdGhtPSJNRDUiIGVuY29kaW5nPSJCYXNlNjQiPjVZbml2RUg0Z3o1V2c1RThNMlh3QVE9PTwvSGFzaFZhbHVlPgogICAgICAgICAgICAgICAgPC9GaWxlU2lnbmF0dXJlPgogICAgICAgICAgICA8L0ZpbGVTaWduYXR1cmVMaXN0PgogICAgICAgIDwvRG9jdW1lbnQ+CiAgICA8L0RvY3VtZW50TGlzdD4KPC9Jbml0VXBsb2FkPgo=</ds:Object></ds:Signature>";
            String urlParameters  = params;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "https://test-e-dokumenty.mf.gov.pl/api/Storage/InitUploadSigned";
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            JSONTokener js = new JSONTokener(in);
            JSONObject jo = new JSONObject(js);
            String refnum = jo.getString("ReferenceNumber");
            String[] a = new String[1];
            JSONArray job = jo.getJSONArray("RequestToUploadFileList");
            String uri = (String) ((JSONObject) job.get(0)).get("Url");
            String[] uri_parse = uri.split("\\?");
            String uri_bezw = uri_parse[0];
            String cred = uri_parse[1];
            String blobname = (String) ((JSONObject) job.get(0)).get("BlobName");
            System.out.println("uri "+uri);
            System.out.println("blobname "+blobname);
            container(uri);

        } catch (Exception ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    private static void container(String ur) {
        try {
            // Upload an image file.
            CloudBlockBlob blob = new CloudBlockBlob(new URI(ur));
            File sourceFile = new File("G:\\Dropbox\\JPK_VAT_TEST_0001.ZIP");
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.print("FileNotFoundException encountered: ");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(-1);
        }
        catch (StorageException storageException) {
            System.out.print("StorageException encountered: ");
            System.out.println(storageException.getMessage());
            System.exit(-1);
        }
        catch (Exception e) {
            System.out.print("Exception encountered: ");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
//
//        private void hepe() {
//            String absoluteUri = "https://taxdocumentstorage10tst.blob.core.windows.net/d8cb2f0f014381ab000000b012f8a3d6/b42748d3-0660-4d81-afc2-3c250fbcdbef";
//            String sas = "sv=2015-07-08&sr=b&si=d8cb2f0f014381ab000000b012f8a3d6&sig=2y%2BZ3cjcyBbBnCM6Mw9a4EPN2KA%2B01kgf9fro%2FK6Xgw%3D";
//            CloudBlockBlob blob = new CloudBlockBlob(new Uri(absoluteUri), new StorageCredentials(sas));
//            FileStream stream = new FileStream("jpk_vat_100-01.xml.zip.aes", FileMode.Open))
//             {
//             blob.UploadFromStream(stream);
//             }
//        }

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
        Logger logger = Logger.getLogger(Wysylka.class.getName());
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
