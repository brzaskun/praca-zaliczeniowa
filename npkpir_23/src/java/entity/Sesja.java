/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "sesja",uniqueConstraints = {@UniqueConstraint(columnNames={"nrsesji"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sesja.findAll", query = "SELECT s FROM Sesja s"),
    @NamedQuery(name = "Sesja.findById", query = "SELECT s FROM Sesja s WHERE s.id = :id"),
    @NamedQuery(name = "Sesja.findByIloscdokumentow", query = "SELECT s FROM Sesja s WHERE s.iloscdokumentow = :iloscdokumentow"),
    @NamedQuery(name = "Sesja.findByIloscmaili", query = "SELECT s FROM Sesja s WHERE s.iloscmaili = :iloscmaili"),
    @NamedQuery(name = "Sesja.findByIloscwydrukow", query = "SELECT s FROM Sesja s WHERE s.iloscwydrukow = :iloscwydrukow"),
    @NamedQuery(name = "Sesja.findByNrsesji", query = "SELECT s FROM Sesja s WHERE s.nrsesji = :nrsesji"),
    @NamedQuery(name = "Sesja.findByZalogowani", query = "SELECT s FROM Sesja s WHERE s.wylogowanie IS NULL"),
    @NamedQuery(name = "Sesja.findByUzytkownik", query = "SELECT s FROM Sesja s WHERE s.uzytkownik = :uzytkownik"),
    @NamedQuery(name = "Sesja.findByWylogowanie", query = "SELECT s FROM Sesja s WHERE s.wylogowanie = :wylogowanie"),
    @NamedQuery(name = "Sesja.findByZalogowanie", query = "SELECT s FROM Sesja s WHERE s.zalogowanie = :zalogowanie")})
public class Sesja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "iloscdokumentow")
    private int iloscdokumentow;
    @Column(name = "iloscmaili")
    private int iloscmaili;
    @Column(name = "iloscwydrukow")
    private int iloscwydrukow;
    @Size(max = 255)
    @Column(name = "nrsesji")
    private String nrsesji;
    @Size(max = 255)
    @Column(name = "uzytkownik")
    private String uzytkownik;
    @Column(name = "zalogowanie")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zalogowanie;
    @Column(name = "wylogowanie")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wylogowanie;
    @Column(name = "ip")
    private String ip;

    public Sesja() {
    }

    public Sesja(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIloscdokumentow() {
        return iloscdokumentow;
    }

    public void setIloscdokumentow(int iloscdokumentow) {
        this.iloscdokumentow = iloscdokumentow;
    }

    public int getIloscmaili() {
        return iloscmaili;
    }

    public void setIloscmaili(int iloscmaili) {
        this.iloscmaili = iloscmaili;
    }

    public int getIloscwydrukow() {
        return iloscwydrukow;
    }

    public void setIloscwydrukow(int iloscwydrukow) {
        this.iloscwydrukow = iloscwydrukow;
    }

    public String getNrsesji() {
        return nrsesji;
    }

    public void setNrsesji(String nrsesji) {
        this.nrsesji = nrsesji;
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Date getZalogowanie() {
        return zalogowanie;
    }

    public void setZalogowanie(Date zalogowanie) {
        this.zalogowanie = zalogowanie;
    }

    public Date getWylogowanie() {
        return wylogowanie;
    }

    public void setWylogowanie(Date wylogowanie) {
        this.wylogowanie = wylogowanie;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        if (!(object instanceof Sesja)) {
            return false;
        }
        Sesja other = (Sesja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sesja[ id=" + id + " ]";
    }
    
}
