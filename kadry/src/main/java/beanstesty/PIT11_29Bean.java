/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.Pracownik;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import pl.gov.crd.wzor._2022._11._09._11890.Deklaracja;
import pl.gov.crd.wzor._2022._11._09._11890.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.wzor._2022._11._09._11890.TKodFormularza;
import pl.gov.crd.wzor._2022._11._09._11890.TNaglowek;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TIdentyfikatorOsobyFizycznej1;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TKodKraju;
import z.Z;

/**
 *
 * @author Osito
 */
public class PIT11_29Bean {
     public static Object[] generujXML(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok, Map<String,Kartawynagrodzen> sumy) {
        Object[] zwrot = new Object[3];
        pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja = genPIT1129(kartawynagrodzen, firma, pracownik, normalna1korekta2, kodurzedu, rok, sumy);
        String sciezka = null;
        try {
            sciezka = marszajuldoplikuxml(deklaracja);
            zwrot[0] = sciezka;
            zwrot[1] = "ok";
            String mainfilename = "pit11_29"+deklaracja.getPodmiot2().getOsobaFizyczna().getPESEL()+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
                Object[] walidacja = xml.XMLValid_PIT11_29.walidujPIT1129(mainfilename);
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
                    zwrot[1] = null;
                    zwrot[2] = null;
                    Msg.msg("e", (String) walidacja[1]);
                }
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return zwrot;
    }
    
    public static String marszajuldoplikuxml(pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja) {
        String sciezka = null;
        try {
            if (deklaracja!=null) {
                JAXBContext context = JAXBContext.newInstance(deklaracja.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                String mainfilename = "pit11_29"+deklaracja.getPodmiot2().getOsobaFizyczna().getPESEL()+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
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
    
     private static Deklaracja genPIT1129(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok, Map<String,Kartawynagrodzen> sumy) {
        pl.gov.crd.wzor._2022._11._09._11890.ObjectFactory of = new pl.gov.crd.wzor._2022._11._09._11890.ObjectFactory();
        Deklaracja deklaracja = of.createDeklaracja();
        deklaracja.setNaglowek(of.createTNaglowek());
        deklaracja.setNaglowek(naglowek(normalna1korekta2, kodurzedu, rok));
        deklaracja.setPodmiot1(pracodawca(firma));
        deklaracja.setPodmiot2(pracownik(pracownik));
        deklaracja.setPozycjeSzczegolowe(pozycjeszczegolowe(kartawynagrodzen, pracownik, sumy));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return deklaracja;
    }
     
      private TNaglowek.CelZlozenia celzlozenia(byte normalna1korekta2) {
        TNaglowek.CelZlozenia zwrot = new TNaglowek.CelZlozenia();
        zwrot.setValue(normalna1korekta2);
        zwrot.setPoz(zwrot.getPoz());
        return zwrot;
    }

    private static pl.gov.crd.wzor._2022._11._09._11890.TNaglowek naglowek(byte normalna1korekta2, String kodurzedu, String rok) {
        TNaglowek naglowek = new TNaglowek();
        TNaglowek.CelZlozenia celzlozenia = new TNaglowek.CelZlozenia();
        celzlozenia.setPoz(celzlozenia.getPoz());
        celzlozenia.setValue(normalna1korekta2);
        naglowek.setCelZlozenia(celzlozenia);
        TNaglowek.KodFormularza kodformularza = new TNaglowek.KodFormularza();
        kodformularza.setKodPodatku(kodformularza.getKodPodatku());
        kodformularza.setKodSystemowy(kodformularza.getKodSystemowy());
        kodformularza.setRodzajZobowiazania(kodformularza.getRodzajZobowiazania());
        kodformularza.setValue(TKodFormularza.PIT_11);
        kodformularza.setWersjaSchemy(kodformularza.getWersjaSchemy());
        naglowek.setKodFormularza(kodformularza);
        naglowek.setKodUrzedu(kodurzedu);
        naglowek.setRok(Data.XMLGCinitRok("2022"));
//        naglowek.setRok(Data.XMLGCinitRok(rok));
        naglowek.setWariantFormularza((byte)29);
        return naglowek;
    }

    private static pl.gov.crd.wzor._2022._11._09._11890.Deklaracja.Podmiot1 pracodawca(FirmaKadry firma) {
        Deklaracja.Podmiot1 pracodawca = new Deklaracja.Podmiot1();
        pracodawca.setRola(pracodawca.getRola());
        if (firma.isOsobafizyczna()) {
            pracodawca.setOsobaFizyczna(osobafizyczna(firma));
        } else {
            pracodawca.setOsobaNiefizyczna(osobaniefizyczna(firma));
        }
        return pracodawca;
    }

    private static TIdentyfikatorOsobyFizycznej1 osobafizyczna(FirmaKadry firma) {
        TIdentyfikatorOsobyFizycznej1 tIdentyfikatorOsobyFizycznej = new TIdentyfikatorOsobyFizycznej1();
        tIdentyfikatorOsobyFizycznej.setNazwisko(firma.getNazwisko());
        tIdentyfikatorOsobyFizycznej.setImiePierwsze(firma.getImie());
        tIdentyfikatorOsobyFizycznej.setNIP(firma.getNip());
        tIdentyfikatorOsobyFizycznej.setDataUrodzenia(Data.dataoddo(firma.getDataurodzenia()));
        return tIdentyfikatorOsobyFizycznej;
    }

    private static TIdentyfikatorOsobyNiefizycznej osobaniefizyczna(FirmaKadry firma) {
        TIdentyfikatorOsobyNiefizycznej tIdentyfikatorOsobyNiefizycznej = new TIdentyfikatorOsobyNiefizycznej();
        tIdentyfikatorOsobyNiefizycznej.setPelnaNazwa(firma.getNazwa());
        tIdentyfikatorOsobyNiefizycznej.setNIP(firma.getNip());
        return tIdentyfikatorOsobyNiefizycznej;
    }

    private static Deklaracja.Podmiot2 pracownik(Pracownik pracownik) {
        Deklaracja.Podmiot2 podmiot2 = new Deklaracja.Podmiot2();
        podmiot2.setRola(podmiot2.getRola());
        podmiot2.setOsobaFizyczna(pracownikdane(pracownik));
        podmiot2.setAdresZamieszkania(adres(pracownik));
        return podmiot2;
    }

    private static Deklaracja.Podmiot2.OsobaFizyczna pracownikdane(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna osobafizyczna = new Deklaracja.Podmiot2.OsobaFizyczna();
        osobafizyczna.setNazwisko(pracownik.getNazwisko());
        osobafizyczna.setImiePierwsze(pracownik.getImie());
        osobafizyczna.setDataUrodzenia(Data.dataoddo(pracownik.getDataurodzenia()));
        osobafizyczna.setPESEL(pracownik.getPesel());
//        osobafizyczna.setKodKrajuWydania(pobierzkraj(pracownik));
//        osobafizyczna.setRodzajNrId(rodzajid(pracownik));
        return osobafizyczna;
    }

    private static Deklaracja.Podmiot2.AdresZamieszkania adres(Pracownik pracownik) {
        Deklaracja.Podmiot2.AdresZamieszkania adres= new Deklaracja.Podmiot2.AdresZamieszkania();
        adres.setRodzajAdresu(adres.getRodzajAdresu());
        Deklaracja.Podmiot2.AdresZamieszkania.KodKraju kraj = new Deklaracja.Podmiot2.AdresZamieszkania.KodKraju();
        kraj.setPoz(kraj.getPoz());
        kraj.setValue(TKodKraju.fromValue(pracownik.getKrajsymbol()));
        adres.setKodKraju(kraj);
        adres.setWojewodztwo(pracownik.getWojewodztwo());
        adres.setPowiat(pracownik.getPowiat());
        adres.setGmina(pracownik.getGmina());
        Deklaracja.Podmiot2.AdresZamieszkania.Miejscowosc miejscowosc = new Deklaracja.Podmiot2.AdresZamieszkania.Miejscowosc();
        miejscowosc.setPoz(miejscowosc.getPoz());
        miejscowosc.setValue(pracownik.getMiasto());
        adres.setMiejscowosc(miejscowosc);
        Deklaracja.Podmiot2.AdresZamieszkania.Ulica ulica = new Deklaracja.Podmiot2.AdresZamieszkania.Ulica();
        ulica.setPoz(ulica.getPoz());
        ulica.setValue(pracownik.getUlica());
        adres.setUlica(ulica);
        Deklaracja.Podmiot2.AdresZamieszkania.NrDomu dom = new Deklaracja.Podmiot2.AdresZamieszkania.NrDomu();
        dom.setPoz(dom.getPoz());
        dom.setValue(pracownik.getDom());
        adres.setNrDomu(dom);
        Deklaracja.Podmiot2.AdresZamieszkania.NrLokalu lokal = new Deklaracja.Podmiot2.AdresZamieszkania.NrLokalu();
        lokal.setPoz(lokal.getPoz());
        lokal.setValue(pracownik.getLokal());
        adres.setNrLokalu(lokal);
        Deklaracja.Podmiot2.AdresZamieszkania.KodPocztowy kodpocztowy = new Deklaracja.Podmiot2.AdresZamieszkania.KodPocztowy();
        kodpocztowy.setPoz(kodpocztowy.getPoz());
        kodpocztowy.setValue(pracownik.getKod());
        adres.setKodPocztowy(kodpocztowy);
        return adres;
    }

    private static Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania pobierzkraj(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania kraj = new Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania();
        kraj.setValue(TKodKraju.PL);
        kraj.setPoz(kraj.getPoz());
        return kraj;
    }

    private static Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId rodzajid(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId id = new Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId();
        if (pracownik.getPesel()==null) {
            
        }
        id.setPoz(id.getPoz());
        return id;
    }
    
     private static Deklaracja.PozycjeSzczegolowe pozycjeszczegolowe(Kartawynagrodzen kartawynagrodzen, Pracownik pracownik, Map<String, Kartawynagrodzen> sumy) {
        Deklaracja.PozycjeSzczegolowe poz = new Deklaracja.PozycjeSzczegolowe();
        if (pracownik.isNierezydent()) {
            poz.setP11((byte)2);
        } else {
            //rezydent
            poz.setP11((byte)1);
        }
        
        Kartawynagrodzen sumaUmowaoprace = sumy.get("sumaUmowaoprace");
        Kartawynagrodzen sumaUmowaopracekosztypodwyzszone = sumy.get("sumaUmowaopracekosztypodwyzszone");
        Kartawynagrodzen sumaUmowaoprace26zwolnione = sumy.get("sumaUmowaoprace26zwolnione");
        Kartawynagrodzen sumaUmowapelnieniefunkcji = sumy.get("sumaUmowapelnieniefunkcji");
        Kartawynagrodzen sumaUmowazlecenia = sumy.get("sumaUmowazlecenia");
        Kartawynagrodzen sumaUmowazlecenia26zwolnione = sumy.get("sumaUmowazlecenia26zwolnione");
        if (sumaUmowaoprace.getBrutto()>0.0) {
            poz.setP29(BigDecimal.valueOf(sumaUmowaoprace.getBrutto()));
            poz.setP30(BigDecimal.valueOf(sumaUmowaoprace.getKosztyuzyskania()));
            BigDecimal subtract = poz.getP29().subtract(poz.getP30());
            if (poz.getP31()!=null) {
                poz.setP31(poz.getP31().add(subtract));
            } else{
                poz.setP31(subtract);
            }
            poz.setP33(BigInteger.valueOf(Z.zUD(sumaUmowaoprace.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getRazemspolecznepracownik()))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getRazemspolecznepracownik())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getPraczdrowotnedopotracenia())));
            }
            //czy dodano PIT-R 1tak 2nie
            poz.setP121((byte)2);
        }
        if (sumaUmowaopracekosztypodwyzszone.getBrutto()>0.0) {
            poz.setP34(BigDecimal.valueOf(sumaUmowaopracekosztypodwyzszone.getBrutto()));
            poz.setP35(BigDecimal.valueOf(sumaUmowaopracekosztypodwyzszone.getKosztyuzyskania()));
            BigDecimal subtract = poz.getP29().subtract(poz.getP30());
            if (poz.getP31()!=null) {
                poz.setP31(poz.getP31().add(subtract));
            } else{
                poz.setP31(subtract);
            }
            poz.setP33(BigInteger.valueOf(Z.zUD(sumaUmowaopracekosztypodwyzszone.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(sumaUmowaopracekosztypodwyzszone.getRazemspolecznepracownik()))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(sumaUmowaopracekosztypodwyzszone.getRazemspolecznepracownik())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowaopracekosztypodwyzszone.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowaopracekosztypodwyzszone.getPraczdrowotnedopotracenia())));
            }
            //czy dodano PIT-R 1tak 2nie
            poz.setP121((byte)2);
        }
        if (sumaUmowapelnieniefunkcji.getBrutto()>0.0) {
            poz.setP54(BigDecimal.valueOf(sumaUmowapelnieniefunkcji.getBrutto()));
            poz.setP55(BigDecimal.valueOf(sumaUmowapelnieniefunkcji.getKosztyuzyskania()));
            poz.setP56(poz.getP54().subtract(poz.getP55()));
            poz.setP57(BigInteger.valueOf(Z.zUD(sumaUmowapelnieniefunkcji.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(sumaUmowapelnieniefunkcji.getRazemspolecznepracownik()))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(sumaUmowapelnieniefunkcji.getRazemspolecznepracownik())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowapelnieniefunkcji.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowapelnieniefunkcji.getPraczdrowotnedopotracenia())));
            }
            //czy dodano PIT-R 1tak 2nie
            poz.setP121((byte)2);
        }
        if (sumaUmowazlecenia.getBrutto()>0.0) {
            poz.setP58(BigDecimal.valueOf(sumaUmowazlecenia.getBrutto()));
            poz.setP59(BigDecimal.valueOf(sumaUmowazlecenia.getKosztyuzyskania()));
            poz.setP60(poz.getP58().subtract(poz.getP59()));
            poz.setP61(BigInteger.valueOf(Z.zUD(sumaUmowazlecenia.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia.getRazemspolecznepracownik()))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(sumaUmowazlecenia.getRazemspolecznepracownik())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowazlecenia.getPraczdrowotnedopotracenia())));
            }
            //czy dodano PIT-R 1tak 2nie
            poz.setP121((byte)2);
        }
        if (sumaUmowaoprace26zwolnione.getBrutto()>85528.0) {
            double kwotaponadlimit = sumaUmowaoprace26zwolnione.getBrutto()-85528.0;
            double zus51 = sumaUmowaoprace26zwolnione.getRazemspolecznepracownik();
            double zuszwolniony = Z.z(85528.0/sumaUmowaoprace26zwolnione.getBrutto()*zus51);
            double zusopodatkowany = Z.z(zus51-zuszwolniony);
            poz.setP28(null);
            if (poz.getP97()!=null) {
                poz.setP97(poz.getP97().add(BigDecimal.valueOf(Z.z(zuszwolniony))));
            } else{
                poz.setP97(BigDecimal.valueOf(Z.z(zuszwolniony)));
            }
            poz.setP110(BigDecimal.valueOf(Z.z(85528.0)));
            if (poz.getP109()!=null) {
                poz.setP109(poz.getP109().add(BigDecimal.valueOf(Z.z(85528.0))));
            } else{
                poz.setP109(BigDecimal.valueOf(Z.z(85528.0)));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getPraczdrowotnedopotracenia())));
            }
            poz.setP121((byte)2);
            //dodatek nadwyzke
            poz.setP29(BigDecimal.valueOf(kwotaponadlimit));
            poz.setP30(BigDecimal.valueOf(sumaUmowaoprace26zwolnione.getKosztyuzyskania()));
            BigDecimal subtract = poz.getP29().subtract(poz.getP30());
            if (poz.getP31()!=null) {
                poz.setP31(poz.getP31().add(subtract));
            } else{
                poz.setP31(subtract);
            }
            poz.setP33(BigInteger.valueOf(Z.zUD(sumaUmowaoprace26zwolnione.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(zusopodatkowany))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(zusopodatkowany)));
            }
        } else if (sumaUmowaoprace26zwolnione.getBrutto()>0.0) {
            poz.setP28(null);
            if (poz.getP97()!=null) {
                poz.setP97(poz.getP97().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getRazemspolecznepracownik()))));
            } else{
                poz.setP97(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getRazemspolecznepracownik())));
            }
            poz.setP110(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getBrutto())));
            if (poz.getP109()!=null) {
                poz.setP109(poz.getP109().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getBrutto()))));
            } else{
                poz.setP109(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getBrutto())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowaoprace26zwolnione.getPraczdrowotnedopotracenia())));
            }
            poz.setP121((byte)2);
        }
        if (sumaUmowazlecenia26zwolnione.getBrutto()>85528.0) {
            double kwotaponadlimit = sumaUmowazlecenia26zwolnione.getBrutto()-85528.0;
            double zus51 = sumaUmowazlecenia26zwolnione.getRazemspolecznepracownik();
            double zuszwolniony = Z.z(85528.0/sumaUmowazlecenia26zwolnione.getBrutto()*zus51);
            double zusopodatkowany = Z.z(zus51-zuszwolniony);
            poz.setP28(null);
            if (poz.getP97()!=null) {
                poz.setP97(poz.getP97().add(BigDecimal.valueOf(Z.z(zuszwolniony))));
            } else{
                poz.setP97(BigDecimal.valueOf(Z.z(zuszwolniony)));
            }
            poz.setP111(BigDecimal.valueOf(Z.z(85528.0)));
            if (poz.getP109()!=null) {
                poz.setP109(poz.getP109().add(BigDecimal.valueOf(Z.z(85528.0))));
            } else{
                poz.setP109(BigDecimal.valueOf(Z.z(85528.0)));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getPraczdrowotnedopotracenia())));
            }
            poz.setP121((byte)2);
            poz.setP58(BigDecimal.valueOf(kwotaponadlimit));
            poz.setP59(BigDecimal.valueOf(sumaUmowazlecenia26zwolnione.getKosztyuzyskania()));
            poz.setP60(poz.getP58().subtract(poz.getP59()));
            poz.setP61(BigInteger.valueOf(Z.zUD(sumaUmowazlecenia26zwolnione.getPodatekdochodowy())));
            if (poz.getP95()!=null) {
                poz.setP95(poz.getP95().add(BigDecimal.valueOf(Z.z(zusopodatkowany))));
            } else{
                poz.setP95(BigDecimal.valueOf(Z.z(zusopodatkowany)));
            }
            //czy dodano PIT-R 1tak 2nie
            poz.setP121((byte)2);
        } else  if (sumaUmowazlecenia26zwolnione.getBrutto()>0.0) {
            poz.setP28(null);
            if (poz.getP97()!=null) {
                poz.setP97(poz.getP97().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getRazemspolecznepracownik()))));
            } else{
                poz.setP97(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getRazemspolecznepracownik())));
            }
            poz.setP111(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getBrutto())));
            if (poz.getP109()!=null) {
                poz.setP109(poz.getP109().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getBrutto()))));
            } else{
                poz.setP109(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getBrutto())));
            }
            if (poz.getP122()!=null) {
                poz.setP122(poz.getP122().add(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getPraczdrowotnedopotracenia()))));
            } else{
                poz.setP122(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26zwolnione.getPraczdrowotnedopotracenia())));
            }
            poz.setP121((byte)2);
        }
        if (poz.getP30()!=null||poz.getP35()!=null||poz.getP37()!=null||poz.getP42()!=null) {
            //koszty uzyskania
            poz.setP28((byte)1);
            if (kartawynagrodzen.isKosztypodwyzszone()) {
                poz.setP28((byte)2);
            } else if (kartawynagrodzen.isKosztywieleumow()) {
                poz.setP28((byte)3);
            } else if (kartawynagrodzen.isKosztypodwyzszone()&&kartawynagrodzen.isKosztywieleumow()) {
                poz.setP28((byte)4);
            }
        }
        return poz;
    }

}
