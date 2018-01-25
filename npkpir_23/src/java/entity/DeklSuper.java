/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class DeklSuper implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "deklaracja")
    protected String deklaracja;
    @Column(name="miesiackwartal")
    protected boolean miesiackwartal;
    @Size(max = 4)
    @Column(name = "nrkwartalu")
    protected String nrkwartalu;
    @Size(max = 255)
    @Column(name = "identyfikator")
    protected String identyfikator;
    @Size(max = 255)
    @Column(name = "kodurzedu")
    protected String kodurzedu;
    @Size(max = 255)
    @Column(name = "miesiac")
    protected String miesiac;
    @Size(max = 255)
    @Column(name = "rok")
    protected String rok;
    @Column(name = "nrkolejny")
    protected int nrkolejny;
    @Size(max = 255)
    @Column(name = "podatnik")
    protected String podatnik;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "upo")
    protected String upo;
    @Size(max = 255)
    @Column(name = "status")
    protected String status;
    @Size(max = 255)
    @Column(name = "opis")
    protected String opis;
    @Column(name = "datazlozenia")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date datazlozenia;
    @Column(name = "dataupo")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataupo;
    @Size(max = 100)
    @Column(name = "sporzadzil")
    protected String sporzadzil;
    @Column(name="testowa")
    protected boolean testowa;
    @Column(name="jestcertyfikat")
    protected boolean jestcertyfikat;
    @Column(name="deklaracjapodpisana")
    protected byte[] deklaracjapodpisana;
    @Column(name = "wzorschemy")
    protected String wzorschemy;
    @Column(name = "datasporzadzenia")
    @Temporal(TemporalType.DATE)
    protected Date datasporzadzenia;
    

    @Override
    public String toString() {
        return "DeklSuper{" + "identyfikator=" + identyfikator + ", miesiac=" + miesiac + ", rok=" + rok + ", podatnik=" + podatnik + ", status=" + status + '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    
}
