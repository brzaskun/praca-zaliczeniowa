/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;

/**
 *
 * @author Osito
 */
@Entity
public class WierszBO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private WierszBOPK wierszBOPK;
    @MapsId("nippodatnika")
    private Podatnik podatnik;
    @MapsId("idkonta")
    private Konto konto;
    private String opis;
    private double kwotaWn;
    private double kwotaMa;

    public WierszBO() {
    }

    
    public WierszBO(Podatnik podatnik, String rok) {
        this.wierszBOPK = new WierszBOPK();
        this.wierszBOPK.setRok(rok);
        this.podatnik = podatnik;
        this.kwotaWn = 0.0;
        this.kwotaMa = 0.0;
    }

    @Override
    public String toString() {
        return "WierszBO{" + "podatnik=" + podatnik + ", konto=" + konto + ", opis=" + opis + ", kwotaWn=" + kwotaWn + ", kwotaMa=" + kwotaMa + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.wierszBOPK);
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
        final WierszBO other = (WierszBO) obj;
        if (!Objects.equals(this.wierszBOPK, other.wierszBOPK)) {
            return false;
        }
        return true;
    }
    
    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    
    
}
