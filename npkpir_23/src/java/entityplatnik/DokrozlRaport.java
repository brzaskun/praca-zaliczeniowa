/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "DOKROZL_RAPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DokrozlRaport.findAll", query = "SELECT d FROM DokrozlRaport d"),
    @NamedQuery(name = "DokrozlRaport.findByIdDokument", query = "SELECT d FROM DokrozlRaport d WHERE d.idDokument = :idDokument"),
    @NamedQuery(name = "DokrozlRaport.findByIdPlatnik", query = "SELECT d FROM DokrozlRaport d WHERE d.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "DokrozlRaport.findByIdKomplet", query = "SELECT d FROM DokrozlRaport d WHERE d.idKomplet = :idKomplet"),
    @NamedQuery(name = "DokrozlRaport.findByIdDokZus", query = "SELECT d FROM DokrozlRaport d WHERE d.idDokZus = :idDokZus"),
    @NamedQuery(name = "DokrozlRaport.findByIdPlZus", query = "SELECT d FROM DokrozlRaport d WHERE d.idPlZus = :idPlZus"),
    @NamedQuery(name = "DokrozlRaport.findByIdKomplZus", query = "SELECT d FROM DokrozlRaport d WHERE d.idKomplZus = :idKomplZus"),
    @NamedQuery(name = "DokrozlRaport.findByPodnrKompletu", query = "SELECT d FROM DokrozlRaport d WHERE d.podnrKompletu = :podnrKompletu"),
    @NamedQuery(name = "DokrozlRaport.findByTyp", query = "SELECT d FROM DokrozlRaport d WHERE d.typ = :typ"),
    @NamedQuery(name = "DokrozlRaport.findByI11", query = "SELECT d FROM DokrozlRaport d WHERE d.i11 = :i11"),
    @NamedQuery(name = "DokrozlRaport.findByI12", query = "SELECT d FROM DokrozlRaport d WHERE d.i12 = :i12"),
    @NamedQuery(name = "DokrozlRaport.findByInserttmp", query = "SELECT d FROM DokrozlRaport d WHERE d.inserttmp = :inserttmp")})
public class DokrozlRaport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_KOMPLET")
    private Integer idKomplet;
    @Column(name = "ID_DOK_ZUS")
    private Integer idDokZus;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_KOMPL_ZUS")
    private Integer idKomplZus;
    @Column(name = "PODNR_KOMPLETU")
    private Integer podnrKompletu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP", nullable = false)
    private int typ;
    @Column(name = "I_1_1")
    private Integer i11;
    @Column(name = "I_1_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i12;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public DokrozlRaport() {
    }

    public DokrozlRaport(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public DokrozlRaport(Integer idDokument, int typ) {
        this.idDokument = idDokument;
        this.typ = typ;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdKomplet() {
        return idKomplet;
    }

    public void setIdKomplet(Integer idKomplet) {
        this.idKomplet = idKomplet;
    }

    public Integer getIdDokZus() {
        return idDokZus;
    }

    public void setIdDokZus(Integer idDokZus) {
        this.idDokZus = idDokZus;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdKomplZus() {
        return idKomplZus;
    }

    public void setIdKomplZus(Integer idKomplZus) {
        this.idKomplZus = idKomplZus;
    }

    public Integer getPodnrKompletu() {
        return podnrKompletu;
    }

    public void setPodnrKompletu(Integer podnrKompletu) {
        this.podnrKompletu = podnrKompletu;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public Integer getI11() {
        return i11;
    }

    public void setI11(Integer i11) {
        this.i11 = i11;
    }

    public Date getI12() {
        return i12;
    }

    public void setI12(Date i12) {
        this.i12 = i12;
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
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DokrozlRaport)) {
            return false;
        }
        DokrozlRaport other = (DokrozlRaport) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.DokrozlRaport[ idDokument=" + idDokument + " ]";
    }
    
}
