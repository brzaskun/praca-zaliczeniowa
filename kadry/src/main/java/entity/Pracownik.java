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
    @NamedQuery(name = "Pracownik.findByImie", query = "SELECT p FROM Pracownik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Pracownik.findByNazwisko", query = "SELECT p FROM Pracownik p WHERE p.nazwisko = :nazwisko")
})
public class Pracownik implements Serializable {

    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "imie")
    private String imie;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "nazwisko")
    private String nazwisko;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 45)
    @Column(name = "pesel")
    private String pesel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;
   
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

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
    
    public void setNazwisko(String nazwisko){
        this.nazwisko = nazwisko;
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

   
}
