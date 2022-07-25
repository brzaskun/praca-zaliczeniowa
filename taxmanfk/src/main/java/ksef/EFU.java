/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ksef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import error.E;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Base64;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import ksef.wysylka.SendInvoiceRequest;
import org.json.JSONObject;
import org.json.JSONTokener;
import pl.gov.mf.ksef.schema.gtw.svc.online.auth.request._2021._10._01._0001.InitSessionTokenRequest;
import pl.gov.mf.ksef.schema.gtw.svc.online.types._2021._10._01._0001.AuthorisationContextTokenType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.DocumentTypeType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.FormCodeType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.ServiceType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.SubjectIdentifierByCompanyType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.SubjectIdentifierByType;

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
            //String plikxml = zwroc(challenge,encodeToString);
            String plikxml = autoryzacja(challenge,encodeToString, "8511005008");
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
            //String faktura = fakturaprzygotuj();
            String faktura = fakturaprzygotujjson();
            out = faktura.getBytes(StandardCharsets.UTF_8);
            outLength = out.length;
            url = new URL("https://ksef-demo.mf.gov.pl/api/online/Invoice/Send");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("SessionToken", tokensesyjny);
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(outLength));
            conn.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(out);
            }
            zwrot = pobierzWynikPolaczenia(conn);
            js = new JSONTokener((Reader) zwrot[0]);
            jo = new JSONObject(js);
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
     
//     <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
//<ns3:InitSessionTokenRequest xmlns="http://ksef.mf.gov.pl/schema/gtw/svc/online/types/2021/10/01/0001" xmlns:ns2="http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001" xmlns:ns3="http://ksef.mf.gov.pl/schema/gtw/svc/online/auth/request/2021/10/01/0001">
//    <ns3:Context>
//        <Challenge>20220619-CR-82A1707DC8-B94115392E-A2</Challenge>
//        <Identifier xsi:type="ns2:SubjectIdentifierByCompanyType" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
//            <ns2:Identifier>8511005008</ns2:Identifier>
//        </Identifier>
//        <Token>aXy0HNb/AFxSJvQAmUikVqS4t9fvO+Ktw9i2AeqG4KHEN5Urr7xZ9j8WW9zOjyBD2khty+A5AmpFAg2IvDUPwH6v7iQBuwV/ApCvwGZI4ekRniNxSIp5xkqVsK47HDKWYXKTFPDyTosuk2SReEwIFuoOe/SpW342zuO3SI6RhyjspxBE4zAf/WKvq7BVfyvDS5fs8dBDcfKb00vM5j9XzwmoyDR82QAF2hGN97SjMvCA851bBQduHTHZp6BvWmYTj/A9ENieK1yvJiEnF9mcRpY5FIraBQls+YqMfiVI/5M83HIMGRHxLEuKiXGUpuj7mYF4urlTw3lOh5hJb/zPkQ==</Token>
//    </ns3:Context>
//</ns3:InitSessionTokenRequest>
//"

    private static String autoryzacja(String challenge, String token, String nip) {
        InitSessionTokenRequest init = new InitSessionTokenRequest();
        AuthorisationContextTokenType at = new AuthorisationContextTokenType();
        at.setChallenge(challenge);
        DocumentTypeType dt = new DocumentTypeType();
        dt.setService(ServiceType.K_SE_F);
        FormCodeType fc = new FormCodeType();
        fc.setSchemaVersion("1-0E");
        fc.setSystemCode("FA (1)");
        fc.setTargetNamespace("http://crd.gov.pl/wzor/2021/11/29/11089/");
        fc.setValue("FA");
        dt.setFormCode(fc);
        at.setDocumentType(dt);
        //at.setEncryption(at.getEncryption());
        SubjectIdentifierByType np = new SubjectIdentifierByCompanyType();
        np.setIdentifier(nip);
        at.setIdentifier(np);
        at.setToken(token);
        init.setContext(at);
        return marszajuldoStringu(init);
    }
    //  "invoiceHash": {
//    "hashSHA": {
//      "algorithm": "SHA-256",
//      "encoding": "Base64",
//      "value": "CJEnYYCW7uLvsThTOGOlF9+g6XDZifIH7uTklUgGcJQ="
//    },
//    "fileSize": 344
//  },
//  "invoicePayload": {
//    "type": "PD94bWwgdmVyc2lvbj0iMS4wIj8+DQo8RmFrdHVyYSB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4bWxuczp4c2Q9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hIiB4bWxucz0iaHR0cDovL2NyZC5nb3YucGwvd3pvci8yMDIxLzExLzI5LzExMDg5LyI+DQogIDxQb2RtaW90MT4NCiAgICA8RW1haWw+dGVzdEBnbWFpbC5jb208L0VtYWlsPg0KICA8L1BvZG1pb3QxPg0KPC9GYWt0dXJhPg=="
//  }
    private static String fakturaprzygotuj() {
        SendInvoiceRequest sen = new SendInvoiceRequest();
        try {
            File file = new File("d://fafa1.xml");
            byte[] filebytes = Files.readAllBytes(file.toPath());
            MessageDigest mg = MessageDigest.getInstance("SHA-256");
            byte[] digestsha = mg.digest(filebytes);
            String shabase64 = Base64.getEncoder().encodeToString(digestsha);
            int size = filebytes.length;
            String contentfilebase64 = Base64.getEncoder().encodeToString(filebytes);
            SendInvoiceRequest.InvoiceHash ih = new SendInvoiceRequest.InvoiceHash();
            SendInvoiceRequest.InvoiceHash.HashSHA ih2 = new SendInvoiceRequest.InvoiceHash.HashSHA();
            ih2.setAlgorithm("SHA-256");
            ih2.setEncoding("Base64");
            ih2.setValue(shabase64);
            ih.setHashSHA(ih2);
            ih.setFileSize((short) size);
            sen.setInvoiceHash(ih);
            SendInvoiceRequest.InvoicePayload pl = new SendInvoiceRequest.InvoicePayload();
            pl.setType("plain");
            pl.setInvoiceBody(contentfilebase64);
            sen.setInvoicePayload(pl);
         } catch (Exception ex) {
             
         }
        return marszajuldoStringu1(sen);
    }
    
    private static String fakturaprzygotujjson() {
        SendInvoiceRequest sen = new SendInvoiceRequest();
        String toJson = null;
        try {
            File file = new File("d://fafa1.xml");
            byte[] filebytes = Files.readAllBytes(file.toPath());
            MessageDigest mg = MessageDigest.getInstance("SHA-256");
            mg.update(filebytes);
            byte[] digestsha = mg.digest();
            String shabase64 = Base64.getEncoder().encodeToString(digestsha);
            int size = filebytes.length;
            String contentfilebase64 = Base64.getEncoder().encodeToString(filebytes);
            JsonObjectBuilder joib = Json.createObjectBuilder();
            JsonObjectBuilder joibS = Json.createObjectBuilder();
            joibS.add("type", "onip").add("identifier", "8511005008");
            joib.add("contextIdentifier", joibS.build());
            SendInvoiceRequest.InvoiceHash ih = new SendInvoiceRequest.InvoiceHash();
            SendInvoiceRequest.InvoiceHash.HashSHA ih2 = new SendInvoiceRequest.InvoiceHash.HashSHA();
            ih2.setAlgorithm("SHA-256");
            ih2.setEncoding("Base64");
            ih2.setValue(shabase64);
            ih.setHashSHA(ih2);
            ih.setFileSize((short) size);
            sen.setInvoiceHash(ih);
            SendInvoiceRequest.InvoicePayload pl = new SendInvoiceRequest.InvoicePayload();
            pl.setType("plain");
            pl.setInvoiceBody(contentfilebase64);
            sen.setInvoicePayload(pl);
            GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
            Gson gson = builder.create();
            toJson = gson.toJson(sen);
         } catch (Exception ex) {
             
         }
        return toJson;
    }
    
    
    public static String marszajuldoStringu(InitSessionTokenRequest dekl) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(InitSessionTokenRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        String zwrot = sw.toString();
        return zwrot;
    }
    
    public static String marszajuldoStringu1(SendInvoiceRequest dekl) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(SendInvoiceRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        String zwrot = sw.toString();
        return zwrot;
    }
    
//    
//    Faktura faktura = new Faktura()
//{ 
//    Podmiot1 = new FakturaPodmiot1()
//    {
//        Email = "test@gmail.com",
//    },
//};
//
//XmlSerializer xmlSerializer = new System.Xml.Serialization.XmlSerializer(faktura.GetType());
//String serializedToXml = SerializeToXml(faktura);
//byte[] serialized;
//using (MemoryStream textWriter = new MemoryStream())
//{
//    xmlSerializer.Serialize(textWriter, faktura);
//    serialized = textWriter.ToArray();
//}
//
//string base64ed = Convert.ToBase64String(serialized);
//byte[] hash = SHA256.Create().ComputeHash(serialized);
//
//SendInvoiceRequest invoiceBody = new SendInvoiceRequest(
//    new File1MBHashType(
//        new HashSHAType("SHA-256", "Base64", hash),
//        base64ed.Length
//        ),
//    new InvoicePayloadType(base64ed)
//    );
//
//Końcowy json (z invoiceBody.ToJson()):
//
//{
//  "invoiceHash": {
//    "hashSHA": {
//      "algorithm": "SHA-256",
//      "encoding": "Base64",
//      "value": "CJEnYYCW7uLvsThTOGOlF9+g6XDZifIH7uTklUgGcJQ="
//    },
//    "fileSize": 344
//  },
//  "invoicePayload": {
//    "type": "PD94bWwgdmVyc2lvbj0iMS4wIj8+DQo8RmFrdHVyYSB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4bWxuczp4c2Q9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hIiB4bWxucz0iaHR0cDovL2NyZC5nb3YucGwvd3pvci8yMDIxLzExLzI5LzExMDg5LyI+DQogIDxQb2RtaW90MT4NCiAgICA8RW1haWw+dGVzdEBnbWFpbC5jb208L0VtYWlsPg0KICA8L1BvZG1pb3QxPg0KPC9GYWt0dXJhPg=="
//  }
//}
    
//    {
//  "queryCriteria": {
//    "subjectType": "subject1",
//    "type": "incremental",
//    "acquisitionTimestampThresholdFrom": "2022-06-01T00:00:00",
//    "acquisitionTimestampThresholdTo": "2022-06-20T00:00:00"
//  }
//}
}
