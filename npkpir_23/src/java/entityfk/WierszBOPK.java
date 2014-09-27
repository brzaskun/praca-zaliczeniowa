/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class WierszBOPK implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nippodatnika;
    private int idkonta;
    private String rok;

    public WierszBOPK() {
    }
        

    public String getNippodatnika() {
        return nippodatnika;
    }

    public void setNippodatnika(String nippodatnika) {
        this.nippodatnika = nippodatnika;
    }

    public int getIdkonta() {
        return idkonta;
    }

    public void setIdkonta(int idkonta) {
        this.idkonta = idkonta;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
    
    
}
