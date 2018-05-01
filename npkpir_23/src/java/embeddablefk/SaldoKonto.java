/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class SaldoKonto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Konto konto;
    private double boWn;
    private double boMa;
    private double obrotyWnMc;
    private double obrotyMaMc;
    private double obrotyWn;
    private double obrotyMa;
    private double obrotyBoWn;
    private double obrotyBoMa;
    private double saldoWn;
    private double saldoMa;
    private String nrpelnymacierzystego;
    private List<StronaWiersza> zapisy;
    private Waluty walutadlabo;
    private String opisdlabo;
    private double kursdlaBO;
    private double saldoWnPLN;
    private double saldoMaPLN;
    private boolean roznicakursowastatystyczna;

    public SaldoKonto() {
        this.zapisy = new ArrayList<>();
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa) {
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = new ArrayList<>();
    }
    
    public SaldoKonto (Konto konto, double saldoWn, double saldoMa, Waluty waluta, List<StronaWiersza> zapisy) {
        this.konto = konto;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.zapisy = new ArrayList<>();
        this.walutadlabo = waluta;
        this.zapisy = zapisy;
    }

    public SaldoKonto(StronaWiersza t,Waluty wal) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursWalutyBOSW();
        boolean mniejszeodzera = t.getKwota() < 0.0;
        if (t.getWnma().equals("Wn")) {
            if (mniejszeodzera) {
                this.saldoWn = -t.getPozostalo();
                this.saldoWnPLN = -t.getPozostaloPLN();
                this.saldoMa = 0.0;
            } else {
                this.saldoWn = t.getPozostalo();
                this.saldoWnPLN = t.getPozostaloPLN();
                this.saldoMa = 0.0;
            }
        } else {
            if (mniejszeodzera) {
                this.saldoWn = 0.0;
                this.saldoMa = -t.getPozostalo();
                this.saldoMaPLN = -t.getPozostaloPLN();
            } else {
                this.saldoWn = 0.0;
                this.saldoMa = t.getPozostalo();
                this.saldoMaPLN = t.getPozostaloPLN();
            }
        }
        this.zapisy = new ArrayList<>();
        this.zapisy.add(t);
        this.walutadlabo = wal;
        this.opisdlabo = t.getDokfkS()+" "+t.getDokfk().getNumerwlasnydokfk()+" "+t.getWiersz().getOpisWiersza()+" zapis BO";
    }
    
    public SaldoKonto(SaldoKonto t, Waluty wal, double kwota, double kwotapln) {
        this.konto = t.getKonto();
        this.kursdlaBO = kwota != 0.0 ? Z.z4(kwotapln/kwota) : 0.0;
        boolean mniejszeodzera = kwota < 0.0 ? true : kwotapln < 0.0 ? true : false;
        if (mniejszeodzera) {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = -kwota;
            this.saldoMaPLN = -kwotapln;
        } else {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwotapln;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        }
        this.walutadlabo = wal;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" waluta "+wal.getSymbolwaluty();
    }
    
    public SaldoKonto(SaldoKonto t, Waluty wal, double kwota, double kwotapln, boolean wn) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursdlaBO();
        if (wn) {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwotapln;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        } else {
            this.saldoWn = 0.0;
            this.saldoWnPLN = 0.0;
            this.saldoMa = kwota;
            this.saldoMaPLN = kwotapln;
        }
        this.walutadlabo = wal;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" waluta "+wal.getSymbolwaluty()+" roznicekursowe";
    }
    
    public SaldoKonto(StronaWiersza t,Waluty wal, double kwota, double kwotapln) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursWalutyBOSW();
        boolean mniejszeodzera = kwota < 0.0;
        if (t.getWnma().equals("Wn")) {
            if (mniejszeodzera) {
                this.saldoWn = -kwota;
                this.saldoWnPLN = -kwotapln;
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            } else {
                this.saldoWn = kwota;
                this.saldoWnPLN = kwotapln;
                this.saldoMa = 0.0;
                this.saldoMaPLN = 0.0;
            }
        } else {
            if (mniejszeodzera) {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = -kwota;
                this.saldoMaPLN = -kwotapln;
            } else {
                this.saldoWn = 0.0;
                this.saldoWnPLN = 0.0;
                this.saldoMa = kwota;
                this.saldoMaPLN = kwotapln;
            }
        }
        this.zapisy = new ArrayList<>();
        this.zapisy.add(t);
        this.walutadlabo = wal;
        this.opisdlabo = t.getDokfkS()+" "+t.getDokfk().getNumerwlasnydokfk()+" "+t.getWiersz().getOpisWiersza()+ "/"+t.getWiersz().getIdporzadkowy()+" zapis BO";
    }

    public SaldoKonto(SaldoKonto t, double kwota, Waluty walutapln) {
        this.konto = t.getKonto();
        this.kursdlaBO = t.getKursdlaBO();
        if (kwota > 0.0) {
            this.saldoWn = kwota;
            this.saldoWnPLN = kwota;
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
        } else {
            this.saldoMa = 0.0;
            this.saldoMaPLN = 0.0;
            this.saldoWn = kwota;
            this.saldoWnPLN = kwota;
        }
        this.zapisy = new ArrayList<>();
        this.walutadlabo = walutapln;
        this.opisdlabo = "konto: "+t.getKonto().getPelnynumer()+" nierozliczone różnice kursowe";
    }
    
     public String getStyleWn() {
        String zwrot = "float: right; color: green; font-weight: 700;";
        if (this.saldoWn != 0.0 && this.konto.getWnma0wm1ma2() == 2) {
            zwrot = "float: right; color: red; font-weight: 900;";
        }
        return zwrot;
    }
    
    public String getStyleMa() {
        String zwrot = "float: right; color: green; font-weight: 700;";
        if (this.saldoMa != 0.0 && this.konto.getWnma0wm1ma2() == 1) {
            zwrot = "float: right; color: red; font-weight: 900;";
        }
        return zwrot;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.konto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SaldoKonto other = (SaldoKonto) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SaldoKonto{" + " " + konto.getPelnynumer() + ", obrotyBoWn=" + obrotyBoWn + ", obrotyBoMa=" + obrotyBoMa + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa +", wal=" + walutadlabo.getSymbolwaluty() + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public double getObrotyWnMc() {
        return obrotyWnMc;
    }

    public void setObrotyWnMc(double obrotyWnMc) {
        this.obrotyWnMc = obrotyWnMc;
    }

    public double getObrotyMaMc() {
        return obrotyMaMc;
    }

    public void setObrotyMaMc(double obrotyMaMc) {
        this.obrotyMaMc = obrotyMaMc;
    }

    public boolean isRoznicakursowastatystyczna() {
        return roznicakursowastatystyczna;
    }

    public void setRoznicakursowastatystyczna(boolean roznicakursowastatystyczna) {
        this.roznicakursowastatystyczna = roznicakursowastatystyczna;
    }

    
    public Konto getKonto() {
        return konto;
    }
    
    public String getTopKontoOpis() {
        Konto zwrot = this.konto.getTopKonto();
        if (zwrot.equals(this.konto)) {
            zwrot = null;
        }
        return zwrot != null ? zwrot.getNazwaKontaInt() : "";
    }
    
    public Konto getTopKonto() {
        Konto zwrot = this.konto.getTopKonto();
        return zwrot;
    }
    
    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    public double getBoWn() {
        return boWn;
    }
    
    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }
    
    public double getBoMa() {
        return boMa;
    }
    
    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }
    
    public double getObrotyWn() {
        return obrotyWn;
    }
    
    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }
    
    public double getObrotyMa() {
        return obrotyMa;
    }
    
    public void setObrotyMa(double obrotyMa) {
        this.obrotyMa = obrotyMa;
    }
    
    public double getObrotyBoWn() {
        return obrotyBoWn;
    }
    
    public void setObrotyBoWn(double obrotyBoWn) {
        this.obrotyBoWn = obrotyBoWn;
    }
    
    public double getObrotyBoMa() {
        return obrotyBoMa;
    }
    
    public void setObrotyBoMa(double obrotyBoMa) {
        this.obrotyBoMa = obrotyBoMa;
    }
    
    public double getSaldoWn() {
        return saldoWn;
    }
    
    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }
    
    public double getSaldoMa() {
        return saldoMa;
    }
    
    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }
    
    public List<StronaWiersza> getZapisy() {
        return zapisy;
    }
    
    public void setZapisy(List<StronaWiersza> zapisy) {
        this.zapisy = zapisy;
    }
    
    public String getNrpelnymacierzystego() {
        return nrpelnymacierzystego;
    }
    
    public void setNrpelnymacierzystego(String nrpelnymacierzystego) {
        this.nrpelnymacierzystego = nrpelnymacierzystego;
    }
    
    public Waluty getWalutadlabo() {
        return walutadlabo;
    }
    
    public void setWalutadlabo(Waluty walutadlabo) {
        this.walutadlabo = walutadlabo;
    }
    
    public String getOpisdlabo() {
        return opisdlabo;
    }
    
    public void setOpisdlabo(String opisdlabo) {
        this.opisdlabo = opisdlabo;
    }
    
    public double getKursdlaBO() {
        return kursdlaBO;
    }
    
    public void setKursdlaBO(double kursdlaBO) {
        this.kursdlaBO = kursdlaBO;
    }
    
    public double getSaldoWnPLN() {
        return saldoWnPLN;
    }
    
    public void setSaldoWnPLN(double saldoWnPLN) {
        this.saldoWnPLN = saldoWnPLN;
    }
    
    public double getSaldoMaPLN() {
        return saldoMaPLN;
    }
    
    public void setSaldoMaPLN(double saldoMaPLN) {
        this.saldoMaPLN = saldoMaPLN;
    }
    
    public String getNazwaObcieta(int ilepokazac) {
        String zwrot = this.konto.getNazwapelna();
        if (zwrot.length() > ilepokazac) {
            zwrot = zwrot.substring(0,ilepokazac);
        }
        return zwrot;
    }
//</editor-fold>
    
    
    public void sumujBOZapisy() {
        this.obrotyBoWn = Z.z(this.boWn + this.obrotyWn);
        this.obrotyBoMa = Z.z(this.boMa + this.obrotyMa);
    }

    public void wyliczSaldo() {
        double Wn_Ma = Z.z(this.obrotyBoWn - this.obrotyBoMa);
        double Ma_Wn = Z.z(this.obrotyBoMa - this.obrotyBoWn);
        this.saldoWn = this.obrotyBoWn > this.obrotyBoMa ? Wn_Ma : 0.0;
        this.saldoMa = this.obrotyBoMa > this.obrotyBoWn ? Ma_Wn : 0.0;
    }
    
    
    
}
