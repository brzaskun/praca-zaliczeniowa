/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "INFOUDO_UBEZP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InfoudoUbezp.findAll", query = "SELECT i FROM InfoudoUbezp i"),
    @NamedQuery(name = "InfoudoUbezp.findById", query = "SELECT i FROM InfoudoUbezp i WHERE i.id = :id"),
    @NamedQuery(name = "InfoudoUbezp.findByIdInfoudo", query = "SELECT i FROM InfoudoUbezp i WHERE i.idInfoudo = :idInfoudo"),
    @NamedQuery(name = "InfoudoUbezp.findByIdUbezpieczony", query = "SELECT i FROM InfoudoUbezp i WHERE i.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "InfoudoUbezp.findByIdProgram", query = "SELECT i FROM InfoudoUbezp i WHERE i.idProgram = :idProgram"),
    @NamedQuery(name = "InfoudoUbezp.findByInserttmp", query = "SELECT i FROM InfoudoUbezp i WHERE i.inserttmp = :inserttmp")})
public class InfoudoUbezp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INFOUDO", nullable = false)
    private int idInfoudo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public InfoudoUbezp() {
    }

    public InfoudoUbezp(Integer id) {
        this.id = id;
    }

    public InfoudoUbezp(Integer id, int idInfoudo, int idUbezpieczony) {
        this.id = id;
        this.idInfoudo = idInfoudo;
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdInfoudo() {
        return idInfoudo;
    }

    public void setIdInfoudo(int idInfoudo) {
        this.idInfoudo = idInfoudo;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoudoUbezp)) {
            return false;
        }
        InfoudoUbezp other = (InfoudoUbezp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.InfoudoUbezp[ id=" + id + " ]";
    }
    
}
