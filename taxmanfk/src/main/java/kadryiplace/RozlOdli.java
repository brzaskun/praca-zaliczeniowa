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
@Table(name = "rozl_odli", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RozlOdli.findAll", query = "SELECT r FROM RozlOdli r"),
    @NamedQuery(name = "RozlOdli.findByRodSerial", query = "SELECT r FROM RozlOdli r WHERE r.rodSerial = :rodSerial"),
    @NamedQuery(name = "RozlOdli.findByRodK33", query = "SELECT r FROM RozlOdli r WHERE r.rodK33 = :rodK33"),
    @NamedQuery(name = "RozlOdli.findByRodK34", query = "SELECT r FROM RozlOdli r WHERE r.rodK34 = :rodK34"),
    @NamedQuery(name = "RozlOdli.findByRodK35", query = "SELECT r FROM RozlOdli r WHERE r.rodK35 = :rodK35"),
    @NamedQuery(name = "RozlOdli.findByRodK36", query = "SELECT r FROM RozlOdli r WHERE r.rodK36 = :rodK36"),
    @NamedQuery(name = "RozlOdli.findByRodK37", query = "SELECT r FROM RozlOdli r WHERE r.rodK37 = :rodK37"),
    @NamedQuery(name = "RozlOdli.findByRodK38", query = "SELECT r FROM RozlOdli r WHERE r.rodK38 = :rodK38"),
    @NamedQuery(name = "RozlOdli.findByRodK39", query = "SELECT r FROM RozlOdli r WHERE r.rodK39 = :rodK39"),
    @NamedQuery(name = "RozlOdli.findByRodK40", query = "SELECT r FROM RozlOdli r WHERE r.rodK40 = :rodK40"),
    @NamedQuery(name = "RozlOdli.findByRodK41", query = "SELECT r FROM RozlOdli r WHERE r.rodK41 = :rodK41"),
    @NamedQuery(name = "RozlOdli.findByRodK42", query = "SELECT r FROM RozlOdli r WHERE r.rodK42 = :rodK42"),
    @NamedQuery(name = "RozlOdli.findByRodK43", query = "SELECT r FROM RozlOdli r WHERE r.rodK43 = :rodK43"),
    @NamedQuery(name = "RozlOdli.findByRodK44", query = "SELECT r FROM RozlOdli r WHERE r.rodK44 = :rodK44"),
    @NamedQuery(name = "RozlOdli.findByRodK45", query = "SELECT r FROM RozlOdli r WHERE r.rodK45 = :rodK45"),
    @NamedQuery(name = "RozlOdli.findByRodK46", query = "SELECT r FROM RozlOdli r WHERE r.rodK46 = :rodK46"),
    @NamedQuery(name = "RozlOdli.findByRodK47", query = "SELECT r FROM RozlOdli r WHERE r.rodK47 = :rodK47"),
    @NamedQuery(name = "RozlOdli.findByRodK48", query = "SELECT r FROM RozlOdli r WHERE r.rodK48 = :rodK48"),
    @NamedQuery(name = "RozlOdli.findByRodK50", query = "SELECT r FROM RozlOdli r WHERE r.rodK50 = :rodK50"),
    @NamedQuery(name = "RozlOdli.findByRodK51", query = "SELECT r FROM RozlOdli r WHERE r.rodK51 = :rodK51"),
    @NamedQuery(name = "RozlOdli.findByRodK52", query = "SELECT r FROM RozlOdli r WHERE r.rodK52 = :rodK52"),
    @NamedQuery(name = "RozlOdli.findByRodK53", query = "SELECT r FROM RozlOdli r WHERE r.rodK53 = :rodK53"),
    @NamedQuery(name = "RozlOdli.findByRodK54", query = "SELECT r FROM RozlOdli r WHERE r.rodK54 = :rodK54"),
    @NamedQuery(name = "RozlOdli.findByRodK55", query = "SELECT r FROM RozlOdli r WHERE r.rodK55 = :rodK55"),
    @NamedQuery(name = "RozlOdli.findByRodK57", query = "SELECT r FROM RozlOdli r WHERE r.rodK57 = :rodK57"),
    @NamedQuery(name = "RozlOdli.findByRodK58", query = "SELECT r FROM RozlOdli r WHERE r.rodK58 = :rodK58"),
    @NamedQuery(name = "RozlOdli.findByRodK59", query = "SELECT r FROM RozlOdli r WHERE r.rodK59 = :rodK59"),
    @NamedQuery(name = "RozlOdli.findByRodK60", query = "SELECT r FROM RozlOdli r WHERE r.rodK60 = :rodK60"),
    @NamedQuery(name = "RozlOdli.findByRodK62", query = "SELECT r FROM RozlOdli r WHERE r.rodK62 = :rodK62"),
    @NamedQuery(name = "RozlOdli.findByRodK64", query = "SELECT r FROM RozlOdli r WHERE r.rodK64 = :rodK64"),
    @NamedQuery(name = "RozlOdli.findByRodK66", query = "SELECT r FROM RozlOdli r WHERE r.rodK66 = :rodK66"),
    @NamedQuery(name = "RozlOdli.findByRodK67", query = "SELECT r FROM RozlOdli r WHERE r.rodK67 = :rodK67"),
    @NamedQuery(name = "RozlOdli.findByRodK68", query = "SELECT r FROM RozlOdli r WHERE r.rodK68 = :rodK68"),
    @NamedQuery(name = "RozlOdli.findByRodK70", query = "SELECT r FROM RozlOdli r WHERE r.rodK70 = :rodK70"),
    @NamedQuery(name = "RozlOdli.findByRodK77", query = "SELECT r FROM RozlOdli r WHERE r.rodK77 = :rodK77"),
    @NamedQuery(name = "RozlOdli.findByRodK78", query = "SELECT r FROM RozlOdli r WHERE r.rodK78 = :rodK78"),
    @NamedQuery(name = "RozlOdli.findByRodK80", query = "SELECT r FROM RozlOdli r WHERE r.rodK80 = :rodK80"),
    @NamedQuery(name = "RozlOdli.findByRodK83", query = "SELECT r FROM RozlOdli r WHERE r.rodK83 = :rodK83"),
    @NamedQuery(name = "RozlOdli.findByRodK86", query = "SELECT r FROM RozlOdli r WHERE r.rodK86 = :rodK86"),
    @NamedQuery(name = "RozlOdli.findByRodK95", query = "SELECT r FROM RozlOdli r WHERE r.rodK95 = :rodK95"),
    @NamedQuery(name = "RozlOdli.findByRodK96", query = "SELECT r FROM RozlOdli r WHERE r.rodK96 = :rodK96"),
    @NamedQuery(name = "RozlOdli.findByRodK31", query = "SELECT r FROM RozlOdli r WHERE r.rodK31 = :rodK31"),
    @NamedQuery(name = "RozlOdli.findByRodTyp", query = "SELECT r FROM RozlOdli r WHERE r.rodTyp = :rodTyp"),
    @NamedQuery(name = "RozlOdli.findByRodK32", query = "SELECT r FROM RozlOdli r WHERE r.rodK32 = :rodK32"),
    @NamedQuery(name = "RozlOdli.findByRodK49", query = "SELECT r FROM RozlOdli r WHERE r.rodK49 = :rodK49"),
    @NamedQuery(name = "RozlOdli.findByRodK56", query = "SELECT r FROM RozlOdli r WHERE r.rodK56 = :rodK56"),
    @NamedQuery(name = "RozlOdli.findByRodK61", query = "SELECT r FROM RozlOdli r WHERE r.rodK61 = :rodK61"),
    @NamedQuery(name = "RozlOdli.findByRodK69", query = "SELECT r FROM RozlOdli r WHERE r.rodK69 = :rodK69"),
    @NamedQuery(name = "RozlOdli.findByRodK79", query = "SELECT r FROM RozlOdli r WHERE r.rodK79 = :rodK79"),
    @NamedQuery(name = "RozlOdli.findByRodDec1Nr", query = "SELECT r FROM RozlOdli r WHERE r.rodDec1Nr = :rodDec1Nr"),
    @NamedQuery(name = "RozlOdli.findByRodDec1Data", query = "SELECT r FROM RozlOdli r WHERE r.rodDec1Data = :rodDec1Data"),
    @NamedQuery(name = "RozlOdli.findByRodDec2Nr", query = "SELECT r FROM RozlOdli r WHERE r.rodDec2Nr = :rodDec2Nr"),
    @NamedQuery(name = "RozlOdli.findByRodDec2Data", query = "SELECT r FROM RozlOdli r WHERE r.rodDec2Data = :rodDec2Data"),
    @NamedQuery(name = "RozlOdli.findByRodChar1", query = "SELECT r FROM RozlOdli r WHERE r.rodChar1 = :rodChar1"),
    @NamedQuery(name = "RozlOdli.findByRodChar2", query = "SELECT r FROM RozlOdli r WHERE r.rodChar2 = :rodChar2"),
    @NamedQuery(name = "RozlOdli.findByRodChar3", query = "SELECT r FROM RozlOdli r WHERE r.rodChar3 = :rodChar3"),
    @NamedQuery(name = "RozlOdli.findByRodChar4", query = "SELECT r FROM RozlOdli r WHERE r.rodChar4 = :rodChar4"),
    @NamedQuery(name = "RozlOdli.findByRodK188", query = "SELECT r FROM RozlOdli r WHERE r.rodK188 = :rodK188"),
    @NamedQuery(name = "RozlOdli.findByRodNum1", query = "SELECT r FROM RozlOdli r WHERE r.rodNum1 = :rodNum1"),
    @NamedQuery(name = "RozlOdli.findByRodNum2", query = "SELECT r FROM RozlOdli r WHERE r.rodNum2 = :rodNum2"),
    @NamedQuery(name = "RozlOdli.findByRodNum3", query = "SELECT r FROM RozlOdli r WHERE r.rodNum3 = :rodNum3"),
    @NamedQuery(name = "RozlOdli.findByRodNum4", query = "SELECT r FROM RozlOdli r WHERE r.rodNum4 = :rodNum4"),
    @NamedQuery(name = "RozlOdli.findByRodNum5", query = "SELECT r FROM RozlOdli r WHERE r.rodNum5 = :rodNum5"),
    @NamedQuery(name = "RozlOdli.findByRodNum6", query = "SELECT r FROM RozlOdli r WHERE r.rodNum6 = :rodNum6"),
    @NamedQuery(name = "RozlOdli.findByRodNum7", query = "SELECT r FROM RozlOdli r WHERE r.rodNum7 = :rodNum7"),
    @NamedQuery(name = "RozlOdli.findByRodNum8", query = "SELECT r FROM RozlOdli r WHERE r.rodNum8 = :rodNum8"),
    @NamedQuery(name = "RozlOdli.findByRodDate1", query = "SELECT r FROM RozlOdli r WHERE r.rodDate1 = :rodDate1"),
    @NamedQuery(name = "RozlOdli.findByRodDate2", query = "SELECT r FROM RozlOdli r WHERE r.rodDate2 = :rodDate2")})
public class RozlOdli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rod_serial", nullable = false)
    private Integer rodSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rod_k_33", precision = 13, scale = 2)
    private BigDecimal rodK33;
    @Column(name = "rod_k_34", precision = 13, scale = 2)
    private BigDecimal rodK34;
    @Column(name = "rod_k_35", precision = 13, scale = 2)
    private BigDecimal rodK35;
    @Column(name = "rod_k_36", precision = 13, scale = 2)
    private BigDecimal rodK36;
    @Column(name = "rod_k_37", precision = 13, scale = 2)
    private BigDecimal rodK37;
    @Column(name = "rod_k_38", precision = 13, scale = 2)
    private BigDecimal rodK38;
    @Column(name = "rod_k_39", precision = 13, scale = 2)
    private BigDecimal rodK39;
    @Column(name = "rod_k_40", precision = 13, scale = 2)
    private BigDecimal rodK40;
    @Column(name = "rod_k_41", precision = 13, scale = 2)
    private BigDecimal rodK41;
    @Column(name = "rod_k_42", precision = 13, scale = 2)
    private BigDecimal rodK42;
    @Column(name = "rod_k_43", precision = 13, scale = 2)
    private BigDecimal rodK43;
    @Column(name = "rod_k_44", precision = 13, scale = 2)
    private BigDecimal rodK44;
    @Column(name = "rod_k_45", precision = 13, scale = 2)
    private BigDecimal rodK45;
    @Column(name = "rod_k_46", precision = 13, scale = 2)
    private BigDecimal rodK46;
    @Column(name = "rod_k_47", precision = 13, scale = 2)
    private BigDecimal rodK47;
    @Column(name = "rod_k_48", precision = 13, scale = 2)
    private BigDecimal rodK48;
    @Column(name = "rod_k_50", precision = 13, scale = 2)
    private BigDecimal rodK50;
    @Column(name = "rod_k_51", precision = 13, scale = 2)
    private BigDecimal rodK51;
    @Column(name = "rod_k_52", precision = 13, scale = 2)
    private BigDecimal rodK52;
    @Column(name = "rod_k_53", precision = 13, scale = 2)
    private BigDecimal rodK53;
    @Column(name = "rod_k_54", precision = 13, scale = 2)
    private BigDecimal rodK54;
    @Column(name = "rod_k_55", precision = 13, scale = 2)
    private BigDecimal rodK55;
    @Column(name = "rod_k_57", precision = 13, scale = 2)
    private BigDecimal rodK57;
    @Column(name = "rod_k_58", precision = 13, scale = 2)
    private BigDecimal rodK58;
    @Column(name = "rod_k_59", precision = 13, scale = 2)
    private BigDecimal rodK59;
    @Column(name = "rod_k_60", precision = 13, scale = 2)
    private BigDecimal rodK60;
    @Column(name = "rod_k_62", precision = 13, scale = 2)
    private BigDecimal rodK62;
    @Column(name = "rod_k_64", precision = 13, scale = 2)
    private BigDecimal rodK64;
    @Column(name = "rod_k_66", precision = 13, scale = 2)
    private BigDecimal rodK66;
    @Column(name = "rod_k_67", precision = 13, scale = 2)
    private BigDecimal rodK67;
    @Column(name = "rod_k_68", precision = 13, scale = 2)
    private BigDecimal rodK68;
    @Column(name = "rod_k_70", precision = 13, scale = 2)
    private BigDecimal rodK70;
    @Column(name = "rod_k_77", precision = 13, scale = 2)
    private BigDecimal rodK77;
    @Column(name = "rod_k_78", precision = 13, scale = 2)
    private BigDecimal rodK78;
    @Column(name = "rod_k_80", precision = 13, scale = 2)
    private BigDecimal rodK80;
    @Column(name = "rod_k_83", precision = 13, scale = 2)
    private BigDecimal rodK83;
    @Column(name = "rod_k_86", precision = 13, scale = 2)
    private BigDecimal rodK86;
    @Column(name = "rod_k_95", precision = 13, scale = 2)
    private BigDecimal rodK95;
    @Column(name = "rod_k_96", precision = 13, scale = 2)
    private BigDecimal rodK96;
    @Column(name = "rod_k_31", precision = 13, scale = 2)
    private BigDecimal rodK31;
    @Column(name = "rod_typ")
    private Character rodTyp;
    @Column(name = "rod_k_32", precision = 13, scale = 2)
    private BigDecimal rodK32;
    @Size(max = 64)
    @Column(name = "rod_k_49", length = 64)
    private String rodK49;
    @Size(max = 64)
    @Column(name = "rod_k_56", length = 64)
    private String rodK56;
    @Size(max = 64)
    @Column(name = "rod_k_61", length = 64)
    private String rodK61;
    @Size(max = 64)
    @Column(name = "rod_k_69", length = 64)
    private String rodK69;
    @Size(max = 64)
    @Column(name = "rod_k_79", length = 64)
    private String rodK79;
    @Size(max = 32)
    @Column(name = "rod_dec1_nr", length = 32)
    private String rodDec1Nr;
    @Column(name = "rod_dec1_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rodDec1Data;
    @Size(max = 32)
    @Column(name = "rod_dec2_nr", length = 32)
    private String rodDec2Nr;
    @Column(name = "rod_dec2_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rodDec2Data;
    @Column(name = "rod_char_1")
    private Character rodChar1;
    @Column(name = "rod_char_2")
    private Character rodChar2;
    @Column(name = "rod_char_3")
    private Character rodChar3;
    @Column(name = "rod_char_4")
    private Character rodChar4;
    @Column(name = "rod_k_188", precision = 13, scale = 2)
    private BigDecimal rodK188;
    @Column(name = "rod_num_1", precision = 13, scale = 2)
    private BigDecimal rodNum1;
    @Column(name = "rod_num_2", precision = 13, scale = 2)
    private BigDecimal rodNum2;
    @Column(name = "rod_num_3", precision = 13, scale = 2)
    private BigDecimal rodNum3;
    @Column(name = "rod_num_4", precision = 13, scale = 2)
    private BigDecimal rodNum4;
    @Column(name = "rod_num_5", precision = 13, scale = 2)
    private BigDecimal rodNum5;
    @Column(name = "rod_num_6", precision = 13, scale = 2)
    private BigDecimal rodNum6;
    @Column(name = "rod_num_7", precision = 17, scale = 6)
    private BigDecimal rodNum7;
    @Column(name = "rod_num_8", precision = 17, scale = 6)
    private BigDecimal rodNum8;
    @Column(name = "rod_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rodDate1;
    @Column(name = "rod_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rodDate2;
    @JoinColumn(name = "rod_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres rodOkrSerial;
    @JoinColumn(name = "rod_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba rodOsoSerial;

    public RozlOdli() {
    }

    public RozlOdli(Integer rodSerial) {
        this.rodSerial = rodSerial;
    }

    public Integer getRodSerial() {
        return rodSerial;
    }

    public void setRodSerial(Integer rodSerial) {
        this.rodSerial = rodSerial;
    }

    public BigDecimal getRodK33() {
        return rodK33;
    }

    public void setRodK33(BigDecimal rodK33) {
        this.rodK33 = rodK33;
    }

    public BigDecimal getRodK34() {
        return rodK34;
    }

    public void setRodK34(BigDecimal rodK34) {
        this.rodK34 = rodK34;
    }

    public BigDecimal getRodK35() {
        return rodK35;
    }

    public void setRodK35(BigDecimal rodK35) {
        this.rodK35 = rodK35;
    }

    public BigDecimal getRodK36() {
        return rodK36;
    }

    public void setRodK36(BigDecimal rodK36) {
        this.rodK36 = rodK36;
    }

    public BigDecimal getRodK37() {
        return rodK37;
    }

    public void setRodK37(BigDecimal rodK37) {
        this.rodK37 = rodK37;
    }

    public BigDecimal getRodK38() {
        return rodK38;
    }

    public void setRodK38(BigDecimal rodK38) {
        this.rodK38 = rodK38;
    }

    public BigDecimal getRodK39() {
        return rodK39;
    }

    public void setRodK39(BigDecimal rodK39) {
        this.rodK39 = rodK39;
    }

    public BigDecimal getRodK40() {
        return rodK40;
    }

    public void setRodK40(BigDecimal rodK40) {
        this.rodK40 = rodK40;
    }

    public BigDecimal getRodK41() {
        return rodK41;
    }

    public void setRodK41(BigDecimal rodK41) {
        this.rodK41 = rodK41;
    }

    public BigDecimal getRodK42() {
        return rodK42;
    }

    public void setRodK42(BigDecimal rodK42) {
        this.rodK42 = rodK42;
    }

    public BigDecimal getRodK43() {
        return rodK43;
    }

    public void setRodK43(BigDecimal rodK43) {
        this.rodK43 = rodK43;
    }

    public BigDecimal getRodK44() {
        return rodK44;
    }

    public void setRodK44(BigDecimal rodK44) {
        this.rodK44 = rodK44;
    }

    public BigDecimal getRodK45() {
        return rodK45;
    }

    public void setRodK45(BigDecimal rodK45) {
        this.rodK45 = rodK45;
    }

    public BigDecimal getRodK46() {
        return rodK46;
    }

    public void setRodK46(BigDecimal rodK46) {
        this.rodK46 = rodK46;
    }

    public BigDecimal getRodK47() {
        return rodK47;
    }

    public void setRodK47(BigDecimal rodK47) {
        this.rodK47 = rodK47;
    }

    public BigDecimal getRodK48() {
        return rodK48;
    }

    public void setRodK48(BigDecimal rodK48) {
        this.rodK48 = rodK48;
    }

    public BigDecimal getRodK50() {
        return rodK50;
    }

    public void setRodK50(BigDecimal rodK50) {
        this.rodK50 = rodK50;
    }

    public BigDecimal getRodK51() {
        return rodK51;
    }

    public void setRodK51(BigDecimal rodK51) {
        this.rodK51 = rodK51;
    }

    public BigDecimal getRodK52() {
        return rodK52;
    }

    public void setRodK52(BigDecimal rodK52) {
        this.rodK52 = rodK52;
    }

    public BigDecimal getRodK53() {
        return rodK53;
    }

    public void setRodK53(BigDecimal rodK53) {
        this.rodK53 = rodK53;
    }

    public BigDecimal getRodK54() {
        return rodK54;
    }

    public void setRodK54(BigDecimal rodK54) {
        this.rodK54 = rodK54;
    }

    public BigDecimal getRodK55() {
        return rodK55;
    }

    public void setRodK55(BigDecimal rodK55) {
        this.rodK55 = rodK55;
    }

    public BigDecimal getRodK57() {
        return rodK57;
    }

    public void setRodK57(BigDecimal rodK57) {
        this.rodK57 = rodK57;
    }

    public BigDecimal getRodK58() {
        return rodK58;
    }

    public void setRodK58(BigDecimal rodK58) {
        this.rodK58 = rodK58;
    }

    public BigDecimal getRodK59() {
        return rodK59;
    }

    public void setRodK59(BigDecimal rodK59) {
        this.rodK59 = rodK59;
    }

    public BigDecimal getRodK60() {
        return rodK60;
    }

    public void setRodK60(BigDecimal rodK60) {
        this.rodK60 = rodK60;
    }

    public BigDecimal getRodK62() {
        return rodK62;
    }

    public void setRodK62(BigDecimal rodK62) {
        this.rodK62 = rodK62;
    }

    public BigDecimal getRodK64() {
        return rodK64;
    }

    public void setRodK64(BigDecimal rodK64) {
        this.rodK64 = rodK64;
    }

    public BigDecimal getRodK66() {
        return rodK66;
    }

    public void setRodK66(BigDecimal rodK66) {
        this.rodK66 = rodK66;
    }

    public BigDecimal getRodK67() {
        return rodK67;
    }

    public void setRodK67(BigDecimal rodK67) {
        this.rodK67 = rodK67;
    }

    public BigDecimal getRodK68() {
        return rodK68;
    }

    public void setRodK68(BigDecimal rodK68) {
        this.rodK68 = rodK68;
    }

    public BigDecimal getRodK70() {
        return rodK70;
    }

    public void setRodK70(BigDecimal rodK70) {
        this.rodK70 = rodK70;
    }

    public BigDecimal getRodK77() {
        return rodK77;
    }

    public void setRodK77(BigDecimal rodK77) {
        this.rodK77 = rodK77;
    }

    public BigDecimal getRodK78() {
        return rodK78;
    }

    public void setRodK78(BigDecimal rodK78) {
        this.rodK78 = rodK78;
    }

    public BigDecimal getRodK80() {
        return rodK80;
    }

    public void setRodK80(BigDecimal rodK80) {
        this.rodK80 = rodK80;
    }

    public BigDecimal getRodK83() {
        return rodK83;
    }

    public void setRodK83(BigDecimal rodK83) {
        this.rodK83 = rodK83;
    }

    public BigDecimal getRodK86() {
        return rodK86;
    }

    public void setRodK86(BigDecimal rodK86) {
        this.rodK86 = rodK86;
    }

    public BigDecimal getRodK95() {
        return rodK95;
    }

    public void setRodK95(BigDecimal rodK95) {
        this.rodK95 = rodK95;
    }

    public BigDecimal getRodK96() {
        return rodK96;
    }

    public void setRodK96(BigDecimal rodK96) {
        this.rodK96 = rodK96;
    }

    public BigDecimal getRodK31() {
        return rodK31;
    }

    public void setRodK31(BigDecimal rodK31) {
        this.rodK31 = rodK31;
    }

    public Character getRodTyp() {
        return rodTyp;
    }

    public void setRodTyp(Character rodTyp) {
        this.rodTyp = rodTyp;
    }

    public BigDecimal getRodK32() {
        return rodK32;
    }

    public void setRodK32(BigDecimal rodK32) {
        this.rodK32 = rodK32;
    }

    public String getRodK49() {
        return rodK49;
    }

    public void setRodK49(String rodK49) {
        this.rodK49 = rodK49;
    }

    public String getRodK56() {
        return rodK56;
    }

    public void setRodK56(String rodK56) {
        this.rodK56 = rodK56;
    }

    public String getRodK61() {
        return rodK61;
    }

    public void setRodK61(String rodK61) {
        this.rodK61 = rodK61;
    }

    public String getRodK69() {
        return rodK69;
    }

    public void setRodK69(String rodK69) {
        this.rodK69 = rodK69;
    }

    public String getRodK79() {
        return rodK79;
    }

    public void setRodK79(String rodK79) {
        this.rodK79 = rodK79;
    }

    public String getRodDec1Nr() {
        return rodDec1Nr;
    }

    public void setRodDec1Nr(String rodDec1Nr) {
        this.rodDec1Nr = rodDec1Nr;
    }

    public Date getRodDec1Data() {
        return rodDec1Data;
    }

    public void setRodDec1Data(Date rodDec1Data) {
        this.rodDec1Data = rodDec1Data;
    }

    public String getRodDec2Nr() {
        return rodDec2Nr;
    }

    public void setRodDec2Nr(String rodDec2Nr) {
        this.rodDec2Nr = rodDec2Nr;
    }

    public Date getRodDec2Data() {
        return rodDec2Data;
    }

    public void setRodDec2Data(Date rodDec2Data) {
        this.rodDec2Data = rodDec2Data;
    }

    public Character getRodChar1() {
        return rodChar1;
    }

    public void setRodChar1(Character rodChar1) {
        this.rodChar1 = rodChar1;
    }

    public Character getRodChar2() {
        return rodChar2;
    }

    public void setRodChar2(Character rodChar2) {
        this.rodChar2 = rodChar2;
    }

    public Character getRodChar3() {
        return rodChar3;
    }

    public void setRodChar3(Character rodChar3) {
        this.rodChar3 = rodChar3;
    }

    public Character getRodChar4() {
        return rodChar4;
    }

    public void setRodChar4(Character rodChar4) {
        this.rodChar4 = rodChar4;
    }

    public BigDecimal getRodK188() {
        return rodK188;
    }

    public void setRodK188(BigDecimal rodK188) {
        this.rodK188 = rodK188;
    }

    public BigDecimal getRodNum1() {
        return rodNum1;
    }

    public void setRodNum1(BigDecimal rodNum1) {
        this.rodNum1 = rodNum1;
    }

    public BigDecimal getRodNum2() {
        return rodNum2;
    }

    public void setRodNum2(BigDecimal rodNum2) {
        this.rodNum2 = rodNum2;
    }

    public BigDecimal getRodNum3() {
        return rodNum3;
    }

    public void setRodNum3(BigDecimal rodNum3) {
        this.rodNum3 = rodNum3;
    }

    public BigDecimal getRodNum4() {
        return rodNum4;
    }

    public void setRodNum4(BigDecimal rodNum4) {
        this.rodNum4 = rodNum4;
    }

    public BigDecimal getRodNum5() {
        return rodNum5;
    }

    public void setRodNum5(BigDecimal rodNum5) {
        this.rodNum5 = rodNum5;
    }

    public BigDecimal getRodNum6() {
        return rodNum6;
    }

    public void setRodNum6(BigDecimal rodNum6) {
        this.rodNum6 = rodNum6;
    }

    public BigDecimal getRodNum7() {
        return rodNum7;
    }

    public void setRodNum7(BigDecimal rodNum7) {
        this.rodNum7 = rodNum7;
    }

    public BigDecimal getRodNum8() {
        return rodNum8;
    }

    public void setRodNum8(BigDecimal rodNum8) {
        this.rodNum8 = rodNum8;
    }

    public Date getRodDate1() {
        return rodDate1;
    }

    public void setRodDate1(Date rodDate1) {
        this.rodDate1 = rodDate1;
    }

    public Date getRodDate2() {
        return rodDate2;
    }

    public void setRodDate2(Date rodDate2) {
        this.rodDate2 = rodDate2;
    }

    public Okres getRodOkrSerial() {
        return rodOkrSerial;
    }

    public void setRodOkrSerial(Okres rodOkrSerial) {
        this.rodOkrSerial = rodOkrSerial;
    }

    public Osoba getRodOsoSerial() {
        return rodOsoSerial;
    }

    public void setRodOsoSerial(Osoba rodOsoSerial) {
        this.rodOsoSerial = rodOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rodSerial != null ? rodSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RozlOdli)) {
            return false;
        }
        RozlOdli other = (RozlOdli) object;
        if ((this.rodSerial == null && other.rodSerial != null) || (this.rodSerial != null && !this.rodSerial.equals(other.rodSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.RozlOdli[ rodSerial=" + rodSerial + " ]";
    }
    
}
