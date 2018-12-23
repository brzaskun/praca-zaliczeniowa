/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import daoFK.UkladBRDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ukladBR",  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uklad", "podatnik", "rok"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UkladBR.findAll", query = "SELECT r FROM UkladBR r"),
    @NamedQuery(name = "UkladBR.findByUklad", query = "SELECT r FROM UkladBR r WHERE r.uklad = :uklad"),
    @NamedQuery(name = "UkladBR.findByPodatnik", query = "SELECT r FROM UkladBR r WHERE r.podatnik = :podatnik ORDER BY r.lp DESC"),
    @NamedQuery(name = "UkladBR.ustawNieaktywne", query = "UPDATE UkladBR k SET k.aktualny = '0' WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "UkladBR.findByPodatnikRok", query = "SELECT r FROM UkladBR r WHERE r.podatnik = :podatnik AND r.rok = :rok ORDER BY r.lp DESC"),
    @NamedQuery(name = "UkladBR.findByRokNieWzor", query = "SELECT r FROM UkladBR r WHERE r.podatnik.nazwapelna!='Wzorcowy' AND r.podatnik != :podatnik AND r.rok = :rok ORDER BY r.podatnik.printnazwa DESC"),
    @NamedQuery(name = "UkladBR.ukladBRustawnieaktywne", query = "UPDATE UkladBR k SET k.aktualny = '0' WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "UkladBR.findByPodatnikRokPodstawowy", query = "SELECT r FROM UkladBR r WHERE r.podatnik = :podatnik AND r.rok = :rok AND r.uklad = 'Podstawowy' ORDER BY r.lp DESC"),
    @NamedQuery(name = "UkladBR.findByPodatnikRokAktywny", query = "SELECT r FROM UkladBR r WHERE r.podatnik = :podatnik AND r.rok = :rok AND r.aktualny = '1'"),
    @NamedQuery(name = "UkladBR.findByRok", query = "SELECT r FROM UkladBR r WHERE r.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByUkladPodRok", query = "SELECT r FROM UkladBR r WHERE r.uklad = :uklad AND r.podatnik = :podatnik AND r.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByWzorcowyRok", query = "SELECT r FROM UkladBR r WHERE r.podatnik.nazwapelna = 'Wzorcowy' AND r.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByBlokada", query = "SELECT r FROM UkladBR r WHERE r.blokada = :blokada")})
public class UkladBR implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lp;
    @JoinColumn(name = "podatnik", referencedColumnName = "NAZWAPELNA")
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String uklad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, name = "blokada")
    private boolean blokada;
    @Column(name = "importowany")
    private boolean importowany;
    @Column(name = "aktualny")
    private boolean aktualny;
    @Column(name = "zwykly0wzrocowy1")
    private boolean zwykly0wzrocowy1;

    public UkladBR() {
    }

    public UkladBR(String uklad, Podatnik podatnik, String rok, boolean blokada) {
        this.uklad = uklad;
        this.podatnik = podatnik;
        this.rok = rok;
        this.blokada = blokada;
    }

    public UkladBR(UkladBR ukladBR) {
        this.uklad = ukladBR.getUklad();
        this.podatnik = ukladBR.getPodatnik();
        this.rok = ukladBR.getRok();
        this.blokada = ukladBR.getBlokada();
        this.importowany = ukladBR.isImportowany();
    }
  
    public void oznaczUkladBR(UkladBRDAO ukladBRDAO) {
        List<UkladBR> pobraneUkladyPodatnika = ukladBRDAO.findPodatnik(podatnik);
        if (pobraneUkladyPodatnika != null) {
            for (UkladBR p : pobraneUkladyPodatnika) {
                if (p.equals(this)) {
                    p.setAktualny(true);
                    this.setAktualny(true);
                } else {
                    p.setAktualny(false);
                }
            }
            ukladBRDAO.editList(pobraneUkladyPodatnika);
        }
    }
   
    public boolean getBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

   
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getUklad() {
        return uklad;
    }

    public void setUklad(String uklad) {
        this.uklad = uklad;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }
    
    public Integer getRokInt() {
        return Integer.parseInt(rok);
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isImportowany() {
        return importowany;
    }

    public void setImportowany(boolean importowany) {
        this.importowany = importowany;
    }

    public boolean isAktualny() {
        return aktualny;
    }

    public void setAktualny(boolean aktualny) {
        this.aktualny = aktualny;
    }

    public boolean isZwykly0wzrocowy1() {
        return zwykly0wzrocowy1;
    }

    public void setZwykly0wzrocowy1(boolean zwykly0wzrocowy1) {
        this.zwykly0wzrocowy1 = zwykly0wzrocowy1;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.uklad);
        hash = 61 * hash + Objects.hashCode(this.podatnik);
        hash = 61 * hash + Objects.hashCode(this.rok);
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
        final UkladBR other = (UkladBR) obj;
        if (!Objects.equals(this.uklad, other.uklad)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UkladBR{" + "lp=" + lp + ", podatnik=" + podatnik + ", uklad=" + uklad + ", rok=" + rok + ", blokada=" + blokada + ", importowany=" + importowany + '}';
    }

    
   
       
    
}
