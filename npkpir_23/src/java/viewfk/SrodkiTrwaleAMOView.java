/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.KontaFKBean;
import dao.AmoDokDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import embeddable.Mce;
import embeddable.Umorzenie;
import embeddablefk.SaldoKonto;
import entity.Amodok;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SrodkiTrwaleAMOView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> kontasrodkitrw;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
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
    private AmoDokDAO amoDokDAO;
    private double roznicasald;

    public SrodkiTrwaleAMOView() {
         E.m(this);
    }
    
    
    
    @PostConstruct
    public void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaSrodkiTrw(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
       kontasrodkitrw = przygotowanalistasald(kontaklienta);
        RequestContext.getCurrentInstance().update("srodkiamo");
    }
    
    
    private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        int licznik = 0;
        if (kontaklienta != null) {
            for (Konto p : kontaklienta) {
                SaldoKonto saldoKonto = new SaldoKonto();
                saldoKonto.setId(licznik++);
                saldoKonto.setKonto(p);
                naniesZapisyNaKonto(saldoKonto, p);
                saldoKonto.sumujBOZapisy();
                saldoKonto.wyliczSaldo();
                dodajdolisty(saldoKonto, przygotowanalista);
            }
            roznicasald = obliczroznicesald(przygotowanalista);
        } else {
            roznicasald = 0.0;
        }
        return przygotowanalista;
    }
     private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisy = null;
        for (String m : Mce.getMceListS()) {
            if (m.equals(wpisView.getMiesiacNastepny())) {
                break;
            } else {
                zapisy = KontaFKBean.pobierzZapisyVATRokMc(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), m, stronaWierszaDAO);
                if (zapisy != null) {
                    for (StronaWiersza r : zapisy) {
                        if (r.getWnma().equals("Wn")) {
                            double suma = Math.round((saldoKonto.getObrotyWn() + r.getKwotaPLN()) * 100);
                            suma /= 100;
                            saldoKonto.setObrotyWn(suma);
                        } else {
                            double suma = Math.round((saldoKonto.getObrotyMa() + r.getKwotaPLN()) * 100);
                            suma /= 100;
                            saldoKonto.setObrotyMa(suma);
                        }
                    }
                }
            }
        }
    }
    
    private boolean kontonastepnymc(Konto p) {
        if (p.getNazwapelna().contains("następny")) {
            return true;
        }
        return false;
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    private double obliczroznicesald(List<SaldoKonto> przygotowanalista) {
        double r = 0.0;
        for (SaldoKonto p : przygotowanalista) {
            r += p.getSaldoWn();
            r -= p.getSaldoMa();
        }
        return r;
    }

    public void generowanieDokumentuAMO() {
        Amodok amodok = odnajdzdokumorzeniowy(wpisView);
        if (amodok != null && !amodok.getUmorzenia().isEmpty()) {
            usundokumentztegosamegomiesiaca();
            Dokfk dokumentamo = stworznowydokument(oblicznumerkolejny());
            try {
                dokDAOfk.dodaj(dokumentamo);
                Msg.msg("Zaksięgowano dokument AMO");
            } catch (EJBException e) {
                E.e(e);
                Msg.msg("e", "Zaksięgowano już dokument AMO za dany okres");
            } catch (Exception e) { 
                E.e(e);
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu AMO");
            }
        } else {
            Msg.msg("e", "Nie ma odpisów amortyzacyjnych do zaksięgowania w tym miesiącu");
        }
    }

    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "AMO", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getDokfkPK().getNrkolejnywserii() + 1;
    }

    private void usundokumentztegosamegomiesiaca() {
        try {
            Dokfk popDokfk = dokDAOfk.findDokfofaType(wpisView.getPodatnikObiekt(), "AMO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            if (popDokfk != null) {
                dokDAOfk.destroy(popDokfk);
            }
        } catch (Exception e) {  E.e(e);
            
        }
    }

    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk("AMO", nrkolejny, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("odpis umorzeniowy za: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd, odnajdzdokumorzeniowy(wpisView));
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
        String numer = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/AMO";
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("AMO", wpisView.getPodatnikObiekt());
        if (rodzajedok != null) {
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu AMO");
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }
    
    private Amodok odnajdzdokumorzeniowy(WpisView wpisView) {
        try {
           return amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "brak wygenerowanych dokumentów umorzeniowych za bieżących miesiąc");
            return null;
        }
    }
    private void ustawwiersze(Dokfk nd, Amodok amodok) {
        if (amodok != null) {
            nd.setListawierszy(new ArrayList<Wiersz>());
            int idporzadkowy = 1;
            for (Umorzenie p : amodok.getUmorzenia()) {
                Wiersz w = new Wiersz(idporzadkowy++, 0);
                uzupelnijwiersz(w, nd);
                String opiswiersza = "odpis amortyzacyjny dla: "+p.getNazwaSrodka(); 
                w.setOpisWiersza(opiswiersza);
                Konto kontoAmortyzacjaST = kontoDAOfk.findKonto("401-1-1", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                Konto kontoAmortyzacjaWNiP = kontoDAOfk.findKonto("401-2-1", wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                Konto kontoumorzenie = kontoDAOfk.findKonto(p.getKontoumorzenie(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                if (p.getRodzaj().equals("wnip")) {
                    StronaWiersza kosztamortyzacji = new StronaWiersza(w, "Wn", Z.z(p.getKwota().doubleValue()), kontoAmortyzacjaWNiP);
                    StronaWiersza kwotaumorzenia = new StronaWiersza(w, "Ma", Z.z(p.getKwota().doubleValue()),kontoumorzenie);
                    w.setStronaWn(kosztamortyzacji);
                    w.setStronaMa(kwotaumorzenia);
                    nd.getListawierszy().add(w);
                } else {
                    StronaWiersza kosztamortyzacji = new StronaWiersza(w, "Wn", Z.z(p.getKwota().doubleValue()), kontoAmortyzacjaST);
                    StronaWiersza kwotaumorzenia = new StronaWiersza(w, "Ma", Z.z(p.getKwota().doubleValue()),kontoumorzenie);
                    w.setStronaWn(kosztamortyzacji);
                    w.setStronaMa(kwotaumorzenia);
                    nd.getListawierszy().add(w);
                }
            }
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

    public List<SaldoKonto> getKontasrodkitrw() {
        return kontasrodkitrw;
    }

    public void setKontasrodkitrw(List<SaldoKonto> kontasrodkitrw) {
        this.kontasrodkitrw = kontasrodkitrw;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getRoznicasald() {
        return roznicasald;
    }

    public void setRoznicasald(double roznicasald) {
        this.roznicasald = roznicasald;
    }

   

   
  
    
    
}
