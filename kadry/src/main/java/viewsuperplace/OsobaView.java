/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import DAOsuperplace.OsobaFacade;
import beanstesty.NieobecnosciBean;
import dao.AngazFacade;
import dao.DefinicjalistaplacFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.RodzajlistyplacFacade;
import dao.RodzajnieobecnosciFacade;
import dao.RodzajpotraceniaFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikPotraceniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.SlownikszkolazatrhistoriaFacade;
import dao.SlownikwypowiedzenieumowyFacade;
import dao.StanowiskopracFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaPotraceniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajnieobecnosci;
import entity.Rodzajpotracenia;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Swiadczeniekodzus;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaDet;
import kadryiplace.OsobaPot;
import kadryiplace.OsobaPropTyp;
import kadryiplace.OsobaSkl;
import kadryiplace.OsobaZlec;
import kadryiplace.Rok;
import msg.Msg;
import pdf.PdfHistoriaImp;
import view.HistoriaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OsobaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private OsobaFacade osobaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private HistoriaView historiaView;
    @Inject
    private SlownikszkolazatrhistoriaFacade slownikszkolazatrhistoriaFacade;
    @Inject
    private SlownikwypowiedzenieumowyFacade slownikwypowiedzenieumowyFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    @Inject
    private EtatPracFacade etatpracFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private RodzajpotraceniaFacade rodzajpotraceniaFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private SwiadczeniekodzusFacade swiadczeniekodzusFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade; 
    @Inject
    private ZmiennaPotraceniaFacade zmiennaPotraceniaFacade;

    private String serial;
    
    public void robgrupa(List<Osoba> wybraneosoby) {
        //OutputStream out = null;
        try {
            List<String> log = new ArrayList<>();
            log.add("Rozpoczęto import pracowników firmy "+wpisView.getFirma());
            if (wybraneosoby!=null) {
                List<Osoba> osoby = historiaView.getOsoby();
                for (Osoba osoba : wybraneosoby) {
                    serial = String.valueOf(osoba.getOsoSerial());
                    rob(log, String.valueOf(osoba.getOsoSerial()) ,osoby);
                }
                wybraneosoby = null;
                Msg.msg("Pobrano grupę osób");
            } else {
                log.add("BŁĄD. Brak wybranych pracowników. Przerwano import pracowników firmy "+wpisView.getFirma());
                Msg.msg("e","Nie wybrano osoób");
            }   log.add("Zakończono import pracowników firmy "+wpisView.getFirma());
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = facesContext.getExternalContext();
//            externalContext.setResponseContentType("text/plain");
//            String filename = "raport"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisu()+".txt";
//            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//            // Write file to response body.
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            StringBuilder sb = new StringBuilder();
//            for (String s : log) {
//                sb.append(s);
//                sb.append("\n");
//            }
//            byte[] array = sb.toString().getBytes();
//            // Writes data to the output stream
//            baos.write(array);
//            out = externalContext.getResponseOutputStream();
//            baos.writeTo(out);
//            // Inform JSF that response is completed and it thus doesn't have to navigate.
//            facesContext.responseComplete();
              PdfHistoriaImp.drukuj(log, wpisView.getFirma().getNazwa(), wpisView.getFirma().getNip());
        } catch (Exception ex) {
            E.e(ex);
        }
//        } finally {
//            try {
//                out.close();
//            } catch (IOException ex) {
//                E.e(ex);
//            }
//        }
        
    }

    public void rob(List<String> log, String serial, List<Osoba> osoby) {
        //List<Osoba> podatnicy = osobaFacade.findAll();
        //Osoba osoba = osobaFacade.findByPesel("83020610048");
//        Osoba osoba = osobaFacade.findBySerial("1609");
        if (serial != null) {
            Osoba osobatmp = osobajuzpobrana(serial, osoby);
            if (osobatmp != null) {
                Msg.msg("e","Ten pracownik został już pobrany");
            } else {
                boolean moznadalej = false;
                Osoba osoba = osobaFacade.findBySerial(serial);
                Pracownik pracownik = pracownikFacade.findByPesel(osoba.getOsoPesel());
                if (pracownik == null) {
                    try {
                        pracownik = OsobaBean.pobierzOsobaBasic(osoba);
                        pracownikFacade.create(pracownik);
                        moznadalej = true;
                        log.add("Utworzono pracownika "+pracownik.getNazwiskoImie());
                        Msg.msg("Dodano nowego pracownika");
                    } catch (Exception e) {
                        log.add("BŁĄD. Nie utworzono pracownika "+osoba.getOsoNazwisko()+" "+osoba.getOsoImie1());
                        log.add(E.e(e));
                    }
                } else if (pracownik!=null) {
                    log.add("Pobrano wcześniej utworzonego pracownika "+pracownik.getNazwiskoImie());
                    moznadalej = true;
                }
                if (moznadalej) {
                    wpisView.setPracownik(pracownik);
                    String datakonca26lat = OsobaBean.obliczdata26(pracownik.getDataurodzenia());
                    FirmaKadry firma = wpisView.getFirma();
                    Angaz angaz = angazFacade.findByPeselFirma(pracownik.getPesel(), firma);
                    if (angaz ==null) {
                        angaz = OsobaBean.nowyangaz(pracownik, firma);
                        angaz.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                        angaz.setDatadodania(new Date());
                        angaz.setSerialsp(serial);
                        angaz.setRok(wpisView.getRokWpisu());
                        angaz.setMc(wpisView.getMiesiacWpisu());
                        if (osoba.getOsoDataZatr()!=null) {
                            String zatrudnienie = Data.data_yyyyMMdd(osoba.getOsoDataZatr());
                            angaz.setRok(Data.getRok(zatrudnienie));
                            angaz.setMc(Data.getMc(zatrudnienie));
                        }
                        angazFacade.create(angaz);
                        log.add("Stworzono angaż dla "+pracownik.getNazwiskoImie());
                        Msg.msg("Stworzono angaż");
                    }
                    wpisView.setAngaz(angaz);
                    List<Slownikszkolazatrhistoria> rodzajezatr = slownikszkolazatrhistoriaFacade.findAll();
                    List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia = slownikwypowiedzenieumowyFacade.findAll();
                    //tu pobieramy umowy o pracę
                    Umowakodzus umowakodzuspraca = null;
                    Umowakodzus umowakodzuszlecenie = null;
                    List<OsobaDet> osobaDetList = osoba.getOsobaDetList();
                    if (!osobaDetList.isEmpty()) {
                        for (OsobaDet p : osobaDetList) {
                            OsobaPropTyp osdOptSerial = p.getOsdOptSerial();
                            Integer wktSerial = p.getOsdWktSerial().getWktSerial();
                            if (p.getOsdWktSerial().getWktUmZlec().equals('N')) {
                                umowakodzuspraca = umowakodzusFacade.findUmowakodzusByWktSerial(wktSerial);
                                log.add("Pobrałem kod umowy o prace");
                            }
                            if (p.getOsdWktSerial().getWktUmZlec().equals('T')) {
                               umowakodzuszlecenie = umowakodzusFacade.findUmowakodzusByWktSerial(wktSerial);
                               log.add("Pobrałem kod umowy zlecenia");
                            }
                        }
                    }
                    if (umowakodzuspraca==null&&umowakodzuszlecenie==null) {
                        Msg.msg("e","Brak takiego kodu ubezpieczenia w nowym programie!");
                        Msg.msg("e","Przerywam import");
                        log.add("BŁĄD. Brak odpowiednika kody z SuperPlac. Przerywam import");
                    } else{
                        Umowa aktywna = null;
                        if (umowakodzuspraca!=null) {
                            List<Umowa> umowyoprace = OsobaBean.pobierzumowy(osoba, angaz, rodzajezatr, rodzajewypowiedzenia, umowakodzuspraca);
                            umowaFacade.createList(umowyoprace);
                            log.add("Udane zachowanie umowę o pracę");
                            Msg.msg("Zachowano umowy");
                            aktywna = umowyoprace.stream().filter(p -> p.isAktywna()).findFirst().get();
                            wpisView.setUmowa(aktywna);
                            List<Stanowiskoprac> stanowiska = OsobaBean.pobierzstanowiska(osoba, aktywna);
                            stanowiskopracFacade.createList(stanowiska);
                            log.add("Udane zachowanie stanowiska");
                            Short formawynagrodzenia = osoba.getOsoWynForma();
                            List<EtatPrac> etaty = OsobaBean.pobierzetaty(osoba, aktywna);
                            etatpracFacade.createList(etaty);
                            log.add("Udane zachowanie etatu");
                            List<OsobaSkl> skladniki = osoba.getOsobaSklList();
                            List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                            Msg.msg("Uzupełniono zmienne dotyczące wynagrodzeń");
                            String rokdlakalendarza = "2020";
                            //paski rok 2020 umowa o pracę
                            List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                            kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                            log.add("Udane zachowanie wygenerowanych kalendarzy za 2020");
                            List<Rodzajnieobecnosci> rodzajnieobscnoscilist = rodzajnieobecnosciFacade.findAll();
                            List<Swiadczeniekodzus> swiadczeniekodzuslist = swiadczeniekodzusFacade.findAll();
                            List<Nieobecnosc> nieobecnosci = OsobaBean.pobierznieobecnosci(osoba, aktywna, rodzajnieobscnoscilist, swiadczeniekodzuslist);
                            for (Nieobecnosc p : nieobecnosci) {
                                p.setImportowana(true);
                                p.setRokod(Data.getRok(p.getDataod()));
                                p.setRokdo(Data.getRok(p.getDatado()));
                                p.setMcod(Data.getMc(p.getDataod()));
                                p.setMcdo(Data.getMc(p.getDatado()));
                            }
                            nieobecnoscFacade.createList(nieobecnosci);
                            log.add("Udane zachowanie nieobecnosci");
                            List<OsobaPot> osobaPotList = osoba.getOsobaPotList();
                            List<Rodzajpotracenia> rodzajepotracen = rodzajpotraceniaFacade.findAll();
                            List<Skladnikpotracenia> skladnikpotracenia = OsobaBean.pobierzskladnipotracenia(osobaPotList, rodzajepotracen, aktywna, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                            List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                            Rok rok = pobierzrok(rokdlakalendarza, rokList);
                            List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                            List<Skladnikwynagrodzenia> skladnikwynagrodzenia = OsobaBean.pobierzskladnikwynagrodzenia(skladniki, rodzajewynagrodzenia, aktywna, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                            log.add("Udane zachowanie składników wynagrodzenia i zmiennych umowa o pracę");
                            List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                            if (paskiumowaoprace.size()>0) {
                                List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                                List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                pasekwynagrodzenFacade.createList(paskigotowe);
                                log.add("Udane zachowanie paskow wynagrodzen za 2020");
                                Msg.msg("Zrobiono kalendarz i paski za 2020 umowa o pracę");
                            }
                            //koniec paski 2020 umowa o pracę
                            rokdlakalendarza = "2021";
                            //paski rok 2021 umowa o pracę
                            generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                            kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                            log.add("Udane zachowanie wygenerowanych kalendarzy za 2021");
                            rok = pobierzrok(rokdlakalendarza, rokList);
                            okresList = pobierzokresySuperplace(1, rok.getOkresList());
                            paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat, skladnikwynagrodzenia, nieobecnosci, skladnikpotracenia);
                            if (paskiumowaoprace.size()>0) {
                                List<Definicjalistaplac> listyaktywne = OsobaBean.generujlistyplac(paskiumowaoprace, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                                List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyaktywne, kalendarze);
                                //paskigotowe = OsobaBean.laczduplikatyumowaoprace(paskigotowe);
                                pasekwynagrodzenFacade.createList(paskigotowe);
                                log.add("Udane zachowanie paskow wynagrodzen za 2021");
                                Msg.msg("Zrobiono kalendarz i paski za 2021 umowa o pracę");
                            }
                            for (Nieobecnosc p : nieobecnosci) {
                                NieobecnosciBean.nanies(p, aktywna, nieobecnoscFacade, kalendarzmiesiacFacade);
                            }
                            log.add("Naniesiono nieobecnosci na kalendarz");
                            rokdlakalendarza = "2022";
                            //paski rok 2022 umowa o pracę
                            generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                            kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                            log.add("Udane zachowanie wygenerowanych kalendarzy za 2022");
                            //koniec paski 2021 umowa o pracę
                        }
                        //pobieranie umow zlecenia
                        if (umowakodzuszlecenie!=null) {
                            try {
                                List<OsobaZlec> listaumow = osoba.getOsobaZlecList();
                                List<Umowa> umowyzlecenia = OsobaBean.pobierzumowyzlecenia(listaumow, angaz, umowakodzuszlecenie);
                                umowaFacade.createList(umowyzlecenia);
                                log.add("Udane zachowanie umowy zlecenia");
                                aktywna = umowyzlecenia.stream().filter(p -> p.isAktywna()).findFirst().get();
                                wpisView.setUmowa(aktywna);
                                Msg.msg("Pobrano umowy zlecenia");
                                String rokdlakalendarza = "2020";
                                //paski rok 2020 umowa zlecenia
                                List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                                kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2020 umowa zlecenia");
                                List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                                Rok rok = pobierzrok(rokdlakalendarza, rokList);
                                List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                List<Rachunekdoumowyzlecenia> rachunki = OsobaBean.zrobrachunkidozlecenia(wpisView, osoba);
                                rachunekdoumowyzleceniaFacade.createList(rachunki);
                                log.add("Udane zachowanie rachunków do umowy zlecenia");
                                Msg.msg("Zrobiono rachunki do zleceń");
                                List<OsobaZlec> skladniki = osoba.getOsobaZlecList();
                                List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                                Rodzajwynagrodzenia rodzajwynagrodzeniaNZ = rodzajwynagrodzeniaFacade.findNZ();
                                List<Skladnikwynagrodzenia> skladnikwynagrodzenia = OsobaBean.pobierzskladnikzlecenie(skladniki, rodzajewynagrodzenia, umowyzlecenia, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                                if (skladnikwynagrodzenia!=null&&skladnikwynagrodzenia.size()==1&&pracownik.isNierezydent()) {
                                    Skladnikwynagrodzenia sklw = skladnikwynagrodzenia.get(0);
                                    sklw.setRodzajwynagrodzenia(rodzajwynagrodzeniaNZ);
                                    skladnikWynagrodzeniaFacade.edit(sklw);
                                    log.add("Udane zachowanie składników wymagrodzenia");
                                    for (Zmiennawynagrodzenia z : sklw.getZmiennawynagrodzeniaList()) {
                                        z.setSkladnikwynagrodzenia(sklw);
                                        zmiennaWynagrodzeniaFacade.edit(z);
                                        log.add("Udane zachowanie zmiennych wynagrodzenia");
                                    }
                                }
                                List<OsobaPot> osobaPotList = osoba.getOsobaPotList();
                                List<Rodzajpotracenia> rodzajepotracen = rodzajpotraceniaFacade.findAll();
                                List<Skladnikpotracenia> skladnikpotracenia = OsobaBean.pobierzskladnipotracenia(osobaPotList, rodzajepotracen, aktywna, skladnikPotraceniaFacade, zmiennaPotraceniaFacade);
                                List<Pasekwynagrodzen> paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat, skladnikwynagrodzenia, null, skladnikpotracenia);
                                if (paskiumowazlecenia.size()>0) {
                                    List<Definicjalistaplac> listyumowazlecenia = OsobaBean.generujlistyplac(paskiumowazlecenia, wpisView.getFirma(), definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikaty(paskigotowe);
                                    //bo definicje listyplac moga juz istniec
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie pasków za 2020 do umowy zlecenia");
                                    Msg.msg("Zrobiono kalendarz i paski za 2020 umowa zlecenia");
                                }
                                //koniec paski 2020 umowa zlecenia
                                rokdlakalendarza = "2021";
                                //paski rok 2021 umowa zlecenia
                                generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                                kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa); 
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2021 umowa zlecenia");
                               rok = pobierzrok(rokdlakalendarza, rokList);
                                okresList = pobierzokresySuperplace(1, rok.getOkresList());
                                paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat, skladnikwynagrodzenia, null, skladnikpotracenia);
                                if (paskiumowazlecenia.size()>0) {
                                    List<Definicjalistaplac> listyumowazlecenia = OsobaBean.generujlistyplac(paskiumowazlecenia, wpisView.getFirma(),definicjalistaplacFacade, rodzajlistyplacFacade, rokdlakalendarza);
                                    List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                                    List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                                    //paskigotowe = OsobaBean.laczduplikaty(paskigotowe);
                                    pasekwynagrodzenFacade.createList(paskigotowe);
                                    log.add("Udane zachowanie pasków za 2021 do umowy zlecenia");
                                    Msg.msg("Zrobiono kalendarz i paski za 2021 umowa zlecenia");
                                }
                                rokdlakalendarza = "2022";
                                //kalendarz rok 2022 umowa o zlecenie
                                generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                                kalendarzmiesiacFacade.editList(generujKalendarzNowaUmowa);
                                log.add("Udane zachowanie wygenerowanych kalendarzy za 2022");
                                //koniec paski 2021 umowa zlecenia
                            } catch (Exception e) {
                                E.e(e);
                                log.add("BŁĄD. Przerywam import umowa zlecenia");
                                log.add(E.e(e));
                                System.out.println("");
                            }
                            log.add("Zakończono import pracownika");
                        }

                        Msg.msg("Przeniesiono nieobecności");
                        //        //ubezpieczenia u danej osoby
                        //        List<OsobaDet> osobaDet = osoba.getOsobaDetList();
                        //        for (OsobaDet r : osobaDet) {
                        //            try {
                        //                //typ P - tenpracodawca, I inny prac, E-edukacja
                        //                String ubezp = r.getOsdEmerProc()+" "+r.getOsdChorProc();
                        //                System.out.println(ubezp);
                        //            } catch (Exception e){}
                        //        }
                        //         List<PlacePrz> placeprz = osoba.getPlacePrzList();
                        //         for (PlacePrz r : placeprz) {
                        //            try {
                        //                String przerwa = Data.data_yyyyMMdd(r.getPrzDataOd())+" "+Data.data_yyyyMMdd(r.getPrzDataDo())+" "+r.getPrzAbsencja()+" "+r.getPrzWyp()+" "+r.getPrzChorWyp();
                        //                System.out.println(przerwa);
                        //            } catch (Exception e){}
                        //        }
                        Msg.msg("Pracownik pobrany");
                    }
                   
                    System.out.println("funkcja sprawdzanie sob ");
                    List<Angaz> listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
                    if (osoby!=null) {
                        for (Osoba o : osoby) {
                            for (Angaz a : listapracownikow) {
                                if (a.getSerialsp()!=null) {
                                    if (o.getOsoSerial().equals(Integer.parseInt(a.getSerialsp()))) {
                                        o.setOsoDodVchar3("tak");
                                        System.out.println("osoba "+a.getSerialsp());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("funkcja sprawdzanie sob koniec");
                    System.out.println("koniec");
                }
            }
        } else {
            Msg.msg("e", "Brak numeru serial");
            System.out.println("koniec rob() brak serialu");
        }
    }
    
    public static void main(String[] args) {
        try {
            try (PrintWriter out = new PrintWriter("kolega")) {
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OsobaView.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

//    public static void main(String[] args) {
//        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
//        EntityManager emH2 = emfH2.createEntityManager();
//        List<Osoba> podatnicy = emH2.createQuery("SELECT o FROM Osoba o JOIN o.osoFirSerial d WHERE d.firSerial = 51").getResultList();
//        int i = 0;
//        for (Osoba o : podatnicy) {
//            String wynik = o.getOsoNazwisko() + " " + o.getOsoImie1();
//            System.out.println(wynik);
//            String zapyt = "SELECT o FROM OsobaPrz o JOIN o.ospOsoSerial d WHERE d.osoSerial = " + o.getOsoSerial();
//            List<OsobaPrz> przerwy = emH2.createQuery(zapyt).getResultList();
//            int j = 0;
//            for (OsobaPrz pr : przerwy) {
//                String wynik2 = Data.data_yyyyMMdd(pr.getOspDataOd()) + " " + Data.data_yyyyMMdd(pr.getOspDataDo()) + " kwota " + pr.getOspKwota().toString();
//                System.out.println(wynik2);
//                j++;
//                if (j > 20) {
//                    break;
//                }
//            }
////            for (Place r : o.getPlaceList()) {
////                System.out.println(r.getLplDniObow());
////                System.out.println(r.getLplDniPrzepr());
////            }
//            i++;
//            if (i > 20) {
//                break;
//            }
//        }
//        List<OsobaPrz> przerwy = emH2.createQuery("SELECT o FROM OsobaPrz o").getResultList();
//        i = 0;
//        for (OsobaPrz o : przerwy) {
//            String wynik = o.getOspDataOd().toString() + " " + o.getOspDataDo().toString() + " kwota " + o.getOspKwota().toString();
//            System.out.println(wynik);
//            i++;
//            if (i > 20) {
//                break;
//            }
//        }
//    }
//
////        for (Fakturywystokresowe f: faktury) {
////            //String query = "SELECT d FROM Faktura d WHERE d.fakturaPK.numerkolejny='"+f.getDokument().getFakturaPK().getNumerkolejny()+"' AND d.fakturaPK.wystawcanazwa='"+f.getDokument().getFakturaPK().getWystawcanazwa()+"'";
////            //Faktura faktura = (Faktura) emH2.createQuery(query).getSingleResult();
////            //f.setFa_id(faktura.getId());
////            emH2.merge(f);
////        }
////        for (Osoba p :podatnicy) {
////            emH2.getTransaction().begin();
////            List<Dokfk> dokfk =  emH2.createQuery("SELECT o FROM Dokfk o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", "2020").getResultList();
//////            List<Rodzajedok> rodzajedok = emH2.createQuery("SELECT o FROM Rodzajedok o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", 2019).getResultList();
//////            if (dokfk!=null && !dokfk.isEmpty() && rodzajedok!=null && !rodzajedok.isEmpty()) {
//////                for (Dokfk s : dokfk) {
//////                    naniesrodzaj(s,rodzajedok);
//////                    emH2.merge(s);
//////                }
//////                System.out.println("podatnik "+p.getPrintnazwa());
//////            }
////        if (dokfk.size()>0) {
////        }
////            emH2.getTransaction().commit();
////        }
////    }
    private Rok pobierzrok(String rokWpisu, List<Rok> rokList) {
        Rok zwrot = null;
        for (Rok p : rokList) {
            if (p.getRokNumer().toString().equals(rokWpisu)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private List<Okres> pobierzokresySuperplace(int mcWpisu, List<Okres> okresList) {
        List<Okres> zwrot = new ArrayList<>();
        for (Okres o : okresList) {
            if (o.getOkrMieNumer() >= mcWpisu) {
                zwrot.add(o);
            }
        }
        return zwrot;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    private Osoba osobajuzpobrana(String serial, List<Osoba> osoby) {
        Osoba zwrot = null;
        if (osoby!=null) {
            for (Osoba o : osoby) {
                if (o.getOsoSerial().equals(Integer.parseInt(serial))) {
                    if(o.getOsoDodVchar3()!=null&&o.getOsoDodVchar3().equals("tak")) {
                        zwrot = o;
                    }
                }
            }
        } else {
            Msg.msg("e","Brak listy pracowników nie można zweryfikować czy osoba została już pobrana");
        }
        return zwrot;
    }

    

    
}
