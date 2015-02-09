/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortfunction;

/**
 *
 * @author Osito
 */
public class Kontonumer {
    String numer;

    public Kontonumer() {
    }

    
    public Kontonumer(String numer) {
        this.numer = numer;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    @Override
    public String toString() {
        return numer;
    }

    
    
    
}
