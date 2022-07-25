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
@Table(name = "ubezp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubezp.findAll", query = "SELECT u FROM Ubezp u"),
    @NamedQuery(name = "Ubezp.findByUbeSerial", query = "SELECT u FROM Ubezp u WHERE u.ubeSerial = :ubeSerial"),
    @NamedQuery(name = "Ubezp.findByUbeProcent", query = "SELECT u FROM Ubezp u WHERE u.ubeProcent = :ubeProcent"),
    @NamedQuery(name = "Ubezp.findByUbeOpis", query = "SELECT u FROM Ubezp u WHERE u.ubeOpis = :ubeOpis"),
    @NamedQuery(name = "Ubezp.findByUbeKolejnosc", query = "SELECT u FROM Ubezp u WHERE u.ubeKolejnosc = :ubeKolejnosc"),
    @NamedQuery(name = "Ubezp.findByUbeMiesiacOd", query = "SELECT u FROM Ubezp u WHERE u.ubeMiesiacOd = :ubeMiesiacOd"),
    @NamedQuery(name = "Ubezp.findByUbeRokOd", query = "SELECT u FROM Ubezp u WHERE u.ubeRokOd = :ubeRokOd"),
    @NamedQuery(name = "Ubezp.findByUbeMiesiacDo", query = "SELECT u FROM Ubezp u WHERE u.ubeMiesiacDo = :ubeMiesiacDo"),
    @NamedQuery(name = "Ubezp.findByUbeRokDo", query = "SELECT u FROM Ubezp u WHERE u.ubeRokDo = :ubeRokDo"),
    @NamedQuery(name = "Ubezp.findByUbeProcEmer", query = "SELECT u FROM Ubezp u WHERE u.ubeProcEmer = :ubeProcEmer"),
    @NamedQuery(name = "Ubezp.findByUbeProcRent", query = "SELECT u FROM Ubezp u WHERE u.ubeProcRent = :ubeProcRent"),
    @NamedQuery(name = "Ubezp.findByUbeProcChor", query = "SELECT u FROM Ubezp u WHERE u.ubeProcChor = :ubeProcChor"),
    @NamedQuery(name = "Ubezp.findByUbeProcWyp", query = "SELECT u FROM Ubezp u WHERE u.ubeProcWyp = :ubeProcWyp"),
    @NamedQuery(name = "Ubezp.findByUbeProcZdro", query = "SELECT u FROM Ubezp u WHERE u.ubeProcZdro = :ubeProcZdro"),
    @NamedQuery(name = "Ubezp.findByUbeProcFp", query = "SELECT u FROM Ubezp u WHERE u.ubeProcFp = :ubeProcFp"),
    @NamedQuery(name = "Ubezp.findByUbeProcFgsp", query = "SELECT u FROM Ubezp u WHERE u.ubeProcFgsp = :ubeProcFgsp"),
    @NamedQuery(name = "Ubezp.findByUbeMinPdst", query = "SELECT u FROM Ubezp u WHERE u.ubeMinPdst = :ubeMinPdst"),
    @NamedQuery(name = "Ubezp.findByUbeDodProc1", query = "SELECT u FROM Ubezp u WHERE u.ubeDodProc1 = :ubeDodProc1"),
    @NamedQuery(name = "Ubezp.findByUbeDodProc2", query = "SELECT u FROM Ubezp u WHERE u.ubeDodProc2 = :ubeDodProc2"),
    @NamedQuery(name = "Ubezp.findByUbeDodProc3", query = "SELECT u FROM Ubezp u WHERE u.ubeDodProc3 = :ubeDodProc3"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota1", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota1 = :ubeDodKwota1"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota2", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota2 = :ubeDodKwota2"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota3", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota3 = :ubeDodKwota3"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar1", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar1 = :ubeDodChar1"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar2", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar2 = :ubeDodChar2"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar3", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar3 = :ubeDodChar3"),
    @NamedQuery(name = "Ubezp.findByUbeOgrZdrowotne", query = "SELECT u FROM Ubezp u WHERE u.ubeOgrZdrowotne = :ubeOgrZdrowotne"),
    @NamedQuery(name = "Ubezp.findByUbeOgrEmerRent", query = "SELECT u FROM Ubezp u WHERE u.ubeOgrEmerRent = :ubeOgrEmerRent"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar4", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar4 = :ubeDodChar4"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar5", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar5 = :ubeDodChar5"),
    @NamedQuery(name = "Ubezp.findByUbeDodChar6", query = "SELECT u FROM Ubezp u WHERE u.ubeDodChar6 = :ubeDodChar6"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota4", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota4 = :ubeDodKwota4"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota5", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota5 = :ubeDodKwota5"),
    @NamedQuery(name = "Ubezp.findByUbeDodKwota6", query = "SELECT u FROM Ubezp u WHERE u.ubeDodKwota6 = :ubeDodKwota6"),
    @NamedQuery(name = "Ubezp.findByUbeDodVchar1", query = "SELECT u FROM Ubezp u WHERE u.ubeDodVchar1 = :ubeDodVchar1"),
    @NamedQuery(name = "Ubezp.findByUbeDodVchar2", query = "SELECT u FROM Ubezp u WHERE u.ubeDodVchar2 = :ubeDodVchar2")})
public class Ubezp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_serial", nullable = false)
    private Integer ubeSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_procent", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcent;
    @Size(max = 32)
    @Column(name = "ube_opis", length = 32)
    private String ubeOpis;
    @Column(name = "ube_kolejnosc")
    private Short ubeKolejnosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_miesiac_od", nullable = false)
    private short ubeMiesiacOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_rok_od", nullable = false)
    private short ubeRokOd;
    @Column(name = "ube_miesiac_do")
    private Short ubeMiesiacDo;
    @Column(name = "ube_rok_do")
    private Short ubeRokDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_emer", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcEmer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_rent", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcRent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_chor", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_wyp", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_zdro", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcZdro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_fp", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcFp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_proc_fgsp", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeProcFgsp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_min_pdst", nullable = false, precision = 13, scale = 2)
    private BigDecimal ubeMinPdst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_proc_1", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeDodProc1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_proc_2", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeDodProc2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_proc_3", nullable = false, precision = 5, scale = 2)
    private BigDecimal ubeDodProc3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_kwota_1", nullable = false, precision = 13, scale = 2)
    private BigDecimal ubeDodKwota1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_kwota_2", nullable = false, precision = 13, scale = 2)
    private BigDecimal ubeDodKwota2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_kwota_3", nullable = false, precision = 13, scale = 2)
    private BigDecimal ubeDodKwota3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_char_1", nullable = false)
    private Character ubeDodChar1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_char_2", nullable = false)
    private Character ubeDodChar2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_dod_char_3", nullable = false)
    private Character ubeDodChar3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_ogr_zdrowotne", nullable = false)
    private Character ubeOgrZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ube_ogr_emer_rent", nullable = false)
    private Character ubeOgrEmerRent;
    @Column(name = "ube_dod_char_4")
    private Character ubeDodChar4;
    @Column(name = "ube_dod_char_5")
    private Character ubeDodChar5;
    @Column(name = "ube_dod_char_6")
    private Character ubeDodChar6;
    @Column(name = "ube_dod_kwota_4", precision = 17, scale = 6)
    private BigDecimal ubeDodKwota4;
    @Column(name = "ube_dod_kwota_5", precision = 17, scale = 6)
    private BigDecimal ubeDodKwota5;
    @Column(name = "ube_dod_kwota_6", precision = 17, scale = 6)
    private BigDecimal ubeDodKwota6;
    @Size(max = 64)
    @Column(name = "ube_dod_vchar_1", length = 64)
    private String ubeDodVchar1;
    @Size(max = 64)
    @Column(name = "ube_dod_vchar_2", length = 64)
    private String ubeDodVchar2;

    public Ubezp() {
    }

    public Ubezp(Integer ubeSerial) {
        this.ubeSerial = ubeSerial;
    }

    public Ubezp(Integer ubeSerial, BigDecimal ubeProcent, short ubeMiesiacOd, short ubeRokOd, BigDecimal ubeProcEmer, BigDecimal ubeProcRent, BigDecimal ubeProcChor, BigDecimal ubeProcWyp, BigDecimal ubeProcZdro, BigDecimal ubeProcFp, BigDecimal ubeProcFgsp, BigDecimal ubeMinPdst, BigDecimal ubeDodProc1, BigDecimal ubeDodProc2, BigDecimal ubeDodProc3, BigDecimal ubeDodKwota1, BigDecimal ubeDodKwota2, BigDecimal ubeDodKwota3, Character ubeDodChar1, Character ubeDodChar2, Character ubeDodChar3, Character ubeOgrZdrowotne, Character ubeOgrEmerRent) {
        this.ubeSerial = ubeSerial;
        this.ubeProcent = ubeProcent;
        this.ubeMiesiacOd = ubeMiesiacOd;
        this.ubeRokOd = ubeRokOd;
        this.ubeProcEmer = ubeProcEmer;
        this.ubeProcRent = ubeProcRent;
        this.ubeProcChor = ubeProcChor;
        this.ubeProcWyp = ubeProcWyp;
        this.ubeProcZdro = ubeProcZdro;
        this.ubeProcFp = ubeProcFp;
        this.ubeProcFgsp = ubeProcFgsp;
        this.ubeMinPdst = ubeMinPdst;
        this.ubeDodProc1 = ubeDodProc1;
        this.ubeDodProc2 = ubeDodProc2;
        this.ubeDodProc3 = ubeDodProc3;
        this.ubeDodKwota1 = ubeDodKwota1;
        this.ubeDodKwota2 = ubeDodKwota2;
        this.ubeDodKwota3 = ubeDodKwota3;
        this.ubeDodChar1 = ubeDodChar1;
        this.ubeDodChar2 = ubeDodChar2;
        this.ubeDodChar3 = ubeDodChar3;
        this.ubeOgrZdrowotne = ubeOgrZdrowotne;
        this.ubeOgrEmerRent = ubeOgrEmerRent;
    }

    public Integer getUbeSerial() {
        return ubeSerial;
    }

    public void setUbeSerial(Integer ubeSerial) {
        this.ubeSerial = ubeSerial;
    }

    public BigDecimal getUbeProcent() {
        return ubeProcent;
    }

    public void setUbeProcent(BigDecimal ubeProcent) {
        this.ubeProcent = ubeProcent;
    }

    public String getUbeOpis() {
        return ubeOpis;
    }

    public void setUbeOpis(String ubeOpis) {
        this.ubeOpis = ubeOpis;
    }

    public Short getUbeKolejnosc() {
        return ubeKolejnosc;
    }

    public void setUbeKolejnosc(Short ubeKolejnosc) {
        this.ubeKolejnosc = ubeKolejnosc;
    }

    public short getUbeMiesiacOd() {
        return ubeMiesiacOd;
    }

    public void setUbeMiesiacOd(short ubeMiesiacOd) {
        this.ubeMiesiacOd = ubeMiesiacOd;
    }

    public short getUbeRokOd() {
        return ubeRokOd;
    }

    public void setUbeRokOd(short ubeRokOd) {
        this.ubeRokOd = ubeRokOd;
    }

    public Short getUbeMiesiacDo() {
        return ubeMiesiacDo;
    }

    public void setUbeMiesiacDo(Short ubeMiesiacDo) {
        this.ubeMiesiacDo = ubeMiesiacDo;
    }

    public Short getUbeRokDo() {
        return ubeRokDo;
    }

    public void setUbeRokDo(Short ubeRokDo) {
        this.ubeRokDo = ubeRokDo;
    }

    public BigDecimal getUbeProcEmer() {
        return ubeProcEmer;
    }

    public void setUbeProcEmer(BigDecimal ubeProcEmer) {
        this.ubeProcEmer = ubeProcEmer;
    }

    public BigDecimal getUbeProcRent() {
        return ubeProcRent;
    }

    public void setUbeProcRent(BigDecimal ubeProcRent) {
        this.ubeProcRent = ubeProcRent;
    }

    public BigDecimal getUbeProcChor() {
        return ubeProcChor;
    }

    public void setUbeProcChor(BigDecimal ubeProcChor) {
        this.ubeProcChor = ubeProcChor;
    }

    public BigDecimal getUbeProcWyp() {
        return ubeProcWyp;
    }

    public void setUbeProcWyp(BigDecimal ubeProcWyp) {
        this.ubeProcWyp = ubeProcWyp;
    }

    public BigDecimal getUbeProcZdro() {
        return ubeProcZdro;
    }

    public void setUbeProcZdro(BigDecimal ubeProcZdro) {
        this.ubeProcZdro = ubeProcZdro;
    }

    public BigDecimal getUbeProcFp() {
        return ubeProcFp;
    }

    public void setUbeProcFp(BigDecimal ubeProcFp) {
        this.ubeProcFp = ubeProcFp;
    }

    public BigDecimal getUbeProcFgsp() {
        return ubeProcFgsp;
    }

    public void setUbeProcFgsp(BigDecimal ubeProcFgsp) {
        this.ubeProcFgsp = ubeProcFgsp;
    }

    public BigDecimal getUbeMinPdst() {
        return ubeMinPdst;
    }

    public void setUbeMinPdst(BigDecimal ubeMinPdst) {
        this.ubeMinPdst = ubeMinPdst;
    }

    public BigDecimal getUbeDodProc1() {
        return ubeDodProc1;
    }

    public void setUbeDodProc1(BigDecimal ubeDodProc1) {
        this.ubeDodProc1 = ubeDodProc1;
    }

    public BigDecimal getUbeDodProc2() {
        return ubeDodProc2;
    }

    public void setUbeDodProc2(BigDecimal ubeDodProc2) {
        this.ubeDodProc2 = ubeDodProc2;
    }

    public BigDecimal getUbeDodProc3() {
        return ubeDodProc3;
    }

    public void setUbeDodProc3(BigDecimal ubeDodProc3) {
        this.ubeDodProc3 = ubeDodProc3;
    }

    public BigDecimal getUbeDodKwota1() {
        return ubeDodKwota1;
    }

    public void setUbeDodKwota1(BigDecimal ubeDodKwota1) {
        this.ubeDodKwota1 = ubeDodKwota1;
    }

    public BigDecimal getUbeDodKwota2() {
        return ubeDodKwota2;
    }

    public void setUbeDodKwota2(BigDecimal ubeDodKwota2) {
        this.ubeDodKwota2 = ubeDodKwota2;
    }

    public BigDecimal getUbeDodKwota3() {
        return ubeDodKwota3;
    }

    public void setUbeDodKwota3(BigDecimal ubeDodKwota3) {
        this.ubeDodKwota3 = ubeDodKwota3;
    }

    public Character getUbeDodChar1() {
        return ubeDodChar1;
    }

    public void setUbeDodChar1(Character ubeDodChar1) {
        this.ubeDodChar1 = ubeDodChar1;
    }

    public Character getUbeDodChar2() {
        return ubeDodChar2;
    }

    public void setUbeDodChar2(Character ubeDodChar2) {
        this.ubeDodChar2 = ubeDodChar2;
    }

    public Character getUbeDodChar3() {
        return ubeDodChar3;
    }

    public void setUbeDodChar3(Character ubeDodChar3) {
        this.ubeDodChar3 = ubeDodChar3;
    }

    public Character getUbeOgrZdrowotne() {
        return ubeOgrZdrowotne;
    }

    public void setUbeOgrZdrowotne(Character ubeOgrZdrowotne) {
        this.ubeOgrZdrowotne = ubeOgrZdrowotne;
    }

    public Character getUbeOgrEmerRent() {
        return ubeOgrEmerRent;
    }

    public void setUbeOgrEmerRent(Character ubeOgrEmerRent) {
        this.ubeOgrEmerRent = ubeOgrEmerRent;
    }

    public Character getUbeDodChar4() {
        return ubeDodChar4;
    }

    public void setUbeDodChar4(Character ubeDodChar4) {
        this.ubeDodChar4 = ubeDodChar4;
    }

    public Character getUbeDodChar5() {
        return ubeDodChar5;
    }

    public void setUbeDodChar5(Character ubeDodChar5) {
        this.ubeDodChar5 = ubeDodChar5;
    }

    public Character getUbeDodChar6() {
        return ubeDodChar6;
    }

    public void setUbeDodChar6(Character ubeDodChar6) {
        this.ubeDodChar6 = ubeDodChar6;
    }

    public BigDecimal getUbeDodKwota4() {
        return ubeDodKwota4;
    }

    public void setUbeDodKwota4(BigDecimal ubeDodKwota4) {
        this.ubeDodKwota4 = ubeDodKwota4;
    }

    public BigDecimal getUbeDodKwota5() {
        return ubeDodKwota5;
    }

    public void setUbeDodKwota5(BigDecimal ubeDodKwota5) {
        this.ubeDodKwota5 = ubeDodKwota5;
    }

    public BigDecimal getUbeDodKwota6() {
        return ubeDodKwota6;
    }

    public void setUbeDodKwota6(BigDecimal ubeDodKwota6) {
        this.ubeDodKwota6 = ubeDodKwota6;
    }

    public String getUbeDodVchar1() {
        return ubeDodVchar1;
    }

    public void setUbeDodVchar1(String ubeDodVchar1) {
        this.ubeDodVchar1 = ubeDodVchar1;
    }

    public String getUbeDodVchar2() {
        return ubeDodVchar2;
    }

    public void setUbeDodVchar2(String ubeDodVchar2) {
        this.ubeDodVchar2 = ubeDodVchar2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ubeSerial != null ? ubeSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubezp)) {
            return false;
        }
        Ubezp other = (Ubezp) object;
        if ((this.ubeSerial == null && other.ubeSerial != null) || (this.ubeSerial != null && !this.ubeSerial.equals(other.ubeSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Ubezp[ ubeSerial=" + ubeSerial + " ]";
    }
    
}
