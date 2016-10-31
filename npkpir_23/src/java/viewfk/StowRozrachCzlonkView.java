/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.MiejscePrzychodowDAO;
import daoFK.StowNaliczenieDAO;
import data.Data;
import embeddable.Mce;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.MiejsceSuper;
import entityfk.StowNaliczenie;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
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
public class StowRozrachCzlonkView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MiejscePrzychodow> czlonkowiestowarzyszenia;
    private List<Konto> konta;
    private List<Pozycja> lista;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private MiejscePrzychodow wybranyczlonek;
    
    public StowRozrachCzlonkView() {
        this.lista = new ArrayList<>();
    }

   public void pobierz() {
       czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
   }
   
   public void pobierzdane() {
       konta = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 7);
       for (Iterator<Konto> it = konta.iterator(); it.hasNext();) {
           Konto p = it.next();
           if (!p.getPelnynumer().startsWith("2")) {
               it.remove();
           }
       }
       for (Konto r : konta) {
           Konto kontowlasciwe = kontoDAOfk.findKontoMacierzystyNrkonta(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), r, wybranyczlonek.getNrkonta());
           List<StronaWiersza> sw = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkie(wpisView.getPodatnikObiekt(), kontowlasciwe, wpisView.getRokWpisuSt());
           Pozycja pozycjaWn = new Pozycja(kontowlasciwe);//przypisy
           Pozycja pozycjaMa = new Pozycja(kontowlasciwe);//wplaty
           for (StronaWiersza s : sw) {
               if (s.isWn()) {
                   pozycjaWn.dodajkwote(s);
               } else {
                   pozycjaMa.dodajkwote(s);
               }
           }
           pozycjaWn.podsumuj();
           pozycjaMa.podsumuj();
           lista.add(pozycjaWn);
           lista.add(pozycjaMa);
       }
   }

   public static class Pozycja {
        private Konto konto;
        private double m01;
        private double m02;
        private double m03;
        private double m04;
        private double m05;
        private double m06;
        private double m07;
        private double m08;
        private double m09;
        private double m10;
        private double m11;
        private double m12;
        private double razem;
        private String opisP;
        private String opisW;
        public Pozycja() {
        }

        private Pozycja(Konto p) {
            this.konto = p;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.konto);
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
            final Pozycja other = (Pozycja) obj;
            if (!Objects.equals(this.konto, other.konto)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Pozycja{" + "miejsce=" + konto.getPelnynumer() + ", m01=" + m01 + ", m02=" + m02 + ", m03=" + m03 + ", m04=" + m04 + ", m05=" + m05 + ", m06=" + m06 + ", m07=" + m07 + ", m08=" + m08 + ", m09=" + m09 + ", m10=" + m10 + ", m11=" + m11 + ", m12=" + m12 + '}';
        }
        
//<editor-fold defaultstate="collapsed" desc="comment">

        public Konto getKonto() {
            return konto;
        }

        public void setKonto(Konto konto) {
            this.konto = konto;
        }
        
        public double getM01() {
            return m01;
        }
        
        public void setM01(double m01) {
            this.m01 = m01;
        }
        
        public double getM02() {
            return m02;
        }
        
        public void setM02(double m02) {
            this.m02 = m02;
        }
        
        public double getM03() {
            return m03;
        }
        
        public void setM03(double m03) {
            this.m03 = m03;
        }
        
        public double getM04() {
            return m04;
        }
        
        public void setM04(double m04) {
            this.m04 = m04;
        }
        
        public double getM05() {
            return m05;
        }
        
        public void setM05(double m05) {
            this.m05 = m05;
        }
        
        public double getM06() {
            return m06;
        }
        
        public void setM06(double m06) {
            this.m06 = m06;
        }
        
        public double getM07() {
            return m07;
        }
        
        public void setM07(double m07) {
            this.m07 = m07;
        }
        
        public double getM08() {
            return m08;
        }
        
        public void setM08(double m08) {
            this.m08 = m08;
        }
        
        public double getM09() {
            return m09;
        }
        
        public void setM09(double m09) {
            this.m09 = m09;
        }
        
        public double getM10() {
            return m10;
        }
        
        public void setM10(double m10) {
            this.m10 = m10;
        }
        
        public double getM11() {
            return m11;
        }
        
        public void setM11(double m11) {
            this.m11 = m11;
        }
        
        public double getM12() {
            return m12;
        }
        
        public void setM12(double m12) {
            this.m12 = m12;
        }
        
        
        public double getRazem() {
            return razem;
        }

        public void setRazem(double razem) {
            this.razem = razem;
        }

        public String getOpisP() {
            return this.konto.getKontomacierzyste().getNazwapelna()+" - "+this.konto.getPelnynumer()+" przypis";
        }
        
        public String getOpisW() {
            return this.konto.getKontomacierzyste().getNazwapelna()+" - "+this.konto.getPelnynumer()+" wpłata";
        }
        
//</editor-fold>        

       private void dodajkwote(StronaWiersza sn) {
           switch (sn.getDokfk().getMiesiac()) {
               case "01":
                   this.setM01(this.getM01()+sn.getKwota());
                   break;
               case "02":
                   this.setM02(this.getM02()+sn.getKwota());
                   break;
               case "03":
                   this.setM03(this.getM03()+sn.getKwota());
                   break;
               case "04":
                   this.setM04(this.getM04()+sn.getKwota());
                   break;
               case "05":
                   this.setM05(this.getM05()+sn.getKwota());
                   break;
               case "06":
                   this.setM06(this.getM06()+sn.getKwota());
                   break;
               case "07":
                   this.setM07(this.getM07()+sn.getKwota());
                   break;
               case "08":
                   this.setM08(this.getM08()+sn.getKwota());
                   break;
               case "09":
                   this.setM09(this.getM09()+sn.getKwota());
                   break;
               case "10":
                   this.setM10(this.getM10()+sn.getKwota());
                   break;
               case "11":
                   this.setM11(this.getM11()+sn.getKwota());
                   break;
               case "12":
                   this.setM12(this.getM12()+sn.getKwota());
                   break;
           }
       }

        private void podsumuj() {
            double suma = 0.0;
            suma += this.m01;
            suma += this.m02;
            suma += this.m03;
            suma += this.m04;
            suma += this.m05;
            suma += this.m06;
            suma += this.m07;
            suma += this.m08;
            suma += this.m09;
            suma += this.m10;
            suma += this.m11;
            suma += this.m12;
            this.razem = suma;
        }
    }
   
    public MiejscePrzychodow getWybranyczlonek() {
        return wybranyczlonek;
    }

    public void setWybranyczlonek(MiejscePrzychodow wybranyczlonek) {
        this.wybranyczlonek = wybranyczlonek;
    }

    public List<Pozycja> getLista() {
        return lista;
    }

    public void setLista(List<Pozycja> lista) {
        this.lista = lista;
    }


    public List<MiejscePrzychodow> getCzlonkowiestowarzyszenia() {
        return czlonkowiestowarzyszenia;
    }

    public void setCzlonkowiestowarzyszenia(List<MiejscePrzychodow> czlonkowiestowarzyszenia) {
        this.czlonkowiestowarzyszenia = czlonkowiestowarzyszenia;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
   
   
   

}
