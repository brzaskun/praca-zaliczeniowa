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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class ParamSuper  implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    protected Integer id;
    
    @Column(name = "mcOd")
    protected String mcOd;
    @Column(name = "rokOd")
    protected String rokOd;
    @Column(name = "mcDo")
    protected String mcDo;
    @Column(name = "rokDo")
    protected String rokDo;
    @Column(name = "parametr")
    protected String parametr;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    protected Podatnik podatnik;
    protected int podid;

    public int getPodid() {
        return podid;
    }

    public void setPodid(int podid) {
        this.podid = podid;
    }
    
    public ParamSuper() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.mcOd);
        hash = 47 * hash + Objects.hashCode(this.rokOd);
        hash = 47 * hash + Objects.hashCode(this.mcDo);
        hash = 47 * hash + Objects.hashCode(this.rokDo);
        hash = 47 * hash + Objects.hashCode(this.parametr);
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
        final ParamSuper other = (ParamSuper) obj;
        if (!Objects.equals(this.mcOd, other.mcOd)) {
            return false;
        }
        if (!Objects.equals(this.rokOd, other.rokOd)) {
            return false;
        }
        if (!Objects.equals(this.mcDo, other.mcDo)) {
            return false;
        }
        if (!Objects.equals(this.rokDo, other.rokDo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "ParamSuper{" + "mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", parametr=" + parametr + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMcOd() {
        return mcOd;
    }

    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }

    public String getRokOd() {
        return rokOd;
    }

    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }

    public String getMcDo() {
        return mcDo;
    }

    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }

    public String getRokDo() {
        return rokDo;
    }

    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }

    public String getParametr() {
        return parametr;
    }

    public void setParametr(String parametr) {
        this.parametr = parametr;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    
   
    
}
