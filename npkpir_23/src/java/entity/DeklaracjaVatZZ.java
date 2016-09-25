/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwaschemy"})
})
@NamedQueries({
    @NamedQuery(name = "DeklaracjaVatZZ.usunliste", query = "DELETE FROM DeklaracjaVatZZ p WHERE p.nazwaschemy = :nazwaschemy")
})
public class DeklaracjaVatZZ implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nazwaschemy")
    private String nazwaschemy;
    @Column(name = "wstep")
    private String wstep;
    @Column(name = "naglowek", length = 2048)
    private String naglowek;
    @Column(name = "powod")
    private String powod;
    @Column(name = "kwota")
    private String kwota;
    @Column(name = "uzasadnienie")
    private String uzasadnienie;
    @OneToOne(mappedBy = "deklaracjaVatZZ")
    private DeklaracjaVatSchema deklaracjaVatSchema;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "zz_powody",
            joinColumns = {
                @JoinColumn(name = "powody", referencedColumnName = "id"),
            },
            inverseJoinColumns = {
                @JoinColumn(name = "vatzzty", referencedColumnName = "id"),
            })
    private List<DeklaracjaVatZZPowod> powody;

    public DeklaracjaVatZZ() {
        this.powody = new ArrayList<>();
    }

    public DeklaracjaVatZZ(DeklaracjaVatZZ dk) {
        this.nazwaschemy = dk.nazwaschemy;
        this.wstep = dk.wstep;
        this.naglowek = dk.naglowek;
        this.powody = new ArrayList<>();
    }

        
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nazwaschemy);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeklaracjaVatZZ other = (DeklaracjaVatZZ) obj;
        if (!Objects.equals(this.nazwaschemy, other.nazwaschemy)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatZZ{" + "nazwaschemy=" + nazwaschemy + ", wstep=" + wstep + ", naglowek=" + naglowek + ", powod=" + powod + ", kwota=" + kwota + ", uzasadnienie=" + uzasadnienie + ", deklaracjaVatSchema=" + deklaracjaVatSchema + '}';
    }

    
  
  
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNazwaschemy() {
        return nazwaschemy;
    }
    
    public void setNazwaschemy(String nazwaschemy) {
        this.nazwaschemy = nazwaschemy;
    }
    
    public String getWstep() {
        return wstep;
    }
    
    public void setWstep(String wstep) {
        this.wstep = wstep;
    }

    public String getPowod() {
        return powod;
    }

    public void setPowod(String powod) {
        this.powod = powod;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getUzasadnienie() {
        return uzasadnienie;
    }

    public void setUzasadnienie(String uzasadnienie) {
        this.uzasadnienie = uzasadnienie;
    }

    public DeklaracjaVatSchema getDeklaracjaVatSchema() {
        return deklaracjaVatSchema;
    }

    public void setDeklaracjaVatSchema(DeklaracjaVatSchema deklaracjaVatSchema) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
    }

    public List<DeklaracjaVatZZPowod> getPowody() {
        return powody;
    }

    public void setPowody(List<DeklaracjaVatZZPowod> powody) {
        this.powody = powody;
    }
    
    public String getNaglowek() {
        return naglowek;
    }
    
    public void setNaglowek(String naglowek) {
        this.naglowek = naglowek;
    }
  
//</editor-fold>
  
    
    
}
