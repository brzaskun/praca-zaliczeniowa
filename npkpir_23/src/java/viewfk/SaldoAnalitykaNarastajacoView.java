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
import embeddable.Mce;
import embeddablefk.SaldoKontoNarastajaco;
import embeddablefk.SaldoKontoNarastajaco;
import entityfk.Konto;
import entityfk.StronaWiersza;
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
public class SaldoAnalitykaNarastajacoView implements Serializable {
    private List<SaldoKontoNarastajaco> listaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;

    public SaldoAnalitykaNarastajacoView() {
    }
    
    @PostConstruct
    public void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView.getPodatnikWpisu());
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    
     private List<SaldoKontoNarastajaco> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKontoNarastajaco> przygotowanalista = new ArrayList<>();
        int licznik = 0;
        for (Konto p : kontaklienta) {
            SaldoKontoNarastajaco saldoKontoNarastajaco = new SaldoKontoNarastajaco();
            saldoKontoNarastajaco.setId(licznik++);
            saldoKontoNarastajaco.setKonto(p);
            naniesBOnaKonto(saldoKontoNarastajaco, p);
            naniesZapisyNaKonto(saldoKontoNarastajaco, p);
            saldoKontoNarastajaco.sumujBOZapisy();
            saldoKontoNarastajaco.wyliczSaldo();
            dodajdolisty(saldoKontoNarastajaco, przygotowanalista);
        }
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
     public List<SaldoKontoNarastajaco> getListaSaldoKonto() {
         return listaSaldoKonto;
     }
     
     public void setListaSaldoKonto(List<SaldoKontoNarastajaco> listaSaldoKonto) {
         this.listaSaldoKonto = listaSaldoKonto;
     }
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKontoNarastajaco saldoKonto, Konto p) {
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
        for (StronaWiersza r : zapisyBO) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setBoWn(saldoKonto.getBoWn() + r.getKwotaPLN());
            } else {
                saldoKonto.setBoMa(saldoKonto.getBoMa() + r.getKwotaPLN());
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKontoNarastajaco saldoKonto, Konto p) {
        for (String m : Mce.getMceListS()) {
            List<StronaWiersza> zapisyRok = pobierzzapisy(p, m);
            double obrotyWn = 0.0;
            double obrotyMa = 0.0;
            for (StronaWiersza r : zapisyRok) {
                if (r.getWnma().equals("Wn")) {
                    obrotyWn += r.getKwotaPLN();
                } else {
                    obrotyMa += r.getKwotaPLN();
                }
            }
            SaldoKontoNarastajaco.Obrotymca o = new SaldoKontoNarastajaco.Obrotymca();
            o.setNazwamca(m);
            o.setObrotyWn(obrotyWn);
            o.setObrotyMa(obrotyMa);
            saldoKonto.getObrotymiesiecy().add(o);
        }
    }

    private void dodajdolisty(SaldoKontoNarastajaco saldoKonto, List<SaldoKontoNarastajaco> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() > 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    private List<StronaWiersza> pobierzzapisy(Konto p, String mc) {
        return KontaFKBean.pobierzZapisyRokMc(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc, stronaWierszaDAO);
    }
   
    
    
}
