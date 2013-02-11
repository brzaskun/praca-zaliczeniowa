/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "platnosci")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platnosci.findByKey", query = "SELECT p FROM Platnosci p WHERE p.platnosciPK.podatnik = :podatnik AND p.platnosciPK.rok = :rok AND p.platnosciPK.miesiac = :miesiac"),
    @NamedQuery(name = "Platnosci.findByPodRok", query = "SELECT p FROM Platnosci p WHERE p.platnosciPK.podatnik = :podatnik AND p.platnosciPK.rok = :rok"),
    @NamedQuery(name = "Platnosci.findAll", query = "SELECT p FROM Platnosci p"),
    @NamedQuery(name = "Platnosci.findByRok", query = "SELECT p FROM Platnosci p WHERE p.platnosciPK.rok = :rok"),
    @NamedQuery(name = "Platnosci.findByMiesiac", query = "SELECT p FROM Platnosci p WHERE p.platnosciPK.miesiac = :miesiac"),
    @NamedQuery(name = "Platnosci.findByPit4", query = "SELECT p FROM Platnosci p WHERE p.pit4 = :pit4"),
    @NamedQuery(name = "Platnosci.findByPit4ods", query = "SELECT p FROM Platnosci p WHERE p.pit4ods = :pit4ods"),
    @NamedQuery(name = "Platnosci.findByPit4suma", query = "SELECT p FROM Platnosci p WHERE p.pit4suma = :pit4suma"),
    @NamedQuery(name = "Platnosci.findByPit4zapl", query = "SELECT p FROM Platnosci p WHERE p.pit4zapl = :pit4zapl"),
    @NamedQuery(name = "Platnosci.findByPit5", query = "SELECT p FROM Platnosci p WHERE p.pit5 = :pit5"),
    @NamedQuery(name = "Platnosci.findByPit5ods", query = "SELECT p FROM Platnosci p WHERE p.pit5ods = :pit5ods"),
    @NamedQuery(name = "Platnosci.findByPit5suma", query = "SELECT p FROM Platnosci p WHERE p.pit5suma = :pit5suma"),
    @NamedQuery(name = "Platnosci.findByPit5zapl", query = "SELECT p FROM Platnosci p WHERE p.pit5zapl = :pit5zapl"),
    @NamedQuery(name = "Platnosci.findByPodatnik", query = "SELECT p FROM Platnosci p WHERE p.platnosciPK.podatnik = :podatnik"),
    @NamedQuery(name = "Platnosci.findByTerminzpit4", query = "SELECT p FROM Platnosci p WHERE p.terminzpit4 = :terminzpit4"),
    @NamedQuery(name = "Platnosci.findByTerminzpit5", query = "SELECT p FROM Platnosci p WHERE p.terminzpit5 = :terminzpit5"),
    @NamedQuery(name = "Platnosci.findByTerminzuz", query = "SELECT p FROM Platnosci p WHERE p.terminzuz = :terminzuz"),
    @NamedQuery(name = "Platnosci.findByTerminzvat", query = "SELECT p FROM Platnosci p WHERE p.terminzvat = :terminzvat"),
    @NamedQuery(name = "Platnosci.findByVat", query = "SELECT p FROM Platnosci p WHERE p.vat = :vat"),
    @NamedQuery(name = "Platnosci.findByVatods", query = "SELECT p FROM Platnosci p WHERE p.vatods = :vatods"),
    @NamedQuery(name = "Platnosci.findByVatsuma", query = "SELECT p FROM Platnosci p WHERE p.vatsuma = :vatsuma"),
    @NamedQuery(name = "Platnosci.findByVatzapl", query = "SELECT p FROM Platnosci p WHERE p.vatzapl = :vatzapl"),
    @NamedQuery(name = "Platnosci.findByZus51", query = "SELECT p FROM Platnosci p WHERE p.zus51 = :zus51"),
    @NamedQuery(name = "Platnosci.findByZus51ods", query = "SELECT p FROM Platnosci p WHERE p.zus51ods = :zus51ods"),
    @NamedQuery(name = "Platnosci.findByZus51suma", query = "SELECT p FROM Platnosci p WHERE p.zus51suma = :zus51suma"),
    @NamedQuery(name = "Platnosci.findByZus51zapl", query = "SELECT p FROM Platnosci p WHERE p.zus51zapl = :zus51zapl"),
    @NamedQuery(name = "Platnosci.findByZus52", query = "SELECT p FROM Platnosci p WHERE p.zus52 = :zus52"),
    @NamedQuery(name = "Platnosci.findByZus52ods", query = "SELECT p FROM Platnosci p WHERE p.zus52ods = :zus52ods"),
    @NamedQuery(name = "Platnosci.findByZus52suma", query = "SELECT p FROM Platnosci p WHERE p.zus52suma = :zus52suma"),
    @NamedQuery(name = "Platnosci.findByZus52zapl", query = "SELECT p FROM Platnosci p WHERE p.zus52zapl = :zus52zapl"),
    @NamedQuery(name = "Platnosci.findByZus53", query = "SELECT p FROM Platnosci p WHERE p.zus53 = :zus53"),
    @NamedQuery(name = "Platnosci.findByZus53ods", query = "SELECT p FROM Platnosci p WHERE p.zus53ods = :zus53ods"),
    @NamedQuery(name = "Platnosci.findByZus53suma", query = "SELECT p FROM Platnosci p WHERE p.zus53suma = :zus53suma"),
    @NamedQuery(name = "Platnosci.findByZus53zapl", query = "SELECT p FROM Platnosci p WHERE p.zus53zapl = :zus53zapl")})
public class Platnosci implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlatnosciPK platnosciPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PIT4")
    private Double pit4;
    @Column(name = "PIT4ODS")
    private Double pit4ods;
    @Column(name = "PIT4SUMA")
    private Double pit4suma;
    @Column(name = "PIT4ZAPL")
    @Temporal(TemporalType.DATE)
    private Date pit4zapl;
    @Column(name = "PIT5")
    private Double pit5;
    @Column(name = "PIT5ODS")
    private Double pit5ods;
    @Column(name = "PIT5SUMA")
    private Double pit5suma;
    @Column(name = "PIT5ZAPL")
    @Temporal(TemporalType.DATE)
    private Date pit5zapl;
    @Size(max = 255)
    @Column(name = "TERMINZPIT4")
    private String terminzpit4;
    @Size(max = 255)
    @Column(name = "TERMINZPIT5")
    private String terminzpit5;
    @Size(max = 255)
    @Column(name = "TERMINZUZ")
    private String terminzuz;
    @Size(max = 255)
    @Column(name = "TERMINZVAT")
    private String terminzvat;
    @Column(name = "VAT")
    private Double vat;
    @Column(name = "VATODS")
    private Double vatods;
    @Column(name = "VATSUMA")
    private Double vatsuma;
    @Column(name = "VATZAPL")
    @Temporal(TemporalType.DATE)
    private Date vatzapl;
    @Column(name = "ZUS51")
    private Double zus51;
    @Column(name = "ZUS51ODS")
    private Double zus51ods;
    @Column(name = "ZUS51SUMA")
    private Double zus51suma;
    @Column(name = "ZUS51ZAPL")
    @Temporal(TemporalType.DATE)
    private Date zus51zapl;
    @Column(name = "ZUS52")
    private Double zus52;
    @Column(name = "ZUS52ODS")
    private Double zus52ods;
    @Column(name = "ZUS52SUMA")
    private Double zus52suma;
    @Column(name = "ZUS52ZAPL")
    @Temporal(TemporalType.DATE)
    private Date zus52zapl;
    @Column(name = "ZUS53")
    private Double zus53;
    @Column(name = "ZUS53ODS")
    private Double zus53ods;
    @Column(name = "ZUS53SUMA")
    private Double zus53suma;
    @Column(name = "ZUS53ZAPL")
    @Temporal(TemporalType.DATE)
    private Date zus53zapl;

    public Platnosci() {
        platnosciPK = new PlatnosciPK();
    }

    public Platnosci(PlatnosciPK platnosciPK) {
        this.platnosciPK = platnosciPK;
    }

    public Platnosci(String rok, String miesiac, String podatnik) {
        this.platnosciPK = new PlatnosciPK(rok, miesiac, podatnik);
    }

    public PlatnosciPK getPlatnosciPK() {
        return platnosciPK;
    }

    public void setPlatnosciPK(PlatnosciPK platnosciPK) {
        this.platnosciPK = platnosciPK;
    }

    public Double getPit4() {
        return pit4;
    }

    public void setPit4(Double pit4) {
        this.pit4 = pit4;
    }

    public Double getPit4ods() {
        return pit4ods;
    }

    public void setPit4ods(Double pit4ods) {
        this.pit4ods = pit4ods;
    }

    public Double getPit4suma() {
        return pit4suma;
    }

    public void setPit4suma(Double pit4suma) {
        this.pit4suma = pit4suma;
    }

    public Date getPit4zapl() {
        return pit4zapl;
    }

    public void setPit4zapl(Date pit4zapl) {
        this.pit4zapl = pit4zapl;
    }

    public Double getPit5() {
        return pit5;
    }

    public void setPit5(Double pit5) {
        this.pit5 = pit5;
    }

    public Double getPit5ods() {
        return pit5ods;
    }

    public void setPit5ods(Double pit5ods) {
        this.pit5ods = pit5ods;
    }

    public Double getPit5suma() {
        return pit5suma;
    }

    public void setPit5suma(Double pit5suma) {
        this.pit5suma = pit5suma;
    }

    public Date getPit5zapl() {
        return pit5zapl;
    }

    public void setPit5zapl(Date pit5zapl) {
        this.pit5zapl = pit5zapl;
    }

    public String getTerminzpit4() {
        return terminzpit4;
    }

    public void setTerminzpit4(String terminzpit4) {
        this.terminzpit4 = terminzpit4;
    }

    public String getTerminzpit5() {
        return terminzpit5;
    }

    public void setTerminzpit5(String terminzpit5) {
        this.terminzpit5 = terminzpit5;
    }

    public String getTerminzuz() {
        return terminzuz;
    }

    public void setTerminzuz(String terminzuz) {
        this.terminzuz = terminzuz;
    }

    public String getTerminzvat() {
        return terminzvat;
    }

    public void setTerminzvat(String terminzvat) {
        this.terminzvat = terminzvat;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getVatods() {
        return vatods;
    }

    public void setVatods(Double vatods) {
        this.vatods = vatods;
    }

    public Double getVatsuma() {
        return vatsuma;
    }

    public void setVatsuma(Double vatsuma) {
        this.vatsuma = vatsuma;
    }

    public Date getVatzapl() {
        return vatzapl;
    }

    public void setVatzapl(Date vatzapl) {
        this.vatzapl = vatzapl;
    }

    public Double getZus51() {
        return zus51;
    }

    public void setZus51(Double zus51) {
        this.zus51 = zus51;
    }

    public Double getZus51ods() {
        return zus51ods;
    }

    public void setZus51ods(Double zus51ods) {
        this.zus51ods = zus51ods;
    }

    public Double getZus51suma() {
        return zus51suma;
    }

    public void setZus51suma(Double zus51suma) {
        this.zus51suma = zus51suma;
    }

    public Date getZus51zapl() {
        return zus51zapl;
    }

    public void setZus51zapl(Date zus51zapl) {
        this.zus51zapl = zus51zapl;
    }

    public Double getZus52() {
        return zus52;
    }

    public void setZus52(Double zus52) {
        this.zus52 = zus52;
    }

    public Double getZus52ods() {
        return zus52ods;
    }

    public void setZus52ods(Double zus52ods) {
        this.zus52ods = zus52ods;
    }

    public Double getZus52suma() {
        return zus52suma;
    }

    public void setZus52suma(Double zus52suma) {
        this.zus52suma = zus52suma;
    }

    public Date getZus52zapl() {
        return zus52zapl;
    }

    public void setZus52zapl(Date zus52zapl) {
        this.zus52zapl = zus52zapl;
    }

    public Double getZus53() {
        return zus53;
    }

    public void setZus53(Double zus53) {
        this.zus53 = zus53;
    }

    public Double getZus53ods() {
        return zus53ods;
    }

    public void setZus53ods(Double zus53ods) {
        this.zus53ods = zus53ods;
    }

    public Double getZus53suma() {
        return zus53suma;
    }

    public void setZus53suma(Double zus53suma) {
        this.zus53suma = zus53suma;
    }

    public Date getZus53zapl() {
        return zus53zapl;
    }

    public void setZus53zapl(Date zus53zapl) {
        this.zus53zapl = zus53zapl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (platnosciPK != null ? platnosciPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platnosci)) {
            return false;
        }
        Platnosci other = (Platnosci) object;
        if ((this.platnosciPK == null && other.platnosciPK != null) || (this.platnosciPK != null && !this.platnosciPK.equals(other.platnosciPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Platnosci[ platnosciPK=" + platnosciPK + " ]";
    }
    
}
