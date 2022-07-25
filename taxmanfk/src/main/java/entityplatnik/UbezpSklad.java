/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "UBEZP_SKLAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpSklad.findAll", query = "SELECT u FROM UbezpSklad u"),
    @NamedQuery(name = "UbezpSklad.findById", query = "SELECT u FROM UbezpSklad u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpSklad.findByIdDokument", query = "SELECT u FROM UbezpSklad u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpSklad.findByIdPlatnik", query = "SELECT u FROM UbezpSklad u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpSklad.findByIdUbezpieczony", query = "SELECT u FROM UbezpSklad u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpSklad.findByIdDokZus", query = "SELECT u FROM UbezpSklad u WHERE u.idDokZus = :idDokZus"),
    @NamedQuery(name = "UbezpSklad.findByNrPoddokumentu", query = "SELECT u FROM UbezpSklad u WHERE u.nrPoddokumentu = :nrPoddokumentu"),
    @NamedQuery(name = "UbezpSklad.findByIdPlZus", query = "SELECT u FROM UbezpSklad u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpSklad.findByIdUbZus", query = "SELECT u FROM UbezpSklad u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpSklad.findByTyp", query = "SELECT u FROM UbezpSklad u WHERE u.typ = :typ"),
    @NamedQuery(name = "UbezpSklad.findByIiiA1", query = "SELECT u FROM UbezpSklad u WHERE u.iiiA1 = :iiiA1"),
    @NamedQuery(name = "UbezpSklad.findByIiiA2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiA2 = :iiiA2"),
    @NamedQuery(name = "UbezpSklad.findByIiiA3", query = "SELECT u FROM UbezpSklad u WHERE u.iiiA3 = :iiiA3"),
    @NamedQuery(name = "UbezpSklad.findByIiiA4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiA4 = :iiiA4"),
    @NamedQuery(name = "UbezpSklad.findByIiiB11", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB11 = :iiiB11"),
    @NamedQuery(name = "UbezpSklad.findByIiiB12", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB12 = :iiiB12"),
    @NamedQuery(name = "UbezpSklad.findByIiiB13", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB13 = :iiiB13"),
    @NamedQuery(name = "UbezpSklad.findByIiiB2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB2 = :iiiB2"),
    @NamedQuery(name = "UbezpSklad.findByIiiB31", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB31 = :iiiB31"),
    @NamedQuery(name = "UbezpSklad.findByIiiB32", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB32 = :iiiB32"),
    @NamedQuery(name = "UbezpSklad.findByIiiB4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB4 = :iiiB4"),
    @NamedQuery(name = "UbezpSklad.findByIiiB5", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB5 = :iiiB5"),
    @NamedQuery(name = "UbezpSklad.findByIiiB6", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB6 = :iiiB6"),
    @NamedQuery(name = "UbezpSklad.findByIiiB7", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB7 = :iiiB7"),
    @NamedQuery(name = "UbezpSklad.findByIiiB8", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB8 = :iiiB8"),
    @NamedQuery(name = "UbezpSklad.findByIiiB9", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB9 = :iiiB9"),
    @NamedQuery(name = "UbezpSklad.findByIiiB10", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB10 = :iiiB10"),
    @NamedQuery(name = "UbezpSklad.findByIiiB111", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB111 = :iiiB111"),
    @NamedQuery(name = "UbezpSklad.findByIiiB121", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB121 = :iiiB121"),
    @NamedQuery(name = "UbezpSklad.findByIiiB131", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB131 = :iiiB131"),
    @NamedQuery(name = "UbezpSklad.findByIiiB14", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB14 = :iiiB14"),
    @NamedQuery(name = "UbezpSklad.findByIiiB15", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB15 = :iiiB15"),
    @NamedQuery(name = "UbezpSklad.findByIiiB16", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB16 = :iiiB16"),
    @NamedQuery(name = "UbezpSklad.findByIiiB17", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB17 = :iiiB17"),
    @NamedQuery(name = "UbezpSklad.findByIiiB18", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB18 = :iiiB18"),
    @NamedQuery(name = "UbezpSklad.findByIiiB19", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB19 = :iiiB19"),
    @NamedQuery(name = "UbezpSklad.findByIiiB20", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB20 = :iiiB20"),
    @NamedQuery(name = "UbezpSklad.findByIiiB21", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB21 = :iiiB21"),
    @NamedQuery(name = "UbezpSklad.findByIiiB22", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB22 = :iiiB22"),
    @NamedQuery(name = "UbezpSklad.findByIiiB23", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB23 = :iiiB23"),
    @NamedQuery(name = "UbezpSklad.findByIiiB24", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB24 = :iiiB24"),
    @NamedQuery(name = "UbezpSklad.findByIiiB25", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB25 = :iiiB25"),
    @NamedQuery(name = "UbezpSklad.findByIiiB26", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB26 = :iiiB26"),
    @NamedQuery(name = "UbezpSklad.findByIiiB27", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB27 = :iiiB27"),
    @NamedQuery(name = "UbezpSklad.findByIiiB28", query = "SELECT u FROM UbezpSklad u WHERE u.iiiB28 = :iiiB28"),
    @NamedQuery(name = "UbezpSklad.findByIiiC1", query = "SELECT u FROM UbezpSklad u WHERE u.iiiC1 = :iiiC1"),
    @NamedQuery(name = "UbezpSklad.findByIiiC2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiC2 = :iiiC2"),
    @NamedQuery(name = "UbezpSklad.findByIiiC3", query = "SELECT u FROM UbezpSklad u WHERE u.iiiC3 = :iiiC3"),
    @NamedQuery(name = "UbezpSklad.findByIiiC4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiC4 = :iiiC4"),
    @NamedQuery(name = "UbezpSklad.findByIiiC5", query = "SELECT u FROM UbezpSklad u WHERE u.iiiC5 = :iiiC5"),
    @NamedQuery(name = "UbezpSklad.findByIiiD1", query = "SELECT u FROM UbezpSklad u WHERE u.iiiD1 = :iiiD1"),
    @NamedQuery(name = "UbezpSklad.findByIiiD2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiD2 = :iiiD2"),
    @NamedQuery(name = "UbezpSklad.findByIiiD3", query = "SELECT u FROM UbezpSklad u WHERE u.iiiD3 = :iiiD3"),
    @NamedQuery(name = "UbezpSklad.findByIiiD4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiD4 = :iiiD4"),
    @NamedQuery(name = "UbezpSklad.findByInserttmp", query = "SELECT u FROM UbezpSklad u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpSklad.findByIiiPpk", query = "SELECT u FROM UbezpSklad u WHERE u.iiiPpk = :iiiPpk"),
    @NamedQuery(name = "UbezpSklad.findByIiiE1", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE1 = :iiiE1"),
    @NamedQuery(name = "UbezpSklad.findByIiiE2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE2 = :iiiE2"),
    @NamedQuery(name = "UbezpSklad.findByIiiE3", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE3 = :iiiE3"),
    @NamedQuery(name = "UbezpSklad.findByIiiE4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE4 = :iiiE4"),
    @NamedQuery(name = "UbezpSklad.findByIiiE5", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE5 = :iiiE5"),
    @NamedQuery(name = "UbezpSklad.findByIiiE6", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE6 = :iiiE6"),
    @NamedQuery(name = "UbezpSklad.findByIiiE7", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE7 = :iiiE7"),
    @NamedQuery(name = "UbezpSklad.findByIiiE8", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE8 = :iiiE8"),
    @NamedQuery(name = "UbezpSklad.findByIiiE9", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE9 = :iiiE9"),
    @NamedQuery(name = "UbezpSklad.findByIiiE10", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE10 = :iiiE10"),
    @NamedQuery(name = "UbezpSklad.findByIiiE11", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE11 = :iiiE11"),
    @NamedQuery(name = "UbezpSklad.findByIiiE12", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE12 = :iiiE12"),
    @NamedQuery(name = "UbezpSklad.findByIiiE13", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE13 = :iiiE13"),
    @NamedQuery(name = "UbezpSklad.findByIiiE14", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE14 = :iiiE14"),
    @NamedQuery(name = "UbezpSklad.findByIiiE15", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE15 = :iiiE15"),
    @NamedQuery(name = "UbezpSklad.findByIiiE16", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE16 = :iiiE16"),
    @NamedQuery(name = "UbezpSklad.findByIiiE17", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE17 = :iiiE17"),
    @NamedQuery(name = "UbezpSklad.findByIiiE18", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE18 = :iiiE18"),
    @NamedQuery(name = "UbezpSklad.findByIiiE19", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE19 = :iiiE19"),
    @NamedQuery(name = "UbezpSklad.findByIiiE20", query = "SELECT u FROM UbezpSklad u WHERE u.iiiE20 = :iiiE20"),
    @NamedQuery(name = "UbezpSklad.findByIiiF1", query = "SELECT u FROM UbezpSklad u WHERE u.iiiF1 = :iiiF1"),
    @NamedQuery(name = "UbezpSklad.findByIiiF2", query = "SELECT u FROM UbezpSklad u WHERE u.iiiF2 = :iiiF2"),
    @NamedQuery(name = "UbezpSklad.findByIiiF3", query = "SELECT u FROM UbezpSklad u WHERE u.iiiF3 = :iiiF3"),
    @NamedQuery(name = "UbezpSklad.findByIiiF4", query = "SELECT u FROM UbezpSklad u WHERE u.iiiF4 = :iiiF4"),
    @NamedQuery(name = "UbezpSklad.findByIiiF5", query = "SELECT u FROM UbezpSklad u WHERE u.iiiF5 = :iiiF5")})
public class UbezpSklad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_DOK_ZUS")
    private Integer idDokZus;
    @Column(name = "NR_PODDOKUMENTU")
    private Integer nrPoddokumentu;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP", nullable = false)
    private int typ;
    @Size(max = 31)
    @Column(name = "III_A_1", length = 31)
    private String iiiA1;
    @Size(max = 22)
    @Column(name = "III_A_2", length = 22)
    private String iiiA2;
    @Column(name = "III_A_3")
    private Character iiiA3;
    @Size(max = 11)
    @Column(name = "III_A_4", length = 11)
    private String iiiA4;
    @Size(max = 4)
    @Column(name = "III_B_1_1", length = 4)
    private String iiiB11;
    @Column(name = "III_B_1_2")
    private Character iiiB12;
    @Column(name = "III_B_1_3")
    private Character iiiB13;
    @Column(name = "III_B_2")
    private Character iiiB2;
    @Column(name = "III_B_3_1")
    private Short iiiB31;
    @Column(name = "III_B_3_2")
    private Short iiiB32;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_4", precision = 15, scale = 2)
    private BigDecimal iiiB4;
    @Column(name = "III_B_5", precision = 15, scale = 2)
    private BigDecimal iiiB5;
    @Column(name = "III_B_6", precision = 15, scale = 2)
    private BigDecimal iiiB6;
    @Column(name = "III_B_7", precision = 15, scale = 2)
    private BigDecimal iiiB7;
    @Column(name = "III_B_8", precision = 15, scale = 2)
    private BigDecimal iiiB8;
    @Column(name = "III_B_9", precision = 15, scale = 2)
    private BigDecimal iiiB9;
    @Column(name = "III_B_10", precision = 15, scale = 2)
    private BigDecimal iiiB10;
    @Column(name = "III_B_11", precision = 15, scale = 2)
    private BigDecimal iiiB111;
    @Column(name = "III_B_12", precision = 15, scale = 2)
    private BigDecimal iiiB121;
    @Column(name = "III_B_13", precision = 15, scale = 2)
    private BigDecimal iiiB131;
    @Column(name = "III_B_14", precision = 15, scale = 2)
    private BigDecimal iiiB14;
    @Column(name = "III_B_15", precision = 15, scale = 2)
    private BigDecimal iiiB15;
    @Column(name = "III_B_16", precision = 15, scale = 2)
    private BigDecimal iiiB16;
    @Column(name = "III_B_17", precision = 15, scale = 2)
    private BigDecimal iiiB17;
    @Column(name = "III_B_18", precision = 15, scale = 2)
    private BigDecimal iiiB18;
    @Column(name = "III_B_19", precision = 15, scale = 2)
    private BigDecimal iiiB19;
    @Column(name = "III_B_20", precision = 15, scale = 2)
    private BigDecimal iiiB20;
    @Column(name = "III_B_21", precision = 15, scale = 2)
    private BigDecimal iiiB21;
    @Column(name = "III_B_22", precision = 15, scale = 2)
    private BigDecimal iiiB22;
    @Column(name = "III_B_23", precision = 15, scale = 2)
    private BigDecimal iiiB23;
    @Column(name = "III_B_24", precision = 15, scale = 2)
    private BigDecimal iiiB24;
    @Column(name = "III_B_25", precision = 15, scale = 2)
    private BigDecimal iiiB25;
    @Column(name = "III_B_26", precision = 15, scale = 2)
    private BigDecimal iiiB26;
    @Column(name = "III_B_27", precision = 15, scale = 2)
    private BigDecimal iiiB27;
    @Column(name = "III_B_28", precision = 15, scale = 2)
    private BigDecimal iiiB28;
    @Column(name = "III_C_1", precision = 15, scale = 2)
    private BigDecimal iiiC1;
    @Column(name = "III_C_2", precision = 15, scale = 2)
    private BigDecimal iiiC2;
    @Column(name = "III_C_3", precision = 15, scale = 2)
    private BigDecimal iiiC3;
    @Column(name = "III_C_4", precision = 15, scale = 2)
    private BigDecimal iiiC4;
    @Column(name = "III_C_5", precision = 15, scale = 2)
    private BigDecimal iiiC5;
    @Column(name = "III_D_1", precision = 7, scale = 2)
    private BigDecimal iiiD1;
    @Column(name = "III_D_2", precision = 7, scale = 2)
    private BigDecimal iiiD2;
    @Column(name = "III_D_3", precision = 7, scale = 2)
    private BigDecimal iiiD3;
    @Column(name = "III_D_4", precision = 8, scale = 2)
    private BigDecimal iiiD4;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "III_PPK", precision = 7, scale = 2)
    private BigDecimal iiiPpk;
    @Column(name = "III_E_1")
    private Character iiiE1;
    @Column(name = "III_E_2", precision = 10, scale = 2)
    private BigDecimal iiiE2;
    @Column(name = "III_E_3", precision = 10, scale = 2)
    private BigDecimal iiiE3;
    @Column(name = "III_E_4", precision = 10, scale = 2)
    private BigDecimal iiiE4;
    @Column(name = "III_E_5")
    private Character iiiE5;
    @Column(name = "III_E_6", precision = 10, scale = 2)
    private BigDecimal iiiE6;
    @Column(name = "III_E_7", precision = 10, scale = 2)
    private BigDecimal iiiE7;
    @Column(name = "III_E_8", precision = 10, scale = 2)
    private BigDecimal iiiE8;
    @Column(name = "III_E_9")
    private Character iiiE9;
    @Column(name = "III_E_10", precision = 10, scale = 2)
    private BigDecimal iiiE10;
    @Column(name = "III_E_11", precision = 10, scale = 2)
    private BigDecimal iiiE11;
    @Column(name = "III_E_12")
    private Character iiiE12;
    @Column(name = "III_E_13", precision = 10, scale = 2)
    private BigDecimal iiiE13;
    @Column(name = "III_E_14")
    private Character iiiE14;
    @Column(name = "III_E_15", precision = 10, scale = 2)
    private BigDecimal iiiE15;
    @Column(name = "III_E_16", precision = 10, scale = 2)
    private BigDecimal iiiE16;
    @Column(name = "III_E_17", precision = 10, scale = 2)
    private BigDecimal iiiE17;
    @Column(name = "III_E_18")
    private Character iiiE18;
    @Column(name = "III_E_19", precision = 10, scale = 2)
    private BigDecimal iiiE19;
    @Column(name = "III_E_20", precision = 10, scale = 2)
    private BigDecimal iiiE20;
    @Column(name = "III_F_1")
    private Short iiiF1;
    @Column(name = "III_F_2", precision = 11, scale = 2)
    private BigDecimal iiiF2;
    @Column(name = "III_F_3", precision = 11, scale = 2)
    private BigDecimal iiiF3;
    @Column(name = "III_F_4", precision = 11, scale = 2)
    private BigDecimal iiiF4;
    @Column(name = "III_F_5", precision = 11, scale = 2)
    private BigDecimal iiiF5;

    public UbezpSklad() {
    }

    public UbezpSklad(Integer id) {
        this.id = id;
    }

    public UbezpSklad(Integer id, int typ) {
        this.id = id;
        this.typ = typ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdDokZus() {
        return idDokZus;
    }

    public void setIdDokZus(Integer idDokZus) {
        this.idDokZus = idDokZus;
    }

    public Integer getNrPoddokumentu() {
        return nrPoddokumentu;
    }

    public void setNrPoddokumentu(Integer nrPoddokumentu) {
        this.nrPoddokumentu = nrPoddokumentu;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public String getIiiA1() {
        return iiiA1;
    }

    public void setIiiA1(String iiiA1) {
        this.iiiA1 = iiiA1;
    }

    public String getIiiA2() {
        return iiiA2;
    }

    public void setIiiA2(String iiiA2) {
        this.iiiA2 = iiiA2;
    }

    public Character getIiiA3() {
        return iiiA3;
    }

    public void setIiiA3(Character iiiA3) {
        this.iiiA3 = iiiA3;
    }

    public String getIiiA4() {
        return iiiA4;
    }

    public void setIiiA4(String iiiA4) {
        this.iiiA4 = iiiA4;
    }

    public String getIiiB11() {
        return iiiB11;
    }

    public void setIiiB11(String iiiB11) {
        this.iiiB11 = iiiB11;
    }

    public Character getIiiB12() {
        return iiiB12;
    }

    public void setIiiB12(Character iiiB12) {
        this.iiiB12 = iiiB12;
    }

    public Character getIiiB13() {
        return iiiB13;
    }

    public void setIiiB13(Character iiiB13) {
        this.iiiB13 = iiiB13;
    }

    public Character getIiiB2() {
        return iiiB2;
    }

    public void setIiiB2(Character iiiB2) {
        this.iiiB2 = iiiB2;
    }

    public Short getIiiB31() {
        return iiiB31;
    }

    public void setIiiB31(Short iiiB31) {
        this.iiiB31 = iiiB31;
    }

    public Short getIiiB32() {
        return iiiB32;
    }

    public void setIiiB32(Short iiiB32) {
        this.iiiB32 = iiiB32;
    }

    public BigDecimal getIiiB4() {
        return iiiB4;
    }

    public void setIiiB4(BigDecimal iiiB4) {
        this.iiiB4 = iiiB4;
    }

    public BigDecimal getIiiB5() {
        return iiiB5;
    }

    public void setIiiB5(BigDecimal iiiB5) {
        this.iiiB5 = iiiB5;
    }

    public BigDecimal getIiiB6() {
        return iiiB6;
    }

    public void setIiiB6(BigDecimal iiiB6) {
        this.iiiB6 = iiiB6;
    }

    public BigDecimal getIiiB7() {
        return iiiB7;
    }

    public void setIiiB7(BigDecimal iiiB7) {
        this.iiiB7 = iiiB7;
    }

    public BigDecimal getIiiB8() {
        return iiiB8;
    }

    public void setIiiB8(BigDecimal iiiB8) {
        this.iiiB8 = iiiB8;
    }

    public BigDecimal getIiiB9() {
        return iiiB9;
    }

    public void setIiiB9(BigDecimal iiiB9) {
        this.iiiB9 = iiiB9;
    }

    public BigDecimal getIiiB10() {
        return iiiB10;
    }

    public void setIiiB10(BigDecimal iiiB10) {
        this.iiiB10 = iiiB10;
    }

    public BigDecimal getIiiB111() {
        return iiiB111;
    }

    public void setIiiB111(BigDecimal iiiB111) {
        this.iiiB111 = iiiB111;
    }

    public BigDecimal getIiiB121() {
        return iiiB121;
    }

    public void setIiiB121(BigDecimal iiiB121) {
        this.iiiB121 = iiiB121;
    }

    public BigDecimal getIiiB131() {
        return iiiB131;
    }

    public void setIiiB131(BigDecimal iiiB131) {
        this.iiiB131 = iiiB131;
    }

    public BigDecimal getIiiB14() {
        return iiiB14;
    }

    public void setIiiB14(BigDecimal iiiB14) {
        this.iiiB14 = iiiB14;
    }

    public BigDecimal getIiiB15() {
        return iiiB15;
    }

    public void setIiiB15(BigDecimal iiiB15) {
        this.iiiB15 = iiiB15;
    }

    public BigDecimal getIiiB16() {
        return iiiB16;
    }

    public void setIiiB16(BigDecimal iiiB16) {
        this.iiiB16 = iiiB16;
    }

    public BigDecimal getIiiB17() {
        return iiiB17;
    }

    public void setIiiB17(BigDecimal iiiB17) {
        this.iiiB17 = iiiB17;
    }

    public BigDecimal getIiiB18() {
        return iiiB18;
    }

    public void setIiiB18(BigDecimal iiiB18) {
        this.iiiB18 = iiiB18;
    }

    public BigDecimal getIiiB19() {
        return iiiB19;
    }

    public void setIiiB19(BigDecimal iiiB19) {
        this.iiiB19 = iiiB19;
    }

    public BigDecimal getIiiB20() {
        return iiiB20;
    }

    public void setIiiB20(BigDecimal iiiB20) {
        this.iiiB20 = iiiB20;
    }

    public BigDecimal getIiiB21() {
        return iiiB21;
    }

    public void setIiiB21(BigDecimal iiiB21) {
        this.iiiB21 = iiiB21;
    }

    public BigDecimal getIiiB22() {
        return iiiB22;
    }

    public void setIiiB22(BigDecimal iiiB22) {
        this.iiiB22 = iiiB22;
    }

    public BigDecimal getIiiB23() {
        return iiiB23;
    }

    public void setIiiB23(BigDecimal iiiB23) {
        this.iiiB23 = iiiB23;
    }

    public BigDecimal getIiiB24() {
        return iiiB24;
    }

    public void setIiiB24(BigDecimal iiiB24) {
        this.iiiB24 = iiiB24;
    }

    public BigDecimal getIiiB25() {
        return iiiB25;
    }

    public void setIiiB25(BigDecimal iiiB25) {
        this.iiiB25 = iiiB25;
    }

    public BigDecimal getIiiB26() {
        return iiiB26;
    }

    public void setIiiB26(BigDecimal iiiB26) {
        this.iiiB26 = iiiB26;
    }

    public BigDecimal getIiiB27() {
        return iiiB27;
    }

    public void setIiiB27(BigDecimal iiiB27) {
        this.iiiB27 = iiiB27;
    }

    public BigDecimal getIiiB28() {
        return iiiB28;
    }

    public void setIiiB28(BigDecimal iiiB28) {
        this.iiiB28 = iiiB28;
    }

    public BigDecimal getIiiC1() {
        return iiiC1;
    }

    public void setIiiC1(BigDecimal iiiC1) {
        this.iiiC1 = iiiC1;
    }

    public BigDecimal getIiiC2() {
        return iiiC2;
    }

    public void setIiiC2(BigDecimal iiiC2) {
        this.iiiC2 = iiiC2;
    }

    public BigDecimal getIiiC3() {
        return iiiC3;
    }

    public void setIiiC3(BigDecimal iiiC3) {
        this.iiiC3 = iiiC3;
    }

    public BigDecimal getIiiC4() {
        return iiiC4;
    }

    public void setIiiC4(BigDecimal iiiC4) {
        this.iiiC4 = iiiC4;
    }

    public BigDecimal getIiiC5() {
        return iiiC5;
    }

    public void setIiiC5(BigDecimal iiiC5) {
        this.iiiC5 = iiiC5;
    }

    public BigDecimal getIiiD1() {
        return iiiD1;
    }

    public void setIiiD1(BigDecimal iiiD1) {
        this.iiiD1 = iiiD1;
    }

    public BigDecimal getIiiD2() {
        return iiiD2;
    }

    public void setIiiD2(BigDecimal iiiD2) {
        this.iiiD2 = iiiD2;
    }

    public BigDecimal getIiiD3() {
        return iiiD3;
    }

    public void setIiiD3(BigDecimal iiiD3) {
        this.iiiD3 = iiiD3;
    }

    public BigDecimal getIiiD4() {
        return iiiD4;
    }

    public void setIiiD4(BigDecimal iiiD4) {
        this.iiiD4 = iiiD4;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public BigDecimal getIiiPpk() {
        return iiiPpk;
    }

    public void setIiiPpk(BigDecimal iiiPpk) {
        this.iiiPpk = iiiPpk;
    }

    public Character getIiiE1() {
        return iiiE1;
    }

    public void setIiiE1(Character iiiE1) {
        this.iiiE1 = iiiE1;
    }

    public BigDecimal getIiiE2() {
        return iiiE2;
    }

    public void setIiiE2(BigDecimal iiiE2) {
        this.iiiE2 = iiiE2;
    }

    public BigDecimal getIiiE3() {
        return iiiE3;
    }

    public void setIiiE3(BigDecimal iiiE3) {
        this.iiiE3 = iiiE3;
    }

    public BigDecimal getIiiE4() {
        return iiiE4;
    }

    public void setIiiE4(BigDecimal iiiE4) {
        this.iiiE4 = iiiE4;
    }

    public Character getIiiE5() {
        return iiiE5;
    }

    public void setIiiE5(Character iiiE5) {
        this.iiiE5 = iiiE5;
    }

    public BigDecimal getIiiE6() {
        return iiiE6;
    }

    public void setIiiE6(BigDecimal iiiE6) {
        this.iiiE6 = iiiE6;
    }

    public BigDecimal getIiiE7() {
        return iiiE7;
    }

    public void setIiiE7(BigDecimal iiiE7) {
        this.iiiE7 = iiiE7;
    }

    public BigDecimal getIiiE8() {
        return iiiE8;
    }

    public void setIiiE8(BigDecimal iiiE8) {
        this.iiiE8 = iiiE8;
    }

    public Character getIiiE9() {
        return iiiE9;
    }

    public void setIiiE9(Character iiiE9) {
        this.iiiE9 = iiiE9;
    }

    public BigDecimal getIiiE10() {
        return iiiE10;
    }

    public void setIiiE10(BigDecimal iiiE10) {
        this.iiiE10 = iiiE10;
    }

    public BigDecimal getIiiE11() {
        return iiiE11;
    }

    public void setIiiE11(BigDecimal iiiE11) {
        this.iiiE11 = iiiE11;
    }

    public Character getIiiE12() {
        return iiiE12;
    }

    public void setIiiE12(Character iiiE12) {
        this.iiiE12 = iiiE12;
    }

    public BigDecimal getIiiE13() {
        return iiiE13;
    }

    public void setIiiE13(BigDecimal iiiE13) {
        this.iiiE13 = iiiE13;
    }

    public Character getIiiE14() {
        return iiiE14;
    }

    public void setIiiE14(Character iiiE14) {
        this.iiiE14 = iiiE14;
    }

    public BigDecimal getIiiE15() {
        return iiiE15;
    }

    public void setIiiE15(BigDecimal iiiE15) {
        this.iiiE15 = iiiE15;
    }

    public BigDecimal getIiiE16() {
        return iiiE16;
    }

    public void setIiiE16(BigDecimal iiiE16) {
        this.iiiE16 = iiiE16;
    }

    public BigDecimal getIiiE17() {
        return iiiE17;
    }

    public void setIiiE17(BigDecimal iiiE17) {
        this.iiiE17 = iiiE17;
    }

    public Character getIiiE18() {
        return iiiE18;
    }

    public void setIiiE18(Character iiiE18) {
        this.iiiE18 = iiiE18;
    }

    public BigDecimal getIiiE19() {
        return iiiE19;
    }

    public void setIiiE19(BigDecimal iiiE19) {
        this.iiiE19 = iiiE19;
    }

    public BigDecimal getIiiE20() {
        return iiiE20;
    }

    public void setIiiE20(BigDecimal iiiE20) {
        this.iiiE20 = iiiE20;
    }

    public Short getIiiF1() {
        return iiiF1;
    }

    public void setIiiF1(Short iiiF1) {
        this.iiiF1 = iiiF1;
    }

    public BigDecimal getIiiF2() {
        return iiiF2;
    }

    public void setIiiF2(BigDecimal iiiF2) {
        this.iiiF2 = iiiF2;
    }

    public BigDecimal getIiiF3() {
        return iiiF3;
    }

    public void setIiiF3(BigDecimal iiiF3) {
        this.iiiF3 = iiiF3;
    }

    public BigDecimal getIiiF4() {
        return iiiF4;
    }

    public void setIiiF4(BigDecimal iiiF4) {
        this.iiiF4 = iiiF4;
    }

    public BigDecimal getIiiF5() {
        return iiiF5;
    }

    public void setIiiF5(BigDecimal iiiF5) {
        this.iiiF5 = iiiF5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpSklad)) {
            return false;
        }
        UbezpSklad other = (UbezpSklad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpSklad[ id=" + id + " ]";
    }
    
}
