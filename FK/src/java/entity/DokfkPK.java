/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
public class DokfkPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String symbol;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int nrkolejny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String podatnik;

    public DokfkPK() {
    }

    public DokfkPK(String symbol, int nrkolejny, String podatnik) {
        this.symbol = symbol;
        this.nrkolejny = nrkolejny;
        this.podatnik = podatnik;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(int nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (symbol != null ? symbol.hashCode() : 0);
        hash += (int) nrkolejny;
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DokfkPK)) {
            return false;
        }
        DokfkPK other = (DokfkPK) object;
        if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
            return false;
        }
        if (this.nrkolejny != other.nrkolejny) {
            return false;
        }
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DokfkPK[ symbol=" + symbol + ", nrkolejny=" + nrkolejny + ", podatnik=" + podatnik + " ]";
    }
    
}
