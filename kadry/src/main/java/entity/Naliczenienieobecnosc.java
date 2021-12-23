/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "naliczenienieobecnosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naliczenienieobecnosc.findAll", query = "SELECT n FROM Naliczenienieobecnosc n"),
    @NamedQuery(name = "Naliczenienieobecnosc.findById", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.id = :id"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByKwota", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.kwota = :kwota"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByKwotastatystyczna", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.kwotastatystyczna = :kwotastatystyczna"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByKwotabezzus", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.kwotabezzus = :kwotabezzus"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByKwotazus", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.kwotazus = :kwotazus"),
    @NamedQuery(name = "Naliczenienieobecnosc.findBySkladnikkistale", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.skladnikistale = :skladnikistale"),
    @NamedQuery(name = "Naliczenienieobecnosc.findBySkladnikizmiennesrednia", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.skladnikizmiennesrednia = :skladnikizmiennesrednia"),
    @NamedQuery(name = "Naliczenienieobecnosc.findBySredniazailemcy", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.sredniazailemcy = :sredniazailemcy"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByProcentzazwolnienie", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.procentzazwolnienie = :procentzazwolnienie"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByStawkadzienna", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.stawkadzienna = :stawkadzienna"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByLiczbagodzinobowiazku", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.liczbagodzinobowiazku = :liczbagodzinobowiazku"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByLiczbagodzinurlopu", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.liczbagodzinurlopu = :liczbagodzinurlopu"),
    @NamedQuery(name = "Naliczenienieobecnosc.findByStawkadziennaredukcji", query = "SELECT n FROM Naliczenienieobecnosc n WHERE n.stawkadziennaredukcji = :stawkadziennaredukcji")})
public class Naliczenienieobecnosc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotastatystyczna")
    private double kwotastatystyczna;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotabezzus")
    private double kwotabezzus;
    @Column(name = "kwotazus")
    private double kwotazus;
    @Column(name = "skladnikistale")
    private double skladnikistale;
    @Column(name = "skladnikizmiennesrednia")
    private double skladnikizmiennesrednia;
    @Column(name = "sredniazailemcy")
    private double sredniazailemcy;
    @Column(name = "procentzazwolnienie")
    private double procentzazwolnienie;
    @Column(name = "stawkadzienna")
    private double stawkadzienna;
    @Column(name = "stawkagodzinowa")
    private double stawkagodzinowa;
    @Column(name = "liczbagodzinobowiazku")
    private double liczbagodzinobowiazku;
    @Column(name = "liczbagodzinurlopu")
    private double liczbagodzinurlopu;
     @Column(name = "liczbadniobowiazku")
    private double liczbadniobowiazku;
    @Column(name = "liczbadniurlopu")
    private double liczbadniurlopu;
    @Column(name = "sumakwotdosredniej")
    private double sumakwotdosredniej;
    @Column(name = "sumagodzindosredniej")
    private double sumagodzindosredniej;
    @Column(name = "stawkadziennaredukcji")
    private double stawkadziennaredukcji;
    @Column(name = "kwotaredukcji")
    private double kwotaredukcji;
    @Column(name = "podstawadochoroby")
    private double podstawadochoroby;
    @JoinColumn(name = "pasekwynagrodzen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pasekwynagrodzen pasekwynagrodzen;
    @JoinColumn(name = "nieobecnosc", referencedColumnName = "id")
    @ManyToOne
    private Nieobecnosc nieobecnosc;
    @JoinColumn(name = "skladnikwynagrodzenia", referencedColumnName = "id")
    @ManyToOne
    private Skladnikwynagrodzenia skladnikwynagrodzenia;
    @Column(name = "jakiskladnikredukowalny")
    private String jakiskladnikredukowalny;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "naliczenienieobecnosc")
    private List<Sredniadlanieobecnosci> sredniadlanieobecnosciList;
    @Column(name = "dataod")
    private String dataod;
    @Column(name = "datado")
    private String datado;

    public Naliczenienieobecnosc() {
        this.sredniadlanieobecnosciList = new ArrayList<>();
    }

    public Naliczenienieobecnosc(Integer id) {
        this.id = id;
        this.sredniadlanieobecnosciList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Nieobecnosc getNieobecnosc() {
        return nieobecnosc;
    }

    public void setNieobecnosc(Nieobecnosc nieobecnosc) {
        this.nieobecnosc = nieobecnosc;
    }

    public Skladnikwynagrodzenia getSkladnikwynagrodzenia() {
        return skladnikwynagrodzenia;
    }

    public void setSkladnikwynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        this.skladnikwynagrodzenia = skladnikwynagrodzenia;
    }

    public double getPodstawadochoroby() {
        return podstawadochoroby;
    }

    public void setPodstawadochoroby(double podstawadochoroby) {
        this.podstawadochoroby = podstawadochoroby;
    }

    public double getLiczbadniobowiazku() {
        return liczbadniobowiazku;
    }

    public void setLiczbadniobowiazku(double liczbadniobowiazku) {
        this.liczbadniobowiazku = liczbadniobowiazku;
    }

    public double getLiczbadniurlopu() {
        return liczbadniurlopu;
    }

    public void setLiczbadniurlopu(double liczbadniurlopu) {
        this.liczbadniurlopu = liczbadniurlopu;
    }

    public double getSumakwotdosredniej() {
        return sumakwotdosredniej;
    }

    public void setSumakwotdosredniej(double sumakwotdosredniej) {
        this.sumakwotdosredniej = sumakwotdosredniej;
    }

    public double getSumagodzindosredniej() {
        return sumagodzindosredniej;
    }

    public void setSumagodzindosredniej(double sumagodzindosredniej) {
        this.sumagodzindosredniej = sumagodzindosredniej;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
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
        if (!(object instanceof Naliczenienieobecnosc)) {
            return false;
        }
        Naliczenienieobecnosc other = (Naliczenienieobecnosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String dane = this.skladnikwynagrodzenia.getId()!=null?String.valueOf(this.skladnikwynagrodzenia.getId()):this.skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod();
        return "Naliczenienieobecnosc{" + "kwota=" + kwota + ", kwotastatystyczna=" + kwotastatystyczna + ", kwotabezzus=" + kwotabezzus + ", kwotazus=" + kwotazus + ", skladnikistale=" + skladnikistale + ", procentzazwolnienie=" + procentzazwolnienie + ", stawkadzienna=" + stawkadzienna + ", stawkadziennaredukcji=" + stawkadziennaredukcji + ", kwotaredukcji=" + kwotaredukcji + ", skladnikwynagrodzenia=" + dane + ", jakiskladnikredukowalny=" + jakiskladnikredukowalny + '}';
    }

   
    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotastatystyczna() {
        return kwotastatystyczna;
    }

    public void setKwotastatystyczna(double kwotastatystyczna) {
        this.kwotastatystyczna = kwotastatystyczna;
    }

    public double getKwotabezzus() {
        return kwotabezzus;
    }

    public void setKwotabezzus(double kwotabezzus) {
        this.kwotabezzus = kwotabezzus;
    }

    public double getKwotazus() {
        return kwotazus;
    }

    public void setKwotazus(double kwotazus) {
        this.kwotazus = kwotazus;
    }

    public double getSkladnikistale() {
        return skladnikistale;
    }

    public void setSkladnikistale(double skladnikistale) {
        this.skladnikistale = skladnikistale;
    }

    public double getSkladnikizmiennesrednia() {
        return skladnikizmiennesrednia;
    }

    public void setSkladnikizmiennesrednia(double skladnikizmiennesrednia) {
        this.skladnikizmiennesrednia = skladnikizmiennesrednia;
    }

    public double getSredniazailemcy() {
        return sredniazailemcy;
    }

    public void setSredniazailemcy(double sredniazailemcy) {
        this.sredniazailemcy = sredniazailemcy;
    }

    public double getProcentzazwolnienie() {
        return procentzazwolnienie;
    }

    public void setProcentzazwolnienie(double procentzazwolnienie) {
        this.procentzazwolnienie = procentzazwolnienie;
    }

    public double getStawkadzienna() {
        return stawkadzienna;
    }

    public void setStawkadzienna(double stawkadzienna) {
        this.stawkadzienna = stawkadzienna;
    }

    public double getStawkagodzinowa() {
        return stawkagodzinowa;
    }

    public void setStawkagodzinowa(double stawkagodzinowa) {
        this.stawkagodzinowa = stawkagodzinowa;
    }

    public double getLiczbagodzinobowiazku() {
        return liczbagodzinobowiazku;
    }

    public void setLiczbagodzinobowiazku(double liczbagodzinobowiazku) {
        this.liczbagodzinobowiazku = liczbagodzinobowiazku;
    }

    public double getLiczbagodzinurlopu() {
        return liczbagodzinurlopu;
    }

    public void setLiczbagodzinurlopu(double liczbagodzinurlopu) {
        this.liczbagodzinurlopu = liczbagodzinurlopu;
    }

    public double getStawkadziennaredukcji() {
        return stawkadziennaredukcji;
    }

    public void setStawkadziennaredukcji(double stawkadziennaredukcji) {
        this.stawkadziennaredukcji = stawkadziennaredukcji;
    }

    public double getKwotaredukcji() {
        return kwotaredukcji;
    }

    public void setKwotaredukcji(double kwotaredukcji) {
        this.kwotaredukcji = kwotaredukcji;
    }

    public Pasekwynagrodzen getPasekwynagrodzen() {
        return pasekwynagrodzen;
    }

    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
        this.pasekwynagrodzen = pasekwynagrodzen;
    }

    public String getJakiskladnikredukowalny() {
        return jakiskladnikredukowalny;
    }

    public void setJakiskladnikredukowalny(String jakiskladnikredukowalny) {
        this.jakiskladnikredukowalny = jakiskladnikredukowalny;
    }

    @XmlTransient
    public List<Sredniadlanieobecnosci> getSredniadlanieobecnosciList() {
        return sredniadlanieobecnosciList;
    }

    public void setSredniadlanieobecnosciList(List<Sredniadlanieobecnosci> sredniadlanieobecnosciList) {
        this.sredniadlanieobecnosciList = sredniadlanieobecnosciList;
    }
}
