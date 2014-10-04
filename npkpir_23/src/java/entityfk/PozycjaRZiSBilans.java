/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import abstractClasses.ToBeATreeNodeObject;
import embeddablefk.KontoKwota;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", name = "Pozycjarzis",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pozycjaString", "podatnik", "rok", "uklad", "bilanslubrzis"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PozycjaRZiS.findAll", query = "SELECT p FROM PozycjaRZiS p"),
    @NamedQuery(name = "PozycjaRZiS.findByLp", query = "SELECT p FROM PozycjaRZiS p WHERE p.lp = :lp"),
    @NamedQuery(name = "PozycjaRZiS.findByFormula", query = "SELECT p FROM PozycjaRZiS p WHERE p.formula = :formula"),
    @NamedQuery(name = "PozycjaRZiS.findByKwota", query = "SELECT p FROM PozycjaRZiS p WHERE p.kwota = :kwota"),
    @NamedQuery(name = "PozycjaRZiS.findByLevel", query = "SELECT p FROM PozycjaRZiS p WHERE p.level = :level"),
    @NamedQuery(name = "PozycjaRZiS.findByMacierzysty", query = "SELECT p FROM PozycjaRZiS p WHERE p.macierzysty = :macierzysty"),
    @NamedQuery(name = "PozycjaRZiS.findByNazwa", query = "SELECT p FROM PozycjaRZiS p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "PozycjaRZiS.findByPodatnik", query = "SELECT p FROM PozycjaRZiS p WHERE p.podatnik = :podatnik"),
    @NamedQuery(name = "PozycjaRZiS.findByPozycjaString", query = "SELECT p FROM PozycjaRZiS p WHERE p.pozycjaString = :pozycjaString"),
    @NamedQuery(name = "PozycjaRZiS.findByPozycjaSymbol", query = "SELECT p FROM PozycjaRZiS p WHERE p.pozycjaSymbol = :pozycjaSymbol"),
    @NamedQuery(name = "PozycjaRZiS.findByPozycjanr", query = "SELECT p FROM PozycjaRZiS p WHERE p.pozycjanr = :pozycjanr"),
    @NamedQuery(name = "PozycjaRZiS.findByPrzychod0koszt1", query = "SELECT p FROM PozycjaRZiS p WHERE p.przychod0koszt1 = :przychod0koszt1"),
    @NamedQuery(name = "PozycjaRZiS.findByPrzyporzadkowanekonta", query = "SELECT p FROM PozycjaRZiS p WHERE p.przyporzadkowanekonta = :przyporzadkowanekonta"),
    @NamedQuery(name = "PozycjaRZiS.findByRok", query = "SELECT p FROM PozycjaRZiS p WHERE p.rok = :rok"),
    @NamedQuery(name = "PozycjaRZiS.findByUkladPodRok", query = "SELECT p FROM PozycjaRZiS p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaRZiS.findByUklad", query = "SELECT p FROM PozycjaRZiS p WHERE p.uklad = :uklad")})
public class PozycjaRZiSBilans extends ToBeATreeNodeObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer lp;
    @Size(max = 255)
    @Column(length = 255)
    private String formula;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private double kwota;
    private int level;
    private int macierzysty;
    @Size(max = 255)
    @Column(length = 255)
    private String nazwa;
    @Size(max = 255)
    @Column(length = 255)
    private String podatnik;
    @Size(max = 255)
    @Column(length = 255)
    private String pozycjaString;
    @Size(max = 255)
    @Column(length = 255)
    private String pozycjaSymbol;
    private Integer pozycjanr;
    //przychod 0 koszt 1
    //aktywa 2 pasywa 3
    private int bilanslubrzis;
    @Lob
    @Column(length=1048576)
    private List<KontoKwota> przyporzadkowanekonta;
    @Size(max = 4)
    @Column(length = 4)
    private String rok;
    @Size(max = 255)
    @Column(length = 255)
    private String uklad;

    public PozycjaRZiSBilans() {
    }
    
    public PozycjaRZiSBilans(Integer lp) {
        this.lp = lp;
    }
    
    public PozycjaRZiSBilans(PozycjaRZiSBilans pozycjaRZiS) {
        this.pozycjanr = pozycjaRZiS.getPozycjanr();
        this.pozycjaString = pozycjaRZiS.getPozycjaString();
        this.pozycjaSymbol = pozycjaRZiS.getPozycjaSymbol();
        this.macierzysty = pozycjaRZiS.getMacierzysty();
        this.level = pozycjaRZiS.getLevel();
        this.nazwa = pozycjaRZiS.getNazwa();
        this.bilanslubrzis = pozycjaRZiS.getBilanslubrzis();
        this.lp = pozycjaRZiS.getLp();
        this.formula = "";
    }

    public PozycjaRZiSBilans(int pozycjanr, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, int lp) {
        
    }
    

    public PozycjaRZiSBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, int bilanslubrzis) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.bilanslubrzis = bilanslubrzis;
        this.formula = "";
    }
    
    public PozycjaRZiSBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, int bilanslubrzis, double kwota) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.bilanslubrzis = bilanslubrzis;
        this.kwota = kwota;
        this.formula = "";
    }

    public PozycjaRZiSBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, int bilanslubrzis, String formula) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.bilanslubrzis = bilanslubrzis;
        this.kwota = 0.0;
        this.formula = formula;
    }
    

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getMacierzysty() {
        return macierzysty;
    }

    @Override
    public void setMacierzysty(int macierzysty) {
        this.macierzysty = macierzysty;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getPozycjaString() {
        return pozycjaString;
    }

    public void setPozycjaString(String pozycjaString) {
        this.pozycjaString = pozycjaString;
    }

    public String getPozycjaSymbol() {
        return pozycjaSymbol;
    }

    public void setPozycjaSymbol(String pozycjaSymbol) {
        this.pozycjaSymbol = pozycjaSymbol;
    }

    public Integer getPozycjanr() {
        return pozycjanr;
    }

    public void setPozycjanr(Integer pozycjanr) {
        this.pozycjanr = pozycjanr;
    }

    public int getBilanslubrzis() {
        return bilanslubrzis;
    }

    public void setBilanslubrzis(int bilanslubrzis) {
        this.bilanslubrzis = bilanslubrzis;
    }

   

    public List<KontoKwota> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(List<KontoKwota> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }



    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getUklad() {
        return uklad;
    }

    public void setUklad(String uklad) {
        this.uklad = uklad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lp != null ? lp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PozycjaRZiSBilans)) {
            return false;
        }
        PozycjaRZiSBilans other = (PozycjaRZiSBilans) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Pozycjarzis[ lp=" + lp + " ]";
    }
    
}
