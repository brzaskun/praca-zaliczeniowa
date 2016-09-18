/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Podatnik;
import entity.Statystyka;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class StatystykaFaktury {
    private int id;
    private Podatnik podatnik;
    private Map<String, StatFaktRok> statFaktRok;

    public StatystykaFaktury() {
        this.statFaktRok = new HashMap<>();
    }
    
    public StatystykaFaktury(Statystyka s) {
        this.statFaktRok =  new HashMap<>();
        this.podatnik = s.getPodatnik();
        this.statFaktRok.put(s.getRok(), new StatFaktRok(s));
    }
        
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Podatnik getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public Map<String, StatFaktRok> getStatFaktRok() {
        return statFaktRok;
    }
    

    
//</editor-fold>
    

    public static class StatFaktRok {
        private String rok;
        private int iloscfaktur;
        private double kwotafaktur;
        private double srednia;
        
        public StatFaktRok() {
        }

        public StatFaktRok(Statystyka s) {
            this.rok = s.getRok();
            this.iloscfaktur = s.getIloscfaktur();
            this.kwotafaktur = s.getKwotafaktur();
            this.srednia = Z.z(this.kwotafaktur/this.iloscfaktur);
        }

     
        //<editor-fold defaultstate="collapsed" desc="comment">
        public String getRok() {
            return rok;
        }
        
        public void setRok(String rok) {
            this.rok = rok;
        }
        
        public int getIloscfaktur() {
            return iloscfaktur;
        }
        
        public void setIloscfaktur(int iloscfaktur) {
            this.iloscfaktur = iloscfaktur;
        }

        public double getSrednia() {
            return srednia;
        }

        public void setSrednia(double srednia) {
            this.srednia = srednia;
        }
        
        public double getKwotafaktur() {
            return kwotafaktur;
        }
        
        public void setKwotafaktur(double kwotafaktur) {
            this.kwotafaktur = kwotafaktur;
        }
        
//</editor-fold>
    }
    
}
