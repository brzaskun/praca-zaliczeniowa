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
import javax.persistence.Lob;
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
@Table(name = "POTWIERDZENIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Potwierdzenie.findAll", query = "SELECT p FROM Potwierdzenie p"),
    @NamedQuery(name = "Potwierdzenie.findById", query = "SELECT p FROM Potwierdzenie p WHERE p.id = :id"),
    @NamedQuery(name = "Potwierdzenie.findByIdPlatnik", query = "SELECT p FROM Potwierdzenie p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Potwierdzenie.findByKeduKsiId", query = "SELECT p FROM Potwierdzenie p WHERE p.keduKsiId = :keduKsiId"),
    @NamedQuery(name = "Potwierdzenie.findByDataPrzyg", query = "SELECT p FROM Potwierdzenie p WHERE p.dataPrzyg = :dataPrzyg"),
    @NamedQuery(name = "Potwierdzenie.findByDataPotw", query = "SELECT p FROM Potwierdzenie p WHERE p.dataPotw = :dataPotw"),
    @NamedQuery(name = "Potwierdzenie.findByPotwJednZus", query = "SELECT p FROM Potwierdzenie p WHERE p.potwJednZus = :potwJednZus"),
    @NamedQuery(name = "Potwierdzenie.findBySkrot", query = "SELECT p FROM Potwierdzenie p WHERE p.skrot = :skrot"),
    @NamedQuery(name = "Potwierdzenie.findByInserttmp", query = "SELECT p FROM Potwierdzenie p WHERE p.inserttmp = :inserttmp")})
public class Potwierdzenie implements Serializable {

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
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NAZWA_KEDU", length = 2147483647)
    private String nazwaKedu;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NAZWA_KSI", length = 2147483647)
    private String nazwaKsi;
    @Size(max = 34)
    @Column(name = "KEDU_KSI_ID", length = 34)
    private String keduKsiId;
    @Column(name = "DATA_PRZYG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrzyg;
    @Column(name = "DATA_POTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotw;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "PRZYG_OPIS", length = 2147483647)
    private String przygOpis;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "POTW_OPIS", length = 2147483647)
    private String potwOpis;
    @Size(max = 128)
    @Column(name = "POTW_JEDN_ZUS", length = 128)
    private String potwJednZus;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "PLIK_POTW", length = 2147483647)
    private String plikPotw;
    @Size(max = 128)
    @Column(name = "SKROT", length = 128)
    private String skrot;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Potwierdzenie() {
    }

    public Potwierdzenie(Integer id) {
        this.id = id;
    }

    public Potwierdzenie(Integer id, int idPlatnik) {
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

    public String getNazwaKedu() {
        return nazwaKedu;
    }

    public void setNazwaKedu(String nazwaKedu) {
        this.nazwaKedu = nazwaKedu;
    }

    public String getNazwaKsi() {
        return nazwaKsi;
    }

    public void setNazwaKsi(String nazwaKsi) {
        this.nazwaKsi = nazwaKsi;
    }

    public String getKeduKsiId() {
        return keduKsiId;
    }

    public void setKeduKsiId(String keduKsiId) {
        this.keduKsiId = keduKsiId;
    }

    public Date getDataPrzyg() {
        return dataPrzyg;
    }

    public void setDataPrzyg(Date dataPrzyg) {
        this.dataPrzyg = dataPrzyg;
    }

    public Date getDataPotw() {
        return dataPotw;
    }

    public void setDataPotw(Date dataPotw) {
        this.dataPotw = dataPotw;
    }

    public String getPrzygOpis() {
        return przygOpis;
    }

    public void setPrzygOpis(String przygOpis) {
        this.przygOpis = przygOpis;
    }

    public String getPotwOpis() {
        return potwOpis;
    }

    public void setPotwOpis(String potwOpis) {
        this.potwOpis = potwOpis;
    }

    public String getPotwJednZus() {
        return potwJednZus;
    }

    public void setPotwJednZus(String potwJednZus) {
        this.potwJednZus = potwJednZus;
    }

    public String getPlikPotw() {
        return plikPotw;
    }

    public void setPlikPotw(String plikPotw) {
        this.plikPotw = plikPotw;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
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
        if (!(object instanceof Potwierdzenie)) {
            return false;
        }
        Potwierdzenie other = (Potwierdzenie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Potwierdzenie[ id=" + id + " ]";
    }
    
}
