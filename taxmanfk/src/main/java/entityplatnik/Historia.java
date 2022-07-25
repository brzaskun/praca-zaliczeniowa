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
@Table(name = "HISTORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historia.findAll", query = "SELECT h FROM Historia h"),
    @NamedQuery(name = "Historia.findByIdUzytkownik", query = "SELECT h FROM Historia h WHERE h.historiaPK.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Historia.findByHaslo", query = "SELECT h FROM Historia h WHERE h.historiaPK.haslo = :haslo"),
    @NamedQuery(name = "Historia.findByInserttmp", query = "SELECT h FROM Historia h WHERE h.inserttmp = :inserttmp")})
public class Historia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoriaPK historiaPK;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Historia() {
    }

    public Historia(HistoriaPK historiaPK) {
        this.historiaPK = historiaPK;
    }

    public Historia(int idUzytkownik, String haslo) {
        this.historiaPK = new HistoriaPK(idUzytkownik, haslo);
    }

    public HistoriaPK getHistoriaPK() {
        return historiaPK;
    }

    public void setHistoriaPK(HistoriaPK historiaPK) {
        this.historiaPK = historiaPK;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historiaPK != null ? historiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historia)) {
            return false;
        }
        Historia other = (Historia) object;
        if ((this.historiaPK == null && other.historiaPK != null) || (this.historiaPK != null && !this.historiaPK.equals(other.historiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Historia[ historiaPK=" + historiaPK + " ]";
    }
    
}
