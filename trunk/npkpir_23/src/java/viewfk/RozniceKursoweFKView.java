/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdffk.PdfRRK;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozniceKursoweFKView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Transakcja> pobranetransakcje;
    @Inject
    private TransakcjaDAO transakcjaDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public RozniceKursoweFKView() {
        pobranetransakcje = new ArrayList<>();
    }

    
    public void init() {
        pobranetransakcje = transakcjaDAO.findPodatnikRokRozniceKursowe(wpisView);
        RequestContext.getCurrentInstance().update("transakcje");
    }

    public void generowanieDokumentuRRK() {
        int nrkolejny = oblicznumerkolejny();
        if (nrkolejny > 1) {
            usundokumentztegosamegomiesiaca(nrkolejny);
        }
        Dokfk dokumentvat = stworznowydokument(nrkolejny);
        try {
            dokDAOfk.dodaj(dokumentvat);
            Msg.msg("Zaksięgowano dokument RRK");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu RRK");
        }
    }

    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "RRK", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }

    private void usundokumentztegosamegomiesiaca(int numerkolejny) {
        Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "RRK", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            dokDAOfk.destroy(popDokfk);
        }
    }

    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk("RRK", nrkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("zaksięgowanie róznic kursowych za: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd);
        return nd;
    }
    
     private void ustawdaty(Dokfk nd) {
        String datadokumentu = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private void ustawkontrahenta(Dokfk nd) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {  E.e(e);
            
        }
    }

    private void ustawnumerwlasny(Dokfk nd) {
        String numer = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/RRK";
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("RRK", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu RKK");
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }

    private void ustawwiersze(Dokfk nd) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        for (Transakcja p : pobranetransakcje) {
            Wiersz w = new Wiersz(idporzadkowy++, 0);
            uzupelnijwiersz(w, nd);
            String rozliczajacy = p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getSeriadokfk()+"/"+p.getRozliczajacy().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
            String dok = p.getNowaTransakcja().getWiersz() == null ? "BO: "+p.getNowaTransakcja().getOpisBO() : p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getSeriadokfk()+"/"+p.getNowaTransakcja().getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii(); 
            String opiswiersza = "księg. różnic kurs: "+dok+" & "+rozliczajacy+" "+p.getNowaTransakcja().getId()+"/"+p.getRozliczajacy().getId(); 
            w.setOpisWiersza(opiswiersza);
            Konto kontoRozniceKursowe = kontoDAOfk.findKonto("755", wpisView);
            double roznicakursowa = Math.abs(p.getRoznicekursowe());
            if (p.getNowaTransakcja().getWnma().equals("Wn")) {
                if (p.getRoznicekursowe() < 0) {
                    StronaWiersza konto755 = new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe);
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto());
                    w.setStronaWn(konto755);
                    w.setStronaMa(kontoRozrachunku);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                } else {
                    StronaWiersza konto755 = new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe);
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto());
                    w.setStronaWn(kontoRozrachunku);
                    w.setStronaMa(konto755);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                }
                
            } else {
                if (p.getRoznicekursowe() > 0) {
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Ma", roznicakursowa, p.getNowaTransakcja().getKonto());
                    StronaWiersza konto755 = new StronaWiersza(w, "Wn", roznicakursowa, kontoRozniceKursowe);
                    w.setStronaWn(konto755);
                    w.setStronaMa(kontoRozrachunku);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                } else {
                    StronaWiersza kontoRozrachunku = new StronaWiersza(w, "Wn", roznicakursowa, p.getNowaTransakcja().getKonto());
                    StronaWiersza konto755 = new StronaWiersza(w, "Ma", roznicakursowa, kontoRozniceKursowe);
                    w.setStronaWn(kontoRozrachunku);
                    w.setStronaMa(konto755);
                    w.getStronaWn().setKwotaPLN(roznicakursowa);
                    w.getStronaMa().setKwotaPLN(roznicakursowa);
                }
            }
            nd.getListawierszy().add(w);
        }
    }
    
     private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
     
    public void przepiszTransakcjeNowePole() {
        List<Transakcja> lista = transakcjaDAO.findAll();
        for (Transakcja p : lista) {
            p.setKwotawwalucierachunku(p.getKwotatransakcji());
            transakcjaDAO.edit(p);
        }
    }
    
    public void drukowanieRRK() {
        if (!pobranetransakcje.isEmpty()) {
            PdfRRK.drukujRKK(pobranetransakcje, wpisView);
        }
    }

    public List<Transakcja> getPobranetransakcje() {
        return pobranetransakcje;
    }

    public void setPobranetransakcje(List<Transakcja> pobranetransakcje) {
        this.pobranetransakcje = pobranetransakcje;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

}
