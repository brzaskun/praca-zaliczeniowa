/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Dok;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cechazapisu.findAll", query = "SELECT c FROM Cechazapisu c"),
    @NamedQuery(name = "Cechazapisu.findByPodatnikOnly", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik = :podatnik"),
    @NamedQuery(name = "Cechazapisu.findByPodatnik", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik IS NULL OR c.podatnik = :podatnik"),
    @NamedQuery(name = "Cechazapisu.findByNazwacechy", query = "SELECT c FROM Cechazapisu c WHERE c.cechazapisuPK.nazwacechy = :nazwacechy"),
    @NamedQuery(name = "Cechazapisu.findByRodzajcechy", query = "SELECT c FROM Cechazapisu c WHERE c.cechazapisuPK.rodzajcechy = :rodzajcechy")})
public class Cechazapisu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CechazapisuPK cechazapisuPK  = new CechazapisuPK();
    @ManyToMany(mappedBy = "cechadokumentuLista")
    private List<Dokfk> dokfkLista;
    @ManyToMany(mappedBy = "cechadokumentuLista")
    private List<Dok> dokLista;
    @ManyToMany(mappedBy = "cechazapisuLista")
    private List<StronaWiersza> stronaWierszaLista;
    // 1 statystyczna 2 pzychody 3 koszty
    @Column(name = "charaktercechy")
    private int charaktercechy;
    @Column(name = "przesuniecie")
    private int przesuniecie;
    @JoinColumn(name = "podatnik", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnik;
            
    
    public Cechazapisu() {
        this.cechazapisuPK = new CechazapisuPK();
        this.dokfkLista = new ArrayList<>();
        this.dokLista = new ArrayList<>();
        this.stronaWierszaLista = new ArrayList<>();
        this.charaktercechy = -1;
    }

    public Cechazapisu(CechazapisuPK cechazapisuPK) {
        this.cechazapisuPK = cechazapisuPK;
        this.dokfkLista = new ArrayList<>();
        this.dokLista = new ArrayList<>();
        this.stronaWierszaLista = new ArrayList<>();
        this.charaktercechy = -1;
    }

    public Cechazapisu(String nazwacechy, String rodzajcechy) {
        this.cechazapisuPK = new CechazapisuPK(nazwacechy, rodzajcechy);
        this.dokfkLista = new ArrayList<>();
        this.dokLista = new ArrayList<>();
        this.stronaWierszaLista = new ArrayList<>();
        this.charaktercechy = -1;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public CechazapisuPK getCechazapisuPK() {
        return cechazapisuPK;
    }
    
    public void setCechazapisuPK(CechazapisuPK cechazapisuPK) {
        this.cechazapisuPK = cechazapisuPK;
    }
    
    public String getNazwa() {
        return this.cechazapisuPK.getNazwacechy();
    }
    public List<Dok> getDokLista() {
        return dokLista;
    }

    public void setDokLista(List<Dok> dokLista) {
        this.dokLista = dokLista;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }
    
    public List<Dokfk> getDokfkLista() {
        return dokfkLista;
    }
    
    public void setDokfkLista(List<Dokfk> dokfkLista) {
        this.dokfkLista = dokfkLista;
    }

    public int getCharaktercechy() {
        return charaktercechy;
    }

    public void setCharaktercechy(int charaktercechy) {
        this.charaktercechy = charaktercechy;
    }

    public int getPrzesuniecie() {
        return przesuniecie;
    }

    public void setPrzesuniecie(int przesuniecie) {
        this.przesuniecie = przesuniecie;
    }
    
    public List<StronaWiersza> getStronaWierszaLista() {
        return stronaWierszaLista;
    }
    
    public void setStronaWierszaLista(List<StronaWiersza> stronaWierszaLista) {
        this.stronaWierszaLista = stronaWierszaLista;
    }
    
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cechazapisuPK != null ? cechazapisuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cechazapisu)) {
            return false;
        }
        Cechazapisu other = (Cechazapisu) object;
        if ((this.cechazapisuPK == null && other.cechazapisuPK != null) || (this.cechazapisuPK != null && !this.cechazapisuPK.equals(other.cechazapisuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Cechazapisu[ cechazapisuPK=" + cechazapisuPK + " ]";
    }
    
}
