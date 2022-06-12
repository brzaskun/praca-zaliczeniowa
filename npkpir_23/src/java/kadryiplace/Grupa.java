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
@Table(name = "grupa", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupa.findAll", query = "SELECT g FROM Grupa g"),
    @NamedQuery(name = "Grupa.findByGruSerial", query = "SELECT g FROM Grupa g WHERE g.gruSerial = :gruSerial"),
    @NamedQuery(name = "Grupa.findByGruNazwa", query = "SELECT g FROM Grupa g WHERE g.gruNazwa = :gruNazwa"),
    @NamedQuery(name = "Grupa.findByGruTyp", query = "SELECT g FROM Grupa g WHERE g.gruTyp = :gruTyp"),
    @NamedQuery(name = "Grupa.findByGruPorzadek", query = "SELECT g FROM Grupa g WHERE g.gruPorzadek = :gruPorzadek"),
    @NamedQuery(name = "Grupa.findByGruChar1", query = "SELECT g FROM Grupa g WHERE g.gruChar1 = :gruChar1"),
    @NamedQuery(name = "Grupa.findByGruChar2", query = "SELECT g FROM Grupa g WHERE g.gruChar2 = :gruChar2"),
    @NamedQuery(name = "Grupa.findByGruChar3", query = "SELECT g FROM Grupa g WHERE g.gruChar3 = :gruChar3"),
    @NamedQuery(name = "Grupa.findByGruChar4", query = "SELECT g FROM Grupa g WHERE g.gruChar4 = :gruChar4"),
    @NamedQuery(name = "Grupa.findByGruVchar1", query = "SELECT g FROM Grupa g WHERE g.gruVchar1 = :gruVchar1"),
    @NamedQuery(name = "Grupa.findByGruVchar2", query = "SELECT g FROM Grupa g WHERE g.gruVchar2 = :gruVchar2"),
    @NamedQuery(name = "Grupa.findByGruNum1", query = "SELECT g FROM Grupa g WHERE g.gruNum1 = :gruNum1"),
    @NamedQuery(name = "Grupa.findByGruNum2", query = "SELECT g FROM Grupa g WHERE g.gruNum2 = :gruNum2")})
public class Grupa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "gru_serial", nullable = false)
    private Integer gruSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "gru_nazwa", nullable = false, length = 64)
    private String gruNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gru_typ", nullable = false)
    private Character gruTyp;
    @Column(name = "gru_porzadek")
    private Short gruPorzadek;
    @Column(name = "gru_char_1")
    private Character gruChar1;
    @Column(name = "gru_char_2")
    private Character gruChar2;
    @Column(name = "gru_char_3")
    private Character gruChar3;
    @Column(name = "gru_char_4")
    private Character gruChar4;
    @Size(max = 64)
    @Column(name = "gru_vchar_1", length = 64)
    private String gruVchar1;
    @Size(max = 64)
    @Column(name = "gru_vchar_2", length = 64)
    private String gruVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gru_num_1", precision = 17, scale = 6)
    private BigDecimal gruNum1;
    @Column(name = "gru_num_2", precision = 17, scale = 6)
    private BigDecimal gruNum2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magGruSerial")
    private List<Magazyn> magazynList;
    @JoinColumn(name = "gru_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma gruFirSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pgrGruSerial")
    private List<Podgrupa> podgrupaList;

    public Grupa() {
    }

    public Grupa(Integer gruSerial) {
        this.gruSerial = gruSerial;
    }

    public Grupa(Integer gruSerial, String gruNazwa, Character gruTyp) {
        this.gruSerial = gruSerial;
        this.gruNazwa = gruNazwa;
        this.gruTyp = gruTyp;
    }

    public Integer getGruSerial() {
        return gruSerial;
    }

    public void setGruSerial(Integer gruSerial) {
        this.gruSerial = gruSerial;
    }

    public String getGruNazwa() {
        return gruNazwa;
    }

    public void setGruNazwa(String gruNazwa) {
        this.gruNazwa = gruNazwa;
    }

    public Character getGruTyp() {
        return gruTyp;
    }

    public void setGruTyp(Character gruTyp) {
        this.gruTyp = gruTyp;
    }

    public Short getGruPorzadek() {
        return gruPorzadek;
    }

    public void setGruPorzadek(Short gruPorzadek) {
        this.gruPorzadek = gruPorzadek;
    }

    public Character getGruChar1() {
        return gruChar1;
    }

    public void setGruChar1(Character gruChar1) {
        this.gruChar1 = gruChar1;
    }

    public Character getGruChar2() {
        return gruChar2;
    }

    public void setGruChar2(Character gruChar2) {
        this.gruChar2 = gruChar2;
    }

    public Character getGruChar3() {
        return gruChar3;
    }

    public void setGruChar3(Character gruChar3) {
        this.gruChar3 = gruChar3;
    }

    public Character getGruChar4() {
        return gruChar4;
    }

    public void setGruChar4(Character gruChar4) {
        this.gruChar4 = gruChar4;
    }

    public String getGruVchar1() {
        return gruVchar1;
    }

    public void setGruVchar1(String gruVchar1) {
        this.gruVchar1 = gruVchar1;
    }

    public String getGruVchar2() {
        return gruVchar2;
    }

    public void setGruVchar2(String gruVchar2) {
        this.gruVchar2 = gruVchar2;
    }

    public BigDecimal getGruNum1() {
        return gruNum1;
    }

    public void setGruNum1(BigDecimal gruNum1) {
        this.gruNum1 = gruNum1;
    }

    public BigDecimal getGruNum2() {
        return gruNum2;
    }

    public void setGruNum2(BigDecimal gruNum2) {
        this.gruNum2 = gruNum2;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    public Firma getGruFirSerial() {
        return gruFirSerial;
    }

    public void setGruFirSerial(Firma gruFirSerial) {
        this.gruFirSerial = gruFirSerial;
    }

    @XmlTransient
    public List<Podgrupa> getPodgrupaList() {
        return podgrupaList;
    }

    public void setPodgrupaList(List<Podgrupa> podgrupaList) {
        this.podgrupaList = podgrupaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruSerial != null ? gruSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupa)) {
            return false;
        }
        Grupa other = (Grupa) object;
        if ((this.gruSerial == null && other.gruSerial != null) || (this.gruSerial != null && !this.gruSerial.equals(other.gruSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Grupa[ gruSerial=" + gruSerial + " ]";
    }
    
}
