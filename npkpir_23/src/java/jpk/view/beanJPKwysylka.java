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
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Osito
 */
public class beanJPKwysylka {
    private static String plikxml2 = "enveloped.xades";
//    private static String plikxml = "enveloping.xades";
    private static final String URL_STEP1 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/InitUploadSigned";
    private static final String URL_STEP2 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/FinishUpload";
    private static final String URL_STEP3 = "https://test-e-dokumenty.mf.gov.pl/api/Storage/Status/";
//    private static final String nazwapliku = "G:\\Dropbox\\JPKFILE\\JPK-VAT-TEST-0001.xml.zip.aes";
    
    
     public static void main(String[] args) {
         wysylka("G:\\Dropbox\\JPKFILE\\JPK-VAT-TEST-0001.xml.zip.aes", "enveloped.xades");
         System.out.println("zakonczylem wysylke");
     }
    
     public static void wysylka(String nazwapliku, String plikxml) {
        try {
            Object[] in = autoryzacja(plikxml, URL_STEP1);
            int responseCode = (int) in[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany etap1");
            }
            JSONTokener js = new JSONTokener((Reader) in[0]);
            JSONObject jo = new JSONObject(js);
            try {
                String errors =  (String) jo.getJSONArray("Errors").get(0);
                String nieudane =  jo.getString("Message");
                System.out.println("nieudana wysylka: "+nieudane);
                System.out.println("errors: "+errors);
                return;
            } catch (Exception e1){}
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
                System.out.println("Kod 200 udany etap2");
            } else {
                String message = jo.getString("Message");
                String errors = ((JSONArray) jo.get("Errors")).getString(0);
                System.out.println("message "+message);
                System.out.println("errors "+errors);
            }
            Object[] ink = upo(URL_STEP3, referenceNumber);
            responseCode = (int) ink[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany etap 3");
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
            System.out.println("Upo nr: "+Upo);
            System.out.println("Referencenumber nr: "+referenceNumber);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
     
    public static void pobierzupo(String referenceNumber) {
        Object[] ink = upo(URL_STEP3, referenceNumber);
            int responseCode = (int) ink[1];
            if (responseCode == 200) {
                System.out.println("Kod 200 udany etap 3");
            }
            JSONTokener js = new JSONTokener((Reader) ink[0]);
            JSONObject jo = new JSONObject(js);
            Integer Code = (Integer) jo.get("Code");
            String Description = (String) jo.get("Description");
            String Details = (String) jo.get("Details");
            String Timestamp = (String) jo.get("Timestamp");
            String Upo = (String) jo.get("Upo");
            System.out.println("Code "+Code);
            System.out.println("Description "+Description);
            System.out.println("Details "+Details);
            System.out.println("Timestamp "+Timestamp);
            System.out.println("Upo nr: "+Upo);
            System.out.println("Referencenumber nr: "+referenceNumber);
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

    private static Object[] autoryzacja(String filename, String URL_autoryzacja) {
        Object[] zwrot = new Object[2];
        try {
            String daneautoryzujace = new String(Files.readAllBytes(Paths.get(filename)));
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

    
