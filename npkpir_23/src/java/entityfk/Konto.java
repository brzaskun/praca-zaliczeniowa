/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import abstractClasses.ToBeATreeNodeObject;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "konto", uniqueConstraints = {@UniqueConstraint(columnNames={"podatnik","pelnynumer","rok"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
    @NamedQuery(name = "Konto.findById", query = "SELECT k FROM Konto k WHERE k.id = :id"),
    @NamedQuery(name = "Konto.findByPodatnik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByKontaPodatnikaBO", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'bilansowe' AND k.pelnynumer LIKE :wzorzec AND k.mapotomkow = 0 AND k.bilansowewynikowe = 'bilansowe' AND k.nrkonta != 0 "),
    @NamedQuery(name = "Konto.findByPodatnikBilansowe", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'bilansowe'"),
    @NamedQuery(name = "Konto.findByPodatnik490", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.nrkonta = '490'"),
    @NamedQuery(name = "Konto.findByNrkonta", query = "SELECT k FROM Konto k WHERE k.nrkonta = :nrkonta"),
    @NamedQuery(name = "Konto.findBySyntetyczne", query = "SELECT k FROM Konto k WHERE k.syntetyczne = :syntetyczne"),
    @NamedQuery(name = "Konto.findByLevel", query = "SELECT k FROM Konto k WHERE k.level = :level"),
    @NamedQuery(name = "Konto.findByNazwapelna", query = "SELECT k FROM Konto k WHERE k.nazwapelna = :nazwapelna"),
    @NamedQuery(name = "Konto.findByNazwaskrocona", query = "SELECT k FROM Konto k WHERE k.nazwaskrocona = :nazwaskrocona"),
    @NamedQuery(name = "Konto.findByBilansowewynikowe", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = :bilansowewynikowe"),
    @NamedQuery(name = "Konto.findByBilansowewynikowePodatnik", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = :bilansowewynikowe AND k.podatnik = :podatnik AND k.mapotomkow = false AND k.nrkonta != 0"),
    @NamedQuery(name = "Konto.findByZwyklerozrachszczegolne", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne"),
    @NamedQuery(name = "Konto.findByRozrachunkowePodatnik", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne AND k.podatnik = :podatnik AND k.mapotomkow = true AND k.slownikowe = 0 AND k.nrkonta != 0"),
    @NamedQuery(name = "Konto.findByVATPodatnik", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne AND k.podatnik = :podatnik AND k.mapotomkow = false AND k.nrkonta != 0"),
    @NamedQuery(name = "Konto.findByMacierzyste", query = "SELECT k FROM Konto k WHERE k.macierzyste = :macierzyste AND NOT k.pelnynumer = '000'"),
    @NamedQuery(name = "Konto.findByMacierzysteBOPodatnik", query = "SELECT k FROM Konto k WHERE k.macierzyste = :macierzyste AND k.podatnik = :podatnik AND NOT k.pelnynumer = '000'"),
    @NamedQuery(name = "Konto.findByMacierzystePodatnikCOUNT", query = "SELECT COUNT(k) FROM Konto k WHERE k.macierzyste = :macierzyste AND k.podatnik = :podatnik AND NOT k.pelnynumer = '000'"),
    @NamedQuery(name = "Konto.findByPozycjaWynikowe", query = "SELECT k FROM Konto k WHERE k.pozycja = :pozycja AND k.bilansowewynikowe = 'wynikowe' AND k.pozycjonowane = 1"),
    @NamedQuery(name = "Konto.findByPozycjaBilansowe", query = "SELECT k FROM Konto k WHERE k.pozycja = :pozycja AND k.bilansowewynikowe = 'bilansowe' AND k.pozycjonowane = 1"),
    @NamedQuery(name = "Konto.findByMacierzysteWynikowe", query = "SELECT k FROM Konto k WHERE k.macierzyste = :macierzyste AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'wynikowe' AND k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByMacierzysteBilansowe", query = "SELECT k FROM Konto k WHERE k.macierzyste = :macierzyste AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'bilansowe' AND k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByPelnynumer", query = "SELECT k FROM Konto k WHERE k.pelnynumer = :pelnynumer"),
    @NamedQuery(name = "Konto.findByPelnynumerPodatnik", query = "SELECT k FROM Konto k WHERE k.pelnynumer = :pelnynumer AND k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByNazwaPodatnik", query = "SELECT k FROM Konto k WHERE k.nazwaskrocona = :nazwaskrocona AND k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByMapotomkow", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownik", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0'"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownikPodatnik", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0' AND k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByMaSlownik", query = "SELECT k FROM Konto k WHERE k.maslownik = :maslownik"),
    @NamedQuery(name = "Konto.findByRozwin", query = "SELECT k FROM Konto k WHERE k.rozwin = :rozwin"),
    @NamedQuery(name = "Konto.updateMapotomkow", query = "UPDATE Konto k SET k.mapotomkow = '0' WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.updateZablokowane", query = "UPDATE Konto k SET k.blokada = '0' WHERE k.podatnik = :podatnik")})
public class Konto extends ToBeATreeNodeObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta")
    private String nrkonta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "syntetyczne")
    private String syntetyczne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "analityka")
    private int level;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "nazwapelna")
    private String nazwapelna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nazwaskrocona")
    private String nazwaskrocona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "bilansowewynikowe")
    private String bilansowewynikowe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "zwyklerozrachszczegolne")
    private String zwyklerozrachszczegolne;
    @Lob
    @Column(name = "pozycja")
    private String pozycja;
    @Column(name = "pozycjonowane")
    private boolean pozycjonowane;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "macierzyste")
    private String macierzyste;
    @Basic(optional = false)
    @NotNull
    @Column(name = "macierzysty")
    private int macierzysty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pelnynumer")
    private String pelnynumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mapotomkow")
    private boolean mapotomkow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rozwin")
    private boolean rozwin;
    @Basic(optional = false)
    @Column(name = "rok")
    private int rok;
    @Basic(optional = false)
    @Column(name = "boWn")
    private double boWn;
    @Basic(optional = false)
    @Column(name = "boMa")
    private double boMa;
    @Basic(optional = false)
    @Column(name = "blokada")
    private boolean blokada;
    @Basic(optional = false)
    @Column(name = "slownikowe")
    private boolean slownikowe;
    @Basic(optional = false)
    @Column(name = "maslownik")
    private boolean maslownik;
    @OneToMany(mappedBy = "konto")
    private List<StronaWiersza> stronaWiersza;
    

    public Konto() {
        this.slownikowe = false;
        this.maslownik = false;
    }

    public Konto(Integer id) {
        this.id = id;
        
    }

    public Konto(Integer id, String podatnik, String nrkonta, String syntetyczne, int analityka, String nazwapelna, String nazwaskrocona, String bilansowewynikowe, String zwyklerozrachszczegolne, String macierzyste, String pelnynumer, boolean rozwin, int rok) {
        this.id = id;
        this.podatnik = podatnik;
        this.nrkonta = nrkonta;
        this.syntetyczne = syntetyczne;
        this.level = analityka;
        this.nazwapelna = nazwapelna;
        this.nazwaskrocona = nazwaskrocona;
        this.bilansowewynikowe = bilansowewynikowe;
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
        this.macierzyste = macierzyste;
        this.pelnynumer = pelnynumer;
        this.rozwin = rozwin;
        this.rok = rok;
        this.boWn = 0.0;
        this.boMa = 0.0;
        this.slownikowe = false;
        this.maslownik = false;
    }   
    
    public void getFinallChildren(List<Konto> listakontwszystkie, String podatnik, SessionFacade kontoFacade) {
        List<Konto> children = kontoFacade.findKontaPotomnePodatnik(podatnik, this.pelnynumer);
        if (!children.isEmpty()) {
            for (Konto o : children) {
                listakontwszystkie.add(o);
                o.getFinallChildren(listakontwszystkie,podatnik, kontoFacade);
            }
        }
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

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getSyntetyczne() {
        return syntetyczne;
    }

    public void setSyntetyczne(String syntetyczne) {
        this.syntetyczne = syntetyczne;
    }

    @Override
    public int getLevel() {
        return level;
    }
    
  
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPozycjonowane() {
        return pozycjonowane;
    }

    public void setPozycjonowane(boolean pozycjonowane) {
        this.pozycjonowane = pozycjonowane;
    }
    

    public String getNazwapelna() {
        return nazwapelna;
    }

    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }

    public String getNazwaskrocona() {
        return nazwaskrocona;
    }

    public void setNazwaskrocona(String nazwaskrocona) {
        this.nazwaskrocona = nazwaskrocona;
    }

    public String getBilansowewynikowe() {
        return bilansowewynikowe;
    }

    public void setBilansowewynikowe(String bilansowewynikowe) {
        this.bilansowewynikowe = bilansowewynikowe;
    }

    public String getZwyklerozrachszczegolne() {
        return zwyklerozrachszczegolne;
    }

    public void setZwyklerozrachszczegolne(String zwyklerozrachszczegolne) {
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
    }

    public String getPozycja() {
        return pozycja;
    }

    public void setPozycja(String pozycja) {
        this.pozycja = pozycja;
    }

   
    public String getMacierzyste() {
        return macierzyste;
    }

    @Override
    public int getMacierzysty() {
        return macierzysty;
    }

    @Override
    public void setMacierzysty(int macierzysty) {
        this.macierzysty = macierzysty;
    }
  

    public void setMacierzyste(String macierzyste) {
        this.macierzyste = macierzyste;
    }

    public String getPelnynumer() {
        return pelnynumer;
    }

    public void setPelnynumer(String pelnynumer) {
        this.pelnynumer = pelnynumer;
    }

    public boolean isMapotomkow() {
        return mapotomkow;
    }

    public void setMapotomkow(boolean mapotomkow) {
        this.mapotomkow = mapotomkow;
    }
    
    public boolean getRozwin() {
        return rozwin;
    }

    public void setRozwin(boolean rozwin) {
        this.rozwin = rozwin;
    }
    
    public Integer getLp() {
        return this.id;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public double getBoWn() {
        return boWn;
    }

    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }

    public double getBoMa() {
        return boMa;
    }

    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }

   

    public boolean isBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

    public boolean isSlownikowe() {
        return slownikowe;
    }

    public void setSlownikowe(boolean slownikowe) {
        this.slownikowe = slownikowe;
    }

    public boolean isMaslownik() {
        return maslownik;
    }

    public void setMaslownik(boolean maslownik) {
        this.maslownik = maslownik;
    }

  
    public List<StronaWiersza> getStronaWiersza() {
        return stronaWiersza;
    }

    public void setStronaWiersza(List<StronaWiersza> stronaWiersza) {
        this.stronaWiersza = stronaWiersza;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Konto other = (Konto) obj;
        if (Objects.equals(this.podatnik, other.podatnik) && !Objects.equals(this.pelnynumer, other.pelnynumer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Konto{" + "id=" + id + ", podatnik=" + podatnik + ", nazwapelna=" + nazwapelna + ", pozycjonowane=" + pozycjonowane + ", pelnynumer=" + pelnynumer + ", mapotomkow=" + mapotomkow + '}';
    }

  

   
    
    
}
