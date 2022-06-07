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
public class UbezpWyborPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGRAM", nullable = false)
    private int idProgram;

    public UbezpWyborPK() {
    }

    public UbezpWyborPK(int idUbezpieczony, int idProgram) {
        this.idUbezpieczony = idUbezpieczony;
        this.idProgram = idProgram;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
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
        hash += (int) idUbezpieczony;
        hash += (int) idProgram;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpWyborPK)) {
            return false;
        }
        UbezpWyborPK other = (UbezpWyborPK) object;
        if (this.idUbezpieczony != other.idUbezpieczony) {
            return false;
        }
        if (this.idProgram != other.idProgram) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpWyborPK[ idUbezpieczony=" + idUbezpieczony + ", idProgram=" + idProgram + " ]";
    }
    
}
