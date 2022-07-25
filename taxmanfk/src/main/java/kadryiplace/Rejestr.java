/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rejestr", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rejestr.findAll", query = "SELECT r FROM Rejestr r"),
    @NamedQuery(name = "Rejestr.findByRejSerial", query = "SELECT r FROM Rejestr r WHERE r.rejSerial = :rejSerial"),
    @NamedQuery(name = "Rejestr.findByRejTyp", query = "SELECT r FROM Rejestr r WHERE r.rejTyp = :rejTyp"),
    @NamedQuery(name = "Rejestr.findByRejOpis", query = "SELECT r FROM Rejestr r WHERE r.rejOpis = :rejOpis"),
    @NamedQuery(name = "Rejestr.findByRejKolKsiegi", query = "SELECT r FROM Rejestr r WHERE r.rejKolKsiegi = :rejKolKsiegi"),
    @NamedQuery(name = "Rejestr.findByRejSkrot", query = "SELECT r FROM Rejestr r WHERE r.rejSkrot = :rejSkrot"),
    @NamedQuery(name = "Rejestr.findByRejKorekty", query = "SELECT r FROM Rejestr r WHERE r.rejKorekty = :rejKorekty"),
    @NamedQuery(name = "Rejestr.findByRejKolejnosc", query = "SELECT r FROM Rejestr r WHERE r.rejKolejnosc = :rejKolejnosc"),
    @NamedQuery(name = "Rejestr.findByRejVat", query = "SELECT r FROM Rejestr r WHERE r.rejVat = :rejVat"),
    @NamedQuery(name = "Rejestr.findByRejRyczalt", query = "SELECT r FROM Rejestr r WHERE r.rejRyczalt = :rejRyczalt"),
    @NamedQuery(name = "Rejestr.findByRejKolPit28", query = "SELECT r FROM Rejestr r WHERE r.rejKolPit28 = :rejKolPit28"),
    @NamedQuery(name = "Rejestr.findByRejChar1", query = "SELECT r FROM Rejestr r WHERE r.rejChar1 = :rejChar1"),
    @NamedQuery(name = "Rejestr.findByRejChar2", query = "SELECT r FROM Rejestr r WHERE r.rejChar2 = :rejChar2"),
    @NamedQuery(name = "Rejestr.findByRejChar3", query = "SELECT r FROM Rejestr r WHERE r.rejChar3 = :rejChar3"),
    @NamedQuery(name = "Rejestr.findByRejChar4", query = "SELECT r FROM Rejestr r WHERE r.rejChar4 = :rejChar4"),
    @NamedQuery(name = "Rejestr.findByRejVchar1", query = "SELECT r FROM Rejestr r WHERE r.rejVchar1 = :rejVchar1"),
    @NamedQuery(name = "Rejestr.findByRejVchar2", query = "SELECT r FROM Rejestr r WHERE r.rejVchar2 = :rejVchar2"),
    @NamedQuery(name = "Rejestr.findByRejInt1", query = "SELECT r FROM Rejestr r WHERE r.rejInt1 = :rejInt1"),
    @NamedQuery(name = "Rejestr.findByRejInt2", query = "SELECT r FROM Rejestr r WHERE r.rejInt2 = :rejInt2"),
    @NamedQuery(name = "Rejestr.findByRejDate1", query = "SELECT r FROM Rejestr r WHERE r.rejDate1 = :rejDate1"),
    @NamedQuery(name = "Rejestr.findByRejDate2", query = "SELECT r FROM Rejestr r WHERE r.rejDate2 = :rejDate2"),
    @NamedQuery(name = "Rejestr.findByRejNum1", query = "SELECT r FROM Rejestr r WHERE r.rejNum1 = :rejNum1"),
    @NamedQuery(name = "Rejestr.findByRejNum2", query = "SELECT r FROM Rejestr r WHERE r.rejNum2 = :rejNum2"),
    @NamedQuery(name = "Rejestr.findByRejStawkaPit28", query = "SELECT r FROM Rejestr r WHERE r.rejStawkaPit28 = :rejStawkaPit28")})
public class Rejestr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rej_serial", nullable = false)
    private Integer rejSerial;
    @Column(name = "rej_typ")
    private Character rejTyp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "rej_opis", nullable = false, length = 64)
    private String rejOpis;
    @Column(name = "rej_kol_ksiegi")
    private Short rejKolKsiegi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "rej_skrot", nullable = false, length = 16)
    private String rejSkrot;
    @Column(name = "rej_korekty")
    private Character rejKorekty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rej_kolejnosc", nullable = false)
    private short rejKolejnosc;
    @Column(name = "rej_vat")
    private Character rejVat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rej_ryczalt", precision = 5, scale = 2)
    private BigDecimal rejRyczalt;
    @Column(name = "rej_kol_pit_28")
    private Short rejKolPit28;
    @Column(name = "rej_char_1")
    private Character rejChar1;
    @Column(name = "rej_char_2")
    private Character rejChar2;
    @Column(name = "rej_char_3")
    private Character rejChar3;
    @Column(name = "rej_char_4")
    private Character rejChar4;
    @Size(max = 64)
    @Column(name = "rej_vchar_1", length = 64)
    private String rejVchar1;
    @Size(max = 64)
    @Column(name = "rej_vchar_2", length = 64)
    private String rejVchar2;
    @Column(name = "rej_int_1")
    private Integer rejInt1;
    @Column(name = "rej_int_2")
    private Integer rejInt2;
    @Column(name = "rej_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejDate1;
    @Column(name = "rej_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejDate2;
    @Column(name = "rej_num_1", precision = 17, scale = 6)
    private BigDecimal rejNum1;
    @Column(name = "rej_num_2", precision = 17, scale = 6)
    private BigDecimal rejNum2;
    @Size(max = 2)
    @Column(name = "rej_stawka_pit_28", length = 2)
    private String rejStawkaPit28;
    @OneToMany(mappedBy = "zakRejSerial")
    private List<Zakupy> zakupyList;
    @JoinColumn(name = "rej_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma rejFirSerial;
    @OneToMany(mappedBy = "sprRejSerial")
    private List<Sprzedaz> sprzedazList;

    public Rejestr() {
    }

    public Rejestr(Integer rejSerial) {
        this.rejSerial = rejSerial;
    }

    public Rejestr(Integer rejSerial, String rejOpis, String rejSkrot, short rejKolejnosc) {
        this.rejSerial = rejSerial;
        this.rejOpis = rejOpis;
        this.rejSkrot = rejSkrot;
        this.rejKolejnosc = rejKolejnosc;
    }

    public Integer getRejSerial() {
        return rejSerial;
    }

    public void setRejSerial(Integer rejSerial) {
        this.rejSerial = rejSerial;
    }

    public Character getRejTyp() {
        return rejTyp;
    }

    public void setRejTyp(Character rejTyp) {
        this.rejTyp = rejTyp;
    }

    public String getRejOpis() {
        return rejOpis;
    }

    public void setRejOpis(String rejOpis) {
        this.rejOpis = rejOpis;
    }

    public Short getRejKolKsiegi() {
        return rejKolKsiegi;
    }

    public void setRejKolKsiegi(Short rejKolKsiegi) {
        this.rejKolKsiegi = rejKolKsiegi;
    }

    public String getRejSkrot() {
        return rejSkrot;
    }

    public void setRejSkrot(String rejSkrot) {
        this.rejSkrot = rejSkrot;
    }

    public Character getRejKorekty() {
        return rejKorekty;
    }

    public void setRejKorekty(Character rejKorekty) {
        this.rejKorekty = rejKorekty;
    }

    public short getRejKolejnosc() {
        return rejKolejnosc;
    }

    public void setRejKolejnosc(short rejKolejnosc) {
        this.rejKolejnosc = rejKolejnosc;
    }

    public Character getRejVat() {
        return rejVat;
    }

    public void setRejVat(Character rejVat) {
        this.rejVat = rejVat;
    }

    public BigDecimal getRejRyczalt() {
        return rejRyczalt;
    }

    public void setRejRyczalt(BigDecimal rejRyczalt) {
        this.rejRyczalt = rejRyczalt;
    }

    public Short getRejKolPit28() {
        return rejKolPit28;
    }

    public void setRejKolPit28(Short rejKolPit28) {
        this.rejKolPit28 = rejKolPit28;
    }

    public Character getRejChar1() {
        return rejChar1;
    }

    public void setRejChar1(Character rejChar1) {
        this.rejChar1 = rejChar1;
    }

    public Character getRejChar2() {
        return rejChar2;
    }

    public void setRejChar2(Character rejChar2) {
        this.rejChar2 = rejChar2;
    }

    public Character getRejChar3() {
        return rejChar3;
    }

    public void setRejChar3(Character rejChar3) {
        this.rejChar3 = rejChar3;
    }

    public Character getRejChar4() {
        return rejChar4;
    }

    public void setRejChar4(Character rejChar4) {
        this.rejChar4 = rejChar4;
    }

    public String getRejVchar1() {
        return rejVchar1;
    }

    public void setRejVchar1(String rejVchar1) {
        this.rejVchar1 = rejVchar1;
    }

    public String getRejVchar2() {
        return rejVchar2;
    }

    public void setRejVchar2(String rejVchar2) {
        this.rejVchar2 = rejVchar2;
    }

    public Integer getRejInt1() {
        return rejInt1;
    }

    public void setRejInt1(Integer rejInt1) {
        this.rejInt1 = rejInt1;
    }

    public Integer getRejInt2() {
        return rejInt2;
    }

    public void setRejInt2(Integer rejInt2) {
        this.rejInt2 = rejInt2;
    }

    public Date getRejDate1() {
        return rejDate1;
    }

    public void setRejDate1(Date rejDate1) {
        this.rejDate1 = rejDate1;
    }

    public Date getRejDate2() {
        return rejDate2;
    }

    public void setRejDate2(Date rejDate2) {
        this.rejDate2 = rejDate2;
    }

    public BigDecimal getRejNum1() {
        return rejNum1;
    }

    public void setRejNum1(BigDecimal rejNum1) {
        this.rejNum1 = rejNum1;
    }

    public BigDecimal getRejNum2() {
        return rejNum2;
    }

    public void setRejNum2(BigDecimal rejNum2) {
        this.rejNum2 = rejNum2;
    }

    public String getRejStawkaPit28() {
        return rejStawkaPit28;
    }

    public void setRejStawkaPit28(String rejStawkaPit28) {
        this.rejStawkaPit28 = rejStawkaPit28;
    }

    @XmlTransient
    public List<Zakupy> getZakupyList() {
        return zakupyList;
    }

    public void setZakupyList(List<Zakupy> zakupyList) {
        this.zakupyList = zakupyList;
    }

    public Firma getRejFirSerial() {
        return rejFirSerial;
    }

    public void setRejFirSerial(Firma rejFirSerial) {
        this.rejFirSerial = rejFirSerial;
    }

    @XmlTransient
    public List<Sprzedaz> getSprzedazList() {
        return sprzedazList;
    }

    public void setSprzedazList(List<Sprzedaz> sprzedazList) {
        this.sprzedazList = sprzedazList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rejSerial != null ? rejSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rejestr)) {
            return false;
        }
        Rejestr other = (Rejestr) object;
        if ((this.rejSerial == null && other.rejSerial != null) || (this.rejSerial != null && !this.rejSerial.equals(other.rejSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Rejestr[ rejSerial=" + rejSerial + " ]";
    }
    
}
