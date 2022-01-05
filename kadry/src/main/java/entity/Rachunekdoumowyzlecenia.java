/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import kadryiplace.PlaceZlec;
import z.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rachunekdoumowyzlecenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findAll", query = "SELECT r FROM Rachunekdoumowyzlecenia r"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findById", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.id = :id"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByDatawystawienia", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByDataod", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.dataod = :dataod"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByDatado", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.datado = :datado"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByKwota", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.kwota = :kwota"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByKoszt", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.koszt = :koszt"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findBySpoleczne", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.spoleczne = :spoleczne"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByChorobowa", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.chorobowa = :chorobowa"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByZdrowotna", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.zdrowotna = :zdrowotna"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByWypadkowa", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.wypadkowa = :wypadkowa"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByPodatek", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.podatek = :podatek"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByRokUmowa", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.rok = :rok and r.umowa = :umowa"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByRokMcUmowa", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.rok = :rok and r.mc = :mc and r.umowa = :umowa"),
    @NamedQuery(name = "Rachunekdoumowyzlecenia.findByProcentkosztowuzyskania", query = "SELECT r FROM Rachunekdoumowyzlecenia r WHERE r.procentkosztowuzyskania = :procentkosztowuzyskania")})
public class Rachunekdoumowyzlecenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datawystawienia")
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dataod")
    private String dataod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datado")
    private String datado;
    @Column(name="rok")
    private String rok;
    @Column(name="mc")
    private String mc;
    @Column(name="wynagrodzeniemiesieczne")
    private double wynagrodzeniemiesieczne;
    @Column(name="wynagrodzeniegodzinowe")
    private double wynagrodzeniegodzinowe;
    @Column(name="iloscgodzin")
    private double iloscgodzin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "koszt")
    private double koszt;
    @Column(name = "spoleczne")
    private boolean spoleczne;
    @Column(name = "chorobowa")
    private boolean chorobowa;
    @Column(name = "zdrowotna")
    private boolean zdrowotna;
    @Column(name = "wypadkowa")
    private boolean wypadkowa;
    @Column(name = "podatek")
    private boolean podatek;
    @Column(name = "procentkosztowuzyskania")
    private double procentkosztowuzyskania;
    @JoinColumn(name = "pasekwynagrodzen", referencedColumnName = "id")
    @ManyToOne
    private Pasekwynagrodzen pasekwynagrodzen;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowa umowa;
    @Column(name="importowany")
    private boolean importowany;

    public Rachunekdoumowyzlecenia() {
    }

    public Rachunekdoumowyzlecenia(Integer id) {
        this.id = id;
    }

    public Rachunekdoumowyzlecenia(Integer id, String datawystawienia, String dataod, String datado) {
        this.id = id;
        this.datawystawienia = datawystawienia;
        this.dataod = dataod;
        this.datado = datado;
    }

    public Rachunekdoumowyzlecenia(PlaceZlec r) {
        this.datawystawienia = Data.data_yyyyMMddNull(r.getPzlDataDo());
        this.dataod =  Data.data_yyyyMMddNull(r.getPzlDataOd());
        this.datado =  Data.data_yyyyMMddNull(r.getPzlDataDo());
        this.kwota = Z.z(r.getPzlKwota().doubleValue());
        this.koszt = Z.z(r.getPzlKoszt().doubleValue());
        this.spoleczne = r.getPzlZus().equals('T');
        this.chorobowa = r.getPzlChorWyp().equals('T');
        this.zdrowotna = r.getPzlZdrowotne().equals('T');;
        this.wypadkowa = r.getPzlWyp().equals('T');;
        this.podatek = r.getPzlPodDoch().equals('T');;
        this.procentkosztowuzyskania = Z.z(r.getPzlKosztProc().doubleValue());
        this.importowany = true;
    }
    
     public Rachunekdoumowyzlecenia(Umowa umowa) {
        this.umowa = umowa;
        this.datawystawienia = Data.aktualnaData();
        this.spoleczne = umowa.isEmerytalne();
        this.chorobowa = umowa.isChorobowe()||umowa.isChorobowedobrowolne();
        this.zdrowotna = umowa.isZdrowotne();
        this.wypadkowa = umowa.isWypadkowe();
        this.procentkosztowuzyskania = Z.z(umowa.getKosztyuzyskaniaprocent());
    }


    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKoszt() {
        return koszt;
    }

    public void setKoszt(double koszt) {
        this.koszt = koszt;
    }

    public boolean getSpoleczne() {
        return spoleczne;
    }

    public void setSpoleczne(boolean spoleczne) {
        this.spoleczne = spoleczne;
    }

    public boolean getChorobowa() {
        return chorobowa;
    }

    public void setChorobowa(boolean chorobowa) {
        this.chorobowa = chorobowa;
    }

    public boolean getZdrowotna() {
        return zdrowotna;
    }

    public void setZdrowotna(boolean zdrowotna) {
        this.zdrowotna = zdrowotna;
    }

    public boolean getWypadkowa() {
        return wypadkowa;
    }

    public void setWypadkowa(boolean wypadkowa) {
        this.wypadkowa = wypadkowa;
    }

    public boolean getPodatek() {
        return podatek;
    }

    public void setPodatek(boolean podatek) {
        this.podatek = podatek;
    }

    public double getProcentkosztowuzyskania() {
        return procentkosztowuzyskania;
    }
    
    public double getProcentkosztowuzyskaniaDisplay() {
        return Z.z(procentkosztowuzyskania/100.0);
    }

    public void setProcentkosztowuzyskania(double procentkosztowuzyskania) {
        this.procentkosztowuzyskania = procentkosztowuzyskania;
    }

    public Pasekwynagrodzen getPasekwynagrodzen() {
        return pasekwynagrodzen;
    }

    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
        this.pasekwynagrodzen = pasekwynagrodzen;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
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

    public boolean isImportowany() {
        return importowany;
    }

    public void setImportowany(boolean importowany) {
        this.importowany = importowany;
    }

    public double getWynagrodzeniemiesieczne() {
        return wynagrodzeniemiesieczne;
    }

    public void setWynagrodzeniemiesieczne(double wynagrodzeniemiesieczne) {
        this.wynagrodzeniemiesieczne = wynagrodzeniemiesieczne;
    }

    public double getWynagrodzeniegodzinowe() {
        return wynagrodzeniegodzinowe;
    }

    public void setWynagrodzeniegodzinowe(double wynagrodzeniegodzinowe) {
        this.wynagrodzeniegodzinowe = wynagrodzeniegodzinowe;
    }

    public double getIloscgodzin() {
        return iloscgodzin;
    }

    public void setIloscgodzin(double iloscgodzin) {
        this.iloscgodzin = iloscgodzin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.pasekwynagrodzen);
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
        final Rachunekdoumowyzlecenia other = (Rachunekdoumowyzlecenia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.pasekwynagrodzen, other.pasekwynagrodzen)) {
            return false;
        }
        return true;
    }
    

    

    @Override
    public String toString() {
        return "Rachunekdoumowyzlecenia{" + "datawystawienia=" + datawystawienia + ", dataod=" + dataod + ", datado=" + datado + ", rok=" + rok + ", mc=" + mc + ", kwota=" + kwota + ", koszt=" + koszt + '}';
    }

    
}
