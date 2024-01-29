/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import embeddable.Mce;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
public class PIT4R_13Bean {
     public static Object[] generujXML(Map<String, Kartawynagrodzen> sumaUmowaoprace, Map<String, Kartawynagrodzen> sumaUmowaopracekosztypodwyzszone, 
             Map<String, Kartawynagrodzen> sumaUmowaoprace26zwolnione, Map<String, Kartawynagrodzen> sumaUmowazlecenia, Map<String, Kartawynagrodzen> sumaUmowapelnieniefunkcji, Map<String, Kartawynagrodzen> sumaUmowazlecenia26zwolnione
             , FirmaKadry firma, byte normalna1korekta2, String kodurzedu, String rok) {
        Object[] zwrot = new Object[3];
        pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja = genPIT4R13(sumaUmowaoprace, sumaUmowaopracekosztypodwyzszone, sumaUmowaoprace26zwolnione, sumaUmowazlecenia, 
                sumaUmowapelnieniefunkcji, sumaUmowazlecenia26zwolnione, firma, normalna1korekta2, kodurzedu, rok);
        String sciezka = null;
        try {
            sciezka = marszajuldoplikuxml(deklaracja);
            zwrot[0] = sciezka;
            zwrot[1] = "ok";
            String nip = deklaracja.getPodmiot1().getOsobaFizyczna()!=null?deklaracja.getPodmiot1().getOsobaFizyczna().getNIP():deklaracja.getPodmiot1().getOsobaNiefizyczna().getNIP();
            String mainfilename = "pit4R_13"+nip+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
                Object[] walidacja = xml.XMLValid_PIT4R_13.walidujPIT4r13(mainfilename);
                zwrot[0] = sciezka;
                zwrot[1] = "ok";
                zwrot[2] = deklaracja;
                Msg.msg("zrob walidacje");
                if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                    zwrot[0] = sciezka;
                    zwrot[1] = "ok";
                    zwrot[2] = deklaracja;
                    Msg.msg("Walidacja JPK pomyślna");
                } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                    zwrot[0] = sciezka;
//                    zwrot[1] = null;
//                    zwrot[2] = null;
                    zwrot[1] = "ok";
                    zwrot[2] = deklaracja;
                    Msg.msg("e", (String) walidacja[1]);
                }
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return zwrot;
    }
    
    public static String marszajuldoplikuxml(pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja) {
        String sciezka = null;
        try {
            if (deklaracja!=null) {
                JAXBContext context = JAXBContext.newInstance(deklaracja.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                String nip = deklaracja.getPodmiot1().getOsobaFizyczna()!=null?deklaracja.getPodmiot1().getOsobaFizyczna().getNIP():deklaracja.getPodmiot1().getOsobaNiefizyczna().getNIP();
                String mainfilename = "pit4R_13"+nip+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+"resources\\xml\\";
                FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
                OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
                marshaller.marshal(deklaracja, writer);
                sciezka = mainfilename;
            } else {
                Msg.msg("e","Nie mozna zachowac danych PIT11 do pliku. Plik jpk pusty");
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }
    
     public static String marszajuldoStringxml(pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja) {
        String zwrot = null;
        try {
            if (deklaracja!=null) {
                JAXBContext context = JAXBContext.newInstance(deklaracja.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                StringWriter sw = new StringWriter();
                marshaller.marshal(deklaracja, sw);
                zwrot = sw.toString();
            } else {
                Msg.msg("e","Nie mozna zachowac danych PIT11 do pliku. Plik jpk pusty");
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
     private static pl.gov.crd.wzor._2023._11._07._12978.Deklaracja genPIT4R13(Map<String, Kartawynagrodzen> sumaUmowaoprace, Map<String, Kartawynagrodzen> sumaUmowaopracekosztypodwyzszone, 
             Map<String, Kartawynagrodzen> sumaUmowaoprace26zwolnione, Map<String, Kartawynagrodzen> sumaUmowazlecenia, Map<String, Kartawynagrodzen> sumaUmowapelnieniefunkcji, Map<String, Kartawynagrodzen> sumaUmowazlecenia26zwolnione, 
             FirmaKadry firma, byte normalna1korekta2, String kodurzedu, String rok) {
        pl.gov.crd.wzor._2023._11._07._12978.ObjectFactory of = new pl.gov.crd.wzor._2023._11._07._12978.ObjectFactory();
        pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja = of.createDeklaracja();
        deklaracja.setNaglowek(of.createTNaglowek());
        deklaracja.setNaglowek(naglowek(normalna1korekta2, kodurzedu, rok));
        deklaracja.setPodmiot1(pracodawca(firma));
        deklaracja.setPozycjeSzczegolowe(pozycjeszczegolowe(sumaUmowaoprace, sumaUmowaopracekosztypodwyzszone, sumaUmowaoprace26zwolnione, sumaUmowazlecenia, sumaUmowapelnieniefunkcji, sumaUmowazlecenia26zwolnione, normalna1korekta2));
        deklaracja.setPouczenia(BigDecimal.ONE);
        return deklaracja;
    }
     

    private static pl.gov.crd.wzor._2023._11._07._12978.TNaglowek naglowek(byte normalna1korekta2, String kodurzedu, String rok) {
        pl.gov.crd.wzor._2023._11._07._12978.TNaglowek naglowek = new pl.gov.crd.wzor._2023._11._07._12978.TNaglowek();
        pl.gov.crd.wzor._2023._11._07._12978.TNaglowek.CelZlozenia celzlozenia = new pl.gov.crd.wzor._2023._11._07._12978.TNaglowek.CelZlozenia();
        celzlozenia.setPoz(celzlozenia.getPoz());
        celzlozenia.setValue(normalna1korekta2);
        naglowek.setCelZlozenia(celzlozenia);
        pl.gov.crd.wzor._2023._11._07._12978.TNaglowek.KodFormularza kodformularza = new pl.gov.crd.wzor._2023._11._07._12978.TNaglowek.KodFormularza();
        kodformularza.setKodPodatku(kodformularza.getKodPodatku());
        kodformularza.setKodSystemowy(kodformularza.getKodSystemowy());
        kodformularza.setRodzajZobowiazania(kodformularza.getRodzajZobowiazania());
        kodformularza.setValue(pl.gov.crd.wzor._2023._11._07._12978.TKodFormularza.PIT_4_R);
        kodformularza.setWersjaSchemy(kodformularza.getWersjaSchemy());
        naglowek.setKodFormularza(kodformularza);
        naglowek.setKodUrzedu(kodurzedu);
        naglowek.setRok(Data.XMLGCinitRok(rok));
//        naglowek.setRok(Data.XMLGCinitRok(rok));
        naglowek.setWariantFormularza((byte)13);
        return naglowek;
    }

    private static pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.Podmiot1 pracodawca(FirmaKadry firma) {
        pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.Podmiot1 pracodawca = new pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.Podmiot1();
        pracodawca.setRola(pracodawca.getRola());
        if (firma.isOsobafizyczna()) {
            pracodawca.setOsobaFizyczna(osobafizyczna(firma));
        } else {
            pracodawca.setOsobaNiefizyczna(osobaniefizyczna(firma));
        }
        return pracodawca;
    }

    private static pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.definicjetypy.TIdentyfikatorOsobyFizycznej2 osobafizyczna(FirmaKadry firma) {
        pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.definicjetypy.TIdentyfikatorOsobyFizycznej2 osobaFizyczna = new pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.definicjetypy.TIdentyfikatorOsobyFizycznej2();
        osobaFizyczna.setNazwisko(firma.getNazwisko());
        osobaFizyczna.setImiePierwsze(firma.getImie());
        osobaFizyczna.setNIP(firma.getNip());
        osobaFizyczna.setDataUrodzenia(Data.dataoddo(firma.getDataurodzenia()));
        return osobaFizyczna;
    }

    private static pl.gov.crd.wzor._2023._11._07._12978.TIdentyfikatorOsobyNiefizycznej osobaniefizyczna(FirmaKadry firma) {
        pl.gov.crd.wzor._2023._11._07._12978.TIdentyfikatorOsobyNiefizycznej osobaNiefizyczna = new pl.gov.crd.wzor._2023._11._07._12978.TIdentyfikatorOsobyNiefizycznej();
        osobaNiefizyczna.setPelnaNazwa(firma.getNazwa());
        osobaNiefizyczna.setNIP(firma.getNip());
        return osobaNiefizyczna;
    }

   

    
    private static pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.PozycjeSzczegolowe pozycjeszczegolowe(Map<String, Kartawynagrodzen> sumaUmowaoprace,
            Map<String, Kartawynagrodzen> sumaUmowaopracekosztypodwyzszone, Map<String, Kartawynagrodzen> sumaUmowaoprace26zwolnione,
            Map<String, Kartawynagrodzen> sumaUmowazlecenia, Map<String, Kartawynagrodzen> sumaUmowapelnieniefunkcji, Map<String, Kartawynagrodzen> sumaUmowazlecenia26zwolnione, byte normalna1korekta2) {
        pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.PozycjeSzczegolowe poz = new pl.gov.crd.wzor._2023._11._07._12978.Deklaracja.PozycjeSzczegolowe();
        if (normalna1korekta2 == (byte) 2) {
            poz.setP7((byte) 1);
        }
        for (String mc : Mce.getMceListS()) {
            double kwota1 = sumaUmowaoprace.get(mc).getPodatekdochodowy();
            if (sumaUmowaoprace.get(mc).isPrzekroczeniedni()) {
                kwota1 = sumaUmowaoprace.get(mc).getPrzekroczenienowypodatek();
            }
            double kwota2 = sumaUmowaopracekosztypodwyzszone.get(mc).getPodatekdochodowy();
            if (sumaUmowaopracekosztypodwyzszone.get(mc).isPrzekroczeniedni()) {
                kwota2 = sumaUmowaopracekosztypodwyzszone.get(mc).getPrzekroczenienowypodatek();
            }
            //ta linijka jest dla podatku od zasiłków
            double kwota3 = sumaUmowaoprace26zwolnione.get(mc).getPodatekdochodowy();
            if (sumaUmowaoprace26zwolnione.get(mc).isPrzekroczeniedni()) {
                kwota3 = sumaUmowaoprace26zwolnione.get(mc).getPrzekroczenienowypodatek();
            }
            int sumakwot = Z.zUD(kwota1 + kwota2 + kwota3);
            Set<String> sumapeseli = new HashSet<>();
            sumapeseli.addAll(sumaUmowaoprace.get(mc).getPesele());
            sumapeseli.addAll(sumaUmowaopracekosztypodwyzszone.get(mc).getPesele());
            sumapeseli.addAll(sumaUmowaoprace26zwolnione.get(mc).getPesele());
            int sumaliczba = sumapeseli.size();
            switch (mc) {
                case "01":
                    poz.setP10(BigInteger.valueOf(sumaliczba));
                    poz.setP16(BigInteger.valueOf(sumakwot));
                    break;
                case "02":
                    poz.setP11(BigInteger.valueOf(sumaliczba));
                    poz.setP17(BigInteger.valueOf(sumakwot));
                    break;
                case "03":
                    poz.setP12(BigInteger.valueOf(sumaliczba));
                    poz.setP18(BigInteger.valueOf(sumakwot));
                    break;
                case "04":
                    poz.setP13(BigInteger.valueOf(sumaliczba));
                    poz.setP19(BigInteger.valueOf(sumakwot));
                    break;
                case "05":
                    poz.setP14(BigInteger.valueOf(sumaliczba));
                    poz.setP20(BigInteger.valueOf(sumakwot));
                    break;
                case "06":
                    poz.setP15(BigInteger.valueOf(sumaliczba));
                    poz.setP21(BigInteger.valueOf(sumakwot));
                    break;
                case "07":
                    poz.setP22(BigInteger.valueOf(sumaliczba));
                    poz.setP28(BigInteger.valueOf(sumakwot));
                    break;
                case "08":
                    poz.setP23(BigInteger.valueOf(sumaliczba));
                    poz.setP29(BigInteger.valueOf(sumakwot));
                    break;
                case "09":
                    poz.setP24(BigInteger.valueOf(sumaliczba));
                    poz.setP30(BigInteger.valueOf(sumakwot));
                    break;
                case "10":
                    poz.setP25(BigInteger.valueOf(sumaliczba));
                    poz.setP31(BigInteger.valueOf(sumakwot));
                    break;
                case "11":
                    poz.setP26(BigInteger.valueOf(sumaliczba));
                    poz.setP32(BigInteger.valueOf(sumakwot));
                    break;
                case "12":
                    poz.setP27(BigInteger.valueOf(sumaliczba));
                    poz.setP33(BigInteger.valueOf(sumakwot));
                    break;
            }
        }
        for (String mc : Mce.getMceListS()) {
            double kwota1 = sumaUmowazlecenia.get(mc).getPodatekdochodowy();
            if (sumaUmowazlecenia.get(mc).isPrzekroczeniedni()) {
                kwota1 = sumaUmowazlecenia.get(mc).getPrzekroczenienowypodatek();
            }
            double kwota2 = sumaUmowapelnieniefunkcji.get(mc).getPodatekdochodowy();
            if (sumaUmowapelnieniefunkcji.get(mc).isPrzekroczeniedni()) {
                kwota2 = sumaUmowapelnieniefunkcji.get(mc).getPrzekroczenienowypodatek();
            }
            double kwota3 = sumaUmowazlecenia26zwolnione.get(mc).getPodatekdochodowy();
            if (sumaUmowazlecenia26zwolnione.get(mc).isPrzekroczeniedni()) {
                kwota3 = sumaUmowazlecenia26zwolnione.get(mc).getPrzekroczenienowypodatek();
            }
            int sumakwot = Z.zUD(kwota1 + kwota2 + kwota3);
            switch (mc) {
                case "01":
                    poz.setP46(BigInteger.valueOf(sumakwot));
                    break;
                case "02":
                    poz.setP47(BigInteger.valueOf(sumakwot));
                    break;
                case "03":
                    poz.setP48(BigInteger.valueOf(sumakwot));
                    break;
                case "04":
                    poz.setP49(BigInteger.valueOf(sumakwot));
                    break;
                case "05":
                    poz.setP50(BigInteger.valueOf(sumakwot));
                    break;
                case "06":
                    poz.setP51(BigInteger.valueOf(sumakwot));
                    break;
                case "07":
                    poz.setP52(BigInteger.valueOf(sumakwot));
                    break;
                case "08":
                    poz.setP53(BigInteger.valueOf(sumakwot));
                    break;
                case "09":
                    poz.setP54(BigInteger.valueOf(sumakwot));
                    break;
                case "10":
                    poz.setP55(BigInteger.valueOf(sumakwot));
                    break;
                case "11":
                    poz.setP56(BigInteger.valueOf(sumakwot));
                    break;
                case "12":
                    poz.setP57(BigInteger.valueOf(sumakwot));
                    break;
            }
        }
        poz.setP70(poz.getP16().add(poz.getP46()));
        poz.setP71(poz.getP17().add(poz.getP47()));
        poz.setP72(poz.getP18().add(poz.getP48()));
        poz.setP73(poz.getP19().add(poz.getP49()));
        poz.setP74(poz.getP20().add(poz.getP50()));
        poz.setP75(poz.getP21().add(poz.getP51()));
        poz.setP76(poz.getP28().add(poz.getP52()));
        poz.setP77(poz.getP29().add(poz.getP53()));
        poz.setP78(poz.getP30().add(poz.getP54()));
        poz.setP79(poz.getP31().add(poz.getP55()));
        poz.setP80(poz.getP32().add(poz.getP56()));
        poz.setP81(poz.getP33().add(poz.getP57()));
        poz.setP122(poz.getP70());
        poz.setP123(poz.getP71());
        poz.setP124(poz.getP72());
        poz.setP125(poz.getP73());
        poz.setP126(poz.getP74());
        poz.setP127(poz.getP75());
        poz.setP128(poz.getP76());
        poz.setP129(poz.getP77());
        poz.setP130(poz.getP78());
        poz.setP131(poz.getP79());
        poz.setP132(poz.getP80());
        poz.setP133(poz.getP81());
        poz.setP146(poz.getP70());
        poz.setP147(poz.getP71());
        poz.setP148(poz.getP72());
        poz.setP149(poz.getP73());
        poz.setP150(poz.getP74());
        poz.setP151(poz.getP75());
        poz.setP152(poz.getP76());
        poz.setP153(poz.getP77());
        poz.setP154(poz.getP78());
        poz.setP155(poz.getP79());
        poz.setP156(poz.getP80());
        poz.setP157(poz.getP81());
        poz.setP171(new BigDecimal(poz.getP70()));
        poz.setP172(new BigDecimal(poz.getP71()));
        poz.setP173(new BigDecimal(poz.getP72()));
        poz.setP174(new BigDecimal(poz.getP73()));
        poz.setP175(new BigDecimal(poz.getP74()));
        poz.setP176(new BigDecimal(poz.getP75()));
        poz.setP177(new BigDecimal(poz.getP76()));
        poz.setP178(new BigDecimal(poz.getP77()));
        poz.setP179(new BigDecimal(poz.getP78()));
        poz.setP180(new BigDecimal(poz.getP79()));
        poz.setP181(new BigDecimal(poz.getP80()));
        poz.setP182(new BigDecimal(poz.getP81()));
        poz.setP158((byte)2);
        return poz;
    }

}
