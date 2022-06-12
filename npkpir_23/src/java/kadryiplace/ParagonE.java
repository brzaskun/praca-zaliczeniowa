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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "paragon_e", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParagonE.findAll", query = "SELECT p FROM ParagonE p"),
    @NamedQuery(name = "ParagonE.findByPaeSerial", query = "SELECT p FROM ParagonE p WHERE p.paeSerial = :paeSerial"),
    @NamedQuery(name = "ParagonE.findByPaeSysSerial", query = "SELECT p FROM ParagonE p WHERE p.paeSysSerial = :paeSysSerial"),
    @NamedQuery(name = "ParagonE.findByPaeChar1", query = "SELECT p FROM ParagonE p WHERE p.paeChar1 = :paeChar1"),
    @NamedQuery(name = "ParagonE.findByPaeVchar1", query = "SELECT p FROM ParagonE p WHERE p.paeVchar1 = :paeVchar1"),
    @NamedQuery(name = "ParagonE.findByPaeVchar2", query = "SELECT p FROM ParagonE p WHERE p.paeVchar2 = :paeVchar2"),
    @NamedQuery(name = "ParagonE.findByPaeNumeric1", query = "SELECT p FROM ParagonE p WHERE p.paeNumeric1 = :paeNumeric1"),
    @NamedQuery(name = "ParagonE.findByPaeDate1", query = "SELECT p FROM ParagonE p WHERE p.paeDate1 = :paeDate1"),
    @NamedQuery(name = "ParagonE.findByPaeInt1", query = "SELECT p FROM ParagonE p WHERE p.paeInt1 = :paeInt1")})
public class ParagonE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pae_serial", nullable = false)
    private Integer paeSerial;
    @Column(name = "pae_sys_serial")
    private Integer paeSysSerial;
    @Column(name = "pae_char_1")
    private Character paeChar1;
    @Size(max = 64)
    @Column(name = "pae_vchar_1", length = 64)
    private String paeVchar1;
    @Size(max = 64)
    @Column(name = "pae_vchar_2", length = 64)
    private String paeVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pae_numeric_1", precision = 17, scale = 6)
    private BigDecimal paeNumeric1;
    @Column(name = "pae_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paeDate1;
    @Column(name = "pae_int_1")
    private Integer paeInt1;
    @JoinColumn(name = "pae_par_serial", referencedColumnName = "par_serial")
    @ManyToOne
    private Paragon paeParSerial;

    public ParagonE() {
    }

    public ParagonE(Integer paeSerial) {
        this.paeSerial = paeSerial;
    }

    public Integer getPaeSerial() {
        return paeSerial;
    }

    public void setPaeSerial(Integer paeSerial) {
        this.paeSerial = paeSerial;
    }

    public Integer getPaeSysSerial() {
        return paeSysSerial;
    }

    public void setPaeSysSerial(Integer paeSysSerial) {
        this.paeSysSerial = paeSysSerial;
    }

    public Character getPaeChar1() {
        return paeChar1;
    }

    public void setPaeChar1(Character paeChar1) {
        this.paeChar1 = paeChar1;
    }

    public String getPaeVchar1() {
        return paeVchar1;
    }

    public void setPaeVchar1(String paeVchar1) {
        this.paeVchar1 = paeVchar1;
    }

    public String getPaeVchar2() {
        return paeVchar2;
    }

    public void setPaeVchar2(String paeVchar2) {
        this.paeVchar2 = paeVchar2;
    }

    public BigDecimal getPaeNumeric1() {
        return paeNumeric1;
    }

    public void setPaeNumeric1(BigDecimal paeNumeric1) {
        this.paeNumeric1 = paeNumeric1;
    }

    public Date getPaeDate1() {
        return paeDate1;
    }

    public void setPaeDate1(Date paeDate1) {
        this.paeDate1 = paeDate1;
    }

    public Integer getPaeInt1() {
        return paeInt1;
    }

    public void setPaeInt1(Integer paeInt1) {
        this.paeInt1 = paeInt1;
    }

    public Paragon getPaeParSerial() {
        return paeParSerial;
    }

    public void setPaeParSerial(Paragon paeParSerial) {
        this.paeParSerial = paeParSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paeSerial != null ? paeSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParagonE)) {
            return false;
        }
        ParagonE other = (ParagonE) object;
        if ((this.paeSerial == null && other.paeSerial != null) || (this.paeSerial != null && !this.paeSerial.equals(other.paeSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ParagonE[ paeSerial=" + paeSerial + " ]";
    }
    
}
