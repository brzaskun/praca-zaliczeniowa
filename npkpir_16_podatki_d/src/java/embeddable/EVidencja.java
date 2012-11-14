/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVidencja implements Serializable{
    private String nazwaEwidencji; 
    private String pozycjaNaDeklaracji;
    private String transAkcja;
    private String rodzajZakupu;
    boolean tylkoNetto;

    public EVidencja() {
    }

    public EVidencja(String nazwaEwidencji, String pozycjaNaDeklaracji, String transAkcja, String rodzajZakupu, boolean tylkoNetto) {
        this.nazwaEwidencji = nazwaEwidencji;
        this.pozycjaNaDeklaracji = pozycjaNaDeklaracji;
        this.transAkcja = transAkcja;
        this.rodzajZakupu = rodzajZakupu;
        this.tylkoNetto = tylkoNetto;
    }
    
    

    public String getNazwaEwidencji() {
        return nazwaEwidencji;
    }

    public void setNazwaEwidencji(String nazwaEwidencji) {
        this.nazwaEwidencji = nazwaEwidencji;
    }

    public String getPozycjaNaDeklaracji() {
        return pozycjaNaDeklaracji;
    }

    public void setPozycjaNaDeklaracji(String pozycjaNaDeklaracji) {
        this.pozycjaNaDeklaracji = pozycjaNaDeklaracji;
    }

    public String getTransAkcja() {
        return transAkcja;
    }

    public void setTransAkcja(String transAkcja) {
        this.transAkcja = transAkcja;
    }

    public boolean isTylkoNetto() {
        return tylkoNetto;
    }

    public void setTylkoNetto(boolean tylkoNetto) {
        this.tylkoNetto = tylkoNetto;
    }

    public String getRodzajZakupu() {
        return rodzajZakupu;
    }

    public void setRodzajZakupu(String rodzajZakupu) {
        this.rodzajZakupu = rodzajZakupu;
    }

    @Override
    public String toString() {
        return nazwaEwidencji;
    }

    
    

    
}
