/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnik", "rok", "mc","imienazwisko"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WierszDRA.findAll", query = "SELECT f FROM WierszDRA f"),
    @NamedQuery(name = "WierszDRA.findByRok", query = "SELECT f FROM WierszDRA f WHERE f.rok = :rok"),
    @NamedQuery(name = "WierszDRA.findByRokMc", query = "SELECT f FROM WierszDRA f WHERE f.rok = :rok AND f.mc = :mc")
})
public class WierszDRA  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column (name = "rok")
    private String rok;
    @Column (name = "mc")
    private String mc;
    @Column (name = "mcnazwa")
    private String mcnazwa;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Column (name = "imienazwisko")
    private String imienazwisko;
    @Column (name = "opodatkowanie")
    private String opodatkowanie;
    @Column (name = "udzial")
    private double udzial;
    @Column (name = "wynikpodatkowymc")
    private double wynikpodatkowymc;
    @Column (name = "wynikpodatkowynar")
    private double wynikpodatkowynar;
    @Column (name = "dochodzus")
    private double dochodzus;
    @Column (name = "dochodzusnar")
    private double dochodzusnar;
    @Column (name = "skladki")
    private double skladki;
    @Column (name = "skladkinar")
    private double skladkinar;
    @Column (name = "dochodzusnetto")
    private double dochodzusnetto;
    @Column (name = "dochodzusnettonar")
    private double dochodzusnettonar;
    @Column (name = "przychod")
    private double przychod;
    @Column (name = "przychodnar")
    private double przychodnar;
    @Column (name = "brakdokumentow")
    private boolean brakdokumentow;
    @Column (name = "jestpit")
    private boolean jestpit;
    @Column (name = "zrobiony")
    private boolean zrobiony;
    @JoinColumn(name = "zusmail", referencedColumnName = "id", updatable = true)
    @ManyToOne(cascade = CascadeType.ALL)
    private Zusmail zusmail;
    @Column(name = "data", insertable=true, updatable=true, columnDefinition="DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date data;
    @Transient
    private boolean blad;
    @Transient
    private Zusdra zusdra;
    @Transient
    private Zusrca zusrca;
    @Transient
    private UbezpZusrca ubezpZusrca;
    @Transient
    private double przychoddra;
    @Transient
    private double przychoddraru;
    @Transient
    private double pit4;

    public WierszDRA() {
    }
    
    
    public WierszDRA(Podatnik p, String rok, String mc, String get) {
        this.podatnik = p;
        this.rok = rok;
        this.mc = mc;
        this.mcnazwa = get;
    }
    
    public double getPrzychodDochod() {
        double zwrot = 0.0;
        if (this.zusdra!=null) {
            if (this.zusrca!=null && this.ubezpZusrca!=null) {
                if (this.opodatkowanie.equals("ryczałt")) {
                    zwrot = this.ubezpZusrca.getIiiSumaprzychodowbrk()!=null?this.ubezpZusrca.getIiiSumaprzychodowbrk().doubleValue():0.0;
                    this.przychoddraru = this.ubezpZusrca.getIiiKwotaprzychodowurk()!=null?this.ubezpZusrca.getIiiKwotaprzychodowurk().doubleValue():0.0;
                } else {
                    if (this.ubezpZusrca.getIiiKwotadochodumpLiniowy()!=null) {
                        zwrot = this.ubezpZusrca.getIiiKwotadochodumpLiniowy().doubleValue();
                    } else if (this.ubezpZusrca.getIiiKwotadochodumpSkala()!=null) {
                        zwrot = this.ubezpZusrca.getIiiKwotadochodumpSkala().doubleValue();
                    }
                }
            } else {
                if (this.opodatkowanie.equals("ryczałt")) {
                    zwrot = this.zusdra.getXiSumaprzychodowbrk()!=null?this.zusdra.getXiSumaprzychodowbrk().doubleValue():0.0;
                    this.przychoddraru = this.zusdra.getXiKwotaprzychodowurk()!=null?this.zusdra.getXiKwotaprzychodowurk().doubleValue():0.0;
                } else {
                    if (this.zusdra.getXiKwotadochodumpLiniowy()!=null) {
                        zwrot = this.zusdra.getXiKwotadochodumpLiniowy().doubleValue();
                    } else if (this.zusdra.getXiKwotadochodumpSkala()!=null) {
                        zwrot = this.zusdra.getXiKwotadochodumpSkala().doubleValue();
                    }
                }
            }
        }
        this.przychoddra = zwrot;
        return zwrot;
    }
    
    public double getZdrowotna() {
        double zwrot = 0.0;
        if (this.zusdra!=null) {
            if (this.zusrca!=null && this.ubezpZusrca!=null) {
                if (this.opodatkowanie.equals("ryczałt")) {
                    zwrot = this.ubezpZusrca.getIiiKwotaskladkiRyczalt()!=null?this.ubezpZusrca.getIiiKwotaskladkiRyczalt().doubleValue():0.0;
                } else {
                    if (this.ubezpZusrca.getIiiKwotaskladkiLiniowy()!=null) {
                        zwrot = this.ubezpZusrca.getIiiKwotaskladkiLiniowy().doubleValue();
                    } else if (this.ubezpZusrca.getIiiKwotaskladkiSkala()!=null) {
                        zwrot = this.ubezpZusrca.getIiiKwotaskladkiSkala().doubleValue();
                    }
                }
            } else {
                if (this.opodatkowanie.equals("ryczałt")) {
                    zwrot = this.zusdra.getXiKwotaskladkiRyczalt()!=null?this.zusdra.getXiKwotaskladkiRyczalt().doubleValue():0.0;
                } else {
                    if (this.zusdra.getXiKwotaskladkiLiniowy()!=null) {
                        zwrot = this.zusdra.getXiKwotaskladkiLiniowy().doubleValue();
                    } else if (this.zusdra.getXiKwotaskladkiSkala()!=null) {
                        zwrot = this.zusdra.getXiKwotaskladkiSkala().doubleValue();
                    }
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


    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMcnazwa() {
        return mcnazwa;
    }

    public void setMcnazwa(String mcnazwa) {
        this.mcnazwa = mcnazwa;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    public boolean isBrakdokumentow() {
        return brakdokumentow;
    }

    public void setBrakdokumentow(boolean brakdokumentow) {
        this.brakdokumentow = brakdokumentow;
    }

    public double getWynikpodatkowynar() {
        return wynikpodatkowynar;
    }

    public void setWynikpodatkowynar(double wynikpodatkowynar) {
        this.wynikpodatkowynar = wynikpodatkowynar;
    }

    public double getDochodzus() {
        return dochodzus;
    }

    public void setDochodzus(double dochodzus) {
        this.dochodzus = dochodzus;
    }

    public double getSkladki() {
        return skladki;
    }

    public void setSkladki(double skladki) {
        this.skladki = skladki;
    }

    public double getDochodzusnetto() {
        return dochodzusnetto;
    }

    public void setDochodzusnetto(double dochodzusnetto) {
        this.dochodzusnetto = dochodzusnetto;
    }

    public double getDochodzusnar() {
        return dochodzusnar;
    }

    public void setDochodzusnar(double dochodzusnar) {
        this.dochodzusnar = dochodzusnar;
    }

    public double getSkladkinar() {
        return skladkinar;
    }

    public void setSkladkinar(double skladkinar) {
        this.skladkinar = skladkinar;
    }

    public double getDochodzusnettonar() {
        return dochodzusnettonar;
    }

    public void setDochodzusnettonar(double dochodzusnettonar) {
        this.dochodzusnettonar = dochodzusnettonar;
    }

   
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    public double getUdzial() {
        return udzial;
    }

    public void setUdzial(double udzial) {
        this.udzial = udzial;
    }

    public double getWynikpodatkowymc() {
        return wynikpodatkowymc;
    }

    public void setWynikpodatkowymc(double wynikpodatkowymc) {
        this.wynikpodatkowymc = wynikpodatkowymc;
    }

    public double getPrzychod() {
        return przychod;
    }

    public void setPrzychod(double przychod) {
        this.przychod = przychod;
    }

    public boolean isJestpit() {
        return jestpit;
    }
    

    public void setJestpit(boolean jestpit) {
        this.jestpit = jestpit;
    }

    public double getPrzychodnar() {
        return przychodnar;
    }

    public void setPrzychodnar(double przychodnar) {
        this.przychodnar = przychodnar;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isZrobiony() {
        return zrobiony;
    }

    public void setZrobiony(boolean zrobiony) {
        this.zrobiony = zrobiony;
    }

    public Zusdra getZusdra() {
        return zusdra;
    }

    public void setZusdra(Zusdra zusdra) {
        this.zusdra = zusdra;
    }

    public Zusrca getZusrca() {
        return zusrca;
    }

    public void setZusrca(Zusrca zusrca) {
        this.zusrca = zusrca;
    }

    public UbezpZusrca getUbezpZusrca() {
        return ubezpZusrca;
    }

    public void setUbezpZusrca(UbezpZusrca ubezpZusrca) {
        this.ubezpZusrca = ubezpZusrca;
    }

    public boolean isBlad() {
        return blad;
    }

    public void setBlad(boolean blad) {
        this.blad = blad;
    }

    public double getPrzychoddra() {
        return przychoddra;
    }

    public void setPrzychoddra(double przychoddra) {
        this.przychoddra = przychoddra;
    }

    public double getPrzychoddraru() {
        return przychoddraru;
    }

    public void setPrzychoddraru(double przychoddraru) {
        this.przychoddraru = przychoddraru;
    }

    public Zusmail getZusmail() {
        return zusmail;
    }

    public void setZusmail(Zusmail zusmail) {
        this.zusmail = zusmail;
    }

    public double getPit4() {
        return pit4;
    }

    public void setPit4(double pit4) {
        this.pit4 = pit4;
    }
    
    
    
    
    @Override
    public String toString() {
        return "WierszDRA{" + "rok=" + rok + ", mc=" + mc + ", mcnazwa=" + mcnazwa + ", podatnik=" + podatnik.getPrintnazwa() + ", imienazwisko=" + imienazwisko + ", opodatkowanie=" + opodatkowanie + ", udzial=" + udzial + ", dochod=" + wynikpodatkowymc + ", przychod=" + przychod + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.rok);
        hash = 97 * hash + Objects.hashCode(this.mc);
        hash = 97 * hash + Objects.hashCode(this.podatnik);
        hash = 97 * hash + Objects.hashCode(this.imienazwisko);
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
        final WierszDRA other = (WierszDRA) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.imienazwisko, other.imienazwisko)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

  

    
    
    
    
}

