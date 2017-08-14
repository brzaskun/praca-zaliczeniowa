/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik"})
})
@NamedQueries({
    @NamedQuery(name = "FakturaStopkaNiemiecka.findAll", query = "SELECT f FROM FakturaStopkaNiemiecka f"),
    @NamedQuery(name = "FakturaStopkaNiemiecka.findByPodatnik", query = "SELECT f FROM FakturaStopkaNiemiecka f WHERE f.podatnik = :podatnik")
})
public class FakturaStopkaNiemiecka  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Column(name = "nazwafirmy")
    private String nazwafirmy;
    @Column(name = "prezes")
    private String prezes;
    @Column(name = "miejscowosc")
    private String miejscowosc;
    @Column(name = "ulica")
    private String ulica;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "komorka")
    private String komorka;
    @Column(name = "email")
    private String email;
    @Column(name = "sad")
    private String sad;
    @Column(name = "krs")
    private String krs;
    @Column(name = "urzadskarbowy")
    private String urzadskarbowy;
    @Column(name = "nip")
    private String nip;
    @Column(name = "bank")
    private String bank;
    @Column(name = "iban")
    private String iban;
    @Column(name = "bic")
    private String bic;
    @Column(name = "blz")
    private String blz;
    @Column(name = "ktonr")
    private String ktonr;


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.podatnik);
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
        final FakturaStopkaNiemiecka other = (FakturaStopkaNiemiecka) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaStopkaNiemiecka{" + "podatnik=" + podatnik.getNazwapelna() + ", nazwafirmy=" + nazwafirmy + ", prezes=" + prezes + ", adres=" + ulica +" " + miejscowosc + ", telefon=" + telefon + ", komorka=" + komorka + ", email=" + email + ", krs=" + krs + ", urzadskarbowy=" + urzadskarbowy + ", nip=" + nip + ", bank=" + bank + ", iban=" + iban + ", bic=" + bic + ", blz=" + blz + ", ktonr=" + ktonr + '}';
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

    public String getNazwafirmy() {
        return nazwafirmy;
    }

    public void setNazwafirmy(String nazwafirmy) {
        this.nazwafirmy = nazwafirmy;
    }

    public String getPrezes() {
        return prezes;
    }

    public void setPrezes(String prezes) {
        this.prezes = prezes;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

 

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKomorka() {
        return komorka;
    }

    public void setKomorka(String komorka) {
        this.komorka = komorka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKrs() {
        return krs;
    }

    public void setKrs(String krs) {
        this.krs = krs;
    }

    public String getUrzadskarbowy() {
        return urzadskarbowy;
    }

    public void setUrzadskarbowy(String urzadskarbowy) {
        this.urzadskarbowy = urzadskarbowy;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBlz() {
        return blz;
    }

    public void setBlz(String blz) {
        this.blz = blz;
    }

    public String getKtonr() {
        return ktonr;
    }

    public void setKtonr(String ktonr) {
        this.ktonr = ktonr;
    }
    
    
    
}
