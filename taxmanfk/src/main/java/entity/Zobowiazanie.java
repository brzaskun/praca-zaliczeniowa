/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zobowiazanie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zobowiazanie.findAll", query = "SELECT z FROM Zobowiazanie z"),
    @NamedQuery(name = "Zobowiazanie.findByMc", query = "SELECT z FROM Zobowiazanie z WHERE z.zobowiazaniePK.mc = :mc"),
    @NamedQuery(name = "Zobowiazanie.findByPitday", query = "SELECT z FROM Zobowiazanie z WHERE z.pitday = :pitday"),
    @NamedQuery(name = "Zobowiazanie.findByRok", query = "SELECT z FROM Zobowiazanie z WHERE z.zobowiazaniePK.rok = :rok"),
    @NamedQuery(name = "Zobowiazanie.findByVatday", query = "SELECT z FROM Zobowiazanie z WHERE z.vatday = :vatday"),
    @NamedQuery(name = "Zobowiazanie.findByZusday1", query = "SELECT z FROM Zobowiazanie z WHERE z.zusday1 = :zusday1"),
    @NamedQuery(name = "Zobowiazanie.findByZusaday2", query = "SELECT z FROM Zobowiazanie z WHERE z.zusaday2 = :zusaday2")})
public class Zobowiazanie implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZobowiazaniePK zobowiazaniePK;
    @Size(max = 255)
    @Column(name = "pitday")
    private String pitday;
    @Size(max = 255)
    @Column(name = "vatday")
    private String vatday;
    @Size(max = 255)
    @Column(name = "zusday1")
    private String zusday1;
    @Size(max = 255)
    @Column(name = "zusaday2")
    private String zusaday2;

    public Zobowiazanie() {
        zobowiazaniePK = new ZobowiazaniePK();
    }

    public Zobowiazanie(ZobowiazaniePK zobowiazaniePK) {
        this.zobowiazaniePK = zobowiazaniePK;
    }

    public Zobowiazanie(String mc, String rok) {
        this.zobowiazaniePK = new ZobowiazaniePK(mc, rok);
    }

    public ZobowiazaniePK getZobowiazaniePK() {
        return zobowiazaniePK;
    }

    public void setZobowiazaniePK(ZobowiazaniePK zobowiazaniePK) {
        this.zobowiazaniePK = zobowiazaniePK;
    }

    public String getPitday() {
        return pitday;
    }

    public void setPitday(String pitday) {
        this.pitday = pitday;
    }

    public String getVatday() {
        return vatday;
    }

    public void setVatday(String vatday) {
        this.vatday = vatday;
    }

    public String getZusday1() {
        return zusday1;
    }

    public void setZusday1(String zusday1) {
        this.zusday1 = zusday1;
    }

    public String getZusaday2() {
        return zusaday2;
    }

    public void setZusaday2(String zusaday2) {
        this.zusaday2 = zusaday2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zobowiazaniePK != null ? zobowiazaniePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zobowiazanie)) {
            return false;
        }
        Zobowiazanie other = (Zobowiazanie) object;
        if ((this.zobowiazaniePK == null && other.zobowiazaniePK != null) || (this.zobowiazaniePK != null && !this.zobowiazaniePK.equals(other.zobowiazaniePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zobowiazanie[ zobowiazaniePK=" + zobowiazaniePK + " ]";
    }
    
}
