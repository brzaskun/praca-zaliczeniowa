    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import beansFK.PlanKontFKBean;
import beansRegon.SzukajDaneBean;
import comparator.Kliencifkcomparator;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.RodzajedokDAO;
import dao.UkladBRDAO;
import embeddable.PanstwaMap;
import entity.Evewidencja;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class ImportBean {
    public static Klienci ustawkontrahenta(String nip, String nazwa, List<Klienci> k, KlienciDAO klienciDAO) {
        //Uwaga zawraca nie null jak nie znajdzie ale new Klieci() (Szukanie po regonie)
        Klienci klient = null;
        try {
            for (Klienci p : k) {
                if (p.getNip().contains(nip.trim())) {
                    klient = p;
                    break;
                }
            }
            if (klient==null) {
                for (Klienci p : k) {
                    if (p.getNpelna().contains(nazwa.trim())) {
                        klient = p;
                        break;
                    }
                }
            }
            if (klient==null) {
                klient = znajdzdaneregonAutomat(nip.trim(), klienciDAO);
            }
            if (klient!=null && klient.getNpelna()!=null && klient.getNip()!=null) {
                k.add(klient);
            }
            naprawkkrajklienta(klient, klienciDAO);
        } catch (Exception e) {
            E.e(e);
        }
        if (klient==null) {
            error.E.s("");
        }
        return klient;
    }
    
    public static Klienci ustawkontrahentaImportJPK(String nip, String nazwa, List<Klienci> k, KlienciDAO klienciDAO) {
        //Uwaga zawraca nie null jak nie znajdzie ale new Klieci() (Szukanie po regonie)
        Klienci klient = null;
        try {
            for (Klienci p : k) {
                if (p.getNip().contains(nip.trim())) {
                    klient = p;
                    break;
                }
            }
            if (klient==null) {
                klient = znajdzdaneregonAutomat(nip.trim(), klienciDAO);
            }
            if (klient!=null && klient.getNpelna()!=null && klient.getNip()!=null) {
                k.add(klient);
            }
            naprawkkrajklienta(klient, klienciDAO);
        } catch (Exception e) {
            E.e(e);
        }
        if (klient==null) {
            error.E.s("");
        }
        return klient;
    }
    
     private static void naprawkkrajklienta(Klienci klient, KlienciDAO klienciDAO) {
        String poczatek = klient.getNip();
        String kraj = null;
        if (poczatek!=null&&poczatek.length()==10) {
            poczatek = poczatek.substring(0,2);
            String regex = "^[a-zA-Z]+$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(poczatek);
            if (m.matches()) {
                kraj = PanstwaMap.getWykazPanstwXS().get(poczatek);
                klient.setKrajnazwa(kraj);
                klient.setKrajkod(poczatek);
                klienciDAO.edit(klient);
            }
        }
    }
    
     public static Klienci znajdzdaneregonAutomat(String nip, KlienciDAO klienciDAO) {
        Klienci zwrot = null;
        try {
            zwrot = SzukajDaneBean.znajdzdaneregonAutomat(nip);
            if (zwrot!=null && zwrot.getNip()!=null) {
                if (!zwrot.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klienciDAO.create(zwrot);
                    Msg.msg("Zaktualizowano dane klienta pobranymi z GUS");
                }
            }
        } catch (Exception e) {
            Msg.msg("e","Błąd, niezaktualizowano dane klienta pobranymi z GUS");
            E.e(e);
        }
        return zwrot;
    }
     
    public static int oblicznumerkolejny(String rodzajdok, DokDAOfk dokDAOfk, Podatnik podatnik, String rok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(podatnik, rodzajdok, rok);
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    public static void ustawnumerwlasny(Dokfk nd, String numer) {
        nd.setNumerwlasnydokfk(numer);
    }
    
    public static void ustawrodzajedok(Dokfk nd, String rodzajdok, RodzajedokDAO rodzajedokDAO, Podatnik podatnik, String rok) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdok, podatnik, rok);
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+rodzajdok);
        }
    }
    
    public static Konto pobierzkontoWn(Klienci klient, KliencifkDAO kliencifkDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        Konto kontoRozrachunkowe = null;
        if (klient.getId() != null) {
            Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt());
            if (klientMaKonto == null) {
                klientMaKonto = new Kliencifk();
                klientMaKonto.setNazwa(klient.getNpelna());
                klientMaKonto.setNip(klient.getNip());
                klientMaKonto.setPodatnik(wpisView.getPodatnikObiekt());
                klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
                kliencifkDAO.create(klientMaKonto);
                List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
                if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                    numerkonta = "203-2-"+klientMaKonto.getNrkonta();
                }
                kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            } else {
                String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
                if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                    numerkonta = "203-2-"+klientMaKonto.getNrkonta();
                }
                kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            }
        }
        return kontoRozrachunkowe;
    }
     
     public static Konto pobierzkontoMa(Klienci klient, KliencifkDAO kliencifkDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatnik(wpisView.getPodatnikObiekt());
            klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
            kliencifkDAO.create(klientMaKonto);
            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null &&!klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        } else {
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        }
        return kontoRozrachunkowe;
    }
     
       private static String pobierznastepnynumer(KliencifkDAO kliencifkDAO, Podatnik podatnik) {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(podatnik);
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size() - 1).getNrkonta()) + 1);
        } catch (Exception e) {
            E.e(e);
            return "1";
        }
    }
       public static void podepnijEwidencjeVat(Dokfk nd, jpkfa2.JPK.Faktura faktura, ListaEwidencjiVat listaEwidencjiVat) {
        Object[] stawka23 = {faktura.getP141()!=null,faktura.getP131(),faktura.getP141(),faktura.getP141()};
        Object[] stawka8 = {faktura.getP142()!=null,faktura.getP132(),faktura.getP142(),faktura.getP142()};
        Object[] stawka5 = {faktura.getP143()!=null,faktura.getP133(),faktura.getP143(),faktura.getP143()};
        Object[] stawkaWDT = {faktura.getP136()!=null,faktura.getP136(),0.0,faktura.getP143()};
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(p);
                    przesuniecie(nd, eVatwpisFK);
                    if (nd.getSeriadokfk().equals("SZ")) {
                        if ((boolean) stawka23[0]) {
                            double netto = stawka23[1]!=null?((BigDecimal)stawka23[1]).doubleValue():0.0;
                            double vat = stawka23[2]!=null?((BigDecimal)stawka23[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 23%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka8[0]) {
                            double netto = stawka8[1]!=null?((BigDecimal)stawka8[1]).doubleValue():0.0;
                            double vat = stawka8[2]!=null?((BigDecimal)stawka8[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 8%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka5[0]) {
                            double netto = stawka5[1]!=null?((BigDecimal)stawka5[1]).doubleValue():0.0;
                            double vat = stawka5[2]!=null?((BigDecimal)stawka5[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 5%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                                break;
                            }
                        }
                    } else if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            double netto = stawkaWDT[1]!=null?((BigDecimal)stawkaWDT[1]).doubleValue():0.0;
                            double vat = 0.0;
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
                            eVatwpisFK.setNetto(Z.z(netto*kurs));
                            eVatwpisFK.setVat(Z.z(vat*kurs));
                            eVatwpisFK.setBrutto(Z.z(eVatwpisFK.getNetto()+eVatwpisFK.getVat()));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                    } else {
                        double netto = faktura.getNetto();
                        double vat = faktura.getVat();
                        if (nd.getSeriadokfk().equals("ZZ") && p.getNazwa().equals("zakup")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("EXP") && p.getNazwa().equals("eksport towarów")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    }
                }
            }
        }
       }
       
       public static void podepnijEwidencjeVat(Dokfk nd, jpkfa3.JPK.Faktura faktura, ListaEwidencjiVat listaEwidencjiVat) {
        Object[] stawka23 = {faktura.getP141()!=null,faktura.getP131(),faktura.getP141(),faktura.getP141W()};
        Object[] stawka8 = {faktura.getP142()!=null,faktura.getP132(),faktura.getP142(),faktura.getP142W()};
        Object[] stawka5 = {faktura.getP143()!=null,faktura.getP133(),faktura.getP143(),faktura.getP143W()};
        Object[] stawkaWDT = {faktura.getP136()!=null,faktura.getP136(),0.0,faktura.getP143W()};
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(p);
                    przesuniecie(nd, eVatwpisFK);
                    if (nd.getSeriadokfk().equals("SZ")) {
                        if ((boolean) stawka23[0]) {
                            double netto = stawka23[1]!=null?((BigDecimal)stawka23[1]).doubleValue():0.0;
                            double vat = stawka23[2]!=null?((BigDecimal)stawka23[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 23%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka8[0]) {
                            double netto = stawka8[1]!=null?((BigDecimal)stawka8[1]).doubleValue():0.0;
                            double vat = stawka8[2]!=null?((BigDecimal)stawka8[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 8%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka5[0]) {
                            double netto = stawka5[1]!=null?((BigDecimal)stawka5[1]).doubleValue():0.0;
                            double vat = stawka5[2]!=null?((BigDecimal)stawka5[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 5%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                                break;
                            }
                        }
                    } else if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            double netto = stawkaWDT[1]!=null?((BigDecimal)stawkaWDT[1]).doubleValue():0.0;
                            double vat = 0.0;
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
                            eVatwpisFK.setNetto(Z.z(netto*kurs));
                            eVatwpisFK.setVat(Z.z(vat*kurs));
                            eVatwpisFK.setBrutto(Z.z(eVatwpisFK.getNetto()+eVatwpisFK.getVat()));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                    } else {
                        double netto = faktura.getNetto();
                        double vat = faktura.getVat();
                        if (nd.getSeriadokfk().equals("ZZ") && p.getNazwa().equals("zakup")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("EXP") && p.getNazwa().equals("eksport towarów")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    }
                }
            }
        }
       }
       //jpk fa 4
       public static void podepnijEwidencjeVat(Dokfk nd, pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura faktura, ListaEwidencjiVat listaEwidencjiVat) {
        Object[] stawka23 = {faktura.getP141()!=null,faktura.getP131(),faktura.getP141(),faktura.getP141W()};
        Object[] stawka8 = {faktura.getP142()!=null,faktura.getP132(),faktura.getP142(),faktura.getP142W()};
        Object[] stawka5 = {faktura.getP143()!=null,faktura.getP133(),faktura.getP143(),faktura.getP143W()};
        Object[] stawkaWDT = {faktura.getP136()!=null,faktura.getP136(),0.0,faktura.getP143W()};
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(p);
                    eVatwpisFK.setRokEw(nd.getVatR());
                    eVatwpisFK.setMcEw(nd.getVatM());
                    przesuniecie(nd, eVatwpisFK);
                    if (nd.getSeriadokfk().equals("SZ")) {
                        if ((boolean) stawka23[0]) {
                            double netto = stawka23[1]!=null?((BigDecimal)stawka23[1]).doubleValue():0.0;
                            double vat = stawka23[2]!=null?((BigDecimal)stawka23[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 23%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka8[0]) {
                            double netto = stawka8[1]!=null?((BigDecimal)stawka8[1]).doubleValue():0.0;
                            double vat = stawka8[2]!=null?((BigDecimal)stawka8[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 8%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                            }
                        }
                        if ((boolean) stawka5[0]) {
                            double netto = stawka5[1]!=null?((BigDecimal)stawka5[1]).doubleValue():0.0;
                            double vat = stawka5[2]!=null?((BigDecimal)stawka5[2]).doubleValue():0.0;
                            if (p.getNazwa().equals("sprzedaż 5%")) {
                                eVatwpisFK.setNettowwalucie(Z.z(netto));
                                eVatwpisFK.setVatwwalucie(Z.z(vat));
                                eVatwpisFK.setNetto(Z.z(netto));
                                eVatwpisFK.setVat(Z.z(vat));
                                eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
                                break;
                            }
                        }
                    } else if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            double netto = stawkaWDT[1]!=null?((BigDecimal)stawkaWDT[1]).doubleValue():0.0;
                            double vat = 0.0;
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
                            eVatwpisFK.setNetto(Z.z(netto*kurs));
                            eVatwpisFK.setVat(Z.z(vat*kurs));
                            eVatwpisFK.setBrutto(Z.z(eVatwpisFK.getNetto()+eVatwpisFK.getVat()));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                    } else {
                        double netto = faktura.getNetto();
                        double vat = faktura.getVat();
                        if (nd.getSeriadokfk().equals("ZZ") && p.getNazwa().equals("zakup")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().startsWith("UPTK") && p.getNazwa().contains("usługi świad. poza ter.kraju")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("EXP") && p.getNazwa().equals("eksport towarów")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    }
                }
            }
        }
       }
       
     public static void podepnijEwidencjeVat(Dokfk nd, double netto, double vat, ListaEwidencjiVat listaEwidencjiVat) {
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(p);
                    przesuniecie(nd, eVatwpisFK);
                    if (Z.z(vat) != 0.0) {
                        if (p.getNazwa().equals("sprzedaż 23%") || p.getNazwa().equals("zakup")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(Z.z(vat));
                            eVatwpisFK.setBrutto(Z.z(Z.z(netto) + Z.z(vat)));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    } else {
                        if (nd.getSeriadokfk().equals("WDT") && p.getNazwa().equals("rejestr WDT")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                        if (nd.getSeriadokfk().equals("EXP") && p.getNazwa().equals("eksport towarów")) {
                            eVatwpisFK.setNettowwalucie(Z.z(netto));
                            eVatwpisFK.setVatwwalucie(Z.z(vat));
                            eVatwpisFK.setNetto(Z.z(netto));
                            eVatwpisFK.setVat(0.0);
                            eVatwpisFK.setBrutto(Z.z(netto));
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                            break;
                        }
                    }
                }
            }
        }
    }
     
     private static void przesuniecie(Dokfk nd, EVatwpisFK eVatwpisFK) {
        if (!nd.getMiesiac().equals(nd.getVatM())) {
            int mcdok = Integer.parseInt(nd.getMiesiac());
            int mdotrzymania =  Integer.parseInt(nd.getVatM());
            int innyokres = mdotrzymania-mcdok;
            eVatwpisFK.setInnyokres(innyokres);
        }
    }

     
        
    public static boolean prawidlowynip (String nip, String kraj) {
        boolean zwrot = false;
        String krajsymbol = PanstwaMap.getWykazPanstwSX().get(kraj);
        String regex = mapapanstwonip.get(krajsymbol);
        if (regex!=null) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(nip);
            if (m.matches()) {
                zwrot = true;
            }
        }
        return zwrot;
    }

    
    private static final Map<String, String> mapapanstwonip;
   
    static {
        mapapanstwonip = new HashMap<>();
        mapapanstwonip.put("AT", "U[0-9]{8}");
        mapapanstwonip.put("BE", "0[0-9]{9}");
        mapapanstwonip.put("BG", "[0-9]{9,10}");
        mapapanstwonip.put("CY", "[0-9]{8}L");
        mapapanstwonip.put("CZ", "[0-9]{8,10}");
        mapapanstwonip.put("DE", "[0-9]{9}");
        mapapanstwonip.put("DK", "[0-9]{8}");
        mapapanstwonip.put("EE", "[0-9]{9}");
        mapapanstwonip.put("EL", "[0-9]{9}");
        mapapanstwonip.put("GR", "[0-9]{9}");
        mapapanstwonip.put("ES", "[0-9A-Z][0-9]{7}[0-9A-Z]");
        mapapanstwonip.put("FI", "[0-9]{8}");
        mapapanstwonip.put("FR", "[0-9A-Z]{2}[0-9]{9}");
        mapapanstwonip.put("GB", "([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})");
        mapapanstwonip.put("HU", "[0-9]{8}");
        mapapanstwonip.put("IE", "[0-9]S[0-9]{5}L");
        mapapanstwonip.put("IT", "[0-9]{11}");
        mapapanstwonip.put("LT", "([0-9]{9}|[0-9]{12})");
        mapapanstwonip.put("LU", "[0-9]{8}");
        mapapanstwonip.put("LV", "[0-9]{11}");
        mapapanstwonip.put("MT", "[0-9]{8}");
        mapapanstwonip.put("NL", "[0-9]{9}B[0-9]{2}");
        mapapanstwonip.put("PL", "[0-9]{10}");
        mapapanstwonip.put("PT", "[0-9]{9}");
        mapapanstwonip.put("RO", "[0-9]{2,10}");
        mapapanstwonip.put("SE", "[0-9]{12}");
        mapapanstwonip.put("SI", "[0-9]{8}");
        mapapanstwonip.put("SK", "[0-9]{10} ");
    }
}
