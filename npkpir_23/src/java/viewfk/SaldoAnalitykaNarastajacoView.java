/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import embeddablefk.SaldoKontoNarastajaco;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdf.PdfKontaNarastajaco;
import view.WpisView;
import waluty.Z;

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
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private String wybranyRodzajKonta;
    private boolean drukujkategorie;
    private boolean saldaniezerowe;

    public SaldoAnalitykaNarastajacoView() {
    }
    
    public void init() {
       listaSaldoKonto = new ArrayList<>();
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
       List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(wierszBODAO, wpisView);
       przygotowanalistasald(kontaklienta, zapisyBO);
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private void przygotowanalistasald(List<Konto> kontaklienta, List<StronaWiersza> zapisyBO) {
        List<StronaWiersza> zapisyRok = pobierzzapisy();
        Map<String,SaldoKontoNarastajaco> przygotowanalista = new HashMap<>();
        int licznik = 0;
        for (Konto p : kontaklienta) {
            if (p.getPelnynumer().equals("809")) {
                System.out.println("stop");
            }
            SaldoKontoNarastajaco saldoKonto = new SaldoKontoNarastajaco();
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(),saldoKonto);
        }
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok);
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

    private void naniesZapisyNaKonto(Map<String,SaldoKontoNarastajaco> przygotowanalista, List<StronaWiersza> zapisyRok) {
        for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
            StronaWiersza st = (StronaWiersza) it.next();
            if (st.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                it.remove();
            }
        }
        List<String> listamcy = new ArrayList<>();
        for (String m : Mce.getMceListS()) {
            if (m.equals(wpisView.getMiesiacNastepny()) && !wpisView.getMiesiacWpisu().equals("12")) {
               break;
            } else {
                listamcy.add(m);
            }
        }  
        if (zapisyRok != null && zapisyRok.size() > 0) {
            for (StronaWiersza r : zapisyRok) {
                SaldoKontoNarastajaco p = przygotowanalista.get(r.getKonto().getPelnynumer());
                if (r.getKonto().getPelnynumer().equals("403-9-21")) {
                    System.out.println("d");
                }
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
        }
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
