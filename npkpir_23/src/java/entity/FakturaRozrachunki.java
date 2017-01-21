/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakturarozrachunki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FakturaRozrachunki.findAll", query = "SELECT e FROM FakturaRozrachunki e"),
    @NamedQuery(name = "FakturaRozrachunki.findByData_k", query = "SELECT e FROM FakturaRozrachunki e WHERE e.dataksiegowania = :data AND e.wystawca = :podatnik"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnik", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikRokMc", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.rok = :rok AND e.mc = :mc"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikKontrahent", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.kontrahent = :kontrahent"),
    @NamedQuery(name = "FakturaRozrachunki.findByPodatnikKontrahentRok", query = "SELECT e FROM FakturaRozrachunki e WHERE e.wystawca = :podatnik AND e.kontrahent = :kontrahent AND e.rok = :rok")
})
public class FakturaRozrachunki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp")
    private Integer lp;
    @JoinColumn(name = "wystawca", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik wystawca;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontrahent;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Column(name = "data_k", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataksiegowania;
    @Size(max = 10)
    @Column(name = "data")
//    @Temporal(TemporalType.DATE)
    private String data;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "rodzajdokumentu")
    private String rodzajdokumentu;
    @Column(name = "nrdokumentu")
    private String nrdokumentu;
    @Column(name = "zaplatakorekta")
    private boolean zaplata0korekta1;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "nowy0archiwum1")
    private boolean nowy0archiwum1;
    @Column(name = "dataupomnienia", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataupomnienia;
    @Column(name = "datatelefon", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datatelefon;
    
    @PrePersist
    private void prepresist() {
        this.dataksiegowania = new Date();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.lp);
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
        final FakturaRozrachunki other = (FakturaRozrachunki) obj;
        if (!Objects.equals(this.lp, other.lp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaRozrachunki{" + "lp=" + lp + ", wystawca=" + wystawca.getNazwapelna() + ", kontrahent=" + kontrahent.getNpelna() + ", wprowadzil=" + wprowadzil + ", dataksiegowania=" + dataksiegowania + ", data=" + data + ", kwota=" + kwota + ", zaplata0korekta1=" + zaplata0korekta1 + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getLp() {
        return lp;
    }
    
    public void setLp(Integer lp) {
        this.lp = lp;
    }
    
    public Podatnik getWystawca() {
        return wystawca;
    }
    
    public void setWystawca(Podatnik wystawca) {
        this.wystawca = wystawca;
    }
    
    public Klienci getKontrahent() {
        return kontrahent;
    }
    
    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
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
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public double getKwota() {
        return kwota;
    }
    
    public void setKwota(double kwota) {
        this.kwota = kwota;
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
    
    public boolean isZaplata0korekta1() {
        return zaplata0korekta1;
    }
    
    public void setZaplata0korekta1(boolean zaplata0korekta1) {
        this.zaplata0korekta1 = zaplata0korekta1;
    }
    
    public String getRodzajdokumentu() {
        return rodzajdokumentu;
    }
    
    public void setRodzajdokumentu(String rodzajdokumentu) {
        this.rodzajdokumentu = rodzajdokumentu;
    }
    
    public String getNrdokumentu() {
        return nrdokumentu;
    }
    
    public void setNrdokumentu(String nrdokumentu) {
        this.nrdokumentu = nrdokumentu;
    }
    
    public boolean isNowy0archiwum1() {
        return nowy0archiwum1;
    }
    
    public void setNowy0archiwum1(boolean nowy0archiwum1) {
        this.nowy0archiwum1 = nowy0archiwum1;
    }
    
    public Date getDataupomnienia() {
        return dataupomnienia;
    }
    
    public void setDataupomnienia(Date dataupomnienia) {
        this.dataupomnienia = dataupomnienia;
    }
    
    public Date getDatatelefon() {
        return datatelefon;
    }
    
    public void setDatatelefon(Date datatelefon) {
        this.datatelefon = datatelefon;
    }
//</editor-fold>
   
    
    
}
