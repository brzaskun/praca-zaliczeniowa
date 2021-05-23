/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import beanstesty.UmowaBean;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
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
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.util.ArrayList;
import java.util.List;
import kadryiplace.Okres;
import kadryiplace.Osoba;
import kadryiplace.OsobaPrz;
import kadryiplace.OsobaSkl;
import kadryiplace.Place;
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

    static List<Umowa> pobierzumowy(Osoba osoba, Angaz angaz, List<Slownikszkolazatrhistoria> rodzajezatr, List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia) {
        List<Umowa> zwrot = new ArrayList<>();
        List<ZatrudHist> zatrudHist = osoba.getZatrudHistList();
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
                zwrot.add(nowa);
                nrumowy++;
            } catch (Exception e){}
        }
        return zwrot;
    }

    static Angaz nowyangaz(Pracownik pracownik, FirmaKadry firma) {
        Angaz nowy = new Angaz();
        nowy.setFirma(firma);
        nowy.setPracownik(pracownik);
        nowy.setRodzajwynagrodzenia(1);
        nowy.setCiagloscchorobowe(true);
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
    
    
    static void pobierzskladnikwynagrodzenia(List<OsobaSkl> skladniki, List<Rodzajwynagrodzenia> rodzajewynagrodzenia, Umowa aktywna, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        if (skladniki!=null) {
            for (OsobaSkl s : skladniki) {
                Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
                skladnik.setUmowa(aktywna);
                skladnik.setRodzajwynagrodzenia(pobierzrodzajwynagrodzenia(s,rodzajewynagrodzenia));
                skladnikWynagrodzeniaFacade.create(skladnik);
                pobierzzmiennawynagrodzenia(aktywna, skladnik, s, zmiennaWynagrodzeniaFacade);
            }
        }
    }
    
    private static Rodzajwynagrodzenia pobierzrodzajwynagrodzenia(OsobaSkl s, List<Rodzajwynagrodzenia> rodzajewynagrodzenia) {
        Rodzajwynagrodzenia zwrot = null;
        if (rodzajewynagrodzenia!=null) {
            for (Rodzajwynagrodzenia p : rodzajewynagrodzenia) {
                if (p.getKod().equals(s.getOssWksSerial().getWksKod())) {
                    zwrot = p;
                    break;
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
                            String dataod = t.getSsoRokOd()+"-"+t.getSsoMiesiacOd()+"-01";
                            wiersz.setDataod(dataod);
                            String datado = Data.ostatniDzien(String.valueOf(t.getSsoRokDo()),String.valueOf(t.getSsoMiesiacDo()));
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

    static List<Pasekwynagrodzen> zrobpaski(WpisView wpisView, Osoba osoba, List<Okres> okresList) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        List<Place> placeList = osoba.getPlaceList();
        for (Place r : placeList) {
            try {
                if (okresList.contains(r.getLplOkrSerial())) {
                    Pasekwynagrodzen nowypasek = new Pasekwynagrodzen(r);
                    String rok = String.valueOf(r.getLplOkrSerial().getOkrRokSerial().getRokNumer());
                    String mc = Mce.getNumberToMiesiac().get(Integer.valueOf(r.getLplOkrSerial().getOkrMieNumer()));
                    nowypasek.setRok(rok);
                    nowypasek.setMc(mc);
                    nowypasek.setImportowany(true);
                    zwrot.add(nowypasek);
                }
            } catch (Exception e){}
        }
        return zwrot;
    }

    static List<Pasekwynagrodzen> przyporzadkuj(List<Pasekwynagrodzen> paski, List<Definicjalistaplac> listyplac, List<Kalendarzmiesiac> kalendarze) {
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

    
    
    
}
