/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
    @NamedQuery(name = "WierszDRA.findByRokPodatnik", query = "SELECT f FROM WierszDRA f WHERE f.rok = :rok AND f.podatnikudzial.podatnikObj = :podatnik"),
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
    @JoinColumn(name = "podatnikudzial", referencedColumnName = "id")
    @ManyToOne
    private PodatnikUdzialy podatnikudzial;
    @JoinColumn(name = "drasumy", referencedColumnName = "id")
    @ManyToOne
    private DraSumy draSumy;
    @Column (name = "opodatkowanie")
    private String opodatkowanie;
    @Column (name = "wynikpodatkowymc")
    private double wynikpodatkowymc;
    @Column (name = "wynikpodatkowynar")
    private double wynikpodatkowynar;
    @Column (name = "wynikpodatkowymcinne")
    private double wynikpodatkowymcInne;
    @Column (name = "wynikpodatkowynarinne")
    private double wynikpodatkowynarInne;
    @Column (name = "dochodzus")
    private double dochodzus;
    @Column (name = "dochodzusnar")
    private double dochodzusnar;
//<editor-fold defaultstate="collapsed" desc="comment">
//    @Column (name = "dochodzusnetto")
//    private double dochodzusnetto;
//    @Column (name = "dochodzusnettonar")
//    private double dochodzusnettonar;
//    @Column (name = "przychod")
//    private double przychod;
//    @Column (name = "przychodnar")
//    private double przychodnar;
//</editor-fold>
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

    public WierszDRA() {
    }
    
    
    public WierszDRA(DraSumy p, String rok, String mc, String get) {
        this.draSumy = p;
        this.rok = rok;
        this.mc = mc;
    }

    public WierszDRA(PodatnikUdzialy podatnikUdzialy, String rok, String mc) {
        this.podatnikudzial = podatnikUdzialy;
        this.rok = rok;
        this.mc = mc;
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


    public double getDochodzusnar() {
        return dochodzusnar;
    }

    public void setDochodzusnar(double dochodzusnar) {
        this.dochodzusnar = dochodzusnar;
    }

    public double getWynikpodatkowymcInne() {
        return wynikpodatkowymcInne;
    }

    public void setWynikpodatkowymcInne(double wynikpodatkowymcInne) {
        this.wynikpodatkowymcInne = wynikpodatkowymcInne;
    }

    public double getWynikpodatkowynarInne() {
        return wynikpodatkowynarInne;
    }

    public void setWynikpodatkowynarInne(double wynikpodatkowynarInne) {
        this.wynikpodatkowynarInne = wynikpodatkowynarInne;
    }

   
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }


    public double getWynikpodatkowymc() {
        return wynikpodatkowymc;
    }

    public void setWynikpodatkowymc(double wynikpodatkowymc) {
        this.wynikpodatkowymc = wynikpodatkowymc;
    }

    public boolean isJestpit() {
        return jestpit;
    }
    

    public void setJestpit(boolean jestpit) {
        this.jestpit = jestpit;
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

    public boolean isBlad() {
        return blad;
    }

    public void setBlad(boolean blad) {
        this.blad = blad;
    }

    public Zusmail getZusmail() {
        return zusmail;
    }

    public void setZusmail(Zusmail zusmail) {
        this.zusmail = zusmail;
    }


    public PodatnikUdzialy getPodatnikudzial() {
        return podatnikudzial;
    }

    public void setPodatnikudzial(PodatnikUdzialy podatnikudzial) {
        this.podatnikudzial = podatnikudzial;
    }

    public DraSumy getDraSumy() {
        return draSumy;
    }

    public void setDraSumy(DraSumy draSumy) {
        this.draSumy = draSumy;
    }

    public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    
    
    @Override
    public String toString() {
        return "WierszDRA{" + "rok=" + rok + ", mc=" + mc + ", podatnik=" + draSumy.getPodatnik().getPrintnazwa() + ", dochod=" + wynikpodatkowymc + ", przychod=" + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.rok);
        hash = 97 * hash + Objects.hashCode(this.mc);
        hash = 97 * hash + Objects.hashCode(this.draSumy);
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
        if (!Objects.equals(this.draSumy, other.draSumy)) {
            return false;
        }
        return true;
    }

  

    
    
    
    
}

