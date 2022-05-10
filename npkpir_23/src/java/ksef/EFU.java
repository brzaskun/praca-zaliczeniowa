/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksef;

import error.E;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Osito
 */
public class EFU {
    
     public static void main(String[] args) {
        Object[] zwrot = new Object[2];
        try {
            JsonObjectBuilder joib = Json.createObjectBuilder();
            JsonObjectBuilder joibS = Json.createObjectBuilder();
            joibS.add("type", "onip").add("identifier", "8511005008");
            joib.add("contextIdentifier", joibS.build());
            JsonObject joia = joib.build();
            String dane = joia.toString();
            byte[] out = dane.getBytes(StandardCharsets.UTF_8);
            int outLength = out.length;
            URL url = new URL("https://ksef-demo.mf.gov.pl/api/online/Session/AuthorisationChallenge");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(outLength));
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(out);
            }
            zwrot = pobierzWynikPolaczenia(conn);
            JSONTokener js = new JSONTokener((Reader) zwrot[0]);
            JSONObject jo = new JSONObject(js);
            String challenge = jo.getString("challenge");
            String timestamp = jo.getString("timestamp");
            System.out.println(challenge);
            System.out.println(timestamp);
            TestEncryptWithPublicKey testEncryptWithPublicKey = new TestEncryptWithPublicKey(timestamp);
            byte[] messageencrypter = testEncryptWithPublicKey.encrypt_with_public_key();
            String encodeToString = Base64.getEncoder().encodeToString(messageencrypter);
            System.out.println(encodeToString);
            String plikxml = zwroc(challenge,encodeToString);
            out = plikxml.getBytes(StandardCharsets.UTF_8);
            outLength = out.length;
            url = new URL("https://ksef-demo.mf.gov.pl/api/online/Session/InitToken");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(outLength));
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(out);
            }
            zwrot = pobierzWynikPolaczenia(conn);
            js = new JSONTokener((Reader) zwrot[0]);
            jo = new JSONObject(js);
            JSONObject o1 = (JSONObject) jo.get("sessionToken");
            String tokensesyjny = o1.getString("token");
            System.out.println(tokensesyjny);
            System.out.println("koniec");
        } catch (Exception e) {
            E.e(e);
        }
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
      
     public static String zwroc(String challange, String token) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        +"<ns3:InitSessionTokenRequest "
                +"xmlns=\"http://ksef.mf.gov.pl/schema/gtw/svc/online/types/2021/10/01/0001\" "
                +"xmlns:ns2=\"http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001\" "
                +"xmlns:ns3=\"http://ksef.mf.gov.pl/schema/gtw/svc/online/auth/request/2021/10/01/0001\">"
                +"<ns3:Context>"
                        +"<Challenge>"+challange+"</Challenge>"
                        +"<Identifier xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns2:SubjectIdentifierByCompanyType\">"
                                +"<ns2:Identifier>8511005008</ns2:Identifier>"
                        +"</Identifier>"
                        +"<DocumentType>"
                                +"<ns2:Service>KSeF</ns2:Service>"
                                +"<ns2:FormCode>"
                                        +"<ns2:SystemCode>FA (1)</ns2:SystemCode>"
                                        +"<ns2:SchemaVersion>1-0E</ns2:SchemaVersion>"
                                        +"<ns2:TargetNamespace>http://crd.gov.pl/wzor/2021/11/29/11089/</ns2:TargetNamespace>"
                                        +"<ns2:Value>FA</ns2:Value>"
                                +"</ns2:FormCode>"
                        +"</DocumentType>"
                        +"<Token>"+token+"</Token>"
                +"</ns3:Context>"
        +"</ns3:InitSessionTokenRequest>";

     }
}
