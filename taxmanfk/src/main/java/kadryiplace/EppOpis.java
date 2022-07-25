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
@Table(name = "epp_opis", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EppOpis.findAll", query = "SELECT e FROM EppOpis e"),
    @NamedQuery(name = "EppOpis.findByEpoSerial", query = "SELECT e FROM EppOpis e WHERE e.epoSerial = :epoSerial"),
    @NamedQuery(name = "EppOpis.findByEpoOpis", query = "SELECT e FROM EppOpis e WHERE e.epoOpis = :epoOpis"),
    @NamedQuery(name = "EppOpis.findByEpoTyp", query = "SELECT e FROM EppOpis e WHERE e.epoTyp = :epoTyp"),
    @NamedQuery(name = "EppOpis.findByEpoKolejnosc", query = "SELECT e FROM EppOpis e WHERE e.epoKolejnosc = :epoKolejnosc"),
    @NamedQuery(name = "EppOpis.findByEpoChar1", query = "SELECT e FROM EppOpis e WHERE e.epoChar1 = :epoChar1"),
    @NamedQuery(name = "EppOpis.findByEpoChar2", query = "SELECT e FROM EppOpis e WHERE e.epoChar2 = :epoChar2"),
    @NamedQuery(name = "EppOpis.findByEpoChar3", query = "SELECT e FROM EppOpis e WHERE e.epoChar3 = :epoChar3"),
    @NamedQuery(name = "EppOpis.findByEpoChar4", query = "SELECT e FROM EppOpis e WHERE e.epoChar4 = :epoChar4"),
    @NamedQuery(name = "EppOpis.findByEpoVchar1", query = "SELECT e FROM EppOpis e WHERE e.epoVchar1 = :epoVchar1")})
public class EppOpis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "epo_serial", nullable = false)
    private Integer epoSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "epo_opis", nullable = false, length = 64)
    private String epoOpis;
    @Column(name = "epo_typ")
    private Character epoTyp;
    @Column(name = "epo_kolejnosc")
    private Integer epoKolejnosc;
    @Column(name = "epo_char_1")
    private Character epoChar1;
    @Column(name = "epo_char_2")
    private Character epoChar2;
    @Column(name = "epo_char_3")
    private Character epoChar3;
    @Column(name = "epo_char_4")
    private Character epoChar4;
    @Size(max = 64)
    @Column(name = "epo_vchar_1", length = 64)
    private String epoVchar1;

    public EppOpis() {
    }

    public EppOpis(Integer epoSerial) {
        this.epoSerial = epoSerial;
    }

    public EppOpis(Integer epoSerial, String epoOpis) {
        this.epoSerial = epoSerial;
        this.epoOpis = epoOpis;
    }

    public Integer getEpoSerial() {
        return epoSerial;
    }

    public void setEpoSerial(Integer epoSerial) {
        this.epoSerial = epoSerial;
    }

    public String getEpoOpis() {
        return epoOpis;
    }

    public void setEpoOpis(String epoOpis) {
        this.epoOpis = epoOpis;
    }

    public Character getEpoTyp() {
        return epoTyp;
    }

    public void setEpoTyp(Character epoTyp) {
        this.epoTyp = epoTyp;
    }

    public Integer getEpoKolejnosc() {
        return epoKolejnosc;
    }

    public void setEpoKolejnosc(Integer epoKolejnosc) {
        this.epoKolejnosc = epoKolejnosc;
    }

    public Character getEpoChar1() {
        return epoChar1;
    }

    public void setEpoChar1(Character epoChar1) {
        this.epoChar1 = epoChar1;
    }

    public Character getEpoChar2() {
        return epoChar2;
    }

    public void setEpoChar2(Character epoChar2) {
        this.epoChar2 = epoChar2;
    }

    public Character getEpoChar3() {
        return epoChar3;
    }

    public void setEpoChar3(Character epoChar3) {
        this.epoChar3 = epoChar3;
    }

    public Character getEpoChar4() {
        return epoChar4;
    }

    public void setEpoChar4(Character epoChar4) {
        this.epoChar4 = epoChar4;
    }

    public String getEpoVchar1() {
        return epoVchar1;
    }

    public void setEpoVchar1(String epoVchar1) {
        this.epoVchar1 = epoVchar1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (epoSerial != null ? epoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EppOpis)) {
            return false;
        }
        EppOpis other = (EppOpis) object;
        if ((this.epoSerial == null && other.epoSerial != null) || (this.epoSerial != null && !this.epoSerial.equals(other.epoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.EppOpis[ epoSerial=" + epoSerial + " ]";
    }
    
}
