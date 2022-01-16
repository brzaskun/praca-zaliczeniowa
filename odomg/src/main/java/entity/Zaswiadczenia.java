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
@Table(name = "zaswiadczenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaswiadczenia.findAll", query = "SELECT z FROM Zaswiadczenia z"),
    @NamedQuery(name = "Zaswiadczenia.findById", query = "SELECT z FROM Zaswiadczenia z WHERE z.id = :id"),
    @NamedQuery(name = "Zaswiadczenia.findByNazwa", query = "SELECT z FROM Zaswiadczenia z WHERE z.nazwa = :nazwa"),
    @NamedQuery(name = "Zaswiadczenia.findBySkrot", query = "SELECT z FROM Zaswiadczenia z WHERE z.skrot = :skrot"),
    @NamedQuery(name = "Zaswiadczenia.findByPoziom", query = "SELECT z FROM Zaswiadczenia z WHERE z.poziom = :poziom"),
    @NamedQuery(name = "Zaswiadczenia.findByLinia1", query = "SELECT z FROM Zaswiadczenia z WHERE z.linia1 = :linia1"),
    @NamedQuery(name = "Zaswiadczenia.findByLinia2", query = "SELECT z FROM Zaswiadczenia z WHERE z.linia2 = :linia2"),
    @NamedQuery(name = "Zaswiadczenia.findByLinia3", query = "SELECT z FROM Zaswiadczenia z WHERE z.linia3 = :linia3"),
    @NamedQuery(name = "Zaswiadczenia.findByTrescM", query = "SELECT z FROM Zaswiadczenia z WHERE z.trescM = :trescM"),
    @NamedQuery(name = "Zaswiadczenia.findByTrescK", query = "SELECT z FROM Zaswiadczenia z WHERE z.trescK = :trescK"),
    @NamedQuery(name = "Zaswiadczenia.findByPdf", query = "SELECT z FROM Zaswiadczenia z WHERE z.pdf = :pdf")})
public class Zaswiadczenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "nazwa", nullable = false, length = 256)
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "skrot", nullable = false, length = 128)
    private String skrot;
    @Size(max = 128)
    @Column(name = "poziom", length = 128)
    private String poziom;
    @Size(max = 512)
    @Column(name = "linia1", length = 512)
    private String linia1;
    @Size(max = 512)
    @Column(name = "linia2", length = 512)
    private String linia2;
    @Size(max = 512)
    @Column(name = "linia3", length = 512)
    private String linia3;
    @Size(max = 8192)
    @Column(name = "trescM", length = 8192)
    private String trescM;
    @Size(max = 8192)
    @Column(name = "trescK", length = 8192)
    private String trescK;
    @Size(max = 45)
    @Column(name = "pdf", length = 45)
    private String pdf;

    public Zaswiadczenia() {
    }

    public Zaswiadczenia(Integer id) {
        this.id = id;
    }

    public Zaswiadczenia(Integer id, String nazwa, String skrot) {
        this.id = id;
        this.nazwa = nazwa;
        this.skrot = skrot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getPoziom() {
        return poziom;
    }

    public void setPoziom(String poziom) {
        this.poziom = poziom;
    }

    public String getLinia1() {
        return linia1;
    }

    public void setLinia1(String linia1) {
        this.linia1 = linia1;
    }

    public String getLinia2() {
        return linia2;
    }

    public void setLinia2(String linia2) {
        this.linia2 = linia2;
    }

    public String getLinia3() {
        return linia3;
    }

    public void setLinia3(String linia3) {
        this.linia3 = linia3;
    }

    public String getTrescM() {
        return trescM;
    }

    public void setTrescM(String trescM) {
        this.trescM = trescM;
    }

    public String getTrescK() {
        return trescK;
    }

    public void setTrescK(String trescK) {
        this.trescK = trescK;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
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
        if (!(object instanceof Zaswiadczenia)) {
            return false;
        }
        Zaswiadczenia other = (Zaswiadczenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zaswiadczenia[ id=" + id + " ]";
    }
    
}
