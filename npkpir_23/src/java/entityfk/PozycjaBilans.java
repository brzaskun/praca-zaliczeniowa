/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.KontoKwota;
import embeddablefk.StronaWierszaKwota;
import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", name = "Pozycjabilans",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pozycjaString", "podatnik", "rok", "uklad", "przychod0koszt1"})
})
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "PozycjaBilans")
@NamedQueries({
    @NamedQuery(name = "PozycjaBilans.findAll", query = "SELECT p FROM PozycjaBilans p"),
    @NamedQuery(name = "PozycjaBilans.Delete", query = "DELETE FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findByMaxLevelPodatnikAktywa", query = "SELECT MAX(p.level) FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok  AND p.przychod0koszt1 = '0'"),
    @NamedQuery(name = "PozycjaBilans.findByMaxLevelPodatnikPasywa", query = "SELECT MAX(p.level) FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok  AND p.przychod0koszt1 = '1'"),
    @NamedQuery(name = "PozycjaBilans.findByLp", query = "SELECT p FROM PozycjaBilans p WHERE p.lp = :lp"),
    @NamedQuery(name = "PozycjaBilans.findByFormula", query = "SELECT p FROM PozycjaBilans p WHERE p.formula = :formula"),
    @NamedQuery(name = "PozycjaBilans.findByKwota", query = "SELECT p FROM PozycjaBilans p WHERE p.kwota = :kwota"),
    @NamedQuery(name = "PozycjaBilans.findByLevel", query = "SELECT p FROM PozycjaBilans p WHERE p.level = :level"),
    @NamedQuery(name = "PozycjaBilans.findByMacierzysty", query = "SELECT p FROM PozycjaBilans p WHERE p.macierzysty = :macierzysty"),
    @NamedQuery(name = "PozycjaBilans.findByNazwa", query = "SELECT p FROM PozycjaBilans p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "PozycjaBilans.findByPodatnik", query = "SELECT p FROM PozycjaBilans p WHERE p.podatnik = :podatnik"),
    @NamedQuery(name = "PozycjaBilans.findByPozycjaString", query = "SELECT p FROM PozycjaBilans p WHERE p.pozycjaString = :pozycjaString"),
    @NamedQuery(name = "PozycjaBilans.findByPozycjaSymbol", query = "SELECT p FROM PozycjaBilans p WHERE p.pozycjaSymbol = :pozycjaSymbol"),
    @NamedQuery(name = "PozycjaBilans.findByPozycjanr", query = "SELECT p FROM PozycjaBilans p WHERE p.pozycjanr = :pozycjanr"),
    @NamedQuery(name = "PozycjaBilans.findByPrzychod0koszt1", query = "SELECT p FROM PozycjaBilans p WHERE p.przychod0koszt1 = :przychod0koszt1"),
    @NamedQuery(name = "PozycjaBilans.findByPrzyporzadkowanekonta", query = "SELECT p FROM PozycjaBilans p WHERE p.przyporzadkowanekonta = :przyporzadkowanekonta"),
    @NamedQuery(name = "PozycjaBilans.findByRok", query = "SELECT p FROM PozycjaBilans p WHERE p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRokAktywa", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok AND p.przychod0koszt1 = '0'"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRokPasywa", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok AND p.przychod0koszt1 = '1'"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRok", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findByUklad", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad")})
public class PozycjaBilans extends PozycjaRZiSBilans implements Serializable {
   

                
    public PozycjaBilans() {
    }
    
    public PozycjaBilans(Integer lp) {
        this.lp = lp;
    }
    
    public PozycjaBilans(PozycjaBilans pozycjaBilans) {
        this.pozycjanr = pozycjaBilans.getPozycjanr();
        this.pozycjaString = pozycjaBilans.getPozycjaString();
        this.pozycjaSymbol = pozycjaBilans.getPozycjaSymbol();
        this.macierzysty = pozycjaBilans.getMacierzysty();
        this.level = pozycjaBilans.getLevel();
        this.nazwa = pozycjaBilans.getNazwa();
        this.przychod0koszt1 = pozycjaBilans.isPrzychod0koszt1();
        this.lp = pozycjaBilans.getLp();
        this.formula = "";
    }

    public PozycjaBilans(int pozycjanr, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, int lp) {
        
    }
    

    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.formula = "";
    }
    
    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota) {
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

    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, String formula) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
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

    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    public List<KontoKwota> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

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
    public int hashCode() {
        int hash = 0;
        hash += (lp != null ? lp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PozycjaBilans)) {
            return false;
        }
        PozycjaBilans other = (PozycjaBilans) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PozycjaBilans{" + "lp=" + lp + ", formula=" + formula + ", nazwa=" + nazwa + ", pozycjaString=" + pozycjaString + ", pozycjaSymbol=" + pozycjaSymbol + ", pozycjanr=" + pozycjanr + ", rok=" + rok + ", uklad=" + uklad + '}';
    }

   
    
}
