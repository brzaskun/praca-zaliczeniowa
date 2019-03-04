/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.KontaFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RMKDokView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> kontarmk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private double obrotyWn;
    
    
    public void init() {
         E.m(this);
       List<Konto> kontaklienta = kontoDAOfk.findKontaRMK(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
       kontarmk = przygotowanalistasald(kontaklienta);
       RequestContext.getCurrentInstance().update("formrmk");
    }
    
    
    private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = Collections.synchronizedList(new ArrayList<>());
        int licznik = 0;
        for (Konto p : kontaklienta) {
            if (p.getPelnynumer().startsWith("641") || p.getPelnynumer().startsWith("645")) {
                SaldoKonto saldoKonto = new SaldoKonto();
                saldoKonto.setId(licznik++);
                saldoKonto.setKonto(p);
                naniesZapisyNaKonto(saldoKonto, p);
                saldoKonto.sumujBOZapisy();
                saldoKonto.wyliczSaldo();
                dodajdolisty(saldoKonto, przygotowanalista);
            }
        }
        obrotyWn = obliczobrotyWn(przygotowanalista);
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
                            saldoKonto.getZapisy().add(r);
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
    
    private double obliczobrotyWn(List<SaldoKonto> przygotowanalista) {
        double r = 0.0;
        for (SaldoKonto p : przygotowanalista) {
            r += p.getObrotyBoWn();
        }
        return r;
    }
   
    public List<SaldoKonto> getKontarmk() {
        return kontarmk;
    }

    public void setKontarmk(List<SaldoKonto> kontarmk) {
        this.kontarmk = kontarmk;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getObrotyWn() {
        return obrotyWn;
    }

    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }

   

   
  
    
    
}
