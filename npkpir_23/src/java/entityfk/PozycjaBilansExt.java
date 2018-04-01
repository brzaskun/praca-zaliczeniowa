/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.StronaWierszaKwota;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Osito
 */
public class PozycjaBilansExt extends PozycjaRZiSBilans implements Serializable {
                
    public PozycjaBilansExt() {
    }
    
    public PozycjaBilansExt(Integer lp) {
        this.lp = lp;
    }
    
    public PozycjaBilansExt(PozycjaBilansExt pozycjaBilans) {
        this.pozycjanr = pozycjaBilans.getPozycjanr();
        this.pozycjaString = pozycjaBilans.getPozycjaString();
        this.pozycjaSymbol = pozycjaBilans.getPozycjaSymbol();
        this.macierzysty = pozycjaBilans.getMacierzysty();
        this.level = pozycjaBilans.getLevel();
        this.nazwa = pozycjaBilans.getNazwa();
        this.przychod0koszt1 = pozycjaBilans.isPrzychod0koszt1();
        this.lp = pozycjaBilans.getLp();
        this.formula = "";
    }

    public PozycjaBilansExt(int pozycjanr, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, int lp) {
        
    }
    

    public PozycjaBilansExt(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.formula = "";
    }
    
    public PozycjaBilansExt(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = kwota;
        this.formula = "";
    }

    public PozycjaBilansExt(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, String formula) {
        this.lp = lp;
        this.pozycjaString = pozycjaString;
        this.pozycjaSymbol = pozycjaSymbol;
        this.macierzysty = macierzysty;
        this.level = level;
        this.nazwa = nazwa;
        this.przychod0koszt1 = przychod0koszt1;
        this.kwota = 0.0;
        this.formula = formula;
    }
    

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
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

    public List<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(List<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    @Override
     public List<StronaWierszaKwota> getPrzyporzadkowanestronywiersza() {
        return przyporzadkowanestronywiersza;
    }

    @Override
    public void setPrzyporzadkowanestronywiersza(List<StronaWierszaKwota> przyporzadkowanestronywiersza) {
        this.przyporzadkowanestronywiersza = przyporzadkowanestronywiersza;
    }


    @Override
    public String getRok() {
        return rok;
    }

    @Override
    public void setRok(String rok) {
        this.rok = rok;
    }

    @Override
    public String getUklad() {
        return uklad;
    }

    @Override
    public void setUklad(String uklad) {
        this.uklad = uklad;
    }
    
    @Override
    public String getDe() {
        return de;
    }
    @Override
    public void setDe(String de) {
        this.de = de;
    }

   

    @Override
    public String toString() {
        return "PozycjaBilans{" + "lp=" + lp + ", formula=" + formula + ", nazwa=" + nazwa + ", pozycjaString=" + pozycjaString + ", pozycjaSymbol=" + pozycjaSymbol + ", pozycjanr=" + pozycjanr + ", rok=" + rok + ", uklad=" + uklad + '}';
    }

   
    
}
