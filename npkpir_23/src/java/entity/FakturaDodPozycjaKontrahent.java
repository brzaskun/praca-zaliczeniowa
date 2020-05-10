/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "FakturaDodPozycjaKontrahent",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"nazwa"})
})
@NamedQueries({
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findAll", query = "SELECT d FROM FakturaDodPozycjaKontrahent d"),
    @NamedQuery(name = "FakturaDodPozycjaKontrahent.findByKontrahent", query = "SELECT d FROM FakturaDodPozycjaKontrahent d WHERE d.kontrahent = :kontrahent"),
    })
public class FakturaDodPozycjaKontrahent  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nazwa")
    private String nazwa;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.nazwa);
        hash = 29 * hash + Objects.hashCode(this.kontrahent);
        hash = 29 * hash + Objects.hashCode(this.rok);
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
        if (!Objects.equals(this.nazwa, other.nazwa)) {
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
        return true;
    }

    @Override
    public String toString() {
        return "FakturaDodPozycjaKlient{" + "nazwa=" + nazwa + ", kontrahent=" + kontrahent.getNpelna() + ", fakturaDodatkowaPozycja=" + fakturaDodatkowaPozycja.getNazwa() + ", ilosc=" + ilosc + ", rok=" + rok + ", mc=" + mc + ", rozliczone=" + rozliczone + '}';
    }
    
    
    
}
