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
    @UniqueConstraint(columnNames={"deklaracjaVatSchema, deklaracjaVatWierszSumaryczny"})
})
@NamedQueries({
    @NamedQuery(name = "DeklaracjaVatSchemaWierszSum.findEwidencjeSchemy", query = "SELECT t FROM DeklaracjaVatSchemaWierszSum t WHERE t.deklaracjaVatSchema = :deklarachaVatSchema ORDER BY t.polenetto"),
    @NamedQuery(name = "DeklaracjaVatSchemaWierszSum.usunliste", query = "DELETE FROM DeklaracjaVatSchemaWierszSum p WHERE p.deklaracjaVatSchema = :deklaracjaVatSchema")
})
public class DeklaracjaVatSchemaWierszSum implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "deklaracjaVatSchema", referencedColumnName = "nazwaschemy")
    private DeklaracjaVatSchema deklaracjaVatSchema;
    @JoinColumn(name = "deklaracjaVatWierszSumaryczny", referencedColumnName = "nazwapozycji")
    private DeklaracjaVatWierszSumaryczny deklaracjaVatWierszSumaryczny;
    @Column(name = "polenetto")
    private String polenetto;
    @Column(name = "polevat")
    private String polevat;
    @Column(name = "funkcja")
    private String funkcja;
    //5 to nieaktywny
    @Column(name = "netto1vat2")
    private int netto1vat2czek3tekst4;
    @Column(name = "czescdeklaracji", length = 1)
    private String czescdeklaracji;

    public DeklaracjaVatSchemaWierszSum(DeklaracjaVatSchema deklaracjaVatSchema, DeklaracjaVatWierszSumaryczny deklaracjaVatWierszSumaryczny, String polenetto, String polevat) {
        this.deklaracjaVatSchema = deklaracjaVatSchema;
        this.deklaracjaVatWierszSumaryczny = deklaracjaVatWierszSumaryczny;
        this.polenetto = polenetto;
        this.polevat = polevat;
    }

    public DeklaracjaVatSchemaWierszSum() {
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.deklaracjaVatSchema);
        hash = 89 * hash + Objects.hashCode(this.deklaracjaVatWierszSumaryczny);
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
        final DeklaracjaVatSchemaWierszSum other = (DeklaracjaVatSchemaWierszSum) obj;
        if (!Objects.equals(this.deklaracjaVatSchema, other.deklaracjaVatSchema)) {
            return false;
        }
        if (!Objects.equals(this.deklaracjaVatWierszSumaryczny, other.deklaracjaVatWierszSumaryczny)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatSchemaWierszSum{" + "deklaracjaVatSchema=" + deklaracjaVatSchema.getNazwaschemy() + ", deklaracjaVatWierszSumaryczny=" + deklaracjaVatWierszSumaryczny.getNazwapozycji() + ", polenetto=" + polenetto + ", polevat=" + polevat + ", funkcja=" + funkcja + ", netto1vat2=" + netto1vat2czek3tekst4 + ", czescdeklaracji=" + czescdeklaracji + '}';
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

    public DeklaracjaVatWierszSumaryczny getDeklaracjaVatWierszSumaryczny() {
        return deklaracjaVatWierszSumaryczny;
    }

    public void setDeklaracjaVatWierszSumaryczny(DeklaracjaVatWierszSumaryczny deklaracjaVatWierszSumaryczny) {
        this.deklaracjaVatWierszSumaryczny = deklaracjaVatWierszSumaryczny;
    }

    public String getFunkcja() {
        return funkcja;
    }

    public void setFunkcja(String funkcja) {
        this.funkcja = funkcja;
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

    public int getNetto1vat2czek3tekst4() {
        return netto1vat2czek3tekst4;
    }

    public void setNetto1vat2czek3tekst4(int netto1vat2czek3tekst4) {
        this.netto1vat2czek3tekst4 = netto1vat2czek3tekst4;
    }
    
    
}
