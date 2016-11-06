/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import implement.ListExt;
import em.Em;
import embeddable.EVatViewPola;
import embeddable.EVatwpisSuma;
import embeddablefk.SaldoKonto;
import entity.EVatOpis;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class EwidencjaVatCheckView implements Serializable {

    private List<EwidencjaKonto> ewkonto;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
   public void wykryjbledy(List<List<EVatViewPola>> ewidencje, List<SaldoKonto> kontavat) {
       List<EVatViewPola> ewidencjezawartosc = pobierzdane(ewidencje);
       List<StronaWiersza> zakupy = pobierzzakupy(kontavat);
       List<StronaWiersza> sprzedaz = pobierzsprzedaz(kontavat);
       List<EVatViewPola> brakinakoncie = sprawdzbrakinakoncie(ewidencjezawartosc, zakupy, sprzedaz);
       if (brakinakoncie.size() > 0) {
           double suma = 0.0;
           for (EVatViewPola p : brakinakoncie) {
               suma += p.getVat();
           }
           System.out.println("sa braki e ewidencji"+suma);
       }
       List<StronaWiersza> brakiwewidencji = sprawdzbrakiwewidencji(ewidencjezawartosc, zakupy, sprzedaz);
       if (brakiwewidencji.size() > 0) {
           double suma = 0.0;
           for (StronaWiersza p : brakiwewidencji) {
               suma += p.getKwota();
           }
           System.out.println("sa braki na kontach "+suma);
           Msg.msg("e","Zakończono sprawdzanie ewidencji. Wykryto błędy.");
       } else {
           Msg.msg("i","Zakończono sprawdzanie ewidencji. Nie wykryto błędów.");
       }
       ewkonto = stworzzestawienie(brakinakoncie, brakiwewidencji);
       System.out.println("jest lista zbiorcza "+ewkonto.size());
       
   }

   private List<EVatViewPola> pobierzdane(List<List<EVatViewPola>> ewidencje) {
       List<EVatViewPola> ewidencjezawartosc = new ArrayList<>();
       for (List<EVatViewPola> p : ewidencje) {
           ewidencjezawartosc.addAll(p);
       }
       System.out.println("size "+ewidencjezawartosc.size());
       int l = 1;
       for (Iterator<EVatViewPola> it = ewidencjezawartosc.iterator(); it.hasNext();) {
           System.out.println("licznik: "+l);
           l++;
           EVatViewPola wiersz = it.next();
           if (wiersz.getNazwaewidencji() == null) {
               it.remove();
           } else if (wiersz.getNazwaewidencji().isTylkoNetto()) {
               it.remove();
           }
       }
       return ewidencjezawartosc;
   }

    private boolean czyjestnakoncie(EVatViewPola p, List<StronaWiersza> zapisy) {
        boolean jest = false;
        for (Iterator it = zapisy.iterator(); it.hasNext(); ) {
            StronaWiersza r = (StronaWiersza) it.next();
            if (Z.zAbs(p.getVat()) == Z.z(r.getKwotaPLN())) {
                if (p.getDokfkPK().equals(r.getDokfk().getDokfkPK())) {
                    if (p.getNrWlDk().equals(r.getDokfk().getNumerwlasnydokfk())) {
                        jest = true;
                        it.remove();
                        break;
                    }
                }
            }
        }
        return jest;
    }

    private List<StronaWiersza> pobierzzakupy(List<SaldoKonto> kontavat) {
        List<StronaWiersza> l = new ArrayList<>();
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getPelnynumer().equals("221-3") || p.getKonto().getPelnynumer().equals("221-4")) {
                l.addAll(p.getZapisy());
            }
        }
        return l;
    }

    private List<StronaWiersza> pobierzsprzedaz(List<SaldoKonto> kontavat) {
        List<StronaWiersza> l = new ArrayList<>();
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getPelnynumer().equals("221-1") || p.getKonto().getPelnynumer().equals("221-2")) {
                l.addAll(p.getZapisy());
            }
        }
        return l;
    }

    private List<EVatViewPola> sprawdzbrakinakoncie(List<EVatViewPola> ewidencjezawartosc, List<StronaWiersza> zakupy, List<StronaWiersza> sprzedaz) {
       List<EVatViewPola> brakinakoncie = new ArrayList<>();
       for (EVatViewPola p : ewidencjezawartosc) {
           boolean nk = false;
           if (p.getNazwaewidencji() != null) {
                if (p.getNazwaewidencji().getTypewidencji().equals("z")) {
                         nk = czyjestnakoncie(p, zakupy);
                } else if (p.getNazwaewidencji().getTypewidencji().equals("s")) {
                         nk = czyjestnakoncie(p, sprzedaz);
                } else if (p.getNazwaewidencji().getTypewidencji().equals("sz")) {
                    nk = czyjestnakoncie(p, zakupy);
                    nk = czyjestnakoncie(p, sprzedaz);
                }
                if (nk == false) {
                    if (!brakinakoncie.contains(p)) {
                        brakinakoncie.add(p);
                    }
                }
           }
       }
       return brakinakoncie;
    }

    private List<StronaWiersza> sprawdzbrakiwewidencji(List<EVatViewPola> ewidencjezawartosc, List<StronaWiersza> zakupy, List<StronaWiersza> sprzedaz) {
       List<StronaWiersza> brakiwewidencji = new ArrayList<>();
       for (StronaWiersza p : zakupy) {
           if (p.getDokfk().getVatM().equals(wpisView.getMiesiacWpisu()) && p.getDokfk().getVatR().equals(wpisView.getRokWpisuSt())) {
            boolean nk = czyjestwewidencji(p, ewidencjezawartosc);
             if (nk == false) {
                     brakiwewidencji.add(p);
             }
           }
       }
       for (StronaWiersza p : sprzedaz) {
           if (p.getDokfk().getVatM().equals(wpisView.getMiesiacWpisu()) && p.getDokfk().getVatR().equals(wpisView.getRokWpisuSt())) {
            boolean nk = czyjestwewidencji(p, ewidencjezawartosc);
             if (nk == false) {
                     brakiwewidencji.add(p);
             }
           }
       }
       return brakiwewidencji;
    }

    private boolean czyjestwewidencji(StronaWiersza r, List<EVatViewPola> ewidencjezawartosc) {
        boolean jest = false;
        for (EVatViewPola p : ewidencjezawartosc) {
             if (Z.z(p.getVat()) == Z.z(r.getKwotaPLN())) {
                if (p.getDokfkPK().equals(r.getDokfk().getDokfkPK())) {
                    if (p.getNrWlDk().equals(r.getDokfk().getNumerwlasnydokfk())) {
                        jest = true;
                        break;
                    }
                }
            }
        }
        return jest;
    }

    private List<EwidencjaKonto> stworzzestawienie(List<EVatViewPola> brakiwewidencji, List<StronaWiersza> brakinakoncie) {
        ListExt<EwidencjaKonto> l = new ListExt<EwidencjaKonto>();
        for (EVatViewPola p : brakiwewidencji) {
            l.add(new EwidencjaKonto(p, null, p.getVat(), 0.0));
        }
        for (StronaWiersza p : brakinakoncie) {
            if (l.zawiera(p)) {
                l.pobierz(p).setKonto(p);
                l.pobierz(p).setMrrokkonto(p.getDokfk().getVatM()+"/"+p.getDokfk().getVatR());
                l.pobierz(p).setDatakonto(p.getDokfk().getDataoperacji());
                l.pobierz(p).setKwotakonto(p.getKwotaPLN());
            } else {
                l.add(new EwidencjaKonto(null, p, 0.0, p.getKwotaPLN()));
            }
        }
        return l;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<EwidencjaKonto> getEwkonto() {
        return ewkonto;
    }

    public void setEwkonto(List<EwidencjaKonto> ewkonto) {
        this.ewkonto = ewkonto;
    }
    
    
   
    public class EwidencjaKonto {
        private EVatViewPola ewidencja;
        private StronaWiersza konto;
        private double kwotaewidencja;
        private double kwotakonto;
        private String dataewidencja;
        private String datakonto;
        private String mrewidencja;
        private String mrrokkonto;

        public EwidencjaKonto(EVatViewPola ewidencja, StronaWiersza konto, double kwotaewidencja, double kwotakonto) {
            this.ewidencja = ewidencja;
            this.konto = konto;
            this.kwotaewidencja = kwotaewidencja;
            this.kwotakonto = kwotakonto;
            if (ewidencja != null) {
                this.dataewidencja = ewidencja.getDataSprz();
                this.mrewidencja = ewidencja.getInnymc()+"/"+ewidencja.getInnyrok();
            }
            if (konto != null) {
                this.datakonto = konto.getDokfk().getDataoperacji();
                this.mrrokkonto = konto.getDokfk().getVatM()+"/"+konto.getDokfk().getVatR();
            }
        }

        public String getZapisEwidencja() {
            if (ewidencja != null) {
                return "Ewidencja: "+ewidencja.getNazwaewidencji().getNazwa()+", dokument: "+ewidencja.getNrWlDk();
            } else {
                return "";
            }
        }
        
        public String getZapisKonto() {
            if (konto != null) {
                return "Konto: "+konto.getKonto().getPelnynumer()+", dokument: "+konto.getDokfk().getNumerwlasnydokfk();
            } else {
                return "";
            }
        }
        
        public boolean equals(StronaWiersza obj) {
            if (obj == null) {
                return false;
            }
            if (this.ewidencja == null) {
                return false;
            }
            final StronaWiersza other = (StronaWiersza) obj;
            if (other.getWiersz().getDokfk() == null) {
                return false;
            }
            if (other.getWiersz().getDokfk() == null) {
                return false;
            }
            if (!Objects.equals(other.getWiersz().getDokfk().getNumerwlasnydokfk(), this.ewidencja.getNrWlDk())) {
                return false;
            }
            if (!Objects.equals(other.getWiersz().getDokfk().getKontr(), this.ewidencja.getKontr())) {
                return false;
            }
            return true;
        }
        
        //<editor-fold defaultstate="collapsed" desc="comment">
        public EVatViewPola getEwidencja() {
            return ewidencja;
        }
        
        public void setEwidencja(EVatViewPola ewidencja) {
            this.ewidencja = ewidencja;
        }
        
        public StronaWiersza getKonto() {
            return konto;
        }
        
        public void setKonto(StronaWiersza konto) {
            this.konto = konto;
        }
        
        public double getKwotaewidencja() {
            return kwotaewidencja;
        }
        
        public void setKwotaewidencja(double kwotaewidencja) {
            this.kwotaewidencja = kwotaewidencja;
        }

        public String getMrrokkonto() {
            return mrrokkonto;
        }

        public void setMrrokkonto(String mrrokkonto) {
            this.mrrokkonto = mrrokkonto;
        }

        public String getMrewidencja() {
            return mrewidencja;
        }

        public void setMrewidencja(String mrewidencja) {
            this.mrewidencja = mrewidencja;
        }
        
        public double getKwotakonto() {
            return kwotakonto;
        }
        
        public void setKwotakonto(double kwotakonto) {
            this.kwotakonto = kwotakonto;
        }
        
        
//</editor-fold>

        public String getDataewidencja() {
            return dataewidencja;
        }

        public void setDataewidencja(String dataewidencja) {
            this.dataewidencja = dataewidencja;
        }

        public String getDatakonto() {
            return datakonto;
        }

        public void setDatakonto(String datakonto) {
            this.datakonto = datakonto;
        }

        @Override
        public String toString() {
            return "EwidencjaKonto{" + "ewidencja=" + ewidencja.getNazwaewidencji().getNazwa() + ", konto=" + konto.getKonto().getPelnynumer() + ", kwotaewidencja=" + kwotaewidencja + ", kwotakonto=" + kwotakonto + ", dataewidencja=" + dataewidencja + ", datakonto=" + datakonto + ", mrewidencja=" + mrewidencja + ", mrrokkonto=" + mrrokkonto + '}';
        }
        
        
        
                
    }
}
