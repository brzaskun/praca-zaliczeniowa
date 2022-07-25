/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "ZALACZNIK", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_KOMUNIKAT", "LP"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zalacznik.findAll", query = "SELECT z FROM Zalacznik z"),
    @NamedQuery(name = "Zalacznik.findByIdZalacznik", query = "SELECT z FROM Zalacznik z WHERE z.idZalacznik = :idZalacznik"),
    @NamedQuery(name = "Zalacznik.findByIdKomunikat", query = "SELECT z FROM Zalacznik z WHERE z.idKomunikat = :idKomunikat"),
    @NamedQuery(name = "Zalacznik.findByLp", query = "SELECT z FROM Zalacznik z WHERE z.lp = :lp"),
    @NamedQuery(name = "Zalacznik.findByOpis", query = "SELECT z FROM Zalacznik z WHERE z.opis = :opis"),
    @NamedQuery(name = "Zalacznik.findByTyp", query = "SELECT z FROM Zalacznik z WHERE z.typ = :typ"),
    @NamedQuery(name = "Zalacznik.findByInserttmp", query = "SELECT z FROM Zalacznik z WHERE z.inserttmp = :inserttmp")})
public class Zalacznik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ZALACZNIK", nullable = false)
    private Integer idZalacznik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_KOMUNIKAT", nullable = false)
    private int idKomunikat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LP", nullable = false)
    private short lp;
    @Size(max = 255)
    @Column(name = "OPIS", length = 255)
    private String opis;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "LOKALIZACJA", length = 2147483647)
    private String lokalizacja;
    @Size(max = 50)
    @Column(name = "TYP", length = 50)
    private String typ;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Zalacznik() {
    }

    public Zalacznik(Integer idZalacznik) {
        this.idZalacznik = idZalacznik;
    }

    public Zalacznik(Integer idZalacznik, int idKomunikat, short lp) {
        this.idZalacznik = idZalacznik;
        this.idKomunikat = idKomunikat;
        this.lp = lp;
    }

    public Integer getIdZalacznik() {
        return idZalacznik;
    }

    public void setIdZalacznik(Integer idZalacznik) {
        this.idZalacznik = idZalacznik;
    }

    public int getIdKomunikat() {
        return idKomunikat;
    }

    public void setIdKomunikat(int idKomunikat) {
        this.idKomunikat = idKomunikat;
    }

    public short getLp() {
        return lp;
    }

    public void setLp(short lp) {
        this.lp = lp;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja = lokalizacja;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZalacznik != null ? idZalacznik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zalacznik)) {
            return false;
        }
        Zalacznik other = (Zalacznik) object;
        if ((this.idZalacznik == null && other.idZalacznik != null) || (this.idZalacznik != null && !this.idZalacznik.equals(other.idZalacznik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zalacznik[ idZalacznik=" + idZalacznik + " ]";
    }
    
}
