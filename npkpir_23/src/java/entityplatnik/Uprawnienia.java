/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "UPRAWNIENIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uprawnienia.findAll", query = "SELECT u FROM Uprawnienia u"),
    @NamedQuery(name = "Uprawnienia.findByIdUzytkownik", query = "SELECT u FROM Uprawnienia u WHERE u.uprawnieniaPK.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Uprawnienia.findByIdPlatnik", query = "SELECT u FROM Uprawnienia u WHERE u.uprawnieniaPK.idPlatnik = :idPlatnik")})
public class Uprawnienia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UprawnieniaPK uprawnieniaPK;

    public Uprawnienia() {
    }

    public Uprawnienia(UprawnieniaPK uprawnieniaPK) {
        this.uprawnieniaPK = uprawnieniaPK;
    }

    public Uprawnienia(int idUzytkownik, int idPlatnik) {
        this.uprawnieniaPK = new UprawnieniaPK(idUzytkownik, idPlatnik);
    }

    public UprawnieniaPK getUprawnieniaPK() {
        return uprawnieniaPK;
    }

    public void setUprawnieniaPK(UprawnieniaPK uprawnieniaPK) {
        this.uprawnieniaPK = uprawnieniaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uprawnieniaPK != null ? uprawnieniaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uprawnienia)) {
            return false;
        }
        Uprawnienia other = (Uprawnienia) object;
        if ((this.uprawnieniaPK == null && other.uprawnieniaPK != null) || (this.uprawnieniaPK != null && !this.uprawnieniaPK.equals(other.uprawnieniaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Uprawnienia[ uprawnieniaPK=" + uprawnieniaPK + " ]";
    }
    
}
