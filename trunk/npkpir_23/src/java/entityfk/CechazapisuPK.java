/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

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
public class CechazapisuPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String nazwacechy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String rodzajcechy;

    public CechazapisuPK() {
    }

    public CechazapisuPK(String nazwacechy, String rodzajcechy) {
        this.nazwacechy = nazwacechy;
        this.rodzajcechy = rodzajcechy;
    }

    public String getNazwacechy() {
        return nazwacechy;
    }

    public void setNazwacechy(String nazwacechy) {
        this.nazwacechy = nazwacechy;
    }

    public String getRodzajcechy() {
        return rodzajcechy;
    }

    public void setRodzajcechy(String rodzajcechy) {
        this.rodzajcechy = rodzajcechy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nazwacechy != null ? nazwacechy.hashCode() : 0);
        hash += (rodzajcechy != null ? rodzajcechy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CechazapisuPK)) {
            return false;
        }
        CechazapisuPK other = (CechazapisuPK) object;
        if ((this.nazwacechy == null && other.nazwacechy != null) || (this.nazwacechy != null && !this.nazwacechy.equals(other.nazwacechy))) {
            return false;
        }
        if ((this.rodzajcechy == null && other.rodzajcechy != null) || (this.rodzajcechy != null && !this.rodzajcechy.equals(other.rodzajcechy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.CechazapisuPK[ nazwacechy=" + nazwacechy + ", rodzajcechy=" + rodzajcechy + " ]";
    }
    
}
