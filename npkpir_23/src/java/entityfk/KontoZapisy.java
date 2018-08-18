/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class KontoZapisy  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Konto konto;
    private List<StronaWiersza> stronywiersza;
    private double sumawn;
    private double sumama;
    private double saldown;
    private double saldoma;

    public KontoZapisy() {
        this.stronywiersza = Collections.synchronizedList(new ArrayList<>());
    }

    public KontoZapisy(Konto konto) {
        this.stronywiersza = Collections.synchronizedList(new ArrayList<>());
        this.konto = konto;
    }
    
    public Konto getKonto() {
        return konto;
    }
  
    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public List<StronaWiersza> getStronywiersza() {
        return stronywiersza;
    }

    public void setStronywiersza(List<StronaWiersza> stronywiersza) {
        this.stronywiersza = stronywiersza;
    }

    public double getSumawn() {
        return sumawn;
    }

    public void setSumawn(double sumawn) {
        this.sumawn = sumawn;
    }

    public double getSumama() {
        return sumama;
    }

    public void setSumama(double sumama) {
        this.sumama = sumama;
    }

    public double getSaldown() {
        return saldown;
    }

    public void setSaldown(double saldown) {
        this.saldown = saldown;
    }

    public double getSaldoma() {
        return saldoma;
    }

    public void setSaldoma(double saldoma) {
        this.saldoma = saldoma;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.konto);
        hash = 97 * hash + Objects.hashCode(this.stronywiersza);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.sumawn) ^ (Double.doubleToLongBits(this.sumawn) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.sumama) ^ (Double.doubleToLongBits(this.sumama) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.saldown) ^ (Double.doubleToLongBits(this.saldown) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.saldoma) ^ (Double.doubleToLongBits(this.saldoma) >>> 32));
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
        final KontoZapisy other = (KontoZapisy) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        if (!Objects.equals(this.stronywiersza, other.stronywiersza)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sumawn) != Double.doubleToLongBits(other.sumawn)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sumama) != Double.doubleToLongBits(other.sumama)) {
            return false;
        }
        if (Double.doubleToLongBits(this.saldown) != Double.doubleToLongBits(other.saldown)) {
            return false;
        }
        if (Double.doubleToLongBits(this.saldoma) != Double.doubleToLongBits(other.saldoma)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
