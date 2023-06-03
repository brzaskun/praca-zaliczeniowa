/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.UmowaStareNowecomparator;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.StanowiskopracFacade;
import dao.UmowaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import static data.Data.getDzien;
import entity.Angaz;
import entity.EtatPrac;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Slownikszkolazatrhistoria;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kadryiplace.Osoba;
import kadryiplace.OsobaZlec;
import kadryiplace.ZatrudHist;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class UmowaBean {
    
    public static Umowa umowa;
    
    public static Umowa create() {
        if (umowa ==null) {
            umowa = new Umowa();
            umowa.setAngaz(AngazBean.create());
            umowa.setChorobowe(Boolean.TRUE);
            umowa.setChorobowedobrowolne(Boolean.FALSE);
            umowa.setDatado("2020-12-31");
            umowa.setDataod("2020-12-01");
            umowa.setDataspoleczne("2020-12-01");
            umowa.setDatazawarcia("2020-12-01");
            umowa.setDatazdrowotne("2020-12-01");
            umowa.setEmerytalne(Boolean.TRUE);
            umowa.setCzastrwania("umowa na okres próbny");
            umowa.setKodzawodu(KodzawoduBean.create());
            umowa.setNfz("16R");
            umowa.setNieliczFGSP(Boolean.FALSE);
            umowa.setNieliczFP(Boolean.FALSE);
            umowa.setRentowe(Boolean.TRUE);
            umowa.setUmowakodzus(UmowakodzusBean.create());
            umowa.setWypadkowe(Boolean.TRUE);
            umowa.setZdrowotne(Boolean.TRUE);
        }
        return umowa;
    }
    
     public static Umowa create(int numerumowy, Osoba osoba, Angaz angaz, ZatrudHist r, Slownikszkolazatrhistoria s, Umowakodzus umowakodzus) {
            Umowa umowa = new Umowa();
            umowa.setAngaz(angaz);
            if (umowakodzus.isPraca()) {
                umowa.setNrkolejny("UP/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
            } else if (umowakodzus.isFunkcja()) {
                umowa.setNrkolejny("PF/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
            }
            umowa.setDataod(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setTerminrozpoczeciapracy(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatado(Data.data_yyyyMMddNull(r.getZahDataDo()));
            umowa.setDataspoleczne(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatazawarcia(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatazdrowotne(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setSlownikszkolazatrhistoria(s);
            umowa.setLiczdourlopu(true);
            if (s.getPraca0nauka1()==false) {
                umowa.setPrzyczynawypowiedzenia(r.getZahZwolUwagi());
                umowa.setChorobowe(Boolean.TRUE);
                umowa.setChorobowedobrowolne(Boolean.FALSE);
                umowa.setEmerytalne(Boolean.TRUE);
                umowa.setCzastrwania("umowa importowana");
                umowa.setNfz(osoba.getOsoKasaKod());
                umowa.setNieliczFGSP(Boolean.FALSE);
                umowa.setNieliczFP(Boolean.FALSE);
                umowa.setRentowe(Boolean.TRUE);
                umowa.setWypadkowe(Boolean.TRUE);
                umowa.setZdrowotne(Boolean.TRUE);
                umowa.setLiczdoemerytury(true);
                umowa.setLiczdonagrody(true);
                umowa.setLiczdostazowego(true);
                umowa.setAktywna(r.getZahStatus().equals('H')?false:true);
                osoba.getOsoUrzSerial();
            }
        return umowa;
    }
        
       
    public static Umowa createzlecenie(int numerumowy, Angaz angaz, OsobaZlec r) {
        Umowa umowa = new Umowa();
        umowa.setAngaz(angaz);
        umowa.setNrkolejny(r.getOzlNrUmowy());
        umowa.setDataod(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatado(Data.data_yyyyMMddNull(r.getOzlDataDo()));
        umowa.setDataspoleczne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazawarcia(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazdrowotne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setLiczdourlopu(false);
        umowa.setChorobowe(Boolean.FALSE);
        umowa.setChorobowedobrowolne(r.getOzlOptSerial().getOptZusChor().equals('T'));
        umowa.setEmerytalne(r.getOzlOptSerial().getOptZusEmer().equals('T'));
        umowa.setCzastrwania("umowa zlecenia import");
        umowa.setNfz(null);
        umowa.setOpiszawodu(r.getOzlPraca1());
        umowa.setNieliczFGSP(r.getOzlOptSerial().getOptZusFgsp().equals('N'));
        umowa.setNieliczFP(r.getOzlOptSerial().getOptZusFp().equals('N'));
        umowa.setRentowe(r.getOzlOptSerial().getOptZusRent().equals('T'));
        umowa.setWypadkowe(r.getOzlOptSerial().getOptZusWyp().equals('T'));
        umowa.setZdrowotne(r.getOzlOptSerial().getOptZusZdro().equals('T'));
        return umowa;
    }

    public static String obliczdatepierwszegozasilku(Angaz angaz, Umowa selected) {
        List<Umowa> umowaList = new CopyOnWriteArrayList<Umowa>(angaz.getUmowaList());
        String zwrot = selected.getDataod();
        try {
            if (umowaList == null || umowaList.isEmpty()) {
                zwrot = pokazXXdzien(selected.getDataod(), 30);
            } else {
                Collections.sort(umowaList, new UmowaStareNowecomparator());
                if (czyjestdziesieclatubezpieczenia(umowaList)) {
                    zwrot = selected.getDataod();
                } else {
                    int iledni = 0;
                    for (Iterator<Umowa> it = umowaList.iterator();it.hasNext();) {
                        Umowa u = it.next();
                        if (u.getId() != null && !u.getId().equals(selected.getId()) && u.getDatado()!=null && !u.getDatado().equals("")) {
                            if (u.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                                int wciaguiludnipodjetoprace = Data.iletodniKalendarzowych(u.getDatado(), selected.getDataod());
                                if ( wciaguiludnipodjetoprace < 90) {
                                    zwrot = selected.getDataod();
                                    break;
                                }
                            } else {
                                iledni = iledni+Data.iletodniKalendarzowych(u.getDataod(), u.getDatado());
                            }
                        }
                    }
                    if (selected.isChorobowe() == true && iledni<30) {
                        zwrot = pokazXXdzien(selected.getDataod(), 30-iledni);
                    } else if (selected.isChorobowedobrowolne() == true) {
                        zwrot = pokazXXdzien(selected.getDataod(), 90-iledni);
                    }
                }
            }
        } catch (Exception ef) {
        }

        return zwrot;
    }
    
    private static String pokazXXdzien(String dataod, int iledni) {
        LocalDate dzienzero = LocalDate.parse(dataod);
        LocalDate dzientrzydziesty = dzienzero.plusDays(iledni);
        return dzientrzydziesty.toString();
    }
    
//    public static void main (String[] args) {
//            LocalDate dzienzero = LocalDate.parse("2021-04-05");
//            LocalDate dzientrzydziesty = dzienzero.plusDays(30);
//            String toString = dzientrzydziesty.toString();
//            System.out.println("");
//        }

    private static boolean czyjestdziesieclatubezpieczenia(List<Umowa> umowaList) {
        boolean zwrot = false;
        double sumalat = 0.0;
        for (Umowa u : umowaList) {
            if (u.isChorobowe()) {
                sumalat = sumalat + u.getIloscdnitrwaniaumowy();
                if (sumalat>3650) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }

    
    public static Umowa createpierwsza(Umowa selected, UmowaFacade umowaFacade, EtatPracFacade etatFacade, StanowiskopracFacade stanowiskopracFacade, 
            RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
        if (selected != null && selected.getAngaz() != null) {
            Angaz angaz = selected.getAngaz();
            try {
                
                if (selected.getUmowakodzus().isPraca()) {
                    selected.setLiczdourlopu(true);
                    try {
                        String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(angaz, selected);
                        selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                    } catch (Exception e){}
                }
                selected.setAktywna(true);
                selected.setDatasystem(new Date());
                String dataostatniejumowy = null;
                selected.setLicznikumow(1);
                umowaFacade.create(selected);
                
                if (selected.getUmowakodzus().isPraca() && selected.getEtat1() != null && selected.getEtat2() != null) {
                    EtatPrac etat = new EtatPrac(angaz, selected.getDataod(), selected.getDatado(), selected.getEtat1(), selected.getEtat2());
                    etatFacade.create(etat);
                    EtatBean.edytujkalendarz(etat, kalendarzmiesiacFacade);
                }
                if (selected.getUmowakodzus().isPraca() && selected.getKodzawodu() != null) {
                    Stanowiskoprac stanowisko = new Stanowiskoprac(angaz, selected.getDataod(), selected.getDatado(), selected.getStanowisko());
                    stanowiskopracFacade.create(stanowisko);
                }
                if (selected.getWynagrodzeniemiesieczne() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findZasadniczePraca() : rodzajwynagrodzeniaFacade.findZasadniczeZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia =  dodajskladnikwynagrodzenia(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, "PLN", selected, 1, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
                if (selected.getWynagrodzeniegodzinowe() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinowePraca() : rodzajwynagrodzeniaFacade.findGodzinoweZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzenia(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, "PLN", selected, 2, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
                
                if (selected.getWynagrodzenieoddelegowanie() != 0.0&&selected.getSymbolwalutyoddelegowanie()!=null) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinoweOddelegowaniePraca() : rodzajwynagrodzeniaFacade.findGodzinoweOddelegowanieZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzeniaOddelegowanie(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, selected.getSymbolwalutyoddelegowanie(), selected, 3, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
            }
        }
        return selected;
    }
    
    public static Umowa createpierwszaZlecenie(Umowa selected, UmowaFacade umowaFacade, EtatPracFacade etatFacade, StanowiskopracFacade stanowiskopracFacade, 
            RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade, KalendarzmiesiacFacade kalendarzmiesiacFacade) {
        if (selected != null && selected.getAngaz() != null) {
            Angaz angaz = selected.getAngaz();
            try {
                
                if (selected.getUmowakodzus().isPraca()) {
                    selected.setLiczdourlopu(true);
                    try {
                        String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(angaz, selected);
                        selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                    } catch (Exception e){}
                }
                selected.setAktywna(true);
                selected.setDatasystem(new Date());
                String dataostatniejumowy = null;
                selected.setLicznikumow(1);
                umowaFacade.create(selected);
                
                if (selected.getUmowakodzus().isPraca() && selected.getEtat1() != null && selected.getEtat2() != null) {
                    EtatPrac etat = new EtatPrac(angaz, selected.getDataod(), selected.getDatado(), selected.getEtat1(), selected.getEtat2());
                    etatFacade.create(etat);
                    EtatBean.edytujkalendarz(etat, kalendarzmiesiacFacade);
                }
                if (selected.getUmowakodzus().isPraca() && selected.getKodzawodu() != null) {
                    Stanowiskoprac stanowisko = new Stanowiskoprac(angaz, selected.getDataod(), selected.getDatado(), selected.getStanowisko());
                    stanowiskopracFacade.create(stanowisko);
                }
                if (selected.getWynagrodzeniemiesieczne() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findZasadniczePraca() : rodzajwynagrodzeniaFacade.findZasadniczeZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia =  dodajskladnikwynagrodzenia(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, "PLN", selected, 1, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
                if (selected.getWynagrodzeniegodzinowe() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinowePraca() : rodzajwynagrodzeniaFacade.findGodzinoweZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzenia(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, "PLN", selected, 2, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
                
                if (selected.getWynagrodzenieoddelegowanie() != 0.0&&selected.getSymbolwalutyoddelegowanie()!=null) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinoweOddelegowaniePraca() : rodzajwynagrodzeniaFacade.findGodzinoweOddelegowanieZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzeniaOddelegowanie(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, selected.getSymbolwalutyoddelegowanie(), selected, 3, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
            }
        }
        return selected;
    }
    
    public static Umowa createpierwszaFunkcja(Umowa selected, UmowaFacade umowaFacade, EtatPracFacade etatFacade, StanowiskopracFacade stanowiskopracFacade, RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        if (selected != null && selected.getAngaz() != null) {
            Angaz angaz = selected.getAngaz();
            try {
                
                if (selected.getUmowakodzus().isPraca()) {
                    selected.setLiczdourlopu(true);
                    try {
                        String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(angaz, selected);
                        selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                    } catch (Exception e){}
                }
                selected.setAktywna(true);
                selected.setDatasystem(new Date());
                String dataostatniejumowy = null;
                selected.setLicznikumow(1);
                umowaFacade.create(selected);
                
                if (selected.getWynagrodzeniemiesieczne() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = rodzajwynagrodzeniaFacade.findZasadniczeFunkcja();
                    Skladnikwynagrodzenia skladnikwynagrodzenia =  dodajskladnikwynagrodzenia(rodzajwynagrodzenia, selected, skladnikWynagrodzeniaFacade);
                    Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia, "PLN", selected, 1, zmiennaWynagrodzeniaFacade);
                    if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                        Msg.msg("Dodano składniki wynagrodzania");
                    }
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
            }
        }
        return selected;
    }
    
    private static Skladnikwynagrodzenia dodajskladnikwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia, Umowa selected, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        skladnikwynagrodzenia.setAngaz(selected.getAngaz());
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzenia);
        try {
            skladnikWynagrodzeniaFacade.create(skladnikwynagrodzenia);
        } catch (Exception e) {
        }
        return skladnikwynagrodzenia;
    }
    
    private static Skladnikwynagrodzenia dodajskladnikwynagrodzeniaOddelegowanie(Rodzajwynagrodzenia rodzajwynagrodzenia, Umowa selected, SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade) {
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        skladnikwynagrodzenia.setAngaz(selected.getAngaz());
        skladnikwynagrodzenia.setOddelegowanie(true);
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzenia);
        try {
            skladnikWynagrodzeniaFacade.create(skladnikwynagrodzenia);
        } catch (Exception e) {
        }
        return skladnikwynagrodzenia;
    }

    public static Zmiennawynagrodzenia dodajzmiennawynagrodzenie(Skladnikwynagrodzenia skladnikwynagrodzenia, String waluta, Umowa umowa, int numer, ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade) {
        Zmiennawynagrodzenia zmiennawynagrodzenia = new Zmiennawynagrodzenia();
        if (skladnikwynagrodzenia.getId() != null) {
            zmiennawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            zmiennawynagrodzenia.setWaluta(waluta);
            if (numer==1&&umowa.getWynagrodzeniemiesieczne() != 0.0) {
                zmiennawynagrodzenia.setNazwa("miesięczne");
                zmiennawynagrodzenia.setKwota(umowa.getWynagrodzeniemiesieczne());
            } else if (numer==2&&umowa.getWynagrodzeniegodzinowe() != 0.0) {
                zmiennawynagrodzenia.setNazwa("godzinowe");
                zmiennawynagrodzenia.setKwota(umowa.getWynagrodzeniegodzinowe());
            } else if (numer==3&&umowa.getWynagrodzenieoddelegowanie() != 0.0) {
                zmiennawynagrodzenia.setNazwa("oddel.");
                zmiennawynagrodzenia.setKwota(umowa.getWynagrodzenieoddelegowanie());
            }
            zmiennawynagrodzenia.setDataod(umowa.getDataod());
            zmiennawynagrodzenia.setDatado(umowa.getDatado());
        }
        try {
            zmiennaWynagrodzeniaFacade.create(zmiennawynagrodzenia);
        } catch (Exception e) {
        }
        return zmiennawynagrodzenia;
    }

    public static void main(String[] args) {
        LocalDate dzienzero = LocalDate.parse("2021-01-01");
        LocalDate dzientrzydziesty = dzienzero.plusDays(30);
        String toString = dzientrzydziesty.toString();
        System.out.println(toString);
        LocalDate dzienzakonczenia = LocalDate.parse("2021-01-01");
        LocalDate dzienrozpoczecia = LocalDate.parse("2021-01-02");
        double between = DAYS.between(dzienzakonczenia, dzienrozpoczecia)+1;
        System.out.println(between);
        String dziendo = getDzien("2021-01-02");
        String dzienod = getDzien("2021-01-01");
        int dziendoint = Integer.parseInt(dziendo);
        int dzienodint = Integer.parseInt(dzienod);
        System.out.println(dziendoint-dzienodint+1);
    }

    public static void naniesDatezasilkunaimportowane(List<Angaz> lista, UmowaFacade umowaFacade) {
        for (Angaz angaz : lista) {
            List<Umowa> umowaList = angaz.getUmowaList();
            for (Iterator<Umowa> it = umowaList.iterator();it.hasNext();) {
                Umowa umowa = it.next();
                if (umowa.getUmowakodzus().isPraca()) {
                       umowa.setLiczdourlopu(true);
                       try {
                           String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(angaz, umowa);
                           umowa.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                       } catch (Exception e){
                           System.out.println("");
                       }
                   }
            }
            umowaFacade.editList(umowaList);
        }
    }

 public static void oznaczumowyciaglosc(List<Umowa> listapraca) {
        if (listapraca!=null&&listapraca.size()>1) {
            Collections.sort(listapraca, new UmowaStareNowecomparator());
            int biezacagrupa = 1;
            String datapoprzednia = null;
            for (Umowa u : listapraca) {
                if (datapoprzednia==null) {
                    u.setGrupaumow(biezacagrupa);
                    datapoprzednia = Data.dodajdzien(u.getDatado(),1);
                } else {
                    if (u.getDataod().equals(datapoprzednia)) {
                        u.setGrupaumow(biezacagrupa);
                        datapoprzednia = Data.dodajdzien(u.getDatado(),1);
                    } else {
                        biezacagrupa = biezacagrupa+1;
                        u.setGrupaumow(biezacagrupa);
                        datapoprzednia = Data.dodajdzien(u.getDatado(),1);
                    }
                }
                
            }
        }
    }  
    
}
