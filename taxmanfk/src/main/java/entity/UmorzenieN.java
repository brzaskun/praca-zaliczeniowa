/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import em.Em;
import embeddable.Mce;
import embeddable.Umorzenie;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umorzenie", uniqueConstraints = {
    @UniqueConstraint(columnNames={"rokUmorzenia", "mcUmorzenia", "srodekTrw"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UmorzenieN.findAll", query = "SELECT k FROM UmorzenieN k"),
    @NamedQuery(name = "UmorzenieN.findStr", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw = :srodekTrw"),
    @NamedQuery(name = "UmorzenieN.findByDokfk", query = "SELECT k FROM UmorzenieN k WHERE k.dokfk = :dokfk"),
    @NamedQuery(name = "UmorzenieN.findByDok", query = "SELECT k FROM UmorzenieN k WHERE k.dok = :dok"),
    @NamedQuery(name = "UmorzenieN.findStrMcRok", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw.id = :srt AND k.mcUmorzenia = :mc AND k.rokUmorzenia = :rok"),
    @NamedQuery(name = "UmorzenieN.findStrNazwaMcRok", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw.nazwa = :srt AND k.srodekTrw.podatnik = :podatnik AND k.mcUmorzenia = :mc AND k.rokUmorzenia = :rok"),
    @NamedQuery(name = "UmorzenieN.findByPodatnikRokMc", query = "SELECT k FROM UmorzenieN k WHERE k.srodekTrw.podatnik = :podatnik AND k.rokUmorzenia = :rok AND k.mcUmorzenia = :mc")
})
public class UmorzenieN implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "dokfk", referencedColumnName = "id")
    private Dokfk dokfk;
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    private Dok dok;

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
        hash = 97 * hash + Objects.hashCode(this.id);
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
        if (kontonetto != null && srodekTrw.getDokfk() != null){
            return "UmorzenieN{" + "id=" + id + ", srodekTrw=" + srodekTrw.getNazwa() + ", podatnik =" + srodekTrw.getDokfk().getPodatnikObj().getNazwapelna() + ", nrUmorzenia=" + nrUmorzenia + ", rokUmorzenia=" + rokUmorzenia + ", mcUmorzenia=" + mcUmorzenia + ", kwota=" + kwota + ", kontonetto=" + kontonetto + ", kontoumorzenie=" + kontoumorzenie + ", rodzaj=" + rodzaj + '}';
        } else {
            return "UmorzenieN{" + "id=" + id + ", srodekTrw=" + srodekTrw.getNazwa() + ", podatnik =" + srodekTrw.getPodatnik() + ", nrUmorzenia=" + nrUmorzenia + ", rokUmorzenia=" + rokUmorzenia + ", mcUmorzenia=" + mcUmorzenia + ", kwota=" + kwota + ", rodzaj=" + rodzaj + '}';
        }

    }

   

    
    //<editor-fold defaultstate="collapsed" desc="comment">
  
    
    public SrodekTrw getSrodekTrw() {
        return srodekTrw;
    }
    
    public void setSrodekTrw(SrodekTrw srodekTrw) {
        this.srodekTrw = srodekTrw;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public String getMcUmorzeniaNazwa() {
        return Mce.getNumberToNazwamiesiaca().get(mcUmorzenia);
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

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }
    
    
//</editor-fold>
    
      public static void main(String[] args)  {
        EntityManager em = Em.getEm();
//        List<SrodekTrw> srodki = em.createNamedQuery("SrodekTrw.findAll").getResultList();
//        for (SrodekTrw p : srodki) {
//            p.setPlanumorzen(new ArrayList<UmorzenieN>());
//            List<Umorzenie> umorzenia = p.getUmorzWyk();
//            List<UmorzenieN> umorzenianowe = Collections.synchronizedList(new ArrayList<>());
//            if (umorzenia != null && umorzenia.size() > 0) {
//                error.E.s(p.getNazwa());
//                for (Umorzenie r : umorzenia) {
//                    UmorzenieN u = new UmorzenieN(r);
//                    if (p.getKontonetto() != null) {
//                        u.setKontonetto(p.getKontonetto().getPelnynumer());
//                    }
//                    if (p.getKontoumorzenie() != null) {
//                        u.setKontoumorzenie(p.getKontoumorzenie().getPelnynumer());
//                    }
//                    u.setSrodekTrw(p);
//                    p.getPlanumorzen().add(u);
//                    umorzenianowe.add(u);
//                }
//            }
//            Em.saveList(em, umorzenianowe);
//            Em.edit(em, p);
//        }
//        for (SrodekTrw p : srodki) {
//            List<UmorzenieN> nowe = em.createNamedQuery("UmorzenieN.findStr").setParameter("srt", p).getResultList();
//            List<Umorzenie> stare = p.getUmorzWyk();
//            double sumastare = 0.0;
//            double sumanowe = 0.0;
//            for (Umorzenie r1 : stare) {
//                sumastare += r1.getKwota().doubleValue();
//            }
//            for (UmorzenieN r2 : nowe) {
//                sumanowe += r2.getKwota();
//            }
//            if (Z.z(sumanowe) != Z.z(sumastare)) {
//                error.E.s("Bladddddd "+p.getNazwa()+" sumastare: "+sumastare+" sumanowe: "+sumanowe);
//            }
//        }
//        List<SrodekTrw> srodki = em.createNamedQuery("SrodekTrw.findAll").getResultList();
//        for (SrodekTrw p : srodki) {
//            p.setPlanumorzen(new ArrayList<UmorzenieN>());
//            List<Umorzenie> umorzenia = p.getUmorzWyk();
//            List<UmorzenieN> umorzenianowe = Collections.synchronizedList(new ArrayList<>());
//            if (umorzenia != null && umorzenia.size() > 0) {
//                error.E.s(p.getNazwa());
//                for (Umorzenie r : umorzenia) {
//                    UmorzenieN u = new UmorzenieN(r);
//                    if (p.getKontonetto() != null) {
//                        u.setKontonetto(p.getKontonetto().getPelnynumer());
//                    }
//                    if (p.getKontoumorzenie() != null) {
//                        u.setKontoumorzenie(p.getKontoumorzenie().getPelnynumer());
//                    }
//                    u.setSrodekTrw(p);
//                    p.getPlanumorzen().add(u);
//                    umorzenianowe.add(u);
//                }
//            }
//            Em.saveList(em, umorzenianowe);
//            Em.edit(em, p);
//        }
//        for (SrodekTrw p : srodki) {
//            List<UmorzenieN> nowe = em.createNamedQuery("UmorzenieN.findStr").setParameter("srt", p).getResultList();
//            List<Umorzenie> stare = p.getUmorzWyk();
//            double sumastare = 0.0;
//            double sumanowe = 0.0;
//            for (Umorzenie r1 : stare) {
//                sumastare += r1.getKwota().doubleValue();
//            }
//            for (UmorzenieN r2 : nowe) {
//                sumanowe += r2.getKwota();
//            }
//            if (Z.z(sumanowe) != Z.z(sumastare)) {
//                error.E.s("Bladddddd "+p.getNazwa()+" sumastare: "+sumastare+" sumanowe: "+sumanowe);
//            }
//        }
        error.E.s("start");
            List<Amodok> amodoklista = em.createNamedQuery("Amodok.findAll").getResultList();
            for (Amodok p : amodoklista) {
                p.setPlanumorzen(new ArrayList<UmorzenieN>());
                List<Umorzenie> stare = p.getUmorzenia();
                for (Umorzenie r : stare) {
                    UmorzenieN nowe = pobierz(em, r, p.getAmodokPK().getPodatnik());
                    if (nowe != null) {
                        nowe.setAmodok(p);
                        nowe.setRodzaj(r.getRodzaj());
                        Em.edit(em, nowe);
                        p.getPlanumorzen().add(nowe);
                    } else {
                    }
                }
                Em.edit(em, p);
                //error.E.s("edycja "+p.getAmodokPK().toString());
            }
    }
      
    public static UmorzenieN pobierz(EntityManager em, Umorzenie r, String podatnik) {
        UmorzenieN zwrot = null;
        try {
            zwrot = (UmorzenieN) em.createNamedQuery("UmorzenieN.findStrMcRok").setParameter("srt", r.getSrodekTrwID()).setParameter("mc", r.getMcUmorzenia()).setParameter("rok", r.getRokUmorzenia()).getSingleResult();
        } catch (Exception e) {
        }
        try {
            if (zwrot == null) {
                zwrot = (UmorzenieN) em.createNamedQuery("UmorzenieN.findStrNazwaMcRok").setParameter("srt", r.getNazwaSrodka()).setParameter("podatnik", podatnik).setParameter("mc", r.getMcUmorzenia()).setParameter("rok", r.getRokUmorzenia()).getSingleResult();
            }
        } catch (Exception e) {
        }   
        return zwrot;
    }
   
    
    
}
