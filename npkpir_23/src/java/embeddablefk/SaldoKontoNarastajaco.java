/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, Obrotymca> obrotymiesiecy;
    private double obrotyBoWn;
    private double obrotyBoMa;
    private double saldoWn;
    private double saldoMa;

    public SaldoKontoNarastajaco() {
        this.obrotymiesiecy = new HashMap<>();
        this.obrotymiesiecy.put("01", new Obrotymca("styczeń"));
        this.obrotymiesiecy.put("02", new Obrotymca("luty"));
        this.obrotymiesiecy.put("03", new Obrotymca("marzec"));
        this.obrotymiesiecy.put("04", new Obrotymca("kwiecień"));
        this.obrotymiesiecy.put("05", new Obrotymca("maj"));
        this.obrotymiesiecy.put("06", new Obrotymca("czerwiec"));
        this.obrotymiesiecy.put("07", new Obrotymca("lipiec"));
        this.obrotymiesiecy.put("08", new Obrotymca("sierpień"));
        this.obrotymiesiecy.put("09", new Obrotymca("wrzesień"));
        this.obrotymiesiecy.put("10", new Obrotymca("październik"));
        this.obrotymiesiecy.put("11", new Obrotymca("listopad"));
        this.obrotymiesiecy.put("12", new Obrotymca("grudzień"));
    }
    
    public SaldoKontoNarastajaco (Konto konto, double saldoWn, double saldoMa) {
        this.obrotymiesiecy = new HashMap<>();
        this.obrotymiesiecy.put("01", new Obrotymca("styczeń"));
        this.obrotymiesiecy.put("02", new Obrotymca("luty"));
        this.obrotymiesiecy.put("03", new Obrotymca("marzec"));
        this.obrotymiesiecy.put("04", new Obrotymca("kwiecień"));
        this.obrotymiesiecy.put("05", new Obrotymca("maj"));
        this.obrotymiesiecy.put("06", new Obrotymca("czerwiec"));
        this.obrotymiesiecy.put("07", new Obrotymca("lipiec"));
        this.obrotymiesiecy.put("08", new Obrotymca("sierpień"));
        this.obrotymiesiecy.put("09", new Obrotymca("wrzesień"));
        this.obrotymiesiecy.put("10", new Obrotymca("październik"));
        this.obrotymiesiecy.put("11", new Obrotymca("listopad"));
        this.obrotymiesiecy.put("12", new Obrotymca("grudzień"));
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
        return "SaldoKontoNarastająco{" + "konto=" + konto + ", obrotyBoWn=" + obrotyBoWn + ", obrotyBoMa=" + obrotyBoMa + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa + '}';
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

    public Map<String, Obrotymca> getObrotymiesiecy() {
        return obrotymiesiecy;
    }

    public void setObrotymiesiecy(Map<String, Obrotymca> obrotymiesiecy) {
        this.obrotymiesiecy = obrotymiesiecy;
    }

   

 
    public void wyliczSaldo() {
        this.saldoWn = this.obrotyBoWn > this.obrotyBoMa ? this.obrotyBoWn - this.obrotyBoMa : 0.0;
        this.saldoMa = this.obrotyBoMa > this.obrotyBoWn ? this.obrotyBoMa - this.obrotyBoWn : 0.0;
    }

    public void sumujBOZapisy() {
        List<Obrotymca> l = new ArrayList<>();
        l.addAll(this.obrotymiesiecy.values());
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

        @Override
        public String toString() {
            return "Obrotymca{" + "nazwamca=" + nazwamca + ", obrotyWn=" + obrotyWn + ", obrotyMa=" + obrotyMa + '}';
        }
        
        
    }
    
    
    
}
