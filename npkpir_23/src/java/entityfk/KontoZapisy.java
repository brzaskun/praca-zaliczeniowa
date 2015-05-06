/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class KontoZapisy {
    private Konto konto;
    private List<StronaWiersza> stronywiersza;
    private double sumawn;
    private double sumama;
    private double saldown;
    private double saldoma;

    public KontoZapisy() {
        this.stronywiersza = new ArrayList<>();
    }

    public KontoZapisy(Konto konto) {
        this.stronywiersza = new ArrayList<>();
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
    
    
    
    
}
