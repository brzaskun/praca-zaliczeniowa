/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class Transakcja implements Serializable {
    
    private TransakcjaPK transakcjaPK;
    private double kwotatransakcji;
    private double roznicekursowe;
    private double poprzedniakwota;
    private boolean zablokujnanoszenie;
    

    public Transakcja() {
        this.transakcjaPK = new TransakcjaPK();
        this.roznicekursowe = 0.0;
    }
    
    public WierszStronafkPK idSparowany() {
        return this.getTransakcjaPK().getSparowany().getWierszStronafk().getWierszStronafkPK();
    }
    
    public WierszStronafkPK idRozliczany() {
        return this.getTransakcjaPK().getRozliczany().getWierszStronafk().getWierszStronafkPK();
    }
    
    
    public double GetSpRozl() {
        return this.transakcjaPK.getSparowany().getRozliczono();
    }
    
    public void SetSpRozl(double suma) {
        transakcjaPK.getSparowany().setRozliczono(suma);
    }
    
     public double GetSpKwotaPier() {
        return this.transakcjaPK.getSparowany().getKwotapierwotna();
    }
    
    public double GetSpPoz() {
        return this.transakcjaPK.getSparowany().getPozostalo();
    }
    
    public void SetSpPoz(double suma) {
        transakcjaPK.getSparowany().setPozostalo(suma);
    }
    
    public double GetRRozl() {
        return this.transakcjaPK.getRozliczany().getRozliczono();
    }
    
    public void SetRRozl(double suma) {
        transakcjaPK.getRozliczany().setRozliczono(suma);
    }
    
     public double GetRKwotaPier() {
        return this.transakcjaPK.getRozliczany().getKwotapierwotna();
    }
    
    public double GetRPoz() {
        return this.transakcjaPK.getRozliczany().getPozostalo();
    }
    
    public void SetRPoz(double suma) {
        transakcjaPK.getRozliczany().setPozostalo(suma);
    }

    public boolean isZablokujnanoszenie() {
        return zablokujnanoszenie;
    }

    public void setZablokujnanoszenie(boolean zablokujnanoszenie) {
        this.zablokujnanoszenie = zablokujnanoszenie;
    }

    public double getRoznicekursowe() {
        return roznicekursowe;
    }

    public void setRoznicekursowe(double roznicekursowe) {
        this.roznicekursowe = roznicekursowe;
    }

   
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.transakcjaPK);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.kwotatransakcji) ^ (Double.doubleToLongBits(this.kwotatransakcji) >>> 32));
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
        final Transakcja other = (Transakcja) obj;
        if (!Objects.equals(this.transakcjaPK, other.transakcjaPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "transakcjaPK=" + transakcjaPK + ", kwotatransakcji=" + kwotatransakcji + '}';
    }

    public TransakcjaPK getTransakcjaPK() {
        return transakcjaPK;
    }

    public void setTransakcjaPK(TransakcjaPK transakcjaPK) {
        this.transakcjaPK = transakcjaPK;
    }

    public double getKwotatransakcji() {
        return kwotatransakcji;
    }

    public void setKwotatransakcji(double kwotatransakcji) {
        this.kwotatransakcji = kwotatransakcji;
    }

    public double getPoprzedniakwota() {
        return poprzedniakwota;
    }

    public void setPoprzedniakwota(double poprzedniakwota) {
        this.poprzedniakwota = poprzedniakwota;
    }
    
    
    
    
}
