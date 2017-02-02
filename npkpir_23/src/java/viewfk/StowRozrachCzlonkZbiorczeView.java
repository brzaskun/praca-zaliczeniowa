/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import comparator.MiejscePrzychodowcomparator;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdffk.PdfStowRozrachunki;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StowRozrachCzlonkZbiorczeView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Konto> konta;
    private List<Zapisy> listazapisow;
    private Konto wybranekonto;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public StowRozrachCzlonkZbiorczeView() {
        this.listazapisow = new ArrayList<>();
    }
    
    
    
    public void pobierzzbiorcze() {
        konta = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 7);
        //Collections.sort(czlonkowiestowarzyszenia, new MiejscePrzychodowcomparator());
    }
   
    public void pobierz() {
        if (wybranekonto != null) {
            listazapisow = new ArrayList<>();
            List<StronaWiersza> pobierzzapisynapotomkach = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteRok(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            for (StronaWiersza p : pobierzzapisynapotomkach) {
                Zapisy z = new Zapisy(p.getKonto());
                if (listazapisow.contains(z)) {
                    Zapisy zo = listazapisow.get(listazapisow.indexOf(z));
                    zo.dodaj(p);
                } else {
                    z.dodaj(p);
                    listazapisow.add(z);
                }
            }
            obliczsalda();
            Collections.sort(listazapisow, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    Kontocomparator k = new Kontocomparator();
                    return k.compare(((Zapisy) o1).getKonto(), ((Zapisy) o2).getKonto());
                }
            });
            System.out.println("");
            
        }
    }
    
    public void drukuj() {
        try {
            PdfStowRozrachunki.drukuj(wpisView, wybranekonto, listazapisow);
            msg.Msg.dP();
        } catch (Exception e) {
            msg.Msg.dPe();
        }
    }

    public List<Konto> getKonta() {
        return konta;
    }

    public void setKonta(List<Konto> konta) {
        this.konta = konta;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Konto getWybranekonto() {
        return wybranekonto;
    }

    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }

    public List<Zapisy> getListazapisow() {
        return listazapisow;
    }

    public void setListazapisow(List<Zapisy> listazapisow) {
        this.listazapisow = listazapisow;
    }

    private void obliczsalda() {
        for (Zapisy p : listazapisow) {
             p.saldo = p.sumawn - p.sumama;
        }
    }
    
    

    public static class Zapisy {
        private Konto konto;
        private double sumawn;
        private double sumama;
        private double saldo;
        
        public Zapisy() {
        }

        private Zapisy(Konto konto) {
            this.konto = konto;
        }

        public Konto getKonto() {
            return konto;
        }

        public void setKonto(Konto konto) {
            this.konto = konto;
        }

        public double getSumawn() {
            return sumawn;
        }

        public void setSumawn(double sumawn) {
            this.sumawn = sumawn;
        }

        public double getSumama() {
            return sumama;
        }

        public void setSumama(double sumama) {
            this.sumama = sumama;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.konto);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Zapisy other = (Zapisy) obj;
            if (!Objects.equals(this.konto, other.konto)) {
                return false;
            }
            return true;
        }

        private void dodaj(StronaWiersza p) {
            if (p.isWn()) {
                this.sumawn += p.getKwota();
            } else {
                this.sumama += p.getKwota();
            }
        }
        
        
        
    }

    
    
    
}
