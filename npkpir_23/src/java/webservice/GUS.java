/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania;
import com.sun.xml.ws.developer.WSBindingProvider;
import error.E;
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
import org.apache.commons.lang3.StringUtils;
import org.tempuri.IUslugaBIRzewnPubl;
import org.tempuri.UslugaBIRzewnPubl;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "UslugaBIRzewnPubl", portName = "e3", endpointInterface = "org.tempuri.IUslugaBIRzewnPubl", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/UslugaBIRzewnPubl1.wsdl")
public class GUS {

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

    public java.lang.String zaloguj(java.lang.String pKluczUzytkownika) {
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
    
    public Map<String, String> pobierz(String nip) {
        Map<String, String> zwrot = new ConcurrentHashMap<>();
        try {
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
                        zwrot.put("fiz_szczegolnaFormaPrawna_Symbol","099");
                        zwrot.putAll(wyslijdanefirmy(pozycje2fiz, rapszcz));
                    }
                }
                e3.wyloguj("e19dbcf03de941479bad");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static List<String> pozycje;
    static {
        pozycje = new ArrayList();
        pozycje.add("Regon");
        pozycje.add("Nazwa");
        pozycje.add("NazwaOryginal");
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
        //NOWE!!!
        pozycje2fiz.add("fiz_dataRozpoczeciaDzialalnosci");
        pozycje2fiz.add("fiz_dataZawieszeniaDzialalnosci");
        pozycje2fiz.add("fiz_dataWznowieniaDzialalnosci");
        pozycje2fiz.add("fiz_dataZaistnieniaZmianyDzialalnosci");
        pozycje2fiz.add("fiz_dataZakonczeniaDzialalnosci");
        pozycje2fiz.add("fiz_dataZaistnieniaZmiany");
        pozycje2fiz.add("fiz_dataZakonczeniaDzialalnosci");
        
   }
    
    public static List<String> pozycje2praw;
    static {
        pozycje2praw = new ArrayList();
        pozycje2praw.add("praw_adSiedzUlica_Nazwa");
        pozycje2praw.add("praw_adSiedzNumerNieruchomosci");
        pozycje2praw.add("praw_adSiedzNumerLokalu");
        pozycje2praw.add("praw_adSiedzMiejscowoscPoczty_Nazwa");
        pozycje2praw.add("praw_dataRozpoczeciaDzialalnosci");
        pozycje2praw.add("praw_numerWrejestrzeEwidencji");
        //Nowe
        pozycje2praw.add("praw_podstawowaFormaPrawna_Symbol");
        pozycje2praw.add("praw_szczegolnaFormaPrawna_Symbol");
        pozycje2praw.add("praw_dataRozpoczeciaDzialalnosci");
        pozycje2praw.add("praw_dataZawieszeniaDzialalnosci");
        pozycje2praw.add("praw_dataWznowieniaDzialalnosci");
        pozycje2praw.add("praw_dataZaistnieniaZmianyDzialalnosci");
        pozycje2praw.add("praw_dataZakonczeniaDzialalnosci");
        pozycje2praw.add("praw_dataZaistnieniaZmiany");
        pozycje2praw.add("praw_dataZakonczeniaDzialalnosci");
   }
    
    
    public static String RES = "\"<root>\n" +
"  <dane>\n" +
"    <Regon>32018253600000</Regon>\n" +
"    <RegonLink>&lt;a href='javascript:danePobierzPelnyRaport(\"32018253600000\",\"DaneRaportPrawnaPubl\", 0);'&gt;320182536&lt;/a&gt;</RegonLink>\n" +
"    <Nazwa>KARMA SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ</Nazwa>\n" +
"    <Wojewodztwo>WIELKOPOLSKIE</Wojewodztwo>\n" +
"    <Powiat>m. Poznań</Powiat>\n" +
"    <Gmina>Poznań-Jeżyce</Gmina>\n" +
"    <Miejscowosc>Poznań</Miejscowosc>\n" +
"    <KodPocztowy>60-419</KodPocztowy>\n" +
"    <Ulica>ul. Rogozińska</Ulica>\n" +
"    <Typ>P</Typ>\n" +
"    <SilosID>6</SilosID>\n" +
"  </dane>\n" +
"</root>\"";
    
    public static String zmniejsznazwe(String element, String p) {
        String zwrot = element;
        if (p.equals("Nazwa")) {
            if (element.contains("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE", "PPU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PROJEKTOWO USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO USŁUGOWE", "PPU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO TRANSPORTOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO TRANSPORTOWE", "PHT");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWE", "PHU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO USŁUGOWO HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO USŁUGOWO HANDLOWE", "PUH");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWO PRODUKCYJNE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO USŁUGOWO PRODUKCYJNE", "PHUP");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO PRODUKCYJNO USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO PRODUKCYJNO USŁUGOWE", "PHUP");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO USŁUGOWO HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO USŁUGOWO HANDLOWE", "PPHU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWO USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWO USŁUGOWE", "PPHU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO HANDLOWE", "PPH");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PROJEKTOWO-USŁUGOWE", "PPU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-TRANSPORTOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-TRANSPORTOWE", "PHT");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWE", "PHU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO USŁUGOWO-HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO USŁUGOWO-HANDLOWE", "PUH");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-USŁUGOWO-HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-USŁUGOWO-HANDLOWE", "PHUP");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWO-PRODUKCYJNE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-USŁUGOWO-PRODUKCYJNE", "PHUP");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO HANDLOWO-PRODUKCYJNO-USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO HANDLOWO-PRODUKCYJNO-USŁUGOWE", "PHUP");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWO-USŁUGOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWO-USŁUGOWE", "PPHU");
            } else if (zwrot.contains("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWE")) {
                zwrot = zwrot.replace("PRZEDSIĘBIORSTWO PRODUKCYJNO-HANDLOWE", "PPH");
            } else if (zwrot.contains("STOWARZYSZENIE")) {
                zwrot = zwrot.replace("STOWARZYSZENIE", "Stowarzyszenie");
            } else if (zwrot.contains("FUNDACJA")) {
                zwrot = zwrot.replace("FUNDACJA", "Fundacja");
            }
            String[] a = StringUtils.splitPreserveAllTokens(zwrot);
            if (element.contains("SPÓŁKA KOMANDYTOWA")) {
                zwrot = element.replace("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ SPÓŁKA KOMANDYTOWA", "sp. z o.o. sp.k.");
            } else if (element.contains("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ")) {
                zwrot = element.replace("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ", "sp. z o.o.");
            } else if (element.contains("SPÓŁKA AKCYJNA")) {
                zwrot = element.replace("SPÓŁKA AKCYJNA", "S.A.");
            } else if (element.contains("SPÓŁKA CYWILNA")) {
                zwrot = element.replace("SPÓŁKA CYWILNA", "S.C.");
            } else if (element.contains("SPÓŁKA JAWNA")) {
                zwrot = element.replace("SPÓŁKA JAWNA", "SP.J.");
            } else if (a.length>3) {
                zwrot = zloznazwe(a,a.length);
            } else if (a.length==3) {
                zwrot = a[0]+" "+StringUtils.capitalize(StringUtils.lowerCase(a[1]))+" "+StringUtils.capitalize(StringUtils.lowerCase(a[2]));
                if (zwrot.contains(" S.c.")) {
                    zwrot = zwrot.replace(" S.c.", " S.C.");
                }
            } else if (a.length==2) {
                zwrot = StringUtils.capitalize(StringUtils.lowerCase(a[0]))+" "+StringUtils.capitalize(StringUtils.lowerCase(a[1]));
            }
        } else if (p.equals("Wojewodztwo")) {
            zwrot = element.substring(0,1).toUpperCase()+element.substring(1).toLowerCase();
        } else if (p.equals("Ulica")) {
            zwrot = element.substring(3);
        }
        return zwrot;
    }
    
    private static String zloznazwe(String[] a, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length-2; i++) {
            sb.append(a[i]);
            sb.append(" ");
        }
        sb.append(StringUtils.capitalize(StringUtils.lowerCase(a[length-2])));
        sb.append(" ");
        sb.append(StringUtils.capitalize(StringUtils.lowerCase(a[length-1])));
        String zwrot = sb.toString();
        if (zwrot.contains(" S.c.")) {
            zwrot = zwrot.replace(" S.c.", " S.C.");
        }
        return zwrot;
    }
//    private String drukujdanefirmy(String wiersz) {
//        String zwrot = "brak danych";
//        if (nip.length()<10) {
//            zwrot = "NIP za krótki";
//        } else if (!wiersz.equals("")) {
//            StringBuilder sb = new StringBuilder();
//            for (String p : pozycje) {
//                int start = wiersz.indexOf("<"+p+">");
//                int stop = wiersz.indexOf("</"+p+">");
//                if (start > -1 && stop > -1) {
//                    String element = wiersz.substring(start, stop);
//                    start = element.indexOf(">")+1;
//                    element = element.substring(start);
//                    element = zmniejsznazwe(element,p);
//                    sb.append(p);
//                    sb.append(" ");
//                    sb.append(element);
//                    sb.append("\n");
//                }
//            }
//            zwrot = sb.toString();
//        }
//        return zwrot;
//    }
    
    private Map<String, String>  wyslijdanefirmy(List<String> pozycje, String rezultat) {
        Map<String, String> zwrot = new ConcurrentHashMap<>();
        if (!rezultat.equals("")) {
            for (String p : pozycje) {
                int start = rezultat.indexOf("<"+p+">");
                int stop = rezultat.indexOf("</"+p+">");
                if (start > -1 && stop > -1) {
                    String element = rezultat.substring(start, stop);
                    start = element.indexOf(">")+1;
                    element = element.substring(start);
                    if (p.equals("Nazwa")) {
                        zwrot.put("NazwaOryginal", element);
                    }
                    element = zmniejsznazwe(element,p);
                    zwrot.put(p, element);
                }
            }
        }
        return zwrot;
    }
}
