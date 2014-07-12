/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "fktest", schema = "")
@XmlRootElement
public class Transakcja  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @AttributeOverride(name = "numertransakcji", column = @Column(name = "idtransakcji"))
    @EmbeddedId 
    private TransakcjaPK id;
    @MapsId("rozliczajacyId")
    @JoinColumn(name="rozliczajacy_id")
    @ManyToOne
    private Rozrachunek rozliczajacy;
    @MapsId("rozliczanyId")
    @JoinColumn(name="rozliczany_id")
    @ManyToOne
    private Rozrachunek rozliczany;
    @MapsId("numertransakcji")
    @GeneratedValue
    private Integer numertransakcjiID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private double kwota;

    public Transakcja() {
    }

    public Transakcja(TransakcjaPK id, Rozrachunek rozliczajacy, Rozrachunek rozliczany, double kwota) {
        this.id = id;
        this.rozliczajacy = rozliczajacy;
        this.rozliczany = rozliczany;
        this.kwota = kwota;
    }


    public Rozrachunek getRozliczajacy() {
        return rozliczajacy;
    }

    public void setRozliczajacy(Rozrachunek rozliczajacy) {
        this.rozliczajacy = rozliczajacy;
    }

    public Rozrachunek getRozliczany() {
        return rozliczany;
    }

    public void setRozliczany(Rozrachunek rozliczany) {
        this.rozliczany = rozliczany;
    }
  
    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Transakcja other = (Transakcja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   
    
    
    
    
}
