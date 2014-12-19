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
import embeddablefk.SaldoKonto;
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
public class SaldoAnalitykaView implements Serializable {
    private List<SaldoKonto> listaSaldoKonto;
    private List<SaldoKonto> sumaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;

    public SaldoAnalitykaView() {
        sumaSaldoKonto = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView.getPodatnikWpisu());
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        for (int i = 1; i < przygotowanalista.size()+1; i++) {
            przygotowanalista.get(i-1).setId(i);
        }
        SaldoKonto p = new SaldoKonto();
        for (SaldoKonto r : przygotowanalista) {
            p.setBoWn(Z.z(p.getBoWn()+r.getBoWn()));
            p.setBoMa(Z.z(p.getBoMa()+r.getBoMa()));
            p.setObrotyWn(Z.z(p.getObrotyWn()+r.getObrotyWn()));
            p.setObrotyMa(Z.z(p.getObrotyMa()+r.getObrotyMa()));
            p.setObrotyBoWn(Z.z(p.getObrotyBoWn()+r.getObrotyBoWn()));
            p.setObrotyBoMa(Z.z(p.getObrotyBoMa()+r.getObrotyBoMa()));
            p.setSaldoWn(Z.z(p.getSaldoWn()+r.getSaldoWn()));
            p.setSaldoMa(Z.z(p.getSaldoMa()+r.getSaldoMa()));
        }
        p.setBoWn(Z.z(p.getBoWn()));
        p.setBoMa(Z.z(p.getBoMa()));
        p.setObrotyWn(Z.z(p.getObrotyWn()));
        p.setObrotyMa(Z.z(p.getObrotyMa()));
        p.setObrotyBoWn(Z.z(p.getObrotyBoWn()));
        p.setObrotyBoMa(Z.z(p.getObrotyBoMa()));
        p.setSaldoWn(Z.z(p.getSaldoWn()));
        p.setSaldoMa(Z.z(p.getSaldoMa()));
        sumaSaldoKonto = new ArrayList<>();
        sumaSaldoKonto.add(p);
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
     
    public List<SaldoKonto> getSumaSaldoKonto() {
        return sumaSaldoKonto;
    }

    public void setSumaSaldoKonto(List<SaldoKonto> sumaSaldoKonto) {
        this.sumaSaldoKonto = sumaSaldoKonto;
    }

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
                saldoKonto.setBoWn(Z.z(saldoKonto.getBoWn() + r.getKwotaPLN()));
            } else {
                saldoKonto.setBoMa(Z.z(saldoKonto.getBoMa() + r.getKwotaPLN()));
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyRok = pobierzzapisy(p);
        for (StronaWiersza r : zapisyRok) {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setObrotyWn(Z.z(saldoKonto.getObrotyWn() + r.getKwotaPLN()));
            } else {
                saldoKonto.setObrotyMa(Z.z(saldoKonto.getObrotyMa() + r.getKwotaPLN()));
            }
        }
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() > 0.0 || saldoKonto.getBoWn() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() > 0.0 || saldoKonto.getBoMa() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    private List<StronaWiersza> pobierzzapisy(Konto p) {
        List<String> poprzedniemce = Mce.poprzedniemce(wpisView.getMiesiacWpisu());
        List<StronaWiersza> zapisy = KontaFKBean.pobierzZapisyRokMc(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), stronaWierszaDAO);
        for (String r : poprzedniemce) {
            zapisy.addAll(KontaFKBean.pobierzZapisyRokMc(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), r, stronaWierszaDAO));
        }
        return zapisy;
    }
   
    
    
}
