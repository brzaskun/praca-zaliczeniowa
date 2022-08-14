/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "uz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uz.findAll", query = "SELECT u FROM Uz u"),
    @NamedQuery(name = "Uz.findByLogin", query = "SELECT u FROM Uz u WHERE u.login = :login"),
    @NamedQuery(name = "Uz.findByAdrmail", query = "SELECT u FROM Uz u WHERE u.email = :email"),
    @NamedQuery(name = "Uz.findByHaslo", query = "SELECT u FROM Uz u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uz.findByImie", query = "SELECT u FROM Uz u WHERE u.imie = :imie"),
    @NamedQuery(name = "Uz.findByNazw", query = "SELECT u FROM Uz u WHERE u.nazw = :nazw"),
    @NamedQuery(name = "Uz.findByUprawnienia", query = "SELECT u FROM Uz u WHERE u.uprawnienia = :uprawnienia"),
    @NamedQuery(name = "Uz.findByUprawnieniaAktywny", query = "SELECT u FROM Uz u WHERE u.uprawnienia = :uprawnienia AND u.aktywny = TRUE")
})
public class Uz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "login")
    private String login;
    @Size(max = 255)
    @Column(name = "haslo")
    private String haslo;
    @Size(max = 255)
    @Column(name = "imie")
    private String imie;
    @Size(max = 255)
    @Column(name = "nazw")
    private String nazw;
    @Size(max = 255)
    @Column(name = "grupa")
    private String grupa;
    @Size(max = 255)
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "uprawnienia")
    private String uprawnienia;
    @Size(max = 10)
    @Column(name = "firma")
    @Pattern(regexp="\\d+",message="Numer NIP firmy powinien składać się wyłącznie z cyfr. Bez '-'")
    private String firma;
    @Size(max = 2)
    @Column(name = "iloscwierszy")
    private String iloscwierszy;
    @Size(max = 255)
    @Column(name = "biezacasesja")
    private String biezacasesja;
    @Size(max = 100)
    @Column(name = "theme")
    private String theme;
    @Size(max = 4)
    @Column(name = "locale")
    private String locale;
    @JoinColumn(name = "loginglowny", referencedColumnName = "login")
    @ManyToOne
    private Uz loginglowny;
    @Size(max = 45)
    @Column(name = "nrtelefonu")
    private String nrtelefonu;
    @Column(name = "sumafaktur")
    private double sumafaktur;
    @Column(name = "liczbapodatnikow")
    private int liczbapodatnikow;
    @Column(name = "wynagrodzenieobecne")
    private double wynagrodzenieobecne;
    @Column(name = "procent")
    private double procent;
    @Column(name = "wynagrodzenieprocentowe")
    private double wynagrodzenieprocentowe;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "rokWpisu")
    private Integer rokWpisu;
    @Size(max = 2)
    @Column(name = "miesiacWpisu")
    private String miesiacWpisu;
    @Size(max = 2)
    @Column(name = "miesiacOd")
    private String miesiacOd;
    @Size(max = 2)
    @Column(name = "miesiacDo")
    private String miesiacDo;
    @Column(name = "aktywny")
    private boolean aktywny;
    
    public Uz() {
    }

    public Uz(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

  
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazw() {
        return nazw;
    }

    public void setNazw(String nazw) {
        this.nazw = nazw;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getIloscwierszy() {
        return iloscwierszy;
    }

    public void setIloscwierszy(String iloscwierszy) {
        this.iloscwierszy = iloscwierszy;
    }

    public String getBiezacasesja() {
        return biezacasesja;
    }

    public void setBiezacasesja(String biezacasesja) {
        this.biezacasesja = biezacasesja;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getImieNazwisko() {
        return this.getImie()+" "+this.getNazw();
    }

    public String getNrtelefonu() {
        return nrtelefonu;
    }

    public void setNrtelefonu(String nrtelefonu) {
        this.nrtelefonu = nrtelefonu;
    }

    public Uz getLoginglowny() {
        return loginglowny;
    }

    public void setLoginglowny(Uz loginglowny) {
        this.loginglowny = loginglowny;
    }

    public double getSumafaktur() {
        return sumafaktur;
    }

    public void setSumafaktur(double sumafaktur) {
        this.sumafaktur = sumafaktur;
    }

    public double getWynagrodzenieobecne() {
        return wynagrodzenieobecne;
    }

    public void setWynagrodzenieobecne(double wynagrodzenieobecne) {
        this.wynagrodzenieobecne = wynagrodzenieobecne;
    }

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    public double getWynagrodzenieprocentowe() {
        return wynagrodzenieprocentowe;
    }

    public void setWynagrodzenieprocentowe(double wynagrodzenieprocentowe) {
        this.wynagrodzenieprocentowe = wynagrodzenieprocentowe;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        this.rokWpisu = rokWpisu;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        this.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        this.miesiacDo = miesiacDo;
    }

    public int getLiczbapodatnikow() {
        return liczbapodatnikow;
    }

    public void setLiczbapodatnikow(int liczbapodatnikow) {
        this.liczbapodatnikow = liczbapodatnikow;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uz)) {
            return false;
        }
        Uz other = (Uz) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uz{" + "login=" + login + ", imie=" + imie + ", nazw=" + nazw + ", email=" + email + ", uprawnienia=" + uprawnienia + ", firma=" + firma + '}';
    }

    public String toStringLIN() {
        return "login " + login + ", " + imie + " " + nazw + ", uprawnienia " + uprawnienia;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    
   
}
