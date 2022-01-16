/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t"),
    @NamedQuery(name = "Test.findById", query = "SELECT t FROM Test t WHERE t.id = :id"),
    @NamedQuery(name = "Test.findByNazwatest", query = "SELECT t FROM Test t WHERE t.nazwatest = :nazwatest"),
    @NamedQuery(name = "Test.findByTresc", query = "SELECT t FROM Test t WHERE t.tresc = :tresc"),
    @NamedQuery(name = "Test.findByRodzaj", query = "SELECT t FROM Test t WHERE t.rodzaj = :rodzaj"),
    @NamedQuery(name = "Test.findByPytanie", query = "SELECT t FROM Test t WHERE t.pytanie = :pytanie"),
    @NamedQuery(name = "Test.findByOdp1", query = "SELECT t FROM Test t WHERE t.odp1 = :odp1"),
    @NamedQuery(name = "Test.findByOdp2", query = "SELECT t FROM Test t WHERE t.odp2 = :odp2"),
    @NamedQuery(name = "Test.findByOdp3", query = "SELECT t FROM Test t WHERE t.odp3 = :odp3"),
    @NamedQuery(name = "Test.findByOdp4", query = "SELECT t FROM Test t WHERE t.odp4 = :odp4"),
    @NamedQuery(name = "Test.findByOdp1walidacja", query = "SELECT t FROM Test t WHERE t.odp1walidacja = :odp1walidacja"),
    @NamedQuery(name = "Test.findByOdp2walidacja", query = "SELECT t FROM Test t WHERE t.odp2walidacja = :odp2walidacja"),
    @NamedQuery(name = "Test.findByOdp3walidacja", query = "SELECT t FROM Test t WHERE t.odp3walidacja = :odp3walidacja"),
    @NamedQuery(name = "Test.findByOdp4walidacja", query = "SELECT t FROM Test t WHERE t.odp4walidacja = :odp4walidacja")})
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "nazwatest", length = 100)
    private String nazwatest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tresc", nullable = false, length = 255)
    private String tresc;
    @Column(name = "rodzaj")
    private Integer rodzaj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "pytanie", nullable = false, length = 512)
    private String pytanie;
    @Size(max = 512)
    @Column(name = "odp1", length = 512)
    private String odp1;
    @Size(max = 512)
    @Column(name = "odp2", length = 512)
    private String odp2;
    @Size(max = 512)
    @Column(name = "odp3", length = 512)
    private String odp3;
    @Size(max = 512)
    @Column(name = "odp4", length = 512)
    private String odp4;
    @Size(max = 5)
    @Column(name = "odp1walidacja", length = 5)
    private String odp1walidacja;
    @Size(max = 5)
    @Column(name = "odp2walidacja", length = 5)
    private String odp2walidacja;
    @Size(max = 5)
    @Column(name = "odp3walidacja", length = 5)
    private String odp3walidacja;
    @Size(max = 5)
    @Column(name = "odp4walidacja", length = 5)
    private String odp4walidacja;

    public Test() {
    }

    public Test(Integer id) {
        this.id = id;
    }

    public Test(Integer id, String tresc, String pytanie) {
        this.id = id;
        this.tresc = tresc;
        this.pytanie = pytanie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwatest() {
        return nazwatest;
    }

    public void setNazwatest(String nazwatest) {
        this.nazwatest = nazwatest;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Integer getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Integer rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getPytanie() {
        return pytanie;
    }

    public void setPytanie(String pytanie) {
        this.pytanie = pytanie;
    }

    public String getOdp1() {
        return odp1;
    }

    public void setOdp1(String odp1) {
        this.odp1 = odp1;
    }

    public String getOdp2() {
        return odp2;
    }

    public void setOdp2(String odp2) {
        this.odp2 = odp2;
    }

    public String getOdp3() {
        return odp3;
    }

    public void setOdp3(String odp3) {
        this.odp3 = odp3;
    }

    public String getOdp4() {
        return odp4;
    }

    public void setOdp4(String odp4) {
        this.odp4 = odp4;
    }

    public String getOdp1walidacja() {
        return odp1walidacja;
    }

    public void setOdp1walidacja(String odp1walidacja) {
        this.odp1walidacja = odp1walidacja;
    }

    public String getOdp2walidacja() {
        return odp2walidacja;
    }

    public void setOdp2walidacja(String odp2walidacja) {
        this.odp2walidacja = odp2walidacja;
    }

    public String getOdp3walidacja() {
        return odp3walidacja;
    }

    public void setOdp3walidacja(String odp3walidacja) {
        this.odp3walidacja = odp3walidacja;
    }

    public String getOdp4walidacja() {
        return odp4walidacja;
    }

    public void setOdp4walidacja(String odp4walidacja) {
        this.odp4walidacja = odp4walidacja;
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
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Test[ id=" + id + " ]";
    }
    
}
