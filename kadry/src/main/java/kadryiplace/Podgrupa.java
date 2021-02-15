/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podgrupa", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podgrupa.findAll", query = "SELECT p FROM Podgrupa p"),
    @NamedQuery(name = "Podgrupa.findByPgrSerial", query = "SELECT p FROM Podgrupa p WHERE p.pgrSerial = :pgrSerial"),
    @NamedQuery(name = "Podgrupa.findByPgrNazwa", query = "SELECT p FROM Podgrupa p WHERE p.pgrNazwa = :pgrNazwa"),
    @NamedQuery(name = "Podgrupa.findByPgrPorzadek", query = "SELECT p FROM Podgrupa p WHERE p.pgrPorzadek = :pgrPorzadek"),
    @NamedQuery(name = "Podgrupa.findByPgrChar1", query = "SELECT p FROM Podgrupa p WHERE p.pgrChar1 = :pgrChar1"),
    @NamedQuery(name = "Podgrupa.findByPgrChar2", query = "SELECT p FROM Podgrupa p WHERE p.pgrChar2 = :pgrChar2"),
    @NamedQuery(name = "Podgrupa.findByPgrChar3", query = "SELECT p FROM Podgrupa p WHERE p.pgrChar3 = :pgrChar3"),
    @NamedQuery(name = "Podgrupa.findByPgrChar4", query = "SELECT p FROM Podgrupa p WHERE p.pgrChar4 = :pgrChar4"),
    @NamedQuery(name = "Podgrupa.findByPgrVchar1", query = "SELECT p FROM Podgrupa p WHERE p.pgrVchar1 = :pgrVchar1"),
    @NamedQuery(name = "Podgrupa.findByPgrVchar2", query = "SELECT p FROM Podgrupa p WHERE p.pgrVchar2 = :pgrVchar2"),
    @NamedQuery(name = "Podgrupa.findByPgrNum1", query = "SELECT p FROM Podgrupa p WHERE p.pgrNum1 = :pgrNum1"),
    @NamedQuery(name = "Podgrupa.findByPgrNum2", query = "SELECT p FROM Podgrupa p WHERE p.pgrNum2 = :pgrNum2")})
public class Podgrupa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pgr_serial", nullable = false)
    private Integer pgrSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "pgr_nazwa", nullable = false, length = 64)
    private String pgrNazwa;
    @Column(name = "pgr_porzadek")
    private Short pgrPorzadek;
    @Column(name = "pgr_char_1")
    private Character pgrChar1;
    @Column(name = "pgr_char_2")
    private Character pgrChar2;
    @Column(name = "pgr_char_3")
    private Character pgrChar3;
    @Column(name = "pgr_char_4")
    private Character pgrChar4;
    @Size(max = 64)
    @Column(name = "pgr_vchar_1", length = 64)
    private String pgrVchar1;
    @Size(max = 64)
    @Column(name = "pgr_vchar_2", length = 64)
    private String pgrVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pgr_num_1", precision = 17, scale = 6)
    private BigDecimal pgrNum1;
    @Column(name = "pgr_num_2", precision = 17, scale = 6)
    private BigDecimal pgrNum2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magPgrSerial")
    private List<Magazyn> magazynList;
    @JoinColumn(name = "pgr_gru_serial", referencedColumnName = "gru_serial", nullable = false)
    @ManyToOne(optional = false)
    private Grupa pgrGruSerial;

    public Podgrupa() {
    }

    public Podgrupa(Integer pgrSerial) {
        this.pgrSerial = pgrSerial;
    }

    public Podgrupa(Integer pgrSerial, String pgrNazwa) {
        this.pgrSerial = pgrSerial;
        this.pgrNazwa = pgrNazwa;
    }

    public Integer getPgrSerial() {
        return pgrSerial;
    }

    public void setPgrSerial(Integer pgrSerial) {
        this.pgrSerial = pgrSerial;
    }

    public String getPgrNazwa() {
        return pgrNazwa;
    }

    public void setPgrNazwa(String pgrNazwa) {
        this.pgrNazwa = pgrNazwa;
    }

    public Short getPgrPorzadek() {
        return pgrPorzadek;
    }

    public void setPgrPorzadek(Short pgrPorzadek) {
        this.pgrPorzadek = pgrPorzadek;
    }

    public Character getPgrChar1() {
        return pgrChar1;
    }

    public void setPgrChar1(Character pgrChar1) {
        this.pgrChar1 = pgrChar1;
    }

    public Character getPgrChar2() {
        return pgrChar2;
    }

    public void setPgrChar2(Character pgrChar2) {
        this.pgrChar2 = pgrChar2;
    }

    public Character getPgrChar3() {
        return pgrChar3;
    }

    public void setPgrChar3(Character pgrChar3) {
        this.pgrChar3 = pgrChar3;
    }

    public Character getPgrChar4() {
        return pgrChar4;
    }

    public void setPgrChar4(Character pgrChar4) {
        this.pgrChar4 = pgrChar4;
    }

    public String getPgrVchar1() {
        return pgrVchar1;
    }

    public void setPgrVchar1(String pgrVchar1) {
        this.pgrVchar1 = pgrVchar1;
    }

    public String getPgrVchar2() {
        return pgrVchar2;
    }

    public void setPgrVchar2(String pgrVchar2) {
        this.pgrVchar2 = pgrVchar2;
    }

    public BigDecimal getPgrNum1() {
        return pgrNum1;
    }

    public void setPgrNum1(BigDecimal pgrNum1) {
        this.pgrNum1 = pgrNum1;
    }

    public BigDecimal getPgrNum2() {
        return pgrNum2;
    }

    public void setPgrNum2(BigDecimal pgrNum2) {
        this.pgrNum2 = pgrNum2;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    public Grupa getPgrGruSerial() {
        return pgrGruSerial;
    }

    public void setPgrGruSerial(Grupa pgrGruSerial) {
        this.pgrGruSerial = pgrGruSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pgrSerial != null ? pgrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Podgrupa)) {
            return false;
        }
        Podgrupa other = (Podgrupa) object;
        if ((this.pgrSerial == null && other.pgrSerial != null) || (this.pgrSerial != null && !this.pgrSerial.equals(other.pgrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Podgrupa[ pgrSerial=" + pgrSerial + " ]";
    }
    
}
