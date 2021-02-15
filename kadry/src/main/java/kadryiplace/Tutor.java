/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tutor", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tutor.findAll", query = "SELECT t FROM Tutor t"),
    @NamedQuery(name = "Tutor.findByTutObject", query = "SELECT t FROM Tutor t WHERE t.tutObject = :tutObject"),
    @NamedQuery(name = "Tutor.findByTutText", query = "SELECT t FROM Tutor t WHERE t.tutText = :tutText")})
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "tut_object", nullable = false, length = 64)
    private String tutObject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000)
    @Column(name = "tut_text", nullable = false, length = 5000)
    private String tutText;

    public Tutor() {
    }

    public Tutor(String tutObject) {
        this.tutObject = tutObject;
    }

    public Tutor(String tutObject, String tutText) {
        this.tutObject = tutObject;
        this.tutText = tutText;
    }

    public String getTutObject() {
        return tutObject;
    }

    public void setTutObject(String tutObject) {
        this.tutObject = tutObject;
    }

    public String getTutText() {
        return tutText;
    }

    public void setTutText(String tutText) {
        this.tutText = tutText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tutObject != null ? tutObject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutor)) {
            return false;
        }
        Tutor other = (Tutor) object;
        if ((this.tutObject == null && other.tutObject != null) || (this.tutObject != null && !this.tutObject.equals(other.tutObject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Tutor[ tutObject=" + tutObject + " ]";
    }
    
}
