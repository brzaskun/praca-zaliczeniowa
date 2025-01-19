/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "oddelegowaniedaty",uniqueConstraints = {
    @UniqueConstraint(columnNames={"firmakadry","pracownik","rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OddelegowanieDaty.findAll", query = "SELECT l FROM OddelegowanieDaty l"),
    @NamedQuery(name = "OddelegowanieDaty.findById", query = "SELECT l FROM OddelegowanieDaty l WHERE l.id = :id"),
    @NamedQuery(name = "OddelegowanieDaty.findByFirmaRok", query = "SELECT l FROM OddelegowanieDaty l WHERE l.firmaKadry = :firmakadry AND l.rok = :rok")})
public class OddelegowanieDaty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @JoinColumn(name = "firmakadry", referencedColumnName = "id")
    @ManyToOne
    private FirmaKadry firmaKadry;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Column(name = "przekroczenieodpoczatku")
    private boolean przekroczenieodpoczatku;
    @Size(max = 2)
    @Column(name = "miesiacprzekroczenia")
    private String miesiacprzekroczenia;
    @Column(name = "datawprowadzenia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawprowadzenia;
    @Column(name = "wprowadzajacy")
    private String wprowadzajacy;

    public OddelegowanieDaty() {
    }

    public OddelegowanieDaty(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FirmaKadry getFirmaKadry() {
        return firmaKadry;
    }

    public void setFirmaKadry(FirmaKadry firmaKadry) {
        this.firmaKadry = firmaKadry;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isPrzekroczenieodpoczatku() {
        return przekroczenieodpoczatku;
    }

    public void setPrzekroczenieodpoczatku(boolean przekroczenieodpoczatku) {
        this.przekroczenieodpoczatku = przekroczenieodpoczatku;
    }


    public String getMiesiacprzekroczenia() {
        return miesiacprzekroczenia;
    }

    public void setMiesiacprzekroczenia(String miesiacprzekroczenia) {
        this.miesiacprzekroczenia = miesiacprzekroczenia;
    }

    public Date getDatawprowadzenia() {
        return datawprowadzenia;
    }

    public void setDatawprowadzenia(Date datawprowadzenia) {
        this.datawprowadzenia = datawprowadzenia;
    }

    public String getWprowadzajacy() {
        return wprowadzajacy;
    }

    public void setWprowadzajacy(String wprowadzajacy) {
        this.wprowadzajacy = wprowadzajacy;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.firmaKadry);
        hash = 71 * hash + Objects.hashCode(this.pracownik);
        hash = 71 * hash + Objects.hashCode(this.rok);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OddelegowanieDaty other = (OddelegowanieDaty) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.firmaKadry, other.firmaKadry)) {
            return false;
        }
        return Objects.equals(this.pracownik, other.pracownik);
    }

   
}
