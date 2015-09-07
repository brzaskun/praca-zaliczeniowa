/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
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
    @Size(min = 1, max = 15)
    @Column(name = "seriadokfk", nullable = false, length = 15)
    private String seriadokfk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nrkolejnywserii", nullable = false)
    private int nrkolejnywserii;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik", nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;

    public DokfkPK() {
    }

    public DokfkPK(String seriadokfk, int nrkolejny, String podatnik, String rok) {
        this.seriadokfk = seriadokfk;
        this.nrkolejnywserii = nrkolejny;
        this.podatnik = podatnik;
        this.rok = rok;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getSeriadokfk() {
        return seriadokfk;
    }
    
    public void setSeriadokfk(String seriadokfk) {
        this.seriadokfk = seriadokfk;
    }
    
    public int getNrkolejnywserii() {
        return nrkolejnywserii;
    }
    
    public void setNrkolejnywserii(int nrkolejnywserii) {
        this.nrkolejnywserii = nrkolejnywserii;
    }
    
    public String getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }
    
    public String getRok() {
        return rok;
    }
    
    public void setRok(String rok) {
        this.rok = rok;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.seriadokfk);
        hash = 97 * hash + this.nrkolejnywserii;
        hash = 97 * hash + Objects.hashCode(this.podatnik);
        hash = 97 * hash + Objects.hashCode(this.rok);
        return hash;
    }
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DokfkPK)) {
            return false;
        }
        DokfkPK other = (DokfkPK) object;
        if ((this.seriadokfk == null && other.seriadokfk != null) || (this.seriadokfk != null && !this.seriadokfk.equals(other.seriadokfk))) {
            return false;
        }
        if (this.nrkolejnywserii != other.nrkolejnywserii) {
            return false;
        }
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "seriadokfk: " + seriadokfk + ", nrkolejnywserii: " + nrkolejnywserii + ", podatnik: " + podatnik + ", rok: " + rok +" ";
    }

    public String toString2() {
        return seriadokfk + "/" + nrkolejnywserii + "/" + rok;
    }
    

    
    
}
