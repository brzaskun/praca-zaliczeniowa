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
    @JoinColumn(name = "podatnik", referencedColumnName = "nip")
    protected Podatnik podatnik;
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
    
    public ParamSuper() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParamSuper{" + "podatnik=" + podatnik + ", mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", parametr=" + parametr + '}';
    }

    
}
