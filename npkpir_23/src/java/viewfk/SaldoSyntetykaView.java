/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.KontaFKBean;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdf.PdfKonta;
import sortfunction.KontoSortBean;
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
    private List<SaldoKonto> listaSaldoKontofilter;
    private List<SaldoKonto> listaSaldoKontowybrane;
    private List<SaldoKonto> sumaSaldoKonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private String wybranyRodzajKonta;

    public SaldoSyntetykaView() {
         E.m(this);
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    public void init() {
        try {
            List<Konto> kontaklienta = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 0);
            if (wybranyRodzajKonta != null) {
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
            }
            listaSaldoKonto = przygotowanalistasald(kontaklienta);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void odswiezsaldosyntetyczne() {
         wpisView.wpisAktualizuj();
         init();
    }
    
     private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : kontaklienta) {
            List<StronaWiersza> zapisyRok = pobierzzapisykonto(p);
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesBOnaKonto(saldoKonto, p);
            naniesZapisyNaKonto(saldoKonto, p, zapisyRok);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        }
        for (int i = 1; i < przygotowanalista.size()+1; i++) {
            przygotowanalista.get(i-1).setId(i);
        }
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        return przygotowanalista;
    }

     //<editor-fold defaultstate="collapsed" desc="comment">
     
    public List<SaldoKonto> getSumaSaldoKonto() {
        return sumaSaldoKonto;
    }

    public void setSumaSaldoKonto(List<SaldoKonto> sumaSaldoKonto) {
        this.sumaSaldoKonto = sumaSaldoKonto;
    }

    public String getWybranyRodzajKonta() {
        return wybranyRodzajKonta;
    }

    public void setWybranyRodzajKonta(String wybranyRodzajKonta) {
        this.wybranyRodzajKonta = wybranyRodzajKonta;
    }

    public List<SaldoKonto> getListaSaldoKonto() {
        return listaSaldoKonto;
    }
     
     public void setListaSaldoKonto(List<SaldoKonto> listaSaldoKonto) {
         this.listaSaldoKonto = listaSaldoKonto;
     }

    public List<SaldoKonto> getListaSaldoKontofilter() {
        return listaSaldoKontofilter;
    }

    public void setListaSaldoKontofilter(List<SaldoKonto> listaSaldoKontofilter) {
        this.listaSaldoKontofilter = listaSaldoKontofilter;
    }

    public List<SaldoKonto> getListaSaldoKontowybrane() {
        return listaSaldoKontowybrane;
    }

    public void setListaSaldoKontowybrane(List<SaldoKonto> listaSaldoKontowybrane) {
        this.listaSaldoKontowybrane = listaSaldoKontowybrane;
    }
    
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
//</editor-fold>

    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
        saldoKonto.setBoWn(Z.z(saldoKonto.getBoWn() + p.getBoWn()));
        saldoKonto.setBoMa(Z.z(saldoKonto.getBoMa() + p.getBoMa()));
    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok) {
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        for (StronaWiersza r : zapisyRok) {
            //bez lub nie dodawaloby zapisow gdt konto levelu 0 jest jednoczenie analitycznym
            if (r.getDokfk().getSeriadokfk().equals("BO") && r.getDokfk().getNrkolejnywserii() == 1) {
                
            } else if (p.getPelnynumer().equals(r.getKonto().getSyntetycznenumer()) || p.getPelnynumer().equals(r.getKonto().getPelnynumer())) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    if (r.getWnma().equals("Wn")) {
                        if (r.getDokfk().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                            saldoKonto.setObrotyWnMc(Z.z(saldoKonto.getObrotyWnMc() + r.getKwotaPLN()));
                        }
                        saldoKonto.setObrotyWn(Z.z(saldoKonto.getObrotyWn() + r.getKwotaPLN()));
                    } else {
                        if (r.getDokfk().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                            saldoKonto.setObrotyMaMc(Z.z(saldoKonto.getObrotyMaMc() + r.getKwotaPLN()));
                        }
                        saldoKonto.setObrotyMa(Z.z(saldoKonto.getObrotyMa() + r.getKwotaPLN()));
                    }
                    saldoKonto.getZapisy().add(r);
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
    
    private List<StronaWiersza> pobierzzapisykonto(Konto konto) {
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikKontoSyntetyczneRok(wpisView.getPodatnikObiekt(), konto, wpisView.getRokWpisuSt());
        return zapisy;
    }
    public void sumujwybranekonta() {
        sumaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        sumaSaldoKonto.add(KontaFKBean.sumujsaldakont(listaSaldoKontowybrane));
    }
    
    public void drukuj(int i) {
        if (listaSaldoKontofilter == null) {
            PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 1, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        } else {
            PdfKonta.drukuj(listaSaldoKontofilter, wpisView, i, 1, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
    }
    
    public void drukujS(int i) {
        if (listaSaldoKontofilter == null && listaSaldoKontowybrane.size() == 0) {
            PdfKonta.drukuj(listaSaldoKonto, wpisView, i, 1, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontofilter != null && listaSaldoKontowybrane.size() == 0) {
            PdfKonta.drukuj(listaSaldoKontofilter, wpisView, i, 1, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
        if (listaSaldoKontowybrane.size() > 0) {
            PdfKonta.drukuj(listaSaldoKontowybrane, wpisView, i, 1, wpisView.getMiesiacWpisu(), sumaSaldoKonto);
        }
    }
    
    public int compare(Object o1, Object o2) {
         try {
            return KontoSortBean.sortZaksiegowaneDok((Konto) o1, (Konto) o2);
         } catch (Exception e) {  E.e(e);
             return 0;
         }
     }

  
   
}
