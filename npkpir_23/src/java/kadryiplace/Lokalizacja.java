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
@Table(name = "lokalizacja", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lokalizacja.findAll", query = "SELECT l FROM Lokalizacja l"),
    @NamedQuery(name = "Lokalizacja.findByLokSerial", query = "SELECT l FROM Lokalizacja l WHERE l.lokSerial = :lokSerial"),
    @NamedQuery(name = "Lokalizacja.findByLokNazwa", query = "SELECT l FROM Lokalizacja l WHERE l.lokNazwa = :lokNazwa"),
    @NamedQuery(name = "Lokalizacja.findByLokKolejnosc", query = "SELECT l FROM Lokalizacja l WHERE l.lokKolejnosc = :lokKolejnosc"),
    @NamedQuery(name = "Lokalizacja.findByLokChar1", query = "SELECT l FROM Lokalizacja l WHERE l.lokChar1 = :lokChar1"),
    @NamedQuery(name = "Lokalizacja.findByLokChar2", query = "SELECT l FROM Lokalizacja l WHERE l.lokChar2 = :lokChar2"),
    @NamedQuery(name = "Lokalizacja.findByLokChar3", query = "SELECT l FROM Lokalizacja l WHERE l.lokChar3 = :lokChar3"),
    @NamedQuery(name = "Lokalizacja.findByLokChar4", query = "SELECT l FROM Lokalizacja l WHERE l.lokChar4 = :lokChar4"),
    @NamedQuery(name = "Lokalizacja.findByLokVchar1", query = "SELECT l FROM Lokalizacja l WHERE l.lokVchar1 = :lokVchar1"),
    @NamedQuery(name = "Lokalizacja.findByLokVchar2", query = "SELECT l FROM Lokalizacja l WHERE l.lokVchar2 = :lokVchar2"),
    @NamedQuery(name = "Lokalizacja.findByLokInt1", query = "SELECT l FROM Lokalizacja l WHERE l.lokInt1 = :lokInt1"),
    @NamedQuery(name = "Lokalizacja.findByLokInt2", query = "SELECT l FROM Lokalizacja l WHERE l.lokInt2 = :lokInt2"),
    @NamedQuery(name = "Lokalizacja.findByLokData1", query = "SELECT l FROM Lokalizacja l WHERE l.lokData1 = :lokData1"),
    @NamedQuery(name = "Lokalizacja.findByLokData2", query = "SELECT l FROM Lokalizacja l WHERE l.lokData2 = :lokData2"),
    @NamedQuery(name = "Lokalizacja.findByLokNum1", query = "SELECT l FROM Lokalizacja l WHERE l.lokNum1 = :lokNum1"),
    @NamedQuery(name = "Lokalizacja.findByLokNum2", query = "SELECT l FROM Lokalizacja l WHERE l.lokNum2 = :lokNum2"),
    @NamedQuery(name = "Lokalizacja.findByLokNum3", query = "SELECT l FROM Lokalizacja l WHERE l.lokNum3 = :lokNum3"),
    @NamedQuery(name = "Lokalizacja.findByLokNum4", query = "SELECT l FROM Lokalizacja l WHERE l.lokNum4 = :lokNum4")})
public class Lokalizacja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "lok_serial", nullable = false)
    private Integer lokSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "lok_nazwa", nullable = false, length = 64)
    private String lokNazwa;
    @Column(name = "lok_kolejnosc")
    private Short lokKolejnosc;
    @Column(name = "lok_char_1")
    private Character lokChar1;
    @Column(name = "lok_char_2")
    private Character lokChar2;
    @Column(name = "lok_char_3")
    private Character lokChar3;
    @Column(name = "lok_char_4")
    private Character lokChar4;
    @Size(max = 64)
    @Column(name = "lok_vchar_1", length = 64)
    private String lokVchar1;
    @Size(max = 64)
    @Column(name = "lok_vchar_2", length = 64)
    private String lokVchar2;
    @Column(name = "lok_int_1")
    private Integer lokInt1;
    @Column(name = "lok_int_2")
    private Integer lokInt2;
    @Column(name = "lok_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lokData1;
    @Column(name = "lok_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lokData2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lok_num_1", precision = 17, scale = 6)
    private BigDecimal lokNum1;
    @Column(name = "lok_num_2", precision = 17, scale = 6)
    private BigDecimal lokNum2;
    @Column(name = "lok_num_3", precision = 17, scale = 6)
    private BigDecimal lokNum3;
    @Column(name = "lok_num_4", precision = 17, scale = 6)
    private BigDecimal lokNum4;
    @OneToMany(mappedBy = "magLokSerial")
    private List<Magazyn> magazynList;
    @JoinColumn(name = "lok_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma lokFirSerial;
    @JoinColumn(name = "lok_lgr_serial", referencedColumnName = "lgr_serial")
    @ManyToOne
    private LokGrupa lokLgrSerial;

    public Lokalizacja() {
    }

    public Lokalizacja(Integer lokSerial) {
        this.lokSerial = lokSerial;
    }

    public Lokalizacja(Integer lokSerial, String lokNazwa) {
        this.lokSerial = lokSerial;
        this.lokNazwa = lokNazwa;
    }

    public Integer getLokSerial() {
        return lokSerial;
    }

    public void setLokSerial(Integer lokSerial) {
        this.lokSerial = lokSerial;
    }

    public String getLokNazwa() {
        return lokNazwa;
    }

    public void setLokNazwa(String lokNazwa) {
        this.lokNazwa = lokNazwa;
    }

    public Short getLokKolejnosc() {
        return lokKolejnosc;
    }

    public void setLokKolejnosc(Short lokKolejnosc) {
        this.lokKolejnosc = lokKolejnosc;
    }

    public Character getLokChar1() {
        return lokChar1;
    }

    public void setLokChar1(Character lokChar1) {
        this.lokChar1 = lokChar1;
    }

    public Character getLokChar2() {
        return lokChar2;
    }

    public void setLokChar2(Character lokChar2) {
        this.lokChar2 = lokChar2;
    }

    public Character getLokChar3() {
        return lokChar3;
    }

    public void setLokChar3(Character lokChar3) {
        this.lokChar3 = lokChar3;
    }

    public Character getLokChar4() {
        return lokChar4;
    }

    public void setLokChar4(Character lokChar4) {
        this.lokChar4 = lokChar4;
    }

    public String getLokVchar1() {
        return lokVchar1;
    }

    public void setLokVchar1(String lokVchar1) {
        this.lokVchar1 = lokVchar1;
    }

    public String getLokVchar2() {
        return lokVchar2;
    }

    public void setLokVchar2(String lokVchar2) {
        this.lokVchar2 = lokVchar2;
    }

    public Integer getLokInt1() {
        return lokInt1;
    }

    public void setLokInt1(Integer lokInt1) {
        this.lokInt1 = lokInt1;
    }

    public Integer getLokInt2() {
        return lokInt2;
    }

    public void setLokInt2(Integer lokInt2) {
        this.lokInt2 = lokInt2;
    }

    public Date getLokData1() {
        return lokData1;
    }

    public void setLokData1(Date lokData1) {
        this.lokData1 = lokData1;
    }

    public Date getLokData2() {
        return lokData2;
    }

    public void setLokData2(Date lokData2) {
        this.lokData2 = lokData2;
    }

    public BigDecimal getLokNum1() {
        return lokNum1;
    }

    public void setLokNum1(BigDecimal lokNum1) {
        this.lokNum1 = lokNum1;
    }

    public BigDecimal getLokNum2() {
        return lokNum2;
    }

    public void setLokNum2(BigDecimal lokNum2) {
        this.lokNum2 = lokNum2;
    }

    public BigDecimal getLokNum3() {
        return lokNum3;
    }

    public void setLokNum3(BigDecimal lokNum3) {
        this.lokNum3 = lokNum3;
    }

    public BigDecimal getLokNum4() {
        return lokNum4;
    }

    public void setLokNum4(BigDecimal lokNum4) {
        this.lokNum4 = lokNum4;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    public Firma getLokFirSerial() {
        return lokFirSerial;
    }

    public void setLokFirSerial(Firma lokFirSerial) {
        this.lokFirSerial = lokFirSerial;
    }

    public LokGrupa getLokLgrSerial() {
        return lokLgrSerial;
    }

    public void setLokLgrSerial(LokGrupa lokLgrSerial) {
        this.lokLgrSerial = lokLgrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lokSerial != null ? lokSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lokalizacja)) {
            return false;
        }
        Lokalizacja other = (Lokalizacja) object;
        if ((this.lokSerial == null && other.lokSerial != null) || (this.lokSerial != null && !this.lokSerial.equals(other.lokSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Lokalizacja[ lokSerial=" + lokSerial + " ]";
    }
    
}
