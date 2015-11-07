/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
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
public class SaldoKontoNarastajaco implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Konto konto;
    private double boWn;
    private double boMa;
    private List<Obrotymca> obrotymiesiecy;
    private double obrotyBoWn;
    private double obrotyBoMa;
    private double saldoWn;
    private double saldoMa;

    public SaldoKontoNarastajaco() {
        this.obrotymiesiecy = new ArrayList<>();
    }
    
    public SaldoKontoNarastajaco (Konto konto, double saldoWn, double saldoMa) {
        this.obrotymiesiecy = new ArrayList<>();
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        
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
        final SaldoKontoNarastajaco other = (SaldoKontoNarastajaco) obj;
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

    public List<Obrotymca> getObrotymiesiecy() {
        return obrotymiesiecy;
    }

    public void setObrotymiesiecy(List<Obrotymca> obrotymiesiecy) {
        this.obrotymiesiecy = obrotymiesiecy;
    }
    

 
    public void wyliczSaldo() {
        this.saldoWn = this.obrotyBoWn > this.obrotyBoMa ? this.obrotyBoWn - this.obrotyBoMa : 0.0;
        this.saldoMa = this.obrotyBoMa > this.obrotyBoWn ? this.obrotyBoMa - this.obrotyBoWn : 0.0;
    }

    public void sumujBOZapisy() {
        List<Obrotymca> l = this.obrotymiesiecy;
        double obrotyWn = 0.0;
        double obrotyMa = 0.0;
        for (Obrotymca p : l) {
            obrotyWn += p.getObrotyWn();
            obrotyMa += p.getObrotyMa();
        }
        this.obrotyBoWn = this.boWn + obrotyWn;
        this.obrotyBoMa = this.boMa + obrotyMa;
    }

    public static class Obrotymca implements Serializable {
    private static final long serialVersionUID = 1L;
        private String nazwamca;
        private double obrotyWn;
        private double obrotyMa;
    
        public Obrotymca() {
        }

        public Obrotymca(String nazwamca) {
            this.nazwamca = nazwamca;
        }
        
        

        public String getNazwamca() {
            return nazwamca;
        }

        public void setNazwamca(String nazwamca) {
            this.nazwamca = nazwamca;
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
        
        
    }
    
    
    
}
