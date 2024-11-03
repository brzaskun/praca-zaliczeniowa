/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webservice;

import entity.Kadryfakturapozycja;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 * 
 * jak chciqalem dodac nowe pola to byla cala zabawa z usuwaniem plikow jaxws-build w npkpir
 * potem byly problemy z nazwami odwarzalem z hostorri. kupa roboty. byc moze nie zauwazylem na pocztaku ze jednak sie przeniosly pola do wygenerowancyh klas p[rzyjrzyj sie dokladnie
 * nic strasznego. zaktualizowac kadry clean and build
 * potem pobrac wsdl i wkleic C:\Users\Osito\Documents\NetBeansProjects\npkpir_23\src\conf\xml-resources\web-service-references\WsKadryFakturaPozycja_1\wsdl\localhost_8080\kadry\WsKadryFakturaPozycja.wsdl
 * clean and buidl i dziala, cala filozofia. nie ruszamy zadnych jaxws albo build!!
 */
@Entity
@Table(name = "wierszfaktury", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nip", "rok", "mc", "opis"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WierszFaktury.findAll", query = "SELECT w FROM WierszFaktury w"),
    @NamedQuery(name = "WierszFaktury.findById", query = "SELECT w FROM WierszFaktury w WHERE w.id = :id"),
    @NamedQuery(name = "WierszFaktury.findByNip", query = "SELECT w FROM WierszFaktury w WHERE w.nip = :nip"),
    @NamedQuery(name = "WierszFaktury.findByRok", query = "SELECT w FROM WierszFaktury w WHERE w.rok = :rok"),
    @NamedQuery(name = "WierszFaktury.findByRokMc", query = "SELECT w FROM WierszFaktury w WHERE w.rok = :rok AND  w.mc = :mc"),
    @NamedQuery(name = "WierszFaktury.findByNipRokMc", query = "SELECT w FROM WierszFaktury w WHERE w.nip = :nip AND w.rok = :rok AND  w.mc = :mc"),
    @NamedQuery(name = "WierszFaktury.findByMc", query = "SELECT w FROM WierszFaktury w WHERE w.mc = :mc"),
    @NamedQuery(name = "WierszFaktury.findByOpis", query = "SELECT w FROM WierszFaktury w WHERE w.opis = :opis"),
    @NamedQuery(name = "WierszFaktury.findBySymbolwaluty", query = "SELECT w FROM WierszFaktury w WHERE w.symbolwaluty = :symbolwaluty"),
    @NamedQuery(name = "WierszFaktury.findByKwota", query = "SELECT w FROM WierszFaktury w WHERE w.kwota = :kwota"),
    @NamedQuery(name = "WierszFaktury.findByIlosc", query = "SELECT w FROM WierszFaktury w WHERE w.ilosc = :ilosc")})
public class WierszFaktury implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nip", nullable = false, length = 45)
    private String nip;
    @Basic(optional = false)
    @Column(name = "nazwa", nullable = false, length = 256)
    private String nazwa;
    @Basic(optional = false)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @Column(name = "mc", nullable = false, length = 2)
    private String mc;
    @Column(name = "opis", length = 128)
    private String opis;
    @Column(name = "symbolwaluty", length = 3)
    private String symbolwaluty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota", precision = 22, scale = 0)
    private double kwota;
    @Column(name = "ilosc")
    private int ilosc;
    @Column(name = "data")
    private String data;
    @Column(name = "zamkniety")
    private boolean zamkniety;
    @Transient
    private boolean nowacena;

    public WierszFaktury() {
    }

    public WierszFaktury(int id) {
        this.id = id;
    }

    public WierszFaktury(int id, String nip, String rok, String mc) {
        this.id = id;
        this.nip = nip;
        this.rok = rok;
        this.mc = mc;
    }

    public WierszFaktury(Kadryfakturapozycja k, String rok, String mc) {
        this.nip = k.getFirmakadry().getNip();
        this.nazwa = k.getFirmakadry().getNazwa();
        this.opis = k.getOpisuslugi().getOpis();
        this.rok = rok;
        this.mc = mc;
        this.kwota = k.getCena();
        this.symbolwaluty = k.getWaluta().getSymbolwaluty();
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

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isNowacena() {
        return nowacena;
    }

    public void setNowacena(boolean nowacena) {
        this.nowacena = nowacena;
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

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

 

    public boolean isZamkniety() {
        return zamkniety;
    }

    public void setZamkniety(boolean zamkniety) {
        this.zamkniety = zamkniety;
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
        if (!(object instanceof WierszFaktury)) {
            return false;
        }
        WierszFaktury other = (WierszFaktury) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WierszFaktury{" + "nip=" + nip + ", nazwa=" + nazwa + ", rok=" + rok + ", mc=" + mc + ", opis=" + opis + ", symbolwaluty=" + symbolwaluty + ", kwota=" + kwota + ", ilosc=" + ilosc + '}';
    }

   
}
