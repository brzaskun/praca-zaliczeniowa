/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatniknip", "nip"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kliencifk.findAll", query = "SELECT k FROM Kliencifk k"),
    @NamedQuery(name = "Kliencifk.findByLp", query = "SELECT k FROM Kliencifk k WHERE k.lp = :lp"),
    @NamedQuery(name = "Kliencifk.findByNip", query = "SELECT k FROM Kliencifk k WHERE k.nip = :nip"),
    @NamedQuery(name = "Kliencifk.findByNipPodatniknip", query = "SELECT k FROM Kliencifk k WHERE k.nip = :nip AND k.podatniknip = :podatniknip"),
    @NamedQuery(name = "Kliencifk.findByNazwa", query = "SELECT k FROM Kliencifk k WHERE k.nazwa = :nazwa"),
    @NamedQuery(name = "Kliencifk.findByPodatniknip", query = "SELECT k FROM Kliencifk k WHERE k.podatniknip = :podatniknip"),
    @NamedQuery(name = "Kliencifk.findByPodatniknazwa", query = "SELECT k FROM Kliencifk k WHERE k.podatniknazwa = :podatniknazwa"),
    @NamedQuery(name = "Kliencifk.findByNrkonta", query = "SELECT k FROM Kliencifk k WHERE k.nrkonta = :nrkonta"),
    @NamedQuery(name = "Kliencifk.findByAktywny", query = "SELECT k FROM Kliencifk k WHERE k.aktywny = :aktywny")})
@Cacheable
public class Kliencifk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp", nullable = false)
    private int lp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nip", nullable = false, length = 15)
    private String nip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwa", nullable = false, length = 255)
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatniknip", nullable = false, length = 255)
    private String podatniknip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatniknazwa", nullable = false, length = 255)
    private String podatniknazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta", nullable = false, length = 5)
    private String nrkonta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny", nullable = false)
    private boolean aktywny;

    public Kliencifk() {
    }

    public Kliencifk(Integer lp) {
        this.lp = lp;
    }

    public Kliencifk(Integer lp, String nip, String nazwa, String podatniknip, String podatniknazwa, String nrkonta, boolean aktywny) {
        this.lp = lp;
        this.nip = nip;
        this.nazwa = nazwa;
        this.podatniknip = podatniknip;
        this.podatniknazwa = podatniknazwa;
        this.nrkonta = nrkonta;
        this.aktywny = aktywny;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }


    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPodatniknip() {
        return podatniknip;
    }

    public void setPodatniknip(String podatniknip) {
        this.podatniknip = podatniknip;
    }

    public String getPodatniknazwa() {
        return podatniknazwa;
    }

    public void setPodatniknazwa(String podatniknazwa) {
        this.podatniknazwa = podatniknazwa;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.lp;
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
        final Kliencifk other = (Kliencifk) obj;
        if (this.lp != other.lp) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Kliencifk{" + "nip=" + nip + ", nazwa=" + nazwa + ", podatniknip=" + podatniknip + ", podatniknazwa=" + podatniknazwa + ", nrkonta=" + nrkonta + '}';
    }

   
    
}
