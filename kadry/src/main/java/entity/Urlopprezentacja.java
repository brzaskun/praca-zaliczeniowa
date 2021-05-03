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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "urlopprezentacja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urlopprezentacja.findAll", query = "SELECT u FROM Urlopprezentacja u"),
    @NamedQuery(name = "Urlopprezentacja.findById", query = "SELECT u FROM Urlopprezentacja u WHERE u.id = :id"),
    @NamedQuery(name = "Urlopprezentacja.findByLatapoprzednie", query = "SELECT u FROM Urlopprezentacja u WHERE u.latapoprzednie = :latapoprzednie"),
    @NamedQuery(name = "Urlopprezentacja.findByWymiarrokbiezacy", query = "SELECT u FROM Urlopprezentacja u WHERE u.wymiarrokbiezacy = :wymiarrokbiezacy"),
    @NamedQuery(name = "Urlopprezentacja.findByW1", query = "SELECT u FROM Urlopprezentacja u WHERE u.w1 = :w1"),
    @NamedQuery(name = "Urlopprezentacja.findByW2", query = "SELECT u FROM Urlopprezentacja u WHERE u.w2 = :w2"),
    @NamedQuery(name = "Urlopprezentacja.findByW3", query = "SELECT u FROM Urlopprezentacja u WHERE u.w3 = :w3"),
    @NamedQuery(name = "Urlopprezentacja.findByW4", query = "SELECT u FROM Urlopprezentacja u WHERE u.w4 = :w4"),
    @NamedQuery(name = "Urlopprezentacja.findByW5", query = "SELECT u FROM Urlopprezentacja u WHERE u.w5 = :w5"),
    @NamedQuery(name = "Urlopprezentacja.findByW6", query = "SELECT u FROM Urlopprezentacja u WHERE u.w6 = :w6"),
    @NamedQuery(name = "Urlopprezentacja.findByW7", query = "SELECT u FROM Urlopprezentacja u WHERE u.w7 = :w7"),
    @NamedQuery(name = "Urlopprezentacja.findByW8", query = "SELECT u FROM Urlopprezentacja u WHERE u.w8 = :w8"),
    @NamedQuery(name = "Urlopprezentacja.findByW9", query = "SELECT u FROM Urlopprezentacja u WHERE u.w9 = :w9"),
    @NamedQuery(name = "Urlopprezentacja.findByW10", query = "SELECT u FROM Urlopprezentacja u WHERE u.w10 = :w10"),
    @NamedQuery(name = "Urlopprezentacja.findByPracownik", query = "SELECT u FROM Urlopprezentacja u WHERE u.pracownik = :pracownik"),
    @NamedQuery(name = "Urlopprezentacja.findByPracownikRok", query = "SELECT u FROM Urlopprezentacja u WHERE u.pracownik = :pracownik AND u.rok = :rok"),
    @NamedQuery(name = "Urlopprezentacja.findByEkwiwalent", query = "SELECT u FROM Urlopprezentacja u WHERE u.ekwiwalent = :ekwiwalent"),
    @NamedQuery(name = "Urlopprezentacja.findByDoprzeniesienia", query = "SELECT u FROM Urlopprezentacja u WHERE u.doprzeniesienia = :doprzeniesienia")})
public class Urlopprezentacja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latapoprzednie")
    private double latapoprzednie;
    @Column(name = "wymiarrokbiezacy")
    private double wymiarrokbiezacy;
    @Column(name = "w1")
    private double w1;
    @Column(name = "w2")
    private double w2;
    @Column(name = "w3")
    private double w3;
    @Column(name = "w4")
    private double w4;
    @Column(name = "w5")
    private double w5;
    @Column(name = "w6")
    private double w6;
    @Column(name = "w7")
    private double w7;
    @Column(name = "w8")
    private double w8;
    @Column(name = "w9")
    private double w9;
    @Column(name = "w10")
    private double w10;
    @Column(name = "w11")
    private double w11;
    @Column(name = "w12")
    private double w12;
    @Column(name = "w13")
    private double w13;
    @Column(name = "ekwiwalent")
    private double ekwiwalent;
    @Column(name = "doprzeniesienia")
    private double doprzeniesienia;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pracownik pracownik;
    @Column(name = "rok", nullable = false)
    private String rok;

    public Urlopprezentacja() {
    }

    public Urlopprezentacja(Integer id) {
        this.id = id;
    }

    public Urlopprezentacja(Pracownik pracownik, String rok) {
        this.pracownik = pracownik;
        this.rok = rok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatapoprzednie() {
        return latapoprzednie;
    }

    public void setLatapoprzednie(double latapoprzednie) {
        this.latapoprzednie = latapoprzednie;
    }

    public double getWymiarrokbiezacy() {
        return wymiarrokbiezacy;
    }

    public void setWymiarrokbiezacy(double wymiarrokbiezacy) {
        this.wymiarrokbiezacy = wymiarrokbiezacy;
    }

    public double getW1() {
        return w1;
    }

    public void setW1(Double w1) {
        this.w1 = w1;
    }

    public double getW2() {
        return w2;
    }

    public void setW2(Double w2) {
        this.w2 = w2;
    }

    public double getW3() {
        return w3;
    }

    public void setW3(Double w3) {
        this.w3 = w3;
    }

    public double getW4() {
        return w4;
    }

    public void setW4(Double w4) {
        this.w4 = w4;
    }

    public double getW5() {
        return w5;
    }

    public void setW5(Double w5) {
        this.w5 = w5;
    }

    public double getW6() {
        return w6;
    }

    public void setW6(Double w6) {
        this.w6 = w6;
    }

    public double getW7() {
        return w7;
    }

    public void setW7(Double w7) {
        this.w7 = w7;
    }

    public double getW8() {
        return w8;
    }

    public void setW8(Double w8) {
        this.w8 = w8;
    }

    public double getW9() {
        return w9;
    }

    public void setW9(Double w9) {
        this.w9 = w9;
    }

    public double getW10() {
        return w10;
    }

    public void setW10(Double w10) {
        this.w10 = w10;
    }

    public double getW11() {
        return w11;
    }

    public void setW11(Double w11) {
        this.w11 = w11;
    }

    public double getW12() {
        return w12;
    }

    public void setW12(Double w12) {
        this.w12 = w12;
    }
    
    public double getW13() {
        return w13;
    }

    public void setW13(Double w13) {
        this.w13 = w13;
    }

    public double getEkwiwalent() {
        return ekwiwalent;
    }

    public void setEkwiwalent(double ekwiwalent) {
        this.ekwiwalent = ekwiwalent;
    }

    public double getDoprzeniesienia() {
        return doprzeniesienia;
    }

    public void setDoprzeniesienia(double doprzeniesienia) {
        this.doprzeniesienia = doprzeniesienia;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
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
        if (!(object instanceof Urlopprezentacja)) {
            return false;
        }
        Urlopprezentacja other = (Urlopprezentacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Urlopprezentacja[ id=" + id + " ]";
    }
    
}
