/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
    @NamedQuery(name = "Rodzajedok.findBySkrot", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajedokPK.skrotNazwyDok = :skrot"),
    @NamedQuery(name = "Rodzajedok.findBySkrotPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajedokPK.skrotNazwyDok = :skrot AND r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByNazwa", query = "SELECT r FROM Rodzajedok r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajedok.findByRodzajtransakcji", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Rodzajedok.findByWzorzec", query = "SELECT r FROM Rodzajedok r WHERE r.wzorzec = :wzorzec"),
    @NamedQuery(name = "Rodzajedok.findByPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByListaWspolna", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajedokPK.podatnik = '0001005008'"),
    @NamedQuery(name = "Rodzajedok.findByKategoriaDokumentu", query = "SELECT r FROM Rodzajedok r WHERE r.kategoriadokumentu = :kategoriadokumentu")
})
public class Rodzajedok implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private RodzajedokPK rodzajedokPK;
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
    @MapsId("podatnik")
    @ManyToOne
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
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

    public Rodzajedok() {
        this.rodzajedokPK = new RodzajedokPK();
    }

    public Rodzajedok(RodzajedokPK rodzajedokPK) {
        this.rodzajedokPK = rodzajedokPK;
    }

    
    public Rodzajedok(String skrot) {
        RodzajedokPK rodzajedokPK = new RodzajedokPK(skrot);
        this.setRodzajedokPK(rodzajedokPK);
    }
    
    public Rodzajedok(String skrot, Podatnik podatnik) {
        this.rodzajedokPK = new RodzajedokPK("VAT", podatnik.getNazwapelna());
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
    

    public RodzajedokPK getRodzajedokPK() {
        return rodzajedokPK;
    }

    public void setRodzajedokPK(RodzajedokPK rodzajedokPK) {
        this.rodzajedokPK = rodzajedokPK;
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
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.rodzajedokPK);
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
        final Rodzajedok other = (Rodzajedok) obj;
        if (!Objects.equals(this.rodzajedokPK, other.rodzajedokPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rodzajedok{" + "rodzajedokPK=" + rodzajedokPK + ", nazwa=" + nazwa + '}';
    }
    

    
}
