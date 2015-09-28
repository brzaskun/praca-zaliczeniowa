/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
@Named
@Embeddable
public class ZusstawkiPK implements Serializable {
    private static final long serialVersionUID = 3477872500026598397L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "miesiac")
    private String miesiac;
    @Column(name = "malyzus")
    private boolean malyzus;

    public ZusstawkiPK() {
    }

 
    
    public ZusstawkiPK(String rok, String miesiac) {
        this.rok = rok;
        this.miesiac = miesiac;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public boolean isMalyzus() {
        return malyzus;
    }

    public void setMalyzus(boolean malyzus) {
        this.malyzus = malyzus;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rok != null ? rok.hashCode() : 0);
        hash += (miesiac != null ? miesiac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZusstawkiPK)) {
            return false;
        }
        ZusstawkiPK other = (ZusstawkiPK) object;
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        if ((this.miesiac == null && other.miesiac != null) || (this.miesiac != null && !this.miesiac.equals(other.miesiac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ZusstawkiPK[ rok=" + rok + ", miesiac=" + miesiac + " ]";
    }
    
}
