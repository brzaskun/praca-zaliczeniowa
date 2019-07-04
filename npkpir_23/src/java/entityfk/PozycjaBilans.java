/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "Pozycjabilans",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pozycjaString", "podatnik", "rok", "uklad", "przychod0koszt1"})
})
@XmlRootElement
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorValue(value = "PozycjaBilans")
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
    @NamedQuery(name = "PozycjaBilans.findByRok", query = "SELECT p FROM PozycjaBilans p WHERE p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRokAktywa", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok AND p.przychod0koszt1 = '0'"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRokPasywa", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok AND p.przychod0koszt1 = '1'"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRokAtywaPasywa", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findByUkladPodRok", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad AND  p.podatnik = :podatnik AND p.rok = :rok"),
    @NamedQuery(name = "PozycjaBilans.findBilansPozString", query = "SELECT p FROM PozycjaBilans p WHERE p.pozycjaString = :pozycjaString AND p.rok = :rok AND p.uklad = :uklad"),
    @NamedQuery(name = "PozycjaBilans.findByUklad", query = "SELECT p FROM PozycjaBilans p WHERE p.uklad = :uklad")})
public class PozycjaBilans extends PozycjaRZiSBilans implements Serializable {
   @JoinColumn(name = "macierzysta", referencedColumnName = "lp")
    protected PozycjaBilans macierzysta;

                
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
    

    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, PozycjaBilans macierzysta, int level, String nazwa, boolean przychod0koszt1) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysta = macierzysta;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.formula = "";
    }
    
    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, PozycjaBilans macierzysta, int level, String nazwa, boolean przychod0koszt1, double kwota) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysta = macierzysta;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = kwota;
        this.formula = "";
    }

    public PozycjaBilans(int lp, String pozycjaString, String pozycjaSymbol, PozycjaBilans macierzysta, int level, String nazwa, boolean przychod0koszt1, String formula) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysta = macierzysta;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = 0.0;
        this.formula = formula;
    }

    @Override
    public int getLp() {
        return lp;
    }

    @Override
    public void setLp(int lp) {
        this.lp = lp;
    }

    @Override
    public int getMacierzysty() {
        return macierzysta.getLp();
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
    public List<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

   @Override
    public void setPrzyporzadkowanekonta(List<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    @Override
     public List<StronaWiersza> getPrzyporzadkowanestronywiersza() {
        return przyporzadkowanestronywiersza;
    }

    @Override
    public void setPrzyporzadkowanestronywiersza(List<StronaWiersza> przyporzadkowanestronywiersza) {
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

    public PozycjaBilans getMacierzysta() {
        return macierzysta;
    }

    public void setMacierzysta(PozycjaBilans macierzysta) {
        this.macierzysta = macierzysta;
    }

    public String getNumeryKont() {
        String zwrot = "";
        if (this.przyporzadkowanekonta!=null && !this.przyporzadkowanekonta.isEmpty()) {
            for (Konto p : this.przyporzadkowanekonta) {
                zwrot += p.getPelnynumer();
                zwrot +=", ";
            }
        }
        return zwrot;
    }
    

   
    @Override
    public String toString() {
        return "PozycjaBilans{" + "lp=" + lp + ", formula=" + formula + ", nazwa=" + nazwa + ", pozycjaString=" + pozycjaString + ", pozycjaSymbol=" + pozycjaSymbol + ", pozycjanr=" + pozycjanr + ", rok=" + rok + ", uklad=" + uklad + '}';
    }

   
    
}
