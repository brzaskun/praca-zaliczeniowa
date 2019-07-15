/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KlienciDAO;
import daoFK.DokDAOfk;
import data.Data;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.primefaces.component.commandbutton.CommandButton;
import waluty.Z;
import webservice.NIPVATcheck;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkWeryfikacjaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private CommandButton ksiegujbutton;

    public void sprawdzNIPVAT(List<Dokfk> wykazZaksiegowanychDokumentow) {
        for (Iterator<Dokfk> it =  wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
            Dokfk dok = it.next();
            try {
                if (dok.getEwidencjaVAT()!=null && !dok.getEwidencjaVAT().isEmpty()) {
                    if (dok.getKontr().getKrajkod()!=null && dok.getKontr().getKrajkod().equals("PL")) {
                        pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.TWynikWeryfikacjiVAT wynik = NIPVATcheck.sprawdzNIP(dok.getKontr().getNip());
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DokfkWeryfikacjaView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (wynik.getKod().value().equals("C")) {
                            it.remove();
                        }
                        System.out.println("nip "+dok.getKontr().getNip()+" wynik "+wynik.getKomunikat());
                        if (wynik.getKomunikat().contains("nie jest zarejestrowany jako podatnik VAT")) {
                            Msg.msg("e","nip "+dok.getKontr().getNip()+" jest nieaktywny");
                        }
                    } else {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            } catch (Exception e) {
                Msg.msg("e","Wystapił błąd przy dok "+dok.getDokfkSN());
            }
        }
        Msg.msg("Zakończyłem sprawdzanie czy kontrahent jest czynnym VAT-owcem");
    }
    
    public void sprawdzNIPVATPKPiR(List<Dok> wykazZaksiegowanychDokumentow) {
        for (Iterator<Dok> it =  wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
            Dok dok = it.next();
            try {
                if (dok.getRodzajedok().getKategoriadokumentu()==1) {
                    if (dok.getEwidencjaVAT1()!=null && !dok.getEwidencjaVAT1().isEmpty()) {
                        if (dok.getKontr().getKrajkod()!=null && dok.getKontr().getKrajkod().equals("PL")) {
                            pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.TWynikWeryfikacjiVAT wynik = NIPVATcheck.sprawdzNIP(dok.getKontr().getNip());
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(DokfkWeryfikacjaView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if (wynik.getKod().value().equals("C")) {
                                it.remove();
                            }
                            System.out.println("nip "+dok.getKontr().getNip()+" wynik "+wynik.getKomunikat());
                            if (wynik.getKomunikat().contains("nie jest zarejestrowany jako podatnik VAT")) {
                                Msg.msg("e","nip "+dok.getKontr().getNip()+" jest nieaktywny");
                            }
                        } else {
                            it.remove();
                        }
                    } else {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            } catch (Exception e) {
                Msg.msg("e","Wystapił błąd przy dok "+dok.getNrWlDk());
            }
        }
        Msg.msg("Zakończyłem sprawdzanie czy kontrahent jest czynnym VAT-owcem");
    }
    
    public void sprawdzWnMawDokfk(List<Dokfk> wykazZaksiegowanychDokumentow) {
        List<Dokfk> listaRozniceWnMa = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakiKontaAnalityczne = Collections.synchronizedList(new ArrayList<>());
        List<Integer> listabrakiKontaAnalityczne_nr = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabraki = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakkontrahenta = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakiKonto = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakiPozycji = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakivat = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listabrakidaty = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listapustaewidencja = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> listaniezgodnoscvatkonto = Collections.synchronizedList(new ArrayList<>());
        for (Dokfk p : wykazZaksiegowanychDokumentow) {
            boolean jestbrakkontrahenta = brakkontrahenta(p, listabrakkontrahenta);
            boolean skorygowanokontrahenta = korekcjakontrahenta(p);
            boolean usunietopusteewidencje = usunpusteewidencje(p, listapustaewidencja);
            boolean ustawionookresyvat = ustawokresyvat(p);
            boolean zledaty = sprawdzdaty(p,listabrakidaty);
            boolean sprawdzonokontavat = sprawdzkontavat(p, listabrakivat);
            boolean pozostaletrzybraki = sprawdzpozostaletrzybraki(p, listabraki, listabrakiPozycji, listabrakiKontaAnalityczne, listabrakiKontaAnalityczne_nr, listaRozniceWnMa, listabrakiKonto);
            boolean porownanoewidencjakonto = porownajewidencjakonto(p,listaniezgodnoscvatkonto);
        }
        boolean czysto = true;
        String main = "Występują księgowania na sytnetykach w " + listabrakiKontaAnalityczne.size() + " dokumentach: ";
        StringBuilder b = new StringBuilder();
        b.append(main);
        int i = 0;
        for (Dokfk p : listabrakiKontaAnalityczne) {
            b.append(p.toString2());
            b.append(" w.");
            b.append(listabrakiKontaAnalityczne_nr.get(i));
            b.append(", ");
        }
        if (listabrakiKontaAnalityczne.size() > 0) {
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listaRozniceWnMa.size() > 0) {
            main = "Występują różnice w stronach Wn i Ma w PLN w " + listaRozniceWnMa.size() + " dokumentach: ";
            b = pobierzbledy(listaRozniceWnMa, main);
            czysto = false;
            dokDAOfk.editList(listaRozniceWnMa);
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabraki.size() > 0) {
            main = "Występują braki w kolumnie pln w " + listabraki.size() + " dokumentach: ";
            b = new StringBuilder();
            b.append(main);
            for (Dokfk p : listabraki) {
                for (StronaWiersza sw : p.getStronyWierszy()) {
                    String symbol = sw.getSymbolWaluty() != null ? sw.getSymbolWaluty() : sw.getSymbolWalutyBO();
                    if (symbol.equals("PLN")) {
                        sw.setKwotaPLN(sw.getKwota());
                    }
                }
                b.append(p.toString2());
                b.append(", ");
            }
            czysto = false;
            dokDAOfk.editList(listabraki);
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabrakiPozycji.size() > 0) {
            main = "Konta w dokumencie nie maja przyporzadkowania do Pozycji w " + listaRozniceWnMa.size() + " dokumentach: ";
            b = pobierzbledy(listabrakiPozycji, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabrakkontrahenta.size() > 0) {
            main = "Brakuje kontrahenta w " + listabrakkontrahenta.size() + " dokumentach: ";
            b = pobierzbledy(listabrakkontrahenta, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabrakiKonto.size() > 0) {
            main = "Brakuje numeru konta w " + listabrakiKonto.size() + " dokumentach: ";
            b = pobierzbledy(listabrakiKonto, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabrakivat.size() > 0) {
            main = "Niezgodność między miesiącem ewidencji vat a typem konta vat w " + listabrakivat.size() + " dokumentach: ";
            b = pobierzbledy(listabrakivat, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listabrakidaty.size() > 0) {
            main = "Złe daty w następujących w " + listabrakivat.size() + " dokumentach: ";
            b = pobierzbledy(listabrakidaty, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (listapustaewidencja.size() > 0) {
            main = "Puste ewidencje vat w " + listapustaewidencja.size() + " dokumentach: ";
            b = pobierzbledy(listapustaewidencja, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
         if (listaniezgodnoscvatkonto.size() > 0) {
            main = "VAT z ewidencji vat niezgodny z kontem w " + listaniezgodnoscvatkonto.size() + " dokumentach: ";
            b = pobierzbledy(listaniezgodnoscvatkonto, main);
            czysto = false;
            Msg.msg("w", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscisprawdzanie");
        }
        if (czysto) {
            Msg.msg("i", "Nie stwierdzono błędów w dokumentach z listy", "zestawieniedokumentow:wiadomoscsprawdzenie");
        }
        ksiegujbutton.setRendered(true);
    }
    
    private StringBuilder pobierzbledy(List<Dokfk> l, String main) {
        StringBuilder b = new StringBuilder();
        b.append(main);
        for (Dokfk p : l) {
            b.append(p.toString2());
            b.append(", ");
        }
        return b;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    private boolean korekcjakontrahenta(Dokfk p) {
        Klienci klientdlaPK = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
        boolean zwrot = true;
        try {
            if ((p.getRodzajedok().getKategoriadokumentu() == 0 || p.getRodzajedok().getKategoriadokumentu() == 5) && klientdlaPK != null) {
                if (p.getKontr() == null) {
                    p.setKontr(klientdlaPK);
                    dokDAOfk.edit(p);
                } else if (!p.getKontr().equals(klientdlaPK)) {
                    p.setKontr(klientdlaPK);
                    dokDAOfk.edit(p);
                }
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private boolean usunpusteewidencje(Dokfk p, List<Dokfk> listapustaewidencja) {
        boolean zwrot = true;
        try {
            double netto = 0.0;
            double vat = 0.0;
            for (EVatwpisFK ew : p.getEwidencjaVAT()) {
                netto += ew.getNetto();
                vat += ew.getVat();
            }
            netto = Z.z(netto);
            vat = Z.z(vat);
            if (netto == 0.0 && vat == 0.0 && p.getEwidencjaVAT().size() > 0) {
                listapustaewidencja.add(p);
                if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 1) {
                    p.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    dokDAOfk.edit(p);
                }
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private boolean ustawokresyvat(Dokfk p) {
        boolean zwrot = true;
        try {
            for (EVatwpisFK ew : p.getEwidencjaVAT()) {
                if (ew.getMcEw() == null) {
                    if (ew.getInnyokres() == 0) {
                        ew.setMcEw(p.getMiesiac());
                        ew.setRokEw(p.getRok());
                    } else {
                        String[] nowyokres = Mce.zwiekszmiesiac(p.getRok(), p.getMiesiac(), ew.getInnyokres());
                        ew.setRokEw(nowyokres[0]);
                        ew.setMcEw(nowyokres[1]);
                        p.setVatR(nowyokres[0]);
                        p.setVatM(nowyokres[1]);
                    }
                    dokDAOfk.edit(p);
                } else if (ew.getMcEw() != null && ew.getInnyokres() != 0 && p.getMiesiac().equals(p.getVatM())) {
                    String[] nowyokres = Mce.zwiekszmiesiac(p.getRok(), p.getMiesiac(), ew.getInnyokres());
                    ew.setRokEw(nowyokres[0]);
                    ew.setMcEw(nowyokres[1]);
                    p.setVatR(nowyokres[0]);
                    p.setVatM(nowyokres[1]);
                    dokDAOfk.edit(p);
                }
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private boolean sprawdzkontavat(Dokfk p, List<Dokfk> listabrakivat) {
        boolean zwrot = true;
        try {
            String kontown = null;
            String kontoma = null;
            if (p.getListawierszy().size() > 1) {
                for (Wiersz w : p.getListawierszy()) {
                    StronaWiersza swwn = w.getStronaWn();
                    if (swwn != null && (swwn.getKonto().getPelnynumer().equals("221-3") || swwn.getKonto().getPelnynumer().equals("221-4"))) {
                        kontown = swwn.getKonto().getPelnynumer();
                    }
                    StronaWiersza swma = w.getStronaMa();
                    if (swma != null && (swma.getKonto().getPelnynumer().equals("221-1") || swma.getKonto().getPelnynumer().equals("221-2"))) {
                        kontoma = swma.getKonto().getPelnynumer();
                    }
                }
            }
            for (EVatwpisFK ew : p.getEwidencjaVAT()) {
                if (p.getListawierszy().size() > 1 && (p.getRodzajedok().getKategoriadokumentu() == 1 || p.getRodzajedok().getKategoriadokumentu() == 2)) {
                    if (ew.getInnyokres() == 0) {
                        if (ew.getEwidencja().getTypewidencji().equals("z") && kontown != null && !kontown.equals("221-3")) {
                            listabrakivat.add(p);
                        }
                        if (ew.getEwidencja().getTypewidencji().equals("s") && kontoma != null && !kontoma.equals("221-1")) {
                            listabrakivat.add(p);
                        }
                        if (ew.getEwidencja().getTypewidencji().equals("sz") && kontown != null && kontoma != null && !kontown.equals("221-3") && !kontoma.equals("221-1")) {
                            listabrakivat.add(p);
                        }
                    } else {
                        if (ew.getEwidencja().getTypewidencji().equals("z") && kontown != null && !kontown.equals("221-4")) {
                            listabrakivat.add(p);
                        }
                        if (ew.getEwidencja().getTypewidencji().equals("s") && kontoma != null && !kontoma.equals("221-2")) {
                            listabrakivat.add(p);
                        }
                        if (ew.getEwidencja().getTypewidencji().equals("sz") && kontown != null && kontoma != null && !kontown.equals("221-4") && !kontoma.equals("221-2")) {
                            listabrakivat.add(p);
                        }
                    }
                }
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private boolean sprawdzpozostaletrzybraki(Dokfk p, List<Dokfk> listabraki, List<Dokfk> listabrakiPozycji, List<Dokfk> listabrakiKontaAnalityczne, List<Integer> listabrakiKontaAnalityczne_nr, List<Dokfk> listaRozniceWnMa, List<Dokfk> listabrakiKonto) {
        boolean zwrot = true;
        try {
            double sumawn = 0.0;
            double sumama = 0.0;
            boolean jestkontonieostatnieWn = false;
            boolean jestkontonieostatnieMa = false;
            boolean brakkonto = false;
            boolean brakwpln = false;
            boolean brakPozycji = false;
            int liczbawierszy = p.getListawierszy().size();
            if (!p.getSeriadokfk().equals("BO")) {
                for (Wiersz r : p.getListawierszy()) {
                    StronaWiersza wn = r.getStronaWn();
                    StronaWiersza ma = r.getStronaMa();
                    if (wn != null) {
                        if (wn.getKonto() == null) {
                            brakkonto = true;
                        } else if (wn.getKonto().getPozycjaWn() == null && wn.getKonto().getPozycjaMa()==null) {
                            brakPozycji = true;
                        }
                        if (wn.getKonto() != null) {
                            jestkontonieostatnieWn = wn.getKonto().isMapotomkow();
                        }
                        if (wn.getKwota() > 0 && wn.getKwotaPLN() == 0) {
                            brakwpln = true;
                        }
                        if (r.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            if (Z.z(wn.getKwota()) != Z.z(wn.getKwotaPLN())) {
                                brakwpln = true;
                            }
                        }
                        sumawn += wn.getKwotaPLN();
                    }
                    if (ma != null) {
                        if (ma.getKonto() == null) {
                            brakkonto = true;
                        } else if (ma.getKonto().getPozycjaWn() == null && ma.getKonto().getPozycjaMa()==null) {
                            brakPozycji = true;
                        }
                        if (ma.getKonto() != null) {
                            jestkontonieostatnieMa = ma.getKonto().isMapotomkow();
                        }
                        if (ma.getKwota() > 0 && ma.getKwotaPLN() == 0) {
                            brakwpln = true;
                        }
                        if (r.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            if (Z.z(ma.getKwota()) != Z.z(ma.getKwotaPLN())) {
                                brakwpln = true;
                            }
                        }
                        sumama += ma.getKwotaPLN();
                    }
                    if (jestkontonieostatnieWn == true || jestkontonieostatnieMa == true) {
                        if (!listabrakiKontaAnalityczne.contains(p)) {
                            listabrakiKontaAnalityczne.add(p);
                            listabrakiKontaAnalityczne_nr.add(r.getIdporzadkowy());
                        }
                    }
                }
            }
            if (Z.z(sumawn) != Z.z(sumama)) {
                double roznica = Z.z(Z.z(sumawn) - Z.z(sumama));
                listaRozniceWnMa.add(p);
                if (liczbawierszy > 1) {
                    StronaWiersza swWn = p.getListawierszy().get(0).getStronaWn();
                    StronaWiersza swMa = p.getListawierszy().get(0).getStronaMa();
                    String symbol = swWn.getSymbolWaluty() != null ? swWn.getSymbolWaluty() : swWn.getSymbolWalutyBO();
                    if (!symbol.equals("PLN")) {
                        if (roznica > 0) {
                            swMa.setKwotaPLN(swMa.getKwotaPLN() + roznica);
                        } else {
                            swWn.setKwotaPLN(swWn.getKwotaPLN() - roznica);
                        }
                    }
                }
            }
            if (brakkonto == true) {
                listabrakiKonto.add(p);
            }
            if (brakwpln == true) {
                listabraki.add(p);
            }
            if (brakPozycji == true) {
                listabrakiPozycji.add(p);
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private boolean porownajewidencjakonto(Dokfk p, List<Dokfk> listaniezgodnoscvatkonto) {
        boolean zwrot = true;
        try {
            if (p.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
                double procent = p.getRodzajedok().getProcentvat();
                double vatewidencja = podsumujvatwewidencji(p.getEwidencjaVAT());
                boolean czysakwotynakontach = sprawdzkwotynakontach(p.getListawierszy(), vatewidencja, procent);
                if (czysakwotynakontach == false) {
                    listaniezgodnoscvatkonto.add(p);
                }
            } else {
                if (!p.getRodzajedok().getSkrot().equals("VAT")) {
                    double sumazewidencji0 = podsumujvatwewidencji0(p.getEwidencjaVAT());
                    double sumazewidencji1 = podsumujvatwewidencji1(p.getEwidencjaVAT());
                    double sumanakontach0 = podsumujkwotynakontach0(p.getListawierszy());
                    double sumanakontach1 = podsumujkwotynakontach1(p.getListawierszy());
                    if (sumazewidencji0 != sumanakontach0 || sumazewidencji1 != sumanakontach1) {
                        listaniezgodnoscvatkonto.add(p);
                    }
                }
            }
        } catch (Exception e) {
            zwrot = false;
            E.e(e);
        }
        return zwrot;
    }

    private double podsumujvatwewidencji0(List<EVatwpisFK> ewidencjaVAT) {
        double zwrot = 0.0;
        for (EVatwpisFK p : ewidencjaVAT) {
            if (p.getInnyokres() == 0) {
                if (p.getEwidencja().getTypewidencji().equals("sz")) {
                    zwrot += p.getVat() * 2;
                } else {
                    zwrot += p.getVat();
                }
            }
       }
        return Z.z(zwrot);
    }
    
    private double podsumujvatwewidencji1(List<EVatwpisFK> ewidencjaVAT) {
        double zwrot = 0.0;
        for (EVatwpisFK p : ewidencjaVAT) {
            if (p.getInnyokres() != 0) {
                if (p.getEwidencja().getTypewidencji().equals("sz")) {
                    zwrot += p.getVat() * 2;
                } else {
                    zwrot += p.getVat();
                }
            }
       }
        return Z.z(zwrot);
    }
    
       
    
    private double podsumujvatwewidencji(List<EVatwpisFK> ewidencjaVAT) {
        double zwrot = 0.0;
        for (EVatwpisFK p : ewidencjaVAT) {
            zwrot += p.getVat();
        }
        return Z.z(zwrot);
    }
    
    private double podsumujkwotynakontach0(List<Wiersz> listawierszy) {
        double suma = 0.0;
        for (Wiersz t : listawierszy) {
            if (t.getKontoWn().getZwyklerozrachszczegolne().equals("vat")) {
                if (t.getKontoWn().getPelnynumer().equals("221-1") || t.getKontoWn().getPelnynumer().equals("221-3")) {
                    suma += t.getKwotaWnPLN();
                }
            }
            if (t.getKontoMa().getZwyklerozrachszczegolne().equals("vat")) {
                if (t.getKontoWn().getPelnynumer().equals("221-1") || t.getKontoWn().getPelnynumer().equals("221-3")) {
                    suma += t.getKwotaMaPLN();
                }
            }
        }
        return Z.z(suma);
    }
    
    private double podsumujkwotynakontach1(List<Wiersz> listawierszy) {
        double suma = 0.0;
        for (Wiersz t : listawierszy) {
            if (t.getKontoWn().getZwyklerozrachszczegolne().equals("vat")) {
                if (t.getKontoWn().getPelnynumer().equals("221-2") || t.getKontoWn().getPelnynumer().equals("221-4")) {
                    suma += t.getKwotaWnPLN();
                }
            }
            if (t.getKontoMa().getZwyklerozrachszczegolne().equals("vat")) {
                if (t.getKontoWn().getPelnynumer().equals("221-2") || t.getKontoWn().getPelnynumer().equals("221-4")) {
                    suma += t.getKwotaMaPLN();
                }
            }
        }
        return Z.z(suma);
    }

    private boolean sprawdzkwotynakontach(List<Wiersz> listawierszy, double vatewidencja, double procent) {
        boolean zwrot = true;
        Wiersz w2 = listawierszy.get(1);
        Wiersz w3 = listawierszy.size() == 3 ? listawierszy.get(2) : null;
        if (procent != 0.0 && w3 == null) {
            zwrot = false;
        }
        if (w2.getStronaWn() != null) {
            if (w2.getKwotaWnPLN() != vatewidencja) {
                zwrot = false;
            }
        }
        if (w2.getStronaMa() != null) {
            if (w2.getKwotaMaPLN() != vatewidencja) {
                zwrot = false;
            }
        }
        if (w3 != null) {
            if (w3.getStronaWn() != null) {
               if (w3.getKwotaWnPLN() != vatewidencja) {
                   zwrot = false;
               }
            }
            if (w3.getStronaMa() != null) {
                if (w3.getKwotaMaPLN() != vatewidencja) {
                    zwrot = false;
                }
            }   
        }
            return zwrot;
        }

    private boolean brakkontrahenta(Dokfk p, List<Dokfk> listabrakkontrahenta) {
        boolean zwrot = true;
        if (p.getKontr() == null) {
            zwrot = false;
            listabrakkontrahenta.add(p);
        }
        return zwrot;
    }

    private boolean sprawdzdaty(Dokfk p, List<Dokfk> listabrakidaty) {
        boolean zwrot = true;
        if (p.getDatadokumentu() == null || !Data.sprawdzpoprawnoscdaty(p.getDatadokumentu())) {
            zwrot = false;
            listabrakidaty.add(p);
        }
        if (p.getDataoperacji() == null || !Data.sprawdzpoprawnoscdaty(p.getDataoperacji())) {
            zwrot = false;
            listabrakidaty.add(p);
        }
        return zwrot;
    }

    public CommandButton getKsiegujbutton() {
        return ksiegujbutton;
    }

    public void setKsiegujbutton(CommandButton ksiegujbutton) {
        this.ksiegujbutton = ksiegujbutton;
    }

    

}
