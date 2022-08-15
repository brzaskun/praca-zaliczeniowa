/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "drasumy", uniqueConstraints = {
    @UniqueConstraint(columnNames={"okres, nr, podatnik,podmiot"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DraSumy.findAll", query = "SELECT a FROM DraSumy a"),
    @NamedQuery(name = "DraSumy.findByRokMc", query = "SELECT a FROM DraSumy a where a.rok = :rok AND a.mc = :mc")
})
public class DraSumy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Transient
    private Zusdra zusdra;
    @Transient
    private Zusrca zusrca;
    @Transient
    private List<UbezpZusrca> ubezpZusrca;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "okres")
    private String okres;
    @JoinColumn(name = "podmiot", referencedColumnName = "id")
    @ManyToOne
    private Podmiot podmiot;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "nr")
    private String nr;
    @Column(name = "data")
    private String data;
    @Column(name = "ubezpieczeni")
    private int ubezpieczeni;
    @Column(name = "przedsiebiorcy")
    private int przedsiebiorcy;
    @Column(name = "pracownicy")
    private int pracownicy;
    @Column(name = "zleceniobiorcy")
    private int zleceniobiorcy;
    @Column(name = "innetytuly")
    private int innetytuly;
    @Column(name = "dozaplaty")
    private double dozaplaty;
    @Column(name = "kod")
    private String kod;
    @Column(name = "spoleczne")
    private double spoleczne;
    @Column(name = "zdrowotne")
    private double zdrowotne;
    

    public DraSumy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Zusdra getZusdra() {
        return zusdra;
    }

    public void setZusdra(Zusdra zusdra) {
        this.zusdra = zusdra;
    }

    public Zusrca getZusrca() {
        return zusrca;
    }

    public void setZusrca(Zusrca zusrca) {
        this.zusrca = zusrca;
    }

    
    public List<UbezpZusrca> getUbezpZusrca() {
        return ubezpZusrca;
    }

    public void setUbezpZusrca(List<UbezpZusrca> ubezpZusrca) {
        this.ubezpZusrca = ubezpZusrca;
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

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.zusdra);
        hash = 53 * hash + Objects.hashCode(this.okres);
        hash = 53 * hash + Objects.hashCode(this.podmiot);
        hash = 53 * hash + Objects.hashCode(this.podatnik);
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
        final DraSumy other = (DraSumy) obj;
        if (!Objects.equals(this.okres, other.okres)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.zusdra, other.zusdra)) {
            return false;
        }
        if (!Objects.equals(this.podmiot, other.podmiot)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    
    
    
    public String getNazwaF() {
        String zwrot = this.zusdra.getIi6Nazwaskr();
        if (zwrot == null) {
            zwrot = this.zusdra.getIi7Nazwisko()+" "+this.zusdra.getIi8Imiepierw();
        }
        return zwrot;
    }
    
    public int getUbezpieczeniF() {
        int suma = 0;
        if (this.zusrca!=null) {
            suma = this.zusrca.getLPozycji();
        } else if (this.zusdra!=null && this.zusdra.getIii1Lubezp()!=null) {
            suma = this.zusdra.getIii1Lubezp();
        }
        return suma;
    }
    
    public int getPrzedsiebiorcyF() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    suma = suma+1;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getIii1Lubezp()!=null) {
            suma = this.zusdra.getIii1Lubezp();
        }
        return suma;
    }
    
     public int getInnetytulyF() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (!p.getIiiB11kodtytub().startsWith("05")&&!p.getIiiB11kodtytub().startsWith("04")&&!p.getIiiB11kodtytub().startsWith("01")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
    
    
    public int getZleceniobiorcyF() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("04")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
    public int getPracownicyF() {
        int suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("01")) {
                    suma = suma+1;
                }
            }
        }
        return suma;
    }
    
    public String getKodF() {
        String suma = "n.d.";
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    suma = p.getIiiB11kodtytub()!=null?String.valueOf(p.getIiiB11kodtytub()):"brak";
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getXi11kodtytub()!=null) {
            suma = this.zusdra.getXi11kodtytub()!=null?String.valueOf(this.zusdra.getXi11kodtytub()):"brak";
        }
        return suma;
    }
    
    //pozycje przy pit-5
    public double getSpoleczneF() {
        double suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    double em = p.getIiiB7KwskleuRN()!= null ? p.getIiiB7KwskleuRN().doubleValue() :0.0;
                    double ren = p.getIiiB8KwsklruRN()!= null ? p.getIiiB8KwsklruRN().doubleValue() :0.0;
                    double cho = p.getIiiB9KwsklchuRN()!= null ? p.getIiiB9KwsklchuRN().doubleValue() :0.0;
                    double wyp = p.getIiiB10KwsklwyuR()!= null ? p.getIiiB10KwsklwyuR().doubleValue() :0.0;
                    suma = suma+em+ren+cho+wyp;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getIv32Kwskspol()!=null) {
            suma = this.zusdra.getIv32Kwskspol().doubleValue();
        }
        return suma;
    }
    
    //pozycje przy pit-5
     public double getZdrowotneF() {
        double suma = 0;
        if (this.ubezpZusrca!=null) {
            for (UbezpZusrca p : this.ubezpZusrca) {
                if (p.getIiiB11kodtytub().startsWith("05")) {
                    double zdr = p.getIiiC4KwsklzuR()!= null ? p.getIiiC4KwsklzuR().doubleValue() :0.0;
                    suma = suma+zdr;
                }
            }
        } else if (this.zusdra!=null && this.zusdra.getVii4Kwzap()!=null) {
            suma = this.zusdra.getVii4Kwzap().doubleValue();
        }
        return suma;
    }

    public Podmiot getPodmiot() {
        return podmiot;
    }

    public void setPodmiot(Podmiot podmiot) {
        this.podmiot = podmiot;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getUbezpieczeni() {
        return ubezpieczeni;
    }

    public void setUbezpieczeni(int ubezpieczeni) {
        this.ubezpieczeni = ubezpieczeni;
    }

    public int getPrzedsiebiorcy() {
        return przedsiebiorcy;
    }

    public void setPrzedsiebiorcy(int przedsiebiorcy) {
        this.przedsiebiorcy = przedsiebiorcy;
    }

    public int getPracownicy() {
        return pracownicy;
    }

    public void setPracownicy(int pracownicy) {
        this.pracownicy = pracownicy;
    }

    public int getZleceniobiorcy() {
        return zleceniobiorcy;
    }

    public void setZleceniobiorcy(int zleceniobiorcy) {
        this.zleceniobiorcy = zleceniobiorcy;
    }

    public int getInnetytuly() {
        return innetytuly;
    }

    public void setInnetytuly(int innetytuly) {
        this.innetytuly = innetytuly;
    }

    public double getDozaplaty() {
        return dozaplaty;
    }

    public void setDozaplaty(double dozaplaty) {
        this.dozaplaty = dozaplaty;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public double getSpoleczne() {
        return spoleczne;
    }

    public void setSpoleczne(double spoleczne) {
        this.spoleczne = spoleczne;
    }

    public double getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(double zdrowotne) {
        this.zdrowotne = zdrowotne;
    }
     
     
}
