/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnik","rok"})
})
@NamedQueries({
    @NamedQuery(name = "SprFinKwotyInfDod.findAll", query = "SELECT m FROM SprFinKwotyInfDod m"),
    @NamedQuery(name = "SprFinKwotyInfDod.findsprfinkwoty", query = "SELECT m FROM SprFinKwotyInfDod m WHERE m.podatnik = :podatnik AND m.rok = :rok")
})
public class SprFinKwotyInfDod implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "rok")
    private String rok;
    @Column(name = "pid1A")
    private BigDecimal pid1A;
    @Column(name = "pid1B")
    private BigDecimal pid1B;
    @Column(name = "pid2A")
    private BigDecimal pid2A;
    @Column(name = "pid2B")
    private BigDecimal pid2B;
    @Column(name = "pid3A")
    private BigDecimal pid3A;
    @Column(name = "pid3B")
    private BigDecimal pid3B;
    @Column(name = "pid4A")
    private BigDecimal pid4A;
    @Column(name = "pid4B")
    private BigDecimal pid4B;
    @Column(name = "pid5A")
    private BigDecimal pid5A;
    @Column(name = "pid5B")
    private BigDecimal pid5B;
    @Column(name = "pid6A")
    private BigDecimal pid6A;
    @Column(name = "pid6B")
    private BigDecimal pid6B;
    @Column(name = "pid7A")
    private BigDecimal pid7A;
    @Column(name = "pid7B")
    private BigDecimal pid7B;
    @Column(name = "pid8A")
    private BigDecimal pid8A;
    @Column(name = "pid8B")
    private BigDecimal pid8B;
    @Column(name = "pid9A")
    private BigDecimal pid9A;
    @Column(name = "pid9B")
    private BigDecimal pid9B;
    @Column(name = "pid10A")
    private BigDecimal pid10A;
    @Column(name = "pid10B")
    private BigDecimal pid10B;
    @Column(name = "pid11A")
    private BigDecimal pid11A;
    @Column(name = "pid11B")
    private BigDecimal pid11B;
    @Column(name = "datasporzadzenia")
    private String datasporzadzenia;
    @Column(name = "plik")
    @Lob
    private byte[] plik;
    @Column(name = "nazwapliku")
    private String nazwapliku;
    @Column(name = "dataod")
    private String dataod;
    @Column(name = "datado")
    private String datado;
    @Column(name = "plikxml")
    @Lob
    private byte[] plikxml;
    @Column(name = "nazwaplikuxml")
    private String nazwaplikuxml;
    //podstawowy rodzaj działalności
    @Column(name = "ppdzialalnosci")
    private String ppdzialalnosci;
    //pozostały rodzaj działalności
    @Column(name = "pozpdzialalnosci")
    private String pozpdzialalnosci;
    @Column(name = "sad")
    private String sad;
    @Column(name = "dopodzialu")
    private double dopodzialu;
    @Column(name = "kapitalrezerwowy")
    private double kapitalrezerwowy;
    @Column(name = "stratazlatubieglych")
    private double stratazlatubieglych;
    @Column(name = "wynikniepodzielony")
    private double wynikniepodzielony;
    @Column(name = "pracownicy")
    private double pracownicy;
    @Column(name = "zleceniobiorcy")
    private double zleceniobiorcy;
    @Column(name = "inni")
    private double inni;
    @Column(name = "sumabilansowa")
    private double sumabilansowa;
    @Column(name = "zyskstratanetto")
    private double zyskstratanetto;
    

    public SprFinKwotyInfDod() {
        this.pid1A = BigDecimal.ZERO;
        this.pid1B = BigDecimal.ZERO;
        this.pid2A = BigDecimal.ZERO;
        this.pid2B = BigDecimal.ZERO;
        this.pid3A = BigDecimal.ZERO;
        this.pid3B = BigDecimal.ZERO;
        this.pid4A = BigDecimal.ZERO;
        this.pid4B = BigDecimal.ZERO;
        this.pid5A = BigDecimal.ZERO;
        this.pid5B = BigDecimal.ZERO;
        this.pid6A = BigDecimal.ZERO;
        this.pid6B = BigDecimal.ZERO;
        this.pid7A = BigDecimal.ZERO;
        this.pid7B = BigDecimal.ZERO;
        this.pid8A = BigDecimal.ZERO;
        this.pid8B = BigDecimal.ZERO;
        this.pid9A = BigDecimal.ZERO;
        this.pid9B = BigDecimal.ZERO;
        this.pid10A = BigDecimal.ZERO;
        this.pid10B = BigDecimal.ZERO;
        this.pid11A = BigDecimal.ZERO;
        this.pid11B = BigDecimal.ZERO;
    }

    public SprFinKwotyInfDod(Podatnik podatnikObiekt, String rokWpisuSt) {
        this.podatnik = podatnikObiekt;
        this.rok = rokWpisuSt;
        this.pid1A = BigDecimal.ZERO;
        this.pid1B = BigDecimal.ZERO;
        this.pid2A = BigDecimal.ZERO;
        this.pid2B = BigDecimal.ZERO;
        this.pid3A = BigDecimal.ZERO;
        this.pid3B = BigDecimal.ZERO;
        this.pid4A = BigDecimal.ZERO;
        this.pid4B = BigDecimal.ZERO;
        this.pid5A = BigDecimal.ZERO;
        this.pid5B = BigDecimal.ZERO;
        this.pid6A = BigDecimal.ZERO;
        this.pid6B = BigDecimal.ZERO;
        this.pid7A = BigDecimal.ZERO;
        this.pid7B = BigDecimal.ZERO;
        this.pid8A = BigDecimal.ZERO;
        this.pid8B = BigDecimal.ZERO;
        this.pid9A = BigDecimal.ZERO;
        this.pid9B = BigDecimal.ZERO;
        this.pid10A = BigDecimal.ZERO;
        this.pid10B = BigDecimal.ZERO;
        this.pid11A = BigDecimal.ZERO;
        this.pid11B = BigDecimal.ZERO;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.podatnik);
        hash = 67 * hash + Objects.hashCode(this.rok);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SprFinKwotyInfDod other = (SprFinKwotyInfDod) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public BigDecimal getPid1A() {
        return pid1A;
    }

    public void setPid1A(BigDecimal pid1A) {
        this.pid1A = pid1A;
    }

    public BigDecimal getPid1B() {
        return pid1B;
    }

    public void setPid1B(BigDecimal pid1B) {
        this.pid1B = pid1B;
    }

    public BigDecimal getPid2A() {
        return pid2A;
    }

    public void setPid2A(BigDecimal pid2A) {
        this.pid2A = pid2A;
    }

    public BigDecimal getPid2B() {
        return pid2B;
    }

    public void setPid2B(BigDecimal pid2B) {
        this.pid2B = pid2B;
    }

    public BigDecimal getPid3A() {
        return pid3A;
    }

    public void setPid3A(BigDecimal pid3A) {
        this.pid3A = pid3A;
    }

    public BigDecimal getPid3B() {
        return pid3B;
    }

    public void setPid3B(BigDecimal pid3B) {
        this.pid3B = pid3B;
    }

    public BigDecimal getPid4A() {
        return pid4A;
    }

    public void setPid4A(BigDecimal pid4A) {
        this.pid4A = pid4A;
    }

    public BigDecimal getPid4B() {
        return pid4B;
    }

    public void setPid4B(BigDecimal pid4B) {
        this.pid4B = pid4B;
    }

    public BigDecimal getPid5A() {
        return pid5A;
    }

    public void setPid5A(BigDecimal pid5A) {
        this.pid5A = pid5A;
    }

    public BigDecimal getPid5B() {
        return pid5B;
    }

    public void setPid5B(BigDecimal pid5B) {
        this.pid5B = pid5B;
    }

    public BigDecimal getPid6A() {
        return pid6A;
    }

    public void setPid6A(BigDecimal pid6A) {
        this.pid6A = pid6A;
    }

    public BigDecimal getPid6B() {
        return pid6B;
    }

    public void setPid6B(BigDecimal pid6B) {
        this.pid6B = pid6B;
    }

    public BigDecimal getPid7A() {
        return pid7A;
    }

    public void setPid7A(BigDecimal pid7A) {
        this.pid7A = pid7A;
    }

    public BigDecimal getPid7B() {
        return pid7B;
    }

    public void setPid7B(BigDecimal pid7B) {
        this.pid7B = pid7B;
    }

    public BigDecimal getPid8A() {
        return pid8A;
    }

    public void setPid8A(BigDecimal pid8A) {
        this.pid8A = pid8A;
    }

    public BigDecimal getPid8B() {
        return pid8B;
    }

    public void setPid8B(BigDecimal pid8B) {
        this.pid8B = pid8B;
    }

    public BigDecimal getPid9A() {
        return pid9A;
    }

    public void setPid9A(BigDecimal pid9A) {
        this.pid9A = pid9A;
    }

    public BigDecimal getPid9B() {
        return pid9B;
    }

    public void setPid9B(BigDecimal pid9B) {
        this.pid9B = pid9B;
    }

    public BigDecimal getPid10A() {
        return pid10A;
    }

    public void setPid10A(BigDecimal pid10A) {
        this.pid10A = pid10A;
    }

    public BigDecimal getPid10B() {
        return pid10B;
    }

    public void setPid10B(BigDecimal pid10B) {
        this.pid10B = pid10B;
    }

    public BigDecimal getPid11A() {
        return pid11A;
    }

    public void setPid11A(BigDecimal pid11A) {
        this.pid11A = pid11A;
    }

    public BigDecimal getPid11B() {
        return pid11B;
    }

    public void setPid11B(BigDecimal pid11B) {
        this.pid11B = pid11B;
    }

    public String getDatasporzadzenia() {
        return datasporzadzenia;
    }

    public void setDatasporzadzenia(String datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public byte[] getPlik() {
        return plik;
    }

    public void setPlik(byte[] plik) {
        this.plik = plik;
    }

    public String getNazwapliku() {
        return nazwapliku;
    }

    public void setNazwapliku(String nazwapliku) {
        this.nazwapliku = nazwapliku;
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

    public byte[] getPlikxml() {
        return plikxml;
    }

    public void setPlikxml(byte[] plikxml) {
        this.plikxml = plikxml;
    }

    public String getNazwaplikuxml() {
        return nazwaplikuxml;
    }

    public void setNazwaplikuxml(String nazwaplikuxml) {
        this.nazwaplikuxml = nazwaplikuxml;
    }

    public String getPpdzialalnosci() {
        return ppdzialalnosci;
    }

    public void setPpdzialalnosci(String ppdzialalnosci) {
        this.ppdzialalnosci = ppdzialalnosci;
    }

    public String getPozpdzialalnosci() {
        return pozpdzialalnosci;
    }

    public void setPozpdzialalnosci(String pozpdzialalnosci) {
        this.pozpdzialalnosci = pozpdzialalnosci;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public double getDopodzialu() {
        return dopodzialu;
    }

    public void setDopodzialu(double dopodzialu) {
        this.dopodzialu = dopodzialu;
    }

    public double getKapitalrezerwowy() {
        return kapitalrezerwowy;
    }

    public void setKapitalrezerwowy(double kapitalrezerwowy) {
        this.kapitalrezerwowy = kapitalrezerwowy;
    }

    public double getWynikniepodzielony() {
        return wynikniepodzielony;
    }

    public void setWynikniepodzielony(double wynikniepodzielony) {
        this.wynikniepodzielony = wynikniepodzielony;
    }

    public double getPracownicy() {
        return pracownicy;
    }

    public void setPracownicy(double pracownicy) {
        this.pracownicy = pracownicy;
    }

    public double getZleceniobiorcy() {
        return zleceniobiorcy;
    }

    public void setZleceniobiorcy(double zleceniobiorcy) {
        this.zleceniobiorcy = zleceniobiorcy;
    }

    public double getInni() {
        return inni;
    }

    public void setInni(double inni) {
        this.inni = inni;
    }

    public double getStratazlatubieglych() {
        return stratazlatubieglych;
    }

    public void setStratazlatubieglych(double stratazlatubieglych) {
        this.stratazlatubieglych = stratazlatubieglych;
    }

    public double getSumabilansowa() {
        return sumabilansowa;
    }

    public void setSumabilansowa(double sumabilansowa) {
        this.sumabilansowa = sumabilansowa;
    }

    public double getZyskstratanetto() {
        return zyskstratanetto;
    }

    public void setZyskstratanetto(double zyskstratanetto) {
        this.zyskstratanetto = zyskstratanetto;
    }

    
    
    
}
