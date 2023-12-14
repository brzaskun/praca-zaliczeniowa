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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import pluginkadry.WierszFaktury;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wierszfakturybaza", catalog = "pkpir", schema = "")
@NamedQueries({
    @NamedQuery(name = "Wierszfakturybaza.findAll", query = "SELECT w FROM Wierszfakturybaza w"),
    @NamedQuery(name = "Wierszfakturybaza.findById", query = "SELECT w FROM Wierszfakturybaza w WHERE w.id = :id"),
    @NamedQuery(name = "Wierszfakturybaza.findByNip", query = "SELECT w FROM Wierszfakturybaza w WHERE w.nip = :nip"),
    @NamedQuery(name = "Wierszfakturybaza.findByRok", query = "SELECT w FROM Wierszfakturybaza w WHERE w.rok = :rok"),
    @NamedQuery(name = "Wierszfakturybaza.findByRokMc", query = "SELECT w FROM Wierszfakturybaza w WHERE w.rok = :rok AND  w.mc = :mc"),
    @NamedQuery(name = "Wierszfakturybaza.findByNipRokMc", query = "SELECT w FROM Wierszfakturybaza w WHERE w.nip = :nip AND w.rok = :rok AND  w.mc = :mc"),
    @NamedQuery(name = "Wierszfakturybaza.findByMc", query = "SELECT w FROM Wierszfakturybaza w WHERE w.mc = :mc"),
    @NamedQuery(name = "Wierszfakturybaza.findByOpis", query = "SELECT w FROM Wierszfakturybaza w WHERE w.opis = :opis"),
    @NamedQuery(name = "Wierszfakturybaza.findBySymbolwaluty", query = "SELECT w FROM Wierszfakturybaza w WHERE w.symbolwaluty = :symbolwaluty"),
    @NamedQuery(name = "Wierszfakturybaza.findByKwota", query = "SELECT w FROM Wierszfakturybaza w WHERE w.kwota = :kwota"),
    @NamedQuery(name = "Wierszfakturybaza.findByIlosc", query = "SELECT w FROM Wierszfakturybaza w WHERE w.ilosc = :ilosc"),
    @NamedQuery(name = "Wierszfakturybaza.findByNazwa", query = "SELECT w FROM Wierszfakturybaza w WHERE w.nazwa = :nazwa")})
public class Wierszfakturybaza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nip")
    private String nip;
    @Basic(optional = false)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @Column(name = "mc")
    private String mc;
    @Column(name = "opis")
    private String opis;
    @Column(name = "symbolwaluty")
    private String symbolwaluty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private Double kwota;
    @Column(name = "ilosc")
    private Integer ilosc;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "naniesiony")
    private boolean naniesiony;
    @Column(name = "wymagakorekty")
    private boolean wymagakorekty;
    @Column(name = "datafaktury")
    private Date datafaktury;

    public Wierszfakturybaza() {
    }

    public Wierszfakturybaza(Integer id) {
        this.id = id;
    }

    public Wierszfakturybaza(Integer id, String nip, String rok, String mc) {
        this.id = id;
        this.nip = nip;
        this.rok = rok;
        this.mc = mc;
    }

    public Wierszfakturybaza(WierszFaktury w) {
        this.nip = w.getNip();
        this.rok = w.getRok();
        this.mc = w.getMc();
        this.opis = w.getOpis();
        this.symbolwaluty = w.getSymbolwaluty();
        this.kwota = w.getKwota();
        this.ilosc = w.getIlosc();
        this.nazwa = w.getNazwa();
    }

   
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    public Integer getIlosc() {
        return ilosc;
    }

    public void setIlosc(Integer ilosc) {
        this.ilosc = ilosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean isNaniesiony() {
        return naniesiony;
    }

    public void setNaniesiony(boolean naniesiony) {
        this.naniesiony = naniesiony;
    }

    public Date getDatafaktury() {
        return datafaktury;
    }

    public void setDatafaktury(Date datafaktury) {
        this.datafaktury = datafaktury;
    }

    public boolean isWymagakorekty() {
        return wymagakorekty;
    }

    public void setWymagakorekty(boolean wymagakorekty) {
        this.wymagakorekty = wymagakorekty;
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
        if (!(object instanceof Wierszfakturybaza)) {
            return false;
        }
        Wierszfakturybaza other = (Wierszfakturybaza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Wierszfakturybaza{" + "nip=" + nip + ", rok=" + rok + ", mc=" + mc + ", opis=" + opis + ", kwota=" + kwota + ", ilosc=" + ilosc + ", nazwa=" + nazwa + ", naniesiony=" + naniesiony + '}';
    }

   
    
}
