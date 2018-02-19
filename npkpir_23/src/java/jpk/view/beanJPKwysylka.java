/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import deklaracje.upo.Potwierdzenie;
import entity.UPO;
import error.E;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
    private static final String URL_STEP1 = "https://e-dokumenty.mf.gov.pl/api/Storage/InitUploadSigned";
    private static final String URL_STEP2 = "https://e-dokumenty.mf.gov.pl/api/Storage/FinishUpload";
    private static final String URL_STEP3 = "https://e-dokumenty.mf.gov.pl/api/Storage/Status/";
//    private static final String nazwapliku = "G:\\Dropbox\\JPKFILE\\JPK-VAT-TEST-0001.xml.zip.aes";

    public static void main(String[] args) {
        wysylkadoMF("G:\\Dropbox\\JPKFILE\\JPK-VAT-TEST-0001.xml.zip.aes", "enveloped.xades", new UPO());
    }

    public static Object[] wysylkadoMF(String aesfilename, String plikxml, UPO upo) {
        Object[] zwrot = null;
        try {
            Object[] etap1zwrot = etap1(plikxml);
            zwrot = etap1zwrot;
            JSONObject jo = (JSONObject) etap1zwrot[0];
            boolean wynik1 = (boolean) etap1zwrot[1];
            String[] wiadomosc = (String[]) etap1zwrot[2];
            String referenceNumber = (String) etap1zwrot[3];
            boolean wynik2 = false;
            if (wynik1) {
                Object[] etap2zwrot = etap2(referenceNumber, aesfilename, jo);
                wynik2 = (boolean) etap2zwrot[1];
                zwrot = etap2zwrot;
            }
            if (wynik2) {
                Object[] etap3zwrot = etap3(referenceNumber, upo);
                zwrot = etap3zwrot;
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }

    public static Object[] etap1(String plikxml) {
        Object[] zwrot = new Object[5];
        JSONObject jo = null;
        boolean wynik = false;
        String[] wiadomosc = new String[2];
        wiadomosc[0] = "i";
        wiadomosc[1] = "Rozpoczęcie autoryzacji do serwisu";
        String referenceNumber = null;
        Object[] in = autoryzacja(plikxml, URL_STEP1);
        int responseCode = (int) in[1];
        if (responseCode == 200) {
            wiadomosc[0] = "i";
            wiadomosc[1] = "Udane połączenie z serwisem";
            wynik = true;
            JSONTokener js = new JSONTokener((Reader) in[0]);
            jo = new JSONObject(js);
            referenceNumber = jo.getString("ReferenceNumber");
        } else {
            JSONTokener js = new JSONTokener((Reader) in[0]);
            jo = new JSONObject(js);
            try {
                String errors = (String) jo.getJSONArray("Errors").get(0);
                String nieudane = jo.getString("Message");
                System.out.println("nieudana wysylka: " + nieudane);
                wiadomosc[0] = "e";
                wiadomosc[1] = "Błąd połączenia z serwisem " + nieudane;
            } catch (Exception e1) {
            }
            try {
                String nieudane = jo.getString("Message");
                wiadomosc[0] = "e";
                wiadomosc[1] = "Błąd wysyłki. " + nieudane;
            } catch (Exception e1) {
            }
        }
        zwrot[0] = jo;
        zwrot[1] = wynik;
        zwrot[2] = wiadomosc;
        zwrot[3] = referenceNumber;
        zwrot[4] = 1;
        return zwrot;
    }

    public static Object[] etap2(String referenceNumber, String aesfilename, JSONObject jo) {
        Object[] zwrot = new Object[5];
        boolean wynik = false;
        String[] wiadomosc = new String[2];
        wiadomosc[0] = "i";
        wiadomosc[1] = "Rozpoczęcie wysyłki pliku jpk do serwisu Azure";
        JSONArray job = jo.getJSONArray("RequestToUploadFileList");
        String uri = (String) ((JSONObject) job.get(0)).get("Url");
        String blobname = (String) ((JSONObject) job.get(0)).get("BlobName");
        System.out.println("ref: " + referenceNumber);
        wysylkaAzure(uri, aesfilename);
        Object[] in1 = zakonczenie(referenceNumber, blobname, URL_STEP2);
        JSONTokener js = new JSONTokener((Reader) in1[0]);
        jo = new JSONObject(js);
        int responseCode = (int) in1[1];
        if (responseCode == 200) {
            wynik = true;
        } else {
            String message = jo.getString("Message");
            String errors = ((JSONArray) jo.get("Errors")).getString(0);
            System.out.println("message " + message);
            wiadomosc[0] = "i";
            wiadomosc[1] = "Nieudane wyslanie pliku do Azure "+message;
        }
        zwrot[0] = jo;
        zwrot[1] = wynik;
        zwrot[2] = wiadomosc;
        zwrot[3] = referenceNumber;
        zwrot[4] = 2;
        return zwrot;
    }

    public static Object[] etap3(String referenceNumber, UPO upo) {
        Object[] zwrot = new Object[5];
        JSONObject jo = null;
        boolean wynik = false;
        String[] wiadomosc = new String[2];
        wiadomosc[0] = "i";
        wiadomosc[1] = "Rozpoczęcie autoryzacji do serwisu";
        Object[] ink = upo(URL_STEP3, referenceNumber);
        int responseCode = (int) ink[1];
        if (responseCode == 200) {
            wiadomosc[0] = "i";
            wiadomosc[1] = "Udane połączenie z serwisem. Pobieranie UPO";
            wynik = true;
            System.out.println("Kod 200 udany etap 3");
            JSONTokener js = new JSONTokener((Reader) ink[0]);
            jo = new JSONObject(js);
            Integer Code = (Integer) jo.get("Code");
            String Description = (String) jo.get("Description");
            String Details = (String) jo.get("Details");
            String Timestamp = (String) jo.get("Timestamp");
            String UpoString = (String) jo.get("Upo");
            upo.setCode(Code);
            upo.setDescription(Description);
            upo.setDetails(Details);
            upo.setTimestamp(Timestamp);
            upo.setUpoString(UpoString);
            upo.setReferenceNumber(referenceNumber);
            System.out.println("Code " + Code);
            System.out.println("Description " + Description);
            System.out.println("Details " + Details);
            System.out.println("Timestamp " + Timestamp);
            System.out.println("Upo nr: " + UpoString);
            JAXBContext context;
            try {
                context = JAXBContext.newInstance(Potwierdzenie.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                int odint = UpoString.indexOf("<Potwierdzenie");
                int doint = UpoString.indexOf("</Potwierdzenie>") + 16;
                if (odint > -1 && doint > -1) {
                    String potw = UpoString.substring(odint, doint);
                    Potwierdzenie potwierdzenie = (Potwierdzenie) unmarshaller.unmarshal(new StringReader(potw));
                    upo.setPotwierdzenie(potwierdzenie);
                    //upo.setJpk(jpk);
                }
            } catch (JAXBException ex) {
                Logger.getLogger(beanJPKwysylka.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (Code==200 || Code==120) {
                wiadomosc[0] = "i";
                wiadomosc[1] = "Sukces, zakończono wysyłkę bez błędów";
                zwrot[4] = 3;
            } else if (Code>300 && Code <400){
                wynik = false;
                wiadomosc[0] = "i";
                wiadomosc[1] = "Trwa przetwarzanie. "+Description;
                zwrot[4] = 4;
            } else {
                wynik = false;
                wiadomosc[0] = "e";
                wiadomosc[1] = "Wysyłka zakończona błędem krytycznym. "+Description;
                zwrot[4] = 4;
            }
        } else {
            wiadomosc[0] = "i";
            wiadomosc[1] = "Nie udało się pobrać UPO. Wystąpił błąd";
            zwrot[4] = 4;
        }
        zwrot[0] = jo;
        zwrot[1] = wynik;
        zwrot[2] = wiadomosc;
        zwrot[3] = upo;
        return zwrot;
    }

    private static void wysylkaAzure(String ur, String nazwapliku) {
        try {
            // Upload an image file.
            CloudBlockBlob blob = new CloudBlockBlob(new URI(ur));
            File sourceFile = new File(nazwapliku);
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.print("FileNotFoundException encountered: ");
            System.exit(-1);
        } catch (StorageException storageException) {
            System.out.print("StorageException encountered: ");
            System.exit(-1);
        } catch (Exception e) {
            System.out.print("Exception encountered: ");
            System.exit(-1);
        }
    }

    private static Object[] autoryzacja(String filename, String URL_autoryzacja) {
        Object[] zwrot = new Object[2];
        try {
            String daneautoryzujace = new String(Files.readAllBytes(Paths.get(filename)));
            byte[] postData = daneautoryzujace.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            URL url = new URL(URL_autoryzacja);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }
            zwrot = pobierzWynikPolaczenia(conn);
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
            zwrot = pobierzWynikPolaczenia(conn);
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
            zwrot = pobierzWynikPolaczenia(conn);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    private static Object[] pobierzWynikPolaczenia(HttpURLConnection conn) {
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
