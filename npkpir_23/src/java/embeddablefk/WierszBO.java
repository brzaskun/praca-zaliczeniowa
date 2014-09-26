/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Osito
 */
@Entity
public class WierszBO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    private Konto konto;
    private String opis;
    private double kwotaWn;
    private double kwotaMa;

    public WierszBO() {
        this.kwotaWn = 0.0;
        this.kwotaMa = 0.0;
    }
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    
    
}
