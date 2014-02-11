/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class VatuepodatnikPK implements Serializable{
    private String klient;
    private String symbolokresu;
    private String rok;

    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public String getSymbolokresu() {
        return symbolokresu;
    }

    public void setSymbolokresu(String symbolokresu) {
        this.symbolokresu = symbolokresu;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
    
    
}
