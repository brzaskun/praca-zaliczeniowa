/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import error.E;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Osito
 */
public class beanJPKwysylka {
    
    private static String plikxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" "
                    + "Id=\"id-f80fcb47a1716069ca994e2132c0e12d\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>"
                    + "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><ds:Reference Id=\"r-id-1\" "
                    + "Type=\"http://www.w3.org/2000/09/xmldsig#Object\" URI=\"#o-id-1\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#base64\"/>"
                    + "</ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>cy5VQ4V2A5zIbQjZqfsOk9fVNzy1dc/8Hgow4KKeRZo="
                    + "</ds:DigestValue></ds:Reference><ds:Reference Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#xades-id-f80fcb47a1716069ca994e2132c0e12d\">"
                    + "<ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></ds:Transforms>"
                    + "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>4MWYCdbM/3yRSEdPo66zPMduADhhjoBy9uDpdMQz59c=</ds:DigestValue>"
                    + "</ds:Reference></ds:SignedInfo><ds:SignatureValue Id=\"value-id-f80fcb47a1716069ca994e2132c0e12d\">"
                    + "XT6K61mPou7Punk1H43eT8JGR5+qWiDnTbcPcaBQOODc7qX+1LS33UGXZO2Z2RB8fVfH4DMwL5bEg2N1zJIbPVEOXmHAnnAyEWmAUgouUlXv0THq+769XnaAec3b+jpmEs+Q/5XePX+qF1qW3Q4w1g"
                    + "6igtvS6VO9Si1XYUIEtDahyZU4nQaYO0bOVx293mYcqPy54oq9HhWp/LhoZZTzxyxWNOMOV7yhjoE6/fHacptpXo8nGO8Yhs/Gub3XSFlkQ1J0BPrYE6RDhZfPj4fhoS7hh2zFH3oH7qSZh/XnHefCRc45xhSuQN"
                    + "jkAL3pBs3LeCcznt7uA9cdz+P3XrNnnQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIDnjCCAoagAwIBAgIEVyxkyDANBgkqhkiG9w0BAQUFADCBkDELMAkGA"
                    + "1UEBhMCUEwxFDASBgNVBAgTC21hem93aWVja2llMREwDwYDVQQHEwhXYXJzemF3YTEeMBwGA1UEChMVTWluaXN0ZXJzdHdvIEZpbmFuc293MSAwHgYDVQQLExdEZXBhcnRhbWVudCBJbmZvcm1hdHlraTEWMBQGA1UE"
                    + "AxMNanBrLm1mLmdvdi5wbDAeFw0xNjA1MDYwOTMyNTZaFw0yMDA1MDUwOTMyNTZaMIGQMQswCQYDVQQGEwJQTDEUMBIGA1UECBMLbWF6b3dpZWNraWUxETAPBgNVBAcTCFdhcnN6YXdhMR4wHAYDVQQKExVNaW5pc3Rlc"
                    + "nN0d28gRmluYW5zb3cxIDAeBgNVBAsTF0RlcGFydGFtZW50IEluZm9ybWF0eWtpMRYwFAYDVQQDEw1qcGsubWYuZ292LnBsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApDhZPQCL8GGe6/rTGP3GT7kSV2"
                    + "tqWVh1QE0XQjoG57QLCowNiROtjzwNsx4bo3TjBjavhmpCmnOCmIIwoPBcSoZslxRyBlbFyE4u5OAGxaiB6gSoapYyfqNxqybiVpSFk8kXUhHswbGY6755Dd9/EuK+R1o8xkkriyHJL6mba1ojppEBEqb0TqxZGUkOAc5"
                    + "DgFmIqgBNqXlQZi2LcdIaRl5xO/vupOWF+Dc5lzV9KcPgWpDyYCJU8PLEIzei4J3HoNYsM9fy3tRAxEeds4+6S+CcOE5rq91HJw+CA2xjZ90olXpuXcxYRwf7PlMP6s4dkVaaAGcrTiPhaDVshChOXwIDAQABMA0GCSqG"
                    + "SIb3DQEBBQUAA4IBAQAELmvPmWik0fY6QE++OpqImT/QA4ojqXOsAkmG9QMFoKKcO7dZQm47INz8Ut0y8qKlNtvylGAVa6sQ62krtnnXJNVEnFrU9uS4eNXs4ZC2TqqC/ni8keiXJFzhm59AAgaj/a+isfZ9xXCT6hCWx"
                    + "uRpJJVsyWGrmDgUC5qFlJ3dlHsHdAi7ZLvvQ48EoFU/6HH/RbAsSiWzL5UV6VZi5fBq0kWr0edsgUp9yDVPcGaGPctSsvH9/3znY1WEPbnqfb8sjyMzrd1WXDVAqy2Ng7xrB4YqVFXCgce0tVFVFEOChUF1pUKQ8eFzS4"
                    + "WtevxBFV9kUZvMV3ul/Wj1AKp4YH32</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" "
                    + "Target=\"#id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedProperties Id=\"xades-id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedSignatureProperties>"
                    + "<xades:SigningTime>2017-01-30T12:32:53Z</xades:SigningTime><xades:SigningCertificate><xades:Cert><xades:CertDigest>"
                    + "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><ds:DigestValue>kPpw/0EDtMXdBvRnqCrP/fD502I=</ds:DigestValue></xades:CertDigest>"
                    + "<xades:IssuerSerial><ds:X509IssuerName>CN=jpk.mf.gov.pl,OU=Departament Informatyki,O=Ministerstwo Finansow,L=Warszawa,ST=mazowieckie,C=PL</ds:X509IssuerName>"
                    + "<ds:X509SerialNumber>1462527176</ds:X509SerialNumber></xades:IssuerSerial></xades:Cert></xades:SigningCertificate>"
                    + "</xades:SignedSignatureProperties><xades:SignedDataObjectProperties><xades:DataObjectFormat ObjectReference=\"#r-id-1\"><xades:MimeType>text/xml</xades:MimeType>"
                    + "</xades:DataObjectFormat></xades:SignedDataObjectProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object>"
                    + "<ds:Object Id=\"o-id-1\">PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8SW5pdFVwbG9hZCB4bWxucz0iaHR0cDovL2UtZG9rdW1lbnR5Lm1mLmdvdi"
                    + "5wbCI+CiAgICA8RG9jdW1lbnRUeXBlPkpQSzwvRG9jdW1lbnRUeXBlPgogICAgPFZlcnNpb24+MDEuMDIuMDEuMjAxNjA2MTc8L1ZlcnNpb24+CiAgICA8RW5jcnlwdGlvbktleSBhbGdvcml0aG09IlJTQSIgbW"
                    + "9kZT0iRUNCIiBwYWRkaW5nPSJQS0NTIzEiIGVuY29kaW5nPSJCYXNlNjQiPlJwQkxrZy9ZNE8raVFWMDFBa3N6aEZmTVN5SmxVUWgxSllRcTYyM0FPUGdOYWhHZW1hMVNxQjFDUlcrVDBNZzA2bjdtTWtialRsZF"
                    + "IxbTdYNXdJS04xcWNwbUxJTjJ2ckNROXpPc1AzcG1tcDlxczc1ZVRNVUxzcjZmTEtveFA1a3Z5UlNhSFZVN2tkdzd3WGgxK0pjYzk3V3pXVXd6KzRBWGlIODdTNW5PUklYRXJ3SFFuZ3d1ellOcVRnNXlzQlJCUz"
                    + "F3QjZhRjJ1UjFoQ25KcXpqcEtSaFFjS2FqWlVHdUdLRXJuTVljUWZoUFA1d1VnelFyRzIvcjlHTmhRUGFRbkxxbHdHei8yUXUzR0E2ekRkQWpzQTZPVU9xQnhpQXIrQXc2RDRUQW9ITklPV0pNWXVTNXQ4QitWcG"
                    + "x6Yk5zQ2orQU9Dd1p3Z1ZVbWEzWDYxNFQ3QT09PC9FbmNyeXB0aW9uS2V5PgogICAgPERvY3VtZW50TGlzdD4KICAgICAgICA8RG9jdW1lbnQ+CiAgICAgICAgICAgIDxGb3JtQ29kZSBzeXN0ZW1Db2RlPSJK"
                    + "UEtfVkFUICgyKSIgc2NoZW1hVmVyc2lvbj0iMS0wIj5KUEtfVkFUPC9Gb3JtQ29kZT4KICAgICAgICAgICAgPEZpbGVOYW1lPkpQSy1WQVQtVEVTVC0wMDAxLnhtbDwvRmlsZU5hbWU+CiAgICAgICAgI"
                    + "CAgIDxDb250ZW50TGVuZ3RoPjEyMDQ8L0NvbnRlbnRMZW5ndGg+CiAgICAgICAgICAgIDxIYXNoVmFsdWUgYWxnb3JpdGhtPSJTSEEtMjU2IiBlbmNvZGluZz0iQmFzZTY0Ij5hZnRCZ0tXdElUSFJTOX"
                    + "ZwcmQ5Qkg3bnNLMm5WU3gydE8zR0V2WXBhWkFVPTwvSGFzaFZhbHVlPgogICAgICAgICAgICA8RmlsZVNpZ25hdHVyZUxpc3QgZmlsZXNOdW1iZXI9IjEiPgogICAgICAgICAgICAgICAgPFBhY2thZ2lu"
                    + "Zz4KICAgICAgICAgICAgICAgICAgICA8U3BsaXRaaXAgdHlwZT0ic3BsaXQiIG1vZGU9InppcCIvPgogICAgICAgICAgICAgICAgPC9QYWNrYWdpbmc+CiAgICAgICAgICAgICAgICA8RW5jcnlwdGlvb"
                    + "j4KICAgICAgICAgICAgICAgICAgICA8QUVTIHNpemU9IjI1NiIgYmxvY2s9IjE2IiBtb2RlPSJDQkMiIHBhZGRpbmc9IlBLQ1MjNyI+CiAgICAgICAgICAgICAgICAgICAgICAgIDxJViBieXRlcz0iMTY"
                    + "iIGVuY29kaW5nPSJCYXNlNjQiPk1USXpORFUyTnpnNU1ERXlNelExTmc9PTwvSVY+CiAgICAgICAgICAgICAgICAgICAgPC9BRVM+CiAgICAgICAgICAgICAgICA8L0VuY3J5cHRpb24+CiAgICAgICAgI"
                    + "CAgICAgICA8RmlsZVNpZ25hdHVyZT4KICAgICAgICAgICAgICAgICAgICA8T3JkaW5hbE51bWJlcj4xPC9PcmRpbmFsTnVtYmVyPgogICAgICAgICAgICAgICAgICAgIDxGaWxlTmFtZT5KUEstVkFULVR"
                    + "FU1QtMDAwMS54bWwuemlwLmFlczwvRmlsZU5hbWU+CiAgICAgICAgICAgICAgICAgICAgPENvbnRlbnRMZW5ndGg+ODAwPC9Db250ZW50TGVuZ3RoPgogICAgICAgICAgICAgICAgICAgIDxIYXNoVmFsd"
                    + "WUgYWxnb3JpdGhtPSJNRDUiIGVuY29kaW5nPSJCYXNlNjQiPjVZbml2RUg0Z3o1V2c1RThNMlh3QVE9PTwvSGFzaFZhbHVlPgogICAgICAgICAgICAgICAgPC9GaWxlU2lnbmF0dXJlPgogICAgICAgICA"
                    + "gICA8L0ZpbGVTaWduYXR1cmVMaXN0PgogICAgICAgIDwvRG9jdW1lbnQ+CiAgICA8L0RvY3VtZW50TGlzdD4KPC9Jbml0VXBsb2FkPgo=</ds:Object></ds:Signature>";
    private static final String URL_STEP1 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/InitUploadSigned";
    private static final String URL_STEP2 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/FinishUpload";
    private static final String URL_STEP3 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/Status/";
    private static final String nazwapliku = "G:\\Dropbox\\JPKFILE\\JPK-VAT-TEST-0001.xml.zip.aes";
    
     public static void main(String[] args) {
        try {
            Object[] in = autoryzacja(plikxml, URL_STEP1);
            int responseCode = (int) in[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany przesyl");
            }
            JSONTokener js = new JSONTokener((Reader) in[0]);
            JSONObject jo = new JSONObject(js);
            String referenceNumber = jo.getString("ReferenceNumber");
            String[] a = new String[1];
            JSONArray job = jo.getJSONArray("RequestToUploadFileList");
            String uri = (String) ((JSONObject) job.get(0)).get("Url");
            String blobname = (String) ((JSONObject) job.get(0)).get("BlobName");
            System.out.println("ref: "+referenceNumber);
            System.out.println("blobname: "+blobname);
            wysylkaAzure(uri, nazwapliku);
            Object[] in1 = zakonczenie(referenceNumber, blobname, URL_STEP2);
            js = new JSONTokener((Reader) in1[0]);
            jo = new JSONObject(js);
            responseCode = (int) in1[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany przesyl");
            } else {
                String message = jo.getString("Message");
                String errors = ((JSONArray) jo.get("Errors")).getString(0);
                System.out.println("message "+message);
                System.out.println("errors "+errors);
            }
            Object[] ink = upo(URL_STEP3, referenceNumber);
            responseCode = (int) ink[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany przesyl");
            }
            js = new JSONTokener((Reader) ink[0]);
            jo = new JSONObject(js);
            Integer Code = (Integer) jo.get("Code");
            String Description = (String) jo.get("Description");
            String Details = (String) jo.get("Details");
            String Timestamp = (String) jo.get("Timestamp");
            String Upo = (String) jo.get("Upo");
            System.out.println("Code "+Code);
            System.out.println("Description "+Description);
            System.out.println("Details "+Details);
            System.out.println("Timestamp "+Timestamp);
            System.out.println("Upo "+Upo);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
        
    
    private static void wysylkaAzure(String ur, String nazwapliku) {
        try {
            // Upload an image file.
            CloudBlockBlob blob = new CloudBlockBlob(new URI(ur));
            File sourceFile = new File(nazwapliku);
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

    private static Object[] autoryzacja(String daneautoryzujace, String URL_autoryzacja) {
        Object[] zwrot = new Object[2];
        try {
            byte[] postData = daneautoryzujace.getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;
            URL url = new URL(URL_autoryzacja);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
               wr.write( postData );
            }
            zwrot = pobierzwiadomosc(conn);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    private static Object[] zakonczenie(String referenceNumber, String blobname, String URLS) {
        Object[] zwrot = new Object[2];
        try {
            String params = "{\"ReferenceNumber\": \"" + referenceNumber + "\",\"AzureBlobNameList\": [\"" + blobname + "\"]}";
            byte[] postData = params.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            URL url = new URL(URLS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }
            zwrot = pobierzwiadomosc(conn);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    private static Object[] upo(String URLS, String referenceNumber) {
        Object[] zwrot = new Object[2];
        try {
            String request = URLS + referenceNumber;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            zwrot = pobierzwiadomosc(conn);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    private static Object[] pobierzwiadomosc(HttpURLConnection conn) {
        Object[] zwrot = new Object[2];
        try {
            Reader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (Exception e) {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            zwrot[0] = in;
            zwrot[1] = (Integer) conn.getResponseCode();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
}

    
