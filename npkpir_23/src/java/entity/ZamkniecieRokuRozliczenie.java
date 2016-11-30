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
import javax.persistence.CascadeType;
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

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zamkniecieRokuRozliczenie", uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnikObj", "zamkniecieRokuEtap"})
})
@NamedQueries({
    @NamedQuery(name = "ZamkniecieRokuRozliczenie.findAll", query = "SELECT e FROM ZamkniecieRokuRozliczenie e"),
    @NamedQuery(name = "ZamkniecieRokuRozliczenie.findByRokPodatnik", query = "SELECT e FROM ZamkniecieRokuRozliczenie e WHERE e.podatnikObj = :podatnik AND e.zamkniecieRokuEtap.rok = :rok")
})
public class ZamkniecieRokuRozliczenie implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    private Podatnik podatnikObj;
    @JoinColumn(name = "zamkniecieRokuEtap", referencedColumnName = "id")
    private ZamkniecieRokuEtap zamkniecieRokuEtap;
    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    private Uz wprowadzil;
    @Temporal(TemporalType.DATE)
    @Column(name = "zatwierdzono")
    private Date zatwierdzono;

    public ZamkniecieRokuRozliczenie() {
    }

    
    public ZamkniecieRokuRozliczenie(ZamkniecieRokuEtap p, Podatnik podatnikObiekt) {
        this.zamkniecieRokuEtap = p;
        this.podatnikObj = podatnikObiekt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public ZamkniecieRokuEtap getZamkniecieRokuEtap() {
        return zamkniecieRokuEtap;
    }

    public void setZamkniecieRokuEtap(ZamkniecieRokuEtap zamkniecieRokuEtap) {
        this.zamkniecieRokuEtap = zamkniecieRokuEtap;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

   

    public Date getZatwierdzono() {
        return zatwierdzono;
    }

    public void setZatwierdzono(Date zatwierdzono) {
        this.zatwierdzono = zatwierdzono;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.podatnikObj);
        hash = 79 * hash + Objects.hashCode(this.zamkniecieRokuEtap);
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
        final ZamkniecieRokuRozliczenie other = (ZamkniecieRokuRozliczenie) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        if (!Objects.equals(this.zamkniecieRokuEtap, other.zamkniecieRokuEtap)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZamkniecieRokuRozliczenie{" + "podatnikObj=" + podatnikObj + ", zamkniecieRokuEtap=" + zamkniecieRokuEtap + ", data=" + data + ", wprowadzil=" + wprowadzil + ", zatwierdzono=" + zatwierdzono + '}';
    }
    
    
    

   
    
}
