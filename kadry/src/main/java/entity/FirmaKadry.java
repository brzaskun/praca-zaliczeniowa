/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "firmakadry", schema = "kadry", uniqueConstraints = {
    @UniqueConstraint(columnNames={"nip"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FirmaKadry.findAll", query = "SELECT f FROM FirmaKadry f"),
    @NamedQuery(name = "FirmaKadry.findById", query = "SELECT f FROM FirmaKadry f WHERE f.id = :id"),
    @NamedQuery(name = "FirmaKadry.findByNazwa", query = "SELECT f FROM FirmaKadry f WHERE f.nazwa = :nazwa"),
    @NamedQuery(name = "FirmaKadry.findByNip", query = "SELECT f FROM FirmaKadry f WHERE f.nip = :nip"),
    @NamedQuery(name = "FirmaKadry.findByBezglobal", query = "SELECT f FROM FirmaKadry f WHERE f.nip != '0000000000'"),
    @NamedQuery(name = "FirmaKadry.findByAktywneBezglobal", query = "SELECT f FROM FirmaKadry f WHERE f.nip != '0000000000' AND f.aktywna = TRUE")
})

public class FirmaKadry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "nip",nullable = false)
    private String nip;
     @Size(max = 255)
    @Column(name = "steuernummer",nullable = false)
    private String steuernummer;
    @Size(max = 255)
    @Column(name = "regon")
    private String regon;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Uz> uzList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Definicjalistaplac> definicjalistaplacList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Angaz> angazList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Kalendarzwzor> kalendarzWzorList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<DeklaracjaPIT11Schowek> deklaracjaPIT11Schowek;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<DeklaracjaPIT4Schowek> deklaracjaPIT4Schowek;
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    @Size(max = 128)
    @Column(name = "emaillp")
    private String emaillp;
    @Size(max = 128)
    @Column(name = "telefon")
    private String telefon;
    @Size(max = 128)
    @Column(name = "miasto")
    private String miasto;
    @Size(max = 128)
    @Column(name = "ulica")
    private String ulica;
    @Size(max = 128)
    @Column(name = "dom")
    private String dom;
    @Size(max = 128)
    @Column(name = "lokal")
    private String lokal;
    @Size(max = 7)
    @Column(name = "kod")
    private String kod;
    @Size(max = 128)
    @Column(name = "gmina")
    private String gmina;
    @Size(max = 128)
    @Column(name = "powiat")
    private String powiat;
    @Size(max = 128)
    @Column(name = "poczta")
    private String poczta;
    @Size(max = 45)
    @Column(name = "kraj")
    private String kraj;
    @Size(max = 128)
    @Column(name = "wojewodztwo")
    private String wojewodztwo;
    @Size(max = 128)
    @Column(name = "banknazwa")
    private String banknazwa;
    @Size(max = 128)
    @Column(name = "bankkonto")
    private String bankkonto;
    @Size(max = 128)
    @Column(name = "reprezentant")
    private String reprezentant;
    @Column(name = "osobafizyczna")
    private boolean osobafizyczna;
    @Column(name = "kodurzeduskarbowego")
    private String kodurzeduskarbowego;
    @Column(name = "nazwaurzeduskarbowego")
    private String nazwaurzeduskarbowego;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "dataurodzenia")
    private String dataurodzenia;
    @Column(name = "zaimportowana")
    private boolean zaimportowana;
    @Column(name = "fir_serial")
    private String fir_serial;
    @Column(name = "dzienlp")
    private String dzienlp;
    @Column(name = "domyslnyformatlp")
    private String domyslnyformatlp;
    @Column(name = "sadpracy")
    private String sadpracy;
    @Column(name = "bankpodatki")
    private String bankpodatki;
    @Column(name = "bankzus")
    private String bankzus;
    @Column(name = "ksiegowa")
    private String ksiegowa;
    @Column(name = "samozatrudnienie")
    private boolean samozatrudnienie;
    @Column(name = "aktywna")
    private boolean aktywna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private List<Grupakadry> grupaList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    List<Wypadkowefirma> wypadkowefirmaList;
    @Transient
    private String loginfirmy;
    @Transient
    private int m1;
    @Transient
    private int m2;
    @Transient
    private int m3;
    @Transient
    private int m4;
    @Transient
    private int m5;
    @Transient
    private int m6;
    @Transient
    private int m7;
    @Transient
    private int m8;
    @Transient
    private int m9;
    @Transient
    private int m10;
    @Transient
    private int m11;
    @Transient
    private int m12;
    @Transient
    private int m13;
    

    public FirmaKadry() {
    }

    public FirmaKadry(int id) {
        this.id = id;
    }

    @XmlTransient
    public List<Grupakadry> getGrupaList() {
        return grupaList;
    }

    public void setGrupaList(List<Grupakadry> grupaList) {
        this.grupaList = grupaList;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAktywna() {
        return aktywna;
    }

    public void setAktywna(boolean aktywna) {
        this.aktywna = aktywna;
    }

    
    public String getFir_serial() {
        return fir_serial;
    }

    public void setFir_serial(String fir_serial) {
        this.fir_serial = fir_serial;
    }

    public String getMiasto() {
        String zwrot = this.miasto;
        if (zwrot==null) {
            zwrot = "brak siedziby";
        }
        return zwrot;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getDomyslnyformatlp() {
        return domyslnyformatlp;
    }

    public void setDomyslnyformatlp(String domyslnyformatlp) {
        this.domyslnyformatlp = domyslnyformatlp;
    }

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getPowiat() {
        return powiat;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public String getLoginfirmy() {
        return loginfirmy;
    }

    public void setLoginfirmy(String loginfirmy) {
        this.loginfirmy = loginfirmy;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getReprezentant() {
        String zwrot = reprezentant;
        if (zwrot==null) {
            zwrot ="nie wprowadzono reprezentanta";
        }
        return zwrot;
    }

    public void setReprezentant(String reprezentant) {
        this.reprezentant = reprezentant;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public boolean isOsobafizyczna() {
        return osobafizyczna;
    }

    public void setOsobafizyczna(boolean osobafizyczna) {
        this.osobafizyczna = osobafizyczna;
    }

    public String getEmaillp() {
        return emaillp;
    }

    public void setEmaillp(String emaillp) {
        this.emaillp = emaillp;
    }

    public String getKodurzeduskarbowego() {
        return kodurzeduskarbowego;
    }

    public void setKodurzeduskarbowego(String kodurzeduskarbowego) {
        this.kodurzeduskarbowego = kodurzeduskarbowego;
    }

    public String getNazwaurzeduskarbowego() {
        return nazwaurzeduskarbowego;
    }

    public void setNazwaurzeduskarbowego(String nazwaurzeduskarbowego) {
        this.nazwaurzeduskarbowego = nazwaurzeduskarbowego;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public boolean isSamozatrudnienie() {
        return samozatrudnienie;
    }

    public void setSamozatrudnienie(boolean samozatrudnienie) {
        this.samozatrudnienie = samozatrudnienie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getDataurodzenia() {
        return dataurodzenia;
    }

    public void setDataurodzenia(String dataurodzenia) {
        this.dataurodzenia = dataurodzenia;
    }

    public boolean isZaimportowana() {
        return zaimportowana;
    }

    public void setZaimportowana(boolean zaimportowana) {
        this.zaimportowana = zaimportowana;
    }

    public String getDzienlp() {
        return dzienlp;
    }

    public String getBankpodatki() {
        return bankpodatki;
    }

    public void setBankpodatki(String bankpodatki) {
        this.bankpodatki = bankpodatki;
    }

    public String getBankzus() {
        return bankzus;
    }

    public void setBankzus(String bankzus) {
        this.bankzus = bankzus;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }

    public int getM4() {
        return m4;
    }

    public void setM4(int m4) {
        this.m4 = m4;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM6() {
        return m6;
    }

    public void setM6(int m6) {
        this.m6 = m6;
    }

    public int getM7() {
        return m7;
    }

    public void setM7(int m7) {
        this.m7 = m7;
    }

    public String getKsiegowa() {
        return ksiegowa;
    }

    public void setKsiegowa(String ksiegowa) {
        this.ksiegowa = ksiegowa;
    }

    
    public int getM8() {
        return m8;
    }

    public void setM8(int m8) {
        this.m8 = m8;
    }

    public int getM9() {
        return m9;
    }

    public void setM9(int m9) {
        this.m9 = m9;
    }

    public int getM10() {
        return m10;
    }

    public void setM10(int m10) {
        this.m10 = m10;
    }

    public int getM11() {
        return m11;
    }

    public void setM11(int m11) {
        this.m11 = m11;
    }

    public int getM12() {
        return m12;
    }

    public void setM12(int m12) {
        this.m12 = m12;
    }

    public int getM13() {
        return m13;
    }

    public void setM13(int m13) {
        this.m13 = m13;
    }
    
    

    public String getSadpracy() {
        String zwrot = this.sadpracy;
        if (zwrot==null) {
            zwrot = "NIE WPROWADZONO DANYCH SĄDU";
        }
        return zwrot;
    }

    public void setSadpracy(String sadpracy) {
        this.sadpracy = sadpracy;
    }
    
    

    public void setDzienlp(String dzienlp) {
        if (dzienlp.equals("")) {
            this.dzienlp = null;
        }else if (dzienlp!=null&&dzienlp.length()==1) {
            this.dzienlp = "0"+this.dzienlp;
        } else {
            this.dzienlp = dzienlp;
        }
    }


    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }
    
    @XmlTransient
    public List<Angaz> getAngazListAktywne() {
        List<Angaz> aktywne = new ArrayList<>();
        for (Angaz a : this.angazList) {
            if (a.isUkryj()==false) {
                aktywne.add(a);
            }
        }
        return aktywne;
    }
    

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
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
        if (!(object instanceof FirmaKadry)) {
            return false;
        }
        FirmaKadry other = (FirmaKadry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirmaKadry{" + "nazwa=" + nazwa + ", nip=" + nip + '}';
    }

    


    @XmlTransient
    public List<Kalendarzwzor> getKalendarzWzorList() {
        return kalendarzWzorList;
    }

    public void setKalendarzWzorList(List<Kalendarzwzor> kalendarzWzorList) {
        this.kalendarzWzorList = kalendarzWzorList;
    }


    @XmlTransient
    public List<Definicjalistaplac> getDefinicjalistaplacList() {
        return definicjalistaplacList;
    }

    public void setDefinicjalistaplacList(List<Definicjalistaplac> definicjalistaplacList) {
        this.definicjalistaplacList = definicjalistaplacList;
    }
    @XmlTransient
    public List<DeklaracjaPIT11Schowek> getDeklaracjaPIT11Schowek() {
        return deklaracjaPIT11Schowek;
    }

    public void setDeklaracjaPIT11Schowek(List<DeklaracjaPIT11Schowek> deklaracjaPIT11Schowek) {
        this.deklaracjaPIT11Schowek = deklaracjaPIT11Schowek;
    }
    @XmlTransient
    public List<DeklaracjaPIT4Schowek> getDeklaracjaPIT4Schowek() {
        return deklaracjaPIT4Schowek;
    }

    public void setDeklaracjaPIT4Schowek(List<DeklaracjaPIT4Schowek> deklaracjaPIT4Schowek) {
        this.deklaracjaPIT4Schowek = deklaracjaPIT4Schowek;
    }


    @XmlTransient
    public List<Uz> getUzList() {
        return uzList;
    }

    public void setUzList(List<Uz> uzList) {
        this.uzList = uzList;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getBanknazwa() {
        return banknazwa;
    }

    public void setBanknazwa(String banknazwa) {
        this.banknazwa = banknazwa;
    }

    public String getBankkonto() {
        return bankkonto;
    }

    public void setBankkonto(String bankkonto) {
        this.bankkonto = bankkonto;
    }

    public String getSteuernummer() {
        String zwrot = "brak Steuernummer";
        if (this.steuernummer!=null) {
            zwrot = this.steuernummer;
        }
        return zwrot;
    }

    public void setSteuernummer(String steuernummer) {
        this.steuernummer = steuernummer;
    }
    
    
    
    @XmlTransient
    public List<Wypadkowefirma> getWypadkowefirmaList() {
        return wypadkowefirmaList;
    }

    public void setWypadkowefirmaList(List<Wypadkowefirma> wypadkowefirmaList) {
        this.wypadkowefirmaList = wypadkowefirmaList;
    }
    
    
    public String getAdres() {
        String zwrot = this.kod+" "+this.miasto+", "+this.ulica+" "+this.dom+"/"+this.lokal;
        if (this.lokal==null) {
            zwrot = this.kod+" "+this.miasto+", "+this.ulica+" "+this.dom;
        }
        return zwrot;
    }
    
    public boolean isSadekalracjePIT11(String rok) {
        boolean zwrot = false;
        if (this.deklaracjaPIT11Schowek!=null) {
            for (DeklaracjaPIT11Schowek pit : this.deklaracjaPIT11Schowek) {
                if (pit.getRok().equals(rok)) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public boolean isSadekalracjePIT4(String rok) {
        boolean zwrot = false;
        if (this.deklaracjaPIT4Schowek!=null) {
            for (DeklaracjaPIT4Schowek pit : this.deklaracjaPIT4Schowek) {
                if (pit.getRok().equals(rok)) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public String getFirmaNazwa() {
        String zwrot = this.nazwa;
        if (zwrot==null||zwrot.equals("")) {
            zwrot = this.nazwisko+" "+this.imie;
        }
        return zwrot;
    }
    
}
