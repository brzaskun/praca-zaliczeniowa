/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.RMK;
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
import view.WpisView;

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
    
    public void importujdok() {
        List<InterpaperXLS> pobranefaktury = ReadXLSFile.getListafaktur();
        generowanieDokumentuRMK(pobranefaktury.get(0));
    }
    
     public void generowanieDokumentuRMK(InterpaperXLS interpaperXLS) {
        Dokfk dokumentRMK = stworznowydokument(oblicznumerkolejny(),interpaperXLS);
        try {
            dokDAOfk.dodaj(dokumentRMK);
            Msg.msg("Zaksięgowano dokument RMK");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu RMK");
        }
    }
     
      private Dokfk stworznowydokument(int numerkolejny, InterpaperXLS interpaperXLS) {
        Dokfk nd = new Dokfk("RMK", numerkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd, interpaperXLS);
        ustawkontrahenta(nd,interpaperXLS);
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk("usługa transportowa");
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd, interpaperXLS);
        ustawwiersze(nd, interpaperXLS);
        nd.setImportowany(true);
        return nd;
    }
      
    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "ZZ", wpisView.getRokWpisuSt());
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
    
    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("ZZ", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu ZZ");
        }
    }
    
    private void ustawtabelenbp(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(interpaperXLS.getDatawystawienia());
        Tabelanbp t = tabelanbpDAO.findByDateWaluta(datadokumentu, interpaperXLS.getWalutaplatnosci());
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty(interpaperXLS.getWalutaplatnosci());
        nd.setWalutadokumentu(w);
    }
    
    private void ustawwiersze(Dokfk nd, InterpaperXLS interpaperXLS) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
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
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getNettowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getBruttowaluta(), null);
        strwn.setKwotaPLN(interpaperXLS.getNettoPLN());
        strma.setKwotaPLN(interpaperXLS.getBruttoPLN());
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, 1);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getVatwaluta(), null);
        strwn.setKwotaPLN(interpaperXLS.getVatPLN());
        w.setStronaWn(strwn);
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
}
