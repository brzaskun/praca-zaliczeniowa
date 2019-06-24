/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class KlienciSuper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @Size(max = 255)
    @Column(name = "dom")
    protected String dom;
    @Size(max = 255)
    @Column(name = "kodpocztowy")
    protected String kodpocztowy;
    @Size(max = 255)
    @Column(name = "krajkod")
    protected String krajkod;
    @Size(max = 255)
    @Column(name = "krajnazwa")
    protected String krajnazwa;
    @Size(max = 255)
    @Column(name = "lokal")
    protected String lokal;
    @Size(max = 255)
    @Column(name = "miejscowosc")
    protected String miejscowosc;
    @Size(max = 255)
    @Column(name = "npelna", nullable = false)
    protected String npelna;
    @Size(max = 255)
    @Column(name = "ulica")
    protected String ulica;
    

    public KlienciSuper() {
    }

    public KlienciSuper(Integer id) {
        this.id = id;
    }
    
    public KlienciSuper(String opis) {
        this.miejscowosc = opis;
    }


    public KlienciSuper(KlienciSuper klienci) {
        this.id = klienci.id;
        this.dom = klienci.dom;
        this.kodpocztowy = klienci.kodpocztowy;
        this.krajkod = klienci.krajkod;
        this.krajnazwa = klienci.krajnazwa;
        this.lokal = klienci.lokal;
        this.miejscowosc = klienci.miejscowosc;
        this.npelna = klienci.npelna;
        this.ulica = klienci.ulica;
    }

    public KlienciSuper(String npelna, String nskrocona, String nip, String kodpocztowy, String miejscowosc, String ulica, String dom, String lokal) {
        this.npelna = npelna;
        this.kodpocztowy = kodpocztowy;
        this.miejscowosc = miejscowosc;
        this.ulica = ulica;
        this.dom = dom;
        this.lokal = lokal;
    }
    
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }


    public String getKodpocztowy() {
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }


    public String getKrajkod() {
        return krajkod;
    }

    public void setKrajkod(String krajkod) {
        this.krajkod = krajkod;
    }

    public String getKrajnazwa() {
        return krajnazwa;
    }

    public void setKrajnazwa(String krajnazwa) {
        this.krajnazwa = krajnazwa;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getNpelna() {
        return npelna;
    }

    public void setNpelna(String npelna) {
        this.npelna = npelna;
    }


    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    
    public String getAdres() {
        return this.kodpocztowy+" "+this.miejscowosc+", ul. "+this.ulica+" "+this.dom+"/"+this.lokal;
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
        if (!(object instanceof KlienciSuper)) {
            return false;
        }
        KlienciSuper other = (KlienciSuper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String toString2() {
        return "Klienci, miejscowosc=" + miejscowosc + ", npelna=" + npelna + '}';
    }

    public String toString3() {
        return getNpelna()+" "+getMiejscowosc();
    }
    
    @Override
    public String toString() {
        return npelna;
    }
    
    
}
