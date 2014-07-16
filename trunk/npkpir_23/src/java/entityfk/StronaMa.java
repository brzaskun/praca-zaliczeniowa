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
@Table(name = "stronama", catalog = "pkpir", schema = "")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "StronaMa.findByStronaMaKontoWaluta", query = "SELECT s FROM StronaMa s WHERE s.konto = :konto AND s.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty"),
})
public class StronaMa extends StronaWiersza implements Serializable {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "konto", referencedColumnName = "id")
    @ManyToOne
    private Konto konto;
    @OneToMany(mappedBy = "stronaMa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
    @Column(name = "MaReadOnly")
    private boolean MaReadOnly;

      

    public StronaMa() {
        this.transakcje = new ArrayList<>();
    }


    public StronaMa(Wiersz nowywiersz) {
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

    public boolean isMaReadOnly() {
        return MaReadOnly;
    }

    public void setMaReadOnly(boolean MaReadOnly) {
        this.MaReadOnly = MaReadOnly;
    }

    
    

}
