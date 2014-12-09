/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import beansFK.KontaFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.WierszBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SaldoAnalitykaView implements Serializable {
    private List<SaldoKonto> listaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;

    public SaldoAnalitykaView() {
    }
    
    @PostConstruct
    private void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView.getPodatnikWpisu());
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        int licznik = 0;
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
     public List<SaldoKonto> getListaSaldoKonto() {
         return listaSaldoKonto;
     }
     
     public void setListaSaldoKonto(List<SaldoKonto> listaSaldoKonto) {
         this.listaSaldoKonto = listaSaldoKonto;
     }
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
        for (StronaWiersza r : zapisyBO) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setBoWn(saldoKonto.getBoWn() + r.getKwotaPLN());
            } else {
                saldoKonto.setBoMa(saldoKonto.getBoMa() + r.getKwotaPLN());
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyRok = KontaFKBean.pobierzZapisyRok(p, wpisView, stronaWierszaDAO);
        for (StronaWiersza r : zapisyRok) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setObrotyWn(saldoKonto.getObrotyWn() + r.getKwotaPLN());
            } else {
                saldoKonto.setObrotyMa(saldoKonto.getObrotyMa() + r.getKwotaPLN());
            }
        }
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
   
    
    
}
