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
@Table(name = "vatrej", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vatrej.findAll", query = "SELECT v FROM Vatrej v"),
    @NamedQuery(name = "Vatrej.findByVarSerial", query = "SELECT v FROM Vatrej v WHERE v.varSerial = :varSerial"),
    @NamedQuery(name = "Vatrej.findByVarOpis", query = "SELECT v FROM Vatrej v WHERE v.varOpis = :varOpis"),
    @NamedQuery(name = "Vatrej.findByVarProcent", query = "SELECT v FROM Vatrej v WHERE v.varProcent = :varProcent"),
    @NamedQuery(name = "Vatrej.findByVarRejSerial", query = "SELECT v FROM Vatrej v WHERE v.varRejSerial = :varRejSerial"),
    @NamedQuery(name = "Vatrej.findByVarKolejnosc", query = "SELECT v FROM Vatrej v WHERE v.varKolejnosc = :varKolejnosc"),
    @NamedQuery(name = "Vatrej.findByVarAktywna", query = "SELECT v FROM Vatrej v WHERE v.varAktywna = :varAktywna"),
    @NamedQuery(name = "Vatrej.findByVarKod", query = "SELECT v FROM Vatrej v WHERE v.varKod = :varKod"),
    @NamedQuery(name = "Vatrej.findByVarChar1", query = "SELECT v FROM Vatrej v WHERE v.varChar1 = :varChar1"),
    @NamedQuery(name = "Vatrej.findByVarChar2", query = "SELECT v FROM Vatrej v WHERE v.varChar2 = :varChar2"),
    @NamedQuery(name = "Vatrej.findByVarChar3", query = "SELECT v FROM Vatrej v WHERE v.varChar3 = :varChar3"),
    @NamedQuery(name = "Vatrej.findByVarChar4", query = "SELECT v FROM Vatrej v WHERE v.varChar4 = :varChar4"),
    @NamedQuery(name = "Vatrej.findByVarVchar1", query = "SELECT v FROM Vatrej v WHERE v.varVchar1 = :varVchar1"),
    @NamedQuery(name = "Vatrej.findByVarVchar2", query = "SELECT v FROM Vatrej v WHERE v.varVchar2 = :varVchar2"),
    @NamedQuery(name = "Vatrej.findByVarDate1", query = "SELECT v FROM Vatrej v WHERE v.varDate1 = :varDate1"),
    @NamedQuery(name = "Vatrej.findByVarDate2", query = "SELECT v FROM Vatrej v WHERE v.varDate2 = :varDate2"),
    @NamedQuery(name = "Vatrej.findByVarNum1", query = "SELECT v FROM Vatrej v WHERE v.varNum1 = :varNum1"),
    @NamedQuery(name = "Vatrej.findByVarNum2", query = "SELECT v FROM Vatrej v WHERE v.varNum2 = :varNum2"),
    @NamedQuery(name = "Vatrej.findByVarNum3", query = "SELECT v FROM Vatrej v WHERE v.varNum3 = :varNum3"),
    @NamedQuery(name = "Vatrej.findByVarNum4", query = "SELECT v FROM Vatrej v WHERE v.varNum4 = :varNum4")})
public class Vatrej implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "var_serial", nullable = false)
    private Integer varSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "var_opis", nullable = false, length = 32)
    private String varOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "var_procent", precision = 5, scale = 2)
    private BigDecimal varProcent;
    @Column(name = "var_rej_serial")
    private Integer varRejSerial;
    @Column(name = "var_kolejnosc")
    private Short varKolejnosc;
    @Column(name = "var_aktywna")
    private Character varAktywna;
    @Size(max = 2)
    @Column(name = "var_kod", length = 2)
    private String varKod;
    @Column(name = "var_char_1")
    private Character varChar1;
    @Column(name = "var_char_2")
    private Character varChar2;
    @Column(name = "var_char_3")
    private Character varChar3;
    @Column(name = "var_char_4")
    private Character varChar4;
    @Size(max = 64)
    @Column(name = "var_vchar_1", length = 64)
    private String varVchar1;
    @Size(max = 64)
    @Column(name = "var_vchar_2", length = 64)
    private String varVchar2;
    @Column(name = "var_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date varDate1;
    @Column(name = "var_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date varDate2;
    @Column(name = "var_num_1", precision = 17, scale = 6)
    private BigDecimal varNum1;
    @Column(name = "var_num_2", precision = 17, scale = 6)
    private BigDecimal varNum2;
    @Column(name = "var_num_3", precision = 17, scale = 6)
    private BigDecimal varNum3;
    @Column(name = "var_num_4", precision = 17, scale = 6)
    private BigDecimal varNum4;
    @JoinColumn(name = "var_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma varFirSerial;

    public Vatrej() {
    }

    public Vatrej(Integer varSerial) {
        this.varSerial = varSerial;
    }

    public Vatrej(Integer varSerial, String varOpis) {
        this.varSerial = varSerial;
        this.varOpis = varOpis;
    }

    public Integer getVarSerial() {
        return varSerial;
    }

    public void setVarSerial(Integer varSerial) {
        this.varSerial = varSerial;
    }

    public String getVarOpis() {
        return varOpis;
    }

    public void setVarOpis(String varOpis) {
        this.varOpis = varOpis;
    }

    public BigDecimal getVarProcent() {
        return varProcent;
    }

    public void setVarProcent(BigDecimal varProcent) {
        this.varProcent = varProcent;
    }

    public Integer getVarRejSerial() {
        return varRejSerial;
    }

    public void setVarRejSerial(Integer varRejSerial) {
        this.varRejSerial = varRejSerial;
    }

    public Short getVarKolejnosc() {
        return varKolejnosc;
    }

    public void setVarKolejnosc(Short varKolejnosc) {
        this.varKolejnosc = varKolejnosc;
    }

    public Character getVarAktywna() {
        return varAktywna;
    }

    public void setVarAktywna(Character varAktywna) {
        this.varAktywna = varAktywna;
    }

    public String getVarKod() {
        return varKod;
    }

    public void setVarKod(String varKod) {
        this.varKod = varKod;
    }

    public Character getVarChar1() {
        return varChar1;
    }

    public void setVarChar1(Character varChar1) {
        this.varChar1 = varChar1;
    }

    public Character getVarChar2() {
        return varChar2;
    }

    public void setVarChar2(Character varChar2) {
        this.varChar2 = varChar2;
    }

    public Character getVarChar3() {
        return varChar3;
    }

    public void setVarChar3(Character varChar3) {
        this.varChar3 = varChar3;
    }

    public Character getVarChar4() {
        return varChar4;
    }

    public void setVarChar4(Character varChar4) {
        this.varChar4 = varChar4;
    }

    public String getVarVchar1() {
        return varVchar1;
    }

    public void setVarVchar1(String varVchar1) {
        this.varVchar1 = varVchar1;
    }

    public String getVarVchar2() {
        return varVchar2;
    }

    public void setVarVchar2(String varVchar2) {
        this.varVchar2 = varVchar2;
    }

    public Date getVarDate1() {
        return varDate1;
    }

    public void setVarDate1(Date varDate1) {
        this.varDate1 = varDate1;
    }

    public Date getVarDate2() {
        return varDate2;
    }

    public void setVarDate2(Date varDate2) {
        this.varDate2 = varDate2;
    }

    public BigDecimal getVarNum1() {
        return varNum1;
    }

    public void setVarNum1(BigDecimal varNum1) {
        this.varNum1 = varNum1;
    }

    public BigDecimal getVarNum2() {
        return varNum2;
    }

    public void setVarNum2(BigDecimal varNum2) {
        this.varNum2 = varNum2;
    }

    public BigDecimal getVarNum3() {
        return varNum3;
    }

    public void setVarNum3(BigDecimal varNum3) {
        this.varNum3 = varNum3;
    }

    public BigDecimal getVarNum4() {
        return varNum4;
    }

    public void setVarNum4(BigDecimal varNum4) {
        this.varNum4 = varNum4;
    }

    public Firma getVarFirSerial() {
        return varFirSerial;
    }

    public void setVarFirSerial(Firma varFirSerial) {
        this.varFirSerial = varFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varSerial != null ? varSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vatrej)) {
            return false;
        }
        Vatrej other = (Vatrej) object;
        if ((this.varSerial == null && other.varSerial != null) || (this.varSerial != null && !this.varSerial.equals(other.varSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Vatrej[ varSerial=" + varSerial + " ]";
    }
    
}
