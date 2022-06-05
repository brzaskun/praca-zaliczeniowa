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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ekwiwalentskladniki", uniqueConstraints = {
    @UniqueConstraint(columnNames={"rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EkwiwalentSkladniki.findAll", query = "SELECT m FROM EkwiwalentSkladniki m"),
    @NamedQuery(name = "EkwiwalentSkladniki.findbyRok", query = "SELECT m FROM EkwiwalentSkladniki m WHERE m.rok=:rok")
})
public class EkwiwalentSkladniki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "kwota")
    private double kwota;
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "mc")
    private String mc;
    @Column(name = "dziennaliczenia")
    private String dziennaliczenia;
//    @OneToOne(mappedBy = "ekwiwalentskladniki")
//    private Pasekwynagrodzen pasekwynagrodzen;




    public EkwiwalentSkladniki() {
    }

    public EkwiwalentSkladniki(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

   
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDziennaliczenia() {
        return dziennaliczenia;
    }

    public void setDziennaliczenia(String dziennaliczenia) {
        this.dziennaliczenia = dziennaliczenia;
    }

//    public Pasekwynagrodzen getPasekwynagrodzen() {
//        return pasekwynagrodzen;
//    }
//
//    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
//        this.pasekwynagrodzen = pasekwynagrodzen;
//    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.rok);
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
        final EkwiwalentSkladniki other = (EkwiwalentSkladniki) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

     
}
