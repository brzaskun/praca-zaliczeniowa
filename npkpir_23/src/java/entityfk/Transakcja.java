/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "transakcja", catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rozliczajacy_id, nowaTransakcja_id"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcja.findByNowaTransakcja", query = "SELECT t FROM Transakcja t WHERE t.nowaTransakcja = :nowatransakcja"),
    @NamedQuery(name = "Transakcja.findByRozliczajacy", query = "SELECT t FROM Transakcja t WHERE t.rozliczajacy = :rozliczajacy"),
    @NamedQuery(name = "Transakcja.findByKonto", query = "SELECT t FROM Transakcja t WHERE t.nowaTransakcja.konto = :konto"),
    @NamedQuery(name = "Transakcja.findByPodatnikRok", query = "SELECT t FROM Transakcja t WHERE t.nowaTransakcja.wiersz.dokfk.rok = :rok AND t.nowaTransakcja.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "Transakcja.findByPodatnikBO", query = "SELECT t FROM Transakcja t WHERE t.nowaTransakcja.wiersz IS NULL AND t.nowaTransakcja.konto.podatnik = :podatnik"),
    @NamedQuery(name = "Transakcja.findByPodatnikRokRozniceKursowe", query = "SELECT t FROM Transakcja t WHERE t.rozliczajacy.wiersz.dokfk.rok = :rok AND t.rozliczajacy.wiersz.dokfk.miesiac = :mc AND t.rozliczajacy.wiersz.dokfk.podatnikObj = :podatnikObj AND t.roznicekursowe != 0"),
    @NamedQuery(name = "Transakcja.findByPodatnikBORozniceKursowe", query = "SELECT t FROM Transakcja t WHERE t.nowaTransakcja.wiersz IS NULL AND t.nowaTransakcja.konto.podatnik = :podatnik AND t.roznicekursowe != 0 AND t.rozliczajacy.wiersz.dokfk.rok = :rok AND t.rozliczajacy.wiersz.dokfk.miesiac = :mc")
})
@Cacheable(false)
public class Transakcja  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    //to jest id generowany przez serwer
    private Integer id;
    @JoinColumn(name="rozliczajacy_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StronaWiersza rozliczajacy;
    @JoinColumn(name="nowaTransakcja_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StronaWiersza nowaTransakcja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwotatransakcji")
    private double kwotatransakcji;
    @Column(name = "poprzedniakwota")
    private double poprzedniakwota;
    @Column(name = "roznicekursowe")
    private double roznicekursowe;
    @Column(name= "datarozrachunku")
    private String datarozrachunku;
    @Column(name = "kwotawwalucierachunku")
    private double kwotawwalucierachunku;
    
    
    public Transakcja() {
    }

    
   public Transakcja(StronaWiersza rozliczajacy, StronaWiersza nowaTransakcja) {
        this.kwotatransakcji = 0.0;
        this.poprzedniakwota = 0.0;
        this.roznicekursowe = 0.0;
        this.rozliczajacy = rozliczajacy;
        this.nowaTransakcja = nowaTransakcja;
    }
   
   public String getOpisWiersza() {
       String zwrot = this.nowaTransakcja.getWiersz() != null ? this.nowaTransakcja.getWiersz().getOpisWiersza() : this.nowaTransakcja.getOpisBO();
       return  zwrot.length() > 40 ? zwrot.substring(0, 39) : zwrot;
   }
   
   public boolean czymoznaedytowac() {
       boolean zwrot = false;
       if (Z.z(this.nowaTransakcja.getPozostalo())==0.0 && Z.z(this.kwotatransakcji)==0.0) {
           zwrot = true;
       }
       return zwrot;
   }

   //<editor-fold defaultstate="collapsed" desc="comment">
   public StronaWiersza getRozliczajacy() {
       return rozliczajacy;
   }
   
   public void setRozliczajacy(StronaWiersza rozliczajacy) {
       this.rozliczajacy = rozliczajacy;
   }
   
   public StronaWiersza getNowaTransakcja() {
       return nowaTransakcja;
   }
   
   public void setNowaTransakcja(StronaWiersza nowaTransakcja) {
       this.nowaTransakcja = nowaTransakcja;
   }
   
   public double getKwotatransakcji() {
       return kwotatransakcji;
   }
   
   public void setKwotatransakcji(double kwotatransakcji) {
       this.kwotatransakcji = kwotatransakcji;
   }
   
   public double getPoprzedniakwota() {
       return poprzedniakwota;
   }
   
   public void setPoprzedniakwota(double poprzedniakwota) {
       this.poprzedniakwota = poprzedniakwota;
   }
   
  
   public double getRoznicekursowe() {
       return roznicekursowe;
   }
   
   public void setRoznicekursowe(double roznicekursowe) {
       this.roznicekursowe = roznicekursowe;
   }
   
   public String getDatarozrachunku() {
       return datarozrachunku;
   }
   
   public void setDatarozrachunku(String datarozrachunku) {
       this.datarozrachunku = datarozrachunku;
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
   public double getKwotawwalucierachunku() {
       return kwotawwalucierachunku;
   }
   
   public void setKwotawwalucierachunku(double kwotawwalucierachunku) {
       this.kwotawwalucierachunku = kwotawwalucierachunku;
   }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.rozliczajacy);
        hash = 71 * hash + Objects.hashCode(this.nowaTransakcja);
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
        final Transakcja other = (Transakcja) obj;
        if (!Objects.equals(this.rozliczajacy, other.rozliczajacy)) {
            return false;
        }
        if (!Objects.equals(this.nowaTransakcja, other.nowaTransakcja)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Transakcja{" + "rozliczajacy=" + rozliczajacy.getId() + ", nowaTransakcja=" + nowaTransakcja.getId() + ", kwotatransakcji=" + kwotatransakcji + ", poprzedniakwota=" + poprzedniakwota + ", roznicekursowe=" + roznicekursowe + ", datarozrachunku=" + datarozrachunku + ", kwotawwalucierachunku=" + kwotawwalucierachunku + '}';
    }

    
   
    
    

    

    
}

