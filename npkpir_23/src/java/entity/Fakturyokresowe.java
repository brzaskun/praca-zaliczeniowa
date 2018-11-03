/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Lob;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturyokresowe.findAll", query = "SELECT f FROM Fakturyokresowe f"),
    @NamedQuery(name = "Fakturyokresowe.findById", query = "SELECT f FROM Fakturyokresowe f WHERE f.id = :id"),
    @NamedQuery(name = "Fakturyokresowe.findByPodatnik", query = "SELECT f FROM Fakturyokresowe f WHERE f.podatnik = :podatnik"),
    @NamedQuery(name = "Fakturyokresowe.findByM1", query = "SELECT f FROM Fakturyokresowe f WHERE f.m1 = :m1"),
    @NamedQuery(name = "Fakturyokresowe.findByM2", query = "SELECT f FROM Fakturyokresowe f WHERE f.m2 = :m2"),
    @NamedQuery(name = "Fakturyokresowe.findByM3", query = "SELECT f FROM Fakturyokresowe f WHERE f.m3 = :m3"),
    @NamedQuery(name = "Fakturyokresowe.findByM4", query = "SELECT f FROM Fakturyokresowe f WHERE f.m4 = :m4"),
    @NamedQuery(name = "Fakturyokresowe.findByM5", query = "SELECT f FROM Fakturyokresowe f WHERE f.m5 = :m5"),
    @NamedQuery(name = "Fakturyokresowe.findByM6", query = "SELECT f FROM Fakturyokresowe f WHERE f.m6 = :m6"),
    @NamedQuery(name = "Fakturyokresowe.findByM7", query = "SELECT f FROM Fakturyokresowe f WHERE f.m7 = :m7"),
    @NamedQuery(name = "Fakturyokresowe.findByM8", query = "SELECT f FROM Fakturyokresowe f WHERE f.m8 = :m8"),
    @NamedQuery(name = "Fakturyokresowe.findByM9", query = "SELECT f FROM Fakturyokresowe f WHERE f.m9 = :m9"),
    @NamedQuery(name = "Fakturyokresowe.findByM10", query = "SELECT f FROM Fakturyokresowe f WHERE f.m10 = :m10"),
    @NamedQuery(name = "Fakturyokresowe.findByM11", query = "SELECT f FROM Fakturyokresowe f WHERE f.m11 = :m11"),
    @NamedQuery(name = "Fakturyokresowe.findByM12", query = "SELECT f FROM Fakturyokresowe f WHERE f.m12 = :m12")})
public class Fakturyokresowe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(nullable = false, length = 200)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private Dok dokument;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m1;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m2;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m3;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m4;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m5;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m6;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m7;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m8;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m9;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m10;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m11;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int m12;
   

    public Fakturyokresowe() {
    }

    public Fakturyokresowe(Integer id) {
        this.id = id;
    }

    public Fakturyokresowe(Integer id, String podatnik, Dok dokument, int m1, int m2, int m3, int m4, int m5, int m6, int m7, int m8, int m9, int m10, int m11, int m12) {
        this.id = id;
        this.podatnik = podatnik;
        this.dokument = dokument;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.m7 = m7;
        this.m8 = m8;
        this.m9 = m9;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public Dok getDokument() {
        return dokument;
    }

    public void setDokument(Dok dokument) {
        this.dokument = dokument;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }

    public int getM4() {
        return m4;
    }

    public void setM4(int m4) {
        this.m4 = m4;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM6() {
        return m6;
    }

    public void setM6(int m6) {
        this.m6 = m6;
    }

    public int getM7() {
        return m7;
    }

    public void setM7(int m7) {
        this.m7 = m7;
    }

    public int getM8() {
        return m8;
    }

    public void setM8(int m8) {
        this.m8 = m8;
    }

    public int getM9() {
        return m9;
    }

    public void setM9(int m9) {
        this.m9 = m9;
    }

    public int getM10() {
        return m10;
    }

    public void setM10(int m10) {
        this.m10 = m10;
    }

    public int getM11() {
        return m11;
    }

    public void setM11(int m11) {
        this.m11 = m11;
    }

    public int getM12() {
        return m12;
    }

    public void setM12(int m12) {
        this.m12 = m12;
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
        if (!(object instanceof Fakturyokresowe)) {
            return false;
        }
        Fakturyokresowe other = (Fakturyokresowe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fakturyokresowe[ id=" + id + " ]";
    }
    
}
