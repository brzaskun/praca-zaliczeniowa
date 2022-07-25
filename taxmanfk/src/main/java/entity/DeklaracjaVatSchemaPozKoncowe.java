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
    @UniqueConstraint(columnNames={"deklaracjaVatSchema, deklaracjaVatPozycjeKoncowe"})
})
@NamedQueries({
    @NamedQuery(name = "DeklaracjaVatSchemaPozKoncowe.findEwidencjeSchemy", query = "SELECT t FROM DeklaracjaVatSchemaPozKoncowe t WHERE t.deklaracjaVatSchema = :deklaracjaVatSchema ORDER BY t.pole"),
    @NamedQuery(name = "DeklaracjaVatSchemaPozKoncowe.usunliste", query = "DELETE FROM DeklaracjaVatSchemaPozKoncowe p WHERE p.deklaracjaVatSchema = :deklaracjaVatSchema")
})
public class DeklaracjaVatSchemaPozKoncowe implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "deklaracjaVatSchema", referencedColumnName = "nazwaschemy")
    private DeklaracjaVatSchema deklaracjaVatSchema;
    @JoinColumn(name = "deklaracjaVatPozycjeKoncowe", referencedColumnName = "nazwapozycji")
    private DeklaracjaVatPozycjeKoncowe deklaracjaVatPozycjeKoncowe;
    @Column(name = "pole")
    private String pole;
    @Column(name = "funkcja")
    private String funkcja;
    @Column(name = "czescdeklaracji", length = 1)
    private String czescdeklaracji;

    public DeklaracjaVatSchemaPozKoncowe(DeklaracjaVatSchema deklaracjaVatSchema, DeklaracjaVatPozycjeKoncowe deklaracjaVatPozycjeKoncowe, String pole) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
        this.deklaracjaVatPozycjeKoncowe = deklaracjaVatPozycjeKoncowe;
        this.pole = pole;
    }

    public DeklaracjaVatSchemaPozKoncowe() {
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.deklaracjaVatSchema);
        hash = 89 * hash + Objects.hashCode(this.deklaracjaVatPozycjeKoncowe);
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
        final DeklaracjaVatSchemaPozKoncowe other = (DeklaracjaVatSchemaPozKoncowe) obj;
        if (!Objects.equals(this.deklaracjaVatSchema, other.deklaracjaVatSchema)) {
            return false;
        }
        if (!Objects.equals(this.deklaracjaVatPozycjeKoncowe, other.deklaracjaVatPozycjeKoncowe)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatSchemaWierszSum{" + "deklaracjaVatSchema=" + deklaracjaVatSchema.getNazwaschemy() + ", deklaracjaVatPozycjeKoncowe=" + deklaracjaVatPozycjeKoncowe.getNazwapozycji() + ", pole=" + pole + ", funkcja=" + funkcja + ", czescdeklaracji=" + czescdeklaracji + '}';
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

    public DeklaracjaVatPozycjeKoncowe getDeklaracjaVatPozycjeKoncowe() {
        return deklaracjaVatPozycjeKoncowe;
    }

    public void setDeklaracjaVatPozycjeKoncowe(DeklaracjaVatPozycjeKoncowe deklaracjaVatPozycjeKoncowe) {
        this.deklaracjaVatPozycjeKoncowe = deklaracjaVatPozycjeKoncowe;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public String getFunkcja() {
        return funkcja;
    }

    public void setFunkcja(String funkcja) {
        this.funkcja = funkcja;
    }

    public String getCzescdeklaracji() {
        return czescdeklaracji;
    }

    public void setCzescdeklaracji(String czescdeklaracji) {
        this.czescdeklaracji = czescdeklaracji;
    }
    
   
      
    
}
