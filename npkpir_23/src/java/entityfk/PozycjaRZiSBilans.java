/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import abstractClasses.ToBeATreeNodeObject;
import embeddablefk.KontoKwota;
import embeddablefk.StronaWierszaKwota;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class PozycjaRZiSBilans extends ToBeATreeNodeObject implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    protected Integer lp;
    @Size(max = 255)
    @Column(length = 255)
    protected String formula;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    protected double kwota;
    protected int level;
    protected int macierzysty;
    @Size(max = 255)
    @Column(length = 255)
    protected String nazwa;
    @Size(max = 255)
    @Column(length = 255)
    protected String podatnik;
    @Size(max = 255)
    @Column(length = 255)
    protected String pozycjaString;
    @Size(max = 255)
    @Column(length = 255)
    protected String pozycjaSymbol;
    protected Integer pozycjanr;
//    //przychod 0 koszt 1
//    //aktywa 2 pasywa 3
//    protected int bilanslubrzis;
    protected boolean przychod0koszt1;
    @Lob
    @Column(length=1048576)
    protected List<KontoKwota> przyporzadkowanekonta;
    @Lob
    @Column(length=1048576)
    protected List<StronaWierszaKwota> przyporzadkowanestronywiersza;
    @Size(max = 4)
    @Column(length = 4)
    protected String rok;
    @Size(max = 255)
    @Column(length = 255)
    protected String uklad;
    @Size(max = 255)
    @Column(length = 255)
    protected String de;

    public PozycjaRZiSBilans() {
    }
    
    public void obsluzPrzyporzadkowaneStronaWiersza(double kwota, StronaWiersza stronawiersza) {
        if (przyporzadkowanestronywiersza == null && kwota != 0.0) {
            przyporzadkowanestronywiersza = new ArrayList<>();
        }
        if (kwota != 0.0) {
            przyporzadkowanestronywiersza.add(new StronaWierszaKwota(stronawiersza, kwota));
        }
    }
    
    public void obsluzPrzyporzadkowaneKonta(double kwota, Konto konto) {
        if (przyporzadkowanekonta == null && kwota != 0.0) {
            przyporzadkowanekonta = new ArrayList<>();
        }
        if (kwota != 0.0) {
            przyporzadkowanekonta.add(new KontoKwota(konto, kwota));
        }
    }
    
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getMacierzysty() {
        return macierzysty;
    }

    @Override
    public void setMacierzysty(int macierzysty) {
        this.macierzysty = macierzysty;
    }

    @Override
    public Integer getLp() {
        return lp;
    }

    @Override
    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getPozycjaString() {
        return pozycjaString;
    }

    public void setPozycjaString(String pozycjaString) {
        this.pozycjaString = pozycjaString;
    }

    public String getPozycjaSymbol() {
        return pozycjaSymbol;
    }

    public void setPozycjaSymbol(String pozycjaSymbol) {
        this.pozycjaSymbol = pozycjaSymbol;
    }

    public Integer getPozycjanr() {
        return pozycjanr;
    }

    public void setPozycjanr(Integer pozycjanr) {
        this.pozycjanr = pozycjanr;
    }

    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    public List<KontoKwota> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(List<KontoKwota> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public List<StronaWierszaKwota> getPrzyporzadkowanestronywiersza() {
        return przyporzadkowanestronywiersza;
    }

    public void setPrzyporzadkowanestronywiersza(List<StronaWierszaKwota> przyporzadkowanestronywiersza) {
        this.przyporzadkowanestronywiersza = przyporzadkowanestronywiersza;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getUklad() {
        return uklad;
    }

    public void setUklad(String uklad) {
        this.uklad = uklad;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    
    
    @Override
    public String toString() {
        return "PozycjaRZiSBilans{" + "lp=" + lp + ", formula=" + formula + ", kwota=" + kwota + ", level=" + level + ", nazwa=" + nazwa + ", podatnik=" + podatnik + ", pozycjaString=" + pozycjaString + ", pozycjaSymbol=" + pozycjaSymbol + ", przychod0koszt1=" + przychod0koszt1 + ", rok=" + rok + ", uklad=" + uklad + '}';
    }

    
    
}
