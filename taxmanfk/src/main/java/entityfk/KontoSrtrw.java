/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.SrodekTrw;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Alternative;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Alternative
public class KontoSrtrw extends Konto implements Serializable {
    private static final long serialVersionUID = 1L;
    private double saldoWnstr;
    private double saldoMastr;
    private double kosztnabycia;
    private double odpisyamo;
    private List<SrodekTrw> srodkitrwale;
    private double roznica;


    public KontoSrtrw(Konto old) {
        super(old);
    }

    public double getSaldoWnstr() {
        return saldoWnstr;
    }

    public void setSaldoWnstr(double saldoWnstr) {
        this.saldoWnstr = saldoWnstr;
    }

    public double getSaldoMastr() {
        return saldoMastr;
    }

    public void setSaldoMastr(double saldoMastr) {
        this.saldoMastr = saldoMastr;
    }

    public double getKosztnabycia() {
        return kosztnabycia;
    }

    public void setKosztnabycia(double kosztnabycia) {
        this.kosztnabycia = kosztnabycia;
    }

    public double getOdpisyamo() {
        return odpisyamo;
    }

    public void setOdpisyamo(double odpisyamo) {
        this.odpisyamo = odpisyamo;
    }

    public List<SrodekTrw> getSrodkitrwale() {
        return srodkitrwale;
    }

    public void setSrodkitrwale(List<SrodekTrw> srodkitrwale) {
        this.srodkitrwale = srodkitrwale;
    }

    public double getRoznica() {
        double zwrot = 0.0;
        if (saldoWnstr!=0.0) {
            zwrot = saldoWnstr-kosztnabycia;
        } else {
            zwrot = saldoMastr-odpisyamo;
        }
        return Z.z(zwrot);
    }

    
    
}
