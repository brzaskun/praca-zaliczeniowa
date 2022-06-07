/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
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
@Table(name = "UBEZP_CZLONEK_RODZINY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpCzlonekRodziny.findAll", query = "SELECT u FROM UbezpCzlonekRodziny u"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findById", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByIdUbezpieczony", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByIdPlZus", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByIdUbZus", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByImiepierw", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.imiepierw = :imiepierw"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByNazwisko", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.nazwisko = :nazwisko"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByNip", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.nip = :nip"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByPesel", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.pesel = :pesel"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByRodzdok", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.rodzdok = :rodzdok"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findBySerianrdok", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.serianrdok = :serianrdok"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByStniep", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.stniep = :stniep"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByKodstpokr", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.kodstpokr = :kodstpokr"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByPogospzub", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.pogospzub = :pogospzub"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByDataurodz", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.dataurodz = :dataurodz"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByDataUzyskaniaUprawnien", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.dataUzyskaniaUprawnien = :dataUzyskaniaUprawnien"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByDataUtratyUprawnien", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.dataUtratyUprawnien = :dataUtratyUprawnien"),
    @NamedQuery(name = "UbezpCzlonekRodziny.findByInserttmp", query = "SELECT u FROM UbezpCzlonekRodziny u WHERE u.inserttmp = :inserttmp")})
public class UbezpCzlonekRodziny implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Column(name = "STNIEP")
    private Character stniep;
    @Size(max = 2)
    @Column(name = "KODSTPOKR", length = 2)
    private String kodstpokr;
    @Column(name = "POGOSPZUB")
    private Character pogospzub;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Column(name = "DATA_UZYSKANIA_UPRAWNIEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUzyskaniaUprawnien;
    @Column(name = "DATA_UTRATY_UPRAWNIEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtratyUprawnien;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpCzlonekRodziny() {
    }

    public UbezpCzlonekRodziny(Integer id) {
        this.id = id;
    }

    public UbezpCzlonekRodziny(Integer id, int idUbezpieczony) {
        this.id = id;
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
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

    public String getImiepierw() {
        return imiepierw;
    }

    public void setImiepierw(String imiepierw) {
        this.imiepierw = imiepierw;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Character getRodzdok() {
        return rodzdok;
    }

    public void setRodzdok(Character rodzdok) {
        this.rodzdok = rodzdok;
    }

    public String getSerianrdok() {
        return serianrdok;
    }

    public void setSerianrdok(String serianrdok) {
        this.serianrdok = serianrdok;
    }

    public Character getStniep() {
        return stniep;
    }

    public void setStniep(Character stniep) {
        this.stniep = stniep;
    }

    public String getKodstpokr() {
        return kodstpokr;
    }

    public void setKodstpokr(String kodstpokr) {
        this.kodstpokr = kodstpokr;
    }

    public Character getPogospzub() {
        return pogospzub;
    }

    public void setPogospzub(Character pogospzub) {
        this.pogospzub = pogospzub;
    }

    public Date getDataurodz() {
        return dataurodz;
    }

    public void setDataurodz(Date dataurodz) {
        this.dataurodz = dataurodz;
    }

    public Date getDataUzyskaniaUprawnien() {
        return dataUzyskaniaUprawnien;
    }

    public void setDataUzyskaniaUprawnien(Date dataUzyskaniaUprawnien) {
        this.dataUzyskaniaUprawnien = dataUzyskaniaUprawnien;
    }

    public Date getDataUtratyUprawnien() {
        return dataUtratyUprawnien;
    }

    public void setDataUtratyUprawnien(Date dataUtratyUprawnien) {
        this.dataUtratyUprawnien = dataUtratyUprawnien;
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
        if (!(object instanceof UbezpCzlonekRodziny)) {
            return false;
        }
        UbezpCzlonekRodziny other = (UbezpCzlonekRodziny) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpCzlonekRodziny[ id=" + id + " ]";
    }
    
}
