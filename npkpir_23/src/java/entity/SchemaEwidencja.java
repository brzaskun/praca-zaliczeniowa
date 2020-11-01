/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"deklaracjaVatSchema, evewidencja, polenetto, polevat"})
})
@NamedQueries({
    @NamedQuery(name = "SchemaEwidencja.findEwidencjeSchemy", query = "SELECT t FROM SchemaEwidencja t WHERE t.deklaracjaVatSchema = :deklarachaVatSchema")
})
public class SchemaEwidencja implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "deklaracjaVatSchema", referencedColumnName = "nazwaschemy")
    private DeklaracjaVatSchema deklaracjaVatSchema;
    @JoinColumn(name = "evewidencjaID", referencedColumnName = "id")
    private Evewidencja evewidencja;
    @Column(name = "polenetto")
    private String polenetto;
    @Column(name = "polevat")
    private String polevat;
    @Column(name = "czescdeklaracji", length = 1)
    private String czescdeklaracji;
    @JoinColumn(name = "schemamacierzysta",referencedColumnName = "id")
    private SchemaEwidencja schemamacierzysta;


    public SchemaEwidencja(DeklaracjaVatSchema deklaracjaVatSchema, Evewidencja evewidencja, String polenetto, String polevat) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
        this.evewidencja = evewidencja;
        this.polenetto = polenetto;
        this.polevat = polevat;
    }

    public SchemaEwidencja() {
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.deklaracjaVatSchema);
        hash = 89 * hash + Objects.hashCode(this.evewidencja);
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
        final SchemaEwidencja other = (SchemaEwidencja) obj;
        if (!Objects.equals(this.deklaracjaVatSchema, other.deklaracjaVatSchema)) {
            return false;
        }
        if (!Objects.equals(this.evewidencja, other.evewidencja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SchemaEwidencja{" + "deklaracjaVatSchema=" + deklaracjaVatSchema.getNazwaschemy() + ", evewidencja=" + evewidencja.getNazwa() + ", polenetto=" + polenetto + ", polevat=" + polevat + ", czescdeklaracji=" + czescdeklaracji + '}';
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DeklaracjaVatSchema getDeklaracjaVatSchema() {
        return deklaracjaVatSchema;
    }

    public void setDeklaracjaVatSchema(DeklaracjaVatSchema deklaracjaVatSchema) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
    }

    public Evewidencja getEvewidencja() {
        return evewidencja;
    }

    public void setEvewidencja(Evewidencja evewidencja) {
        this.evewidencja = evewidencja;
    }


    public String getPolenetto() {
        return polenetto;
    }

    public void setPolenetto(String polenetto) {
        this.polenetto = polenetto;
    }

    public String getPolevat() {
        return polevat;
    }

    public void setPolevat(String polevat) {
        this.polevat = polevat;
    }
    
    public String getCzescdeklaracji() {
        return czescdeklaracji;
    }

    public void setCzescdeklaracji(String czescdeklaracji) {
        this.czescdeklaracji = czescdeklaracji;
    }

    public SchemaEwidencja getSchemamacierzysta() {
        return schemamacierzysta;
    }

    public void setSchemamacierzysta(SchemaEwidencja schemamacierzysta) {
        this.schemamacierzysta = schemamacierzysta;
    }
    
    
}
