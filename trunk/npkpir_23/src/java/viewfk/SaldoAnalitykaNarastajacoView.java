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
import waluty.Z;

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
       List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
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
                saldoKonto.setBoWn(Z.z(saldoKonto.getBoWn() + r.getKwotaPLN()));
            } else {
                saldoKonto.setBoMa(Z.z(saldoKonto.getBoMa() + r.getKwotaPLN()));
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKontoNarastajaco saldoKonto, Konto p) {
        for (String m : Mce.getMceListS()) {
            if (m.equals(wpisView.getMiesiacNastepny())) {
                break;
            } else {
                List<StronaWiersza> zapisyRok = pobierzzapisy(p, m);
                if (zapisyRok != null && zapisyRok.size() > 0) {
                    double obrotyWn = 0.0;
                    double obrotyMa = 0.0;
                    for (StronaWiersza r : zapisyRok) {
                        if (r.getWnma().equals("Wn")) {
                            obrotyWn += Z.z(r.getKwotaPLN());
                        } else {
                            obrotyMa += Z.z(r.getKwotaPLN());
                        }
                    }
                    SaldoKontoNarastajaco.Obrotymca o = new SaldoKontoNarastajaco.Obrotymca();
                    o.setNazwamca(m);
                    o.setObrotyWn(Z.z(obrotyWn));
                    o.setObrotyMa(Z.z(obrotyMa));
                    saldoKonto.getObrotymiesiecy().add(o);
                } else {
                    SaldoKontoNarastajaco.Obrotymca o = new SaldoKontoNarastajaco.Obrotymca();
                    o.setNazwamca(m);
                    o.setObrotyWn(0.0);
                    o.setObrotyMa(0.0);
                    saldoKonto.getObrotymiesiecy().add(o);
                }
            }
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
   
     public boolean czywyswietlic(String kolumna) {
        int granica = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        int mc = Mce.getMiesiacToNumber().get(kolumna);
        if (mc <= granica) {
            return true;
        }
        return false;
    }
    
    
}
