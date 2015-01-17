/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

/**
 *
 * @author Osito
 */
public class CitBiezacyPozycja {
    private int id;
    private String symbolrzis;
    private String opis;
    private boolean przychod0koszt1;
    private double kwota;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbolrzis() {
        return symbolrzis;
    }

    public void setSymbolrzis(String symbolrzis) {
        this.symbolrzis = symbolrzis;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }
    
    
}
