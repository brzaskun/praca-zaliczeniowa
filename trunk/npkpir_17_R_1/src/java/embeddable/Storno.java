/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Klienci;
import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
@Named
public class Storno implements Serializable {
    @Column(name = "id_dok")
    private Long idDok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nr_wl_dk")
    private String nrWlDk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kontr")
    private Klienci kontr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "podatnik")
    private String podatnik;
    @Column(name = "kwota")
    private Double kwota;
    @Column(name = "kwota")
    private Double vat;

    public Storno() {
    }

    public Storno(Long idDok, String nrWlDk, Klienci kontr, String podatnik, Double kwota, Double vat) {
        this.idDok = idDok;
        this.nrWlDk = nrWlDk;
        this.kontr = kontr;
        this.podatnik = podatnik;
        this.kwota = kwota;
        this.vat = vat;
    }

    public Long getIdDok() {
        return idDok;
    }

    public void setIdDok(Long idDok) {
        this.idDok = idDok;
    }

    public String getNrWlDk() {
        return nrWlDk;
    }

    public void setNrWlDk(String nrWlDk) {
        this.nrWlDk = nrWlDk;
    }

    public Klienci getKontr() {
        return kontr;
    }

    public void setKontr(Klienci kontr) {
        this.kontr = kontr;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idDok);
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
        final Storno other = (Storno) obj;
        if (!Objects.equals(this.idDok, other.idDok)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storno{" + "idDok=" + idDok + ", nrWlDk=" + nrWlDk + ", kontr=" + kontr + ", podatnik=" + podatnik + ", kwota=" + kwota + ", vat=" + vat + '}';
    }
    
    
   
}
