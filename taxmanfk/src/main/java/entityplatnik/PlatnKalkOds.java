/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "PLATN_KALK_ODS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnKalkOds.findAll", query = "SELECT p FROM PlatnKalkOds p"),
    @NamedQuery(name = "PlatnKalkOds.findById", query = "SELECT p FROM PlatnKalkOds p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnKalkOds.findByIdPlatnik", query = "SELECT p FROM PlatnKalkOds p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnKalkOds.findByIdPlZus", query = "SELECT p FROM PlatnKalkOds p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnKalkOds.findByZakres", query = "SELECT p FROM PlatnKalkOds p WHERE p.zakres = :zakres"),
    @NamedQuery(name = "PlatnKalkOds.findByDataBazowaOds", query = "SELECT p FROM PlatnKalkOds p WHERE p.dataBazowaOds = :dataBazowaOds"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien01", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien01 = :odsDzien01"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien02", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien02 = :odsDzien02"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien03", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien03 = :odsDzien03"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien04", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien04 = :odsDzien04"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien05", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien05 = :odsDzien05"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien06", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien06 = :odsDzien06"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien07", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien07 = :odsDzien07"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien08", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien08 = :odsDzien08"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien09", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien09 = :odsDzien09"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien10", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien10 = :odsDzien10"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien11", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien11 = :odsDzien11"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien12", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien12 = :odsDzien12"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien13", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien13 = :odsDzien13"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien14", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien14 = :odsDzien14"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien15", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien15 = :odsDzien15"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien16", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien16 = :odsDzien16"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien17", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien17 = :odsDzien17"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien18", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien18 = :odsDzien18"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien19", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien19 = :odsDzien19"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien20", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien20 = :odsDzien20"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien21", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien21 = :odsDzien21"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien22", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien22 = :odsDzien22"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien23", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien23 = :odsDzien23"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien24", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien24 = :odsDzien24"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien25", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien25 = :odsDzien25"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien26", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien26 = :odsDzien26"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien27", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien27 = :odsDzien27"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien28", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien28 = :odsDzien28"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien29", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien29 = :odsDzien29"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien30", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien30 = :odsDzien30"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien31", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien31 = :odsDzien31"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien32", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien32 = :odsDzien32"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien33", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien33 = :odsDzien33"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien34", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien34 = :odsDzien34"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien35", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien35 = :odsDzien35"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien36", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien36 = :odsDzien36"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien37", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien37 = :odsDzien37"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien38", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien38 = :odsDzien38"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien39", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien39 = :odsDzien39"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien40", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien40 = :odsDzien40"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien41", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien41 = :odsDzien41"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien42", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien42 = :odsDzien42"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien43", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien43 = :odsDzien43"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien44", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien44 = :odsDzien44"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien45", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien45 = :odsDzien45"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien46", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien46 = :odsDzien46"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien47", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien47 = :odsDzien47"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien48", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien48 = :odsDzien48"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien49", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien49 = :odsDzien49"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien50", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien50 = :odsDzien50"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien51", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien51 = :odsDzien51"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien52", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien52 = :odsDzien52"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien53", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien53 = :odsDzien53"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien54", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien54 = :odsDzien54"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien55", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien55 = :odsDzien55"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien56", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien56 = :odsDzien56"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien57", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien57 = :odsDzien57"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien58", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien58 = :odsDzien58"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien59", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien59 = :odsDzien59"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien60", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien60 = :odsDzien60"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien61", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien61 = :odsDzien61"),
    @NamedQuery(name = "PlatnKalkOds.findByOdsDzien62", query = "SELECT p FROM PlatnKalkOds p WHERE p.odsDzien62 = :odsDzien62"),
    @NamedQuery(name = "PlatnKalkOds.findByInserttmp", query = "SELECT p FROM PlatnKalkOds p WHERE p.inserttmp = :inserttmp")})
public class PlatnKalkOds implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ZAKRES")
    private Integer zakres;
    @Column(name = "DATA_BAZOWA_ODS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataBazowaOds;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ODS_DZIEN_01", precision = 10, scale = 2)
    private BigDecimal odsDzien01;
    @Column(name = "ODS_DZIEN_02", precision = 10, scale = 2)
    private BigDecimal odsDzien02;
    @Column(name = "ODS_DZIEN_03", precision = 10, scale = 2)
    private BigDecimal odsDzien03;
    @Column(name = "ODS_DZIEN_04", precision = 10, scale = 2)
    private BigDecimal odsDzien04;
    @Column(name = "ODS_DZIEN_05", precision = 10, scale = 2)
    private BigDecimal odsDzien05;
    @Column(name = "ODS_DZIEN_06", precision = 10, scale = 2)
    private BigDecimal odsDzien06;
    @Column(name = "ODS_DZIEN_07", precision = 10, scale = 2)
    private BigDecimal odsDzien07;
    @Column(name = "ODS_DZIEN_08", precision = 10, scale = 2)
    private BigDecimal odsDzien08;
    @Column(name = "ODS_DZIEN_09", precision = 10, scale = 2)
    private BigDecimal odsDzien09;
    @Column(name = "ODS_DZIEN_10", precision = 10, scale = 2)
    private BigDecimal odsDzien10;
    @Column(name = "ODS_DZIEN_11", precision = 10, scale = 2)
    private BigDecimal odsDzien11;
    @Column(name = "ODS_DZIEN_12", precision = 10, scale = 2)
    private BigDecimal odsDzien12;
    @Column(name = "ODS_DZIEN_13", precision = 10, scale = 2)
    private BigDecimal odsDzien13;
    @Column(name = "ODS_DZIEN_14", precision = 10, scale = 2)
    private BigDecimal odsDzien14;
    @Column(name = "ODS_DZIEN_15", precision = 10, scale = 2)
    private BigDecimal odsDzien15;
    @Column(name = "ODS_DZIEN_16", precision = 10, scale = 2)
    private BigDecimal odsDzien16;
    @Column(name = "ODS_DZIEN_17", precision = 10, scale = 2)
    private BigDecimal odsDzien17;
    @Column(name = "ODS_DZIEN_18", precision = 10, scale = 2)
    private BigDecimal odsDzien18;
    @Column(name = "ODS_DZIEN_19", precision = 10, scale = 2)
    private BigDecimal odsDzien19;
    @Column(name = "ODS_DZIEN_20", precision = 10, scale = 2)
    private BigDecimal odsDzien20;
    @Column(name = "ODS_DZIEN_21", precision = 10, scale = 2)
    private BigDecimal odsDzien21;
    @Column(name = "ODS_DZIEN_22", precision = 10, scale = 2)
    private BigDecimal odsDzien22;
    @Column(name = "ODS_DZIEN_23", precision = 10, scale = 2)
    private BigDecimal odsDzien23;
    @Column(name = "ODS_DZIEN_24", precision = 10, scale = 2)
    private BigDecimal odsDzien24;
    @Column(name = "ODS_DZIEN_25", precision = 10, scale = 2)
    private BigDecimal odsDzien25;
    @Column(name = "ODS_DZIEN_26", precision = 10, scale = 2)
    private BigDecimal odsDzien26;
    @Column(name = "ODS_DZIEN_27", precision = 10, scale = 2)
    private BigDecimal odsDzien27;
    @Column(name = "ODS_DZIEN_28", precision = 10, scale = 2)
    private BigDecimal odsDzien28;
    @Column(name = "ODS_DZIEN_29", precision = 10, scale = 2)
    private BigDecimal odsDzien29;
    @Column(name = "ODS_DZIEN_30", precision = 10, scale = 2)
    private BigDecimal odsDzien30;
    @Column(name = "ODS_DZIEN_31", precision = 10, scale = 2)
    private BigDecimal odsDzien31;
    @Column(name = "ODS_DZIEN_32", precision = 10, scale = 2)
    private BigDecimal odsDzien32;
    @Column(name = "ODS_DZIEN_33", precision = 10, scale = 2)
    private BigDecimal odsDzien33;
    @Column(name = "ODS_DZIEN_34", precision = 10, scale = 2)
    private BigDecimal odsDzien34;
    @Column(name = "ODS_DZIEN_35", precision = 10, scale = 2)
    private BigDecimal odsDzien35;
    @Column(name = "ODS_DZIEN_36", precision = 10, scale = 2)
    private BigDecimal odsDzien36;
    @Column(name = "ODS_DZIEN_37", precision = 10, scale = 2)
    private BigDecimal odsDzien37;
    @Column(name = "ODS_DZIEN_38", precision = 10, scale = 2)
    private BigDecimal odsDzien38;
    @Column(name = "ODS_DZIEN_39", precision = 10, scale = 2)
    private BigDecimal odsDzien39;
    @Column(name = "ODS_DZIEN_40", precision = 10, scale = 2)
    private BigDecimal odsDzien40;
    @Column(name = "ODS_DZIEN_41", precision = 10, scale = 2)
    private BigDecimal odsDzien41;
    @Column(name = "ODS_DZIEN_42", precision = 10, scale = 2)
    private BigDecimal odsDzien42;
    @Column(name = "ODS_DZIEN_43", precision = 10, scale = 2)
    private BigDecimal odsDzien43;
    @Column(name = "ODS_DZIEN_44", precision = 10, scale = 2)
    private BigDecimal odsDzien44;
    @Column(name = "ODS_DZIEN_45", precision = 10, scale = 2)
    private BigDecimal odsDzien45;
    @Column(name = "ODS_DZIEN_46", precision = 10, scale = 2)
    private BigDecimal odsDzien46;
    @Column(name = "ODS_DZIEN_47", precision = 10, scale = 2)
    private BigDecimal odsDzien47;
    @Column(name = "ODS_DZIEN_48", precision = 10, scale = 2)
    private BigDecimal odsDzien48;
    @Column(name = "ODS_DZIEN_49", precision = 10, scale = 2)
    private BigDecimal odsDzien49;
    @Column(name = "ODS_DZIEN_50", precision = 10, scale = 2)
    private BigDecimal odsDzien50;
    @Column(name = "ODS_DZIEN_51", precision = 10, scale = 2)
    private BigDecimal odsDzien51;
    @Column(name = "ODS_DZIEN_52", precision = 10, scale = 2)
    private BigDecimal odsDzien52;
    @Column(name = "ODS_DZIEN_53", precision = 10, scale = 2)
    private BigDecimal odsDzien53;
    @Column(name = "ODS_DZIEN_54", precision = 10, scale = 2)
    private BigDecimal odsDzien54;
    @Column(name = "ODS_DZIEN_55", precision = 10, scale = 2)
    private BigDecimal odsDzien55;
    @Column(name = "ODS_DZIEN_56", precision = 10, scale = 2)
    private BigDecimal odsDzien56;
    @Column(name = "ODS_DZIEN_57", precision = 10, scale = 2)
    private BigDecimal odsDzien57;
    @Column(name = "ODS_DZIEN_58", precision = 10, scale = 2)
    private BigDecimal odsDzien58;
    @Column(name = "ODS_DZIEN_59", precision = 10, scale = 2)
    private BigDecimal odsDzien59;
    @Column(name = "ODS_DZIEN_60", precision = 10, scale = 2)
    private BigDecimal odsDzien60;
    @Column(name = "ODS_DZIEN_61", precision = 10, scale = 2)
    private BigDecimal odsDzien61;
    @Column(name = "ODS_DZIEN_62", precision = 10, scale = 2)
    private BigDecimal odsDzien62;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnKalkOds() {
    }

    public PlatnKalkOds(Integer id) {
        this.id = id;
    }

    public PlatnKalkOds(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getZakres() {
        return zakres;
    }

    public void setZakres(Integer zakres) {
        this.zakres = zakres;
    }

    public Date getDataBazowaOds() {
        return dataBazowaOds;
    }

    public void setDataBazowaOds(Date dataBazowaOds) {
        this.dataBazowaOds = dataBazowaOds;
    }

    public BigDecimal getOdsDzien01() {
        return odsDzien01;
    }

    public void setOdsDzien01(BigDecimal odsDzien01) {
        this.odsDzien01 = odsDzien01;
    }

    public BigDecimal getOdsDzien02() {
        return odsDzien02;
    }

    public void setOdsDzien02(BigDecimal odsDzien02) {
        this.odsDzien02 = odsDzien02;
    }

    public BigDecimal getOdsDzien03() {
        return odsDzien03;
    }

    public void setOdsDzien03(BigDecimal odsDzien03) {
        this.odsDzien03 = odsDzien03;
    }

    public BigDecimal getOdsDzien04() {
        return odsDzien04;
    }

    public void setOdsDzien04(BigDecimal odsDzien04) {
        this.odsDzien04 = odsDzien04;
    }

    public BigDecimal getOdsDzien05() {
        return odsDzien05;
    }

    public void setOdsDzien05(BigDecimal odsDzien05) {
        this.odsDzien05 = odsDzien05;
    }

    public BigDecimal getOdsDzien06() {
        return odsDzien06;
    }

    public void setOdsDzien06(BigDecimal odsDzien06) {
        this.odsDzien06 = odsDzien06;
    }

    public BigDecimal getOdsDzien07() {
        return odsDzien07;
    }

    public void setOdsDzien07(BigDecimal odsDzien07) {
        this.odsDzien07 = odsDzien07;
    }

    public BigDecimal getOdsDzien08() {
        return odsDzien08;
    }

    public void setOdsDzien08(BigDecimal odsDzien08) {
        this.odsDzien08 = odsDzien08;
    }

    public BigDecimal getOdsDzien09() {
        return odsDzien09;
    }

    public void setOdsDzien09(BigDecimal odsDzien09) {
        this.odsDzien09 = odsDzien09;
    }

    public BigDecimal getOdsDzien10() {
        return odsDzien10;
    }

    public void setOdsDzien10(BigDecimal odsDzien10) {
        this.odsDzien10 = odsDzien10;
    }

    public BigDecimal getOdsDzien11() {
        return odsDzien11;
    }

    public void setOdsDzien11(BigDecimal odsDzien11) {
        this.odsDzien11 = odsDzien11;
    }

    public BigDecimal getOdsDzien12() {
        return odsDzien12;
    }

    public void setOdsDzien12(BigDecimal odsDzien12) {
        this.odsDzien12 = odsDzien12;
    }

    public BigDecimal getOdsDzien13() {
        return odsDzien13;
    }

    public void setOdsDzien13(BigDecimal odsDzien13) {
        this.odsDzien13 = odsDzien13;
    }

    public BigDecimal getOdsDzien14() {
        return odsDzien14;
    }

    public void setOdsDzien14(BigDecimal odsDzien14) {
        this.odsDzien14 = odsDzien14;
    }

    public BigDecimal getOdsDzien15() {
        return odsDzien15;
    }

    public void setOdsDzien15(BigDecimal odsDzien15) {
        this.odsDzien15 = odsDzien15;
    }

    public BigDecimal getOdsDzien16() {
        return odsDzien16;
    }

    public void setOdsDzien16(BigDecimal odsDzien16) {
        this.odsDzien16 = odsDzien16;
    }

    public BigDecimal getOdsDzien17() {
        return odsDzien17;
    }

    public void setOdsDzien17(BigDecimal odsDzien17) {
        this.odsDzien17 = odsDzien17;
    }

    public BigDecimal getOdsDzien18() {
        return odsDzien18;
    }

    public void setOdsDzien18(BigDecimal odsDzien18) {
        this.odsDzien18 = odsDzien18;
    }

    public BigDecimal getOdsDzien19() {
        return odsDzien19;
    }

    public void setOdsDzien19(BigDecimal odsDzien19) {
        this.odsDzien19 = odsDzien19;
    }

    public BigDecimal getOdsDzien20() {
        return odsDzien20;
    }

    public void setOdsDzien20(BigDecimal odsDzien20) {
        this.odsDzien20 = odsDzien20;
    }

    public BigDecimal getOdsDzien21() {
        return odsDzien21;
    }

    public void setOdsDzien21(BigDecimal odsDzien21) {
        this.odsDzien21 = odsDzien21;
    }

    public BigDecimal getOdsDzien22() {
        return odsDzien22;
    }

    public void setOdsDzien22(BigDecimal odsDzien22) {
        this.odsDzien22 = odsDzien22;
    }

    public BigDecimal getOdsDzien23() {
        return odsDzien23;
    }

    public void setOdsDzien23(BigDecimal odsDzien23) {
        this.odsDzien23 = odsDzien23;
    }

    public BigDecimal getOdsDzien24() {
        return odsDzien24;
    }

    public void setOdsDzien24(BigDecimal odsDzien24) {
        this.odsDzien24 = odsDzien24;
    }

    public BigDecimal getOdsDzien25() {
        return odsDzien25;
    }

    public void setOdsDzien25(BigDecimal odsDzien25) {
        this.odsDzien25 = odsDzien25;
    }

    public BigDecimal getOdsDzien26() {
        return odsDzien26;
    }

    public void setOdsDzien26(BigDecimal odsDzien26) {
        this.odsDzien26 = odsDzien26;
    }

    public BigDecimal getOdsDzien27() {
        return odsDzien27;
    }

    public void setOdsDzien27(BigDecimal odsDzien27) {
        this.odsDzien27 = odsDzien27;
    }

    public BigDecimal getOdsDzien28() {
        return odsDzien28;
    }

    public void setOdsDzien28(BigDecimal odsDzien28) {
        this.odsDzien28 = odsDzien28;
    }

    public BigDecimal getOdsDzien29() {
        return odsDzien29;
    }

    public void setOdsDzien29(BigDecimal odsDzien29) {
        this.odsDzien29 = odsDzien29;
    }

    public BigDecimal getOdsDzien30() {
        return odsDzien30;
    }

    public void setOdsDzien30(BigDecimal odsDzien30) {
        this.odsDzien30 = odsDzien30;
    }

    public BigDecimal getOdsDzien31() {
        return odsDzien31;
    }

    public void setOdsDzien31(BigDecimal odsDzien31) {
        this.odsDzien31 = odsDzien31;
    }

    public BigDecimal getOdsDzien32() {
        return odsDzien32;
    }

    public void setOdsDzien32(BigDecimal odsDzien32) {
        this.odsDzien32 = odsDzien32;
    }

    public BigDecimal getOdsDzien33() {
        return odsDzien33;
    }

    public void setOdsDzien33(BigDecimal odsDzien33) {
        this.odsDzien33 = odsDzien33;
    }

    public BigDecimal getOdsDzien34() {
        return odsDzien34;
    }

    public void setOdsDzien34(BigDecimal odsDzien34) {
        this.odsDzien34 = odsDzien34;
    }

    public BigDecimal getOdsDzien35() {
        return odsDzien35;
    }

    public void setOdsDzien35(BigDecimal odsDzien35) {
        this.odsDzien35 = odsDzien35;
    }

    public BigDecimal getOdsDzien36() {
        return odsDzien36;
    }

    public void setOdsDzien36(BigDecimal odsDzien36) {
        this.odsDzien36 = odsDzien36;
    }

    public BigDecimal getOdsDzien37() {
        return odsDzien37;
    }

    public void setOdsDzien37(BigDecimal odsDzien37) {
        this.odsDzien37 = odsDzien37;
    }

    public BigDecimal getOdsDzien38() {
        return odsDzien38;
    }

    public void setOdsDzien38(BigDecimal odsDzien38) {
        this.odsDzien38 = odsDzien38;
    }

    public BigDecimal getOdsDzien39() {
        return odsDzien39;
    }

    public void setOdsDzien39(BigDecimal odsDzien39) {
        this.odsDzien39 = odsDzien39;
    }

    public BigDecimal getOdsDzien40() {
        return odsDzien40;
    }

    public void setOdsDzien40(BigDecimal odsDzien40) {
        this.odsDzien40 = odsDzien40;
    }

    public BigDecimal getOdsDzien41() {
        return odsDzien41;
    }

    public void setOdsDzien41(BigDecimal odsDzien41) {
        this.odsDzien41 = odsDzien41;
    }

    public BigDecimal getOdsDzien42() {
        return odsDzien42;
    }

    public void setOdsDzien42(BigDecimal odsDzien42) {
        this.odsDzien42 = odsDzien42;
    }

    public BigDecimal getOdsDzien43() {
        return odsDzien43;
    }

    public void setOdsDzien43(BigDecimal odsDzien43) {
        this.odsDzien43 = odsDzien43;
    }

    public BigDecimal getOdsDzien44() {
        return odsDzien44;
    }

    public void setOdsDzien44(BigDecimal odsDzien44) {
        this.odsDzien44 = odsDzien44;
    }

    public BigDecimal getOdsDzien45() {
        return odsDzien45;
    }

    public void setOdsDzien45(BigDecimal odsDzien45) {
        this.odsDzien45 = odsDzien45;
    }

    public BigDecimal getOdsDzien46() {
        return odsDzien46;
    }

    public void setOdsDzien46(BigDecimal odsDzien46) {
        this.odsDzien46 = odsDzien46;
    }

    public BigDecimal getOdsDzien47() {
        return odsDzien47;
    }

    public void setOdsDzien47(BigDecimal odsDzien47) {
        this.odsDzien47 = odsDzien47;
    }

    public BigDecimal getOdsDzien48() {
        return odsDzien48;
    }

    public void setOdsDzien48(BigDecimal odsDzien48) {
        this.odsDzien48 = odsDzien48;
    }

    public BigDecimal getOdsDzien49() {
        return odsDzien49;
    }

    public void setOdsDzien49(BigDecimal odsDzien49) {
        this.odsDzien49 = odsDzien49;
    }

    public BigDecimal getOdsDzien50() {
        return odsDzien50;
    }

    public void setOdsDzien50(BigDecimal odsDzien50) {
        this.odsDzien50 = odsDzien50;
    }

    public BigDecimal getOdsDzien51() {
        return odsDzien51;
    }

    public void setOdsDzien51(BigDecimal odsDzien51) {
        this.odsDzien51 = odsDzien51;
    }

    public BigDecimal getOdsDzien52() {
        return odsDzien52;
    }

    public void setOdsDzien52(BigDecimal odsDzien52) {
        this.odsDzien52 = odsDzien52;
    }

    public BigDecimal getOdsDzien53() {
        return odsDzien53;
    }

    public void setOdsDzien53(BigDecimal odsDzien53) {
        this.odsDzien53 = odsDzien53;
    }

    public BigDecimal getOdsDzien54() {
        return odsDzien54;
    }

    public void setOdsDzien54(BigDecimal odsDzien54) {
        this.odsDzien54 = odsDzien54;
    }

    public BigDecimal getOdsDzien55() {
        return odsDzien55;
    }

    public void setOdsDzien55(BigDecimal odsDzien55) {
        this.odsDzien55 = odsDzien55;
    }

    public BigDecimal getOdsDzien56() {
        return odsDzien56;
    }

    public void setOdsDzien56(BigDecimal odsDzien56) {
        this.odsDzien56 = odsDzien56;
    }

    public BigDecimal getOdsDzien57() {
        return odsDzien57;
    }

    public void setOdsDzien57(BigDecimal odsDzien57) {
        this.odsDzien57 = odsDzien57;
    }

    public BigDecimal getOdsDzien58() {
        return odsDzien58;
    }

    public void setOdsDzien58(BigDecimal odsDzien58) {
        this.odsDzien58 = odsDzien58;
    }

    public BigDecimal getOdsDzien59() {
        return odsDzien59;
    }

    public void setOdsDzien59(BigDecimal odsDzien59) {
        this.odsDzien59 = odsDzien59;
    }

    public BigDecimal getOdsDzien60() {
        return odsDzien60;
    }

    public void setOdsDzien60(BigDecimal odsDzien60) {
        this.odsDzien60 = odsDzien60;
    }

    public BigDecimal getOdsDzien61() {
        return odsDzien61;
    }

    public void setOdsDzien61(BigDecimal odsDzien61) {
        this.odsDzien61 = odsDzien61;
    }

    public BigDecimal getOdsDzien62() {
        return odsDzien62;
    }

    public void setOdsDzien62(BigDecimal odsDzien62) {
        this.odsDzien62 = odsDzien62;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof PlatnKalkOds)) {
            return false;
        }
        PlatnKalkOds other = (PlatnKalkOds) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnKalkOds[ id=" + id + " ]";
    }
    
}
