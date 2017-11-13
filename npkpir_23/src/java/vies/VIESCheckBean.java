/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies;

import daoFK.ViesDAO;
import embeddable.VatUe;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import error.E;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Osito
 */
public class VIESCheckBean {
    
    public static void sprawdz(List<VatUe> klienciWDTWNT, ViesDAO viesDAO, Podatnik podatnik, Uz wprowadzil)  {
         if (klienciWDTWNT != null) {
            List<Vies> viesy = new ArrayList<>();
            for (VatUe p : klienciWDTWNT) {
                if (p.getKontrahent() != null && p.getVies() == null) {
                    String kraj = p.getKontrahent().getKrajkod();
                    String nip = p.getKontrahent().getNip();
                    boolean jestprefix = sprawdznip(p.getKontrahent().getNip());
                    if (jestprefix) {
                        nip = p.getKontrahent().getNip().substring(2);
                    }
                    Vies v = null;
                    try {
                        v = VIESCheckBean.pobierz(kraj, nip, p.getKontrahent(), podatnik, wprowadzil);
                        p.setVies(v);
                    } catch (SocketTimeoutException se) {
                        E.e(se);
                    }
                    if (v != null) {
                        viesy.add(v);
                    }
                }
            }
            if (viesy.size() > 0) {
                viesDAO.editList(viesy);
            }
        }
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }
    
    private static Vies pobierz(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        if (kraj.equals("ES")) {
            System.out.println("");
        }
        try {
            Connection.Response res = null;
            if (kraj.equals("ES")) {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                .data("memberStateCode", kraj, "number", nip, "traderName",k.getNpelna(),"traderCompanyType","","traderStreet",k.getUlica(),"traderPostalCode",k.getKodpocztowy(),"traderCity",k.getMiejscowosc(),"requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                .method(Method.POST)
                .execute();
            } else {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", kraj, "number", nip, "requesterMemberStateCode", "PL", "requesterNumber", podatnik.getNip())
                    .method(Method.POST)
                    .execute();
            }
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            if (table != null) {
                Elements tds = table.getElementsByTag("td");
                if (tds != null && tds.size() < 26) {
                    if (tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        zwrot.setData(new Date(tds.get(8).text()));
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    } else if (tds.get(0).text().contains("Member State service unavailable. Please re-submit your request later.")) {
                        zwrot.setPodatnik(podatnik);
                        zwrot.setData(new Date(tds.get(8).text()));
                        zwrot.setWynik(false);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi("sna");
                    }
                } else if (tds != null) {
                    if (tds != null && tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        zwrot.setData(new Date(tds.get(8).text()));
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        String adres = tds.get(16).text()+" "+tds.get(19).text()+" "+tds.get(22).text();
                        zwrot.setAdresfirmy(adres);
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(25).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    }
                }
            } else {
                zwrot =  new Vies();
            }
        } catch (IOException ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }
    
    public static void sprawdz() {
        try {
            Connection.Response res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", "PL", "number", "8511005008", "requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                    .method(Method.POST)
                    .execute();
            
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            Elements tds = table.getElementsByTag("td");
            boolean znalazl = false;
            System.out.println(new Date(tds.get(8).text()).toString());
            for (Element link : tds) {
                String linkText = link.text();
                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
                    znalazl = true;
                    if (!linkText.equals("")) {
                        System.out.println(linkText);
                    }
                } else {
                    System.out.println("Nie znalazl");
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) {
//        sprawdz();
//    }
    
    public static void main(String[] args) {
        try {
            String minfin = "https://test-e-dokumenty.mf.gov.pl/api/Storage/InitUploadSigned";
            String params = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"id-f80fcb47a1716069ca994e2132c0e12d\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><ds:Reference Id=\"r-id-1\" Type=\"http://www.w3.org/2000/09/xmldsig#Object\" URI=\"#o-id-1\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#base64\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>cy5VQ4V2A5zIbQjZqfsOk9fVNzy1dc/8Hgow4KKeRZo=</ds:DigestValue></ds:Reference><ds:Reference Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#xades-id-f80fcb47a1716069ca994e2132c0e12d\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>4MWYCdbM/3yRSEdPo66zPMduADhhjoBy9uDpdMQz59c=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue Id=\"value-id-f80fcb47a1716069ca994e2132c0e12d\">XT6K61mPou7Punk1H43eT8JGR5+qWiDnTbcPcaBQOODc7qX+1LS33UGXZO2Z2RB8fVfH4DMwL5bEg2N1zJIbPVEOXmHAnnAyEWmAUgouUlXv0THq+769XnaAec3b+jpmEs+Q/5XePX+qF1qW3Q4w1g6igtvS6VO9Si1XYUIEtDahyZU4nQaYO0bOVx293mYcqPy54oq9HhWp/LhoZZTzxyxWNOMOV7yhjoE6/fHacptpXo8nGO8Yhs/Gub3XSFlkQ1J0BPrYE6RDhZfPj4fhoS7hh2zFH3oH7qSZh/XnHefCRc45xhSuQNjkAL3pBs3LeCcznt7uA9cdz+P3XrNnnQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIDnjCCAoagAwIBAgIEVyxkyDANBgkqhkiG9w0BAQUFADCBkDELMAkGA1UEBhMCUEwxFDASBgNVBAgTC21hem93aWVja2llMREwDwYDVQQHEwhXYXJzemF3YTEeMBwGA1UEChMVTWluaXN0ZXJzdHdvIEZpbmFuc293MSAwHgYDVQQLExdEZXBhcnRhbWVudCBJbmZvcm1hdHlraTEWMBQGA1UEAxMNanBrLm1mLmdvdi5wbDAeFw0xNjA1MDYwOTMyNTZaFw0yMDA1MDUwOTMyNTZaMIGQMQswCQYDVQQGEwJQTDEUMBIGA1UECBMLbWF6b3dpZWNraWUxETAPBgNVBAcTCFdhcnN6YXdhMR4wHAYDVQQKExVNaW5pc3RlcnN0d28gRmluYW5zb3cxIDAeBgNVBAsTF0RlcGFydGFtZW50IEluZm9ybWF0eWtpMRYwFAYDVQQDEw1qcGsubWYuZ292LnBsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApDhZPQCL8GGe6/rTGP3GT7kSV2tqWVh1QE0XQjoG57QLCowNiROtjzwNsx4bo3TjBjavhmpCmnOCmIIwoPBcSoZslxRyBlbFyE4u5OAGxaiB6gSoapYyfqNxqybiVpSFk8kXUhHswbGY6755Dd9/EuK+R1o8xkkriyHJL6mba1ojppEBEqb0TqxZGUkOAc5DgFmIqgBNqXlQZi2LcdIaRl5xO/vupOWF+Dc5lzV9KcPgWpDyYCJU8PLEIzei4J3HoNYsM9fy3tRAxEeds4+6S+CcOE5rq91HJw+CA2xjZ90olXpuXcxYRwf7PlMP6s4dkVaaAGcrTiPhaDVshChOXwIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQAELmvPmWik0fY6QE++OpqImT/QA4ojqXOsAkmG9QMFoKKcO7dZQm47INz8Ut0y8qKlNtvylGAVa6sQ62krtnnXJNVEnFrU9uS4eNXs4ZC2TqqC/ni8keiXJFzhm59AAgaj/a+isfZ9xXCT6hCWxuRpJJVsyWGrmDgUC5qFlJ3dlHsHdAi7ZLvvQ48EoFU/6HH/RbAsSiWzL5UV6VZi5fBq0kWr0edsgUp9yDVPcGaGPctSsvH9/3znY1WEPbnqfb8sjyMzrd1WXDVAqy2Ng7xrB4YqVFXCgce0tVFVFEOChUF1pUKQ8eFzS4WtevxBFV9kUZvMV3ul/Wj1AKp4YH32</ds:X509Certificate></ds:X509Data></ds:KeyInfo><ds:Object><xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Target=\"#id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedProperties Id=\"xades-id-f80fcb47a1716069ca994e2132c0e12d\"><xades:SignedSignatureProperties><xades:SigningTime>2017-01-30T12:32:53Z</xades:SigningTime><xades:SigningCertificate><xades:Cert><xades:CertDigest><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><ds:DigestValue>kPpw/0EDtMXdBvRnqCrP/fD502I=</ds:DigestValue></xades:CertDigest><xades:IssuerSerial><ds:X509IssuerName>CN=jpk.mf.gov.pl,OU=Departament Informatyki,O=Ministerstwo Finansow,L=Warszawa,ST=mazowieckie,C=PL</ds:X509IssuerName><ds:X509SerialNumber>1462527176</ds:X509SerialNumber></xades:IssuerSerial></xades:Cert></xades:SigningCertificate></xades:SignedSignatureProperties><xades:SignedDataObjectProperties><xades:DataObjectFormat ObjectReference=\"#r-id-1\"><xades:MimeType>text/xml</xades:MimeType></xades:DataObjectFormat></xades:SignedDataObjectProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object><ds:Object Id=\"o-id-1\">PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8SW5pdFVwbG9hZCB4bWxucz0iaHR0cDovL2UtZG9rdW1lbnR5Lm1mLmdvdi5wbCI+CiAgICA8RG9jdW1lbnRUeXBlPkpQSzwvRG9jdW1lbnRUeXBlPgogICAgPFZlcnNpb24+MDEuMDIuMDEuMjAxNjA2MTc8L1ZlcnNpb24+CiAgICA8RW5jcnlwdGlvbktleSBhbGdvcml0aG09IlJTQSIgbW9kZT0iRUNCIiBwYWRkaW5nPSJQS0NTIzEiIGVuY29kaW5nPSJCYXNlNjQiPlJwQkxrZy9ZNE8raVFWMDFBa3N6aEZmTVN5SmxVUWgxSllRcTYyM0FPUGdOYWhHZW1hMVNxQjFDUlcrVDBNZzA2bjdtTWtialRsZFIxbTdYNXdJS04xcWNwbUxJTjJ2ckNROXpPc1AzcG1tcDlxczc1ZVRNVUxzcjZmTEtveFA1a3Z5UlNhSFZVN2tkdzd3WGgxK0pjYzk3V3pXVXd6KzRBWGlIODdTNW5PUklYRXJ3SFFuZ3d1ellOcVRnNXlzQlJCUzF3QjZhRjJ1UjFoQ25KcXpqcEtSaFFjS2FqWlVHdUdLRXJuTVljUWZoUFA1d1VnelFyRzIvcjlHTmhRUGFRbkxxbHdHei8yUXUzR0E2ekRkQWpzQTZPVU9xQnhpQXIrQXc2RDRUQW9ITklPV0pNWXVTNXQ4QitWcGx6Yk5zQ2orQU9Dd1p3Z1ZVbWEzWDYxNFQ3QT09PC9FbmNyeXB0aW9uS2V5PgogICAgPERvY3VtZW50TGlzdD4KICAgICAgICA8RG9jdW1lbnQ+CiAgICAgICAgICAgIDxGb3JtQ29kZSBzeXN0ZW1Db2RlPSJKUEtfVkFUICgyKSIgc2NoZW1hVmVyc2lvbj0iMS0wIj5KUEtfVkFUPC9Gb3JtQ29kZT4KICAgICAgICAgICAgPEZpbGVOYW1lPkpQSy1WQVQtVEVTVC0wMDAxLnhtbDwvRmlsZU5hbWU+CiAgICAgICAgICAgIDxDb250ZW50TGVuZ3RoPjEyMDQ8L0NvbnRlbnRMZW5ndGg+CiAgICAgICAgICAgIDxIYXNoVmFsdWUgYWxnb3JpdGhtPSJTSEEtMjU2IiBlbmNvZGluZz0iQmFzZTY0Ij5hZnRCZ0tXdElUSFJTOXZwcmQ5Qkg3bnNLMm5WU3gydE8zR0V2WXBhWkFVPTwvSGFzaFZhbHVlPgogICAgICAgICAgICA8RmlsZVNpZ25hdHVyZUxpc3QgZmlsZXNOdW1iZXI9IjEiPgogICAgICAgICAgICAgICAgPFBhY2thZ2luZz4KICAgICAgICAgICAgICAgICAgICA8U3BsaXRaaXAgdHlwZT0ic3BsaXQiIG1vZGU9InppcCIvPgogICAgICAgICAgICAgICAgPC9QYWNrYWdpbmc+CiAgICAgICAgICAgICAgICA8RW5jcnlwdGlvbj4KICAgICAgICAgICAgICAgICAgICA8QUVTIHNpemU9IjI1NiIgYmxvY2s9IjE2IiBtb2RlPSJDQkMiIHBhZGRpbmc9IlBLQ1MjNyI+CiAgICAgICAgICAgICAgICAgICAgICAgIDxJViBieXRlcz0iMTYiIGVuY29kaW5nPSJCYXNlNjQiPk1USXpORFUyTnpnNU1ERXlNelExTmc9PTwvSVY+CiAgICAgICAgICAgICAgICAgICAgPC9BRVM+CiAgICAgICAgICAgICAgICA8L0VuY3J5cHRpb24+CiAgICAgICAgICAgICAgICA8RmlsZVNpZ25hdHVyZT4KICAgICAgICAgICAgICAgICAgICA8T3JkaW5hbE51bWJlcj4xPC9PcmRpbmFsTnVtYmVyPgogICAgICAgICAgICAgICAgICAgIDxGaWxlTmFtZT5KUEstVkFULVRFU1QtMDAwMS54bWwuemlwLmFlczwvRmlsZU5hbWU+CiAgICAgICAgICAgICAgICAgICAgPENvbnRlbnRMZW5ndGg+ODAwPC9Db250ZW50TGVuZ3RoPgogICAgICAgICAgICAgICAgICAgIDxIYXNoVmFsdWUgYWxnb3JpdGhtPSJNRDUiIGVuY29kaW5nPSJCYXNlNjQiPjVZbml2RUg0Z3o1V2c1RThNMlh3QVE9PTwvSGFzaFZhbHVlPgogICAgICAgICAgICAgICAgPC9GaWxlU2lnbmF0dXJlPgogICAgICAgICAgICA8L0ZpbGVTaWduYXR1cmVMaXN0PgogICAgICAgIDwvRG9jdW1lbnQ+CiAgICA8L0RvY3VtZW50TGlzdD4KPC9Jbml0VXBsb2FkPgo=</ds:Object></ds:Signature>";
//            RequestBuilder builder = new RequestBuilder("POST", minfin);
//            builder.setHeader( "Content-Type", "text/html" );
//            try {
//              builder.sendRequest( params, null );
//            } catch ( RequestException e ) {
//              // showError(e);
//            }
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

        for (int c; (c = in.read()) >= 0;) {
            System.out.print((char)c);
        }
//            Connection con = Jsoup.connect(minfin)
//                    .method(Connection.Method.POST)
//                    .header("Content-type", "application/x-www-form-urlencoded");
//            con.data("xml",params);
//            Connection.Response res = con.execute();
//            Document doc = res.parse();
        } catch (Exception ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
