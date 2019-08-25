/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import embeddable.Mce;
import embeddablefk.SaldoKontoNarastajaco;
import entityfk.Konto;
import entityfk.StronaWiersza;
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
import pdf.PdfKontaNarastajaco;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SaldoAnalitykaNarastajacoView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKontoNarastajaco> listaSaldoKonto;
    private List<SaldoKontoNarastajaco> listaSaldoKontoSelected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private String wybranyRodzajKonta;
    private boolean drukujkategorie;
    private boolean saldaniezerowe;

    public SaldoAnalitykaNarastajacoView() {
         ////E.m(this);
    }
    
    public void init() { //E.m(this);
       try {
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
         if (wybranyRodzajKonta.equals("bilansowe")) {
             for(Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                 if (it.next().getBilansowewynikowe().equals("wynikowe")) {
                     it.remove();
                 }
             }
         } else if (wybranyRodzajKonta.equals("wynikowe")){
             for(Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
                 if (it.next().getBilansowewynikowe().equals("bilansowe")) {
                     it.remove();
                 }
             }
         }
        listaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        przygotowanalistasald(kontaklienta, zapisyBO, zapisyObrotyRozp);
       } catch (Exception e) {
           E.e(e);
       }
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private void przygotowanalistasald(List<Konto> kontaklienta, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp) {
        List<StronaWiersza> zapisyRok = pobierzzapisy();
        Map<String,SaldoKontoNarastajaco> przygotowanalista = new ConcurrentHashMap<>();
        int licznik = 0;
        for (Konto p : kontaklienta) {
            if (p.getPelnynumer().equals("809")) {
            }
            SaldoKontoNarastajaco saldoKonto = new SaldoKontoNarastajaco();
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(),saldoKonto);
        }
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, false);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok, true);
        for (SaldoKontoNarastajaco saldoKontoNarastajaco : przygotowanalista.values()) {
            saldoKontoNarastajaco.sumujBOZapisy();
            saldoKontoNarastajaco.wyliczSaldo();
        }
        listaSaldoKonto.addAll(przygotowanalista.values());
        for (Iterator<SaldoKontoNarastajaco> it = listaSaldoKonto.iterator(); it.hasNext();)  {
            SaldoKontoNarastajaco skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0) {
                it.remove();
            }
        }
    }
     
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<SaldoKontoNarastajaco> getListaSaldoKontoSelected() {
        return listaSaldoKontoSelected;
    }

    public void setListaSaldoKontoSelected(List<SaldoKontoNarastajaco> listaSaldoKontoSelected) {
        this.listaSaldoKontoSelected = listaSaldoKontoSelected;
    }

    public String getWybranyRodzajKonta() {
        return wybranyRodzajKonta;
    }

    public void setWybranyRodzajKonta(String wybranyRodzajKonta) {
        this.wybranyRodzajKonta = wybranyRodzajKonta;
    }

    public List<SaldoKontoNarastajaco> getListaSaldoKonto() {
        return listaSaldoKonto;
    }
     
     public void setListaSaldoKonto(List<SaldoKontoNarastajaco> listaSaldoKonto) {
         this.listaSaldoKonto = listaSaldoKonto;
     }

    public boolean isDrukujkategorie() {
        return drukujkategorie;
    }

    public void setDrukujkategorie(boolean drukujkategorie) {
        this.drukujkategorie = drukujkategorie;
    }

    public boolean isSaldaniezerowe() {
        return saldaniezerowe;
    }

    public void setSaldaniezerowe(boolean saldaniezerowe) {
        this.saldaniezerowe = saldaniezerowe;
    }
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
//</editor-fold>

    private void naniesBOnaKonto(Map<String,SaldoKontoNarastajaco> przygotowanalista, List<StronaWiersza> zapisyBO) {
        for (StronaWiersza r : zapisyBO) {
            SaldoKontoNarastajaco p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getWnma().equals("Wn")) {
                    p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
                } else {
                    p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
                }
            }
        }
    }

    private void naniesZapisyNaKonto(Map<String,SaldoKontoNarastajaco> przygotowanalista, List<StronaWiersza> zapisyRok, boolean obroty0zapisy1) {
        List<String> listamcy = Collections.synchronizedList(new ArrayList<>());
        for (String m : Mce.getMceListS()) {
            if (m.equals(wpisView.getMiesiacNastepny()) && !wpisView.getMiesiacWpisu().equals("12")) {
               break;
            } else {
                listamcy.add(m);
            }
        }  
        if (zapisyRok != null && zapisyRok.size() > 0) {
            for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
                StronaWiersza r = (StronaWiersza) it.next();
                if (obroty0zapisy1 == true && !r.getDokfk().getSeriadokfk().equals("BO")) {
                    nanieskonkretnyzapis(r, przygotowanalista, listamcy);
                } else if (obroty0zapisy1 == false && r.getDokfk().getSeriadokfk().equals("BO")) {
                    nanieskonkretnyzapis(r, przygotowanalista, listamcy);
                }
            }
        }
    }
    
    private void nanieskonkretnyzapis(StronaWiersza r, Map<String, SaldoKontoNarastajaco> przygotowanalista, List<String> listamcy) {
        SaldoKontoNarastajaco p = przygotowanalista.get(r.getKonto().getPelnynumer());
        String mc = r.getWiersz().getDokfk().getMiesiac();
        if (listamcy.contains(mc)) {
            try {
                SaldoKontoNarastajaco.Obrotymca o = p.getObrotymiesiecy().get(mc);
                if (r.getWnma().equals("Wn")) {
                    o.setObrotyWn(o.getObrotyWn() + Z.z(r.getKwotaPLN()));
                } else {
                    o.setObrotyMa(o.getObrotyMa() + Z.z(r.getKwotaPLN()));
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    private boolean czynieBO(StronaWiersza r) {
        boolean zwrot = false;
        if (!r.getDokfk().getSeriadokfk().equals("BO")) {
            zwrot = true;
        } else if (r.getDokfk().getSeriadokfk().equals("BO") && r.getDokfk().getNrkolejnywserii() != 1) {
            zwrot = true;
        }
        return zwrot;
    }

    private List<StronaWiersza> pobierzzapisy() {
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        return zapisy;
    }
   
     public boolean czywyswietlic(String kolumna) {
        String mcWP = wpisView.getMiesiacWpisu().equals("CR") ? "06" : wpisView.getMiesiacWpisu();
        int granica = Mce.getMiesiacToNumber().get(mcWP);
        int mc = Mce.getMiesiacToNumber().get(kolumna);
        if (mc <= granica) {
            return true;
        }
        return false;
    }
    
    public void drukuj(int i, int polowaroku) {
        if (listaSaldoKontoSelected==null) {
            PdfKontaNarastajaco.drukuj(listaSaldoKonto, wpisView, i, 0, polowaroku, drukujkategorie, saldaniezerowe);
        } else {
            PdfKontaNarastajaco.drukuj(listaSaldoKontoSelected, wpisView, i, 0, polowaroku, drukujkategorie, saldaniezerowe);
        }
    }
    
    
}
