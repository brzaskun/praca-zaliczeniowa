/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
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
@Table(name = "kontopozycjazapis", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ukladBR, kontoID")
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KontopozycjaZapis.DeleteWynikowe", query = "DELETE FROM KontopozycjaZapis p WHERE p.ukladBR = :uklad AND  p.wynik0bilans1 = false"),
    @NamedQuery(name = "KontopozycjaZapis.DeleteBilansowe", query = "DELETE FROM KontopozycjaZapis p WHERE p.ukladBR = :uklad AND  p.wynik0bilans1 = true"),
    @NamedQuery(name = "KontopozycjaZapis.findAll", query = "SELECT k FROM KontopozycjaZapis k"),
    @NamedQuery(name = "KontopozycjaZapis.findByPozycjaWn", query = "SELECT k FROM KontopozycjaZapis k WHERE k.pozycjaWn = :pozycjaWn"),
    @NamedQuery(name = "KontopozycjaZapis.findByPozycjaMa", query = "SELECT k FROM KontopozycjaZapis k WHERE k.pozycjaMa = :pozycjaMa"),
    @NamedQuery(name = "KontopozycjaZapis.findByPodatnik", query = "SELECT k FROM KontopozycjaZapis k WHERE k.ukladBR = :podatnik"),
    @NamedQuery(name = "KontopozycjaZapis.findByUklad", query = "SELECT k FROM KontopozycjaZapis k WHERE k.ukladBR = :uklad"),
    @NamedQuery(name = "KontopozycjaZapis.findByUkladWynikowe", query = "SELECT k FROM KontopozycjaZapis k WHERE k.ukladBR = :uklad AND  k.wynik0bilans1 = false"),
    @NamedQuery(name = "KontopozycjaZapis.findByUkladBilansowe", query = "SELECT k FROM KontopozycjaZapis k WHERE k.ukladBR = :uklad AND  k.wynik0bilans1 = true"),
    @NamedQuery(name = "KontopozycjaZapis.findByKontoId", query = "SELECT k FROM KontopozycjaZapis k WHERE k.kontoID = :kontoId AND k.ukladBR = :ukladBR"),
    @NamedQuery(name = "KontopozycjaZapis.findByRok", query = "SELECT k FROM KontopozycjaZapis k WHERE k.ukladBR.rok = :rok")})
public class KontopozycjaZapis extends KontopozycjaSuper implements Serializable {
    private static final long serialVersionUID = 1L;

    public KontopozycjaZapis() {
    }

    
    public KontopozycjaZapis(KontopozycjaBiezaca p) {
        this.idKP = null;
        this.kontoID = p.kontoID;
        this.pozycjaMa = p.pozycjaMa;
        this.pozycjaWn = p.pozycjaWn;
        this.stronaMa = p.stronaMa;
        this.stronaWn = p.stronaWn;
        this.syntetykaanalityka = p.syntetykaanalityka;
        this.ukladBR = p.ukladBR;
        this.wynik0bilans1 = p.wynik0bilans1;
    }
    
    public KontopozycjaZapis(KontopozycjaZapis p, Konto konto, UkladBR ukladBR) {
        this.kontoID = konto;
        this.ukladBR = ukladBR;
        this.pozycjaMa = p.pozycjaMa;
        this.pozycjaWn = p.pozycjaWn;
        this.stronaMa = p.stronaMa;
        this.stronaWn = p.stronaWn;
        this.syntetykaanalityka = p.syntetykaanalityka;
        this.wynik0bilans1 = p.wynik0bilans1;
    }

       

    public Integer getIdKP() {
        return idKP;
    }

    public void setIdKP(Integer idKP) {
        this.idKP = idKP;
    }

    public String getPozycjaWn() {
        return pozycjaWn;
    }

    public void setPozycjaWn(String pozycjaWn) {
        this.pozycjaWn = pozycjaWn;
    }

    public String getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(String stronaWn) {
        this.stronaWn = stronaWn;
    }

    public String getPozycjaMa() {
        return pozycjaMa;
    }

    public void setPozycjaMa(String pozycjaMa) {
        this.pozycjaMa = pozycjaMa;
    }

    public String getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(String stronaMa) {
        this.stronaMa = stronaMa;
    }

    public String getSyntetykaanalityka() {
        return syntetykaanalityka;
    }

    public void setSyntetykaanalityka(String syntetykaanalityka) {
        this.syntetykaanalityka = syntetykaanalityka;
    }

    public UkladBR getUkladBR() {
        return ukladBR;
    }

    public void setUkladBR(UkladBR ukladBR) {
        this.ukladBR = ukladBR;
    }

    public Konto getKontoID() {
        return kontoID;
    }

    public void setKontoID(Konto kontoID) {
        this.kontoID = kontoID;
    }

    public boolean isWynik0bilans1() {
        return wynik0bilans1;
    }

    public void setWynik0bilans1(boolean wynik0bilans1) {
        this.wynik0bilans1 = wynik0bilans1;
    }

    
    
    

    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.ukladBR);
        hash = 41 * hash + Objects.hashCode(this.kontoID);
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
        final KontopozycjaZapis other = (KontopozycjaZapis) obj;
        if (!Objects.equals(this.ukladBR, other.ukladBR)) {
            return false;
        }
        if (!Objects.equals(this.kontoID, other.kontoID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KontopozycjaZapis{" + ", konto=" + kontoID + "pozycjaWn=" + pozycjaWn + ", pozycjaMa=" + pozycjaMa + ", pozycjonowane=" + syntetykaanalityka + ", ukladBR=" + ukladBR + '}';
    }
    
    
    
    

   
    
}
