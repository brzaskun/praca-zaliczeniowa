/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
//docelowo trzeba to zrobic
@Table(name = "pracownik", uniqueConstraints = {
    @UniqueConstraint(columnNames={"pesel"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pracownik.findAll", query = "SELECT p FROM Pracownik p"),
    @NamedQuery(name = "Pracownik.findById", query = "SELECT p FROM Pracownik p WHERE p.id = :id"),
    @NamedQuery(name = "Pracownik.findByPesel", query = "SELECT p FROM Pracownik p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "Pracownik.findByImie", query = "SELECT p FROM Pracownik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Pracownik.findByNazwisko", query = "SELECT p FROM Pracownik p WHERE p.nazwisko = :nazwisko")
})
public class Pracownik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
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
    @Size(max = 128)
    @Column(name = "nazwiskorodowe")
    private String nazwiskorodowe;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "dataurodzenia")
    private String dataurodzenia;
    @Size(max = 128)
    @Column(name = "miejsceurodzenia")
    private String miejsceurodzenia;
    @Size(max = 11)
    @Column(name = "pesel", unique = true)
    private String pesel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
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
    @Size(max = 7)
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
    @Size(max = 45)
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
    @Size(max = 1)
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
    @Size(max = 45)
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "ipusera")
    private String ipusera;
    @Column(name = "datalogowania")
    private String datalogowania;
    @Column(name = "modyfikowal")
    private String modyfikowal;
    @Column(name = "ojciec")
    private String ojciec;
    @Column(name = "matka")
    private String matka;
    @Column(name = "formawynagrodzenia")
    private int formawynagrodzenia;
    @Column(name = "nierezydent")
    private boolean nierezydent;
    @Column(name = "zaliczkiwinnymkraju")
    private boolean zaliczkiwinnymkraju;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Angaz> angazList;
    @OneToMany(mappedBy = "pracownik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeklaracjaPIT11Schowek> deklaracjaList;
    @Column(name = "ulgadlaklasysredniej")
    private boolean ulgadlaklasysredniej;
    @Column(name = "kodurzeduskarbowego")
    private String kodurzeduskarbowego;
    @Column(name = "nazwaurzeduskarbowego")
    private String nazwaurzeduskarbowego;
    @Column(name = "aktywny")
    private boolean aktywny;

    public Pracownik() {
       this.aktywny = true;
    }

    public Pracownik(int id) {
        this.id = id;
        this.aktywny = true;
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

    public boolean isNierezydent() {
        return nierezydent;
    }

    public void setNierezydent(boolean nierezydent) {
        this.nierezydent = nierezydent;
    }

    public boolean isUlgadlaklasysredniej() {
        return ulgadlaklasysredniej;
    }

    public void setUlgadlaklasysredniej(boolean ulgadlaklasysredniej) {
        this.ulgadlaklasysredniej = ulgadlaklasysredniej;
    }


    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
    }

    public String getKodurzeduskarbowego() {
        return kodurzeduskarbowego;
    }

    public void setKodurzeduskarbowego(String kodurzeduskarbowego) {
        this.kodurzeduskarbowego = kodurzeduskarbowego;
    }

    public String getNazwaurzeduskarbowego() {
        return nazwaurzeduskarbowego;
    }

    public void setNazwaurzeduskarbowego(String nazwaurzeduskarbowego) {
        this.nazwaurzeduskarbowego = nazwaurzeduskarbowego;
    }
    
    

    @XmlTransient
    public List<DeklaracjaPIT11Schowek> getDeklaracjaList() {
        return deklaracjaList;
    }

    public void setDeklaracjaList(List<DeklaracjaPIT11Schowek> deklaracjaList) {
        this.deklaracjaList = deklaracjaList;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
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
        return "Pracownik{" + "nazwisko=" + nazwisko + ", imie=" + imie + ", dataurodzenia=" + dataurodzenia + ", pesel=" + pesel + ", email=" + email + ", miasto=" + miasto + ", kraj=" + kraj + ", datazatrudnienia=" + datazatrudnienia + ", plec=" + plec + ", obywatelstwo=" + obywatelstwo + ", angazList=" + angazList + '}';
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
    
    public List<Umowa> getUmowy(){
        List<Umowa> zwrot = new ArrayList<>();
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            for (Angaz p : angazList) {
                if (p.getUmowaList()!=null && !p.getUmowaList().isEmpty()) {
                    zwrot.addAll(p.getUmowaList());
                }
            }
        }
        return zwrot;
    }
    
    public List<Nieobecnosc> getNieobecnosci(){
        List<Nieobecnosc> zwrot = new ArrayList<>();
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            for (Angaz p : angazList) {
                if (p.getUmowaList()!=null && !p.getUmowaList().isEmpty()) {
                    for (Umowa u : p.getUmowaList()) {
                        if (u.getNieobecnoscList()!=null&&!u.getNieobecnoscList().isEmpty()) {
                            zwrot.addAll(u.getNieobecnoscList());
                        }
                    }
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
    public String getIpusera() {
        return ipusera;
    }
    public void setIpusera(String ipusera) {
        this.ipusera = ipusera;
    }
    public String getDatalogowania() {
        return datalogowania;
    }
    public void setDatalogowania(String datalogowania) {
        this.datalogowania = datalogowania;
    }
    public String getModyfikowal() {
        return modyfikowal;
    }
    public void setModyfikowal(String modyfikowal) {
        this.modyfikowal = modyfikowal;
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

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
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
    
    public void setDatazatrudnienia(Date datazatrudnienia) {
        this.datazatrudnienia = Data.data_yyyyMMdd(datazatrudnienia);
    }

    public String getDatazwolnienia() {
        return datazwolnienia;
    }

    public void setDatazwolnienia(String datazwolnienia) {
        this.datazwolnienia = datazwolnienia;
    }
     
    public void setDatazwolnienia(Date datazwolnienia) {
        this.datazwolnienia = Data.data_yyyyMMdd(datazwolnienia);
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


    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getFormawynagrodzenia() {
        return formawynagrodzenia;
    }

    public void setFormawynagrodzenia(int formawynagrodzenia) {
        this.formawynagrodzenia = formawynagrodzenia;
    }

    public boolean isZaliczkiwinnymkraju() {
        return zaliczkiwinnymkraju;
    }

    public void setZaliczkiwinnymkraju(boolean zaliczkiwinnymkraju) {
        this.zaliczkiwinnymkraju = zaliczkiwinnymkraju;
    }

     public String getAdres() {
        String zwrot = this.kod+" "+this.miasto+", "+this.ulica+" "+this.dom+"/"+this.lokal;
        if (this.lokal==null) {
            zwrot = this.kod+" "+this.miasto+", "+this.ulica+" "+this.dom;
        }
        return zwrot;
    }
    
    
   
}
