/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania;
import com.sun.xml.ws.developer.WSBindingProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.AddressingFeature;
import org.tempuri.IUslugaBIRzewnPubl;
import org.tempuri.UslugaBIRzewnPubl;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "UslugaBIRzewnPubl", portName = "e3", endpointInterface = "org.tempuri.IUslugaBIRzewnPubl", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/UslugaBIRzewnPubl1.wsdl")
public class NewWebServiceFromWSDL {

    public java.lang.String pobierzCaptcha() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.Boolean sprawdzCaptcha(java.lang.String pCaptcha) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String getValue(java.lang.String pNazwaParametru) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String setValue(java.lang.String pNazwaParametru, java.lang.String pWartoscParametru) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static java.lang.String zaloguj(java.lang.String pKluczUzytkownika) {
            UslugaBIRzewnPubl service = new UslugaBIRzewnPubl();
            IUslugaBIRzewnPubl e3 = service.getE3(new AddressingFeature());
            String login = e3.zaloguj("e19dbcf03de941479bad");
            return login;
    }

    public java.lang.Boolean wyloguj(java.lang.String pIdentyfikatorSesji) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String daneSzukaj(cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania pParametryWyszukiwania) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String danePobierzPelnyRaport(java.lang.String pRegon, java.lang.String pNazwaRaportu) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String daneKomunikat() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public void pobierz(String nip) {
        Map<String, String> zwrot = new ConcurrentHashMap<>();
        UslugaBIRzewnPubl service = new UslugaBIRzewnPubl();
            IUslugaBIRzewnPubl e3 = service.getE3(new AddressingFeature());
            String login = e3.zaloguj("e19dbcf03de941479bad");
        WSBindingProvider bp = (WSBindingProvider) e3;
            Map<String, Object> req_ctx = ((BindingProvider)e3).getRequestContext();
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("sid", Collections.singletonList(login));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            //bp.setOutboundHeaders(Headers.create(new QName("http://tempuri.org/","sid"),login));
            String statussesji = e3.getValue("StatusSesji");
            if (statussesji.equals("1")) {
                ParametryWyszukiwania pw = new ParametryWyszukiwania();
                JAXBElement<String> jb = new JAXBElement(new QName("http://CIS/BIR/PUBL/2014/07/DataContract","Nip"), String.class, nip);
                pw.setNip(jb);
                String res = e3.daneSzukaj(pw);
                if (res.equals("")) {
                    zwrot.put("Nieznaleziono", nip);
                } else {
                    Map<String, String> zwrottmp = wyslijdanefirmy(pozycje, res);
                    String typjedn = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportTypJednostki");
                    String rapszcz = null;
                    String prawna = "<Typ>P</Typ>";
                    zwrot = wyslijdanefirmy(pozycje, res);
                    if (typjedn.contains(prawna)) {
                        rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportPrawna");
                        zwrot.put("Typ", "P");
                        zwrot.putAll(wyslijdanefirmy(pozycje2praw, rapszcz));
                    } else {
                        zwrot.put("Typ", "F");
                        rapszcz = e3.danePobierzPelnyRaport(zwrottmp.get("Regon"), "PublDaneRaportDzialalnoscFizycznejCeidg");
                        zwrot.putAll(wyslijdanefirmy(pozycje2fiz, rapszcz));
                    }
                }
                e3.wyloguj("e19dbcf03de941479bad");
            }
        System.out.println("");
    }
    
    public static List<String> pozycje;
    static {
        pozycje = new ArrayList();
        pozycje.add("Regon");
        pozycje.add("Nazwa");
        pozycje.add("Wojewodztwo");
        pozycje.add("Powiat");
        pozycje.add("Gmina");
        pozycje.add("Miejscowosc");
        pozycje.add("KodPocztowy");
        pozycje.add("Ulica");
   }
   
    public static List<String> pozycje2fiz;
    static {
        pozycje2fiz = new ArrayList();
        pozycje2fiz.add("fiz_adSiedzUlica_Nazwa");
        pozycje2fiz.add("fiz_adSiedzNumerNieruchomosci");
        pozycje2fiz.add("fiz_adSiedzNumerLokalu");
        pozycje2fiz.add("fiz_adSiedzMiejscowoscPoczty_Nazwa");
        
   }
    
     private static Map<String, String>  wyslijdanefirmy(List<String> pozycje, String rezultat) {
        Map<String, String> zwrot = new ConcurrentHashMap<>();
        if (!rezultat.equals("")) {
            for (String p : pozycje) {
                int start = rezultat.indexOf("<"+p+">");
                int stop = rezultat.indexOf("</"+p+">");
                if (start > -1 && stop > -1) {
                    String element = rezultat.substring(start, stop);
                    start = element.indexOf(">")+1;
                    element = element.substring(start);
                    System.out.println(element);
                    zwrot.put(p, element);
                }
            }
        }
        return zwrot;
    }
    
    public static List<String> pozycje2praw;
    static {
        pozycje2praw = new ArrayList();
        pozycje2praw.add("praw_adSiedzUlica_Nazwa");
        pozycje2praw.add("praw_adSiedzNumerNieruchomosci");
        pozycje2praw.add("praw_adSiedzNumerLokalu");
        pozycje2praw.add("praw_adSiedzMiejscowoscPoczty_Nazwa");
   }
}
