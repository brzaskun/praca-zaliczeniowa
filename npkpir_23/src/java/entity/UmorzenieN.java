/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import em.Em;
import embeddable.Umorzenie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umorzenie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UmorzenieN.findAll", query = "SELECT k FROM UmorzenieN k"),
    @NamedQuery(name = "UmorzenieN.findStr", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw = :srt"),
    @NamedQuery(name = "UmorzenieN.findStrMcRok", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw.id = :srt AND k.mcUmorzenia = :mc AND k.rokUmorzenia = :rok")
})
public class UmorzenieN implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "srodekTrw", referencedColumnName = "id")
    private SrodekTrw srodekTrw;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "amodok_mc", referencedColumnName = "mc"),
        @JoinColumn(name = "amodok_podatnik", referencedColumnName = "podatnik"),
        @JoinColumn(name = "amodok_rok", referencedColumnName = "rok")
    })
    private Amodok amodok;
    @Column(name = "nrUmorzenia")
    private int nrUmorzenia;
    @Column(name = "rokUmorzenia")
    private int rokUmorzenia;
    @Column(name = "mcUmorzenia")
    private int mcUmorzenia;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kontonetto")
    private String kontonetto;
    @Column(name = "kontoumorzenie")
    private String kontoumorzenie;
    //chodzi o to czy środek trwały czy wnip
    @Column(name = "rodzaj")
    private String rodzaj;

    public UmorzenieN() {
    }

    public UmorzenieN(Umorzenie u) {
        this.kwota = u.getKwota().doubleValue();
        this.mcUmorzenia = u.getMcUmorzenia();
        this.rokUmorzenia = u.getRokUmorzenia();
        this.nrUmorzenia = u.getNrUmorzenia();
        this.rodzaj = u.getRodzaj();
        this.kontonetto = u.getKontonetto();
        this.kontoumorzenie = u.getKontoumorzenie();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final UmorzenieN other = (UmorzenieN) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UmorzenieN{" + "id=" + id + ", srodekTrw=" + srodekTrw.getNazwa() + ", nrUmorzenia=" + nrUmorzenia + ", rokUmorzenia=" + rokUmorzenia + ", mcUmorzenia=" + mcUmorzenia + ", kwota=" + kwota + ", kontonetto=" + kontonetto + ", kontoumorzenie=" + kontoumorzenie + ", rodzaj=" + rodzaj + '}';
    }

   

    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public SrodekTrw getSrodekTrw() {
        return srodekTrw;
    }
    
    public void setSrodekTrw(SrodekTrw srodekTrw) {
        this.srodekTrw = srodekTrw;
    }
    
    public int getNrUmorzenia() {
        return nrUmorzenia;
    }
    
    public void setNrUmorzenia(int nrUmorzenia) {
        this.nrUmorzenia = nrUmorzenia;
    }
    
    public int getRokUmorzenia() {
        return rokUmorzenia;
    }
    
    public void setRokUmorzenia(int rokUmorzenia) {
        this.rokUmorzenia = rokUmorzenia;
    }
    
    public int getMcUmorzenia() {
        return mcUmorzenia;
    }
    
    public void setMcUmorzenia(int mcUmorzenia) {
        this.mcUmorzenia = mcUmorzenia;
    }
    
    public double getKwota() {
        return kwota;
    }
    
    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getKontonetto() {
        return kontonetto;
    }

    public void setKontonetto(String kontonetto) {
        this.kontonetto = kontonetto;
    }

    public String getKontoumorzenie() {
        return kontoumorzenie;
    }

    public void setKontoumorzenie(String kontoumorzenie) {
        this.kontoumorzenie = kontoumorzenie;
    }

    public Amodok getAmodok() {
        return amodok;
    }

    public void setAmodok(Amodok amodok) {
        this.amodok = amodok;
    }
  
    
    public String getRodzaj() {
        return rodzaj;
    }
    
    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }
//</editor-fold>
    
      public static void main(String[] args)  {
        EntityManager em = Em.getEm();
        List<SrodekTrw> srodki = em.createNamedQuery("SrodekTrw.findAll").getResultList();
        for (SrodekTrw p : srodki) {
            p.setPlanumorzen(new ArrayList<UmorzenieN>());
            List<Umorzenie> umorzenia = p.getUmorzWyk();
            List<UmorzenieN> umorzenianowe = new ArrayList<>();
            if (umorzenia != null && umorzenia.size() > 0) {
                System.out.println(p.getNazwa());
                for (Umorzenie r : umorzenia) {
                    UmorzenieN u = new UmorzenieN(r);
                    if (p.getKontonetto() != null) {
                        u.setKontonetto(p.getKontonetto().getPelnynumer());
                    }
                    if (p.getKontoumorzenie() != null) {
                        u.setKontoumorzenie(p.getKontoumorzenie().getPelnynumer());
                    }
                    u.setSrodekTrw(p);
                    p.getPlanumorzen().add(u);
                    umorzenianowe.add(u);
                }
            }
            Em.saveList(em, umorzenianowe);
            Em.edit(em, p);
        }
        for (SrodekTrw p : srodki) {
            List<UmorzenieN> nowe = em.createNamedQuery("UmorzenieN.findStr").setParameter("srt", p).getResultList();
            List<Umorzenie> stare = p.getUmorzWyk();
            double sumastare = 0.0;
            double sumanowe = 0.0;
            for (Umorzenie r1 : stare) {
                sumastare += r1.getKwota().doubleValue();
            }
            for (UmorzenieN r2 : nowe) {
                sumanowe += r2.getKwota();
            }
            if (Z.z(sumanowe) != Z.z(sumastare)) {
                System.out.println("Bladddddd "+p.getNazwa()+" sumastare: "+sumastare+" sumanowe: "+sumanowe);
            }
        }
        System.out.println("e");
            List<Amodok> amodoklista = em.createNamedQuery("Amodok.findAll").getResultList();
            for (Amodok p : amodoklista) {
                p.setPlanumorzen(new ArrayList<UmorzenieN>());
                List<Umorzenie> stare = p.getUmorzenia();
                for (Umorzenie r : stare) {
                    UmorzenieN nowe = pobierz(em, r);
                    if (nowe != null) {
                        p.getPlanumorzen().add(nowe);
                        nowe.setAmodok(p);
                        System.out.println("jest "+nowe.getAmodok().toString());
                    }
                }
                Em.edit(em, p.getPlanumorzen());
                Em.edit(em, p);
                //System.out.println("edycja "+p.getAmodokPK().toString());
            }
            System.out.println("KONIEC KONIEC KONIEC");
    }
      
    public static UmorzenieN pobierz(EntityManager em, Umorzenie r) {
        UmorzenieN zwrot = null;
        try {
            zwrot = (UmorzenieN) em.createNamedQuery("UmorzenieN.findStrMcRok").setParameter("srt", r.getSrodekTrwID()).setParameter("mc", r.getMcUmorzenia()).setParameter("rok", r.getRokUmorzenia()).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }
   
    
    
}
