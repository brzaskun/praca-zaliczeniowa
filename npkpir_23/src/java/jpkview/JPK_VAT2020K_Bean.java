/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import data.Data;
import entity.DokSuper;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.JPKvatwersjaEvewidencja;
import entity.Klienci;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import error.E;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.regex.Pattern;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class JPK_VAT2020K_Bean {
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazy(EVatwpis1 ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setTypDokumentu(pobierztypdokumentu(ev));
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(Data.dataoddo(ev.getDok().getDataSprz()));
            w.setDataWystawienia(Data.dataoddo(ev.getDok().getDataWyst()));
            w.setKodKrajuNadaniaTIN(kodkraju(ev.getDok().getKontr1()));
            w.setNrKontrahenta(przetworznip(ev.getDok().getKontr1().getNip()));
            w.setNazwaKontrahenta(ev.getDok().getKontr1().getNpelna());
            w.setDowodSprzedazy(ev.getDok().getNrWlDk());
            dodajcechydowierszaSprzedaz(w,ev);
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazy(EVatwpisDedra ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setTypDokumentu(pobierztypdokumentu(ev));
            w.setLpSprzedazy(lp);
            w.setDataSprzedazy(Data.dataoddo(ev.getDataoperacji()));
            w.setDataWystawienia(Data.dataoddo(ev.getDatadokumentu()));
            w.setNrKontrahenta("brak");
            w.setNazwaKontrahenta(ev.getImienazwisko());
            w.setDowodSprzedazy(ev.getFaktura());
            dodajcechydowierszaSprzedaz(w,ev);
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
        } catch (Exception ex) {

        }
        return w;
    }
    
    
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz dodajwierszsprzedazyFK(EVatwpisFK ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz();
        try {
            w.setLpSprzedazy(lp);
            if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setTypDokumentu(pobierztypdokumentu(ev));
                w.setDataSprzedazy(Data.dataoddo(ev.getDataoperacji()));
                w.setDataWystawienia(Data.dataoddo(ev.getDatadokumentu()));
                w.setKodKrajuNadaniaTIN(kodkraju(ev.getDokfk().getKontr()));
                w.setNrKontrahenta(przetworznip(ev.getDokfk().getKontr().getNip()));
                w.setNazwaKontrahenta(ev.getKlient().getNpelna());
                w.setDowodSprzedazy(ev.getNumerwlasnydokfk());
            } else {
                w.setTypDokumentu(pobierztypdokumentu(ev));
                w.setDataSprzedazy(Data.dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWystawienia(Data.dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setKodKrajuNadaniaTIN(kodkraju(ev.getDokfk().getKontr()));
                w.setNrKontrahenta(przetworznip(ev.getDokfk().getKontr().getNip()));
                w.setNazwaKontrahenta(ev.getDokfk().getKontr().getNpelna());
                w.setDowodSprzedazy(ev.getDokfk().getNumerwlasnydokfk());
            }
            dodajkwotydowierszaSprzedazy(w,ev,sprzedazCtrl, jPKvatwersjaEvewidencja);
            dodajcechydowierszaSprzedaz(w,ev);
        } catch (Exception ex) {

        }
        return w;
    }
    
    private static void dodajkwotydowierszaSprzedazy(pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz w, EVatwpisSuper ev, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        try {
            String netto = jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz().replace("_", "");
            String vat = jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz() != null ? jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz().replace("_", "") : null;
            String nettosuma = jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz_suma() != null ? jPKvatwersjaEvewidencja.getPolejpk_netto_sprzedaz_suma().replace("_", "") : null;
            String vatsuma = jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz_suma() != null ? jPKvatwersjaEvewidencja.getPolejpk_vat_sprzedaz_suma().replace("_", "") : null;
            if (netto != null) {
                Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (vat != null) {
                Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (w.getTypDokumentu() == null || !w.getTypDokumentu().value().equals("FP")) {
                if (nettosuma != null) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(nettosuma), BigDecimal.class);
                    method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
                }
                if (vatsuma != null) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpk(vatsuma), BigDecimal.class);
                    method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                    sprzedazCtrl.setPodatekNalezny(sprzedazCtrl.getPodatekNalezny().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
                }
                if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                    sprzedazCtrl.setLiczbaWierszySprzedazy(sprzedazCtrl.getLiczbaWierszySprzedazy().add(BigInteger.ONE));
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static String zwrocpolejpk(String pole) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(pole);
        return sb.toString();
    }
    
     public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpis1 ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDokumentZakupu(pobierzdokumentzakupu(ev));
            w.setDataZakupu(Data.dataoddo(ev.getDok().getDataSprz()));
            w.setDataWplywu(Data.dataoddo(ev.getDok().getDataWyst()));
            w.setNazwaDostawcy(ev.getDok().getKontr1().getNpelna());
            w.setKodKrajuNadaniaTIN(kodkraju(ev.getDok().getKontr1()));
            w.setNrDostawcy(przetworznip(ev.getDok().getKontr1().getNip()));
            w.setDowodZakupu(ev.getDok().getNrWlDk());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl, jPKvatwersjaEvewidencja);
            dodajcechydowierszaZakupu(w,ev);
        } catch (Exception ex) {
            
        }
        return w;
    }
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpisDedra ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            w.setDokumentZakupu(pobierzdokumentzakupu(ev));
            w.setDataZakupu(Data.dataoddo(ev.getDataoperacji()));
            w.setDataWplywu(Data.dataoddo(ev.getDatadokumentu()));
            w.setNazwaDostawcy(ev.getKlient().getNpelna());
            w.setKodKrajuNadaniaTIN(kodkraju(ev.getKlient()));
            w.setNrDostawcy(przetworznip(ev.getKlient().getNip()));
            w.setDowodZakupu(ev.getFaktura());
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl, jPKvatwersjaEvewidencja);
            dodajcechydowierszaZakupu(w,ev);
        } catch (Exception ex) {
            
        }
        return w;
    }
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz dodajwierszzakupu(EVatwpisFK ev, BigInteger lp, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz();
        try {
            w.setLpZakupu(lp);
            if ((ev.getDokfk().getRodzajedok().getKategoriadokumentu()==0 || ev.getDokfk().getRodzajedok().getKategoriadokumentu()==5) && ev.getNumerwlasnydokfk()!=null) {
                w.setDokumentZakupu(pobierzdokumentzakupu(ev));
                w.setDataZakupu(Data.dataoddo(ev.getDataoperacji()));
                w.setDataWplywu(Data.dataoddo(ev.getDatadokumentu()));
                w.setKodKrajuNadaniaTIN(kodkraju(ev.getDokfk().getKontr()));
                w.setNrDostawcy(przetworznip(ev.getDokfk().getKontr().getNip()));
                w.setNazwaDostawcy(ev.getKlient().getNpelna());
                w.setDowodZakupu(ev.getNumerwlasnydokfk());
            } else {
                w.setDokumentZakupu(pobierzdokumentzakupu(ev));
                w.setDataZakupu(Data.dataoddo(ev.getDokfk().getDataoperacji()));
                w.setDataWplywu(Data.dataoddo(ev.getDokfk().getDatawystawienia()));
                w.setNazwaDostawcy(ev.getDokfk().getKontr().getNpelna());
                w.setKodKrajuNadaniaTIN(kodkraju(ev.getDokfk().getKontr()));
                w.setNrDostawcy(przetworznip(ev.getDokfk().getKontr().getNip()));
                w.setDowodZakupu(ev.getDokfk().getNumerwlasnydokfk());
            }
            dodajkwotydowierszaZakupu(w,ev, zakupCtrl, jPKvatwersjaEvewidencja);
            dodajcechydowierszaZakupu(w,ev);
        } catch (Exception ex) {

        }
        return w;
    }
    
    private static void dodajkwotydowierszaZakupu(pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w, EVatwpisSuper ev, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl, JPKvatwersjaEvewidencja jPKvatwersjaEvewidencja) {
        try {
            String netto = jPKvatwersjaEvewidencja.getPolejpk_netto_zakup().replace("_", "");
            String vat = jPKvatwersjaEvewidencja.getPolejpk_vat_zakup() != null ? jPKvatwersjaEvewidencja.getPolejpk_vat_zakup().replace("_", "") : null;
            if (netto != null) {
                Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz.class.getMethod(zwrocpolejpk(netto),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getNetto())).setScale(2, RoundingMode.HALF_EVEN));
            }
            if (vat != null) {
                Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz.class.getMethod(zwrocpolejpk(vat),BigDecimal.class);
                method.invoke(w, BigDecimal.valueOf(Z.z(ev.getVat())));
                zakupCtrl.setPodatekNaliczony(zakupCtrl.getPodatekNaliczony().add(BigDecimal.valueOf(ev.getVat())).setScale(2, RoundingMode.HALF_EVEN));
            }
             if (ev.getNetto() != 0.0 || ev.getVat() != 0.0) {
                zakupCtrl.setLiczbaWierszyZakupow(zakupCtrl.getLiczbaWierszyZakupow().add(BigInteger.ONE));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
     public static String przetworznip(String nip) {
        String dobrynip = nip;
        boolean jestprefix = sprawdznip(nip);
            if (jestprefix) {
                dobrynip = nip.substring(2);
        }
        return dobrynip;
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }

    private static String kodkraju(Klienci p) {
        String zwrot = null;
        if (p.getKrajkod() != null) {
            try {
                zwrot = p.getKrajkod();
                if (zwrot.equals("GR")) {
                    zwrot = "EL";
                }
            } catch (Exception e) {
            }
        } else if (p.getKrajnazwa().equals("Polska")) {
            zwrot = "PL";
        }
        return zwrot;
    }
    
    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Naglowek naglowek(String rok, String mc, String kodurzedu) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Naglowek n = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Naglowek();
        try {
            byte p = 1;
            pl.gov.crd.wzor._2020._05._08._9394.TNaglowek.CelZlozenia cel = new pl.gov.crd.wzor._2020._05._08._9394.TNaglowek.CelZlozenia();
            cel.setValue(p);
            cel.setPoz(cel.getPoz());
            n.setCelZlozenia(cel);
            byte warform = 1;
            n.setWariantFormularza(warform);
            pl.gov.crd.wzor._2020._05._08._9394.TNaglowek.KodFormularza k = new pl.gov.crd.wzor._2020._05._08._9394.TNaglowek.KodFormularza();
            k.setValue(pl.gov.crd.wzor._2020._05._08._9394.TKodFormularza.JPK_VAT);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setDataWytworzeniaJPK(Data.databiezaca());
            n.setRok(Data.XMLGCinitRok(rok));
            n.setMiesiac(Byte.parseByte(mc));
            n.setKodUrzedu(kodurzedu);
            n.setNazwaSystemu("Taxman");
        } catch (Exception ex) {

        }
        return n;
    }

    public static pl.gov.crd.wzor._2020._05._08._9394.JPK.Podmiot1 podmiot1(Podatnik wv, String telefon, String email) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Podmiot1 p = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Podmiot1();
        p.setRola(p.getRola());
        if (wv.getPesel().equals("00000000000")) {
            p.setOsobaNiefizyczna(zrobNiefizyczn(wv, telefon, email));
        } else {
            p.setOsobaFizyczna(zrobFizyczna(wv, telefon, email));
        }
        return p;
    }

    private static pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaFizyczna zrobFizyczna(Podatnik wv, String telefon, String email) {
        pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaFizyczna p = new pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaFizyczna();
        p.setNIP(wv.getNip());
        p.setImiePierwsze(wv.getImie());
        p.setNazwisko(wv.getNazwisko());
        XMLGregorianCalendar dataurodzenia=null;
        try {
            dataurodzenia = Data.dataoddo(wv.getDataurodzenia());
        } catch (DatatypeConfigurationException ex) {
            
        }
        p.setDataUrodzenia(dataurodzenia);
        p.setEmail(email);
        p.setTelefon(telefon);
        return p;
    }

    private static pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaNiefizyczna zrobNiefizyczn(Podatnik wv, String telefon, String email) {
        pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaNiefizyczna p = new pl.gov.crd.wzor._2020._05._08._9394.TPodmiotDowolnyBezAdresu.OsobaNiefizyczna();
        p.setNIP(wv.getNip());
        p.setPelnaNazwa(wv.getPrintnazwa());
        p.setEmail(email);
        p.setTelefon(telefon);
        return p;
    }

    private static void dodajcechydowierszaSprzedaz(pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz w, EVatwpisSuper ev) {
        DokSuper dok = null;
        if (ev instanceof EVatwpisFK) {
            dok = ((EVatwpisFK) ev).getDokfk();
        } else if (ev instanceof EVatwpis1) {
            dok = ((EVatwpis1) ev).getDok();
        }
        if (dok!=null) {
            try {
                if (dok.getOznaczenie1()!=null) {
                    try {
                        Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpkdok(dok.getOznaczenie1().getSymbol()),Byte.class);
                        method.invoke(w, Byte.valueOf("1"));
                    } catch (Exception e){}
                }
                if (dok.getOznaczenie2()!=null) {
                    try {
                        Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpkdok(dok.getOznaczenie2().getSymbol()),Byte.class);
                        method.invoke(w, Byte.valueOf("1"));
                    } catch (Exception e){}
                }
                if (dok.getOznaczenie3()!=null) {
                    try {
                        Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpkdok(dok.getOznaczenie3().getSymbol()),Byte.class);
                        method.invoke(w, Byte.valueOf("1"));
                    } catch (Exception e){}
                }
                if (dok.getOznaczenie4()!=null) {
                    try {
                        Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz.class.getMethod(zwrocpolejpkdok(dok.getOznaczenie4().getSymbol()),Byte.class);
                        method.invoke(w, Byte.valueOf("1"));
                    } catch (Exception e){}
                }
            } catch (Exception e) {
            }
        }
    }
    
    private static pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy pobierztypdokumentu(EVatwpisSuper ev) {
        pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy zwrot = null;
        DokSuper dok = null;
        if (ev instanceof EVatwpisFK) {
            dok = ((EVatwpisFK) ev).getDokfk();
        } else if (ev instanceof EVatwpis1) {
            dok = ((EVatwpis1) ev).getDok();
        }
        if (dok!=null) {
            try {
                if (dok.getOznaczenie1()!=null) {
                    String symbol = dok.getOznaczenie1().getSymbol();
                    zwrot = pobierzoznaczeniesprzedaz(symbol);
                }
                if (dok.getOznaczenie2()!=null && zwrot==null) {
                    String symbol = dok.getOznaczenie2().getSymbol();
                    zwrot = pobierzoznaczeniesprzedaz(symbol);
                }
                if (dok.getOznaczenie3()!=null && zwrot==null) {
                    String symbol = dok.getOznaczenie3().getSymbol();
                    zwrot = pobierzoznaczeniesprzedaz(symbol);
                }
                if (dok.getOznaczenie4()!=null && zwrot==null) {
                    String symbol = dok.getOznaczenie4().getSymbol();
                    zwrot = pobierzoznaczeniesprzedaz(symbol);
                }
                
            } catch (Exception e) {
            }
        }
        return zwrot;
    }
    
    private static pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy pobierzoznaczeniesprzedaz(String symbol) {
        pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy zwrot = null;
        if (symbol.equals("RO")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy.RO;
        } else if (symbol.equals("WEW")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy.WEW;
        } else if (symbol.equals("FP")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduSprzedazy.FP;
        }
        return zwrot;
    }
    
    
    private static pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu pobierzdokumentzakupu(EVatwpisSuper ev) {
        pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu zwrot = null;
        DokSuper dok = null;
        if (ev instanceof EVatwpisFK) {
            dok = ((EVatwpisFK) ev).getDokfk();
        } else if (ev instanceof EVatwpis1) {
            dok = ((EVatwpis1) ev).getDok();
        }
        if (dok!=null) {
            try {
                if (dok.getOznaczenie1()!=null) {
                    String symbol = dok.getOznaczenie1().getSymbol();
                    zwrot = pobierzoznaczeniezakup(symbol);
                }
                if (dok.getOznaczenie2()!=null) {
                    String symbol = dok.getOznaczenie2().getSymbol();
                    zwrot = pobierzoznaczeniezakup(symbol);
                }
                if (dok.getOznaczenie3()!=null) {
                    String symbol = dok.getOznaczenie3().getSymbol();
                    zwrot = pobierzoznaczeniezakup(symbol);
                }
                if (dok.getOznaczenie4()!=null) {
                    String symbol = dok.getOznaczenie4().getSymbol();
                    zwrot = pobierzoznaczeniezakup(symbol);
                }
                
            } catch (Exception e) {
            }
        }
        return zwrot;
    }
    

    private static pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu pobierzoznaczeniezakup(String symbol) {
        pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu zwrot = null;
        if (symbol.equals("MK")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.MK;
        } else if (symbol.equals("WEW")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.WEW;
        } else if (symbol.equals("VAT_RR")) {
            zwrot = pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.VAT_RR;
        }
        return zwrot;
    }
    
    

    private static String zwrocpolejpkdok(String pole) {
        pole = pole.replace("_", "");
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(pole);
        return sb.toString();
    }

    private static void dodajcechydowierszaZakupu(pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w, EVatwpisSuper ev) {
        DokSuper dok = null;
        if (ev instanceof EVatwpisFK) {
            dok = ((EVatwpisFK) ev).getDokfk();
        } else if (ev instanceof EVatwpis1) {
            dok = ((EVatwpis1) ev).getDok();
        }
        if (dok!=null) {
            try {
                if (dok.getOznaczenie1()!=null) {
                    oznaczpolejpkdok(dok.getOznaczenie1().getSymbol(),w);
                }
                if (dok.getOznaczenie2()!=null) {
                    oznaczpolejpkdok(dok.getOznaczenie1().getSymbol(),w);
                }
                if (dok.getOznaczenie3()!=null) {
                    oznaczpolejpkdok(dok.getOznaczenie1().getSymbol(),w);
                                    }
                if (dok.getOznaczenie4()!=null) {
                    oznaczpolejpkdok(dok.getOznaczenie1().getSymbol(),w);
                                    }
            } catch (Exception e) {
            }
        }
    }
        private static void oznaczpolejpkdok(String pole, pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz w) {
            if (pole.equals("MK")) {
                w.setDokumentZakupu(pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.MK);
            }
            if (pole.equals("VAT_RR")) {
                w.setDokumentZakupu(pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.VAT_RR);
            }
            if (pole.equals("WEW")) {
                w.setDokumentZakupu(pl.gov.crd.wzor._2020._05._08._9394.TDowoduZakupu.WEW);
            }
            if (pole.equals("MPP")) {
                w.setMPP(new  Byte("1"));
            }
            if (pole.equals("IMP")) {
                w.setIMP(new  Byte("1"));
            }
        }

    
    

    
    }
