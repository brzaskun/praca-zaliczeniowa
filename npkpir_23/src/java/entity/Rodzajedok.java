/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajedok",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"SKROTNAZWYDOK, podid, rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajedok.findAll", query = "SELECT r FROM Rodzajedok r"),
    @NamedQuery(name = "Rodzajedok.findBySkrot", query = "SELECT r FROM Rodzajedok r WHERE r.skrotNazwyDok = :skrot"),
    @NamedQuery(name = "Rodzajedok.findBySkrotPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.skrotNazwyDok = :skrot AND r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findBySkrotPodatnikRok", query = "SELECT r FROM Rodzajedok r WHERE r.skrotNazwyDok = :skrot AND r.podatnikObj = :podatnik AND r.rok = :rok"),
    @NamedQuery(name = "Rodzajedok.findByNazwa", query = "SELECT r FROM Rodzajedok r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajedok.findByRodzajtransakcji", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Rodzajedok.findByWzorzec", query = "SELECT r FROM Rodzajedok r WHERE r.wzorzec = :wzorzec"),
    @NamedQuery(name = "Rodzajedok.findByPodatnikNull", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik AND r.rok IS NULL"),
    @NamedQuery(name = "Rodzajedok.findByPodatnikRok", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik AND r.rok = :rok"),
    @NamedQuery(name = "Rodzajedok.findByListaWspolna", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByKategoriaDokumentu", query = "SELECT r FROM Rodzajedok r WHERE r.kategoriadokumentu = :kategoriadokumentu")
})
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
    @Column(name="tylkovatnalezny")
    private boolean tylkovatnalezny;
    @Column(name = "procentkup")
    private double procentkup;
    @Column(name = "tylkovat")
    private boolean tylkovat;
    @Column(name = "tylkojpk")
    private boolean tylkojpk;
    @Column(name = "jednostronny")
    private boolean jednostronny;
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "oznaczenie1", referencedColumnName = "id")
    private JPKoznaczenia oznaczenie1;
    @JoinColumn(name = "oznaczenie2", referencedColumnName = "id")
    private JPKoznaczenia oznaczenie2;


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
        this.procentkup = rodzajedok.getProcentkup();
        this.rodzajtransakcji = rodzajedok.getRodzajtransakcji();
        this.skrot = rodzajedok.getSkrot();
        this.wzorzec = rodzajedok.getWzorzec();
        this.stawkavat = rodzajedok.getStawkavat();
        this.tylkovatnalezny = rodzajedok.isTylkovatnalezny();
        this.tylkovat = rodzajedok.isTylkovat();
        this.tylkojpk = rodzajedok.isTylkojpk();
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

    public boolean isTylkovat() {
        return tylkovat;
    }

    public void setTylkovat(boolean tylkovat) {
        this.tylkovat = tylkovat;
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

    public double getProcentkup() {
        return procentkup;
    }

    public void setProcentkup(double procentkup) {
        this.procentkup = procentkup;
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

    public boolean isTylkovatnalezny() {
        return tylkovatnalezny;
    }

    public void setTylkovatnalezny(boolean tylkovatnalezny) {
        this.tylkovatnalezny = tylkovatnalezny;
    }

    public boolean isJednostronny() {
        return jednostronny;
    }

    public void setJednostronny(boolean jednostronny) {
        this.jednostronny = jednostronny;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public boolean isDokProsty() {
        boolean zwrot = true;
        if (this.kategoriadokumentu==1 || this.kategoriadokumentu==2) {
            zwrot = false;
        }
        return zwrot;
    }

    public JPKoznaczenia getOznaczenie1() {
        return oznaczenie1;
    }

    public void setOznaczenie1(JPKoznaczenia oznaczenie1) {
        this.oznaczenie1 = oznaczenie1;
    }

    public JPKoznaczenia getOznaczenie2() {
        return oznaczenie2;
    }

    public void setOznaczenie2(JPKoznaczenia oznaczenie2) {
        this.oznaczenie2 = oznaczenie2;
    }

    public boolean isTylkojpk() {
        return tylkojpk;
    }

    public void setTylkojpk(boolean tylkojpk) {
        this.tylkojpk = tylkojpk;
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
