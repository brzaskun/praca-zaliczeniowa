/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(name = "stronawn", catalog = "pkpir", schema = "")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "StronaWn.findByStronaWnKontoWaluta", query = "SELECT s FROM StronaWn s WHERE s.konto = :konto AND s.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty")
})
public class StronaWn extends StronaWiersza implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "konto", referencedColumnName = "id")
    @ManyToOne
    private Konto konto;
    @OneToMany(mappedBy="stronaWn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
    @Column(name = "WnReadOnly")
    private boolean WnReadOnly;
    
   
    public StronaWn() {
        this.transakcje = new ArrayList<>();
    }
    

    public StronaWn(Wiersz nowywiersz) {
        super();
        this.transakcje = new ArrayList<>();
        this.wiersz = nowywiersz;
    }

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public boolean isWnReadOnly() {
        return WnReadOnly;
    }

    public void setWnReadOnly(boolean WnReadOnly) {
        this.WnReadOnly = WnReadOnly;
    }
    
    
    
   
    
    
}
