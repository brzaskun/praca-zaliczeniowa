/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class SaldoKonto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Konto konto;
    private double boWn;
    private double boMa;
    private double obrotyWn;
    private double obrotyMa;
    private double obrotyBoWn;
    private double obrotyBoMa;
    private double saldoWn;
    private double saldoMa;
    private String nrpelnymacierzystego;
    private List<StronaWiersza> zapisy;

    public SaldoKonto() {
        this.zapisy = new ArrayList<>();
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa) {
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.konto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SaldoKonto other = (SaldoKonto) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaldoKonto{" + "konto=" + konto + ", obrotyBoWn=" + obrotyBoWn + ", obrotyBoMa=" + obrotyBoMa + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getBoWn() {
        return boWn;
    }

    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }

    public double getBoMa() {
        return boMa;
    }

    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }

    public double getObrotyWn() {
        return obrotyWn;
    }

    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }

    public double getObrotyMa() {
        return obrotyMa;
    }

    public void setObrotyMa(double obrotyMa) {
        this.obrotyMa = obrotyMa;
    }

    public double getObrotyBoWn() {
        return obrotyBoWn;
    }

    public void setObrotyBoWn(double obrotyBoWn) {
        this.obrotyBoWn = obrotyBoWn;
    }

    public double getObrotyBoMa() {
        return obrotyBoMa;
    }

    public void setObrotyBoMa(double obrotyBoMa) {
        this.obrotyBoMa = obrotyBoMa;
    }

    public double getSaldoWn() {
        return saldoWn;
    }

    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }

    public double getSaldoMa() {
        return saldoMa;
    }

    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public List<StronaWiersza> getZapisy() {
        return zapisy;
    }

    public void setZapisy(List<StronaWiersza> zapisy) {
        this.zapisy = zapisy;
    }

    public String getNrpelnymacierzystego() {
        return nrpelnymacierzystego;
    }

    public void setNrpelnymacierzystego(String nrpelnymacierzystego) {
        this.nrpelnymacierzystego = nrpelnymacierzystego;
    }

    
    
    public void sumujBOZapisy() {
        double obrotyWn = Math.round((this.boWn + this.obrotyWn)*100);
        obrotyWn /= 100;
        this.obrotyBoWn = obrotyWn;
        double obrotyMa = Math.round((this.boMa + this.obrotyMa)*100);
        obrotyMa /= 100;
        this.obrotyBoMa = obrotyMa;
    }

    public void wyliczSaldo() {
        double Wn_Ma = Math.round((this.obrotyBoWn - this.obrotyBoMa)*100);
        Wn_Ma /= 100;
        double Ma_Wn = Math.round((this.obrotyBoMa - this.obrotyBoWn)*100);
        Ma_Wn /= 100;
        this.saldoWn = this.obrotyBoWn > this.obrotyBoMa ? Wn_Ma : 0.0;
        this.saldoMa = this.obrotyBoMa > this.obrotyBoWn ? Ma_Wn : 0.0;
    }
    
    
    
}
