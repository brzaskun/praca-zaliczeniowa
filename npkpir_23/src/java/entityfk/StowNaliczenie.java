/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

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
    @UniqueConstraint(columnNames = {"miejsce", "rok", "mc", "kategoria"})
})
@NamedQueries({
    @NamedQuery(name = "StowNaliczenie.DeleteNaliczoneMcRok", query = "DELETE FROM StowNaliczenie p WHERE p.miejsce.podatnikObj = :podatnikObj AND  p.rok = :rok AND p.mc = :mc AND p.kategoria = :kategoria")
})
public class StowNaliczenie implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    protected Integer id;
    @JoinColumn(name="miejsce", referencedColumnName = "id")
    private MiejsceSuper miejsce;
    @Column(name="rok")
    private String rok;
    @Column(name="mc")
    private String mc;
    @Column(name="przych0koszt1")
    private boolean przych0koszt1;
    @Column(name="kwota")
    private double kwota;
    @Column(name="kategoria")
    private String kategoria;

    public StowNaliczenie() {
    }

    public StowNaliczenie(MiejscePrzychodow p) {
        this.miejsce = p;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.miejsce);
        hash = 97 * hash + Objects.hashCode(this.rok);
        hash = 97 * hash + Objects.hashCode(this.mc);
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
        final StowNaliczenie other = (StowNaliczenie) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.kategoria, other.kategoria)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.miejsce, other.miejsce)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StowNaliczenie{" + "miejsce=" + miejsce.opismiejsca + ", rok=" + rok + ", mc=" + mc + ", przych0koszt1=" + przych0koszt1 + ", kwota=" + kwota + ", kategoria=" + kategoria + '}';
    }

    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MiejsceSuper getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(MiejsceSuper miejsce) {
        this.miejsce = miejsce;
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

    public boolean isPrzych0koszt1() {
        return przych0koszt1;
    }

    public void setPrzych0koszt1(boolean przych0koszt1) {
        this.przych0koszt1 = przych0koszt1;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
 
    
}
