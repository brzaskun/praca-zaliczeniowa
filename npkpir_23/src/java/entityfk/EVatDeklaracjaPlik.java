/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.*;
import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@Entity
@NamedQueries({
    @NamedQuery(name = "EVatDeklaracjaPlik.findByPodatnik", query = "SELECT k FROM EVatDeklaracjaPlik k WHERE k.podatnikObj = :podatnik"),
    @NamedQuery(name = "EVatDeklaracjaPlik.findByPodatnikRok", query = "SELECT k FROM EVatDeklaracjaPlik k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok"),
    @NamedQuery(name = "EVatDeklaracjaPlik.findByPodatnikRokMc", query = "SELECT k FROM EVatDeklaracjaPlik k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc")
})
public class EVatDeklaracjaPlik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Column(name = "lp")
    private int lp;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnikObj;
    @Column(name = "dataoperacji")
    private String dataoperacji;
    @Size(max = 2)
    @Column(name = "mcEw")
    private String mcEw;
    @Size(max = 4)
    @Column(name = "rokEw")
    private String rokEw;
    @Column(name = "nazwapliku")
    private String nazwapliku;
    @Column(name = "uwagi")
    private String uwagi;
    @Column(name = "upo")
    private String upo;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;


    public EVatDeklaracjaPlik() {
    }

    
    
    public EVatDeklaracjaPlik(WpisView wpisView, String nazwakrotka, String dzis) {
        this.dataoperacji = dzis;
        this.mcEw = wpisView.getMiesiacWpisu();
        this.rokEw = wpisView.getRokWpisuSt();
        this.nazwapliku = nazwakrotka;
        this.podatnikObj = wpisView.getPodatnikObiekt();
        this.wprowadzil = wpisView.getUzer();
    }

   
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">\

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public String getMcEw() {
        return mcEw;
    }

    public void setMcEw(String mcEw) {
        this.mcEw = mcEw;
    }

    public String getRokEw() {
        return rokEw;
    }

    public void setRokEw(String rokEw) {
        this.rokEw = rokEw;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getNazwapliku() {
        return nazwapliku;
    }

    public void setNazwapliku(String nazwapliku) {
        this.nazwapliku = nazwapliku;
    }

    public String getUpo() {
        return upo;
    }

    public void setUpo(String upo) {
        this.upo = upo;
    }


    public String getMcRok() {
        String zwrot = "";
        if (this.mcEw!=null) {
            zwrot = this.mcEw+"/"+this.rokEw;
        }
        return zwrot;
    }
   
    
    public String getDataoperacji() {
        return dataoperacji;
    }
    
    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
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
        final EVatDeklaracjaPlik other = (EVatDeklaracjaPlik) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EVatDeklaracjaPlik{" + "id=" + id + ", lp=" + lp + ", podatnikObj=" + podatnikObj + ", dataoperacji=" + dataoperacji + ", mcEw=" + mcEw + ", rokEw=" + rokEw + ", nazwapliku=" + nazwapliku + ", upo=" + upo + '}';
    }

    


     
    
   
    
}


