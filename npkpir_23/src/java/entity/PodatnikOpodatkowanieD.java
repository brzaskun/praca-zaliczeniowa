/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatnikopodatkowanied")
@NamedQueries({
    @NamedQuery(name = "PodatnikOpodatkowanieD.findBypodatnik", query = "SELECT k FROM PodatnikOpodatkowanieD k WHERE k.podatnikObj = :podatnik"),
    @NamedQuery(name = "PodatnikOpodatkowanieD.findBypodatnikRok", query = "SELECT k FROM PodatnikOpodatkowanieD k WHERE k.podatnikObj = :podatnik AND k.rokOd = :rok AND k.rokDo = :rok")
})
public class PodatnikOpodatkowanieD implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "mcOd")
    private String mcOd;
    @Column(name = "rokOd")
    private String rokOd;
    @Column(name = "mcDo")
    private String mcDo;
    @Column(name = "rokDo")
    private String rokDo;
    @Column(name = "formaopodatkowania")
    private String formaopodatkowania;
    @Column(name = "mc0kw1")
    private boolean mc0kw1;
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnikObj;
    @Column(name="stawkapodatkuospr")
    private double stawkapodatkuospr;
    @Column(name = "dolaczonydoroku")
    private String dolaczonydoroku;
    @Column(name = "zamkniety")
    private boolean zamkniety;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datawprowadzenia")
    private Date datawprowadzenia;
    @JoinColumn(name = "ksiegowa", referencedColumnName = "login")
    @ManyToOne
    private Uz ksiegowa;
 private int podid;

    public int getPodid() {
        return podid;
    }

    public void setPodid(int podid) {
        this.podid = podid;
    }
    public PodatnikOpodatkowanieD() {
        
    }
    
    public PodatnikOpodatkowanieD(Parametr p, Podatnik podatnikObj) {
        this.rokOd = p.getRokOd();
        this.rokDo = p.getRokDo();
        this.mcOd = p.getMcOd();
        this.mcDo = p.getMcDo();
        this.formaopodatkowania = p.getParametr();
        this.mc0kw1 = false;
        this.podatnikObj = podatnikObj;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.mcOd);
        hash = 37 * hash + Objects.hashCode(this.rokOd);
        hash = 37 * hash + Objects.hashCode(this.mcDo);
        hash = 37 * hash + Objects.hashCode(this.rokDo);
        hash = 37 * hash + Objects.hashCode(this.podatnikObj);
        return hash;
    }
    
    
   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PodatnikOpodatkowanieD other = (PodatnikOpodatkowanieD) obj;
        if (!Objects.equals(this.mcOd, other.mcOd)) {
            return false;
        }
        if (!Objects.equals(this.rokOd, other.rokOd)) {
            return false;
        }
        if (!Objects.equals(this.mcDo, other.mcDo)) {
            return false;
        }
        if (!Objects.equals(this.rokDo, other.rokDo)) {
            return false;
        }
        if (!Objects.equals(this.formaopodatkowania, other.formaopodatkowania)) {
            return false;
        }
        if (!Objects.equals(this.mc0kw1, other.mc0kw1)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PodatnikOpodatkowanieD{" + "id=" + id + ", mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", formaopodatkowania=" + formaopodatkowania + ", mc0kw1=" + mc0kw1 + ", podatnikObj=" + podatnikObj + '}';
    }

  
    
    
    public String getMcOd() {
        return mcOd;
    }

    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }

    public String getRokOd() {
        return rokOd;
    }

    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }

    public String getMcDo() {
        return mcDo;
    }

    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }

    public String getRokDo() {
        return rokDo;
    }

    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }

    public String getFormaopodatkowania() {
        return formaopodatkowania;
    }

    public void setFormaopodatkowania(String formaopodatkowania) {
        this.formaopodatkowania = formaopodatkowania;
    }

    public boolean isMc0kw1() {
        return mc0kw1;
    }

    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }

    public double getStawkapodatkuospr() {
        return stawkapodatkuospr;
    }

    public void setStawkapodatkuospr(double stawkapodatkuospr) {
        this.stawkapodatkuospr = stawkapodatkuospr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public String getDolaczonydoroku() {
        return dolaczonydoroku;
    }

    public void setDolaczonydoroku(String dolaczonydoroku) {
        this.dolaczonydoroku = dolaczonydoroku;
    }

    public boolean isZamkniety() {
        return zamkniety;
    }

    public void setZamkniety(boolean zamkniety) {
        this.zamkniety = zamkniety;
    }

    public Date getDatawprowadzenia() {
        return datawprowadzenia;
    }

    public void setDatawprowadzenia(Date datawprowadzenia) {
        this.datawprowadzenia = datawprowadzenia;
    }

    public Uz getKsiegowa() {
        return ksiegowa;
    }

    public void setKsiegowa(Uz ksiegowa) {
        this.ksiegowa = ksiegowa;
    }

   
    
}
