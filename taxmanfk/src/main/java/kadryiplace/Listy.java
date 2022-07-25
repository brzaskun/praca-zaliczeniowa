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
@Table(name = "listy", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listy.findAll", query = "SELECT l FROM Listy l"),
    @NamedQuery(name = "Listy.findByLisSerial", query = "SELECT l FROM Listy l WHERE l.lisSerial = :lisSerial"),
    @NamedQuery(name = "Listy.findByLisDataOd", query = "SELECT l FROM Listy l WHERE l.lisDataOd = :lisDataOd"),
    @NamedQuery(name = "Listy.findByLisNumer", query = "SELECT l FROM Listy l WHERE l.lisNumer = :lisNumer"),
    @NamedQuery(name = "Listy.findByLisDataDo", query = "SELECT l FROM Listy l WHERE l.lisDataDo = :lisDataDo"),
    @NamedQuery(name = "Listy.findByLisUwagi", query = "SELECT l FROM Listy l WHERE l.lisUwagi = :lisUwagi"),
    @NamedQuery(name = "Listy.findByLisLp", query = "SELECT l FROM Listy l WHERE l.lisLp = :lisLp"),
    @NamedQuery(name = "Listy.findByLisSporzData", query = "SELECT l FROM Listy l WHERE l.lisSporzData = :lisSporzData"),
    @NamedQuery(name = "Listy.findByLisSporzDane", query = "SELECT l FROM Listy l WHERE l.lisSporzDane = :lisSporzDane"),
    @NamedQuery(name = "Listy.findByLisZatwData", query = "SELECT l FROM Listy l WHERE l.lisZatwData = :lisZatwData"),
    @NamedQuery(name = "Listy.findByLisZatwDane", query = "SELECT l FROM Listy l WHERE l.lisZatwDane = :lisZatwDane"),
    @NamedQuery(name = "Listy.findByLisWyplData", query = "SELECT l FROM Listy l WHERE l.lisWyplData = :lisWyplData"),
    @NamedQuery(name = "Listy.findByLisWyplDane", query = "SELECT l FROM Listy l WHERE l.lisWyplDane = :lisWyplDane"),
    @NamedQuery(name = "Listy.findByLisKwotaDod1", query = "SELECT l FROM Listy l WHERE l.lisKwotaDod1 = :lisKwotaDod1"),
    @NamedQuery(name = "Listy.findByLisKwotaDod2", query = "SELECT l FROM Listy l WHERE l.lisKwotaDod2 = :lisKwotaDod2"),
    @NamedQuery(name = "Listy.findByLisKwotaDod3", query = "SELECT l FROM Listy l WHERE l.lisKwotaDod3 = :lisKwotaDod3"),
    @NamedQuery(name = "Listy.findByLisKwotaDod4", query = "SELECT l FROM Listy l WHERE l.lisKwotaDod4 = :lisKwotaDod4"),
    @NamedQuery(name = "Listy.findByLisWplataZal", query = "SELECT l FROM Listy l WHERE l.lisWplataZal = :lisWplataZal"),
    @NamedQuery(name = "Listy.findByLisDodData1", query = "SELECT l FROM Listy l WHERE l.lisDodData1 = :lisDodData1"),
    @NamedQuery(name = "Listy.findByLisDodData2", query = "SELECT l FROM Listy l WHERE l.lisDodData2 = :lisDodData2"),
    @NamedQuery(name = "Listy.findByLisDodData3", query = "SELECT l FROM Listy l WHERE l.lisDodData3 = :lisDodData3"),
    @NamedQuery(name = "Listy.findByLisDodData4", query = "SELECT l FROM Listy l WHERE l.lisDodData4 = :lisDodData4"),
    @NamedQuery(name = "Listy.findByLisChar1", query = "SELECT l FROM Listy l WHERE l.lisChar1 = :lisChar1"),
    @NamedQuery(name = "Listy.findByLisChar2", query = "SELECT l FROM Listy l WHERE l.lisChar2 = :lisChar2"),
    @NamedQuery(name = "Listy.findByLisChar3", query = "SELECT l FROM Listy l WHERE l.lisChar3 = :lisChar3"),
    @NamedQuery(name = "Listy.findByLisChar4", query = "SELECT l FROM Listy l WHERE l.lisChar4 = :lisChar4"),
    @NamedQuery(name = "Listy.findByLisVchar1", query = "SELECT l FROM Listy l WHERE l.lisVchar1 = :lisVchar1"),
    @NamedQuery(name = "Listy.findByLisVchar2", query = "SELECT l FROM Listy l WHERE l.lisVchar2 = :lisVchar2")})
public class Listy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "lis_serial", nullable = false)
    private Integer lisSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lis_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDataOd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "lis_numer", nullable = false, length = 32)
    private String lisNumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lis_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDataDo;
    @Size(max = 32)
    @Column(name = "lis_uwagi", length = 32)
    private String lisUwagi;
    @Column(name = "lis_lp")
    private Integer lisLp;
    @Column(name = "lis_sporz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisSporzData;
    @Size(max = 64)
    @Column(name = "lis_sporz_dane", length = 64)
    private String lisSporzDane;
    @Column(name = "lis_zatw_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisZatwData;
    @Size(max = 64)
    @Column(name = "lis_zatw_dane", length = 64)
    private String lisZatwDane;
    @Column(name = "lis_wypl_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisWyplData;
    @Size(max = 64)
    @Column(name = "lis_wypl_dane", length = 64)
    private String lisWyplDane;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lis_kwota_dod_1", precision = 17, scale = 6)
    private BigDecimal lisKwotaDod1;
    @Column(name = "lis_kwota_dod_2", precision = 17, scale = 6)
    private BigDecimal lisKwotaDod2;
    @Column(name = "lis_kwota_dod_3", precision = 17, scale = 6)
    private BigDecimal lisKwotaDod3;
    @Column(name = "lis_kwota_dod_4", precision = 17, scale = 6)
    private BigDecimal lisKwotaDod4;
    @Column(name = "lis_wplata_zal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisWplataZal;
    @Column(name = "lis_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDodData1;
    @Column(name = "lis_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDodData2;
    @Column(name = "lis_dod_data_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDodData3;
    @Column(name = "lis_dod_data_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lisDodData4;
    @Column(name = "lis_char_1")
    private Character lisChar1;
    @Column(name = "lis_char_2")
    private Character lisChar2;
    @Column(name = "lis_char_3")
    private Character lisChar3;
    @Column(name = "lis_char_4")
    private Character lisChar4;
    @Size(max = 64)
    @Column(name = "lis_vchar_1", length = 64)
    private String lisVchar1;
    @Size(max = 64)
    @Column(name = "lis_vchar_2", length = 64)
    private String lisVchar2;
    @OneToMany(mappedBy = "sklLisSerial")
    private List<PlaceSkl> placeSklList;
    @OneToMany(mappedBy = "pzzLisSerial")
    private List<PlacePrzZus> placePrzZusList;
    @OneToMany(mappedBy = "przLisSerial")
    private List<PlacePrz> placePrzList;
    @OneToMany(mappedBy = "lplLisSerial")
    private List<Place> placeList;
    @OneToMany(mappedBy = "pzlLisSerial")
    private List<PlaceZlec> placeZlecList;
    @JoinColumn(name = "lis_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres lisOkrSerial;
    @JoinColumn(name = "lis_tyt_serial", referencedColumnName = "tyt_serial")
    @ManyToOne
    private Tytul lisTytSerial;
    @OneToMany(mappedBy = "ppoLisSerial")
    private List<PlacePot> placePotList;

    public Listy() {
    }

    public Listy(Integer lisSerial) {
        this.lisSerial = lisSerial;
    }

    public Listy(Integer lisSerial, Date lisDataOd, String lisNumer, Date lisDataDo) {
        this.lisSerial = lisSerial;
        this.lisDataOd = lisDataOd;
        this.lisNumer = lisNumer;
        this.lisDataDo = lisDataDo;
    }

    public Integer getLisSerial() {
        return lisSerial;
    }

    public void setLisSerial(Integer lisSerial) {
        this.lisSerial = lisSerial;
    }

    public Date getLisDataOd() {
        return lisDataOd;
    }

    public void setLisDataOd(Date lisDataOd) {
        this.lisDataOd = lisDataOd;
    }

    public String getLisNumer() {
        return lisNumer;
    }

    public void setLisNumer(String lisNumer) {
        this.lisNumer = lisNumer;
    }

    public Date getLisDataDo() {
        return lisDataDo;
    }

    public void setLisDataDo(Date lisDataDo) {
        this.lisDataDo = lisDataDo;
    }

    public String getLisUwagi() {
        return lisUwagi;
    }

    public void setLisUwagi(String lisUwagi) {
        this.lisUwagi = lisUwagi;
    }

    public Integer getLisLp() {
        return lisLp;
    }

    public void setLisLp(Integer lisLp) {
        this.lisLp = lisLp;
    }

    public Date getLisSporzData() {
        return lisSporzData;
    }

    public void setLisSporzData(Date lisSporzData) {
        this.lisSporzData = lisSporzData;
    }

    public String getLisSporzDane() {
        return lisSporzDane;
    }

    public void setLisSporzDane(String lisSporzDane) {
        this.lisSporzDane = lisSporzDane;
    }

    public Date getLisZatwData() {
        return lisZatwData;
    }

    public void setLisZatwData(Date lisZatwData) {
        this.lisZatwData = lisZatwData;
    }

    public String getLisZatwDane() {
        return lisZatwDane;
    }

    public void setLisZatwDane(String lisZatwDane) {
        this.lisZatwDane = lisZatwDane;
    }

    public Date getLisWyplData() {
        return lisWyplData;
    }

    public void setLisWyplData(Date lisWyplData) {
        this.lisWyplData = lisWyplData;
    }

    public String getLisWyplDane() {
        return lisWyplDane;
    }

    public void setLisWyplDane(String lisWyplDane) {
        this.lisWyplDane = lisWyplDane;
    }

    public BigDecimal getLisKwotaDod1() {
        return lisKwotaDod1;
    }

    public void setLisKwotaDod1(BigDecimal lisKwotaDod1) {
        this.lisKwotaDod1 = lisKwotaDod1;
    }

    public BigDecimal getLisKwotaDod2() {
        return lisKwotaDod2;
    }

    public void setLisKwotaDod2(BigDecimal lisKwotaDod2) {
        this.lisKwotaDod2 = lisKwotaDod2;
    }

    public BigDecimal getLisKwotaDod3() {
        return lisKwotaDod3;
    }

    public void setLisKwotaDod3(BigDecimal lisKwotaDod3) {
        this.lisKwotaDod3 = lisKwotaDod3;
    }

    public BigDecimal getLisKwotaDod4() {
        return lisKwotaDod4;
    }

    public void setLisKwotaDod4(BigDecimal lisKwotaDod4) {
        this.lisKwotaDod4 = lisKwotaDod4;
    }

    public Date getLisWplataZal() {
        return lisWplataZal;
    }

    public void setLisWplataZal(Date lisWplataZal) {
        this.lisWplataZal = lisWplataZal;
    }

    public Date getLisDodData1() {
        return lisDodData1;
    }

    public void setLisDodData1(Date lisDodData1) {
        this.lisDodData1 = lisDodData1;
    }

    public Date getLisDodData2() {
        return lisDodData2;
    }

    public void setLisDodData2(Date lisDodData2) {
        this.lisDodData2 = lisDodData2;
    }

    public Date getLisDodData3() {
        return lisDodData3;
    }

    public void setLisDodData3(Date lisDodData3) {
        this.lisDodData3 = lisDodData3;
    }

    public Date getLisDodData4() {
        return lisDodData4;
    }

    public void setLisDodData4(Date lisDodData4) {
        this.lisDodData4 = lisDodData4;
    }

    public Character getLisChar1() {
        return lisChar1;
    }

    public void setLisChar1(Character lisChar1) {
        this.lisChar1 = lisChar1;
    }

    public Character getLisChar2() {
        return lisChar2;
    }

    public void setLisChar2(Character lisChar2) {
        this.lisChar2 = lisChar2;
    }

    public Character getLisChar3() {
        return lisChar3;
    }

    public void setLisChar3(Character lisChar3) {
        this.lisChar3 = lisChar3;
    }

    public Character getLisChar4() {
        return lisChar4;
    }

    public void setLisChar4(Character lisChar4) {
        this.lisChar4 = lisChar4;
    }

    public String getLisVchar1() {
        return lisVchar1;
    }

    public void setLisVchar1(String lisVchar1) {
        this.lisVchar1 = lisVchar1;
    }

    public String getLisVchar2() {
        return lisVchar2;
    }

    public void setLisVchar2(String lisVchar2) {
        this.lisVchar2 = lisVchar2;
    }

    @XmlTransient
    public List<PlaceSkl> getPlaceSklList() {
        return placeSklList;
    }

    public void setPlaceSklList(List<PlaceSkl> placeSklList) {
        this.placeSklList = placeSklList;
    }

    @XmlTransient
    public List<PlacePrzZus> getPlacePrzZusList() {
        return placePrzZusList;
    }

    public void setPlacePrzZusList(List<PlacePrzZus> placePrzZusList) {
        this.placePrzZusList = placePrzZusList;
    }

    @XmlTransient
    public List<PlacePrz> getPlacePrzList() {
        return placePrzList;
    }

    public void setPlacePrzList(List<PlacePrz> placePrzList) {
        this.placePrzList = placePrzList;
    }

    @XmlTransient
    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    public Okres getLisOkrSerial() {
        return lisOkrSerial;
    }

    public void setLisOkrSerial(Okres lisOkrSerial) {
        this.lisOkrSerial = lisOkrSerial;
    }

    public Tytul getLisTytSerial() {
        return lisTytSerial;
    }

    public void setLisTytSerial(Tytul lisTytSerial) {
        this.lisTytSerial = lisTytSerial;
    }

    @XmlTransient
    public List<PlacePot> getPlacePotList() {
        return placePotList;
    }

    public void setPlacePotList(List<PlacePot> placePotList) {
        this.placePotList = placePotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lisSerial != null ? lisSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listy)) {
            return false;
        }
        Listy other = (Listy) object;
        if ((this.lisSerial == null && other.lisSerial != null) || (this.lisSerial != null && !this.lisSerial.equals(other.lisSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Listy[ lisSerial=" + lisSerial + " ]";
    }
    
}
