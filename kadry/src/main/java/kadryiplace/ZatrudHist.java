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
@Table(name = "zatrud_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZatrudHist.findAll", query = "SELECT z FROM ZatrudHist z"),
    @NamedQuery(name = "ZatrudHist.findByZahSerial", query = "SELECT z FROM ZatrudHist z WHERE z.zahSerial = :zahSerial"),
    @NamedQuery(name = "ZatrudHist.findByZahDataOd", query = "SELECT z FROM ZatrudHist z WHERE z.zahDataOd = :zahDataOd"),
    @NamedQuery(name = "ZatrudHist.findByZahDataDo", query = "SELECT z FROM ZatrudHist z WHERE z.zahDataDo = :zahDataDo"),
    @NamedQuery(name = "ZatrudHist.findByZahZwol", query = "SELECT z FROM ZatrudHist z WHERE z.zahZwol = :zahZwol"),
    @NamedQuery(name = "ZatrudHist.findByZahZwolKod", query = "SELECT z FROM ZatrudHist z WHERE z.zahZwolKod = :zahZwolKod"),
    @NamedQuery(name = "ZatrudHist.findByZahZwolUwagi", query = "SELECT z FROM ZatrudHist z WHERE z.zahZwolUwagi = :zahZwolUwagi"),
    @NamedQuery(name = "ZatrudHist.findByZahStatus", query = "SELECT z FROM ZatrudHist z WHERE z.zahStatus = :zahStatus"),
    @NamedQuery(name = "ZatrudHist.findByZahTyp", query = "SELECT z FROM ZatrudHist z WHERE z.zahTyp = :zahTyp"),
    @NamedQuery(name = "ZatrudHist.findByZahOpis", query = "SELECT z FROM ZatrudHist z WHERE z.zahOpis = :zahOpis"),
    @NamedQuery(name = "ZatrudHist.findByZahUrlop", query = "SELECT z FROM ZatrudHist z WHERE z.zahUrlop = :zahUrlop"),
    @NamedQuery(name = "ZatrudHist.findByZahChar1", query = "SELECT z FROM ZatrudHist z WHERE z.zahChar1 = :zahChar1"),
    @NamedQuery(name = "ZatrudHist.findByZahChar2", query = "SELECT z FROM ZatrudHist z WHERE z.zahChar2 = :zahChar2"),
    @NamedQuery(name = "ZatrudHist.findByZahChar3", query = "SELECT z FROM ZatrudHist z WHERE z.zahChar3 = :zahChar3"),
    @NamedQuery(name = "ZatrudHist.findByZahChar4", query = "SELECT z FROM ZatrudHist z WHERE z.zahChar4 = :zahChar4"),
    @NamedQuery(name = "ZatrudHist.findByZahVchar1", query = "SELECT z FROM ZatrudHist z WHERE z.zahVchar1 = :zahVchar1"),
    @NamedQuery(name = "ZatrudHist.findByZahVchar2", query = "SELECT z FROM ZatrudHist z WHERE z.zahVchar2 = :zahVchar2"),
    @NamedQuery(name = "ZatrudHist.findByZahDate1", query = "SELECT z FROM ZatrudHist z WHERE z.zahDate1 = :zahDate1"),
    @NamedQuery(name = "ZatrudHist.findByZahNum1", query = "SELECT z FROM ZatrudHist z WHERE z.zahNum1 = :zahNum1")})
public class ZatrudHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zah_serial", nullable = false)
    private Integer zahSerial;
    @Column(name = "zah_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zahDataOd;
    @Column(name = "zah_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zahDataDo;
    @Column(name = "zah_zwol")
    private Character zahZwol;
    @Column(name = "zah_zwol_kod")
    private Character zahZwolKod;
    @Size(max = 64)
    @Column(name = "zah_zwol_uwagi", length = 64)
    private String zahZwolUwagi;
    //H historyczny A aktualny
    @Basic(optional = false)
    @NotNull
    @Column(name = "zah_status", nullable = false)
    private Character zahStatus;
    //A - zasadnicza szkoła zawodowa
    //B - średnia szkoła zawodowa
    //Z - średnia szkoła zawodowa dla absolwentow ZSZ
    //V - liceum zawodowe
    //C - szkola ogólnokształcąca
    //D - szkoła policealna
    //E - szkola wyzsza
    //I - inny pracodawca
    //J - inny okres
    //Y - okres pobytu w ewidencji dla bezrobotnych
    //Z - okres pobierania zasiłku dla bezrobotnych
    //W - służba wojskowa
    //1 biezacy okres probny
    //2 biezacy okres wstepny
    //3 biezacy na czas okreslonej pracy
    //4 biezacy czas okreslony
    //5 biezacy czas okreslony zastępstwo
    //P biezacy nieokreślony
    @Basic(optional = false)
    @NotNull
    @Column(name = "zah_typ", nullable = false)
    private Character zahTyp;
    @Size(max = 64)
    @Column(name = "zah_opis", length = 64)
    private String zahOpis;
    @Column(name = "zah_urlop")
    private Character zahUrlop;
    @Column(name = "zah_char_1")
    private Character zahChar1;
    @Column(name = "zah_char_2")
    private Character zahChar2;
    @Column(name = "zah_char_3")
    private Character zahChar3;
    @Column(name = "zah_char_4")
    private Character zahChar4;
    @Size(max = 64)
    @Column(name = "zah_vchar_1", length = 64)
    private String zahVchar1;
    @Size(max = 64)
    @Column(name = "zah_vchar_2", length = 64)
    private String zahVchar2;
    @Column(name = "zah_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zahDate1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zah_num_1", precision = 17, scale = 6)
    private BigDecimal zahNum1;
    @JoinColumn(name = "zah_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba zahOsoSerial;

    public ZatrudHist() {
    }

    public ZatrudHist(Integer zahSerial) {
        this.zahSerial = zahSerial;
    }

    public ZatrudHist(Integer zahSerial, Character zahStatus, Character zahTyp) {
        this.zahSerial = zahSerial;
        this.zahStatus = zahStatus;
        this.zahTyp = zahTyp;
    }

    public Integer getZahSerial() {
        return zahSerial;
    }

    public void setZahSerial(Integer zahSerial) {
        this.zahSerial = zahSerial;
    }

    public Date getZahDataOd() {
        return zahDataOd;
    }

    public void setZahDataOd(Date zahDataOd) {
        this.zahDataOd = zahDataOd;
    }

    public Date getZahDataDo() {
        return zahDataDo;
    }

    public void setZahDataDo(Date zahDataDo) {
        this.zahDataDo = zahDataDo;
    }

    public Character getZahZwol() {
        return zahZwol;
    }

    public void setZahZwol(Character zahZwol) {
        this.zahZwol = zahZwol;
    }

    public Character getZahZwolKod() {
        return zahZwolKod;
    }

    public void setZahZwolKod(Character zahZwolKod) {
        this.zahZwolKod = zahZwolKod;
    }

    public String getZahZwolUwagi() {
        return zahZwolUwagi;
    }

    public void setZahZwolUwagi(String zahZwolUwagi) {
        this.zahZwolUwagi = zahZwolUwagi;
    }

    public Character getZahStatus() {
        return zahStatus;
    }

    public void setZahStatus(Character zahStatus) {
        this.zahStatus = zahStatus;
    }

    public Character getZahTyp() {
        return zahTyp;
    }

    public void setZahTyp(Character zahTyp) {
        this.zahTyp = zahTyp;
    }

    public String getZahOpis() {
        return zahOpis;
    }

    public void setZahOpis(String zahOpis) {
        this.zahOpis = zahOpis;
    }

    public Character getZahUrlop() {
        return zahUrlop;
    }

    public void setZahUrlop(Character zahUrlop) {
        this.zahUrlop = zahUrlop;
    }

    public Character getZahChar1() {
        return zahChar1;
    }

    public void setZahChar1(Character zahChar1) {
        this.zahChar1 = zahChar1;
    }

    public Character getZahChar2() {
        return zahChar2;
    }

    public void setZahChar2(Character zahChar2) {
        this.zahChar2 = zahChar2;
    }

    public Character getZahChar3() {
        return zahChar3;
    }

    public void setZahChar3(Character zahChar3) {
        this.zahChar3 = zahChar3;
    }

    public Character getZahChar4() {
        return zahChar4;
    }

    public void setZahChar4(Character zahChar4) {
        this.zahChar4 = zahChar4;
    }

    public String getZahVchar1() {
        return zahVchar1;
    }

    public void setZahVchar1(String zahVchar1) {
        this.zahVchar1 = zahVchar1;
    }

    public String getZahVchar2() {
        return zahVchar2;
    }

    public void setZahVchar2(String zahVchar2) {
        this.zahVchar2 = zahVchar2;
    }

    public Date getZahDate1() {
        return zahDate1;
    }

    public void setZahDate1(Date zahDate1) {
        this.zahDate1 = zahDate1;
    }

    public BigDecimal getZahNum1() {
        return zahNum1;
    }

    public void setZahNum1(BigDecimal zahNum1) {
        this.zahNum1 = zahNum1;
    }

    public Osoba getZahOsoSerial() {
        return zahOsoSerial;
    }

    public void setZahOsoSerial(Osoba zahOsoSerial) {
        this.zahOsoSerial = zahOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zahSerial != null ? zahSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZatrudHist)) {
            return false;
        }
        ZatrudHist other = (ZatrudHist) object;
        if ((this.zahSerial == null && other.zahSerial != null) || (this.zahSerial != null && !this.zahSerial.equals(other.zahSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ZatrudHist[ zahSerial=" + zahSerial + " ]";
    }
    
}
