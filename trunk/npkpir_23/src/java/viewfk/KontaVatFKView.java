/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.BOFKBean;
import beansFK.KontaFKBean;
import com.sun.corba.se.impl.protocol.RequestCanceledException;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
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
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontaVatFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> kontavat;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    boolean dodajBO;
    
    @PostConstruct
    private void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaVAT(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
       kontavat = przygotowanalistasald(kontaklienta);
    }
    
    public void dodajBOdoKont() {
        dodajBO = true;
        init();
        RequestContext.getCurrentInstance().update("form:akorderonbis:saldokontvat");
        Msg.msg("Dodano zapisy z BO");
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
    public List<SaldoKonto> getKontavat() {
         return kontavat;
     }

    public void setKontavat(List<SaldoKonto> kontavat) {
        this.kontavat = kontavat;
    }

     
    public WpisView getWpisView() {
        return wpisView;
    }
     
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
        if (dodajBO) {
            List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
            for (StronaWiersza r : zapisyBO) {
                if (r.getWnma().equals("Wn")) {
                    saldoKonto.setBoWn(saldoKonto.getBoWn() + r.getKwotaPLN());
                } else {
                    saldoKonto.setBoMa(saldoKonto.getBoMa() + r.getKwotaPLN());
                }
            }
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p) {
        List<StronaWiersza> zapisyRok = KontaFKBean.pobierzZapisyRokMc(p, wpisView, stronaWierszaDAO);
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
