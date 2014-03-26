/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Straty implements Serializable{
    
    private String rok;
    private String kwota;
    private String polowakwoty;
    private String wykorzystano;
    private String zostalo;

    public Straty() {
    }

    public Straty(String rok, String kwota, String polowakwoty, String wykorzystano, String zostalo) {
        this.rok = rok;
        this.kwota = kwota;
        this.polowakwoty = polowakwoty;
        this.wykorzystano = wykorzystano;
        this.zostalo = zostalo;
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
    

    
    
}
