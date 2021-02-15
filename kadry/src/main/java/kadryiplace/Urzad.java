/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "urzad", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urzad.findAll", query = "SELECT u FROM Urzad u"),
    @NamedQuery(name = "Urzad.findByUrzSerial", query = "SELECT u FROM Urzad u WHERE u.urzSerial = :urzSerial"),
    @NamedQuery(name = "Urzad.findByUrzNazwa", query = "SELECT u FROM Urzad u WHERE u.urzNazwa = :urzNazwa"),
    @NamedQuery(name = "Urzad.findByUrzPanSerial", query = "SELECT u FROM Urzad u WHERE u.urzPanSerial = :urzPanSerial"),
    @NamedQuery(name = "Urzad.findByUrzKod", query = "SELECT u FROM Urzad u WHERE u.urzKod = :urzKod"),
    @NamedQuery(name = "Urzad.findByUrzUlica", query = "SELECT u FROM Urzad u WHERE u.urzUlica = :urzUlica"),
    @NamedQuery(name = "Urzad.findByUrzNrKonta", query = "SELECT u FROM Urzad u WHERE u.urzNrKonta = :urzNrKonta"),
    @NamedQuery(name = "Urzad.findByUrzDom", query = "SELECT u FROM Urzad u WHERE u.urzDom = :urzDom"),
    @NamedQuery(name = "Urzad.findByUrzMieszkanie", query = "SELECT u FROM Urzad u WHERE u.urzMieszkanie = :urzMieszkanie"),
    @NamedQuery(name = "Urzad.findByUrzNrKontaV", query = "SELECT u FROM Urzad u WHERE u.urzNrKontaV = :urzNrKontaV"),
    @NamedQuery(name = "Urzad.findByUrzVchar1", query = "SELECT u FROM Urzad u WHERE u.urzVchar1 = :urzVchar1"),
    @NamedQuery(name = "Urzad.findByUrzVchar2", query = "SELECT u FROM Urzad u WHERE u.urzVchar2 = :urzVchar2"),
    @NamedQuery(name = "Urzad.findByUrzInt1", query = "SELECT u FROM Urzad u WHERE u.urzInt1 = :urzInt1"),
    @NamedQuery(name = "Urzad.findByUrzInt2", query = "SELECT u FROM Urzad u WHERE u.urzInt2 = :urzInt2"),
    @NamedQuery(name = "Urzad.findByUrzChar1", query = "SELECT u FROM Urzad u WHERE u.urzChar1 = :urzChar1"),
    @NamedQuery(name = "Urzad.findByUrzChar2", query = "SELECT u FROM Urzad u WHERE u.urzChar2 = :urzChar2"),
    @NamedQuery(name = "Urzad.findByUrzTyp", query = "SELECT u FROM Urzad u WHERE u.urzTyp = :urzTyp")})
public class Urzad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "urz_serial", nullable = false)
    private Integer urzSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "urz_nazwa", nullable = false, length = 128)
    private String urzNazwa;
    @Column(name = "urz_pan_serial")
    private Integer urzPanSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "urz_kod", nullable = false, length = 5)
    private String urzKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "urz_ulica", nullable = false, length = 64)
    private String urzUlica;
    @Size(max = 64)
    @Column(name = "urz_nr_konta", length = 64)
    private String urzNrKonta;
    @Size(max = 10)
    @Column(name = "urz_dom", length = 10)
    private String urzDom;
    @Size(max = 10)
    @Column(name = "urz_mieszkanie", length = 10)
    private String urzMieszkanie;
    @Size(max = 64)
    @Column(name = "urz_nr_konta_v", length = 64)
    private String urzNrKontaV;
    @Size(max = 64)
    @Column(name = "urz_vchar_1", length = 64)
    private String urzVchar1;
    @Size(max = 64)
    @Column(name = "urz_vchar_2", length = 64)
    private String urzVchar2;
    @Column(name = "urz_int_1")
    private Integer urzInt1;
    @Column(name = "urz_int_2")
    private Integer urzInt2;
    @Column(name = "urz_char_1")
    private Character urzChar1;
    @Column(name = "urz_char_2")
    private Character urzChar2;
    @Column(name = "urz_typ")
    private Character urzTyp;
    @OneToMany(mappedBy = "firUrzSerialV")
    private List<Firma> firmaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rzuUrzSerial")
    private List<Rozliczus> rozliczusList;
    @JoinColumn(name = "urz_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank urzBanSerial;
    @JoinColumn(name = "urz_ban_serial_v", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank urzBanSerialV;
    @JoinColumn(name = "urz_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto urzMiaSerial;

    public Urzad() {
    }

    public Urzad(Integer urzSerial) {
        this.urzSerial = urzSerial;
    }

    public Urzad(Integer urzSerial, String urzNazwa, String urzKod, String urzUlica) {
        this.urzSerial = urzSerial;
        this.urzNazwa = urzNazwa;
        this.urzKod = urzKod;
        this.urzUlica = urzUlica;
    }

    public Integer getUrzSerial() {
        return urzSerial;
    }

    public void setUrzSerial(Integer urzSerial) {
        this.urzSerial = urzSerial;
    }

    public String getUrzNazwa() {
        return urzNazwa;
    }

    public void setUrzNazwa(String urzNazwa) {
        this.urzNazwa = urzNazwa;
    }

    public Integer getUrzPanSerial() {
        return urzPanSerial;
    }

    public void setUrzPanSerial(Integer urzPanSerial) {
        this.urzPanSerial = urzPanSerial;
    }

    public String getUrzKod() {
        return urzKod;
    }

    public void setUrzKod(String urzKod) {
        this.urzKod = urzKod;
    }

    public String getUrzUlica() {
        return urzUlica;
    }

    public void setUrzUlica(String urzUlica) {
        this.urzUlica = urzUlica;
    }

    public String getUrzNrKonta() {
        return urzNrKonta;
    }

    public void setUrzNrKonta(String urzNrKonta) {
        this.urzNrKonta = urzNrKonta;
    }

    public String getUrzDom() {
        return urzDom;
    }

    public void setUrzDom(String urzDom) {
        this.urzDom = urzDom;
    }

    public String getUrzMieszkanie() {
        return urzMieszkanie;
    }

    public void setUrzMieszkanie(String urzMieszkanie) {
        this.urzMieszkanie = urzMieszkanie;
    }

    public String getUrzNrKontaV() {
        return urzNrKontaV;
    }

    public void setUrzNrKontaV(String urzNrKontaV) {
        this.urzNrKontaV = urzNrKontaV;
    }

    public String getUrzVchar1() {
        return urzVchar1;
    }

    public void setUrzVchar1(String urzVchar1) {
        this.urzVchar1 = urzVchar1;
    }

    public String getUrzVchar2() {
        return urzVchar2;
    }

    public void setUrzVchar2(String urzVchar2) {
        this.urzVchar2 = urzVchar2;
    }

    public Integer getUrzInt1() {
        return urzInt1;
    }

    public void setUrzInt1(Integer urzInt1) {
        this.urzInt1 = urzInt1;
    }

    public Integer getUrzInt2() {
        return urzInt2;
    }

    public void setUrzInt2(Integer urzInt2) {
        this.urzInt2 = urzInt2;
    }

    public Character getUrzChar1() {
        return urzChar1;
    }

    public void setUrzChar1(Character urzChar1) {
        this.urzChar1 = urzChar1;
    }

    public Character getUrzChar2() {
        return urzChar2;
    }

    public void setUrzChar2(Character urzChar2) {
        this.urzChar2 = urzChar2;
    }

    public Character getUrzTyp() {
        return urzTyp;
    }

    public void setUrzTyp(Character urzTyp) {
        this.urzTyp = urzTyp;
    }

    @XmlTransient
    public List<Firma> getFirmaList() {
        return firmaList;
    }

    public void setFirmaList(List<Firma> firmaList) {
        this.firmaList = firmaList;
    }

    @XmlTransient
    public List<Rozliczus> getRozliczusList() {
        return rozliczusList;
    }

    public void setRozliczusList(List<Rozliczus> rozliczusList) {
        this.rozliczusList = rozliczusList;
    }

    public Bank getUrzBanSerial() {
        return urzBanSerial;
    }

    public void setUrzBanSerial(Bank urzBanSerial) {
        this.urzBanSerial = urzBanSerial;
    }

    public Bank getUrzBanSerialV() {
        return urzBanSerialV;
    }

    public void setUrzBanSerialV(Bank urzBanSerialV) {
        this.urzBanSerialV = urzBanSerialV;
    }

    public Miasto getUrzMiaSerial() {
        return urzMiaSerial;
    }

    public void setUrzMiaSerial(Miasto urzMiaSerial) {
        this.urzMiaSerial = urzMiaSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (urzSerial != null ? urzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Urzad)) {
            return false;
        }
        Urzad other = (Urzad) object;
        if ((this.urzSerial == null && other.urzSerial != null) || (this.urzSerial != null && !this.urzSerial.equals(other.urzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Urzad[ urzSerial=" + urzSerial + " ]";
    }
    
}
