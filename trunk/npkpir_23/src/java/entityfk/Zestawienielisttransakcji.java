/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.Transakcja;
import embeddablefk.WierszStronafkPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zestawienielisttransakcji", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zestawienielisttransakcji.findAll", query = "SELECT z FROM Zestawienielisttransakcji z"),
    @NamedQuery(name = "Zestawienielisttransakcji.findByKluczlisty", query = "SELECT z FROM Zestawienielisttransakcji z WHERE z.kluczlisty = :kluczlisty"),
    @NamedQuery(name = "Zestawienielisttransakcji.findByIdzestawienia", query = "SELECT z FROM Zestawienielisttransakcji z WHERE z.idzestawienia = :idzestawienia")})
public class Zestawienielisttransakcji implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, name = "idzestawienia")
    private Integer idzestawienia;
    @Column(name="kluczlisty")
    private WierszStronafkPK kluczlisty;
    @Lob
    @Column(name="listatransakcji")
    private List<Transakcja> listatransakcji;

    public Zestawienielisttransakcji() {
    }

    public Zestawienielisttransakcji(Integer idzestawienia) {
        this.idzestawienia = idzestawienia;
    }

    public Integer getIdzestawienia() {
        return idzestawienia;
    }

    public void setIdzestawienia(Integer idzestawienia) {
        this.idzestawienia = idzestawienia;
    }

    public WierszStronafkPK getKluczlisty() {
        return kluczlisty;
    }

    public void setKluczlisty(WierszStronafkPK kluczlisty) {
        this.kluczlisty = kluczlisty;
    }

    
   public List<Transakcja> getListatransakcji() {
        return listatransakcji;
    }

    public void setListatransakcji(List<Transakcja> listatransakcji) {
        this.listatransakcji = listatransakcji;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idzestawienia != null ? idzestawienia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zestawienielisttransakcji)) {
            return false;
        }
        Zestawienielisttransakcji other = (Zestawienielisttransakcji) object;
        if ((this.idzestawienia == null && other.idzestawienia != null) || (this.idzestawienia != null && !this.idzestawienia.equals(other.idzestawienia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Zestawienielisttransakcji[ idzestawienia=" + idzestawienia + " ]";
    }
    
}
