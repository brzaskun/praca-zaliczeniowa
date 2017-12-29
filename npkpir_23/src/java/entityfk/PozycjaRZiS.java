/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.KontoKwota;
import embeddablefk.StronaWierszaKwota;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "Pozycjarzis",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pozycjaString", "podatnik", "rok", "uklad"})})
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "PozycjaRZiS")
@NamedQueries({
    @NamedQuery(name = "PozycjaRZiS.findAll", query = "SELECT p FROM PozycjaRZiS p"),
    @NamedQuery(name = "PozycjaRZiS.Delete", query = "DELETE FROM PozycjaRZiS p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaRZiS.findByMaxLevelPodatnik", query = "SELECT MAX(p.level) FROM PozycjaRZiS p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
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
@Cacheable
public class PozycjaRZiS extends PozycjaRZiSBilans implements Serializable {
    
    @Transient
    private Map<String,Double> mce;
    
    public PozycjaRZiS() {
        
    }
    
    public PozycjaRZiS(Integer lp) {
        this.lp = lp;
    }
    
    public PozycjaRZiS(PozycjaRZiS pozycjaRZiS) {
        this.pozycjanr = pozycjaRZiS.getPozycjanr();
        this.pozycjaString = pozycjaRZiS.getPozycjaString();
        this.pozycjaSymbol = pozycjaRZiS.getPozycjaSymbol();
        this.macierzysty = pozycjaRZiS.getMacierzysty();
        this.level = pozycjaRZiS.getLevel();
        this.nazwa = pozycjaRZiS.getNazwa();
        this.przychod0koszt1 = pozycjaRZiS.isPrzychod0koszt1();
        this.lp = pozycjaRZiS.getLp();
        this.formula = "";
    }

    

    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.formula = "";
    }
    
    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = kwota;
        this.formula = "";
    }

    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, String formula) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = 0.0;
        if (formula != null ) {
            this.formula = formula;
        } else {
            this.formula = "";
        }
        
    }
    
    @Override
    public Integer getLp() {
        return lp;
    }

    @Override
    public void setLp(Integer lp) {
        this.lp = lp;
    }

    @Override
    public String getFormula() {
        return formula;
    }

    @Override
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public double getKwota() {
        return kwota;
    }

    @Override
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

    @Override
    public String getNazwa() {
        return nazwa;
    }

    @Override
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String getPodatnik() {
        return podatnik;
    }

    @Override
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    @Override
    public String getPozycjaString() {
        return pozycjaString;
    }

    @Override
    public void setPozycjaString(String pozycjaString) {
        this.pozycjaString = pozycjaString;
    }

    @Override
    public String getPozycjaSymbol() {
        return pozycjaSymbol;
    }

    @Override
    public void setPozycjaSymbol(String pozycjaSymbol) {
        this.pozycjaSymbol = pozycjaSymbol;
    }

    @Override
    public Integer getPozycjanr() {
        return pozycjanr;
    }

    @Override
    public void setPozycjanr(Integer pozycjanr) {
        this.pozycjanr = pozycjanr;
    }

    @Override
    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    @Override
    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    @Override
    public List<KontoKwota> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    @Override
    public void setPrzyporzadkowanekonta(List<KontoKwota> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    @Override
     public List<StronaWierszaKwota> getPrzyporzadkowanestronywiersza() {
        return przyporzadkowanestronywiersza;
    }

    @Override
    public void setPrzyporzadkowanestronywiersza(List<StronaWierszaKwota> przyporzadkowanestronywiersza) {
        this.przyporzadkowanestronywiersza = przyporzadkowanestronywiersza;
    }

    @Override
    public String getRok() {
        return rok;
    }

    @Override
    public void setRok(String rok) {
        this.rok = rok;
    }

    @Override
    public String getUklad() {
        return uklad;
    }

    @Override
    public void setUklad(String uklad) {
        this.uklad = uklad;
    }
    @Override
    public String getDe() {
        return de;
    }
    @Override
    public void setDe(String de) {
        this.de = de;
    }
    
    @Override
    public double getKwotabo() {
        return kwotabo;
    }

    @Override
    public void setKwotabo(double kwotabo) {
        this.kwotabo = kwotabo;
    }

    public Map<String, Double> getMce() {
        return mce;
    }

    public void setMce(Map<String, Double> mce) {
        this.mce = mce;
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
        if (!(object instanceof PozycjaRZiS)) {
            return false;
        }
        PozycjaRZiS other = (PozycjaRZiS) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    
    
}
