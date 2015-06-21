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
import error.E;
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
public class SaldoAnalitykaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listaSaldoKonto;
    private List<SaldoKonto> listaSaldoKontoSelected;
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
    
    
    public void init() {
       List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
       listaSaldoKonto = przygotowanalistasald(kontaklienta);
    }
    
    public void odswiezsaldoanalityczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<StronaWiersza> zapisyRok = pobierzzapisy();
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        List<StronaWiersza> wierszenieuzupelnione = new ArrayList<>();
        for (Konto p : kontaklienta) {
            if (p.getPelnynumer().equals("809")) {
                System.out.println("stop");
            }
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p , zapisyRok, wierszenieuzupelnione);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        for (int i = 1; i < przygotowanalista.size()+1; i++) {
            przygotowanalista.get(i-1).setId(i);
        }
        sumaSaldoKonto = new ArrayList<>();
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        for (StronaWiersza t : wierszenieuzupelnione) {
            Msg.msg("e", "W tym dokumencie nie ma uzupełnionych kont: "+t.getDokfkS());
        }
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

    public List<SaldoKonto> getListaSaldoKontoSelected() {
        return listaSaldoKontoSelected;
    }

    public void setListaSaldoKontoSelected(List<SaldoKonto> listaSaldoKontoSelected) {
        this.listaSaldoKontoSelected = listaSaldoKontoSelected;
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
            } catch (Exception e) {  E.e(e);
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
        if (listaSaldoKontoSelected == null) {
            PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 0);
        } else {
            PdfKonta.drukuj(listaSaldoKontoSelected, wpisView, i, 0);
        }
    }
    
    
}
