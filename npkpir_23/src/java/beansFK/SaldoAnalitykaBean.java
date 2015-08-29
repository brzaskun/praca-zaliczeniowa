/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.util.List;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SaldoAnalitykaBean extends Thread {
    private Konto p;
    private StronaWiersza r;
    private SaldoKonto saldoKonto;
    double sumaWn;
    double sumaMa;

    public SaldoAnalitykaBean(Konto p, StronaWiersza r, SaldoKonto saldoKonto, double sumaWn, double sumaMa) {
        this.p = p;
        this.r = r;
        this.saldoKonto = saldoKonto;
        this.sumaWn = sumaWn;
        this.sumaMa = sumaMa;
    }
   
    
    
    public void run() {
       if (r.getKonto().equals(p)) {
        if (r.getWnma().equals("Wn")) {
            sumaWn += r.getKwotaPLN();
        } else {
            sumaMa += r.getKwotaPLN();
        }
        saldoKonto.getZapisy().add(r);
    }
    }

    public Konto getP() {
        return p;
    }

    public void setP(Konto p) {
        this.p = p;
    }

    public StronaWiersza getR() {
        return r;
    }

    public void setR(StronaWiersza r) {
        this.r = r;
    }

    public SaldoKonto getSaldoKonto() {
        return saldoKonto;
    }

    public void setSaldoKonto(SaldoKonto saldoKonto) {
        this.saldoKonto = saldoKonto;
    }

    public double getSumaWn() {
        return sumaWn;
    }

    public void setSumaWn(double sumaWn) {
        this.sumaWn = sumaWn;
    }

    public double getSumaMa() {
        return sumaMa;
    }

    public void setSumaMa(double sumaMa) {
        this.sumaMa = sumaMa;
    }
  
    
    
    
}
