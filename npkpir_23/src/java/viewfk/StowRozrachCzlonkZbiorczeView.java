/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.MiejscePrzychodowDAO;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import pdffk.PdfStowRozrachunki;
import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class StowRozrachCzlonkZbiorczeView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Konto> konta;
    private List<MiejscePrzychodow> miejscaprzychodow;
    private List<Zapisy> listazapisow;
    private List<Zapisy> listazapisowfiltered;
    private Konto wybranekonto;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public StowRozrachCzlonkZbiorczeView() {
        this.listazapisow = Collections.synchronizedList(new ArrayList<>());
    }
    
    //nie ruszac bo nie dziala u geusta
    @PostConstruct
    public void pobierzzbiorcze() {
        konta = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 7);
        miejscaprzychodow = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        //Collections.sort(czlonkowiestowarzyszenia, new MiejscePrzychodowcomparator());
    }
   
    public void pobierz() {
        if (wybranekonto != null) {
            listazapisow = Collections.synchronizedList(new ArrayList<>());
            List<StronaWiersza> pobierzzapisynapotomkach = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteRok(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            for (StronaWiersza p : pobierzzapisynapotomkach) {
                MiejscePrzychodow mp = pobierzmiejsceprzychodow(p.getKonto());
                Zapisy z = new Zapisy(p.getKonto(),mp);
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
            
        }
    }
    
    public void drukuj() {
        try {
            if (listazapisowfiltered != null && !listazapisowfiltered.isEmpty()) {
                PdfStowRozrachunki.drukuj(wpisView, wybranekonto, listazapisowfiltered);
            } else {
                PdfStowRozrachunki.drukuj(wpisView, wybranekonto, listazapisow);
            }
            msg.Msg.dP();
        } catch (Exception e) {
            msg.Msg.dPe();
        }
    }
    private void obliczsalda() {
        for (Zapisy p : listazapisow) {
             p.saldo = p.sumawn - p.sumama;
        }
    }

    private MiejscePrzychodow pobierzmiejsceprzychodow(Konto konto) {
        MiejscePrzychodow p = null;
        for (MiejscePrzychodow r : miejscaprzychodow) {
            if (r.getNrkonta().equals(konto.getNrkonta())) {
                p = r;
            }
        }
        return p;
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

    public List<Zapisy> getListazapisowfiltered() {
        return listazapisowfiltered;
    }

    public void setListazapisowfiltered(List<Zapisy> listazapisowfiltered) {
        this.listazapisowfiltered = listazapisowfiltered;
    }

   
    
    

    public static class Zapisy {
        private Konto konto;
        private MiejscePrzychodow mp;
        private double sumawn;
        private double sumama;
        private double saldo;
        
        public Zapisy() {
        }

        private Zapisy(Konto konto) {
            this.konto = konto;
        }

        private Zapisy(Konto konto, MiejscePrzychodow mp) {
            this.konto = konto;
            this.mp = mp;
        }

        public Konto getKonto() {
            return konto;
        }

        public void setKonto(Konto konto) {
            this.konto = konto;
        }

        public MiejscePrzychodow getMp() {
            return mp;
        }

        public void setMp(MiejscePrzychodow mp) {
            this.mp = mp;
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
