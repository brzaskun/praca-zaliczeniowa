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
public class TmpUbezpieczeniPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UB_ZUS", nullable = false)
    private int idUbZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGRAM", nullable = false)
    private int idProgram;

    public TmpUbezpieczeniPK() {
    }

    public TmpUbezpieczeniPK(int idUbZus, int idProgram) {
        this.idUbZus = idUbZus;
        this.idProgram = idProgram;
    }

    public int getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(int idUbZus) {
        this.idUbZus = idUbZus;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUbZus;
        hash += (int) idProgram;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpUbezpieczeniPK)) {
            return false;
        }
        TmpUbezpieczeniPK other = (TmpUbezpieczeniPK) object;
        if (this.idUbZus != other.idUbZus) {
            return false;
        }
        if (this.idProgram != other.idProgram) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.TmpUbezpieczeniPK[ idUbZus=" + idUbZus + ", idProgram=" + idProgram + " ]";
    }
    
}
