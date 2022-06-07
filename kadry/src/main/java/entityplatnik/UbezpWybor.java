/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "UBEZP_WYBOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpWybor.findAll", query = "SELECT u FROM UbezpWybor u"),
    @NamedQuery(name = "UbezpWybor.findByIdUbezpieczony", query = "SELECT u FROM UbezpWybor u WHERE u.ubezpWyborPK.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpWybor.findByIdProgram", query = "SELECT u FROM UbezpWybor u WHERE u.ubezpWyborPK.idProgram = :idProgram"),
    @NamedQuery(name = "UbezpWybor.findByWybor", query = "SELECT u FROM UbezpWybor u WHERE u.wybor = :wybor")})
public class UbezpWybor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UbezpWyborPK ubezpWyborPK;
    @Column(name = "WYBOR")
    private Character wybor;

    public UbezpWybor() {
    }

    public UbezpWybor(UbezpWyborPK ubezpWyborPK) {
        this.ubezpWyborPK = ubezpWyborPK;
    }

    public UbezpWybor(int idUbezpieczony, int idProgram) {
        this.ubezpWyborPK = new UbezpWyborPK(idUbezpieczony, idProgram);
    }

    public UbezpWyborPK getUbezpWyborPK() {
        return ubezpWyborPK;
    }

    public void setUbezpWyborPK(UbezpWyborPK ubezpWyborPK) {
        this.ubezpWyborPK = ubezpWyborPK;
    }

    public Character getWybor() {
        return wybor;
    }

    public void setWybor(Character wybor) {
        this.wybor = wybor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ubezpWyborPK != null ? ubezpWyborPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpWybor)) {
            return false;
        }
        UbezpWybor other = (UbezpWybor) object;
        if ((this.ubezpWyborPK == null && other.ubezpWyborPK != null) || (this.ubezpWyborPK != null && !this.ubezpWyborPK.equals(other.ubezpWyborPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpWybor[ ubezpWyborPK=" + ubezpWyborPK + " ]";
    }
    
}
