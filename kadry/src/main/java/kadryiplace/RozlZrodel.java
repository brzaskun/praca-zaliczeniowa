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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rozl_zrodel", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rzr_okr_serial", "rzr_oso_serial"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RozlZrodel.findAll", query = "SELECT r FROM RozlZrodel r"),
    @NamedQuery(name = "RozlZrodel.findByRzrSerial", query = "SELECT r FROM RozlZrodel r WHERE r.rzrSerial = :rzrSerial"),
    @NamedQuery(name = "RozlZrodel.findByRzrK106", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK106 = :rzrK106"),
    @NamedQuery(name = "RozlZrodel.findByRzrK107", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK107 = :rzrK107"),
    @NamedQuery(name = "RozlZrodel.findByRzrK108", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK108 = :rzrK108"),
    @NamedQuery(name = "RozlZrodel.findByRzrK115", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK115 = :rzrK115"),
    @NamedQuery(name = "RozlZrodel.findByRzrK116", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK116 = :rzrK116"),
    @NamedQuery(name = "RozlZrodel.findByRzrK117", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK117 = :rzrK117"),
    @NamedQuery(name = "RozlZrodel.findByRzrK124", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK124 = :rzrK124"),
    @NamedQuery(name = "RozlZrodel.findByRzrK125", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK125 = :rzrK125"),
    @NamedQuery(name = "RozlZrodel.findByRzrK126", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK126 = :rzrK126"),
    @NamedQuery(name = "RozlZrodel.findByRzrK131", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK131 = :rzrK131"),
    @NamedQuery(name = "RozlZrodel.findByRzrK132", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK132 = :rzrK132"),
    @NamedQuery(name = "RozlZrodel.findByRzrK133", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK133 = :rzrK133"),
    @NamedQuery(name = "RozlZrodel.findByRzrK138", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK138 = :rzrK138"),
    @NamedQuery(name = "RozlZrodel.findByRzrK139", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK139 = :rzrK139"),
    @NamedQuery(name = "RozlZrodel.findByRzrK140", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK140 = :rzrK140"),
    @NamedQuery(name = "RozlZrodel.findByRzrK145", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK145 = :rzrK145"),
    @NamedQuery(name = "RozlZrodel.findByRzrK146", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK146 = :rzrK146"),
    @NamedQuery(name = "RozlZrodel.findByRzrK147", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK147 = :rzrK147"),
    @NamedQuery(name = "RozlZrodel.findByRzrK152", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK152 = :rzrK152"),
    @NamedQuery(name = "RozlZrodel.findByRzrK153", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK153 = :rzrK153"),
    @NamedQuery(name = "RozlZrodel.findByRzrK154", query = "SELECT r FROM RozlZrodel r WHERE r.rzrK154 = :rzrK154"),
    @NamedQuery(name = "RozlZrodel.findByRzrTyp", query = "SELECT r FROM RozlZrodel r WHERE r.rzrTyp = :rzrTyp"),
    @NamedQuery(name = "RozlZrodel.findByRzrKsiega", query = "SELECT r FROM RozlZrodel r WHERE r.rzrKsiega = :rzrKsiega"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln2", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln2 = :rzrDzialaln2"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln3", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln3 = :rzrDzialaln3"),
    @NamedQuery(name = "RozlZrodel.findByRzrNajem1", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNajem1 = :rzrNajem1"),
    @NamedQuery(name = "RozlZrodel.findByRzrNajem2", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNajem2 = :rzrNajem2"),
    @NamedQuery(name = "RozlZrodel.findByRzrNajem3", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNajem3 = :rzrNajem3"),
    @NamedQuery(name = "RozlZrodel.findByRzrNajem4", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNajem4 = :rzrNajem4"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln1", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln1 = :rzrDzialaln1"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum1", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum1 = :rzrNum1"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum2", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum2 = :rzrNum2"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum3", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum3 = :rzrNum3"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum4", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum4 = :rzrNum4"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum5", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum5 = :rzrNum5"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum6", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum6 = :rzrNum6"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum7", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum7 = :rzrNum7"),
    @NamedQuery(name = "RozlZrodel.findByRzrNum8", query = "SELECT r FROM RozlZrodel r WHERE r.rzrNum8 = :rzrNum8"),
    @NamedQuery(name = "RozlZrodel.findByRzrChar1", query = "SELECT r FROM RozlZrodel r WHERE r.rzrChar1 = :rzrChar1"),
    @NamedQuery(name = "RozlZrodel.findByRzrChar2", query = "SELECT r FROM RozlZrodel r WHERE r.rzrChar2 = :rzrChar2"),
    @NamedQuery(name = "RozlZrodel.findByRzrChar3", query = "SELECT r FROM RozlZrodel r WHERE r.rzrChar3 = :rzrChar3"),
    @NamedQuery(name = "RozlZrodel.findByRzrChar4", query = "SELECT r FROM RozlZrodel r WHERE r.rzrChar4 = :rzrChar4"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln4", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln4 = :rzrDzialaln4"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln4U", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln4U = :rzrDzialaln4U"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln4P", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln4P = :rzrDzialaln4P"),
    @NamedQuery(name = "RozlZrodel.findByRzrDzialaln4K", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDzialaln4K = :rzrDzialaln4K"),
    @NamedQuery(name = "RozlZrodel.findByRzrDate1", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDate1 = :rzrDate1"),
    @NamedQuery(name = "RozlZrodel.findByRzrDate2", query = "SELECT r FROM RozlZrodel r WHERE r.rzrDate2 = :rzrDate2")})
public class RozlZrodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzr_serial", nullable = false)
    private Integer rzrSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rzr_k_106", precision = 7, scale = 4)
    private BigDecimal rzrK106;
    @Column(name = "rzr_k_107", precision = 13, scale = 2)
    private BigDecimal rzrK107;
    @Column(name = "rzr_k_108", precision = 13, scale = 2)
    private BigDecimal rzrK108;
    @Column(name = "rzr_k_115", precision = 7, scale = 4)
    private BigDecimal rzrK115;
    @Column(name = "rzr_k_116", precision = 13, scale = 2)
    private BigDecimal rzrK116;
    @Column(name = "rzr_k_117", precision = 13, scale = 2)
    private BigDecimal rzrK117;
    @Column(name = "rzr_k_124", precision = 7, scale = 4)
    private BigDecimal rzrK124;
    @Column(name = "rzr_k_125", precision = 13, scale = 2)
    private BigDecimal rzrK125;
    @Column(name = "rzr_k_126", precision = 13, scale = 2)
    private BigDecimal rzrK126;
    @Column(name = "rzr_k_131", precision = 7, scale = 4)
    private BigDecimal rzrK131;
    @Column(name = "rzr_k_132", precision = 13, scale = 2)
    private BigDecimal rzrK132;
    @Column(name = "rzr_k_133", precision = 13, scale = 2)
    private BigDecimal rzrK133;
    @Column(name = "rzr_k_138", precision = 7, scale = 4)
    private BigDecimal rzrK138;
    @Column(name = "rzr_k_139", precision = 13, scale = 2)
    private BigDecimal rzrK139;
    @Column(name = "rzr_k_140", precision = 13, scale = 2)
    private BigDecimal rzrK140;
    @Column(name = "rzr_k_145", precision = 7, scale = 4)
    private BigDecimal rzrK145;
    @Column(name = "rzr_k_146", precision = 13, scale = 2)
    private BigDecimal rzrK146;
    @Column(name = "rzr_k_147", precision = 13, scale = 2)
    private BigDecimal rzrK147;
    @Column(name = "rzr_k_152", precision = 7, scale = 4)
    private BigDecimal rzrK152;
    @Column(name = "rzr_k_153", precision = 13, scale = 2)
    private BigDecimal rzrK153;
    @Column(name = "rzr_k_154", precision = 13, scale = 2)
    private BigDecimal rzrK154;
    @Column(name = "rzr_typ")
    private Character rzrTyp;
    @Column(name = "rzr_ksiega")
    private Character rzrKsiega;
    @Column(name = "rzr_dzialaln_2")
    private Character rzrDzialaln2;
    @Column(name = "rzr_dzialaln_3")
    private Character rzrDzialaln3;
    @Column(name = "rzr_najem_1")
    private Character rzrNajem1;
    @Column(name = "rzr_najem_2")
    private Character rzrNajem2;
    @Column(name = "rzr_najem_3")
    private Character rzrNajem3;
    @Column(name = "rzr_najem_4")
    private Character rzrNajem4;
    @Column(name = "rzr_dzialaln_1")
    private Character rzrDzialaln1;
    @Column(name = "rzr_num_1", precision = 13, scale = 2)
    private BigDecimal rzrNum1;
    @Column(name = "rzr_num_2", precision = 13, scale = 2)
    private BigDecimal rzrNum2;
    @Column(name = "rzr_num_3", precision = 13, scale = 2)
    private BigDecimal rzrNum3;
    @Column(name = "rzr_num_4", precision = 13, scale = 2)
    private BigDecimal rzrNum4;
    @Column(name = "rzr_num_5", precision = 13, scale = 2)
    private BigDecimal rzrNum5;
    @Column(name = "rzr_num_6", precision = 13, scale = 2)
    private BigDecimal rzrNum6;
    @Column(name = "rzr_num_7", precision = 17, scale = 6)
    private BigDecimal rzrNum7;
    @Column(name = "rzr_num_8", precision = 17, scale = 6)
    private BigDecimal rzrNum8;
    @Column(name = "rzr_char_1")
    private Character rzrChar1;
    @Column(name = "rzr_char_2")
    private Character rzrChar2;
    @Column(name = "rzr_char_3")
    private Character rzrChar3;
    @Column(name = "rzr_char_4")
    private Character rzrChar4;
    @Column(name = "rzr_dzialaln_4")
    private Character rzrDzialaln4;
    @Column(name = "rzr_dzialaln_4_u", precision = 7, scale = 4)
    private BigDecimal rzrDzialaln4U;
    @Column(name = "rzr_dzialaln_4_p", precision = 7, scale = 4)
    private BigDecimal rzrDzialaln4P;
    @Column(name = "rzr_dzialaln_4_k", precision = 7, scale = 4)
    private BigDecimal rzrDzialaln4K;
    @Column(name = "rzr_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzrDate1;
    @Column(name = "rzr_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzrDate2;
    @JoinColumn(name = "rzr_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres rzrOkrSerial;
    @JoinColumn(name = "rzr_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba rzrOsoSerial;

    public RozlZrodel() {
    }

    public RozlZrodel(Integer rzrSerial) {
        this.rzrSerial = rzrSerial;
    }

    public Integer getRzrSerial() {
        return rzrSerial;
    }

    public void setRzrSerial(Integer rzrSerial) {
        this.rzrSerial = rzrSerial;
    }

    public BigDecimal getRzrK106() {
        return rzrK106;
    }

    public void setRzrK106(BigDecimal rzrK106) {
        this.rzrK106 = rzrK106;
    }

    public BigDecimal getRzrK107() {
        return rzrK107;
    }

    public void setRzrK107(BigDecimal rzrK107) {
        this.rzrK107 = rzrK107;
    }

    public BigDecimal getRzrK108() {
        return rzrK108;
    }

    public void setRzrK108(BigDecimal rzrK108) {
        this.rzrK108 = rzrK108;
    }

    public BigDecimal getRzrK115() {
        return rzrK115;
    }

    public void setRzrK115(BigDecimal rzrK115) {
        this.rzrK115 = rzrK115;
    }

    public BigDecimal getRzrK116() {
        return rzrK116;
    }

    public void setRzrK116(BigDecimal rzrK116) {
        this.rzrK116 = rzrK116;
    }

    public BigDecimal getRzrK117() {
        return rzrK117;
    }

    public void setRzrK117(BigDecimal rzrK117) {
        this.rzrK117 = rzrK117;
    }

    public BigDecimal getRzrK124() {
        return rzrK124;
    }

    public void setRzrK124(BigDecimal rzrK124) {
        this.rzrK124 = rzrK124;
    }

    public BigDecimal getRzrK125() {
        return rzrK125;
    }

    public void setRzrK125(BigDecimal rzrK125) {
        this.rzrK125 = rzrK125;
    }

    public BigDecimal getRzrK126() {
        return rzrK126;
    }

    public void setRzrK126(BigDecimal rzrK126) {
        this.rzrK126 = rzrK126;
    }

    public BigDecimal getRzrK131() {
        return rzrK131;
    }

    public void setRzrK131(BigDecimal rzrK131) {
        this.rzrK131 = rzrK131;
    }

    public BigDecimal getRzrK132() {
        return rzrK132;
    }

    public void setRzrK132(BigDecimal rzrK132) {
        this.rzrK132 = rzrK132;
    }

    public BigDecimal getRzrK133() {
        return rzrK133;
    }

    public void setRzrK133(BigDecimal rzrK133) {
        this.rzrK133 = rzrK133;
    }

    public BigDecimal getRzrK138() {
        return rzrK138;
    }

    public void setRzrK138(BigDecimal rzrK138) {
        this.rzrK138 = rzrK138;
    }

    public BigDecimal getRzrK139() {
        return rzrK139;
    }

    public void setRzrK139(BigDecimal rzrK139) {
        this.rzrK139 = rzrK139;
    }

    public BigDecimal getRzrK140() {
        return rzrK140;
    }

    public void setRzrK140(BigDecimal rzrK140) {
        this.rzrK140 = rzrK140;
    }

    public BigDecimal getRzrK145() {
        return rzrK145;
    }

    public void setRzrK145(BigDecimal rzrK145) {
        this.rzrK145 = rzrK145;
    }

    public BigDecimal getRzrK146() {
        return rzrK146;
    }

    public void setRzrK146(BigDecimal rzrK146) {
        this.rzrK146 = rzrK146;
    }

    public BigDecimal getRzrK147() {
        return rzrK147;
    }

    public void setRzrK147(BigDecimal rzrK147) {
        this.rzrK147 = rzrK147;
    }

    public BigDecimal getRzrK152() {
        return rzrK152;
    }

    public void setRzrK152(BigDecimal rzrK152) {
        this.rzrK152 = rzrK152;
    }

    public BigDecimal getRzrK153() {
        return rzrK153;
    }

    public void setRzrK153(BigDecimal rzrK153) {
        this.rzrK153 = rzrK153;
    }

    public BigDecimal getRzrK154() {
        return rzrK154;
    }

    public void setRzrK154(BigDecimal rzrK154) {
        this.rzrK154 = rzrK154;
    }

    public Character getRzrTyp() {
        return rzrTyp;
    }

    public void setRzrTyp(Character rzrTyp) {
        this.rzrTyp = rzrTyp;
    }

    public Character getRzrKsiega() {
        return rzrKsiega;
    }

    public void setRzrKsiega(Character rzrKsiega) {
        this.rzrKsiega = rzrKsiega;
    }

    public Character getRzrDzialaln2() {
        return rzrDzialaln2;
    }

    public void setRzrDzialaln2(Character rzrDzialaln2) {
        this.rzrDzialaln2 = rzrDzialaln2;
    }

    public Character getRzrDzialaln3() {
        return rzrDzialaln3;
    }

    public void setRzrDzialaln3(Character rzrDzialaln3) {
        this.rzrDzialaln3 = rzrDzialaln3;
    }

    public Character getRzrNajem1() {
        return rzrNajem1;
    }

    public void setRzrNajem1(Character rzrNajem1) {
        this.rzrNajem1 = rzrNajem1;
    }

    public Character getRzrNajem2() {
        return rzrNajem2;
    }

    public void setRzrNajem2(Character rzrNajem2) {
        this.rzrNajem2 = rzrNajem2;
    }

    public Character getRzrNajem3() {
        return rzrNajem3;
    }

    public void setRzrNajem3(Character rzrNajem3) {
        this.rzrNajem3 = rzrNajem3;
    }

    public Character getRzrNajem4() {
        return rzrNajem4;
    }

    public void setRzrNajem4(Character rzrNajem4) {
        this.rzrNajem4 = rzrNajem4;
    }

    public Character getRzrDzialaln1() {
        return rzrDzialaln1;
    }

    public void setRzrDzialaln1(Character rzrDzialaln1) {
        this.rzrDzialaln1 = rzrDzialaln1;
    }

    public BigDecimal getRzrNum1() {
        return rzrNum1;
    }

    public void setRzrNum1(BigDecimal rzrNum1) {
        this.rzrNum1 = rzrNum1;
    }

    public BigDecimal getRzrNum2() {
        return rzrNum2;
    }

    public void setRzrNum2(BigDecimal rzrNum2) {
        this.rzrNum2 = rzrNum2;
    }

    public BigDecimal getRzrNum3() {
        return rzrNum3;
    }

    public void setRzrNum3(BigDecimal rzrNum3) {
        this.rzrNum3 = rzrNum3;
    }

    public BigDecimal getRzrNum4() {
        return rzrNum4;
    }

    public void setRzrNum4(BigDecimal rzrNum4) {
        this.rzrNum4 = rzrNum4;
    }

    public BigDecimal getRzrNum5() {
        return rzrNum5;
    }

    public void setRzrNum5(BigDecimal rzrNum5) {
        this.rzrNum5 = rzrNum5;
    }

    public BigDecimal getRzrNum6() {
        return rzrNum6;
    }

    public void setRzrNum6(BigDecimal rzrNum6) {
        this.rzrNum6 = rzrNum6;
    }

    public BigDecimal getRzrNum7() {
        return rzrNum7;
    }

    public void setRzrNum7(BigDecimal rzrNum7) {
        this.rzrNum7 = rzrNum7;
    }

    public BigDecimal getRzrNum8() {
        return rzrNum8;
    }

    public void setRzrNum8(BigDecimal rzrNum8) {
        this.rzrNum8 = rzrNum8;
    }

    public Character getRzrChar1() {
        return rzrChar1;
    }

    public void setRzrChar1(Character rzrChar1) {
        this.rzrChar1 = rzrChar1;
    }

    public Character getRzrChar2() {
        return rzrChar2;
    }

    public void setRzrChar2(Character rzrChar2) {
        this.rzrChar2 = rzrChar2;
    }

    public Character getRzrChar3() {
        return rzrChar3;
    }

    public void setRzrChar3(Character rzrChar3) {
        this.rzrChar3 = rzrChar3;
    }

    public Character getRzrChar4() {
        return rzrChar4;
    }

    public void setRzrChar4(Character rzrChar4) {
        this.rzrChar4 = rzrChar4;
    }

    public Character getRzrDzialaln4() {
        return rzrDzialaln4;
    }

    public void setRzrDzialaln4(Character rzrDzialaln4) {
        this.rzrDzialaln4 = rzrDzialaln4;
    }

    public BigDecimal getRzrDzialaln4U() {
        return rzrDzialaln4U;
    }

    public void setRzrDzialaln4U(BigDecimal rzrDzialaln4U) {
        this.rzrDzialaln4U = rzrDzialaln4U;
    }

    public BigDecimal getRzrDzialaln4P() {
        return rzrDzialaln4P;
    }

    public void setRzrDzialaln4P(BigDecimal rzrDzialaln4P) {
        this.rzrDzialaln4P = rzrDzialaln4P;
    }

    public BigDecimal getRzrDzialaln4K() {
        return rzrDzialaln4K;
    }

    public void setRzrDzialaln4K(BigDecimal rzrDzialaln4K) {
        this.rzrDzialaln4K = rzrDzialaln4K;
    }

    public Date getRzrDate1() {
        return rzrDate1;
    }

    public void setRzrDate1(Date rzrDate1) {
        this.rzrDate1 = rzrDate1;
    }

    public Date getRzrDate2() {
        return rzrDate2;
    }

    public void setRzrDate2(Date rzrDate2) {
        this.rzrDate2 = rzrDate2;
    }

    public Okres getRzrOkrSerial() {
        return rzrOkrSerial;
    }

    public void setRzrOkrSerial(Okres rzrOkrSerial) {
        this.rzrOkrSerial = rzrOkrSerial;
    }

    public Osoba getRzrOsoSerial() {
        return rzrOsoSerial;
    }

    public void setRzrOsoSerial(Osoba rzrOsoSerial) {
        this.rzrOsoSerial = rzrOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rzrSerial != null ? rzrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RozlZrodel)) {
            return false;
        }
        RozlZrodel other = (RozlZrodel) object;
        if ((this.rzrSerial == null && other.rzrSerial != null) || (this.rzrSerial != null && !this.rzrSerial.equals(other.rzrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.RozlZrodel[ rzrSerial=" + rzrSerial + " ]";
    }
    
}
