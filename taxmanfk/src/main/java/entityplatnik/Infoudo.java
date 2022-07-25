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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "INFOUDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Infoudo.findAll", query = "SELECT i FROM Infoudo i"),
    @NamedQuery(name = "Infoudo.findById", query = "SELECT i FROM Infoudo i WHERE i.id = :id"),
    @NamedQuery(name = "Infoudo.findByIdPlatnik", query = "SELECT i FROM Infoudo i WHERE i.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Infoudo.findByIdUzytkownik", query = "SELECT i FROM Infoudo i WHERE i.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Infoudo.findByIdZdarzenia", query = "SELECT i FROM Infoudo i WHERE i.idZdarzenia = :idZdarzenia"),
    @NamedQuery(name = "Infoudo.findByCzas", query = "SELECT i FROM Infoudo i WHERE i.czas = :czas"),
    @NamedQuery(name = "Infoudo.findByTyp", query = "SELECT i FROM Infoudo i WHERE i.typ = :typ"),
    @NamedQuery(name = "Infoudo.findByData", query = "SELECT i FROM Infoudo i WHERE i.data = :data"),
    @NamedQuery(name = "Infoudo.findByIdraps", query = "SELECT i FROM Infoudo i WHERE i.idraps = :idraps"),
    @NamedQuery(name = "Infoudo.findByOkrrozl", query = "SELECT i FROM Infoudo i WHERE i.okrrozl = :okrrozl"),
    @NamedQuery(name = "Infoudo.findByIdProgram", query = "SELECT i FROM Infoudo i WHERE i.idProgram = :idProgram"),
    @NamedQuery(name = "Infoudo.findByInserttmp", query = "SELECT i FROM Infoudo i WHERE i.inserttmp = :inserttmp")})
public class Infoudo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UZYTKOWNIK", nullable = false)
    private int idUzytkownik;
    @Column(name = "ID_ZDARZENIA")
    private Integer idZdarzenia;
    @Column(name = "CZAS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date czas;
    @Column(name = "TYP")
    private Integer typ;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Size(max = 2)
    @Column(name = "IDRAPS", length = 2)
    private String idraps;
    @Size(max = 6)
    @Column(name = "OKRROZL", length = 6)
    private String okrrozl;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Infoudo() {
    }

    public Infoudo(Integer id) {
        this.id = id;
    }

    public Infoudo(Integer id, int idPlatnik, int idUzytkownik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
        this.idUzytkownik = idUzytkownik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public int getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(int idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Integer getIdZdarzenia() {
        return idZdarzenia;
    }

    public void setIdZdarzenia(Integer idZdarzenia) {
        this.idZdarzenia = idZdarzenia;
    }

    public Date getCzas() {
        return czas;
    }

    public void setCzas(Date czas) {
        this.czas = czas;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIdraps() {
        return idraps;
    }

    public void setIdraps(String idraps) {
        this.idraps = idraps;
    }

    public String getOkrrozl() {
        return okrrozl;
    }

    public void setOkrrozl(String okrrozl) {
        this.okrrozl = okrrozl;
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
        if (!(object instanceof Infoudo)) {
            return false;
        }
        Infoudo other = (Infoudo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Infoudo[ id=" + id + " ]";
    }
    
}
