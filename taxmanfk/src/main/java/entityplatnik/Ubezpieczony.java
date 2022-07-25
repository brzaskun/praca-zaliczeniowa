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
@Table(name = "UBEZPIECZONY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubezpieczony.findAll", query = "SELECT u FROM Ubezpieczony u"),
    @NamedQuery(name = "Ubezpieczony.findById", query = "SELECT u FROM Ubezpieczony u WHERE u.id = :id"),
    @NamedQuery(name = "Ubezpieczony.findByIdPlatnik", query = "SELECT u FROM Ubezpieczony u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Ubezpieczony.findByAtrybutI", query = "SELECT u FROM Ubezpieczony u WHERE u.atrybutI = :atrybutI"),
    @NamedQuery(name = "Ubezpieczony.findByAtrybutIi", query = "SELECT u FROM Ubezpieczony u WHERE u.atrybutIi = :atrybutIi"),
    @NamedQuery(name = "Ubezpieczony.findByStatuswr", query = "SELECT u FROM Ubezpieczony u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "Ubezpieczony.findByStatuspt", query = "SELECT u FROM Ubezpieczony u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "Ubezpieczony.findByIdUzytkownik", query = "SELECT u FROM Ubezpieczony u WHERE u.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Ubezpieczony.findByDataUtw", query = "SELECT u FROM Ubezpieczony u WHERE u.dataUtw = :dataUtw"),
    @NamedQuery(name = "Ubezpieczony.findByInserttmp", query = "SELECT u FROM Ubezpieczony u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "Ubezpieczony.findByIdPlZus", query = "SELECT u FROM Ubezpieczony u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "Ubezpieczony.findByIdUbZus", query = "SELECT u FROM Ubezpieczony u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "Ubezpieczony.findByStatusPotwWZus", query = "SELECT u FROM Ubezpieczony u WHERE u.statusPotwWZus = :statusPotwWZus"),
    @NamedQuery(name = "Ubezpieczony.findByZnacznikCzasu", query = "SELECT u FROM Ubezpieczony u WHERE u.znacznikCzasu = :znacznikCzasu"),
    @NamedQuery(name = "Ubezpieczony.findByDataPotwWZus", query = "SELECT u FROM Ubezpieczony u WHERE u.dataPotwWZus = :dataPotwWZus"),
    @NamedQuery(name = "Ubezpieczony.findByStatusWerAut", query = "SELECT u FROM Ubezpieczony u WHERE u.statusWerAut = :statusWerAut"),
    @NamedQuery(name = "Ubezpieczony.findBySkrotDanych", query = "SELECT u FROM Ubezpieczony u WHERE u.skrotDanych = :skrotDanych")})
public class Ubezpieczony implements Serializable {

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
    @Column(name = "ATRYBUT_I")
    private Integer atrybutI;
    @Column(name = "ATRYBUT_II")
    private Integer atrybutIi;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Column(name = "DATA_UTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtw;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "STATUS_POTW_W_ZUS")
    private Character statusPotwWZus;
    @Column(name = "ZNACZNIK_CZASU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date znacznikCzasu;
    @Column(name = "DATA_POTW_W_ZUS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotwWZus;
    @Column(name = "STATUS_WER_AUT")
    private Character statusWerAut;
    @Size(max = 40)
    @Column(name = "SKROT_DANYCH", length = 40)
    private String skrotDanych;

    public Ubezpieczony() {
    }

    public Ubezpieczony(Integer id) {
        this.id = id;
    }

    public Ubezpieczony(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
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

    public Integer getAtrybutI() {
        return atrybutI;
    }

    public void setAtrybutI(Integer atrybutI) {
        this.atrybutI = atrybutI;
    }

    public Integer getAtrybutIi() {
        return atrybutIi;
    }

    public void setAtrybutIi(Integer atrybutIi) {
        this.atrybutIi = atrybutIi;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Date getDataUtw() {
        return dataUtw;
    }

    public void setDataUtw(Date dataUtw) {
        this.dataUtw = dataUtw;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Character getStatusPotwWZus() {
        return statusPotwWZus;
    }

    public void setStatusPotwWZus(Character statusPotwWZus) {
        this.statusPotwWZus = statusPotwWZus;
    }

    public Date getZnacznikCzasu() {
        return znacznikCzasu;
    }

    public void setZnacznikCzasu(Date znacznikCzasu) {
        this.znacznikCzasu = znacznikCzasu;
    }

    public Date getDataPotwWZus() {
        return dataPotwWZus;
    }

    public void setDataPotwWZus(Date dataPotwWZus) {
        this.dataPotwWZus = dataPotwWZus;
    }

    public Character getStatusWerAut() {
        return statusWerAut;
    }

    public void setStatusWerAut(Character statusWerAut) {
        this.statusWerAut = statusWerAut;
    }

    public String getSkrotDanych() {
        return skrotDanych;
    }

    public void setSkrotDanych(String skrotDanych) {
        this.skrotDanych = skrotDanych;
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
        if (!(object instanceof Ubezpieczony)) {
            return false;
        }
        Ubezpieczony other = (Ubezpieczony) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Ubezpieczony[ id=" + id + " ]";
    }
    
}
