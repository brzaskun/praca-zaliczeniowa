/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Pod;
import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "SrodekTrw")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SrodekTrw.findAll", query = "SELECT s FROM SrodekTrw s"),
    @NamedQuery(name = "SrodekTrw.findByDatawy", query = "SELECT s FROM SrodekTrw s WHERE s.datawy = :datawy"),
    @NamedQuery(name = "SrodekTrw.findByDataprzek", query = "SELECT s FROM SrodekTrw s WHERE s.dataprzek = :dataprzek"),
    @NamedQuery(name = "SrodekTrw.findByDatazak", query = "SELECT s FROM SrodekTrw s WHERE s.datazak = :datazak"),
    @NamedQuery(name = "SrodekTrw.findByVat", query = "SELECT s FROM SrodekTrw s WHERE s.vat = :vat"),
    @NamedQuery(name = "SrodekTrw.findByOdpismc", query = "SELECT s FROM SrodekTrw s WHERE s.odpismc = :odpismc"),
    @NamedQuery(name = "SrodekTrw.findByNazwa", query = "SELECT s FROM SrodekTrw s WHERE s.nazwa = :nazwa"),
    @NamedQuery(name = "SrodekTrw.findByKst", query = "SELECT s FROM SrodekTrw s WHERE s.kst = :kst"),
    @NamedQuery(name = "SrodekTrw.findById", query = "SELECT s FROM SrodekTrw s WHERE s.id = :id"),
    @NamedQuery(name = "SrodekTrw.findByNrsrodka", query = "SELECT s FROM SrodekTrw s WHERE s.nrsrodka = :nrsrodka"),
    @NamedQuery(name = "SrodekTrw.findByOdpisrok", query = "SELECT s FROM SrodekTrw s WHERE s.odpisrok = :odpisrok"),
    @NamedQuery(name = "SrodekTrw.findByStawka", query = "SELECT s FROM SrodekTrw s WHERE s.stawka = :stawka"),
    @NamedQuery(name = "SrodekTrw.findBySymbol", query = "SELECT s FROM SrodekTrw s WHERE s.symbol = :symbol"),
    @NamedQuery(name = "SrodekTrw.findByNetto", query = "SELECT s FROM SrodekTrw s WHERE s.netto = :netto")})

public class SrodekTrw implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nrsrodka")
    private Integer nrsrodka;
    @Lob
    @Column(name = "umorzPlan")
    private List umorzPlan;
    @Lob
    @Column(name = "umorzWyk")
    private List<Umorzenie> umorzWyk;
    @Column(name = "odpisrok")
    private Double odpisrok;
    @Column(name = "stawka")
    private Double stawka;
    @Size(max = 255)
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "netto")
    private Double netto;
    @Column(name = "podatnik")
    private String podatnik;
     @Column(name = "datawy")
    private Double datawy;
    @Size(max = 255)
    @Column(name = "dataprzek")
    private String dataprzek;
    @Size(max = 255)
    @Column(name = "datazak")
    private String datazak;
    @Column(name = "vat")
    private Double vat;
    @Column(name = "odpismc")
    private Double odpismc;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "KST")
    private String kst;
    @Size(max = 255)
    @Column(name = "typ")
    private String typ;
    @Column(name = "umorzeniepoczatkowe")
    private Double umorzeniepoczatkowe;
    @Column (name = "umorzeniezaksiegowane")
    private boolean umorzeniezaksiegowane;
            
            
            
    public SrodekTrw() {
    }

    public SrodekTrw(Integer id) {
        this.id = id;
    }

    public Double getDatawy() {
        return datawy;
    }

    public void setDatawy(Double datawy) {
        this.datawy = datawy;
    }

    public String getDataprzek() {
        return dataprzek;
    }

    public void setDataprzek(String dataprzek) {
        this.dataprzek = dataprzek;
    }

    public String getDatazak() {
        return datazak;
    }

    public void setDatazak(String datazak) {
        this.datazak = datazak;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getOdpismc() {
        return odpismc;
    }

    public void setOdpismc(Double odpismc) {
        this.odpismc = odpismc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKst() {
        return kst;
    }

    public void setKst(String kst) {
        this.kst = kst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNrsrodka() {
        return nrsrodka;
    }

    public void setNrsrodka(Integer nrsrodka) {
        this.nrsrodka = nrsrodka;
    }

    public List<Double> getUmorzPlan() {
        return umorzPlan;
    }

    public void setUmorzPlan(List<Double> umorzPlan) {
        this.umorzPlan = umorzPlan;
    }

    public List<Umorzenie> getUmorzWyk() {
        return umorzWyk;
    }

    public void setUmorzWyk(List<Umorzenie> umorzWyk) {
        this.umorzWyk = umorzWyk;
    }

    public Double getOdpisrok() {
        return odpisrok;
    }

    public void setOdpisrok(Double odpisrok) {
        this.odpisrok = odpisrok;
    }

    public Double getStawka() {
        return stawka;
    }

    public void setStawka(Double stawka) {
        this.stawka = stawka;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

  

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Double getUmorzeniepoczatkowe() {
        return umorzeniepoczatkowe;
    }

    public void setUmorzeniepoczatkowe(Double umorzeniepoczatkowe) {
        this.umorzeniepoczatkowe = umorzeniepoczatkowe;
    }

    public boolean isUmorzeniezaksiegowane() {
        return umorzeniezaksiegowane;
    }

    public void setUmorzeniezaksiegowane(boolean umorzeniezaksiegowane) {
        this.umorzeniezaksiegowane = umorzeniezaksiegowane;
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
        if (!(object instanceof SrodekTrw)) {
            return false;
        }
        SrodekTrw other = (SrodekTrw) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Str[ id=" + id + " ]";
    }
    
}
