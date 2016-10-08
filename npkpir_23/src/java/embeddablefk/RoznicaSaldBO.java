/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class RoznicaSaldBO  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Konto konto;
    private double kwotaroznicy;

    public RoznicaSaldBO() {
    }

    public RoznicaSaldBO(Konto konto, double kwotaroznicy) {
        this.konto = konto;
        this.kwotaroznicy = kwotaroznicy;
    }

    @Override
    public String toString() {
        return "RoznicaSaldBO{" + "konto=" + konto.getPelnynumer() + ", kwotaroznicy=" + kwotaroznicy + '}';
    }

    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaroznicy() {
        return kwotaroznicy;
    }

    public void setKwotaroznicy(double kwotaroznicy) {
        this.kwotaroznicy = kwotaroznicy;
    }
    
    
}
