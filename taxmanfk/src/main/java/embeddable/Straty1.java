/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Straty1 implements Serializable{
    private static final long serialVersionUID = 1L;
    private String rok;
    private String kwota;
    private String polowakwoty;
    private String wykorzystano;
    private String sumabiezace;
    private String zostalo;
    //tu wpisujemu co zosatlo wykorszystane w latach
    private List<Wykorzystanie> wykorzystanieBiezace;
    
    

    public Straty1() {
        wykorzystanieBiezace = Collections.synchronizedList(new ArrayList<>());
    }

    public Straty1(String rok, String kwota, String polowakwoty, String wykorzystano, String zostalo) {
        this.rok = rok;
        this.kwota = kwota;
        this.polowakwoty = polowakwoty;
        this.wykorzystano = wykorzystano;
        this.zostalo = zostalo;
        this.wykorzystanieBiezace = Collections.synchronizedList(new ArrayList<>());
    }

        
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getPolowakwoty() {
        return polowakwoty;
    }

    public void setPolowakwoty(String polowakwoty) {
        this.polowakwoty = polowakwoty;
    }

    public String getWykorzystano() {
        return wykorzystano;
    }

    public void setWykorzystano(String wykorzystano) {
        this.wykorzystano = wykorzystano;
    }

    public String getZostalo() {
        return zostalo;
    }

    public void setZostalo(String zostalo) {
        this.zostalo = zostalo;
    }

    public List<Wykorzystanie> getWykorzystanieBiezace() {
        return wykorzystanieBiezace;
    }

    public void setWykorzystanieBiezace(List<Wykorzystanie> wykorzystanieBiezace) {
        this.wykorzystanieBiezace = wykorzystanieBiezace;
    }

    public String getSumabiezace() {
        return sumabiezace;
    }

    public void setSumabiezace(String sumabiezace) {
        this.sumabiezace = sumabiezace;
    }

  
    

    public static class Wykorzystanie implements Serializable{
        private static final long serialVersionUID = 2L;
        private String rokwykorzystania;
        private Double kwotawykorzystania;
        
        public Wykorzystanie() {
        }
        

        public String getRokwykorzystania() {
            return rokwykorzystania;
        }

        public void setRokwykorzystania(String rokwykorzystania) {
            this.rokwykorzystania = rokwykorzystania;
        }

        public Double getKwotawykorzystania() {
            return kwotawykorzystania;
        }

        public void setKwotawykorzystania(Double kwotawykorzystania) {
            this.kwotawykorzystania = kwotawykorzystania;
        }

        @Override
        public String toString() {
            return "Wykorzystanie{" + "rokwykorzystania=" + rokwykorzystania + ", kwotawykorzystania=" + kwotawykorzystania + '}';
        }
        
        
      
    }

   

       

    
    
}
