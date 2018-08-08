/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import error.E;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(catalog = "pkpir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturywystokresowe.findAll", query = "SELECT f FROM Fakturywystokresowe f"),
    @NamedQuery(name = "Fakturywystokresowe.findById", query = "SELECT f FROM Fakturywystokresowe f WHERE f.id = :id"),
    @NamedQuery(name = "Fakturywystokresowe.findByPodatnik", query = "SELECT f FROM Fakturywystokresowe f WHERE f.podatnik = :podatnik"),
    @NamedQuery(name = "Fakturywystokresowe.findByRok", query = "SELECT f FROM Fakturywystokresowe f WHERE f.rok = :rok"),
    @NamedQuery(name = "Fakturywystokresowe.findByPodatnikRok", query = "SELECT f FROM Fakturywystokresowe f WHERE f.podatnik = :podatnik AND f.rok = :rok"),
    @NamedQuery(name = "Fakturywystokresowe.findByPodatnikRokBiezace", query = "SELECT f FROM Fakturywystokresowe f WHERE f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByNipodbiorcy", query = "SELECT f FROM Fakturywystokresowe f WHERE f.nipodbiorcy = :nipodbiorcy"),
    @NamedQuery(name = "Fakturywystokresowe.findByBrutto", query = "SELECT f FROM Fakturywystokresowe f WHERE f.brutto = :brutto"),
    @NamedQuery(name = "Fakturywystokresowe.findByFaktura", query = "SELECT f FROM Fakturywystokresowe f WHERE f.dokument = :faktura"),
    @NamedQuery(name = "Fakturywystokresowe.findByOkresowa", query = "SELECT f FROM Fakturywystokresowe f WHERE f.rok = :rok AND f.nipodbiorcy = :nipodbiorcy AND f.podatnik = :podatnik AND f.brutto = :brutto"),
    @NamedQuery(name = "Fakturywystokresowe.findByM1", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m1 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM2", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m2 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM3", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m3 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM4", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m4 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM5", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m5 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM6", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m6 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM7", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m7 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM8", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m8 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM9", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m9 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM10", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m10 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM11", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m11 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'"),
    @NamedQuery(name = "Fakturywystokresowe.findByM12", query = "SELECT f FROM Fakturywystokresowe f WHERE f.m12 > 0 and f.podatnik = :podatnik AND f.rok = :rok AND f.biezaca0archiwalna1 = '0'")
})
public class Fakturywystokresowe implements Serializable {
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
    @Size(max = 4)
    @Column(length = 4)
    private String rok;
    @Size(max = 16)
    @Column(length = 16)
    private String nipodbiorcy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double brutto;
    @JoinColumns({
        @JoinColumn(name = "fa_wystawcanazwa", referencedColumnName = "wystawcanazwa"),
        @JoinColumn(name = "fa_numerkolejny", referencedColumnName = "numerkolejny")
    })
    private Faktura dokument;
    @Size(max = 10)
    @Column(length = 10)
    private String datawystawienia;
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
    @Column(name = "biezaca0archiwalna1")
    private boolean biezaca0archiwalna1;

    public Fakturywystokresowe() {
    }

    public Fakturywystokresowe(Integer id) {
        this.id = id;
    }

    public Fakturywystokresowe(Integer id, String podatnik, String rok, Faktura dokument, int m1, int m2, int m3, int m4, int m5, int m6, int m7, int m8, int m9, int m10, int m11, int m12) {
        this.id = id;
        this.podatnik = podatnik;
        this.rok = rok;
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

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    
    public String getNipodbiorcy() {
        return nipodbiorcy;
    }

    public void setNipodbiorcy(String nipodbiorcy) {
        this.nipodbiorcy = nipodbiorcy;
    }

    public Double getBrutto() {
        return brutto;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }

    public Faktura getDokument() {
        return dokument;
    }

    public void setDokument(Faktura dokument) {
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
    
    public double getNetto() {
        return this.dokument.getNettoPrzelicz();
    }
    
    public double getVat() {
        return this.dokument.getVatPrzelicz();
    }

    public boolean isBiezaca0archiwalna1() {
        return biezaca0archiwalna1;
    }

    public void setBiezaca0archiwalna1(boolean biezaca0archiwalna1) {
        this.biezaca0archiwalna1 = biezaca0archiwalna1;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public String getDatawystawieniaOld() {
        try {
            String zwrot = this.dokument.getDatawystawienia();
            if (datawystawienia!=null) {
                zwrot = datawystawienia;
            }
            return zwrot;
        } catch (Exception e) {
            E.e(e);
        }
        return null; 
    }
    
    public String getStyldaty(String mc) {
        String zwrot = "color: initial";
        if (Data.getMc(this.getDatawystawieniaOld()).equals(mc)) {
            zwrot = "color: green";
        }
        return zwrot;
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
        if (!(object instanceof Fakturywystokresowe)) {
            return false;
        }
        Fakturywystokresowe other = (Fakturywystokresowe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fakturywystokresowe{" + "id=" + id + ", podatnik=" + podatnik + ", rok=" + rok + ", nipodbiorcy=" + nipodbiorcy + ", brutto=" + brutto + '}';
    }

   
    
}
