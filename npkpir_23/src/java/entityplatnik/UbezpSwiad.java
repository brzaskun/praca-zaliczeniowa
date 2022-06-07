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
@Table(name = "UBEZP_SWIAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpSwiad.findAll", query = "SELECT u FROM UbezpSwiad u"),
    @NamedQuery(name = "UbezpSwiad.findById", query = "SELECT u FROM UbezpSwiad u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpSwiad.findByIdDokument", query = "SELECT u FROM UbezpSwiad u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpSwiad.findByIdPlatnik", query = "SELECT u FROM UbezpSwiad u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpSwiad.findByIdUbezpieczony", query = "SELECT u FROM UbezpSwiad u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpSwiad.findByIdDokZus", query = "SELECT u FROM UbezpSwiad u WHERE u.idDokZus = :idDokZus"),
    @NamedQuery(name = "UbezpSwiad.findByNrPoddokumentu", query = "SELECT u FROM UbezpSwiad u WHERE u.nrPoddokumentu = :nrPoddokumentu"),
    @NamedQuery(name = "UbezpSwiad.findByIdPlZus", query = "SELECT u FROM UbezpSwiad u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpSwiad.findByIdUbZus", query = "SELECT u FROM UbezpSwiad u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpSwiad.findByIiiA1", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiA1 = :iiiA1"),
    @NamedQuery(name = "UbezpSwiad.findByIiiA2", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiA2 = :iiiA2"),
    @NamedQuery(name = "UbezpSwiad.findByIiiA3", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiA3 = :iiiA3"),
    @NamedQuery(name = "UbezpSwiad.findByIiiA4", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiA4 = :iiiA4"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB11", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB11 = :iiiB11"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB12", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB12 = :iiiB12"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB13", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB13 = :iiiB13"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB2", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB2 = :iiiB2"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB3", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB3 = :iiiB3"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB4", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB4 = :iiiB4"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB5", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB5 = :iiiB5"),
    @NamedQuery(name = "UbezpSwiad.findByIiiB6", query = "SELECT u FROM UbezpSwiad u WHERE u.iiiB6 = :iiiB6"),
    @NamedQuery(name = "UbezpSwiad.findByInserttmp", query = "SELECT u FROM UbezpSwiad u WHERE u.inserttmp = :inserttmp")})
public class UbezpSwiad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_DOK_ZUS")
    private Integer idDokZus;
    @Column(name = "NR_PODDOKUMENTU")
    private Integer nrPoddokumentu;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Size(max = 31)
    @Column(name = "III_A_1", length = 31)
    private String iiiA1;
    @Size(max = 22)
    @Column(name = "III_A_2", length = 22)
    private String iiiA2;
    @Column(name = "III_A_3")
    private Character iiiA3;
    @Size(max = 11)
    @Column(name = "III_A_4", length = 11)
    private String iiiA4;
    @Size(max = 4)
    @Column(name = "III_B_1_1", length = 4)
    private String iiiB11;
    @Column(name = "III_B_1_2")
    private Character iiiB12;
    @Column(name = "III_B_1_3")
    private Character iiiB13;
    @Size(max = 3)
    @Column(name = "III_B_2", length = 3)
    private String iiiB2;
    @Column(name = "III_B_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB3;
    @Column(name = "III_B_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB4;
    @Column(name = "III_B_5")
    private Integer iiiB5;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_6", precision = 7, scale = 2)
    private BigDecimal iiiB6;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpSwiad() {
    }

    public UbezpSwiad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdDokZus() {
        return idDokZus;
    }

    public void setIdDokZus(Integer idDokZus) {
        this.idDokZus = idDokZus;
    }

    public Integer getNrPoddokumentu() {
        return nrPoddokumentu;
    }

    public void setNrPoddokumentu(Integer nrPoddokumentu) {
        this.nrPoddokumentu = nrPoddokumentu;
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

    public String getIiiA1() {
        return iiiA1;
    }

    public void setIiiA1(String iiiA1) {
        this.iiiA1 = iiiA1;
    }

    public String getIiiA2() {
        return iiiA2;
    }

    public void setIiiA2(String iiiA2) {
        this.iiiA2 = iiiA2;
    }

    public Character getIiiA3() {
        return iiiA3;
    }

    public void setIiiA3(Character iiiA3) {
        this.iiiA3 = iiiA3;
    }

    public String getIiiA4() {
        return iiiA4;
    }

    public void setIiiA4(String iiiA4) {
        this.iiiA4 = iiiA4;
    }

    public String getIiiB11() {
        return iiiB11;
    }

    public void setIiiB11(String iiiB11) {
        this.iiiB11 = iiiB11;
    }

    public Character getIiiB12() {
        return iiiB12;
    }

    public void setIiiB12(Character iiiB12) {
        this.iiiB12 = iiiB12;
    }

    public Character getIiiB13() {
        return iiiB13;
    }

    public void setIiiB13(Character iiiB13) {
        this.iiiB13 = iiiB13;
    }

    public String getIiiB2() {
        return iiiB2;
    }

    public void setIiiB2(String iiiB2) {
        this.iiiB2 = iiiB2;
    }

    public Date getIiiB3() {
        return iiiB3;
    }

    public void setIiiB3(Date iiiB3) {
        this.iiiB3 = iiiB3;
    }

    public Date getIiiB4() {
        return iiiB4;
    }

    public void setIiiB4(Date iiiB4) {
        this.iiiB4 = iiiB4;
    }

    public Integer getIiiB5() {
        return iiiB5;
    }

    public void setIiiB5(Integer iiiB5) {
        this.iiiB5 = iiiB5;
    }

    public BigDecimal getIiiB6() {
        return iiiB6;
    }

    public void setIiiB6(BigDecimal iiiB6) {
        this.iiiB6 = iiiB6;
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
        if (!(object instanceof UbezpSwiad)) {
            return false;
        }
        UbezpSwiad other = (UbezpSwiad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpSwiad[ id=" + id + " ]";
    }
    
}
