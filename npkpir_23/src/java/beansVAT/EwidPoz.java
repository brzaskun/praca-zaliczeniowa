/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import entity.Deklaracjevat;
import entity.SchemaEwidencja;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EwidPoz.findAll", query = "SELECT d FROM EwidPoz d")
})
public class EwidPoz implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "polenetto")
    private String polenetto;
    @Column(name = "polevat")
    private String polevat;
    @JoinColumn(name = "wierszSchemaEwidencja", referencedColumnName = "id")
    @ManyToOne
    private SchemaEwidencja wierszSchemaEwidencja;
    @JoinColumn(name = "wierszSchemaEwidencjaMacierzysty", referencedColumnName = "id")
    @ManyToOne
    private SchemaEwidencja wierszSchemaEwidencjaMacierzysty;
    @JoinColumn(name = "deklaracja", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Deklaracjevat deklaracja;
    @Column(name = "netto")
    double netto;
    @Column(name = "vat")
    double vat;
   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.wierszSchemaEwidencja);
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
        final EwidPoz other = (EwidPoz) obj;
        if (!Objects.equals(this.wierszSchemaEwidencja, other.wierszSchemaEwidencja)) {
            return false;
        }
        return true;
    }

    public EwidPoz() {
    }

    
    
    
    public EwidPoz(SchemaEwidencja wierszSchemaEwidencja, SchemaEwidencja wierszSchemaEwidencjaMacierzysty, BigDecimal netto, BigDecimal vat) {
        this.polenetto = wierszSchemaEwidencja.getPolenetto();
        this.polevat = wierszSchemaEwidencja.getPolevat();
        this.wierszSchemaEwidencja = wierszSchemaEwidencja;
        this.wierszSchemaEwidencjaMacierzysty = wierszSchemaEwidencjaMacierzysty;
        this.netto = Z.z(netto.doubleValue());
        this.vat = Z.z(vat.doubleValue());
       }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public SchemaEwidencja getWierszSchemaEwidencja() {
        return wierszSchemaEwidencja;
    }

    public void setWierszSchemaEwidencja(SchemaEwidencja wierszSchemaEwidencja) {
        this.wierszSchemaEwidencja = wierszSchemaEwidencja;
    }

    public SchemaEwidencja getWierszSchemaEwidencjaMacierzysty() {
        return wierszSchemaEwidencjaMacierzysty;
    }

    public void setWierszSchemaEwidencjaMacierzysty(SchemaEwidencja wierszSchemaEwidencjaMacierzysty) {
        this.wierszSchemaEwidencjaMacierzysty = wierszSchemaEwidencjaMacierzysty;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public Deklaracjevat getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(Deklaracjevat deklaracja) {
        this.deklaracja = deklaracja;
    }
    
    @Override
    public String toString() {
        if (wierszSchemaEwidencjaMacierzysty != null) {
            return "EwidPoz{" + "polenetto=" + polenetto + ", polevat=" + polevat + ", odnalezionyWierszSchemaEwidencja=" + wierszSchemaEwidencja.getEvewidencja().getNazwa() + ", odnalezionyWierszSchemaEwidencjaMacierzysty=" + wierszSchemaEwidencjaMacierzysty.getEvewidencja().getNazwa() + ", netto=" + netto + ", vat=" + vat + ", tylkonetto=" + wierszSchemaEwidencja.getEvewidencja().isTylkoNetto() + '}';
        } else {
            return "EwidPoz{" + "polenetto=" + polenetto + ", polevat=" + polevat + ", odnalezionyWierszSchemaEwidencja=" + wierszSchemaEwidencja.getEvewidencja().getNazwa() + ", odnalezionyWierszSchemaEwidencjaMacierzysty=null, netto=" + netto + ", vat=" + vat + ", tylkonetto=" + wierszSchemaEwidencja.getEvewidencja().isTylkoNetto() + '}';
        }
    }
    
    
    
    
}
