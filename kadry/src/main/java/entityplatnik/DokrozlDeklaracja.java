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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "DOKROZL_DEKLARACJA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DokrozlDeklaracja.findAll", query = "SELECT d FROM DokrozlDeklaracja d"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdDokument", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idDokument = :idDokument"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdPlatnik", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdKomplet", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idKomplet = :idKomplet"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdWdrZus", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idWdrZus = :idWdrZus"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdDokZus", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idDokZus = :idDokZus"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdPlZus", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idPlZus = :idPlZus"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIdKomplZus", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.idKomplZus = :idKomplZus"),
    @NamedQuery(name = "DokrozlDeklaracja.findByPodnrKompletu", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.podnrKompletu = :podnrKompletu"),
    @NamedQuery(name = "DokrozlDeklaracja.findByI21", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.i21 = :i21"),
    @NamedQuery(name = "DokrozlDeklaracja.findByI22", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.i22 = :i22"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIii1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iii1 = :iii1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIii2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iii2 = :iii2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIii3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iii3 = :iii3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv1 = :iv1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv2 = :iv2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv3 = :iv3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv4 = :iv4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv5 = :iv5"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv6", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv6 = :iv6"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv7", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv7 = :iv7"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv8", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv8 = :iv8"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv9", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv9 = :iv9"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv10", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv10 = :iv10"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv11", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv11 = :iv11"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv12", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv12 = :iv12"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv13", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv13 = :iv13"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv14", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv14 = :iv14"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv15", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv15 = :iv15"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv16", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv16 = :iv16"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv17", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv17 = :iv17"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv18", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv18 = :iv18"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv19", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv19 = :iv19"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv20", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv20 = :iv20"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv21", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv21 = :iv21"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv22", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv22 = :iv22"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv23", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv23 = :iv23"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv24", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv24 = :iv24"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv25", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv25 = :iv25"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv26", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv26 = :iv26"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv27", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv27 = :iv27"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv28", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv28 = :iv28"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv29", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv29 = :iv29"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv30", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv30 = :iv30"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv31", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv31 = :iv31"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv32", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv32 = :iv32"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv33", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv33 = :iv33"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv34", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv34 = :iv34"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv35", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv35 = :iv35"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv36", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv36 = :iv36"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIv37", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.iv37 = :iv37"),
    @NamedQuery(name = "DokrozlDeklaracja.findByV1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.v1 = :v1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByV2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.v2 = :v2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByV3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.v3 = :v3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByV4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.v4 = :v4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByV5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.v5 = :v5"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVi1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vi1 = :vi1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVi2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vi2 = :vi2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii1 = :vii1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii2 = :vii2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii3 = :vii3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii4 = :vii4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii5 = :vii5"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii6", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii6 = :vii6"),
    @NamedQuery(name = "DokrozlDeklaracja.findByVii7", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.vii7 = :vii7"),
    @NamedQuery(name = "DokrozlDeklaracja.findByViii1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.viii1 = :viii1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByViii2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.viii2 = :viii2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByViii3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.viii3 = :viii3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIx1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.ix1 = :ix1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIx2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.ix2 = :ix2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIx3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.ix3 = :ix3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX11", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x11 = :x11"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX12", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x12 = :x12"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX13", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x13 = :x13"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x2 = :x2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x3 = :x3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x4 = :x4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x5 = :x5"),
    @NamedQuery(name = "DokrozlDeklaracja.findByX6", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.x6 = :x6"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIxKwotaDoZwrotu", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.ixKwotaDoZwrotu = :ixKwotaDoZwrotu"),
    @NamedQuery(name = "DokrozlDeklaracja.findByIxKwotaDoZapl", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.ixKwotaDoZapl = :ixKwotaDoZapl"),
    @NamedQuery(name = "DokrozlDeklaracja.findByInserttmp", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.inserttmp = :inserttmp"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi1 = :xi1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi2 = :xi2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi3 = :xi3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi4 = :xi4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi5 = :xi5"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi6", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi6 = :xi6"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi7", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi7 = :xi7"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi8", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi8 = :xi8"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi9", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi9 = :xi9"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi10", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi10 = :xi10"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi11", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi11 = :xi11"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi12", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi12 = :xi12"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi13", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi13 = :xi13"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi14", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi14 = :xi14"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi15", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi15 = :xi15"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi16", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi16 = :xi16"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi17", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi17 = :xi17"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi18", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi18 = :xi18"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi19", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi19 = :xi19"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXi20", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xi20 = :xi20"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXii1", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xii1 = :xii1"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXii2", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xii2 = :xii2"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXii3", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xii3 = :xii3"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXii4", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xii4 = :xii4"),
    @NamedQuery(name = "DokrozlDeklaracja.findByXii5", query = "SELECT d FROM DokrozlDeklaracja d WHERE d.xii5 = :xii5")})
public class DokrozlDeklaracja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_KOMPLET")
    private Integer idKomplet;
    @Column(name = "ID_WDR_ZUS")
    private Integer idWdrZus;
    @Column(name = "ID_DOK_ZUS")
    private Integer idDokZus;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_KOMPL_ZUS")
    private Integer idKomplZus;
    @Column(name = "PODNR_KOMPLETU")
    private Integer podnrKompletu;
    @Column(name = "I_2_1")
    private Integer i21;
    @Column(name = "I_2_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i22;
    @Column(name = "III_1")
    private Integer iii1;
    @Column(name = "III_2")
    private Character iii2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_3", precision = 5, scale = 2)
    private BigDecimal iii3;
    @Column(name = "IV_1", precision = 15, scale = 2)
    private BigDecimal iv1;
    @Column(name = "IV_2", precision = 15, scale = 2)
    private BigDecimal iv2;
    @Column(name = "IV_3", precision = 15, scale = 2)
    private BigDecimal iv3;
    @Column(name = "IV_4", precision = 15, scale = 2)
    private BigDecimal iv4;
    @Column(name = "IV_5", precision = 15, scale = 2)
    private BigDecimal iv5;
    @Column(name = "IV_6", precision = 15, scale = 2)
    private BigDecimal iv6;
    @Column(name = "IV_7", precision = 15, scale = 2)
    private BigDecimal iv7;
    @Column(name = "IV_8", precision = 15, scale = 2)
    private BigDecimal iv8;
    @Column(name = "IV_9", precision = 15, scale = 2)
    private BigDecimal iv9;
    @Column(name = "IV_10", precision = 15, scale = 2)
    private BigDecimal iv10;
    @Column(name = "IV_11", precision = 15, scale = 2)
    private BigDecimal iv11;
    @Column(name = "IV_12", precision = 15, scale = 2)
    private BigDecimal iv12;
    @Column(name = "IV_13", precision = 15, scale = 2)
    private BigDecimal iv13;
    @Column(name = "IV_14", precision = 15, scale = 2)
    private BigDecimal iv14;
    @Column(name = "IV_15", precision = 15, scale = 2)
    private BigDecimal iv15;
    @Column(name = "IV_16", precision = 15, scale = 2)
    private BigDecimal iv16;
    @Column(name = "IV_17", precision = 15, scale = 2)
    private BigDecimal iv17;
    @Column(name = "IV_18", precision = 15, scale = 2)
    private BigDecimal iv18;
    @Column(name = "IV_19", precision = 15, scale = 2)
    private BigDecimal iv19;
    @Column(name = "IV_20", precision = 15, scale = 2)
    private BigDecimal iv20;
    @Column(name = "IV_21", precision = 15, scale = 2)
    private BigDecimal iv21;
    @Column(name = "IV_22", precision = 15, scale = 2)
    private BigDecimal iv22;
    @Column(name = "IV_23", precision = 15, scale = 2)
    private BigDecimal iv23;
    @Column(name = "IV_24", precision = 15, scale = 2)
    private BigDecimal iv24;
    @Column(name = "IV_25", precision = 15, scale = 2)
    private BigDecimal iv25;
    @Column(name = "IV_26", precision = 15, scale = 2)
    private BigDecimal iv26;
    @Column(name = "IV_27", precision = 15, scale = 2)
    private BigDecimal iv27;
    @Column(name = "IV_28", precision = 15, scale = 2)
    private BigDecimal iv28;
    @Column(name = "IV_29", precision = 15, scale = 2)
    private BigDecimal iv29;
    @Column(name = "IV_30", precision = 15, scale = 2)
    private BigDecimal iv30;
    @Column(name = "IV_31", precision = 15, scale = 2)
    private BigDecimal iv31;
    @Column(name = "IV_32", precision = 15, scale = 2)
    private BigDecimal iv32;
    @Column(name = "IV_33", precision = 15, scale = 2)
    private BigDecimal iv33;
    @Column(name = "IV_34", precision = 15, scale = 2)
    private BigDecimal iv34;
    @Column(name = "IV_35", precision = 15, scale = 2)
    private BigDecimal iv35;
    @Column(name = "IV_36", precision = 15, scale = 2)
    private BigDecimal iv36;
    @Column(name = "IV_37", precision = 15, scale = 2)
    private BigDecimal iv37;
    @Column(name = "V_1", precision = 15, scale = 2)
    private BigDecimal v1;
    @Column(name = "V_2", precision = 15, scale = 2)
    private BigDecimal v2;
    @Column(name = "V_3", precision = 15, scale = 2)
    private BigDecimal v3;
    @Column(name = "V_4", precision = 15, scale = 2)
    private BigDecimal v4;
    @Column(name = "V_5", precision = 15, scale = 2)
    private BigDecimal v5;
    @Column(name = "VI_1", precision = 12, scale = 2)
    private BigDecimal vi1;
    @Column(name = "VI_2", precision = 12, scale = 2)
    private BigDecimal vi2;
    @Column(name = "VII_1", precision = 11, scale = 2)
    private BigDecimal vii1;
    @Column(name = "VII_2", precision = 11, scale = 2)
    private BigDecimal vii2;
    @Column(name = "VII_3", precision = 11, scale = 2)
    private BigDecimal vii3;
    @Column(name = "VII_4", precision = 11, scale = 2)
    private BigDecimal vii4;
    @Column(name = "VII_5", precision = 11, scale = 2)
    private BigDecimal vii5;
    @Column(name = "VII_6", precision = 11, scale = 2)
    private BigDecimal vii6;
    @Column(name = "VII_7", precision = 12, scale = 2)
    private BigDecimal vii7;
    @Column(name = "VIII_1", precision = 11, scale = 2)
    private BigDecimal viii1;
    @Column(name = "VIII_2", precision = 11, scale = 2)
    private BigDecimal viii2;
    @Column(name = "VIII_3", precision = 12, scale = 2)
    private BigDecimal viii3;
    @Column(name = "IX_1")
    private Integer ix1;
    @Column(name = "IX_2")
    private Integer ix2;
    @Column(name = "IX_3", precision = 12, scale = 2)
    private BigDecimal ix3;
    @Size(max = 4)
    @Column(name = "X_1_1", length = 4)
    private String x11;
    @Column(name = "X_1_2")
    private Character x12;
    @Column(name = "X_1_3")
    private Character x13;
    @Column(name = "X_2", precision = 11, scale = 2)
    private BigDecimal x2;
    @Column(name = "X_3", precision = 11, scale = 2)
    private BigDecimal x3;
    @Column(name = "X_4", precision = 11, scale = 2)
    private BigDecimal x4;
    @Column(name = "X_5", precision = 11, scale = 2)
    private BigDecimal x5;
    @Column(name = "X_6")
    private Character x6;
    @Column(name = "IX_KWOTA_DO_ZWROTU", precision = 13, scale = 2)
    private BigDecimal ixKwotaDoZwrotu;
    @Column(name = "IX_KWOTA_DO_ZAPL", precision = 13, scale = 2)
    private BigDecimal ixKwotaDoZapl;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "XI_1")
    private Character xi1;
    @Column(name = "XI_2", precision = 10, scale = 2)
    private BigDecimal xi2;
    @Column(name = "XI_3", precision = 10, scale = 2)
    private BigDecimal xi3;
    @Column(name = "XI_4", precision = 10, scale = 2)
    private BigDecimal xi4;
    @Column(name = "XI_5")
    private Character xi5;
    @Column(name = "XI_6", precision = 10, scale = 2)
    private BigDecimal xi6;
    @Column(name = "XI_7", precision = 10, scale = 2)
    private BigDecimal xi7;
    @Column(name = "XI_8", precision = 10, scale = 2)
    private BigDecimal xi8;
    @Column(name = "XI_9")
    private Character xi9;
    @Column(name = "XI_10", precision = 10, scale = 2)
    private BigDecimal xi10;
    @Column(name = "XI_11", precision = 10, scale = 2)
    private BigDecimal xi11;
    @Column(name = "XI_12")
    private Character xi12;
    @Column(name = "XI_13", precision = 10, scale = 2)
    private BigDecimal xi13;
    @Column(name = "XI_14")
    private Character xi14;
    @Column(name = "XI_15", precision = 10, scale = 2)
    private BigDecimal xi15;
    @Column(name = "XI_16", precision = 10, scale = 2)
    private BigDecimal xi16;
    @Column(name = "XI_17", precision = 10, scale = 2)
    private BigDecimal xi17;
    @Column(name = "XI_18")
    private Character xi18;
    @Column(name = "XI_19", precision = 10, scale = 2)
    private BigDecimal xi19;
    @Column(name = "XI_20", precision = 10, scale = 2)
    private BigDecimal xi20;
    @Column(name = "XII_1")
    private Short xii1;
    @Column(name = "XII_2", precision = 11, scale = 2)
    private BigDecimal xii2;
    @Column(name = "XII_3", precision = 11, scale = 2)
    private BigDecimal xii3;
    @Column(name = "XII_4", precision = 11, scale = 2)
    private BigDecimal xii4;
    @Column(name = "XII_5", precision = 11, scale = 2)
    private BigDecimal xii5;

    public DokrozlDeklaracja() {
    }

    public DokrozlDeklaracja(Integer idDokument) {
        this.idDokument = idDokument;
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

    public Integer getIdKomplet() {
        return idKomplet;
    }

    public void setIdKomplet(Integer idKomplet) {
        this.idKomplet = idKomplet;
    }

    public Integer getIdWdrZus() {
        return idWdrZus;
    }

    public void setIdWdrZus(Integer idWdrZus) {
        this.idWdrZus = idWdrZus;
    }

    public Integer getIdDokZus() {
        return idDokZus;
    }

    public void setIdDokZus(Integer idDokZus) {
        this.idDokZus = idDokZus;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdKomplZus() {
        return idKomplZus;
    }

    public void setIdKomplZus(Integer idKomplZus) {
        this.idKomplZus = idKomplZus;
    }

    public Integer getPodnrKompletu() {
        return podnrKompletu;
    }

    public void setPodnrKompletu(Integer podnrKompletu) {
        this.podnrKompletu = podnrKompletu;
    }

    public Integer getI21() {
        return i21;
    }

    public void setI21(Integer i21) {
        this.i21 = i21;
    }

    public Date getI22() {
        return i22;
    }

    public void setI22(Date i22) {
        this.i22 = i22;
    }

    public Integer getIii1() {
        return iii1;
    }

    public void setIii1(Integer iii1) {
        this.iii1 = iii1;
    }

    public Character getIii2() {
        return iii2;
    }

    public void setIii2(Character iii2) {
        this.iii2 = iii2;
    }

    public BigDecimal getIii3() {
        return iii3;
    }

    public void setIii3(BigDecimal iii3) {
        this.iii3 = iii3;
    }

    public BigDecimal getIv1() {
        return iv1;
    }

    public void setIv1(BigDecimal iv1) {
        this.iv1 = iv1;
    }

    public BigDecimal getIv2() {
        return iv2;
    }

    public void setIv2(BigDecimal iv2) {
        this.iv2 = iv2;
    }

    public BigDecimal getIv3() {
        return iv3;
    }

    public void setIv3(BigDecimal iv3) {
        this.iv3 = iv3;
    }

    public BigDecimal getIv4() {
        return iv4;
    }

    public void setIv4(BigDecimal iv4) {
        this.iv4 = iv4;
    }

    public BigDecimal getIv5() {
        return iv5;
    }

    public void setIv5(BigDecimal iv5) {
        this.iv5 = iv5;
    }

    public BigDecimal getIv6() {
        return iv6;
    }

    public void setIv6(BigDecimal iv6) {
        this.iv6 = iv6;
    }

    public BigDecimal getIv7() {
        return iv7;
    }

    public void setIv7(BigDecimal iv7) {
        this.iv7 = iv7;
    }

    public BigDecimal getIv8() {
        return iv8;
    }

    public void setIv8(BigDecimal iv8) {
        this.iv8 = iv8;
    }

    public BigDecimal getIv9() {
        return iv9;
    }

    public void setIv9(BigDecimal iv9) {
        this.iv9 = iv9;
    }

    public BigDecimal getIv10() {
        return iv10;
    }

    public void setIv10(BigDecimal iv10) {
        this.iv10 = iv10;
    }

    public BigDecimal getIv11() {
        return iv11;
    }

    public void setIv11(BigDecimal iv11) {
        this.iv11 = iv11;
    }

    public BigDecimal getIv12() {
        return iv12;
    }

    public void setIv12(BigDecimal iv12) {
        this.iv12 = iv12;
    }

    public BigDecimal getIv13() {
        return iv13;
    }

    public void setIv13(BigDecimal iv13) {
        this.iv13 = iv13;
    }

    public BigDecimal getIv14() {
        return iv14;
    }

    public void setIv14(BigDecimal iv14) {
        this.iv14 = iv14;
    }

    public BigDecimal getIv15() {
        return iv15;
    }

    public void setIv15(BigDecimal iv15) {
        this.iv15 = iv15;
    }

    public BigDecimal getIv16() {
        return iv16;
    }

    public void setIv16(BigDecimal iv16) {
        this.iv16 = iv16;
    }

    public BigDecimal getIv17() {
        return iv17;
    }

    public void setIv17(BigDecimal iv17) {
        this.iv17 = iv17;
    }

    public BigDecimal getIv18() {
        return iv18;
    }

    public void setIv18(BigDecimal iv18) {
        this.iv18 = iv18;
    }

    public BigDecimal getIv19() {
        return iv19;
    }

    public void setIv19(BigDecimal iv19) {
        this.iv19 = iv19;
    }

    public BigDecimal getIv20() {
        return iv20;
    }

    public void setIv20(BigDecimal iv20) {
        this.iv20 = iv20;
    }

    public BigDecimal getIv21() {
        return iv21;
    }

    public void setIv21(BigDecimal iv21) {
        this.iv21 = iv21;
    }

    public BigDecimal getIv22() {
        return iv22;
    }

    public void setIv22(BigDecimal iv22) {
        this.iv22 = iv22;
    }

    public BigDecimal getIv23() {
        return iv23;
    }

    public void setIv23(BigDecimal iv23) {
        this.iv23 = iv23;
    }

    public BigDecimal getIv24() {
        return iv24;
    }

    public void setIv24(BigDecimal iv24) {
        this.iv24 = iv24;
    }

    public BigDecimal getIv25() {
        return iv25;
    }

    public void setIv25(BigDecimal iv25) {
        this.iv25 = iv25;
    }

    public BigDecimal getIv26() {
        return iv26;
    }

    public void setIv26(BigDecimal iv26) {
        this.iv26 = iv26;
    }

    public BigDecimal getIv27() {
        return iv27;
    }

    public void setIv27(BigDecimal iv27) {
        this.iv27 = iv27;
    }

    public BigDecimal getIv28() {
        return iv28;
    }

    public void setIv28(BigDecimal iv28) {
        this.iv28 = iv28;
    }

    public BigDecimal getIv29() {
        return iv29;
    }

    public void setIv29(BigDecimal iv29) {
        this.iv29 = iv29;
    }

    public BigDecimal getIv30() {
        return iv30;
    }

    public void setIv30(BigDecimal iv30) {
        this.iv30 = iv30;
    }

    public BigDecimal getIv31() {
        return iv31;
    }

    public void setIv31(BigDecimal iv31) {
        this.iv31 = iv31;
    }

    public BigDecimal getIv32() {
        return iv32;
    }

    public void setIv32(BigDecimal iv32) {
        this.iv32 = iv32;
    }

    public BigDecimal getIv33() {
        return iv33;
    }

    public void setIv33(BigDecimal iv33) {
        this.iv33 = iv33;
    }

    public BigDecimal getIv34() {
        return iv34;
    }

    public void setIv34(BigDecimal iv34) {
        this.iv34 = iv34;
    }

    public BigDecimal getIv35() {
        return iv35;
    }

    public void setIv35(BigDecimal iv35) {
        this.iv35 = iv35;
    }

    public BigDecimal getIv36() {
        return iv36;
    }

    public void setIv36(BigDecimal iv36) {
        this.iv36 = iv36;
    }

    public BigDecimal getIv37() {
        return iv37;
    }

    public void setIv37(BigDecimal iv37) {
        this.iv37 = iv37;
    }

    public BigDecimal getV1() {
        return v1;
    }

    public void setV1(BigDecimal v1) {
        this.v1 = v1;
    }

    public BigDecimal getV2() {
        return v2;
    }

    public void setV2(BigDecimal v2) {
        this.v2 = v2;
    }

    public BigDecimal getV3() {
        return v3;
    }

    public void setV3(BigDecimal v3) {
        this.v3 = v3;
    }

    public BigDecimal getV4() {
        return v4;
    }

    public void setV4(BigDecimal v4) {
        this.v4 = v4;
    }

    public BigDecimal getV5() {
        return v5;
    }

    public void setV5(BigDecimal v5) {
        this.v5 = v5;
    }

    public BigDecimal getVi1() {
        return vi1;
    }

    public void setVi1(BigDecimal vi1) {
        this.vi1 = vi1;
    }

    public BigDecimal getVi2() {
        return vi2;
    }

    public void setVi2(BigDecimal vi2) {
        this.vi2 = vi2;
    }

    public BigDecimal getVii1() {
        return vii1;
    }

    public void setVii1(BigDecimal vii1) {
        this.vii1 = vii1;
    }

    public BigDecimal getVii2() {
        return vii2;
    }

    public void setVii2(BigDecimal vii2) {
        this.vii2 = vii2;
    }

    public BigDecimal getVii3() {
        return vii3;
    }

    public void setVii3(BigDecimal vii3) {
        this.vii3 = vii3;
    }

    public BigDecimal getVii4() {
        return vii4;
    }

    public void setVii4(BigDecimal vii4) {
        this.vii4 = vii4;
    }

    public BigDecimal getVii5() {
        return vii5;
    }

    public void setVii5(BigDecimal vii5) {
        this.vii5 = vii5;
    }

    public BigDecimal getVii6() {
        return vii6;
    }

    public void setVii6(BigDecimal vii6) {
        this.vii6 = vii6;
    }

    public BigDecimal getVii7() {
        return vii7;
    }

    public void setVii7(BigDecimal vii7) {
        this.vii7 = vii7;
    }

    public BigDecimal getViii1() {
        return viii1;
    }

    public void setViii1(BigDecimal viii1) {
        this.viii1 = viii1;
    }

    public BigDecimal getViii2() {
        return viii2;
    }

    public void setViii2(BigDecimal viii2) {
        this.viii2 = viii2;
    }

    public BigDecimal getViii3() {
        return viii3;
    }

    public void setViii3(BigDecimal viii3) {
        this.viii3 = viii3;
    }

    public Integer getIx1() {
        return ix1;
    }

    public void setIx1(Integer ix1) {
        this.ix1 = ix1;
    }

    public Integer getIx2() {
        return ix2;
    }

    public void setIx2(Integer ix2) {
        this.ix2 = ix2;
    }

    public BigDecimal getIx3() {
        return ix3;
    }

    public void setIx3(BigDecimal ix3) {
        this.ix3 = ix3;
    }

    public String getX11() {
        return x11;
    }

    public void setX11(String x11) {
        this.x11 = x11;
    }

    public Character getX12() {
        return x12;
    }

    public void setX12(Character x12) {
        this.x12 = x12;
    }

    public Character getX13() {
        return x13;
    }

    public void setX13(Character x13) {
        this.x13 = x13;
    }

    public BigDecimal getX2() {
        return x2;
    }

    public void setX2(BigDecimal x2) {
        this.x2 = x2;
    }

    public BigDecimal getX3() {
        return x3;
    }

    public void setX3(BigDecimal x3) {
        this.x3 = x3;
    }

    public BigDecimal getX4() {
        return x4;
    }

    public void setX4(BigDecimal x4) {
        this.x4 = x4;
    }

    public BigDecimal getX5() {
        return x5;
    }

    public void setX5(BigDecimal x5) {
        this.x5 = x5;
    }

    public Character getX6() {
        return x6;
    }

    public void setX6(Character x6) {
        this.x6 = x6;
    }

    public BigDecimal getIxKwotaDoZwrotu() {
        return ixKwotaDoZwrotu;
    }

    public void setIxKwotaDoZwrotu(BigDecimal ixKwotaDoZwrotu) {
        this.ixKwotaDoZwrotu = ixKwotaDoZwrotu;
    }

    public BigDecimal getIxKwotaDoZapl() {
        return ixKwotaDoZapl;
    }

    public void setIxKwotaDoZapl(BigDecimal ixKwotaDoZapl) {
        this.ixKwotaDoZapl = ixKwotaDoZapl;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Character getXi1() {
        return xi1;
    }

    public void setXi1(Character xi1) {
        this.xi1 = xi1;
    }

    public BigDecimal getXi2() {
        return xi2;
    }

    public void setXi2(BigDecimal xi2) {
        this.xi2 = xi2;
    }

    public BigDecimal getXi3() {
        return xi3;
    }

    public void setXi3(BigDecimal xi3) {
        this.xi3 = xi3;
    }

    public BigDecimal getXi4() {
        return xi4;
    }

    public void setXi4(BigDecimal xi4) {
        this.xi4 = xi4;
    }

    public Character getXi5() {
        return xi5;
    }

    public void setXi5(Character xi5) {
        this.xi5 = xi5;
    }

    public BigDecimal getXi6() {
        return xi6;
    }

    public void setXi6(BigDecimal xi6) {
        this.xi6 = xi6;
    }

    public BigDecimal getXi7() {
        return xi7;
    }

    public void setXi7(BigDecimal xi7) {
        this.xi7 = xi7;
    }

    public BigDecimal getXi8() {
        return xi8;
    }

    public void setXi8(BigDecimal xi8) {
        this.xi8 = xi8;
    }

    public Character getXi9() {
        return xi9;
    }

    public void setXi9(Character xi9) {
        this.xi9 = xi9;
    }

    public BigDecimal getXi10() {
        return xi10;
    }

    public void setXi10(BigDecimal xi10) {
        this.xi10 = xi10;
    }

    public BigDecimal getXi11() {
        return xi11;
    }

    public void setXi11(BigDecimal xi11) {
        this.xi11 = xi11;
    }

    public Character getXi12() {
        return xi12;
    }

    public void setXi12(Character xi12) {
        this.xi12 = xi12;
    }

    public BigDecimal getXi13() {
        return xi13;
    }

    public void setXi13(BigDecimal xi13) {
        this.xi13 = xi13;
    }

    public Character getXi14() {
        return xi14;
    }

    public void setXi14(Character xi14) {
        this.xi14 = xi14;
    }

    public BigDecimal getXi15() {
        return xi15;
    }

    public void setXi15(BigDecimal xi15) {
        this.xi15 = xi15;
    }

    public BigDecimal getXi16() {
        return xi16;
    }

    public void setXi16(BigDecimal xi16) {
        this.xi16 = xi16;
    }

    public BigDecimal getXi17() {
        return xi17;
    }

    public void setXi17(BigDecimal xi17) {
        this.xi17 = xi17;
    }

    public Character getXi18() {
        return xi18;
    }

    public void setXi18(Character xi18) {
        this.xi18 = xi18;
    }

    public BigDecimal getXi19() {
        return xi19;
    }

    public void setXi19(BigDecimal xi19) {
        this.xi19 = xi19;
    }

    public BigDecimal getXi20() {
        return xi20;
    }

    public void setXi20(BigDecimal xi20) {
        this.xi20 = xi20;
    }

    public Short getXii1() {
        return xii1;
    }

    public void setXii1(Short xii1) {
        this.xii1 = xii1;
    }

    public BigDecimal getXii2() {
        return xii2;
    }

    public void setXii2(BigDecimal xii2) {
        this.xii2 = xii2;
    }

    public BigDecimal getXii3() {
        return xii3;
    }

    public void setXii3(BigDecimal xii3) {
        this.xii3 = xii3;
    }

    public BigDecimal getXii4() {
        return xii4;
    }

    public void setXii4(BigDecimal xii4) {
        this.xii4 = xii4;
    }

    public BigDecimal getXii5() {
        return xii5;
    }

    public void setXii5(BigDecimal xii5) {
        this.xii5 = xii5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DokrozlDeklaracja)) {
            return false;
        }
        DokrozlDeklaracja other = (DokrozlDeklaracja) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.DokrozlDeklaracja[ idDokument=" + idDokument + " ]";
    }
    
}
