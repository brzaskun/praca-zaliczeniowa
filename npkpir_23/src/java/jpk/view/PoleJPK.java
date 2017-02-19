/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpk.view;

import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PoleJPK {
    private String symbol;
    private String opis;
    private boolean netto0vat1;

    PoleJPK(String symbol, String opis) {
        this.symbol = symbol;
        this.opis = opis;
        this.netto0vat1 = false;
    }
    
    PoleJPK(String symbol, String opis, boolean netto0vat1) {
        this.symbol = symbol;
        this.opis = opis;
        this.netto0vat1 = netto0vat1;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isNetto0vat1() {
        return netto0vat1;
    }

    public void setNetto0vat1(boolean netto0vat1) {
        this.netto0vat1 = netto0vat1;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.symbol);
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
        final PoleJPK other = (PoleJPK) obj;
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return symbol + " - " + opis;
    }
    
    
    
}
