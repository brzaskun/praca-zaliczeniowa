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
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfKonta;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SaldoSyntetykaView implements Serializable {
     private static final long serialVersionUID = 1L;
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

    public SaldoSyntetykaView() {
        sumaSaldoKonto = new ArrayList<>();
    }
    
    
    public void init() {
       List<Konto> kontaanalityczne = kontoDAOfk.findKontaOstAlityka(wpisView);
       kontoDAOfk.zerujkontazLevelu(wpisView,0);
       List<Konto> kontasyntetyczne = kontoDAOfk.findKontazLevelu(wpisView,0);
       listaSaldoKonto = przygotowanalistasald(kontasyntetyczne, kontaanalityczne);
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontasyntetyczne, List<Konto> kontaanalityczne) {
        List<StronaWiersza> zapisyRok = pobierzzapisy();
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        List<SaldoKonto> przygotowanalistasyntetyczne = new ArrayList<>();
        List<StronaWiersza> wierszenieuzupelnione = new ArrayList<>();
        for (Konto p : kontaanalityczne) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p , zapisyRok, wierszenieuzupelnione);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            saldoKonto.setNrpelnymacierzystego(p.getSyntetycznenumer());
            Konto k = p.getSyntetyczne(kontasyntetyczne);
            if (k == null) {
                k = p.getSyntetyczne0(kontasyntetyczne);
            }
            naniesBOnaKontoSyntetyczne(k, saldoKonto);
            naniesZapisyNaKontoSyntetyczne(k, saldoKonto);
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        for (Konto p : kontasyntetyczne) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaSaldo(saldoKonto, p);
            naniesZapisyNaSaldoSyntetyczne(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalistasyntetyczne);
        }
        for (int i = 1; i < przygotowanalista.size()+1; i++) {
            przygotowanalista.get(i-1).setId(i);
        }
        sumaSaldoKonto = new ArrayList<>();
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalistasyntetyczne));
        for (StronaWiersza t : wierszenieuzupelnione) {
            Msg.msg("e", "W tym dokumencie nie ma uzupełnionych kont: "+t.getDokfkS());
        }
        return przygotowanalistasyntetyczne;
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
    
    
    private void naniesBOnaKontoSyntetyczne(Konto k, SaldoKonto saldoKontoanalityka) {
        if (saldoKontoanalityka.getBoWn() > 0.0) {
            k.setBoWn(Z.z(k.getBoWn() + saldoKontoanalityka.getBoWn()));
        } 
        if (saldoKontoanalityka.getBoMa() > 0.0){
            k.setBoMa(Z.z(k.getBoMa() + saldoKontoanalityka.getBoMa()));
        }
    }
    
    private void naniesBOnaSaldo(SaldoKonto saldoKontoanalityka, Konto k) {
        if (k.getBoWn() > 0.0) {
            saldoKontoanalityka.setBoWn(Z.z(saldoKontoanalityka.getBoWn() + k.getBoWn()));
        } 
        if (k.getBoMa() > 0.0){
            saldoKontoanalityka.setBoMa(Z.z(saldoKontoanalityka.getBoMa() + k.getBoMa()));
        }
    }
     
    private void naniesZapisyNaKontoSyntetyczne(Konto k, SaldoKonto saldoKontoanalityka) {
        if (saldoKontoanalityka.getObrotyWn() > 0.0) {
            k.setObrotyWn(Z.z(k.getObrotyWn() + saldoKontoanalityka.getObrotyWn()));
        }
        if (saldoKontoanalityka.getObrotyMa() > 0.0){
            k.setObrotyMa(Z.z(k.getObrotyMa() + saldoKontoanalityka.getObrotyMa()));
        }
    }
    
     private void naniesZapisyNaSaldoSyntetyczne(SaldoKonto saldoKontoanalityka, Konto k) {
        if (k.getObrotyWn() > 0.0) {
            saldoKontoanalityka.setObrotyWn(Z.z(saldoKontoanalityka.getObrotyWn() + k.getObrotyWn()));
        }
        if (k.getObrotyMa() > 0.0){
            saldoKontoanalityka.setObrotyMa(Z.z(saldoKontoanalityka.getObrotyMa() + k.getObrotyMa()));
        }
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok,  List<StronaWiersza> wierszenieuzupelnione) {
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
            StronaWiersza st = (StronaWiersza) it.next();
            if (st.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                it.remove();
            }
        }
        for (StronaWiersza r : zapisyRok) {
            if (r.getKonto() == null) {
                System.out.println(r.toString());
            }
            if (r.getWiersz().getDokfk().getMiesiac()==null) {
                System.out.println(r.toString());
            }
            try {
                if (r.getKonto().equals(p) && Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    if (r.getWnma().equals("Wn")) {
                        saldoKonto.setObrotyWn(Z.z(saldoKonto.getObrotyWn() + r.getKwotaPLN()));
                    } else {
                        saldoKonto.setObrotyMa(Z.z(saldoKonto.getObrotyMa() + r.getKwotaPLN()));
                    }
                    saldoKonto.getZapisy().add(r);
                }
            } catch (Exception e) {  System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString());
                if (wierszenieuzupelnione.size() > 0) {
                    boolean jest = false;
                    for (StronaWiersza t : wierszenieuzupelnione) {
                        if (t.getDokfkS().equals(r.getDokfkS())) {
                            jest = true;
                        }
                    }
                    if (jest==false) {
                        wierszenieuzupelnione.add(r);
                    }
                } else {
                    wierszenieuzupelnione.add(r);
                }
            }
        }
       
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() != 0.0 || saldoKonto.getBoWn() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() != 0.0 || saldoKonto.getBoMa() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    private List<StronaWiersza> pobierzzapisy() {
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        return zapisy;
    }
   
    public void drukuj(int i) {
        PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 0);
    }
    
    public void drukujS(int i) {
        PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 1);
    }
}
