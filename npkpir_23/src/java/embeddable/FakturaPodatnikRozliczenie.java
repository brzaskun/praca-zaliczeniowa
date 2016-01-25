/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Faktura;
import entity.FakturaRozrachunki;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class FakturaPodatnikRozliczenie implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int lp;
    private Faktura faktura;
    private FakturaRozrachunki rozliczenie;
    private boolean faktura0rozliczenie1;
    private String rok;
    private String mc;
    private String data;
    private boolean nowy0rozliczony1;
    private double kwota;
    private double saldo;

    public FakturaPodatnikRozliczenie(FakturaRozrachunki p) {
        this.rozliczenie = p;
        this.faktura0rozliczenie1 = !p.isZaplata0korekta1();
        this.rok = p.getRok();
        this.mc = p.getMc();
        this.data = p.getData();
        this.kwota = p.getKwota();
    }

    public FakturaPodatnikRozliczenie(Faktura r) {
        this.faktura = r;
        this.faktura0rozliczenie1 = false;
        this.rok = r.getRok();
        this.mc = r.getMc();
        this.data = r.getDatawystawienia();
        this.kwota = r.getBruttopk() != 0.0 ? r.getBruttopk() : r.getBrutto();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.faktura);
        hash = 17 * hash + Objects.hashCode(this.rozliczenie);
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
        final FakturaPodatnikRozliczenie other = (FakturaPodatnikRozliczenie) obj;
        if (!Objects.equals(this.faktura, other.faktura)) {
            return false;
        }
        if (!Objects.equals(this.rozliczenie, other.rozliczenie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaRozliczenie{" + "lp=" + lp + ", faktura=" + faktura.getFakturaPK().getNumerkolejny() + ", rozliczenie=" + rozliczenie.getNrdokumentu() + ", faktura0rozliczenie1=" + faktura0rozliczenie1 + ", rok=" + rok + ", mc=" + mc + ", nowy0rozliczony1=" + nowy0rozliczony1 + '}';
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    public void setFaktura(Faktura faktura) {
        this.faktura = faktura;
    }

    public FakturaRozrachunki getRozliczenie() {
        return rozliczenie;
    }

    public void setRozliczenie(FakturaRozrachunki rozliczenie) {
        this.rozliczenie = rozliczenie;
    }

    public boolean isFaktura0rozliczenie1() {
        return faktura0rozliczenie1;
    }

    public void setFaktura0rozliczenie1(boolean faktura0rozliczenie1) {
        this.faktura0rozliczenie1 = faktura0rozliczenie1;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isNowy0rozliczony1() {
        return nowy0rozliczony1;
    }

    public void setNowy0rozliczony1(boolean nowy0rozliczony1) {
        this.nowy0rozliczony1 = nowy0rozliczony1;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getKontrahent() {
        if (this.faktura != null) {
            return this.faktura.getKontrahent().getNpelna();
        } else {
            return this.rozliczenie.getKontrahent().getNpelna();
        }
    }
    
    public String getWprowadzil() {
        if (this.faktura != null) {
            return this.faktura.getAutor();
        } else {
            return this.rozliczenie.getWprowadzil().getLogin();
        }
    }
    
    public String getRodzajDok() {
        if (this.faktura != null) {
            return "faktura";
        } else {
            return this.rozliczenie.getRodzajdokumentu();
        }
    }
    
    public String getNrDok() {
        if (this.faktura != null) {
            return this.faktura.getFakturaPK().getNumerkolejny();
        } else {
            return this.rozliczenie.getNrdokumentu();
        }
    }
    
}
