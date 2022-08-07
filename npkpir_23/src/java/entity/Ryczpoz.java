/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.RyczaltPodatek;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ryczpoz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ryczpoz.findAll", query = "SELECT r FROM Ryczpoz r"),
    @NamedQuery(name = "Ryczpoz.findById", query = "SELECT r FROM Ryczpoz r WHERE r.id = :id"),
    @NamedQuery(name = "Ryczpoz.findByDozaplaty", query = "SELECT r FROM Ryczpoz r WHERE r.dozaplaty = :dozaplaty"),
    @NamedQuery(name = "Ryczpoz.findByNaleznazal", query = "SELECT r FROM Ryczpoz r WHERE r.naleznazal = :naleznazal"),
    @NamedQuery(name = "Ryczpoz.findByNalzalodpoczrok", query = "SELECT r FROM Ryczpoz r WHERE r.nalzalodpoczrok = :nalzalodpoczrok"),
    @NamedQuery(name = "Ryczpoz.findByPkpirM", query = "SELECT r FROM Ryczpoz r WHERE r.pkpirM = :pkpirM"),
    @NamedQuery(name = "Ryczpoz.findByPkpirR", query = "SELECT r FROM Ryczpoz r WHERE r.pkpirR = :pkpirR"),
    @NamedQuery(name = "Ryczpoz.findByPodatek", query = "SELECT r FROM Ryczpoz r WHERE r.podatek = :podatek"),
    @NamedQuery(name = "Ryczpoz.findByPodatnik", query = "SELECT r FROM Ryczpoz r WHERE r.podatnik = :podatnik"),
    @NamedQuery(name = "Ryczpoz.findByPodstawa", query = "SELECT r FROM Ryczpoz r WHERE r.podstawa = :podstawa"),
    @NamedQuery(name = "Ryczpoz.findByPrzelano", query = "SELECT r FROM Ryczpoz r WHERE r.przelano = :przelano"),
    @NamedQuery(name = "Ryczpoz.findByPrzychody", query = "SELECT r FROM Ryczpoz r WHERE r.przychody = :przychody"),
    @NamedQuery(name = "Ryczpoz.findByPrzychodyudzial", query = "SELECT r FROM Ryczpoz r WHERE r.przychodyudzial = :przychodyudzial"),
    @NamedQuery(name = "Ryczpoz.findByTerminwplaty", query = "SELECT r FROM Ryczpoz r WHERE r.terminwplaty = :terminwplaty"),
    @NamedQuery(name = "Ryczpoz.findByUdzial", query = "SELECT r FROM Ryczpoz r WHERE r.udzial = :udzial"),
    @NamedQuery(name = "Ryczpoz.findByUdzialowiec", query = "SELECT r FROM Ryczpoz r WHERE r.udzialowiec = :udzialowiec"),
    @NamedQuery(name = "Ryczpoz.findByWynik", query = "SELECT r FROM Ryczpoz r WHERE r.wynik = :wynik"),
    @NamedQuery(name = "Ryczpoz.findByZamkniety", query = "SELECT r FROM Ryczpoz r WHERE r.zamkniety = :zamkniety"),
    @NamedQuery(name = "Ryczpoz.findByZus51", query = "SELECT r FROM Ryczpoz r WHERE r.zus51 = :zus51"),
    @NamedQuery(name = "Ryczpoz.findByZus52", query = "SELECT r FROM Ryczpoz r WHERE r.zus52 = :zus52")})
public class Ryczpoz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dozaplaty", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal dozaplaty;
    @Column(name = "naleznazal", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal naleznazal;
    @Column(name = "nalzalodpoczrok", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal nalzalodpoczrok;
    @Size(max = 255)
    @Column(name = "pkpir_m")
    private String pkpirM;
    @Size(max = 255)
    @Column(name = "pkpir_r")
    private String pkpirR;
    @Column(name = "podatek", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal podatek;
    @Size(max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Column(name = "podstawa")
    private BigDecimal podstawa;
    @Column(name = "przelano")
    private Boolean przelano;
    @Column(name = "przychody", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal przychody;
    @Column(name = "przychodyudzial", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal przychodyudzial;
    @Size(max = 255)
    @Column(name = "terminwplaty")
    private String terminwplaty;
    @Size(max = 255)
    @Column(name = "udzial")
    private String udzial;
    @Size(max = 255)
    @Column(name = "udzialowiec")
    private String udzialowiec;
    @Column(name = "wynik")
    private BigDecimal wynik;
    @Column(name = "zamkniety")
    private Boolean zamkniety;
    @Column(name = "zus51", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal zus51;
    @Column(name = "zus52", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal zus52;
    @Lob
    @Column(name = "listapodatkow")
    private List<RyczaltPodatek> listapodatkow;
    @Column(name = "strata", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal strata;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik1;
    @JoinColumn(name = "podmiot", referencedColumnName = "id")
    @ManyToOne
    private Podmiot podmiot;

    public Ryczpoz() {
    }

    public Ryczpoz(Integer id) {
        this.id = id;
    }
    
    public double getP17() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.17) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP15() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.15) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP14() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.14) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP125() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.125) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP12() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.12) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP10() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.10) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP85() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.085) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP55() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.055) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP30() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.03) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    
    public double getP20() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.02) {
                    zwrot = p.getPrzychod();
                }
            }
        }
        return zwrot;
    }
    public double getP17p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.17) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
    
     
      public double getP15p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.15) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
      
     public double getP14p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.14) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
     
     public double getP125p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.125) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
     public double getP12p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.12) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
     
    public double getP10p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.10) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
    
     
     
    public double getP85p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.085) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
    
    public double getP55p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.055) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
    
    public double getP30p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.03) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }
    
    
    public double getP20p() {
        double zwrot = 0.0;
        if (listapodatkow!=null) {
            for (RyczaltPodatek p : this.listapodatkow) {
                if (p.getStawka()==0.02) {
                    zwrot = p.getPodatek();
                }
            }
        }
        return zwrot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDozaplaty() {
        return dozaplaty;
    }

    public void setDozaplaty(BigDecimal dozaplaty) {
        this.dozaplaty = dozaplaty;
    }

    public BigDecimal getNaleznazal() {
        return naleznazal;
    }

    public void setNaleznazal(BigDecimal naleznazal) {
        this.naleznazal = naleznazal;
    }

    public BigDecimal getNalzalodpoczrok() {
        return nalzalodpoczrok;
    }

    public void setNalzalodpoczrok(BigDecimal nalzalodpoczrok) {
        this.nalzalodpoczrok = nalzalodpoczrok;
    }

    public String getPkpirM() {
        return pkpirM;
    }

    public void setPkpirM(String pkpirM) {
        this.pkpirM = pkpirM;
    }

    public String getPkpirR() {
        return pkpirR;
    }

    public void setPkpirR(String pkpirR) {
        this.pkpirR = pkpirR;
    }

    public BigDecimal getPodatek() {
        return podatek;
    }

    public void setPodatek(BigDecimal podatek) {
        this.podatek = podatek;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public BigDecimal getPodstawa() {
        return podstawa;
    }

    public void setPodstawa(BigDecimal podstawa) {
        this.podstawa = podstawa;
    }

    public Boolean getPrzelano() {
        return przelano;
    }

    public void setPrzelano(Boolean przelano) {
        this.przelano = przelano;
    }

    public BigDecimal getPrzychody() {
        return przychody;
    }

    public void setPrzychody(BigDecimal przychody) {
        this.przychody = przychody;
    }

    public BigDecimal getPrzychodyudzial() {
        return przychodyudzial;
    }

    public void setPrzychodyudzial(BigDecimal przychodyudzial) {
        this.przychodyudzial = przychodyudzial;
    }

    public String getTerminwplaty() {
        return terminwplaty;
    }

    public void setTerminwplaty(String terminwplaty) {
        this.terminwplaty = terminwplaty;
    }

    public String getUdzial() {
        return udzial;
    }

    public void setUdzial(String udzial) {
        this.udzial = udzial;
    }

    public String getUdzialowiec() {
        return udzialowiec;
    }

    public void setUdzialowiec(String udzialowiec) {
        this.udzialowiec = udzialowiec;
    }

    public BigDecimal getWynik() {
        return wynik;
    }

    public void setWynik(BigDecimal wynik) {
        this.wynik = wynik;
    }

    public Boolean getZamkniety() {
        return zamkniety;
    }

    public void setZamkniety(Boolean zamkniety) {
        this.zamkniety = zamkniety;
    }

    public BigDecimal getZus51() {
        return zus51;
    }

    public void setZus51(BigDecimal zus51) {
        this.zus51 = zus51;
    }

    public BigDecimal getZus52() {
        return zus52;
    }

    public void setZus52(BigDecimal zus52) {
        this.zus52 = zus52;
    }

    public List<RyczaltPodatek> getListapodatkow() {
        return listapodatkow;
    }

    public void setListapodatkow(List<RyczaltPodatek> listapodatkow) {
        this.listapodatkow = listapodatkow;
    }

    public BigDecimal getStrata() {
        return strata;
    }

    public void setStrata(BigDecimal strata) {
        this.strata = strata;
    }

    public Podatnik getPodatnik1() {
        return podatnik1;
    }

    public void setPodatnik1(Podatnik podatnik1) {
        this.podatnik1 = podatnik1;
    }

    
    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ryczpoz)) {
            return false;
        }
        Ryczpoz other = (Ryczpoz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ryczpoz[ id=" + id + " ]";
    }

    public void setSumowaniePrzych(double p17, double p15, double p14, double p125, double p12, double p10, double p85, double p55, double p30, double p20,  
            double p17p, double p15p, double p14p, double p125p, double p12p, double p10p, double p85p, double p55p, double p30p, double p20p) {
        List<RyczaltPodatek> l = new ArrayList<>();
        l.add(new RyczaltPodatek(0.17,p17,p17p));
        l.add(new RyczaltPodatek(0.15,p15,p15p));
        l.add(new RyczaltPodatek(0.14,p14,p14p));
        l.add(new RyczaltPodatek(0.125,p125,p125p));
        l.add(new RyczaltPodatek(0.12,p12,p12p));
        l.add(new RyczaltPodatek(0.10,p10,p10p));
        l.add(new RyczaltPodatek(0.085,p85,p85p));
        l.add(new RyczaltPodatek(0.055,p55,p55p));
        l.add(new RyczaltPodatek(0.03,p30,p30p));
        l.add(new RyczaltPodatek(0.02,p20,p20p));
        this.listapodatkow = l;
    }

    
    
}
