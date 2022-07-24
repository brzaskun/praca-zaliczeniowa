/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podmiot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podmiot.findAll", query = "SELECT p FROM Podmiot p"),
    @NamedQuery(name = "Podmiot.findById", query = "SELECT p FROM Podmiot p WHERE p.id = :id"),
    @NamedQuery(name = "Podmiot.findByNip", query = "SELECT p FROM Podmiot p WHERE p.nip = :nip"),
    @NamedQuery(name = "Podmiot.findByPesel", query = "SELECT p FROM Podmiot p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "Podmiot.findByKrs", query = "SELECT p FROM Podmiot p WHERE p.krs = :krs"),
    @NamedQuery(name = "Podmiot.findByRegon", query = "SELECT p FROM Podmiot p WHERE p.regon = :regon"),
    @NamedQuery(name = "Podmiot.findByNazwa", query = "SELECT p FROM Podmiot p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Podmiot.findByNazwisko", query = "SELECT p FROM Podmiot p WHERE p.nazwisko = :nazwisko"),
    @NamedQuery(name = "Podmiot.findByImie", query = "SELECT p FROM Podmiot p WHERE p.imie = :imie"),
    @NamedQuery(name = "Podmiot.findByPrintnazwa", query = "SELECT p FROM Podmiot p WHERE p.printnazwa = :printnazwa"),
    @NamedQuery(name = "Podmiot.findByOsobafizyczna", query = "SELECT p FROM Podmiot p WHERE p.osobafizyczna = :osobafizyczna"),
    @NamedQuery(name = "Podmiot.findByAktywny", query = "SELECT p FROM Podmiot p WHERE p.aktywny = :aktywny")})
public class Podmiot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nip", unique = true, length = 45)
    private String nip;
    @Size(max = 45)
    @Column(name = "pesel", length = 45)
    private String pesel;
    @Size(max = 45)
    @Column(name = "krs", length = 45)
    private String krs;
    @Size(max = 45)
    @Column(name = "regon", length = 45)
    private String regon;
    @Size(max = 45)
    @Column(name = "nazwa", length = 45)
    private String nazwa;
    @Size(max = 45)
    @Column(name = "nazwisko", length = 45)
    private String nazwisko;
    @Size(max = 45)
    @Column(name = "imie", length = 45)
    private String imie;
    @Size(max = 45)
    @Column(name = "printnazwa", length = 45)
    private String printnazwa;
    @Column(name = "osobafizyczna")
    private boolean osobafizyczna;
    @Column(name = "aktywny")
    private boolean aktywny;
    @Column(name = "login")
    private String login;
    @Column(name = "pin")
    private String pin;
    @Column(name = "email")
    private String email;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "krajrezydencji")
    private String krajrezydencji;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "podmiot")
    private List<PodatnikUdzialy> podatnikudzialy;

    public Podmiot() {
    }

    public Podmiot(Integer id) {
        this.id = id;
    }

    public Podmiot(Integer id, String nip) {
        this.id = id;
        this.nip = nip;
    }

    public Podmiot(Podatnik p) {
        this.nip = p.getNip();
        if (p.getPesel()!=null&&!p.getPesel().equals("00000000000")) {
            this.pesel = p.getPesel();
        }
        this.krs = null;
        this.regon = p.getRegon();
        this.nazwa = p.getNazwaRejestr();
        this.nazwisko = p.getNazwisko();
        this.imie = p.getImie();
        this.printnazwa = p.getPrintnazwa();
        this.osobafizyczna = false;
        this.aktywny = p.isPodmiotaktywny();
    }

    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getKrs() {
        return krs;
    }

    public void setKrs(String krs) {
        this.krs = krs;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getPrintnazwa() {
        return printnazwa;
    }

    public void setPrintnazwa(String printnazwa) {
        this.printnazwa = printnazwa;
    }

    public boolean isOsobafizyczna() {
        return osobafizyczna;
    }

    public void setOsobafizyczna(boolean osobafizyczna) {
        this.osobafizyczna = osobafizyczna;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKrajrezydencji() {
        return krajrezydencji;
    }

    public void setKrajrezydencji(String krajrezydencji) {
        this.krajrezydencji = krajrezydencji;
    }

   
    public List<PodatnikUdzialy> getPodatnikudzialy() {
        return podatnikudzialy;
    }

    public void setPodatnikudzialy(List<PodatnikUdzialy> podatnikudzialy) {
        this.podatnikudzialy = podatnikudzialy;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nip);
        hash = 29 * hash + Objects.hashCode(this.printnazwa);
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
        final Podmiot other = (Podmiot) obj;
        if (!Objects.equals(this.nip, other.nip)) {
            return false;
        }
        if (!Objects.equals(this.printnazwa, other.printnazwa)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Podmiot{" + "printnazwa=" + printnazwa + ", osobafizyczna=" + osobafizyczna + ", aktywny=" + aktywny + '}';
    }
    
    

  
    
}
