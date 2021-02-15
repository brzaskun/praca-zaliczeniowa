/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "prefer", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prefer.findAll", query = "SELECT p FROM Prefer p"),
    @NamedQuery(name = "Prefer.findByPreKod", query = "SELECT p FROM Prefer p WHERE p.preKod = :preKod"),
    @NamedQuery(name = "Prefer.findByPreWartosc", query = "SELECT p FROM Prefer p WHERE p.preWartosc = :preWartosc"),
    @NamedQuery(name = "Prefer.findByPreOpis", query = "SELECT p FROM Prefer p WHERE p.preOpis = :preOpis"),
    @NamedQuery(name = "Prefer.findByPreKolejnosc", query = "SELECT p FROM Prefer p WHERE p.preKolejnosc = :preKolejnosc"),
    @NamedQuery(name = "Prefer.findByPreInteger", query = "SELECT p FROM Prefer p WHERE p.preInteger = :preInteger"),
    @NamedQuery(name = "Prefer.findByPreChar", query = "SELECT p FROM Prefer p WHERE p.preChar = :preChar"),
    @NamedQuery(name = "Prefer.findByPreDotyczy", query = "SELECT p FROM Prefer p WHERE p.preDotyczy = :preDotyczy"),
    @NamedQuery(name = "Prefer.findByPreLong", query = "SELECT p FROM Prefer p WHERE p.preLong = :preLong"),
    @NamedQuery(name = "Prefer.findByPreDate", query = "SELECT p FROM Prefer p WHERE p.preDate = :preDate"),
    @NamedQuery(name = "Prefer.findByPreSerial", query = "SELECT p FROM Prefer p WHERE p.preSerial = :preSerial"),
    @NamedQuery(name = "Prefer.findByPreWartoscKr", query = "SELECT p FROM Prefer p WHERE p.preWartoscKr = :preWartoscKr"),
    @NamedQuery(name = "Prefer.findByPreDecimal", query = "SELECT p FROM Prefer p WHERE p.preDecimal = :preDecimal"),
    @NamedQuery(name = "Prefer.findByPreDecimalKr", query = "SELECT p FROM Prefer p WHERE p.preDecimalKr = :preDecimalKr")})
public class Prefer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pre_kod", nullable = false)
    private Character preKod;
    @Size(max = 254)
    @Column(name = "pre_wartosc", length = 254)
    private String preWartosc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "pre_opis", nullable = false, length = 128)
    private String preOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pre_kolejnosc", nullable = false)
    private short preKolejnosc;
    @Column(name = "pre_integer")
    private Short preInteger;
    @Column(name = "pre_char")
    private Character preChar;
    @Size(max = 2)
    @Column(name = "pre_dotyczy", length = 2)
    private String preDotyczy;
    @Column(name = "pre_long")
    private Integer preLong;
    @Column(name = "pre_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date preDate;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pre_serial", nullable = false)
    private Integer preSerial;
    @Size(max = 16)
    @Column(name = "pre_wartosc_kr", length = 16)
    private String preWartoscKr;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pre_decimal", precision = 13, scale = 2)
    private BigDecimal preDecimal;
    @Column(name = "pre_decimal_kr", precision = 5, scale = 2)
    private BigDecimal preDecimalKr;

    public Prefer() {
    }

    public Prefer(Integer preSerial) {
        this.preSerial = preSerial;
    }

    public Prefer(Integer preSerial, Character preKod, String preOpis, short preKolejnosc) {
        this.preSerial = preSerial;
        this.preKod = preKod;
        this.preOpis = preOpis;
        this.preKolejnosc = preKolejnosc;
    }

    public Character getPreKod() {
        return preKod;
    }

    public void setPreKod(Character preKod) {
        this.preKod = preKod;
    }

    public String getPreWartosc() {
        return preWartosc;
    }

    public void setPreWartosc(String preWartosc) {
        this.preWartosc = preWartosc;
    }

    public String getPreOpis() {
        return preOpis;
    }

    public void setPreOpis(String preOpis) {
        this.preOpis = preOpis;
    }

    public short getPreKolejnosc() {
        return preKolejnosc;
    }

    public void setPreKolejnosc(short preKolejnosc) {
        this.preKolejnosc = preKolejnosc;
    }

    public Short getPreInteger() {
        return preInteger;
    }

    public void setPreInteger(Short preInteger) {
        this.preInteger = preInteger;
    }

    public Character getPreChar() {
        return preChar;
    }

    public void setPreChar(Character preChar) {
        this.preChar = preChar;
    }

    public String getPreDotyczy() {
        return preDotyczy;
    }

    public void setPreDotyczy(String preDotyczy) {
        this.preDotyczy = preDotyczy;
    }

    public Integer getPreLong() {
        return preLong;
    }

    public void setPreLong(Integer preLong) {
        this.preLong = preLong;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate;
    }

    public Integer getPreSerial() {
        return preSerial;
    }

    public void setPreSerial(Integer preSerial) {
        this.preSerial = preSerial;
    }

    public String getPreWartoscKr() {
        return preWartoscKr;
    }

    public void setPreWartoscKr(String preWartoscKr) {
        this.preWartoscKr = preWartoscKr;
    }

    public BigDecimal getPreDecimal() {
        return preDecimal;
    }

    public void setPreDecimal(BigDecimal preDecimal) {
        this.preDecimal = preDecimal;
    }

    public BigDecimal getPreDecimalKr() {
        return preDecimalKr;
    }

    public void setPreDecimalKr(BigDecimal preDecimalKr) {
        this.preDecimalKr = preDecimalKr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preSerial != null ? preSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prefer)) {
            return false;
        }
        Prefer other = (Prefer) object;
        if ((this.preSerial == null && other.preSerial != null) || (this.preSerial != null && !this.preSerial.equals(other.preSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Prefer[ preSerial=" + preSerial + " ]";
    }
    
}
