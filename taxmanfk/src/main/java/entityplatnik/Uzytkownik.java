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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UZYTKOWNIK", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"LOGIN"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uzytkownik.findAll", query = "SELECT u FROM Uzytkownik u"),
    @NamedQuery(name = "Uzytkownik.findById", query = "SELECT u FROM Uzytkownik u WHERE u.id = :id"),
    @NamedQuery(name = "Uzytkownik.findByLogin", query = "SELECT u FROM Uzytkownik u WHERE u.login = :login"),
    @NamedQuery(name = "Uzytkownik.findByImie", query = "SELECT u FROM Uzytkownik u WHERE u.imie = :imie"),
    @NamedQuery(name = "Uzytkownik.findByNazwisko", query = "SELECT u FROM Uzytkownik u WHERE u.nazwisko = :nazwisko"),
    @NamedQuery(name = "Uzytkownik.findByHaslo", query = "SELECT u FROM Uzytkownik u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uzytkownik.findByDataHasla", query = "SELECT u FROM Uzytkownik u WHERE u.dataHasla = :dataHasla"),
    @NamedQuery(name = "Uzytkownik.findByIdPlatnik", query = "SELECT u FROM Uzytkownik u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Uzytkownik.findByStatus", query = "SELECT u FROM Uzytkownik u WHERE u.status = :status"),
    @NamedQuery(name = "Uzytkownik.findByInserttmp", query = "SELECT u FROM Uzytkownik u WHERE u.inserttmp = :inserttmp")})
public class Uzytkownik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "LOGIN", nullable = false, length = 14)
    private String login;
    @Size(max = 30)
    @Column(name = "IMIE", length = 30)
    private String imie;
    @Size(max = 30)
    @Column(name = "NAZWISKO", length = 30)
    private String nazwisko;
    @Size(max = 48)
    @Column(name = "HASLO", length = 48)
    private String haslo;
    @Column(name = "DATA_HASLA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHasla;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Uzytkownik() {
    }

    public Uzytkownik(Integer id) {
        this.id = id;
    }

    public Uzytkownik(Integer id, String login) {
        this.id = id;
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public Date getDataHasla() {
        return dataHasla;
    }

    public void setDataHasla(Date dataHasla) {
        this.dataHasla = dataHasla;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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
        if (!(object instanceof Uzytkownik)) {
            return false;
        }
        Uzytkownik other = (Uzytkownik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Uzytkownik[ id=" + id + " ]";
    }
    
}
