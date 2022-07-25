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
    private double jest;
    private String jestStrona;
    private double winnobyc;
    private String winnobycStrona;

    public RoznicaSaldBO() {
    }

    public RoznicaSaldBO(Konto konto, double kwotaroznicy, double jest, String jestStrona, double winnobyc, String winnobycStrona) {
        this.konto = konto;
        this.kwotaroznicy = kwotaroznicy;
        this.jest = jest;
        this.jestStrona = jestStrona;
        this.winnobyc = winnobyc;
        this.winnobycStrona = winnobycStrona;
    }

    
    
    public double getJest() {
        return jest;
    }

    public void setJest(double jest) {
        this.jest = jest;
    }

    public String getJestStrona() {
        return jestStrona;
    }

    public void setJestStrona(String jestStrona) {
        this.jestStrona = jestStrona;
    }

    public double getWinnobyc() {
        return winnobyc;
    }

    public void setWinnobyc(double winnobyc) {
        this.winnobyc = winnobyc;
    }

    public String getWinnobycStrona() {
        return winnobycStrona;
    }

    public void setWinnobycStrona(String winnobycStrona) {
        this.winnobycStrona = winnobycStrona;
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
