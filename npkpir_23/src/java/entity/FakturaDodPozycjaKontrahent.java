/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "FakturaDodPozycjaKontrahent",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"kontrahent, ilosc, rok, mc"})
})
@NamedQueries({
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findAll", query = "SELECT d FROM FakturaDodPozycjaKontrahent d"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findByKontrahent", query = "SELECT d FROM FakturaDodPozycjaKontrahent d WHERE d.kontrahent = :kontrahent"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findById", query = "SELECT d FROM FakturaDodPozycjaKontrahent d WHERE d.id = :id"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findByKontrahentRokMc", query = "SELECT d FROM FakturaDodPozycjaKontrahent d WHERE d.kontrahent = :kontrahent AND d.rok = :rok AND d.mc = :mc"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findByRok", query = "SELECT d FROM FakturaDodPozycjaKontrahent d WHERE d.rok = :rok"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.deleteById", query = "DELETE FROM FakturaDodPozycjaKontrahent s WHERE s.id = :id"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.aktualizuj", query = "UPDATE FakturaDodPozycjaKontrahent r SET r.rozliczone = '0' WHERE r.id = :id")
    })
public class FakturaDodPozycjaKontrahent  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontrahent;
    @JoinColumn(name = "fakturaDodatkowaPozycja", referencedColumnName = "id")
    @ManyToOne
    private FakturaDodatkowaPozycja fakturaDodatkowaPozycja;
    @Column(name = "ilosc")
    private int ilosc;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "rozliczone")
    private boolean rozliczone;
    @Column(name = "permanentny")
    private boolean permanentny;
    @Column(name = "kwotaindywid")
    private double kwotaindywid;

    public FakturaDodPozycjaKontrahent() {
    }
        

    public FakturaDodPozycjaKontrahent(FakturaDodPozycjaKontrahent p, String rok, String mc) {
        this.kontrahent = p.getKontrahent();
        this.fakturaDodatkowaPozycja = p.getFakturaDodatkowaPozycja();
        this.ilosc = 0;
        this.rok = rok;
        this.mc = mc;
        this.rozliczone = false;
        this.permanentny = p.isPermanentny();
        this.kwotaindywid = Z.z(p.getKwotaindywid());
    }

  
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.kontrahent);
        hash = 47 * hash + Objects.hashCode(this.fakturaDodatkowaPozycja);
        hash = 47 * hash + Objects.hashCode(this.rok);
        hash = 47 * hash + Objects.hashCode(this.mc);
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
        final FakturaDodPozycjaKontrahent other = (FakturaDodPozycjaKontrahent) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        if (!Objects.equals(this.fakturaDodatkowaPozycja, other.fakturaDodatkowaPozycja)) {
            return false;
        }
        return true;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public FakturaDodatkowaPozycja getFakturaDodatkowaPozycja() {
        return fakturaDodatkowaPozycja;
    }

    public void setFakturaDodatkowaPozycja(FakturaDodatkowaPozycja fakturaDodatkowaPozycja) {
        this.fakturaDodatkowaPozycja = fakturaDodatkowaPozycja;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public boolean isRozliczone() {
        return rozliczone;
    }

    public void setRozliczone(boolean rozliczone) {
        this.rozliczone = rozliczone;
    }

    public boolean isPermanentny() {
        return permanentny;
    }

    public void setPermanentny(boolean permanentny) {
        this.permanentny = permanentny;
    }

    public double getKwotaindywid() {
        return kwotaindywid;
    }

    public void setKwotaindywid(double kwotaindywid) {
        this.kwotaindywid = kwotaindywid;
    }

 
    

    @Override
    public String toString() {
        return "FakturaDodPozycjaKlient{" + ", kontrahent=" + kontrahent.getNpelna() + ", fakturaDodatkowaPozycja=" + fakturaDodatkowaPozycja.getNazwa() + ", ilosc=" + ilosc + ", rok=" + rok + ", mc=" + mc + ", rozliczone=" + rozliczone + '}';
    }
    
    
    
}
