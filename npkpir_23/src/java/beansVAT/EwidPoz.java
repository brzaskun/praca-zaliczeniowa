/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import entity.SchemaEwidencja;
import java.math.BigDecimal;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class EwidPoz {
    
    String polenetto;
    String polevat;
    SchemaEwidencja odnalezionyWierszSchemaEwidencja;
    SchemaEwidencja odnalezionyWierszSchemaEwidencjaMacierzysty;
    double netto;
    double vat;
    boolean tylkonetto;

    public EwidPoz(SchemaEwidencja odnalezionyWierszSchemaEwidencja, SchemaEwidencja odnalezionyWierszSchemaEwidencjaMacierzysty, BigDecimal netto, BigDecimal vat, boolean tylkonetto) {
        this.polenetto = odnalezionyWierszSchemaEwidencja.getPolenetto();
        this.polevat = odnalezionyWierszSchemaEwidencja.getPolevat();
        this.odnalezionyWierszSchemaEwidencja = odnalezionyWierszSchemaEwidencja;
        this.odnalezionyWierszSchemaEwidencjaMacierzysty = odnalezionyWierszSchemaEwidencjaMacierzysty;
        this.netto = Z.z(netto.doubleValue());
        this.vat = Z.z(vat.doubleValue());
        this.tylkonetto = false;
    }

    public String getPolenetto() {
        return polenetto;
    }

    public void setPolenetto(String polenetto) {
        this.polenetto = polenetto;
    }

    public String getPolevat() {
        return polevat;
    }

    public void setPolevat(String polevat) {
        this.polevat = polevat;
    }

    public SchemaEwidencja getOdnalezionyWierszSchemaEwidencja() {
        return odnalezionyWierszSchemaEwidencja;
    }

    public void setOdnalezionyWierszSchemaEwidencja(SchemaEwidencja odnalezionyWierszSchemaEwidencja) {
        this.odnalezionyWierszSchemaEwidencja = odnalezionyWierszSchemaEwidencja;
    }

    public SchemaEwidencja getOdnalezionyWierszSchemaEwidencjaMacierzysty() {
        return odnalezionyWierszSchemaEwidencjaMacierzysty;
    }

    public void setOdnalezionyWierszSchemaEwidencjaMacierzysty(SchemaEwidencja odnalezionyWierszSchemaEwidencjaMacierzysty) {
        this.odnalezionyWierszSchemaEwidencjaMacierzysty = odnalezionyWierszSchemaEwidencjaMacierzysty;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public boolean isTylkonetto() {
        return tylkonetto;
    }

    public void setTylkonetto(boolean tylkonetto) {
        this.tylkonetto = tylkonetto;
    }

    @Override
    public String toString() {
        return "EwidPoz{" + "polenetto=" + polenetto + ", polevat=" + polevat + ", odnalezionyWierszSchemaEwidencja=" + odnalezionyWierszSchemaEwidencja.getEvewidencja().getNazwa() + ", odnalezionyWierszSchemaEwidencjaMacierzysty=" + odnalezionyWierszSchemaEwidencjaMacierzysty.getEvewidencja().getNazwa() + ", netto=" + netto + ", vat=" + vat + ", tylkonetto=" + tylkonetto + '}';
    }
    
    
    
    
}
