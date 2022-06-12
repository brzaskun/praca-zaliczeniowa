/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "wzory_dok", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WzoryDok.findAll", query = "SELECT w FROM WzoryDok w"),
    @NamedQuery(name = "WzoryDok.findByWzoSerial", query = "SELECT w FROM WzoryDok w WHERE w.wzoSerial = :wzoSerial"),
    @NamedQuery(name = "WzoryDok.findByWzoDokument", query = "SELECT w FROM WzoryDok w WHERE w.wzoDokument = :wzoDokument"),
    @NamedQuery(name = "WzoryDok.findByWzoNrPola", query = "SELECT w FROM WzoryDok w WHERE w.wzoNrPola = :wzoNrPola"),
    @NamedQuery(name = "WzoryDok.findByWzoZawartosc", query = "SELECT w FROM WzoryDok w WHERE w.wzoZawartosc = :wzoZawartosc"),
    @NamedQuery(name = "WzoryDok.findByWzoChar1", query = "SELECT w FROM WzoryDok w WHERE w.wzoChar1 = :wzoChar1"),
    @NamedQuery(name = "WzoryDok.findByWzoChar2", query = "SELECT w FROM WzoryDok w WHERE w.wzoChar2 = :wzoChar2"),
    @NamedQuery(name = "WzoryDok.findByWzoNum1", query = "SELECT w FROM WzoryDok w WHERE w.wzoNum1 = :wzoNum1")})
public class WzoryDok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wzo_serial", nullable = false)
    private Integer wzoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wzo_dokument", nullable = false)
    private short wzoDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wzo_nr_pola", nullable = false)
    private short wzoNrPola;
    @Size(max = 254)
    @Column(name = "wzo_zawartosc", length = 254)
    private String wzoZawartosc;
    @Column(name = "wzo_char_1")
    private Character wzoChar1;
    @Column(name = "wzo_char_2")
    private Character wzoChar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wzo_num_1", precision = 17, scale = 6)
    private BigDecimal wzoNum1;

    public WzoryDok() {
    }

    public WzoryDok(Integer wzoSerial) {
        this.wzoSerial = wzoSerial;
    }

    public WzoryDok(Integer wzoSerial, short wzoDokument, short wzoNrPola) {
        this.wzoSerial = wzoSerial;
        this.wzoDokument = wzoDokument;
        this.wzoNrPola = wzoNrPola;
    }

    public Integer getWzoSerial() {
        return wzoSerial;
    }

    public void setWzoSerial(Integer wzoSerial) {
        this.wzoSerial = wzoSerial;
    }

    public short getWzoDokument() {
        return wzoDokument;
    }

    public void setWzoDokument(short wzoDokument) {
        this.wzoDokument = wzoDokument;
    }

    public short getWzoNrPola() {
        return wzoNrPola;
    }

    public void setWzoNrPola(short wzoNrPola) {
        this.wzoNrPola = wzoNrPola;
    }

    public String getWzoZawartosc() {
        return wzoZawartosc;
    }

    public void setWzoZawartosc(String wzoZawartosc) {
        this.wzoZawartosc = wzoZawartosc;
    }

    public Character getWzoChar1() {
        return wzoChar1;
    }

    public void setWzoChar1(Character wzoChar1) {
        this.wzoChar1 = wzoChar1;
    }

    public Character getWzoChar2() {
        return wzoChar2;
    }

    public void setWzoChar2(Character wzoChar2) {
        this.wzoChar2 = wzoChar2;
    }

    public BigDecimal getWzoNum1() {
        return wzoNum1;
    }

    public void setWzoNum1(BigDecimal wzoNum1) {
        this.wzoNum1 = wzoNum1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wzoSerial != null ? wzoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WzoryDok)) {
            return false;
        }
        WzoryDok other = (WzoryDok) object;
        if ((this.wzoSerial == null && other.wzoSerial != null) || (this.wzoSerial != null && !this.wzoSerial.equals(other.wzoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WzoryDok[ wzoSerial=" + wzoSerial + " ]";
    }
    
}
