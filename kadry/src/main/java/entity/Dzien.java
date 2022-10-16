/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
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
import z.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dzien")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dzien.findAll", query = "SELECT d FROM Dzien d"),
    @NamedQuery(name = "Dzien.findById", query = "SELECT d FROM Dzien d WHERE d.id = :id"),
    @NamedQuery(name = "Dzien.findByNrdnia", query = "SELECT d FROM Dzien d WHERE d.nrdnia = :nrdnia"),
    @NamedQuery(name = "Dzien.findByDatastring", query = "SELECT d FROM Dzien d WHERE d.datastring >= :dataod AND d.datastring <= :datado AND d.kalendarzwzor.firma = :firma"),
    @NamedQuery(name = "Dzien.findByRob1wolny0swieto2", query = "SELECT d FROM Dzien d WHERE d.typdnia = :typdnia"),
    @NamedQuery(name = "Dzien.findByNormagodzin", query = "SELECT d FROM Dzien d WHERE d.normagodzin = :normagodzin"),
    @NamedQuery(name = "Dzien.findByPrzepracowano", query = "SELECT d FROM Dzien d WHERE d.przepracowano = :przepracowano"),
    @NamedQuery(name = "Dzien.findByPiecdziesiatki", query = "SELECT d FROM Dzien d WHERE d.piecdziesiatki = :piecdziesiatki"),
    @NamedQuery(name = "Dzien.findBySetki", query = "SELECT d FROM Dzien d WHERE d.setki = :setki"),
    @NamedQuery(name = "Dzien.findByPoranocna", query = "SELECT d FROM Dzien d WHERE d.poranocna = :poranocna")})
public class Dzien implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nrdnia")
    private int nrdnia;
    @Column(name = "datastring")
    private String datastring;
    //0roboczy
    //1sobota
    //2niedziela
    //3swieto
    //4ekwiwalentzaswieto
    @Column(name = "typdnia")
    private int typdnia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "normagodzin")
    private double normagodzin;
    @Column(name = "normagodzinwzorcowa")
    private double normagodzinwzorcowa;
    @Column(name = "przepracowano")
    private double przepracowano;
    @Column(name = "piecdziesiatki")
    private double piecdziesiatki;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "setki")
    private double setki;
    @Column(name = "poranocna")
    private double poranocna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etat1")
    private int etat1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etat2")
    private int etat2;
    @Size(max = 5)
    @Column(name = "kod")
    private String kod;
    @Column(name = "urlopplatny")
    private double urlopPlatny;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "urlopbezplatny")
    private double urlopbezplatny;
    @Column(name = "wynagrodzeniezachorobe")
    private double wynagrodzeniezachorobe;
    @Column(name = "zasilek")
    private double zasilek;
    @Column(name = "opiekadziecko")
    private double opiekadziecko;
    @JoinColumn(name = "kalendarzwzor", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzwzor kalendarzwzor;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzmiesiac kalendarzmiesiac;
    @JoinColumn(name = "nieobecnosc", referencedColumnName = "id")
    @ManyToOne
    private Nieobecnosc nieobecnosc;

    
    
    public Dzien() {
    }

    public Dzien(int id) {
        this.id = id;
    }

    public Dzien(int nrdnia, String datastring, int rob1wolny0swieto2, double normagodzin, double przepracowano, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.datastring = datastring;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        try {
            LocalDate data = LocalDate.parse(datastring);
            DayOfWeek dayOfWeek = data.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY) {
                this.typdnia = 1;
                this.normagodzin = 0;
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                this.typdnia = 2;
                this.normagodzin = 0;
            }
        } catch (Exception e){}
        this.przepracowano = przepracowano;
        this.kalendarzmiesiac = kalendarzmiesiac;
    }
    
    public Dzien(int nrdnia, String datastring, int rob1wolny0swieto2, double normagodzin, double przepracowano, double piecdziesiatki, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.datastring = datastring;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        try {
            LocalDate data = LocalDate.parse(datastring);
            DayOfWeek dayOfWeek = data.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY) {
                this.typdnia = 1;
                this.normagodzin = 0;
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                this.typdnia = 2;
                this.normagodzin = 0;
            }
        } catch (Exception e){}
        this.przepracowano = przepracowano;
        this.kalendarzmiesiac = kalendarzmiesiac;
        this.piecdziesiatki = piecdziesiatki;
    }
    
    public Dzien(int nrdnia, String datastring, int rob1wolny0swieto2, double normagodzin, Kalendarzwzor kalendarzWzor) {
        this.nrdnia = nrdnia;
        this.datastring = datastring;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        try {
            LocalDate data = LocalDate.parse(datastring);
            DayOfWeek dayOfWeek = data.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY) {
                this.typdnia = 1;
                this.normagodzin = 0;
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                this.typdnia = 2;
                this.normagodzin = 0;
            }
        } catch (Exception e){}
        this.kalendarzwzor = kalendarzWzor;
    }


    public Dzien(Dzien dzienwzor, Kalendarzmiesiac kalendarzmiesiac, EtatPrac etat) {
        this.nrdnia = dzienwzor.nrdnia;
        this.datastring = dzienwzor.datastring;
        this.typdnia = dzienwzor.typdnia;
        this.normagodzin = dzienwzor.normagodzin;
        this.normagodzinwzorcowa = dzienwzor.normagodzin;
        this.etat1 = 1;
        this.etat2 = 1;
        if (etat!=null) {
            this.etat1 = etat.getEtat1();
            this.etat2 = etat.getEtat2();
            this.normagodzin = Z.z(this.normagodzinwzorcowa*this.etat1/this.etat2);
        }
        //tu nie ma bledu bo inczaej pracownik ma zero.za kladamy ze pracowal
        this.przepracowano = dzienwzor.normagodzin;
        this.piecdziesiatki = dzienwzor.piecdziesiatki;
        this.setki = dzienwzor.setki;
        this.poranocna = dzienwzor.poranocna;
        this.kod = dzienwzor.kod;
        this.urlopPlatny = dzienwzor.urlopPlatny;
        this.urlopbezplatny = dzienwzor.urlopbezplatny;
        this.wynagrodzeniezachorobe = dzienwzor.wynagrodzeniezachorobe;
        this.zasilek = dzienwzor.zasilek;
        this.opiekadziecko = dzienwzor.opiekadziecko;
        this.kalendarzmiesiac = kalendarzmiesiac;
    }
    
    public Dzien(Dzien dzienwzor, Kalendarzwzor kalendarzwzor) {
        this.id = null;
        this.nrdnia = dzienwzor.nrdnia;
        this.datastring = dzienwzor.datastring;
        this.typdnia = dzienwzor.typdnia;
        this.normagodzin = dzienwzor.normagodzin;
        //tu nie ma bledu bo inczaej pracownik ma zero.za kladamy ze pracowal
        this.przepracowano = dzienwzor.normagodzin;
        this.piecdziesiatki = dzienwzor.piecdziesiatki;
        this.setki = dzienwzor.setki;
        this.poranocna = dzienwzor.poranocna;
        this.kod = dzienwzor.kod;
        this.urlopPlatny = dzienwzor.urlopPlatny;
        this.urlopbezplatny = dzienwzor.urlopbezplatny;
        this.wynagrodzeniezachorobe = dzienwzor.wynagrodzeniezachorobe;
        this.zasilek = dzienwzor.zasilek;
        this.opiekadziecko = dzienwzor.opiekadziecko;
        this.kalendarzwzor = kalendarzwzor;
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
        if (!(object instanceof Dzien)) {
            return false;
        }
        Dzien other = (Dzien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Dzien{" + "data=" + datastring + ", nrdnia=" + nrdnia + ", typdnia=" + typdnia + ", normagodzin=" + normagodzin + ", przepracowano=" + przepracowano + ", piecdziesiatki=" + piecdziesiatki + ", setki=" + setki + ", poranocna=" + poranocna + ", kod=" + kod + '}';
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Kalendarzmiesiac getKalendarzmiesiac() {
        return kalendarzmiesiac;
    }
    public void setKalendarzmiesiac(Kalendarzmiesiac kalendarzmiesiac) {
        this.kalendarzmiesiac = kalendarzmiesiac;
    }
    public Nieobecnosc getNieobecnosc() {
        return nieobecnosc;
    }

    public void setNieobecnosc(Nieobecnosc nieobecnosc) {
        this.nieobecnosc = nieobecnosc;
    }
    public Kalendarzwzor getKalendarzwzor() {
        return kalendarzwzor;
    }
    public void setKalendarzwzor(Kalendarzwzor kalendarzwzor) {
        this.kalendarzwzor = kalendarzwzor;
    }

    public int getNrdnia() {
        return nrdnia;
    }

    public void setNrdnia(int nrdnia) {
        this.nrdnia = nrdnia;
    }

    public String getDatastring() {
        return datastring;
    }

    public void setDatastring(String datastring) {
        this.datastring = datastring;
    }

    public double getNormagodzinwzorcowa() {
        return normagodzinwzorcowa;
    }

    public void setNormagodzinwzorcowa(double normagodzinwzorcowa) {
        this.normagodzinwzorcowa = normagodzinwzorcowa;
    }

    public int getEtat1() {
        return etat1;
    }

    public void setEtat1(int etat1) {
        this.etat1 = etat1;
    }

    public int getEtat2() {
        return etat2;
    }

    public void setEtat2(int etat2) {
        this.etat2 = etat2;
    }


    

    public int getTypdnia() {
        return typdnia;
    }

    public String getTypdniaString() {
        String zwrot = "robocz.";
        switch (typdnia) {
            case 1:
                zwrot = "sobota";
                break;
            case 2: 
                zwrot = "niedziela";
                break;
            case 3: 
                zwrot = "święto";
                break;
            case 4: 
                zwrot = "ekw.za św.";
                break;
        }
        return zwrot;
    }
    
    public String getTypdniaCol() {
        String zwrot = "init";
        switch (typdnia) {
            case 1:
                zwrot = "blue";
                break;
            case 2: 
                zwrot = "red";
                break;
            case 3: 
                zwrot = "orange";
                break;
            case 4: 
                zwrot = "purple";
                break;
        }
        return zwrot;
    }

    
    public void setTypdnia(int typdnia) {
        this.typdnia = typdnia;
    }

    public double getNormagodzin() {
        return normagodzin;
    }

    public void setNormagodzin(double normagodzin) {
        this.normagodzin = normagodzin;
    }

    public double getPrzepracowano() {
        return przepracowano;
    }

    public void setPrzepracowano(double przepracowano) {
        this.przepracowano = przepracowano;
    }

    public double getPiecdziesiatki() {
        return piecdziesiatki;
    }

    public void setPiecdziesiatki(double piecdziesiatki) {
        this.piecdziesiatki = piecdziesiatki;
    }

    public double getSetki() {
        return setki;
    }


    public void setSetki(double setki) {
        this.setki = setki;
    }


    public double getPoranocna() {
        return poranocna;
    }

    public void setPoranocna(double poranocna) {
        this.poranocna = poranocna;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public double getUrlopPlatny() {
        return urlopPlatny;
    }

    public void setUrlopPlatny(double urlopPlatny) {
        this.urlopPlatny = urlopPlatny;
    }

    public double getUrlopbezplatny() {
        return urlopbezplatny;
    }

    public void setUrlopbezplatny(double urlopbezplatny) {
        this.urlopbezplatny = urlopbezplatny;
    }

    public double getWynagrodzeniezachorobe() {
        return wynagrodzeniezachorobe;
    }

    public void setWynagrodzeniezachorobe(double wynagrodzeniezachorobe) {
        this.wynagrodzeniezachorobe = wynagrodzeniezachorobe;
    }

    public double getZasilek() {
        return zasilek;
    }

    public void setZasilek(double zasilek) {
        this.zasilek = zasilek;
    }

    public double getOpiekadziecko() {
        return opiekadziecko;
    }

    public void setOpiekadziecko(double opiekadziecko) {
        this.opiekadziecko = opiekadziecko;
    }

    void nanies(Dzien dzienwzor) {
        this.nrdnia = dzienwzor.nrdnia;
        this.datastring = dzienwzor.datastring;
        this.typdnia = dzienwzor.typdnia;
        this.przepracowano = dzienwzor.normagodzin;
        this.normagodzin = dzienwzor.normagodzin;
        this.piecdziesiatki = 0.0;
        this.setki = 0.0;
        this.poranocna = 0.0;
        this.wynagrodzeniezachorobe = 0;
        this.zasilek = 0;
        this.urlopPlatny = 0.0;
        this.urlopbezplatny = 0.0;
        this.opiekadziecko = 0.0;
        this.kod = null;
    }
    
    void resetnieobecnosc(Dzien dzienwzor) {
        this.przepracowano = dzienwzor.normagodzin;
        this.normagodzin = dzienwzor.normagodzin;
        this.wynagrodzeniezachorobe = 0;
        this.zasilek = 0;
        this.urlopPlatny = 0.0;
        this.urlopbezplatny = 0.0;
        this.opiekadziecko = 0.0;
        this.kod = null;
    }

    void nanieswzor(Dzien dzienwzor) {
        this.typdnia = dzienwzor.typdnia;
        this.normagodzin = dzienwzor.normagodzin;
        this.przepracowano = dzienwzor.normagodzin;
        if (dzienwzor.getKod()!=null&&!dzienwzor.getKod().equals("")) {
            this.kod = dzienwzor.kod;
        }
    }

    public void nanieswzorcowe(List<Dzien> wzorcowe) {
        Dzien wzorzec = wzorcowe.stream().filter(p->p.datastring.equals(this.datastring)).findFirst().get();
        resetnieobecnosc(wzorzec);
    }

  
    
}
