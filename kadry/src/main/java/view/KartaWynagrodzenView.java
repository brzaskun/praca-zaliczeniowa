/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kartawynagrodzencomparator;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfKartaWynagrodzen;
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
@Named
@ViewScoped
public class KartaWynagrodzenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KartaWynagrodzenFacade kartaWynagrodzenFacade;
    @Inject
    private WpisView wpisView;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    private Kartawynagrodzen sumaUmowaoprace;
    private Kartawynagrodzen sumaUmowaopracekosztypodwyzszone;
    private Kartawynagrodzen sumaUmowaoprace26;
    private Kartawynagrodzen sumaUmowaopracekosztypodwyzszone26;
    private Kartawynagrodzen sumaUmowapelnieniefunkcji;
    private Kartawynagrodzen sumaUmowazlecenia;
    private Kartawynagrodzen sumaUmowazlecenia26;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;

    
    
    @PostConstruct
    public void init() {
        pobierzdane();
    }

        
       
    public void pobierzdane() {
        if (wpisView.getAngaz()!=null) {
            kartawynagrodzenlist = pobierzkartywynagrodzen(wpisView.getAngaz(), wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), wpisView.getAngaz());
            Msg.msg("Pobrano dane wynagrodzeń");
        }
    }
    
    private List<Kartawynagrodzen> pobierzkartywynagrodzen(Angaz selectedangaz, String rok) {
        List<Kartawynagrodzen> kartypobranezbazy = selectedangaz.getKartawynagrodzenList();
        Collections.sort(kartypobranezbazy, new Kartawynagrodzencomparator());
        if (kartypobranezbazy==null || kartypobranezbazy.isEmpty()) {
            kartypobranezbazy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                Kartawynagrodzen nowa = new Kartawynagrodzen();
                nowa.setAngaz(selectedangaz);
                nowa.setRok(rok);
                nowa.setMc(mc);
                kartypobranezbazy.add(nowa);
            }
            kartaWynagrodzenFacade. createEditList(kartypobranezbazy);
        } else {
            for (Iterator<Kartawynagrodzen> it = kartypobranezbazy.iterator();it.hasNext();) {
                Kartawynagrodzen k = it.next();
                if (k.getId()==null) {
                    it.remove();
                }
            }
        }
        return kartypobranezbazy;
    }
    
    private void aktualizujdane(List<Kartawynagrodzen> kartawynagrodzenlist, String rok, Angaz angaz) {
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(rok, angaz);
        if (paski!=null && !paski.isEmpty()) {
            sumuj(kartawynagrodzenlist, paski);
        }
    }

    private void sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski) {
        sumaUmowaoprace = new Kartawynagrodzen();
        sumaUmowaoprace26 = new Kartawynagrodzen();
        sumaUmowaopracekosztypodwyzszone = new Kartawynagrodzen();
        sumaUmowaopracekosztypodwyzszone26 = new Kartawynagrodzen();
        sumaUmowapelnieniefunkcji  = new Kartawynagrodzen();
        sumaUmowazlecenia  = new Kartawynagrodzen();
        sumaUmowazlecenia26  = new Kartawynagrodzen();
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setMc("razem");
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            if (karta.getId()!=null) {
                karta.zeruj();
            }
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                if (pasek.getMc().equals(karta.getMc())) {
                    //tu sie dodaje paski do karty wynagrodzen
                    if (angazzpaskow.contains(pasek.getKalendarzmiesiac().getUmowa().getAngaz())) {
                        karta.setKosztywieleumow(true);
                        suma.setKosztywieleumow(true);
                    } else {
                        angazzpaskow.add(pasek.getKalendarzmiesiac().getUmowa().getAngaz());
                    }
                    if (pasek.getProcentkosztow()>100.0) {
                        karta.setKosztypodwyzszone(true);
                        suma.setKosztypodwyzszone(true);
                    }
//                    id,nazwa,typ
//                    1,"umowa o pracę",1
//                    2,"umowa zlecenia i o dzieło",2
//                    3,"pełnienie obowiązków",3
//                    4,zasiłki,4

                    if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==false) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone.dodaj(pasek);
                        } else {
                            sumaUmowaoprace.dodaj(pasek);
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==true) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone26.dodaj(pasek);
                        } else {
                            sumaUmowaoprace26.dodaj(pasek);
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==3&&pasek.isDo26lat()==false) {
                        sumaUmowapelnieniefunkcji.dodaj(pasek);
                    }
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        kartaWynagrodzenFacade.editList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
    }
    
    public void drukuj() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getAngaz(), wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodzeń");
        }
    }


    public void pit11() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            Kartawynagrodzen kartawynagrodzen = kartawynagrodzenlist.get(0);
            FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
            Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
            String[] sciezka = generujXML(kartawynagrodzen, firma, pracownik, (byte)1, "3220", kartawynagrodzen.getRok());
            String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wydrukowano PIT-11");
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Brak karty wynagrodzeń");
        }
    }
    
    public String[] generujXML(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok) {
        String[] zwrot = new String[2];
        pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja = genPIT1127(kartawynagrodzen, firma, pracownik, normalna1korekta2, kodurzedu, rok);
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
    
    private String marszajuldoplikuxml(pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja) {
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
    
     private Deklaracja genPIT1127(Kartawynagrodzen kartawynagrodzen, FirmaKadry firma, Pracownik pracownik, byte normalna1korekta2, String kodurzedu, String rok) {
        pl.gov.crd.wzor._2021._03._04._10477.ObjectFactory of = new pl.gov.crd.wzor._2021._03._04._10477.ObjectFactory();
        Deklaracja deklaracja = of.createDeklaracja();
        deklaracja.setNaglowek(of.createTNaglowek());
        deklaracja.setNaglowek(naglowek(normalna1korekta2, kodurzedu, rok));
        deklaracja.setPodmiot1(pracodawca(firma));
        deklaracja.setPodmiot2(pracownik(pracownik));
        deklaracja.setPozycjeSzczegolowe(pozycjeszczegolowe(kartawynagrodzen, pracownik));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return deklaracja;
    }

    
    public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }

    private TNaglowek.CelZlozenia celzlozenia(byte normalna1korekta2) {
        TNaglowek.CelZlozenia zwrot = new TNaglowek.CelZlozenia();
        zwrot.setValue(normalna1korekta2);
        zwrot.setPoz(zwrot.getPoz());
        return zwrot;
    }

    private TNaglowek naglowek(byte normalna1korekta2, String kodurzedu, String rok) {
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

    private Deklaracja.Podmiot1 pracodawca(FirmaKadry firma) {
        Deklaracja.Podmiot1 pracodawca = new Deklaracja.Podmiot1();
        pracodawca.setRola(pracodawca.getRola());
        if (firma.isOsobafizyczna()) {
            pracodawca.setOsobaFizyczna(osobafizyczna(firma));
        } else {
            pracodawca.setOsobaNiefizyczna(osobaniefizyczna(firma));
        }
        return pracodawca;
    }

    private TIdentyfikatorOsobyFizycznej1 osobafizyczna(FirmaKadry firma) {
        TIdentyfikatorOsobyFizycznej1 tIdentyfikatorOsobyFizycznej1 = new TIdentyfikatorOsobyFizycznej1();
        tIdentyfikatorOsobyFizycznej1.setNazwisko(firma.getNazwisko());
        tIdentyfikatorOsobyFizycznej1.setImiePierwsze(firma.getImie());
        tIdentyfikatorOsobyFizycznej1.setNIP(firma.getNip());
        tIdentyfikatorOsobyFizycznej1.setDataUrodzenia(Data.dataoddo(firma.getDataurodzenia()));
        return tIdentyfikatorOsobyFizycznej1;
    }

    private TIdentyfikatorOsobyNiefizycznej osobaniefizyczna(FirmaKadry firma) {
        TIdentyfikatorOsobyNiefizycznej tIdentyfikatorOsobyNiefizycznej = new TIdentyfikatorOsobyNiefizycznej();
        tIdentyfikatorOsobyNiefizycznej.setPelnaNazwa(firma.getNazwa());
        tIdentyfikatorOsobyNiefizycznej.setNIP(firma.getNip());
        return tIdentyfikatorOsobyNiefizycznej;
    }

    private Deklaracja.Podmiot2 pracownik(Pracownik pracownik) {
        Deklaracja.Podmiot2 podmiot2 = new Deklaracja.Podmiot2();
        podmiot2.setRola(podmiot2.getRola());
        podmiot2.setOsobaFizyczna(pracownikdane(pracownik));
        podmiot2.setAdresZamieszkania(adres(pracownik));
        return podmiot2;
    }

    private Deklaracja.Podmiot2.OsobaFizyczna pracownikdane(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna osobafizyczna = new Deklaracja.Podmiot2.OsobaFizyczna();
        osobafizyczna.setNazwisko(pracownik.getNazwisko());
        osobafizyczna.setImiePierwsze(pracownik.getImie());
        osobafizyczna.setDataUrodzenia(Data.dataoddo(pracownik.getDataurodzenia()));
        osobafizyczna.setPESEL(pracownik.getPesel());
//        osobafizyczna.setKodKrajuWydania(pobierzkraj(pracownik));
//        osobafizyczna.setRodzajNrId(rodzajid(pracownik));
        return osobafizyczna;
    }

    private Deklaracja.Podmiot2.AdresZamieszkania adres(Pracownik pracownik) {
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

    private Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania pobierzkraj(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania kraj = new Deklaracja.Podmiot2.OsobaFizyczna.KodKrajuWydania();
        kraj.setValue(TKodKraju.PL);
        kraj.setPoz(kraj.getPoz());
        return kraj;
    }

    private Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId rodzajid(Pracownik pracownik) {
        Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId id = new Deklaracja.Podmiot2.OsobaFizyczna.RodzajNrId();
        if (pracownik.getPesel()==null) {
            
        }
        id.setPoz(id.getPoz());
        return id;
    }

    private Deklaracja.PozycjeSzczegolowe pozycjeszczegolowe(Kartawynagrodzen kartawynagrodzen, Pracownik pracownik) {
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
            poz.setP75(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getRazemspolecznepracownik())));
            poz.setP78(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getPraczdrowotnedopotracenia())));
            poz.setP93(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getBrutto())));
            poz.setP92(BigDecimal.valueOf(Z.z(sumaUmowaoprace26.getBrutto())));
        }
        return poz;
    }

   
   

   
    
    
}
