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
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.UkladBRDAO;
import embeddable.Panstwa;
import embeddable.PanstwaMap;
import embeddablefk.ImportJPKSprzedaz;
import entity.Evewidencja;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import gus.GUSView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public static Klienci ustawkontrahenta(String nip, String nazwa, List<Klienci> k, GUSView gUSView, KlienciDAO klienciDAO) {
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
                klient = znajdzdaneregonAutomat(nip.trim(), klienciDAO, gUSView);
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
    
    public static Klienci ustawkontrahentaImportJPK(String nip, String nazwa, List<Klienci> k, GUSView gUSView, KlienciDAO klienciDAO) {
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
                klient = znajdzdaneregonAutomat(nip.trim(), klienciDAO, gUSView);
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
    
     public static Klienci znajdzdaneregonAutomat(String nip, KlienciDAO klienciDAO, GUSView gUSView) {
        Klienci zwrot = null;
        try {
            zwrot = SzukajDaneBean.znajdzdaneregonAutomat(nip, gUSView);
            if (zwrot!=null && zwrot.getNip()!=null) {
                if (!zwrot.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klienciDAO.dodaj(zwrot);
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
            Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
            if (klientMaKonto == null) {
                klientMaKonto = new Kliencifk();
                klientMaKonto.setNazwa(klient.getNpelna());
                klientMaKonto.setNip(klient.getNip());
                klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
                klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
                klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
                kliencifkDAO.dodaj(klientMaKonto);
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
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientMaKonto.setNrkonta(pobierznastepnynumer(kliencifkDAO, wpisView.getPodatnikObiekt()));
            kliencifkDAO.dodaj(klientMaKonto);
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
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(podatnik.getNip());
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size() - 1).getNrkonta()) + 1);
        } catch (Exception e) {
            E.e(e);
            return "1";
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

   

   
}
