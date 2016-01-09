/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InterpaperImportView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    
    public void importujdok() {
        List<InterpaperXLS> pobranefaktury = ReadXLSFile.getListafaktur();
        for (InterpaperXLS p : pobranefaktury) {
           generowanieDokumentu(p);
        }
    }
    
     public void generowanieDokumentu(InterpaperXLS interpaperXLS) {
        String rodzajdok = interpaperXLS.getVatwaluta() > 0 ? "SZ" : "UPTK";
        Dokfk dokument = stworznowydokument(oblicznumerkolejny(rodzajdok),interpaperXLS, rodzajdok);
        try {
            dokument.setImportowany(true);
            dokDAOfk.dodaj(dokument);
            Msg.msg("Zaksięgowano dokument "+rodzajdok+" o nr własnym"+dokument.getNumerwlasnydokfk());
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
        }
    }
     
      private Dokfk stworznowydokument(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok) {
        Dokfk nd = new Dokfk(rodzajdok, numerkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd, interpaperXLS);
        ustawkontrahenta(nd,interpaperXLS);
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk("usługa transportowa");
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd, rodzajdok);
        ustawtabelenbp(nd, interpaperXLS);
        podepnijEwidencjeVat(nd, interpaperXLS);
        ustawwiersze(nd, interpaperXLS);
        nd.setImportowany(true);
        return nd;
    }
      
    private int oblicznumerkolejny(String rodzajdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), rodzajdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }
    
    private void ustawdaty(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(interpaperXLS.getDatawystawienia());
        String datasprzedazy = formatterX.format(interpaperXLS.getDatasprzedaży());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(datasprzedazy.split("-")[1]);
        nd.setVatR(datasprzedazy.split("-")[0]);
    }
    
    private void ustawkontrahenta(Dokfk nd, InterpaperXLS interpaperXLS) {
        try {
            List<Klienci> k = klienciDAO.findAll();
            Klienci klient = null;
            for (Klienci p : k) {
                if (p.getNpelna().contains(interpaperXLS.getKontrahent()) || p.getNskrocona().contains(interpaperXLS.getKontrahent())) {
                    klient = p;
                    break;
                }
            }
            nd.setKontr(klient);
        } catch (Exception e) {
            
        }
    }
    
    private void ustawnumerwlasny(Dokfk nd, InterpaperXLS interpaperXLS) {
        String numer = interpaperXLS.getNrfaktury();
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawrodzajedok(Dokfk nd, String rodzajdok) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdok, wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+rodzajdok);
        }
    }
    
    private void ustawtabelenbp(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(interpaperXLS.getDatasprzedaży());
        DateTime dzienposzukiwany = new DateTime(datadokumentu);
        boolean znaleziono = false;
        int zabezpieczenie = 0;
        while (!znaleziono && (zabezpieczenie < 365)) {
            dzienposzukiwany = dzienposzukiwany.minusDays(1);
            String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
            Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, interpaperXLS.getWalutaplatnosci());
            if (tabelanbppobrana instanceof Tabelanbp) {
                znaleziono = true;
                nd.setTabelanbp(tabelanbppobrana);
                break;
            }
            zabezpieczenie++;
        }
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty(interpaperXLS.getWalutaplatnosci());
        nd.setWalutadokumentu(w);
    }
    
    private void ustawwiersze(Dokfk nd, InterpaperXLS interpaperXLS) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        nd.getListawierszy().add(przygotujwierszNetto(interpaperXLS, nd));
        if (interpaperXLS.getVatwaluta() != 0) {
            nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd));
        }
    }

    private Wiersz przygotujwierszNetto(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(0, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        Konto kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        strwn.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strma.setKwotaPLN(interpaperXLS.getNettoPLN());
        strma.setKonto(kontonetto);
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, 2);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getVatwaluta(), null);
        strma.setKwotaPLN(interpaperXLS.getVatPLN());
        Konto kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
    private void podepnijEwidencjeVat(Dokfk nd, InterpaperXLS interpaperXLS) {
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    boolean nievatowiec = wpisView.getRodzajopodatkowania().contains("bez VAT");
                    if (!nievatowiec && nd.getRodzajedok().getKategoriadokumentu() != 0) {
                        /*wyswietlamy ewidencje VAT*/
                        List<String> opisewidencji = new ArrayList<>();
                        opisewidencji.addAll(listaEwidencjiVat.pobierzOpisyEwidencji(nd.getRodzajedok().getRodzajtransakcji()));
                        int k = 0;
                        for (String p : opisewidencji) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK();
                            eVatwpisFK.setLp(k++);
                            eVatwpisFK.setEwidencja(evewidencjaDAO.znajdzponazwie(p));
                            if (p.equals("sprzedaż 23%")||p.equals("usługi świad. poza ter.kraju")) {
                                eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLN()));
                                eVatwpisFK.setVat(0.0);
                                eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
                            } else {
                                eVatwpisFK.setNetto(0.0);
                                eVatwpisFK.setVat(0.0);
                                eVatwpisFK.setBrutto(0.0);
                            }
                            eVatwpisFK.setDokfk(nd);
                            eVatwpisFK.setEstawka("op");
                            nd.getEwidencjaVAT().add(eVatwpisFK);
                        }
                } else {
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów! podepnijEwidencjeVat()");
                }
            }
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
