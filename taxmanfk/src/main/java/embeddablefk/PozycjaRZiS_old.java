///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package embeddablefk;
//
//import abstractClasses.ToBeATreeNodeObject;
//import java.io.Serializable;
//
///**
// *
// * @author Osito
// */
//public class PozycjaRZiS extends ToBeATreeNodeObject implements Serializable{
//    private static final long serialVersionUID = 1L;
//    private int pozycjanr;
//    private String pozycjaString;
//    private String pozycjaSymbol;
//    private int macierzysty;
//    private int level;
//    private String nazwa;
//    private boolean przychod0koszt1;
//    private int lp;
//    private double kwota;
//    private String przyporzadkowanekonta;
//    private String formula;
//
//    public PozycjaRZiS(PozycjaRZiS pozycjaRZiS) {
//        this.pozycjanr = pozycjaRZiS.getPozycjanr();
//        this.pozycjaString = pozycjaRZiS.getPozycjaString();
//        this.pozycjaSymbol = pozycjaRZiS.getPozycjaSymbol();
//        this.macierzysty = pozycjaRZiS.getMacierzysty();
//        this.level = pozycjaRZiS.getLevel();
//        this.nazwa = pozycjaRZiS.getNazwa();
//        this.przychod0koszt1 = pozycjaRZiS.isPrzychod0koszt1();
//        this.lp = pozycjaRZiS.getLp();
//        this.formula = "";
//    }
//
//    public PozycjaRZiS(int pozycjanr, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, int lp) {
//        
//    }
//    
//
//    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1) {
//        this.lp = lp;
//        this.pozycjaString = pozycjaString;
//        this.pozycjaSymbol = pozycjaSymbol;
//        this.macierzysty = macierzysty;
//        this.level = level;
//        this.nazwa = nazwa;
//        this.przychod0koszt1 = przychod0koszt1;
//        this.formula = "";
//    }
//    
//    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota) {
//        this.lp = lp;
//        this.pozycjaString = pozycjaString;
//        this.pozycjaSymbol = pozycjaSymbol;
//        this.macierzysty = macierzysty;
//        this.level = level;
//        this.nazwa = nazwa;
//        this.przychod0koszt1 = przychod0koszt1;
//        this.kwota = kwota;
//        this.formula = "";
//    }
//
//    public PozycjaRZiS(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, String formula) {
//        this.lp = lp;
//        this.pozycjaString = pozycjaString;
//        this.pozycjaSymbol = pozycjaSymbol;
//        this.macierzysty = macierzysty;
//        this.level = level;
//        this.nazwa = nazwa;
//        this.przychod0koszt1 = przychod0koszt1;
//        this.kwota = 0.0;
//        this.formula = formula;
//    }
//    
//    
//    public int getPozycjanr() {
//        return pozycjanr;
//    }
//
//    public void setPozycjanr(int pozycjanr) {
//        this.pozycjanr = pozycjanr;
//    }
//
//    public String getPozycjaString() {
//        return pozycjaString;
//    }
//
//    public void setPozycjaString(String pozycjaString) {
//        this.pozycjaString = pozycjaString;
//    }
//
//    public String getPozycjaSymbol() {
//        return pozycjaSymbol;
//    }
//
//    public void setPozycjaSymbol(String pozycjaSymbol) {
//        this.pozycjaSymbol = pozycjaSymbol;
//    }
//
//    @Override
//    public int getMacierzysty() {
//        return macierzysty;
//    }
//
//    @Override
//    public void setMacierzysty(int macierzysty) {
//        this.macierzysty = macierzysty;
//    }
//   
//    @Override
//     public int getLevel() {
//        return level;
//    }
//
//    @Override
//    public void setLevel(int level) {
//        this.level = level;
//    }
//
//    public String getNazwa() {
//        return nazwa;
//    }
//
//    public void setNazwa(String nazwa) {
//        this.nazwa = nazwa;
//    }
//
//    public boolean isPrzychod0koszt1() {
//        return przychod0koszt1;
//    }
//
//    public void setPrzychod0koszt1(boolean przychod0koszt1) {
//        this.przychod0koszt1 = przychod0koszt1;
//    }
//
//    public int getLp() {
//        return lp;
//    }
//
//    public void setLp(int lp) {
//        this.lp = lp;
//    }
//
//    public double getKwota() {
//        return kwota;
//    }
//
//    public void setKwota(double kwota) {
//        this.kwota = kwota;
//    }
//
//    public String getPrzyporzadkowanekonta() {
//        return przyporzadkowanekonta;
//    }
//
//    public void setPrzyporzadkowanekonta(String przyporzadkowanekonta) {
//        this.przyporzadkowanekonta = przyporzadkowanekonta;
//    }
//
//    public String getFormula() {
//        return formula;
//    }
//
//    public void setFormula(String formula) {
//        this.formula = formula;
//    }
//
//    
//   
//    
//    
//    
//}
