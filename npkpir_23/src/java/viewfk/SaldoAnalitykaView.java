/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BOFKBean;
import beansFK.CechazapisuBean;
import beansFK.KontaFKBean;
import dao.StronaWierszaDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.WalutyDAOfk;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import embeddablefk.Sprawozdanie_0;
import entity.Podatnik;
import entityfk.Cechazapisu;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Waluty;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfKonta;
import pdf.PdfKontaPorownanie;
import sortfunction.KontoSortBean;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SaldoAnalitykaView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listaSaldoKonto;
    private List<SaldoKonto> listaSaldoKontowybrane;
    private List<SaldoKonto> listaSaldoKontofilter;
    private List<SaldoKonto> sumaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private String wybranyRodzajKonta;
    private List<Sprawozdanie_0> grupa0;
    private List<StronaWiersza> wybranezpisykonta;
    private boolean tylkosaldaniezerowe;
    private boolean zzapisami;
    private boolean tylkozapisywalutowe;
    private boolean bezslownikowych;
    private String wybranacechadok;
    private List<Cechazapisu> pobranecechypodatnik;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;

    public SaldoAnalitykaView() {
        ////E.m(this);
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        wybranyRodzajKonta = "wszystkie";
    }

    public void init() { //E.m(this);
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
        if (wybranyRodzajKonta != null) {
            if (wybranyRodzajKonta.equals("bilansowe")) {
                for (Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("wynikowe")) {
                        it.remove();
                    }
                }
            } else if (wybranyRodzajKonta.equals("wynikowe")) {
                for (Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("bilansowe")) {
                        it.remove();
                    }
                }
            }
        }
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        przygotowanalistasald(kontaklienta, zapisyBO, zapisyObrotyRozp, wybranyRodzajKonta);
    }
    
     public void initbo() {
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
        if (wybranyRodzajKonta != null) {
            if (wybranyRodzajKonta.equals("bilansowe")) {
                for (Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("wynikowe")) {
                        it.remove();
                    }
                }
            } else if (wybranyRodzajKonta.equals("wynikowe")) {
                for (Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("bilansowe")) {
                        it.remove();
                    }
                }
            }
        }
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<Konto> kontaklientarokpop = kontoDAOfk.findKontaOstAlitykaRokPop(wpisView);
        przygotowanalistasaldbo(kontaklienta, kontaklientarokpop, zapisyBO, zapisyObrotyRozp, wybranyRodzajKonta, wpisView.getRokWpisuSt(),"12");
    }
     
     
    public void initzamknijksiegi(Podatnik podatnik, String rok, String rokuprzedni) {
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(podatnik, Integer.parseInt(rok));
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, podatnik, rok);
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, podatnik, rok);
        List<Konto> kontaklientarokpop = kontoDAOfk.findKontaOstAlitykaRokPop(podatnik, Integer.parseInt(rokuprzedni));
        przygotowanalistasaldbo(kontaklienta, kontaklientarokpop, zapisyBO, zapisyObrotyRozp, "wszystkie", rok, "12");
    }

    public void zmienkryteriawyswietlania() {
        if (tylkosaldaniezerowe) {
            if (listaSaldoKontofilter!=null && !listaSaldoKontofilter.isEmpty()) {
                for (Iterator<SaldoKonto> it = listaSaldoKontofilter.iterator(); it.hasNext();) {
                    SaldoKonto p = it.next();
                    if (p.getSaldoWn() == 0.0 && p.getSaldoMa() == 0.0) {
                        it.remove();
                    }
                }
            } else {
                for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
                    SaldoKonto p = it.next();
                    if (p.getSaldoWn() == 0.0 && p.getSaldoMa() == 0.0) {
                        it.remove();
                    }
                }
            }
        } else {
            PrimeFaces.current().executeScript("PF('tablicasaldaanalityczne').clearFilters();PF('tablicasaldaanalityczne').unselectAllRows();");
            init();
        }
    }
    
    public void zmienkryteriabezslownikowych() {
        if (bezslownikowych) {
            for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
                SaldoKonto p = it.next();
                if (p.getKonto().isSlownikowe()) {
                    it.remove();
                }
            }
        } else {
            init();
        }
    }
    
    

    public void initGenerowanieBO() {
        int rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        wpisView.setRokWpisu(rok - 1);
        wpisView.setRokWpisuSt(String.valueOf(rok - 1));
        wpisView.setMiesiacWpisu("12");
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
        listaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        List<StronaWiersza> zapisyRok = pobierzzapisy("wszystkie", wpisView.getRokWpisuSt(),"12");
//        for (StronaWiersza p : zapisyRok) {
//            if (p.getKonto().getPelnynumer().equals("201-2-19") && p.getKwota() == 123.0) {
//                error.E.s("");
//            }
//        }
        przygotowanalistasaldBO(kontaklienta, zapisyRok);
        Waluty walpln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
            SaldoKonto skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0) {
                it.remove();
            } else {
                skn.setWalutadlabo(walpln);
            }
        }
        wpisView.setRokWpisu(rok);
        wpisView.setRokWpisuSt(String.valueOf(rok));
        wpisView.setMiesiacWpisu(mc);
    }

    public void odswiezsaldoanalityczne() {
        tylkosaldaniezerowe = false;
        wpisView.wpisAktualizuj();
        init();
    }
    
     
    
    public void odswiezsaldoanalitycznebo() {
        tylkosaldaniezerowe = false;
        wpisView.wpisAktualizuj();
        initbo();
    }

    public void przeliczSaldoKonto(int numerwiersza) {
        SaldoKonto p = listaSaldoKonto.get(numerwiersza);
        p.setBoWn(0.0);
        p.setBoMa(0.0);
        p.setObrotyWnMc(0.0);
        p.setObrotyMaMc(0.0);
        p.setObrotyWn(0.0);
        p.setObrotyMa(0.0);
        String mc = wpisView.getMiesiacWpisu();
        for (StronaWiersza r : p.getZapisy()) {
            if (r.getTypStronaWiersza() == 9) {
                if (tylkozapisywalutowe && !r.getSymbolWalutBOiSW().equals("PLN")) {
                    if (r.getWnma().equals("Wn")) {
                        p.setBoWn(Z.z(p.getBoWn() + r.getKwota()));
                    } else {
                        p.setBoMa(Z.z(p.getBoMa() + r.getKwota()));
                    }
                } else if (!tylkozapisywalutowe) {
                    if (r.getWnma().equals("Wn")) {
                        p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
                    } else {
                        p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
                    }
                }
            } else {
                if (tylkozapisywalutowe && !r.getSymbolWalutBOiSW().equals("PLN")) {
                    if (r.getWnma().equals("Wn")) {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + r.getKwota()));
                        }
                        p.setObrotyWn(Z.z(p.getObrotyWn() + r.getKwota()));
                    } else {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + r.getKwota()));
                        }
                        p.setObrotyMa(Z.z(p.getObrotyMa() + r.getKwota()));
                    }
                } else if (!tylkozapisywalutowe) {
                    if (r.getWnma().equals("Wn")) {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + r.getKwotaPLN()));
                        }
                        p.setObrotyWn(Z.z(p.getObrotyWn() + r.getKwotaPLN()));
                    } else {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + r.getKwotaPLN()));
                        }
                        p.setObrotyMa(Z.z(p.getObrotyMa() + r.getKwotaPLN()));
                    }
                }
            }
        }
        p.sumujBOZapisy();
        p.wyliczSaldo();
    }

    public void przygotowanalistasald(List<Konto> kontaklienta, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp, String rodzajkonta) {
        try {
            if (kontaklienta!=null) {
                listaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
                List<StronaWiersza> zapisyRok = pobierzzapisy(rodzajkonta, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                CechazapisuBean.luskaniezapisowZCechami(wybranacechadok, zapisyRok);
                CechazapisuBean.luskaniezapisowZCechami(wybranacechadok, zapisyBO);
                Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
                List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
                kontaklienta.stream().forEach((p) -> {
                    SaldoKonto saldoKonto = new SaldoKonto();
                    saldoKonto.setKonto(p);
                    przygotowanalista.put(p.getPelnynumer(), saldoKonto);
                });
                naniesBOnaKonto(przygotowanalista, zapisyBO);
                naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, wierszenieuzupelnione, false);
                naniesZapisyNaKonto(przygotowanalista, zapisyRok, wierszenieuzupelnione, true);
                przygotowanalista.values().stream().map((s) -> {
                    s.sumujBOZapisy();
                    return s;
                }).forEachOrdered(SaldoKonto::wyliczSaldo);
        //        for (int i = 1; i < przygotowanalista.size()+1; i++) {
        //            przygotowanalista.get(i-1).setId(i);
        //        }
                sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
                sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
                for (StronaWiersza t : wierszenieuzupelnione) {
                    Msg.msg("e", "W tym dokumencie nie ma uzupełnionych kont: " + t.getDokfkS());
                }
                listaSaldoKonto.addAll(przygotowanalista.values());
                for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
                    SaldoKonto skn = it.next();
                    if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0) {
                        it.remove();
                    }
                }
            }
        } catch (Exception e){
            E.e(e);
        }
    }

    private void przygotowanalistasaldBO(List<Konto> kontaklienta, List<StronaWiersza> zapisyRok) {
        Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
        List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
        kontaklienta.stream().map((p) -> {
            if (p.getPelnynumer().equals("201-2-34")) {
            }
            return p;
        }).forEachOrdered((p) -> {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(), saldoKonto);
        });
        resetujBOnaKonto(przygotowanalista);
        naniesZapisyNaKontoBO(przygotowanalista, zapisyRok, wierszenieuzupelnione);
        przygotowanalista.values().stream().map((s) -> {
            s.sumujBOZapisy();
            return s;
        }).forEachOrdered((s) -> {
            s.wyliczSaldo();
        });
//        for (int i = 1; i < przygotowanalista.size()+1; i++) {
//            przygotowanalista.get(i-1).setId(i);
//        }
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        for (StronaWiersza t : wierszenieuzupelnione) {
            Msg.msg("e", "W tym dokumencie nie ma uzupełnionych kont: " + t.getDokfkS());
        }
        listaSaldoKonto.addAll(przygotowanalista.values());
        for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
            SaldoKonto skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0) {
                it.remove();
            }
        }
    }

    public void sumujwybranekonta() {
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        if (listaSaldoKontofilter != null && listaSaldoKontofilter.size() > 0 && (listaSaldoKontowybrane == null || listaSaldoKontowybrane.size() == 0)) {
            sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(listaSaldoKontofilter));
            //Msg.msg("Filter "+listaSaldoKontofilter.size());
        } else if (listaSaldoKontowybrane != null && listaSaldoKontowybrane.size() > 0) {
            sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(listaSaldoKontowybrane));
            //Msg.msg("Wybrane");
        } else {
            sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(listaSaldoKonto));
        }
        PrimeFaces.current().ajax().update("tabelazsumamianalityka:sumy");
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<SaldoKonto> getSumaSaldoKonto() {
        return sumaSaldoKonto;
    }

    public void setSumaSaldoKonto(List<SaldoKonto> sumaSaldoKonto) {
        this.sumaSaldoKonto = sumaSaldoKonto;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public List<StronaWiersza> getWybranezpisykonta() {
        return wybranezpisykonta;
    }

    public void setWybranezpisykonta(List<StronaWiersza> wybranezpisykonta) {
        this.wybranezpisykonta = wybranezpisykonta;
    }

    public boolean isTylkozapisywalutowe() {
        return tylkozapisywalutowe;
    }

    public void setTylkozapisywalutowe(boolean tylkozapisywalutowe) {
        this.tylkozapisywalutowe = tylkozapisywalutowe;
    }

    public boolean isZzapisami() {
        return zzapisami;
    }

    public void setZzapisami(boolean zzapisami) {
        this.zzapisami = zzapisami;
    }

    public boolean isTylkosaldaniezerowe() {
        return tylkosaldaniezerowe;
    }

    public void setTylkosaldaniezerowe(boolean tylkosaldaniezerowe) {
        this.tylkosaldaniezerowe = tylkosaldaniezerowe;
    }

    public List<SaldoKonto> getListaSaldoKontowybrane() {
        return listaSaldoKontowybrane;
    }

    public void setListaSaldoKontowybrane(List<SaldoKonto> listaSaldoKontowybrane) {
        this.listaSaldoKontowybrane = listaSaldoKontowybrane;
    }

    public String getWybranyRodzajKonta() {
        return wybranyRodzajKonta;
    }

    public void setWybranyRodzajKonta(String wybranyRodzajKonta) {
        this.wybranyRodzajKonta = wybranyRodzajKonta;
    }

    public List<SaldoKonto> getListaSaldoKonto() {
        return listaSaldoKonto;
    }

    public List<Sprawozdanie_0> getGrupa0() {
        return grupa0;
    }

    public void setGrupa0(List<Sprawozdanie_0> grupa0) {
        this.grupa0 = grupa0;
    }

    public List<SaldoKonto> getListaSaldoKontofilter() {
        return listaSaldoKontofilter;
    }

    public void setListaSaldoKontofilter(List<SaldoKonto> listaSaldoKontofilter) {
        this.listaSaldoKontofilter = listaSaldoKontofilter;
    }

    public void setListaSaldoKonto(List<SaldoKonto> listaSaldoKonto) {
        this.listaSaldoKonto = listaSaldoKonto;
    }

    public boolean isBezslownikowych() {
        return bezslownikowych;
    }

    public void setBezslownikowych(boolean bezslownikowych) {
        this.bezslownikowych = bezslownikowych;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
//</editor-fold>

    private void resetujBOnaKonto(Map<String, SaldoKonto> przygotowanalista) {
        przygotowanalista.values().stream().filter((r) -> (r != null)).map((r) -> {
            r.setBoWn(0.0);
            return r;
        }).forEachOrdered((r) -> {
            r.setBoMa(0.0);
        });
    }

    private void naniesBOnaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyBO) {
        zapisyBO.stream().forEach((r) -> {
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (tylkozapisywalutowe && !r.getSymbolWalutBOiSW().equals("PLN")) {
                    if (r.getWnma().equals("Wn")) {
                        p.setBoWn(Z.z(p.getBoWn() + r.getKwota()));
                    } else {
                        p.setBoMa(Z.z(p.getBoMa() + r.getKwota()));
                    }
                    p.getZapisy().add(r);
                } else if (!tylkozapisywalutowe) {
                    if (r.getWnma().equals("Wn")) {
                        p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
                    } else {
                        p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
                    }
                    p.getZapisy().add(r);
                }
            }
        });
    }

    private void naniesZapisyNaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyRok, List<StronaWiersza> wierszenieuzupelnione, boolean obroty0zapisy1) {
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        zapisyRok.stream().forEach(r-> {
            if (obroty0zapisy1 == true && !r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione);
                }
            } else if (obroty0zapisy1 == false && r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione);
                }
            }
        });
    }

    private void nanieskonkretnyzapis(StronaWiersza r, Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> wierszenieuzupelnione) {
        try {
            String mc = wpisView.getMiesiacWpisu();
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getKonto().equals(p.getKonto())) {
                    if (tylkozapisywalutowe && !r.getSymbolWalutBOiSW().equals("PLN")) {
                        if (r.getWnma().equals("Wn")) {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + Z.z(r.getKwota())));
                            }
                            p.setObrotyWn(Z.z(p.getObrotyWn() + Z.z(r.getKwota())));
                        } else {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + Z.z(r.getKwota())));
                            }
                            p.setObrotyMa(Z.z(p.getObrotyMa() + Z.z(r.getKwota())));
                        }
                        p.getZapisy().add(r);
                    } else if (!tylkozapisywalutowe) {
                        if (r.getWnma().equals("Wn")) {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + Z.z(r.getKwotaPLN())));
                            }
                            p.setObrotyWn(Z.z(p.getObrotyWn() + Z.z(r.getKwotaPLN())));
                        } else {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + Z.z(r.getKwotaPLN())));
                            }
                            p.setObrotyMa(Z.z(p.getObrotyMa() + Z.z(r.getKwotaPLN())));
                        }
                        p.getZapisy().add(r);
                    }
                }
            }
        } catch (Exception e) {
            if (r.getKonto() == null) {
            }
            if (r.getWiersz().getDokfk().getMiesiac() == null) {
            }
            E.e(e);
            if (wierszenieuzupelnione.size() > 0) {
                boolean jest = false;
                for (StronaWiersza t : wierszenieuzupelnione) {
                    if (t.getDokfkS().equals(r.getDokfkS())) {
                        jest = true;
                    }
                }
                if (jest == false) {
                    wierszenieuzupelnione.add(r);
                }
            } else {
                wierszenieuzupelnione.add(r);
            }
        }
    }

//    private boolean czynieBOnieObroty(StronaWiersza r) {
//        boolean zwrot = true;
//        if (r.getDokfk().getSeriadokfk().equals("BO")) {
//            zwrot = false;
//        } else if (r.getDokfk().getSeriadokfk().equals("BO") && r.getDokfk().getNrkolejnywserii() != 1) {
//            zwrot = false;
//        }
//        return zwrot;
//    }

    //sumuje tylko zapisy w bo
    private void naniesZapisyNaKontoBO(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyRok, List<StronaWiersza> wierszenieuzupelnione) {
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        for (StronaWiersza r : zapisyRok) {
            if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                try {
                    SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
                    if (p != null) {
                        if (r.getKonto().equals(p.getKonto())) {
                            if (r.getWnma().equals("Wn")) {
                                p.setObrotyWn(Z.z(p.getObrotyWn() + r.getKwotaPLN()));
                            } else {
                                p.setObrotyMa(Z.z(p.getObrotyMa() + r.getKwotaPLN()));
                            }
                            p.getZapisy().add(r);
                        }
                    }
                } catch (Exception e) {
                    if (r.getKonto() == null) {
                    }
                    if (r.getWiersz().getDokfk().getMiesiac() == null) {
                    }
                    E.e(e);
                    if (wierszenieuzupelnione.size() > 0) {
                        boolean jest = false;
                        for (StronaWiersza t : wierszenieuzupelnione) {
                            if (t.getDokfkS().equals(r.getDokfkS())) {
                                jest = true;
                            }
                        }
                        if (jest == false) {
                            wierszenieuzupelnione.add(r);
                        }
                    } else {
                        wierszenieuzupelnione.add(r);
                    }
                }
            }
        }

    }

    private List<StronaWiersza> pobierzzapisy(String rodzajkont, String rok, String mc) {
        List<StronaWiersza> zapisyRok = null;
        if (rodzajkont.equals("wszystkie")) {
            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokMcodMcdo(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01",mc);;
        } else if (rodzajkont.equals("bilansowe")) {
            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), rok, mc);
        } else if (rodzajkont.equals("wynikowe")) {
            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokWynik(wpisView.getPodatnikObiekt(), rok, mc);
        }
        return zapisyRok;
    }
    
//    private List<StronaWiersza> pobierzzapisyRO(String rodzajkont) {
//        List<StronaWiersza> zapisyRok = null;
//        if (rodzajkont.equals("wszystkie")) {
//            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//        } else if (rodzajkont.equals("bilansowe")) {
//            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//        } else if (rodzajkont.equals("wynikowe")) {
//            zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(),"12");
//        }
//        return zapisyRok;
//    }

    public void drukuj(int i) {
        if (listaSaldoKontofilter == null && listaSaldoKontowybrane.size() == 0) {
            PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontofilter != null && listaSaldoKontowybrane.size() == 0) {
            PdfKonta.drukuj(listaSaldoKontofilter, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontowybrane.size() > 0) {
            PdfKonta.drukuj(listaSaldoKontowybrane, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
    }
    
     public void drukujporownanie(int i) {
        if (listaSaldoKontofilter == null && listaSaldoKontowybrane.size() == 0) {
            PdfKontaPorownanie.drukuj(listaSaldoKonto, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontofilter != null && listaSaldoKontowybrane.size() == 0) {
            PdfKontaPorownanie.drukuj(listaSaldoKontofilter, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontowybrane.size() > 0) {
            PdfKontaPorownanie.drukuj(listaSaldoKontowybrane, wpisView, i, 0, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
    }

    public int compare(Object o1, Object o2) {
        try {
            return KontoSortBean.sortZaksiegowaneDok((Konto) o1, (Konto) o2);
        } catch (Exception e) {
            E.e(e);
            return 0;
        }
    }

    public List<Sprawozdanie_0> kontagrupy_0() {
        List<Sprawozdanie_0> l = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : listaSaldoKonto) {

        }
        return l;
    }

    public void sprawozdanie() {
        przygotuj_0();
    }
    
    public void zaksiegujsaldakont() {
        try {
            kontoDAOfk.zerujsaldazaksiegowane(wpisView);
            for (SaldoKonto p : listaSaldoKonto) {
                Konto anal = p.getKonto();
                if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                    anal.setZaksiegowane(true);
                    anal.setSaldoWnksiegi(Z.z(p.getSaldoWn()));
                    anal.setSaldoMaksiegi(Z.z(p.getSaldoMa()));
                    kontoDAOfk.edit(anal);
                    if (anal.getKontomacierzyste() != null && !anal.isMapotomkow()) {
                        obsluzmacierzyste(anal, anal.getSaldoWnksiegi(), anal.getSaldoMaksiegi());
                    }
                }
            }
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    private void obsluzmacierzyste(Konto p, double saldoWnksiegi, double saldoMaksiegi) {
        Konto mac = kontoDAOfk.findKonto(p.getKontomacierzyste().getId());
        if (mac != null) {
            mac.setSaldoWnksiegi(Z.z(mac.getSaldoWnksiegi()+saldoWnksiegi));
            mac.setSaldoMaksiegi(Z.z(mac.getSaldoMaksiegi()+saldoMaksiegi));
            mac.setZaksiegowane(true);
            kontoDAOfk.edit(mac);
            if (mac.getKontomacierzyste() != null) {
                obsluzmacierzyste(mac, saldoWnksiegi, saldoMaksiegi);
            }
        }
    }

    private void przygotuj_0() {
        List<SaldoKonto> pobranekonta = pobierzkonta(listaSaldoKonto, "0", 0);
        //grupa0 = generujgrupe0(pobranekonta);
    }

    private List<SaldoKonto> pobierzkonta(List<SaldoKonto> listaSaldoKonto, String string, int i) {
        List<SaldoKonto> l = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().startsWith(string)) {
                l.add(p);
            } else if (Integer.parseInt(p.getKonto().getPelnynumer()) > i) {
                break;
            }
        }
        return l;
    }

//    private List<Sprawozdanie_0> generujgrupe0(List<SaldoKonto> pobranekonta) {
//
//    }
//

    private void przygotowanalistasaldbo(List<Konto> kontaklienta, List<Konto> kontaklientarokpop, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp, String wybranyRodzajKonta, String rok, String mc) {
        List<StronaWiersza> zapisyRok = pobierzzapisy(wybranyRodzajKonta, rok, mc);
        CechazapisuBean.luskaniezapisowZCechami(wybranacechadok, zapisyRok);
        CechazapisuBean.luskaniezapisowZCechami(wybranacechadok, zapisyBO);
        Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
        List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
        kontaklienta.stream().map((p) -> {
            if (p.getPelnynumer().equals("202-1-5")) {
            }
            return p;
        }).forEachOrdered((p) -> {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(), saldoKonto);
        });
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, wierszenieuzupelnione, false);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok, wierszenieuzupelnione, true);
        przygotowanalista.values().stream().map((s) -> {
            s.sumujBOZapisy();
            return s;
        }).forEachOrdered((s) -> {
            s.wyliczSaldo();
        });
        for (SaldoKonto k : przygotowanalista.values()) {
            for (Konto l : kontaklientarokpop) {
                if (k.getKonto().equals(l)) {
                    k.setBoWn(Z.z(l.getSaldoWnksiegi()));
                    k.setBoMa(Z.z(l.getSaldoMaksiegi()));
                    break;
                }
            }
        }
//        for (int i = 1; i < przygotowanalista.size()+1; i++) {
//            przygotowanalista.get(i-1).setId(i);
//        }
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        for (StronaWiersza t : wierszenieuzupelnione) {
            Msg.msg("e", "W tym dokumencie nie ma uzupełnionych kont: " + t.getDokfkS());
        }
        listaSaldoKonto = new ArrayList<>();
        listaSaldoKonto.addAll(przygotowanalista.values());
        for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
            SaldoKonto skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0) {
                it.remove();
            }
        }
    }

   

    
}
