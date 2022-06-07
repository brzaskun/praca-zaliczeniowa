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
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class HistoriaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UZYTKOWNIK", nullable = false)
    private int idUzytkownik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "HASLO", nullable = false, length = 50)
    private String haslo;

    public HistoriaPK() {
    }

    public HistoriaPK(int idUzytkownik, String haslo) {
        this.idUzytkownik = idUzytkownik;
        this.haslo = haslo;
    }

    public int getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(int idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUzytkownik;
        hash += (haslo != null ? haslo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaPK)) {
            return false;
        }
        HistoriaPK other = (HistoriaPK) object;
        if (this.idUzytkownik != other.idUzytkownik) {
            return false;
        }
        if ((this.haslo == null && other.haslo != null) || (this.haslo != null && !this.haslo.equals(other.haslo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.HistoriaPK[ idUzytkownik=" + idUzytkownik + ", haslo=" + haslo + " ]";
    }
    
}
