/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pracownik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pracownik.findAll", query = "SELECT p FROM Pracownik p"),
    @NamedQuery(name = "Pracownik.findById", query = "SELECT p FROM Pracownik p WHERE p.id = :id"),
    @NamedQuery(name = "Pracownik.findByPesel", query = "SELECT p FROM Pracownik p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "Pracownik.findByImie", query = "SELECT p FROM Pracownik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Pracownik.findByNazwisko", query = "SELECT p FROM Pracownik p WHERE p.nazwisko = :nazwisko")
})
public class Pracownik implements Serializable {

    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 128)
    @Column(name = "nazwisko")
    private String nazwisko;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 128)
    @Column(name = "imie")
    private String imie;
    @Size(max = 128)
    @Column(name = "drugieimie")
    private String drugieimie;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "nazwiskorodowe")
    private String nazwiskorodowe;
    @Size(max = 128)
    @Column(name = "ojciec")
    private String ojciec;
    @Size(max = 128)
    @Column(name = "matka")
    private String matka;
    @Size(max = 128)
    @Column(name = "dataurodzenia")
    private String dataurodzenia;
    @Size(max = 128)
    @Column(name = "miejsceurodzenia")
    private String miejsceurodzenia;
    @Size(max = 128)
    @Column(name = "pesel")
    private String pesel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;
     @Size(max = 128)
    @Column(name = "telefon")
    private String telefon;
    @Size(max = 128)
    @Column(name = "miasto")
    private String miasto;
    @Size(max = 128)
    @Column(name = "ulica")
    private String ulica;
    @Size(max = 128)
    @Column(name = "dom")
    private String dom;
    @Size(max = 128)
    @Column(name = "lokal")
    private String lokal;
    @Size(max = 128)
    @Column(name = "kod")
    private String kod;
    @Size(max = 128)
    @Column(name = "gmina")
    private String gmina;
    @Size(max = 128)
    @Column(name = "powiat")
    private String powiat;
    @Size(max = 128)
    @Column(name = "poczta")
    private String poczta;
     @Size(max = 128)
    @Column(name = "kraj")
    private String kraj;
    @Size(max = 128)
    @Column(name = "wojewodztwo")
    private String wojewodztwo;
    @Size(max = 128)
    @Column(name = "datazatrudnienia")
    private String datazatrudnienia;
    @Size(max = 128)
    @Column(name = "datazwolnienia")
    private String datazwolnienia;
    @Size(max = 128)
    @Column(name = "banknazwa")
    private String banknazwa;
    @Size(max = 128)
    @Column(name = "bankkonto")
    private String bankkonto;
    @Size(max = 128)
    @Column(name = "zusid")
    private String zusid;
    @Size(max = 128)
    @Column(name = "plec")
    private String plec;
    @Size(max = 128)
    @Column(name = "obywatelstwo")
    private String obywatelstwo;
    @Size(max = 128)
    @Column(name = "dowodosobisty")
    private String dowodosobisty;
    @Size(max = 128)
    @Column(name = "paszport")
    private String paszport;
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Angaz> angazList;

    public Pracownik() {
    }

    public Pracownik(int id) {
        this.id = id;
    }

    public Pracownik(String podsumowanie, String a) {
        this.nazwisko = "podsumowanie";
        this.imie = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pracownik)) {
            return false;
        }
        Pracownik other = (Pracownik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pracownik[ id=" + id + " ]";
    }
    public String getNazwiskoImie() {
        return nazwisko+" "+imie;
    }
    public String getCzyjestangaz(){
        String zwrot = "";
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            zwrot = "✔";
        }
        return zwrot;
    }
    
    public String getCzyjestumowa(){
        String zwrot = "";
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            for (Angaz p : angazList) {
                if (p.getUmowaList()!=null && !p.getUmowaList().isEmpty()) {
                    zwrot = "✔";
                }
            }
        }
        return zwrot;
    }

  
    public String getFirmypracownika(){
        String zwrot = "";
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            for (Angaz p : angazList) {
                if (p.getUmowaList()!=null && !p.getUmowaList().isEmpty()) {
                    zwrot = zwrot+p.getFirma().getNazwa()+"; ";
                }
            }
        }
        return zwrot;
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
    
    public void setImie(String imie){
        this.imie = imie;
    }

    public String getDrugieimie() {
        return drugieimie;
    }

    public void setDrugieimie(String drugieimie) {
        this.drugieimie = drugieimie;
    }

    public String getNazwiskorodowe() {
        return nazwiskorodowe;
    }

    public void setNazwiskorodowe(String nazwiskorodowe) {
        this.nazwiskorodowe = nazwiskorodowe;
    }

    public String getOjciec() {
        return ojciec;
    }

    public void setOjciec(String ojciec) {
        this.ojciec = ojciec;
    }

    public String getMatka() {
        return matka;
    }

    public void setMatka(String matka) {
        this.matka = matka;
    }

    public String getDataurodzenia() {
        return dataurodzenia;
    }

    public void setDataurodzenia(String dataurodzenia) {
        this.dataurodzenia = dataurodzenia;
    }

    public String getMiejsceurodzenia() {
        return miejsceurodzenia;
    }

    public void setMiejsceurodzenia(String miejsceurodzenia) {
        this.miejsceurodzenia = miejsceurodzenia;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getPowiat() {
        return powiat;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getDatazatrudnienia() {
        return datazatrudnienia;
    }

    public void setDatazatrudnienia(String datazatrudnienia) {
        this.datazatrudnienia = datazatrudnienia;
    }

    public String getDatazwolnienia() {
        return datazwolnienia;
    }

    public void setDatazwolnienia(String datazwolnienia) {
        this.datazwolnienia = datazwolnienia;
    }

    public String getBanknazwa() {
        return banknazwa;
    }

    public void setBanknazwa(String banknazwa) {
        this.banknazwa = banknazwa;
    }

    public String getBankkonto() {
        return bankkonto;
    }

    public void setBankkonto(String bankkonto) {
        this.bankkonto = bankkonto;
    }

    public String getZusid() {
        return zusid;
    }

    public void setZusid(String zusid) {
        this.zusid = zusid;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getObywatelstwo() {
        return obywatelstwo;
    }

    public void setObywatelstwo(String obywatelstwo) {
        this.obywatelstwo = obywatelstwo;
    }

    public String getDowodosobisty() {
        return dowodosobisty;
    }

    public void setDowodosobisty(String dowodosobisty) {
        this.dowodosobisty = dowodosobisty;
    }

    public String getPaszport() {
        return paszport;
    }

    public void setPaszport(String paszport) {
        this.paszport = paszport;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

   
}
