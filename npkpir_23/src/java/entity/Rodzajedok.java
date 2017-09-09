/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajedok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajedok.findAll", query = "SELECT r FROM Rodzajedok r"),
    @NamedQuery(name = "Rodzajedok.findBySkrot", query = "SELECT r FROM Rodzajedok r WHERE r.skrotNazwyDok = :skrot"),
    @NamedQuery(name = "Rodzajedok.findBySkrotPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.skrotNazwyDok = :skrot AND r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByNazwa", query = "SELECT r FROM Rodzajedok r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajedok.findByRodzajtransakcji", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Rodzajedok.findByWzorzec", query = "SELECT r FROM Rodzajedok r WHERE r.wzorzec = :wzorzec"),
    @NamedQuery(name = "Rodzajedok.findByPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByListaWspolna", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByKategoriaDokumentu", query = "SELECT r FROM Rodzajedok r WHERE r.kategoriadokumentu = :kategoriadokumentu")
})
@Cacheable
public class Rodzajedok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name="SKROTNAZWYDOK")
    private String skrotNazwyDok;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "skrot")
    private String skrot;
    @Size(max = 255)
    @Column(name = "rodzajtransakcji")
    private String rodzajtransakcji;
    @Size(max = 255)
    @Column(name = "wzorzec")
    private String wzorzec;
//    <f:selectItem itemLabel="koszt z VAT" itemValue="1"/>
//    <f:selectItem itemLabel="przychód z VAT" itemValue="2"/>
//    <f:selectItem itemLabel="koszt bez VAT" itemValue="3"/>
//    <f:selectItem itemLabel="przychód bez VAT" itemValue="4"/>
//    <f:selectItem itemLabel="dokument prosty" itemValue="5"/>
//    <f:selectItem itemLabel="płatności" itemValue="0"/>
    @Column(name = "kategoriadokumentu")
    private int kategoriadokumentu;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnikObj;
    @ManyToOne
    @JoinColumn(name = "kontorozrachunkowe", referencedColumnName = "id")
    private Konto kontorozrachunkowe;
    @ManyToOne
    @JoinColumn(name = "kontovat", referencedColumnName = "id")
    private Konto kontovat;
    @ManyToOne
    @JoinColumn(name = "kontoRZiS", referencedColumnName = "id")
    private Konto kontoRZiS;
//    @OneToMany(mappedBy = "rodzajedok")
//    private List<Dokfk> dokumentyfk;
    @Column(name = "pokazkg")
    private boolean pokazkg;
    @Column(name = "pokazszt")
    private boolean pokazszt;
    @Column(name = "niepokazuj")
    private boolean niepokazuj;
    @Size(max = 255)
    @Column(name = "de")
    private String de;
    @Column(name = "procentvat")
    private double procentvat;
    @Column(name = "stawkavat")
    private double stawkavat;
   


    public Rodzajedok() {
        
    }

    
    public Rodzajedok(Rodzajedok rodzajedok, Podatnik podatnik) {
        this.skrotNazwyDok = rodzajedok.getSkrotNazwyDok();
        this.podatnikObj = podatnik;
        this.de = rodzajedok.getDe();
        this.kategoriadokumentu = rodzajedok.getKategoriadokumentu();
        this.nazwa = rodzajedok.getNazwa();
        this.niepokazuj = rodzajedok.isNiepokazuj();
        this.pokazkg = rodzajedok.isPokazkg();
        this.pokazszt = rodzajedok.isPokazszt();
        this.procentvat = rodzajedok.getProcentvat();
        this.rodzajtransakcji = rodzajedok.getRodzajtransakcji();
        this.skrot = rodzajedok.getSkrot();
        this.wzorzec = rodzajedok.getWzorzec();
    }

    
    
    public Rodzajedok(String skrot, Podatnik podatnik) {
        this.skrotNazwyDok = "VAT";
        this.podatnikObj = podatnikObj;
    }
    
     public Rodzajedok(String skrot, String skrot2) {
        this.skrot = skrot;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }
    
//    @XmlTransient
//    public List<Dokfk> getDokumentyfk() {
//        return dokumentyfk;
//    }
//
//    @XmlTransient
//    public void setDokumentyfk(List<Dokfk> dokumentyfk) {
//        this.dokumentyfk = dokumentyfk;
//    }

    public String getSkrotNazwyDok() {
        return skrotNazwyDok;
    }

    public void setSkrotNazwyDok(String skrotNazwyDok) {
        this.skrotNazwyDok = skrotNazwyDok;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRodzajtransakcji() {
        return rodzajtransakcji;
    }

    public double getProcentvat() {
        return procentvat;
    }

    public void setProcentvat(double procentvat) {
        this.procentvat = procentvat;
    }

    public void setRodzajtransakcji(String rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }

    public String getWzorzec() {
        return wzorzec;
    }

    public void setWzorzec(String wzorzec) {
        this.wzorzec = wzorzec;
    }

    public int getKategoriadokumentu() {
        return kategoriadokumentu;
    }

    public void setKategoriadokumentu(int kategoriadokumentu) {
        this.kategoriadokumentu = kategoriadokumentu;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public Konto getKontorozrachunkowe() {
        return kontorozrachunkowe;
    }

    public void setKontorozrachunkowe(Konto kontorozrachunkowe) {
        this.kontorozrachunkowe = kontorozrachunkowe;
    }

    public Konto getKontovat() {
        return kontovat;
    }

    public void setKontovat(Konto kontovat) {
        this.kontovat = kontovat;
    }

    public Konto getKontoRZiS() {
        return kontoRZiS;
    }

    public void setKontoRZiS(Konto kontoRZiS) {
        this.kontoRZiS = kontoRZiS;
    }

    public boolean isPokazkg() {
        return pokazkg;
    }

    public void setPokazkg(boolean pokazkg) {
        this.pokazkg = pokazkg;
    }

    public boolean isPokazszt() {
        return pokazszt;
    }

    public void setPokazszt(boolean pokazszt) {
        this.pokazszt = pokazszt;
    }

    public boolean isNiepokazuj() {
        return niepokazuj;
    }

    public void setNiepokazuj(boolean niepokazuj) {
        this.niepokazuj = niepokazuj;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public double getStawkavat() {
        return stawkavat;
    }

    public void setStawkavat(double stawkavat) {
        this.stawkavat = stawkavat;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.skrotNazwyDok);
        hash = 97 * hash + Objects.hashCode(this.podatnikObj);
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
        final Rodzajedok other = (Rodzajedok) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.skrotNazwyDok, other.skrotNazwyDok)) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rodzajedok{" + "skrotNazwyDok=" + skrotNazwyDok + ", kategoriadokumentu=" + kategoriadokumentu + ", podatnikObj=" + podatnikObj + '}';
    }
    
    
    

   
    

    
}
