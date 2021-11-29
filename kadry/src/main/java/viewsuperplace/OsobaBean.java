/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import beanstesty.UmowaBean;
import comparator.Umowacomparator;
import comparator.ZatrudHistComparator;
import dao.DefinicjalistaplacFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.RodzajlistyplacFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajlistyplac;
import entity.Rodzajnieobecnosci;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Swiadczeniekodzus;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaPrz;
import kadryiplace.OsobaSkl;
import kadryiplace.OsobaZlec;
import kadryiplace.Place;
import kadryiplace.PlacePrz;
import kadryiplace.PlaceSkl;
import kadryiplace.PlaceZlec;
import kadryiplace.StSystOpis;
import kadryiplace.StSystWart;
import kadryiplace.StanHist;
import kadryiplace.WymiarHist;
import kadryiplace.ZatrudHist;
import msg.Msg;
import view.WpisView;
import z.Z;

/**
 *
 * @author Osito
 */
public class OsobaBean {
    
    public static Pracownik pobierzOsobaBasic(Osoba p) {
        Pracownik pracownik = new Pracownik();
        pracownik.setImie(p.getOsoImie1());
        pracownik.setDrugieimie(p.getOsoImie2());
        pracownik.setNazwisko(p.getOsoNazwisko());
        pracownik.setPesel(p.getOsoPesel());
        pracownik.setDowodosobisty(p.getOsoDodVchar1());
        pracownik.setDataurodzenia(Data.data_yyyyMMdd(p.getOsoUrodzData()));
        pracownik.setPlec(p.getOsoPlec().toString());
        pracownik.setOjciec(p.getOsoImieOjca());
        pracownik.setMatka(p.getOsoImieMatki());
        pracownik.setMiejsceurodzenia(p.getOsoMiejsceUr());
        pracownik.setFormawynagrodzenia(p.getOsoWynForma());
        pracownik.setBankkonto(p.getOsoKonto());
        pracownik.setDatazatrudnienia(p.getOsoDataZatr());
        pracownik.setDatazwolnienia(p.getOsoDataZwol());
        pracownik.setObywatelstwo(p.getOsoObywatelstwo().toLowerCase());
        //adres
        pracownik.setKraj(p.getOsoPanSerial().getPanNazwa());
        pracownik.setWojewodztwo(p.getOsoWojewodztwo());
        pracownik.setPowiat(p.getOsoPowiat());
        pracownik.setGmina(p.getOsoGmina());
        pracownik.setMiasto(p.getOsoMiaSerial().getMiaNazwa());
        pracownik.setUlica(p.getOsoUlica());
        pracownik.setDom(p.getOsoDom());
        pracownik.setLokal(p.getOsoDom());
        pracownik.setKod(p.getOsoKod());
        pracownik.setPoczta(p.getOsoPoczta());
        return pracownik;
    }

    static List<Umowa> pobierzumowy(Osoba osoba, Angaz angaz, List<Slownikszkolazatrhistoria> rodzajezatr, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia, Umowakodzus umowakodzus) {
        List<Umowa> zwrot = new ArrayList<>();
        List<ZatrudHist> zatrudHist = osoba.getZatrudHistList();
        Collections.sort(zatrudHist, new ZatrudHistComparator());
        int nrumowy = 1;
        for (ZatrudHist r : zatrudHist) {
            try {
                Slownikszkolazatrhistoria slownikszkolazatrhistoria = pobierzrodzajzatr(r, rodzajezatr);
                Slownikwypowiedzenieumowy slownikwypowiedzenieumowy = null;
                if (r.getZahZwolKod()!=null) {
                    slownikwypowiedzenieumowy = pobierzrodzajwypowiedzenia(r, rodzajewypowiedzenia);
                }
                Umowa nowa = UmowaBean.create(nrumowy, osoba, angaz, r, slownikszkolazatrhistoria);
                nowa.setAngaz(angaz);
                nowa.setSlownikszkolazatrhistoria(slownikszkolazatrhistoria);
                nowa.setSlownikwypowiedzenieumowy(slownikwypowiedzenieumowy);
                nowa.setPrzyczynawypowiedzenia(r.getZahZwolUwagi());
                nowa.setUmowakodzus(umowakodzus);
                nowa.setImportowana(true);
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e){}
        }
        return zwrot;
    }
    
    static List<Umowa> pobierzumowyzlecenia(List<OsobaZlec> listaumow, Angaz angaz, Umowakodzus umowakodzus) {
        List<Umowa> zwrot = new ArrayList<>();
        int nrumowy = 1;
        for (OsobaZlec r : listaumow) {
            try {
                Umowa nowa = UmowaBean.createzlecenie(nrumowy, angaz, r);
                nowa.setAngaz(angaz);
                nowa.setUmowakodzus(umowakodzus);
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e){}
        }
        Collections.sort(zwrot, new Umowacomparator());
        zwrot.get(0).setAktywna(true);
        return zwrot;
    }

    static Angaz nowyangaz(Pracownik pracownik, FirmaKadry firma) {
        Angaz nowy = new Angaz();
        nowy.setFirma(firma);
        nowy.setPracownik(pracownik);
        return nowy;
    }

    private static Slownikszkolazatrhistoria pobierzrodzajzatr(ZatrudHist r, List<Slownikszkolazatrhistoria> rodzajezatr) {
        Slownikszkolazatrhistoria zwrot = null;
        for (Slownikszkolazatrhistoria p : rodzajezatr) {
            if (p.getSymbol().equals(r.getZahTyp().toString())) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private static Slownikwypowiedzenieumowy pobierzrodzajwypowiedzenia(ZatrudHist r, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia) {
        Slownikwypowiedzenieumowy zwrot = null;
        for (Slownikwypowiedzenieumowy p : rodzajewypowiedzenia) {
            if (p.getSymbol().equals(r.getZahZwolKod().toString())) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    static List<Stanowiskoprac> pobierzstanowiska(Osoba osoba, Umowa umowa) {
        List<Stanowiskoprac> zwrot = new ArrayList<>();
        List<StanHist> stanHist = osoba.getStanHistList();
        for (StanHist r : stanHist) {
            try {
                Stanowiskoprac stan = new Stanowiskoprac();
                stan.setDataod(Data.data_yyyyMMdd(r.getSthDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getSthDataDo()));
                stan.setOpis(r.getSthStaSerial().getStaNazwa());
                stan.setUmowa(umowa);
                zwrot.add(stan);
            } catch (Exception e){}
        }
        return zwrot;
    }

    static List<EtatPrac> pobierzetaty(Osoba osoba, Umowa umowa) {
        List<EtatPrac> zwrot = new ArrayList<>();
        List<WymiarHist> wymiarHist = osoba.getWymiarHistList();
        for (WymiarHist r : wymiarHist) {
            try {
                EtatPrac stan = new EtatPrac();
                stan.setDataod(Data.data_yyyyMMdd(r.getWehDataOd()));
                stan.setDatado(Data.data_yyyyMMdd(r.getWehDataDo()));
                stan.setEtat1(r.getWehEtat1());
                stan.setEtat2(r.getWehEtat2());
                stan.setUmowa(umowa);
                zwrot.add(stan);
            } catch (Exception e){}
        }
        return zwrot;
    }
    
    static List<Kalendarzmiesiac> generujKalendarzNowaUmowa(Angaz angaz, Pracownik pracownik, Umowa umowa, KalendarzmiesiacFacade kalendarzmiesiacFacade, KalendarzwzorFacade kalendarzwzorFacade, String rok) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        if (angaz!=null && pracownik!=null && umowa!=null) {
            //Integer rokodumowy = Integer.parseInt(Data.getRok(umowa.getDataod()));
            //Integer rokimportu = Integer.parseInt(rok);
            //Integer mcod = Integer.parseInt(Data.getMc(umowa.getDataod()));
            //Integer dzienod = Integer.parseInt(Data.getDzien(umowa.getDataod()));
            Integer mcod = 1;
            Integer dzienod = 1;
//            if (rokodumowy<rokimportu) {
//                mcod = 1;
//                dzienod = 1;
//            }
//            if (rokodumowy<=rokimportu) {
            for (String mce: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mce);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(rok);
                    kal.setMc(mce);
                    kal.setUmowa(umowa);
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(umowa,rok, mce);
                    if (kalmiesiac==null) {
                        Kalendarzwzor pobranywzorcowy = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mce);
                        if (pobranywzorcowy!=null) {
                            kal.ganerujdnizwzrocowego(pobranywzorcowy, dzienod);
                            zwrot.add(kal);
                            dzienod = 1;
                        } else {
                            Msg.msg("e","Brak kalendarza wzorcowego za "+mce);
                            break;
                        }
                    }
                }
                }
                Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
//            }
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
        return zwrot;
    }
    
    
    static List<Skladnikwynagrodzenia> pobierzskladnikwynagrodzenia(List<OsobaSkl> skladniki, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Umowa aktywna, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        List<Skladnikwynagrodzenia> zwrot = new ArrayList<>();
        if (skladniki!=null) {
            OsobaSkl wybrany = null;
            for (OsobaSkl s : skladniki) {
                if (wybrany == null) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnik(wybrany, rodzajewynagrodzenia, aktywna, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    zwrot.add(generujskladnik);
                } else if (s.getOssSerial()>wybrany.getOssSerial()) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnik(wybrany, rodzajewynagrodzenia, aktywna, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    zwrot.add(generujskladnik);
                }
            }
        }
        return zwrot;
    }
    
    static List<Skladnikwynagrodzenia> pobierzskladnikzlecenie(List<OsobaZlec> skladniki, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, List<Umowa> umowyzlecenia, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        List<Skladnikwynagrodzenia> zwrot = new ArrayList<>();
        if (skladniki!=null) {
            OsobaZlec wybrany = null;
            for (OsobaZlec s : skladniki) {
                if (wybrany == null) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnikzlecenie(wybrany, rodzajewynagrodzenia, umowyzlecenia, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    zwrot.add(generujskladnik);
                } else if (s.getOzlSerial()>wybrany.getOzlSerial()) {
                    wybrany = s;
                    Skladnikwynagrodzenia generujskladnik = generujskladnikzlecenie(wybrany, rodzajewynagrodzenia, umowyzlecenia, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                    zwrot.add(generujskladnik);
                }
            }
        }
        return zwrot;
    }
    
    private static Skladnikwynagrodzenia generujskladnik(OsobaSkl wybrany, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Umowa aktywna, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        skladnik.setUmowa(aktywna);
        skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzenia(wybrany,rodzajewynagrodzenia));
        skladnikWynagrodzeniaFacade.create(skladnik);
        pobierzzmiennawynagrodzenia(aktywna, skladnik, wybrany, zmiennaWynagrodzeniaFacade);
        return skladnik;
    }
    
     private static Skladnikwynagrodzenia generujskladnikzlecenie(OsobaZlec wybrany, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, List<Umowa> umowyzlecenia, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        Umowa aktywna = pobierzumowezlecenia(wybrany, umowyzlecenia);
        skladnik.setUmowa(aktywna);
        skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzeniazlecenie(wybrany,rodzajewynagrodzenia));
        skladnikWynagrodzeniaFacade.create(skladnik);
        pobierzzmiennawynagrodzeniazlecenie(aktywna, skladnik, wybrany, zmiennaWynagrodzeniaFacade);
        return skladnik;
    }
    private static Umowa pobierzumowezlecenia(OsobaZlec wybrany, List<Umowa> umowyzlecenia) {
        Umowa zwrot = null;
        for (Umowa u: umowyzlecenia) {
            if (u.getNrkolejny().equals(wybrany.getOzlNrUmowy())) {
                zwrot = u;
            }
        }
        return zwrot;
    }
    private static Rodzajwynagrodzenia pobierzrodzajwynagrodzenia(OsobaSkl s, List<Rodzajwynagrodzenia> rodzajewynagrodzenia) {
        Rodzajwynagrodzenia zwrot = null;
        if (rodzajewynagrodzenia!=null) {
            for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
                if (p.getKod().equals(s.getOssWksSerial().getWksKod())) {
                    if (p.getOpispelny().equals(s.getOssWksSerial().getWksOpis())){
                        zwrot = p;
                        break;
                    }
                }
            }
        }
        return zwrot;
    }
    
     private static Rodzajwynagrodzenia pobierzrodzajwynagrodzeniazlecenie(OsobaZlec s, List<Rodzajwynagrodzenia> rodzajewynagrodzenia) {
        Rodzajwynagrodzenia zwrot = null;
        if (rodzajewynagrodzenia!=null) {
            for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
                if (p.getKod().equals(s.getOzlWksSerial().getWksKod())) {
                    if (p.getOpispelny().equals(s.getOzlWksSerial().getWksOpis())){
                        zwrot = p;
                        break;
                    }
                }
            }
        }
        return zwrot;
    }


    static void pobierzzmiennawynagrodzenia(Umowa aktywna, Skladnikwynagrodzenia skladnikwynagrodzenia, OsobaSkl s, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        if (skladnikwynagrodzenia!=null) {
            StSystOpis ossSsdSerial1 = s.getOssSsdSerial1();
            if (ossSsdSerial1!=null) {
                List<StSystWart> osobaSklList = s.getOssSsdSerial1().getStSystWartList();
                if (osobaSklList!=null) {
                    for (StSystWart t :osobaSklList) {
                        Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
                        wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                        wiersz.setNazwa(ossSsdSerial1.getSsdNazwa());
                        wiersz.setNetto0brutto1(true);
                        wiersz.setWaluta("PLN");
                        if (t.getSsoDataOd()!=null) {
                            wiersz.setDataod(Data.data_yyyyMMdd(t.getSsoDataOd()));
                            if (t.getSsoDataDo()!=null) {
                                wiersz.setDatado(Data.data_yyyyMMdd(t.getSsoDataDo()));
                            }
                            String ss = String.valueOf(t.getSsoStatus());
                            if (ss.equals("A")) {
                                wiersz.setAktywna(true);
                            }
                        } else {
                            String mcod = String.valueOf(t.getSsoMiesiacOd());
                            if (mcod.length()==1) {
                                mcod="0"+mcod;
                            }
                            String dataod = t.getSsoRokOd()+"-"+mcod+"-01";
                            wiersz.setDataod(dataod);
                            String mcdo = String.valueOf(t.getSsoMiesiacDo());
                            if (mcdo.length()==1) {
                                mcdo="0"+mcdo;
                            }
                            String datado = Data.ostatniDzien(String.valueOf(t.getSsoRokDo()),mcdo);
                            wiersz.setDatado(datado);
                        }
                        wiersz.setKwota(Z.z(t.getSsoNumeric().doubleValue()));
                        if (wiersz.getKwota()!=0.0) {
                            zmiennaWynagrodzeniaFacade.create(wiersz);
                        }
                    }
                }
            } else {
                Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
                wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                wiersz.setNazwa(s.getOssWksSerial().getWksOpisSkr());
                wiersz.setNetto0brutto1(true);
                wiersz.setWaluta("PLN");
                wiersz.setDataod(Data.data_yyyyMMdd(s.getOssDataOd()));
                if (s.getOssDataDo()!=null) {
                    wiersz.setDatado(Data.data_yyyyMMdd(s.getOssDataDo()));
                } else {
                    wiersz.setAktywna(true);
                }
                wiersz.setKwota(Z.z(s.getOssKwota().doubleValue()));
                if (wiersz.getKwota()!=0.0) {
                    zmiennaWynagrodzeniaFacade.create(wiersz);
                }
            }
        }
    }
    
    static void pobierzzmiennawynagrodzeniazlecenie(Umowa aktywna, Skladnikwynagrodzenia skladnikwynagrodzenia, OsobaZlec s, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        if (skladnikwynagrodzenia!=null) {
            Zmiennawynagrodzenia wiersz = new Zmiennawynagrodzenia();
            wiersz.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            wiersz.setNazwa(s.getOzlWksSerial().getWksOpisSkr());
            wiersz.setNetto0brutto1(true);
            wiersz.setWaluta("PLN");
            wiersz.setDataod(Data.data_yyyyMMdd(s.getOzlDataOd()));
            if (s.getOzlDataDo()!=null) {
                wiersz.setDatado(Data.data_yyyyMMdd(s.getOzlDataDo()));
            } else {
                wiersz.setAktywna(true);
            }
            wiersz.setKwota(Z.z(s.getOzlKwota().doubleValue()));
            if (wiersz.getKwota()!=0.0) {
                zmiennaWynagrodzeniaFacade.create(wiersz);
            }
        }
    }

//     static public void naniesnieobecnosc(Nieobecnosc nieobecnosc) {
//        if (nieobecnosc.isNaniesiona() == false) {
//            try {
//                if (nieobecnosc.getRokod().equals() || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                    String mcod = nieobecnosc.getMcod();
//                    if (nieobecnosc.getRokod().equals(wpisView.getRokUprzedni())) {
//                        mcod = "01";
//                    }
//                    String mcdo = nieobecnosc.getMcdo();
//                    for (String mc : Mce.getMceListS()) {
//                        if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mc);
//                            if (znaleziony != null) {
//                                if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                                    znaleziony.naniesnieobecnosc(nieobecnosc);
//                                    nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+Data.iletodniRoboczych(nieobecnosc.getDataod(), nieobecnosc.getDatado(), znaleziony.getDzienList()));
//                                    nieobecnosc.setNaniesiona(true);
//                                }
//                                nieobecnoscFacade.edit(nieobecnosc);
//                                kalendarzmiesiacFacade.edit(znaleziony);
//                                czynaniesiono = true;
//                                kalendarzmiesiacView.init();
//                            } else {
//                                Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//            }
//        }
//    }
//    
    
    
    static List<Pasekwynagrodzen> zrobpaskiimportUmowaopraceizlecenia(WpisView wpisView, Osoba osoba, List<Okres> okresList, boolean umowaoprace0zlecenia1, String datakonca26lat,
        List<Skladnikwynagrodzenia> skladnikwynagrodzenia, List<Nieobecnosc> nieobecnoscilista) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        List<Place> placeList = osoba.getPlaceList();
        for (Place r : placeList) {
            try {
                if (umowaoprace0zlecenia1) {
                    if (okresList.contains(r.getLplOkrSerial())&&r.getLplKodTytU12().startsWith("04")) {
                        String rok = String.valueOf(r.getLplOkrSerial().getOkrRokSerial().getRokNumer());
                        String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getLplOkrSerial().getOkrMieNumer()));
                        Pasekwynagrodzen nowypasek = new Pasekwynagrodzen(r);
                        nowypasek.setRok(rok);
                        nowypasek.setMc(mc);
                        nowypasek.setImportowany(true);
                        List<PlaceZlec> placeZlecList = r.getPlaceZlecList();
                        historycznenaliczeniezlecenie(placeZlecList, nowypasek, skladnikwynagrodzenia);
                        nowypasek.setBrutto(nowypasek.getBrutto()+nowypasek.getBruttobezzus() + nowypasek.getBruttozus() + nowypasek.getBruttobezzusbezpodatek());
                        nowypasek.setBruttominusspoleczne(Z.z(nowypasek.getBrutto()-nowypasek.getRazemspolecznepracownik()));
                        zwrot.add(nowypasek);
                    }
                } else {
                    if (okresList.contains(r.getLplOkrSerial())&&!r.getLplKodTytU12().startsWith("04")) {
                        String rok = String.valueOf(r.getLplOkrSerial().getOkrRokSerial().getRokNumer());
                        String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getLplOkrSerial().getOkrMieNumer()));
                        Pasekwynagrodzen nowypasek = new Pasekwynagrodzen(r);
                        nowypasek.setRok(rok);
                        nowypasek.setMc(mc);
                        nowypasek.setImportowany(true);
                        List<PlaceSkl> placeSklList = r.getPlaceSklList();
                        historycznenaliczeniewynagrodzenia(placeSklList, nowypasek, skladnikwynagrodzenia);
                        List<PlacePrz>  placePrzList = r.getPlacePrzList();
                        historycznenaliczenienieobecnosc(placePrzList, nowypasek, nieobecnoscilista);
                        nowypasek.setBrutto(nowypasek.getBrutto()+nowypasek.getBruttobezzus() + nowypasek.getBruttozus() + nowypasek.getBruttobezzusbezpodatek());
                        nowypasek.setBruttominusspoleczne(Z.z(nowypasek.getBrutto()-nowypasek.getRazemspolecznepracownik()));
                        zwrot.add(nowypasek);
                    }
                }
            } catch (Exception e){}
        }
        return zwrot;
    }

    
        static void historycznenaliczeniezlecenie(List<PlaceZlec> placeZlecList, Pasekwynagrodzen pasekwynagrodzen, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        for (PlaceZlec p : placeZlecList) {
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getPzlDataOd()));
            naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getPzlDataDo()));
            naliczenieskladnikawynagrodzenia.setKwotadolistyplac(p.getPzlKwota().doubleValue());
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikzlecenie(p, skladnikwynagrodzenia));
            naliczenieskladnikawynagrodzenia.setSkl_dod_1(p.getPzlDod1());
            if (p.getPzlZus().equals('T')) {
                pasekwynagrodzen.setBruttozus(Z.z(pasekwynagrodzen.getBruttozus()+p.getPzlKwota().doubleValue()));
            } else if (p.getPzlPodDoch().equals('T')) {
                    pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus()+p.getPzlKwota().doubleValue()));
            } else {
                pasekwynagrodzen.setBruttobezzusbezpodatek(Z.z(pasekwynagrodzen.getBruttobezzusbezpodatek()+p.getPzlKwota().doubleValue()));
            }
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
            pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        }
    }
        static void historycznenaliczeniewynagrodzenia(List<PlaceSkl> placeSklList, Pasekwynagrodzen pasekwynagrodzen, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        for (PlaceSkl p : placeSklList) {
            if (!p.getSklRodzaj().equals('U')) {//U to sa redukcje wynagrodzenia
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getSklDataOd()));
                naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getSklDataDo()));
                naliczenieskladnikawynagrodzenia.setKwotadolistyplac(p.getSklKwota().doubleValue());
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikwynagrodzenia(p, skladnikwynagrodzenia));
                naliczenieskladnikawynagrodzenia.setSkl_dod_1(p.getSklDod1());
                if (p.getSklZus().equals('T')) {
                    pasekwynagrodzen.setBruttozus(Z.z(pasekwynagrodzen.getBruttozus()+p.getSklKwota().doubleValue()));
                } else if (p.getSklPodDoch().equals('T')) {
                        pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus()+p.getSklKwota().doubleValue()));
                } else {
                    pasekwynagrodzen.setBruttobezzusbezpodatek(Z.z(pasekwynagrodzen.getBruttobezzusbezpodatek()+p.getSklKwota().doubleValue()));
                }
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            } else {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                naliczenieskladnikawynagrodzenia.setDataod(Data.data_yyyyMMddNull(p.getSklDataOd()));
                naliczenieskladnikawynagrodzenia.setDatado(Data.data_yyyyMMddNull(p.getSklDataDo()));
                naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(p.getSklKwota().doubleValue());
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(histporiapobierzskladnikwynagrodzenia(p, skladnikwynagrodzenia));
                naliczenieskladnikawynagrodzenia.setSkl_rodzaj(p.getSklRodzaj());
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            }
        }
    }
    
    static void historycznenaliczenienieobecnosc(List<PlacePrz> placePrzList, Pasekwynagrodzen pasekwynagrodzen, List<Nieobecnosc> nieobecnoscilista) {
        for (PlacePrz p : placePrzList) {
            Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
            naliczenienieobecnosc.setNieobecnosc(historiawyszukajnieobecnosci(p, nieobecnoscilista));
            naliczenienieobecnosc.setLiczbadniobowiazku(30);
            naliczenienieobecnosc.setLiczbadniurlopu(p.getPrzLiczba());
            naliczenienieobecnosc.setPodstawadochoroby(p.getPrzPodstawa().doubleValue());
            naliczenienieobecnosc.setStawkadzienna(p.getPrzKwota1().doubleValue());
            naliczenienieobecnosc.setKwota(p.getPrzKwota().doubleValue());
            naliczenienieobecnosc.setKwotabezzus(p.getPrzKwota().doubleValue());
            naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
            pasekwynagrodzen.setBruttobezzus(Z.z(pasekwynagrodzen.getBruttobezzus()+p.getPrzKwota().doubleValue()));
            pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
        }
    }
    
    private static Nieobecnosc historiawyszukajnieobecnosci(PlacePrz p, List<Nieobecnosc> nieobecnoscilista) {
        Nieobecnosc zwrot = null;
        for (Nieobecnosc n : nieobecnoscilista) {
            String przerwaod = Data.data_yyyyMMddNull(p.getPrzDataOd());
            String przerwado = Data.data_yyyyMMddNull(p.getPrzDataDo());
            boolean czyjestpo = Data.czyjestpo(n.getDataod(), przerwaod);
            boolean czyjestprzed = Data.czyjestprzed(n.getDatado(), przerwado);
            if (czyjestpo&&czyjestprzed) {
                zwrot = n;
                break;
            }
        }
        return zwrot;
    }
    
     private static Skladnikwynagrodzenia histporiapobierzskladnikwynagrodzenia(PlaceSkl s, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        Skladnikwynagrodzenia zwrot = null;
        if (skladnikwynagrodzenia!=null) {
            for (Skladnikwynagrodzenia p : skladnikwynagrodzenia) {
                if (p.getRodzajwynagrodzenia().getWks_serial().equals(s.getSklWksSerial().getWksSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
     
     private static Skladnikwynagrodzenia histporiapobierzskladnikzlecenie(PlaceZlec s, List<Skladnikwynagrodzenia> skladnikwynagrodzenia) {
        Skladnikwynagrodzenia zwrot = null;
        if (skladnikwynagrodzenia!=null) {
            for (Skladnikwynagrodzenia p : skladnikwynagrodzenia) {
                if (p.getUmowa().getNrkolejny().equals(s.getPzlLplSerial().getLplPotrInne1Opis())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
     
   
     static List<Rachunekdoumowyzlecenia> zrobrachunkidozlecenia(WpisView wpisView, Osoba osoba) {
        List<Rachunekdoumowyzlecenia> zwrot = new ArrayList<>();
        List<PlaceZlec> placeList = osoba.getPlaceZlecList();
        for (PlaceZlec r : placeList) {
            try {
                Rachunekdoumowyzlecenia nowypasek = new Rachunekdoumowyzlecenia(r);
                nowypasek.setUmowa(wpisView.getUmowa());
                String rok = String.valueOf(r.getPzlOkrSerial().getOkrRokSerial().getRokNumer());
                String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getPzlOkrSerial().getOkrMieNumer()));
                nowypasek.setRok(rok);
                nowypasek.setMc(mc);
                nowypasek.setImportowany(true);
                zwrot.add(nowypasek);
            } catch (Exception e){}
        }
        return zwrot;
    }
   

    static List<Pasekwynagrodzen> dodajlisteikalendarzdopaska(List<Pasekwynagrodzen> paski, List<Definicjalistaplac> listyplac, List<Kalendarzmiesiac> kalendarze) {
        for (Pasekwynagrodzen p : paski) {
            for (Definicjalistaplac r : listyplac) {
                if (r.getRodzajlistyplac().getTyt_serial().equals(p.getLis_tyt_serial())) {
                    if (r.getRok().equals(p.getRok())&&r.getMc().equals(p.getMc())) {
                        p.setDefinicjalistaplac(r);
                        break;
                    }
                }
            }
            for (Kalendarzmiesiac r : kalendarze) {
                if (r.getRok().equals(p.getRok())&&r.getMc().equals(p.getMc())) {
                    p.setKalendarzmiesiac(r);
                    break;
                }
            }
        }
        return paski;
    }

    static List<Nieobecnosc> pobierznieobecnosci(Osoba osoba, Umowa umowa, List<Rodzajnieobecnosci> rodzajnieobecnoscilist, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        List<OsobaPrz> osobaPrzList = osoba.getOsobaPrzList();
        for (OsobaPrz r : osobaPrzList) {
            try {
                Nieobecnosc n = new Nieobecnosc(r, umowa);
                n.setRodzajnieobecnosci(histporiapobierzrodzajnieobescnosci(r, rodzajnieobecnoscilist));
                n.setSwiadczeniekodzus(histporiapobierzswiadczeniekodzus(r, swiadczeniekodzuslist));
                if (n.getUmowa()!=null) {
                    zwrot.add(n);
                }
            } catch (Exception e){
                System.out.println("");
            }
        }
        return zwrot;
    }

    
      private static Rodzajnieobecnosci histporiapobierzrodzajnieobescnosci(OsobaPrz s, List<Rodzajnieobecnosci> rodzajnieobecnoscilist) {
        Rodzajnieobecnosci zwrot = null;
        if (rodzajnieobecnoscilist!=null) {
            for (Rodzajnieobecnosci p : rodzajnieobecnoscilist) {
                if (p.getAbsSerial().equals(s.getOspAbsSerial().getAbsSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
     
      private static Swiadczeniekodzus histporiapobierzswiadczeniekodzus(OsobaPrz s, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        Swiadczeniekodzus zwrot = null;
        if (swiadczeniekodzuslist!=null && s.getOspWkpSerial()!=null) {
            for (Swiadczeniekodzus p : swiadczeniekodzuslist) {
                if (p.getWkp_serial().equals(s.getOspWkpSerial().getWkpSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

  
    public static String obliczdata26(String dataurodzenia) {
        LocalDate date = LocalDate.parse(dataurodzenia);
        LocalDate plusYears = date.plusYears(26);
        return plusYears.toString();
    }

    public static List<Definicjalistaplac> generujlistyplac(List<Pasekwynagrodzen> paskiumowaoprace, FirmaKadry firma, DefinicjalistaplacFacade definicjalistaplacFacade, RodzajlistyplacFacade rodzajlistyplacFacade, String rok) {
        List<Definicjalistaplac> listy = null;
        if (paskiumowaoprace!=null && !paskiumowaoprace.isEmpty()) {
            Pasekwynagrodzen pasekwzorcowy = paskiumowaoprace.get(0);
            Integer lis_tyt_serial = pasekwzorcowy.getLis_tyt_serial();
            Rodzajlistyplac rodzajlistyplac = rodzajlistyplacFacade.findByTyt_serial(lis_tyt_serial);
            listy = definicjalistaplacFacade.findByFirmaRokRodzaj(firma, pasekwzorcowy.getRok(), rodzajlistyplac);
            if (listy==null || listy.isEmpty()) {
                rodzajlistyplac = rodzajlistyplacFacade.findByTyt_serial(lis_tyt_serial);
                for(String mc : Mce.getMceListS()) {
                    Definicjalistaplac definicjalistaplac = nowalista(rok, mc, rodzajlistyplac, firma);
                    definicjalistaplacFacade.create(definicjalistaplac);
                    listy.add(definicjalistaplac);
                }
            }
        }
        return listy;
    }
    
    public static String generujRodzajLP(String rok, String mc, Rodzajlistyplac wybranyrodzajlisty) {
        String zwrot = null;
        if (wybranyrodzajlisty.getSymbol()!=null) {
            zwrot = rok+"/"+mc+"/"+wybranyrodzajlisty.getSymbol();
        }
        return zwrot;
    }
    
    
    public static Definicjalistaplac nowalista(String rok, String mc, Rodzajlistyplac rodzajlistyplac, FirmaKadry firma) {
        Definicjalistaplac selected = new Definicjalistaplac();
         try {
            String lewaczesc = rok+"-"+mc+"-";
            String nowadata = Data.ostatniDzien(rok, mc);
            selected.setDatasporzadzenia(nowadata);
            String[] zwiekszone = Mce.zwiekszmiesiac(rok, mc);
            String rokN = zwiekszone[0];
            String mcN = zwiekszone[1];
            lewaczesc = rokN+"-"+mcN+"-";
            selected.setDatazus(lewaczesc+"15");
            selected.setRodzajlistyplac(rodzajlistyplac);
            selected.setDatapodatek(lewaczesc+"20");
            selected.setMc(mc);
            selected.setOpis("");
            selected.setRok(rok);
            selected.setFirma(firma);
            String nazwalisty = OsobaBean.generujRodzajLP(rok, mc, rodzajlistyplac);
            if (nazwalisty==null) {
                selected = null;
            } else {
                selected.setNrkolejny(nazwalisty);
            }
        } catch (Exception e) {}
         return selected;
    }
    
    public static void main(String[] arg) {
        String data = "1970-05-11";
        System.out.println(obliczdata26(data));
    }

    

   
    
    
}
