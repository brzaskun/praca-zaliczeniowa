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
import javax.persistence.Lob;
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
@Table(name = "ostatnidokument")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ostatnidokument.findAll", query = "SELECT o FROM Ostatnidokument o"),
    @NamedQuery(name = "Ostatnidokument.findByUzytkownik", query = "SELECT o FROM Ostatnidokument o WHERE o.uzytkownik = :uzytkownik")})
public class Ostatnidokument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "uzytkownik")
    private String uzytkownik;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "dokument")
    private Dok dokument;

    public Ostatnidokument() {
    }

    public Ostatnidokument(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Ostatnidokument(String uzytkownik, Dok dokument) {
        this.uzytkownik = uzytkownik;
        this.dokument = dokument;
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Dok getDokument() {
        return dokument;
    }

    public void setDokument(Dok dokument) {
        this.dokument = dokument;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uzytkownik != null ? uzytkownik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ostatnidokument)) {
            return false;
        }
        Ostatnidokument other = (Ostatnidokument) object;
        if ((this.uzytkownik == null && other.uzytkownik != null) || (this.uzytkownik != null && !this.uzytkownik.equals(other.uzytkownik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ostatnidokument[ uzytkownik=" + uzytkownik + " ]";
    }
    
}
