/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Osito
 */
@Entity
@Named
public class Rozrachunek1 implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Column(name = "dataplatnosci")
    private String dataplatnosci;
    @Column(name = "kwotawplacona")
    private Double kwotawplacona;
    @Column(name = "dorozliczenia")
    private Double dorozliczenia;
    @Column(name = "ujetowstorno")
    private boolean ujetowstorno;
    @Column(name = "wprowadzil")
    private String wprowadzil;
    @Column(name = "datawprowadzenia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawprowadzenia;
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @ManyToOne(cascade = CascadeType.ALL)
    private Dok dok;

    public Rozrachunek1() {
    }

    
    public Rozrachunek1(String dataplatnosci, Double kwotawplacona, Double dorozliczenia, String wprowadzil, Date datawprowadzenia) {
        this.dataplatnosci = dataplatnosci;
        this.kwotawplacona = kwotawplacona;
        this.dorozliczenia = dorozliczenia;
        this.ujetowstorno = false;
        this.wprowadzil = wprowadzil;
        this.datawprowadzenia = datawprowadzenia;
    }

    
    public String getDataplatnosci() {
        return dataplatnosci;
    }

    public void setDataplatnosci(String dataplatnosci) {
        this.dataplatnosci = dataplatnosci;
    }

    public Double getKwotawplacona() {
        return kwotawplacona;
    }

    public void setKwotawplacona(Double kwotawplacona) {
        this.kwotawplacona = kwotawplacona;
    }

    public Double getDorozliczenia() {
        return dorozliczenia;
    }

    public void setDorozliczenia(Double dorozliczenia) {
        this.dorozliczenia = dorozliczenia;
    }

    public boolean isUjetowstorno() {
        return ujetowstorno;
    }

    public void setUjetowstorno(boolean ujetowstorno) {
        this.ujetowstorno = ujetowstorno;
    }

    public String getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public Date getDatawprowadzenia() {
        return datawprowadzenia;
    }

    public void setDatawprowadzenia(Date datawprowadzenia) {
        this.datawprowadzenia = datawprowadzenia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }
   
    
    
}