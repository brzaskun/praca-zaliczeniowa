/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import beanstesty.UmowaBean;
import comparator.Umowacomparator;
import comparator.ZatrudHistComparator;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
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
    
    private static Skladnikwynagrodzenia generujskladnik(OsobaSkl wybrany, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Umowa aktywna, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
        skladnik.setUmowa(aktywna);
        skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzenia(wybrany,rodzajewynagrodzenia));
        skladnikWynagrodzeniaFacade.create(skladnik);
        pobierzzmiennawynagrodzenia(aktywna, skladnik, wybrany, zmiennaWynagrodzeniaFacade);
        return skladnik;
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

    static List<Pasekwynagrodzen> zrobpaskiimportUmowaopraceizlecenia(WpisView wpisView, Osoba osoba, List<Okres> okresList, boolean umowaoprace0zlecenia1, String datakonca26lat,
        List<Skladnikwynagrodzenia> skladnikwynagrodzenia, List<Rodzajnieobecnosci> rodzajnieobecnoscilist, NieobecnoscFacade nieobecnoscFacade, Umowa umowa, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
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
                        historycznenaliczenienieobecnosc(placePrzList, nowypasek, rodzajnieobecnoscilist, nieobecnoscFacade, umowa, swiadczeniekodzuslist);
                        nowypasek.setBrutto(nowypasek.getBrutto()+nowypasek.getBruttobezzus() + nowypasek.getBruttozus() + nowypasek.getBruttobezzusbezpodatek());
                        zwrot.add(nowypasek);
                    }
                }
            } catch (Exception e){}
        }
        return zwrot;
    }
    Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
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
    
    static void historycznenaliczenienieobecnosc(List<PlacePrz> placePrzList, Pasekwynagrodzen pasekwynagrodzen, List<Rodzajnieobecnosci> rodzajnieobecnoscilist, NieobecnoscFacade nieobecnoscFacade, Umowa umowa, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        for (PlacePrz p : placePrzList) {
            Nieobecnosc nieobecnosc = new Nieobecnosc();
            nieobecnosc.setDataod(Data.data_yyyyMMddNull(p.getPrzDataOd()));
            nieobecnosc.setDatado(Data.data_yyyyMMddNull(p.getPrzDataDo()));
            nieobecnosc.setUmowa(umowa);
            nieobecnosc.setRokod(pasekwynagrodzen.getRok());
            nieobecnosc.setRokdo(pasekwynagrodzen.getRok());
            nieobecnosc.setMcod(pasekwynagrodzen.getMc());
            nieobecnosc.setMcdo(pasekwynagrodzen.getMc());
            nieobecnosc.setRodzajnieobecnosci(histporiapobierzrodzajnieobescnosci(p, rodzajnieobecnoscilist));
            if (p.getPrzWkpSerial()!=null) {
                nieobecnosc.setSwiadczeniekodzus(histporiapobierzswiadczeniekodzus(p, swiadczeniekodzuslist));
            }
            nieobecnoscFacade.create(nieobecnosc);
            Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
            naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
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
     
     private static Rodzajnieobecnosci histporiapobierzrodzajnieobescnosci(PlacePrz s, List<Rodzajnieobecnosci> rodzajnieobecnoscilist) {
        Rodzajnieobecnosci zwrot = null;
        if (rodzajnieobecnoscilist!=null) {
            for (Rodzajnieobecnosci p : rodzajnieobecnoscilist) {
                if (p.getAbsSerial().equals(s.getPrzWkpSerial().getWkpAbsSerial())) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
     
      private static Swiadczeniekodzus histporiapobierzswiadczeniekodzus(PlacePrz s, List<Swiadczeniekodzus> swiadczeniekodzuslist) {
        Swiadczeniekodzus zwrot = null;
        if (swiadczeniekodzuslist!=null) {
            for (Swiadczeniekodzus p : swiadczeniekodzuslist) {
                if (p.getWkp_serial().equals(s.getPrzWkpSerial().getWkpSerial())) {
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
                if (r.getRok().equals(p.getRok())&&r.getMc().equals(p.getMc())) {
                    p.setDefinicjalistaplac(r);
                    break;
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

    static List<Nieobecnosc> pobierznieobecnosci(Osoba osoba, Umowa umowa) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        List<OsobaPrz> osobaPrzList = osoba.getOsobaPrzList();
        for (OsobaPrz r : osobaPrzList) {
            try {
                Nieobecnosc n = new Nieobecnosc(r, umowa);
                if (n.getUmowa()!=null) {
                    zwrot.add(n);
                }
            } catch (Exception e){
                System.out.println("");
            }
        }
        return zwrot;
    }

  
    public static String obliczdata26(String dataurodzenia) {
        LocalDate date = LocalDate.parse(dataurodzenia);
        LocalDate plusYears = date.plusYears(26);
        return plusYears.toString();
    }

    public static void main(String[] arg) {
        String data = "1970-05-11";
        System.out.println(obliczdata26(data));
    }

   
    
    
}
