/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Osito
 */
@Embeddable
public class UprawnieniaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UZYTKOWNIK", nullable = false)
    private int idUzytkownik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;

    public UprawnieniaPK() {
    }

    public UprawnieniaPK(int idUzytkownik, int idPlatnik) {
        this.idUzytkownik = idUzytkownik;
        this.idPlatnik = idPlatnik;
    }

    public int getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(int idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUzytkownik;
        hash += (int) idPlatnik;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UprawnieniaPK)) {
            return false;
        }
        UprawnieniaPK other = (UprawnieniaPK) object;
        if (this.idUzytkownik != other.idUzytkownik) {
            return false;
        }
        if (this.idPlatnik != other.idPlatnik) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UprawnieniaPK[ idUzytkownik=" + idUzytkownik + ", idPlatnik=" + idPlatnik + " ]";
    }
    
}
