/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class KontoSumyRok implements Serializable {
    private static final long serialVersionUID = 1L;
    private Konto konto;
    private double styczen;
    private double luty;
    private double marzec;
    private double kwiecien;
    private double maj;
    private double czerwiec;
    private double lipiec;
    private double sierpien;
    private double wrzesien;
    private double pazdziernik;
    private double listopad;
    private double grudzien;

    public KontoSumyRok() {
    }

    public KontoSumyRok(Konto konto) {
        this.konto = konto;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.konto);
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
        final KontoSumyRok other = (KontoSumyRok) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KontoSumyRok{" + "konto=" + konto.getPelnynumer() + ", styczen=" + styczen + ", luty=" + luty + ", marzec=" + marzec + ", kwiecien=" + kwiecien + ", maj=" + maj + ", czerwiec=" + czerwiec + ", lipiec=" + lipiec + ", sierpien=" + sierpien + ", wrzesien=" + wrzesien + ", pazdziernik=" + pazdziernik + ", listopad=" + listopad + ", grudzien=" + grudzien + '}';
    }
    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getStyczen() {
        return styczen;
    }

    public void setStyczen(double styczen) {
        this.styczen = styczen;
    }

    public double getLuty() {
        return luty;
    }

    public void setLuty(double luty) {
        this.luty = luty;
    }

    public double getMarzec() {
        return marzec;
    }

    public void setMarzec(double marzec) {
        this.marzec = marzec;
    }

    public double getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(double kwiecien) {
        this.kwiecien = kwiecien;
    }

    public double getMaj() {
        return maj;
    }

    public void setMaj(double maj) {
        this.maj = maj;
    }

    public double getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(double czerwiec) {
        this.czerwiec = czerwiec;
    }

    public double getLipiec() {
        return lipiec;
    }

    public void setLipiec(double lipiec) {
        this.lipiec = lipiec;
    }

    public double getSierpien() {
        return sierpien;
    }

    public void setSierpien(double sierpien) {
        this.sierpien = sierpien;
    }

    public double getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(double wrzesien) {
        this.wrzesien = wrzesien;
    }

    public double getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(double pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public double getListopad() {
        return listopad;
    }

    public void setListopad(double listopad) {
        this.listopad = listopad;
    }

    public double getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(double grudzien) {
        this.grudzien = grudzien;
    }
    
    public boolean isEmpty() {
        if (this.styczen != 0) {
            return false;
        }
        if (this.luty != 0) {
            return false;
        }
        if (this.marzec != 0) {
            return false;
        }
        if (this.kwiecien != 0) {
            return false;
        }
        if (this.maj != 0) {
            return false;
        }
        if (this.czerwiec != 0) {
            return false;
        }
        if (this.lipiec != 0) {
            return false;
        }
        if (this.sierpien != 0) {
            return false;
        }
        if (this.wrzesien != 0) {
            return false;
        }
        if (this.pazdziernik != 0) {
            return false;
        }
        if (this.listopad != 0) {
            return false;
        }
        if (this.grudzien != 0) {
            return false;
        }
        return true;
    }
    
    
}
