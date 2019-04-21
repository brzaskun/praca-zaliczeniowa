/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "SrodekTrw_NowaWartosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SrodekTrw_NowaWartosc.findAll", query = "SELECT s FROM SrodekTrw s")
})

public class SrodekTrw_NowaWartosc implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "srodekTrw", referencedColumnName = "id")
    private SrodekTrw srodekTrw;
    @Column(name = "datazmiany")
    private String datazmiany;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotapierwotna")
    private double kwotapierwotna;
    @Column(name = "mc")
    private String mc;
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Column(name = "data_k", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataksiegowania;

    public SrodekTrw_NowaWartosc() {
    }

    
    public SrodekTrw_NowaWartosc(SrodekTrw wybranysrodektrwalyPosiadane, WpisView wpisView, String datazmiany, double kwotazmiany) {
        this.dataksiegowania = new Date();
        this.datazmiany = datazmiany;
        this.kwota = kwotazmiany;
        this.kwotapierwotna = wybranysrodektrwalyPosiadane.getNetto();
        this.mc = datazmiany.split("-")[1];
        this.rok = datazmiany.split("-")[0];
        this.srodekTrw = wybranysrodektrwalyPosiadane;
        this.wprowadzil = wpisView.getUzer();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SrodekTrw getSrodekTrw() {
        return srodekTrw;
    }

    public void setSrodekTrw(SrodekTrw srodekTrw) {
        this.srodekTrw = srodekTrw;
    }

    public String getDatazmiany() {
        return datazmiany;
    }

    public void setDatazmiany(String datazmiany) {
        this.datazmiany = datazmiany;
    }

    public double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

  
    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public Date getDataksiegowania() {
        return dataksiegowania;
    }

    public void setDataksiegowania(Date dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
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
        if (!(object instanceof SrodekTrw_NowaWartosc)) {
            return false;
        }
        SrodekTrw_NowaWartosc other = (SrodekTrw_NowaWartosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SrodekTrw_NowaWartosc{" + "srodekTrw=" + srodekTrw + ", datazmiany=" + datazmiany + ", kwota=" + kwota + ", kwotapierwotna=" + kwotapierwotna + ", mc=" + mc + ", rok=" + rok + ", wprowadzil=" + wprowadzil + ", dataksiegowania=" + dataksiegowania + '}';
    }

    
    
}

