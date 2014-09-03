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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Rodzajedok.findBySkrot", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajedokPK.skrot = :skrot"),
    @NamedQuery(name = "Rodzajedok.findByNazwa", query = "SELECT r FROM Rodzajedok r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajedok.findByRodzajtransakcji", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Rodzajedok.findByWzorzec", query = "SELECT r FROM Rodzajedok r WHERE r.wzorzec = :wzorzec"),
    @NamedQuery(name = "Rodzajedok.findByPodatnik", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj = :podatnik"),
    @NamedQuery(name = "Rodzajedok.findByListaWspolna", query = "SELECT r FROM Rodzajedok r WHERE r.podatnikObj IS NULL"),
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
    @Column(name = "rodzajtransakcji")
    private String rodzajtransakcji;
    @Size(max = 255)
    @Column(name = "wzorzec")
    private String wzorzec;
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

    public Rodzajedok() {
    }

    public Rodzajedok(String skrot) {
        RodzajedokPK rodzajedokPK = new RodzajedokPK(skrot);
        this.setRodzajedokPK(rodzajedokPK);
    }

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
