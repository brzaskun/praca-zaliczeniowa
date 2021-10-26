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
import pl.gov.crd.wzor._2021._03._04._10477.Deklaracja;
import pl.gov.crd.wzor._2021._03._04._10477.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.wzor._2021._03._04._10477.TKodFormularza;
import pl.gov.crd.wzor._2021._03._04._10477.TNaglowek;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._07._06.ed.definicjetypy.TIdentyfikatorOsobyFizycznej1;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._07._06.ed.definicjetypy.TKodKraju;
import z.Z;

/**
 *
 * @author Osito
 */
public class PIT11_27Bean {
     public static String[] generujXML(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok, Map<String,Kartawynagrodzen> sumy) {
        String[] zwrot = new String[2];
        pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja = genPIT1127(kartawynagrodzen, firma, pracownik, normalna1korekta2, kodurzedu, rok, sumy);
        String sciezka = null;
        try {
            sciezka = marszajuldoplikuxml(deklaracja);
            zwrot[0] = sciezka;
            zwrot[1] = "ok";
            String mainfilename = "pit11_27"+deklaracja.getPodmiot2().getOsobaFizyczna().getPESEL()+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
                Object[] walidacja = xml.XMLValid_PIT11_27.walidujPIT1127(mainfilename);
                if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                    zwrot[0] = sciezka;
                    zwrot[1] = "ok";
                    Msg.msg("Walidacja JPK pomyślna");
                } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                    zwrot[0] = sciezka;
                    zwrot[1] = null;
                    Msg.msg("e", (String) walidacja[1]);
                }
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return zwrot;
    }
    
    public static String marszajuldoplikuxml(pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja) {
        String sciezka = null;
        try {
            if (deklaracja!=null) {
                JAXBContext context = JAXBContext.newInstance(deklaracja.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                String mainfilename = "pit11_27"+deklaracja.getPodmiot2().getOsobaFizyczna().getPESEL()+"rok"+deklaracja.getNaglowek().getRok()+deklaracja.getNaglowek().getKodFormularza().getWersjaSchemy()+".xml";
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
    
     private static Deklaracja genPIT1127(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok, Map<String,Kartawynagrodzen> sumy) {
        pl.gov.crd.wzor._2021._03._04._10477.ObjectFactory of = new pl.gov.crd.wzor._2021._03._04._10477.ObjectFactory();
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

    private static TNaglowek naglowek(byte normalna1korekta2, String kodurzedu, String rok) {
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
        naglowek.setRok(Data.XMLGCinitRok("2021"));
//        naglowek.setRok(Data.XMLGCinitRok(rok));
        naglowek.setWariantFormularza((byte)27);
        return naglowek;
    }

    private static Deklaracja.Podmiot1 pracodawca(FirmaKadry firma) {
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
        TIdentyfikatorOsobyFizycznej1 tIdentyfikatorOsobyFizycznej1 = new TIdentyfikatorOsobyFizycznej1();
        tIdentyfikatorOsobyFizycznej1.setNazwisko(firma.getNazwisko());
        tIdentyfikatorOsobyFizycznej1.setImiePierwsze(firma.getImie());
        tIdentyfikatorOsobyFizycznej1.setNIP(firma.getNip());
        tIdentyfikatorOsobyFizycznej1.setDataUrodzenia(Data.dataoddo(firma.getDataurodzenia()));
        return tIdentyfikatorOsobyFizycznej1;
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
        kraj.setValue(TKodKraju.PL);
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
        //koszty uzyskania
        poz.setP28((byte)1);
        if (kartawynagrodzen.isKosztypodwyzszone()) {
            poz.setP28((byte)2);
        } else if (kartawynagrodzen.isKosztywieleumow()) {
            poz.setP28((byte)3);
        } else if (kartawynagrodzen.isKosztypodwyzszone()&&kartawynagrodzen.isKosztywieleumow()) {
            poz.setP28((byte)4);
        }
        Kartawynagrodzen sumaUmowaoprace = sumy.get("sumaUmowaoprace");
        Kartawynagrodzen sumaUmowaoprace26 = sumy.get("sumaUmowaoprace26");
        Kartawynagrodzen sumaUmowazlecenia26 = sumy.get("sumaUmowazlecenia26");
        if (sumaUmowaoprace.getBrutto()>0.0) {
            poz.setP29(BigDecimal.valueOf(sumaUmowaoprace.getBrutto()));
            poz.setP30(BigDecimal.valueOf(sumaUmowaoprace.getKosztyuzyskania()));
            poz.setP31(poz.getP29().subtract(poz.getP30()));
            poz.setP33(BigInteger.valueOf(Z.zUD(sumaUmowaoprace.getPodatekdochodowy())));
            poz.setP75(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getRazemspolecznepracownik())));
            poz.setP78(BigDecimal.valueOf(Z.z(sumaUmowaoprace.getPraczdrowotnedopotracenia())));
            //czy dodano PIT-R 1tak 2nie
            poz.setP96((byte)2);
        }
        if (sumaUmowaoprace26.getBrutto()>0.0) {
            poz.setP28(null);
            poz.setP75(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getRazemspolecznepracownik())));
            poz.setP78(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getPraczdrowotnedopotracenia())));
            poz.setP93(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getBrutto())));
            poz.setP92(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getBrutto())));
            poz.setP96((byte)2);
        }
        if (sumaUmowazlecenia26.getBrutto()>0.0) {
            poz.setP28(null);
            poz.setP75(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26.getRazemspolecznepracownik())));
            poz.setP78(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26.getPraczdrowotnedopotracenia())));
            poz.setP94(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26.getBrutto())));
            poz.setP92(BigDecimal.valueOf(Z.z(sumaUmowazlecenia26.getBrutto())));
            poz.setP96((byte)2);
        }
        return poz;
    }

}
