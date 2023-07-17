/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ekwiwalenturlop", uniqueConstraints = {
    @UniqueConstraint(columnNames={"umowa"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EkwiwalentUrlop.findAll", query = "SELECT e FROM EkwiwalentUrlop e"),
    @NamedQuery(name = "EkwiwalentUrlop.findById", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.id = :id"),
    @NamedQuery(name = "EkwiwalentUrlop.findByDziennaliczenia", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.dziennaliczenia = :dziennaliczenia"),
    @NamedQuery(name = "EkwiwalentUrlop.findByKwota", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.kwota = :kwota"),
    @NamedQuery(name = "EkwiwalentUrlop.findByMc", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.mc = :mc"),
    @NamedQuery(name = "EkwiwalentUrlop.findByRok", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.rok = :rok"),
    @NamedQuery(name = "EkwiwalentUrlop.findByUmowa", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.umowa = :umowa"),
    @NamedQuery(name = "EkwiwalentUrlop.findByDni", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.dni = :dni"),
    @NamedQuery(name = "EkwiwalentUrlop.findByGodziny", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.godziny = :godziny"),
    @NamedQuery(name = "EkwiwalentUrlop.findByNaniesiony", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.naniesiony = :naniesiony"),
    @NamedQuery(name = "EkwiwalentUrlop.findByData", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.data = :data"),
    @NamedQuery(name = "EkwiwalentUrlop.findByWspolczynnik", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.wspolczynnik = :wspolczynnik"),
    @NamedQuery(name = "EkwiwalentUrlop.findByZalegly", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.zalegly = :zalegly"),
    @NamedQuery(name = "EkwiwalentUrlop.findByBiezacy", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.biezacy = :biezacy"),
    @NamedQuery(name = "EkwiwalentUrlop.findByEtat1", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.etat1 = :etat1"),
    @NamedQuery(name = "EkwiwalentUrlop.findByEtat2", query = "SELECT e FROM EkwiwalentUrlop e WHERE e.etat2 = :etat2")})
public class EkwiwalentUrlop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne()
    private Umowa umowa;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
    
    @Size(max = 255)
    @Column(name = "dziennaliczenia")
    private String dziennaliczenia;
    @Size(max = 255)
    @Column(name = "dzienwyplaty")
    private String dzienwyplaty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotastale")
    private double kwotastale;
    @Column(name = "kwotazmienne")
    private double kwotazmienne;
    @Size(max = 255)
    @Column(name = "mc")
    private String mc;
    @Size(max = 255)
    @Column(name = "rok")
    private String rok;
    @Column(name = "dni")
    private Integer dni;
    @Size(max = 45)
    @Column(name = "godziny")
    private int godziny;
    @Column(name = "naniesiony")
    private boolean naniesiony;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "wspolczynnik")
    private double wspolczynnik;
    @Column(name = "zalegly")
    private Integer zalegly;
    @Column(name = "biezacy")
    private Integer biezacy;
    @Column(name = "etat1")
    private Integer etat1;
    @Column(name = "etat2")
    private Integer etat2;
    @Column(name = "wykorzystany")
    private Integer wykorzystany;

    public EkwiwalentUrlop() {
    }

    public EkwiwalentUrlop(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDziennaliczenia() {
        return dziennaliczenia;
    }

    public void setDziennaliczenia(String dziennaliczenia) {
        this.dziennaliczenia = dziennaliczenia;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public int getGodziny() {
        return godziny;
    }

    public void setGodziny(int godziny) {
        this.godziny = godziny;
    }

    public boolean isNaniesiony() {
        return naniesiony;
    }

    public void setNaniesiony(boolean naniesiony) {
        this.naniesiony = naniesiony;
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getWspolczynnik() {
        return wspolczynnik;
    }

    public void setWspolczynnik(double wspolczynnik) {
        this.wspolczynnik = wspolczynnik;
    }

    public Integer getZalegly() {
        return zalegly;
    }

    public void setZalegly(Integer zalegly) {
        this.zalegly = zalegly;
    }

    public Integer getBiezacy() {
        return biezacy;
    }
    
    public Integer getRazembiezacyzalegly() {
        int z = zalegly;
        int b = biezacy;
        return z+b;
    }
    

    public void setBiezacy(Integer biezacy) {
        this.biezacy = biezacy;
    }

    public Integer getEtat1() {
        return etat1;
    }

    public void setEtat1(Integer etat1) {
        this.etat1 = etat1;
    }

    public Integer getEtat2() {
        return etat2;
    }

    public void setEtat2(Integer etat2) {
        this.etat2 = etat2;
    }

    public double getKwotastale() {
        return kwotastale;
    }

    public void setKwotastale(double kwotastale) {
        this.kwotastale = kwotastale;
    }

    public double getKwotazmienne() {
        return kwotazmienne;
    }

    public void setKwotazmienne(double kwotazmienne) {
        this.kwotazmienne = kwotazmienne;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public Integer getWykorzystany() {
        return wykorzystany;
    }

    public void setWykorzystany(Integer wykorzystany) {
        this.wykorzystany = wykorzystany;
    }

    public String getDzienwyplaty() {
        return dzienwyplaty;
    }

    public void setDzienwyplaty(String dzienwyplaty) {
        this.dzienwyplaty = dzienwyplaty;
    }
    
    
    
    public String getEtat() {
        String zwrot = "brak danych";
        if (this.etat1!=null) {
            zwrot = this.etat1+"/"+this.etat2;
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
        if (!(object instanceof EkwiwalentUrlop)) {
            return false;
        }
        EkwiwalentUrlop other = (EkwiwalentUrlop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EkwiwalentUrlop{" + "angaz=" + angaz.getNazwiskoiImie() + ", dziennaliczenia=" + dziennaliczenia + ", dzienwyplaty=" + dzienwyplaty + ", kwota=" + kwota + ", kwotastale=" + kwotastale + ", kwotazmienne=" + kwotazmienne + ", mc=" + mc + ", rok=" + rok + '}';
    }

   
}
