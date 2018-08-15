/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.MiejscePrzychodowDAO;
import daoFK.StowNaliczenieDAO;
import data.Data;
import embeddable.Mce;
import entityfk.MiejscePrzychodow;
import entityfk.MiejsceSuper;
import entityfk.StowNaliczenie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
public class StowNaliczenieZestView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pozycja> lista;
    @Inject
    private StowNaliczenieDAO stowNaliczenieDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranakategoria;
    
    public StowNaliczenieZestView() {
        this.lista = Collections.synchronizedList(new ArrayList<>());
    }

    public void pobierz() {
        this.lista = Collections.synchronizedList(new ArrayList<>());
        if (!wybranakategoria.equals("wybierz")  && !wpisView.getMiesiacWpisu().equals("CR")) {
            List<String> mce = Mce.getMceListS();
            List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
            for (Iterator<MiejscePrzychodow> it = czlonkowiestowarzyszenia.iterator(); it.hasNext(); ) {
                MiejscePrzychodow p = it.next();
                if (!Data.czyjestpomiedzy(p.getPoczatek(), p.getKoniec(), wpisView.getRokWpisuSt(), "12")) {
                    it.remove();
                } else {
                    lista.add(new Pozycja(p));
                }
            }
            for (String mc : mce) {
                List<StowNaliczenie> listanal = stowNaliczenieDAO.findByMcKategoria(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc, wybranakategoria);
                for (StowNaliczenie sn : listanal) {
                    for (Pozycja poz : lista) {
                        if (poz.getMiejsce().equals(sn.getMiejsce())) {
                            nanieskwota (poz, sn, mc);
                        }
                    }
                }
            }
        }
    }

    private void nanieskwota(Pozycja poz, StowNaliczenie sn, String mc) {
        switch (mc) {
            case "01":
                poz.setM01(sn.getKwota());
                break;
            case "02":
                poz.setM02(sn.getKwota());
                break;
            case "03":
                poz.setM03(sn.getKwota());
                break;
            case "04":
                poz.setM04(sn.getKwota());
                break;
            case "05":
                poz.setM05(sn.getKwota());
                break;
            case "06":
                poz.setM06(sn.getKwota());
                break;
            case "07":
                poz.setM07(sn.getKwota());
                break;
            case "08":
                poz.setM08(sn.getKwota());
                break;
            case "09":
                poz.setM09(sn.getKwota());
                break;
            case "10":
                poz.setM10(sn.getKwota());
                break;
            case "11":
                poz.setM11(sn.getKwota());
                break;
            case "12":
                poz.setM12(sn.getKwota());
                break;
        }
    }

    public static class Pozycja {
        private MiejsceSuper miejsce;
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
        public Pozycja() {
        }

        private Pozycja(MiejscePrzychodow p) {
            this.miejsce = p;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.miejsce);
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
            if (!Objects.equals(this.miejsce, other.miejsce)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Pozycja{" + "miejsce=" + miejsce.getOpismiejsca() + ", m01=" + m01 + ", m02=" + m02 + ", m03=" + m03 + ", m04=" + m04 + ", m05=" + m05 + ", m06=" + m06 + ", m07=" + m07 + ", m08=" + m08 + ", m09=" + m09 + ", m10=" + m10 + ", m11=" + m11 + ", m12=" + m12 + '}';
        }
        
//<editor-fold defaultstate="collapsed" desc="comment">
        
        public MiejsceSuper getMiejsce() {
            return miejsce;
        }
        
        public void setMiejsce(MiejsceSuper miejsce) {
            this.miejsce = miejsce;
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
        
        
//</editor-fold>
        
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<Pozycja> getLista() {
        return lista;
    }
    
    public void setLista(List<Pozycja> lista) {
        this.lista = lista;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public String getWybranakategoria() {
        return wybranakategoria;
    }
    
    public void setWybranakategoria(String wybranakategoria) {
        this.wybranakategoria = wybranakategoria;
    }
    
//</editor-fold>
    
}
