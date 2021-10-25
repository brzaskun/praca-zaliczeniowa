/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import DAOsuperplace.OsobaFacade;
import dao.AngazFacade;
import dao.DefinicjalistaplacFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnosckodzusFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.SlownikszkolazatrhistoriaFacade;
import dao.SlownikwypowiedzenieumowyFacade;
import dao.StanowiskopracFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
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
import entity.Rodzajwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaDet;
import kadryiplace.OsobaPropTyp;
import kadryiplace.OsobaPrz;
import kadryiplace.OsobaSkl;
import kadryiplace.OsobaZlec;
import kadryiplace.Rok;
import kadryiplace.WynKodTyt;
import msg.Msg;
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
    private NieobecnosckodzusFacade nieobecnosckodzusFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;

    private String serial;

    public void rob(List<Osoba> osoby) {
        //List<Osoba> podatnicy = osobaFacade.findAll();
        //Osoba osoba = osobaFacade.findByPesel("83020610048");
//        Osoba osoba = osobaFacade.findBySerial("1609");
        if (serial != null) {
            if (osobajuzpobrana(serial, osoby)) {
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
                            Msg.msg("Dodano nowego pracownika");
                    } catch (Exception e) {
                    }
                } else if (pracownik!=null) {
                    moznadalej = true;
                }
                if (moznadalej) {
                    wpisView.setPracownik(pracownik);
                    String datakonca26lat = OsobaBean.obliczdata26(pracownik.getDataurodzenia());
                    FirmaKadry firma = wpisView.getFirma();
                    Angaz angaz = angazFacade.findByPeselFirma(pracownik.getPesel(), firma);
                    if (angaz ==null) {
                        angaz = OsobaBean.nowyangaz(pracownik, firma);
                        angaz.setSerialsp(serial);
                        angazFacade.create(angaz);
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
                            WynKodTyt osdWktSerial = p.getOsdWktSerial();
                            String wktKod = osdWktSerial.getWktKod();
                            if (p.getOsdTytul().equals('W')) {
                                umowakodzuspraca = umowakodzusFacade.findUmowakodzusByKod(wktKod);
                            }
                            if (p.getOsdTytul().equals('Z')) {
                                {
                                    umowakodzuszlecenie = umowakodzusFacade.findUmowakodzusByKod(wktKod);
                                }
                            }
                        }
                    }
                    Umowa aktywna = null;
                    if (umowakodzuspraca!=null) {
                        List<Umowa> umowyoprace = OsobaBean.pobierzumowy(osoba, angaz, rodzajezatr, rodzajewypowiedzenia, umowakodzuspraca);
                        umowaFacade.createList(umowyoprace);
                        Msg.msg("Zachowano umowy");
                        aktywna = umowyoprace.stream().filter(p -> p.isAktywna()).findFirst().get();
                        wpisView.setUmowa(aktywna);
                        List<Stanowiskoprac> stanowiska = OsobaBean.pobierzstanowiska(osoba, aktywna);
                        stanowiskopracFacade.createList(stanowiska);
                        Short formawynagrodzenia = osoba.getOsoWynForma();
                        List<EtatPrac> etaty = OsobaBean.pobierzetaty(osoba, aktywna);
                        etatpracFacade.createList(etaty);
                        List<OsobaSkl> skladniki = osoba.getOsobaSklList();
                        List<Rodzajwynagrodzenia> rodzajewynagrodzenia = rodzajwynagrodzeniaFacade.findAll();
                        OsobaBean.pobierzskladnikwynagrodzenia(skladniki, rodzajewynagrodzenia, aktywna, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                        Msg.msg("Uzupełniono zmienne dotyczące wynagrodzeń");
                        String rokdlakalendarza = "2020";
                        //paski rok 2020 umowa o pracę
                        List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                        kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                        List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                        Rok rok = pobierzrok(rokdlakalendarza, rokList);
                        List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                        List<Pasekwynagrodzen> paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat);
                        List<Definicjalistaplac> listyumowaoprace = definicjalistaplacFacade.findByFirmaRokUmowaoprace(wpisView.getFirma(), rokdlakalendarza);
                        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                        List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyumowaoprace, kalendarze);
                        pasekwynagrodzenFacade.createList(paskigotowe);
                        Msg.msg("Zrobiono kalendarz i paski za 2020 umowa o pracę");
                        //koniec paski 2020 umowa o pracę
                        rokdlakalendarza = "2021";
                        //paski rok 2021 umowa o pracę
                        generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                        kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                        rok = pobierzrok(rokdlakalendarza, rokList);
                        okresList = pobierzokresySuperplace(1, rok.getOkresList());
                        paskiumowaoprace = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat);
                        listyumowaoprace = definicjalistaplacFacade.findByFirmaRokUmowaoprace(wpisView.getFirma(), rokdlakalendarza);
                        kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                        paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowaoprace, listyumowaoprace, kalendarze);
                        pasekwynagrodzenFacade.createList(paskigotowe);
                        Msg.msg("Zrobiono kalendarz i paski za 2021 umowa o pracę");
                        //koniec paski 2021 umowa o pracę
                    }
                    //pobieranie umow zlecenia
                    if (umowakodzuszlecenie!=null) {
                        try {
                            List<OsobaZlec> listaumow = osoba.getOsobaZlecList();
                            List<Umowa> umowyzlecenia = OsobaBean.pobierzumowyzlecenia(listaumow, angaz, umowakodzuszlecenie);
                            umowaFacade.createList(umowyzlecenia);
                            aktywna = umowyzlecenia.stream().filter(p -> p.isAktywna()).findFirst().get();
                            wpisView.setUmowa(aktywna);
                            Msg.msg("Pobrano umowy zlecenia");
                            String rokdlakalendarza = "2020";
                            //paski rok 2020 umowa zlecenia
                            List<Kalendarzmiesiac> generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                            kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                            List<Rok> rokList = osoba.getOsoFirSerial().getRokList();
                            Rok rok = pobierzrok(rokdlakalendarza, rokList);
                            List<Okres> okresList = pobierzokresySuperplace(1, rok.getOkresList());
                            List<Rachunekdoumowyzlecenia> rachunki = OsobaBean.zrobrachunkidozlecenia(wpisView, osoba);
                            rachunekdoumowyzleceniaFacade.createList(rachunki);
                            Msg.msg("Zrobiono rachunki do zleceń za 2020");
                            List<Pasekwynagrodzen> paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, true, datakonca26lat);
                            List<Definicjalistaplac> listyumowazlecenia = definicjalistaplacFacade.findByFirmaRokUmowazlecenia(wpisView.getFirma(), rokdlakalendarza);
                            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                            List<Pasekwynagrodzen> paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                            pasekwynagrodzenFacade.createList(paskigotowe);
                            Msg.msg("Zrobiono kalendarz i paski za 2020 umowa zlecenia");
                            //koniec paski 2020 umowa zlecenia
                            rokdlakalendarza = "2021";
                            //paski rok 2021 umowa zlecenia
                            generujKalendarzNowaUmowa = OsobaBean.generujKalendarzNowaUmowa(angaz, pracownik, aktywna, kalendarzmiesiacFacade, kalendarzwzorFacade, rokdlakalendarza);
                            kalendarzmiesiacFacade.createList(generujKalendarzNowaUmowa);
                            rok = pobierzrok(rokdlakalendarza, rokList);
                            okresList = pobierzokresySuperplace(1, rok.getOkresList());
                            paskiumowazlecenia = OsobaBean.zrobpaskiimportUmowaopraceizlecenia(wpisView, osoba, okresList, false, datakonca26lat);
                            listyumowazlecenia = definicjalistaplacFacade.findByFirmaRokUmowaoprace(wpisView.getFirma(), rokdlakalendarza);
                            kalendarze = kalendarzmiesiacFacade.findByRokUmowa(aktywna, rokdlakalendarza);
                            paskigotowe = OsobaBean.dodajlisteikalendarzdopaska(paskiumowazlecenia, listyumowazlecenia, kalendarze);
                            pasekwynagrodzenFacade.createList(paskigotowe);
                            Msg.msg("Zrobiono kalendarz i paski za 2021 umowa zlecenia");
                            //koniec paski 2021 umowa zlecenia
                        } catch (Exception e) {
                        }
                    }
                    List<Nieobecnosc> nieobecnosci = OsobaBean.pobierznieobecnosci(osoba, aktywna);
                    for (Nieobecnosc p : nieobecnosci) {
                        p.setImportowana(true);
                        p.setRokod(Data.getRok(p.getDataod()));
                        p.setRokdo(Data.getRok(p.getDatado()));
                        p.setMcod(Data.getMc(p.getDataod()));
                        p.setMcdo(Data.getMc(p.getDatado()));
                        if (p.getKodzwolnienia().length() < 3) {
                            p.setNieobecnosckodzus(nieobecnosckodzusFacade.findByOpis(p.getOpis()));
                        } else {
                            try {
                                p.setNieobecnosckodzus(nieobecnosckodzusFacade.findByKod(p.getKodzwolnienia()));
                            } catch (Exception e) {
                                p.setNieobecnosckodzus(nieobecnosckodzusFacade.findByOpis(p.getOpis()));
                            }
                        }
                    }
                    nieobecnosckodzusFacade.createList(nieobecnosci);
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
                for (Osoba o : osoby) {
                    List<Angaz> listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
                    for (Angaz a : listapracownikow) {
                        if (a.getSerialsp()!=null) {
                            if (o.getOsoSerial().equals(Integer.parseInt(a.getSerialsp()))) {
                                o.setOsoDodVchar3("tak");
                                break;
                            }
                        }
                    }
                }
                System.out.println("koniec");
            }
        } else {
            Msg.msg("e", "Brak numeru serial");
            System.out.println("koniec");
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
        EntityManager emH2 = emfH2.createEntityManager();
        List<Osoba> podatnicy = emH2.createQuery("SELECT o FROM Osoba o JOIN o.osoFirSerial d WHERE d.firSerial = 51").getResultList();
        int i = 0;
        for (Osoba o : podatnicy) {
            String wynik = o.getOsoNazwisko() + " " + o.getOsoImie1();
            System.out.println(wynik);
            String zapyt = "SELECT o FROM OsobaPrz o JOIN o.ospOsoSerial d WHERE d.osoSerial = " + o.getOsoSerial();
            List<OsobaPrz> przerwy = emH2.createQuery(zapyt).getResultList();
            int j = 0;
            for (OsobaPrz pr : przerwy) {
                String wynik2 = Data.data_yyyyMMdd(pr.getOspDataOd()) + " " + Data.data_yyyyMMdd(pr.getOspDataDo()) + " kwota " + pr.getOspKwota().toString();
                System.out.println(wynik2);
                j++;
                if (j > 20) {
                    break;
                }
            }
//            for (Place r : o.getPlaceList()) {
//                System.out.println(r.getLplDniObow());
//                System.out.println(r.getLplDniPrzepr());
//            }
            i++;
            if (i > 20) {
                break;
            }
        }
        List<OsobaPrz> przerwy = emH2.createQuery("SELECT o FROM OsobaPrz o").getResultList();
        i = 0;
        for (OsobaPrz o : przerwy) {
            String wynik = o.getOspDataOd().toString() + " " + o.getOspDataDo().toString() + " kwota " + o.getOspKwota().toString();
            System.out.println(wynik);
            i++;
            if (i > 20) {
                break;
            }
        }
    }

//        for (Fakturywystokresowe f: faktury) {
//            //String query = "SELECT d FROM Faktura d WHERE d.fakturaPK.numerkolejny='"+f.getDokument().getFakturaPK().getNumerkolejny()+"' AND d.fakturaPK.wystawcanazwa='"+f.getDokument().getFakturaPK().getWystawcanazwa()+"'";
//            //Faktura faktura = (Faktura) emH2.createQuery(query).getSingleResult();
//            //f.setFa_id(faktura.getId());
//            emH2.merge(f);
//        }
//        for (Osoba p :podatnicy) {
//            emH2.getTransaction().begin();
//            List<Dokfk> dokfk =  emH2.createQuery("SELECT o FROM Dokfk o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", "2020").getResultList();
////            List<Rodzajedok> rodzajedok = emH2.createQuery("SELECT o FROM Rodzajedok o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", 2019).getResultList();
////            if (dokfk!=null && !dokfk.isEmpty() && rodzajedok!=null && !rodzajedok.isEmpty()) {
////                for (Dokfk s : dokfk) {
////                    naniesrodzaj(s,rodzajedok);
////                    emH2.merge(s);
////                }
////                System.out.println("podatnik "+p.getPrintnazwa());
////            }
//        if (dokfk.size()>0) {
//        }
//            emH2.getTransaction().commit();
//        }
//    }
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

    private boolean osobajuzpobrana(String serial, List<Osoba> osoby) {
        boolean zwrot = false;
        if (osoby!=null) {
            for (Osoba o : osoby) {
                if (o.getOsoSerial().equals(Integer.parseInt(serial))) {
                    if(o.getOsoDodVchar3()!=null&&o.getOsoDodVchar3().equals("tak")) {
                        zwrot = true;
                    }
                }
            }
        } else {
            Msg.msg("e","Brak listy pracowników nie można zweryfikować czy osoba została już pobrana");
        }
        return zwrot;
    }

    
}
