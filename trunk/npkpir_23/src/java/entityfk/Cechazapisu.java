/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cechazapisu.findAll", query = "SELECT c FROM Cechazapisu c"),
    @NamedQuery(name = "Cechazapisu.findByNazwacechy", query = "SELECT c FROM Cechazapisu c WHERE c.cechazapisuPK.nazwacechy = :nazwacechy"),
    @NamedQuery(name = "Cechazapisu.findByRodzajcechy", query = "SELECT c FROM Cechazapisu c WHERE c.cechazapisuPK.rodzajcechy = :rodzajcechy")})
public class Cechazapisu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CechazapisuPK cechazapisuPK;

    public Cechazapisu() {
    }

    public Cechazapisu(CechazapisuPK cechazapisuPK) {
        this.cechazapisuPK = cechazapisuPK;
    }

    public Cechazapisu(String nazwacechy, String rodzajcechy) {
        this.cechazapisuPK = new CechazapisuPK(nazwacechy, rodzajcechy);
    }

    public CechazapisuPK getCechazapisuPK() {
        return cechazapisuPK;
    }

    public void setCechazapisuPK(CechazapisuPK cechazapisuPK) {
        this.cechazapisuPK = cechazapisuPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cechazapisuPK != null ? cechazapisuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cechazapisu)) {
            return false;
        }
        Cechazapisu other = (Cechazapisu) object;
        if ((this.cechazapisuPK == null && other.cechazapisuPK != null) || (this.cechazapisuPK != null && !this.cechazapisuPK.equals(other.cechazapisuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Cechazapisu[ cechazapisuPK=" + cechazapisuPK + " ]";
    }
    
}
